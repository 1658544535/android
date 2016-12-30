package com.qunyu.taoduoduo.adapter;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Paint;
import android.text.TextPaint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.akexorcist.roundcornerprogressbar.RoundCornerProgressBar;
import com.bumptech.glide.Glide;
import com.qunyu.taoduoduo.R;
import com.qunyu.taoduoduo.bean.SecKillListsApiBean;
import com.qunyu.taoduoduo.utils.LogUtil;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2016/9/27.
 */

public class SecKillListAdapter extends BaseAdapter {

    private Context mContext;
    //单行的布局
    private int mResource;
    //列表展现的数据
    private ArrayList<SecKillListsApiBean> mData;
    private LayoutInflater mInflater;

    /**
     * 构造方法
     *
     * @param context
     * @param data    列表展现的数据
     */
    public SecKillListAdapter(Context context, ArrayList<SecKillListsApiBean> data) {
        mContext = context;
        mData = data;
        if(context!=null){
            mInflater = (LayoutInflater) context.getSystemService(mContext.LAYOUT_INFLATER_SERVICE);
        }

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
    public int getViewTypeCount() {
        return 5;
    }

    @Override
    public int getItemViewType(int position) {
        int i = 0;
        switch (mData.get(position).type) {
            case "1":
                i = 1;
                break;
            case "2":
                i = 2;
                break;
            case "3":
                i = 3;
                break;
            default:
                i = 4;
                break;
        }
        return i;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LogUtil.Log("+");
        ViewHolder1 holder1 = null;
        ViewHolder2 holder2 = null;
        ViewHolder3 holder3 = null;
        int type = getItemViewType(position);
        if (convertView == null) {
            switch (type) {
                case 1:
                    LogUtil.Log("1+");
                    convertView = mInflater.inflate(R.layout.fragment_jrmiao_h, parent, false);
                    //使用减少findView的次数
                    holder1 = new ViewHolder1(convertView);
                    //设置标记
                    convertView.setTag(holder1);
                    break;
                case 2:
                    LogUtil.Log("1+");
                    convertView = mInflater.inflate(R.layout.fragment_jrmiao_h, parent, false);
                    //使用减少findView的次数
                    holder1 = new ViewHolder1(convertView);
                    //设置标记
                    convertView.setTag(holder1);
                    break;
                case 3:
                    LogUtil.Log("3+");
                    convertView = mInflater.inflate(R.layout.fragment_jrmiao, parent, false);
                    //使用减少findView的次数
                    holder2 = new ViewHolder2(convertView);
                    //设置标记
                    convertView.setTag(holder2);
                    break;
                default:
                    LogUtil.Log("4+");
                    convertView = mInflater.inflate(R.layout.fragment_jrmiao_wks, parent, false);
                    //使用减少findView的次数
                    holder3 = new ViewHolder3(convertView);
                    //设置标记
                    convertView.setTag(holder3);
                    break;
            }
        } else {

            switch (type) {
                case 1:
                case 2:
                    holder1 = (ViewHolder1) convertView.getTag();
                    break;
                case 3:
                    holder2 = (ViewHolder2) convertView.getTag();
                    break;
                default:
                    holder3 = (ViewHolder3) convertView.getTag();
                    break;
            }
        }
        //设置数据
        final SecKillListsApiBean dataSet = mData.get(position);
        if (dataSet == null) {
            return null;
        }

        switch (type) {
            case 1:
                LogUtil.Log("1");
                holder1.tvZt.setText(dataSet.getTime() + " 正在进行中");
                TextPaint tp = holder1.tvZt.getPaint();
                tp.setFakeBoldText(true);
                holder1.tvZt.setTextColor(mContext.getResources().getColor(R.color.text_01));
                holder1.imageView3.setBackground(mContext.getResources().getDrawable(R.mipmap.dd_ms_ks));
                if (dataSet.getActivityId().equals("-1")) {
                    holder1.tvSw.setVisibility(View.VISIBLE);
                } else {
                    holder1.tvSw.setVisibility(View.GONE);
                }
                if (position == 0) {
                    holder1.vv.setVisibility(View.GONE);
                } else {
                    holder1.vv.setVisibility(View.VISIBLE);
                }
                break;
            case 2:
                LogUtil.Log("2");
                holder1.tvZt.setText(dataSet.getTime());
                holder1.tvZt.setTextColor(mContext.getResources().getColor(R.color.text_05));
                TextPaint tp1 = holder1.tvZt.getPaint();
                tp1.setFakeBoldText(false);
                holder1.imageView3.setBackground(mContext.getResources().getDrawable(R.mipmap.dd_ms_wks));
                if (dataSet.getActivityId().equals("-1")) {
                    holder1.tvSw.setVisibility(View.VISIBLE);
                } else {
                    holder1.tvSw.setVisibility(View.GONE);
                }
                if (position == 0) {
                    holder1.vv.setVisibility(View.GONE);
                } else {
                    holder1.vv.setVisibility(View.VISIBLE);
                }
                break;
            case 3:
                LogUtil.Log("3");
                Glide.with(mContext).load(dataSet.getProductImage()).placeholder(R.mipmap.default_load).error(R.mipmap.default_load).into(holder2.imageView3);
                holder2.tvZt.setText(dataSet.getProductName());
                if (dataSet.getIsSellOut().equals("1")) {
                    holder2.imageView4.setBackground(mContext.getResources().getDrawable(R.mipmap.dd_ma_ysw));
                } else {
                    holder2.imageView4.setBackground(mContext.getResources().getDrawable(R.mipmap.dd_ms_qg));
                }
                holder2.tvSw.setText("￥" + dataSet.getProductPrice());
                holder2.tvSw1.setText("￥" + dataSet.getAlonePrice());
                holder2.tvSw1.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);
                try {
                    if (dataSet.getKillId().equals(mData.get(position + 1).getKillId())) {
                        holder2.imageView5.setVisibility(View.VISIBLE);
                    } else {
                        holder2.imageView5.setVisibility(View.GONE);
                    }
                } catch (Exception e) {
                    holder2.imageView5.setVisibility(View.GONE);
                }
                try {
                    holder2.progress_1.setProgressColor(Color.parseColor("#FF6B71"));
                    holder2.progress_1.setProgressBackgroundColor(Color.parseColor("#FFB4AD"));
                    holder2.progress_1.setMax(100);
                    holder2.progress_1.setProgress(Float.parseFloat(dataSet.getSalePerce()));
                    holder2.progress_2.setProgressColor(Color.parseColor("#ed3b27"));
                    holder2.progress_2.setProgressBackgroundColor(Color.parseColor("#FF464E"));
                    holder2.tv_1.setText(dataSet.getSalePerce() + "%");
                } catch (Exception e) {
                }
                break;
            default:
                LogUtil.Log("4");
                Glide.with(mContext).load(dataSet.getProductImage()).placeholder(R.mipmap.default_load).error(R.mipmap.default_load).into(holder3.imageView3);
                holder3.tvZt.setText(dataSet.getProductName());
                holder3.tvSw2.setText("限量：" + dataSet.getLimitNum());
                holder3.tvSw.setText("￥" + dataSet.getProductPrice());
                holder3.tvSw1.setText("￥" + dataSet.getAlonePrice());
                holder3.tvSw1.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);
                try {
                    if (dataSet.getKillId().equals(mData.get(position + 1).getKillId())) {
                        holder3.imageView5.setVisibility(View.VISIBLE);
                    } else {
                        holder3.imageView5.setVisibility(View.GONE);
                    }
                } catch (Exception e) {
                    holder3.imageView5.setVisibility(View.GONE);
                }
                break;
        }
        return convertView;
    }

    static class ViewHolder1 {
        @BindView(R.id.imageView3)
        ImageView imageView3;
        @BindView(R.id.tv_zt)
        TextView tvZt;
        @BindView(R.id.tv_sw)
        TextView tvSw;
        @BindView(R.id.vv)
        View vv;

        ViewHolder1(View view) {
            ButterKnife.bind(this, view);
        }
    }

    static class ViewHolder2 {
        @BindView(R.id.imageView3)
        ImageView imageView3;
        @BindView(R.id.tv_zt)
        TextView tvZt;
        @BindView(R.id.tv_sw)
        TextView tvSw;
        @BindView(R.id.tv_sw1)
        TextView tvSw1;
        @BindView(R.id.tv_1)
        TextView tv_1;
        @BindView(R.id.imageView4)
        ImageView imageView4;
        @BindView(R.id.imageView5)
        ImageView imageView5;
        @BindView(R.id.progress_1)
        RoundCornerProgressBar progress_1;
        @BindView(R.id.progress_2)
        RoundCornerProgressBar progress_2;

        ViewHolder2(View view) {
            ButterKnife.bind(this, view);
        }
    }

    static class ViewHolder3 {
        @BindView(R.id.imageView3)
        ImageView imageView3;
        @BindView(R.id.tv_zt)
        TextView tvZt;
        @BindView(R.id.tv_sw)
        TextView tvSw;
        @BindView(R.id.tv_sw1)
        TextView tvSw1;
        @BindView(R.id.tv_sw2)
        TextView tvSw2;
        @BindView(R.id.imageView4)
        ImageView imageView4;
        @BindView(R.id.imageView5)
        ImageView imageView5;

        ViewHolder3(View view) {
            ButterKnife.bind(this, view);
        }
    }
}

