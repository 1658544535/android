package com.qunyu.taoduoduo.mvpview;


/**
 * 红包弹窗
 * Created by Administrator on 2016/10/6.
 */

public interface EventActivityView {
    void initView();

    void showProgressDialog(String msg);

    void removeDialog();


    void toastMessage(String msg);

    void loadSuccess();

    void onFinish();

}
