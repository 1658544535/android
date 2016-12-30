package com.qunyu.taoduoduo.api;

import com.andbase.library.http.model.AbRequestParams;
import com.qunyu.taoduoduo.base.BaseApi;
import com.qunyu.taoduoduo.global.Constant;

/**
 * Created by Administrator on 2016/10/28.
 * 中奖信息详情
 */

public class PrizeDetailApi implements BaseApi {
    private String activityId;
    private String attendId;
    private String pageNo;
    private String activityType;//pdh1.3:  活动类型(5-0.1抽奖7-免费抽奖)

    @Override
    public AbRequestParams getParamMap() {
        AbRequestParams params = new AbRequestParams();
        params.put("activityId", activityId);
        params.put("attendId", attendId);
        params.put("pageNo", pageNo);
        params.put("activityType", activityType);
        return params;
    }

    @Override
    public String getUrl() {
        return Constant.prizeDetailApi;
    }

    public String getActivityId() {
        return activityId;
    }

    public void setActivityId(String activityId) {
        this.activityId = activityId;
    }

    public String getAttendId() {
        return attendId;
    }

    public void setAttendId(String attendId) {
        this.attendId = attendId;
    }

    public String getPageNo() {
        return pageNo;
    }

    public void setPageNo(String pageNo) {
        this.pageNo = pageNo;
    }

    public String getActivityType() {
        return activityType;
    }

    public void setActivityType(String activityType) {
        this.activityType = activityType;
    }
}
