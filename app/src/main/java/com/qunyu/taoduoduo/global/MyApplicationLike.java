package com.qunyu.taoduoduo.global;

import android.annotation.TargetApi;
import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.content.res.AssetManager;
import android.content.res.Resources;
import android.os.Build;
import android.support.multidex.MultiDex;

import com.andbase.library.config.AbAppConfig;
import com.meiqia.core.callback.OnInitCallback;
import com.meiqia.meiqiasdk.util.MQConfig;
import com.qunyu.taoduoduo.bean.UserInfoBean;
import com.qunyu.taoduoduo.utils.LogUtil;
import com.qunyu.taoduoduo.utils.UserInfoUtils;
import com.tencent.bugly.Bugly;
import com.tencent.bugly.beta.Beta;
import com.tencent.tinker.loader.app.DefaultApplicationLike;
import com.umeng.analytics.MobclickAgent;
import com.umeng.message.IUmengRegisterCallback;
import com.umeng.message.PushAgent;
import com.umeng.socialize.PlatformConfig;
import com.umeng.socialize.UMShareAPI;

import cn.jpush.android.api.JPushInterface;

/**
 * application代理类
 */

public class MyApplicationLike extends DefaultApplicationLike {
    public static final String TAG = "Tinker.MyApplicationLike";

    public MyApplicationLike(Application application, int tinkerFlags,
                             boolean tinkerLoadVerifyFlag, long applicationStartElapsedTime,
                             long applicationStartMillisTime, Intent tinkerResultIntent, Resources[] resources,
                             ClassLoader[] classLoader, AssetManager[] assetManager) {
        super(application, tinkerFlags, tinkerLoadVerifyFlag, applicationStartElapsedTime,
                applicationStartMillisTime, tinkerResultIntent, resources, classLoader,
                assetManager);
    }


    @Override
    public void onCreate() {
        super.onCreate();
        context = getApplication().getBaseContext();
        loginUser = UserInfoUtils.getUserInfo();
        // 这里实现SDK初始化，appId替换成你的在Bugly平台申请的appId
        /**
         * 设置启动延时为15s（默认延时3s），APP启动12s后初始化SDK，避免影响APP启动速度;
         */
        Beta.initDelay = 12*1000;
        Bugly.init(getApplication(), "f7a2b539e4", false);
        AbAppConfig.USER_AGENT = "PinDeHaoAndroid";
        MobclickAgent.setScenarioType(getApplication(), MobclickAgent.EScenarioType.E_UM_NORMAL);
        System.setProperty("http.agent", "PinDeHaoAndroid");
        PushAgent mPushAgent = PushAgent.getInstance(getApplication());
        //注册推送服务，每次调用register方法都会回调该接口
        mPushAgent.register(new IUmengRegisterCallback() {

            @Override
            public void onSuccess(String deviceToken) {
                //注册成功会返回device token
                LogUtil.Log("deviceToken:", deviceToken);
                UserInfoUtils.setDeviceToken(deviceToken);
            }

            @Override
            public void onFailure(String s, String s1) {
                LogUtil.Log("deviceTokenfail:", s + "|" + s1);
            }
        });
        MQConfig.init(getApplication(), "5c41509e6f8988fb7ecc31cc445e86df", new OnInitCallback() {
            @Override
            public void onSuccess(String clientId) {
//                Toast.makeText(MainActivity.this, "init success", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(int code, String message) {
//                Toast.makeText(MainActivity.this, "int failure", Toast.LENGTH_SHORT).show();
            }
        });
        MQConfig.isShowClientAvatar = true;
        MQConfig.isLoadMessagesFromNativeOpen = true;
        UMShareAPI.get(getApplication());
        PlatformConfig.setWeixin("wx86d84e4f49f10c88", "5ce38eb1db67ab9976d001e6dc0d22cc");
        PlatformConfig.setSinaWeibo("655720738", "3ff4e846e0d5b2324ae6de160a64c014");

        //极光推送初始化
        JPushInterface.setDebugMode(false);
        JPushInterface.init(getApplication());
        JPushInterface.getRegistrationID(getApplication());
        LogUtil.Log("RegistrationID:", JPushInterface.getRegistrationID(getApplication()));
    }


    @TargetApi(Build.VERSION_CODES.ICE_CREAM_SANDWICH)
    @Override
    public void onBaseContextAttached(Context base) {
        super.onBaseContextAttached(base);
        // you must install multiDex whatever tinker is installed!
        MultiDex.install(base);

        // 安装tinker
        // TinkerManager.installTinker(this); 替换成下面Bugly提供的方法
        Beta.installTinker(this);
    }

    @TargetApi(Build.VERSION_CODES.ICE_CREAM_SANDWICH)
    public void registerActivityLifecycleCallback(Application.ActivityLifecycleCallbacks callbacks) {
        getApplication().registerActivityLifecycleCallbacks(callbacks);
    }


    private static UserInfoBean loginUser;
    public static Context context;
    public static UserInfoBean getLoginUser() {
        if (loginUser == null) {
            loginUser = UserInfoUtils.getUserInfo();
        }
        return loginUser;
    }
    public static void setLoginUser(UserInfoBean u) {
        loginUser = u;
    }

}
