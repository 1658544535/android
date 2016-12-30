package com.qunyu.taoduoduo.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.qunyu.taoduoduo.R;
import com.qunyu.taoduoduo.bean.OpenGroupBean;
import com.qunyu.taoduoduo.view.CircleTransform;

import java.util.List;

/**
 * Created by Administrator on 2016/9/30.
 */

public class WaitGroupListAdapter extends BaseAdapter {
    private Context mc;
    private List<OpenGroupBean.WaitGroup> mlist;
    private LayoutInflater layoutInflater;
    OpenGroupBean.WaitGroup bean;

    public WaitGroupListAdapter(Context c, List<OpenGroupBean.WaitGroup> list) {
        this.mc = c;
        this.mlist = list;
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
        ViewHolder holder = new ViewHolder();
        if (convertView == null) {
            convertView = layoutInflater.inflate(R.layout.activity_goods_detail_tuan_list_items, null, false);
            holder.tv_userName = (TextView) convertView.findViewById(R.id.tv_userName);
            holder.tv_oweNum = (TextView) convertView.findViewById(R.id.tv_oweNum);
            holder.tv_endTime = (TextView) convertView.findViewById(R.id.tv_endTime);
            holder.tv_cantuan = (TextView) convertView.findViewById(R.id.tv_cantuan);
            holder.iv_userImage = (ImageView) convertView.findViewById(R.id.iv_userImage);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        bean = mlist.get(position);
        holder.tv_userName.setText(bean.userName);
        holder.tv_oweNum.setText("还差" + bean.oweNum + "人成团");
        holder.tv_endTime.setText(bean.Hour + ":" + bean.Min + ":" + bean.Ss+"后结束");
        if (holder.tv_endTime.getTag() != bean.userImage) {
            Glide.with(mc).load(bean.userImage).error(R.mipmap.default_touxiang).transform(new CircleTransform(mc)).into(holder.iv_userImage);
            holder.tv_endTime.setTag(bean.userImage);
        }
        return convertView;
    }

    private class ViewHolder {
        TextView tv_userName, tv_oweNum, tv_endTime, tv_cantuan;
        ImageView iv_userImage;

    }
}
