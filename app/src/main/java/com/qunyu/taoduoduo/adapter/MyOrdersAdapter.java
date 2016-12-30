package com.qunyu.taoduoduo.adapter;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.text.Html;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.andbase.library.http.AbHttpUtil;
import com.andbase.library.http.listener.AbStringHttpResponseListener;
import com.andbase.library.http.model.AbRequestParams;
import com.andbase.library.http.model.AbResult;
import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.qunyu.taoduoduo.R;
import com.qunyu.taoduoduo.activity.ApplyRefundActivity;
import com.qunyu.taoduoduo.activity.GroupDetailActivity;
import com.qunyu.taoduoduo.activity.LogisticsActivity;
import com.qunyu.taoduoduo.activity.OrderDetailActivity;
import com.qunyu.taoduoduo.activity.PrizeDetailActivity;
import com.qunyu.taoduoduo.activity.PrizeDetailMoreActivity;
import com.qunyu.taoduoduo.base.BaseModel;
import com.qunyu.taoduoduo.base.BaseUtil;
import com.qunyu.taoduoduo.bean.MyOrderBean;
import com.qunyu.taoduoduo.global.AnyEventType;
import com.qunyu.taoduoduo.global.Constant;
import com.qunyu.taoduoduo.global.Untool;
import com.qunyu.taoduoduo.utils.LogUtil;
import com.qunyu.taoduoduo.utils.ToastUtils;
import com.zhy.android.percent.support.PercentLinearLayout;
import com.zhy.android.percent.support.PercentRelativeLayout;

import org.greenrobot.eventbus.EventBus;

import java.lang.reflect.Type;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2016/9/27.
 */

public class MyOrdersAdapter extends BaseAdapter {

    private Context mContext;
    //单行的布局
    private int mResource;
    //列表展现的数据
    private ArrayList<MyOrderBean> mData;
    int s = 0;
    private LayoutInflater mInflater;
    String mStatus;

    /**
     * 构造方法
     *
     * @param context
     * @param data    列表展现的数据
     */
    public MyOrdersAdapter(Context context, ArrayList<MyOrderBean> data, String status
    ) {
        mContext = context;
        mData = data;
        mStatus = status;
        try {
            mInflater = (LayoutInflater) context.getSystemService(mContext.LAYOUT_INFLATER_SERVICE);
        } catch (Exception c) {
        }
    }

    @Override
    public int getCount() {
        return mData.size();
    }

