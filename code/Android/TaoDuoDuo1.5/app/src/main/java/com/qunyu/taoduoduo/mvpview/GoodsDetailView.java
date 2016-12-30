package com.qunyu.taoduoduo.mvpview;

import android.view.View;

import com.qunyu.taoduoduo.bean.GuessYourLikeBean;
import com.qunyu.taoduoduo.bean.OpenGroupBean;

import java.util.List;

/**
 * Created by Administrator on 2016/10/6.
 */

public interface GoodsDetailView extends BaseView{


  void setBannar(List<OpenGroupBean.Banner> banners);

  void setDetail(OpenGroupBean openGroupBean);

  void setWaitGroupList(List<OpenGroupBean.WaitGroup> waitGroupList);

  void onaddFavoriteSuccess();

  void ondelFavoriteSuccess();

  void setguessYourLike(List<GuessYourLikeBean> list);

  void setEmptyView();

  void loadguessYourLike();

  void setSellOut();//售罄

  void addguessFavorite(String activityId, String pid,int position);//猜你喜欢收藏

  void delguessFavorite(String activityId, String pid,int position);//猜你喜欢取消收藏

  void updateguessYourLike();

  void orderConfirm();


}
