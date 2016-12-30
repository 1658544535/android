package com.qunyu.taoduoduo.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.qunyu.taoduoduo.R;
import com.qunyu.taoduoduo.bean.MyCouponsListBean;

import java.util.ArrayList;

/**
 * Created by Administrator on 2016/9/29.
 * 我的优惠券
 */

public class CouponsAdapter extends BaseAdapter {
   private Context mc;
   private ArrayList<MyCouponsListBean.CouponList> mlist;
    private ArrayList<MyCouponsListBean> mlists;
   private LayoutInflater layoutInflater;
    String validETime;
    String validSTime;

    public CouponsAdapter(Context context,ArrayList<MyCouponsListBean.CouponList> list) {
        this.mlist=list;
        this.mc=context;
        if(mc!=null){
            layoutInflater= LayoutInflater.from(mc);
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
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder=new ViewHolder();
        MyCouponsListBean.CouponList bean = mlist.get(position);
        if(convertView==null){
            convertView=layoutInflater.inflate(R.layout.coupon_listitem,null,false);
            holder.couponName= (TextView) convertView.findViewById(R.id.tv_couponName);
            holder.effectTime=(TextView)convertView.findViewById(R.id.tv_effective_Dates);
            holder.tv_jdje=(TextView) convertView.findViewById(R.id.tv_jiandiaojine);
            holder.tv_m=(TextView) convertView.findViewById(R.id.tv_m);
            // holder.overduebg1=(ImageView) convertView.findViewById(R.id.iv_overduebg1);
            holder.overduebg2=(ImageView) convertView.findViewById(R.id.iv_overduebg2);
            convertView.setTag(holder);
        }else{
            holder= (ViewHolder) convertView.getTag();
        }
//        if(bean.getSource().equals("0")){
//            holder.couponName.setText("折扣券");
//        }else{
        holder.couponName.setText("折扣券");
//        }

        holder.tv_m.setText("￥"+bean.getM());

        if (mlist.get(position).getIsProduct().equals("1")) {
            if (mlist.get(position).getCouponType().equals("1")) {//判断满减还是直减
                holder.tv_jdje.setText("购买指定商品满" + bean.getOm() + "减" + bean.getM() + "元");
            } else {
                holder.tv_jdje.setText("购买指定商品直减" + bean.getM() + "元");
            }
        } else {
            if (mlist.get(position).getCouponType().equals("1")) {//判断满减还是直减
                holder.tv_jdje.setText("全场商品满" + bean.getOm() + "减" + bean.getM() + "元");
            } else {
                holder.tv_jdje.setText("全场商品直减" + bean.getM() + "元");
            }
        }
        //有效期
        validETime=bean.getValidEtime();
        validSTime=bean.getValidStime();
        String [] startTime=validETime.split("-");
        String [] endTime=validSTime.split("-");
        validETime=startTime[0]+"."+startTime[1]+"."+startTime[2];
        validSTime=endTime[0]+"."+endTime[1]+"."+endTime[2];
        holder.effectTime.setText("有效期："+validSTime+"-"+validETime);

        if(Integer.parseInt(bean.getOverdue())==1){
            holder.tv_m.setBackgroundResource(R.mipmap.coupon_gray);

            holder.overduebg2.setVisibility(View.VISIBLE);
            holder.overduebg2.setImageResource(R.mipmap.over_due);
            holder.couponName.setTextColor(mc.getResources().getColor(R.color.text_09));
            holder.tv_jdje.setTextColor(mc.getResources().getColor(R.color.text_09));
        }
        if(Integer.parseInt(bean.getUsed())==1){
            holder.tv_m.setBackgroundResource(R.mipmap.coupon_gray);

            holder.overduebg2.setVisibility(View.VISIBLE);
            holder.overduebg2.setImageResource(R.mipmap.yishiyong_icon);
            holder.couponName.setTextColor(mc.getResources().getColor(R.color.text_09));
            holder.tv_jdje.setTextColor(mc.getResources().getColor(R.color.text_09));
        }

        return convertView;
    }
    class ViewHolder{
        TextView couponName;
        TextView tv_jdje;
        TextView effectTime;
        TextView tv_m;
        ImageView overduebg1,overduebg2;
    }
}
