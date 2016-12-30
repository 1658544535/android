package com.qunyu.taoduoduo.mvpview;

import com.qunyu.taoduoduo.bean.MyCouponsListBean;

/**
 * Created by Administrator on 2016/10/6.
 */

public interface ConfirmOrderView {

    void showLoading(String msg);

    void hideLoading();

    void toastMsg(String msg);

    void setCouponNo(MyCouponsListBean.CouponList couponbean);//设置优惠券


}

