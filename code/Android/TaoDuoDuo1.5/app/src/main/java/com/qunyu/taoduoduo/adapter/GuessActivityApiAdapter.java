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
import com.qunyu.taoduoduo.bean.GuessActivityBean;
import com.zhy.android.percent.support.PercentRelativeLayout;

import java.text.DecimalFormat;
import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2016/9/27.
 */

public class GuessActivityApiAdapter extends BaseAdapter {

    private Context mContext;
    //单行的布局
    private int mResource;
    //列表展现的数据
    private ArrayList<GuessActivityBean> mData;

    /**
     * 构造方法
     *
     * @param context
     * @param data    列表展现的数据
     */
    public GuessActivityApiAdapter(Context context, ArrayList<GuessActivityBean> data
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
            convertView = LayoutInflater.from(mContext).inflate(R.layout.fragment_cai_jia_ge_list_items, parent, false);
            //使用减少findView的次数
            holder = new ViewHolder(convertView);
            //设置标记
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        //设置数据
        final GuessActivityBean dataSet = mData.get(position);
        if (dataSet == null) {
            return null;
        }
        //获取该行数据
        //图片的下载
        Glide.with(mContext).load(dataSet.getProductImage()).placeholder(R.mipmap.default_load_long).error(R.mipmap.default_load_long).into(holder.ivBanner);
        holder.tvname.setText(dataSet.getProductName());
        holder. rl_time.setVisibility(View.VISIBLE);
        DecimalFormat format = new DecimalFormat("0");
        String str = "提示区间： " + dataSet.getMinPrice() + "-" + dataSet.getMaxPrice() + "   |   已有" + dataSet.getJoinNum() + "人参与";
//        if (dataSet.getHour().length() > 2) {
//            holder.tvtime.setText(" " + dataSet.getHour() + "  _   " + dataSet.getMin() + "       " + dataSet.getSs());
            holder.tv_hh.setText(dataSet.getHour());
            holder.tv_mm.setText(dataSet.getMin());
            holder.tv_ss.setText(dataSet.getSs());
//        } else {
//            holder.tvtime.setText("  " + dataSet.getHour() + "  _    " + dataSet.getMin() + "       " + dataSet.getSs());
//        }

        SpannableStringBuilder style = new SpannableStringBuilder(str);
        style.setSpan(new ForegroundColorSpan(mContext.getResources().getColor(R.color.text_01)), str.length() - 3 - (dataSet.getJoinNum() + "").length(), str.length() - 3, Spannable.SPAN_EXCLUSIVE_INCLUSIVE);
        holder.tvJiage.setText(style);
        return convertView;
    }

    static class ViewHolder {
        @BindView(R.id.imageView5)
        ImageView ivBanner;
        @BindView(R.id.tv_renshu)
        TextView tvname;
        @BindView(R.id.textView1)
        TextView tvJiage;
//        @BindView(R.id.textView8)
//        TextView tvtime;
        @BindView(R.id.rl_time)
        PercentRelativeLayout rl_time;
        @BindView(R.id.tv_hh)
        TextView tv_hh;
        @BindView(R.id.tv_ss)
        TextView tv_ss;
        @BindView(R.id.tv_mm)
        TextView tv_mm;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}

