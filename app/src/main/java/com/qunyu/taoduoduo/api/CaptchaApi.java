package com.qunyu.taoduoduo.api;

import com.andbase.library.http.model.AbRequestParams;
import com.qunyu.taoduoduo.base.BaseApi;
import com.qunyu.taoduoduo.global.Constant;
import com.qunyu.taoduoduo.utils.MD5;

import java.util.HashMap;
import java.util.Map;

/**
 * 发送手机验证码
 * Created by Administrator on 2016/9/30.
 */

public class CaptchaApi implements BaseApi {
    private String phone;//验证手机号码
   // private String source;//1-注册 2-修改密码 3-绑定第三方 4-其他

    // private String sign;//加密验证
    @Override
    public AbRequestParams getParamMap() {
        AbRequestParams params=new AbRequestParams();
        params.put("phone",phone);
        params.put("source","4");
        Map<String, String> map = new HashMap<String, String>();
        map.put("phone", this.phone);
        map.put("source", "4");
        params.put("sign", MD5.createLinkString(map));
        return params;
    }


    @Override
    public String getUrl() {
        return Constant.CAPTCHA_URL;
    }

//    public String getSource() {
//        return source;
//    }
//
//    public void setSource(String source) {
//        this.source = source;
//    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }


}
