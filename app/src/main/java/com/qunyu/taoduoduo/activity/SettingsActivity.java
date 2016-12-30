package com.qunyu.taoduoduo.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.qunyu.taoduoduo.R;
import com.qunyu.taoduoduo.base.BaseActivity;
import com.qunyu.taoduoduo.base.BaseUtil;
import com.zhy.android.percent.support.PercentRelativeLayout;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Administrator on 2016/12/12.
 */

public class SettingsActivity extends BaseActivity implements View.OnClickListener {

    @BindView(R.id.layout_zhoubian)
    PercentRelativeLayout layout_zhoubian;
    @BindView(R.id.layout_aboutPdh)
    PercentRelativeLayout layout_aboutPdh;
    @BindView(R.id.layout_duihuan)
    PercentRelativeLayout layoutDuihuan;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        baseSetContentView(R.layout.settings_activity);
        ButterKnife.bind(this);
        layoutDuihuan.setVisibility(View.GONE);
    }

    @Override
    protected void init() {
        super.init();
        baseSetText("设置");
    }

    @Override
    @OnClick({R.id.layout_zhoubian, R.id.layout_aboutPdh, R.id.layout_duihuan})
    public void onClick(View v) {
        if (v == layout_zhoubian) {
            startActivity(new Intent(SettingsActivity.this, ZhouBianActivity.class));
        } else if (v == layout_aboutPdh) {
            BaseUtil.ToAc(SettingsActivity.this, AboutPdhActivity.class);
        } else if (v == layoutDuihuan) {
            BaseUtil.ToAc(SettingsActivity.this, DuiHuanActivity.class);
        }
    }
}
