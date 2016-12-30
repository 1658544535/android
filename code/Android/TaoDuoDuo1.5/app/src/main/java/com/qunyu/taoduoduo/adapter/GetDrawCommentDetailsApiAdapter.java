package com.qunyu.taoduoduo.adapter;

import android.content.Context;
import android.content.Intent;
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
import com.qunyu.taoduoduo.bean.GetDrawCommentDetailsBean;
import com.qunyu.taoduoduo.bean.GuessActivityBean;
import com.qunyu.taoduoduo.pictouch.ViewPagerExampleActivity;
import com.zhy.android.percent.support.PercentLinearLayout;

import java.text.DecimalFormat;
import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2016/9/27.
 */

public class GetDrawCommentDetailsApiAdapter extends BaseAdapter {

    private Context mContext;
    //单行的布局
    private int mResource;
    //列表展现的数据
    private ArrayList<GetDrawCommentDetailsBean.UserInfoBean> mData;


    /**
     * 构造方法
     *
     * @param context
     * @param data    列表展现的数据
     */
    public GetDrawCommentDetailsApiAdapter(Context context, ArrayList<GetDrawCommentDetailsBean.UserInfoBean> data) {
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
    public View getView(final int position, View convertView, ViewGroup parent) {
        final ViewHolder holder;
        if (convertView == null) {
            //使用自定义的list_items作为Layout
            convertView = LayoutInflater.from(mContext).inflate(R.layout.activity_pin_lun_item, parent, false);
            //使用减少findView的次数
            holder = new ViewHolder(convertView);
            //设置标记
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        //设置数据
        final GetDrawCommentDetailsBean.UserInfoBean dataSet = mData.get(position);
        if (dataSet == null) {
            return null;
        }
        //获取该行数据
        //图片的下载
        Glide.with(mContext).load(dataSet.getUserImage()).placeholder(R.mipmap.default_load).error(R.mipmap.default_load).into(holder.ivLogo);
        holder.tvLogo.setText(dataSet.getUserName());
        holder.tvLogoRs.setText(dataSet.getCommentTime());
        holder.tvC.setText(dataSet.getCommentText());
        holder.hs1.setVisibility(View.GONE);
        holder.pl02.setVisibility(View.GONE);
        holder.pl03.setVisibility(View.GONE);
        holder.pl04.setVisibility(View.GONE);
        holder.pl05.setVisibility(View.GONE);
        holder.pl06.setVisibility(View.GONE);
        holder.pl07.setVisibility(View.GONE);
        holder.pl08.setVisibility(View.GONE);
        holder.pl09.setVisibility(View.GONE);
        holder.pl01.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setClass(mContext,
                        ViewPagerExampleActivity.class);
                intent.putExtra("page", 0);
                intent.putExtra("list", position);
                mContext.startActivity(intent);
            }
        });
        holder.pl02.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setClass(mContext,
                        ViewPagerExampleActivity.class);
                intent.putExtra("page", 1);
                intent.putExtra("list", position);
                mContext.startActivity(intent);
            }
        });
        holder.pl03.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setClass(mContext,
                        ViewPagerExampleActivity.class);
                intent.putExtra("page", 2);
                intent.putExtra("list", position);
                mContext.startActivity(intent);
            }
        });
        holder.pl04.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setClass(mContext,
                        ViewPagerExampleActivity.class);
                intent.putExtra("page", 3);
                intent.putExtra("list", position);
                mContext.startActivity(intent);
            }
        });
        holder.pl05.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setClass(mContext,
                        ViewPagerExampleActivity.class);
                intent.putExtra("page", 4);
                intent.putExtra("list", position);
                mContext.startActivity(intent);
            }
        });
        holder.pl06.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setClass(mContext,
                        ViewPagerExampleActivity.class);
                intent.putExtra("page", 5);
                intent.putExtra("list", position);
                mContext.startActivity(intent);
            }
        });
        holder.pl07.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setClass(mContext,
                        ViewPagerExampleActivity.class);
                intent.putExtra("page", 6);
                intent.putExtra("list", position);
                mContext.startActivity(intent);
            }
        });
        holder.pl08.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setClass(mContext,
                        ViewPagerExampleActivity.class);
                intent.putExtra("page", 7);
                intent.putExtra("list", position);
                mContext.startActivity(intent);
            }
        });
        holder.pl09.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setClass(mContext,
                        ViewPagerExampleActivity.class);
                intent.putExtra("page", 8);
                intent.putExtra("list", position);
                mContext.startActivity(intent);
            }
        });
        for (int i = 0; i < dataSet.getCommentImage().size(); i++) {
            if (i == 0) {
                holder.hs1.setVisibility(View.VISIBLE);
                Glide.with(mContext).load(dataSet.getCommentImage().get(i).getImage()).placeholder(R.mipmap.default_load).error(R.mipmap.default_load).into(holder.iv01);
            } else if (i == 1) {
                holder.pl02.setVisibility(View.VISIBLE);
                Glide.with(mContext).load(dataSet.getCommentImage().get(i).getImage()).placeholder(R.mipmap.default_load).error(R.mipmap.default_load).into(holder.iv02);
            } else if (i == 2) {
                holder.pl03.setVisibility(View.VISIBLE);
                Glide.with(mContext).load(dataSet.getCommentImage().get(i).getImage()).placeholder(R.mipmap.default_load).error(R.mipmap.default_load).into(holder.iv03);
            } else if (i == 3) {
                holder.pl04.setVisibility(View.VISIBLE);
                Glide.with(mContext).load(dataSet.getCommentImage().get(i).getImage()).placeholder(R.mipmap.default_load).error(R.mipmap.default_load).into(holder.iv04);
            } else if (i == 4) {
                holder.pl05.setVisibility(View.VISIBLE);
                Glide.with(mContext).load(dataSet.getCommentImage().get(i).getImage()).placeholder(R.mipmap.default_load).error(R.mipmap.default_load).into(holder.iv05);
            } else if (i == 5) {
                holder.pl06.setVisibility(View.VISIBLE);
                Glide.with(mContext).load(dataSet.getCommentImage().get(i).getImage()).placeholder(R.mipmap.default_load).error(R.mipmap.default_load).into(holder.iv06);
            } else if (i == 6) {
                holder.pl07.setVisibility(View.VISIBLE);
                Glide.with(mContext).load(dataSet.getCommentImage().get(i).getImage()).placeholder(R.mipmap.default_load).error(R.mipmap.default_load).into(holder.iv07);
            } else if (i == 7) {
                holder.pl08.setVisibility(View.VISIBLE);
                Glide.with(mContext).load(dataSet.getCommentImage().get(i).getImage()).placeholder(R.mipmap.default_load).error(R.mipmap.default_load).into(holder.iv08);
            } else if (i == 8) {
                holder.pl09.setVisibility(View.VISIBLE);
                Glide.with(mContext).load(dataSet.getCommentImage().get(i).getImage()).placeholder(R.mipmap.default_load).error(R.mipmap.default_load).into(holder.iv09);
            }
        }
        return convertView;
    }

    static class ViewHolder {
        @BindView(R.id.iv_logo)
        ImageView ivLogo;
        @BindView(R.id.tv_logo)
        TextView tvLogo;
        @BindView(R.id.tv_logo_rs)
        TextView tvLogoRs;
        @BindView(R.id.tv_c)
        TextView tvC;
        @BindView(R.id.iv_01)
        ImageView iv01;
        @BindView(R.id.pl_01)
        PercentLinearLayout pl01;
        @BindView(R.id.iv_02)
        ImageView iv02;
        @BindView(R.id.pl_02)
        PercentLinearLayout pl02;
        @BindView(R.id.iv_03)
        ImageView iv03;
        @BindView(R.id.pl_03)
        PercentLinearLayout pl03;
        @BindView(R.id.iv_04)
        ImageView iv04;
        @BindView(R.id.pl_04)
        PercentLinearLayout pl04;
        @BindView(R.id.iv_05)
        ImageView iv05;
        @BindView(R.id.pl_05)
        PercentLinearLayout pl05;
        @BindView(R.id.iv_06)
        ImageView iv06;
        @BindView(R.id.pl_06)
        PercentLinearLayout pl06;
        @BindView(R.id.iv_07)
        ImageView iv07;
        @BindView(R.id.pl_07)
        PercentLinearLayout pl07;
        @BindView(R.id.iv_08)
        ImageView iv08;
        @BindView(R.id.pl_08)
        PercentLinearLayout pl08;
        @BindView(R.id.iv_09)
        ImageView iv09;
        @BindView(R.id.pl_09)
        PercentLinearLayout pl09;
        @BindView(R.id.hs_1)
        HorizontalScrollView hs1;
        @BindView(R.id.activity_start)
        PercentLinearLayout activityStart;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}

