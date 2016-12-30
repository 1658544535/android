package com.qunyu.taoduoduo.activity;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.andbase.library.util.AbSharedUtil;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.qunyu.taoduoduo.BuildConfig;
import com.qunyu.taoduoduo.R;
import com.qunyu.taoduoduo.base.BaseActivity;
import com.qunyu.taoduoduo.base.BaseModel;
import com.qunyu.taoduoduo.bean.UserInfoBean;
import com.qunyu.taoduoduo.global.Constant;
import com.qunyu.taoduoduo.global.MyApplicationLike;
import com.qunyu.taoduoduo.utils.LogUtil;
import com.qunyu.taoduoduo.utils.ToastUtils;
import com.qunyu.taoduoduo.utils.UserInfoUtils;

import java.lang.reflect.Type;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Administrator on 2016/12/12.
 * 关于拼得好
 */

public class AboutPdhActivity extends BaseActivity {

    @BindView(R.id.tv_banben)
    TextView tv_banben;
    @BindView(R.id.iv_download)
    ImageView iv_download;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        baseSetContentView(R.layout.aboutpdh_activity);
        ButterKnife.bind(this);
        iv_download.setImageResource(R.mipmap.qr_code);
        LogUtil.Log(BuildConfig.VERSION_NAME + "");
        tv_banben.setText("v" + BuildConfig.VERSION_NAME);

    }

    @Override
    protected void init() {
        super.init();
        baseSetText("关于拼得好");
    }

    @OnClick(R.id.iv_download)
    public void onClick() {
        if (Constant.DEBUG) {
            UserInfoUtils.signOut();
            String xx = "{\"uid\":398,\"phone\":\"13542874154\",\"name\":\"pdh13542874154\",\"image\":\"\"}";
            Gson gson = new Gson();
            Type type = new TypeToken<UserInfoBean>() {
            }.getType();
           UserInfoBean base = gson.fromJson(xx, type);
            UserInfoUtils.setUserInfo(base);
            ToastUtils.showToast(AboutPdhActivity.this, "测试账号启动");
        }
    }
}
