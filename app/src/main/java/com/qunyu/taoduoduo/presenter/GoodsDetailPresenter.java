package com.qunyu.taoduoduo.presenter;

import android.content.Context;

import com.andbase.library.http.AbHttpUtil;
import com.andbase.library.http.listener.AbStringHttpResponseListener;
import com.andbase.library.http.model.AbRequestParams;
import com.andbase.library.http.model.AbResult;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.qunyu.taoduoduo.activity.ConfirmOrderActivity;
import com.qunyu.taoduoduo.activity.GoodsDetailActivity;
import com.qunyu.taoduoduo.api.AddFavoriteApi;
import com.qunyu.taoduoduo.api.AddPurchaseApi;
import com.qunyu.taoduoduo.api.DelFavoriteApi;
import com.qunyu.taoduoduo.api.GetProductSkusApi;
import com.qunyu.taoduoduo.api.GuessYourLikeApi;
import com.qunyu.taoduoduo.api.OpenGroupActivityApi;
import com.qunyu.taoduoduo.base.BaseModel;
import com.qunyu.taoduoduo.bean.AddPurchaseBean;
import com.qunyu.taoduoduo.bean.GetShareContentBean;
import com.qunyu.taoduoduo.bean.GuessYourLikeBean;
import com.qunyu.taoduoduo.bean.OpenGroupBean;
import com.qunyu.taoduoduo.bean.ProductSkuBean;
import com.qunyu.taoduoduo.global.Constant;
import com.qunyu.taoduoduo.mvpview.GoodsDetailView;
import com.qunyu.taoduoduo.utils.LogUtil;
import com.qunyu.taoduoduo.utils.StringUtils;
import com.qunyu.taoduoduo.utils.ToastUtils;
import com.qunyu.taoduoduo.utils.UserInfoUtils;

import java.lang.reflect.Type;
import java.util.List;

/**
 * 开团商品详情
 * Created by Administrator on 2016/10/8.
 */

public class GoodsDetailPresenter {
    GoodsDetailView view;
    public OpenGroupBean openGroupBean;
    OpenGroupActivityApi groupActivityApi;
    Context context;
    AddFavoriteApi addFavoriteApi;
    DelFavoriteApi delFavoriteApi;
    GetProductSkusApi productSkusApi;
    GuessYourLikeApi guessYourLikeApi;
    public List<GuessYourLikeBean> guessYourLikeBeanList;

    AddPurchaseApi addPurchaseApi;

    public GetShareContentBean share;

    public GoodsDetailPresenter(GoodsDetailView goodsDetailView, Context context) {
        view = goodsDetailView;
        this.context = context;
        groupActivityApi = new OpenGroupActivityApi();
        addFavoriteApi = new AddFavoriteApi();
        delFavoriteApi = new DelFavoriteApi();
        productSkusApi = new GetProductSkusApi();
        guessYourLikeApi = new GuessYourLikeApi();
        addPurchaseApi = new AddPurchaseApi();
    }

