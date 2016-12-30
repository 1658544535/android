package com.qunyu.taoduoduo.activity;

import android.app.Activity;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.andbase.library.http.AbHttpUtil;
import com.andbase.library.http.listener.AbStringHttpResponseListener;
import com.andbase.library.http.model.AbRequestParams;
import com.andbase.library.http.model.AbResult;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.qunyu.taoduoduo.R;
import com.qunyu.taoduoduo.base.BaseActivity;
import com.qunyu.taoduoduo.base.BaseModel;
import com.qunyu.taoduoduo.bean.GetShareContentBean;
import com.qunyu.taoduoduo.fragment.FreeLotteryListFragment;
import com.qunyu.taoduoduo.global.Constant;
import com.qunyu.taoduoduo.utils.LogUtil;
import com.qunyu.taoduoduo.utils.ToastUtils;
import com.umeng.analytics.MobclickAgent;
import com.umeng.socialize.ShareAction;
import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.media.UMImage;
import com.umeng.socialize.utils.Log;
import com.zhy.android.percent.support.PercentRelativeLayout;

import java.lang.reflect.Type;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Administrator on 2016/10/29.
 * 免费抽奖
 */

public class FreeLotteryListActivity extends BaseActivity {
    ImageView iv_fenxiang;

    @BindView(R.id.iv_ing)
    ImageView iv_ing;
    @BindView(R.id.tv_ing)
    TextView tv_ing;
    @BindView(R.id.iv_ckwq)
    ImageView iv_ckwq;
    @BindView(R.id.tv_ckwq)
    TextView tv_ckwq;
    PopupWindow popupWindow1;

