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
import com.qunyu.taoduoduo.bean.GuessYourLikeBean;
import com.qunyu.taoduoduo.mvpview.GoodsDetailView;

import java.util.List;

/**
 * Created by Administrator on 2016/9/27.
 */

public class PinLunSuGuessYourLikeAdapter extends BaseAdapter {

    private Context mContext;
    //单行的布局
    private int mResource;
    //列表展现的数据
    private List<GuessYourLikeBean> mData;

    /**
     * 构造方法
     *
     * @param context
     * @param data    列表展现的数据
     */
    public PinLunSuGuessYourLikeAdapter(Context context, List<GuessYourLikeBean> data
    ) {
        mContext = context;
        mData = data;
        //view = goodsDetailView;
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
        ViewHolder holder;
        if (convertView == null) {
            //使用自定义的list_items作为Layout
            convertView = LayoutInflater.from(mContext).inflate(R.layout.activity_goods_detail_guessyourlike_item, parent, false);
            //使用减少findView的次数
            holder = new ViewHolder();
            holder.iv_picture = (ImageView) convertView.findViewById(R.id.iv_picture);
            holder.tv_productName = (TextView) convertView.findViewById(R.id.tv_productName);
            holder.tv_price = (TextView) convertView.findViewById(R.id.tv_price);
            holder.iv_collect = (ImageView) convertView.findViewById(R.id.iv_collect);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        final GuessYourLikeBean guessYourLikeBean = mData.get(position);
        holder.tv_productName.setText(guessYourLikeBean.productName);
        holder.tv_price.setText("￥" + guessYourLikeBean.price);
        try {
            Glide.with(mContext).load(guessYourLikeBean.productImage).placeholder(R.mipmap.default_load).error(R.mipmap.default_load).into(holder.iv_picture);
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (guessYourLikeBean.isCollect.equals("1")) {
            holder.iv_collect.setImageResource(R.mipmap.cnxh_scfull);
        } else {
            holder.iv_collect.setImageResource(R.mipmap.cnxh_sc);
        }
        holder.iv_collect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (guessYourLikeBean.isCollect.equals("1")) {
                   // view.delguessFavorite(guessYourLikeBean.activityId, guessYourLikeBean.productId, position);
                } else {
                    //view.addguessFavorite(guessYourLikeBean.activityId, guessYourLikeBean.productId, position);
                }

            }
        });

        return convertView;
    }

    class ViewHolder {
        ImageView iv_picture;
        TextView tv_productName;
        TextView tv_price;
        ImageView iv_collect;
    }
}

