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
import com.qunyu.taoduoduo.bean.HomeGroupProductBean;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2016/9/27.
 */

public class HomeGroupProductAdapter extends BaseAdapter {

    private Context mContext;
    //单行的布局
    private int mResource;
    //列表展现的数据
    private ArrayList<HomeGroupProductBean> mData;

    /**
     * 构造方法
     *
     * @param context
     * @param data    列表展现的数据
     */
    public HomeGroupProductAdapter(Context context, ArrayList<HomeGroupProductBean> data
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
            convertView = LayoutInflater.from(mContext).inflate(R.layout.fg_home_items, parent, false);
            //使用减少findView的次数
            holder = new ViewHolder(convertView);
            //设置标记
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        //设置数据
        final HomeGroupProductBean dataSet = mData.get(position);
        if (dataSet == null) {
            return null;
        }
        //获取该行数据
        //图片的下载
        Glide.with(mContext).load(dataSet.productImage).placeholder(R.mipmap.default_load_long).error(R.mipmap.default_load_long).into(holder.imageView);
        holder.tvGoodName.setText(dataSet.productName);
        holder.tvGoodXl.setText("销量：" + dataSet.proSellrNum);
        holder.tvRenshu.setText(dataSet.groupNum + "人团");
        holder.tvJiage.setText("￥" + dataSet.productPrice);
        holder.tvYjiage.setText("￥" + dataSet.alonePrice);
        holder.tvYjiage.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG | Paint.ANTI_ALIAS_FLAG);
        return convertView;
    }

    static class ViewHolder {
        @BindView(R.id.imageView)
        ImageView imageView;
        @BindView(R.id.tv_good_name)
        TextView tvGoodName;
        @BindView(R.id.tv_good_xl)
        TextView tvGoodXl;
        @BindView(R.id.tv_renshu)
        TextView tvRenshu;
        @BindView(R.id.tv_jiage)
        TextView tvJiage;
        @BindView(R.id.tv_yjiage)
        TextView tvYjiage;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}

