package com.aliouswang.photoselector.library.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

public abstract class AdapterQuickBase<T> extends AdapterBase<T, ViewHolderHelper> {
	
	public AdapterQuickBase(Context context, int[] layoutResArrays) {
		super(context, layoutResArrays);
	}

	public AdapterQuickBase(Context context, int[] layoutResArrays, List<T> data) {
		super(context, layoutResArrays, data);
	}
	
	protected ViewHolderHelper getAdapterHelper(int position, View convertView, ViewGroup parent){
		return ViewHolderHelper.get(mContext, convertView, parent, this.mLayoutResArrays[getItemViewType(position)], position);
	}

}