    @Override
    public Object getItem(int position) {
        return mData.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getViewTypeCount() {
        return 666;
    }

    @Override
    public int getItemViewType(int position) {
        int i = 0;
        if (mData.get(position).getSource().equals("5") || mData.get(position).getSource().equals("7")) {
            i = 555;
        } else {
            if (mData.get(position).getOrderStatus().equals("1")) {
                //待付款
                if (mData.get(position).getIsCancel().equals("1")) {
                    //取消
                    i = 1;
                } else {
                    //正常
                    i = 2;
                }
            } else if (mData.get(position).getOrderStatus().equals("2")) {
                //已付款
                if (mData.get(position).getIsSuccess().equals("0")) {
                    //拼团中
                    i = 3;
                } else if (mData.get(position).getIsSuccess().equals("1")) {
                    //拼团成功
                    i = 4;
                } else if (mData.get(position).getIsSuccess().equals("2")) {
                    //拼团失败
                    i = 41;
                }
            } else if (mData.get(position).getOrderStatus().equals("3")) {
                //已发货
                i = 5;
            } else {
                //已完成
                i = 6;
            }
        }
        return i;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Log.d("++", "getView: " + s);
        //设置数据
        int type = getItemViewType(position);
        QxViewHolder qxholder = null;
        DfkViewHolder dfkholder = null;
        PingViewHolder pingholder = null;
        DfhViewHolder dfhholder = null;
        DshViewHolder dshholder = null;
        YwcViewHolder ywcholder = null;
        ViewHolder holder = null;
        //使用自定义的list_items作为Layout
        if (convertView == null) {
            switch (type) {
                case 1:
                case 41:
                    convertView = mInflater.inflate(R.layout.activity_orders_yiquxiao, parent, false);
                    //使用减少findView的次数
                    qxholder = new QxViewHolder(convertView);
                    //设置标记
                    convertView.setTag(qxholder);
                    break;
                case 2:
                    convertView = mInflater.inflate(R.layout.activity_orders_daifukuang, parent, false);
                    //使用减少findView的次数
                    dfkholder = new DfkViewHolder(convertView);
                    //设置标记
                    convertView.setTag(dfkholder);
                    break;
                case 3:
                    convertView = mInflater.inflate(R.layout.activity_orders_pingtuaning, parent, false);
                    //使用减少findView的次数
                    pingholder = new PingViewHolder(convertView);
                    //设置标记
                    convertView.setTag(pingholder);
                    break;
                case 4:
                    convertView = mInflater.inflate(R.layout.activity_orders_daifahuo, parent, false);
                    //使用减少findView的次数
                    dfhholder = new DfhViewHolder(convertView);
                    //设置标记
                    convertView.setTag(dfhholder);
                    break;
                case 5:
                    convertView = mInflater.inflate(R.layout.activity_orders_daishouhuo, parent, false);
                    //使用减少findView的次数
                    dshholder = new DshViewHolder(convertView);
                    //设置标记
                    convertView.setTag(dshholder);
                    break;
                case 555:
                    holder = new ViewHolder();
                    convertView = mInflater.inflate(R.layout.my_draw_listitem_copy, null, false);
                    holder.iv_logo = (ImageView) convertView.findViewById(R.id.iv_mydraw_logo);
                    holder.tv_title = (TextView) convertView.findViewById(R.id.tv_mydraw_title);
                    holder.tv_status = (TextView) convertView.findViewById(R.id.tv_mydraw_status);
                    holder.tv_num = (TextView) convertView.findViewById(R.id.tv_mydraw_proNum);
                    holder.tv_price = (TextView) convertView.findViewById(R.id.tv_mydraw_Price);
                    holder.tv_zhongjianMsg = (TextView) convertView.findViewById(R.id.tv_zhongjian_Msg);
                    holder.tv_shaitu = (TextView) convertView.findViewById(R.id.tv_shaitu);
                    holder.tv_shaitu1 = (TextView) convertView.findViewById(R.id.tv_shaitu1);
                    holder.layout_bottom = (PercentLinearLayout) convertView.findViewById(R.id.layout_mydraw_bottom);
                    convertView.setTag(holder);
                    break;
                default:
                    convertView = mInflater.inflate(R.layout.activity_orders_daipingjia, parent, false);
                    //使用减少findView的次数
                    ywcholder = new YwcViewHolder(convertView);
                    //设置标记
                    convertView.setTag(ywcholder);
                    break;
            }
        } else {
            switch (type) {
                case 1:
                case 41:
                    qxholder = (QxViewHolder) convertView.getTag();
                    break;
                case 2:
                    dfkholder = (DfkViewHolder) convertView.getTag();
                    break;
                case 3:
                    pingholder = (PingViewHolder) convertView.getTag();
                    break;
                case 4:
                    dfhholder = (DfhViewHolder) convertView.getTag();
                    break;
                case 5:
                    dshholder = (DshViewHolder) convertView.getTag();
                    break;
                case 555:
                    holder = (ViewHolder) convertView.getTag();
                    break;
                default:
                    ywcholder = (YwcViewHolder) convertView.getTag();
                    break;
            }
        }
        final MyOrderBean dataSet = mData.get(position);
//        Log.d("++", "getView: " + dataSet.getActivityId() + type);
        if (dataSet == null) {
            Log.d("onSuccess: ", "onSuccess: ");
            return null;
        }
        try {
            final int k = position;
            DecimalFormat format = new DecimalFormat("0.00");
            SpannableStringBuilder style1 = new SpannableStringBuilder("实付：￥" + format.format(new BigDecimal(dataSet.getOrderPrice())) + "（免运费）");
            style1.setSpan(new ForegroundColorSpan(mContext.getResources().getColor(R.color.text_01)), 3, 4 + format.format(new BigDecimal(dataSet.getOrderPrice())).length(), Spannable.SPAN_EXCLUSIVE_INCLUSIVE);
            switch (type) {
                case 1:
                    Glide.with(mContext).load(dataSet.getProductImage()).placeholder(R.mipmap.default_load).error(R.mipmap.default_load).into(qxholder.ivLogo);
                    qxholder.tvName.setText(dataSet.getProductName());
                    qxholder.tvJg.setText(style1);
                    qxholder.tv_gd.setText("交易已取消");
                case 41:
                    //获取该行数据
                    //图片的下载
                    Glide.with(mContext).load(dataSet.getProductImage()).placeholder(R.mipmap.default_load).error(R.mipmap.default_load).into(qxholder.ivLogo);
                    qxholder.tvName.setText(dataSet.getProductName());
                    qxholder.tvJg.setText(style1);
                    if (!dataSet.getIsRefund().equals("2")) {
                        qxholder.tv_gd.setText("未成团，退款中");
                    } else {
                        qxholder.tv_gd.setText("未成团，已退款");
                    }
                    break;
                case 2:
                    //获取该行数据
                    //图片的下载
                    Glide.with(mContext).load(dataSet.getProductImage()).placeholder(R.mipmap.default_load).error(R.mipmap.default_load).into(dfkholder.ivLogo);
                    dfkholder.tvName.setText(dataSet.getProductName());
                    dfkholder.tvJg.setText(style1);
//                dfkholder.tvZf.setOnClickListener(new View.OnClickListener() {
//
//                    @Override
//                    public void onClick(View v) {
//                        // TODO Auto-generated method stub
//                        Log.i("匿名内部类", "去支付");
//                    }
//                });
                    dfkholder.tvQx.setOnClickListener(new View.OnClickListener() {

                        @Override
                        public void onClick(View v) {
                            // TODO Auto-generated method stub
                            Log.i("匿名内部类", "取消");
                            new AlertDialog.Builder(mContext).setTitle("提示框").setMessage("确认取消该订单？")
                                    .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int which) {
                                            cancelOrderGet(dataSet.getId(), k);

                                        }
                                    })
                                    .setNegativeButton("取消", null)
                                    .show();
                        }
                    });
                    break;
                case 3:
                    //获取该行数据
                    //图片的下载
                    Glide.with(mContext).load(dataSet.getProductImage()).placeholder(R.mipmap.default_load).error(R.mipmap.default_load).into(pingholder.ivLogo);
                    pingholder.tvName.setText(dataSet.getProductName());
                    pingholder.tvJg.setText(style1);
                    pingholder.tv_zt.setText("拼团中，差" + dataSet.getOweNum() + "人");
                    pingholder.tv_ck.setOnClickListener(new View.OnClickListener() {

                        @Override
                        public void onClick(View v) {
                            // TODO Auto-generated method stub
                            Log.i("匿名内部类", "查看");
                            Bundle bundle6 = new Bundle();
//                        bundle6.putString("source", dataSet.getSource());
//                        bundle6.putString("recordId", dataSet.getAttendId() + "");
                            bundle6.putString("oid", dataSet.getId() + "");
                            bundle6.putString("type", 3 + "");
                            BaseUtil.ToAcb(mContext, OrderDetailActivity.class, bundle6);
                        }
                    });
                    pingholder.tv_yq.setOnClickListener(new View.OnClickListener() {

                        @Override
                        public void onClick(View v) {
                            // TODO Auto-generated method stub
                            Log.i("匿名内部类", "邀请");
                            Bundle bundle6 = new Bundle();
                            bundle6.putString("source", dataSet.getSource());
                            bundle6.putString("recordId", dataSet.getAttendId() + "");
                            BaseUtil.ToAcb(mContext, GroupDetailActivity.class, bundle6);
                        }
                    });
                    break;
                case 4:
                    //获取该行数据
                    //图片的下载
                    Glide.with(mContext).load(dataSet.getProductImage()).placeholder(R.mipmap.default_load).error(R.mipmap.default_load).into(dfhholder.ivLogo);
                    dfhholder.tvName.setText(dataSet.getProductName());
                    dfhholder.tvJg.setText(style1);
                    if (dataSet.getRefundStatus().equals("1")|| dataSet.getRefundStatus().equals("5") || dataSet.getRefundStatus().equals("6")) {
//                    dfhholder.tvTk.setText("申请售后中");
                        dfhholder.tv_shsqz.setVisibility(View.VISIBLE);
                        dfhholder.tvTk.setVisibility(View.GONE);
                        //dfhholder.tvTk.setClickable(false);
                    } else {
                        dfhholder.tvTk.setOnClickListener(new View.OnClickListener() {

                            @Override
                            public void onClick(View v) {
                                // TODO Auto-generated method stub
                                Log.i("匿名内部类", "申请退款");
                                Bundle bundle5 = new Bundle();
                                EventBus.getDefault().post(new AnyEventType(dataSet.getId() + ";xin"));
                                bundle5.putString("price", dataSet.getOrderPrice() + "");
                                bundle5.putString("oid", dataSet.getId() + "");
//                            bundle5.putString("phone", Untool.getPhone() + "");
                                bundle5.putString("orderStatus", dataSet.getOrderStatus());
                                BaseUtil.ToAcb(mContext, ApplyRefundActivity.class, bundle5);
                            }
                        });
                    }
                    break;
                case 5:
                    //获取该行数据
                    //图片的下载
                    Glide.with(mContext).load(dataSet.getProductImage()).placeholder(R.mipmap.default_load).error(R.mipmap.default_load).into(dshholder.ivLogo);
                    dshholder.tvName.setText(dataSet.getProductName());
                    dshholder.tvJg.setText(style1);
                    if (dataSet.getRefundStatus().equals("0") || dataSet.getRefundStatus().equals("5") || dataSet.getRefundStatus().equals("6")) {
                        dshholder.tv_shsqz.setVisibility(View.GONE);
                        dshholder.tv_btn3.setVisibility(View.VISIBLE);
                    } else {
                        dfhholder.tvTk.setText("申请售后中");
                        dshholder.tv_shsqz.setVisibility(View.VISIBLE);
                        dshholder.tv_btn3.setVisibility(View.GONE);
                        //dfhholder.tvTk.setClickable(false);
                    }
                    dshholder.tv_btn1.setOnClickListener(new View.OnClickListener() {

                        @Override
                        public void onClick(View v) {
                            // TODO Auto-generated method stub
                            Log.i("匿名内部类", "延长收货");
                        }
                    });
                    dshholder.tv_btn2.setOnClickListener(new View.OnClickListener() {

                        @Override
                        public void onClick(View v) {
                            // TODO Auto-generated method stub
                            Log.i("匿名内部类", "查看物流");
                            Bundle bundle1 = new Bundle();
                            bundle1.putString("orderId", dataSet.getId());
                            BaseUtil.ToAcb(mContext, LogisticsActivity.class, bundle1);
                        }
                    });
                    dshholder.tv_btn3.setOnClickListener(new View.OnClickListener() {

                        @Override
                        public void onClick(View v) {
                            // TODO Auto-generated method stub
                            Log.i("匿名内部类", "确定收货");
                            new AlertDialog.Builder(mContext).setTitle("提示框").setMessage("是否确定收货？")
                                    .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int which) {
                                            editOrderStatusGet(dataSet.getId(), k);
                                        }
                                    })
                                    .setNegativeButton("取消", null)
                                    .show();
                        }
                    });
                    break;
                case 555:
                    Glide.with(mContext).load(dataSet.getProductImage()).placeholder(R.mipmap.default_load)
                            .error(R.mipmap.default_load).into(holder.iv_logo);
                    holder.tv_title.setText(dataSet.getProductName());
                    holder.tv_price.setText(Html.fromHtml("实付:<font color=\"#FF464E\">￥" + dataSet.getOrderPrice() + "</font>(免运费)"));
