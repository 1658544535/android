package com.qunyu.taoduoduo.presenter;

import android.content.Context;

import com.andbase.library.http.AbHttpUtil;
import com.andbase.library.http.listener.AbStringHttpResponseListener;
import com.andbase.library.http.model.AbResult;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.qunyu.taoduoduo.api.AddFavoriteApi;
import com.qunyu.taoduoduo.api.AddPurchaseApi;
import com.qunyu.taoduoduo.api.DelFavoriteApi;
import com.qunyu.taoduoduo.api.GetProductSkusApi;
import com.qunyu.taoduoduo.api.GuessYourLikeApi;
import com.qunyu.taoduoduo.api.OpenGroupActivityApi;
import com.qunyu.taoduoduo.base.BaseModel;
import com.qunyu.taoduoduo.bean.AddPurchaseBean;
import com.qunyu.taoduoduo.bean.GuessYourLikeBean;
import com.qunyu.taoduoduo.bean.OpenGroupBean;
import com.qunyu.taoduoduo.bean.ProductSkuBean;
import com.qunyu.taoduoduo.mvpview.CaiGoodsDetialView;
import com.qunyu.taoduoduo.mvpview.GoodsDetailView;
import com.qunyu.taoduoduo.utils.LogUtil;
import com.qunyu.taoduoduo.utils.StringUtils;
import com.qunyu.taoduoduo.utils.UserInfoUtils;

import java.lang.reflect.Type;
import java.util.List;

/**
 * 猜价
 * Created by Administrator on 2016/10/8.
 */

public class CaiGoodsPresenter {
    CaiGoodsDetialView view;

    Context context;


    AddPurchaseApi addPurchaseApi;

    public CaiGoodsPresenter(CaiGoodsDetialView goodsDetailView, Context context) {
        view = goodsDetailView;
        this.context = context;

        addPurchaseApi = new AddPurchaseApi();
    }


    public void confirmOrder(String source, String activityid, String attendId, int num, String pid, String skuLinkId) {

        if (StringUtils.isNotBlank(source) && source.equals("4")) {
            addPurchaseApi.setActivityId("0");
        } else {
            addPurchaseApi.setActivityId(activityid);
        }

        addPurchaseApi.setAttendId(attendId);
        addPurchaseApi.setNum(num + "");
        addPurchaseApi.setPid(pid);
        addPurchaseApi.setSkuLinkId(skuLinkId);//商品skuid,没有时传空
        addPurchaseApi.setSource(source);//来源，1-普通拼团2-团免3-猜价4单独购
        addPurchaseApi.setUid(UserInfoUtils.GetUid());
        LogUtil.Log(addPurchaseApi.getUrl() + "?" + addPurchaseApi.getParamMap().getParamString());
        AbHttpUtil.getInstance(context).get(addPurchaseApi.getUrl(), addPurchaseApi.getParamMap(), new AbStringHttpResponseListener() {
            @Override
            public void onSuccess(int i, String content) {
                LogUtil.Log(content);
                AbResult result = new AbResult(content);
                if (result.getResultCode() == AbResult.RESULT_OK) {
                    Gson gson = new Gson();
                    Type type = new TypeToken<BaseModel<AddPurchaseBean>>() {
                    }.getType();
                    BaseModel<AddPurchaseBean> base = gson.fromJson(content, type);
                    LogUtil.Log(base.success + ":" + base.error_msg + ":" + base.result);
                    if (base.success && base.result != null) {
                        view.orderConfirm();
                    } else if (!base.success) {
                        view.toastMessage(base.error_msg);
                    }
                } else {
                    view.toastMessage("网络异常，数据加载失败");
                    LogUtil.Log(result.getResultMessage());
                }
            }

            @Override
            public void onStart() {
                view.showProgressDialog("加载中...");
            }

            @Override
            public void onFinish() {
                view.removeDialog();
            }

            @Override
            public void onFailure(int i, String s, Throwable error) {
                view.toastMessage("网络异常，数据加载失败");
                LogUtil.Log(error.getMessage());
            }
        });
    }
}
