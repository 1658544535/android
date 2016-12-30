//package com.qunyu.taoduoduo.activity;
//
//import android.app.Activity;
//import android.os.Bundle;
//import android.support.v4.view.PagerAdapter;
//import android.support.v4.view.ViewPager;
//import android.view.View;
//import android.view.ViewGroup;
//import android.view.WindowManager;
//import android.widget.ImageView;
//import android.widget.TextView;
//
//import com.bumptech.glide.Glide;
//import com.qunyu.taoduoduo.R;
//import com.qunyu.taoduoduo.utils.UserInfoUtils;
//
//import java.util.ArrayList;
//import java.util.List;
//
//import butterknife.BindView;
//import butterknife.ButterKnife;
//import uk.co.senab.photoview.PhotoView;
//import uk.co.senab.photoview.PhotoViewAttacher;
//
///**
// * Created by Administrator on 2016/11/15.
// * 图片详情图放大(不可缩放,需要缩放换成PhotoviewActivity类)
// */
//
//public class ProductImageEnlargelActivity extends Activity implements PhotoViewAttacher.OnViewTapListener {
//    private ImageView iv_enlarge, iv_enlarge1, iv_enlarge2;
//    private PhotoView[] iv_enlarges;
//    @BindView(R.id.vp_imageenlarge)
//    ViewPager vp_imageenlarge;
//    @BindView(R.id.tv_imageenlarge)
//    TextView tv_indicator;
//
//    private List<View> viewList;
//    private int imagesize = 0;
//    private String[] imageUrl;
//    private int currentPosition = 0;
//    private ImageView[] imagegroups;
//    ImageView img;
//    View view;
//
//    private PhotoViewAttacher attacher;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.productimage_enlarge_activity);
//        ButterKnife.bind(this);
//        viewList = new ArrayList<>();
//        currentPosition = getIntent().getIntExtra("ivposition", 0);
////        setBgTransluction();//设置背景透明度
//        setVp();//设置Viewpager
//        vp_imageenlarge.setCurrentItem(currentPosition);
//        onlistener();//Viewpager滑动监听
//
//    }
//
//    private void onlistener() {
//        vp_imageenlarge.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
//            @Override
//            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
//                tv_indicator.setText(position + 1 + "/" + imagesize);
//            }
//
//            @Override
//            public void onPageSelected(int position) {
//
//            }
//
//            @Override
//            public void onPageScrollStateChanged(int state) {
//
//            }
//        });
//
//    }
//
//    private void setVp() {
//        //获取图片数组
//        imagesize = UserInfoUtils.bannerImage.size();
//        imageUrl = new String[imagesize];
////
//        for (int i = 0; i < imagesize; i++) {
//            imageUrl[i] = UserInfoUtils.bannerImage.get(i).bannerImage;
//        }
//        MyPagerAdapter adapter = new MyPagerAdapter();
//        vp_imageenlarge.setAdapter(adapter);
//    }
//
//    private void setBgTransluction() {
//        WindowManager.LayoutParams winLP = getWindow().getAttributes();
//        winLP.alpha = 0.95f;
//        getWindow().setAttributes(winLP);
//    }
//
//    //避免放大失真
////    private void displayImage() {
////        //想让图片宽是屏幕的宽度
////        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.yangyang);
////        //测量
////        BitmapFactory.Options options = new BitmapFactory.Options();
////        options.inJustDecodeBounds=true;//只测量
////        int height = bitmap.getHeight();
////        int width = bitmap.getWidth();
////        //再拿到屏幕的宽
////        WindowManager windowManager = getWindowManager();
////        Display display = windowManager.getDefaultDisplay();
////        int screenWidth = display.getWidth();
////        //计算如果让照片是屏幕的宽，选要乘以多少？
////        scale = screenWidth/width;
////        //这个时候。只需让图片的宽是屏幕的宽，高乘以比例
////        int  displayHeight=height*scale;//要显示的高，这样避免失真
////        //最终让图片按照宽是屏幕 高是等比例缩放的大小
////        layoutParams = new LinearLayout.LayoutParams(screenWidth, displayHeight);
////    }
//
//    @Override
//    public void onViewTap(View view, float x, float y) {
//        finish();
//    }
//
//    class MyPagerAdapter extends PagerAdapter {
//
//        @Override
//        public int getCount() {
//            return imagesize;
//        }
//
//        @Override
//        public boolean isViewFromObject(View view, Object object) {
//            return view == object;
//        }
//
//        @Override
//        public void destroyItem(ViewGroup container, int position, Object object) {
////            container.removeView(viewList.get(position%viewList.size()));
//            // super.destroyItem(container, position, object);
//            container.removeView((View) object);
//            attacher = null;
//        }
//
//        @Override
//        public Object instantiateItem(ViewGroup container, int position) {
//            view = container.inflate(ProductImageEnlargelActivity.this, R.layout.photoview_item, null);
////            ViewGroup parents = (ViewGroup) view.getParent();
////            if (parents != null) {
////                parents.removeView(view);
////            }
//            ImageView photoView = (ImageView) view.findViewById(R.id.photoView);
//            Glide.with(ProductImageEnlargelActivity.this).load(imageUrl[position]).placeholder(R.mipmap.default_load_long).error(R.mipmap.default_load_long).into(photoView);
//            photoView.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    onBackPressed();
//                }
//            });
////  attacher = new PhotoViewAttacher(photoView);
////            attacher.setOnViewTapListener(ProductImageEnlargelActivity.this);//设置图片的点击事件
//            container.addView(view);
//            return view;
//        }
//    }
//}
