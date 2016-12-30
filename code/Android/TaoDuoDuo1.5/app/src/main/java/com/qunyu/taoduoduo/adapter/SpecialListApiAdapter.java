package com.qunyu.taoduoduo.adapter;

import android.content.Context;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.ForegroundColorSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.qunyu.taoduoduo.R;
import com.qunyu.taoduoduo.activity.GoodsDetailActivity;
import com.qunyu.taoduoduo.activity.SpecialDetailActivity;
import com.qunyu.taoduoduo.base.BaseUtil;
import com.qunyu.taoduoduo.bean.SpecialListBean;
import com.zhy.android.percent.support.PercentLinearLayout;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2016/9/27.
 */

public class SpecialListApiAdapter extends BaseAdapter {

    private Context mContext;
    //单行的布局
    private int mResource;
    //列表展现的数据
    private ArrayList<SpecialListBean> mData;

    /**
     * 构造方法
     *
     * @param context
     * @param data    列表展现的数据
     */
    public SpecialListApiAdapter(Context context, ArrayList<SpecialListBean> data
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
            convertView = LayoutInflater.from(mContext).inflate(R.layout.activity_zhuan_ti_feng_lei_items, parent, false);
            //使用减少findView的次数
            holder = new ViewHolder(convertView);
            //设置标记
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        //设置数据
        final SpecialListBean dataSet = mData.get(position);
        if (dataSet == null) {
            return null;
        }
        //获取该行数据
        //图片的下载
        Glide.with(mContext).load(dataSet.getSpecialImage()).into(holder.imageView);
        holder.imageView.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Do something in response to button click
                Bundle bundle = new Bundle();
                bundle.putString("specialId", dataSet.getSpecialId());
                BaseUtil.ToAcb(mContext, SpecialDetailActivity.class, bundle);
            }
        });
        for (int i = 0; i < dataSet.getProductList().size(); i++) {
            switch (i) {
                case 0:
                    holder.pl01.setVisibility(View.VISIBLE);
                    Glide.with(mContext).load(dataSet.getProductList().get(i).getProductImage()).into(holder.iv01);
                    holder.tv01.setText(dataSet.getProductList().get(i).getProductName());
                    SpannableStringBuilder style = new SpannableStringBuilder("￥" + dataSet.getProductList().get(i).getPrice() + "￥" + dataSet.getProductList().get(i).getAlonePrice());
                    style.setSpan(new ForegroundColorSpan(mContext.getResources().getColor(R.color.text_01)), 0, ("￥" + dataSet.getProductList().get(i).getPrice()).length(), Spannable.SPAN_EXCLUSIVE_INCLUSIVE);
                    holder.tv011.setText(style);
                    holder.pl01.setOnClickListener(new View.OnClickListener() {
                        public void onClick(View v) {
                            // Do something in response to button click
                            Bundle bundle = new Bundle();
                            bundle.putString("activityId", dataSet.getProductList().get(0).getActivityId() + "");
                            bundle.putString("pid", dataSet.getProductList().get(0).getProductId() + "");
                            BaseUtil.ToAcb(mContext, GoodsDetailActivity.class, bundle);
                        }
                    });
                    break;
                case 1:
                    holder.pl02.setVisibility(View.VISIBLE);
                    Glide.with(mContext).load(dataSet.getProductList().get(i).getProductImage()).into(holder.iv02);
                    holder.tv02.setText(dataSet.getProductList().get(i).getProductName());
                    SpannableStringBuilder style2 = new SpannableStringBuilder("￥" + dataSet.getProductList().get(i).getPrice() + "￥" + dataSet.getProductList().get(i).getAlonePrice());
                    style2.setSpan(new ForegroundColorSpan(mContext.getResources().getColor(R.color.text_01)), 0, ("￥" + dataSet.getProductList().get(i).getPrice()).length(), Spannable.SPAN_EXCLUSIVE_INCLUSIVE);
                    holder.tv021.setText(style2);
                    holder.pl02.setOnClickListener(new View.OnClickListener() {
                        public void onClick(View v) {
                            // Do something in response to button click
                            Bundle bundle = new Bundle();
                            bundle.putString("activityId", dataSet.getProductList().get(1).getActivityId() + "");
                            bundle.putString("pid", dataSet.getProductList().get(1).getProductId() + "");
                            BaseUtil.ToAcb(mContext, GoodsDetailActivity.class, bundle);
                        }
                    });
                    break;
                case 2:
                    holder.pl03.setVisibility(View.VISIBLE);
                    Glide.with(mContext).load(dataSet.getProductList().get(i).getProductImage()).into(holder.iv03);
                    holder.tv03.setText(dataSet.getProductList().get(i).getProductName());
                    SpannableStringBuilder style3 = new SpannableStringBuilder("￥" + dataSet.getProductList().get(i).getPrice() + "￥" + dataSet.getProductList().get(i).getAlonePrice());
                    style3.setSpan(new ForegroundColorSpan(mContext.getResources().getColor(R.color.text_01)), 0, ("￥" + dataSet.getProductList().get(i).getPrice()).length(), Spannable.SPAN_EXCLUSIVE_INCLUSIVE);
                    holder.tv031.setText(style3);
                    holder.pl03.setOnClickListener(new View.OnClickListener() {
                        public void onClick(View v) {
                            // Do something in response to button click
                            Bundle bundle = new Bundle();
                            bundle.putString("activityId", dataSet.getProductList().get(2).getActivityId() + "");
                            bundle.putString("pid", dataSet.getProductList().get(2).getProductId() + "");
                            BaseUtil.ToAcb(mContext, GoodsDetailActivity.class, bundle);
                        }
                    });
                    break;
                case 3:
                    holder.pl04.setVisibility(View.VISIBLE);
                    Glide.with(mContext).load(dataSet.getProductList().get(i).getProductImage()).into(holder.iv04);
                    holder.tv04.setText(dataSet.getProductList().get(i).getProductName());
                    SpannableStringBuilder style4 = new SpannableStringBuilder("￥" + dataSet.getProductList().get(i).getPrice() + "￥" + dataSet.getProductList().get(i).getAlonePrice());
                    style4.setSpan(new ForegroundColorSpan(mContext.getResources().getColor(R.color.text_01)), 0, ("￥" + dataSet.getProductList().get(i).getPrice()).length(), Spannable.SPAN_EXCLUSIVE_INCLUSIVE);
                    holder.tv041.setText(style4);
                    holder.pl04.setOnClickListener(new View.OnClickListener() {
                        public void onClick(View v) {
                            // Do something in response to button click
                            Bundle bundle = new Bundle();
                            bundle.putString("activityId", dataSet.getProductList().get(3).getActivityId() + "");
                            bundle.putString("pid", dataSet.getProductList().get(3).getProductId() + "");
                            BaseUtil.ToAcb(mContext, GoodsDetailActivity.class, bundle);
                        }
                    });
                    break;
                case 4:
                    holder.pl05.setVisibility(View.VISIBLE);
                    Glide.with(mContext).load(dataSet.getProductList().get(i).getProductImage()).into(holder.iv05);
                    holder.tv05.setText(dataSet.getProductList().get(i).getProductName());
                    SpannableStringBuilder style5 = new SpannableStringBuilder("￥" + dataSet.getProductList().get(i).getPrice() + "￥" + dataSet.getProductList().get(i).getAlonePrice());
                    style5.setSpan(new ForegroundColorSpan(mContext.getResources().getColor(R.color.text_01)), 0, ("￥" + dataSet.getProductList().get(i).getPrice()).length(), Spannable.SPAN_EXCLUSIVE_INCLUSIVE);
                    holder.tv051.setText(style5);
                    holder.pl05.setOnClickListener(new View.OnClickListener() {
                        public void onClick(View v) {
                            // Do something in response to button click
                            Bundle bundle = new Bundle();
                            bundle.putString("activityId", dataSet.getProductList().get(4).getActivityId() + "");
                            bundle.putString("pid", dataSet.getProductList().get(4).getProductId() + "");
                            BaseUtil.ToAcb(mContext, GoodsDetailActivity.class, bundle);
                        }
                    });

                    break;
                case 5:
                    holder.pl06.setVisibility(View.VISIBLE);
                    Glide.with(mContext).load(dataSet.getProductList().get(i).getProductImage()).into(holder.iv06);
                    holder.tv06.setText(dataSet.getProductList().get(i).getProductName());
                    SpannableStringBuilder style6 = new SpannableStringBuilder("￥" + dataSet.getProductList().get(i).getPrice() + "￥" + dataSet.getProductList().get(i).getAlonePrice());
                    style6.setSpan(new ForegroundColorSpan(mContext.getResources().getColor(R.color.text_01)), 0, ("￥" + dataSet.getProductList().get(i).getPrice()).length(), Spannable.SPAN_EXCLUSIVE_INCLUSIVE);
                    holder.tv061.setText(style6);
                    holder.pl06.setOnClickListener(new View.OnClickListener() {
                        public void onClick(View v) {
                            // Do something in response to button click
                            Bundle bundle = new Bundle();
                            bundle.putString("activityId", dataSet.getProductList().get(5).getActivityId() + "");
                            bundle.putString("pid", dataSet.getProductList().get(5).getProductId() + "");
                            BaseUtil.ToAcb(mContext, GoodsDetailActivity.class, bundle);
                        }
                    });
                    holder.pl07.setVisibility(View.VISIBLE);
                    holder.pl07.setOnClickListener(new View.OnClickListener() {
                        public void onClick(View v) {
                            // Do something in response to button click
                            Bundle bundle = new Bundle();
                            bundle.putString("specialId", dataSet.getSpecialId());
                            BaseUtil.ToAcb(mContext, SpecialDetailActivity.class, bundle);
                        }
                    });
                    break;
                case 6:

                    break;
            }
        }
        return convertView;
    }


    static class ViewHolder {
        @BindView(R.id.imageView)
        ImageView imageView;
        @BindView(R.id.iv_01)
        ImageView iv01;
        @BindView(R.id.tv_01)
        TextView tv01;
        @BindView(R.id.tv_011)
        TextView tv011;
        @BindView(R.id.pl_01)
        PercentLinearLayout pl01;
        @BindView(R.id.iv_02)
        ImageView iv02;
        @BindView(R.id.tv_02)
        TextView tv02;
        @BindView(R.id.tv_021)
        TextView tv021;
        @BindView(R.id.pl_02)
        PercentLinearLayout pl02;
        @BindView(R.id.iv_03)
        ImageView iv03;
        @BindView(R.id.tv_03)
        TextView tv03;
        @BindView(R.id.tv_031)
        TextView tv031;
        @BindView(R.id.pl_03)
        PercentLinearLayout pl03;
        @BindView(R.id.iv_04)
        ImageView iv04;
        @BindView(R.id.tv_04)
        TextView tv04;
        @BindView(R.id.tv_041)
        TextView tv041;
        @BindView(R.id.pl_04)
        PercentLinearLayout pl04;
        @BindView(R.id.iv_05)
        ImageView iv05;
        @BindView(R.id.tv_05)
        TextView tv05;
        @BindView(R.id.tv_051)
        TextView tv051;
        @BindView(R.id.pl_05)
        PercentLinearLayout pl05;
        @BindView(R.id.iv_06)
        ImageView iv06;
        @BindView(R.id.tv_06)
        TextView tv06;
        @BindView(R.id.tv_061)
        TextView tv061;
        @BindView(R.id.pl_06)
        PercentLinearLayout pl06;
        @BindView(R.id.pl_07)
        PercentLinearLayout pl07;
        @BindView(R.id.hs_1)
        HorizontalScrollView hs1;
        @BindView(R.id.activity_start)
        PercentLinearLayout activityStart;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}

