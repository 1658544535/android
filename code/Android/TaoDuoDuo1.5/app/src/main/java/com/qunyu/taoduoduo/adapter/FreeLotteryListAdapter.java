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
import com.qunyu.taoduoduo.activity.PrizeDetailMoreActivity;
import com.qunyu.taoduoduo.base.BaseUtil;
import com.qunyu.taoduoduo.bean.LotteryListBean;
import com.qunyu.taoduoduo.utils.LogUtil;

import java.util.ArrayList;


/**
 * Created by Administrator on 2016/10/27.
 */
public class FreeLotteryListAdapter extends BaseAdapter {
    private Context mc;
    private ArrayList<LotteryListBean> mlist;
    private LotteryListBean bean;
    private LayoutInflater layoutInflater;
    private String type;
    private String index;

    public FreeLotteryListAdapter(Context c, ArrayList<LotteryListBean> list, String type, String index) {
        this.mc = c;
        this.mlist = list;
        this.type = type;
        this.index = index;
        try {
            layoutInflater = LayoutInflater.from(mc);
        } catch (Exception e) {
            e.printStackTrace();
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
        try {
            if (convertView == null) {
                convertView = layoutInflater.inflate(R.layout.freelotterylist_item, null, false);
                holder.iv_logo = (ImageView) convertView.findViewById(R.id.iv_lotteryLogo);
                holder.iv_openGroup = (ImageView) convertView.findViewById(R.id.iv_openGroup);
                holder.tv_title = (TextView) convertView.findViewById(R.id.tv_lotteryTitle);
                holder.tv_price = (TextView) convertView.findViewById(R.id.tv_lotteryPrice);
                holder.iv_ckpl = (ImageView) convertView.findViewById(R.id.iv_ckpl);
                holder.tv_alonePrice = (TextView) convertView.findViewById(R.id.tv_lotteryAlonePrice);
                holder.tv_num = (TextView) convertView.findViewById(R.id.tv_tnum);
                holder.tv_zhongjian_Msg = (TextView) convertView.findViewById(R.id.tv_zhongjian_Msg);
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

            byte[] array = bean.getGroupNum().getBytes();
            if (array.length <= 1) {
                holder.tv_title.setText("\u3000\u3000\u3000" + bean.getProductName());
            } else if (array.length <= 2) {
                holder.tv_title.setText("\u3000\u3000\u3000" + bean.getProductName());
            } else {
                holder.tv_title.setText("\u3000\u3000\u3000\u3000" + bean.getProductName());
            }
            holder.tv_num.setText(bean.getGroupNum() + "人团");
            holder.tv_price.setText("￥" + bean.getProductPrice());

            if (type != null && type.equals("1")) {
                holder.iv_ckpl.setVisibility(View.GONE);
                holder.tv_zhongjian_Msg.setVisibility(View.GONE);
                if (index != null && index.equals("free")) {
                    holder.iv_openGroup.setImageResource(R.mipmap.freegroup_btn);
                } else {
                    holder.iv_openGroup.setImageResource(R.mipmap.open_group_icon);
                }
            } else if (type != null && type.equals("2")) {
                holder.iv_openGroup.setVisibility(View.GONE);
                if (index != null && index.equals("free")) {
                    holder.iv_ckpl.setVisibility(View.GONE);
                } else {
                    holder.tv_zhongjian_Msg.setVisibility(View.GONE);
                }
            }


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
            holder.tv_zhongjian_Msg.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Bundle b = new Bundle();
                    b.putString("activityId", mlist.get(position).getActivityId());
                    b.putString("activityType", "7");
                    LogUtil.Log("activityId===" + bean.getActivityId());
                    BaseUtil.ToAcb(mc, PrizeDetailMoreActivity.class, b);
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }


        return convertView;
    }

    private class ViewHolder {
        ImageView iv_logo, iv_openGroup, iv_ckpl;
        TextView tv_title, tv_price, tv_alonePrice, tv_num, tv_zhongjian_Msg;
    }

}
