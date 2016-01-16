package com.aliouswang.photoselector.library;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.aliouswang.photoselector.library.adapter.PhotoFilterAdapter;
import com.aliouswang.photoselector.library.adapter.PhotoImageAdapter;
import com.aliouswang.photoselector.library.model.PhotoLoadResult;
import com.aliouswang.photoselector.library.task.PhotoLoadListener;
import com.aliouswang.photoselector.library.task.PhotoLoadTask;
import com.aliouswang.photoselector.library.utils.ScreenUtils;

/**
 * Created by aliouswang on 16/1/15.
 */
public class ChoosePhotoFragment extends Fragment{

    public static final int DEFAULT_ANIM_DURATION = 500;
    private View mRootView;

    private TextView mFilterTextView;
    private ListView mFilterListView;
    private PhotoFilterAdapter mPhotoFilterAdapter;
    private boolean bFilterShowing = false;
    private volatile boolean isFilterAnimaing = false;
    private int mFilterHeight;

    private GridView mPhotoGridView;
    private PhotoImageAdapter mPhotoImageAdapter;
    private View mMaskerView;

    private PhotoLoadResult mPhotoLoadResult;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater,
                             @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mRootView = inflater.inflate(R.layout.choose_photo_fragment_layout, container, false);
        initView();
        accessData();
        return mRootView;
    }

    public void initView() {
        mFilterTextView = (TextView) mRootView.findViewById(R.id.tv_filter);
        mFilterTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isFilterAnimaing) return;
                if (bFilterShowing) {
                    hideFilter(DEFAULT_ANIM_DURATION);
                }else {
                    showFilter(DEFAULT_ANIM_DURATION);
                }
            }
        });
        initFilterListView();
        initPhotoGridView();
    }

    private void calculateFilterHeight() {
        int count = mPhotoFilterAdapter.getCount();
        int totalCellHeight = (int) (count * ScreenUtils.dpToPx(getContext(), 110));
        int topMargin = (int) ScreenUtils.dpToPx(getContext(), 130);
        int extraHeight = topMargin + (int) ScreenUtils.dpToPx(getContext(), 50) ;
        int screenHeight = ScreenUtils.getScreenHeight(getContext());
        int aviableHeight = screenHeight - extraHeight;
        if (totalCellHeight < aviableHeight) {
            mFilterHeight = totalCellHeight;
            topMargin += (aviableHeight - totalCellHeight);
        }else {
            mFilterHeight = aviableHeight;
        }
        RelativeLayout.LayoutParams rlp = (RelativeLayout.LayoutParams)
                mFilterListView.getLayoutParams();
        rlp.topMargin = topMargin;
        mFilterListView.setLayoutParams(rlp);


    }

    private PhotoLoadTask mPhotoLoadTask;
    public void accessData() {
        mPhotoLoadTask = PhotoLoadTask.getInstance(getContext())
                .setLoadListener(new PhotoLoadListener() {
                    @Override
                    public void preLoad() {

                    }

                    @Override
                    public void finishLoad(PhotoLoadResult photoLoadResult) {
                        if (photoLoadResult != null) {
                            mPhotoLoadResult = photoLoadResult;
                            if (photoLoadResult != null && !photoLoadResult.isEmpty()) {
                                mPhotoImageAdapter.thumbPhotos = photoLoadResult.thumbPhotos;
                                mPhotoImageAdapter.setData(photoLoadResult.photoGroups.get(0).photos);

                                mPhotoFilterAdapter = new PhotoFilterAdapter(getContext(),
                                        new int [] {R.layout.list_dir_item},
                                        photoLoadResult.photoGroups);
                                mFilterListView.setAdapter(mPhotoFilterAdapter);
                                calculateFilterHeight();
                                hideFilter(0);
                                mFilterTextView.setText(photoLoadResult.photoGroups.get(0).dirName);
                            }
                        }
                    }
                })
                .beginTask();
    }

    private void initFilterListView() {
        mFilterListView = (ListView) mRootView.findViewById(R.id.list_filter);
        mFilterListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                mPhotoFilterAdapter.setSelectIndex(position);
                mPhotoImageAdapter.setData(mPhotoLoadResult.photoGroups.get(position)
                        .photos);
                hideFilter(DEFAULT_ANIM_DURATION);
                mFilterTextView.setText(mPhotoLoadResult.photoGroups.get(position).dirName);
            }
        });
    }

    private void initPhotoGridView() {
        mPhotoGridView = (GridView) mRootView.findViewById(R.id.gv_photos);
        mPhotoImageAdapter = new PhotoImageAdapter(getContext(),
                new int [] {R.layout.grid_image_layout});
        mPhotoGridView.setAdapter(mPhotoImageAdapter);
        mMaskerView = mRootView.findViewById(R.id.filter_master_view);
        mMaskerView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hideFilter(DEFAULT_ANIM_DURATION);
            }
        });
    }

    private void showFilter(int duration) {
        if (isFilterAnimaing) return;
        isFilterAnimaing = true;
        ObjectAnimator animator = ObjectAnimator.ofFloat(mFilterListView,
                "translationY", mFilterHeight, 0);
        animator.setDuration(duration);
        animator.setInterpolator(new AccelerateDecelerateInterpolator());
        animator.start();
        animator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {
                bFilterShowing = true;
                isFilterAnimaing = false;
                mMaskerView.setVisibility(View.VISIBLE);
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });

        ObjectAnimator alphaAnimator = ObjectAnimator.ofFloat(mMaskerView,
                "alpha", 0, 1);
        alphaAnimator.setDuration(duration);
        alphaAnimator.start();
    }

    private void hideFilter(int duration) {
        if (isFilterAnimaing) return;
        isFilterAnimaing = true;
        ObjectAnimator animator = ObjectAnimator.ofFloat(mFilterListView,
                "translationY", 0, mFilterHeight);
        animator.setDuration(duration);
        animator.setInterpolator(new AccelerateDecelerateInterpolator());
        animator.start();
        animator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {
                bFilterShowing = false;
                isFilterAnimaing = false;
                mMaskerView.setVisibility(View.GONE);
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });

        ObjectAnimator alphaAnimator = ObjectAnimator.ofFloat(mMaskerView,
                "alpha", 1, 0);
        alphaAnimator.setDuration(duration);
        alphaAnimator.start();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mPhotoLoadTask != null) {
            mPhotoLoadTask.cancelTask();
        }
    }
}
