package com.qunyu.taoduoduo.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;

import com.andbase.library.http.AbHttpUtil;
import com.andbase.library.http.listener.AbStringHttpResponseListener;
import com.andbase.library.http.model.AbResult;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.qunyu.taoduoduo.R;
import com.qunyu.taoduoduo.base.BaseModel;
import com.qunyu.taoduoduo.base.BaseUtil;
import com.qunyu.taoduoduo.bean.ProductTypeBean;
import com.qunyu.taoduoduo.global.Constant;
import com.qunyu.taoduoduo.utils.LogUtil;
import com.qunyu.taoduoduo.utils.ToastUtils;
import com.qunyu.taoduoduo.utils.UserInfoUtils;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class StartActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);
        //延时3秒
        ProductTypeGet();
        new Handler().postDelayed(new Runnable() {
            public void run() {
                //execute the task
                setAct();
            }
        }, 3000);
    }

    private void setAct() {
        //if (UserInfoUtils.isLogin()) {
        if (UserInfoUtils.getIsFirst()) {
            startActivity(new Intent(StartActivity.this, GuideActivity.class));
        } else {
            BaseUtil.ToAc(StartActivity.this, TabActivity.class);
        }

        //BaseUtil.ToAc(StartActivity.this, SpecialDetailActivity.class);
        //BaseUtil.ToAc(StartActivity.this, TagMainActivity.class);
        // } else {
        // startActivity(new Intent(StartActivity.this, PhoneLoginActivity.class));
//            BaseUtil.ToAc(StartActivity.this, TabActivity.class);
        //}

        finish();
    }


    public static ArrayList<ProductTypeBean> list;

    private void ProductTypeGet() {
        LogUtil.Log(Constant.productType);
        AbHttpUtil.getInstance(StartActivity.this).get(Constant.productType, new AbStringHttpResponseListener() {

            @Override
            public void onStart() {
//                AbDialogUtil.showProgressDialog(LoginActivity.this, 0, "正在登录...");
            }

            @Override
            public void onFinish() {
//                AbDialogUtil.removeDialog(LoginActivity.this);
            }

            @Override
            public void onFailure(int statusCode, String content, Throwable error) {
                ToastUtils.showShortToast(StartActivity.this, "网络异常，数据加载失败");
                LogUtil.Log(error.getMessage());
            }

            @Override
            public void onSuccess(int statusCode, String content) {
                AbResult result = new AbResult(content);
                Constant.TYPE = content;
                if (result.getResultCode() == AbResult.RESULT_OK) {
                    Gson gson = new Gson();
                    Type type = new TypeToken<BaseModel<ArrayList<ProductTypeBean>>>() {
                    }.getType();
                    BaseModel<ArrayList<ProductTypeBean>> base = gson.fromJson(content, type);
                    LogUtil.Log("onSuccess: " + content);
                    if (base.result != null) {
                        list = base.result;
//                        setTabtop(base.result);
//
//                        CheckGroupFreeGet();

                    }
                } else {
                    ToastUtils.showShortToast(StartActivity.this, "网络异常，数据加载失败");
                    LogUtil.Log(result.getResultMessage());
                }

            }
        });
    }
}
//  ┏┓　　　┏┓
//┏┛┻━━━┛┻┓
//┃　　　　　　　┃ 　
//┃　　　━　　　┃
//┃　┳┛　┗┳　┃
//┃　　　　　　　┃
//┃　　　┻　　　┃
//┃　　　　　　　┃
//┗━┓　　　┏━┛
//    ┃　　　┃  神兽保佑　　　　　　　　
//    ┃　　　┃  代码无BUG！
//    ┃　　　┗━━━┓
//    ┃　　　　　　　┣┓
//    ┃　　　　　　　┏┛
//    ┗┓┓┏━┳┓┏┛
//      ┃┫┫　┃┫┫
//      ┗┻┛　┗┻┛