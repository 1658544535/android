package com.qunyu.taoduoduo.activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.Html;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.qunyu.taoduoduo.R;
import com.qunyu.taoduoduo.adapter.GroupDetailGuessYourLikeAdapter;
import com.qunyu.taoduoduo.adapter.KaiTuanTipsAdapter;
import com.qunyu.taoduoduo.adapter.SkuColorAdapter;
import com.qunyu.taoduoduo.adapter.SkuFormatAdapter;
import com.qunyu.taoduoduo.base.BaseActivity;
import com.qunyu.taoduoduo.base.BaseUtil;
import com.qunyu.taoduoduo.bean.GroupDetailBean;
import com.qunyu.taoduoduo.bean.GuessYourLikeBean;
import com.qunyu.taoduoduo.mvpview.GroupDetailView;
import com.qunyu.taoduoduo.mvpview.SkuModeView;
import com.qunyu.taoduoduo.presenter.GroupDetailPresenter;
import com.qunyu.taoduoduo.presenter.SkuPresenter;
import com.qunyu.taoduoduo.utils.LogUtil;
import com.qunyu.taoduoduo.utils.StringUtils;
import com.qunyu.taoduoduo.utils.ToastUtils;
import com.qunyu.taoduoduo.view.CircleTransform;
import com.qunyu.taoduoduo.widget.GridViewForScrollView;
import com.qunyu.taoduoduo.widget.ListViewForScrollView;
import com.qunyu.taoduoduo.widget.ObservableScrollView;
import com.umeng.analytics.MobclickAgent;
import com.umeng.socialize.ShareAction;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.media.UMImage;
import com.umeng.socialize.utils.Log;
import com.zhy.android.percent.support.PercentLinearLayout;
import com.zhy.android.percent.support.PercentRelativeLayout;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;


/**
 * Created by Administrator on 2016/11/23.
 * 1.21优化新团详情页面
 */

public class GroupDetailActivity extends BaseActivity implements GroupDetailView, View.OnClickListener, SkuModeView {
    String recordId;//团记录id
    String tag;//tag团免 pay支付页跳转，弹窗判断
    String source;//活动类型：1-普通2-团免3-猜价5-0.1抽奖6-限时秒杀7-免费抽奖
    GroupDetailPresenter presenter;

    //detail
    ImageView iv_tag;
    TextView tv_zjxq;//中奖详情
    ImageView iv_product;
    TextView tv_pname;
    TextView tv_jiage;
    TextView tv_grouptag;
    int constastus;// 1商品已售罄 2已成团 3参团成功 4开团成功 5拼团成功 6拼团中 7拼团失败 8已成团待开奖 9已开奖
    PercentRelativeLayout rl_detail;

    //猜你喜欢
    ImageView iv_gylike;//猜你喜欢头部

    //groupTips
    TextView tv_group_tips;
    PercentLinearLayout ll_grouppeople;
    private View convertView[] = null;
    ImageView iv_headlogo;
    TextView tv_headtag;

    //底部
    TextView tv_more;
    TextView tv_tijiao;

    //全部团详情
    KaiTuanTipsAdapter kaiTuanTipsAdapter;
    ListViewForScrollView listViewForScrollView;
    boolean isshow = false;
    TextView tv_showall;
    PercentLinearLayout ll_allpeople;//
    PercentRelativeLayout rl_allpeoplebottom;
    TextView tv_btm_pmsg;
    ImageView wenhao;

    //Time
    PercentRelativeLayout rl_time;
    TextView tv_hh;
    TextView tv_mm;
    TextView tv_ss;
    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    String thour;
    String tmin;
    String tss;

    //share
    ImageView iv_fenxiang;
    PopupWindow sharePopupWindow;

    //tips
    PopupWindow tipspopupWindow;
    TextView tv_message;
    PercentRelativeLayout rl_main;

    //sku
    SkuColorAdapter skuColorAdapter;
    SkuFormatAdapter formatAdapter;
    PopupWindow skupopupWindow;
    ImageView iv_iamge;
    TextView tv_productName;
    TextView tv_skuprice;
    TextView tv_colortitle;
    TextView tv_formattitle;
    GridViewForScrollView gv_color;
    GridViewForScrollView gv_format;
    View add;
    View reduce;
    TextView tv_pName;
    TextView tv_skutips;
    TextView tv_num;
    Button btn_offirm;
    SkuPresenter skuPresenter;
    TextView tv_price;

    //rule
    ImageView iv_rule;
    PopupWindow rulepopupWindow;

    //0.1rule
    TextView tv_yimaoqian;
    PopupWindow rule01popupWindow;

