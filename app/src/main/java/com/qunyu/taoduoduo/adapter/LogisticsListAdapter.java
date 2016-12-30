package com.qunyu.taoduoduo.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.qunyu.taoduoduo.R;
import com.qunyu.taoduoduo.bean.LogisticsBean;

import java.util.ArrayList;

/**
 * Created by Administrator on 2016/10/7.
 */

public class LogisticsListAdapter extends BaseAdapter {
    Context mc;
    LayoutInflater layoutInflater;
    private ArrayList<LogisticsBean.ExpressBean> mlist;


    public LogisticsListAdapter(Context c, ArrayList<LogisticsBean.ExpressBean> list){
        this.mc=c;
        this.mlist=list;
       // this.pageNo=pageNo;

        layoutInflater= LayoutInflater.from(mc);
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
        ViewHolder holder=new ViewHolder();
        LogisticsBean.ExpressBean bean = mlist.get(position);

        if(convertView==null){
            convertView=layoutInflater.inflate(R.layout.my_logistics_item,null,false);
            holder.tv_content= (TextView) convertView.findViewById(R.id.tv_logistics_message);
            holder.tv_time= (TextView) convertView.findViewById(R.id.tv_logistics_time);
            holder.iv_indicator= (ImageView) convertView.findViewById(R.id.iv_logistics_indicator);
            convertView.setTag(holder);
        }else{
            holder= (ViewHolder) convertView.getTag();
        }
        if (bean.getIndex().equals("0")) {
                holder.tv_content.setTextColor(mc.getResources().getColor(R.color.text_01));
                holder.tv_time.setTextColor(mc.getResources().getColor(R.color.text_01));
                holder.iv_indicator.setImageResource(R.mipmap.logisticsbig);
        } else {
            holder.tv_content.setTextColor(mc.getResources().getColor(R.color.text_23));
            holder.tv_time.setTextColor(mc.getResources().getColor(R.color.text_23));
            holder.iv_indicator.setImageResource(R.mipmap.logistics);
            }
        holder.tv_content.setText(bean.getContent());
        holder.tv_time.setText(bean.getTime());
        return convertView;
    }
    private class ViewHolder{
        TextView tv_content,tv_time;
        ImageView iv_indicator;
    }
}