//                if (dataSet.getIsPrize() != null && dataSet.getIsPrize().equals("0") && !dataSet.getOrderStatus().equals("1")) {
//                    holder.layout_bottom.setVisibility(View.GONE);
//                } else {
                    holder.layout_bottom.setVisibility(View.VISIBLE);
//                }
                    String orderStatus;
                    if (dataSet.getOrderStatus() != null) {
                        orderStatus = dataSet.getOrderStatus();
                        holder.tv_shaitu.setTag(orderStatus);
                        holder.tv_shaitu1.setTag(orderStatus);
                        holder.tv_zhongjianMsg.setTag(orderStatus);
                        holder.tv_zhongjianMsg.setClickable(true);
                        holder.tv_shaitu.setClickable(true);
                        switch (orderStatus) {
                            case "1":
                                orderStatus = "待支付";
                                holder.tv_shaitu1.setVisibility(View.GONE);
                                holder.tv_shaitu.setText("取消");
                                holder.tv_zhongjianMsg.setText("去支付");
                                holder.tv_zhongjianMsg.setClickable(false);
                                break;
                            case "2":
                                orderStatus = "拼团中，差" + dataSet.getOweNum() + "人";
                                holder.tv_shaitu1.setVisibility(View.GONE);
                                holder.tv_shaitu.setText("查看");
                                holder.tv_zhongjianMsg.setText("邀请好友拼团");
                                holder.tv_shaitu.setClickable(false);
                                break;
                            case "3":
                                if (!dataSet.getSource().equals("7")) {
                                    orderStatus = "未成团，待退款";
                                } else {
                                    orderStatus = "未成团，已退款";
                                }
                                holder.layout_bottom.setVisibility(View.VISIBLE);
                                holder.tv_shaitu1.setVisibility(View.GONE);
                                holder.tv_shaitu.setText("查看");
                                holder.tv_zhongjianMsg.setVisibility(View.VISIBLE);
                                holder.tv_shaitu.setClickable(false);
                                break;
                            case "4":
                                orderStatus = "未成团，已退款";
                                holder.tv_shaitu1.setVisibility(View.GONE);
                                holder.tv_shaitu.setText("查看");
                                holder.tv_zhongjianMsg.setVisibility(View.VISIBLE);
                                holder.tv_shaitu.setClickable(false);
                                holder.layout_bottom.setVisibility(View.VISIBLE);
                                break;
                            case "5":
                                orderStatus = "交易已取消";
                                holder.layout_bottom.setVisibility(View.GONE);
                                break;
                            case "6":
                                if (!dataSet.getSource().equals("7")) {
                                    orderStatus = "未中奖，待返款";
                                } else {
                                    orderStatus = "未中奖，已返款";
                                }
                                holder.tv_zhongjianMsg.setVisibility(View.VISIBLE);
                                holder.tv_shaitu.setVisibility(View.GONE);
//                            holder.tv_shaitu.setText("中奖信息");
//                            if (!dataSet.getSource().equals("7")) {
                                holder.tv_shaitu1.setVisibility(View.GONE);
//                            }
                                break;
                            case "7":
                                orderStatus = "未中奖，已返款";
                                holder.tv_shaitu.setVisibility(View.GONE);
                                holder.tv_zhongjianMsg.setVisibility(View.VISIBLE);
//                            holder.tv_shaitu.setText("中奖信息");
//                            if (!dataSet.getSource().equals("7")) {
                                holder.tv_shaitu1.setVisibility(View.GONE);
//                            }
                                break;
                            case "8":
                                orderStatus = "已成团，待开奖";
                                holder.layout_bottom.setVisibility(View.GONE);
                                break;
                            case "9":
                                orderStatus = "已中奖，已完成";
                                holder.layout_bottom.setVisibility(View.GONE);
                                break;
                            case "10":
                                orderStatus = "已中奖，待发货";
//                            if (!dataSet.getSource().equals("7")) {
                                holder.tv_shaitu1.setVisibility(View.GONE);
//                            }
                                holder.tv_shaitu.setText("中奖信息");
//                            if (dataSet.getSource().equals("7")) {
                                holder.tv_zhongjianMsg.setVisibility(View.GONE);
//                            }
                                holder.tv_zhongjianMsg.setText("申请退款");
                                break;
                            case "11":
                                orderStatus = "已中奖，待收货";
                                holder.tv_shaitu.setText("查看物流");
                                holder.tv_shaitu1.setText("中奖信息");
                                holder.tv_zhongjianMsg.setText("确定收货");
                                break;
                            case "12":
                                orderStatus = "已成团，待开奖";
                                holder.layout_bottom.setVisibility(View.GONE);
                                break;
                        }
                        holder.tv_status.setText(orderStatus);

                    }
                    //中奖信息3
                    holder.tv_zhongjianMsg.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            switch (v.getTag().toString()) {
                                case "1":
//                                holder.tv_zhongjianMsg.setText("去支付");
                                    break;
                                case "2":
                                    Log.i("匿名内部类", "邀请");
                                    Bundle bundle6 = new Bundle();
                                    bundle6.putString("source", dataSet.getSource());
                                    bundle6.putString("recordId", dataSet.getAttendId() + "");
                                    BaseUtil.ToAcb(mContext, GroupDetailActivity.class, bundle6);
//                                holder.tv_zhongjianMsg.setText("邀请好友拼团");
                                    break;
                                case "3":
                                case "4":
                                case "5":
                                case "6":
                                case "7":
                                case "8":
                                case "9":
                                    Log.i("匿名内部类", "查看中奖信息");
                                    Log.i("匿名内部类", "查看中奖信息");
                                    Bundle bundle8 = new Bundle();
                                    if (dataSet.getSource() != null && dataSet.getSource().equals("7")) {
                                        bundle8.putString("activityId", dataSet.getActivityId());
                                        bundle8.putString("activityType", "7");
//                                    LogUtil.Log("activityId===" + bean.getActivityId());
                                        BaseUtil.ToAcb(mContext, PrizeDetailMoreActivity.class, bundle8);//免费抽奖中奖信息直接跳转到全部中奖信息
                                    } else {
                                        bundle8.putString("attendId", dataSet.getAttendId());
                                        bundle8.putString("attendId", dataSet.getAttendId());
                                        bundle8.putString("activityType", dataSet.getSource());
                                        bundle8.putString("orderStatus", dataSet.getOrderStatus());
                                        BaseUtil.ToAcb(mContext, PrizeDetailActivity.class, bundle8);
                                    }
                                    break;
                                case "10":
                                    Log.i("匿名内部类", "申请退款");
                                    Bundle bundle5 = new Bundle();
                                    EventBus.getDefault().post(new AnyEventType(dataSet.getId() + ";xin"));
                                    bundle5.putString("price", dataSet.getOrderPrice() + "");
                                    bundle5.putString("oid", dataSet.getId() + "");
//                            bundle5.putString("phone", Untool.getPhone() + "");
                                    bundle5.putString("orderStatus", dataSet.getOrderStatus());
                                    BaseUtil.ToAcb(mContext, ApplyRefundActivity.class, bundle5);
//                                holder.tv_zhongjianMsg.setText("申请退款");
                                    break;
                                case "11":
                                    Log.i("匿名内部类", "确定收货");
                                    new AlertDialog.Builder(mContext).setTitle("提示框").setMessage("是否确定收货？")
                                            .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                                                public void onClick(DialogInterface dialog, int which) {
                                                    editOrderStatusGet(dataSet.getId(), k);
                                                }
                                            })
                                            .setNegativeButton("取消", null)
                                            .show();
