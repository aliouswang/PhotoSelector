package com.aliouswang.photoselector.library.model;

import java.util.ArrayList;

/**
 * Created by aliouswang on 16/1/4.
 */
public class PhotoLoadResult {

    public ArrayList<DiskPhotoGroup> photoGroups;
    public ArrayList<DiskPhoto> thumbPhotos;

    public boolean isEmpty() {
        return photoGroups != null && photoGroups.isEmpty();
    }

    public void attachThumbToImage() {
        for (DiskPhotoGroup photoGroup : photoGroups) {
            ArrayList<DiskPhoto> photos = photoGroup.photos;
            if (photos != null) {
                for (DiskPhoto photo : photos) {
                    int index = thumbPhotos.indexOf(photo);
                    photo.thumb_path = thumbPhotos.get(index).thumb_path;
                }
            }
        }
    }

}
