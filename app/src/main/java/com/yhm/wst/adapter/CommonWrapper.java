package com.yhm.wst.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.yhm.wst.recycler.wrapper.EmptyWrapper;
import com.yhm.wst.recycler.wrapper.HeaderAndFooterWrapper;
import com.yhm.wst.recycler.wrapper.LoadMoreWrapper;


/**
 * Created by liang_xs on 2017/11/28.
 */
public abstract class CommonWrapper<VH extends RecyclerView.ViewHolder> extends RecyclerView.Adapter<VH>{

    private HeaderAndFooterWrapper headerAndFooterWrapper;
    private EmptyWrapper emptyWrapper;
    private LoadMoreWrapper loadMoreWrapper;

    public CommonWrapper(){
        emptyWrapper = new EmptyWrapper(this);
        headerAndFooterWrapper = new HeaderAndFooterWrapper(emptyWrapper);
        loadMoreWrapper = new LoadMoreWrapper(headerAndFooterWrapper);
    }

    public RecyclerView.Adapter getWrapperAdapter(){
        return loadMoreWrapper;
    }

    public void addHeaderView(View headerView){
        headerAndFooterWrapper.addHeaderView(headerView);
    }

    public void addFootView(View footerView){
        headerAndFooterWrapper.addFootView(footerView);
    }

    public void setLoadMoreView(int layoutId){
        loadMoreWrapper.setLoadMoreView(layoutId);
    }

    public void setLoadMoreView(View loadMoreView){
        loadMoreWrapper.setLoadMoreView(loadMoreView);
    }

    public void setOnLoadMoreListener(LoadMoreWrapper.OnLoadMoreListener onLoadMoreListener){
        loadMoreWrapper.setOnLoadMoreListener(onLoadMoreListener);
    }

    public void setEmptyView(View emptyView){
        emptyWrapper.setEmptyView(emptyView);
    }

    public void setEmptyView(int layoutId){
        emptyWrapper.setEmptyView(layoutId);
    }

    public void notifyWrapperDataSetChanged(){
        getWrapperAdapter().notifyDataSetChanged();
    }

    public void notifyWrapperItemChanged(int position){
        getWrapperAdapter().notifyItemChanged(position);
    }

    public int getHeadersCount(){
        return headerAndFooterWrapper.getHeadersCount();
    }

    public int getFootersCount(){
        return headerAndFooterWrapper.getFootersCount();
    }

    public void removeHeaders(){
        headerAndFooterWrapper.removerHeaders();
        notifyWrapperDataSetChanged();
    }

}