    //红包
    ImageView iv_hongbao;


    //置顶
    ImageView iv_top;
    ObservableScrollView scrollView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        baseSetContentView(R.layout.activity_group_detail);
        recordId = getIntent().getStringExtra("recordId");
        LogUtil.Log("recordId" + recordId);
        if (StringUtils.isBlank(recordId)) {
            ToastUtils.showShortToast(this, "缺少参数");
            return;
        }
        tag = getIntent().getStringExtra("tag");
        LogUtil.Log("tag" + tag);
        initView();
        presenter = new GroupDetailPresenter(this, this);
        presenter.loadData(recordId);
        presenter.getShareContentApiGet(recordId);
        skuPresenter = new SkuPresenter(this, this, this);
    }


    @Override
    public void initView() {
        iv_top = (ImageView) findViewById(R.id.iv_top);
        iv_top.setOnClickListener(this);
        scrollView = (ObservableScrollView) findViewById(R.id.scrollView);
        scrollView.setScrollViewListener(new ObservableScrollView.ScrollViewListener() {
            @Override
            public void onScrollChanged(ObservableScrollView scrollView, int x, int y, int oldx, int oldy) {
                if (y > 800) {
                    iv_top.setVisibility(View.VISIBLE);
                } else {
                    iv_top.setVisibility(View.INVISIBLE);
                }
            }
        });
        iv_hongbao = (ImageView) findViewById(R.id.iv_hongbao);
        iv_hongbao.setOnClickListener(this);
        wenhao = (ImageView) findViewById(R.id.wenhao);
        tv_btm_pmsg = (TextView) findViewById(R.id.tv_btm_pmsg);
        rl_allpeoplebottom = (PercentRelativeLayout) findViewById(R.id.rl_allpeoplebottom);
        ll_allpeople = (PercentLinearLayout) findViewById(R.id.ll_allpeople);
        ll_grouppeople = (PercentLinearLayout) findViewById(R.id.ll_grouppeople);
        tv_yimaoqian = (TextView) findViewById(R.id.tv_yimaoqian);
        iv_rule = (ImageView) findViewById(R.id.iv_rule);
        rl_main = (PercentRelativeLayout) findViewById(R.id.rl_main);
        iv_fenxiang = (ImageView) findViewById(R.id.iv_fenxiang);
        iv_fenxiang.setVisibility(View.VISIBLE);
        iv_fenxiang.setOnClickListener(this);
        rl_time = (PercentRelativeLayout) findViewById(R.id.rl_time);
        tv_hh = (TextView) findViewById(R.id.tv_hh);
        tv_mm = (TextView) findViewById(R.id.tv_mm);
        tv_ss = (TextView) findViewById(R.id.tv_ss);
        rl_detail = (PercentRelativeLayout) findViewById(R.id.rl_detail);
        tv_showall = (TextView) findViewById(R.id.tv_showall);
        tv_more = (TextView) findViewById(R.id.tv_more);
        tv_more.setOnClickListener(this);
        tv_tijiao = (TextView) findViewById(R.id.tv_tijiao);
        tv_tijiao.setOnClickListener(this);
        tv_group_tips = (TextView) findViewById(R.id.tv_group_tips);
        iv_tag = (ImageView) findViewById(R.id.iv_tag);
        tv_zjxq = (TextView) findViewById(R.id.tv_zjxq);
        tv_grouptag = (TextView) findViewById(R.id.tv_grouptag);
        listViewForScrollView = (ListViewForScrollView) findViewById(R.id.lv_gpeople);
        listViewForScrollView.setFocusable(false);
        iv_product = (ImageView) findViewById(R.id.iv_product);
        tv_pname = (TextView) findViewById(R.id.tv_pname);
        tv_jiage = (TextView) findViewById(R.id.tv_jiage);
        iv_gylike = (ImageView) findViewById(R.id.iv_gylike);
        gv_cainixihuan = (GridViewForScrollView) findViewById(R.id.gv_cainixihuan);
        gv_cainixihuan.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                GuessYourLikeBean guessYourLikeBean = (GuessYourLikeBean) parent.getAdapter().getItem(position);
                Intent intent = new Intent(GroupDetailActivity.this, GoodsDetailActivity.class);
                intent.putExtra("pid", guessYourLikeBean.productId);
                intent.putExtra("activityId", guessYourLikeBean.activityId);
                startActivity(intent);
            }

        });
        gv_cainixihuan.setFocusable(false);
        tv_showall.setOnClickListener(this);
        tv_zjxq.setOnClickListener(this);
        rl_detail.setOnClickListener(this);
        iv_rule.setOnClickListener(this);
        tv_yimaoqian.setOnClickListener(this);
    }

    @Override
    public void toastMessage(String msg) {
        ToastUtils.showShortToast(this, msg);
    }

    ProgressDialog progressDialog;

    @Override
    public void showProgressDialog(String msg) {
        if (progressDialog == null) {
            progressDialog = new ProgressDialog(GroupDetailActivity.this);
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
    public void setDetail(GroupDetailBean groupDetailBean) {
        try {
            source = groupDetailBean.activityType;
            Glide.with(GroupDetailActivity.this).load(groupDetailBean.productImage).placeholder(R.mipmap.default_load).error(R.mipmap.default_load).into(iv_product);
            tv_pname.setText("          " + groupDetailBean.productName);
            tv_jiage.setText("￥" + groupDetailBean.groupPrice);
            tv_grouptag.setText(groupDetailBean.groupNum + "人团");
            if (source.equals("5")) {
                tv_yimaoqian.setVisibility(View.VISIBLE);
            }
            if (groupDetailBean.status.equals("0")) {
                //拼团中
                if (groupDetailBean.isSellOut.equals("1")) {
                    //已售罄
                    baseSetText("商品已售罄");
                    constastus = 1;
                } else {
                    if (groupDetailBean.userIsHead.equals("1")) {
                        //开团成功
                        //自己开的团
                        baseSetText("开团成功");
                        constastus = 4;
                    } else if (groupDetailBean.isGroup.equals("1")) {
                        //已参团
                        //别人开的
                        baseSetText("参团成功");
                        constastus = 3;

                    } else {
                        //未参团
                        baseSetText("拼团中");
                        constastus = 6;
                    }


                }
            } else if (groupDetailBean.status.equals("1")) {
                //已成功
                if (groupDetailBean.isGroup.equals("1") || groupDetailBean.isOpen.equals("1")) {
                    //已参团
                    baseSetText("拼团成功");
                    constastus = 5;
                } else if (groupDetailBean.isGroup.equals("0") && groupDetailBean.isOpen.equals("0")) {
                    //没参团，已成团
                    baseSetText("已成团");
                    constastus = 2;

                }
            } else if (groupDetailBean.status.equals("2")) {
                //组团失败
                baseSetText("拼团失败");
                constastus = 7;
            } else if (groupDetailBean.status.equals("3")) {
                //待开奖，免费抽奖
                baseSetText("待开奖");
                constastus = 8;

            } else if (groupDetailBean.status.equals("4")) {
                //已开奖，免费抽奖
                baseSetText("已开奖");
                constastus = 9;
            }
            LogUtil.Log("constastus", ":-------" + constastus);
            switch (constastus) {
                case 1:
                    iv_tag.setImageResource(R.mipmap.agd_ysq);
                    tv_group_tips.setText(R.string.txq_sq);
                    tv_tijiao.setText("商品已售罄");
                    break;
                case 2:
                    iv_tag.setImageResource(R.mipmap.agd_yct);
                    tv_group_tips.setText(R.string.txq_yct);
                    tv_tijiao.setText("我要开团");
                    if (source.equals("5")) {
                        tv_zjxq.setVisibility(View.VISIBLE);
                    }
                    break;
                case 3:
                    String s = "还差<font color='#FF464E'>" + groupDetailBean.poorNum + "</font>人，赶紧分享召集小伙伴组团啦~";
                    tv_group_tips.setText(Html.fromHtml(s));
                    tv_tijiao.setText("还差" + groupDetailBean.poorNum + "人拼团成功");
                    setTime(groupDetailBean);
                    if (!groupDetailBean.poorNum.equals("0")) {
                        initTipsPopuptWindow(groupDetailBean.poorNum);
                    }
                    rl_allpeoplebottom.setVisibility(View.VISIBLE);
                    tv_btm_pmsg.setText("已有" + groupDetailBean.joinNum + "人参团，还差" + groupDetailBean.poorNum + "人，快加入我们吧！");
                    break;
                case 4:
                    String s4 = "还差<font color='#FF464E'>" + groupDetailBean.poorNum + "</font>人，赶紧分享召集小伙伴组团啦~";
                    tv_group_tips.setText(Html.fromHtml(s4));
                    tv_tijiao.setText("还差" + groupDetailBean.poorNum + "人拼团成功");
                    setTime(groupDetailBean);
                    if (!groupDetailBean.poorNum.equals("0")) {
                        initTipsPopuptWindow(groupDetailBean.poorNum);
                    }
                    rl_allpeoplebottom.setVisibility(View.VISIBLE);
                    tv_btm_pmsg.setText("已有" + groupDetailBean.joinNum + "人参团，还差" + groupDetailBean.poorNum + "人，快加入我们吧！");
                    break;
                case 5:
                    iv_tag.setImageResource(R.mipmap.agd_ptcg);
                    tv_group_tips.setVisibility(View.GONE);
                    tv_tijiao.setText("我要开团");
                    if (source.equals("5")) {
                        tv_zjxq.setVisibility(View.VISIBLE);
                    }
                    break;
                case 6:
                    String s6 = "您终于来了，还差<font color='#FF464E'>" + groupDetailBean.poorNum + "</font>人，来参团吧~";
                    tv_group_tips.setText(Html.fromHtml(s6));
                    tv_tijiao.setText("我要参团");
                    setTime(groupDetailBean);
//                if (!groupDetailBean.poorNum.equals("0")) {
//                    initTipsPopuptWindow(groupDetailBean.poorNum);
//                }
                    rl_allpeoplebottom.setVisibility(View.VISIBLE);
                    tv_btm_pmsg.setText("已有" + groupDetailBean.joinNum + "人参团，还差" + groupDetailBean.poorNum + "人，快加入我们吧！");
                    break;
                case 7:
                    iv_tag.setImageResource(R.mipmap.agd_ptsb);
                    tv_group_tips.setText(R.string.txq_ptsb);
                    tv_tijiao.setText("我要开团");
                    rl_allpeoplebottom.setVisibility(View.VISIBLE);
                    //wenhao.setVisibility(View.INVISIBLE);
                    tv_btm_pmsg.setText("组团时间到，未召集到相应人数的小伙伴！");
                    if (source.equals("5")) {
                        tv_zjxq.setVisibility(View.VISIBLE);
                    }
                    break;
                case 8:
                    //iv_tag.setImageResource(R.mipmap.agd_ykj);
                    tv_group_tips.setVisibility(View.GONE);
                    tv_tijiao.setText("我要开团");
                    break;
                case 9:
                    iv_tag.setImageResource(R.mipmap.agd_ykj);
                    tv_group_tips.setVisibility(View.GONE);
                    tv_tijiao.setText("我要开团");
                    if (source.equals("7")) {
                        tv_zjxq.setVisibility(View.VISIBLE);
                    }
                    break;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        presenter.guessYourLike(groupDetailBean.activityId);

    }


    @Override
    public void setGroupUserList(List<GroupDetailBean.GroupUser> groupUserList) {
        kaiTuanTipsAdapter = new KaiTuanTipsAdapter(this, groupUserList);
        listViewForScrollView.setAdapter(kaiTuanTipsAdapter);
    }


    @Override
    public void setGroupUserGridList(List<GroupDetailBean.GroupUser> groupUserList) {
        // groupDetailPeopleAdapter = new GroupDetailPeopleAdapter(this, groupUserList);
        //gv_grouppeople.setAdapter(groupDetailPeopleAdapter);
        LayoutInflater layoutInflater = LayoutInflater.from(GroupDetailActivity.this);
        int num = Integer.parseInt(presenter.groupDetailBean.groupNum);
        boolean outtag = false;
        if (num > 10) {
            num = 10;
            outtag = true;
        }
        for (int j = 0; j < num;
             j++) {
            convertView = new View[num];
            convertView[j] = (View) layoutInflater.inflate(R.layout.activity_group_detail_touxiang_item, null);
            iv_headlogo = (ImageView) convertView[j].findViewById(R.id.iv_headlogo);
            tv_headtag = (TextView) convertView[j].findViewById(R.id.tv_headtag);
            if (j < groupUserList.size()) {
                Glide.with(this).load(groupUserList.get(j).userImage).placeholder(R.mipmap.default_touxiang).error(R.mipmap.default_touxiang).transform(new CircleTransform(this)).into(iv_headlogo);
                if (groupUserList.get(j).isHead.equals("1")) {
                    tv_headtag.setVisibility(View.VISIBLE);
                }
            }
            if (outtag && j == 9) {
                Glide.with(this).load(R.mipmap.agd_diandian).into(iv_headlogo);
            }

            ll_grouppeople.addView(convertView[j]);
        }


    }


    GridViewForScrollView gv_cainixihuan;
    GroupDetailGuessYourLikeAdapter guessYourLikeAdapter;

    @Override
    public void setguessYourLike(List<GuessYourLikeBean> list) {
        iv_gylike.setVisibility(View.VISIBLE);
        guessYourLikeAdapter = new GroupDetailGuessYourLikeAdapter(this, list, this);
        gv_cainixihuan.setAdapter(guessYourLikeAdapter);
    }


    @Override
    public void setTime(GroupDetailBean groupDetailBean) {
        rl_time.setVisibility(View.VISIBLE);
        try {
            Date parse1 = dateFormat.parse(groupDetailBean.endTime);
            Date parse = dateFormat.parse(groupDetailBean.nowTime);
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
            thour = BaseUtil.Bl(hour);
            tmin = BaseUtil.Bl(min);
            tss = BaseUtil.Bl(s);
            tv_hh.setText(thour);
            tv_mm.setText(tmin);
            tv_ss.setText(tss);
        } catch (Exception e) {
            e.printStackTrace();
        }
        thandler.postDelayed(runnable, 1000);

    }

    @Override
    public void orderConfirm() {
        String skuid = skuPresenter.forValidSKuId();
        Intent sintent = new Intent(GroupDetailActivity.this, ConfirmOrderActivity.class);
        sintent.putExtra("activityId", presenter.groupDetailBean.activityId);
        sintent.putExtra("num", skuPresenter.num + "");
        sintent.putExtra("pid", presenter.groupDetailBean.productId);
        sintent.putExtra("source", source);
        sintent.putExtra("attendId", recordId);
        sintent.putExtra("skuLinkId", skuid);
        initializeSkuPopwindow();
        startActivity(sintent);
        skupopupWindow.dismiss();
        finish();
    }

    Handler thandler = new Handler();
    Runnable runnable = new Runnable() {
        @Override
        public void run() {
            try {
                long hour = Long.parseLong(thour);
                long min = Long.parseLong(tmin);
                long s = Long.parseLong(tss);
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
                    thour = BaseUtil.Bl(hour);
                    tmin = BaseUtil.Bl(min);
                    tss = BaseUtil.Bl(s);
                    tv_hh.setText(thour);
                    tv_mm.setText(tmin);
                    tv_ss.setText(tss);
                } else {
                    finish();
                    LogUtil.Log("++", "run: ++++++++++++++++++++++++++++++++++++");
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            thandler.postDelayed(this, 1000);

        }
    };

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_more:
                startActivity(new Intent(GroupDetailActivity.this, TabActivity.class));
                finish();
                break;
            case R.id.tv_tijiao:
                switch (constastus) {
                    case 1:
                        break;
                    case 2:
                        Intent intent = new Intent(GroupDetailActivity.this, GoodsDetailActivity.class);
                        intent.putExtra("pid", presenter.groupDetailBean.productId);
                        intent.putExtra("activityId", presenter.groupDetailBean.activityId);
                        startActivity(intent);
                        break;
                    case 3:
                        initTipsPopuptWindow(presenter.groupDetailBean.poorNum);
                        break;
                    case 4:
                        initTipsPopuptWindow(presenter.groupDetailBean.poorNum);
                        break;
                    case 5:
                        Intent intent5 = new Intent(GroupDetailActivity.this, GoodsDetailActivity.class);
                        intent5.putExtra("pid", presenter.groupDetailBean.productId);
                        intent5.putExtra("activityId", presenter.groupDetailBean.activityId);
                        startActivity(intent5);
                        break;
                    case 6:
//                        if(source.equals("6")||source.equals("7")){
//                            //掌上秒杀和免费抽只能开一次或参一次
//                            if(presenter.groupDetailBean.isGroup.equals("1")||presenter.groupDetailBean.isOpen.equals("1")){
//                                toastMessage("您已参加过该活动");
//                                return;
//                            }
//                        }
//                        if(source.equals("5")){
//                            //0.1只能开和参一次
//                            if(presenter.groupDetailBean.isGroup.equals("1")&&presenter.groupDetailBean.isOpen.equals("1")){
//                                toastMessage("您已参加过该活动");
//                                return;
//                            }
//                        }
                        initializeSkuPopwindow();
                        skuPresenter.loadSku(presenter.groupDetailBean.productId);
                        break;
                    case 7:
                        Intent intent7 = new Intent(GroupDetailActivity.this, GoodsDetailActivity.class);
                        intent7.putExtra("pid", presenter.groupDetailBean.productId);
                        intent7.putExtra("activityId", presenter.groupDetailBean.activityId);
                        startActivity(intent7);
                        break;
                    case 8:
                        Intent intent8 = new Intent(GroupDetailActivity.this, GoodsDetailActivity.class);
                        intent8.putExtra("pid", presenter.groupDetailBean.productId);
                        intent8.putExtra("activityId", presenter.groupDetailBean.activityId);
                        startActivity(intent8);
                        break;
                    case 9:
                        Intent intent9 = new Intent(GroupDetailActivity.this, GoodsDetailActivity.class);
                        intent9.putExtra("pid", presenter.groupDetailBean.productId);
                        intent9.putExtra("activityId", presenter.groupDetailBean.activityId);
                        startActivity(intent9);
                        break;
                }
                break;
            case R.id.tv_showall:
                updateUi.sendEmptyMessage(0);
                break;
            case R.id.tv_zjxq:
                Intent pintent = new Intent(GroupDetailActivity.this, PrizeDetailMoreActivity.class);
                pintent.putExtra("activityId", presenter.groupDetailBean.activityId);
                pintent.putExtra("activityType", presenter.groupDetailBean.activityType);
                startActivity(pintent);
                break;
            case R.id.rl_detail:
                Intent intent = new Intent(GroupDetailActivity.this, GoodsDetailActivity.class);
                intent.putExtra("pid", presenter.groupDetailBean.productId);
                intent.putExtra("activityId", presenter.groupDetailBean.activityId);
                startActivity(intent);
                break;
            case R.id.iv_fenxiang:
                backgroundAlpha(GroupDetailActivity.this, 0.5f);
                getsharePopupWindow();
                // 这里是位置显示方式,在屏幕的左侧
                sharePopupWindow.showAtLocation(v, Gravity.BOTTOM, 0, 0);
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
                presenter.confirmOrder(source, presenter.groupDetailBean.activityId, recordId, skuPresenter.num, presenter.groupDetailBean.productId, skuid);
//                Intent sintent = new Intent(GroupDetailActivity.this, ConfirmOrderActivity.class);
//                sintent.putExtra("activityId", presenter.groupDetailBean.activityId);
//                sintent.putExtra("num", num + "");
//                sintent.putExtra("pid", presenter.groupDetailBean.productId);
//                sintent.putExtra("source", source);
//                sintent.putExtra("attendId", recordId);
//                sintent.putExtra("skuLinkId", skuid);
//                initializeskuPopwindow();
//                startActivity(sintent);
//                skupopupWindow.dismiss();
//                finish();
                break;

            case R.id.iv_rule:
                initrulePopuptWindow();
                break;
            case R.id.tv_yimaoqian:
                init01rulePopuptWindow();
                break;
            case R.id.iv_hongbao:
                startActivity(new Intent(GroupDetailActivity.this, EventActivity.class));
                break;
            case R.id.iv_top:
                scrollView.smoothScrollTo(0, 0);
                break;


        }
    }

    Handler updateUi = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (isshow) {
                ll_allpeople.setVisibility(View.GONE);
                isshow = false;
                tv_showall.setText("点击查看全部团详情▼");
            } else {
                ll_allpeople.setVisibility(View.VISIBLE);
                isshow = true;
                tv_showall.setText("点击隐藏全部团详情▲");
            }
        }
    };

    @Override
    protected void onDestroy() {
        updateUi.removeCallbacksAndMessages(null);
        thandler.removeCallbacksAndMessages(null);
        super.onDestroy();
    }


//share

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

    public void onResume() {
        super.onResume();
        MobclickAgent.onResume(this);
    }

    public void onPause() {
        super.onPause();
        MobclickAgent.onPause(this);
    }

    /***
     * 获取PopupWindow实例
     */
    private void getsharePopupWindow() {
        if (null != sharePopupWindow) {
            sharePopupWindow.dismiss();
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
        sharePopupWindow = new PopupWindow(popupWindow_view,
                PercentRelativeLayout.LayoutParams.MATCH_PARENT, PercentRelativeLayout.LayoutParams.WRAP_CONTENT, true);
        // 设置动画效果
        sharePopupWindow.setAnimationStyle(R.style.AnimBottom);
        // 点击其他地方消失
        // 实例化一个ColorDrawable颜色为半透明
        ColorDrawable dw = new ColorDrawable(0x00000000);
        // 设置SelectPicPopupWindow弹出窗体的背景
        sharePopupWindow.setBackgroundDrawable(dw);
        backgroundAlpha(GroupDetailActivity.this, 0.5f);// 0.0-1.0
        sharePopupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {

            @Override
            public void onDismiss() {
                // TODO Auto-generated method stub
                backgroundAlpha(GroupDetailActivity.this, 1f);
            }
        });
        ImageView iv_wb = (ImageView) popupWindow_view.findViewById(R.id.iv_wb);
        iv_wb.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                // System.out.println("++++++++++++++++");
                UMImage urlImage = new UMImage(GroupDetailActivity.this,
                        presenter.share.getImage());
                new ShareAction(GroupDetailActivity.this)
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
                UMImage urlImage = new UMImage(GroupDetailActivity.this,
                        presenter.share.getImage());
                new ShareAction(GroupDetailActivity.this)
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
                UMImage urlImage = new UMImage(GroupDetailActivity.this,
                        presenter.share.getImage());
                new ShareAction(GroupDetailActivity.this)
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
                if (sharePopupWindow != null && sharePopupWindow.isShowing()) {
                    sharePopupWindow.dismiss();
                    sharePopupWindow = null;
                }
                return false;
            }
        });
    }

    private UMShareListener umShareListener = new UMShareListener() {
        @Override
        public void onResult(SHARE_MEDIA platform) {
            Log.d("plat", "platform" + platform);

            Toast.makeText(GroupDetailActivity.this, platform + " 分享成功啦", Toast.LENGTH_SHORT).show();

        }

        @Override
        public void onError(SHARE_MEDIA platform, Throwable t) {
            Toast.makeText(GroupDetailActivity.this, platform + " 分享失败啦", Toast.LENGTH_SHORT).show();
            if (t != null) {
                Log.d("throw", "throw:" + t.getMessage());
            }
        }

        @Override
        public void onCancel(SHARE_MEDIA platform) {
            Toast.makeText(GroupDetailActivity.this, platform + " 分享取消了", Toast.LENGTH_SHORT).show();
        }
    };

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        UMShareAPI.get(this).onActivityResult(requestCode, resultCode, data);
    }
