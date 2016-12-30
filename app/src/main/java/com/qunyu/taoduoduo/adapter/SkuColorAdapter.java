package com.qunyu.taoduoduo.adapter;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.qunyu.taoduoduo.R;
import com.qunyu.taoduoduo.activity.GoodsDetailActivity;
import com.qunyu.taoduoduo.bean.ProductSkuBean;

import java.util.List;

/**
 * Created by Administrator on 2016/9/27.
 */

public class SkuColorAdapter extends BaseAdapter {

    private Context mContext;

    private List<ProductSkuBean.SkuValue> mData;
    public int selectPosition = -1;

    /**
     * 构造方法
     *
     * @param context
     * @param data    列表展现的数据
     */
    public SkuColorAdapter(Context context, List<ProductSkuBean.SkuValue> data
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
        ViewHolder holder = new ViewHolder();
        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.activity_goods_detail_sku_pop_item, null, false);
            holder.btn_skuvalue = (TextView) convertView.findViewById(R.id.tv_skuvalue);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        ProductSkuBean.SkuValue skuValue = mData.get(position);
        holder.btn_skuvalue.setText(skuValue.optionValue);
        //LogUtil.Log(skuValue.optionValue+":"+skuValue.optionValue.length());
        if (position == selectPosition) {
            //选中
            holder.btn_skuvalue.setBackgroundResource(R.drawable.sku_red_radius);
            holder.btn_skuvalue.setTextColor(ContextCompat.getColor(mContext, R.color.white));
        } else {
            //未选中
            holder.btn_skuvalue.setBackgroundResource(R.drawable.sku_shap_corner_gray);
            holder.btn_skuvalue.setTextColor(ContextCompat.getColor(mContext, R.color.black));
        }


        return convertView;
    }

    class ViewHolder {
        TextView btn_skuvalue;
    }
}

