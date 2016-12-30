package com.qunyu.taoduoduo.activity;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AlertDialog;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.TextUtils;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.alipay.sdk.app.PayTask;
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
import com.qunyu.taoduoduo.api.PayOrderApi;
import com.qunyu.taoduoduo.base.BaseActivity;
import com.qunyu.taoduoduo.base.BaseModel;
import com.qunyu.taoduoduo.base.BaseUtil;
import com.qunyu.taoduoduo.bean.AddOrderByPurchaseBean;
import com.qunyu.taoduoduo.bean.OrderDetailInfoBean;
import com.qunyu.taoduoduo.global.AnyEventType;
import com.qunyu.taoduoduo.global.Constant;
import com.qunyu.taoduoduo.global.Untool;
import com.qunyu.taoduoduo.presenter.QueryPayStatusPresenter;
import com.qunyu.taoduoduo.utils.LogUtil;
import com.qunyu.taoduoduo.utils.PayResult;
import com.qunyu.taoduoduo.utils.StringUtils;
import com.qunyu.taoduoduo.utils.ToastUtils;
import com.qunyu.taoduoduo.utils.UserInfoUtils;
import com.qunyu.taoduoduo.wxapi.WXPayEntryActivity;
import com.tencent.mm.sdk.modelpay.PayReq;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.WXAPIFactory;
import com.umeng.analytics.MobclickAgent;
import com.zhy.android.percent.support.PercentRelativeLayout;

import org.greenrobot.eventbus.EventBus;

import java.lang.reflect.Type;
import java.text.DecimalFormat;
import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.qunyu.taoduoduo.global.Constant.orderDetailApi;

public class OrderDetailActivity extends BaseActivity {
    String oid = null;
    String type = "1";
    @BindView(R.id.iv_top)
    ImageView ivTop;
    @BindView(R.id.tv_u_name)
    TextView tvUName;
    @BindView(R.id.tv_u_dz)
    TextView tvUDz;
    @BindView(R.id.detail)
    PercentRelativeLayout detail;
    @BindView(R.id.info)
    PercentRelativeLayout info;
    @BindView(R.id.zf)
    PercentRelativeLayout zf;
    @BindView(R.id.bottom)
    PercentRelativeLayout bottom;
    @BindView(R.id.tv_zt)
    TextView tvZt;
    @BindView(R.id.iv_logo)
    ImageView ivLogo;
    @BindView(R.id.tv_name)
    TextView tvName;
    @BindView(R.id.tv_jg)
    TextView tvJg;
    @BindView(R.id.tv_btn2)
    TextView tvBtn2;
    @BindView(R.id.tv_btn1)
    TextView tvBtn1;
    @BindView(R.id.tv_ddbh)
    TextView tvDdbh;
    @BindView(R.id.tv_zffs)
    TextView tvZffs;
    @BindView(R.id.tv_xdsj)
    TextView tvXdsj;
    @BindView(R.id.tv_ctsj)
    TextView tvCtsj;
    @BindView(R.id.tv_fhsj)
    TextView tvFhsj;
    @BindView(R.id.tv_cjsj)
    TextView tvCjsj;
    @BindView(R.id.tv_kdfs)
    TextView tvKdfs;
    @BindView(R.id.tv_ydbh)
    TextView tvYdbh;
    @BindView(R.id.iv_wx3)
    ImageView ivWx3;
    @BindView(R.id.wx)
    PercentRelativeLayout wx;
    @BindView(R.id.iv_zfb3)
    ImageView ivZfb3;
    @BindView(R.id.zfb)
    PercentRelativeLayout zfb;
    @BindView(R.id.iv_qq3)
    ImageView ivQq3;
    @BindView(R.id.qq)
    PercentRelativeLayout qq;
    @BindView(R.id.tv_ckwl)
    TextView tvCkwl;
    @BindView(R.id.tv_qdsh)
    TextView tvQdsh;
    @BindView(R.id.tv_shsqz)
    TextView tv_shsqz;
    String poy = null;
    OrderDetailInfoBean date;
    @BindView(R.id.tv_shsqz1)
    TextView tv_shsqz1;
    String payMethod = "1";//默认支付宝
    @BindView(R.id.tv_btn3)
    TextView tvBtn3;
    ImageView kefu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        baseSetContentView(R.layout.activity_order_detail);
        ButterKnife.bind(this);
        Bundle bundle = this.getIntent().getExtras();
        oid = bundle.getString("oid");
        type = bundle.getString("type");
        LogUtil.Log("type" + type);
        qq.setVisibility(View.GONE);
        kefu = (ImageView) findViewById(R.id.iv_fenxiang);
        kefu.setImageResource(R.mipmap.kefu_icon);
        kefu.setVisibility(View.VISIBLE);
        kefu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (UserInfoUtils.isLogin()) {
                    HashMap<String, String> clientInfo = new HashMap<>();
                    clientInfo.put("name", Untool.getName());
                    clientInfo.put("avatar", Untool.getImage());
                    clientInfo.put("tel", Untool.getPhone());
                    clientInfo.put("comment", "订单编号:" + date.getOrderInfo().getOrderNo());
                    Intent intent = new MQIntentBuilder(OrderDetailActivity.this).setCustomizedId(Untool.getUid()).setClientInfo(clientInfo)
                            .build();
                    new Handler().postDelayed(new Runnable() {

                        public void run() {
                            HashMap<String, String> updateInfo = new HashMap<>();
                            updateInfo.put("name", Untool.getName());
                            updateInfo.put("avatar", Untool.getImage());
                            updateInfo.put("tel", Untool.getPhone());
                            updateInfo.put("comment", "订单编号:" + date.getOrderInfo().getOrderNo());
                            MQManager.getInstance(OrderDetailActivity.this).updateClientInfo(updateInfo, new OnClientInfoCallback() {
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
                    BaseUtil.ToAcb(OrderDetailActivity.this, PhoneLoginActivity.class, bundle2);//没有登录跳转自登录界面
                }
            }
        });
//        OrderdetailGet();
    }

    @Override
    protected void onResume() {
        OrderdetailGet();
        super.onResume();
        MobclickAgent.onResume(this);

    }

    public void onPause() {
        super.onPause();
        MobclickAgent.onPause(this);
    }

    private void OrderdetailGet() {
        AbRequestParams params = new AbRequestParams();
        params.put("oid", oid);
        LogUtil.Log(orderDetailApi + "?" + params.getParamString());
        AbHttpUtil.getInstance(this).get(Constant.orderDetailApi, params, new AbStringHttpResponseListener() {

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
                ToastUtils.showShortToast(OrderDetailActivity.this, "网络异常，数据加载失败");
                LogUtil.Log(error.getMessage());
            }

            @Override
            public void onSuccess(int statusCode, String content) {
                AbResult result = new AbResult(content);
                if (result.getResultCode() == AbResult.RESULT_OK) {
                    Gson gson = new Gson();
                    Type type1 = new TypeToken<BaseModel<OrderDetailInfoBean>>() {
                    }.getType();
                    BaseModel<OrderDetailInfoBean> base = gson.fromJson(content, type1);
                    if (base.result != null) {
                        Log.d(oid + "+", "onSuccess: " + content);
                        date = base.result;
                        source = date.getSource();
                        orderNo = date.getOrderInfo().getOrderNo();
                        attendId = date.getAttendId();
                        if (date.getSource().equals("5") || date.getSource().equals("7")) {
                            type = 555 + "";
                        } else {
//                            Log.d(TAG, "onItemClick: " + date.getId());
                            int i = 0;
                            if (date.getOrderStatus().equals("1")) {
                                //待付款
                                if (date.getIsCancel().equals("1")) {
                                    //取消
                                    i = 1;
                                } else {
                                    //正常
                                    i = 2;
                                }
                            } else if (date.getOrderStatus().equals("2")) {
                                //已付款
                                if (date.getIsSuccess().equals("0")) {
                                    //拼团中
                                    i = 3;
                                } else if (date.getIsSuccess().equals("1")) {
                                    //拼团成功
                                    i = 4;
                                } else if (date.getIsSuccess().equals("2")) {
                                    //拼团失败
                                    i = 41;
                                }
                            } else if (date.getOrderStatus().equals("3")) {
                                //已发货
                                i = 5;
                            } else {
                                //已完成
                                i = 6;
                            }
                            type = i + "";
                        }
                        SetView(base.result);
                        if (date.getRefundStatus().equals("0") || date.getRefundStatus().equals("5") || date.getRefundStatus().equals("6")) {

//
                        } else {
                            tvQdsh.setVisibility(View.GONE);
                            tv_shsqz1.setVisibility(View.VISIBLE);
                        }

                    } else {
                        ToastUtils.showShortToast(OrderDetailActivity.this, base.error_msg);
                    }
                } else {
                    ToastUtils.showShortToast(OrderDetailActivity.this, "网络异常，数据加载失败");
                    LogUtil.Log(result.getResultMessage());
                }

            }
        });

    }


