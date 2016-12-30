package com.qunyu.taoduoduo.base;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.qunyu.taoduoduo.R;
import com.zhy.android.percent.support.PercentRelativeLayout;

//头部基类
public class BaseActivity extends AppCompatActivity {

    //    @BindView(R.id.iv_head_left)
    ImageView ivHeadLeft;
    //    @BindView(R.id.tv_head_title)
    TextView tvHeadTitle;
    //    @BindView(R.id.tv_head_right)
    TextView tvHeadRight;
    PercentRelativeLayout ss;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.base);
        ss = (PercentRelativeLayout) findViewById(R.id.ss);

        ivHeadLeft = (ImageView) findViewById(R.id.iv_head_left);
        tvHeadTitle = (TextView) findViewById(R.id.tv_head_title);
        tvHeadRight = (TextView) findViewById(R.id.tv_head_right);
        ivHeadLeft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        tvHeadRight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setRight();
            }
        });
        init();
    }

    View v;

    public void baseSetContentView(int layoutResId) {
        LinearLayout llContent = (LinearLayout) findViewById(R.id.ll_tp);
//        llContent.setPadding(0, BaseUtil.getStatusHeight(getApplicationContext()), 0, 0);
        LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        v = inflater.inflate(layoutResId, null);
        llContent.addView(v);
    }

    protected View getView() {
        return v;
    }

    protected void init() {
    }

    public void baseSetText(String str) {
        tvHeadTitle.setText(str);
    }

    protected void setRight() {
    }

}
