package com.qunyu.taoduoduo.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.andbase.library.http.AbHttpUtil;
import com.andbase.library.http.listener.AbStringHttpResponseListener;
import com.andbase.library.http.model.AbResult;
import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.meiqia.meiqiasdk.util.MQIntentBuilder;
import com.qunyu.taoduoduo.R;
import com.qunyu.taoduoduo.api.CancelOrderApi;
import com.qunyu.taoduoduo.api.OrderDetailApi;
import com.qunyu.taoduoduo.api.PayOrderApi;
import com.qunyu.taoduoduo.base.BaseActivity;
import com.qunyu.taoduoduo.base.BaseModel;
import com.qunyu.taoduoduo.bean.MyAddressBean;
import com.qunyu.taoduoduo.bean.OrderDetailBean;
import com.qunyu.taoduoduo.bean.PayOrderBean;
import com.qunyu.taoduoduo.config.AppConfig;
import com.qunyu.taoduoduo.global.Untool;
import com.qunyu.taoduoduo.utils.StringUtils;
import com.qunyu.taoduoduo.utils.ToastUtils;
import com.qunyu.taoduoduo.utils.UserInfoUtils;
import com.umeng.analytics.MobclickAgent;
import com.zhy.android.percent.support.PercentLinearLayout;
import com.zhy.android.percent.support.PercentRelativeLayout;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.qunyu.taoduoduo.R.id.tv_userPhoneNum;

/**
 * Created by Administrator on 2016/9/28.
 * 确认订单（待付款）
 */

public class WaitPayActivity extends BaseActivity implements View.OnClickListener {
    @BindView(R.id.tv_address_status)
    TextView tv_address_status;
    @BindView(R.id.tv_username)
    TextView tv_userName;
    @BindView(tv_userPhoneNum)
    TextView tv_userPhone;
    @BindView(R.id.tv_userAddress)
    TextView tv_userAddress;
    @BindView(R.id.iv_shangpin_logo)
    ImageView iv_productImage;
    @BindView(R.id.tv_shangpin_title)
    TextView tv_productTitle;
    @BindView(R.id.tv_shangpin_price)
    TextView tv_productPrice;
    @BindView(R.id.tv_orderNum)
    TextView tv_orderNum;
    @BindView(R.id.tv_payMethod)
    TextView tv_payMethod;
    @BindView(R.id.tv_confirmOrderTime)
    TextView tv_orderTime;
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
    @BindView(R.id.iv_select_payqq)
    ImageView iv_select_payqq;
    @BindView(R.id.btn_cancelOrder)
    TextView btn_cancelOrder;
    @BindView(R.id.btn_goPay)
    TextView btn_goPay;
    @BindView(R.id.layout_kefu)
    PercentLinearLayout layout_kefu;
    @BindView(R.id.layout_address)
    PercentRelativeLayout layout_address;
    @BindView(R.id.tv_emptyTips)
    TextView tv_emptyTips;


    private MyAddressBean defaultAddressBean;

    private OrderDetailApi orderDetailApi;
    private OrderDetailBean bean;
    private OrderDetailBean.AddressInfo addressInfo;
    private OrderDetailBean.OrderInfo orderInfo;
    private OrderDetailBean.ProductInfo productInfo;

    private PayOrderApi payOrderApi;
    private PayOrderBean payOrderbean;

    String oid;//订单id
    String payMethod;//付款方式
    String addressId;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        baseSetContentView(R.layout.wait_pay_activity);
        ButterKnife.bind(this);
        oid = getIntent().getStringExtra("oid");
        bean = new OrderDetailBean();
        addressInfo = new OrderDetailBean().new AddressInfo();
        orderInfo = new OrderDetailBean().new OrderInfo();
        productInfo = new OrderDetailBean().new ProductInfo();

