package com.qunyu.taoduoduo.activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.andbase.library.http.AbHttpUtil;
import com.andbase.library.http.listener.AbStringHttpResponseListener;
import com.andbase.library.http.model.AbRequestParams;
import com.andbase.library.http.model.AbResult;
import com.andbase.library.util.AbToastUtil;
import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.qunyu.taoduoduo.R;
import com.qunyu.taoduoduo.adapter.SpecialDetailAdapter;
import com.qunyu.taoduoduo.base.BaseActivity;
import com.qunyu.taoduoduo.base.BaseModel;
import com.qunyu.taoduoduo.base.BaseUtil;
import com.qunyu.taoduoduo.bean.GetShareContentBean;
import com.qunyu.taoduoduo.bean.SpecialDetailBean;
import com.qunyu.taoduoduo.bean.SpecialImageBean;
import com.qunyu.taoduoduo.bean.ZoneBean;
import com.qunyu.taoduoduo.global.Constant;
import com.qunyu.taoduoduo.mvpview.SpecialDetailView;
import com.qunyu.taoduoduo.mvpview.ZoneView;
import com.qunyu.taoduoduo.presenter.SpecialDetailPresenter;
import com.qunyu.taoduoduo.presenter.ZonePresenter;
import com.qunyu.taoduoduo.pulltorefresh.PullToRefreshLayout;
import com.qunyu.taoduoduo.pulltorefresh.PullableScrollView;
import com.qunyu.taoduoduo.utils.LogUtil;
import com.qunyu.taoduoduo.utils.StringUtils;
import com.qunyu.taoduoduo.utils.ToastUtils;
import com.qunyu.taoduoduo.widget.GridViewForScrollView;
import com.umeng.analytics.MobclickAgent;
import com.umeng.socialize.ShareAction;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.media.UMImage;
import com.umeng.socialize.utils.Log;
import com.zhy.android.percent.support.PercentRelativeLayout;

import java.lang.reflect.Type;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


/**
 * 7.7专区
 */

public class ZoneActivity extends BaseActivity implements ZoneView, View.OnClickListener {

    String id;//		专区id
    PullToRefreshLayout refresh_view;
    GridViewForScrollView gv_special;
    SpecialDetailAdapter specialDetailAdapter;
    ImageView iv_head;//头部
    ZonePresenter presenter;
    ImageView iv_fenxiang;//分享
    ImageView iv_head_left;
    TextView tv_head_title;
    @BindView(R.id.iv_top)
    ImageView ivTop;
    @BindView(R.id.sv_t)
    PullableScrollView svT;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        baseSetContentView(R.layout.activity_special_detail);
        ButterKnife.bind(this);
        initView();
        presenter = new ZonePresenter(this, this);
        presenter.loadZoneApi();//77专区
        //presenter.loadSpecialImage("6");//新品推荐
        GetShareContentApiGet();
    }

    public void onResume() {
        super.onResume();
        MobclickAgent.onResume(this);
    }

    public void onPause() {
        super.onPause();
        MobclickAgent.onPause(this);
    }

    GetShareContentBean share;

    private void GetShareContentApiGet() {
        AbRequestParams params = new AbRequestParams();
        params.put("id", "14");
        params.put("type", "14");
        LogUtil.Log(Constant.getShareContentApi + "?" + params.getParamString());
        AbHttpUtil.getInstance(ZoneActivity.this).get(Constant.getShareContentApi, params, new AbStringHttpResponseListener() {

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
                ToastUtils.showShortToast(ZoneActivity.this, "网络异常，数据加载失败");
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
                    ToastUtils.showShortToast(ZoneActivity.this, "网络异常，数据加载失败");
                }

            }
        });

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_fenxiang:
                backgroundAlpha(ZoneActivity.this, 0.5f);
                getPopupWindow();
