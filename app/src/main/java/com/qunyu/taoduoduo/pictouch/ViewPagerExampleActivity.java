package com.qunyu.taoduoduo.pictouch;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.qunyu.taoduoduo.R;
import com.qunyu.taoduoduo.activity.PinLunActivity;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;


public class ViewPagerExampleActivity extends Activity {

    @BindView(R.id.btn_head_left)
    ImageView btnHeadLeft;
    @BindView(R.id.text_head_title)
    TextView textHeadTitle;
    @BindView(R.id.collect)
    ImageView collect;
    @BindView(R.id.cancel_collect)
    ImageView cancelCollect;
    @BindView(R.id.view_pager)
    ExtendedViewPager viewPager;
    @BindView(R.id.tv_num_tag)
    TextView tvNumTag;
    @BindView(R.id.loPageTurningPoint)
    LinearLayout loPageTurningPoint;
    /**
     * Step 1: Download and set up v4 support library:
     * http://developer.android.com/tools/support-library/setup.html Step 2:
     * Create ExtendedViewPager wrapper which calls
     * TouchImageView.canScrollHorizontallyFroyo Step 3: ExtendedViewPager is a
     * custom view and must be referred to by its full package name in XML Step
     * 4: Write TouchImageAdapter, located below Step 5. The ViewPager in the
     * XML should be ExtendedViewPager
     */
    private ArrayList<ImageView> mPointViews = new ArrayList<ImageView>();
    // @ViewInject(R.id.rl_ll)
    // private RelativeLayout rl_ll;
    @SuppressWarnings("unused")
    public static int page, list;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_viewpager_example);
        ButterKnife.bind(this);
        Intent intent = getIntent();
        page = intent.getIntExtra("page", 0);// 第一个参数是取值的key,第二个参数是默认值
        page = intent.getIntExtra("list", 0);// 第一个参数是取值的key,第二个参数是默认值
        ExtendedViewPager mViewPager = (ExtendedViewPager) findViewById(R.id.view_pager);
        mViewPager.setAdapter(new TouchImageAdapter());

        // Toast.makeText(getApplication(), "点击了第" + page + "个",
        // Toast.LENGTH_SHORT).show();

        tvNumTag.setText(page + 1 + "/"
                + PinLunActivity.date.getUserInfo().get(list).getCommentImage().size());
        btnHeadLeft.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // do something
                onBackPressed();
            }
        });
        for (int count = 0; count < PinLunActivity.date.getUserInfo().get(list).getCommentImage().size(); count++) {
            // 翻页指示的点
            ImageView pointView = new ImageView(this);
            pointView.setPadding(5, 0, 5, 0);

            pointView.setImageResource(R.drawable.dian2);
            mPointViews.add(pointView);

            loPageTurningPoint.addView(pointView);

        }
        mPointViews.get(page).setImageResource(R.drawable.dianshi);
        mViewPager.setCurrentItem(page);
        mViewPager.setOnPageChangeListener(new OnPageChangeListener() {

            @Override
            public void onPageSelected(int arg0) {
                tvNumTag.setText(arg0 + 1 + "/"
                        + PinLunActivity.date.getUserInfo().get(list).getCommentImage().size());
                for (int count = 0; count < PinLunActivity.date.getUserInfo().get(list).getCommentImage()
                        .size(); count++) {
                    // 翻页指示的点

                    mPointViews.get(count).setImageResource(R.drawable.dian2);
                }
                mPointViews.get(arg0).setImageResource(R.drawable.dianshi);
                page = arg0;
            }

            @Override
            public void onPageScrolled(int arg0, float arg1, int arg2) {
                // TODO Auto-generated method stub

            }

            @Override
            public void onPageScrollStateChanged(int arg0) {
                // TODO Auto-generated method stub

            }
        });
    }

    static class TouchImageAdapter extends PagerAdapter {

        // private static int[] images = { R.drawable.nature_1,
        // R.drawable.nature_2, R.drawable.nature_3, R.drawable.nature_4,
        // R.drawable.nature_5 };

        @Override
        public int getCount() {
            return PinLunActivity.date.getUserInfo().get(list).getCommentImage().size();
        }

        @Override
        public View instantiateItem(final ViewGroup container, int position) {
            TouchImageView img = new TouchImageView(container.getContext());
//            bitmapUtils.display(img,
//                    ProductDetailFragmentTop.list.get(position).image);
            Glide.with(container.getContext()).load(PinLunActivity.date.getUserInfo().get(list).getCommentImage().get(position).getImage()).placeholder(R.mipmap.default_load).error(R.mipmap.default_load).into(img);
            // img.setImageResource(images[position]);
            container.addView(img, LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.MATCH_PARENT);
            img.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    // do something

                    ((Activity) container.getContext()).finish();
                }
            });
            return img;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

    }
}
