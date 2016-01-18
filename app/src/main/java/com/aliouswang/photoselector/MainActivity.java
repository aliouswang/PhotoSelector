package com.aliouswang.photoselector;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.Button;
import android.widget.GridView;

public class MainActivity extends FragmentActivity {

    private GridView mGridView;
    private SelectedPhotoAdapter mPhotoAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_layout);

        Button select_photo = (Button) findViewById(R.id.select_photo);
        select_photo.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, PhotoSelectActivity.class);
                startActivity(intent);
            }
        });

        mGridView = (GridView) findViewById(R.id.gridview);
        mPhotoAdapter = new SelectedPhotoAdapter(this, new int [] {R.layout.select_photo_item});
        mGridView.setAdapter(mPhotoAdapter);
    }



}
