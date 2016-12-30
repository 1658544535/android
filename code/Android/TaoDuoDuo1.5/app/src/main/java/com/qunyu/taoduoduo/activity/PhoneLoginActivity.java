package com.qunyu.taoduoduo.activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.andbase.library.http.AbHttpUtil;
import com.andbase.library.http.listener.AbStringHttpResponseListener;
import com.andbase.library.http.model.AbResult;
import com.andbase.library.util.AbDialogUtil;
import com.geetest.android.sdk.Geetest;
import com.geetest.android.sdk.GtDialog;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.qunyu.taoduoduo.R;
import com.qunyu.taoduoduo.api.CaptchaApi;
import com.qunyu.taoduoduo.api.UserloginApi;
import com.qunyu.taoduoduo.base.BaseModel;
import com.qunyu.taoduoduo.bean.ProductTypeBean;
import com.qunyu.taoduoduo.bean.UserInfoBean;
import com.qunyu.taoduoduo.global.Constant;
import com.qunyu.taoduoduo.mvpview.PhoneLoginView;
import com.qunyu.taoduoduo.utils.LogUtil;
import com.qunyu.taoduoduo.utils.StringUtils;
import com.qunyu.taoduoduo.utils.ToastUtils;
import com.qunyu.taoduoduo.utils.UserInfoUtils;
import com.umeng.analytics.MobclickAgent;

import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import cn.jpush.android.api.JPushInterface;

public class PhoneLoginActivity extends Activity implements PhoneLoginView, View.OnClickListener {


    TextView tv_sendVerifyCode;
    //    private CountDownTimerService.ServiceBinder mBinderService;
//    Intent intent;
    TimeCount time;
    CaptchaApi captchaApi;
    String phone;
    String captcha;
    EditText et_phone;
    EditText et_captcha;
    TextView tv_login;
    int tag;
    TextView tv_cancel;
    String geekValid = "1";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_deng_lu);
        initView();
        time = new TimeCount(60000, 1000);
        captchaApi = new CaptchaApi();
        tag = getIntent().getIntExtra("tag", 0);
