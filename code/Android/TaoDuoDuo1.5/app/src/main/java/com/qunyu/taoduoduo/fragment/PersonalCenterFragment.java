package com.qunyu.taoduoduo.fragment;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.andbase.library.http.AbHttpUtil;
import com.andbase.library.http.listener.AbStringHttpResponseListener;
import com.andbase.library.http.model.AbResult;
import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.meiqia.core.MQManager;
import com.meiqia.core.bean.MQMessage;
import com.meiqia.core.callback.OnClientInfoCallback;
import com.meiqia.core.callback.OnGetMessageListCallback;
import com.meiqia.meiqiasdk.util.MQIntentBuilder;
import com.qunyu.taoduoduo.R;
import com.qunyu.taoduoduo.activity.AfterSaleActivity;
import com.qunyu.taoduoduo.activity.CouponsActivity;
import com.qunyu.taoduoduo.activity.EditUserInfoActivity;
import com.qunyu.taoduoduo.activity.MyAddressActivity;
import com.qunyu.taoduoduo.activity.MyCaiJiaActivity;
import com.qunyu.taoduoduo.activity.MyCollectListActivity;
import com.qunyu.taoduoduo.activity.MyDrawsActivity;
import com.qunyu.taoduoduo.activity.MyGroupListActivity;
import com.qunyu.taoduoduo.activity.OrdersActivity;
import com.qunyu.taoduoduo.activity.PhoneLoginActivity;
import com.qunyu.taoduoduo.activity.QianBaoActivity;
import com.qunyu.taoduoduo.activity.SettingsActivity;
import com.qunyu.taoduoduo.activity.TuanMianActivity;
import com.qunyu.taoduoduo.api.PersonalCenterMsgApi;
import com.qunyu.taoduoduo.base.BaseModel;
import com.qunyu.taoduoduo.base.BaseUtil;
import com.qunyu.taoduoduo.bean.PersonalMessageBean;
import com.qunyu.taoduoduo.bean.UserInfoBean;
import com.qunyu.taoduoduo.config.AppConfig;
import com.qunyu.taoduoduo.global.Untool;
import com.qunyu.taoduoduo.utils.LogUtil;
import com.qunyu.taoduoduo.utils.StringUtils;
import com.qunyu.taoduoduo.utils.ToastUtils;
import com.qunyu.taoduoduo.utils.UserInfoUtils;
import com.qunyu.taoduoduo.view.BadgeView;
import com.qunyu.taoduoduo.view.CircleTransform;
import com.zhy.android.percent.support.PercentLinearLayout;
import com.zhy.android.percent.support.PercentRelativeLayout;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Administrator on 2016/9/27.
 */

public class PersonalCenterFragment extends Fragment implements View.OnClickListener {
    String TAG = "PersonalCenterFragment";
    View view;
    @BindView(R.id.iv_userlogo)
    ImageView userImage;
    @BindView(R.id.tv_username)
    TextView userName;
    @BindView(R.id.tv_pintuanNum)
    TextView pintuanzhong_Num;
    @BindView(R.id.tv_daifahuoNum)
    TextView daifahuo_Num;
    @BindView(R.id.tv_daishouhuoNum)
    TextView daishouhuo_Num;
    @BindView(R.id.tv_daipinjiaNum)
    TextView daipinjia_Num;
    @BindView(R.id.tv_tuikuanNum)
    TextView tuikuan_Num;
    @BindView(R.id.pintuanzhong_num)
    PercentLinearLayout layout_pintuanzhong;
    @BindView(R.id.daifahuo_num)
    PercentLinearLayout layout_daifahuo;
    @BindView(R.id.daishouhuo_num)
    PercentLinearLayout layout_dashouhuo;
    @BindView(R.id.daipinjia_num)
    PercentLinearLayout layout_daipinjia;
    @BindView(R.id.tuikuan_num)
    PercentLinearLayout layout_tuikuan;
    @BindView(R.id.tv_exitLogin)
    TextView btn_exitLogin;//退出登录
    @BindView(R.id.layout_myCoupons)
    PercentLinearLayout layout_coupon;
    @BindView(R.id.layout_myGroup)
    PercentLinearLayout layout_pintuan;
    @BindView(R.id.layout_myCaijia)
    PercentLinearLayout layout_caijia;
    @BindView(R.id.layout_myCollect)
    PercentLinearLayout layout_collect;
    @BindView(R.id.layout_myAddress)
    PercentLinearLayout layout_address;
    //团免券
    @BindView(R.id.tv_effective_Dates)
    TextView tv_effectiveTime;
    @BindView(R.id.layout_tuanmain)
    PercentRelativeLayout layout_tuanmain;
    String couponBTime;//团免券开始时间
    String couponETime;//结束时间