    String zf1;

    private void SetView(OrderDetailInfoBean date) {
        tvBtn3.setVisibility(View.GONE);
        switch (type) {
            case "1":
                ivTop.setImageResource(R.mipmap.dd_od_jyqx);
                baseSetText("交易已取消");
                bottom.setVisibility(View.GONE);
                zf.setVisibility(View.GONE);
                tvCtsj.setVisibility(View.GONE);
                tvFhsj.setVisibility(View.GONE);
                tvCjsj.setVisibility(View.GONE);
                tvKdfs.setVisibility(View.GONE);
                tvYdbh.setVisibility(View.GONE);
                detail.removeAllViews();
                View vHead = View.inflate(OrderDetailActivity.this, R.layout.activity_order_detail_sp_zfqx, null);
                ivLogo = (ImageView) vHead.findViewById(R.id.iv_logo);
                tvName = (TextView) vHead.findViewById(R.id.tv_name);
                tvJg = (TextView) vHead.findViewById(R.id.tv_jg);
                tvZt = (TextView) vHead.findViewById(R.id.tv_zt);
                tvZt.setText("");
                detail.addView(vHead);
                break;
            case "2":
                ivTop.setImageResource(R.mipmap.dd_od_dfk);
                baseSetText("确定订单");
                poy = "1";
                tvCtsj.setVisibility(View.GONE);
                tvFhsj.setVisibility(View.GONE);
                tvCjsj.setVisibility(View.GONE);
                tvKdfs.setVisibility(View.GONE);
                tvYdbh.setVisibility(View.GONE);
                ivWx3.setVisibility(View.GONE);
                ivQq3.setVisibility(View.GONE);
                tvQdsh.setText("去支付");
                tvQdsh.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        payOrder();
                    }
                });
                tvCkwl.setText("取消订单");
                detail.removeAllViews();
                View vHead1 = View.inflate(OrderDetailActivity.this, R.layout.activity_order_detail_sp_zfqx, null);
                ivLogo = (ImageView) vHead1.findViewById(R.id.iv_logo);
                tvName = (TextView) vHead1.findViewById(R.id.tv_name);
                tvJg = (TextView) vHead1.findViewById(R.id.tv_jg);
                tvZt = (TextView) vHead1.findViewById(R.id.tv_zt);
                tvZt.setText("待支付");
                detail.addView(vHead1);
                break;
            case "3":
                ivTop.setImageResource(R.mipmap.dd_od_wcg);
                baseSetText("拼团中");
                bottom.setVisibility(View.GONE);
                zf.setVisibility(View.GONE);
                tvCtsj.setVisibility(View.GONE);
                tvFhsj.setVisibility(View.GONE);
                tvCjsj.setVisibility(View.GONE);
                tvKdfs.setVisibility(View.GONE);
                tvYdbh.setVisibility(View.GONE);
