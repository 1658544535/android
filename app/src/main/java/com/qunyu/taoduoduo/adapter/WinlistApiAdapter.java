package com.qunyu.taoduoduo.adapter;

import android.content.Context;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.ForegroundColorSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.qunyu.taoduoduo.R;
import com.qunyu.taoduoduo.bean.WinListBean;
import com.qunyu.taoduoduo.view.CircleTransform;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2016/9/27.
 */

public class WinlistApiAdapter extends BaseAdapter {

    private Context mContext;
    //单行的布局
    private int mResource;
    //列表展现的数据
    private ArrayList<WinListBean.PrizeListBean> mData;
    DecimalFormat format;

    /**
     * 构造方法
     *
     * @param context
     * @param data    列表展现的数据
     */
    public WinlistApiAdapter(Context context, ArrayList<WinListBean.PrizeListBean> data
    ) {
        mContext = context;
        mData = data;
        format = new DecimalFormat("0.0");
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
            convertView = LayoutInflater.from(mContext).inflate(R.layout.activity_ping_tuan_jie_guo_chen_yuan_list_item_cy, parent, false);
            //使用减少findView的次数
            holder = new ViewHolder(convertView);
            //设置标记
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        //设置数据
        final WinListBean.PrizeListBean dataSet = mData.get(position);
        if (dataSet == null) {
            return null;
        }
        //获取该行数据
        //图片的下载
        Glide.with(mContext).load(dataSet.getUserImage()).transform(new CircleTransform(mContext)).error(R.mipmap.default_touxiang).into(holder.ivLogo);
        SpannableStringBuilder style1 = new SpannableStringBuilder("出价：￥" + format.format(new BigDecimal(dataSet.getUserPrice())));
        style1.setSpan(new ForegroundColorSpan(mContext.getResources().getColor(R.color.text_01)), 3, 4 + format.format(new BigDecimal(dataSet.getUserPrice())).length(), Spannable.SPAN_EXCLUSIVE_INCLUSIVE);
        holder.tvLogo.setText(dataSet.getUserName());
        holder.tvLogo1.setText(style1);
        holder.tvLogoRs.setText(dataSet.getJoinTime());
        return convertView;
    }

    static class ViewHolder {
        @BindView(R.id.iv_logo)
        ImageView ivLogo;
        @BindView(R.id.tv_logo)
        TextView tvLogo;
        @BindView(R.id.tv_logo1)
        TextView tvLogo1;
        @BindView(R.id.tv_logo_rs)
        TextView tvLogoRs;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}