    public void loadData(String activityId, String pid, String tag) {
        //AbAppConfig.USER_AGENT = "PinDeHaoAndroid";
        //System.setProperty("http.agent", "PinDeHaoAndroid");
        if (StringUtils.isNotBlank(tag) && tag.equals("caijia")) {
            //猜价格
            groupActivityApi.setPid(pid);
        } else {
            groupActivityApi.setActivityId(activityId);
        }

        if (UserInfoUtils.isLogin()) {
            groupActivityApi.setUserId(UserInfoUtils.GetUid());
        }
        LogUtil.Log(groupActivityApi.getUrl() + "?" + groupActivityApi.getParamMap().getParamString());
        AbHttpUtil.getInstance(context).get(groupActivityApi.getUrl(), groupActivityApi.getParamMap(), new AbStringHttpResponseListener() {
            @Override
            public void onSuccess(int i, String s) {
                {
                    LogUtil.Log(s);
                    AbResult result = new AbResult(s);
                    if (result.getResultCode() == AbResult.RESULT_OK) {
                        Gson gson = new Gson();
                        Type type = new TypeToken<BaseModel<OpenGroupBean>>() {
                        }.getType();
                        BaseModel<OpenGroupBean> base = gson.fromJson(s, type);
                        if (base.success && base.result != null) {
                            openGroupBean = base.result;
                            //openGroupBean.productStatus="0";
                            if (openGroupBean.productStatus.equals("1")) {
                                view.setDetail(openGroupBean);
                                if (openGroupBean.banners != null && openGroupBean.banners.size() > 0) {
                                    view.setBannar(openGroupBean.banners);
                                }
                                if (openGroupBean.waitGroupList != null && !openGroupBean.waitGroupList.isEmpty()) {
                                    view.setWaitGroupList(openGroupBean.waitGroupList);
                                }
                            } else {
                                //下架
                                view.setEmptyView();
                            }
                            //view.loadguessYourLike();

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

    public void addFavorite(String activityId, String pid) {
        addFavoriteApi.setUid(UserInfoUtils.GetUid());
        addFavoriteApi.setActivityId(activityId);
        addFavoriteApi.setFavSenId(pid);
        LogUtil.Log(addFavoriteApi.getUrl() + "?" + addFavoriteApi.getParamMap().getParamString());
        AbHttpUtil.getInstance(context).get(addFavoriteApi.getUrl(), addFavoriteApi.getParamMap(), new AbStringHttpResponseListener() {
            @Override
            public void onSuccess(int i, String s) {
                view.removeDialog();
                LogUtil.Log(s);
                AbResult result = new AbResult(s);
                if (result.getResultCode() == AbResult.RESULT_OK) {
                    Gson gson = new Gson();
                    Type type = new TypeToken<BaseModel<Object>>() {
                    }.getType();
                    BaseModel<Object> base = gson.fromJson(s, type);
                    if (base.success) {
                        view.onaddFavoriteSuccess();
                    }
                    view.toastMessage(base.error_msg);


                } else {
                    view.toastMessage("网络异常，数据加载失败");
                }

            }

            @Override
            public void onStart() {
                view.showProgressDialog("收藏中...");

            }

            @Override
            public void onFinish() {
                view.removeDialog();
            }

            @Override
            public void onFailure(int i, String s, Throwable throwable) {
                view.removeDialog();
                view.toastMessage(throwable.getMessage());
            }
        });

    }

    public void delFavorite(String activityId, String pid) {
        delFavoriteApi.setUid(UserInfoUtils.GetUid());
        delFavoriteApi.setActivityId(activityId);
        delFavoriteApi.setFavSenId(pid);
        LogUtil.Log(delFavoriteApi.getUrl() + "?" + delFavoriteApi.getParamMap().getParamString());
        AbHttpUtil.getInstance(context).get(delFavoriteApi.getUrl(), delFavoriteApi.getParamMap(), new AbStringHttpResponseListener() {
            @Override
            public void onSuccess(int i, String s) {
                view.removeDialog();
                LogUtil.Log(s);
                AbResult result = new AbResult(s);
                if (result.getResultCode() == AbResult.RESULT_OK) {
                    Gson gson = new Gson();
                    Type type = new TypeToken<BaseModel<Object>>() {
                    }.getType();
                    BaseModel<Object> base = gson.fromJson(s, type);
                    if (base.success) {
                        view.ondelFavoriteSuccess();
                    }
                    view.toastMessage(base.error_msg);


                } else {
                    view.toastMessage("网络异常，数据加载失败");
                }

            }

            @Override
            public void onStart() {
                view.showProgressDialog("取消收藏中...");

            }

            @Override
            public void onFinish() {
                view.removeDialog();
            }

            @Override
            public void onFailure(int i, String s, Throwable throwable) {
                view.removeDialog();
                view.toastMessage(throwable.getMessage());
            }
        });

    }

//    public void loadSku(String pid) {
//        productSkusApi.setPid(pid);
//        LogUtil.Log(productSkusApi.getUrl() + "?" + productSkusApi.getParamMap().getParamString());
//        AbHttpUtil.getInstance(context).get(productSkusApi.getUrl(), productSkusApi.getParamMap(), new AbStringHttpResponseListener() {
//            @Override
//            public void onSuccess(int i, String s) {
//
//                LogUtil.Log(s);
//                AbResult result = new AbResult(s);
//                if (result.getResultCode() == AbResult.RESULT_OK) {
//                    Gson gson = new Gson();
//                    Type type = new TypeToken<BaseModel<ProductSkuBean>>() {
//                    }.getType();
//                    BaseModel<ProductSkuBean> base = gson.fromJson(s, type);
//                    if (base.success && base.result != null) {
//                        productSkuBean = base.result;
//                        validSKu = productSkuBean.validSKu;
//                        skuList = productSkuBean.skuList;
//                        skuColorValue = skuList.get(0).skuValue;
//                        skuFormatValue = skuList.get(1).skuValue;
//                        for (ProductSkuBean.SkuValue sKu :
//                                skuColorValue) {
//                            if (skucolorMAxsize < sKu.optionValue.length()) {
//                                skucolorMAxsize = sKu.optionValue.length();
//                            }
//
//                        }
//                        for (ProductSkuBean.SkuValue sKu :
//                                skuFormatValue) {
//                            if (skuformatMAxsize < sKu.optionValue.length()) {
//                                skuformatMAxsize = sKu.optionValue.length();
//                            }
//                            if (skuformatMAxsize < sKu.optionValue.length()) {
//                                skuformatMAxsize = sKu.optionValue.length();
//                            }
//
//                        }
//                        view.showPopuWindow();
//                    } else {
//                        view.toastMessage(base.error_msg);
//                    }
//
//
//                } else {
//                    view.toastMessage("网络异常，数据加载失败");
//                }
//
//            }
//
//            @Override
//            public void onStart() {
//                view.showProgressDialog("加载中...");
//            }
//
//            @Override
//            public void onFinish() {
//                view.removeDialog();
//            }
//
//            @Override
//            public void onFailure(int i, String s, Throwable throwable) {
//                view.toastMessage(throwable.getMessage());
//            }
//        });
//    }

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
                            guessYourLikeBeanList = base.result;
                            view.setguessYourLike(guessYourLikeBeanList);

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

    public void addguessFavorite(String activityId, String pid, final int postion) {
        addFavoriteApi.setUid(UserInfoUtils.GetUid());
        addFavoriteApi.setActivityId(activityId);
        addFavoriteApi.setFavSenId(pid);
        LogUtil.Log(addFavoriteApi.getUrl() + "?" + addFavoriteApi.getParamMap().getParamString());
        AbHttpUtil.getInstance(context).get(addFavoriteApi.getUrl(), addFavoriteApi.getParamMap(), new AbStringHttpResponseListener() {
            @Override
            public void onSuccess(int i, String s) {
                view.removeDialog();
                LogUtil.Log(s);
                AbResult result = new AbResult(s);
                if (result.getResultCode() == AbResult.RESULT_OK) {
                    Gson gson = new Gson();
                    Type type = new TypeToken<BaseModel<Object>>() {
                    }.getType();
                    BaseModel<Object> base = gson.fromJson(s, type);
                    if (base.success) {
                        if (guessYourLikeBeanList.get(postion).isCollect.equals("1")) {
                            guessYourLikeBeanList.get(postion).isCollect = "0";
                        } else {
                            guessYourLikeBeanList.get(postion).isCollect = "1";
                        }
                        view.updateguessYourLike();
                    }
                    view.toastMessage(base.error_msg);


                } else {
                    view.toastMessage("网络异常，数据加载失败");
                }

            }

            @Override
            public void onStart() {
                view.showProgressDialog("收藏中...");

            }

            @Override
            public void onFinish() {
                view.removeDialog();
            }

            @Override
            public void onFailure(int i, String s, Throwable throwable) {
                view.removeDialog();
                view.toastMessage(throwable.getMessage());
            }
        });

    }

    public void delguessFavorite(String activityId, String pid, final int position) {
        delFavoriteApi.setUid(UserInfoUtils.GetUid());
        delFavoriteApi.setActivityId(activityId);
        delFavoriteApi.setFavSenId(pid);
        LogUtil.Log(delFavoriteApi.getUrl() + "?" + delFavoriteApi.getParamMap().getParamString());
        AbHttpUtil.getInstance(context).get(delFavoriteApi.getUrl(), delFavoriteApi.getParamMap(), new AbStringHttpResponseListener() {
            @Override
            public void onSuccess(int i, String s) {
                view.removeDialog();
                LogUtil.Log(s);
                AbResult result = new AbResult(s);
                if (result.getResultCode() == AbResult.RESULT_OK) {
                    Gson gson = new Gson();
                    Type type = new TypeToken<BaseModel<Object>>() {
                    }.getType();
                    BaseModel<Object> base = gson.fromJson(s, type);
                    if (base.success) {
                        if (guessYourLikeBeanList.get(position).isCollect.equals("1")) {
                            guessYourLikeBeanList.get(position).isCollect = "0";
                        } else {
                            guessYourLikeBeanList.get(position).isCollect = "1";
                        }
                        view.updateguessYourLike();
                    }
                    view.toastMessage(base.error_msg);


                } else {
                    view.toastMessage("网络异常，数据加载失败");
                }

            }

            @Override
            public void onStart() {
                view.showProgressDialog("取消收藏中...");

            }

            @Override
            public void onFinish() {
                view.removeDialog();
            }

            @Override
            public void onFailure(int i, String s, Throwable throwable) {
                view.removeDialog();
                view.toastMessage(throwable.getMessage());
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


    public void getShareContentApiGet(String source,String activityId) {
        AbRequestParams params = new AbRequestParams();
        switch (source) {
//            String source;//来源，1-普通拼团2-团免3-猜价4-单独购买5-0.1抽奖6-限时秒杀 7免费抽奖
            // int actStatus;//底部部按钮状态，0.1抽奖和限时秒杀   1-0.1进行中可参加  2-0.1进行中已参加过  3-0.1结束查看中奖名单
            //4-限时开抢中 5-已售罄 6-限时即将开抢  7-限时已结束
            //免费抽奖 8-立即开团  9-已参加过该活动 10-等待开奖中 11-查看中奖名单 12-活动未开始
            case "5":
                params.put("type", "19");
                break;
            case "6":
                params.put("type", "17");
                break;
            case "7":
                params.put("type", "22");
                break;
            default:
                params.put("type", "8");
                break;
        }
        params.put("id", activityId);
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
                LogUtil.Log(error.getMessage());
            }

            @Override
            public void onSuccess(int statusCode, String content) {
                AbResult result = new AbResult(content);

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
                    view.toastMessage("网络异常，数据加载失败");
                    LogUtil.Log(result.getResultMessage());
                }

            }
        });

    }
}
