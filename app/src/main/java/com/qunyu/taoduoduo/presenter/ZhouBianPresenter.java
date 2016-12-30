package com.qunyu.taoduoduo.presenter;

import android.content.Context;
import android.content.Intent;

import com.andbase.library.http.AbHttpUtil;
import com.andbase.library.http.listener.AbStringHttpResponseListener;
import com.andbase.library.http.model.AbResult;
import com.andbase.library.util.AbDialogUtil;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.qunyu.taoduoduo.R;
import com.qunyu.taoduoduo.activity.PhoneLoginActivity;
import com.qunyu.taoduoduo.activity.TabActivity;
import com.qunyu.taoduoduo.api.AddUserCouponApi;
import com.qunyu.taoduoduo.api.CaptchaApi;
import com.qunyu.taoduoduo.api.GainCouponApi;
import com.qunyu.taoduoduo.api.UserloginApi;
import com.qunyu.taoduoduo.base.BaseModel;
import com.qunyu.taoduoduo.bean.UserInfoBean;
import com.qunyu.taoduoduo.mvpview.EventActivityView;
import com.qunyu.taoduoduo.mvpview.ZhouBianView;
import com.qunyu.taoduoduo.utils.LogUtil;
import com.qunyu.taoduoduo.utils.ToastUtils;
import com.qunyu.taoduoduo.utils.UserInfoUtils;

import java.lang.reflect.Type;

/**
 * 周边
 */
public class ZhouBianPresenter {
    ZhouBianView view;
    Context context;


    public ZhouBianPresenter(ZhouBianView view, Context context) {
        this.view = view;
        this.context = context;
    }

    public void sendcaptcha(String phone) {
        CaptchaApi captchaApi = new CaptchaApi();
        captchaApi.setPhone(phone);
        LogUtil.Log(captchaApi.getUrl() + "?" + captchaApi.getParamMap().getParamString());
        AbHttpUtil.getInstance(context).post(captchaApi.getUrl(), captchaApi.getParamMap()
                , new AbStringHttpResponseListener() {

                    @Override
                    public void onStart() {

                    }

                    @Override
                    public void onFinish() {


                    }

                    @Override
                    public void onFailure(int i, String s, Throwable error) {
                        view.toastMessage("网络异常，加载失败！");
                    }

                    @Override
                    public void onSuccess(int statusCode, String content) {
                        LogUtil.Log(content);
                        try {
                            AbResult result = new AbResult(content);
                            if (result.getResultCode() == AbResult.RESULT_OK) {
                                Gson gson = new Gson();
                                Type type = new TypeToken<BaseModel<Object>>() {
                                }.getType();
                                BaseModel<Object> base = gson.fromJson(content, type);
                                if (base != null && base.success) {
                                    view.toastMessage(context.getString(R.string.send_success));

                                }

                            } else {
                                view.toastMessage("网络异常，加载失败！");
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                    }
                });

    }

    public void login(String phone, String deviceToken, String captcha, final String couponNo) {
        LogUtil.Log("deviceToken:" + deviceToken);
        UserloginApi userloginApi = new UserloginApi();
        userloginApi.setPhone(phone);
        userloginApi.setBaidu_uid(deviceToken);
        userloginApi.setCaptcha(captcha);
        LogUtil.Log(userloginApi.getUrl() + "?" + userloginApi.getParamMap().getParamString());
        AbHttpUtil.getInstance(context).post(userloginApi.getUrl(), userloginApi.getParamMap(), new AbStringHttpResponseListener() {
            @Override
            public void onSuccess(int i, String s) {
                LogUtil.Log(s);
                try {
                    AbResult result = new AbResult(s);
                    LogUtil.Log(result.toString());
                    if (result.getResultCode() == AbResult.RESULT_OK) {
                        Gson gson = new Gson();
                        Type type = new TypeToken<BaseModel<UserInfoBean>>() {
                        }.getType();
                        BaseModel<UserInfoBean> base = gson.fromJson(s, type);
                        if (base != null && base.success) {
                            UserInfoUtils.setUserInfo(base.result);
                            addUserCoupon(couponNo);
                            view.setLoginView();
                        } else {
                            view.toastMessage(base.error_msg);
                        }


                    } else {
                        view.toastMessage("网络异常，加载失败！");
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void onStart() {
                view.showProgressDialog("登录中...");

            }

            @Override
            public void onFinish() {
                view.removeDialog();
            }

            @Override
            public void onFailure(int i, String s, Throwable throwable) {
                LogUtil.Log(s);
                view.toastMessage("网络异常，加载失败！");
            }
        });
    }

    public void addUserCoupon(String couponNo) {
        AddUserCouponApi addUserCouponApi = new AddUserCouponApi();
        addUserCouponApi.setCouponNo(couponNo);
        addUserCouponApi.setUid(UserInfoUtils.GetUid());
        LogUtil.Log(addUserCouponApi.getUrl() + "?" + addUserCouponApi.getParamMap().getParamString());
        AbHttpUtil.getInstance(context).post(addUserCouponApi.getUrl(), addUserCouponApi.getParamMap(), new AbStringHttpResponseListener() {
            @Override
            public void onSuccess(int i, String s) {
                LogUtil.Log(s);
                AbResult result = new AbResult(s);
                LogUtil.Log(result.toString());
                try {
                    if (result.getResultCode() == AbResult.RESULT_OK) {
                        Gson gson = new Gson();
                        Type type = new TypeToken<BaseModel<UserInfoBean>>() {
                        }.getType();
                        BaseModel<UserInfoBean> base = gson.fromJson(s, type);
                        if (base != null && base.success) {
                            view.addUserCouponSucess();
                        }
                        view.toastMessage(base.error_msg);

                    } else {
                        view.toastMessage("网络异常，加载失败！");
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onStart() {
                view.showProgressDialog("领取中...");

            }

            @Override
            public void onFinish() {
                view.removeDialog();
            }

            @Override
            public void onFailure(int i, String s, Throwable throwable) {
                LogUtil.Log(s);
                view.toastMessage("网络异常，加载失败！");
            }
        });
    }


}
