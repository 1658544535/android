package com.qunyu.taoduoduo.activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import com.qunyu.taoduoduo.R;
import com.qunyu.taoduoduo.base.BaseUtil;
import com.qunyu.taoduoduo.mvpview.EventActivityView;
import com.qunyu.taoduoduo.presenter.EventPresenter;
import com.qunyu.taoduoduo.utils.ToastUtils;
import com.qunyu.taoduoduo.utils.UserInfoUtils;

/**
 * 现金红包活动
 */

public class EventActivity extends Activity implements View.OnClickListener,EventActivityView {

    ImageView iv_take;
    ProgressDialog progressDialog;
    EventPresenter presenter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event);
        initView();
        presenter = new EventPresenter(this,this);

    }

    public boolean onTouchEvent(MotionEvent event) {
        finish();
        return true;
    }

    @Override
    public void onClick(View v) {
        if (UserInfoUtils.isLogin()) {
            presenter.loadData();
        } else {
            BaseUtil.ToAc(EventActivity.this, PhoneLoginActivity.class);
        }

    }

    @Override
    public void initView() {
        iv_take = (ImageView) findViewById(R.id.iv_take);
        iv_take.setOnClickListener(this);
    }

    @Override
    public void showProgressDialog(String msg) {
        if (progressDialog == null) {
            progressDialog = new ProgressDialog(EventActivity.this);
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
    public void toastMessage(String msg) {
        ToastUtils.showShortToast(EventActivity.this,msg);
    }

    @Override
    public void loadSuccess() {
        startActivity(new Intent(EventActivity.this,CouponsActivity.class));
        finish();
    }

    @Override
    public void onFinish() {
        finish();
    }
}
