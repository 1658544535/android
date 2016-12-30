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
import com.qunyu.taoduoduo.bean.SpecialDetailBean;

import java.util.List;

/**
 * Created by Administrator on 2016/10/10.
 */

public class SpecialDetailAdapter extends BaseAdapter {
    private Context mc;
    private LayoutInflater layoutInflater;
    private List<SpecialDetailBean> mlist;

    public SpecialDetailAdapter(Context c, List<SpecialDetailBean> list){
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
    public View getView( int position, View convertView, ViewGroup parent) {
        ViewHolder holder=new ViewHolder();
        if(convertView==null){
            convertView=layoutInflater.inflate(R.layout.activity_special_detail_item,null,false);
            holder.tv_productName= (TextView) convertView.findViewById(R.id.tv_productName);
            holder.tv_tnum= (TextView) convertView.findViewById(R.id.tv_tnum);
            holder.tv_price= (TextView) convertView.findViewById(R.id.tv_price);
            holder.tv_grouponNum= (TextView) convertView.findViewById(R.id.tv_grouponNum);
            holder.iv_picture= (ImageView) convertView.findViewById(R.id.iv_picture);
            convertView.setTag(holder);
        }else{
            holder= (ViewHolder) convertView.getTag();
        }
        SpecialDetailBean specialDetailBean = mlist.get(position);
        holder.tv_price.setText("￥"+specialDetailBean.price);
        holder.tv_productName.setText("           "+specialDetailBean.productName);
        holder.tv_tnum.setText(specialDetailBean.num+"人团");
        holder.tv_grouponNum.setText("已团"+specialDetailBean.grouponNum+"件");
        Glide.with(mc).load(specialDetailBean.productImage).placeholder(R.mipmap.default_load).error(R.mipmap.default_load).into(holder.iv_picture);
        return convertView;
    }
    class ViewHolder{
        TextView tv_productName,tv_tnum,tv_price,tv_grouponNum;
        ImageView iv_picture;
    }
}
