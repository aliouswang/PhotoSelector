package com.aliouswang.photoselector.library;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.widget.Button;

import com.aliouswang.photoselector.library.listener.OnPhotoSelectChanged;
import com.aliouswang.photoselector.library.model.DiskPhoto;

import java.util.ArrayList;

/**
 * Created by aliouswang on 16/1/18.
 */
public class ChoosePhotoActivity extends FragmentActivity{

    public static final String CHOOSE_PHOTO_INTENT_FLAG = "choose_photo_intent_flag";
    public static final int CHOOSE_PHOTO_REQUEST_CODE = 0x100;

    private Button mConfirmButton;
    private ArrayList<DiskPhoto> mDiskPhotos;
    ChoosePhotoFragment mChoosePhotoFragment;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.choose_photo_activity);

        Intent intent = getIntent();
        if (intent != null) {
            mDiskPhotos =
                    (ArrayList<DiskPhoto>) intent.getSerializableExtra("CHOOSE_PHOTO_INTENT_FLAG");
        }

        mConfirmButton = (Button) findViewById(R.id.btn_confirm);

        FragmentManager manager = getSupportFragmentManager();
        mChoosePhotoFragment = new ChoosePhotoFragment();
        if (mDiskPhotos != null && !mDiskPhotos.isEmpty()) {
            mChoosePhotoFragment.setSelectPhotos(mDiskPhotos);
        }
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.replace(R.id.content, mChoosePhotoFragment);
        transaction.commit();

        mChoosePhotoFragment.setOnPhotoSelectChanged(new OnPhotoSelectChanged() {
            @Override
            public void onPhotoChanged(int curSize) {
                if (curSize <= 0) {
                    mConfirmButton.setEnabled(false);
                    mConfirmButton.setText("完成");
                    mConfirmButton.setBackgroundResource(R.drawable.dark_green_btn_bg);
                }else {
                    mConfirmButton.setEnabled(true);
                    mConfirmButton.setText("完成(" + curSize + "/9)");
                    mConfirmButton.setBackgroundResource(R.drawable.green_btn_selector);
                }
            }
        });
    }
}