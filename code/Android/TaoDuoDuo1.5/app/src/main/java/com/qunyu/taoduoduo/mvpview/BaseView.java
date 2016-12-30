package com.qunyu.taoduoduo.mvpview;

/**
 * Created by Administrator on 2016/10/6.
 */

public interface BaseView {

  void initView();

  void showProgressDialog(String msg);

  void removeDialog();

  void toastMessage(String msg);
}