//                // 这里是位置显示方式,在屏幕的左侧
                popupWindow1.showAtLocation(v, Gravity.BOTTOM, 0, 0);
                break;
            case R.id.iv_head_left:
                onBackPressed();
                break;
        }
    }

    @Override
    public void initView() {
        tv_head_title = (TextView) findViewById(R.id.tv_head_title);
        iv_head_left = (ImageView) findViewById(R.id.iv_head_left);
        iv_head_left.setOnClickListener(this);
        iv_head = (ImageView) findViewById(R.id.iv_head);
        refresh_view = (PullToRefreshLayout) findViewById(R.id.refresh_view);
        gv_special = (GridViewForScrollView) findViewById(R.id.gv_special);
        gv_special.setFocusable(false);
        gv_special.setSelector(R.color.transparent);
        refresh_view.setOnPullListener(new PullToRefreshLayout.OnPullListener() {
            @Override
            public void onRefresh(PullToRefreshLayout pullToRefreshLayout) {
                //刷新头部和列表
                //presenter.loadSpecialImage("6");
                presenter.loadZoneApi();
            }

            @Override
            public void onLoadMore(PullToRefreshLayout pullToRefreshLayout) {
                if (StringUtils.isNotBlank(id)) {
                    //presenter.loadSpecialData("6", true);
                    presenter.loadData(id, true);
                }

            }
        });
        gv_special.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                SpecialDetailBean specialDetailBean = (SpecialDetailBean) parent.getAdapter().getItem(position);
                Intent intent = new Intent(ZoneActivity.this, GoodsDetailActivity.class);
                intent.putExtra("activityId", specialDetailBean.activityId);
                intent.putExtra("pid", specialDetailBean.productId);
                startActivity(intent);
            }
        });
        iv_fenxiang = (ImageView) findViewById(R.id.iv_fenxiang);
        iv_fenxiang.setVisibility(View.VISIBLE);
        iv_fenxiang.setOnClickListener(this);
        ivTop.setVisibility(View.GONE);
        svT.setOnScrollListener(new PullableScrollView.OnScrollListener() {
            @Override
            public void onScroll(int scrollY) {
                if (svT.getScrollY() > 1000) {
                    ivTop.setVisibility(View.VISIBLE);
                } else {
                    ivTop.setVisibility(View.GONE);
                }
            }
        });
    }

    @Override
    public void initView(View view) {

    }

    @Override
    public void setList(List<SpecialDetailBean> list) {
        specialDetailAdapter = new SpecialDetailAdapter(this, list);
        gv_special.setAdapter(specialDetailAdapter);
    }

    @Override
    public void onLoadMore() {
        if (specialDetailAdapter != null) {
            specialDetailAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void setHead(ZoneBean zoneBean) {
        Glide.with(this).load(zoneBean.banner).placeholder(R.mipmap.default_load_long).error(R.mipmap.default_load_long).into(iv_head);
        tv_head_title.setText(zoneBean.title);
        id = zoneBean.id;
        if (StringUtils.isNotBlank(id)) {
            presenter.loadData(id, false);
        }
    }

    @Override
    public void loadFinish() {
        refresh_view.loadmoreFinish(PullToRefreshLayout.SUCCEED);
        refresh_view.refreshFinish(PullToRefreshLayout.SUCCEED);
    }

    @Override
    public void toastMessage(String msg) {
        ToastUtils.showShortToast(this, msg);
    }

    @Override
    public void setSpecialHead(SpecialImageBean specialImageBean) {
        Glide.with(this).load(specialImageBean.image).placeholder(R.mipmap.default_load_long).error(R.mipmap.default_load_long).into(iv_head);
        tv_head_title.setText(specialImageBean.specialName);
        //id = zoneBean.id;
        //if (StringUtils.isNotBlank(id)) {
        presenter.loadSpecialData("6", false);
        //}
    }

    PopupWindow popupWindow1;

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

    /***
     * 获取PopupWindow实例
     */
    private void getPopupWindow() {
        if (null != popupWindow1) {
            popupWindow1.dismiss();
            return;
        } else {
            initPopuptWindow();
        }
    }

    /**
     * 创建PopupWindow
     */
    protected void initPopuptWindow() {
        // TODO Auto-generated method stub
        // 获取自定义布局文件activity_popupwindow_left.xml的视图
        View popupWindow_view = getLayoutInflater().inflate(
                R.layout.pop_fx, null, false);

        // 创建PopupWindow实例,200,LayoutParams.MATCH_PARENT分别是宽度和高度
        popupWindow1 = new PopupWindow(popupWindow_view,
                PercentRelativeLayout.LayoutParams.MATCH_PARENT, PercentRelativeLayout.LayoutParams.WRAP_CONTENT, true);
        popupWindow1.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        // 设置动画效果
        popupWindow1.setAnimationStyle(R.style.AnimBottom);
        // 点击其他地方消失
        // 实例化一个ColorDrawable颜色为半透明
        ColorDrawable dw = new ColorDrawable(0x00000000);
        // 设置SelectPicPopupWindow弹出窗体的背景
        popupWindow1.setBackgroundDrawable(dw);
        backgroundAlpha(ZoneActivity.this, 0.5f);// 0.0-1.0
        popupWindow1.setOnDismissListener(new PopupWindow.OnDismissListener() {

            @Override
            public void onDismiss() {
                // TODO Auto-generated method stub
                backgroundAlpha(ZoneActivity.this, 1f);
            }
        });
        ImageView iv_wb = (ImageView) popupWindow_view.findViewById(R.id.iv_wb);
        iv_wb.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                // System.out.println("++++++++++++++++");
                UMImage urlImage = new UMImage(ZoneActivity.this,
                        share.getImage());
                new ShareAction(ZoneActivity.this)
                        .setPlatform(SHARE_MEDIA.SINA)
                        .withText(BaseUtil.Kl(share.getTitle())).withTitle(share.getTitle()).withMedia(urlImage)
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
                UMImage urlImage = new UMImage(ZoneActivity.this,
                        share.getImage());
                new ShareAction(ZoneActivity.this)
                        .setPlatform(SHARE_MEDIA.WEIXIN)
                        .withText(BaseUtil.Kl(share.getContent())).withTitle(share.getTitle()).withMedia(urlImage)
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
                UMImage urlImage = new UMImage(ZoneActivity.this,
                        share.getImage());
                new ShareAction(ZoneActivity.this)
                        .setPlatform(SHARE_MEDIA.WEIXIN_CIRCLE)
                        .withText(BaseUtil.Kl(share.getContent())).withTitle(share.getTitle()).withMedia(urlImage)
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

            Toast.makeText(ZoneActivity.this, platform + " 分享成功啦", Toast.LENGTH_SHORT).show();

        }

        @Override
        public void onError(SHARE_MEDIA platform, Throwable t) {
            Toast.makeText(ZoneActivity.this, platform + " 分享失败啦", Toast.LENGTH_SHORT).show();
            if (t != null) {
                Log.d("throw", "throw:" + t.getMessage());
            }
        }

        @Override
        public void onCancel(SHARE_MEDIA platform) {
            Toast.makeText(ZoneActivity.this, platform + " 分享取消了", Toast.LENGTH_SHORT).show();
        }
    };

    @OnClick(R.id.iv_top)
    public void onClick() {
        svT.scrollTo(10, 10);
        ivTop.setVisibility(View.GONE);
    }
}