//                tvZt.setText("拼团中，差" + date.getOweNum() + "人");
                tvZt.setText("拼团中");
                tvBtn2.setVisibility(View.GONE);
                break;
            case "4":
                if (date.getOrderStatus().equals("2")) {
                    ivTop.setImageResource(R.mipmap.dd_od_dfh);
                    baseSetText("拼团成功");
                    tvFhsj.setVisibility(View.GONE);
                    tvCjsj.setVisibility(View.GONE);
                    tvKdfs.setVisibility(View.GONE);
                    tvYdbh.setVisibility(View.GONE);
                    bottom.setVisibility(View.GONE);
                    zf.setVisibility(View.GONE);
                    tvBtn2.setVisibility(View.GONE);
                    if (date.getSource().equals("4") || date.getSource().equals("3")) {
                        detail.removeAllViews();
                        View vHead2 = View.inflate(OrderDetailActivity.this, R.layout.activity_order_detail_sp_zfqx, null);
                        ivLogo = (ImageView) vHead2.findViewById(R.id.iv_logo);
                        tvName = (TextView) vHead2.findViewById(R.id.tv_name);
                        tvJg = (TextView) vHead2.findViewById(R.id.tv_jg);
                        tvZt = (TextView) vHead2.findViewById(R.id.tv_zt);
                        detail.addView(vHead2);
                    }
                    tvZt.setText("已成团，待发货");
                } else if (date.getOrderStatus().equals("3")) {
                    ivTop.setImageResource(R.mipmap.dd_od_dsh);
                    baseSetText("待收货");
                    tvFhsj.setVisibility(View.GONE);
                    tvCjsj.setVisibility(View.GONE);
                    tvKdfs.setVisibility(View.GONE);
                    tvYdbh.setVisibility(View.GONE);
                    zf.setVisibility(View.GONE);
                    if (date.getSource().equals("3")) {
                        detail.removeAllViews();
                        View vHead2 = View.inflate(OrderDetailActivity.this, R.layout.activity_order_detail_sp_zfqx, null);
                        ivLogo = (ImageView) vHead2.findViewById(R.id.iv_logo);
                        tvName = (TextView) vHead2.findViewById(R.id.tv_name);
                        tvJg = (TextView) vHead2.findViewById(R.id.tv_jg);
                        tvZt = (TextView) vHead2.findViewById(R.id.tv_zt);
                        detail.addView(vHead2);
                    }
                    if (date.getSource().equals("4")) {
                        tvBtn2.setVisibility(View.GONE);
                    }
                    tvBtn1.setText("申请退款");
                    if (date.getRefundStatus().equals("0") || date.getRefundStatus().equals("5") || date.getRefundStatus().equals("6")) {

//
                    } else {
                        tvZt.setText("申请售后中");
                        tvBtn1.setVisibility(View.GONE);
                        tv_shsqz.setVisibility(View.VISIBLE);
                        EventBus.getDefault().post(new AnyEventType(date.getOrderInfo().getOrderId() + ";xin"));
                    }
                    tvZt.setText("待收货");
                } else {
                    ivTop.setImageResource(R.mipmap.dd_od_jycg);
                    baseSetText("已签收");
                    tvCkwl.setVisibility(View.GONE);
                    zf.setVisibility(View.GONE);
                    tvBtn2.setVisibility(View.GONE);
                    if (date.getSource().equals("4")) {
                        detail.removeAllViews();
                        View vHead2 = View.inflate(OrderDetailActivity.this, R.layout.activity_order_detail_sp_zfqx, null);
                        ivLogo = (ImageView) vHead2.findViewById(R.id.iv_logo);
                        tvName = (TextView) vHead2.findViewById(R.id.tv_name);
                        tvJg = (TextView) vHead2.findViewById(R.id.tv_jg);
                        tvZt = (TextView) vHead2.findViewById(R.id.tv_zt);
                        detail.addView(vHead2);
                    }
                    tvZt.setText("已签收");
                    tvQdsh.setText("查看物流");
                    tvCkwl.setVisibility(View.GONE);
                }
                tvZffs.setVisibility(View.GONE);
                break;
            case "41":
                tvFhsj.setVisibility(View.GONE);
                tvCjsj.setVisibility(View.GONE);
                tvKdfs.setVisibility(View.GONE);
                tvYdbh.setVisibility(View.GONE);
                bottom.setVisibility(View.GONE);
                tvCtsj.setVisibility(View.GONE);
                zf.setVisibility(View.GONE);
                tvBtn2.setVisibility(View.GONE);
                if (!date.getRefPriStatus().equals("2")) {
                    ivTop.setImageResource(R.mipmap.dd_od_wcttkz);
                    baseSetText("拼团失败");
                    tvZt.setText("未成团，退款中");
                } else {
                    ivTop.setImageResource(R.mipmap.twcttk);
                    baseSetText("拼团失败");
                    tvZt.setText("未成团，已退款");
                }
                break;
            case "5":
                ivTop.setImageResource(R.mipmap.dd_od_dsh);
                baseSetText("待收货");
                tvFhsj.setVisibility(View.GONE);
                tvCjsj.setVisibility(View.GONE);
                tvKdfs.setVisibility(View.GONE);
                tvYdbh.setVisibility(View.GONE);
                zf.setVisibility(View.GONE);
                if (date.getSource().equals("3")) {
                    detail.removeAllViews();
                    View vHead2 = View.inflate(OrderDetailActivity.this, R.layout.activity_order_detail_sp_zfqx, null);
                    ivLogo = (ImageView) vHead2.findViewById(R.id.iv_logo);
                    tvName = (TextView) vHead2.findViewById(R.id.tv_name);
                    tvJg = (TextView) vHead2.findViewById(R.id.tv_jg);
                    tvZt = (TextView) vHead2.findViewById(R.id.tv_zt);
                    detail.addView(vHead2);
                }
                if (date.getSource().equals("4")) {
                    tvBtn2.setVisibility(View.GONE);
                }
                tvBtn1.setText("申请退款");
                if (date.getRefundStatus().equals("0") || date.getRefundStatus().equals("5") || date.getRefundStatus().equals("6")) {

//
                } else {
                    tvZt.setText("申请售后中");
                    tvBtn1.setVisibility(View.GONE);
                    tv_shsqz.setVisibility(View.VISIBLE);
                    EventBus.getDefault().post(new AnyEventType(date.getOrderInfo().getOrderId() + ";xin"));
                }
                tvZt.setText("待收货");
                break;
            case "555":
                switch (date.getOrderStatus()) {
                    case "1":
                        ivTop.setImageResource(R.mipmap.dd_od_dfk);
                        baseSetText("确定订单");
                        poy = "1";
                        tvCtsj.setVisibility(View.GONE);
                        tvFhsj.setVisibility(View.GONE);
                        tvCjsj.setVisibility(View.GONE);
                        tvKdfs.setVisibility(View.GONE);
                        tvYdbh.setVisibility(View.GONE);
                        ivZfb3.setVisibility(View.GONE);
                        ivQq3.setVisibility(View.GONE);
                        tvQdsh.setText("去支付");
                        tvQdsh.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                payOrder();
                            }
                        });
                        tvCkwl.setText("取消订单");
                        detail.removeAllViews();
                        View vHead11 = View.inflate(OrderDetailActivity.this, R.layout.activity_order_detail_sp_zfqx, null);
                        ivLogo = (ImageView) vHead11.findViewById(R.id.iv_logo);
                        tvName = (TextView) vHead11.findViewById(R.id.tv_name);
                        tvJg = (TextView) vHead11.findViewById(R.id.tv_jg);
                        tvZt = (TextView) vHead11.findViewById(R.id.tv_zt);
                        tvZt.setText("待支付");
                        detail.addView(vHead11);
                        break;
                    case "2":
                        ivTop.setImageResource(R.mipmap.dd_od_wcg);
                        baseSetText("拼团中");
                        bottom.setVisibility(View.GONE);
                        zf.setVisibility(View.GONE);
                        tvCtsj.setVisibility(View.GONE);
                        tvFhsj.setVisibility(View.GONE);
                        tvCjsj.setVisibility(View.GONE);
                        tvKdfs.setVisibility(View.GONE);
                        tvYdbh.setVisibility(View.GONE);
//                        tvZt.setText("拼团中，差" + date.getOweNum() + "人");
                        tvZt.setText("拼团中");
                        tvBtn2.setVisibility(View.GONE);
                        break;
                    case "3":
                        baseSetText("拼团失败");
                        if (!date.getSource().equals("7")) {
                            ivTop.setImageResource(R.mipmap.dd_od_wcttkz);
                            tvZt.setText("未成团，待退款");
                        } else {
                            ivTop.setImageResource(R.mipmap.twcttk);
                            tvZt.setText("未成团，已退款");
                        }
                        tvFhsj.setVisibility(View.GONE);
                        tvCjsj.setVisibility(View.GONE);
                        tvKdfs.setVisibility(View.GONE);
                        tvYdbh.setVisibility(View.GONE);
                        bottom.setVisibility(View.GONE);
                        tvCtsj.setVisibility(View.GONE);
                        zf.setVisibility(View.GONE);
