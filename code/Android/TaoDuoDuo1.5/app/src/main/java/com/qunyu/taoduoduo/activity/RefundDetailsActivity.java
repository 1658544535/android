package com.qunyu.taoduoduo.activity;

import android.os.Bundle;
import android.text.Html;
import android.widget.ImageView;
import android.widget.TextView;

import com.andbase.library.http.AbHttpUtil;
import com.andbase.library.http.listener.AbStringHttpResponseListener;
import com.andbase.library.http.model.AbResult;
import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.qunyu.taoduoduo.R;
import com.qunyu.taoduoduo.api.RefundDetailsApi;
import com.qunyu.taoduoduo.base.BaseActivity;
import com.qunyu.taoduoduo.base.BaseModel;
import com.qunyu.taoduoduo.bean.RefundDetailsBean;
import com.qunyu.taoduoduo.utils.LogUtil;
import com.qunyu.taoduoduo.utils.ToastUtils;
import com.qunyu.taoduoduo.utils.UserInfoUtils;
import com.umeng.analytics.MobclickAgent;

import java.lang.reflect.Type;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2016/10/12.
 * 售后详情
 */

public class RefundDetailsActivity extends BaseActivity {
    String oid=null;//订单id

    @BindView(R.id.tv_refundType)
    TextView tv_refundType;
    @BindView(R.id.tv_refundMoney)
    TextView tv_refundM;
    @BindView(R.id.tv_refundMoneyMost)
    TextView tv_refundMs;
    @BindView(R.id.tv_refundReason)
    TextView tv_refundReason;
    @BindView(R.id.tv_refundDescribe)
    TextView tv_refundD;
    @BindView(R.id.tv_contactWay)
    TextView tv_phone;
    @BindView(R.id.iv_uploadVoucher1)
    ImageView iv_voucher1;
    @BindView(R.id.iv_uploadVoucher2)
    ImageView iv_voucher2;
    @BindView(R.id.iv_uploadVoucher3)
    ImageView iv_voucher3;


     RefundDetailsBean bean;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        baseSetContentView(R.layout.refund_details_activity);
        ButterKnife.bind(this);
        oid=getIntent().getStringExtra("oid");
        bean=new RefundDetailsBean();

    }

    @Override
    protected void init() {
        super.init();
        baseSetText("申请退款");
    }

    @Override
    protected void onResume() {
        super.onResume();
        MobclickAgent.onResume(this);
        getMsg();
    }
    public void onPause() {
        super.onPause();
        MobclickAgent.onPause(this);
    }

  private void getMsg(){
      RefundDetailsApi refundDetailsApi=new RefundDetailsApi();
      refundDetailsApi.setOid(oid);
      refundDetailsApi.setUid(UserInfoUtils.GetUid());
      AbHttpUtil.getInstance(this).get(refundDetailsApi.getUrl(), refundDetailsApi.getParamMap(), new AbStringHttpResponseListener() {
          @Override
          public void onSuccess(int i, String s) {
              AbResult result=new AbResult(s);
              if(result.getResultCode()==AbResult.RESULT_OK){
                  Gson gson=new Gson();
                  Type type=new TypeToken<BaseModel<RefundDetailsBean>>(){}.getType();
                  BaseModel<RefundDetailsBean> base=gson.fromJson(s,type);
                  if(base.result!=null){
                      bean=base.result;
                      setMsg();
                  }
              }else{
                  ToastUtils.showShortToast(RefundDetailsActivity.this, "网络异常，数据加载失败");
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
          public void onFailure(int i, String s, Throwable throwable) {
              ToastUtils.showShortToast(RefundDetailsActivity.this, throwable.getMessage());
          }
      });

  }

    private void setMsg(){
        int type = 0;
        if (bean.getType() != null) {
            type = Integer.parseInt(bean.getType());
        }
        if(type==1){
            tv_refundType.setText("退款");
        }else{
            tv_refundType.setText("退货");
        }
        tv_refundM.setText(Html.fromHtml("￥<font color=\"#FF464E\">"+bean.getRefundPrice()+"</font>"));
        tv_refundMs.setText("(最高可退 ￥"+bean.getRefundPrice()+"元)");
        String reason=null;
        switch (bean.getRefundType()){
            case "1":
                reason="没有收到货";
                break;
            case "2":
                reason="商品有质量问题";
                break;
            case "3":
                reason="商品与描述不一致";
                break;
            case "4":
                reason="商品少发漏发发错";
                break;
            case "5":
                reason="商品有划痕";
                break;
            case "6":
                reason="质疑假货";
                break;
            case "7":
                reason="其他";
                break;
        }
        tv_refundReason.setText(reason);
        tv_refundD.setText(bean.getRemarks());
        tv_phone.setText(bean.getPhone());
        Glide.with(this).load(bean.getRefundImage1()).into(iv_voucher1);
        Glide.with(this).load(bean.getRefundImage2()).into(iv_voucher2);
        Glide.with(this).load(bean.getRefundImage3()).into(iv_voucher3);
    }
}
