package com.aliouswang.photoselector.library.model;

import java.io.Serializable;

/**
 * Created by aliouswang on 16/1/15.
 */
public abstract class Photo implements Serializable {

    private static final long serialVersionUID = -1L;

    public abstract String getImage();

    public abstract String getThumbImage();

    public abstract boolean isDishPhoto();

}
