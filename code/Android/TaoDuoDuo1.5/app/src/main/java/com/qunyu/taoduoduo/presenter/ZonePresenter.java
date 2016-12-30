package com.qunyu.taoduoduo.presenter;

import android.content.Context;

import com.andbase.library.http.AbHttpUtil;
import com.andbase.library.http.listener.AbStringHttpResponseListener;
import com.andbase.library.http.model.AbResult;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.qunyu.taoduoduo.R;
import com.qunyu.taoduoduo.api.NewSpecialApi;
import com.qunyu.taoduoduo.api.NewSpecialImageApi;
import com.qunyu.taoduoduo.api.SpecialDetailApi;
import com.qunyu.taoduoduo.api.SpecialImageApi;
import com.qunyu.taoduoduo.api.ZoneApi;
import com.qunyu.taoduoduo.api.ZoneProductsApi;
import com.qunyu.taoduoduo.base.BaseModel;
import com.qunyu.taoduoduo.bean.SpecialDetailBean;
import com.qunyu.taoduoduo.bean.SpecialImageBean;
import com.qunyu.taoduoduo.bean.ZoneBean;
import com.qunyu.taoduoduo.mvpview.ZoneView;
import com.qunyu.taoduoduo.utils.LogUtil;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * 专区
 * Created by Administrator on 2016/10/8.
 */

public class ZonePresenter {
    ZoneView view;
    ZoneApi zoneApi;
    Context context;
    List<SpecialDetailBean> list;
    int pageNo = 1;
    ZoneProductsApi zoneProductsApi;
    ZoneBean zoneBean;
    //专题详情
    SpecialDetailApi specialDetailApi;
    SpecialImageApi specialImageApi;
    SpecialImageBean specialImageBean;
    NewSpecialImageApi newSpecialImageApi;
    NewSpecialApi newSpecialApi;

    public ZonePresenter(ZoneView view, Context context) {
        this.view = view;
        this.context = context;
        zoneApi = new ZoneApi();
        list = new ArrayList<>();
        zoneProductsApi = new ZoneProductsApi();
        specialDetailApi = new SpecialDetailApi();
        specialImageApi = new SpecialImageApi();
        newSpecialImageApi = new NewSpecialImageApi();
        newSpecialApi = new NewSpecialApi();

    }

    public void loadData(String id, final boolean isLoadmore) {
        if (isLoadmore) {
            pageNo++;
        } else {
            pageNo = 1;
        }

        zoneProductsApi.setPageNo(pageNo);
        zoneProductsApi.setId(id);

        LogUtil.Log(zoneProductsApi.getUrl() + "?" + zoneProductsApi.getParamMap().getParamString());
        AbHttpUtil.getInstance(context).post(zoneProductsApi.getUrl(), zoneProductsApi.getParamMap(), new AbStringHttpResponseListener() {
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

    public void loadZoneApi() {
        LogUtil.Log(zoneApi.getUrl() + "?" + zoneApi.getParamMap().getParamString());
        AbHttpUtil.getInstance(context).post(zoneApi.getUrl(), zoneApi.getParamMap(), new AbStringHttpResponseListener() {
            @Override
            public void onSuccess(int i, String s) {
                {
                    LogUtil.Log(s);
                    AbResult result = new AbResult(s);
                    if (result.getResultCode() == AbResult.RESULT_OK) {
                        Gson gson = new Gson();
                        Type type = new TypeToken<BaseModel<ZoneBean>>() {
                        }.getType();
                        BaseModel<ZoneBean> base = gson.fromJson(s, type);
                        if (base.success && base.result != null) {
                            zoneBean = base.result;
                            view.setHead(zoneBean);

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

    public void loadSpecialData(String specialId, final boolean isLoadmore) {
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
                            view.setSpecialHead(specialImageBean);

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

    public void newSpecialImage() {
        LogUtil.Log(newSpecialImageApi.getUrl() + "?" + newSpecialImageApi.getParamMap().getParamString());
        AbHttpUtil.getInstance(context).post(newSpecialImageApi.getUrl(), newSpecialImageApi.getParamMap(), new AbStringHttpResponseListener() {
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
                            view.setSpecialHead(specialImageBean);

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


    public void loadnewSpecial(final boolean isLoadmore) {
        if (isLoadmore) {
            pageNo++;
        } else {
            pageNo = 1;
        }

        newSpecialApi.setPageNo(pageNo);

        LogUtil.Log(newSpecialApi.getUrl() + "?" + newSpecialApi.getParamMap().getParamString());
        AbHttpUtil.getInstance(context).post(newSpecialApi.getUrl(), newSpecialApi.getParamMap(), new AbStringHttpResponseListener() {
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

}
