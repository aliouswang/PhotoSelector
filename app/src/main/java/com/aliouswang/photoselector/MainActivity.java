package com.aliouswang.photoselector;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.Button;
import android.widget.GridView;

import com.aliouswang.photoselector.library.activity.ChoosePhotoActivity;
import com.aliouswang.photoselector.library.model.DiskPhoto;

import java.util.ArrayList;

public class MainActivity extends FragmentActivity {

    private GridView mGridView;
    private SelectedPhotoAdapter mPhotoAdapter;
    private ArrayList<DiskPhoto> mSelectPhotos  = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_layout);

        Button select_photo = (Button) findViewById(R.id.select_photo);
        select_photo.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, ChoosePhotoActivity.class);
                intent.putExtra(ChoosePhotoActivity.CHOOSE_PHOTO_INTENT_FLAG, mSelectPhotos);
                startActivityForResult(intent, ChoosePhotoActivity.CHOOSE_PHOTO_REQUEST_CODE);
            }
        });

        mGridView = (GridView) findViewById(R.id.gridview);
        mPhotoAdapter = new SelectedPhotoAdapter(this, new int [] {R.layout.select_photo_item});
        mGridView.setAdapter(mPhotoAdapter);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK && requestCode == ChoosePhotoActivity.CHOOSE_PHOTO_REQUEST_CODE) {
            if (data != null) {
                Object object = data.getSerializableExtra(ChoosePhotoActivity.CHOOSE_PHOTO_INTENT_FLAG);
                if (object != null) {
                    mSelectPhotos = (ArrayList<DiskPhoto>) object;
                    mPhotoAdapter.setData(mSelectPhotos);
                }
            }
        }
    }
}
