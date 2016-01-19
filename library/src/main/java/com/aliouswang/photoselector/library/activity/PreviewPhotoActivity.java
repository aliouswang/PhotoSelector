package com.aliouswang.photoselector.library.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;

import com.aliouswang.photoselector.library.R;
import com.aliouswang.photoselector.library.model.DiskPhoto;

import java.util.ArrayList;

/**
 * Created by Administrator on 2016/1/19 0019.
 */
public class PreviewPhotoActivity extends Activity{

    private ViewPager mViewPager;
    private ArrayList<DiskPhoto> mSelectedPhoto;

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

    }


}
