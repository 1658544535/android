package com.qunyu.taoduoduo.fragment;

import android.app.Activity;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
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
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.qunyu.taoduoduo.R;
import com.qunyu.taoduoduo.activity.CaiGoodsDetailActivity;
import com.qunyu.taoduoduo.activity.CaiJiaGeActivity;
import com.qunyu.taoduoduo.adapter.GuessActivityApiWanQiAdapter;
import com.qunyu.taoduoduo.base.BaseModel;
import com.qunyu.taoduoduo.base.BaseUtil;
import com.qunyu.taoduoduo.bean.GetShareContentBean;
import com.qunyu.taoduoduo.bean.GuessActivityBean;
import com.qunyu.taoduoduo.global.Constant;
import com.qunyu.taoduoduo.global.MyApplication;
import com.qunyu.taoduoduo.pulltorefresh.PullToRefreshLayout;
import com.qunyu.taoduoduo.pulltorefresh.PullableListView;
import com.qunyu.taoduoduo.utils.LogUtil;
import com.qunyu.taoduoduo.utils.ToastUtils;
import com.umeng.socialize.ShareAction;
import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.media.UMImage;
import com.zhy.android.percent.support.PercentRelativeLayout;

import java.lang.reflect.Type;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CaiJiaGeWanQiFragment extends Fragment {
    String TAG = "CaiJiaGeFragment";
    MyApplication application;
    View view = null;
    @BindView(R.id.iv_head_left)
    ImageView ivHeadLeft;
    @BindView(R.id.tv_head_title)
    TextView tvHeadTitle;
    @BindView(R.id.lv_t)
    PullableListView lvT;
    @BindView(R.id.refresh_view)
    PullToRefreshLayout refreshView;
    int pageNo = 1;
    ImageView iv_fenxiang;
    @BindView(R.id.zw)
    TextView zw;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_cai_jia_ge, container, false);
        application = (MyApplication) this.getActivity().getApplication();
        GuessBannerGet();
        ButterKnife.bind(this, view);
        //
//        handler.postDelayed(runnable, 1000);
        ivHeadLeft.setVisibility(View.GONE);
        tvHeadTitle.setText("猜价格赢好礼");
        refreshView.setOnPullListener(new PullToRefreshLayout.OnPullListener() {
            @Override
            public void onRefresh(PullToRefreshLayout pullToRefreshLayout) {
                new Handler() {
                    @Override
                    public void handleMessage(Message msg) {
                        // 千万别忘了告诉控件刷新完毕了哦！
                        pageNo = 1;
                        GuessActivityGet();

                    }
                }.sendEmptyMessageDelayed(0, 800);
            }

            @Override
            public void onLoadMore(PullToRefreshLayout pullToRefreshLayout) {
                new Handler() {
                    @Override
                    public void handleMessage(Message msg) {
                        // 千万别忘了告诉控件加载完毕了哦！
                        pageNo++;
                        GuessActivityGet();
                    }
                }.sendEmptyMessageDelayed(0, 800);
            }
        });
        refreshView.setPullDownEnable(false);
        lvT.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
                                    long arg3) {
                // TODO Auto-generated method stub
                Bundle bundle = new Bundle();
                bundle.putString("activityId", date.get(arg2).getActivityId() + "");
                bundle.putString("productId", date.get(arg2).getProductId() + "");
                bundle.putString("tag", "tag");
                BaseUtil.ToAcb(getActivity(), CaiGoodsDetailActivity.class, bundle);
            }

        });
        GetShareContentApiGet();
        iv_fenxiang = (ImageView) view.findViewById(R.id.iv_fenxiang);
        iv_fenxiang.setVisibility(View.VISIBLE);
        iv_fenxiang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                backgroundAlpha(getActivity(), 0.5f);
