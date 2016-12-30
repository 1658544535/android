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
import com.qunyu.taoduoduo.activity.GroupDetailActivity;
import com.qunyu.taoduoduo.activity.OrderDetailActivity;
import com.qunyu.taoduoduo.base.BaseUtil;
import com.qunyu.taoduoduo.bean.MyGroupListBean;

import java.util.ArrayList;

/**
 * Created by Administrator on 2016/9/29.
 * 我的团
 */

public class MyGroupListAdapter extends BaseAdapter {
    private Context mc;
    private ArrayList<MyGroupListBean> mlist;
    private MyGroupListBean bean;
    private LayoutInflater layoutInflater;


    public MyGroupListAdapter(Context context, ArrayList<MyGroupListBean> list) {
        this.mlist = list;
        this.mc = context;
        if (mc != null) {
            layoutInflater = (LayoutInflater) mc.getSystemService(mc.LAYOUT_INFLATER_SERVICE);
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
        ViewHolder holder = new ViewHolder();
        bean = mlist.get(position);
        if (convertView == null) {
            convertView = layoutInflater.inflate(R.layout.my_tuan_listitem, null, false);
            holder.tv_status = (TextView) convertView.findViewById(R.id.tv_mytuan_status);
            holder.tv_title = (TextView) convertView.findViewById(R.id.tv_shangpin_title);
            holder.tv_price = (TextView) convertView.findViewById(R.id.tv_Price);
            holder.iv_proImage = (ImageView) convertView.findViewById(R.id.iv_shangpin_logo);
            holder.iv_invite = (ImageView) convertView.findViewById(R.id.iv_invite);
            holder.iv_orderDetail = (ImageView) convertView.findViewById(R.id.iv_orderDetails);
            holder.iv_tuanDetail = (ImageView) convertView.findViewById(R.id.iv_tuanDetail);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        Glide.with(mc).load(bean.getProductImage()).placeholder(R.mipmap.default_load).error(R.mipmap.default_load).into(holder.iv_proImage);

        int status = Integer.parseInt(bean.getActivityStatus());

        if (status == 1) {
            holder.tv_status.setText("拼团中");
            holder.iv_tuanDetail.setVisibility(View.GONE);
            holder.iv_invite.setVisibility(View.VISIBLE);
        } else if (status == 2) {
            holder.tv_status.setText("拼团成功");
        } else if (status == 3) {
            holder.tv_status.setText("拼团失败");
        }
        holder.tv_title.setText(bean.getProductName());
        holder.tv_price.setText(Html.fromHtml("" + bean.getGroupNum() + "人团 <font color=\"#FF464E\">￥" + bean.getAlonePrice() + "</font>"));

        holder.iv_orderDetail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle b = new Bundle();
                b.putString("oid", mlist.get(position).getOrderId());
                if (mlist.get(position).getActivityStatus().equals("1")) {
                    b.putString("type", "3");
                } else if (mlist.get(position).getActivityStatus().equals("3")) {
                    b.putString("type", "41");
                } else if (mlist.get(position).getActivityStatus().equals("2")) {
                    b.putString("type", "4");
                }
                BaseUtil.ToAcb(mc, OrderDetailActivity.class, b);
            }
        });
        holder.iv_tuanDetail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle b = new Bundle();
                b.putString("source", "1");
                b.putString("recordId", mlist.get(position).getGroupRecId());
                BaseUtil.ToAcb(mc, GroupDetailActivity.class, b);
            }
        });
        holder.iv_invite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle b = new Bundle();
                b.putString("source", "1");
                b.putString("recordId", mlist.get(position).getGroupRecId());
                BaseUtil.ToAcb(mc, GroupDetailActivity.class, b);
            }
        });

        return convertView;
    }

    class ViewHolder {
        TextView tv_status;
        TextView tv_title;
        TextView tv_price;
        ImageView iv_proImage, iv_invite, iv_orderDetail, iv_tuanDetail;
    }
}
