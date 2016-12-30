package com.qunyu.taoduoduo.api;

import com.andbase.library.http.model.AbRequestParams;
import com.qunyu.taoduoduo.base.BaseApi;
import com.qunyu.taoduoduo.global.Constant;

/**
 * 拼团-开团页面
 * Created by Administrator on 2016/9/27.
 */

public class OpenGroupActivityApi implements BaseApi {
    private String activityId;//拼团活动id/必填，pid/activity二选一作为参数
    private String pid;//商品ID
    private String userId;//用户id

    @Override
    public AbRequestParams getParamMap() {
        // TODO Auto-generated method stub
        AbRequestParams params = new AbRequestParams();
        params.put("activityId", activityId);
        params.put("pid", pid);
        params.put("userId", userId);
        return params;
    }

    public String getActivityId() {
        return activityId;
    }

    public void setActivityId(String activityId) {
        this.activityId = activityId;
    }

    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    @Override
    public String getUrl() {
        return Constant.openGroupActivityApi;
    }
}
