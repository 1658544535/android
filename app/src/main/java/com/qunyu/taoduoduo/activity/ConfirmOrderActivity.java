package com.qunyu.taoduoduo.activity;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
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
import com.qunyu.taoduoduo.R;
import com.qunyu.taoduoduo.api.AddOrderByPurchaseApi;
import com.qunyu.taoduoduo.api.AddPurchaseApi;
import com.qunyu.taoduoduo.api.DefaultAddressApi;
import com.qunyu.taoduoduo.base.BaseActivity;
import com.qunyu.taoduoduo.base.BaseModel;
import com.qunyu.taoduoduo.bean.AddOrderByPurchaseBean;
import com.qunyu.taoduoduo.bean.AddPurchaseBean;
import com.qunyu.taoduoduo.bean.MyAddressBean;
import com.qunyu.taoduoduo.bean.MyCouponsListBean;
import com.qunyu.taoduoduo.bean.OrderDetailInfoBean;
import com.qunyu.taoduoduo.config.AppConfig;
import com.qunyu.taoduoduo.mvpview.ConfirmOrderView;
import com.qunyu.taoduoduo.presenter.ConfirmorderPresenter;
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

import java.lang.reflect.Type;
import java.text.DecimalFormat;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.qunyu.taoduoduo.global.Constant.orderDetailApi;

/**
 * Created by Administrator on 2016/9/28.
 * 确认订单
 */

public class ConfirmOrderActivity extends BaseActivity implements View.OnClickListener,ConfirmOrderView {
    private DefaultAddressApi defaultAddressApi;
    private MyAddressBean defaultAddressBean;

    private AddPurchaseApi addPurchaseApi;
    private AddPurchaseBean bean;


    private AddOrderByPurchaseApi addOrderByPurchaseApi;

    @BindView(R.id.tv_address_status)
    TextView tv_addressStatus;
    @BindView(R.id.tv_username)
    TextView tv_userName;
    @BindView(R.id.tv_userPhoneNum)
    TextView tv_userPhoneNum;
    @BindView(R.id.tv_userAddress)
    TextView tv_userAddress;
    @BindView(R.id.iv_shangpin_logo)
    ImageView iv_shangpin_logo;
    @BindView(R.id.tv_shangpin_title)
    TextView tv_title;
    @BindView(R.id.tv_pintuan_jiage)
    TextView tv_pintuanJiage;
    @BindView(R.id.iv_productDetails)
    ImageView iv_productDetails;
    @BindView(R.id.rl_pdetail)
    PercentRelativeLayout rl_pdetail;//商品

    @BindView(R.id.btn_addProductNum)
    TextView btn_addNum;
    @BindView(R.id.btn_reduceProductNum)
    TextView btn_reduceNum;
    @BindView(R.id.tv_productNum)
    TextView tv_productNum;
    @BindView(R.id.tv_allPrice)
    TextView tv_allPrice;

    @BindView(R.id.layout_payWechat)
    PercentRelativeLayout layout_payWeChat;
    @BindView(R.id.layout_payAlipay)
    PercentRelativeLayout layout_payAlipay;
    @BindView(R.id.layout_payQQ)
    PercentRelativeLayout layout_payQQ;
    @BindView(R.id.iv_select_payWechat)
    ImageView iv_select_payWechat;
    @BindView(R.id.iv_select_payAlipay)
    ImageView iv_select_payAlipay;
    @BindView(R.id.iv_select_payQQ)
    ImageView iv_select_payqq;

    @BindView(R.id.tv_shifukuan)
    TextView tv_shifukuan;
    @BindView(R.id.tv_nowPay)
    TextView tv_nowPay;
    @BindView(R.id.tv_sellprice)
    TextView tv_sellprice;//原价
    @BindView(R.id.tv_espressPrice)
    TextView tv_espressPrice;//运费
    @BindView(R.id.tv_emptyTips)
    TextView tv_emptyTips;//空地址提示
    @BindView(R.id.rl_address)
    PercentRelativeLayout rl_address;//地址
    @BindView(R.id.rl_youhuiquan)
    PercentRelativeLayout rl_youhuiquan;//优惠券
    @BindView(R.id.tv_couponNo)
    TextView tv_couponNo;//优惠券码
    @BindView(R.id.tv_couPricet)
    TextView tv_couPricet;//优惠金额
    @BindView(R.id.edt_buyerMessage)
    EditText edt_buyerMsg;//买家留言

