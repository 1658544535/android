package com.qunyu.taoduoduo.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.qunyu.taoduoduo.R;
import com.qunyu.taoduoduo.bean.ProductTypeBean;
import com.zhy.android.percent.support.PercentRelativeLayout;

import java.util.ArrayList;

/**
 * Created by Administrator on 2016/11/14.
 */

public class ProductCategorylv1Adapter extends BaseAdapter {
    private LayoutInflater layoutInflater;
    private Context mc;
    private ArrayList<ProductTypeBean> mlist;
    public static int selectposition = 0;

    public ProductCategorylv1Adapter(Context c, ArrayList<ProductTypeBean> list) {
        this.mc = c;
        this.mlist = list;
        layoutInflater = LayoutInflater.from(mc);
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
        ViewHolder mholder = new ViewHolder();
        if (convertView == null) {
            convertView = layoutInflater.inflate(R.layout.productcategory_lv1item, null);
            mholder.tv_name = (TextView) convertView.findViewById(R.id.lv1_name);
            mholder.lv1_layout = (PercentRelativeLayout) convertView.findViewById(R.id.lv1_layout);
            convertView.setTag(mholder);
        } else {
            mholder = (ViewHolder) convertView.getTag();
        }
        mholder.tv_name.setText(mlist.get(position).oneName);
//        Glide.with(mc).load(mlist.get(position).oneIcon).into(mholder.iv_icon);

        if (selectposition == position) {
            mholder.lv1_layout.setBackgroundResource(R.color.white);
            mholder.tv_name.setTextColor(mc.getResources().getColor(R.color.red));
        } else {
            mholder.lv1_layout.setBackgroundResource(R.color.my_bg);
            mholder.tv_name.setTextColor(mc.getResources().getColor(R.color.classification_t));
        }
        return convertView;
    }

    class ViewHolder {
        TextView tv_name;
        PercentRelativeLayout lv1_layout;
    }

}
