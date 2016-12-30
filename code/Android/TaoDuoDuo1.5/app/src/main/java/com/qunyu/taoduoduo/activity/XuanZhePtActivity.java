package com.qunyu.taoduoduo.activity;

import android.os.Bundle;

import com.qunyu.taoduoduo.R;
import com.qunyu.taoduoduo.base.BaseActivity;

public class XuanZhePtActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_xuan_zhe_pt);
    }

    @Override
    protected void init() {
        super.init();
        baseSetText("选择拼团");
    }
}
