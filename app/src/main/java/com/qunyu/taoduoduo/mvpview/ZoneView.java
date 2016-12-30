package com.qunyu.taoduoduo.mvpview;

import com.qunyu.taoduoduo.bean.SpecialDetailBean;
import com.qunyu.taoduoduo.bean.SpecialImageBean;
import com.qunyu.taoduoduo.bean.ZoneBean;

import java.util.List;


/**
 * 专区
 * Created by Administrator on 2016/10/6.
 */

public interface ZoneView {
    void initView();

    void initView(android.view.View view);

    void setList(List<SpecialDetailBean> list);

    void onLoadMore();

    void setHead(ZoneBean zoneBean);

    void loadFinish();

    void toastMessage(String msg);

    void setSpecialHead(SpecialImageBean specialImageBean);
}
