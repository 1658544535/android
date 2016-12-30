package com.qunyu.taoduoduo.activity;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.text.format.DateUtils;
import android.text.format.Formatter;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.WindowManager;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.allure.lbanners.LMBanners;
import com.allure.lbanners.transformer.TransitionEffect;
import com.andbase.library.http.AbHttpUtil;
import com.andbase.library.http.listener.AbStringHttpResponseListener;
import com.andbase.library.http.model.AbRequestParams;
import com.andbase.library.http.model.AbResult;
import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.meiqia.core.MQManager;
import com.meiqia.core.callback.OnClientInfoCallback;
import com.meiqia.meiqiasdk.util.MQIntentBuilder;
import com.qunyu.taoduoduo.R;
import com.qunyu.taoduoduo.adapter.GoodsBannarAdapter;
import com.qunyu.taoduoduo.adapter.GuessYourLikeAdapter;
import com.qunyu.taoduoduo.adapter.SkuColorAdapter;
import com.qunyu.taoduoduo.adapter.SkuFormatAdapter;
import com.qunyu.taoduoduo.adapter.WaitGroupListAdapter;
import com.qunyu.taoduoduo.base.BaseModel;
import com.qunyu.taoduoduo.base.BaseUtil;
import com.qunyu.taoduoduo.bean.GetShareContentBean;
import com.qunyu.taoduoduo.bean.GuessYourLikeBean;
import com.qunyu.taoduoduo.bean.OpenGroupBean;
import com.qunyu.taoduoduo.bean.ProductSkuBean;
import com.qunyu.taoduoduo.config.AppConfig;
import com.qunyu.taoduoduo.global.AnyEventType;
import com.qunyu.taoduoduo.global.Constant;
import com.qunyu.taoduoduo.global.Untool;
import com.qunyu.taoduoduo.mvpview.GoodsDetailView;
import com.qunyu.taoduoduo.mvpview.SkuModeView;
import com.qunyu.taoduoduo.presenter.GoodsDetailPresenter;
import com.qunyu.taoduoduo.presenter.SkuPresenter;
import com.qunyu.taoduoduo.utils.LogUtil;
import com.qunyu.taoduoduo.utils.StringUtils;
import com.qunyu.taoduoduo.utils.ToastUtils;
import com.qunyu.taoduoduo.utils.UserInfoUtils;
import com.qunyu.taoduoduo.widget.GridViewForScrollView;
import com.qunyu.taoduoduo.widget.ListViewForScrollView;
import com.umeng.analytics.MobclickAgent;
import com.umeng.socialize.ShareAction;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.media.UMImage;
import com.umeng.socialize.utils.Log;
import com.zhy.android.percent.support.PercentLinearLayout;
import com.zhy.android.percent.support.PercentRelativeLayout;

import org.greenrobot.eventbus.EventBus;

import java.lang.reflect.Type;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.regex.Pattern;

/**
 * 普通开团商品详情
 */
public class GoodsDetailActivity extends AppCompatActivity implements GoodsDetailView, View.OnClickListener, SkuModeView {

    String activityId;
    String pid;
    GoodsDetailPresenter presenter;
    ImageView iv_back;

    ImageView iv_home;
    ImageView iv_collect;
    ImageView iv_fx;
    ImageView iv_kefu;
    TextView tv_alonePrice;
    TextView tv_pintuanprice;


    String source;//来源，1-普通拼团2-团免3-猜价4单独购买5-0.1抽奖6-限时秒杀 7免费抽奖

    PercentLinearLayout ll_product_top;

    PercentRelativeLayout bottom_layout;

    PercentRelativeLayout rl_empty;
    ImageView iv_empty_back;

    String tag;//有值 caijia  猜价格，传pid，其它不用
    ImageView iv_pingtuantips;
    //Sku
    PopupWindow skupopupWindow;
    PercentRelativeLayout rl_main;
    ImageView iv_iamge;
    TextView tv_productName;
    TextView tv_price;
    TextView tv_colortitle;
    TextView tv_formattitle;
    GridViewForScrollView gv_color;
    GridViewForScrollView gv_format;
    View add;
    View reduce;
    TextView tv_num;
    Button btn_offirm;
    SkuColorAdapter skuColorAdapter;
    SkuFormatAdapter formatAdapter;
    SkuPresenter skuPresenter;
    TextView tv_pName;
    TextView tv_skutips;
    //sku
    //detail
    TextView tv_productPrice;
    TextView tv_sellingPrice;
    TextView tv_proSellrNum;
    TextView tv_productSketch;
    WebView wbv;
    boolean iscollect = false;
    OpenGroupBean dat;
    ImageView iv_gylike;//猜你喜欢头部
    //detail

    ImageView iv_mstag;//限时秒杀图片标识
    TextView tv_shuoming;//0.1抽奖，限时秒杀活动时间说明
    TextView tv_bom_huodong;//0.1抽奖，限时秒杀活动底部按钮
    TextView tv_zhongjiantips;//0.1中奖说明

    int actStatus;//底部部按钮状态，0.1抽奖和限时秒杀   1-0.1进行中可参加  2-0.1进行中已参加过  3-0.1结束查看中奖名单
    //4-限时开抢中 5-已售罄 6-限时即将开抢  7-限时已结束
    //免费抽奖 8-立即开团  9-已参加过该活动 10-等待开奖中 11-查看中奖名单 12-活动未开始

    TextView tv_bom_sellout;//商品已售罄

