package com.qunyu.taoduoduo.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.qunyu.taoduoduo.R;
import com.qunyu.taoduoduo.config.AppConfig;
import com.qunyu.taoduoduo.global.MyApplication;
import com.qunyu.taoduoduo.wheel.ArrayWheelAdapter;
import com.qunyu.taoduoduo.wheel.OnWheelChangedListener;
import com.qunyu.taoduoduo.wheel.WheelView;
import com.umeng.analytics.MobclickAgent;

/**
 * Created by Administrator on 2016/11/24.
 * 申请退款界面的wheelview样式
 */

public class ApplyRefundSelectListActivity extends Activity implements OnWheelChangedListener {

    private WheelView wheelView;
    private TextView tv_cancel, tv_confirm;

    private String[] mDatas;

    private int mCurrentItem;

    private Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.applyrefund_wheelview);
        intent = getIntent();

        getWindow().setLayout(LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);
        mDatas = intent.getStringArrayExtra("mDatas");
        wheelView = (WheelView) findViewById(R.id.wv_applyrefund);
//        AbstractWheelTextAdapter.DEFAULT_TEXT_COLOR=0xFF000000;
        tv_cancel = (TextView) findViewById(R.id.tv_cancel);
        tv_confirm = (TextView) findViewById(R.id.tv_confirm);
        wheelView.setVisibleItems(7);
        wheelView.setWheelBackground(R.drawable.wheel_bg_shape);
        wheelView.setViewAdapter(new ArrayWheelAdapter<String>(this, mDatas));
        wheelView.addChangingListener(this);
        onListener();

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
        tv_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        tv_confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent.putExtra("currentItem", mCurrentItem);
                setResult(AppConfig.RESULT_REFUND, intent);
                finish();
            }
        });
    }

    @Override
    public void onChanged(WheelView wheel, int oldValue, int newValue) {
        mCurrentItem = wheel.getCurrentItem();
    }
}
