package com.qunyu.taoduoduo.adapter;

import android.content.Context;
import android.os.Bundle;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.qunyu.taoduoduo.R;
import com.qunyu.taoduoduo.activity.PrizeDetailActivity;
import com.qunyu.taoduoduo.activity.PrizeDetailMoreActivity;
import com.qunyu.taoduoduo.activity.SaiDanActivity;
import com.qunyu.taoduoduo.base.BaseUtil;
import com.qunyu.taoduoduo.bean.MyDrawBean;
import com.qunyu.taoduoduo.utils.LogUtil;
import com.zhy.android.percent.support.PercentRelativeLayout;

import java.util.ArrayList;

/**
 * Created by Administrator on 2016/10/7.
 */

/**
 * Created by Administrator on 2016/10/27.
 */
public class MyDrawAdapter extends BaseAdapter {
    private Context mc;
    private ArrayList<MyDrawBean> mlist;
    private MyDrawBean bean;
    private LayoutInflater layoutInflater;
    private String type;//1:0.1抽奖    2:免费抽奖

    public MyDrawAdapter(Context c, ArrayList<MyDrawBean> list, String type) {
        this.mc = c;
        this.mlist = list;
        this.type = type;
        if (mc != null) {
            layoutInflater = LayoutInflater.from(mc);
        }
    }

    @Override
    public int getCount() {
        return mlist.size();
    }

