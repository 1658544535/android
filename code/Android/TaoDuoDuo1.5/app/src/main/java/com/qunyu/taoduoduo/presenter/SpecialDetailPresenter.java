package com.qunyu.taoduoduo.presenter;

import android.content.Context;

import com.andbase.library.http.AbHttpUtil;
import com.andbase.library.http.listener.AbStringHttpResponseListener;
import com.andbase.library.http.model.AbResult;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.qunyu.taoduoduo.R;
import com.qunyu.taoduoduo.api.SpecialDetailApi;
import com.qunyu.taoduoduo.api.SpecialImageApi;
import com.qunyu.taoduoduo.base.BaseModel;
import com.qunyu.taoduoduo.bean.SpecialDetailBean;
import com.qunyu.taoduoduo.bean.SpecialImageBean;
import com.qunyu.taoduoduo.mvpview.SpecialDetailView;
import com.qunyu.taoduoduo.utils.LogUtil;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * 专题详情
 * Created by Administrator on 2016/10/8.
 */

public class SpecialDetailPresenter {
    SpecialDetailView view;
    SpecialDetailApi specialDetailApi;
    Context context;
    List<SpecialDetailBean> list;
    int pageNo = 1;
    SpecialImageApi specialImageApi;
    SpecialImageBean specialImageBean;

    public SpecialDetailPresenter(SpecialDetailView view, Context context) {
        this.view = view;
        this.context = context;
        specialDetailApi = new SpecialDetailApi();
        list = new ArrayList<>();
        specialImageApi = new SpecialImageApi();

    }

    public void loadData(String specialId, final boolean isLoadmore) {
        if (isLoadmore) {
            pageNo++;
        } else {
            pageNo = 1;
        }

        specialDetailApi.setPageNo(pageNo);
        specialDetailApi.setSpecialId(specialId);

        LogUtil.Log(specialDetailApi.getUrl() + "?" + specialDetailApi.getParamMap().getParamString());
        AbHttpUtil.getInstance(context).post(specialDetailApi.getUrl(), specialDetailApi.getParamMap(), new AbStringHttpResponseListener() {
            @Override
            public void onSuccess(int i, String s) {
                {
                    LogUtil.Log(s);
                    AbResult result = new AbResult(s);
                    if (result.getResultCode() == AbResult.RESULT_OK) {
                        Gson gson = new Gson();
                        Type type = new TypeToken<BaseModel<List<SpecialDetailBean>>>() {
                        }.getType();
                        BaseModel<List<SpecialDetailBean>> base = gson.fromJson(s, type);
                        if (base.success && base.result != null && !base.result.isEmpty()) {
                            if (isLoadmore) {
                                list.addAll(base.result);
                                view.onLoadMore();
                            } else {
                                list = base.result;
                                view.setList(list);
                            }


                        } else {
                            if (isLoadmore) {
                                view.toastMessage(context.getString(R.string.list_empty));
                            } else {
                                view.toastMessage(base.error_msg);
                            }

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
                view.loadFinish();
            }

            @Override
            public void onFailure(int i, String s, Throwable throwable) {
                view.toastMessage(throwable.getMessage());
            }
        });
    }

    public void loadSpecialImage(String specialId) {


        specialImageApi.setSpecialId(specialId);

        LogUtil.Log(specialImageApi.getUrl() + "?" + specialImageApi.getParamMap().getParamString());
        AbHttpUtil.getInstance(context).post(specialImageApi.getUrl(), specialImageApi.getParamMap(), new AbStringHttpResponseListener() {
            @Override
            public void onSuccess(int i, String s) {
                {
                    LogUtil.Log(s);
                    AbResult result = new AbResult(s);
                    if (result.getResultCode() == AbResult.RESULT_OK) {
                        Gson gson = new Gson();
                        Type type = new TypeToken<BaseModel<SpecialImageBean>>() {
                        }.getType();
                        BaseModel<SpecialImageBean> base = gson.fromJson(s, type);
                        if (base.success && base.result != null) {
                            specialImageBean = base.result;
                            view.setHead(specialImageBean);

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


}
