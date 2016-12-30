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
import com.qunyu.taoduoduo.bean.SearchAllProductBean;

import java.util.ArrayList;

/**
 * Created by Administrator on 2016/10/7.
 */

public class SearchProductAdapter extends BaseAdapter {
    private Context mc;
    private ArrayList<SearchAllProductBean.productss> mlist;
    private SearchAllProductBean.productss bean;
    private LayoutInflater layoutInflater;
    public SearchProductAdapter(Context c,ArrayList<SearchAllProductBean.productss> list){
        this.mc=c;
        this.mlist=list;
        layoutInflater=LayoutInflater.from(mc);
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
        bean=mlist.get(position);
        ViewHolder holder=new ViewHolder();
        if(convertView==null){
            convertView=layoutInflater.inflate(R.layout.search_griditem,null,false);
            holder.iv_productImage= (ImageView) convertView.findViewById(R.id.iv_productLogo);
            holder.tv_attendNum= (TextView) convertView.findViewById(R.id.tv_productedNum);
            holder.tv_groupNum= (TextView) convertView.findViewById(R.id.tv_groupNum);
            holder.tv_productName= (TextView) convertView.findViewById(R.id.tv_productMsg);
            holder.tv_productPrice= (TextView) convertView.findViewById(R.id.tv_productPrice);
            convertView.setTag(holder);
        }else{
            holder= (ViewHolder) convertView.getTag();
        }
        Glide.with(mc).load(bean.getProductImage()).placeholder(R.mipmap.default_load).error(R.mipmap.default_load).into(holder.iv_productImage);
        holder.tv_groupNum.setText(bean.getGroupNum()+"人团");
        holder.tv_productName.setText("            "+bean.getProductName());
        holder.tv_productPrice.setText("￥"+bean.getProductPrice());
        holder.tv_attendNum.setText("已团"+bean.getproSellrNum()+"件");



        return convertView;
    }
    private class ViewHolder{
        ImageView iv_productImage;
        TextView tv_attendNum,tv_groupNum,tv_productName,tv_productPrice;
    }

}
