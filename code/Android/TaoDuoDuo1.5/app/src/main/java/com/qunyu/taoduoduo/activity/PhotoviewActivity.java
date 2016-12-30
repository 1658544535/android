package com.qunyu.taoduoduo.activity;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.qunyu.taoduoduo.R;
import com.qunyu.taoduoduo.bean.OpenGroupBean;
import com.qunyu.taoduoduo.widget.PhotoViewPager;
import com.umeng.analytics.MobclickAgent;

import java.util.List;

import uk.co.senab.photoview.PhotoView;
import uk.co.senab.photoview.PhotoViewAttacher;
import uk.co.senab.photoview.PhotoViewAttacher.OnViewTapListener;

import static com.qunyu.taoduoduo.R.id.vp_imageenlarge;

/**
 * 图片放大（可缩放）
 */
public class PhotoviewActivity extends Activity implements OnViewTapListener {
    private PhotoViewPager mViewPager;
    private PhotoView mPhotoView;
    private List<String> mImgUrls;
    private PhotoViewAdapter mAdapter;
    private PhotoViewAttacher mAttacher;
    private TextView tv_indicator;
    private List<OpenGroupBean.Banner> bannerImages;

    int mPosition;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.productimage_enlarge_activity);
        mPosition = getIntent().getIntExtra("ivposition", 0);
        bannerImages = (List<OpenGroupBean.Banner>) getIntent().getSerializableExtra("bannerImage");
        tv_indicator = (TextView) findViewById(R.id.tv_imageenlarge);
        setupView();
        setupData();
        onlistener();
    }

    private void setupView() {
        mViewPager = (PhotoViewPager) findViewById(vp_imageenlarge);
        // mPhotoView = (PhotoView) findViewById(R.id.photo);
    }
    public void onResume() {
        super.onResume();
        MobclickAgent.onResume(this);
    }
    public void onPause() {
        super.onPause();
        MobclickAgent.onPause(this);
    }
    private void setupData() {
        // int mCurrentUrl = getIntent().getIntExtra(MainActivity.PHOTO_POSITION,0);
        // mImgUrls = Arrays.asList(Images.imageThumbUrls);
        mAdapter = new PhotoViewAdapter();
        mViewPager.setAdapter(mAdapter);
        //设置当前需要显示的图片
        mViewPager.setCurrentItem(mPosition);
    }

    private void onlistener() {
        mViewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                tv_indicator.setText(position + 1 + "/" + bannerImages.size());
            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

    }

    @Override
    public void onViewTap(View view, float x, float y) {
        ActivityCompat.finishAfterTransition(this);
    }

    class PhotoViewAdapter extends PagerAdapter {

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            View view = container.inflate(PhotoviewActivity.this,
                    R.layout.photoview_item, null);
            mPhotoView = (PhotoView) view.findViewById(R.id.photoView);
            //使用ImageLoader加载图片
//            CSApplication.getInstance().imageLoader.displayImage(
//                    mImgUrls.get(position),mPhotoView, DisplayImageOptionsUtil.defaultOptions);
            Glide.with(PhotoviewActivity.this).load(bannerImages.get(position).bannerImage).into(mPhotoView);
            //给图片增加点击事件
            mAttacher = new PhotoViewAttacher(mPhotoView);
            mAttacher.setOnViewTapListener(PhotoviewActivity.this);
            container.addView(view);
            return view;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            mAttacher = null;
            container.removeView((View) object);
        }

        @Override
        public int getCount() {
            if (bannerImages == null) {
                return 0;
            } else {
                return bannerImages.size();
            }
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }
    }
}
