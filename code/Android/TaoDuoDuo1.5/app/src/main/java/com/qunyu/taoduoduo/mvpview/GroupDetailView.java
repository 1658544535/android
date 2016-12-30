package com.qunyu.taoduoduo.mvpview;

import com.qunyu.taoduoduo.bean.GroupDetailBean;
import com.qunyu.taoduoduo.bean.GuessYourLikeBean;

import java.util.List;

/**
 * 1.2新团详情
 */

public interface GroupDetailView extends BaseView{

  void setDetail(GroupDetailBean groupDetailBean);

  void setGroupUserList(List<GroupDetailBean.GroupUser> groupUserList);

  void setGroupUserGridList(List<GroupDetailBean.GroupUser> groupUserList);

  void setguessYourLike(List<GuessYourLikeBean> list);//猜你喜欢

  void setTime(GroupDetailBean groupDetailBean);

  void orderConfirm();
}