    PopupWindow popupWindow1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_goods_detail);
        activityId = getIntent().getStringExtra("activityId");
        LogUtil.Log("activityId:" + activityId);
        pid = getIntent().getStringExtra("pid");
        tag = getIntent().getStringExtra("tag");
        LogUtil.Log("pid:" + pid + tag);
        initView();
        presenter = new GoodsDetailPresenter(this, this);
        presenter.loadData(activityId, pid, tag);
        skuPresenter = new SkuPresenter(this, this, this);
        handler.postDelayed(runnable, 1000);
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
    public void initView() {
        tv_bom_sellout = (TextView) findViewById(R.id.tv_bom_sellout);
        tv_zhongjiantips = (TextView) findViewById(R.id.tv_zhongjiantips);
        tv_bom_huodong = (TextView) findViewById(R.id.tv_bom_huodong);
        tv_bom_huodong.setOnClickListener(this);
        iv_mstag = (ImageView) findViewById(R.id.iv_mstag);
        tv_shuoming = (TextView) findViewById(R.id.tv_shuoming);
        iv_gylike = (ImageView) findViewById(R.id.iv_gylike);
        iv_pingtuantips = (ImageView) findViewById(R.id.iv_pingtuantips);
        iv_empty_back = (ImageView) findViewById(R.id.iv_empty_back);
        iv_empty_back.setOnClickListener(this);
        banner = (LMBanners) findViewById(R.id.banner);
        tv_productPrice = (TextView) findViewById(R.id.tv_productPrice);
        tv_sellingPrice = (TextView) findViewById(R.id.tv_sellingPrice);
        tv_proSellrNum = (TextView) findViewById(R.id.tv_proSellrNum);
        tv_productName = (TextView) findViewById(R.id.tv_productName);
        tv_productSketch = (TextView) findViewById(R.id.tv_productSketch);
        iv_back = (ImageView) findViewById(R.id.iv_back);
        iv_back.setOnClickListener(this);
        lv_t = (ListViewForScrollView) findViewById(R.id.lv_t);
        wbv = (WebView) findViewById(R.id.wbv);
        wbv.setFocusable(false);
        iv_home = (ImageView) findViewById(R.id.iv_home);
        iv_collect = (ImageView) findViewById(R.id.iv_collect);
        iv_kefu = (ImageView) findViewById(R.id.iv_kefu);
        tv_alonePrice = (TextView) findViewById(R.id.tv_alonePrice);
        tv_pintuanprice = (TextView) findViewById(R.id.tv_pintuanprice);
        iv_fx = (ImageView) findViewById(R.id.iv_fx);
        iv_fx.setOnClickListener(this);
        iv_home.setOnClickListener(this);
        iv_collect.setOnClickListener(this);
        tv_pintuanprice.setOnClickListener(this);
        iv_kefu.setOnClickListener(this);
        rl_main = (PercentRelativeLayout) findViewById(R.id.rl_main);
        tv_alonePrice.setOnClickListener(this);
        lv_t.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (UserInfoUtils.isLogin()) {
                    OpenGroupBean.WaitGroup waitGroup = (OpenGroupBean.WaitGroup) parent.getAdapter().getItem(position);

                    //0.1抽奖
                    if (actStatus == 2 || actStatus == 9) {
                        toastMessage("您已参加过该活动");
                    } else {
                        Intent intent = new Intent(GoodsDetailActivity.this, GroupDetailActivity.class);
                        intent.putExtra("recordId", waitGroup.groupRecId);
                        startActivity(intent);
                    }


                } else {
                    Intent intent = new Intent(GoodsDetailActivity.this, PhoneLoginActivity.class);
                    intent.putExtra("tag", 99);
                    startActivityForResult(intent, AppConfig.LOGIN);
                }

            }
        });
        lv_t.setFocusable(false);
        gv_cainixihuan = (GridViewForScrollView) findViewById(R.id.gv_cainixihuan);
        gv_cainixihuan.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                GuessYourLikeBean guessYourLikeBean = (GuessYourLikeBean) parent.getAdapter().getItem(position);
                Intent intent = new Intent(GoodsDetailActivity.this, GoodsDetailActivity.class);
                intent.putExtra("pid", guessYourLikeBean.productId);
                intent.putExtra("activityId", guessYourLikeBean.activityId);
                startActivity(intent);
            }

        });
        gv_cainixihuan.setFocusable(false);
        ll_product_top = (PercentLinearLayout) findViewById(R.id.ll_product_top);
        bottom_layout = (PercentRelativeLayout) findViewById(R.id.bottom_layout);
        rl_empty = (PercentRelativeLayout) findViewById(R.id.rl_empty);

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
        backgroundAlpha(GoodsDetailActivity.this, 0.5f);// 0.0-1.0
        popupWindow1.setOnDismissListener(new PopupWindow.OnDismissListener() {

            @Override
            public void onDismiss() {
                // TODO Auto-generated method stub
                backgroundAlpha(GoodsDetailActivity.this, 1f);
            }
        });
        ImageView iv_wb = (ImageView) popupWindow_view.findViewById(R.id.iv_wb);
        iv_wb.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                // System.out.println("++++++++++++++++");
                UMImage urlImage = new UMImage(GoodsDetailActivity.this,
                        presenter.share.getImage());
                new ShareAction(GoodsDetailActivity.this)
                        .setPlatform(SHARE_MEDIA.SINA)
                        .withText(presenter.share.getContent()).withTitle(presenter.share.getTitle()).withMedia(urlImage)
                        .withTargetUrl(presenter.share.getUrl())
                        .setCallback(umShareListener).share();

            }
        });
        ImageView iv_wx = (ImageView) popupWindow_view.findViewById(R.id.iv_wx);
        iv_wx.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                // System.out.println("++++++++++++++++");
                UMImage urlImage = new UMImage(GoodsDetailActivity.this,
                        presenter.share.getImage());
                new ShareAction(GoodsDetailActivity.this)
                        .setPlatform(SHARE_MEDIA.WEIXIN)
                        .withText(presenter.share.getContent()).withTitle(presenter.share.getTitle()).withMedia(urlImage)
                        .withTargetUrl(presenter.share.getUrl())
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
                UMImage urlImage = new UMImage(GoodsDetailActivity.this,
                        presenter.share.getImage());
                new ShareAction(GoodsDetailActivity.this)
                        .setPlatform(SHARE_MEDIA.WEIXIN_CIRCLE)
                        .withText(presenter.share.getContent()).withTitle(presenter.share.getTitle()).withMedia(urlImage)
                        .withTargetUrl(presenter.share.getUrl())
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
    private void getSharePopupWindow() {
        if (null != popupWindow1) {
            popupWindow1.dismiss();
            return;
        } else {
            initSharePopuptWindow();
        }
    }

    @Override
    public void toastMessage(String message) {
        ToastUtils.showShortToast(this, message);
    }

    LMBanners banner;

    @Override
    public void setBannar(List<OpenGroupBean.Banner> banners) {
        dat.banners = banners;
        List<OpenGroupBean.Banner> bannerImage = new ArrayList<>();
        bannerImage = banners;
        banner.setAdapter(new GoodsBannarAdapter(this, bannerImage), banners);
        banner.setAutoPlay(false);//自动播放
        banner.setVertical(false);//是否可以垂直
        banner.setScrollDurtion(666);//两页切换时间
        banner.setCanLoop(false);//循环播放
        //banner.hideIndicatorLayout();//隐藏原点
        banner.setHoriZontalTransitionEffect(TransitionEffect.Cube);//选中喜欢的样式
        try {
            banner.clearImageTimerTask();
        } catch (Exception e) {
            e.printStackTrace();
        }


    }


    @Override
    public void setDetail(OpenGroupBean openGroupBean) {
        activityId = openGroupBean.activityId;
        source = openGroupBean.activityType;
        ll_product_top.setVisibility(View.VISIBLE);
        bottom_layout.setVisibility(View.VISIBLE);
        iv_fx.setVisibility(View.VISIBLE);
        dat = openGroupBean;
        tv_productPrice.setText("￥" + openGroupBean.productPrice);
        tv_sellingPrice.setText("￥" + openGroupBean.sellingPrice);
        tv_sellingPrice.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);
        tv_proSellrNum.setText("累积销量：" + openGroupBean.proSellrNum + "件");
        tv_productName.setText(openGroupBean.productName);
        tv_productSketch.setText(openGroupBean.productSketch);
        LogUtil.Log(Constant.GETPRODUCTINFOVIEW_URL + "?id=" + pid);
        wbv.loadUrl(Constant.GETPRODUCTINFOVIEW_URL + "?id=" + pid);
        WebSettings webSettings = wbv.getSettings();
        webSettings.setJavaScriptEnabled(true); // 支持js
        webSettings.setDefaultTextEncodingName("UTF-8");
        wbv.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                loadguessYourLike();

            }
        });
        tv_alonePrice.setText("￥" + openGroupBean.alonePrice + "\n单独购买");
        if (openGroupBean.activityType.equals("2")) {
            // 团免
            tv_pintuanprice.setText("0元开团\n￥0.0");
        } else {
            tv_pintuanprice.setText("￥" + openGroupBean.productPrice + "\n" + openGroupBean.groupNum + "人成团");
        }

        if (openGroupBean.isCollect.equals("1")) {
            iv_collect.setImageResource(R.mipmap.gd_collect);
            iscollect = true;

        } else {
            iv_collect.setImageResource(R.mipmap.gd_collcet_empty);
            iscollect = false;
        }
        if (openGroupBean.activityType.equals("5")) {
            //0.1抽奖
            tv_zhongjiantips.setVisibility(View.VISIBLE);
            String s = "活动时间：<font color='#FF464E'><b>" + openGroupBean.activitySTime + "</b></font> 到 <font color='#FF464E'><b>" + openGroupBean.activityETime + "</b></font><br>活动规则：<br>1. 0.1元支付开团，规定时间内邀请好友支付0.1元参团，成团即开奖。" +
                    "<br>2.团长必中，并从每个成团中抽取一个幸运团成员，获得奖品。" +
                    "<br>3.不成团或无中奖用户均全额退款。" +
                    "<br>4.每人均有一次开团与一次参团机会。" +
                    "<br>5.活动奖品预计开奖后72小时内发放。" +
                    "<br><br>温馨提醒:" +
                    "<br>每个团组团有效期为6个小时，如团结束时间大于活动结束时间时，则按活动结束的时间。";
            tv_shuoming.setText(Html.fromHtml(s));
            tv_alonePrice.setVisibility(View.GONE);
            tv_pintuanprice.setVisibility(View.GONE);
            tv_bom_huodong.setVisibility(View.VISIBLE);
            if (openGroupBean.activityStatus.equals("1")) {
                //进行中
                if (openGroupBean.isOpen.equals("1")) {
                    //开过团
                    actStatus = 2;
                } else {
                    if (openGroupBean.isSellOut.equals("1")) {
                        //售罄
                        iv_mstag.setImageResource(R.mipmap.ms_ysq);
                        actStatus = 5;
                    } else {
                        actStatus = 1;
                    }

                }
            } else if (openGroupBean.activityStatus.equals("2")) {
                //已结束
                actStatus = 3;
            }


        }
        if (openGroupBean.activityType.equals("6")) {
            //限时秒杀
            String s = "【活动说明：活动<font color='#FF464E'><b>" + openGroupBean.startTime + "</b></font>开始，限量<font color='#FF464E'><b>" + openGroupBean.limitNum + "</b></font>份，售完即止！商品售完时未能成团者即视为活动失败】";
            tv_shuoming.setText(Html.fromHtml(s));
            tv_alonePrice.setVisibility(View.GONE);
            tv_pintuanprice.setVisibility(View.GONE);
            tv_bom_huodong.setVisibility(View.VISIBLE);
            if (openGroupBean.activityStatus.equals("0")) {
                //未开始
                iv_mstag.setImageResource(R.mipmap.ms_jjkq);
                actStatus = 6;
            } else if (openGroupBean.activityStatus.equals("1")) {
                //活动中
                if (openGroupBean.isSellOut.equals("1")) {
                    //售罄
                    iv_mstag.setImageResource(R.mipmap.ms_ysq);
                    //iv_kefu.setVisibility(View.GONE);
                    actStatus = 5;
                } else {
                    actStatus = 4;
                    iv_mstag.setImageResource(R.mipmap.ms_kqz);

                }
            } else if (openGroupBean.activityStatus.equals("2")) {
                //活动结束
                iv_mstag.setImageResource(R.mipmap.ms_yjs);
                //iv_kefu.setVisibility(View.GONE);
                actStatus = 7;
            }
        }

        if (openGroupBean.activityType.equals("7")) {
            //免费抽奖
            tv_zhongjiantips.setVisibility(View.VISIBLE);
            tv_zhongjiantips.setText("免费抽奖");
            String s = "活动时间：<font color='#FF464E'><b>" + openGroupBean.activitySTime + "</b></font> 到 <font color='#FF464E'><b>" + openGroupBean.activityETime + "</b></font>" +
                    "<br>活动规则：<br>1、0元立即开团，规定时间内邀请好友一起0元拼团，拼团成功后将进入待抽奖箱。" +
                    "<br>2、待活动时间结束，系统将随机抽取幸运的中奖团，该团成员将获得奖品。" +
                    "<br>3、不成团或无中奖用户均全额退款。" +
                    "<br>4、每人均有一次参与（开团或参团）机会。" +
                    "<br>5、活动奖品预计开奖后72小时内发放。" +
                    "<br><br>温馨提醒:" +
                    "<br>每个团组团有效期为24个小时，如团结束时间大于活动结束时间时，则按活动结束的时间。";
            tv_shuoming.setText(Html.fromHtml(s));
            tv_alonePrice.setVisibility(View.GONE);
            tv_pintuanprice.setVisibility(View.GONE);
            tv_bom_huodong.setVisibility(View.VISIBLE);
            if (openGroupBean.isWaitOpen.equals("0")) {
                //活动进行中
                if (openGroupBean.isOpen.equals("1") || openGroupBean.isGroup.equals("1")) {
                    //参加过
                    actStatus = 9;
                } else {
                    if (openGroupBean.isSellOut.equals("1")) {
                        //售罄
                        iv_mstag.setImageResource(R.mipmap.ms_ysq);
                        actStatus = 5;
                    } else {
                        actStatus = 8;
                    }

                }
            } else if (openGroupBean.isWaitOpen.equals("1")) {
                //待开奖
                actStatus = 10;
            } else if (openGroupBean.isWaitOpen.equals("2")) {
                //已开奖/活动已结束
                actStatus = 11;
            } else if (openGroupBean.isWaitOpen.equals("3")) {
                //活动未开始
                actStatus = 12;
            }

        }


        LogUtil.Log("actStatus:" + actStatus);
        switch (actStatus) {
            case 1:
                tv_bom_huodong.setText("￥0.1\n" + openGroupBean.groupNum + "人成团");
                break;
            case 2:
                tv_bom_huodong.setText("您已参加过该活动");
                tv_bom_huodong.setBackgroundResource(R.color.gray);
                break;
            case 3:
                tv_bom_huodong.setText("查看中奖名单");
                break;
            case 4:
                tv_bom_huodong.setText("￥" + openGroupBean.productPrice + "\n" + openGroupBean.groupNum + "人成团");
                break;
            case 5:
                tv_bom_huodong.setText("更多拼团");
                break;
            case 6:
                tv_bom_huodong.setText("即将开抢");
                break;
            case 7:
                tv_bom_huodong.setText("更多拼团");
                break;
            case 8:
                tv_bom_huodong.setText("￥0\n" + openGroupBean.groupNum + "人团");
                break;
            case 9:
                tv_bom_huodong.setText("您已参加过该活动");
                tv_bom_huodong.setBackgroundResource(R.color.gray);
                break;
            case 10:
                tv_bom_huodong.setText("等待开奖中");
                tv_bom_huodong.setBackgroundResource(R.color.gray);
                break;
            case 11:
                tv_bom_huodong.setText("查看中奖名单");
                break;
            case 12:
                tv_bom_huodong.setBackgroundResource(R.color.gray);
                tv_bom_huodong.setText("活动尚未开始");
                break;
        }

        presenter.getShareContentApiGet(source, activityId);

        if (presenter.openGroupBean.activityStatus.equals("1") && presenter.openGroupBean.isSellOut.equals("1")) {
            //进行中已售罄
            setSellOut();
        }
    }

    Handler handler = new Handler();
    Runnable runnable = new Runnable() {
        @Override
        public void run() {
//            date = GrtJs(date);
            if (date != null) {
                for (int i = 0; i < date.size(); i++) {
                    try {
                        long hour = Long.parseLong(date.get(i).Hour);
                        long min = Long.parseLong(date.get(i).Min);
                        long s = Long.parseLong(date.get(i).Ss);
                        if (hour != 0 || min != 0 || s != 0) {
                            s--;
                            if (s < 0) {
                                min--;
                                s = 59;
                                if (min < 0) {
                                    min = 59;
                                    hour--;
                                    if (hour < 0) {
                                        // 倒计时结束
                                        hour = 0;
                                    }
                                }
                            }
                            date.get(i).Hour = BaseUtil.Bl(hour);
                            date.get(i).Min = BaseUtil.Bl(min);
                            date.get(i).Ss = BaseUtil.Bl(s);
                        } else {
                            presenter.loadData(activityId, pid, tag);
                            android.util.Log.d("++", "run: ++++++++++++++++++++++++++++++++++++");
                        }
                    } catch (Exception e) {
                    }
                }
            }
            try {
                groupListAdapter.notifyDataSetChanged();
            } catch (Exception e) {
            }
            handler.postDelayed(this, 1000);

        }
    };
    WaitGroupListAdapter groupListAdapter;
    ListViewForScrollView lv_t;
    List<OpenGroupBean.WaitGroup> date;

    @Override
    public void setWaitGroupList(List<OpenGroupBean.WaitGroup> waitGroupList) {
        iv_pingtuantips.setVisibility(View.VISIBLE);
        if (waitGroupList != null) {
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            for (int i = 0; i < waitGroupList.size(); i++) {
                try {
                    Date parse1 = dateFormat.parse(waitGroupList.get(i).endTime);
                    Date parse = dateFormat.parse(waitGroupList.get(i).nowTime);
                    long diff = parse1.getTime() - parse.getTime();
                    long day = diff / (24 * 60 * 60 * 1000);
                    long hour = (diff - day * (1000 * 60 * 60 * 24)) / (1000 * 60 * 60) + (day * 24);
                    long hours = (diff - day * (1000 * 60 * 60 * 24)) / (1000 * 60 * 60);
                    long min = ((diff / (60 * 1000)) - day * 24 * 60 - hours * 60);
                    long s = (diff / 1000 - day * 24 * 60 * 60 - hours * 60 * 60 - min * 60);
                    if (hour != 0 || min != 0 || s != 0) {
                        s--;
                        if (s < 0) {
                            min--;
                            s = 59;
                            if (min < 0) {
                                min = 59;
                                hour--;
                                if (hour < 0) {
                                    // 倒计时结束
                                    hour = 0;
                                }
                            }
                        }
                    }
                    waitGroupList.get(i).Hour = BaseUtil.Bl(hour);
                    waitGroupList.get(i).Min = BaseUtil.Bl(min);
                    waitGroupList.get(i).Ss = BaseUtil.Bl(s);

                } catch (Exception e) {
                }
            }

        }
        date = waitGroupList;
        groupListAdapter = new WaitGroupListAdapter(this, waitGroupList);
        lv_t.setAdapter(groupListAdapter);
    }

    ProgressDialog progressDialog;

    @Override
    public void showProgressDialog(String msg) {
        if (progressDialog == null) {
            progressDialog = new ProgressDialog(GoodsDetailActivity.this);
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
    public void onaddFavoriteSuccess() {
        iscollect = true;
        iv_collect.setImageResource(R.mipmap.gd_collect);
    }

    @Override
    public void ondelFavoriteSuccess() {
        iscollect = false;
        iv_collect.setImageResource(R.mipmap.gd_collcet_empty);
    }


    GridViewForScrollView gv_cainixihuan;
    GuessYourLikeAdapter guessYourLikeAdapter;

    @Override
    public void setguessYourLike(List<GuessYourLikeBean> list) {
        iv_gylike.setVisibility(View.VISIBLE);
        guessYourLikeAdapter = new GuessYourLikeAdapter(this, list, this);
        gv_cainixihuan.setAdapter(guessYourLikeAdapter);
    }

    @Override
    public void setEmptyView() {
        rl_empty.setVisibility(View.VISIBLE);
        loadguessYourLike();
    }

    @Override
    public void loadguessYourLike() {
        presenter.guessYourLike(activityId);
    }

    @Override
    public void setSellOut() {
        tv_bom_huodong.setVisibility(View.GONE);
        tv_bom_sellout.setVisibility(View.VISIBLE);
        tv_pintuanprice.setVisibility(View.GONE);
        tv_alonePrice.setVisibility(View.GONE);
    }

    @Override
    public void addguessFavorite(String activityId, String pid, int position) {
        if (UserInfoUtils.isLogin()) {
            presenter.addguessFavorite(activityId, pid, position);
        } else {
            Intent intent = new Intent(GoodsDetailActivity.this, PhoneLoginActivity.class);
            intent.putExtra("tag", 99);
            startActivityForResult(intent, AppConfig.LOGIN);
        }

    }

    @Override
    public void delguessFavorite(String activityId, String pid, int position) {
        if (UserInfoUtils.isLogin()) {
            presenter.delguessFavorite(activityId, pid, position);
        } else {
            Intent intent = new Intent(GoodsDetailActivity.this, PhoneLoginActivity.class);
            intent.putExtra("tag", 99);
            startActivityForResult(intent, AppConfig.LOGIN);
        }
    }

    @Override
    public void updateguessYourLike() {
        if (guessYourLikeAdapter != null) {
            guessYourLikeAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void orderConfirm() {
        try {
            String skuid = skuPresenter.forValidSKuId();
            Intent intent = new Intent(GoodsDetailActivity.this, ConfirmOrderActivity.class);
            intent.putExtra("activityId", activityId);
            intent.putExtra("num", skuPresenter.num + "");
            intent.putExtra("pid", pid);
            intent.putExtra("skuLinkId", skuid);
            intent.putExtra("source", source);
            initializeSkuPopwindow();
            startActivityForResult(intent, 66);
            skupopupWindow.dismiss();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private UMShareListener umShareListener = new UMShareListener() {
        @Override
        public void onResult(SHARE_MEDIA platform) {
            Log.d("plat", "platform" + platform);

            Toast.makeText(GoodsDetailActivity.this, platform + " 分享成功啦", Toast.LENGTH_SHORT).show();

        }

        @Override
        public void onError(SHARE_MEDIA platform, Throwable t) {
            Toast.makeText(GoodsDetailActivity.this, platform + " 分享失败啦", Toast.LENGTH_SHORT).show();
            if (t != null) {
                Log.d("throw", "throw:" + t.getMessage());
            }
        }

        @Override
        public void onCancel(SHARE_MEDIA platform) {
            Toast.makeText(GoodsDetailActivity.this, platform + " 分享取消了", Toast.LENGTH_SHORT).show();
        }
    };

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        UMShareAPI.get(this).onActivityResult(requestCode, resultCode, data);
        if (requestCode == AppConfig.LOGIN || requestCode == 66 && resultCode == RESULT_OK) {
            presenter.loadData(activityId, pid, tag);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_fx:
                backgroundAlpha(GoodsDetailActivity.this, 0.5f);
                getSharePopupWindow();
                // 这里是位置显示方式,在屏幕的左侧
                popupWindow1.showAtLocation(v, Gravity.BOTTOM, 0, 0);
                break;
            case R.id.iv_kefu:
                HashMap<String, String> clientInfo = new HashMap<>();
                clientInfo.put("name", Untool.getName());
                clientInfo.put("avatar", Untool.getImage());
//                clientInfo.put("gender", "男");
                clientInfo.put("tel", Untool.getPhone());
                LogUtil.Log("tel" + Untool.getPhone());
                clientInfo.put("comment", "产品Id:" + pid);
//                clientInfo.put("技能1", "休刊");
                Intent intent1 = new MQIntentBuilder(this).setCustomizedId(Untool.getUid()).setClientInfo(clientInfo)
                        // 相同的 id 会被识别为同一个顾客
                        .build();
                new Handler().postDelayed(new Runnable() {

                    public void run() {
                        HashMap<String, String> updateInfo = new HashMap<>();
                        updateInfo.put("name", Untool.getName());
                        updateInfo.put("avatar", Untool.getImage());
                        updateInfo.put("tel", Untool.getPhone());
                        updateInfo.put("comment", "产品Id:" + pid);
                        MQManager.getInstance(GoodsDetailActivity.this).updateClientInfo(updateInfo, new OnClientInfoCallback() {
                            @Override
                            public void onSuccess() {

                            }

                            @Override
                            public void onFailure(int i, String s) {

                            }
                        });
                    }

                }, 500);
                startActivity(intent1);
                break;
            case R.id.iv_empty_back:
                onBackPressed();
                break;
            case R.id.iv_back:
                onBackPressed();
                break;

            case R.id.iv_home:
                startActivity(new Intent(GoodsDetailActivity.this, TabActivity.class));
                EventBus.getDefault().post(new AnyEventType("home"));
                finish();
                break;
            case R.id.iv_collect:
                if (UserInfoUtils.isLogin()) {
                    if (iscollect) {
                        presenter.delFavorite(activityId, pid);
                    } else {
                        presenter.addFavorite(activityId, pid);
                    }
                } else {
                    Intent intent = new Intent(GoodsDetailActivity.this, PhoneLoginActivity.class);
                    intent.putExtra("tag", 99);
                    startActivityForResult(intent, AppConfig.LOGIN);

                }

                break;
            case R.id.tv_pintuanprice:
                if (UserInfoUtils.isLogin()) {
                    source = presenter.openGroupBean.activityType;
                    initializeSkuPopwindow();
                    skuPresenter.loadSku(pid);
                } else {
                    Intent intent = new Intent(GoodsDetailActivity.this, PhoneLoginActivity.class);
                    intent.putExtra("tag", 99);
                    startActivityForResult(intent, AppConfig.LOGIN);

                }

                break;
            case R.id.tv_bom_huodong:
                switch (actStatus) {
                    case 1:
                        if (UserInfoUtils.isLogin()) {
                            source = presenter.openGroupBean.activityType;
                            initializeSkuPopwindow();
                            skuPresenter.loadSku(pid);
                        } else {
                            Intent intent = new Intent(GoodsDetailActivity.this, PhoneLoginActivity.class);
                            intent.putExtra("tag", 99);
                            startActivityForResult(intent, AppConfig.LOGIN);

                        }
                        break;
                    case 2:
                        break;
                    case 3:
                        Intent p = new Intent(GoodsDetailActivity.this, PrizeDetailMoreActivity.class);
                        p.putExtra("activityId", activityId);
                        p.putExtra("activityType", "5");
                        startActivity(p);
                        finish();
                        break;
                    case 4:
                        if (UserInfoUtils.isLogin()) {
                            source = presenter.openGroupBean.activityType;
                            initializeSkuPopwindow();
                            skuPresenter.loadSku(pid);
                        } else {
                            Intent intent = new Intent(GoodsDetailActivity.this, PhoneLoginActivity.class);
                            intent.putExtra("tag", 99);
                            startActivityForResult(intent, AppConfig.LOGIN);

                        }
                        break;
                    case 5:
                        Intent m5 = new Intent(GoodsDetailActivity.this, TabActivity.class);
                        startActivity(m5);
                        finish();
                        break;
                    case 6:
                        Intent m6 = new Intent(GoodsDetailActivity.this, TabActivity.class);
                        startActivity(m6);
                        finish();
                        break;
                    case 7:
                        Intent m7 = new Intent(GoodsDetailActivity.this, TabActivity.class);
                        startActivity(m7);
                        finish();
                        break;
                    case 8:
                        if (UserInfoUtils.isLogin()) {
                            source = presenter.openGroupBean.activityType;
                            initializeSkuPopwindow();
                            skuPresenter.loadSku(pid);
                        } else {
                            Intent intent = new Intent(GoodsDetailActivity.this, PhoneLoginActivity.class);
                            intent.putExtra("tag", 99);
                            startActivityForResult(intent, AppConfig.LOGIN);

                        }
                        break;
                    case 9:
                        //Intent m7 = new Intent(GoodsDetailActivity.this, MiaoShaTabActivity.class);
                        //startActivity(m7);
                        break;
                    case 10:
                        //Intent m7 = new Intent(GoodsDetailActivity.this, MiaoShaTabActivity.class);
                        //startActivity(m7);
                        break;
                    case 11:
                        Intent intent11 = new Intent(GoodsDetailActivity.this, PrizeDetailMoreActivity.class);
                        intent11.putExtra("activityId", activityId);
                        intent11.putExtra("activityType", "7");
                        startActivity(intent11);
                        finish();
                        break;
                    case 12:
                        //Intent m7 = new Intent(GoodsDetailActivity.this, MiaoShaTabActivity.class);
                        //startActivity(m7);
                        break;
                }
                break;

            case R.id.tv_alonePrice:
                if (UserInfoUtils.isLogin()) {
                    source = "4";
                    initializeSkuPopwindow();
                    skuPresenter.loadSku(pid);
                } else {
                    Intent intent = new Intent(GoodsDetailActivity.this, PhoneLoginActivity.class);
                    intent.putExtra("tag", 99);
                    startActivityForResult(intent, AppConfig.LOGIN);

                }

                break;
            case R.id.add:
                skuPresenter.num = skuPresenter.num + 1;
                tv_num.setText(skuPresenter.num + "");
                break;
            case R.id.reduce:
                if (skuPresenter.num > 1) {
                    skuPresenter.num = skuPresenter.num - 1;
                    tv_num.setText(skuPresenter.num + "");
                }
                break;
            case R.id.btn_offirm:
                String skuid = skuPresenter.forValidSKuId();
                presenter.confirmOrder(source, activityId, null, skuPresenter.num, pid, skuid);
                break;


        }
    }


    @Override
    protected void onDestroy() {
        skuPresenter.onDestroy();
        super.onDestroy();
        handler.removeCallbacksAndMessages(null);
    }


    //sku
    @Override
    public void initializeSkuPopwindow() {
        skuPresenter.num = 1;
        skuPresenter.skuColor = null;
        skuPresenter.skuFormat = null;
        if (skuColorAdapter != null) {
            skuColorAdapter.selectPosition = -1;
        }
        if (formatAdapter != null) {
            formatAdapter.selectPosition = -1;
        }
    }

    @Override
    public void setSkuOnClickListener() {
        if (source.equals("3") ||source.equals("2") || source.equals("5") || source.equals("6") || source.equals("7")) {
            //团免,0.1秒杀,限时秒杀,猜价格
            add.setOnClickListener(null);
            reduce.setOnClickListener(null);
        } else {
            add.setOnClickListener(this);
            reduce.setOnClickListener(this);
        }
    }


    @Override
    public void initSkuPopuWindow() {
        try {
            View view = getLayoutInflater().inflate(
                    R.layout.activity_goods_detail_sku_pop, null);
            skupopupWindow = new PopupWindow(view,
                    WindowManager.LayoutParams.MATCH_PARENT,
                    WindowManager.LayoutParams.WRAP_CONTENT);
            skupopupWindow.setFocusable(true);
            // 实例化一个ColorDrawable颜色为半透明
            ColorDrawable dw = new ColorDrawable(0x00000000);
            skupopupWindow.setBackgroundDrawable(dw);
            // 设置popWindow的显示和消失动画
            skupopupWindow.setAnimationStyle(R.style.AnimBottom);

            skupopupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {

                @Override
                public void onDismiss() {
                    WindowManager.LayoutParams lp = getWindow().getAttributes();
                    lp.alpha = 1f;
                    getWindow().setAttributes(lp);

                }
            });
            initSkuPopView(view);
            skuColorAdapter = new SkuColorAdapter(this, skuPresenter.skuColorValue);
            gv_color.setAdapter(skuColorAdapter);
            if (skuPresenter.SKU_MODEL == skuPresenter.SKU_MODEL_TWO) {
                formatAdapter = new SkuFormatAdapter(this, skuPresenter.skuFormatValue, skuPresenter);
                gv_format.setAdapter(formatAdapter);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public void updateSkuPopuWindow() {
        try {
            tv_num.setText(skuPresenter.num + "");
            skuColorAdapter.notifyDataSetChanged();
            if (skuPresenter.SKU_MODEL == skuPresenter.SKU_MODEL_TWO) {
                formatAdapter.notifyDataSetChanged();
            }
            skupopupWindow.update();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @Override
    public void initSkuPopView(View view) {
        try {
            iv_iamge = (ImageView) view.findViewById(R.id.iv_iamge);
            tv_pName = (TextView) view.findViewById(R.id.tv_pName);
            tv_price = (TextView) view.findViewById(R.id.tv_price);
            tv_colortitle = (TextView) view.findViewById(R.id.tv_colortitle);
            tv_formattitle = (TextView) view.findViewById(R.id.tv_formattitle);
            gv_color = (GridViewForScrollView) view.findViewById(R.id.gv_color);
            gv_color.setSelector(new ColorDrawable(Color.TRANSPARENT));
            gv_format = (GridViewForScrollView) view.findViewById(R.id.gv_format);
            gv_format.setSelector(new ColorDrawable(Color.TRANSPARENT));
            tv_skutips = (TextView) view.findViewById(R.id.tv_skutips);
            add = view.findViewById(R.id.add);
            reduce = view.findViewById(R.id.reduce);
            tv_num = (TextView) view.findViewById(R.id.tv_num);
            btn_offirm = (Button) view.findViewById(R.id.btn_offirm);
            //提示
            if (skuPresenter.SKU_MODEL == skuPresenter.SKU_MODEL_SINGLE) {
                tv_skutips.setText("请选择" + skuPresenter.skuList.get(0).skuTitle);
                tv_colortitle.setText(skuPresenter.skuList.get(0).skuTitle);
            } else {
                tv_formattitle.setVisibility(View.VISIBLE);
                gv_format.setVisibility(View.VISIBLE);
                tv_skutips.setText("请选择" + skuPresenter.skuList.get(0).skuTitle + "和" + skuPresenter.skuList.get(1).skuTitle);
                tv_colortitle.setText(skuPresenter.skuList.get(0).skuTitle);
                tv_formattitle.setText(skuPresenter.skuList.get(1).skuTitle);

            }
            tv_num.setText(skuPresenter.num + "");
            if (skuPresenter.validSKu != null && !skuPresenter.validSKu.isEmpty()) {
                Glide.with(this).load(skuPresenter.validSKu.get(0).skuImg).placeholder(R.mipmap.default_load).error(R.mipmap.default_load).into(iv_iamge);
            }

            LogUtil.Log("skucolorMAxsize:======" + skuPresenter.skucolorMAxsize);
            int colornum = skuPresenter.getNumColumns(skuPresenter.skucolorMAxsize);
            gv_color.setNumColumns(colornum);
            gv_color.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    LogUtil.Log("gv_color:==========" + position);
                    LogUtil.Log("gv_colorclickable:==========" + skuPresenter.skuColorValue.get(position).clickable);
                    skuColorAdapter.selectPosition = position;
                    skuPresenter.skuColor = skuPresenter.skuColorValue.get(position).optionValue;
                    skuColorAdapter.notifyDataSetChanged();
                    if (skuPresenter.SKU_MODEL == skuPresenter.SKU_MODEL_TWO) {
                        formatAdapter.notifyDataSetChanged();
                    }
                    skuPresenter.setBtn_offirm(btn_offirm, iv_iamge, GoodsDetailActivity.this);
                }


            });

            if (skuPresenter.SKU_MODEL == skuPresenter.SKU_MODEL_TWO) {
                LogUtil.Log("skuformatMAxsize:======" + skuPresenter.skuformatMAxsize);
                int formatnum = skuPresenter.getNumColumns(skuPresenter.skuformatMAxsize);
                gv_format.setNumColumns(formatnum);
                gv_format.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        LogUtil.Log("gv_format:==========" + position);
                        LogUtil.Log("gv_formatclickable:==========" + skuPresenter.skuFormatValue.get(position).clickable);
                        if (skuPresenter.skuFormatValue.get(position).clickable == 0) {
                            formatAdapter.selectPosition = position;
                            skuPresenter.skuFormat = skuPresenter.skuFormatValue.get(position).optionValue;
                            formatAdapter.notifyDataSetChanged();
                            skuColorAdapter.notifyDataSetChanged();
                            skuPresenter.setBtn_offirm(btn_offirm, iv_iamge, GoodsDetailActivity.this);
                        }
                    }

                });
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    @Override
    public void showSkuPopuWindow() {
        if (skupopupWindow == null) {
            initSkuPopuWindow();
        } else {
            updateSkuPopuWindow();
        }
        WindowManager.LayoutParams lp = getWindow().getAttributes();
        lp.alpha = 0.4f;
        getWindow().setAttributes(lp);
        setSkuPopPrice();
        setSkuOnClickListener();
        if (skuPresenter.validSKu != null && skuPresenter.validSKu.size() == 1) {
            //如果sku只有一个直接提交订单
            skuColorAdapter.selectPosition = 0;
            skuColorAdapter.notifyDataSetChanged();
            if (skuPresenter.SKU_MODEL == skuPresenter.SKU_MODEL_TWO) {
                formatAdapter.selectPosition = 0;
                formatAdapter.notifyDataSetChanged();
            }

        } else if (skuPresenter.validSKu != null && skuPresenter.validSKu.size() > 1
                ) {
            if (skuPresenter.SKU_MODEL == skuPresenter.SKU_MODEL_SINGLE) {
                skuColorAdapter.selectPosition = 0;
                skuColorAdapter.notifyDataSetChanged();
            } else {
                formatAdapter.selectPosition = 0;
                formatAdapter.notifyDataSetChanged();
            }

        }
        skuPresenter.setBtn_offirm(btn_offirm, iv_iamge, GoodsDetailActivity.this);
        skupopupWindow.showAtLocation(rl_main, Gravity.BOTTOM, 0, 0);


    }

    @Override
    public void setSkuPopPrice() {
        if (source.equals("4")) {
            tv_price.setText("￥" + presenter.openGroupBean.alonePrice);
        } else {
            tv_price.setText("￥" + presenter.openGroupBean.productPrice);
        }
    }
}
