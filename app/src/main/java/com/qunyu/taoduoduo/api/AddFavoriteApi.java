package com.qunyu.taoduoduo.api;

import com.andbase.library.http.model.AbRequestParams;
import com.qunyu.taoduoduo.base.BaseApi;
import com.qunyu.taoduoduo.global.Constant;

/**
 * 添加收藏收藏
 */

public class AddFavoriteApi implements BaseApi {
    private String activityId;//	活动id
    //private String favType;//收藏的类型：5-拼团
    private String uid;//	会员id
    private String favSenId;//	产品id
    @Override
    public AbRequestParams getParamMap() {
        AbRequestParams params=new AbRequestParams();
        params.put("activityId",activityId);
        params.put("favType","5");
        params.put("uid",uid);
        params.put("favSenId",favSenId);
        return params;
    }

    public String getActivityId() {
        return activityId;
    }

    public void setActivityId(String activityId) {
        this.activityId = activityId;
    }

   // public String getFavType() {
   //     return favType;
   // }

    //public void setFavType(String favType) {
    //    this.favType = favType;
    //}

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    @Override
    public String getUrl() {
        return Constant.ADDFAVORITE_URL;
    }

    public String getFavSenId() {
        return favSenId;
    }

    public void setFavSenId(String favSenId) {
        this.favSenId = favSenId;
    }
}