//                        tvBtn2.setVisibility(View.GONE);
                        tvBtn2.setText("查看中奖记录");
                        break;
                    case "4":
                        ivTop.setImageResource(R.mipmap.twcttk);
                        baseSetText("拼团失败");
                        tvZt.setText("未成团，已退款");
                        tvFhsj.setVisibility(View.GONE);
                        tvCjsj.setVisibility(View.GONE);
                        tvKdfs.setVisibility(View.GONE);
                        tvYdbh.setVisibility(View.GONE);
                        bottom.setVisibility(View.GONE);
                        tvCtsj.setVisibility(View.GONE);
                        zf.setVisibility(View.GONE);
//                        tvBtn2.setVisibility(View.GONE);
                        tvBtn2.setText("查看中奖记录");
                        break;
                    case "5":
                        ivTop.setImageResource(R.mipmap.dd_od_jyqx);
                        baseSetText("交易已取消");
                        bottom.setVisibility(View.GONE);
                        zf.setVisibility(View.GONE);
                        tvCtsj.setVisibility(View.GONE);
                        tvFhsj.setVisibility(View.GONE);
                        tvCjsj.setVisibility(View.GONE);
                        tvKdfs.setVisibility(View.GONE);
                        tvYdbh.setVisibility(View.GONE);
                        detail.removeAllViews();
                        View vHead2 = View.inflate(OrderDetailActivity.this, R.layout.activity_order_detail_sp, null);
                        ivLogo = (ImageView) vHead2.findViewById(R.id.iv_logo);
                        tvName = (TextView) vHead2.findViewById(R.id.tv_name);
                        tvJg = (TextView) vHead2.findViewById(R.id.tv_jg);
                        tvZt = (TextView) vHead2.findViewById(R.id.tv_zt);
                        tvBtn1 = (TextView) vHead2.findViewById(R.id.tv_btn1);
                        tvBtn2 = (TextView) vHead2.findViewById(R.id.tv_btn2);
                        detail.addView(vHead2);
                        tvBtn1.setText("查看中奖记录");
                        tvBtn2.setVisibility(View.GONE);
                        tvZt.setText("交易已取消");
                        tvBtn1.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Bundle bundle7 = new Bundle();
                                bundle7.putString("activityId", OrderDetailActivity.this.date.getActivityId());
                                bundle7.putString("activityType", OrderDetailActivity.this.date.getOrderStatus());
                                BaseUtil.ToAcb(OrderDetailActivity.this, PrizeDetailMoreActivity.class, bundle7);
                            }
                        });
                        break;
                    case "6":
                        baseSetText("未中奖");
                        if (!date.getSource().equals("7")) {
                            ivTop.setImageResource(R.mipmap.dd_wzj_fkz);
                            tvZt.setText("未中奖，待返款");
                        } else {
                            ivTop.setImageResource(R.mipmap.zwjy);
                            tvZt.setText("未中奖，已返款");
                        }
                        tvFhsj.setVisibility(View.GONE);
                        tvCjsj.setVisibility(View.GONE);
                        tvKdfs.setVisibility(View.GONE);
                        tvYdbh.setVisibility(View.GONE);
                        bottom.setVisibility(View.GONE);
//                        tvCtsj.setVisibility(View.GONE);
                        zf.setVisibility(View.GONE);
                        tvBtn2.setText("查看中奖记录");
                        break;
                    case "7":
                        ivTop.setImageResource(R.mipmap.zwjy);
                        baseSetText("未中奖");
                        tvZt.setText("未中奖，已返款");
                        tvFhsj.setVisibility(View.GONE);
                        tvCjsj.setVisibility(View.GONE);
                        tvKdfs.setVisibility(View.GONE);
                        tvYdbh.setVisibility(View.GONE);
                        bottom.setVisibility(View.GONE);
//                        tvCtsj.setVisibility(View.GONE);
                        zf.setVisibility(View.GONE);
                        tvBtn2.setText("查看中奖记录");
                        break;
//                    case "8":
//                        baseSetText("已成团，待开奖");
//                        break;
                    case "9":
                        ivTop.setImageResource(R.mipmap.dd_od_jycg);
                        baseSetText("已签收");
                        tvCkwl.setVisibility(View.GONE);
                        zf.setVisibility(View.GONE);
//                        tvBtn2.setVisibility(View.GONE);
                        tvBtn2.setText("查看中奖记录");
                        if (date.getSource().equals("4")) {
                            detail.removeAllViews();
                            View vHead3 = View.inflate(OrderDetailActivity.this, R.layout.activity_order_detail_sp_zfqx, null);
                            ivLogo = (ImageView) vHead3.findViewById(R.id.iv_logo);
                            tvName = (TextView) vHead3.findViewById(R.id.tv_name);
                            tvJg = (TextView) vHead3.findViewById(R.id.tv_jg);
                            tvZt = (TextView) vHead3.findViewById(R.id.tv_zt);
                            detail.addView(vHead3);
                        }
                        tvZt.setText("已签收");
                        tvQdsh.setText("查看物流");
                        tvCkwl.setVisibility(View.GONE);
                        break;
                    case "10":
                        ivTop.setImageResource(R.mipmap.zjdfh);
                        baseSetText("拼团成功");
                        tvFhsj.setVisibility(View.GONE);
                        tvCjsj.setVisibility(View.GONE);
                        tvKdfs.setVisibility(View.GONE);
                        tvYdbh.setVisibility(View.GONE);
                        bottom.setVisibility(View.GONE);
                        zf.setVisibility(View.GONE);
//                        tvBtn2.setVisibility(View.GONE);
                        tvBtn2.setText("查看中奖记录");
                        if (date.getSource().equals("4")) {
                            detail.removeAllViews();
                            View vHead4 = View.inflate(OrderDetailActivity.this, R.layout.activity_order_detail_sp_zfqx, null);
                            ivLogo = (ImageView) vHead4.findViewById(R.id.iv_logo);
                            tvName = (TextView) vHead4.findViewById(R.id.tv_name);
                            tvJg = (TextView) vHead4.findViewById(R.id.tv_jg);
                            tvZt = (TextView) vHead4.findViewById(R.id.tv_zt);
                            detail.addView(vHead4);
                        }
                        tvZt.setText("已中奖，待发货");
                        break;
                    case "11":
                        ivTop.setImageResource(R.mipmap.dd_od_dsh);
                        baseSetText("待收货");
                        tvFhsj.setVisibility(View.GONE);
                        tvCjsj.setVisibility(View.GONE);
                        tvKdfs.setVisibility(View.GONE);
                        tvYdbh.setVisibility(View.GONE);
                        zf.setVisibility(View.GONE);
                        tvBtn3.setVisibility(View.VISIBLE);

