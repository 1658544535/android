package com.qunyu.taoduoduo.adapter;

import android.content.Context;
import android.graphics.Paint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.qunyu.taoduoduo.R;
import com.qunyu.taoduoduo.activity.GoodsDetailActivity;
import com.qunyu.taoduoduo.activity.PinLunActivity;
import com.qunyu.taoduoduo.base.BaseUtil;
import com.qunyu.taoduoduo.bean.LotteryListBean;

import java.util.ArrayList;

/**
 * Created by Administrator on 2016/10/7.
 */

/**
 * Created by Administrator on 2016/10/27.
 */
public class LotteryListAdapter extends BaseAdapter {
    private Context mc;
    private ArrayList<LotteryListBean> mlist;
    private LotteryListBean bean;
    private LayoutInflater layoutInflater;
    private String type;

    public LotteryListAdapter(Context c, ArrayList<LotteryListBean> list, String type) {
        this.mc = c;
        this.mlist = list;
        this.type = type;
        layoutInflater = LayoutInflater.from(mc);
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
            convertView = layoutInflater.inflate(R.layout.lotterylist_item, null, false);
            holder.iv_logo = (ImageView) convertView.findViewById(R.id.iv_lotteryLogo);
            holder.iv_openGroup = (ImageView) convertView.findViewById(R.id.iv_openGroup);
            holder.tv_title = (TextView) convertView.findViewById(R.id.tv_lotteryTitle);
            holder.tv_price = (TextView) convertView.findViewById(R.id.tv_lotteryPrice);
            holder.iv_ckpl = (ImageView) convertView.findViewById(R.id.iv_ckpl);
            holder.tv_alonePrice = (TextView) convertView.findViewById(R.id.tv_lotteryAlonePrice);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.tv_alonePrice.setText("￥" + bean.getAlonePrice());
        holder.tv_alonePrice.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);
        Glide.with(mc).load(bean.getProductImage())
                .placeholder(R.mipmap.default_load)
                .error(R.mipmap.default_load)
                .into(holder.iv_logo);
        if (type != null && type.equals("1")) {
            holder.iv_ckpl.setVisibility(View.GONE);
        } else if (type != null && type.equals("2")) {
            holder.iv_openGroup.setVisibility(View.GONE);
        }
        holder.tv_title.setText(bean.getProductName());
//        holder.tv_price.setText(bean.getProductPrice());

        //立即开团
        holder.iv_openGroup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle b = new Bundle();
                b.putString("pid", mlist.get(position).getProductId());
                b.putString("activityId", mlist.get(position).getActivityId());
                BaseUtil.ToAcb(mc, GoodsDetailActivity.class, b);
            }
        });

        holder.iv_ckpl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle b = new Bundle();
                b.putString("pid", mlist.get(position).getProductId());
                b.putString("activityId", mlist.get(position).getActivityId());
                BaseUtil.ToAcb(mc, PinLunActivity.class, b);
            }
        });



        return convertView;
    }

    private class ViewHolder {
        ImageView iv_logo, iv_openGroup, iv_ckpl;
        TextView tv_title, tv_price, tv_alonePrice;
    }

}
