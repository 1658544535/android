package com.qunyu.taoduoduo.api;

import com.andbase.library.http.model.AbRequestParams;
import com.qunyu.taoduoduo.base.BaseApi;
import com.qunyu.taoduoduo.global.Constant;

/**
 * Created by Administrator on 2016/10/11.
 * 猜你喜欢
 */

public class GuessYourLikeApi implements BaseApi {
    private String activityId;//	活动id
    private String userId;//	用户id
    @Override
    public AbRequestParams getParamMap() {
        AbRequestParams params=new AbRequestParams();
        params.put("activityId",activityId);
        params.put("userId",userId);
        return params;
    }

    @Override
    public String getUrl() {
        return Constant.GUESSYOURLIKEAPI_URL;
    }

    public String getActivityId() {
        return activityId;
    }

    public void setActivityId(String activityId) {
        this.activityId = activityId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