//                if (date.getSource().equals("4")) {
//                    detail.removeAllViews();
//                    View vHead2 = View.inflate(OrderDetailActivity.this, R.layout.activity_order_detail_sp_zfqx, null);
//                    ivLogo = (ImageView) vHead2.findViewById(R.id.iv_logo);
//                    tvName = (TextView) vHead2.findViewById(R.id.tv_name);
//                    tvJg = (TextView) vHead2.findViewById(R.id.tv_jg);
//                    tvZt = (TextView) vHead2.findViewById(R.id.tv_zt);
//                    detail.addView(vHead2);
//                }
                        tvBtn3.setText("查看中奖记录");
                        if (date.getSource().equals("7")) {
                            tvBtn1.setText("查看团详情");
                            tvBtn2.setVisibility(View.GONE);
                        } else {
                            tvBtn1.setText("申请退款");
                        }
                        if (date.getRefundStatus().equals("0") || date.getRefundStatus().equals("5") || date.getRefundStatus().equals("6")) {

//
                        } else {
                            tvZt.setText("申请售后中");
                            tvBtn1.setVisibility(View.GONE);
                            tv_shsqz.setVisibility(View.VISIBLE);
                            EventBus.getDefault().post(new AnyEventType(date.getOrderInfo().getOrderId() + ";xin"));
                        }
                        tvZt.setText("待收货");
                        break;
                    case "12":
                        ivTop.setImageResource(R.mipmap.dkj);
                        baseSetText("待开奖");
                        tvZt.setText("待开奖");
                        tvFhsj.setVisibility(View.GONE);
                        tvCjsj.setVisibility(View.GONE);
                        tvKdfs.setVisibility(View.GONE);
                        tvYdbh.setVisibility(View.GONE);
                        bottom.setVisibility(View.GONE);
                        tvCtsj.setVisibility(View.GONE);
                        zf.setVisibility(View.GONE);
                        tvBtn2.setVisibility(View.GONE);
                        tvBtn2.setText("查看中奖记录");
                        break;
                }
                break;
            default:
                ivTop.setImageResource(R.mipmap.dd_od_jycg);
                baseSetText("已签收");
                tvCkwl.setVisibility(View.GONE);
                zf.setVisibility(View.GONE);
                tvBtn2.setVisibility(View.GONE);
                if (date.getSource().equals("4") || date.getSource().equals("3")) {
                    detail.removeAllViews();
                    View vHead2 = View.inflate(OrderDetailActivity.this, R.layout.activity_order_detail_sp_zfqx, null);
                    ivLogo = (ImageView) vHead2.findViewById(R.id.iv_logo);
                    tvName = (TextView) vHead2.findViewById(R.id.tv_name);
                    tvJg = (TextView) vHead2.findViewById(R.id.tv_jg);
                    tvZt = (TextView) vHead2.findViewById(R.id.tv_zt);
                    detail.addView(vHead2);
                }
                tvZt.setText("已签收");
                tvQdsh.setText("查看物流");
                tvCkwl.setVisibility(View.GONE);
                break;
        }
//        try {
        tvUName.setText(date.getAddressInfo().getConsignee() + "  " + date.getAddressInfo().getTel());
        tvUDz.setText(date.getAddressInfo().getAddress());
        if (date.getOrderInfo() != null && StringUtils.isNotBlank(date.getOrderInfo().getPaymethod())) {
            switch (date.getOrderInfo().getPaymethod()) {
                case "2":
                    zf1 = "微信";
                    break;
                case "1":
                    zf1 = "支付宝";
                    break;
                case "8":
                    zf1 = "微信";
                    break;
            }
            tvZffs.setText("支付方式： " + zf1);
        }

        tvDdbh.setText("订单编号： " + date.getOrderInfo().getOrderNo());

        tvXdsj.setText("下单时间： " + date.getOrderInfo().getCreateTime());
        tvCtsj.setText("成团时间： " + date.getOrderInfo().getGroupTime());
        tvFhsj.setText("发货时间： " + date.getOrderInfo().getSendTime());
        tvCjsj.setText("成交时间： " + date.getOrderInfo().getConfirmTime());
        tvKdfs.setText("快递方式： " + date.getOrderInfo().getLogisticsName());
        tvYdbh.setText("运单编号： " + date.getOrderInfo().getLogisticsNo());
        try {
            Glide.with(this).load(date.getProductInfo().getProductImage()).into(ivLogo);
        } catch (Exception e) {
        }
        tvName.setText(date.getProductInfo().getProductName());
        DecimalFormat format = new DecimalFormat("0.00");
        SpannableStringBuilder style = new SpannableStringBuilder("共1件商品 合计：￥" + date.getProductInfo().getAllPrice() + "（免运费）");
        style.setSpan(new ForegroundColorSpan(OrderDetailActivity.this.getResources().getColor(R.color.text_01)), "共1件商品 合计：".length(), ("共1件商品 合计：￥" + date.getProductInfo().getAllPrice()).length(), Spannable.SPAN_EXCLUSIVE_INCLUSIVE);
        tvJg.setText(style);
