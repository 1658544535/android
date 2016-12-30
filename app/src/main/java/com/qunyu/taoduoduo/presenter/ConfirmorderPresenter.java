package com.qunyu.taoduoduo.presenter;

import android.app.Activity;
import android.content.Intent;
import android.os.Message;
import android.view.View;

import com.alipay.sdk.app.PayTask;
import com.andbase.library.http.AbHttpUtil;
import com.andbase.library.http.listener.AbStringHttpResponseListener;
import com.andbase.library.http.model.AbResult;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.qunyu.taoduoduo.activity.OrdersActivity;
import com.qunyu.taoduoduo.activity.ValidUserCouponsActivity;
import com.qunyu.taoduoduo.adapter.CouponsAdapter;
import com.qunyu.taoduoduo.api.AddOrderByPurchaseApi;
import com.qunyu.taoduoduo.api.GetValidUserCouponApi;
import com.qunyu.taoduoduo.base.BaseModel;
import com.qunyu.taoduoduo.bean.AddOrderByPurchaseBean;
import com.qunyu.taoduoduo.bean.MyCouponsListBean;
import com.qunyu.taoduoduo.mvpview.ConfirmOrderView;
import com.qunyu.taoduoduo.utils.LogUtil;
import com.qunyu.taoduoduo.utils.StringUtils;
import com.qunyu.taoduoduo.utils.ToastUtils;
import com.qunyu.taoduoduo.utils.UserInfoUtils;
import com.qunyu.taoduoduo.wxapi.WXPayEntryActivity;
import com.tencent.mm.sdk.modelpay.PayReq;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.WXAPIFactory;

import java.lang.reflect.Type;
import java.util.ArrayList;

/**
 * 订单提交
 * Created by Administrator on 2016/10/8.
 */

public class ConfirmorderPresenter {
    ConfirmOrderView view;
    Activity context;

    //优惠券
    GetValidUserCouponApi getValidUserCouponApi;

    public ConfirmorderPresenter(ConfirmOrderView view, Activity context) {
        this.view = view;
        this.context = context;
        getValidUserCouponApi = new GetValidUserCouponApi();

    }

    public void getCouponMsg(String pid, String price) {
        getValidUserCouponApi.setPid(pid);
        getValidUserCouponApi.setPrice(price);
        getValidUserCouponApi.setUid(UserInfoUtils.GetUid());
        LogUtil.Log(getValidUserCouponApi.getUrl() + "?" + getValidUserCouponApi.getParamMap().getParamString());
        AbHttpUtil.getInstance(context).get(getValidUserCouponApi.getUrl(), getValidUserCouponApi.getParamMap(), new AbStringHttpResponseListener() {
            @Override
            public void onSuccess(int statusCode, String content) {
                LogUtil.Log(content);
                AbResult result = new AbResult(content);
                if (result.getResultCode() == AbResult.RESULT_OK) {
                    Gson gson = new Gson();
                    Type type = new TypeToken<BaseModel<ArrayList<MyCouponsListBean.CouponList>>>() {
                    }.getType();
                    BaseModel<ArrayList<MyCouponsListBean.CouponList>> base = gson.fromJson(content, type);
                    if (base.success && base.result != null && !base.result.isEmpty() && base.result.size() > 0) {
                        view.setCouponNo(base.result.get(0));

                    } else {
                    }
                }

            }

            @Override
            public void onStart() {

            }

            @Override
            public void onFinish() {

            }

            @Override
            public void onFailure(int i, String s, Throwable error) {

            }
        });

    }
}
