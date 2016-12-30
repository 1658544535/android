package com.qunyu.taoduoduo.adapter;

/**
 * Created by Administrator on 2016/9/22.
 */

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;

import com.allure.lbanners.LMBanners;
import com.allure.lbanners.adapter.LBaseAdapter;
import com.bumptech.glide.Glide;
import com.qunyu.taoduoduo.R;
import com.qunyu.taoduoduo.activity.PhotoviewActivity;
import com.qunyu.taoduoduo.bean.OpenGroupBean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by luomin on 16/7/12.
 */
public class GoodsBannarAdapter implements LBaseAdapter<OpenGroupBean.Banner> {
    private Context mContext;
    private List<OpenGroupBean.Banner> bannerImage;

    public GoodsBannarAdapter(Context context, List<OpenGroupBean.Banner> bannerImage) {
        mContext = context;
        this.bannerImage = bannerImage;
    }


    @Override
    public View getView(final LMBanners lBanners, final Context context, final int position, OpenGroupBean.Banner data) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.goods_banner_item, null);
        final ImageView imageView = (ImageView) view.findViewById(R.id.id_image);
        Glide.with(mContext).load(data.bannerImage).placeholder(R.mipmap.default_load_long).error(R.mipmap.default_load_long).into(imageView);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, PhotoviewActivity.class);//图片点击放大
                Bundle b = new Bundle();
                b.putInt("ivposition", position);
                b.putSerializable("bannerImage", (Serializable) bannerImage);
                intent.putExtras(b);
                //Activity过度的动画效果
                ActivityOptionsCompat compat = ActivityOptionsCompat.makeScaleUpAnimation(imageView, imageView.getWidth() / 2, imageView.getHeight() / 2, 0, 0);
                ActivityCompat.startActivity((Activity) mContext, intent, compat.toBundle());
//                mContext.startActivity(intent);
            }
        });

        return view;
    }


}
