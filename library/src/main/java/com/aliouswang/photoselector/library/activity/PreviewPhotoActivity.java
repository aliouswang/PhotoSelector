package com.aliouswang.photoselector.library.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;

import com.aliouswang.photoselector.library.R;
import com.aliouswang.photoselector.library.adapter.PreviewPageAdapter;
import com.aliouswang.photoselector.library.model.DiskPhoto;
import com.bumptech.glide.Glide;

import java.util.ArrayList;

import uk.co.senab.photoview.PhotoView;

/**
 * Created by Administrator on 2016/1/19 0019.
 */
public class PreviewPhotoActivity extends Activity{

    private ViewPager mViewPager;
    private ArrayList<DiskPhoto> mSelectedPhoto;
    private PreviewPageAdapter mPageAdaper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.preview_photo_activity);
        parseIntent();
        initView();
    }

    private void parseIntent() {
        Intent intent = getIntent();
        if (intent != null) {
            mSelectedPhoto = (ArrayList)
                    intent.getSerializableExtra(ChoosePhotoActivity.CHOOSE_PHOTO_INTENT_FLAG);
        }
    }

    private void initView() {
        mViewPager = (ViewPager) findViewById(R.id.viewpager);
        mPageAdaper = new PreviewPageAdapter();
        mViewPager.setAdapter(mPageAdaper);
        setupViewPager();
    }

    private void setupViewPager() {
        ArrayList<View> viewList = new ArrayList<>();
        LayoutInflater inflater = LayoutInflater.from(this);
        for(DiskPhoto photo : mSelectedPhoto) {
            View view = inflater.inflate(R.layout.preview_photo_layout, null);
            viewList.add(view);
            PhotoView imageView = (PhotoView) view.findViewById(R.id.img_photo);
            Glide.with(this).load("file\\" + photo.getImage()).into(imageView);
        }
        mPageAdaper.setViewList(viewList);
    }


}
