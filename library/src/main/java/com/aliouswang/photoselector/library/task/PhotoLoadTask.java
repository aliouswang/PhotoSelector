package com.aliouswang.photoselector.library.task;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;

import com.aliouswang.photoselector.library.model.DiskPhoto;
import com.aliouswang.photoselector.library.model.DiskPhotoGroup;
import com.aliouswang.photoselector.library.model.PhotoLoadResult;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import rx.Observable;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func2;
import rx.schedulers.Schedulers;

/**
 * Created by aliouswang on 16/1/15.
 */
public class PhotoLoadTask {

    private static volatile PhotoLoadTask instance;
    private PhotoLoadListener loadListener;
    private Subscription subscription;
    private Context mContext;

    private PhotoLoadTask() {

    }

    public static PhotoLoadTask getInstance(Context context) {
        if (instance == null) {
            synchronized (PhotoLoadTask.class) {
                if (instance == null) {
                    instance = new PhotoLoadTask();
                }
            }
        }
        instance.mContext = context;
        return instance;
    }

    public PhotoLoadTask setLoadListener(PhotoLoadListener listener) {
        this.loadListener = listener;
        return this;
    }

    public PhotoLoadTask beginTask() {
        if (subscription == null) {
            if (loadListener != null) {
                loadListener.preLoad();
            }
            Observable<PhotoLoadResult> loadImageTask
                    = Observable.create(new Observable.OnSubscribe<PhotoLoadResult>(){
                @Override
                public void call(Subscriber<? super PhotoLoadResult> subscriber) {
                    subscriber.onNext(getAllPhotoGroup());
                    subscriber.onCompleted();
                }
            })
                    ;
            Observable<DiskPhotoGroup> loadThumbTask =
                    Observable.create(new Observable.OnSubscribe<DiskPhotoGroup>() {
                        @Override
                        public void call(Subscriber<? super DiskPhotoGroup> subscriber) {
                            ArrayList<DiskPhoto> photos = getAllThumbPhotos();
                            DiskPhotoGroup photoGroup = new DiskPhotoGroup();
                            photoGroup.photos = photos;
                            subscriber.onNext(photoGroup);
                            subscriber.onCompleted();
                        }
                    });
            Observable zipTask = Observable.zip(loadImageTask, loadThumbTask, new Func2<PhotoLoadResult,
                    DiskPhotoGroup, PhotoLoadResult>() {
                @Override
                public PhotoLoadResult call(PhotoLoadResult photoLoadResult, DiskPhotoGroup photoGroup) {
                    PhotoLoadResult result
                            = new PhotoLoadResult();
                    result.photoGroups = photoLoadResult.photoGroups;
                    result.thumbPhotos = photoGroup.photos;
                    return result;
                }
            }).subscribeOn(Schedulers.newThread())
                    .observeOn(AndroidSchedulers.mainThread());
            subscription = zipTask.subscribe(new Subscriber<PhotoLoadResult>() {
                @Override
                public void onCompleted() {
                }

                @Override
                public void onError(Throwable e) {

                }

                @Override
                public void onNext(PhotoLoadResult photoLoadResult) {
                    if (loadListener != null) {
                        loadListener.finishLoad(photoLoadResult);
                    }
                }
            });
        }
        return this;
    }

    private ArrayList<DiskPhotoGroup> mPhotoGroups = new ArrayList<>();
    public PhotoLoadResult getAllPhotoGroup() {
        Uri mImageUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
        ContentResolver mContentResolver = this.mContext.getContentResolver();
        StringBuilder selection = new StringBuilder();
        selection.append(MediaStore.Images.Media.MIME_TYPE).append("=?");
        selection.append(" or ");
        selection.append(MediaStore.Images.Media.MIME_TYPE).append("=?");

        Cursor mCursor = null;
        try {
            final String[] projection = {MediaStore.Images.Media.DATA, MediaStore.Images.Media._ID };
            mCursor = mContentResolver.query(mImageUri, projection, selection.toString(), new String[] {
                    "image/jpeg", "image/png"
            }, MediaStore.Images.Media.DATE_TAKEN);

            // 遍历结果
            while (mCursor.moveToNext()) {
                String path = mCursor.getString(mCursor
                        .getColumnIndex(MediaStore.Images.Media.DATA));
                int imageId = mCursor.getInt(mCursor
                        .getColumnIndex(MediaStore.Images.Media._ID));
                File file = new File(path);
                String parentName = "";
                if (file.getParentFile() != null) {
                    parentName = file.getParentFile().getName();
                } else {
                    parentName = file.getName();
                }
                DiskPhotoGroup item = new DiskPhotoGroup();
                item.dirName = parentName;
                DiskPhoto photo = new DiskPhoto();
                photo.image_path = path;
                photo.image_id = imageId;
                int searchIdx = mPhotoGroups.indexOf(item);
                if (searchIdx >= 0) {
                    DiskPhotoGroup imageGroup = mPhotoGroups.get(searchIdx);
                    imageGroup.photos.add(photo);
                } else {
                    item.photos.add(photo);
                    mPhotoGroups.add(item);
                }
            }

            if (mPhotoGroups != null && !mPhotoGroups.isEmpty()) {
                Comparator<DiskPhotoGroup> comparator = new Comparator<DiskPhotoGroup>() {
                    @Override
                    public int compare(DiskPhotoGroup lhs, DiskPhotoGroup rhs) {
                        return rhs.photos.size() - lhs.photos.size();
                    }
                };
                Collections.sort(mPhotoGroups, comparator);
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (mCursor != null && !mCursor.isClosed()) {
                mCursor.close();
            }
            PhotoLoadResult photoLoadResult = new PhotoLoadResult();
            photoLoadResult.photoGroups = mPhotoGroups;
            return photoLoadResult;
        }
    }

    public ArrayList<DiskPhoto> getAllThumbPhotos() {
        String [] projection = {MediaStore.Images.Thumbnails._ID,
                MediaStore.Images.Thumbnails.IMAGE_ID,
                MediaStore.Images.Thumbnails.DATA};
        Cursor cursor = this.mContext.getContentResolver().query(MediaStore.Images.Thumbnails.EXTERNAL_CONTENT_URI,
                projection, null, null, null);
        ArrayList<DiskPhoto> photos = new ArrayList<>();
        if (cursor.moveToFirst()) {
            int _idColumn = cursor.getColumnIndex(MediaStore.Images.Thumbnails._ID);
            int image_idColumn = cursor.getColumnIndex(MediaStore.Images.Thumbnails.IMAGE_ID);
            int dataColumn = cursor.getColumnIndex(MediaStore.Images.Thumbnails.DATA);

            do {
                DiskPhoto photo = new DiskPhoto();
                photo.thumb_id = cursor.getInt(_idColumn);
                photo.image_id = cursor.getInt(image_idColumn);
                photo.thumb_path = cursor.getString(dataColumn);
                photos.add(photo);
            } while (cursor.moveToNext());
            if (cursor != null) {
                cursor.close();
            }
        }
        return photos;
    }

    public void cancelTask() {
        if (subscription != null && !subscription.isUnsubscribed()) {
            subscription.unsubscribe();
        }
    }

}
