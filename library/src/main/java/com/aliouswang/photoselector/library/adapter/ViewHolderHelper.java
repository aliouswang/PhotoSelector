package com.aliouswang.photoselector.library.adapter;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.facebook.drawee.drawable.ScalingUtils;
import com.facebook.drawee.view.SimpleDraweeView;


/**
 * ViewHolder pattern for ListView adapter.
 * It's support several types of view styles.
 *
 *
 * @author aliouswang
 *
 */
public class ViewHolderHelper {

    private SparseArray<View> mViews;


    private Context mContext;

    private int position;

    private View mConvertView;

    private ViewHolderHelper(Context context, ViewGroup parent, int layoutId, int position){
        this.mContext = context;
        this.position = position;
        this.mViews = new SparseArray<View>();
        this.mConvertView = LayoutInflater.from(mContext).inflate(layoutId, parent, false);
        mConvertView.setTag(this);
    }


    public static ViewHolderHelper get(Context context, View convertView, ViewGroup parent, int layoutId , int position){
        if(convertView == null){
            return new ViewHolderHelper(context, parent, layoutId, position);
        }
        ViewHolderHelper existingHelper = (ViewHolderHelper)convertView.getTag();
        existingHelper.position = position;
        return existingHelper;
    }

    public <T extends View> T retrieveView(int viewId){
        View view = mViews.get(viewId);
        if (view == null) {
            view = mConvertView.findViewById(viewId);
            mViews.put(viewId, view);
        }
        return (T)view;
    }

    public ViewHolderHelper setText(int viewId, String value){
        TextView view = retrieveView(viewId);
        view.setText(value);
        return this;
    }

    public ViewHolderHelper setTextColor(int viewId, int colorId){
        TextView view = retrieveView(viewId);
        view.setTextColor(mContext.getResources().getColor(colorId));
        return this;
    }
    public ViewHolderHelper setBtn(int viewId, View.OnClickListener listene){
        Button viewButton =retrieveView(viewId);
        viewButton.setOnClickListener(listene);
        return this;
    }

    public ViewHolderHelper setFrescoImageFromUrl(int viewId, String url) {
        SimpleDraweeView imageView = retrieveView(viewId);
        imageView.getHierarchy().setActualImageScaleType(ScalingUtils.ScaleType.FIT_XY);
        imageView.setImageURI(Uri.parse(url));
        return this;
    }

    public ViewHolderHelper setFrescoImageFromDiskPath(int viewId, String path) {
        SimpleDraweeView imageView = retrieveView(viewId);
        imageView.getHierarchy().setActualImageScaleType(ScalingUtils.ScaleType.FIT_XY);
        imageView.setImageURI(Uri.parse("file://" + path));
        return this;
    }

    public ViewHolderHelper setFrescoImageWithLoading(int viewId, String url) {
        SimpleDraweeView imageView = retrieveView(viewId);

//        GenericDraweeHierarchyBuilder builder =
//                new GenericDraweeHierarchyBuilder(mContext.getResources());
//        GenericDraweeHierarchy hierarchy = builder
//                .setFadeDuration(300)
//                .setProgressBarImage(new CircleProgressDrawable())
//                .build();
//        imageView.setHierarchy(hierarchy);

        imageView.setImageURI(Uri.parse(url));
        return this;
    }

    public ViewHolderHelper setImageFromUrl(int viewId, String url){
        setFrescoImageFromUrl(viewId, url);
//        ImageView imageView = retrieveView(viewId);
//		Picasso.with(mContext).load(url).placeholder(R.drawable.default_img_bg).into(imageView);
//        setImageFromUrl(viewId, url, R.drawable.default_load_failed_image);
        return this;
    }
//
//    public ViewHolderHelper setImageFromUrl(int viewId, String url , int defaultImageId){
//        ImageView imageView = retrieveView(viewId);
//        DdUtil.displayImage(mContext, imageView, url, defaultImageId);
//        return this;
//    }

    public ViewHolderHelper setImageResId(int viewId, int resId){
        ImageView imageView = retrieveView(viewId);
        imageView.setImageResource(resId);
        return this;
    }

    public ViewHolderHelper setImageResDrawable(int viewId, Drawable drawable){
        ImageView imageView = retrieveView(viewId);
        imageView.setImageDrawable(drawable);
        return this;
    }

    public ViewHolderHelper setBackgroundResId(int viewId, int resId){
        View view = retrieveView(viewId);
        view.setBackgroundResource(resId);
        return this;
    }

    public ViewHolderHelper setViewSize(int viewId, int width , int height){
        View view = retrieveView(viewId);
        RelativeLayout.LayoutParams rlp =
                (RelativeLayout.LayoutParams)view.getLayoutParams();
        rlp.width = width;
        rlp.height = height;
        view.setLayoutParams(rlp);
        return this;
    }

    public ViewHolderHelper setClickListener(int viewId, View.OnClickListener listener){
        View view = retrieveView(viewId);
        view.setOnClickListener(listener);
        return this;
    }

    public ViewHolderHelper setCheckBoxCheck(int viewId, boolean boxCheck) {
        View view = retrieveView(viewId);
        if (view instanceof CheckBox) {
            CheckBox checkBox = (CheckBox) view;
            checkBox.setChecked(boxCheck);
        }
        return this;
    }

    public ViewHolderHelper setEnable(int viewId, boolean enable) {
        View view = retrieveView(viewId);
        view.setEnabled(enable);
        return this;
    }

    public ViewHolderHelper setCheckBoxCheckListener(int viewId, CompoundButton.OnCheckedChangeListener listener) {
        View view = retrieveView(viewId);
        if (view instanceof CheckBox) {
            CheckBox checkBox = (CheckBox) view;
            checkBox.setOnCheckedChangeListener(listener);
        }
        return this;
    }


    public ViewHolderHelper setVisiable(int viewId, int visiable){
        View view = retrieveView(viewId);
        view.setVisibility(visiable);
        return this;
    }

    public ViewHolderHelper setClickable(int viewId, boolean bClickable){
        View view = retrieveView(viewId);
        view.setClickable(bClickable);
        view.setFocusable(bClickable);
        return this;
    }


    public View getView(){
        return this.mConvertView;
    }

    public int getPosition(){
        return this.position;
    }

}
