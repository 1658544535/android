package com.qunyu.taoduoduo.mvpview;

import com.qunyu.taoduoduo.bean.SpecialDetailBean;
import com.qunyu.taoduoduo.bean.SpecialImageBean;

import java.util.List;

/**
 * Created by Administrator on 2016/10/6.
 */

public interface SpecialDetailView {
    void initView();

    void setList(List<SpecialDetailBean> list);

    void onLoadMore();

    void setHead(SpecialImageBean head);

    void loadFinish();

    void toastMessage(String msg);
}
