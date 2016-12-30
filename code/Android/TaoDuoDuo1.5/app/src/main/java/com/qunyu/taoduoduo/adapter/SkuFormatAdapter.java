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
import com.qunyu.taoduoduo.presenter.SkuPresenter;
import com.qunyu.taoduoduo.utils.LogUtil;
import com.qunyu.taoduoduo.utils.StringUtils;

import java.util.List;

/**
 * Created by Administrator on 2016/9/27.
 */

public class SkuFormatAdapter extends BaseAdapter {

    private Context mContext;

    private List<ProductSkuBean.SkuValue> mData;

    public int selectPosition = -1;

    SkuPresenter presenter;

    /**
     * 构造方法
     *
     * @param context
     * @param data    列表展现的数据
     */
    public SkuFormatAdapter(Context context, List<ProductSkuBean.SkuValue> data, SkuPresenter skuPresenter
    ) {
        mContext = context;
        mData = data;
        presenter = skuPresenter;
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
        //LogUtil.Log("selectPositionformat:"+selectPosition);
        ProductSkuBean.SkuValue skuValue = mData.get(position);
        holder.btn_skuvalue.setText(skuValue.optionValue);
        if (presenter.skuColor == null) {
            if (position == selectPosition) {
                //选中
                holder.btn_skuvalue.setBackgroundResource(R.drawable.sku_red_radius);
                holder.btn_skuvalue.setTextColor(ContextCompat.getColor(mContext, R.color.white));
            } else {
                //未选中
                holder.btn_skuvalue.setBackgroundResource(R.drawable.sku_shap_corner_gray);
                holder.btn_skuvalue.setTextColor(ContextCompat.getColor(mContext, R.color.black));
            }
        } else {
            skuValue.clickable = presenter.forValidSKuClickable(presenter.skuColor, skuValue.optionValue);
            if (skuValue.clickable == 0) {
                //可选
                if (position == selectPosition) {
                    //选中
                    holder.btn_skuvalue.setBackgroundResource(R.drawable.sku_red_radius);
                    holder.btn_skuvalue.setTextColor(ContextCompat.getColor(mContext, R.color.white));
                } else {
                    //未选中
                    holder.btn_skuvalue.setBackgroundResource(R.drawable.sku_shap_corner_gray);
                    holder.btn_skuvalue.setTextColor(ContextCompat.getColor(mContext, R.color.black));
                }
            } else {
                //不可选
                holder.btn_skuvalue.setBackgroundResource(R.drawable.sku_shap_corner_gray_dash);
                holder.btn_skuvalue.setTextColor(ContextCompat.getColor(mContext, R.color.line_03));
                if (StringUtils.isNotBlank(presenter.skuFormat) && presenter.skuFormat.equals(skuValue.optionValue)) {
                    //清空
                    presenter.skuFormat = null;
                    selectPosition = -1;
                }

            }
        }

        return convertView;
    }

    class ViewHolder {
        TextView btn_skuvalue;
    }
}

