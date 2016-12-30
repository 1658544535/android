package com.qunyu.taoduoduo.presenter;

import android.content.Context;

import com.andbase.library.http.AbHttpUtil;
import com.andbase.library.http.listener.AbStringHttpResponseListener;
import com.andbase.library.http.model.AbResult;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.qunyu.taoduoduo.api.GetProductSkusApi;
import com.qunyu.taoduoduo.api.GroupDetailApi;
import com.qunyu.taoduoduo.base.BaseModel;
import com.qunyu.taoduoduo.bean.GroupDetailBean;
import com.qunyu.taoduoduo.bean.ProductSkuBean;
import com.qunyu.taoduoduo.mvpview.KaiTuanTipsView;
import com.qunyu.taoduoduo.utils.LogUtil;
import com.qunyu.taoduoduo.utils.UserInfoUtils;

import java.lang.reflect.Type;
import java.util.List;

/**
 * 拼团提示页，团详情页
 * Created by Administrator on 2016/10/8.
 */

public class PinTuanTipsPresenter {
    KaiTuanTipsView view;
    GroupDetailApi groupDetailApi;
    Context context;
    public GroupDetailBean groupDetailBean;

    GetProductSkusApi productSkusApi;
    ProductSkuBean productSkuBean;
    public List<ProductSkuBean.SkuValue> skuColorValue;
    public List<ProductSkuBean.SkuValue> skuFormatValue;
    public List<ProductSkuBean.ValidSKu> validSKu;
    public List<ProductSkuBean.SkuList> skuList;
    public int skucolorMAxsize = 0;
    public int skuformatMAxsize = 0;

    public PinTuanTipsPresenter(KaiTuanTipsView view, Context context) {
        this.view = view;
        this.context = context;
        groupDetailApi = new GroupDetailApi();
        productSkusApi = new GetProductSkusApi();

    }

    public void loadData(String recordId) {
        groupDetailApi.setRecordId(recordId);
        if (UserInfoUtils.isLogin()) {
            groupDetailApi.setUserId(UserInfoUtils.GetUid());
        }
        LogUtil.Log(groupDetailApi.getUrl() + "?" + groupDetailApi.getParamMap().getParamString());
        AbHttpUtil.getInstance(context).post(groupDetailApi.getUrl(), groupDetailApi.getParamMap(), new AbStringHttpResponseListener() {
            @Override
            public void onSuccess(int i, String s) {
                {
                    LogUtil.Log(s);
                    AbResult result = new AbResult(s);
                    if (result.getResultCode() == AbResult.RESULT_OK) {
                        Gson gson = new Gson();
                        Type type = new TypeToken<BaseModel<GroupDetailBean>>() {
                        }.getType();
                        BaseModel<GroupDetailBean> base = gson.fromJson(s, type);
                        if (base.success && base.result != null) {
                            groupDetailBean = base.result;
                            view.setDetail(groupDetailBean);
                            if (groupDetailBean.groupUserList != null && !groupDetailBean.groupUserList.isEmpty()) {
                                view.setGroupUserList(groupDetailBean.groupUserList);
                            }
                        } else {
                            view.toastMessage(base.error_msg);
                        }


                    } else {
                        view.toastMessage("网络异常，数据加载失败");
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
            public void onFailure(int i, String s, Throwable throwable) {
                view.toastMessage(throwable.getMessage());
            }
        });
    }

    public void loadSku() {
        productSkusApi.setPid(groupDetailBean.productId);
        LogUtil.Log(productSkusApi.getUrl() + "?" + productSkusApi.getParamMap().getParamString());
        AbHttpUtil.getInstance(context).get(productSkusApi.getUrl(), productSkusApi.getParamMap(), new AbStringHttpResponseListener() {
            @Override
            public void onSuccess(int i, String s) {

                LogUtil.Log(s);
                AbResult result = new AbResult(s);
                if (result.getResultCode() == AbResult.RESULT_OK) {
                    Gson gson = new Gson();
                    Type type = new TypeToken<BaseModel<ProductSkuBean>>() {
                    }.getType();
                    BaseModel<ProductSkuBean> base = gson.fromJson(s, type);
                    if (base.success && base.result != null) {
                        productSkuBean = base.result;
                        validSKu = productSkuBean.validSKu;
                        skuList =  productSkuBean.skuList;
                        skuColorValue = skuList.get(0).skuValue;
                        skuFormatValue = skuList.get(1).skuValue;
                        for (ProductSkuBean.SkuValue sKu:
                                skuColorValue) {
                            if(skucolorMAxsize<sKu.optionValue.length()){
                                skucolorMAxsize = sKu.optionValue.length();
                            }

                        }
                        for (ProductSkuBean.SkuValue sKu:
                                skuFormatValue) {
                            if(skuformatMAxsize<sKu.optionValue.length()){
                                skuformatMAxsize = sKu.optionValue.length();
                            }
                            if(skuformatMAxsize<sKu.optionValue.length()){
                                skuformatMAxsize = sKu.optionValue.length();
                            }

                        }
                        view.showSkuPopuWindow();
                    } else {
                        view.toastMessage(base.error_msg);
                    }


                } else {
                    view.toastMessage("网络异常，数据加载失败");
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
            public void onFailure(int i, String s, Throwable throwable) {
                view.toastMessage(throwable.getMessage());
            }
        });
    }

}