    @BindView(R.id.layout_weifukuantext)
    PercentRelativeLayout layout_weifukuantext;
    @BindView(R.id.tv_weifukuantext)
    TextView tv_wfkt;
    //    @BindView(R.id.tv_miantuanquan)
//    TextView tvMiantuanquan;
//    @BindView(R.id.tv_djxz)
//    TextView tvDjxz;
//    @BindView(R.id.tv_effective_Dates)
//    TextView tvEffectiveDates;
//    @BindView(R.id.layout_myPrize)
//    PercentLinearLayout layoutMyPrize;
    @BindView(R.id.layout_kefu)
    PercentLinearLayout layoutKefu;
    @BindView(R.id.ke)
    ImageView keke;
    @BindView(R.id.tv_qb)
    TextView tvQb;
    @BindView(R.id.rl_qb)
    PercentRelativeLayout rlQb;

    private PersonalMessageBean bean;
    private PersonalCenterMsgApi personalCenterMsgApi;

    private String photo;
    private String name;
    @BindView(R.id.layout_settings)
    PercentLinearLayout layout_settings;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.personal_center_fragment, container, false);
        ButterKnife.bind(this, view);
        bean = new PersonalMessageBean();
        personalCenterMsgApi = new PersonalCenterMsgApi();
        badge = new BadgeView(getActivity());
        rlQb.setVisibility(View.GONE);
        layout_settings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(), SettingsActivity.class));
            }
        });
        return view;

    }

    BadgeView badge;

    @Override
    public void onResume() {
        super.onResume();
        handler.postDelayed(task, 5000);
        if (UserInfoUtils.isLogin()) {
            getPersonalMsg();
//            rlQb.setVisibility(View.VISIBLE);
        } else {
            //userName.setText("未登录");
            rlQb.setVisibility(View.GONE);
        }
        layout_tuanmain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BaseUtil.ToAc(getActivity(), TuanMianActivity.class);
            }
        });
        //getPersonalMsg();
    }

    @Override
    public void onPause() {
        super.onPause();
//        LogUtil.Log("消息未读数:");
        handler.removeCallbacksAndMessages(null);
    }

    private Handler handler = new Handler();

    private Runnable task = new Runnable() {
        public void run() {
            // TODOAuto-generated method stub
            MQManager.getInstance(getActivity()).getUnreadMessages(Untool.getUid(), new OnGetMessageListCallback() {
                @Override
                public void onSuccess(List<MQMessage> list) {
//                    LogUtil.Log("消息未读数:" + list.size());
//                badge.setBackgroundResource(R.color.base_red);
                    if (UserInfoUtils.isLogin()) {
                        badge.setBackground(9, Color.parseColor("#FF464E"));
                        badge.setTargetView(layoutKefu);
                        badge.setBadgeMargin(0, 12, 22, 0);
                        badge.setBadgeCount(list.size());
                    }
                }

                @Override
                public void onFailure(int i, String s) {

                }
            });
            handler.postDelayed(this, 3 * 1000);//设置延迟时间，此处是5秒
            //需要执行的代码
        }
    };

    private void getPersonalMsg() {
        personalCenterMsgApi.setUserId(UserInfoUtils.GetUid());
        LogUtil.Log(personalCenterMsgApi.getUrl() + "?" + personalCenterMsgApi.getParamMap().getParamString());

        AbHttpUtil.getInstance(getActivity()).get(personalCenterMsgApi.getUrl(), personalCenterMsgApi.getParamMap()
                , new AbStringHttpResponseListener() {

                    @Override
                    public void onStart() {

                    }

                    @Override
                    public void onFinish() {


                    }

                    @Override
                    public void onFailure(int i, String s, Throwable error) {
                        ToastUtils.showShortToast(getActivity(), "网络异常，数据加载失败");
                        LogUtil.Log(error.getMessage());
                    }

                    @Override
                    public void onSuccess(int statusCode, String content) {
                        AbResult result = new AbResult(content);
                        if (result.getResultCode() == AbResult.RESULT_OK) {
                            Gson gson = new Gson();
                            Type type = new TypeToken<BaseModel<PersonalMessageBean>>() {
                            }.getType();
                            BaseModel<PersonalMessageBean> base = gson.fromJson(content, type);
                            Log.d(TAG, "onSuccess: " + content);
                            if (base != null) {
                                bean = base.result;
                                setPersonalMsg();
                            }
                        } else {
                            ToastUtils.showShortToast(getActivity(), "网络异常，数据加载失败");
                            LogUtil.Log(result.getResultMessage());
                        }
                    }
                });

    }

    private void setPersonalMsg() {
        try {
            UserInfoBean ll = UserInfoUtils.getUserInfo();
            ll.name = bean.getName();
            ll.image = bean.getUserImage();
            UserInfoUtils.setUserInfo(ll);
            //错误You cannot start a load for a destroyed activity
            Glide.with(getActivity()).load(bean.getUserImage()).placeholder(R.mipmap.default_touxiang)
                    .error(R.mipmap.default_touxiang)
                    .transform(new CircleTransform(getActivity())).into(userImage);
        } catch (Exception e) {
            e.printStackTrace();
        }


        userName.setText(bean.getName());
        pintuanzhong_Num.setText(bean.getGroupingNum());
        daifahuo_Num.setText(bean.getWaitSendNum());
        daishouhuo_Num.setText(bean.getWaitRecNum());
        daipinjia_Num.setText(bean.getWaitComNum());
        tuikuan_Num.setText(bean.getSaleSerNum());

        //判断是否免团券
        if (StringUtils.isNotBlank(bean.getIsGroupFree()) && bean.getIsGroupFree().equals("1")) {
            getActivity().findViewById(R.id.iv_tuanmianquan).setVisibility(View.VISIBLE);
            couponBTime = bean.getCouponBTime();
            couponETime = bean.getCouponETime();

            if (couponBTime != null && couponETime != null) {
                String[] startTime = couponBTime.split("-");
                String[] endTime = couponETime.split("-");
                couponBTime = startTime[0] + "." + startTime[1] + "." + startTime[2];
                couponETime = endTime[0] + "." + endTime[1] + "." + endTime[2];
            }
            tv_effectiveTime.setText("有效期：" + couponBTime + "-" + couponETime);
        } else {
            getActivity().findViewById(R.id.iv_tuanmianquan).setVisibility(View.GONE);
        }
//

        String waitPayNum = bean.getWaitPayNum();
        if (StringUtils.isNotBlank(waitPayNum) && !waitPayNum.equals("0")) {
            layout_weifukuantext.setVisibility(View.VISIBLE);
            tv_wfkt.setText(Html.fromHtml("还有<font color=\"#ff464e\">" + waitPayNum + "个订单</font>未付款"));
        }
        btn_exitLogin.setVisibility(View.VISIBLE);
    }


    @Override
    @OnClick({R.id.tv_exitLogin, R.id.iv_userlogo, R.id.tv_weifukuantext, R.id.pintuanzhong_num, R.id.daifahuo_num,
            R.id.daishouhuo_num, R.id.daipinjia_num, R.id.tuikuan_num, R.id.layout_myCoupons, R.id.layout_myGroup, R.id.layout_myCaijia
            , R.id.layout_myCollect, R.id.layout_myAddress, R.id.layout_myPrize, R.id.layout_kefu, R.id.rl_qb})
    public void onClick(View v) {
        if (UserInfoUtils.isLogin()) {
            switch (v.getId()) {
                case R.id.tv_exitLogin:
                    //退出登录
                    new AlertDialog.Builder(getActivity())
                            .setTitle("提示").setMessage("确定退出登录吗？")
                            .setPositiveButton("确认", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    UserInfoUtils.signOut();
                                    AppConfig.MYNOTIC_SIZE = 0;
                                    BaseUtil.ToAc(getActivity(), PhoneLoginActivity.class);
                                    getActivity().finish();
                                }
                            })
                            .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }
                            }).create().show();


                    break;
                case R.id.iv_userlogo:
