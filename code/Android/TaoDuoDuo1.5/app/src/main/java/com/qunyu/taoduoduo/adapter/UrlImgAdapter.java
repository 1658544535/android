package com.qunyu.taoduoduo.adapter;

/**
 * Created by Administrator on 2016/9/22.
 */

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;

import com.allure.lbanners.LMBanners;
import com.allure.lbanners.adapter.LBaseAdapter;
import com.bumptech.glide.Glide;
import com.qunyu.taoduoduo.R;
import com.qunyu.taoduoduo.activity.CaiGoodsDetailActivity;
import com.qunyu.taoduoduo.activity.CaiJiaGeActivity;
import com.qunyu.taoduoduo.activity.GoodsDetailActivity;
import com.qunyu.taoduoduo.activity.SpecialDetailActivity;
import com.qunyu.taoduoduo.activity.ZhuanTiFengLeiActivity;
import com.qunyu.taoduoduo.base.BaseUtil;
import com.qunyu.taoduoduo.bean.GroupHomeBean;
import com.qunyu.taoduoduo.utils.StringUtils;

/**
 * Created by luomin on 16/7/12.
 */
public class UrlImgAdapter implements LBaseAdapter<GroupHomeBean> {
    private Context mContext;

    public UrlImgAdapter(Context context) {
        mContext = context;
    }


    @Override
    public View getView(final LMBanners lBanners,  Context context, int position, final GroupHomeBean data) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.banner_item, null);
        ImageView imageView = (ImageView) view.findViewById(R.id.id_image);
        try {
            //此处经常在app关闭后报错
            Glide.with(context).load(data.getBanner()).placeholder(R.mipmap.default_load_long).error(R.mipmap.default_load_long).into(imageView);
        } catch (Exception e) {
            e.printStackTrace();
        }
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                        MainActivity.this.startActivity(new Intent(MainActivity.this,SeconedAc.class));
                if (data.getType().equals("0")) {
                    Intent intent = new Intent(mContext, GoodsDetailActivity.class);
                    intent.putExtra("activityId", data.getTypeId());
//                    intent.putExtra("pid", data.getProductId());
                    mContext.startActivity(intent);
                } else if (data.getType().equals("1")) {
                    Intent intent = new Intent(mContext, GoodsDetailActivity.class);
                    intent.putExtra("activityId", data.getTypeId());
                    intent.putExtra("pid", data.getProductId());
                    mContext.startActivity(intent);
                } else if (data.getType().equals("2")) {
                    Intent intent = new Intent(mContext, GoodsDetailActivity.class);
                    intent.putExtra("activityId", data.getTypeId());
                    intent.putExtra("pid", data.getProductId());
                    mContext.startActivity(intent);
                } else if (data.getType().equals("3")) {
                    if (StringUtils.isEmpty(data.getTypeId())) {
                        BaseUtil.ToAc(mContext, CaiJiaGeActivity.class);
                    } else {
                        Intent intent = new Intent(mContext, CaiGoodsDetailActivity.class);
                        intent.putExtra("activityId", data.getTypeId());
                        intent.putExtra("productId", data.getProductId());
                        mContext.startActivity(intent);
                    }
                } else if (data.getType().equals("4")) {
                    if (StringUtils.isEmpty(data.getTypeId())) {
                        BaseUtil.ToAc(mContext, ZhuanTiFengLeiActivity.class);
                    } else {
                        Bundle bundle = new Bundle();
                        bundle.putString("specialId", data.getTypeId());
                        BaseUtil.ToAcb(mContext, SpecialDetailActivity.class, bundle);
                    }
                } else if (data.getType().equals("5")) {
                    Bundle bundle = new Bundle();
                    bundle.putString("id", data.getTypeId());
                    BaseUtil.ToAcb(mContext, ZhuanTiFengLeiActivity.class, bundle);
                }

            }
        });
        return view;
    }


}
