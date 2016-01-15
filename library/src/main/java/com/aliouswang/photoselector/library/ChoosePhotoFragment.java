package com.aliouswang.photoselector.library;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by aliouswang on 16/1/15.
 */
public class ChoosePhotoFragment extends Fragment{

    private View mRootView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater,
                             @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mRootView = inflater.inflate(R.layout.choose_photo_fragment_layout, container, false);
        initView();
        return mRootView;
    }

    public void initView() {

    }
}