//                    BaseUtil.ToAc(getActivity(), SaiDanActivity.class);
                    Bundle b = new Bundle();
                    if (StringUtils.isNotBlank(userName.getText().toString())) {
                        b.putString("name", userName.getText().toString());
                    }
                    b.putString("photo", bean.getUserImage());
                    Intent intent0 = new Intent(getActivity(), EditUserInfoActivity.class);
                    intent0.putExtras(b);
                    startActivityForResult(intent0, 0);
//                    BaseUtil.ToAcb(getActivity(), EditUserInfoActivity.class, b);
                    break;
                case R.id.tv_weifukuantext:
                    Bundle bundle0 = new Bundle();
                    bundle0.putString("page", "1");
                    BaseUtil.ToAcb(getActivity(), OrdersActivity.class, bundle0);
                    break;
                case R.id.pintuanzhong_num:
                    Bundle bundle1 = new Bundle();
                    bundle1.putString("page", "2");
                    BaseUtil.ToAcb(getActivity(), OrdersActivity.class, bundle1);
                    break;
                case R.id.daifahuo_num:
                    Bundle bundle2 = new Bundle();
                    bundle2.putString("page", "3");
                    BaseUtil.ToAcb(getActivity(), OrdersActivity.class, bundle2);
                    break;
                case R.id.daishouhuo_num:
                    Bundle bundle3 = new Bundle();
                    bundle3.putString("page", "4");
                    BaseUtil.ToAcb(getActivity(), OrdersActivity.class, bundle3);
                    break;
                case R.id.daipinjia_num:
                    Bundle bundle4 = new Bundle();
                    bundle4.putString("page", "5");
                    BaseUtil.ToAcb(getActivity(), OrdersActivity.class, bundle4);
                    break;
                case R.id.tuikuan_num:
                    BaseUtil.ToAc(getActivity(), AfterSaleActivity.class);
                    break;
                case R.id.layout_myCoupons:
                    BaseUtil.ToAc(getActivity(), CouponsActivity.class);
                    break;
                case R.id.layout_myGroup:
                    BaseUtil.ToAc(getActivity(), MyGroupListActivity.class);
                    break;
                case R.id.layout_myCaijia:
                    BaseUtil.ToAc(getActivity(), MyCaiJiaActivity.class);
                    break;
                case R.id.layout_myCollect:
                    BaseUtil.ToAc(getActivity(), MyCollectListActivity.class);
                    break;
                case R.id.layout_myAddress:
                    BaseUtil.ToAc(getActivity(), MyAddressActivity.class);
                    break;
                case R.id.layout_myPrize:
                    BaseUtil.ToAc(getActivity(), MyDrawsActivity.class);
                    break;
                case R.id.layout_kefu:
                    badge.setBadgeCount(0);
                    LogUtil.Log("美洽版本" + MQManager.getMeiqiaSDKVersion() + Untool.getName());

                    HashMap<String, String> clientInfo = new HashMap<>();
                    clientInfo.put("name", Untool.getName());
                    clientInfo.put("avatar", Untool.getImage());
                    clientInfo.put("tel", Untool.getPhone());
                    clientInfo.put("comment", "app");
                    Intent intent = new MQIntentBuilder(getActivity()).setCustomizedId(Untool.getUid()).setClientInfo(clientInfo)
