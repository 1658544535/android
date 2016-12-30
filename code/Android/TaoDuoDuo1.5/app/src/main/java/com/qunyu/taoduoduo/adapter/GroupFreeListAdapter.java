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
import com.qunyu.taoduoduo.bean.GroupFreeListBean;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2016/9/27.
 */

public class GroupFreeListAdapter extends BaseAdapter {

    private Context mContext;
    //单行的布局
    private int mResource;
    //列表展现的数据
    private ArrayList<GroupFreeListBean> mData;

    /**
     * 构造方法
     *
     * @param context
     * @param data    列表展现的数据
     */
    public GroupFreeListAdapter(Context context, ArrayList<GroupFreeListBean> data
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
        ViewHolder holder;
        if (convertView == null) {
            //使用自定义的list_items作为Layout
            convertView = LayoutInflater.from(mContext).inflate(R.layout.activity_tuan_mian_list_items, parent, false);
            //使用减少findView的次数
            holder = new ViewHolder(convertView);
            //设置标记
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        //设置数据
        final GroupFreeListBean dataSet = mData.get(position);
        if (dataSet == null) {
            return null;
        }
        //获取该行数据
        //图片的下载
        Glide.with(mContext).load(dataSet.getProductImage()).placeholder(R.mipmap.default_load).error(R.mipmap.default_load).into(holder.iv_banner);
        holder.tv_name.setText("          "+dataSet.getProductName());
        holder.tv_0Jiage.setText("￥0/件");
        holder.tv_ptjia.setText("￥" + dataSet.getProductPrice());
        holder.rs.setText(dataSet.getGroupNum() + "人团");
        holder.tv_ptjia.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG | Paint.ANTI_ALIAS_FLAG);
        return convertView;
    }

    static class ViewHolder {
        @BindView(R.id.imageView5)
        ImageView iv_banner;
        @BindView(R.id.tv_renshu)
        TextView tv_name;
        @BindView(R.id.tv_jiage)
        TextView tv_0Jiage;
        @BindView(R.id.textView6)
        TextView tv_ptjia;
        @BindView(R.id.textView7)
        TextView tv_kt;
        @BindView(R.id.rs)
        TextView rs;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}