    private String activityid;//活动id,默认0-普通商品单独购
    private String attendId;//该团记录id(参团时必填)
    private int num;//商品数量
    private String pid;//商品id
    private String skuLinkId = null;//商品skuid,没有时传空
    private String source;//来源，1-普通拼团2-团免3-猜价 4单独购买 5-0.1抽奖6-限时秒杀
    private String addressId;//我的地址ID，快递时必填
    private String buyer_message;//买家留言
    private String channel;//订单渠道(1app,2微信)
    private String consigneeType;//收货方式（1，快递2，自提）
    private String couponNo;//优惠券码

    private String payMethod = "1";//默认；2：微信支付1支付宝、
    DecimalFormat decimalFormat;

    public static ConfirmOrderActivity instance = null;

    //mvp
    ConfirmorderPresenter presenter ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        baseSetContentView(R.layout.confirm_order_activity);
        ButterKnife.bind(this);
        instance = this;
        activityid = getIntent().getStringExtra("activityId");
        attendId = getIntent().getStringExtra("attendId");
        num = Integer.parseInt(getIntent().getStringExtra("num"));
        pid = getIntent().getStringExtra("pid");
        skuLinkId = getIntent().getStringExtra("skuLinkId");
        source = getIntent().getStringExtra("source");
        LogUtil.Log("source:"+source);
        decimalFormat = new DecimalFormat("#.##");
        defaultAddressBean = new MyAddressBean();
        presenter = new ConfirmorderPresenter(this,this);
        onListener();
        getMsg();
        getDefaultAddress();
    }


    @Override
    protected void init() {
        super.init();
        baseSetText("确认订单");
    }

    public void onResume() {
        super.onResume();
        MobclickAgent.onResume(this);
    }
    public void onPause() {
        super.onPause();
        MobclickAgent.onPause(this);
    }

    private void onListener() {
        rl_address.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ConfirmOrderActivity.this, MyAddressActivity.class);
                intent.putExtra("tag", "selectaddress");
                startActivityForResult(intent, AppConfig.SELECT_ADDRESS);
            }
        });
        layout_payAlipay.setOnClickListener(this);
        layout_payQQ.setOnClickListener(this);
        layout_payWeChat.setOnClickListener(this);
        //立即支付
        tv_nowPay.setOnClickListener(onClickListener);
        btn_addNum.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                num++;
                tv_productNum.setText("" + num);
                getallPrice();
            }
        });
        btn_reduceNum.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (num > 1) {
                    num--;
                    tv_productNum.setText(num + "");
                    getallPrice();
                }

            }
        });
        rl_youhuiquan.setOnClickListener(onClickListener);

        rl_pdetail.setOnClickListener(onClickListener);
    }

    View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.rl_youhuiquan:
                    LogUtil.Log(pid);
                    LogUtil.Log(allprice.toString());
                    Intent intent = new Intent(ConfirmOrderActivity.this, ValidUserCouponsActivity.class);
                    intent.putExtra("pid", pid);
                    intent.putExtra("price", allprice.toString());
                    startActivityForResult(intent, AppConfig.SELECT_COUPONNO);
                    break;
                case R.id.rl_pdetail:
                    Intent intentg = new Intent(ConfirmOrderActivity.this, GoodsDetailActivity.class);
                    intentg.putExtra("pid", pid);
                    intentg.putExtra("activityId", activityid);
                    startActivity(intentg);
                    break;
                case R.id.tv_nowPay:
                    addOrderByPurchase();
                    break;
            }
        }
    };

    AddOrderByPurchaseBean addOrderByPurchaseBean;
    String oid;

    private void addOrderByPurchase() {
        if (StringUtils.isBlank(addressId)) {
            toastMsg("请选择您的收货地址");
            return;
        }
        addOrderByPurchaseApi = new AddOrderByPurchaseApi();
        if (StringUtils.isNotBlank(source) && source.equals("4")) {
            addOrderByPurchaseApi.setActivityId("0");
        } else {
            addOrderByPurchaseApi.setActivityId(activityid);
        }

        buyer_message = edt_buyerMsg.getText().toString();
        addOrderByPurchaseApi.setBuyer_message(buyer_message);
        // LogUtil.Log(buyer_message);
        addOrderByPurchaseApi.setAddressId(addressId);
        addOrderByPurchaseApi.setAttendId(attendId);
        addOrderByPurchaseApi.setConsigneeType("1");
        addOrderByPurchaseApi.setCouponNo(couponNo);
        addOrderByPurchaseApi.setNum(num + "");
        addOrderByPurchaseApi.setPayMethod(payMethod);
        addOrderByPurchaseApi.setPid(pid);
        addOrderByPurchaseApi.setSkuLinkId(skuLinkId);
        addOrderByPurchaseApi.setSource(source);
        addOrderByPurchaseApi.setUid(UserInfoUtils.GetUid());
        LogUtil.Log(addOrderByPurchaseApi.getUrl() + "?" + addOrderByPurchaseApi.getParamMap().getParamString());
        AbHttpUtil.getInstance(this).get(addOrderByPurchaseApi.getUrl(), addOrderByPurchaseApi.getParamMap(), new AbStringHttpResponseListener() {
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
                                payWecChat(base.result.wxpay, base.result.orderId);
                                oid = base.result.orderId;
                            } else if (payMethod.equals("1") && base.result != null && base.result.aplipay != null) {
                                oid = base.result.orderId;
                                goAliPay(base.result.aplipay);
                            } else {
                                Intent intent = new Intent(ConfirmOrderActivity.this, OrdersActivity.class);
                                //intent.putExtra("source","1.1");
                                intent.putExtra("page", "0");
                                startActivity(intent);
                                setResult(RESULT_OK);
                                finish();
                            }
                        } else {
                            Intent intent = new Intent(ConfirmOrderActivity.this, OrdersActivity.class);
                            //intent.putExtra("source","1.1");
                            intent.putExtra("page", "0");
                            startActivity(intent);
                            setResult(RESULT_OK);
                            finish();
                        }


                    }
                } else {
                    toastMsg("网络异常，数据加载失败");
                    LogUtil.Log(result.getResultMessage());
                }
            }

            @Override
            public void onStart() {
                showLoading("订单提交中...");
            }

            @Override
            public void onFinish() {
                hideLoading();
            }

            @Override
            public void onFailure(int i, String s, Throwable error) {
               toastMsg( "网络异常，数据加载失败");
                LogUtil.Log(error.getMessage());
            }
        });

    }

    private void getMsg() {
        addPurchaseApi = new AddPurchaseApi();
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
        AbHttpUtil.getInstance(this).get(addPurchaseApi.getUrl(), addPurchaseApi.getParamMap(), new AbStringHttpResponseListener() {
            @Override
            public void onSuccess(int i, String content) {
                LogUtil.Log(content);
                AbResult result = new AbResult(content);
                if (result.getResultCode() == AbResult.RESULT_OK) {
                    Gson gson = new Gson();
                    Type type = new TypeToken<BaseModel<AddPurchaseBean>>() {
                    }.getType();
                    BaseModel<AddPurchaseBean> base = gson.fromJson(content, type);
                    LogUtil.Log("666",base.success+":"+base.error_msg+":"+base.result);
                    if (base.success&&base.result != null) {
                        bean = base.result;
                        setMsg();
                    }else if(!base.success){
                        //0.1和免费开团参加过返回false无法提交
                        toastMsg(base.error_msg);
                        finish();
                    }
                } else {
                    toastMsg("网络异常，数据加载失败");
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
                toastMsg("网络异常，数据加载失败");
                LogUtil.Log(error.getMessage());
            }
        });
    }

    private void getDefaultAddress() {
        defaultAddressApi = new DefaultAddressApi();
        defaultAddressApi.setUid(UserInfoUtils.GetUid());
        LogUtil.Log(defaultAddressApi.getUrl() + "?" + defaultAddressApi.getParamMap().getParamString());
        AbHttpUtil.getInstance(this).get(defaultAddressApi.getUrl(), defaultAddressApi.getParamMap(), new AbStringHttpResponseListener() {
            @Override
            public void onSuccess(int i, String content) {
                LogUtil.Log(content);
                AbResult result = new AbResult(content);
                if (result.getResultCode() == AbResult.RESULT_OK) {
                    Gson gson = new Gson();
                    Type type = new TypeToken<BaseModel<MyAddressBean>>() {
                    }.getType();
                    BaseModel<MyAddressBean> base = gson.fromJson(content, type);
                    if (base.result != null && base.success) {
                        defaultAddressBean = base.result;
                        setAddress();
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
                toastMsg( "网络异常，数据加载失败");
                LogUtil.Log(error.getMessage());
            }
        });
    }

    private void setMsg() {

        Glide.with(this).load(bean.products.productImage).placeholder(R.mipmap.default_load).error(R.mipmap.default_load).into(iv_shangpin_logo);
        tv_title.setText(bean.products.productName);
        if (source.equals("4")) {
            tv_pintuanJiage.setText("价格：￥" + bean.products.price);
        } else {
            tv_pintuanJiage.setText("拼团价：￥" + bean.products.price);
        }
        tv_sellprice.setText("￥" + bean.products.sellingPrice);
        tv_sellprice.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);
        tv_productNum.setText("共" + bean.products.productNumber + "件");
        tv_allPrice.setText("￥" + bean.products.allPrice);
        if (StringUtils.isNotBlank(bean.espressPrice) && !bean.espressPrice.equals("0")) {
            tv_espressPrice.setText("运费：" + bean.espressPrice);
        } else {
            tv_espressPrice.setText(" (全场包邮)");
        }
        tv_shifukuan.setText("￥" + bean.products.allPrice);
        if (StringUtils.isNotBlank(bean.products.allPrice)) {
            allprice = Float.parseFloat(bean.products.allPrice);
        }
        presenter.getCouponMsg(pid,allprice.toString());

    }


    void setAddress() {
        addressId = defaultAddressBean.getAddId();
        tv_emptyTips.setVisibility(View.GONE);
        tv_userName.setText(defaultAddressBean.getName());
        tv_userPhoneNum.setText(defaultAddressBean.getTel());
        if (defaultAddressBean.getIsRemote().equals("1")) {
            tv_userAddress.setText("(无法配送至该地址)" + defaultAddressBean.getAddress());
            tv_addressStatus.setVisibility(View.VISIBLE);
            tv_addressStatus.setText("当前地址不在配送范围");
            tv_nowPay.setBackgroundResource(R.color.gray);
            tv_nowPay.setOnClickListener(null);
        } else if (defaultAddressBean.getIsRemote().equals("0")) {
            tv_userAddress.setText(defaultAddressBean.getAddress());
            tv_addressStatus.setText("");
            tv_nowPay.setBackgroundResource(R.color.base_red);
            tv_nowPay.setOnClickListener(onClickListener);

        }
    }


    @Override
    public void onClick(View v) {
        iv_select_payWechat.setVisibility(View.GONE);
        iv_select_payqq.setVisibility(View.GONE);
        iv_select_payAlipay.setVisibility(View.GONE);
        switch (v.getId()) {
            case R.id.layout_payWechat:
                iv_select_payWechat.setVisibility(View.VISIBLE);
                payMethod = "2";
                break;
            case R.id.layout_payAlipay:
                iv_select_payAlipay.setVisibility(View.VISIBLE);
                payMethod = "1";
                break;
            case R.id.layout_payQQ:
                iv_select_payqq.setVisibility(View.VISIBLE);
                payMethod = "3";
                break;
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == AppConfig.SELECT_ADDRESS) {
                defaultAddressBean = (MyAddressBean) data.getSerializableExtra("myAddressBean");
                setAddress();
            }
            if (requestCode == AppConfig.SELECT_COUPONNO) {
                couponbean = (MyCouponsListBean.CouponList) data.getSerializableExtra("couponbean");
                setCouponNo(couponbean);
            }
        }

    }

    Float allprice;

    void getallPrice() {
        Float d = Float.parseFloat(bean.products.price) * num;
        tv_allPrice.setText("￥" + decimalFormat.format(d));
        Float b = Float.parseFloat(bean.espressPrice);
        allprice = b + d;
        if (couponMprcie != null && couponMprcie > 0) {
            LogUtil.Log("couponMprcie:" + couponMprcie);
            LogUtil.Log("b + d:" + (b + d));
            LogUtil.Log("b + d- couponMprcie:" + (b + d - couponMprcie));
            tv_shifukuan.setText("￥" + (decimalFormat.format(b + d - couponMprcie)));
        } else {
            tv_shifukuan.setText("￥" + (decimalFormat.format(b + d)));
        }


    }

    MyCouponsListBean.CouponList couponbean;
    Float couponMprcie;
    @Override
     public void setCouponNo(MyCouponsListBean.CouponList couponbean) {
        //tv_couponNo.setText("优惠券码:" + couponbean.getCouponNo());
        tv_couPricet.setText("优惠" + couponbean.getM() + "元");
        couponNo = couponbean.getCouponNo();
        if (StringUtils.isNotBlank(couponbean.getM())) {
            couponMprcie = Float.parseFloat(couponbean.getM());
            getallPrice();
        }

    }

    ProgressDialog progressDialog;

    @Override
    public void showLoading(String msg) {
        if (progressDialog == null) {
            progressDialog = new ProgressDialog(ConfirmOrderActivity.this);
        }
        progressDialog.setMessage(msg);
        progressDialog.setCancelable(false);
        progressDialog.show();
    }

    @Override
    public void hideLoading() {
        if (progressDialog != null && progressDialog.isShowing()) {
            progressDialog.dismiss();
        }
    }

    @Override
    public void toastMsg(String msg) {
        ToastUtils.showShortToast(ConfirmOrderActivity.this,msg);
    }

    //微信start
    PayReq req;
    final IWXAPI msgApi = WXAPIFactory.createWXAPI(
            ConfirmOrderActivity.this, null);

    protected void payWecChat(AddOrderByPurchaseBean.Wxpay wxpay, String oid) {
        if (!msgApi.isWXAppInstalled()) {
            toastMsg(
                    "您的手机中尚未安装微信哦");
            return;
        }
        if (!msgApi.isWXAppSupportAPI()) {
            toastMsg(
                    "当前微信版本过低");
            return;
        }
        outTradeNo = wxpay.out_trade_no;
        WXPayEntryActivity.outTradeNo = outTradeNo;
        WXPayEntryActivity.oid = oid;
        WXPayEntryActivity.source = source;
        req = new PayReq();
        msgApi.registerApp(wxpay.appid);
        // 生成签名参数
        req.appId = wxpay.appid;
        req.partnerId = wxpay.partnerid;
        req.prepayId = wxpay.prepayid;
        req.packageValue = "Sign=WXPay";
        req.nonceStr = wxpay.noncestr;
        req.timeStamp = wxpay.timestamp;
        LogUtil.Log(wxpay.timestamp+"时间");
        req.sign = wxpay.sign;
        msgApi.sendReq(req);
        setResult(RESULT_OK);
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
                PayTask alipay = new PayTask(ConfirmOrderActivity.this);
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
            queryPayStatusPresenter = new QueryPayStatusPresenter(ConfirmOrderActivity.this);
            queryPayStatusPresenter.loadData(outTradeNo, "1");
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
                        OrderdetailGet(oid);
                    } else {

                        toastMsg("支付结果确认中");
                        Intent intent = new Intent(ConfirmOrderActivity.this, OrdersActivity.class);
                        //intent.putExtra("source","1.1");
                        intent.putExtra("page", "0");
                        startActivity(intent);
                        finish();
                    }

                    break;
                }
            }
        }

        ;
    };

    // 支付宝结束

    private void OrderdetailGet(String oid) {
        AbRequestParams params = new AbRequestParams();
        params.put("oid", oid);
        LogUtil.Log(orderDetailApi + "?oid=" + oid);
        AbHttpUtil.getInstance(this).get(orderDetailApi, params, new AbStringHttpResponseListener() {

            @Override
            public void onStart() {
                showLoading("数据加载中...");
            }

            @Override
            public void onFinish() {
                hideLoading();
            }

            @Override
            public void onFailure(int statusCode, String content, Throwable error) {
                toastMsg("网络异常，数据加载失败");
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
                        attendId = base.result.getAttendId();
                        if (StringUtils.isNotBlank(source) && StringUtils.isNotBlank(attendId)) {
                            if (source.equals("4")) {
                                //单独购
                                Intent intent = new Intent(ConfirmOrderActivity.this, OrdersActivity.class);
                                //intent.putExtra("source","1.1");
                                intent.putExtra("page", "0");
                                startActivity(intent);
                            } else if(source.equals("5")){
                                //0.1抽奖和限时秒杀
                                Intent intent = new Intent(ConfirmOrderActivity.this, GroupDetailActivity.class);
                                intent.putExtra("recordId", attendId);
                                intent.putExtra("source", source);
                                intent.putExtra("tag", "pay");
                                startActivity(intent);
                            } else  {
                                Intent intent = new Intent(ConfirmOrderActivity.this, GroupDetailActivity.class);
                                intent.putExtra("recordId", attendId);
                                intent.putExtra("source", source);
                                intent.putExtra("tag", "pay");
                                startActivity(intent);

                            }
                            setResult(RESULT_OK);
                            finish();

                        }

                    } else {
                        toastMsg(base.error_msg);
                    }
                } else {
                    toastMsg("网络异常，数据加载失败");
                }

            }
        });

    }

}
