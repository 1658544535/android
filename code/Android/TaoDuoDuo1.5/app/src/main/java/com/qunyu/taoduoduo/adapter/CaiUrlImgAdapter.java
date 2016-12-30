package com.qunyu.taoduoduo.adapter;

/**
 * Created by Administrator on 2016/9/22.
 */

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;

import com.allure.lbanners.LMBanners;
import com.allure.lbanners.adapter.LBaseAdapter;
import com.bumptech.glide.Glide;
import com.qunyu.taoduoduo.R;
import com.qunyu.taoduoduo.bean.ProductFocusImagsBean;

/**
 * Created by luomin on 16/7/12.
 */
public class CaiUrlImgAdapter implements LBaseAdapter<ProductFocusImagsBean> {
    private Context mContext;

    public CaiUrlImgAdapter(Context context) {
        mContext = context;
    }


    @Override
    public View getView(final LMBanners lBanners, final Context context, int position, ProductFocusImagsBean data) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.banner_item, null);
        ImageView imageView = (ImageView) view.findViewById(R.id.id_image);
        Glide.with(mContext).load(data.getImage()).into(imageView);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            }
        });
        return view;
    }


}