//share

    //tips
    protected void initTipsPopuptWindow(String poorNum) {
        // TODO Auto-generated method stub
        // 获取自定义布局文件activity_popupwindow_left.xml的视图
        View popupWindow_view = getLayoutInflater().inflate(
                R.layout.activity_ping_tuan_jie_guo_pop, null, false);

        // 创建PopupWindow实例,200,LayoutParams.MATCH_PARENT分别是宽度和高度
        tipspopupWindow = new PopupWindow(popupWindow_view,
                PercentRelativeLayout.LayoutParams.MATCH_PARENT, PercentRelativeLayout.LayoutParams.MATCH_PARENT, true);
        // 设置动画效果
        //popupWindow.setAnimationStyle(R.style.AnimBottom);
        // 点击其他地方消失
        // 实例化一个ColorDrawable颜色为半透明
        String s = "还差<font color='#fec64b'>" + poorNum + "</font>人，赶快邀请好友参团吧";
        tv_message = (TextView) popupWindow_view.findViewById(R.id.tv_message);
        tv_message.setText(Html.fromHtml(s));
        ColorDrawable dw = new ColorDrawable(0x00000000);
        // 设置SelectPicPopupWindow弹出窗体的背景
        tipspopupWindow.setBackgroundDrawable(dw);
        backgroundAlpha(GroupDetailActivity.this, 0.5f);// 0.0-1.0
        tipspopupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {

            @Override
            public void onDismiss() {
                // TODO Auto-generated method stub
                backgroundAlpha(GroupDetailActivity.this, 1f);
            }
        });


        // popupWindow.setTouchable(true);
        popupWindow_view.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                // TODO Auto-generated method stub
                if (tipspopupWindow != null && tipspopupWindow.isShowing()) {
                    tipspopupWindow.dismiss();
                    tipspopupWindow = null;
                }
                return false;
            }
        });
        tipspopupWindow.showAtLocation(rl_main, Gravity.BOTTOM, 0, 0);
    }


    //rule
    protected void initrulePopuptWindow() {
        // TODO Auto-generated method stub
        // 获取自定义布局文件activity_popupwindow_left.xml的视图
        View popupWindow_view = getLayoutInflater().inflate(
                R.layout.activity_group_detail_rule_pop, null, false);

        // 创建PopupWindow实例,200,LayoutParams.MATCH_PARENT分别是宽度和高度
        rulepopupWindow = new PopupWindow(popupWindow_view,
                PercentRelativeLayout.LayoutParams.MATCH_PARENT, PercentRelativeLayout.LayoutParams.MATCH_PARENT, true);
        // 设置动画效果
        rulepopupWindow.setAnimationStyle(R.style.AnimBottom);
        // 点击其他地方消失
        // 实例化一个ColorDrawable颜色为半透明
        //ColorDrawable dw = new ColorDrawable(0x00000000);
        // 设置SelectPicPopupWindow弹出窗体的背景
        //rulepopupWindow.setBackgroundDrawable(dw);
        backgroundAlpha(GroupDetailActivity.this, 0.5f);// 0.0-1.0
        rulepopupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {

            @Override
            public void onDismiss() {
                // TODO Auto-generated method stub
                backgroundAlpha(GroupDetailActivity.this, 1f);
            }
        });


        // popupWindow.setTouchable(true);
        popupWindow_view.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                // TODO Auto-generated method stub
                if (rulepopupWindow != null && rulepopupWindow.isShowing()) {
                    rulepopupWindow.dismiss();
                    rulepopupWindow = null;
                }
                return false;
            }
        });
        rulepopupWindow.showAtLocation(rl_main, Gravity.BOTTOM, 0, 0);
    }

    //0.1rule
    protected void init01rulePopuptWindow() {
        // TODO Auto-generated method stub
        // 获取自定义布局文件activity_popupwindow_left.xml的视图
        View popupWindow_view = getLayoutInflater().inflate(
                R.layout.activity_group_detail_01rule_pop, null, false);

        // 创建PopupWindow实例,200,LayoutParams.MATCH_PARENT分别是宽度和高度
        rule01popupWindow = new PopupWindow(popupWindow_view,
                PercentRelativeLayout.LayoutParams.MATCH_PARENT, PercentRelativeLayout.LayoutParams.MATCH_PARENT, true);
        rule01popupWindow.setFocusable(true);
        // 设置动画效果
        rule01popupWindow.setAnimationStyle(R.style.AnimBottom);
        // 点击其他地方消失
        // 实例化一个ColorDrawable颜色为半透明
        ColorDrawable dw = new ColorDrawable(0x00000000);
        // 设置SelectPicPopupWindow弹出窗体的背景
        rule01popupWindow.setBackgroundDrawable(dw);
        backgroundAlpha(GroupDetailActivity.this, 0.5f);// 0.0-1.0
        View v_btn = (View) popupWindow_view.findViewById(R.id.v_btn);
        v_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rule01popupWindow.dismiss();
                Intent vintent = new Intent(GroupDetailActivity.this, GoodsDetailActivity.class);
                vintent.putExtra("pid", presenter.groupDetailBean.productId);
                vintent.putExtra("activityId", presenter.groupDetailBean.activityId);
                startActivity(vintent);

            }
        });
        rule01popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {

            @Override
            public void onDismiss() {
                // TODO Auto-generated method stub
                backgroundAlpha(GroupDetailActivity.this, 1f);
            }
        });


        // popupWindow.setTouchable(true);
        popupWindow_view.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                // TODO Auto-generated method stub
                if (rule01popupWindow != null && rule01popupWindow.isShowing()) {
                    rule01popupWindow.dismiss();
                    rule01popupWindow = null;
                }
                return false;
            }
        });


        rule01popupWindow.showAtLocation(rl_main, Gravity.BOTTOM, 0, 0);
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
        skuPresenter.setBtn_offirm(btn_offirm, iv_iamge, GroupDetailActivity.this);
        skupopupWindow.showAtLocation(rl_main, Gravity.BOTTOM, 0, 0);


    }

    @Override
    public void setSkuPopPrice() {
        if (source.equals("4")) {
            tv_price.setText("￥" + presenter.groupDetailBean.alonePrice);
        } else {
            tv_price.setText("￥" + presenter.groupDetailBean.groupPrice);
        }
    }

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
            //团免,0.1秒杀,限时秒杀
            add.setOnClickListener(null);
            reduce.setOnClickListener(null);
        } else {
            add.setOnClickListener(this);
            reduce.setOnClickListener(this);
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
                    if(skuPresenter.SKU_MODEL == skuPresenter.SKU_MODEL_TWO){
                        formatAdapter.notifyDataSetChanged();
                    }
                    skuPresenter.setBtn_offirm(btn_offirm, iv_iamge, GroupDetailActivity.this);
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
                            skuPresenter.setBtn_offirm(btn_offirm, iv_iamge, GroupDetailActivity.this);
                        }
                    }

                });
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
            if(skuPresenter.SKU_MODEL == skuPresenter.SKU_MODEL_TWO){
                formatAdapter.notifyDataSetChanged();
            }
            skupopupWindow.update();
        } catch (Exception e) {
            e.printStackTrace();
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
                if(skuPresenter.SKU_MODEL == skuPresenter.SKU_MODEL_TWO){
                    formatAdapter = new SkuFormatAdapter(this, skuPresenter.skuFormatValue, skuPresenter);
                    gv_format.setAdapter(formatAdapter);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }


    }
}
