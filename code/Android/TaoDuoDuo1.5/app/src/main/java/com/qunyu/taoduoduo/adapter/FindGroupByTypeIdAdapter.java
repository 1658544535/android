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
import com.qunyu.taoduoduo.bean.FindGroupByTypeIdBean;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2016/9/27.
 */

public class FindGroupByTypeIdAdapter extends BaseAdapter {

    private Context mContext;
    //单行的布局
    private int mResource;
    //列表展现的数据
    private ArrayList<FindGroupByTypeIdBean> mData;

    /**
     * 构造方法
     *
     * @param context
     * @param data    列表展现的数据
     */
    public FindGroupByTypeIdAdapter(Context context, ArrayList<FindGroupByTypeIdBean> data
    ) {
        mContext = context;
        mData = data;
    }

    @Override
    public int getCount() {
        return mData.size();
    }

    @Override
    public Object getItem(int position) {
        return mData.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final ViewHolder holder;
        if (convertView == null) {
            //使用自定义的list_items作为Layout
            convertView = LayoutInflater.from(mContext).inflate(R.layout.fg_goods_items, parent, false);
            //使用减少findView的次数
            holder = new ViewHolder(convertView);
            //设置标记
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        //设置数据
        final FindGroupByTypeIdBean dataSet = mData.get(position);
        if (dataSet == null) {
            return null;
        }
        //获取该行数据
        //图片的下载
        Glide.with(mContext).load(dataSet.getProductImage()).placeholder(R.mipmap.default_load).error(R.mipmap.default_load).into(holder.ivBanner);
        holder.tvName.setText("          " + dataSet.getProductName());
        holder.tvGoodXl.setText("已团" + dataSet.getProSellrNum() + "件");
        holder.TvRen.setText(dataSet.getGroupNum() + "人团");
        holder.tvRenshu.setText("￥" + dataSet.getProductPrice());
        return convertView;
    }

    static class ViewHolder {
        @BindView(R.id.imageView3)
        ImageView ivBanner;
        @BindView(R.id.textView6)
        TextView TvRen;
        @BindView(R.id.textView7)
        TextView tvName;
        @BindView(R.id.tv_renshu)
        TextView tvRenshu;
        @BindView(R.id.tv_good_xl)
        TextView tvGoodXl;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}

