package com.qunyu.taoduoduo.adapter;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.qunyu.taoduoduo.R;
import com.qunyu.taoduoduo.activity.UserOrderNoticeActivity;
import com.qunyu.taoduoduo.base.BaseUtil;
import com.qunyu.taoduoduo.bean.MyNoticeBean;
import com.qunyu.taoduoduo.config.AppConfig;
import com.qunyu.taoduoduo.utils.LogUtil;

import java.util.ArrayList;


/**
 * Created by Administrator on 2016/12/5.
 * 我的消息adapter
 */
public class MyNoticeAdapter extends BaseAdapter {
    private Context mc;
    private ArrayList<MyNoticeBean> mlist;
    private MyNoticeBean bean;
    private LayoutInflater layoutInflater;
    private Integer noticSize;

    public MyNoticeAdapter(Context c, ArrayList<MyNoticeBean> list, Integer noticSize) {
        this.mc = c;
        this.mlist = list;
        this.noticSize = noticSize;
        LogUtil.Log("noticesize:" + noticSize + "|" + AppConfig.MYNOTIC_SIZE);
        if(mc !=null){
            layoutInflater = LayoutInflater.from(mc);
        }

    }

    @Override
    public int getCount() {
        return mlist != null ? mlist.size() : 0;
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
    public View getView(int position, View convertView, ViewGroup parent) {
        final MyNoticeBean bean = mlist.get(position);
        ViewHolder holder = new ViewHolder();
        if (convertView == null) {
            convertView = layoutInflater.inflate(R.layout.message_fragment_item, null, false);
            holder.iv_icon = (ImageView) convertView.findViewById(R.id.iv_msgIcon);
            holder.tv_title = (TextView) convertView.findViewById(R.id.tv_msgTitle);
            holder.tv_stitle = (TextView) convertView.findViewById(R.id.tv_msgSTitle);
            holder.tv_time = (TextView) convertView.findViewById(R.id.tv_msgTime);
            holder.iv_redPoint = (ImageView) convertView.findViewById(R.id.iv_red_point);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        Glide.with(mc).load(bean.getImages()).placeholder(R.mipmap.default_load).error(R.mipmap.default_load)
                .into(holder.iv_icon);
        holder.tv_title.setText(bean.getName());
        holder.tv_stitle.setText(bean.getTitle());
        holder.tv_time.setText(bean.getTime());

        //小红点的显示
        if (AppConfig.MYNOTIC_SIZE != null) {
            if (Integer.parseInt(bean.getCount()) > AppConfig.MYNOTIC_SIZE) {
                holder.iv_redPoint.setVisibility(View.VISIBLE);
            } else {
                holder.iv_redPoint.setVisibility(View.GONE);
            }
        }

        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (bean.getType() != null && bean.getType().equals("1")) {//系统消息

                } else if (bean.getType() != null && bean.getType().equals("2")) {//每日推荐

                } else if (bean.getType() != null && bean.getType().equals("3")) {//订单消息
                    Bundle b = new Bundle();
                    b.putString("count", bean.getCount());
                    BaseUtil.ToAcb(mc, UserOrderNoticeActivity.class, b);
                }
            }
        });

        return convertView;
    }

    private class ViewHolder {
        ImageView iv_icon, iv_redPoint;
        TextView tv_title, tv_stitle, tv_time;

    }

}
