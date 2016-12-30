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
import com.qunyu.taoduoduo.activity.CaiJiaGeActivity;
import com.qunyu.taoduoduo.activity.FreeLotteryListActivity;
import com.qunyu.taoduoduo.activity.GoodsDetailActivity;
import com.qunyu.taoduoduo.activity.GroupDetailActivity;
import com.qunyu.taoduoduo.activity.LotteryListActivity;
import com.qunyu.taoduoduo.activity.OrderDetailActivity;
import com.qunyu.taoduoduo.activity.SpecialDetailActivity;
import com.qunyu.taoduoduo.activity.TabActivity;
import com.qunyu.taoduoduo.activity.ZhuanTiFengLeiActivity;
import com.qunyu.taoduoduo.activity.ZoneActivity;
import com.qunyu.taoduoduo.base.BaseUtil;
import com.qunyu.taoduoduo.bean.UserOrderNoticeBean;
import com.qunyu.taoduoduo.utils.LogUtil;
import com.zhy.android.percent.support.PercentRelativeLayout;

import java.util.ArrayList;

import static com.qunyu.taoduoduo.R.id.tv_describe;
import static com.qunyu.taoduoduo.R.id.tv_linkName;

/**
 * Created by Administrator on 2016/10/7.
 */

/**
 * Created by Administrator on 2016/12/7.
 */
public class UserOrderNoticeAdapter extends BaseAdapter {
    private Context mc;
    private ArrayList<UserOrderNoticeBean> mlist;
    private LayoutInflater layoutInflater;

    public UserOrderNoticeAdapter(Context c, ArrayList<UserOrderNoticeBean> list) {
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
    public View getView(int position, View convertView, ViewGroup parent) {
        LogUtil.Log(position + "");
        LogUtil.Log(mlist.size() + "");
        final UserOrderNoticeBean bean = mlist.get(position);
        ViewHolder holder = new ViewHolder();
        if (convertView == null) {
            convertView = layoutInflater.inflate(R.layout.mynotice_item2, parent, false);
            holder.tv_time = (TextView) convertView.findViewById(R.id.tv_titleTime);
            holder.tv_proName = (TextView) convertView.findViewById(R.id.tv_title);
            holder.tv_titleName = (TextView) convertView.findViewById(R.id.tv_titleName);
            holder.tv_describe = (TextView) convertView.findViewById(tv_describe);
            holder.tv_linkName = (TextView) convertView.findViewById(tv_linkName);
            holder.iv_proImage = (ImageView) convertView.findViewById(R.id.iv_proImage);
            holder.layout_readAll = (PercentRelativeLayout) convertView.findViewById(R.id.layout_readAll);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.tv_time.setText(bean.getTime());
        holder.tv_titleName.setText(bean.getTitle());
        Glide.with(mc).load(bean.getProductImage()).placeholder(R.mipmap.default_load).error(R.mipmap.default_load)
                .into(holder.iv_proImage);

        holder.tv_proName.setText(bean.getProductName());
        holder.tv_linkName.setText(bean.getLinkName());
        String describe = bean.getContent();
        holder.tv_describe.setText(describe);

        /*linkType：跳转对应页面
        * 1-商品详情页，2-订单详情页，3-团详情页，4-首页，5-普通拼团，
        * 6-猜价格，7-免费抽奖，8-专题，9-专题分类，10-77专区，11-0.1夺宝*/
        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LogUtil.Log("click");
                LogUtil.Log("type====" + bean.getLinkType());
                switch (bean.getLinkType()) {
                    case "1":
                        Bundle b1 = new Bundle();
                        b1.putString("activityId", bean.getLinkParam());
//                        b1.putString("tag", "caijia");
                        b1.putString("pid", bean.getProductId());
                        BaseUtil.ToAcb(mc, GoodsDetailActivity.class, b1);
                        break;
                    case "2":
                        Bundle b2 = new Bundle();
                        b2.putString("oid", bean.getLinkParam());
                        b2.putString("type", "0");
                        BaseUtil.ToAcb(mc, OrderDetailActivity.class, b2);
                        break;
                    case "3":
                        Bundle b3 = new Bundle();
                        b3.putString("recordId", bean.getLinkParam());
                        BaseUtil.ToAcb(mc, GroupDetailActivity.class, b3);
                        break;
                    case "4":
                        BaseUtil.ToAc(mc, TabActivity.class);
                        break;
                    case "5":
                        Bundle b6 = new Bundle();
                        b6.putString("activityId", bean.getLinkParam());
                        b6.putString("pid", bean.getProductId());
//                        b6.putString("tag", "caijia");
                        BaseUtil.ToAcb(mc, GoodsDetailActivity.class, b6);
                        break;
                    case "6":
                        BaseUtil.ToAc(mc, CaiJiaGeActivity.class);
                        break;
                    case "7":
                        BaseUtil.ToAc(mc, FreeLotteryListActivity.class);
                        break;
                    case "8":
                        Bundle b4 = new Bundle();
                        b4.putString("specialId", bean.getLinkParam());
                        BaseUtil.ToAcb(mc, SpecialDetailActivity.class, b4);
                        break;
                    case "9":
                        Bundle b5 = new Bundle();
                        b5.putString("id", bean.getLinkParam());
                        BaseUtil.ToAcb(mc, ZhuanTiFengLeiActivity.class, b5);
                        break;
                    case "10":
                        BaseUtil.ToAc(mc, ZoneActivity.class);
                        break;
                    case "11":
                        BaseUtil.ToAc(mc, LotteryListActivity.class);
                        break;
                }
            }
        });
        return convertView;
    }

    private class ViewHolder {
        TextView tv_time, tv_titleName, tv_proName, tv_describe, tv_linkName;
        ImageView iv_proImage;
        PercentRelativeLayout layout_readAll;

    }

}
