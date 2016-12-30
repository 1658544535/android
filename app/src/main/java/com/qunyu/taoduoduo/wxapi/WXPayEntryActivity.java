package com.qunyu.taoduoduo.wxapi;


import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.andbase.library.http.AbHttpUtil;
import com.andbase.library.http.listener.AbStringHttpResponseListener;
import com.andbase.library.http.model.AbRequestParams;
import com.andbase.library.http.model.AbResult;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.qunyu.taoduoduo.R;
import com.qunyu.taoduoduo.activity.ConfirmOrderActivity;
import com.qunyu.taoduoduo.activity.GroupDetailActivity;
import com.qunyu.taoduoduo.activity.OrdersActivity;
import com.qunyu.taoduoduo.base.BaseModel;
import com.qunyu.taoduoduo.bean.OrderDetailInfoBean;
import com.qunyu.taoduoduo.config.AppConfig;
import com.qunyu.taoduoduo.presenter.QueryPayStatusPresenter;
import com.qunyu.taoduoduo.utils.LogUtil;
import com.qunyu.taoduoduo.utils.StringUtils;
import com.qunyu.taoduoduo.utils.ToastUtils;
import com.tencent.mm.sdk.constants.ConstantsAPI;
import com.tencent.mm.sdk.modelbase.BaseReq;
import com.tencent.mm.sdk.modelbase.BaseResp;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.IWXAPIEventHandler;
import com.tencent.mm.sdk.openapi.WXAPIFactory;

import java.lang.reflect.Type;

import static com.qunyu.taoduoduo.global.Constant.orderDetailApi;


public class WXPayEntryActivity extends Activity implements IWXAPIEventHandler {


    private IWXAPI api;
    QueryPayStatusPresenter queryPayStatusPresenter;
    public static String outTradeNo;
    public static String oid;
    TextView tv_message;
    TextView tv_head_title;
    ImageView iv_paystatus;
    ImageView iv_head_left;
    String attendId;
    public static String source;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pay_result_wx);
        api = WXAPIFactory.createWXAPI(this, AppConfig.APP_ID);
        api.handleIntent(getIntent(), this);
        tv_head_title = (TextView) findViewById(R.id.tv_head_title);
        tv_head_title.setText("支付结果");
        LogUtil.Log("source" + source);
        LogUtil.Log("outTradeNo" + outTradeNo);
        LogUtil.Log("oid" + oid);
        try {
            ConfirmOrderActivity.instance.finish();
        } catch (Exception e) {
            e.printStackTrace();
        }


    }


    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
        api.handleIntent(intent, this);
    }

    @Override
    public void onReq(BaseReq req) {
    }

    @Override
    public void onResp(BaseResp resp) {
        tv_message = (TextView) findViewById(R.id.tv_message);
        iv_paystatus = (ImageView) findViewById(R.id.iv_paystatus);
        queryPayStatusPresenter = new QueryPayStatusPresenter(this);
        queryPayStatusPresenter.loadData(outTradeNo, "2");
        iv_head_left = (ImageView) findViewById(R.id.iv_head_left);
        OrderdetailGet();
        if (resp.getType() == ConstantsAPI.COMMAND_PAY_BY_WX) {
            LogUtil.Log("errCode:" + resp.errCode + "errStr:" + resp.errStr);
            switch (resp.errCode) {
                case 0:
                    iv_paystatus.setImageResource(R.mipmap.pay_success);
                    tv_message.setTextColor(getResources().getColor(
                            R.color.text_pay_success));
                    tv_message.setText("支付成功");
                    iv_head_left.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if (StringUtils.isNotBlank(attendId) && StringUtils.isNotBlank(source)&&!source.equals("4")) {
                                if(source.equals("5")){
                                    //0.1秒杀
                                    Intent intent = new Intent(WXPayEntryActivity.this, GroupDetailActivity.class);
                                    intent.putExtra("recordId", attendId);
                                    intent.putExtra("source", source);
                                    intent.putExtra("tag", "pay");
                                    startActivity(intent);
                                }else {
                                    Intent intent = new Intent(WXPayEntryActivity.this, GroupDetailActivity.class);
                                    intent.putExtra("recordId", attendId);
                                    intent.putExtra("source", source);
                                    intent.putExtra("tag", "pay");
                                    startActivity(intent);
                                }



                            } else {
                                Intent intent = new Intent(WXPayEntryActivity.this, OrdersActivity.class);
                                //intent.putExtra("source","1.1");
                                intent.putExtra("page", "0");
                                startActivity(intent);
                            }
                            finish();

                        }
                    });

                    break;
                case -1:
                    iv_paystatus.setImageResource(R.mipmap.pay_fail);
                    tv_message.setTextColor(getResources().getColor(
                            R.color.text_pay_fail));
                    tv_message.setText("发起支付失败");
                    iv_head_left.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent intent = new Intent(WXPayEntryActivity.this, OrdersActivity.class);
                            //intent.putExtra("source","1.1");
                            intent.putExtra("page", "0");
                            startActivity(intent);
                            finish();
                        }
                    });
                    break;
                case -2:
                    iv_paystatus.setImageResource(R.mipmap.pay_fail);
                    tv_message.setTextColor(getResources().getColor(
                            R.color.text_pay_fail));
                    tv_message.setText("支付取消");
                    iv_head_left.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent intent = new Intent(WXPayEntryActivity.this, OrdersActivity.class);
                            //intent.putExtra("source","1.1");
                            intent.putExtra("page", "0");
                            startActivity(intent);
                            finish();
                        }
                    });
                    break;

            }

        }
    }

    private void OrderdetailGet() {
        AbRequestParams params = new AbRequestParams();
        params.put("oid", oid);
        LogUtil.Log(orderDetailApi + "?oid=" + oid);
        AbHttpUtil.getInstance(this).get(orderDetailApi, params, new AbStringHttpResponseListener() {

            @Override
            public void onStart() {
                showLoading("数据加载中....");
            }

            @Override
            public void onFinish() {
               hideLoading();
            }

            @Override
            public void onFailure(int statusCode, String content, Throwable error) {
                ToastUtils.showShortToast(WXPayEntryActivity.this, "网络异常，数据加载失败");
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
                        //ToastUtils.showShortToast(getApplicationContext(),attendId);

                    } else {
                        ToastUtils.showShortToast(WXPayEntryActivity.this, base.error_msg);
                    }
                } else {
                    ToastUtils.showShortToast(WXPayEntryActivity.this, "网络异常，数据加载失败");
                    LogUtil.Log(result.getResultMessage());
                }

            }
        });

    }

    ProgressDialog progressDialog;

    void showLoading(String msg) {
        if (progressDialog == null) {
            progressDialog = new ProgressDialog(WXPayEntryActivity.this);
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