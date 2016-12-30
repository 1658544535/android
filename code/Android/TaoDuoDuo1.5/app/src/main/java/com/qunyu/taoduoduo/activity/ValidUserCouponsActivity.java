package com.qunyu.taoduoduo.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.andbase.library.http.AbHttpUtil;
import com.andbase.library.http.listener.AbStringHttpResponseListener;
import com.andbase.library.http.model.AbResult;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.qunyu.taoduoduo.R;
import com.qunyu.taoduoduo.adapter.CouponsAdapter;
import com.qunyu.taoduoduo.api.GetValidUserCouponApi;
import com.qunyu.taoduoduo.base.BaseActivity;
import com.qunyu.taoduoduo.base.BaseModel;
import com.qunyu.taoduoduo.bean.MyCouponsListBean;
import com.qunyu.taoduoduo.utils.LogUtil;
import com.qunyu.taoduoduo.utils.ToastUtils;
import com.qunyu.taoduoduo.utils.UserInfoUtils;
import com.umeng.analytics.MobclickAgent;
import com.zhy.android.percent.support.PercentRelativeLayout;

import java.lang.reflect.Type;
import java.util.ArrayList;

/**
 * 订单可用优惠券
 */

public class ValidUserCouponsActivity extends BaseActivity {
    ListView lv_mycoupon;
    GetValidUserCouponApi getValidUserCouponApi;
    CouponsAdapter couponsAdapter;
    String pid;
    String price;
    PercentRelativeLayout layout_couponNull;
    private ArrayList<MyCouponsListBean.CouponList> couponList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        baseSetContentView(R.layout.order_coupon_activity);
        lv_mycoupon = (ListView) findViewById(R.id.lv_mycoupon);
        layout_couponNull = (PercentRelativeLayout) findViewById(R.id.layout_couponNull);
        pid = getIntent().getStringExtra("pid");
        price = getIntent().getStringExtra("price");
        getCouponMsg();
        lv_mycoupon.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                MyCouponsListBean.CouponList couponbean = (MyCouponsListBean.CouponList) parent.getAdapter().getItem(position);
                Intent intent = new Intent();
                intent.putExtra("couponbean", couponbean);
                setResult(RESULT_OK, intent);
                finish();

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
        baseSetText("优惠券");

    }

    public void getCouponMsg() {
        getValidUserCouponApi = new GetValidUserCouponApi();
        getValidUserCouponApi.setPid(pid);
        getValidUserCouponApi.setPrice(price);
        getValidUserCouponApi.setUid(UserInfoUtils.GetUid());
        LogUtil.Log(getValidUserCouponApi.getUrl() + "?" + getValidUserCouponApi.getParamMap().getParamString());
        AbHttpUtil.getInstance(ValidUserCouponsActivity.this).get(getValidUserCouponApi.getUrl(), getValidUserCouponApi.getParamMap(), new AbStringHttpResponseListener() {
            @Override
            public void onSuccess(int statusCode, String content) {
                LogUtil.Log(content);
                AbResult result = new AbResult(content);
                if (result.getResultCode() == AbResult.RESULT_OK) {
                    Gson gson = new Gson();
                    Type type = new TypeToken<BaseModel<ArrayList<MyCouponsListBean.CouponList>>>() {
                    }.getType();
                    BaseModel<ArrayList<MyCouponsListBean.CouponList>> base = gson.fromJson(content, type);
                    if (base.success && base != null && !base.result.isEmpty()) {
                        couponList = base.result;
                        couponsAdapter = new CouponsAdapter(ValidUserCouponsActivity.this, couponList);
                        lv_mycoupon.setAdapter(couponsAdapter);

                    } else {
                        layout_couponNull.setVisibility(View.VISIBLE);
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
                ToastUtils.showShortToast(ValidUserCouponsActivity.this, "网络异常，数据加载失败");
            }
        });

    }
}
