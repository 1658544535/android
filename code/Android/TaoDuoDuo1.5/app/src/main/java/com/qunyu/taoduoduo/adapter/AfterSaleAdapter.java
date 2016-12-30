package com.qunyu.taoduoduo.adapter;

import android.content.Context;
import android.os.Bundle;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.qunyu.taoduoduo.R;
import com.qunyu.taoduoduo.activity.ApplyRefundActivity;
import com.qunyu.taoduoduo.activity.LogisticsActivity;
import com.qunyu.taoduoduo.activity.RefundDetailsActivity;
import com.qunyu.taoduoduo.activity.WriteOrderNumActivity;
import com.qunyu.taoduoduo.base.BaseUtil;
import com.qunyu.taoduoduo.bean.AfterSaleListBean;

import java.util.ArrayList;

/**
 * Created by Administrator on 2016/10/10.
 */

public class AfterSaleAdapter extends BaseAdapter {
    private Context mc;
    private LayoutInflater layoutInflater;
    private ArrayList<AfterSaleListBean> mlist;
    private AfterSaleListBean bean;

    public AfterSaleAdapter(Context c, ArrayList<AfterSaleListBean> list){
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
            convertView=layoutInflater.inflate(R.layout.after_sale_listitem,null,false);
            holder.tv_status= (TextView) convertView.findViewById(R.id.tv_afterSale_status);
            holder.tv_payM= (TextView) convertView.findViewById(R.id.tv_payMoney);
            holder.tv_title= (TextView) convertView.findViewById(R.id.tv_shangpin_title);
            holder.iv_detail= (TextView) convertView.findViewById(R.id.iv_afterSale_details);
            holder.iv_logo= (ImageView) convertView.findViewById(R.id.iv_shangpin_logo);
            holder.iv_applyrefund= (ImageView) convertView.findViewById(R.id.iv_applyRefund);
            holder.iv_watchexpress= (ImageView) convertView.findViewById(R.id.iv_watchExpress);
            holder.iv_writeOrderNum= (ImageView) convertView.findViewById(R.id.iv_writeOrderId);
            holder.iv_detail2=(ImageView)convertView.findViewById(R.id.iv_afterSale_details2);
            convertView.setTag(holder);
        }else{
            holder= (ViewHolder) convertView.getTag();
        }
        //holder.tv_status.setText(bean.getRefundStatus());
        holder.tv_title.setText(bean.getProductName());
        holder.tv_payM.setText(Html.fromHtml("实付:￥<font color=\"#FF464E\">"+bean.getOrderPrice()+"</font>(免运费)"));
        Glide.with(mc).load(bean.getProductImage()).placeholder(R.mipmap.default_load).error(R.mipmap.default_load).into(holder.iv_logo);
        final int refundStatus=Integer.parseInt(bean.getRefundStatus());

        if(refundStatus==0){
            holder.tv_status.setText("待申请");
            holder.iv_applyrefund.setVisibility(View.GONE);
        }else if(refundStatus==1){
            holder.tv_status.setText("审核中");
            holder.iv_applyrefund.setVisibility(View.GONE);
            holder.iv_detail.setVisibility(View.GONE);
            holder.iv_watchexpress.setVisibility(View.GONE);
            holder.iv_writeOrderNum.setVisibility(View.GONE);
            holder.iv_detail2.setVisibility(View.VISIBLE);
        }else if(refundStatus==2){
            holder.tv_status.setText("审核通过，请退货");
            holder.iv_detail.setVisibility(View.VISIBLE);
            holder.iv_writeOrderNum.setVisibility(View.VISIBLE);
            holder.iv_applyrefund.setVisibility(View.GONE);
        }else if(refundStatus==3){
            holder.tv_status.setText("退货中");
            holder.iv_detail.setVisibility(View.VISIBLE);
            holder.iv_watchexpress.setVisibility(View.VISIBLE);
            holder.iv_applyrefund.setVisibility(View.GONE);
            holder.iv_writeOrderNum.setVisibility(View.GONE);
        }else if(refundStatus==4){
            holder.tv_status.setText("退货成功");
            holder.iv_applyrefund.setVisibility(View.GONE);
            holder.iv_detail.setVisibility(View.GONE);
            holder.iv_watchexpress.setVisibility(View.GONE);
            holder.iv_writeOrderNum.setVisibility(View.GONE);
            holder.iv_detail2.setVisibility(View.VISIBLE);
        }else if(refundStatus==5){
            holder.tv_status.setText("退货失败");
            holder.iv_applyrefund.setVisibility(View.GONE);
            holder.iv_detail.setVisibility(View.GONE);
            holder.iv_watchexpress.setVisibility(View.GONE);
            holder.iv_writeOrderNum.setVisibility(View.GONE);
            holder.iv_detail2.setVisibility(View.VISIBLE);
        }else if(refundStatus==6){
            holder.tv_status.setText("审核不通过");
            holder.iv_applyrefund.setVisibility(View.GONE);
            holder.iv_detail.setVisibility(View.GONE);
            holder.iv_watchexpress.setVisibility(View.GONE);
            holder.iv_writeOrderNum.setVisibility(View.GONE);
            holder.iv_detail2.setVisibility(View.VISIBLE);
        }else if(refundStatus==7){
            holder.tv_status.setText("退款成功");
            holder.iv_applyrefund.setVisibility(View.GONE);
            holder.iv_detail.setVisibility(View.GONE);
            holder.iv_watchexpress.setVisibility(View.GONE);
            holder.iv_writeOrderNum.setVisibility(View.GONE);
            holder.iv_detail2.setVisibility(View.VISIBLE);
        }
        holder.iv_applyrefund.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle b=new Bundle();
                b.putString("oid",mlist.get(position).getOrderId());
                b.putString("price",mlist.get(position).getOrderPrice());
                BaseUtil.ToAcb(mc, ApplyRefundActivity.class,b);
            }
        });
        holder.iv_watchexpress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle b=new Bundle();
                b.putString("orderId",mlist.get(position).getOrderId());
                b.putString("refundStatus",mlist.get(position).getRefundStatus());
                BaseUtil.ToAcb(mc, LogisticsActivity.class,b);

            }
        });
        holder.iv_writeOrderNum.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle b=new Bundle();
                b.putString("oid",mlist.get(position).getOrderId());
                BaseUtil.ToAcb(mc, WriteOrderNumActivity.class,b);
            }
        });
        holder.iv_detail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle b=new Bundle();
                b.putString("oid",mlist.get(position).getOrderId());
                BaseUtil.ToAcb(mc, RefundDetailsActivity.class,b);
            }
        });
        holder.iv_detail2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle b=new Bundle();
                b.putString("oid",mlist.get(position).getOrderId());
                BaseUtil.ToAcb(mc, RefundDetailsActivity.class,b);
            }
        });


        return convertView;
    }
    class ViewHolder{
        TextView tv_status,tv_title,tv_payM,iv_detail;
        ImageView iv_applyrefund,iv_watchexpress,iv_writeOrderNum,iv_logo,iv_detail2;
    }
}