//        intent = new Intent(this, CountDownTimerService.class);
//        startService(intent);
        geekValid();
        captcha1.setTimeout(5000);

        captcha1.setGeetestListener(new Geetest.GeetestListener() {
            @Override
            public void readContentTimeout() {
                mGtAppDlgTask.cancel(true);
                //TODO 获取验证参数超时
                progressDialog.dismiss();
                //Looper.prepare() & Looper.loop(): 在当前线程并没有绑定Looper时返回为null, 可以与toastMsg()一同在正式版本移除
                Looper.prepare();
                Looper.loop();
            }

            @Override
            public void submitPostDataTimeout() {
                //TODO 提交二次验证超时
            }
        });

    }

    private void geekValid() {
        LogUtil.Log(Constant.geekValid);
        AbHttpUtil.getInstance(PhoneLoginActivity.this).get(Constant.geekValid, new AbStringHttpResponseListener() {

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
//                ToastUtils.showShortToast(PhoneLoginActivity.this, "网络异常，数据加载失败");
                LogUtil.Log(error.getMessage());
            }

            @Override
            public void onSuccess(int statusCode, String content) {
                AbResult result = new AbResult(content);
                Constant.TYPE = content;
                if (result.getResultCode() == AbResult.RESULT_OK) {
                    Gson gson = new Gson();
                    Type type = new TypeToken<BaseModel<String>>() {
                    }.getType();
                    BaseModel<String> base = gson.fromJson(content, type);
//                    Log.d(TAG, "onSuccess: " + content);
                    if (base.result != null) {
                        geekValid = base.result;
                    }
                } else {
//                    ToastUtils.showShortToast(PhoneLoginActivity.this, "网络异常，数据加载失败");
                    LogUtil.Log(result.getResultMessage());
                }

            }
        });

    }

    @Override
    public void initView() {
        tv_sendVerifyCode = (TextView) findViewById(R.id.tv_sendVerifyCode);
        tv_sendVerifyCode.setOnClickListener(this);
        et_phone = (EditText) findViewById(R.id.et_phone);
        et_captcha = (EditText) findViewById(R.id.et_captcha);
        tv_login = (TextView) findViewById(R.id.tv_login);
        tv_cancel = (TextView) findViewById(R.id.tv_loginCancel);
        tv_cancel.setOnClickListener(this);
        tv_login.setOnClickListener(this);

    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_login:
                login();
                break;

            case R.id.tv_sendVerifyCode:
                LogUtil.Log("tv_sendVerifyCode===============");
                phone = et_phone.getText().toString();
                if (StringUtils.isEmpty(phone)) {
                    ToastUtils.showShortToast(PhoneLoginActivity.this, "请输入您的手机号码");
                    return;
                }
                if (!StringUtils.isMobileNO(phone)) {
                    ToastUtils.showShortToast(PhoneLoginActivity.this, "你输入的手机号码格式不正确");
                    return;
                }
                if (geekValid.equals("1")) {
                    GtAppDlgTask gtAppDlgTask = new GtAppDlgTask();
                    mGtAppDlgTask = gtAppDlgTask;
                    mGtAppDlgTask.execute();

                    if (!((Activity) context).isFinishing()) {
                        progressDialog = ProgressDialog.show(context, null, "Loading", true, true);
                        progressDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
                            @Override
                            public void onCancel(DialogInterface dialog) {
                                if (mGtAppDlgTask.getStatus() == AsyncTask.Status.RUNNING) {
//                                Log.i("async task", "status running");
                                    captcha1.cancelReadConnection();
                                    mGtAppDlgTask.cancel(true);
                                } else {
//                                Log.i("async task", "No thing happen");
                                }
                            }
                        });
                    }
                } else {
                    time.start();
                    sendcaptcha();
                }
                //bindService(intent, connection, Context.BIND_AUTO_CREATE);
                break;
            case R.id.tv_loginCancel:
                Intent intent = getIntent();
                setResult(Activity.RESULT_OK, intent);
                onBackPressed();
                break;

        }
    }

    private void login() {
        phone = et_phone.getText().toString();
        if (StringUtils.isEmpty(phone)) {
            ToastUtils.showShortToast(PhoneLoginActivity.this, "请输入您的手机号码");
            return;
        }
        if (!StringUtils.isMobileNO(phone)) {
            ToastUtils.showShortToast(PhoneLoginActivity.this, "你输入的手机号码格式不正确");
            return;
        }
        captcha = et_captcha.getText().toString();
        if (StringUtils.isEmpty(captcha)) {
            ToastUtils.showShortToast(PhoneLoginActivity.this, "请输入验证码");
            return;
        }
        String deviceToken = UserInfoUtils.getDeviceToken();
        if (StringUtils.isBlank(deviceToken)) {
            deviceToken = JPushInterface.getRegistrationID(getApplication());
            UserInfoUtils.setDeviceToken(deviceToken);
        }
        LogUtil.Log("deviceToken:" + deviceToken);
        UserloginApi userloginApi = new UserloginApi();
        userloginApi.setPhone(phone);
        userloginApi.setBaidu_uid(deviceToken);
        userloginApi.setCaptcha(captcha);
        LogUtil.Log(userloginApi.getUrl() + "?" + userloginApi.getParamMap().getParamString());
        AbHttpUtil.getInstance(PhoneLoginActivity.this).post(userloginApi.getUrl(), userloginApi.getParamMap(), new AbStringHttpResponseListener() {
            @Override
            public void onSuccess(int i, String s) {
                LogUtil.Log(s);
                AbResult result = new AbResult(s);
                LogUtil.Log(result.toString());
                if (result.getResultCode() == AbResult.RESULT_OK) {
                    Gson gson = new Gson();
                    Type type = new TypeToken<BaseModel<UserInfoBean>>() {
                    }.getType();
                    BaseModel<UserInfoBean> base = gson.fromJson(s, type);
                    if (base != null && base.success) {
                        UserInfoUtils.setUserInfo(base.result);
                        setResult(RESULT_OK);
                        if (tag == 0) {
                            startActivity(new Intent(PhoneLoginActivity.this, TabActivity.class));
                        }
                        finish();
                    }
                    ToastUtils.showShortToast(PhoneLoginActivity.this, base.error_msg);

                } else {
                    ToastUtils.showToast(PhoneLoginActivity.this, "网络异常，加载失败！");
                }

            }

            @Override
            public void onStart() {
                // AbDialogUtil.showProgressDialog(PhoneLoginActivity.this, 0, "正在登录...");

            }

            @Override
            public void onFinish() {
                AbDialogUtil.removeDialog(PhoneLoginActivity.this);
            }

            @Override
            public void onFailure(int i, String s, Throwable throwable) {
                LogUtil.Log(s);
                ToastUtils.showToast(PhoneLoginActivity.this, "网络异常，加载失败！");
            }
        });
    }

    private void sendcaptcha() {
        captchaApi.setPhone(phone);
        LogUtil.Log(captchaApi.getUrl() + "?" + captchaApi.getParamMap().getParamString());
        AbHttpUtil.getInstance(PhoneLoginActivity.this).post(captchaApi.getUrl(), captchaApi.getParamMap()
                , new AbStringHttpResponseListener() {

                    @Override
                    public void onStart() {

                    }

                    @Override
                    public void onFinish() {


                    }

                    @Override
                    public void onFailure(int i, String s, Throwable error) {
                        ToastUtils.showToast(PhoneLoginActivity.this, "网络异常，加载失败！");
                    }

                    @Override
                    public void onSuccess(int statusCode, String content) {
                        LogUtil.Log(content);
                        AbResult result = new AbResult(content);
                        if (result.getResultCode() == AbResult.RESULT_OK) {
                            Gson gson = new Gson();
                            Type type = new TypeToken<BaseModel<Object>>() {
                            }.getType();
                            BaseModel<Object> base = gson.fromJson(content, type);
                            if (base != null) {
                                ToastUtils.showShortToast(PhoneLoginActivity.this, base.error_msg);
                            }

                        } else {
                            ToastUtils.showToast(PhoneLoginActivity.this, "网络异常，加载失败！");
                        }
                    }
                });

    }


