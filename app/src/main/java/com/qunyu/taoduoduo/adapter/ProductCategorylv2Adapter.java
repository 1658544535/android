package com.qunyu.taoduoduo.adapter;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.qunyu.taoduoduo.R;
import com.qunyu.taoduoduo.activity.TypeTabActivity;
import com.qunyu.taoduoduo.base.BaseUtil;
import com.qunyu.taoduoduo.bean.ProductTypeBean;
import com.qunyu.taoduoduo.utils.LogUtil;
import com.qunyu.taoduoduo.widget.NoScrollGridView;

import java.util.ArrayList;

/**
 * Created by Administrator on 2016/10/7.
 */

public class ProductCategorylv2Adapter extends BaseAdapter {
    private Context mc;
    private ArrayList<ProductTypeBean> mlist;
    private ProductTypeBean bean;
    private LayoutInflater layoutInflater;
    private SjldAdapter sjldAdapter;
    public static Integer mposition;

    public ProductCategorylv2Adapter(Context c, ArrayList<ProductTypeBean> list) {
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
    public View getView(final int position, View convertView, ViewGroup parent) {
        bean = mlist.get(position);
        ViewHolder holder = new ViewHolder();
        if (convertView == null) {
            convertView = layoutInflater.inflate(R.layout.productcategory_lv2item, null);
            holder.tv_name = (TextView) convertView.findViewById(R.id.tv_name);
            holder.ngv = (NoScrollGridView) convertView.findViewById(R.id.ngv1);
            holder.tv_seeMore = (TextView) convertView.findViewById(R.id.tv_seeMore);
            holder.lastview = convertView.findViewById(R.id.lastview);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.tv_name.setText(bean.oneName);
        sjldAdapter = new SjldAdapter(mc, bean.twoLevelList);
        holder.ngv.setAdapter(sjldAdapter);
        LogUtil.Log("xygv======" + holder.ngv.getScrollX() + "/" + holder.ngv.getScrollY());
//            if (mposition != null && position == mposition) {
//                holder.lastview.setVisibility(View.VISIBLE);
//                mposition = null;
//            }else{
//                holder.lastview.setVisibility(View.GONE);
//            }
        if (position == mlist.size() - 1) {
            holder.lastview.setVisibility(View.VISIBLE);
        } else {
            holder.lastview.setVisibility(View.GONE);
        }
        holder.tv_seeMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putString("tt", mlist.get(position).oneName);
                bundle.putString("typeId", mlist.get(position).oneId);
                Log.d("item", "position====" + position);
                BaseUtil.ToAcb(mc, TypeTabActivity.class, bundle);
            }
        });
        return convertView;
    }

    private class ViewHolder {
        TextView tv_name, tv_seeMore;
        NoScrollGridView ngv;
        View lastview;
    }

}
