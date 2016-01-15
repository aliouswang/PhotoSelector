package com.aliouswang.photoselector.library.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.ArrayList;
import java.util.List;

public abstract class AdapterBase <T, H extends ViewHolderHelper> extends BaseAdapter {

    protected final Context mContext;
    protected List<T> mData;
    protected final int [] mLayoutResArrays;

    public AdapterBase(Context context, int [] layoutResArrays){
        this(context, layoutResArrays, null);
    }

    public AdapterBase(Context context, int [] layoutResArrays, List<T> data){
        this.mData = data == null ? new ArrayList<T>() : data;
        this.mContext = context;
        this.mLayoutResArrays = layoutResArrays;
    }

    public void removeData(T data) {
        this.mData.remove(data);
        this.notifyDataSetChanged();
    }

    public void clearAllData() {
        this.mData.clear();
        this.notifyDataSetChanged();
    }

    public void setData(ArrayList<T> data){
        this.mData = data;
        this.notifyDataSetChanged();
    }

    public void addData(ArrayList<T> data){
        if(data != null){
            this.mData.addAll(data);
        }

        this.notifyDataSetChanged();
    }

    public void addData(int index, T data) {
        this.mData.add(index, data);
        this.notifyDataSetChanged();
    }

    public void addData(T data) {
        this.mData.add(data);
        this.notifyDataSetChanged();
    }

    public ArrayList<T> getAllData() {
        return (ArrayList<T>)this.mData;
    }

    public void replaceData(T data, int replaceIndex) {
        this.mData.remove(replaceIndex);
        this.mData.add(replaceIndex, data);
        this.notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        if(this.mData == null){
            return getShowLoadMore()? 1 : 0;
        }
        return getShowLoadMore()? this.mData.size() + 1 : this.mData.size();
    }

    @Override
    public T getItem(int position) {
        if(position >= this.mData.size()){
            return null;
        }
        return mData.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getViewTypeCount() {
        return this.mLayoutResArrays.length;
    }

    /**
     * You should always override this method,to return the
     * correct view type for every cell
     *
     */
    public int getItemViewType(int position){
        if (isLoadMoreCell(position)) {
            return 1;
        }else {
            return 0;
        }
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final H helper = getAdapterHelper(position, convertView, parent);
        if (!isLoadMoreCell(position)) {
            T item = getItem(position);
            convert(helper, item);
        }
        return helper.getView();
    }

    protected abstract void convert(H helper, T item);
    protected abstract H getAdapterHelper(int position, View convertView, ViewGroup parent);

    public boolean getShowLoadMore() {
        return showLoadMore;
    }

    boolean showLoadMore = false;

    public void setShowLoadMore(boolean showLoadMore) {
        this.showLoadMore = showLoadMore;
        notifyDataSetChanged();
    }

    public boolean isLoadMoreCell(int position) {
        return getShowLoadMore() && position == getCount() - 1;
    }

}
