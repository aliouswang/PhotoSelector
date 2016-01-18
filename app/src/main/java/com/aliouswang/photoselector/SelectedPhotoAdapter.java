package com.aliouswang.photoselector;

import android.content.Context;

import com.aliouswang.photoselector.library.adapter.AdapterEnhancedBase;
import com.aliouswang.photoselector.library.adapter.ViewHolderHelper;
import com.aliouswang.photoselector.library.model.DiskPhoto;

import java.util.List;

/**
 * Created by aliouswang on 16/1/18.
 */
public class SelectedPhotoAdapter extends AdapterEnhancedBase<DiskPhoto>{

    public SelectedPhotoAdapter(Context context, int[] layoutResArrays) {
        super(context, layoutResArrays);
    }

    public SelectedPhotoAdapter(Context context, int[] layoutResArrays, List<DiskPhoto> data) {
        super(context, layoutResArrays, data);
    }

    @Override
    protected void convert(ViewHolderHelper helper, DiskPhoto item) {
        super.convert(helper, item);
        helper.setFrescoImageFromDiskPath(R.id.draweeView, item.getThumbImage());
    }
}
