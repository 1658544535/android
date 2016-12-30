package com.qunyu.taoduoduo.activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.allure.lbanners.LMBanners;
import com.andbase.library.http.AbHttpUtil;
import com.andbase.library.http.listener.AbStringHttpResponseListener;
import com.andbase.library.http.model.AbRequestParams;
import com.andbase.library.http.model.AbResult;
import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.meiqia.core.MQManager;
import com.meiqia.core.callback.OnClientInfoCallback;
import com.meiqia.meiqiasdk.util.MQIntentBuilder;
import com.qunyu.taoduoduo.R;
import com.qunyu.taoduoduo.adapter.CaiUrlImgAdapter;
import com.qunyu.taoduoduo.adapter.SkuColorAdapter;
import com.qunyu.taoduoduo.adapter.SkuFormatAdapter;
import com.qunyu.taoduoduo.base.BaseActivity;
import com.qunyu.taoduoduo.base.BaseModel;
import com.qunyu.taoduoduo.base.BaseUtil;
import com.qunyu.taoduoduo.bean.GetShareContentBean;
import com.qunyu.taoduoduo.bean.GuessWinListBean;
import com.qunyu.taoduoduo.bean.ProductFocusImagsBean;
import com.qunyu.taoduoduo.bean.ReadyJoinBean;
import com.qunyu.taoduoduo.bean.UserJoinInfoBean;
import com.qunyu.taoduoduo.config.AppConfig;
import com.qunyu.taoduoduo.fragment.AddressDialogFragment;
import com.qunyu.taoduoduo.global.AnyEventType;
import com.qunyu.taoduoduo.global.Constant;
import com.qunyu.taoduoduo.global.Untool;
import com.qunyu.taoduoduo.mvpview.CaiGoodsDetialView;
import com.qunyu.taoduoduo.mvpview.SkuModeView;
import com.qunyu.taoduoduo.presenter.CaiGoodsPresenter;
import com.qunyu.taoduoduo.presenter.SkuPresenter;
import com.qunyu.taoduoduo.utils.LogUtil;
import com.qunyu.taoduoduo.utils.ToastUtils;
import com.qunyu.taoduoduo.utils.UserInfoUtils;
import com.qunyu.taoduoduo.view.CircleTransform;
import com.qunyu.taoduoduo.widget.GridViewForScrollView;
import com.umeng.analytics.MobclickAgent;
import com.umeng.socialize.ShareAction;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.media.UMImage;
import com.zhy.android.percent.support.PercentLinearLayout;
import com.zhy.android.percent.support.PercentRelativeLayout;

import org.greenrobot.eventbus.EventBus;

import java.lang.reflect.Type;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.qunyu.taoduoduo.global.Constant.guessWinListApi;

public class CaiGoodsDetailActivity extends BaseActivity implements View.OnClickListener, CaiGoodsDetialView, SkuModeView {
    @BindView(R.id.rl_01)
    PercentRelativeLayout rl_01;
    //    @BindView(R.id.tv_s)
    TextView tv_s;
    //    @BindView(R.id.tv_f)
    TextView tv_f;
    //    @BindView(R.id.tv_m)
    TextView tv_m;
    @BindView(R.id.top)
    PercentRelativeLayout top;
    @BindView(R.id.list)
    PercentRelativeLayout list;
    @BindView(R.id.wbv)
    WebView wbv;
    @BindView(R.id.bottom)
    PercentRelativeLayout bottom;
    String activityId = null;
    String productId = null;
    ReadyJoinBean date = null;
    LMBanners banners = null;
    TextView tvname = null;
    TextView tvf = null;
    TextView tvjs = null;
    PercentLinearLayout tvtime = null;
    TextView tv_dz = null;
    TextView tv_gj = null;
    TextView tv_jg = null;
    ImageView tvhome = null;
    TextView tvbtn1 = null;
    TextView tvbtn2 = null;
    ImageView tv_kf = null;
    DecimalFormat format, format1;
    private ArrayList<ProductFocusImagsBean> networkImages = new ArrayList<ProductFocusImagsBean>();
    private final static String fileName = "province.json";
    //添加地址弹出框
    AddressDialogFragment fragment;
    ImageView iv_fenxiang;
    String tag = "0";

    CaiGoodsPresenter presenter;

    //sku
    SkuColorAdapter skuColorAdapter;
    SkuFormatAdapter formatAdapter;
    SkuPresenter skuPresenter;
    PopupWindow skupopupWindow;
    PercentRelativeLayout rl_main;
    ImageView iv_iamge;
    TextView tv_productName;
    TextView tv_price;
    TextView tv_colortitle;
    TextView tv_formattitle;
    GridViewForScrollView gv_color;
    GridViewForScrollView gv_format;
    View add;
    View reduce;
    TextView tv_num;
    Button btn_offirm;
    TextView tv_pName;
    TextView tv_skutips;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = this.getIntent().getExtras();
        activityId = bundle.getString("activityId");
        productId = bundle.getString("productId");
        tag = "1";
        try {
            tag = bundle.getString("tag", "1");
        } catch (Exception e) {

        }

        baseSetContentView(R.layout.activity_c_goods_detail);
        baseSetText("猜价赢好礼");
        ButterKnife.bind(this);
        iv_fenxiang = (ImageView) findViewById(R.id.iv_fenxiang);
        iv_fenxiang.setVisibility(View.VISIBLE);
        iv_fenxiang.setOnClickListener(this);
        format = new DecimalFormat("0");
        format1 = new DecimalFormat("0.0");
        GetShareContentApiGet();

