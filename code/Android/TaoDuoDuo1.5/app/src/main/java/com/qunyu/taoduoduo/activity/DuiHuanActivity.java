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
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.andbase.library.http.AbHttpUtil;
import com.andbase.library.http.listener.AbStringHttpResponseListener;
import com.andbase.library.http.model.AbResult;
import com.geetest.android.sdk.Geetest;
import com.geetest.android.sdk.GtDialog;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.qunyu.taoduoduo.R;
import com.qunyu.taoduoduo.base.BaseModel;
import com.qunyu.taoduoduo.fragment.ZhoubianDialogFragment;
import com.qunyu.taoduoduo.global.Constant;
import com.qunyu.taoduoduo.mvpview.ZhouBianView;
import com.qunyu.taoduoduo.presenter.ZhouBianPresenter;
import com.qunyu.taoduoduo.utils.LogUtil;
import com.qunyu.taoduoduo.utils.StringUtils;
import com.qunyu.taoduoduo.utils.ToastUtils;
import com.qunyu.taoduoduo.utils.UserInfoUtils;
import com.umeng.analytics.MobclickAgent;

import java.lang.reflect.Type;

import cn.jpush.android.api.JPushInterface;

public class DuiHuanActivity extends Activity implements View.OnClickListener, ZhouBianView {

    EditText et_phone;
    EditText et_captcha;
    TextView tv_captcha;
    EditText et_code;
    TextView tv_btn;

    ProgressDialog progressDialog;

    ZhouBianPresenter presenter;
    TimeCount time;

