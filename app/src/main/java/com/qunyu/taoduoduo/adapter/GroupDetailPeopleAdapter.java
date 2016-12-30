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
import com.qunyu.taoduoduo.view.CircleTransform;

import java.util.List;

/**
 * Created by Administrator on 2016/9/27.
 */

public class GroupDetailPeopleAdapter extends BaseAdapter {

    private Context mContext;

    public List<GroupDetailBean.GroupUser> list;


    public GroupDetailPeopleAdapter(Context context, List<GroupDetailBean.GroupUser> groupUserList
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
            convertView = LayoutInflater.from(mContext).inflate(R.layout.activity_group_detail_touxiang_item, null, false);
            holder.iv_headlogo = (ImageView) convertView.findViewById(R.id.iv_headlogo);
            holder.tv_headtag = (TextView) convertView.findViewById(R.id.tv_headtag);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        GroupDetailBean.GroupUser groupUser = list.get(position);
        if (groupUser.isHead.equals("1")) {
            holder.tv_headtag.setVisibility(View.VISIBLE);
        } else {
            holder.tv_headtag.setVisibility(View.GONE);
        }
        Glide.with(mContext).load(groupUser.userImage).placeholder(R.mipmap.agd_default_touxiang).error(R.mipmap.agd_default_touxiang).transform(new CircleTransform(mContext)).into(holder.iv_headlogo);
        return convertView;
    }

    class ViewHolder {
        ImageView iv_headlogo;
        TextView tv_headtag;
    }
}

