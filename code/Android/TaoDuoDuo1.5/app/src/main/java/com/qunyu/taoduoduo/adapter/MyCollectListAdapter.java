package com.qunyu.taoduoduo.adapter;

import android.content.Context;
import android.os.Bundle;
import android.os.Message;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.qunyu.taoduoduo.R;
import com.qunyu.taoduoduo.activity.GoodsDetailActivity;
import com.qunyu.taoduoduo.activity.MyCollectListActivity;
import com.qunyu.taoduoduo.base.BaseUtil;
import com.qunyu.taoduoduo.bean.MyCollectListBean;

import java.util.ArrayList;

/**
 * Created by Administrator on 2016/10/10.
 */

public class MyCollectListAdapter extends BaseAdapter {
    private Context mc;
    private LayoutInflater layoutInflater;
    private ArrayList<MyCollectListBean> mlist;
    private MyCollectListBean bean;

    public MyCollectListAdapter(Context c, ArrayList<MyCollectListBean> list){
        this.mc=c;
        this.mlist=list;
        layoutInflater= LayoutInflater.from(mc);
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
        ViewHolder holder=new ViewHolder();
        bean=mlist.get(position);
        if(convertView==null){
            convertView=layoutInflater.inflate(R.layout.my_collect_listitem,null,false);
            holder.tv_title= (TextView) convertView.findViewById(R.id.tv_shangpin_title);
            holder.tv_payM= (TextView) convertView.findViewById(R.id.tv_tuanNum);
            holder.iv_logo= (ImageView) convertView.findViewById(R.id.iv_shangpin_logo);
            holder.iv_collect_icon= (ImageView) convertView.findViewById(R.id.iv_collect_icon);
            holder.iv_qukaituan= (ImageView) convertView.findViewById(R.id.iv_qukaituan);
            convertView.setTag(holder);
        }else{
            holder= (ViewHolder) convertView.getTag();
        }
        holder.tv_title.setText(bean.getProductName());
        holder.tv_payM.setText(Html.fromHtml(""+bean.getGroupNum()+"人团  ￥<font color=\"#FF464E\">"+bean.getProductPrice()+"</font>"));
        Glide.with(mc).load(bean.getProductImage()).placeholder(R.mipmap.default_load).error(R.mipmap.default_load).into(holder.iv_logo);
        holder.iv_collect_icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Message msg=new Message();
                Bundle data=new Bundle();
                data.putString("activityId",mlist.get(position).getActivityId());
                msg.setData(data);
                msg.arg1=position;
                MyCollectListActivity.handler.sendMessage(msg);
            }
        });
        holder.iv_qukaituan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle b=new Bundle();
                b.putString("activityId",mlist.get(position).getActivityId());
                b.putString("pid",mlist.get(position).getProductId());
                BaseUtil.ToAcb(mc,GoodsDetailActivity.class,b);
            }
        });

        return convertView;
    }
    class ViewHolder{
        TextView tv_title,tv_payM;
        ImageView iv_logo,iv_collect_icon,iv_qukaituan;
    }
}