    String phone;
    String code;
    String couponNo;
    String geekValid = "1";
    ImageView iv_back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_zhoubian);
        initView();
        presenter = new ZhouBianPresenter(this, this);
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
        AbHttpUtil.getInstance(DuiHuanActivity.this).get(Constant.geekValid, new AbStringHttpResponseListener() {

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
//                ToastUtils.showShortToast(ZhouBianActivity.this, "网络异常，数据加载失败");
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
//                    ToastUtils.showShortToast(ZhouBianActivity.this, "网络异常，数据加载失败");
                    LogUtil.Log(result.getResultMessage());
                }

            }
        });

    }

    @Override
    public void onResume() {
        super.onResume();
        MobclickAgent.onResume(this);
    }

    @Override
    public void onPause() {
        super.onPause();
        MobclickAgent.onPause(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_captcha:
                LogUtil.Log("sendVerifyCode===============");
                phone = et_phone.getText().toString();
                if (StringUtils.isBlank(phone)) {
                    toastMessage("请输入您的手机号码");
                    return;
                }
                if (!StringUtils.isMobileNO(phone)) {
                    toastMessage("你输入的手机号码格式不正确");
                    return;
                }
                if (geekValid.equals("1")) {
                    GtAppDlgTask gtAppDlgTask = new GtAppDlgTask();
                    mGtAppDlgTask = gtAppDlgTask;
                    mGtAppDlgTask.execute();

                    if (!((Activity) context).isFinishing()) {
                        progressDialog1 = ProgressDialog.show(context, null, "Loading", true, true);
                        progressDialog1.setOnCancelListener(new DialogInterface.OnCancelListener() {
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
                    presenter.sendcaptcha(phone);
                }
                break;
            case R.id.tv_btn:
                if (UserInfoUtils.isLogin()) {
                    couponNo = et_code.getText().toString();
                    if (StringUtils.isEmpty(couponNo)) {
                        toastMessage("请输入兑换码");
                        return;
                    }
                    presenter.addUserCoupon(couponNo);
                } else {
                    phone = et_phone.getText().toString();
                    if (StringUtils.isEmpty(phone)) {
                        toastMessage("请输入您的手机号码");
                        return;
                    }
                    if (!StringUtils.isMobileNO(phone)) {
                        toastMessage("你输入的手机号码格式不正确");
                        return;
                    }
                    String captcha = et_captcha.getText().toString();
                    if (StringUtils.isEmpty(captcha)) {
                        toastMessage("请输入验证码");
                        return;
                    }
                    couponNo = et_code.getText().toString();
                    if (StringUtils.isEmpty(couponNo)) {
                        toastMessage("请输入兑换码");
                        return;
                    }
                    String deviceToken = UserInfoUtils.getDeviceToken();
                    if (StringUtils.isBlank(deviceToken)) {
                        deviceToken = JPushInterface.getRegistrationID(getApplication());
                        UserInfoUtils.setDeviceToken(deviceToken);
                    }
                    presenter.login(phone, deviceToken, captcha, couponNo);

                }
                break;
            case R.id.iv_back:
                onBackPressed();
                break;
        }
    }

    @Override
    public void initView() {
        et_phone = (EditText) findViewById(R.id.et_phone);
        et_captcha = (EditText) findViewById(R.id.et_captcha);
        tv_captcha = (TextView) findViewById(R.id.tv_captcha);
        et_code = (EditText) findViewById(R.id.et_code);
        tv_btn = (TextView) findViewById(R.id.tv_btn);
        tv_btn.setOnClickListener(this);
        tv_captcha.setOnClickListener(this);
        LogUtil.Log("login", UserInfoUtils.isLogin() + "");
        if (!UserInfoUtils.isLogin()) {
            et_phone.setVisibility(View.VISIBLE);
            et_captcha.setVisibility(View.VISIBLE);
            tv_captcha.setVisibility(View.VISIBLE);
            time = new TimeCount(60000, 1000);
        }
        iv_back = (ImageView) findViewById(R.id.iv_back);
        iv_back.setOnClickListener(this);
    }

    @Override
    public void showProgressDialog(String msg) {
        if (progressDialog == null) {
            progressDialog = new ProgressDialog(this);
        }
        progressDialog.setMessage(msg);
        progressDialog.setCancelable(false);
        progressDialog.show();
    }

    @Override
    public void removeDialog() {
        if (progressDialog != null && progressDialog.isShowing()) {
            progressDialog.dismiss();
        }
    }

    @Override
    public void toastMessage(String msg) {
        ToastUtils.showShortToast(this, msg);
    }

    @Override
    public void addUserCouponSucess() {
//        new AlertDialog.Builder(this)
//                .setTitle("提示").setMessage("亲，您的优惠券已兑换成功！\n您可到个人中心-我的优惠券查看")
//                .setPositiveButton("继续逛", new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialog, int which) {
//                        dialog.dismiss();
//                        startActivity(new Intent(ZhouBianActivity.this, TabActivity.class));
//                        finish();
//                    }
//                })
//                .setNegativeButton("去查看", new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialog, int which) {
//                        dialog.dismiss();
//                        startActivity(new Intent(ZhouBianActivity.this, CouponsActivity.class));
//                        finish();
//                    }
//                }).create().show();

        new ZhoubianDialogFragment.Builder(this)
                .setSex1ClickListener(new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        startActivity(new Intent(DuiHuanActivity.this, CouponsActivity.class));
                        finish();
                    }
                })
                .setSex2ClickListener(new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {


                        dialog.dismiss();
                        startActivity(new Intent(DuiHuanActivity.this, TabActivity.class));
                        finish();
                    }
                }).create().show();


    }

    @Override
    public void setLoginView() {
        et_phone.setVisibility(View.GONE);
        et_captcha.setVisibility(View.GONE);
        tv_captcha.setVisibility(View.GONE);
    }

    // 验证码倒计时
    class TimeCount extends CountDownTimer {

        public TimeCount(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);// 参数依次为总时长,和计时的时间间隔
        }

        @Override
        public void onFinish() {// 计时完毕时触发
            try {
                tv_captcha.setText("获取验证码");
                tv_captcha.setClickable(true);
            } catch (Exception e) {
                e.printStackTrace();
            }

        }

        @Override
        public void onTick(long millisUntilFinished) {// 计时过程显示
            try {
                tv_captcha.setClickable(false);
                // btn_sendCaptcha.setText(millisUntilFinished /1000+"秒");
                tv_captcha.setText("请稍等...(" + millisUntilFinished / 1000
                        + ")");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }

    private Context context = DuiHuanActivity.this;
    //因为可能用户当时所处在低速高延迟网络，所以异步请求可能在后台用时很久才获取到验证的数据。可以自己设计状态指示器, demo仅作演示。
    private ProgressDialog progressDialog1;
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
                        presenter.sendcaptcha(phone);
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

                progressDialog1.dismiss();

                if (status) {
                    //TODO 验证加载完成
                } else {
                    //TODO 验证加载超时,未准备完成
                }
            }

            @Override
            public void gtError() {
                progressDialog1.dismiss();
            }

        });

    }

}
