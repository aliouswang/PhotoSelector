package com.aliouswang.photoselector.library.model;

import android.text.TextUtils;

/**
 * Created by aliouswang on 16/1/15.
 */
public class NetworkPhoto extends Photo{

    public String thumbImageUrl;
    public String imageUrl;

    @Override
    public String getImage() {
        return imageUrl;
    }

    public String getThumbImage() {
        if (TextUtils.isEmpty(thumbImageUrl)) return imageUrl;
        return thumbImageUrl;
    }

    @Override
    public boolean isDishPhoto() {
        return false;
    }
}
