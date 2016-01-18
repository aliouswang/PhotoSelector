package com.aliouswang.photoselector;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import com.aliouswang.photoselector.library.ChoosePhotoFragment;

/**
 * Created by aliouswang on 16/1/18.
 */
public class PhotoSelectActivity extends FragmentActivity{

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_main);

        FragmentManager manager = getSupportFragmentManager();
        ChoosePhotoFragment fragment = new ChoosePhotoFragment();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.replace(R.id.content, fragment);
        transaction.commit();
    }

}