    int type;//1正在进行中；2结束

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        baseSetContentView(R.layout.freelotterylist_activity);
        iv_fenxiang = (ImageView) findViewById(R.id.iv_fenxiang);
        iv_fenxiang.setVisibility(View.VISIBLE);
        ButterKnife.bind(this);
        GetShareContentApiGet();
        //分享按钮
        iv_fenxiang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                backgroundAlpha(FreeLotteryListActivity.this, 0.5f);
                getSharePopupWindow();
                // 这里是位置显示方式,在屏幕的左侧
                popupWindow1.showAtLocation(v, Gravity.BOTTOM, 0, 0);
            }
        });
        iv_ing.performClick();
    }

    @Override
    protected void init() {
        super.init();
        baseSetText("免费试用");
    }

    public void onResume() {
        super.onResume();
        MobclickAgent.onResume(this);
    }

    public void onPause() {
        super.onPause();
        MobclickAgent.onPause(this);
    }

    private void setbaseText() {
        tv_ing.setTextColor(getResources().getColor(R.color.text_24));
        iv_ing.setImageResource(R.mipmap.prize_ing_icon);
        tv_ckwq.setTextColor(getResources().getColor(R.color.text_24));
        iv_ckwq.setImageResource(R.mipmap.chakan_wanqi_icon);

    }

    private void setFragment(String type) {
        FreeLotteryListFragment f = new FreeLotteryListFragment();
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.replace(R.id.lottery_content, f);
        Bundle b = new Bundle();
        b.putString("type", type);
        b.putString("index", "free");
        f.setArguments(b);
        transaction.commit();
    }


    @OnClick({R.id.iv_ing, R.id.tv_ing, R.id.iv_ckwq, R.id.tv_ckwq})
    public void onClick(View v) {
        setbaseText();
        switch (v.getId()) {
            case R.id.iv_ing:
            case R.id.tv_ing:
                tv_ing.setTextColor(getResources().getColor(R.color.text_01));
                iv_ing.setImageResource(R.mipmap.prize_ing_select_icon);
                setFragment("1");
                break;
            case R.id.iv_ckwq:
            case R.id.tv_ckwq:
                tv_ckwq.setTextColor(getResources().getColor(R.color.text_01));
                iv_ckwq.setImageResource(R.mipmap.chakan_wanqi_select_icon);
                setFragment("2");
                break;

        }
    }

    GetShareContentBean share;

    private void GetShareContentApiGet() {
        AbRequestParams params = new AbRequestParams();
        params.put("id", "21");
        params.put("type", "21");
        LogUtil.Log(Constant.getShareContentApi + "?" + params.getParamString());
        AbHttpUtil.getInstance(FreeLotteryListActivity.this).get(Constant.getShareContentApi, params, new AbStringHttpResponseListener() {

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
                ToastUtils.showShortToast(FreeLotteryListActivity.this, "网络异常，数据加载失败");
            }

            @Override
            public void onSuccess(int statusCode, String content) {
                AbResult result = new AbResult(content);

                if (result.getResultCode() == AbResult.RESULT_OK) {
                    Gson gson = new Gson();
                    Type type = new TypeToken<BaseModel<GetShareContentBean>>() {
                    }.getType();
                    BaseModel<GetShareContentBean> base = gson.fromJson(content, type);
                    if (base.result != null) {
                        share = base.result;
//                        Log.d("++++", "onSuccess: " + networkImages.get(1).getImage());
                    }
                } else {
                    ToastUtils.showShortToast(FreeLotteryListActivity.this, "网络异常，数据加载失败");
                }

            }
        });

    }

    /***
     * 获取PopupWindow实例
     */
    private void getSharePopupWindow() {
        if (null != popupWindow1) {
            popupWindow1.dismiss();
            return;
        } else {
            initSharePopuptWindow();
        }
    }

    /**
     * 设置添加屏幕的背景透明度
     *
     * @param bgAlpha
     */
    public void backgroundAlpha(Activity context, float bgAlpha) {
        WindowManager.LayoutParams lp = context.getWindow().getAttributes();
        lp.alpha = bgAlpha;
        context.getWindow()
                .addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
        context.getWindow().setAttributes(lp);
    }

    /**
     * 创建PopupWindow
     */
    protected void initSharePopuptWindow() {
        // TODO Auto-generated method stub
        // 获取自定义布局文件activity_popupwindow_left.xml的视图
        View popupWindow_view = getLayoutInflater().inflate(
                R.layout.pop_fx, null, false);

        // 创建PopupWindow实例,200,LayoutParams.MATCH_PARENT分别是宽度和高度
        popupWindow1 = new PopupWindow(popupWindow_view,
                PercentRelativeLayout.LayoutParams.MATCH_PARENT, PercentRelativeLayout.LayoutParams.WRAP_CONTENT, true);
        // 设置动画效果
        popupWindow1.setAnimationStyle(R.style.AnimBottom);
        // 点击其他地方消失
        // 实例化一个ColorDrawable颜色为半透明
        ColorDrawable dw = new ColorDrawable(0x00000000);
        // 设置SelectPicPopupWindow弹出窗体的背景
        popupWindow1.setBackgroundDrawable(dw);
        backgroundAlpha(FreeLotteryListActivity.this, 0.5f);// 0.0-1.0
        popupWindow1.setOnDismissListener(new PopupWindow.OnDismissListener() {

            @Override
            public void onDismiss() {
                // TODO Auto-generated method stub
                backgroundAlpha(FreeLotteryListActivity.this, 1f);
            }
        });
        ImageView iv_wb = (ImageView) popupWindow_view.findViewById(R.id.iv_wb);
        iv_wb.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                // System.out.println("++++++++++++++++");
                UMImage urlImage = new UMImage(FreeLotteryListActivity.this,
                        share.getImage());
                new ShareAction(FreeLotteryListActivity.this)
                        .setPlatform(SHARE_MEDIA.SINA)
                        .withText(share.getContent()).withTitle(share.getTitle()).withMedia(urlImage)
                        .withTargetUrl(share.getUrl())
                        .setCallback(umShareListener).share();

            }
        });
        ImageView iv_wx = (ImageView) popupWindow_view.findViewById(R.id.iv_wx);
        iv_wx.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                // System.out.println("++++++++++++++++");
                UMImage urlImage = new UMImage(FreeLotteryListActivity.this,
                        share.getImage());
                new ShareAction(FreeLotteryListActivity.this)
                        .setPlatform(SHARE_MEDIA.WEIXIN)
                        .withText(share.getContent()).withTitle(share.getTitle()).withMedia(urlImage)
                        .withTargetUrl(share.getUrl())
                        .setCallback(umShareListener).share();

            }
        });
        ImageView iv_pyq = (ImageView) popupWindow_view
                .findViewById(R.id.iv_pyq);
        iv_pyq.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                // System.out.println("++++++++++++++++");
                UMImage urlImage = new UMImage(FreeLotteryListActivity.this,
                        share.getImage());
                new ShareAction(FreeLotteryListActivity.this)
                        .setPlatform(SHARE_MEDIA.WEIXIN_CIRCLE)
                        .withText(share.getContent()).withTitle(share.getTitle()).withMedia(urlImage)
                        .withTargetUrl(share.getUrl())
                        .setCallback(umShareListener).share();

            }
        });
        // popupWindow.setTouchable(true);
        popupWindow_view.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                // TODO Auto-generated method stub
                if (popupWindow1 != null && popupWindow1.isShowing()) {
                    popupWindow1.dismiss();
                    popupWindow1 = null;
                }
                return false;
            }
        });
    }

    private UMShareListener umShareListener = new UMShareListener() {
        @Override
        public void onResult(SHARE_MEDIA platform) {
            Log.d("plat", "platform" + platform);

            Toast.makeText(FreeLotteryListActivity.this, platform + " 分享成功啦", Toast.LENGTH_SHORT).show();

        }

        @Override
        public void onError(SHARE_MEDIA platform, Throwable t) {
            Toast.makeText(FreeLotteryListActivity.this, platform + " 分享失败啦", Toast.LENGTH_SHORT).show();
            if (t != null) {
                Log.d("throw", "throw:" + t.getMessage());
            }
        }

        @Override
        public void onCancel(SHARE_MEDIA platform) {
            Toast.makeText(FreeLotteryListActivity.this, platform + " 分享取消了", Toast.LENGTH_SHORT).show();
        }
    };
}

