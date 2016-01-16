package com.aliouswang.photoselector.library.adapter;

import android.content.Context;
import android.graphics.Color;
import android.text.TextUtils;
import android.view.View;

import com.aliouswang.photoselector.library.R;
import com.aliouswang.photoselector.library.model.DiskPhoto;
import com.facebook.drawee.view.SimpleDraweeView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by aliouswang on 16/1/5.
 */
public class PhotoImageAdapter extends AdapterEnhancedBase<DiskPhoto>{

    public static final int MAX_SELECT_IMAGE_COUNT = 9;

    private ArrayList<DiskPhoto> seleteImages = new ArrayList<DiskPhoto>();

    public ArrayList<DiskPhoto> thumbPhotos;

    public ArrayList<DiskPhoto> getSeleteImages() {
        return seleteImages;
    }


    public void setSeleteImages(ArrayList<DiskPhoto> seleteImages) {
        this.seleteImages = seleteImages;
        notifyDataSetChanged();
    }

    public PhotoImageAdapter(Context context, int[] layoutResArrays) {
        super(context, layoutResArrays);
    }

    public PhotoImageAdapter(Context context, int[] layoutResArrays, List<DiskPhoto> data) {
        super(context, layoutResArrays, data);
    }

    @Override
    protected void convert(final ViewHolderHelper helper, final DiskPhoto photo) {
        super.convert(helper, photo);
        String thumbPath = getThumbPath(photo);
        helper.setFrescoImageFromDiskPath(R.id.imageview, thumbPath);
        final SimpleDraweeView draweeView = helper.retrieveView(R.id.imageview);
        helper.setClickListener(R.id.img_select, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (seleteImages.contains(photo)) {
                    seleteImages.remove(photo);
                    helper.setImageResId(R.id.img_select, R.drawable.transparent_bg);
                    draweeView.setColorFilter(null);
                }else {
                    if (seleteImages.size() >= MAX_SELECT_IMAGE_COUNT) return;
                    seleteImages.add(photo);
                    helper.setImageResId(R.id.img_select, R.drawable.pic_choose);
                    draweeView.setColorFilter(Color.parseColor("#70000000"));
                }
            }
        });
        if (seleteImages.contains(photo)) {
            helper.setImageResId(R.id.img_select, R.drawable.pic_choose);
            draweeView.setColorFilter(Color.parseColor("#70000000"));
        }else {
            helper.setImageResId(R.id.img_select, R.drawable.transparent_bg);
            draweeView.setColorFilter(null);
        }
    }

    /**
     * Get thumb image path, if thumb image is null,
     * just return src photo's image path.
     * @param srcPhoto
     * @return
     */
    private String getThumbPath(DiskPhoto srcPhoto) {
        if (srcPhoto.thumbEmpty) {
            return srcPhoto.image_path;
        }
        else if (!TextUtils.isEmpty(srcPhoto.thumb_path)) {
            return srcPhoto.thumb_path;
        }
        for (DiskPhoto photo : thumbPhotos) {
            if (photo.image_id == srcPhoto.image_id) {
                if (!TextUtils.isEmpty(photo.thumb_path)) {
                    srcPhoto.thumb_path = photo.thumb_path;
                    return photo.thumb_path;
                }else {
                    photo.thumbEmpty = true;
                    return srcPhoto.image_path;
                }
            }
        }
        return srcPhoto.image_path;
    }
}
