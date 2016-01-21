package com.aliouswang.photoselector.library.adapter;

import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

/**
 * Created by Administrator on 2016/1/20 0020.
 */
public class PreviewPageAdapter extends PagerAdapter{

    private ArrayList<View> mViewList = new ArrayList<>();

    public void setViewList(ArrayList<View> viewList) {
        this.mViewList = viewList;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return mViewList.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView(mViewList.get(position));
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        container.addView(mViewList.get(position), ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT);
        return mViewList.get(position);
    }
}
