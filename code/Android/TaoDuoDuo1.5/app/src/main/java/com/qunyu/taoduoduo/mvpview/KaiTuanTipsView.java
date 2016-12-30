package com.qunyu.taoduoduo.mvpview;

import com.qunyu.taoduoduo.bean.GroupDetailBean;

import java.util.List;

/**
 * Created by Administrator on 2016/10/6.
 */

public interface KaiTuanTipsView {
  void initView();

  void toastMessage(String msg);

  void showProgressDialog(String msg);

  void  removeDialog();

  void setDetail(GroupDetailBean groupDetailBean);

  void setGroupUserList(List<GroupDetailBean.GroupUser> groupUserList);

  void setEndTime(GroupDetailBean groupDetailBean);

  void showSkuPopuWindow();
}
