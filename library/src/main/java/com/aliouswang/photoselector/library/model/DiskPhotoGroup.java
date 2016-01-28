package com.aliouswang.photoselector.library.model;

import java.util.ArrayList;

/**
 * Created by aliouswang on 16/1/15.
 */
public class DiskPhotoGroup {

    public ArrayList<DiskPhoto> photos = new ArrayList<>();
    public String dirName;

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof DiskPhotoGroup)) {
            return false;
        }
        return dirName.equals(((DiskPhotoGroup)o).dirName);
    }

    /**
     *
     * @return
     */
    public String getFirstImgPath() {
        if (photos.size() > 0) {
//            String result = photos.get(0).image_path;
//            if (TextUtils.isEmpty(result)) {
//                result = photos.get(0).getThumbImage();
//            }
            return photos.get(0).getThumbImage();
        }
        return "";
    }

    /**
     *
     * @return
     */
    public int getImageCount() {
        return photos.size();
    }

}
