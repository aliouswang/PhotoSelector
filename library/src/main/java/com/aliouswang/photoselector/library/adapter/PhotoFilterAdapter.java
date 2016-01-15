package com.aliouswang.photoselector.library.adapter;

import android.content.Context;
import android.view.View;

import com.aliouswang.photoselector.library.R;
import com.aliouswang.photoselector.library.model.DiskPhotoGroup;

import java.util.List;

/**
 * Created by aliouswang on 16/1/5.
 */
public class PhotoFilterAdapter extends AdapterEnhancedBase<DiskPhotoGroup>{

    private int selectFolderIndex = 0;

    public PhotoFilterAdapter(Context context, int[] layoutResArrays) {
        super(context, layoutResArrays);
    }

    public PhotoFilterAdapter(Context context, int[] layoutResArrays, List<DiskPhotoGroup> data) {
        super(context, layoutResArrays, data);
    }

    public void setSelectIndex(int selectIndex) {
        if (this.selectFolderIndex == selectIndex) return;
        this.selectFolderIndex = selectIndex;
        notifyDataSetChanged();
    }

    @Override
    protected void convert(ViewHolderHelper helper, DiskPhotoGroup item) {
        helper.setText(R.id.id_dir_item_name, item.dirName);
        helper.setFrescoImageFromDiskPath(R.id.id_dir_item_image,
                item.getFirstImgPath());
        helper.setText(R.id.id_dir_item_count, item.getImageCount() + "张");
        if (selectFolderIndex == helper.getPosition()) {
            helper.setVisiable(R.id.img_select_tag, View.VISIBLE);
        }else {
            helper.setVisiable(R.id.img_select_tag, View.GONE);
        }
    }
}
