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
import com.qunyu.taoduoduo.activity.CaiGoodsDetailActivity;
import com.qunyu.taoduoduo.base.BaseUtil;
import com.qunyu.taoduoduo.bean.MyCaijiaListBean;

import java.util.ArrayList;

/**
 * Created by Administrator on 2016/10/6.
 */

public class MyCaijiaListAdapter extends BaseAdapter {
    private ArrayList<MyCaijiaListBean> mlist;
    private MyCaijiaListBean bean;
    private LayoutInflater layoutInflater;
    private String minPrice, maxPrice;
    private String myPrice;
    Context mc;

    public MyCaijiaListAdapter(Context c, ArrayList<MyCaijiaListBean> list) {
        this.mc = c;
        this.mlist = list;
        layoutInflater = (LayoutInflater) mc.getSystemService(mc.LAYOUT_INFLATER_SERVICE);
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
        ViewHolder holder = new ViewHolder();
        bean = mlist.get(position);
        if (convertView == null) {
            convertView = layoutInflater.inflate(R.layout.my_caijia_listitem, null, false);
            holder.tv_status = (TextView) convertView.findViewById(R.id.tv_caijia_status);
            holder.tv_title = (TextView) convertView.findViewById(R.id.tv_shangpin_title);
            holder.tv_jiage_status = (TextView) convertView.findViewById(R.id.tv_jiage_status);
            holder.tv_mycaijia = (TextView) convertView.findViewById(R.id.tv_mycaijia);
            holder.iv_product_logo = (ImageView) convertView.findViewById(R.id.iv_shangpin_logo);
            holder.iv_detail = (ImageView) convertView.findViewById(R.id.iv_watchDetail);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        //活动类型(1进行中,2已结束,3待开奖)
        int activityStatus = Integer.parseInt(mlist.get(position).getActivityStatus());
        String price = mlist.get(position).getPrize();
        if (activityStatus == 3) {
            holder.tv_status.setText("待开奖");
        } else if (activityStatus == 1) {
            holder.tv_status.setText("进行中");
        } else if (activityStatus == 2) {
            if (price == null || price == "") {
                holder.tv_status.setText("未得奖");
            } else {
                switch (price) {
                    case "1":
                        if (bean.getIsRecCoupon().equals("0")) {
                            holder.tv_status.setText("一等奖，待完善信息");
                        } else if (bean.getIsRecCoupon().equals("1")) {
                            holder.tv_status.setText("一等奖，已完成");
                        }
                        break;
                    case "2":
                        if (bean.getIsRecCoupon().equals("0")) {
                            holder.tv_status.setText("二等奖,奖品发放中...");
                        } else if (bean.getIsRecCoupon().equals("1")) {
                            holder.tv_status.setText("二等奖，已送券");
                        }
                        break;
                    case "3":
                        if (bean.getIsRecCoupon().equals("0")) {
                            holder.tv_status.setText("三等奖,奖品发放中");

                        } else if (bean.getIsRecCoupon().equals("1")) {
                            holder.tv_status.setText("三等奖，已送券");
                        }
                        break;
                }
            }
        }
        holder.tv_title.setText(mlist.get(position).getProductName());
        Glide.with(mc).load(mlist.get(position).getProductImage()).placeholder(R.mipmap.default_load).error(R.mipmap.default_load).into(holder.iv_product_logo);

        myPrice = mlist.get(position).getUserPrice();
        holder.tv_mycaijia.setText(Html.fromHtml("我的猜价：<font color=\"#FF464E\">￥" + myPrice + "</font>"));
        minPrice = mlist.get(position).getMinPrice();
        maxPrice = mlist.get(position).getMaxPrice();
        holder.tv_jiage_status.setText(Html.fromHtml(
                "价格区间：<font color=\"#FF464E\">￥" + minPrice + "-" + maxPrice + " </font>"));

        //查看详情
        holder.iv_detail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle b = new Bundle();
                b.putString("activityId", mlist.get(position).getActivityId());
                b.putString("productId", mlist.get(position).getProductId());
                BaseUtil.ToAcb(mc, CaiGoodsDetailActivity.class, b);
            }
        });
        return convertView;
    }

    class ViewHolder {
        TextView tv_status, tv_title, tv_jiage, tv_jiage_status, tv_mycaijia;
        ImageView iv_product_logo, iv_detail;
    }
}
