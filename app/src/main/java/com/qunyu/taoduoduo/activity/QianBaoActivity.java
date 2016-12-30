package com.qunyu.taoduoduo.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.qunyu.taoduoduo.R;
import com.qunyu.taoduoduo.base.BaseActivity;

public class QianBaoActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        baseSetContentView(R.layout.activity_qian_bao);
        baseSetText("我的钱包");
    }
}