        presenter = new CaiGoodsPresenter(this, this);
        skuPresenter = new SkuPresenter(this, this, this);
    }

    public void onPause() {
        super.onPause();
        MobclickAgent.onPause(this);
    }

    @Override
    protected void onDestroy() {
        handler.removeCallbacksAndMessages(null);
        try {
            skuPresenter.onDestroy();
        } catch (Exception e) {
            e.printStackTrace();
        }
        super.onDestroy();

    }

    GetShareContentBean share;

    private void GetShareContentApiGet() {
        AbRequestParams params = new AbRequestParams();
        params.put("id", activityId);
        params.put("type", "11");
        LogUtil.Log(Constant.getShareContentApi + "?" + params.getParamString());
        AbHttpUtil.getInstance(this).get(Constant.getShareContentApi, params, new AbStringHttpResponseListener() {

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
                ToastUtils.showShortToast(CaiGoodsDetailActivity.this, "网络异常，数据加载失败");
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
                    ToastUtils.showShortToast(CaiGoodsDetailActivity.this, "网络异常，数据加载失败");
                    LogUtil.Log(result.getResultMessage());
                }

            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        UMShareAPI.get(this).onActivityResult(requestCode, resultCode, data);
    }

    @Override
    protected void onResume() {
        super.onResume();
        ReadyJoinGet();
        MobclickAgent.onResume(this);

    }

    private void GroupHomeApiGet() {
        AbRequestParams params = new AbRequestParams();
        params.put("productId", productId);
        LogUtil.Log(Constant.productFocusImagsApi + "?" + params.getParamString());
        AbHttpUtil.getInstance(this).get(Constant.productFocusImagsApi, params, new AbStringHttpResponseListener() {

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
                ToastUtils.showShortToast(CaiGoodsDetailActivity.this, "网络异常，数据加载失败");
                LogUtil.Log(error.getMessage());
            }

            @Override
            public void onSuccess(int statusCode, String content) {
                AbResult result = new AbResult(content);

                if (result.getResultCode() == AbResult.RESULT_OK) {
                    Gson gson = new Gson();
                    Type type = new TypeToken<BaseModel<ArrayList<ProductFocusImagsBean>>>() {
                    }.getType();
                    BaseModel<ArrayList<ProductFocusImagsBean>> base = gson.fromJson(content, type);
                    if (base.result != null) {
                        networkImages = base.result;
//                        Log.d("++++", "onSuccess: " + networkImages.get(1).getImage());
                    }
                    try {
                        SetView(date);
                    } catch (Exception c) {
                    }
                } else {
                    ToastUtils.showShortToast(CaiGoodsDetailActivity.this, "网络异常，数据加载失败");
                    LogUtil.Log(result.getResultMessage());
                }

            }
        });

    }

    private void ReadyJoinGet() {
        AbRequestParams params = new AbRequestParams();
        params.put("userId", Untool.getUid());
        params.put("activityId", activityId);
        LogUtil.Log(Constant.readyJoinApi + "?" + params.getParamString());
        AbHttpUtil.getInstance(this).get(Constant.readyJoinApi, params, new AbStringHttpResponseListener() {

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

                ToastUtils.showShortToast(CaiGoodsDetailActivity.this, "网络异常，数据加载失败");
                LogUtil.Log(error.getMessage());
            }

            @Override
            public void onSuccess(int statusCode, String content) {
                AbResult result = new AbResult(content);
                Log.d("++++", "onSuccess: " + content + activityId);
                if (result.getResultCode() == AbResult.RESULT_OK) {
                    Gson gson = new Gson();
                    Type type = new TypeToken<BaseModel<ReadyJoinBean>>() {
                    }.getType();
                    BaseModel<ReadyJoinBean> base = gson.fromJson(content, type);
                    if (base.result != null) {
                        date = base.result;
                        productId = date.getProductId();

                        GroupHomeApiGet();
                    } else {
                        ToastUtils.showShortToast(CaiGoodsDetailActivity.this, base.error_msg);
                    }
                } else {
                    ToastUtils.showShortToast(CaiGoodsDetailActivity.this, "网络异常，数据加载失败");
                    LogUtil.Log(result.getResultMessage());
                }

            }
        });

    }

    TextView tv_renshu;

    private void Top(int R1, SpannableStringBuilder t6) {
        top.removeAllViews();
        View vHead = View.inflate(CaiGoodsDetailActivity.this, R1, null);
        try {
            banners = (LMBanners) vHead.findViewById(R.id.banners);
            banners.setAdapter(new CaiUrlImgAdapter(CaiGoodsDetailActivity.this), networkImages);
            //参数设置
            banners.setAutoPlay(false);//自动播放
            banners.setVertical(false);//是否可以垂直
            banners.setScrollDurtion(222);//两页切换时间
            banners.setCanLoop(false);//循环播放
            banners.setSelectIndicatorRes(R.drawable.page_indicator_select);//选中的原点
            banners.setUnSelectUnIndicatorRes(R.drawable.page_indicator_unselect);//未选中的原点
//        banners.setHoriZontalTransitionEffect(TransitionEffect.Default);//选中喜欢的样式
//        banners.setHoriZontalCustomTransformer(new ParallaxTransformer(R.id.id_image));//自定义样式
            banners.setDurtion(2000);//切换时间
            banners.hideIndicatorLayout();//隐藏原点
            banners.showIndicatorLayout();//显示原点
            banners.setIndicatorPosition(LMBanners.IndicaTorPosition.BOTTOM_MID);//设置原点显示位置
            try {
                if (date.getIsPublic().equals("1")) {
                    tv_renshu = (TextView) vHead.findViewById(R.id.tv_renshu);
                    switch (date.getPrize()) {
                        case "0":
                            tv_renshu.setText("来晚啦！该活动已经结束");
                            break;
                        case "1":
                            tv_renshu.setText("恭喜您，获得一等奖");
                            break;
                        case "2":
                            tv_renshu.setText("恭喜您，获得二等奖");
                            if (date.getIsAlert().equals("0") && date.getIsRecCoupon().equals("1")) {
                                initPopuptWindow2();
                                backgroundAlpha(CaiGoodsDetailActivity.this, 0.5f);
                                getPopupWindow2();
                                popupWindow.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
                                popupWindow.showAtLocation(rl_01, Gravity.CENTER, 0, 0);
                                callGuessCouponAlertApi();
                            }
                            break;
                        case "3":
                            tv_renshu.setText("恭喜您，获得三等奖");
                            if (date.getIsAlert().equals("0") && date.getIsRecCoupon().equals("1")) {

                                initPopuptWindow2();
                                backgroundAlpha(CaiGoodsDetailActivity.this, 0.5f);
                                getPopupWindow2();
                                popupWindow.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
                                popupWindow.showAtLocation(rl_01, Gravity.CENTER, 0, 0);
                                callGuessCouponAlertApi();
                            }
                            break;
                    }
                }
            } catch (Exception e) {
            }
            tv_s = (TextView) vHead.findViewById(R.id.tv_s);
            tv_f = (TextView) vHead.findViewById(R.id.tv_f);
            tv_m = (TextView) vHead.findViewById(R.id.tv_m);
            tvname = (TextView) vHead.findViewById(R.id.textView1);
            tvjs = (TextView) vHead.findViewById(R.id.tv_js2);
            tvtime = (PercentLinearLayout) vHead.findViewById(R.id.tv_good_xl);
            tvname.setText(date.getProductName());
            tvf = (TextView) vHead.findViewById(R.id.textView6);
            tvf.setText(t6);


        } catch (Exception e) {
        }
        top.addView(vHead);
        handler.postDelayed(runnable, 1000);
    }

    private void callGuessCouponAlertApi() {
        AbRequestParams params = new AbRequestParams();
        params.put("activityId", activityId);
        params.put("userId", Untool.getUid());
        LogUtil.Log(Constant.callGuessCouponAlertApi + "?" + params.getParamString());
        AbHttpUtil.getInstance(CaiGoodsDetailActivity.this).get(Constant.callGuessCouponAlertApi, params, new AbStringHttpResponseListener() {

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
            }

            @Override
            public void onSuccess(int statusCode, String content) {
                AbResult result = new AbResult(content);


            }
        });

    }

    private void Bottom(int R1) {
        bottom.removeAllViews();
        View vHead = View.inflate(CaiGoodsDetailActivity.this, R1, null);
        tvhome = (ImageView) vHead.findViewById(R.id.textView1);
        tvhome.setOnClickListener(this);
        try {
            tvbtn1 = (TextView) vHead.findViewById(R.id.tv_gd);
            tvbtn1.setOnClickListener(this);
            if (!date.getPrize().equals("1") && date.getIsWin().equals("1")) {
                if (date.getIsRecCoupon().equals("1")) {
                    tvbtn1.setOnClickListener(this);
                } else {
                    tvbtn1.setOnClickListener(this);
                }
            }
        } catch (Exception e) {
        }
        try {
            tvbtn2 = (TextView) vHead.findViewById(R.id.tv_good_xl);
            tvbtn2.setOnClickListener(this);
        } catch (Exception e) {
        }
        try {
            tv_dz = (TextView) vHead.findViewById(R.id.tv_dz);

            if (date.getIsRecCoupon().equals("1")) {
                tv_dz.setBackgroundColor(getResources().getColor(R.color.base_line));
                tv_dz.setClickable(false);
            } else {
                tv_dz.setOnClickListener(this);
            }
        } catch (Exception e) {
        }
        try {
            tv_kf = (ImageView) vHead.findViewById(R.id.tv_kf);
            tv_kf.setOnClickListener(this);
        } catch (Exception e) {
        }
        try {
            tv_gj = (TextView) vHead.findViewById(R.id.tv_gj);
            tv_gj.setOnClickListener(this);
        } catch (Exception e) {
        }
        try {
            tv_jg = (TextView) vHead.findViewById(R.id.tv_jg);
            if (tag.equals("1")) {
                tv_jg.setText("您的报价为： ￥" + format1.format(new BigDecimal(date.getUserInfo().getUserPrice())) + " | 等待揭晓");
            } else {
                tv_jg.setText("耐心等待开奖结果！");
            }

        } catch (Exception e) {
        }
        bottom.addView(vHead);
    }

    private void List(int R1, int t) {
        list.removeAllViews();
        View vHead = View.inflate(CaiGoodsDetailActivity.this, R1, null);
        switch (t) {
            case 1:
                //已参与
                List1(vHead);
                break;
            case 2:
                //未参与
                List2(vHead);
                break;
            case 3:
                //已开奖
                List4(vHead);
                break;
            case 4:
                //未开奖
                List3(vHead);
                break;
        }
        list.addView(vHead);
    }

    ImageView iv_u_logo = null;
    TextView tv_u = null;
    TextView tv_u1 = null;
    TextView tv_u_renshu = null;
    TextView tv_rs1 = null;
    TextView tv_cy = null;

    private void List1(View view) {
        iv_u_logo = (ImageView) view.findViewById(R.id.iv_u_logo);
        Glide.with(CaiGoodsDetailActivity.this).load(date.getUserInfo().getUserImage()).transform(new CircleTransform(CaiGoodsDetailActivity.this)).error(R.mipmap.default_touxiang).into(iv_u_logo);
        tv_u = (TextView) view.findViewById(R.id.tv_u);
        tv_u1 = (TextView) view.findViewById(R.id.tv_u2);
        SpannableStringBuilder style1 = new SpannableStringBuilder("出价：￥" + format1.format(new BigDecimal(date.getUserInfo().getUserPrice())));
        style1.setSpan(new ForegroundColorSpan(CaiGoodsDetailActivity.this.getResources().getColor(R.color.text_01)), 3, 4 + format1.format(new BigDecimal(date.getUserInfo().getUserPrice())).length(), Spannable.SPAN_EXCLUSIVE_INCLUSIVE);
        tv_u.setText(date.getUserInfo().getUserName());
        tv_u1.setText(style1);
        tv_u_renshu = (TextView) view.findViewById(R.id.tv_u_renshu);
        tv_u_renshu.setText(date.getUserInfo().getJoinTime());
        tv_rs1 = (TextView) view.findViewById(R.id.tv_rs1);
        List3(view);
    }

    TextView tv_gd;

    private void List2(View view) {
        tv_gd = (TextView) view.findViewById(R.id.tv_gd);
        SpannableStringBuilder style = new SpannableStringBuilder("已有" + date.getJoinNum() + "个小伙伴参与，您需要提交价格才可以看到其它记录");
        style.setSpan(new ForegroundColorSpan(CaiGoodsDetailActivity.this.getResources().getColor(R.color.text_01)), 2, ("已有" + date.getJoinNum() + "个").length(), Spannable.SPAN_EXCLUSIVE_INCLUSIVE);
        tv_gd.setText(style);
    }

    private void List3(View view) {
        tv_rs1 = (TextView) view.findViewById(R.id.tv_rs1);
        tv_cy = (TextView) view.findViewById(R.id.tv_cy);

        tv_cy.setOnClickListener(this);
        //
        View view1 = (PercentLinearLayout) view.findViewById(R.id.list_layout1);
        view1.setVisibility(View.GONE);
        //
        View view2 = (PercentLinearLayout) view.findViewById(R.id.list_layout2);
        view2.setVisibility(View.GONE);
        //
        View view3 = (PercentLinearLayout) view.findViewById(R.id.list_layout3);
        view3.setVisibility(View.GONE);
        //
        View view4 = (PercentLinearLayout) view.findViewById(R.id.list_layout4);
        view4.setVisibility(View.GONE);
        //
        View view5 = (PercentLinearLayout) view.findViewById(R.id.list_layout5);
        view5.setVisibility(View.GONE);
        //
        final View[] V1 = {view1, view2, view3, view4, view5};
        AbRequestParams params = new AbRequestParams();
        params.put("activityId", activityId);
        LogUtil.Log(Constant.userJoinInfoApi + "?" + params.getParamString());
        AbHttpUtil.getInstance(this).get(Constant.userJoinInfoApi, params, new AbStringHttpResponseListener() {

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
                ToastUtils.showShortToast(CaiGoodsDetailActivity.this, "网络异常，数据加载失败");
                LogUtil.Log(error.getMessage());
            }

            @Override
            public void onSuccess(int statusCode, String content) {
                AbResult result = new AbResult(content);
                if (result.getResultCode() == AbResult.RESULT_OK) {
                    Gson gson = new Gson();
                    Type type = new TypeToken<BaseModel<UserJoinInfoBean>>() {
                    }.getType();
                    BaseModel<UserJoinInfoBean> base = gson.fromJson(content, type);
                    if (base.result != null) {
                        SpannableStringBuilder style = new SpannableStringBuilder("已有" + base.result.getJoinNum() + "个小伙伴参与");
                        style.setSpan(new ForegroundColorSpan(CaiGoodsDetailActivity.this.getResources().getColor(R.color.text_01)), 2, 3 + base.result.getJoinNum().length(), Spannable.SPAN_EXCLUSIVE_INCLUSIVE);
                        tv_rs1.setText(style);
                        int k;
                        if (base.result.getJoinUserList().size() < 5) {
                            k = base.result.getJoinUserList().size();
                        } else {
                            k = 5;
                        }
                        for (int i = 0; i < k; i++) {
                            V1[i].setVisibility(View.VISIBLE);
                            ImageView iv_logo = (ImageView) V1[i].findViewById(R.id.iv_logo);
                            Glide.with(CaiGoodsDetailActivity.this).load(base.result.getJoinUserList().get(i).getUserImage()).transform(new CircleTransform(CaiGoodsDetailActivity.this)).error(R.mipmap.default_touxiang).into(iv_logo);
                            TextView tv_logo = (TextView) V1[i].findViewById(R.id.tv_logo);
                            TextView tv_logo1 = (TextView) V1[i].findViewById(R.id.tv_logo1);
                            SpannableStringBuilder style1 = new SpannableStringBuilder("出价：￥" + format1.format(new BigDecimal(base.result.getJoinUserList().get(i).getUserPrice())));
                            style1.setSpan(new ForegroundColorSpan(CaiGoodsDetailActivity.this.getResources().getColor(R.color.text_01)), 3, 4 + format1.format(new BigDecimal(base.result.getJoinUserList().get(i).getUserPrice())).length(), Spannable.SPAN_EXCLUSIVE_INCLUSIVE);
                            tv_logo.setText(base.result.getJoinUserList().get(i).getUserName());
                            tv_logo1.setText(style1);
                            TextView tv_logo_rs = (TextView) V1[i].findViewById(R.id.tv_logo_rs);
                            tv_logo_rs.setText(base.result.getJoinUserList().get(i).getJoinTime());
                        }
                    } else {
                        ToastUtils.showShortToast(CaiGoodsDetailActivity.this, base.error_msg);
                    }
                } else {
                    ToastUtils.showShortToast(CaiGoodsDetailActivity.this, "网络异常，数据加载失败");
                    LogUtil.Log(result.getResultMessage());
                }

            }
        });
    }

    private void List4(View view) {
        final PercentRelativeLayout r1d = (PercentRelativeLayout) view.findViewById(R.id.r1d);
        r1d.setVisibility(View.GONE);
        final PercentRelativeLayout r2d = (PercentRelativeLayout) view.findViewById(R.id.r2d);
        r2d.setVisibility(View.GONE);
        final PercentRelativeLayout r3d = (PercentRelativeLayout) view.findViewById(R.id.r3d);
        r3d.setVisibility(View.GONE);
        final PercentLinearLayout l1d = (PercentLinearLayout) view.findViewById(R.id.l1d);
        l1d.setVisibility(View.GONE);
        final PercentLinearLayout l2d = (PercentLinearLayout) view.findViewById(R.id.l2d);
        l2d.setVisibility(View.GONE);
        final PercentLinearLayout l3d = (PercentLinearLayout) view.findViewById(R.id.l3d);
        l3d.setVisibility(View.GONE);
        final TextView tv_1d = (TextView) view.findViewById(R.id.tv_1d);
        final TextView tv_2d = (TextView) view.findViewById(R.id.tv_2d);
        final TextView tv_3d = (TextView) view.findViewById(R.id.tv_3d);
        final TextView tv_1d_gd = (TextView) view.findViewById(R.id.tv_1d_gd);
        final TextView tv_2d_gd = (TextView) view.findViewById(R.id.tv_2d_gd);
        final TextView tv_3d_gd = (TextView) view.findViewById(R.id.tv_3d_gd);
        tv_1d_gd.setOnClickListener(this);
        tv_2d_gd.setOnClickListener(this);
        tv_3d_gd.setOnClickListener(this);
        //
        View view1 = (PercentLinearLayout) view.findViewById(R.id.list_layout1);
        view1.setVisibility(View.GONE);
        View view2 = (PercentLinearLayout) view.findViewById(R.id.list_layout2);
        view2.setVisibility(View.GONE);
        final View[] Vd1 = {view1, view2};
        //
        View view3 = (PercentLinearLayout) view.findViewById(R.id.list_layout3);
        view3.setVisibility(View.GONE);
        View view4 = (PercentLinearLayout) view.findViewById(R.id.list_layout4);
        view4.setVisibility(View.GONE);
        final View[] Vd2 = {view3, view4};
        //
        View view5 = (PercentLinearLayout) view.findViewById(R.id.list_layout5);
        view5.setVisibility(View.GONE);
        //
        View view6 = (PercentLinearLayout) view.findViewById(R.id.list_layout6);
        view6.setVisibility(View.GONE);
        final View[] Vd3 = {view5, view6};
        AbRequestParams params = new AbRequestParams();
        params.put("activityId", activityId);
        LogUtil.Log(Constant.guessWinListApi + "?" + params.getParamString());
        AbHttpUtil.getInstance(this).get(guessWinListApi, params, new AbStringHttpResponseListener() {

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
                ToastUtils.showShortToast(CaiGoodsDetailActivity.this, "网络异常，数据加载失败");
                LogUtil.Log(error.getMessage());
            }

            @Override
            public void onSuccess(int statusCode, String content) {
                AbResult result = new AbResult(content);
                if (result.getResultCode() == AbResult.RESULT_OK) {
                    Gson gson = new Gson();
                    Type type = new TypeToken<BaseModel<GuessWinListBean>>() {
                    }.getType();
                    BaseModel<GuessWinListBean> base = gson.fromJson(content, type);
                    if (base.result != null) {
                        if (base.result.getFriPrizeList() != null && base.result.getFriPrizeList().size() != 0) {
                            SpannableStringBuilder style = new SpannableStringBuilder("获得一等奖的小伙伴【" + base.result.getFriPrizeNum() + "】");
                            style.setSpan(new ForegroundColorSpan(CaiGoodsDetailActivity.this.getResources().getColor(R.color.text_01)), 10, 10 + base.result.getFriPrizeNum().length(), Spannable.SPAN_EXCLUSIVE_INCLUSIVE);
                            tv_1d.setText(style);
                            r1d.setVisibility(View.VISIBLE);
                            l1d.setVisibility(View.VISIBLE);
                            for (int i = 0; i < base.result.getFriPrizeList().size(); i++) {
                                if (i < 2) {

                                    Vd1[i].setVisibility(View.VISIBLE);
                                    ImageView iv_logo = (ImageView) Vd1[i].findViewById(R.id.iv_logo);
                                    Glide.with(CaiGoodsDetailActivity.this).load(base.result.getFriPrizeList().get(i).getUserImage()).error(R.mipmap.default_touxiang).transform(new CircleTransform(CaiGoodsDetailActivity.this)).into(iv_logo);
                                    TextView tv_logo = (TextView) Vd1[i].findViewById(R.id.tv_logo);
                                    TextView tv_logo1 = (TextView) Vd1[i].findViewById(R.id.tv_logo1);
                                    SpannableStringBuilder style1 = new SpannableStringBuilder("出价：￥" + format1.format(new BigDecimal(base.result.getFriPrizeList().get(i).getUserPrice())));
                                    style1.setSpan(new ForegroundColorSpan(CaiGoodsDetailActivity.this.getResources().getColor(R.color.text_01)), 3, 4 + format1.format(new BigDecimal(base.result.getFriPrizeList().get(i).getUserPrice())).length(), Spannable.SPAN_EXCLUSIVE_INCLUSIVE);
                                    tv_logo.setText(base.result.getFriPrizeList().get(i).getUserName());
                                    tv_logo1.setText(style1);
                                    TextView tv_logo_rs = (TextView) Vd1[i].findViewById(R.id.tv_logo_rs);
                                    tv_logo_rs.setText(base.result.getFriPrizeList().get(i).getJoinTime());
                                }
                            }
                        }
                        if (base.result.getTwoPrizeList() != null && base.result.getTwoPrizeList().size() != 0) {
                            SpannableStringBuilder style = new SpannableStringBuilder("获得二等奖的小伙伴【" + base.result.getTwoPrizeNum() + "】");
                            style.setSpan(new ForegroundColorSpan(CaiGoodsDetailActivity.this.getResources().getColor(R.color.text_01)), 10, 10 + base.result.getTwoPrizeNum().length(), Spannable.SPAN_EXCLUSIVE_INCLUSIVE);
                            tv_2d.setText(style);
                            r2d.setVisibility(View.VISIBLE);
                            l2d.setVisibility(View.VISIBLE);
                            for (int i = 0; i < base.result.getTwoPrizeList().size(); i++) {
                                if (i < 2) {
                                    Vd2[i].setVisibility(View.VISIBLE);
                                    ImageView iv_logo = (ImageView) Vd2[i].findViewById(R.id.iv_logo);
                                    Glide.with(CaiGoodsDetailActivity.this).load(base.result.getTwoPrizeList().get(i).getUserImage()).error(R.mipmap.default_touxiang).transform(new CircleTransform(CaiGoodsDetailActivity.this)).into(iv_logo);
                                    TextView tv_logo = (TextView) Vd2[i].findViewById(R.id.tv_logo);
                                    TextView tv_logo1 = (TextView) Vd2[i].findViewById(R.id.tv_logo1);
                                    SpannableStringBuilder style1 = new SpannableStringBuilder("出价：￥" + format1.format(new BigDecimal(base.result.getTwoPrizeList().get(i).getUserPrice())));
                                    style1.setSpan(new ForegroundColorSpan(CaiGoodsDetailActivity.this.getResources().getColor(R.color.text_01)), 3, 4 + format1.format(new BigDecimal(base.result.getTwoPrizeList().get(i).getUserPrice())).length(), Spannable.SPAN_EXCLUSIVE_INCLUSIVE);
                                    tv_logo.setText(base.result.getTwoPrizeList().get(i).getUserName());
                                    tv_logo1.setText(style1);
                                    TextView tv_logo_rs = (TextView) Vd2[i].findViewById(R.id.tv_logo_rs);
                                    tv_logo_rs.setText(base.result.getTwoPrizeList().get(i).getJoinTime());
                                }
                            }
                        }
                        if (base.result.getThrPrizeList() != null && base.result.getThrPrizeList().size() != 0) {
                            SpannableStringBuilder style = new SpannableStringBuilder("获得三等奖的小伙伴【" + base.result.getThrPrizeNum() + "】");
                            style.setSpan(new ForegroundColorSpan(CaiGoodsDetailActivity.this.getResources().getColor(R.color.text_01)), 10, 10 + base.result.getThrPrizeNum().length(), Spannable.SPAN_EXCLUSIVE_INCLUSIVE);
                            tv_3d.setText(style);
                            r3d.setVisibility(View.VISIBLE);
                            l3d.setVisibility(View.VISIBLE);
                            for (int i = 0; i < base.result.getThrPrizeList().size(); i++) {
                                if (i < 2) {
                                    Vd3[i].setVisibility(View.VISIBLE);
                                    ImageView iv_logo = (ImageView) Vd3[i].findViewById(R.id.iv_logo);
                                    Glide.with(CaiGoodsDetailActivity.this).load(base.result.getThrPrizeList().get(i).getUserImage()).error(R.mipmap.default_touxiang).transform(new CircleTransform(CaiGoodsDetailActivity.this)).into(iv_logo);
                                    TextView tv_logo = (TextView) Vd3[i].findViewById(R.id.tv_logo);
                                    TextView tv_logo1 = (TextView) Vd3[i].findViewById(R.id.tv_logo1);
                                    SpannableStringBuilder style1 = new SpannableStringBuilder("出价：￥" + format1.format(new BigDecimal(base.result.getThrPrizeList().get(i).getUserPrice())));
                                    style1.setSpan(new ForegroundColorSpan(CaiGoodsDetailActivity.this.getResources().getColor(R.color.text_01)), 3, 4 + format1.format(new BigDecimal(base.result.getThrPrizeList().get(i).getUserPrice())).length(), Spannable.SPAN_EXCLUSIVE_INCLUSIVE);
                                    tv_logo.setText(base.result.getThrPrizeList().get(i).getUserName());
                                    tv_logo1.setText(style1);
                                    TextView tv_logo_rs = (TextView) Vd3[i].findViewById(R.id.tv_logo_rs);
                                    tv_logo_rs.setText(base.result.getThrPrizeList().get(i).getJoinTime());
                                }
                            }
                        }
                    } else {
                        ToastUtils.showShortToast(CaiGoodsDetailActivity.this, base.error_msg);
                    }
                } else {
                    ToastUtils.showShortToast(CaiGoodsDetailActivity.this, "网络异常，数据加载失败");
                    LogUtil.Log(result.getResultMessage());
                }

            }
        });
    }


    public void onClick(View view) {

        switch (view.getId()) {
            case R.id.textView1:
                startActivity(new Intent(CaiGoodsDetailActivity.this, TabActivity.class));
                EventBus.getDefault().post(new AnyEventType("home"));
                finish();
//                fragment = new AddressDialogFragment();
//                fragment.show(getSupportFragmentManager(), "AddressDialogFragment");
                break;
            case R.id.tv_gd:
                Bundle bundle = new Bundle();
//                    bundle.putString("activityId", date.getOrdGrouponId() + "");
                bundle.putString("tag", "caijia");
                bundle.putString("pid", productId + "");
                BaseUtil.ToAcb(CaiGoodsDetailActivity.this, GoodsDetailActivity.class, bundle);
                break;
            case R.id.tv_good_xl:
                finish();
                break;
            case R.id.tv_kf:
                if (UserInfoUtils.isLogin()) {
                    HashMap<String, String> clientInfo = new HashMap<>();
                    clientInfo.put("name", Untool.getName());
                    clientInfo.put("avatar", Untool.getImage());
                    clientInfo.put("tel", Untool.getPhone());
                    clientInfo.put("comment", "产品Id:" + productId);
                    Intent intent = new MQIntentBuilder(this).setCustomizedId(Untool.getUid()).setClientInfo(clientInfo)
                            .build();
                    new Handler().postDelayed(new Runnable() {

                        public void run() {
                            HashMap<String, String> updateInfo = new HashMap<>();
                            updateInfo.put("name", Untool.getName());
                            updateInfo.put("avatar", Untool.getImage());
                            updateInfo.put("tel", Untool.getPhone());
                            updateInfo.put("comment", "产品Id:" + productId);
                            MQManager.getInstance(CaiGoodsDetailActivity.this).updateClientInfo(updateInfo, new OnClientInfoCallback() {
                                @Override
                                public void onSuccess() {

                                }

                                @Override
                                public void onFailure(int i, String s) {

                                }
                            });
                        }

                    }, 500);
                    startActivity(intent);
                } else {
                    Bundle bundle2 = new Bundle();
                    bundle2.putInt("tag", 99);
                    BaseUtil.ToAcb(CaiGoodsDetailActivity.this, PhoneLoginActivity.class, bundle2);//没有登录跳转自登录界面
                }
                break;
            case R.id.btn_offirm:
                String skuid = skuPresenter.forValidSKuId();
                presenter.confirmOrder("3", activityId, null, 1, productId, skuid);
//                Bundle b = new Bundle();
//                b.putString("activityId", activityId);
//                b.putString("pid", productId);
//                b.putString("attend", "");
//                b.putString("num", "1");
//                b.putString("skuLinkId", skuid);
//                b.putString("source", "3");
//                BaseUtil.ToAcb(CaiGoodsDetailActivity.this, ConfirmOrderActivity.class, b);
//                skupopupWindow.dismiss();
                break;

            case R.id.tv_dz:
                if (UserInfoUtils.isLogin()) {
                    initializeSkuPopwindow();
                    skuPresenter.loadSku(productId);
                } else {
                    Intent intent = new Intent(CaiGoodsDetailActivity.this, PhoneLoginActivity.class);
                    intent.putExtra("tag", 99);
                    startActivityForResult(intent, AppConfig.LOGIN);

                }

////                b.putString("skuLinkId",s);
//                b.putString("source","3");
//                Message msg=new Message();
//                msg.setData(b);
//                AddressDialogFragment.mhandler1.sendMessage(msg);
//                fragment = new AddressDialogFragment();
//                fragment.show(getSupportFragmentManager(), "AddressDialogFragment");
//                activityid = getIntent().getStringExtra("activityId");
//                attendId = getIntent().getStringExtra("attendId");
//                num = Integer.parseInt(getIntent().getStringExtra("num"));
//                pid = getIntent().getStringExtra("pid");
//                skuLinkId = getIntent().getStringExtra("skuLinkId");
//                source = getIntent().getStringExtra("source");


                break;
            case R.id.tv_gj:
                if (UserInfoUtils.isLogin()) {
                    initPopuptWindow();
                    backgroundAlpha(CaiGoodsDetailActivity.this, 0.5f);
                    getPopupWindow();
                    popupWindow.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
                    popupWindow.showAtLocation(rl_01, Gravity.BOTTOM, 0, BaseUtil.getBottomStatusHeight(this));
                } else {
                    Bundle bundle2 = new Bundle();
                    bundle2.putInt("tag", 99);
                    BaseUtil.ToAcb(CaiGoodsDetailActivity.this, PhoneLoginActivity.class, bundle2);//没有登录跳转自登录界面
                }
                break;
            case R.id.tv_cy:
                Bundle bundle2 = new Bundle();
                bundle2.putString("activityId", activityId + "");
                bundle2.putString("productId", productId + "");
                bundle2.putString("type", "1");
                BaseUtil.ToAcb(CaiGoodsDetailActivity.this, CanYuYongHuActivity.class, bundle2);
                break;
            case R.id.tv_1d_gd:
                Bundle bundle1 = new Bundle();
                bundle1.putString("activityId", activityId + "");
                bundle1.putString("productId", productId + "");
                bundle1.putString("type", "2");
                bundle1.putString("prize", "1");
                BaseUtil.ToAcb(CaiGoodsDetailActivity.this, CanYuYongHuActivity.class, bundle1);
                break;
            case R.id.tv_2d_gd:
                Bundle bundle12 = new Bundle();
                bundle12.putString("activityId", activityId + "");
                bundle12.putString("productId", productId + "");
                bundle12.putString("type", "2");
                bundle12.putString("prize", "2");
                BaseUtil.ToAcb(CaiGoodsDetailActivity.this, CanYuYongHuActivity.class, bundle12);
                break;
            case R.id.tv_3d_gd:
                Bundle bundle13 = new Bundle();
                bundle13.putString("activityId", activityId + "");
                bundle13.putString("productId", productId + "");
                bundle13.putString("type", "2");
                bundle13.putString("prize", "3");
                BaseUtil.ToAcb(CaiGoodsDetailActivity.this, CanYuYongHuActivity.class, bundle13);
                break;
            case R.id.iv_fenxiang:
                backgroundAlpha(CaiGoodsDetailActivity.this, 0.5f);
                getPopupWindows();
                // 这里是位置显示方式,在屏幕的左侧
                popupWindow1.showAtLocation(view, Gravity.BOTTOM, 0, 0);
                break;
            default:
                break;
        }
    }

    Handler handler = new Handler();
    Runnable runnable = new Runnable() {
        @Override
        public void run() {
//            date = GrtJs(date);
            try {
                if (hour != 0 || min != 0 || s != 0) {
                    s--;
                    if (s < 0) {
                        min--;
                        s = 59;
                        if (min < 0) {
                            min = 59;
                            hour--;
                            if (hour < 0) {
                                // 倒计时结束
                                hour = 0;
                                date.setIsStart("2");
//                                SetView(date);
                                tvjs.setVisibility(View.VISIBLE);
                                tvtime.setVisibility(View.GONE);
                                tv_s.setText("已结束");
                                tv_s.setTextColor(Color.parseColor("#FF464E"));
                                tv_f.setVisibility(View.GONE);
                                tv_m.setVisibility(View.GONE);
                                handler.removeCallbacksAndMessages(null);
//                                GroupHomeApiGet();
//                                ReadyJoinGet();
                                return;
                            }
                        }
                    }
                } else {
                    tvjs.setVisibility(View.VISIBLE);
                    tvtime.setVisibility(View.GONE);
                    tv_s.setText("已结束");
                    tv_s.setTextColor(Color.parseColor("#FF464E"));
                    tv_f.setVisibility(View.GONE);
                    tv_m.setVisibility(View.GONE);
                    handler.removeCallbacksAndMessages(null);
//                    date.setIsStart("2");
//                    SetView(date);
                    return;
                }
//                if (BaseUtil.Bl(hour).length() > 2) {
//                    tvtime.setText(" " + BaseUtil.Bl(hour) + "         " + BaseUtil.Bl(min) + "         " + BaseUtil.Bl(s));
                tv_s.setText(BaseUtil.Bl(hour));
                tv_f.setText(BaseUtil.Bl(min));
                tv_m.setText(BaseUtil.Bl(s));
//                } else {
//                    tvtime.setText("  " + BaseUtil.Bl(hour) + "         " + BaseUtil.Bl(min) + "         " + BaseUtil.Bl(s));
//                }
                handler.postDelayed(this, 1000);
            } catch (Exception e) {
            }


        }
    };

    long hour;
    long min;
    long s;

    private void SetView(ReadyJoinBean date) {

        String minn = null;
        String max = null;
        String real = null;
        SpannableStringBuilder style = null;
        try {
            style = new SpannableStringBuilder("最终价格：" + date.getRealPrice());
            style.setSpan(new ForegroundColorSpan(this.getResources().getColor(R.color.text_01)), 5, style.length(), Spannable.SPAN_EXCLUSIVE_INCLUSIVE);
        } catch (Exception e) {
        }
        if (date.getIsStart().equals("1")) {
            //进行中
            Top(R.layout.activity_c_goods_detail_top_ing, new SpannableStringBuilder(date.getMinPrice() + "-" + date.getMaxPrice()));
            if (date.getIsJoin().equals("1")) {
                //已参与
                Bottom(R.layout.activity_c_goods_detail_bootom_huodongyc);
                List(R.layout.activity_c_goods_detail_cy, 1);
            } else {
                //未参与
                Bottom(R.layout.activity_c_goods_detail_bootom_huodongwc);
                List(R.layout.activity_c_goods_detail_tz, 2);
            }
        } else {
            //已结束
            Top(R.layout.activity_c_goods_detail_top_js, style);
            if (date.getIsJoin().equals("1")) {
                //已参与
                if (date.getIsPublic().equals("1")) {
                    //已开奖
                    List(R.layout.activity_c_goods_detail_jl, 3);
                    if (date.getIsWin().equals("1")) {
                        //得奖
                        if (date.getPrize().equals("1")) {
                            //一等奖
                            Bottom(R.layout.activity_c_goods_detail_bootom_huodongjy);
                        } else {
                            //二三等
                            if (!date.getIsRecCoupon().equals("1")) {
                                String str = "三";
                                if (date.getPrize().equals("2")) {
                                    str = "二";
                                }
                                Bottom(R.layout.activity_c_goods_detail_bootom_huodongjw);
                                tvbtn1.setText("获得" + str + "等奖，奖品发放中");
                            } else {
                                Bottom(R.layout.activity_c_goods_detail_bootom_huodongjw);
                                tvbtn1.setText("恭喜您获得" + date.getCouponPrice() +
                                        "元抵用卷" +
                                        "\n" +
                                        "点击马上购买");
                            }
                        }
                    } else {
                        //未得奖
                        Bottom(R.layout.activity_c_goods_detail_bootom_huodongjwc);
//                        tvbtn1.setText("恭喜您获得" + date.getCouponPrice() +
//                                "元" +
//                                "\n" +
//                                "点击马上购买");
                    }
                } else {
                    //未开奖
                    Top(R.layout.activity_c_goods_detail_top_kj, null);
                    Bottom(R.layout.activity_c_goods_detail_bootom_huodongwk);
                    List(R.layout.activity_c_goods_detail_yj, 4);
                }
            } else {
                //未参与

                if (date.getIsPublic().equals("1")) {
                    //已开奖
                    if (tag.equals("0")) {
                        Bottom(R.layout.activity_c_goods_detail_bootom_huodongwc);
                        List(R.layout.activity_c_goods_detail_tz, 2);
                    } else {
                        List(R.layout.activity_c_goods_detail_jl, 3);
                        Bottom(R.layout.activity_c_goods_detail_bootom_huodongjwc);
                    }
                } else {
                    //未开奖
                    if (tag.equals("0")) {
                        List(R.layout.activity_c_goods_detail_tz, 2);
                        Bottom(R.layout.activity_c_goods_detail_bootom_huodongwk);
                        Top(R.layout.activity_c_goods_detail_top_kj, null);
                    } else {
                        List(R.layout.activity_c_goods_detail_yj, 4);
                        Bottom(R.layout.activity_c_goods_detail_bootom_huodongwk);
                        Top(R.layout.activity_c_goods_detail_top_kj, null);
                    }

                }
            }
        }
        if (date != null) {
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            try {
                Date parse1 = dateFormat.parse(date.getEndTime());
                Date parse = dateFormat.parse(date.getNowTime());
                long diff = parse1.getTime() - parse.getTime();
                long day = diff / (24 * 60 * 60 * 1000);
                hour = (diff - day * (1000 * 60 * 60 * 24)) / (1000 * 60 * 60) + (day * 24);
//                hour = 0;
                long hours = (diff - day * (1000 * 60 * 60 * 24)) / (1000 * 60 * 60);
                min = ((diff / (60 * 1000)) - day * 24 * 60 - hours * 60);
//                min = 0;
                s = (diff / 1000 - day * 24 * 60 * 60 - hours * 60 * 60 - min * 60);
//                s = 5;
                if (hour != 0 || min != 0 || s != 0) {
                    s--;
                    if (s < 0) {
                        min--;
                        s = 59;
                        if (min < 0) {
                            min = 59;
                            hour--;
                            if (hour < 0) {
                                // 倒计时结束
                                hour = 0;
                            }
                        }
                    }
                }
                tv_s.setText(BaseUtil.Bl(hour));
                tv_f.setText(BaseUtil.Bl(min));
                tv_m.setText(BaseUtil.Bl(s));
            } catch (Exception e) {
            }
        }
        wbv.loadUrl(Constant.BASEURL + "getProductInfoView.do?id=" + productId
        );
        WebSettings webSettings = wbv.getSettings();
        webSettings.setJavaScriptEnabled(true); // 支持js
        webSettings.setDefaultTextEncodingName("UTF-8");
//        wbv.setScrollBarStyle(1); // 滚动风格
    }

    PopupWindow popupWindow;

    /**
     * 创建PopupWindow
     */
    protected void initPopuptWindow() {
        // TODO Auto-generated method stub
        // 获取自定义布局文件activity_popupwindow_left.xml的视图
        View popupWindow_view = this.getLayoutInflater().inflate(
                R.layout.dialog_bao_jia, null, false);

        // 创建PopupWindow实例,200,LayoutParams.MATCH_PARENT分别是宽度和高度
        popupWindow = new PopupWindow(popupWindow_view,
                ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT, true);
        // 设置动画效果
        popupWindow.setAnimationStyle(R.style.PopupAnimation);
        // 点击其他地方消失
        // 实例化一个ColorDrawable颜色为半透明
        ColorDrawable dw = new ColorDrawable(0x00000000);
        // 设置SelectPicPopupWindow弹出窗体的背景
        popupWindow.setBackgroundDrawable(dw);
        backgroundAlpha(this, 0.5f);// 0.0-1.0
        popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {

            @Override
            public void onDismiss() {
                // TODO Auto-generated method stub
                backgroundAlpha(CaiGoodsDetailActivity.this, 1f);
            }
        });
        final EditText editText1 = (EditText) popupWindow_view.findViewById(R.id.editText1);
        final EditText editText2 = (EditText) popupWindow_view.findViewById(R.id.editText2);
        ImageView iv_back = (ImageView) popupWindow_view.findViewById(R.id.iv_back);
        iv_back.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                popupWindow.dismiss();
                popupWindow = null;
            }
        });
        ImageView iv_tj = (ImageView) popupWindow_view.findViewById(R.id.iv_tj);
        iv_tj.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                if (((new BigDecimal(date.getMinPrice())).compareTo(new BigDecimal(editText1.getText() + "." + editText2.getText())) != 1) && ((new BigDecimal(date.getMaxPrice())).compareTo(new BigDecimal(editText1.getText() + "." + editText2.getText())) != -1)) {
                    AbRequestParams params = new AbRequestParams();
                    params.put("activityId", activityId);
                    params.put("userId", Untool.getUid());
                    params.put("price", editText1.getText() + "." + editText2.getText());
                    LogUtil.Log(Constant.guessPriceApi + "?" + params.getParamString());
                    AbHttpUtil.getInstance(CaiGoodsDetailActivity.this).get(Constant.guessPriceApi, params, new AbStringHttpResponseListener() {

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
                            ToastUtils.showShortToast(CaiGoodsDetailActivity.this, "报价失败!");
                        }

                        @Override
                        public void onSuccess(int statusCode, String content) {
                            LogUtil.Log(content);
                            AbResult result = new AbResult(content);
                            if (result.getResultCode() == AbResult.RESULT_OK) {
                                Gson gson = new Gson();
                                Type type = new TypeToken<BaseModel<UserJoinInfoBean>>() {
                                }.getType();
                                BaseModel<UserJoinInfoBean> base = gson.fromJson(content, type);
                                if (base.result != null) {
                                    if (base.result.getIsGuess() == 1) {
                                        ReadyJoinGet();
                                        ToastUtils.showShortToast(CaiGoodsDetailActivity.this, "报价成功！");
                                    } else {
                                        ToastUtils.showShortToast(CaiGoodsDetailActivity.this, "报价失败！");
                                    }
                                    popupWindow.dismiss();
                                    popupWindow = null;
                                } else {
                                    ToastUtils.showShortToast(CaiGoodsDetailActivity.this, "报价失败！");
                                }
                            } else {

                                ToastUtils.showShortToast(CaiGoodsDetailActivity.this, "网络异常，数据加载失败");
                                LogUtil.Log(result.getResultMessage());
                            }

                        }
                    });
                } else {
                    ToastUtils.showShortToast(CaiGoodsDetailActivity.this, "你的报价超出商品价格区间!");
                }
            }
        });

    }


    /**
     * 创建PopupWindow
     */
    protected void initPopuptWindow2() {
        // TODO Auto-generated method stub
        // 获取自定义布局文件activity_popupwindow_left.xml的视图
        View popupWindow_view = this.getLayoutInflater().inflate(
                R.layout.dialog_cjg, null, false);

        // 创建PopupWindow实例,200,LayoutParams.MATCH_PARENT分别是宽度和高度
        popupWindow = new PopupWindow(popupWindow_view,
                ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT, true);
        // 设置动画效果
        popupWindow.setAnimationStyle(R.style.PopupAnimation);
        // 点击其他地方消失
        // 实例化一个ColorDrawable颜色为半透明
        ColorDrawable dw = new ColorDrawable(0x00000000);
        // 设置SelectPicPopupWindow弹出窗体的背景
        popupWindow.setBackgroundDrawable(dw);
        backgroundAlpha(this, 0.5f);// 0.0-1.0
        popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {

            @Override
            public void onDismiss() {
                // TODO Auto-generated method stub
                backgroundAlpha(CaiGoodsDetailActivity.this, 1f);
            }
        });
        final TextView tv_yhj = (TextView) popupWindow_view.findViewById(R.id.tv_yhj);
        tv_yhj.setText("恭喜您获得" + date.getCouponPrice() + "元抵用卷");
        ImageView iv_back = (ImageView) popupWindow_view.findViewById(R.id.iv_back);
        iv_back.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                popupWindow.dismiss();
                popupWindow = null;
            }
        });
        ImageView iv_tj = (ImageView) popupWindow_view.findViewById(R.id.iv_yhj);
        iv_tj.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                Bundle bundle = new Bundle();
