package com.qunyu.taoduoduo.activity;

import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.andbase.library.http.AbHttpUtil;
import com.andbase.library.http.listener.AbStringHttpResponseListener;
import com.andbase.library.http.model.AbRequestParams;
import com.andbase.library.http.model.AbResult;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.qunyu.taoduoduo.R;
import com.qunyu.taoduoduo.adapter.UserJoinInfoApiAdapter;
import com.qunyu.taoduoduo.adapter.WinlistApiAdapter;
import com.qunyu.taoduoduo.base.BaseActivity;
import com.qunyu.taoduoduo.base.BaseModel;
import com.qunyu.taoduoduo.bean.UserJoinInfoBean;
import com.qunyu.taoduoduo.bean.WinListBean;
import com.qunyu.taoduoduo.global.Constant;
import com.qunyu.taoduoduo.utils.ToastUtils;
import com.umeng.analytics.MobclickAgent;

import java.lang.reflect.Type;
import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CanYuYongHuActivity extends BaseActivity implements View.OnClickListener {
    String activityId = null;
    String productId = null;
    String type = null;
    String prize = "1";
    int pageNo = 1;
    @BindView(R.id.lv_t)
    ListView lvT;
    UserJoinInfoApiAdapter adapter;
    ArrayList<UserJoinInfoBean.JoinUserListBean> mData;
    WinlistApiAdapter adapter1;
    ArrayList<WinListBean.PrizeListBean> mData1;
    View vFoot;
    TextView textView1, textView2, textView3, tv_1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = this.getIntent().getExtras();
        activityId = bundle.getString("activityId");
        productId = bundle.getString("productId");
        type = bundle.getString("type");
        prize = bundle.getString("prize");
        baseSetContentView(R.layout.activity_can_yu_yong_hu);
        ButterKnife.bind(this);
        vFoot = View.inflate(CanYuYongHuActivity.this, R.layout.activity_can_yu_yong_hu_more, null);
        TextView tv_good_xl = (TextView) vFoot.findViewById(R.id.tv_good_xl);
        tv_good_xl.setOnClickListener(this);
//        lvT.addFooterView(vFoot);
        if (type.equals("1")) {
            baseSetText("参与用户列表");
            View vHead = View.inflate(CanYuYongHuActivity.this, R.layout.activity_can_yu_yong_hu_pt, null);
            tv_1 = (TextView) vHead.findViewById(R.id.tv_1);
            lvT.addHeaderView(vHead);
            UserJoinInfoGet();
        } else {
            baseSetText("得奖人列表");
            View vHead = View.inflate(CanYuYongHuActivity.this, R.layout.activity_can_yu_yong_hu_tab, null);
            tv_1 = (TextView) vHead.findViewById(R.id.tv_1);
            textView1 = (TextView) vHead.findViewById(R.id.textView1);
            textView3 = (TextView) vHead.findViewById(R.id.textView3);
            textView2 = (TextView) vHead.findViewById(R.id.textView2);
            textView1.setOnClickListener(this);
            textView2.setOnClickListener(this);
            textView3.setOnClickListener(this);
            lvT.addHeaderView(vHead);
            switch (prize) {
                case "1":
                    textView1.setBackgroundColor(CanYuYongHuActivity.this.getResources().getColor(R.color.text_01));
                    textView2.setBackgroundColor(CanYuYongHuActivity.this.getResources().getColor(R.color.btn_03));
                    textView3.setBackgroundColor(CanYuYongHuActivity.this.getResources().getColor(R.color.btn_03));
                    break;
                case "2":
                    textView1.setBackgroundColor(CanYuYongHuActivity.this.getResources().getColor(R.color.btn_03));
                    textView2.setBackgroundColor(CanYuYongHuActivity.this.getResources().getColor(R.color.text_01));
                    textView3.setBackgroundColor(CanYuYongHuActivity.this.getResources().getColor(R.color.btn_03));
                    break;
                case "3":
                    textView1.setBackgroundColor(CanYuYongHuActivity.this.getResources().getColor(R.color.btn_03));
                    textView2.setBackgroundColor(CanYuYongHuActivity.this.getResources().getColor(R.color.btn_03));
                    textView3.setBackgroundColor(CanYuYongHuActivity.this.getResources().getColor(R.color.text_01));
                    break;
            }
            WinList();
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
    public void onClick(View view) {

        switch (view.getId()) {
            case R.id.textView1:
                prize = "1";
                pageNo = 1;
                WinList();
                textView1.setBackgroundColor(CanYuYongHuActivity.this.getResources().getColor(R.color.text_01));
                textView2.setBackgroundColor(CanYuYongHuActivity.this.getResources().getColor(R.color.btn_03));
                textView3.setBackgroundColor(CanYuYongHuActivity.this.getResources().getColor(R.color.btn_03));
                break;
            case R.id.textView2:
                prize = "2";
                pageNo = 1;
                WinList();
                textView1.setBackgroundColor(CanYuYongHuActivity.this.getResources().getColor(R.color.btn_03));
                textView2.setBackgroundColor(CanYuYongHuActivity.this.getResources().getColor(R.color.text_01));
                textView3.setBackgroundColor(CanYuYongHuActivity.this.getResources().getColor(R.color.btn_03));
                break;
            case R.id.textView3:
                prize = "3";
                pageNo = 1;
                WinList();
                textView1.setBackgroundColor(CanYuYongHuActivity.this.getResources().getColor(R.color.btn_03));
                textView2.setBackgroundColor(CanYuYongHuActivity.this.getResources().getColor(R.color.btn_03));
                textView3.setBackgroundColor(CanYuYongHuActivity.this.getResources().getColor(R.color.text_01));
                break;
            case R.id.tv_good_xl:
                pageNo++;
                if (type.equals("1")) {
                    UserJoinInfoGet();
                } else {
                    WinList();
                }
                break;
            default:
                break;
        }
    }

    private void UserJoinInfoGet() {
        AbRequestParams params = new AbRequestParams();
        params.put("activityId", activityId);
        params.put("pageNo", pageNo + "");
        AbHttpUtil.getInstance(CanYuYongHuActivity.this).get(Constant.userJoinInfoApi, params, new AbStringHttpResponseListener() {

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
                ToastUtils.showShortToast(CanYuYongHuActivity.this, "网络异常，数据加载失败");
            }

            @Override
            public void onSuccess(int statusCode, String content) {
                AbResult result = new AbResult(content);
                if (result.getResultCode() == AbResult.RESULT_OK) {
                    Gson gson = new Gson();
                    Type type = new TypeToken<BaseModel<UserJoinInfoBean>>() {
                    }.getType();
                    BaseModel<UserJoinInfoBean> base = gson.fromJson(content, type);
                    Log.d("+++", "onSuccess: " + content);
                    if (base.result != null) {
                        SpannableStringBuilder style = new SpannableStringBuilder("共有" + base.result.getJoinNum() + "位用户参与此活动");
                        style.setSpan(new ForegroundColorSpan(CanYuYongHuActivity.this.getResources().getColor(R.color.text_01)), 2, 2 + base.result.getJoinNum().length(), Spannable.SPAN_EXCLUSIVE_INCLUSIVE);
                        tv_1.setText(style);
                        if (pageNo == 1) {
                            mData = base.result.getJoinUserList();
                            if (mData.size() > 19) {
                                lvT.addFooterView(vFoot);
                            }else{
                                lvT.removeFooterView(vFoot);
                            }
                            adapter = new UserJoinInfoApiAdapter(CanYuYongHuActivity.this, mData);
                            lvT.setAdapter(adapter);
                        } else {
                            if (base.result.getJoinUserList().size() < 19) {
                                lvT.removeFooterView(vFoot);
                            }
                            mData.addAll(base.result.getJoinUserList());
                            adapter.notifyDataSetChanged();
                        }
                    }
//                    ToastUtils.showShortToast(CanYuYongHuActivity.this, "网络异常，数据加载失败");
                } else {
                    ToastUtils.showShortToast(CanYuYongHuActivity.this, "网络异常，数据加载失败");
                }

            }
        });

    }

    private void WinList() {
        AbRequestParams params = new AbRequestParams();
        params.put("activityId", activityId);
        params.put("pageNo", pageNo + "");
        params.put("prize", prize + "");
        AbHttpUtil.getInstance(CanYuYongHuActivity.this).get(Constant.winListApi, params, new AbStringHttpResponseListener() {

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
                ToastUtils.showShortToast(CanYuYongHuActivity.this, "网络异常，数据加载失败");
            }

            @Override
            public void onSuccess(int statusCode, String content) {
                AbResult result = new AbResult(content);
                if (result.getResultCode() == AbResult.RESULT_OK) {
                    Gson gson = new Gson();
                    Type type = new TypeToken<BaseModel<WinListBean>>() {
                    }.getType();
                    BaseModel<WinListBean> base = gson.fromJson(content, type);
                    Log.d("+++", "onSuccess: " + content);
                    if (base.result != null) {
                        SpannableStringBuilder style = new SpannableStringBuilder("共有" + base.result.getJoinNum() + "位用户参与此活动");
                        style.setSpan(new ForegroundColorSpan(CanYuYongHuActivity.this.getResources().getColor(R.color.text_01)), 2, 2 + base.result.getJoinNum().length(), Spannable.SPAN_EXCLUSIVE_INCLUSIVE);
                        tv_1.setText(style);
                        if (pageNo == 1) {
                            mData1 = base.result.getPrizeList();
                            if (mData1.size() > 19) {
                                lvT.addFooterView(vFoot);
                            }else{
                                lvT.removeFooterView(vFoot);
                            }
                            adapter1 = new WinlistApiAdapter(CanYuYongHuActivity.this, mData1);
                            lvT.setAdapter(adapter1);
                        } else {
                            if (base.result.getPrizeList().size() < 19) {
                                lvT.removeFooterView(vFoot);
                            }
                            mData1.addAll(base.result.getPrizeList());
                            adapter1.notifyDataSetChanged();
                        }
                    }
//                    ToastUtils.showShortToast(CanYuYongHuActivity.this, "网络异常，数据加载失败");
                } else {
                    ToastUtils.showShortToast(CanYuYongHuActivity.this, "网络异常，数据加载失败");
                }

            }
        });

    }
}
