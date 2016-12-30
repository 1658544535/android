package com.qunyu.taoduoduo.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.qunyu.taoduoduo.R;
import com.qunyu.taoduoduo.bean.PrizeDetailBean;
import com.qunyu.taoduoduo.view.CircleTransform;

import java.util.ArrayList;

/**
 * Created by Administrator on 2016/10/28.
 */

public class PrizeDetailMoreAdapter extends BaseExpandableListAdapter {
    private ArrayList<PrizeDetailBean.prizelists> mlist;
    private ArrayList<PrizeDetailBean.prizelists.groupLists> groupListses;

    private Context mc;
    private LayoutInflater layoutInflater;

    public PrizeDetailMoreAdapter(Context c, ArrayList<PrizeDetailBean.prizelists> list) {
        this.mlist = list;
        this.mc = c;
        layoutInflater = LayoutInflater.from(mc);
    }

    @Override
    public int getGroupCount() {
        return mlist != null ? mlist.size() : 0;
    }

    @Override
    public int getChildrenCount(int parentPosition) {
        return mlist.get(parentPosition).getGroupList().size();
    }

    @Override
    public Object getGroup(int parentPosition) {
        return mlist.get(parentPosition);
    }

    @Override
    public Object getChild(int parentPosition, int childPosition) {
        return mlist.get(parentPosition).getGroupList().get(childPosition);
    }

    @Override
    public long getGroupId(int parentPosition) {
        return parentPosition;
    }

    @Override
    public long getChildId(int parentPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }


    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        ViewHolderGroup holderGroup = new ViewHolderGroup();
        if (convertView == null) {
            convertView = layoutInflater.inflate(R.layout.prize_detail_more_groupitem, null);
            holderGroup.layout_groupItem = (LinearLayout) convertView.findViewById(R.id.layout_groupItem);
            convertView.setTag(holderGroup);
        } else {
            holderGroup = (ViewHolderGroup) convertView.getTag();
        }
        return convertView;
    }

    class ViewHolderGroup {
        LinearLayout layout_groupItem;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        ViewHolder holder = new ViewHolder();
        PrizeDetailBean.prizelists.groupLists bean = mlist.get(groupPosition).getGroupList().get(childPosition);
        if (convertView == null) {
            convertView = layoutInflater.inflate(R.layout.prize_detail_more_item, null);
            holder.iv_touxiang = (ImageView) convertView.findViewById(R.id.iv_prizeDetail_touxianm);
            holder.tv_name = (TextView) convertView.findViewById(R.id.tv_prizeDetail_namem);
            holder.tv_time = (TextView) convertView.findViewById(R.id.tv_prizeDetail_timem);
            holder.tv_oderId = (TextView) convertView.findViewById(R.id.tv_prizeDetail_orderNumm);
            holder.tv_tuanzhang_icon = (TextView) convertView.findViewById(R.id.tv_tuanzhang_icon);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        Glide.with(mc).load(bean.getUserlogo()).transform(new CircleTransform(mc)).placeholder(R.mipmap.default_touxiang)
                .error(R.mipmap.default_touxiang).into(holder.iv_touxiang);
        if (bean.getIsHead() != null && bean.getIsHead().equals("1")) {
            holder.tv_tuanzhang_icon.setVisibility(View.VISIBLE);
        } else {
            holder.tv_tuanzhang_icon.setVisibility(View.GONE);
        }
        holder.tv_name.setText(bean.getName());
        holder.tv_oderId.setText(bean.getOrderNo());
        holder.tv_time.setText(bean.getAttendTime());


        return convertView;
    }

    class ViewHolder {
        ImageView iv_touxiang;
        TextView tv_name, tv_oderId, tv_time, tv_tuanzhang_icon;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }
}
