package com.qunyu.taoduoduo.adapter;

import android.content.Context;
import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.qunyu.taoduoduo.R;
import com.qunyu.taoduoduo.bean.SellOutListBean;

import java.util.ArrayList;

/**
 * Created by Administrator on 2016/10/7.
 */

/**
 * Created by Administrator on 2016/10/26.
 * 售罄商品
 */
public class SellOutListAdapter extends BaseAdapter {
    private Context mc;
    private ArrayList<SellOutListBean> mlist;
    private SellOutListBean bean;
    private LayoutInflater layoutInflater;

    public SellOutListAdapter(Context c, ArrayList<SellOutListBean> list) {
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
        bean = mlist.get(position);
        ViewHolder holder = new ViewHolder();
        if (convertView == null) {
            convertView = layoutInflater.inflate(R.layout.selloutlist_item, parent, false);
            holder.iv_logo = (ImageView) convertView.findViewById(R.id.iv_selloutLogo);
            holder.iv_btn = (ImageView) convertView.findViewById(R.id.iv_selloutBtn);
            holder.tv_title = (TextView) convertView.findViewById(R.id.tv_selloutTitle);
            holder.tv_price = (TextView) convertView.findViewById(R.id.tv_selloutPrice);
            holder.tv_alonePrice = (TextView) convertView.findViewById(R.id.tv_selloutAlonePrice);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        Glide.with(mc).load(bean.getProductImage()).into(holder.iv_logo);
        holder.tv_title.setText(bean.getProductName());
        holder.tv_price.setText("￥" + bean.getProductPrice());
        holder.tv_alonePrice.setText("￥" + bean.getAlonePrice());
        holder.tv_alonePrice.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);
        return convertView;
    }

    private class ViewHolder {
        ImageView iv_logo, iv_btn;
        TextView tv_title, tv_price, tv_alonePrice;
    }

}
