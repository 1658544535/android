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
import com.qunyu.taoduoduo.activity.TypeTabActivity;
import com.qunyu.taoduoduo.base.BaseUtil;
import com.qunyu.taoduoduo.bean.ProductTypeBean;
import com.qunyu.taoduoduo.utils.LogUtil;

import java.util.List;

/**
 * Created by Administrator on 2016/10/7.
 */

/**
 * Created by Administrator on 2016/11/10.
 */
public class SjldAdapter extends BaseAdapter {
    private Context mc;
    private List<ProductTypeBean.TwoLevelListBean> mlist;
    private ProductTypeBean.TwoLevelListBean bean;
    private LayoutInflater layoutInflater;

    public SjldAdapter(Context c, List<ProductTypeBean.TwoLevelListBean> list) {
        this.mc = c;
        this.mlist = list;
        layoutInflater = LayoutInflater.from(mc);
    }

    @Override
    public int getCount() {
        return mlist.size() > 12 ? 12 : mlist.size();
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
            convertView = layoutInflater.inflate(R.layout.productcategory_gv1item, null);
            holder.iv1 = (ImageView) convertView.findViewById(R.id.iv_ProductCategorylogo);
            holder.tv1 = (TextView) convertView.findViewById(R.id.tv_ProductCategoryname);
            convertView.setTag(holder);

        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        Glide.with(mc).load(bean.twoIcon).placeholder(R.mipmap.default_load).into(holder.iv1);
        holder.tv1.setText(bean.twoName);
        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LogUtil.Log("gvitem=====" + position);
                Bundle bundle = new Bundle();
                bundle.putString("tt", mlist.get(position).twoName);
                bundle.putString("typeId", mlist.get(position).twoId);
                BaseUtil.ToAcb(mc, TypeTabActivity.class, bundle);
            }
        });


        return convertView;
    }

    private class ViewHolder {
        ImageView iv1;
        TextView tv1;
    }

}