    @Override
    public Object getItem(int position) {
        return mlist.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        bean = mlist.get(position);
        ViewHolder holder = new ViewHolder();
        if (convertView == null) {
            convertView = layoutInflater.inflate(R.layout.my_draw_listitem, null, false);
            holder.iv_logo = (ImageView) convertView.findViewById(R.id.iv_mydraw_logo);
            holder.tv_title = (TextView) convertView.findViewById(R.id.tv_mydraw_title);
            holder.tv_status = (TextView) convertView.findViewById(R.id.tv_mydraw_status);
            holder.tv_num = (TextView) convertView.findViewById(R.id.tv_mydraw_proNum);
            holder.tv_price = (TextView) convertView.findViewById(R.id.tv_mydraw_Price);
            holder.tv_zhongjianMsg = (TextView) convertView.findViewById(R.id.tv_zhongjian_Msg);
            holder.tv_shaitu = (TextView) convertView.findViewById(R.id.tv_shaitu);
            holder.layout_bottom = (PercentRelativeLayout) convertView.findViewById(R.id.layout_mydraw_bottom);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        Glide.with(mc).load(bean.getProductImage()).placeholder(R.mipmap.default_load)
                .error(R.mipmap.default_load).into(holder.iv_logo);
        holder.tv_title.setText(bean.getProductName());
        holder.tv_price.setText(Html.fromHtml("实付:<font color=\"#FF464E\">￥" + bean.getProductPrice() + "</font>(免运费)"));
        //0.1抽奖中奖按钮显示
        if (type != null && type.equals("1")) {
            if (bean.getIsPrize() != null && bean.getIsPrize().equals("0")) {
                holder.layout_bottom.setVisibility(View.GONE);
            } else {
                holder.layout_bottom.setVisibility(View.VISIBLE);
            }
        }

        //判断是否显示我要嗮图按钮
        if (bean.getIsShow() != null && bean.getIsShow().equals("0")) {
            holder.tv_shaitu.setVisibility(View.GONE);
        } else {
            if (type != null && type.equals("2")) {
                holder.tv_shaitu.setVisibility(View.GONE);//免费抽奖不显示
            } else {
                holder.tv_shaitu.setVisibility(View.VISIBLE);
            }
        }
        String orderStatus;
        if (bean.getOrderStatus() != null) {
            orderStatus = bean.getOrderStatus();
            switch (orderStatus) {
                case "1":
                    orderStatus = "待支付";
                    break;
                case "2":
                    orderStatus = "拼团中，差" + bean.getPoorNum() + "人";
                    holder.tv_zhongjianMsg.setVisibility(View.GONE);
                    break;
                case "3":
                    if (type != null && type.equals("2")) {
                        orderStatus = "未成团，已退款";
                        holder.tv_zhongjianMsg.setVisibility(View.VISIBLE);
                    } else {
                        orderStatus = "未成团，退款中";
                    }

                    break;
                case "4":
                    orderStatus = "未成团，已退款";
                    if (type != null && type.equals("2")) {
                        holder.tv_zhongjianMsg.setVisibility(View.VISIBLE);
                    }
                    break;
                case "5":
                    orderStatus = "交易已取消";
                    break;
                case "6":
                    if (type != null && type.equals("2")) {
                        holder.tv_zhongjianMsg.setVisibility(View.VISIBLE);
                        orderStatus = "未中奖，已返款";
                    } else {
                        orderStatus = "未中奖，待返款";
                    }
                    break;
                case "7":
                    orderStatus = "未中奖，已返款";
                    if (type != null && type.equals("2")) {
                        holder.tv_zhongjianMsg.setVisibility(View.VISIBLE);
                    }
                    break;
                case "12":
                    if (type != null && type.equals("2")) {
                        orderStatus = "已成团，待开奖";

                    } else {
                        orderStatus = "待开奖";

                    }
                    holder.tv_zhongjianMsg.setVisibility(View.GONE);
                    break;
                case "9":
                    orderStatus = "已中奖，已完成";
                    if (type != null && type.equals("2")) {
                        holder.tv_zhongjianMsg.setVisibility(View.VISIBLE);
                    }
                    break;
                case "10":
                    orderStatus = "已中奖，待发货";
                    if (type != null && type.equals("2")) {
                        holder.tv_zhongjianMsg.setVisibility(View.VISIBLE);
                    }
                    break;
                case "11":
                    orderStatus = "已中奖，待收货";
                    if (type != null && type.equals("2")) {
                        holder.tv_zhongjianMsg.setVisibility(View.VISIBLE);
                    }
                    break;
            }
            holder.tv_status.setText(orderStatus);
        }
        //中奖信息
        holder.tv_zhongjianMsg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle b = new Bundle();
//                b.putString("attendId", mlist.get(position).getAttendId());
//                LogUtil.Log("attendId=======" + mlist.get(position).getAttendId());
////                b.putString("activityId", mlist.get(position).getActivityId());
////                LogUtil.Log("attendId=======" + mlist.get(position).getActivityId());
//                BaseUtil.ToAcb(mc, PrizeDetailActivity.class, b);
//                if (mlist.get(position).getOrderStatus() != null && mlist.get(position).getOrderStatus().equals("3") || mlist.get(position).getOrderStatus().equals("4")) {
//                    LogUtil.Log("activityId=======" + mlist.get(position).getActivityId());
//                    b.putString("activityId", mlist.get(position).getActivityId());
//                    BaseUtil.ToAcb(mc, PrizeDetailMoreActivity.class, b);
//                } else {

                if (type != null && type.equals("2")) {
                    b.putString("activityId", mlist.get(position).getActivityId());
                    b.putString("activityType", "7");
                    LogUtil.Log("activityId===" + bean.getActivityId());
                    BaseUtil.ToAcb(mc, PrizeDetailMoreActivity.class, b);//免费抽奖中奖信息直接跳转到全部中奖信息
                } else {
                    b.putString("activityType", "5");
                    b.putString("attendId", mlist.get(position).getAttendId());
                    b.putString("orderStatus", mlist.get(position).getOrderStatus());
                    BaseUtil.ToAcb(mc, PrizeDetailActivity.class, b);
                }

                LogUtil.Log("attendId=======" + mlist.get(position).getAttendId());
//                b.putString("activityId", mlist.get(position).getActivityId());
//                LogUtil.Log("attendId=======" + mlist.get(position).getActivityId());


//            }
            }
        });
        //我要晒图
        holder.tv_shaitu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle b = new Bundle();
                b.putString("AttendId", mlist.get(position).getAttendId());
                b.putString("activityId", mlist.get(position).getActivityId());
                b.putString("pid", mlist.get(position).getProductId());
                b.putString("image", mlist.get(position).getProductImage());
                b.putString("name", mlist.get(position).getProductName());
                BaseUtil.ToAcb(mc, SaiDanActivity.class, b);
            }
        });


        return convertView;
    }

    private class ViewHolder {
        ImageView iv_logo;
        TextView tv_title, tv_status, tv_num, tv_price;
        TextView tv_zhongjianMsg, tv_shaitu;
        PercentRelativeLayout layout_bottom;
    }

}
