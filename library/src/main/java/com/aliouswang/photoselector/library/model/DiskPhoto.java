package com.aliouswang.photoselector.library.model;

import android.text.TextUtils;

/**
 * Created by aliouswang on 16/1/15.
 */
public class DiskPhoto extends Photo{

    public int thumb_id;
    public int image_id;
    public String thumb_path;
    public String image_path;

    public boolean thumbEmpty;

    @Override
    public String getImage() {
        if (!TextUtils.isEmpty(image_path)) {
            return image_path;
        }
        return thumb_path;
    }

    public String getThumbImage() {
        if (!TextUtils.isEmpty(thumb_path)) {
            return thumb_path;
        }
        return image_path;
    }

    @Override
    public boolean isDishPhoto() {
        return true;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || !(o instanceof DiskPhoto)) return false;
        DiskPhoto other = (DiskPhoto) o;
        return other.image_id == image_id;
    }
}
