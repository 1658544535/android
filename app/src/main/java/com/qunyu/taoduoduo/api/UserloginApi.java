package com.qunyu.taoduoduo.api;

import com.andbase.library.http.model.AbRequestParams;
import com.qunyu.taoduoduo.base.BaseApi;
import com.qunyu.taoduoduo.global.Constant;
import com.qunyu.taoduoduo.utils.MD5;
import com.qunyu.taoduoduo.utils.StringUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * 登录
 * Created by Administrator on 2016/9/27.
 */

public class UserloginApi implements BaseApi {
    private String baidu_uid;//机器码
    private String captcha;//验证码
    private String openid;//微信ID
    private String phone;//帐号
    private String qq_openid;//QQID
    private String sinaid;//	新浪ID

    @Override
    public AbRequestParams getParamMap() {
        // TODO Auto-generated method stub
        AbRequestParams params = new AbRequestParams();
        params.put("baidu_uid", baidu_uid);
        params.put("captcha", captcha);
        params.put("openid", openid);
        params.put("phone", phone);
        params.put("qq_openid", qq_openid);
        params.put("sinaid", sinaid);
        params.put("source",2);//必填；1-ios,2-android,3-weixin
        Map<String, String> map = new HashMap<String, String>();
        map.put("baidu_uid", baidu_uid);
        map.put("captcha", captcha);
        map.put("phone", phone);
        map.put("source","2");//必填；
        if(StringUtils.isNotBlank(qq_openid)){
            map.put("qq_openid", qq_openid);
        }
        if(StringUtils.isNotBlank(sinaid)){
            map.put("sinaid", sinaid);
        }
        if(StringUtils.isNotBlank(openid)){
            map.put("openid", openid);
        }
        params.put("sign", MD5.createLinkString(map));
        return params;
    }


    public String getBaidu_uid() {
        return baidu_uid;
    }

    public void setBaidu_uid(String baidu_uid) {
        this.baidu_uid = baidu_uid;
    }

    public String getCaptcha() {
        return captcha;
    }

    public void setCaptcha(String captcha) {
        this.captcha = captcha;
    }

    public String getOpenid() {
        return openid;
    }

    public void setOpenid(String openid) {
        this.openid = openid;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getQq_openid() {
        return qq_openid;
    }

    public void setQq_openid(String qq_openid) {
        this.qq_openid = qq_openid;
    }

    public String getSinaid() {
        return sinaid;
    }

    public void setSinaid(String sinaid) {
        this.sinaid = sinaid;
    }

    @Override
    public String getUrl() {
        return Constant.USERLOGIN_URL;
    }
}
