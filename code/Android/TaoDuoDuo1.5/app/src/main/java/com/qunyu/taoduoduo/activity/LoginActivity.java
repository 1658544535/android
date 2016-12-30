package com.qunyu.taoduoduo.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;

import com.qunyu.taoduoduo.R;
import com.umeng.analytics.MobclickAgent;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener{
    ImageView iv_phone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_deng_lu3);
        initView();
    }
    public void onResume() {
        super.onResume();
        MobclickAgent.onResume(this);
    }
    public void onPause() {
        super.onPause();
        MobclickAgent.onPause(this);
    }
    private void initView() {
        iv_phone = (ImageView) findViewById(R.id.iv_phone);
        iv_phone.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.iv_phone:
                startActivity(new Intent(LoginActivity.this,PhoneLoginActivity.class));
                finish();
                break;
        }
    }
}