//                                holder.tv_zhongjianMsg.setText("确定收货");
                                    break;
                            }
                        }
                    });
                    //我要晒图2
                    holder.tv_shaitu.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            switch (v.getTag().toString()) {
                                case "1":
//                                holder.tv_shaitu.setText("取消");
                                    Log.i("匿名内部类", "取消");
                                    new AlertDialog.Builder(mContext).setTitle("提示框").setMessage("确认取消该订单？")
                                            .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                                                public void onClick(DialogInterface dialog, int which) {
                                                    cancelOrderGet(dataSet.getId(), k);
                                                }
                                            })
                                            .setNegativeButton("取消", null)
                                            .show();
                                    break;
                                case "10":
//                                holder.tv_shaitu.setText("中奖信息");
                                    Log.i("匿名内部类", "查看中奖信息");
                                    Log.i("匿名内部类", "查看中奖信息");
                                    Bundle bundle8 = new Bundle();
                                    if (dataSet.getSource() != null && dataSet.getSource().equals("7")) {
                                        bundle8.putString("activityId", dataSet.getActivityId());
                                        bundle8.putString("activityType", "7");
//                                    LogUtil.Log("activityId===" + bean.getActivityId());
                                        BaseUtil.ToAcb(mContext, PrizeDetailMoreActivity.class, bundle8);//免费抽奖中奖信息直接跳转到全部中奖信息
                                    } else {
                                        bundle8.putString("attendId", dataSet.getAttendId());
                                        bundle8.putString("attendId", dataSet.getAttendId());
                                        bundle8.putString("activityType", dataSet.getSource());
                                        bundle8.putString("orderStatus", dataSet.getOrderStatus());
                                        BaseUtil.ToAcb(mContext, PrizeDetailActivity.class, bundle8);
                                    }
                                    break;
                                case "11":