//    ServiceConnection connection = new ServiceConnection() {
//        @Override
//        public void onServiceConnected(ComponentName name, IBinder service) {
//            mBinderService = (CountDownTimerService.ServiceBinder) service;
//            //countDownTimerService.setCountDownTimerListener(new CountDownTimerListener() {
//                @Override
//                public void onFinish() {
//                    tv_sendVerifyCode.setText(getString(R.string.dl_fayan));
//                    tv_sendVerifyCode.setTextColor(ContextCompat.getColor(PhoneLoginActivity.this, R.color.base_red));
//                    tv_sendVerifyCode.setClickable(true);
//                    stopService(intent);
//                }
//
//                @Override
//                public void onTick(long millisUntilFinished) {
//                    LogUtil.Log("millisUntilFinished++++++++++++"+millisUntilFinished);
//                    tv_sendVerifyCode.setClickable(false);
//                    tv_sendVerifyCode.setTextColor(ContextCompat.getColor(PhoneLoginActivity.this, R.color.gray_text));
//                    // btn_sendCaptcha.setText(millisUntilFinished /1000+"秒");
//                    tv_sendVerifyCode.setText("请稍等...(" + millisUntilFinished / 1000
//                            + ")");
//                }
//            });
//        }
//
//        @Override
//        public void onServiceDisconnected(ComponentName name) {
//
//        }
//    };

    @Override
    protected void onDestroy() {
        //countDownTimerService.setCountDownTimerListener(null);
        super.onDestroy();
    }


    // 验证码倒计时
    class TimeCount extends CountDownTimer {

        public TimeCount(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);// 参数依次为总时长,和计时的时间间隔
        }

        @Override
        public void onFinish() {// 计时完毕时触发
            tv_sendVerifyCode.setText("获取验证码");
            tv_sendVerifyCode.setClickable(true);
        }

        @Override
        public void onTick(long millisUntilFinished) {// 计时过程显示
            tv_sendVerifyCode.setClickable(false);
            // btn_sendCaptcha.setText(millisUntilFinished /1000+"秒");
            tv_sendVerifyCode.setText("请稍等...(" + millisUntilFinished / 1000
                    + ")");
        }

    }

    public void onResume() {
        super.onResume();
        MobclickAgent.onResume(this);
    }

    public void onPause() {
        super.onPause();
        MobclickAgent.onPause(this);
    }

    @Override
    public void onBackPressed() {
        InputMethodManager inputMethodManager =
                (InputMethodManager) getApplicationContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(et_captcha.getWindowToken(), 0);
        super.onBackPressed();
    }

    private Context context = PhoneLoginActivity.this;
    //因为可能用户当时所处在低速高延迟网络，所以异步请求可能在后台用时很久才获取到验证的数据。可以自己设计状态指示器, demo仅作演示。
    private ProgressDialog progressDialog;
    private GtAppDlgTask mGtAppDlgTask;

    // 创建验证码网络管理器实例
    private Geetest captcha1 = new Geetest(

            // 设置获取id，challenge，success的URL，需替换成自己的服务器URL
            Constant.BASEURL + "getValidCode.do",

            // 设置二次验证的URL，需替换成自己的服务器URL
            "http://api.apiapp.cc/gtcap/gt-server-validate/"
    );

    class GtAppDlgTask extends AsyncTask<Void, Void, Boolean> {

        @Override
        protected Boolean doInBackground(Void... params) {

            return captcha1.checkServer();
        }

        @Override
        protected void onPostExecute(Boolean result) {

            if (result) {

                // 根据captcha.getSuccess()的返回值 自动推送正常或者离线验证
                if (captcha1.getSuccess()) {
                    openGtTest(context, captcha1.getGt(), captcha1.getChallenge(), captcha1.getSuccess());
                } else {
                    // TODO 从API_1获得极验服务宕机或不可用通知, 使用备用验证或静态验证
                    // 静态验证依旧调用上面的openGtTest(_, _, _), 服务器会根据getSuccess()的返回值, 自动切换
                    // openGtTest(context, captcha.getGt(), captcha.getChallenge(), captcha.getSuccess());

                    // 执行此处网站主的备用验证码方案
                }

            } else {
            }
        }
    }

    public void openGtTest(Context ctx, String id, String challenge, boolean success) {

        GtDialog dialog = new GtDialog(ctx, id, challenge, success);

        // 启用debug可以在webview上看到验证过程的一些数据
//        dialog.setDebug(true);

        dialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialog) {
                //TODO 取消验证
            }
        });

        dialog.setGtListener(new GtDialog.GtListener() {

            @Override
            public void gtResult(boolean success, String result) {

                if (success) {


                    try {

                        //TODO 验证通过, 获取二次验证响应, 根据响应判断验证是否通过完整验证
                        time.start();
                        sendcaptcha();
                    } catch (Exception e) {

                        e.printStackTrace();
                    }

                } else {
                    //TODO 验证失败
                }
            }

            @Override
            public void gtCallClose() {

            }

            @Override
            public void gtCallReady(Boolean status) {

                progressDialog.dismiss();

                if (status) {
                    //TODO 验证加载完成
                } else {
                    //TODO 验证加载超时,未准备完成
                }
            }

            @Override
            public void gtError() {
                progressDialog.dismiss();
            }

        });

    }

}