//                getPopupWindow();
//                // 这里是位置显示方式,在屏幕的左侧
//                popupWindow1.showAtLocation(view, Gravity.BOTTOM, 0, 0);
                BaseUtil.ToAc(getActivity(), CaiJiaGeActivity.class);
            }
        });
        return view;
    }

    GetShareContentBean share;

    private void GetShareContentApiGet() {
        AbRequestParams params = new AbRequestParams();
        params.put("id", "10");
        params.put("type", "10");
        LogUtil.Log(Constant.getShareContentApi + "?" + params.getParamString());
        AbHttpUtil.getInstance(getActivity()).get(Constant.getShareContentApi, params, new AbStringHttpResponseListener() {

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
                ToastUtils.showShortToast(getActivity(), "网络异常，数据加载失败");
                LogUtil.Log(error.getMessage());
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
                    ToastUtils.showShortToast(getActivity(), "网络异常，数据加载失败");
                    LogUtil.Log(result.getResultMessage());
                }

            }
        });

    }

    //    Handler handler = new Handler();
//    Runnable runnable = new Runnable() {
//        @Override
//        public void run() {
////            date = GrtJs(date);
//            if (date != null) {
//                for (int i = 0; i < date.size(); i++) {
//                    try {
//                        long hour = Long.parseLong(date.get(i).getHour());
//                        long min = Long.parseLong(date.get(i).getMin());
//                        long s = Long.parseLong(date.get(i).getSs());
//                        if (hour != 0 || min != 0 || s != 0) {
//                            s--;
//                            if (s < 0) {
//                                min--;
//                                s = 59;
//                                if (min < 0) {
//                                    min = 59;
//                                    hour--;
//                                    if (hour < 0) {
//                                        // 倒计时结束
//                                        hour = 0;
//                                    }
//                                }
//                            }
//                            date.get(i).setHour(BaseUtil.Bl(hour));
//                            date.get(i).setMin(BaseUtil.Bl(min));
//                            date.get(i).setSs(BaseUtil.Bl(s));
//                        } else {
//                            date.remove(i);
//                        }
//
//                    } catch (Exception e) {
//                    }
//                }
//            }
//            try {
//                adapter.notifyDataSetChanged();
//            } catch (Exception e) {
//            }
//            handler.postDelayed(this, 1000);
//
//        }
//    };
//
    private ArrayList<GuessActivityBean> GrtJs(ArrayList<GuessActivityBean> date) {
        if (date != null) {
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            for (int i = 0; i < date.size(); i++) {
                try {
                    Date parse1 = dateFormat.parse(date.get(i).getEndTime());
                    Date parse = dateFormat.parse(date.get(i).getNowTime());
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
                    date.get(i).setHour(BaseUtil.Bl(hour));
                    date.get(i).setMin(BaseUtil.Bl(min));
                    date.get(i).setSs(BaseUtil.Bl(s));

                } catch (Exception e) {
                }
            }
            return date;

        }
        return date;
    }

    private void GuessBannerGet() {
//        AbHttpUtil.getInstance(getActivity()).get(Constant.guessBannerApi, null, new AbStringHttpResponseListener() {
//
//            @Override
//            public void onStart() {
////                AbDialogUtil.showProgressDialog(LoginActivity.this, 0, "正在登录...");
//            }
//
//            @Override
//            public void onFinish() {
////                AbDialogUtil.removeDialog(LoginActivity.this);
//            }
//
//            @Override
//            public void onFailure(int statusCode, String content, Throwable error) {
//                ToastUtils.showShortToast(getActivity(), "网络异常，数据加载失败");
//            }
//
//            @Override
//            public void onSuccess(int statusCode, String content) {
//                AbResult result = new AbResult(content);
//                if (result.getResultCode() == AbResult.RESULT_OK) {
//                    Gson gson = new Gson();
//                    Type type = new TypeToken<BaseModel<GuessBannerBean>>() {
//                    }.getType();
//                    BaseModel<GuessBannerBean> base = gson.fromJson(content, type);
//                    View vHead = View.inflate(getActivity(), R.layout.fragment_cai_jia_ge_list_head, null);
//                    ImageView imageView1 = (ImageView) vHead.findViewById(R.id.banners);
//                    imageView1.setOnClickListener(new View.OnClickListener() {
//                        @Override
//                        public void onClick(View view) {
//
//                        }
//                    });
//                    Glide.with(getActivity()).load(base.result.getBanner()).into(imageView1);
//                    lvT.addHeaderView(vHead);
////                    GuessActivityGet();
//                } else {
//                    ToastUtils.showShortToast(getActivity(), result.getResultMessage());
//                }
//
//            }
//        });

    }

    GuessActivityApiWanQiAdapter adapter;
    ArrayList<GuessActivityBean> date;

    private void GuessActivityGet() {
        AbRequestParams params = new AbRequestParams();
        params.put("pageNo", pageNo);
        AbHttpUtil.getInstance(getActivity()).get(Constant.guessBeforeActivityApi, params, new AbStringHttpResponseListener() {

            @Override
            public void onStart() {
//                AbDialogUtil.showProgressDialog(LoginActivity.this, 0, "正在登录...");
            }

            @Override
            public void onFinish() {
//                AbDialogUtil.removeDialog(LoginActivity.this);
                try {
                    refreshView.refreshFinish(PullToRefreshLayout.SUCCEED);
                    refreshView.loadmoreFinish(PullToRefreshLayout.SUCCEED);
                } catch (Exception e) {
                }
            }

            @Override
            public void onFailure(int statusCode, String content, Throwable error) {
                ToastUtils.showShortToast(getActivity(), "网络异常，数据加载失败");
                LogUtil.Log(error.getMessage());
            }

            @Override
            public void onSuccess(int statusCode, String content) {
                AbResult result = new AbResult(content);
                if (result.getResultCode() == AbResult.RESULT_OK) {
                    Gson gson = new Gson();
                    Type type = new TypeToken<BaseModel<ArrayList<GuessActivityBean>>>() {
                    }.getType();
                    BaseModel<ArrayList<GuessActivityBean>> base = gson.fromJson(content, type);
                    Log.d(TAG, "onSuccess: " + content);
                    if (base.success) {
                        if (pageNo == 1) {
                            date = GrtJs(base.result);
                            adapter = new GuessActivityApiWanQiAdapter(getActivity(), date);
                            lvT.setAdapter(adapter);
                            zw.setVisibility(View.GONE);
                        } else {
                            date.addAll(GrtJs(base.result));
                            adapter.notifyDataSetChanged();
                        }
                    } else {
                        ToastUtils.showShortToast(getActivity(), "已显示全部");
                    }
                } else {

                    ToastUtils.showShortToast(getActivity(), "网络异常，数据加载失败");
                    LogUtil.Log(result.getResultMessage());
                }

            }
        });

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
        View popupWindow_view = getLayoutInflater(getArguments()).inflate(
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
        backgroundAlpha(getActivity(), 0.5f);// 0.0-1.0
        popupWindow1.setOnDismissListener(new PopupWindow.OnDismissListener() {

            @Override
            public void onDismiss() {
                // TODO Auto-generated method stub
                backgroundAlpha(getActivity(), 1f);
            }
        });
        ImageView iv_wb = (ImageView) popupWindow_view.findViewById(R.id.iv_wb);
        iv_wb.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                // System.out.println("++++++++++++++++");
                UMImage urlImage = new UMImage(getActivity(),
                        share.getImage());
                new ShareAction(getActivity())
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
                UMImage urlImage = new UMImage(getActivity(),
                        share.getImage());
                new ShareAction(getActivity())
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
                UMImage urlImage = new UMImage(getActivity(),
                        share.getImage());
                new ShareAction(getActivity())
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
            com.umeng.socialize.utils.Log.d("plat", "platform" + platform);

            Toast.makeText(getActivity(), platform + " 分享成功啦", Toast.LENGTH_SHORT).show();

        }

        @Override
        public void onError(SHARE_MEDIA platform, Throwable t) {
            Toast.makeText(getActivity(), platform + " 分享失败啦", Toast.LENGTH_SHORT).show();
            if (t != null) {
                com.umeng.socialize.utils.Log.d("throw", "throw:" + t.getMessage());
            }
        }

        @Override
        public void onCancel(SHARE_MEDIA platform) {
            Toast.makeText(getActivity(), platform + " 分享取消了", Toast.LENGTH_SHORT).show();
        }
    };

    @Override
    public void onResume() {
        super.onResume();
        pageNo = 1;
        GuessActivityGet();
    }


}
