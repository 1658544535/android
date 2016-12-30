package com.qunyu.taoduoduo.presenter;

import android.content.Context;

import com.andbase.library.http.AbHttpUtil;
import com.andbase.library.http.listener.AbStringHttpResponseListener;
import com.andbase.library.http.model.AbRequestParams;
import com.andbase.library.http.model.AbResult;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.qunyu.taoduoduo.api.AddPurchaseApi;
import com.qunyu.taoduoduo.api.GetProductSkusApi;
import com.qunyu.taoduoduo.api.GroupDetailApi;
import com.qunyu.taoduoduo.api.GuessYourLikeApi;
import com.qunyu.taoduoduo.base.BaseModel;
import com.qunyu.taoduoduo.bean.AddPurchaseBean;
import com.qunyu.taoduoduo.bean.GetShareContentBean;
import com.qunyu.taoduoduo.bean.GroupDetailBean;
import com.qunyu.taoduoduo.bean.GuessYourLikeBean;
import com.qunyu.taoduoduo.bean.ProductSkuBean;
import com.qunyu.taoduoduo.global.Constant;
import com.qunyu.taoduoduo.mvpview.GroupDetailView;
import com.qunyu.taoduoduo.utils.LogUtil;
import com.qunyu.taoduoduo.utils.StringUtils;
import com.qunyu.taoduoduo.utils.UserInfoUtils;

import java.lang.reflect.Type;
import java.util.List;

/**
 * 1.21团详情
 * Created by Administrator on 2016/10/8.
 */

public class GroupDetailPresenter {
    GroupDetailView view;
    GroupDetailApi groupDetailApi;
    Context context;
    public GroupDetailBean groupDetailBean;

    GuessYourLikeApi guessYourLikeApi;

    //share
    public GetShareContentBean share;

    AddPurchaseApi addPurchaseApi;

    public GroupDetailPresenter(GroupDetailView view, Context context) {
        this.view = view;
        this.context = context;
        groupDetailApi = new GroupDetailApi();
        guessYourLikeApi = new GuessYourLikeApi();
        addPurchaseApi = new AddPurchaseApi();

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
                                view.setGroupUserGridList(groupDetailBean.groupUserList);
                            }
                        } else {
                            view.toastMessage(base.error_msg);
                        }


                    } else {
                        view.toastMessage("网络异常，加载数据失败");
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


    /**
     * 猜你喜欢
     * @param activityId
     */
    public void guessYourLike(String activityId) {
        guessYourLikeApi.setActivityId(activityId);
        guessYourLikeApi.setUserId(UserInfoUtils.GetUid());
        LogUtil.Log(guessYourLikeApi.getUrl() + "?" + guessYourLikeApi.getParamMap().getParamString());
        AbHttpUtil.getInstance(context).get(guessYourLikeApi.getUrl(), guessYourLikeApi.getParamMap(), new AbStringHttpResponseListener() {
            @Override
            public void onSuccess(int i, String s) {
                {
                    LogUtil.Log(s);
                    AbResult result = new AbResult(s);
                    if (result.getResultCode() == AbResult.RESULT_OK) {
                        Gson gson = new Gson();
                        Type type = new TypeToken<BaseModel<List<GuessYourLikeBean>>>() {
                        }.getType();
                        BaseModel<List<GuessYourLikeBean>> base = gson.fromJson(s, type);
                        if (base.success && base.result != null) {
                            view.setguessYourLike(base.result);

                        }
//                        else {
//                            view.toastMessage(base.error_msg);
//                        }


                    } else {
                        // view.toastMessage(result.getResultMessage());
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
                // view.toastMessage(throwable.getMessage());
            }
        });
    }

    public void getShareContentApiGet(String recordId) {
        AbRequestParams params = new AbRequestParams();
        params.put("id", recordId);
        params.put("type", "9");
        LogUtil.Log(Constant.getShareContentApi + "?" + params.getParamString());
        AbHttpUtil.getInstance(context).get(Constant.getShareContentApi, params, new AbStringHttpResponseListener() {

            @Override
            public void onStart() {
//                AbDialogUtil.showProgressDialog(LoginActivity.this, 0, "正在登录...");
            }

            @Override
            public void onFinish() {
//                AbDialogUtil.removeDialog(LoginActivity.this);
            }

            @Override
            public void onFailure(int statusCode, String content, Throwable error) {
                view.toastMessage("网络异常，数据加载失败");
            }

            @Override
            public void onSuccess(int statusCode, String content) {
                AbResult result = new AbResult(content);
                LogUtil.Log(content);
                if (result.getResultCode() == AbResult.RESULT_OK) {
                    Gson gson = new Gson();
                    Type type = new TypeToken<BaseModel<GetShareContentBean>>() {
                    }.getType();
                    BaseModel<GetShareContentBean> base = gson.fromJson(content, type);
                    if (base.result != null) {
                        share = base.result;
//                        Log.d("++++", "onSuccess: " + networkImages.get(1).getImage());
                    }
                } else {
                    view.toastMessage("网络异常，加载数据失败");
                }

            }
        });

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
