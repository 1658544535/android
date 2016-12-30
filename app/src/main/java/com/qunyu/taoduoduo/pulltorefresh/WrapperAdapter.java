package com.qunyu.taoduoduo.pulltorefresh;

import android.support.v7.widget.RecyclerView;

public interface WrapperAdapter
{

    @SuppressWarnings("rawtypes")
	public RecyclerView.Adapter getWrappedAdapter();
}