//                            .setPreSendTextMessage("我是预发送文字消息")
//                            .setScheduledAgent("a8efc884b472b9a09e2dbbe9ee5450fd") // agentId 可以从工作台查询
//                            .setScheduledGroup("ae34e7f4376a07b3c8a3da5243651003") // groupId 可以从工作台查询
                            .build();
                    new Handler().postDelayed(new Runnable() {

                        public void run() {
                            HashMap<String, String> updateInfo = new HashMap<>();
                            updateInfo.put("name", Untool.getName());
                            updateInfo.put("avatar", Untool.getImage());
                            updateInfo.put("tel", Untool.getPhone());
                            updateInfo.put("comment", "app");
                            MQManager.getInstance(getActivity()).updateClientInfo(updateInfo, new OnClientInfoCallback() {
                                @Override
                                public void onSuccess() {

                                }

                                @Override
                                public void onFailure(int i, String s) {

                                }
                            });
                        }

                    }, 500);
                    startActivity(intent);
                    break;
                case R.id.layout_settings:
                    BaseUtil.ToAc(getActivity(), SettingsActivity.class);
                    break;
                case R.id.rl_qb:
                    BaseUtil.ToAc(getActivity(), QianBaoActivity.class);
                    break;
            }
        } else {
            Bundle b = new Bundle();
            b.putInt("tag", 99);
            BaseUtil.ToAcb(getActivity(), PhoneLoginActivity.class, b);//没有登录跳转自登录界面
        }

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == 0) {
                photo = data.getStringExtra("photo");
                if (StringUtils.isNotBlank(photo)) {
                    try {
                        Glide.with(getActivity()).load(photo).placeholder(R.mipmap.default_touxiang)
                                .error(R.mipmap.default_touxiang)
                                .transform(new CircleTransform(getActivity())).into(userImage);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                name = data.getStringExtra("name");
                if (StringUtils.isNotBlank(photo)) {
                    userName.setText(name);
                }
            }
        }
    }
}
