package com.qunyu.taoduoduo.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.andbase.library.app.base.AbBaseFragment;
import com.qunyu.taoduoduo.R;

/**
 * 引导页面
 */
public class GuideFragment extends AbBaseFragment {

    ImageView iv_guide;
    int imagesrc;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View v = inflater.inflate(R.layout.fragment_guide, null);
        iv_guide = (ImageView) v.findViewById(R.id.iv_guide);
        if(getArguments()!=null){
            imagesrc = getArguments().getInt("imagesrc");
            iv_guide.setImageResource(imagesrc);
        }
        return v;
    }


    @Override
    public void onResume() {
        super.onResume();
    }

}