//                bundle.putString("activityId", date.getOrdGrouponId() + "");
                bundle.putString("tag", "caijia");
                bundle.putString("pid", productId + "");
                BaseUtil.ToAcb(CaiGoodsDetailActivity.this, GoodsDetailActivity.class, bundle);
                date.setIsAlert("1");
                popupWindow.dismiss();
                popupWindow = null;
            }
        });

    }

    /**
     * 设置添加屏幕的背景透明度
     *
     * @param bgAlpha
     */
    public void backgroundAlpha(Activity context, float bgAlpha) {
        WindowManager.LayoutParams lp = context.getWindow().getAttributes();
        lp.alpha = bgAlpha;
        context.getWindow()
                .addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
        context.getWindow().setAttributes(lp);
    }

    /***
     * 获取PopupWindow实例
     */
    private void getPopupWindow() {
        if (null != popupWindow) {
            popupWindow.dismiss();
            return;
        } else {
            initPopuptWindow();
        }
    }


    /***
     * 获取PopupWindow实例
     */
    private void getPopupWindow2() {
        if (null != popupWindow) {
            popupWindow.dismiss();
            return;
        } else {
            initPopuptWindow2();
        }
    }

    PopupWindow popupWindow1;


    /***
     * 获取PopupWindow实例
     */
    private void getPopupWindows() {
        if (null != popupWindow1) {
            popupWindow1.dismiss();
            return;
        } else {
            initPopuptWindows();
        }
    }

    /**
     * 创建PopupWindow
     */
    protected void initPopuptWindows() {
        // TODO Auto-generated method stub
        // 获取自定义布局文件activity_popupwindow_left.xml的视图
        View popupWindow_view = getLayoutInflater().inflate(
                R.layout.pop_fx, null, false);

        // 创建PopupWindow实例,200,LayoutParams.MATCH_PARENT分别是宽度和高度
        popupWindow1 = new PopupWindow(popupWindow_view,
                PercentRelativeLayout.LayoutParams.MATCH_PARENT, PercentRelativeLayout.LayoutParams.WRAP_CONTENT, true);
        // 设置动画效果
        popupWindow1.setAnimationStyle(R.style.AnimBottom);
        // 点击其他地方消失
        // 实例化一个ColorDrawable颜色为半透明
        ColorDrawable dw = new ColorDrawable(0x00000000);
        // 设置SelectPicPopupWindow弹出窗体的背景
        popupWindow1.setBackgroundDrawable(dw);
        backgroundAlpha(CaiGoodsDetailActivity.this, 0.5f);// 0.0-1.0
        popupWindow1.setOnDismissListener(new PopupWindow.OnDismissListener() {

            @Override
            public void onDismiss() {
                // TODO Auto-generated method stub
                backgroundAlpha(CaiGoodsDetailActivity.this, 1f);
            }
        });
        ImageView iv_wb = (ImageView) popupWindow_view.findViewById(R.id.iv_wb);
        iv_wb.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                // System.out.println("++++++++++++++++");
                UMImage urlImage = new UMImage(CaiGoodsDetailActivity.this,
                        share.getImage());
                new ShareAction(CaiGoodsDetailActivity.this)
                        .setPlatform(SHARE_MEDIA.SINA)
                        .withText(share.getContent()).withTitle(share.getTitle()).withMedia(urlImage)
                        .withTargetUrl(share.getUrl())
                        .setCallback(umShareListener).share();

            }
        });
        ImageView iv_wx = (ImageView) popupWindow_view.findViewById(R.id.iv_wx);
        iv_wx.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                // System.out.println("++++++++++++++++");
                UMImage urlImage = new UMImage(CaiGoodsDetailActivity.this,
                        share.getImage());
                new ShareAction(CaiGoodsDetailActivity.this)
                        .setPlatform(SHARE_MEDIA.WEIXIN)
                        .withText(share.getContent()).withTitle(share.getTitle()).withMedia(urlImage)
                        .withTargetUrl(share.getUrl())
                        .setCallback(umShareListener).share();

            }
        });
        ImageView iv_pyq = (ImageView) popupWindow_view
                .findViewById(R.id.iv_pyq);
        iv_pyq.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                // System.out.println("++++++++++++++++");
                UMImage urlImage = new UMImage(CaiGoodsDetailActivity.this,
                        share.getImage());
                new ShareAction(CaiGoodsDetailActivity.this)
                        .setPlatform(SHARE_MEDIA.WEIXIN_CIRCLE)
                        .withText(share.getContent()).withTitle(share.getTitle()).withMedia(urlImage)
                        .withTargetUrl(share.getUrl())
                        .setCallback(umShareListener).share();

            }
        });
        // popupWindow.setTouchable(true);
        popupWindow_view.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                // TODO Auto-generated method stub
                if (popupWindow1 != null && popupWindow1.isShowing()) {
                    popupWindow1.dismiss();
                    popupWindow1 = null;
                }
                return false;
            }
        });
    }

    private UMShareListener umShareListener = new UMShareListener() {
        @Override
        public void onResult(SHARE_MEDIA platform) {
            com.umeng.socialize.utils.Log.d("plat", "platform" + platform);

            Toast.makeText(CaiGoodsDetailActivity.this, platform + " 分享成功啦", Toast.LENGTH_SHORT).show();

        }

        @Override
        public void onError(SHARE_MEDIA platform, Throwable t) {
            Toast.makeText(CaiGoodsDetailActivity.this, platform + " 分享失败啦", Toast.LENGTH_SHORT).show();
            if (t != null) {
                com.umeng.socialize.utils.Log.d("throw", "throw:" + t.getMessage());
            }
        }

        @Override
        public void onCancel(SHARE_MEDIA platform) {
            Toast.makeText(CaiGoodsDetailActivity.this, platform + " 分享取消了", Toast.LENGTH_SHORT).show();
        }
    };


    ProgressDialog progressDialog;

    @Override
    public void initView() {

    }

    @Override
    public void showProgressDialog(String msg) {
        if (progressDialog == null) {
            progressDialog = new ProgressDialog(CaiGoodsDetailActivity.this);
        }
        progressDialog.setMessage(msg);
        progressDialog.setCancelable(false);
        progressDialog.show();
    }

    @Override
    public void removeDialog() {
        if (progressDialog != null && progressDialog.isShowing()) {
            progressDialog.dismiss();
        }
    }


    @Override
    public void toastMessage(String message) {
        ToastUtils.showShortToast(this, message);
    }

    @Override
    public void orderConfirm() {
        String skuid = skuPresenter.forValidSKuId();
        Bundle b = new Bundle();
        b.putString("activityId", activityId);
        b.putString("pid", productId);
        b.putString("attend", "");
        b.putString("num", "1");
        b.putString("skuLinkId", skuid);
        b.putString("source", "3");
        BaseUtil.ToAcb(CaiGoodsDetailActivity.this, ConfirmOrderActivity.class, b);
        skupopupWindow.dismiss();
    }


    @Override
    public void showSkuPopuWindow() {

        if (skupopupWindow == null) {
            initSkuPopuWindow();
        } else {
            updateSkuPopuWindow();
        }
        WindowManager.LayoutParams lp = getWindow().getAttributes();
        lp.alpha = 0.4f;
        getWindow().setAttributes(lp);
        setSkuPopPrice();
        setSkuOnClickListener();
        if (skuPresenter.validSKu != null && skuPresenter.validSKu.size() == 1) {
            //如果sku只有一个直接提交订单
            skuColorAdapter.selectPosition = 0;
            skuColorAdapter.notifyDataSetChanged();
            if (skuPresenter.SKU_MODEL == skuPresenter.SKU_MODEL_TWO) {
                formatAdapter.selectPosition = 0;
                formatAdapter.notifyDataSetChanged();
            }

        } else if (skuPresenter.validSKu != null && skuPresenter.validSKu.size() > 1
                ) {
            if (skuPresenter.SKU_MODEL == skuPresenter.SKU_MODEL_SINGLE) {
                skuColorAdapter.selectPosition = 0;
                skuColorAdapter.notifyDataSetChanged();
            } else {
                formatAdapter.selectPosition = 0;
                formatAdapter.notifyDataSetChanged();
            }

        }
        skuPresenter.setBtn_offirm(btn_offirm, iv_iamge, CaiGoodsDetailActivity.this);
        skupopupWindow.showAtLocation(rl_01, Gravity.BOTTOM, 0, 0);


    }

    @Override
    public void setSkuPopPrice() {
        try {
            tv_price.setText("￥" + date.getRealPrice());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void initializeSkuPopwindow() {
        skuPresenter.num = 1;
        skuPresenter.skuColor = null;
        skuPresenter.skuFormat = null;
        if (skuColorAdapter != null) {
            skuColorAdapter.selectPosition = -1;
        }
        if (formatAdapter != null) {
            formatAdapter.selectPosition = -1;
        }
    }

    @Override
    public void setSkuOnClickListener() {

    }

    @Override
    public void initSkuPopView(View view) {
        try {
            iv_iamge = (ImageView) view.findViewById(R.id.iv_iamge);
            tv_pName = (TextView) view.findViewById(R.id.tv_pName);
            tv_price = (TextView) view.findViewById(R.id.tv_price);
            tv_colortitle = (TextView) view.findViewById(R.id.tv_colortitle);
            tv_formattitle = (TextView) view.findViewById(R.id.tv_formattitle);
            gv_color = (GridViewForScrollView) view.findViewById(R.id.gv_color);
            gv_color.setSelector(new ColorDrawable(Color.TRANSPARENT));
            gv_format = (GridViewForScrollView) view.findViewById(R.id.gv_format);
            gv_format.setSelector(new ColorDrawable(Color.TRANSPARENT));
            tv_skutips = (TextView) view.findViewById(R.id.tv_skutips);
            add = view.findViewById(R.id.add);
            reduce = view.findViewById(R.id.reduce);
            tv_num = (TextView) view.findViewById(R.id.tv_num);
            btn_offirm = (Button) view.findViewById(R.id.btn_offirm);
            //提示
            if (skuPresenter.SKU_MODEL == skuPresenter.SKU_MODEL_SINGLE) {
                tv_skutips.setText("请选择" + skuPresenter.skuList.get(0).skuTitle);
                tv_colortitle.setText(skuPresenter.skuList.get(0).skuTitle);
            } else {
                tv_formattitle.setVisibility(View.VISIBLE);
                gv_format.setVisibility(View.VISIBLE);
                tv_skutips.setText("请选择" + skuPresenter.skuList.get(0).skuTitle + "和" + skuPresenter.skuList.get(1).skuTitle);
                tv_colortitle.setText(skuPresenter.skuList.get(0).skuTitle);
                tv_formattitle.setText(skuPresenter.skuList.get(1).skuTitle);

            }
            tv_num.setText(skuPresenter.num + "");
            if (skuPresenter.validSKu != null && !skuPresenter.validSKu.isEmpty()) {
                Glide.with(this).load(skuPresenter.validSKu.get(0).skuImg).placeholder(R.mipmap.default_load).error(R.mipmap.default_load).into(iv_iamge);
            }

            LogUtil.Log("skucolorMAxsize:======" + skuPresenter.skucolorMAxsize);
            int colornum = skuPresenter.getNumColumns(skuPresenter.skucolorMAxsize);
            gv_color.setNumColumns(colornum);
            gv_color.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    LogUtil.Log("gv_color:==========" + position);
                    LogUtil.Log("gv_colorclickable:==========" + skuPresenter.skuColorValue.get(position).clickable);
                    skuColorAdapter.selectPosition = position;
                    skuPresenter.skuColor = skuPresenter.skuColorValue.get(position).optionValue;
                    skuColorAdapter.notifyDataSetChanged();
                    if (skuPresenter.SKU_MODEL == skuPresenter.SKU_MODEL_TWO) {
                        formatAdapter.notifyDataSetChanged();
                    }
                    skuPresenter.setBtn_offirm(btn_offirm, iv_iamge, CaiGoodsDetailActivity.this);
                }


            });

            if (skuPresenter.SKU_MODEL == skuPresenter.SKU_MODEL_TWO) {
                LogUtil.Log("skuformatMAxsize:======" + skuPresenter.skuformatMAxsize);
                int formatnum = skuPresenter.getNumColumns(skuPresenter.skuformatMAxsize);
                gv_format.setNumColumns(formatnum);
                gv_format.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        LogUtil.Log("gv_format:==========" + position);
                        LogUtil.Log("gv_formatclickable:==========" + skuPresenter.skuFormatValue.get(position).clickable);
                        if (skuPresenter.skuFormatValue.get(position).clickable == 0) {
                            formatAdapter.selectPosition = position;
                            skuPresenter.skuFormat = skuPresenter.skuFormatValue.get(position).optionValue;
                            formatAdapter.notifyDataSetChanged();
                            skuColorAdapter.notifyDataSetChanged();
                            skuPresenter.setBtn_offirm(btn_offirm, iv_iamge, CaiGoodsDetailActivity.this);
                        }
                    }

                });
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void updateSkuPopuWindow() {
        try {
            tv_num.setText(skuPresenter.num + "");
            skuColorAdapter.notifyDataSetChanged();
            if (skuPresenter.SKU_MODEL == skuPresenter.SKU_MODEL_TWO) {
                formatAdapter.notifyDataSetChanged();
            }
            skupopupWindow.update();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void initSkuPopuWindow() {
        try {
            View view = getLayoutInflater().inflate(
                    R.layout.activity_goods_detail_sku_pop, null);
            skupopupWindow = new PopupWindow(view,
                    WindowManager.LayoutParams.MATCH_PARENT,
                    WindowManager.LayoutParams.WRAP_CONTENT);
            skupopupWindow.setFocusable(true);
            // 实例化一个ColorDrawable颜色为半透明
            ColorDrawable dw = new ColorDrawable(0x00000000);
            skupopupWindow.setBackgroundDrawable(dw);
            // 设置popWindow的显示和消失动画
            skupopupWindow.setAnimationStyle(R.style.AnimBottom);

            skupopupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {

                @Override
                public void onDismiss() {
                    WindowManager.LayoutParams lp = getWindow().getAttributes();
                    lp.alpha = 1f;
                    getWindow().setAttributes(lp);

                }
            });
            initSkuPopView(view);
            skuColorAdapter = new SkuColorAdapter(this, skuPresenter.skuColorValue);
            gv_color.setAdapter(skuColorAdapter);
            if (skuPresenter.SKU_MODEL == skuPresenter.SKU_MODEL_TWO) {
                formatAdapter = new SkuFormatAdapter(this, skuPresenter.skuFormatValue, skuPresenter);
                gv_format.setAdapter(formatAdapter);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
