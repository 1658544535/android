package com.qunyu.taoduoduo.presenter;

import android.content.Context;

import com.andbase.library.http.AbHttpUtil;
import com.andbase.library.http.listener.AbStringHttpResponseListener;
import com.andbase.library.http.model.AbResult;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.qunyu.taoduoduo.R;
import com.qunyu.taoduoduo.api.GainCouponApi;
import com.qunyu.taoduoduo.api.SpecialDetailApi;
import com.qunyu.taoduoduo.api.SpecialImageApi;
import com.qunyu.taoduoduo.base.BaseModel;
import com.qunyu.taoduoduo.bean.SpecialDetailBean;
import com.qunyu.taoduoduo.bean.SpecialImageBean;
import com.qunyu.taoduoduo.mvpview.EventActivityView;
import com.qunyu.taoduoduo.mvpview.SpecialDetailView;
import com.qunyu.taoduoduo.utils.LogUtil;
import com.qunyu.taoduoduo.utils.UserInfoUtils;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * 红包测试
 * Created by Administrator on 2016/10/8.
 */

public class EventPresenter {
    EventActivityView view;
    Context context;
    GainCouponApi gainCouponApi;


    public EventPresenter(EventActivityView view, Context context) {
        this.view = view;
        this.context = context;
        gainCouponApi = new GainCouponApi();


    }

    public void loadData() {
        gainCouponApi.setUserId(UserInfoUtils.GetUid());

        LogUtil.Log(gainCouponApi.getUrl() + "?" + gainCouponApi.getParamMap().getParamString());
        AbHttpUtil.getInstance(context).post(gainCouponApi.getUrl(), gainCouponApi.getParamMap(), new AbStringHttpResponseListener() {
            @Override
            public void onSuccess(int i, String s) {
                {
                    LogUtil.Log(s);
                    AbResult result = new AbResult(s);
                    if (result.getResultCode() == AbResult.RESULT_OK) {
                        Gson gson = new Gson();
                        Type type = new TypeToken<BaseModel<Integer>>() {
                        }.getType();
                        BaseModel<Integer> base = gson.fromJson(s, type);
                        if (base.success && base.result ==1) {
                            view.loadSuccess();
                        }else {
                            view.onFinish();
                        }
                        view.toastMessage(base.error_msg);
                    } else {
                        view.toastMessage("网络异常，数据加载失败");
                    }
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
                LogUtil.Log(throwable.getMessage());
                view.toastMessage("网络异常，加载失败");
            }
        });
    }




}
