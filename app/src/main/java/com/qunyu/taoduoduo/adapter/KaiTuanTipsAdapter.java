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
import com.qunyu.taoduoduo.bean.GroupDetailBean;

import java.util.List;

/**
 * Created by Administrator on 2016/9/27.
 */

public class KaiTuanTipsAdapter extends BaseAdapter {

    private Context mContext;

    public List<GroupDetailBean.GroupUser> list;


    public KaiTuanTipsAdapter(Context context, List<GroupDetailBean.GroupUser> groupUserList
    ) {
        mContext = context;
        list = groupUserList;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = new ViewHolder();
        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.activity_ping_tuan_jie_guo_chen_yuan_list_item_tz, null, false);
            holder.iv_logo = (ImageView) convertView.findViewById(R.id.iv_logo);
            holder.tv_tuanzhang = (TextView) convertView.findViewById(R.id.tv_tuanzhang);
            holder.tv_name = (TextView) convertView.findViewById(R.id.tv_name);
            holder.tv_time = (TextView) convertView.findViewById(R.id.tv_time);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        GroupDetailBean.GroupUser groupUser = list.get(position);
        if (groupUser.isHead.equals("1")) {
            holder.tv_tuanzhang.setVisibility(View.VISIBLE);
        } else {
            holder.tv_tuanzhang.setVisibility(View.GONE);
        }
        Glide.with(mContext).load(groupUser.userImage).placeholder(R.mipmap.default_touxiang).error(R.mipmap.default_touxiang).into(holder.iv_logo);
        holder.tv_name.setText(groupUser.userName);
        holder.tv_time.setText(groupUser.joinTime);
        return convertView;
    }

    class ViewHolder {
        ImageView iv_logo;
        TextView tv_tuanzhang, tv_name, tv_time;
    }
}