//        } catch (Exception e) {
//        }
    }


    @OnClick({R.id.tv_btn2, R.id.tv_btn1, R.id.tv_btn3, R.id.wx, R.id.zfb, R.id.qq, R.id.tv_ckwl, R.id.tv_qdsh, R.id.detail})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.detail:
                try {
                    if (date.getRefundStatus().equals("0") || date.getRefundStatus().equals("5") || date.getRefundStatus().equals("6")) {
                        if (date.getSource().equals("3")) {
                            Bundle bundle = new Bundle();
//                    bundle.putString("activityId", date.getActivityId() + "");
                            bundle.putString("tag", "caijia");
                            bundle.putString("pid", date.getProductInfo().getProductId() + "");
                            BaseUtil.ToAcb(OrderDetailActivity.this, GoodsDetailActivity.class, bundle);
                        } else if (date.getSource().equals("4") || date.getSource().equals("7") || date.getSource().equals("5")) {
                            Bundle bundle = new Bundle();
                            bundle.putString("tag", "caijia");
                            bundle.putString("pid", date.getProductInfo().getProductId() + "");
                            BaseUtil.ToAcb(OrderDetailActivity.this, GoodsDetailActivity.class, bundle);
                        } else {
                            Bundle bundle = new Bundle();
                            bundle.putString("activityId", date.getActivityId() + "");
                            bundle.putString("pid", date.getProductInfo().getProductId() + "");
                            BaseUtil.ToAcb(OrderDetailActivity.this, GoodsDetailActivity.class, bundle);
                        }
                    }
                } catch (Exception E) {
                    LogUtil.Log(E + "");
                }
                break;
            case R.id.tv_btn2:
                if ((type.equals("555") && date.getOrderStatus().equals("11")) || type.equals("4") || type.equals("5")) {
                    Bundle bundle8 = new Bundle();
                    bundle8.putString("recordId", date.getAttendId());
                    bundle8.putString("source", date.getSource());
                    BaseUtil.ToAcb(OrderDetailActivity.this, GroupDetailActivity.class, bundle8);
                    return;
                }

                if (((!date.getSource().equals("5") && (!date.getSource().equals("7")) || ((date.getSource().equals("5") && (date.getOrderStatus().equals("1")) && (date.getSource().equals("7") && (date.getOrderStatus().equals("1")))))))) {
                    Bundle bundle6 = new Bundle();
                    bundle6.putString("price", date.getProductInfo().getOrderPrice() + "");
                    bundle6.putString("oid", oid + "");
//                        bundle5.putString("phone", "11");
                    bundle6.putString("orderStatus", date.getOrderStatus());
                    BaseUtil.ToAcb(OrderDetailActivity.this, ApplyRefundActivity.class, bundle6);
                } else {
                    if (date.getOrderStatus().equals("11")) {
                        Bundle bundle8 = new Bundle();
                        bundle8.putString("recordId", date.getAttendId());
                        bundle8.putString("source", date.getSource());
                        BaseUtil.ToAcb(OrderDetailActivity.this, GroupDetailActivity.class, bundle8);
                    } else {
                        Log.i("匿名内部类", "查看中奖信息");
                        Bundle bundle7 = new Bundle();
                        if (date.getOrderStatus().equals("5") || date.getOrderStatus().equals("4") || date.getOrderStatus().equals("3") || date.getSource().equals("7")) {
                            bundle7.putString("activityId", date.getActivityId());
                            bundle7.putString("activityType", OrderDetailActivity.this.date.getSource());
                            BaseUtil.ToAcb(OrderDetailActivity.this, PrizeDetailMoreActivity.class, bundle7);
                        } else {
                            bundle7.putString("attendId", date.getAttendId());
                            bundle7.putString("activityType", date.getSource());
                            bundle7.putString("orderStatus", date.getOrderStatus());
                            BaseUtil.ToAcb(OrderDetailActivity.this, PrizeDetailActivity.class, bundle7);
                        }
                    }
                }
                break;
            case R.id.tv_btn1:
                switch (type) {
                    case "5":
                        Bundle bundle5 = new Bundle();
                        bundle5.putString("price", date.getProductInfo().getOrderPrice() + "");
                        bundle5.putString("oid", oid + "");
//                        bundle5.putString("phone", "11");
                        bundle5.putString("orderStatus", date.getOrderStatus());
                        BaseUtil.ToAcb(OrderDetailActivity.this, ApplyRefundActivity.class, bundle5);
                        break;
                    default:
                        if (!date.getSource().equals("5") || (date.getSource().equals("5") && (date.getOrderStatus().equals("1")))) {
                            Bundle bundle7 = new Bundle();
                            bundle7.putString("source", date.getSource());
                            bundle7.putString("recordId", date.getAttendId() + "");
                            BaseUtil.ToAcb(OrderDetailActivity.this, GroupDetailActivity.class, bundle7);
                        } else if (date.getOrderStatus().equals("11")) {
                            Bundle bundle4 = new Bundle();
                            bundle4.putString("price", date.getProductInfo().getOrderPrice() + "");
                            bundle4.putString("oid", oid + "");
//                        bundle5.putString("phone", "11");
                            bundle4.putString("orderStatus", date.getOrderStatus());
                            BaseUtil.ToAcb(OrderDetailActivity.this, ApplyRefundActivity.class, bundle4);
                        } else if (date.getOrderStatus().equals("5")) {
                            Log.i("匿名内部类", "查看中奖信息");
                            Bundle bundle7 = new Bundle();
                            if (date.getOrderStatus().equals("5") || date.getOrderStatus().equals("4") || date.getOrderStatus().equals("3") || date.getSource().equals("7")) {
                                bundle7.putString("activityId", date.getActivityId());
                                bundle7.putString("activityType", OrderDetailActivity.this.date.getSource());
                                BaseUtil.ToAcb(OrderDetailActivity.this, PrizeDetailMoreActivity.class, bundle7);
                            } else {
                                bundle7.putString("attendId", date.getAttendId());
                                bundle7.putString("activityType", date.getSource());
                                bundle7.putString("orderStatus", date.getOrderStatus());
                                BaseUtil.ToAcb(OrderDetailActivity.this, PrizeDetailActivity.class, bundle7);
                            }
                        } else {
                            Bundle bundle8 = new Bundle();
                            bundle8.putString("recordId", date.getAttendId());
                            bundle8.putString("source", date.getSource());
                            BaseUtil.ToAcb(OrderDetailActivity.this, GroupDetailActivity.class, bundle8);
                        }
                        break;
                }

                break;
            case R.id.tv_btn3:
                Log.i("匿名内部类", "查看中奖信息");
                Bundle bundle7 = new Bundle();
                if (date.getOrderStatus().equals("5") || date.getOrderStatus().equals("4") || date.getOrderStatus().equals("3") || date.getSource().equals("7")) {
                    bundle7.putString("activityId", date.getActivityId());
                    bundle7.putString("activityType", OrderDetailActivity.this.date.getSource());
                    BaseUtil.ToAcb(OrderDetailActivity.this, PrizeDetailMoreActivity.class, bundle7);
                } else {
                    bundle7.putString("attendId", date.getAttendId());
                    bundle7.putString("activityType", date.getSource());
                    bundle7.putString("orderStatus", date.getOrderStatus());
                    BaseUtil.ToAcb(OrderDetailActivity.this, PrizeDetailActivity.class, bundle7);
                }
                break;
            case R.id.wx:
                switchPayMethod(1);
                break;
            case R.id.zfb:
                switchPayMethod(2);
                break;
            case R.id.qq:
                break;
            case R.id.tv_ckwl:
                switch (type) {
                    case "2":
                        new AlertDialog.Builder(OrderDetailActivity.this).setTitle("提示框").setMessage("确认取消该订单？")
                                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {
                                        cancelOrderGet(oid);
                                    }
                                })
                                .setNegativeButton("取消", null)
                                .show();
                        break;
                    case "555":
                        if (date.getOrderStatus().equals("1")) {
                            new AlertDialog.Builder(OrderDetailActivity.this).setTitle("提示框").setMessage("确认取消该订单？")
                                    .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int which) {
                                            cancelOrderGet(oid);
                                        }
                                    })
                                    .setNegativeButton("取消", null)
                                    .show();
                        } else {
                            Bundle bundle1 = new Bundle();
                            bundle1.putString("orderId", oid);
                            BaseUtil.ToAcb(OrderDetailActivity.this, LogisticsActivity.class, bundle1);
                        }
                        break;
                    default:
                        Bundle bundle1 = new Bundle();
                        bundle1.putString("orderId", oid);
                        BaseUtil.ToAcb(OrderDetailActivity.this, LogisticsActivity.class, bundle1);
                        break;
                }
                break;
            case R.id.tv_qdsh:
                switch (type) {
                    case "1":
                    case "2":
                        //支付
                        break;
                    case "3":
                    case "4":
                    case "5":
                        new AlertDialog.Builder(OrderDetailActivity.this).setTitle("提示框").setMessage("是否确定收货？")
                                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {
                                        editOrderStatusGet(oid);
                                    }
                                })
                                .setNegativeButton("取消", null)
                                .show();
                        break;
                    default:
                        if (date.getOrderStatus().equals("11")) {
                            new AlertDialog.Builder(OrderDetailActivity.this).setTitle("提示框").setMessage("是否确定收货？")
                                    .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int which) {
                                            editOrderStatusGet(oid);
                                        }
                                    })
                                    .setNegativeButton("取消", null)
                                    .show();
                        } else {
                            Bundle bundle1 = new Bundle();
                            bundle1.putString("orderId", oid);
                            BaseUtil.ToAcb(OrderDetailActivity.this, LogisticsActivity.class, bundle1);
                        }

                        break;
                }
        }
    }

    private void cancelOrderGet(final String oid) {
        AbRequestParams params = new AbRequestParams();
        params.put("oid", oid);
        AbHttpUtil.getInstance(OrderDetailActivity.this).get(Constant.cancelOrder, params, new AbStringHttpResponseListener() {
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
                ToastUtils.showShortToast(OrderDetailActivity.this, "网络异常，数据加载失败");
                LogUtil.Log(error.getMessage());
            }

            @Override
            public void onSuccess(int statusCode, String content) {
                AbResult result = new AbResult(content);
                if (result.getResultCode() == AbResult.RESULT_OK) {
                    Gson gson = new Gson();
                    Type type = new TypeToken<BaseModel<String>>() {
                    }.getType();
                    BaseModel<String> base = gson.fromJson(content, type);
//                    Log.d(TAG, "onSuccess: " + content);
                    if (base.result != null) {
                        if (base.result.equals("1")) {
                            if (date.getSource().equals("5")) {
                                date.setOrderStatus("5");
                            } else {
                                OrderDetailActivity.this.type = "1";
                            }
                            EventBus.getDefault().post(new AnyEventType(oid + ";cancel"));
                            SetView(date);
                        }
                        ToastUtils.showShortToast(OrderDetailActivity.this, base.error_msg);
                    } else {
                        ToastUtils.showShortToast(OrderDetailActivity.this, base.error_msg);
                    }
                } else {
                    ToastUtils.showShortToast(OrderDetailActivity.this, "网络异常，数据加载失败");
                    LogUtil.Log(result.getResultMessage());
                }

            }
        });

    }

    private void editOrderStatusGet(final String oid) {
        AbRequestParams params = new AbRequestParams();
        params.put("oid", oid);
        params.put("status", "0");
        params.put("uid", Untool.getUid());
        AbHttpUtil.getInstance(OrderDetailActivity.this).get(Constant.editOrderStatus, params, new AbStringHttpResponseListener() {
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
                        ToastUtils.showShortToast(OrderDetailActivity.this, "网络异常，数据加载失败");
                        LogUtil.Log(error.getMessage());
                    }

                    @Override
                    public void onSuccess(int statusCode, String content) {
                        AbResult result = new AbResult(content);
                        if (result.getResultCode() == AbResult.RESULT_OK) {
                            Gson gson = new Gson();
                            Type type = new TypeToken<BaseModel<String>>() {
                            }.getType();
                            BaseModel<String> base = gson.fromJson(content, type);
//                    Log.d(TAG, "onSuccess: " + content);
                            if (base.result != null) {
                                if (base.result.equals("1")) {
                                    if (date.getSource().equals("5")) {
                                        date.setOrderStatus("9");
                                    } else {
                                        OrderDetailActivity.this.type = "6";
                                    }
                                    EventBus.getDefault().post(new AnyEventType(oid + ";finish"));
                                    SetView(date);
                                }
                                ToastUtils.showShortToast(OrderDetailActivity.this, base.error_msg);

                            } else {
                                ToastUtils.showShortToast(OrderDetailActivity.this, "网络异常，数据加载失败");
                                LogUtil.Log(result.getResultMessage());
                            }

                        }
                    }
                }

        );

    }

    void switchPayMethod(int i) {
        switch (i) {
            case 1:
                payMethod = "2";
                ivWx3.setVisibility(View.VISIBLE);
                ivZfb3.setVisibility(View.GONE);
                break;
            case 2:
                payMethod = "1";
                ivWx3.setVisibility(View.GONE);
                ivZfb3.setVisibility(View.VISIBLE);
                break;
        }
    }

    AddOrderByPurchaseBean addOrderByPurchaseBean;
    String source;
    String orderNo;

    private void payOrder() {
        PayOrderApi payOrderApi = new PayOrderApi();
        payOrderApi.setPayMethod(payMethod);
        payOrderApi.setOrderNo(orderNo);
        payOrderApi.setUid(UserInfoUtils.GetUid());

        LogUtil.Log(payOrderApi.getUrl() + "?" + payOrderApi.getParamMap().getParamString());
        AbHttpUtil.getInstance(this).get(payOrderApi.getUrl(), payOrderApi.getParamMap(), new AbStringHttpResponseListener() {
            @Override
            public void onSuccess(int i, String content) {
                LogUtil.Log(content);
                AbResult result = new AbResult(content);
                if (result.getResultCode() == AbResult.RESULT_OK) {
                    Gson gson = new Gson();
                    Type type = new TypeToken<BaseModel<AddOrderByPurchaseBean>>() {
                    }.getType();
                    BaseModel<AddOrderByPurchaseBean> base = gson.fromJson(content, type);
                    if (base.success) {
                        addOrderByPurchaseBean = base.result;
                        LogUtil.Log("payMethod:" + payMethod);
                        if (StringUtils.isNotBlank(addOrderByPurchaseBean.fullpay) && addOrderByPurchaseBean.fullpay.equals("0")) {
                            if (payMethod.equals("2") && base.result != null && base.result.wxpay != null) {
                                //微信支付
                                payWecChat(base.result.wxpay);
                            } else if (payMethod.equals("1") && base.result != null && base.result.aplipay != null) {
                                goAliPay(base.result.aplipay);
                            } else {
                                Intent intent = new Intent(OrderDetailActivity.this, OrdersActivity.class);
                                //intent.putExtra("source","1.1");
                                intent.putExtra("page", "0");
                                startActivity(intent);
                                OrderDetailActivity.this.finish();
                            }
                        } else {
                            Intent intent = new Intent(OrderDetailActivity.this, OrdersActivity.class);
                            //intent.putExtra("source","1.1");
                            intent.putExtra("page", "0");
                            startActivity(intent);
                            OrderDetailActivity.this.finish();
                        }


                    } else {
                        ToastUtils.showShortToast(OrderDetailActivity.this, base.error_msg);
                    }
                } else {
                    ToastUtils.showShortToast(OrderDetailActivity.this, "网络异常，数据加载失败");
                    LogUtil.Log(result.getResultMessage());
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
                ToastUtils.showShortToast(OrderDetailActivity.this, "网络异常，数据加载失败");
                LogUtil.Log(error.getMessage());
            }
        });

    }

    //微信start
    PayReq req;
    final IWXAPI msgApi = WXAPIFactory.createWXAPI(
            OrderDetailActivity.this, null);

    protected void payWecChat(AddOrderByPurchaseBean.Wxpay wxpay) {
        if (!msgApi.isWXAppInstalled()) {
            ToastUtils.showShortToast(OrderDetailActivity.this,
                    "您的手机中尚未安装微信哦");
            return;
        }
        if (!msgApi.isWXAppSupportAPI()) {
            ToastUtils.showShortToast(OrderDetailActivity.this,
                    "当前微信版本过低");
            return;
        }
        outTradeNo = wxpay.out_trade_no;
        WXPayEntryActivity.outTradeNo = outTradeNo;
        WXPayEntryActivity.oid = oid;
        WXPayEntryActivity.source = source;
        // WXPayEntryActivity.attendId = attendId;
        req = new PayReq();
        msgApi.registerApp(wxpay.appid);
        // 生成签名参数
        req.appId = wxpay.appid;
        req.partnerId = wxpay.partnerid;
        req.prepayId = wxpay.prepayid;
        req.packageValue = "Sign=WXPay";
        req.nonceStr = wxpay.noncestr;
        req.timeStamp = wxpay.timestamp;
        req.sign = wxpay.sign;
        msgApi.sendReq(req);
        OrderDetailActivity.this.finish();
    }

    //微信end
    // 支付宝开始
    private static final int SDK_PAY_FLAG = 1;

    // private static final int SDK_CHECK_FLAG = 2;

    /**
     * 支付宝支付发起
     */
    String outTradeNo;
    QueryPayStatusPresenter queryPayStatusPresenter;

    protected void goAliPay(final AddOrderByPurchaseBean.Alipay aplipay) {
        outTradeNo = aplipay.out_trade_no;
        Runnable payRunnable = new Runnable() {

            @Override
            public void run() {
                // 构造PayTask 对象
                PayTask alipay = new PayTask(OrderDetailActivity.this);
                // 调用支付接口，获取支付结果
                String result = alipay.pay(aplipay.sign, true);

                Message msg = new Message();
                msg.what = SDK_PAY_FLAG;
                msg.obj = result;
                mHandler.sendMessage(msg);
            }
        };

        // 必须异步调用
        Thread payThread = new Thread(payRunnable);
        payThread.start();

    }

    @SuppressLint("HandlerLeak")
    private Handler mHandler = new Handler() {
        public void handleMessage(Message msg) {
            queryPayStatusPresenter = new QueryPayStatusPresenter(OrderDetailActivity.this);
            queryPayStatusPresenter.loadData(outTradeNo, "1");
            LogUtil.Log("oid:" + oid);
            switch (msg.what) {
                case SDK_PAY_FLAG: {
                    LogUtil.Log("------------------payResult:" + msg.obj);
                    PayResult payResult = new PayResult((String) msg.obj);

                    // 支付宝返回此次支付结果及加签，建议对支付宝签名信息拿签约时支付宝提供的公钥做验签
                    // String resultInfo = payResult.getResult();

                    String resultStatus = payResult.getResultStatus();
                    LogUtil.Log("------------------resultStatus:" + resultStatus);
                    // 判断resultStatus 为“9000”则代表支付成功，具体状态码代表含义可参考接口文档

                    if (TextUtils.equals(resultStatus, "9000")) {
                        OrderdetailPayGet();

                    } else {

                        ToastUtils.showShortToast(OrderDetailActivity.this,
                                "支付结果确认中");
                        Intent intent = new Intent(OrderDetailActivity.this, OrdersActivity.class);
                        //intent.putExtra("source","1.1");
                        intent.putExtra("page", "0");
                        startActivity(intent);
                        OrderDetailActivity.this.finish();
                    }

                    break;
                }
            }
        }

        ;
    };

    // 支付宝结束
    String attendId;

    private void OrderdetailPayGet() {
        AbRequestParams params = new AbRequestParams();
        params.put("oid", oid);
        LogUtil.Log(orderDetailApi + "?oid=" + oid);
        // ToastUtils.showShortToast(OrderDetailActivity.this,"oid=" + oid);
        AbHttpUtil.getInstance(this).get(orderDetailApi, params, new AbStringHttpResponseListener() {

            @Override
            public void onStart() {
                showLoading("加载中...");
            }

            @Override
            public void onFinish() {
                hideLoading();
            }

            @Override
            public void onFailure(int statusCode, String content, Throwable error) {
                ToastUtils.showShortToast(OrderDetailActivity.this, "网络异常，数据加载失败");
                LogUtil.Log(error.getMessage());
            }

            @Override
            public void onSuccess(int statusCode, String content) {
                AbResult result = new AbResult(content);
                if (result.getResultCode() == AbResult.RESULT_OK) {
                    Gson gson = new Gson();
                    Type type = new TypeToken<BaseModel<OrderDetailInfoBean>>() {
                    }.getType();
                    BaseModel<OrderDetailInfoBean> base = gson.fromJson(content, type);
                    if (base.result != null) {
                        Log.d(oid + "+", "onSuccess: " + content);
                        attendId = base.result.getAttendId();
                        // ToastUtils.showShortToast(OrderDetailActivity.this,"attendId=" + attendId);
                        if (StringUtils.isNotBlank(attendId) && StringUtils.isNotBlank(source) && !source.equals("4")) {
                            if (source.equals("5")) {
                                //0.1抽奖
                                Intent intent = new Intent(OrderDetailActivity.this, GroupDetailActivity.class);
                                intent.putExtra("recordId", attendId);
                                intent.putExtra("source", source);
                                intent.putExtra("tag", "pay");
                                startActivity(intent);
                            } else {
                                Intent intent = new Intent(OrderDetailActivity.this, GroupDetailActivity.class);
                                intent.putExtra("recordId", attendId);
                                intent.putExtra("source", source);
                                intent.putExtra("tag", "pay");
                                startActivity(intent);
                            }

                        } else {
                            Intent intent = new Intent(OrderDetailActivity.this, OrdersActivity.class);
                            //intent.putExtra("source","1.1");
                            intent.putExtra("page", "0");
                            startActivity(intent);
                        }

                        OrderDetailActivity.this.finish();
                        //ToastUtils.showShortToast(getApplicationContext(),attendId);

                    } else {
                        ToastUtils.showShortToast(OrderDetailActivity.this, base.error_msg);
                    }
                } else {
                    ToastUtils.showShortToast(OrderDetailActivity.this, "网络异常，数据加载失败");
                    LogUtil.Log(result.getResultMessage());
                }

            }
        });

    }

    ProgressDialog progressDialog;

    void showLoading(String msg) {
        if (progressDialog == null) {
            progressDialog = new ProgressDialog(OrderDetailActivity.this);
        }
        progressDialog.setMessage(msg);
        progressDialog.setCancelable(false);
        progressDialog.show();
    }

    void hideLoading() {
        if (progressDialog != null && progressDialog.isShowing()) {
            progressDialog.dismiss();
        }
    }

}