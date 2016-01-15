package com.aliouswang.photoselector.library.task;

import com.aliouswang.photoselector.library.model.PhotoLoadResult;

/**
 * Created by aliouswang on 16/1/15.
 */
public interface PhotoLoadListener {

    void preLoad();
    void finishLoad(PhotoLoadResult loadResult);

}