        payOrderbean = new PayOrderBean();
        onListener();
        getMsg();
    }

    private void onListener() {
        layout_kefu.setOnClickListener(this);
        layout_payAlipay.setOnClickListener(this);
        layout_payQQ.setOnClickListener(this);
        layout_payWeChat.setOnClickListener(this);

        layout_kefu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HashMap<String, String> clientInfo = new HashMap<>();
                clientInfo.put("name", Untool.getName());
                clientInfo.put("avatar", Untool.getImage());
//                clientInfo.put("gender", "男");
                clientInfo.put("tel", Untool.getPhone());
//                clientInfo.put("技能1", "休刊");
                Intent intent = new MQIntentBuilder(WaitPayActivity.this).setCustomizedId(Untool.getUid()).setClientInfo(clientInfo)
                        // 相同的 id 会被识别为同一个顾客
                        .build();
                startActivity(intent);
            }
        });

        //取消订单
        btn_cancelOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cancelOrder();
            }
        });
        //去支付
        btn_goPay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                payOrder();
            }
        });

        //选择地址
        layout_address.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(WaitPayActivity.this, MyAddressActivity.class);
                intent.putExtra("tag", "selectaddress");
                startActivityForResult(intent, AppConfig.SELECT_ADDRESS);
            }
        });


    }

    public void onResume() {
        super.onResume();
        MobclickAgent.onResume(this);
    }

    public void onPause() {
        super.onPause();
        MobclickAgent.onPause(this);
    }

    @Override
    protected void init() {
        super.init();
        baseSetText("确认订单");
    }

    private void getMsg() {
        OrderDetailApi orderDetailApi = new OrderDetailApi();
        orderDetailApi.setOid(oid);
        AbHttpUtil.getInstance(this).get(orderDetailApi.getUrl(), orderDetailApi.getParamMap(), new AbStringHttpResponseListener() {
            @Override
            public void onSuccess(int i, String content) {
                AbResult result = new AbResult();
                if (result.getResultCode() == AbResult.RESULT_OK) {
                    Gson gson = new Gson();
                    Type type = new TypeToken<BaseModel<OrderDetailBean>>() {
                    }.getType();
                    BaseModel<OrderDetailBean> base = gson.fromJson(content, type);
                    if (base.result != null) {
                        bean = base.result;
                        addressInfo = bean.getAddressInfo();
                        orderInfo = bean.getOrderInfo();
                        productInfo = bean.getProductInfo();
                        setMsg();
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
                ToastUtils.showShortToast(WaitPayActivity.this, "网络异常，数据加载失败");
            }
        });
    }

    private void setMsg() {
        if (addressInfo != null) {
            tv_userName.setText(addressInfo.getConsignee());
            tv_userPhone.setText(addressInfo.getTel());
            tv_userAddress.setText(addressInfo.getAddress());
        }
        tv_address_status.setVisibility(View.VISIBLE);

        Glide.with(this).load(productInfo.getProductImage()).into(iv_productImage);
        tv_productTitle.setText(productInfo.getProductName());
        tv_productPrice.setText(productInfo.getAllPrice());

        if (StringUtils.isNotBlank(productInfo.getEspressPrice()) && !productInfo.getEspressPrice().equals("0")) {
            tv_productPrice.setText(Html.fromHtml("共" + productInfo.getNumber() +
                    "件商品 合计：<font color=\"#ff464e\">￥" + productInfo.getOrderPrice() + "</font>"));
        } else {
            tv_productPrice.setText(Html.fromHtml("共" + productInfo.getNumber() +
                    "件商品 合计：<font color=\"#ff464e\">￥" + productInfo.getOrderPrice() + "</font>(全场包邮)"));
        }

        tv_orderNum.setText("订单编号：" + orderInfo.getOrderNo());
        if (orderInfo.getPaymethod().equals("1")) {
            tv_payMethod.setText("支付方式：微信");
        } else if (orderInfo.getPaymethod().equals("2")) {
            tv_payMethod.setText("支付方式：支付宝");

        } else if (orderInfo.getPaymethod().equals("3")) {
            tv_payMethod.setText("支付方式：QQ");
        }
        tv_orderTime.setText("下单时间：" + orderInfo.getCreateTime());

    }

    void setAddress() {
        addressId = defaultAddressBean.getAddId();
        tv_emptyTips.setVisibility(View.GONE);
        tv_userName.setText(defaultAddressBean.getName());
        tv_userPhone.setText(defaultAddressBean.getTel());
        tv_userAddress.setText(defaultAddressBean.getAddress());
        if (defaultAddressBean.getIsRemote().equals("1")) {
            tv_address_status.setText("当前地址不在配送范围");
        } else if (defaultAddressBean.getIsRemote().equals("0")) {
            tv_address_status.setText("");
        }
    }

    private void cancelOrder() {
        CancelOrderApi orderApi = new CancelOrderApi();
        orderApi.setOid(oid);
        AbHttpUtil.getInstance(this).get(orderApi.getUrl(), orderApi.getParamMap(), new AbStringHttpResponseListener() {
            @Override
            public void onSuccess(int i, String content) {
                AbResult result = new AbResult();
                if (result.getResultCode() == AbResult.RESULT_OK) {
                    JSONObject jsonObject = new JSONObject();
                    ToastUtils.showShortToast(WaitPayActivity.this, jsonObject.optString("error-msg"));
                    if (jsonObject.optBoolean("success")) {

                    }
                } else {
                    ToastUtils.showShortToast(WaitPayActivity.this, "网络异常，数据加载失败");
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
                ToastUtils.showShortToast(WaitPayActivity.this, "网络异常，数据加载失败");
            }
        });
    }

    private void payOrder() {
        payOrderApi = new PayOrderApi();
        payOrderApi.setOrderNo(oid);
        payOrderApi.setPayMethod(payMethod);
        payOrderApi.setUid(UserInfoUtils.GetUid());
        AbHttpUtil.getInstance(this).get(payOrderApi.getUrl(), payOrderApi.getParamMap(), new AbStringHttpResponseListener() {
            @Override
            public void onSuccess(int i, String content) {
                AbResult result = new AbResult(content);
                if (result.getResultCode() == AbResult.RESULT_OK) {
                    try {
                        JSONObject jsonObject = new JSONObject(content);
                        ToastUtils.showShortToast(WaitPayActivity.this, jsonObject.optString("error-msg"));
                        if (jsonObject.optBoolean("success")) {

                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
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
                ToastUtils.showShortToast(WaitPayActivity.this, "网络异常，数据加载失败");
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == AppConfig.SELECT_ADDRESS && resultCode == RESULT_OK) {
            defaultAddressBean = (MyAddressBean) data.getSerializableExtra("myAddressBean");
            setAddress();
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
                payMethod = "1";
                break;
            case R.id.layout_payAlipay:
                iv_select_payAlipay.setVisibility(View.VISIBLE);
                payMethod = "2";
                break;
            case R.id.layout_payQQ:
                iv_select_payqq.setVisibility(View.VISIBLE);
                payMethod = "3";
                break;
        }
    }
}