//                                holder.tv_shaitu.setText("查看物流");
                                    Log.i("匿名内部类", "查看物流");
                                    Bundle bundle1 = new Bundle();
                                    bundle1.putString("orderId", dataSet.getId());
                                    BaseUtil.ToAcb(mContext, LogisticsActivity.class, bundle1);
                                    break;
                                default:
                                    Bundle bundle = new Bundle();
                                    bundle.putString("oid", dataSet.getId() + "");
                                    if (dataSet.getSource().equals("5") || dataSet.getSource().equals("7")) {
                                        bundle.putString("type", "555");
                                    } else {
//                                    Log.d(TAG, "onItemClick: " + date.get(arg2).getId());
                                        int i = 0;
                                        if (dataSet.getOrderStatus().equals("1")) {
                                            //待付款
                                            if (dataSet.getIsCancel().equals("1")) {
                                                //取消
                                                i = 1;
                                            } else {
                                                //正常
                                                i = 2;
                                            }
                                        } else if (dataSet.getOrderStatus().equals("2")) {
                                            //已付款
                                            if (dataSet.getIsSuccess().equals("0")) {
                                                //拼团中
                                                i = 3;
                                            } else if (dataSet.getIsSuccess().equals("1")) {
                                                //拼团成功
                                                i = 4;
                                            } else if (dataSet.getIsSuccess().equals("1")) {
                                                //拼团失败
                                                i = 41;
                                            }
                                        } else if (dataSet.getOrderStatus().equals("3")) {
                                            //已发货
                                            i = 5;
                                        } else {
                                            //已完成
                                            i = 6;
                                        }
                                        bundle.putString("type", i + "");
                                    }
                                    BaseUtil.ToAcb(mContext, OrderDetailActivity.class, bundle);
                                    break;
                            }
                        }
                    });
                    //我要晒图1
                    holder.tv_shaitu1.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            switch (v.getTag().toString()) {
                                case "11":
//                                holder.tv_shaitu1.setText("中奖信息");
                                    Log.i("匿名内部类", "查看中奖信息");
                                    Bundle bundle8 = new Bundle();
                                    if (dataSet.getSource() != null && dataSet.getSource().equals("7")) {
                                        bundle8.putString("activityId", dataSet.getActivityId());
                                        bundle8.putString("activityType", "7");
//                                    LogUtil.Log("activityId===" + bean.getActivityId());
                                        BaseUtil.ToAcb(mContext, PrizeDetailMoreActivity.class, bundle8);//免费抽奖中奖信息直接跳转到全部中奖信息
                                    } else {
                                        bundle8.putString("attendId", dataSet.getAttendId());
                                        bundle8.putString("attendId", dataSet.getAttendId());
                                        bundle8.putString("activityType", dataSet.getSource());
                                        bundle8.putString("orderStatus", dataSet.getOrderStatus());
                                        BaseUtil.ToAcb(mContext, PrizeDetailActivity.class, bundle8);
                                    }
                                    break;

                            }
                        }
                    });
                    break;
                default:
                    //获取该行数据
                    //图片的下载
                    Glide.with(mContext).load(dataSet.getProductImage()).placeholder(R.mipmap.default_load).error(R.mipmap.default_load).into(ywcholder.ivLogo);
                    ywcholder.tvName.setText(dataSet.getProductName());
                    ywcholder.tvJg.setText(style1);
                    break;
            }
        } catch (Exception E) {
            Log.d("onSuccess: ", E + "");
        }
        return convertView;
    }

    private void cancelOrderGet(String oid, final int position) {
        AbRequestParams params = new AbRequestParams();
        params.put("oid", oid);
        AbHttpUtil.getInstance(mContext).get(Constant.cancelOrder, params, new AbStringHttpResponseListener() {
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
                ToastUtils.showShortToast(mContext, "网络异常，数据加载失败");
                LogUtil.Log(error.getMessage());
            }

            @Override
            public void onSuccess(int statusCode, String content) {
                AbResult result = new AbResult(content);
                if (result.getResultCode() == AbResult.RESULT_OK) {
                    Gson gson = new Gson();
                    Type type = new TypeToken<BaseModel<String>>() {
                    }.getType();
                    BaseModel<String> base = gson.fromJson(content, type);
//                    Log.d(TAG, "onSuccess: " + content);
                    if (base.result != null) {
                        if (base.result.equals("1")) {
//                            if (mStatus.equals("7")) {
//                                mData.get(position).setIsCancel("1");
//                            } else {
//                                mData.remove(position);
//                            }
                            EventBus.getDefault().post(new AnyEventType(mData.get(position).getId() + ";cancel"));
//                            notifyDataSetChanged();
                        }
                        ToastUtils.showShortToast(mContext, base.error_msg);
                    } else {
                        ToastUtils.showShortToast(mContext, base.error_msg);
                    }
                } else {
                    ToastUtils.showShortToast(mContext, "网络异常，数据加载失败");
                    LogUtil.Log(result.getResultMessage());
                }

            }
        });

    }

    private void editOrderStatusGet(String oid, final int position) {
        AbRequestParams params = new AbRequestParams();
        params.put("oid", oid);
        params.put("status", "0");
        params.put("uid", Untool.getUid());
        AbHttpUtil.getInstance(mContext).get(Constant.editOrderStatus, params, new AbStringHttpResponseListener() {
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
                ToastUtils.showShortToast(mContext, "网络异常，数据加载失败");
                LogUtil.Log(error.getMessage());
            }

            @Override
            public void onSuccess(int statusCode, String content) {
                AbResult result = new AbResult(content);
                if (result.getResultCode() == AbResult.RESULT_OK) {
                    Gson gson = new Gson();
                    Type type = new TypeToken<BaseModel<String>>() {
                    }.getType();
                    BaseModel<String> base = gson.fromJson(content, type);
//                    Log.d(TAG, "onSuccess: " + content);
                    if (base.result != null) {
                        if (base.result.equals("1")) {
                            EventBus.getDefault().post(new AnyEventType(mData.get(position).getId() + ";finish"));
//                            notifyDataSetChanged();
                        }
                        ToastUtils.showShortToast(mContext, base.error_msg);
                    } else {
                        ToastUtils.showShortToast(mContext, base.error_msg);
                    }
                } else {
                    ToastUtils.showShortToast(mContext, "网络异常，数据加载失败");
                    LogUtil.Log(result.getResultMessage());
                }

            }
        });

    }

    class QxViewHolder {
        @BindView(R.id.iv_logo)
        ImageView ivLogo;
        @BindView(R.id.tv_name)
        TextView tvName;
        @BindView(R.id.tv_jg)
        TextView tvJg;
        @BindView(R.id.tv_gd)
        TextView tv_gd;

        QxViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }

    class DfkViewHolder {
        @BindView(R.id.iv_logo)
        ImageView ivLogo;
        @BindView(R.id.tv_name)
        TextView tvName;
        @BindView(R.id.tv_jg)
        TextView tvJg;
        @BindView(R.id.tv_zf)
        TextView tvZf;
        @BindView(R.id.tv_qx)
        TextView tvQx;

        DfkViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }

    class PingViewHolder {
        @BindView(R.id.iv_logo)
        ImageView ivLogo;
        @BindView(R.id.tv_name)
        TextView tvName;
        @BindView(R.id.tv_jg)
        TextView tvJg;
        @BindView(R.id.tv_ck)
        TextView tv_ck;
        @BindView(R.id.tv_yq)
        TextView tv_yq;
        @BindView(R.id.tv_zt)
        TextView tv_zt;

        PingViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }

    class DfhViewHolder {
        @BindView(R.id.iv_logo)
        ImageView ivLogo;
        @BindView(R.id.tv_name)
        TextView tvName;
        @BindView(R.id.tv_jg)
        TextView tvJg;
        @BindView(R.id.tv_tk)
        TextView tvTk;
        @BindView(R.id.tv_shsqz)
        TextView tv_shsqz;

        DfhViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }

    class DshViewHolder {
        @BindView(R.id.iv_logo)
        ImageView ivLogo;
        @BindView(R.id.tv_name)
        TextView tvName;
        @BindView(R.id.tv_jg)
        TextView tvJg;
        @BindView(R.id.tv_btn3)
        TextView tv_btn3;
        @BindView(R.id.tv_btn2)
        TextView tv_btn2;
        @BindView(R.id.tv_btn1)
        TextView tv_btn1;
        @BindView(R.id.tv_shsqz)
        TextView tv_shsqz;

        DshViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }

    class YwcViewHolder {
        @BindView(R.id.iv_logo)
        ImageView ivLogo;
        @BindView(R.id.tv_name)
        TextView tvName;
        @BindView(R.id.tv_jg)
        TextView tvJg;

        YwcViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }

    private class ViewHolder {
        ImageView iv_logo;
        TextView tv_title, tv_status, tv_num, tv_price, tv_shaitu1;
        TextView tv_zhongjianMsg, tv_shaitu;
        PercentLinearLayout layout_bottom;
    }
}

