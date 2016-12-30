package com.qunyu.taoduoduo.activity;

import android.app.Activity;
import android.content.res.Resources;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.andbase.library.http.AbHttpUtil;
import com.andbase.library.http.listener.AbStringHttpResponseListener;
import com.andbase.library.http.model.AbRequestParams;
import com.andbase.library.http.model.AbResult;
import com.andbase.library.util.AbToastUtil;
import com.andbase.library.view.tabpager.AbTabPagerView;
import com.blankj.utilcode.utils.ScreenUtils;
import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.qunyu.taoduoduo.R;
import com.qunyu.taoduoduo.base.BaseActivity;
import com.qunyu.taoduoduo.base.BaseModel;
import com.qunyu.taoduoduo.base.BaseUtil;
import com.qunyu.taoduoduo.bean.CheckGroupFreeBean;
import com.qunyu.taoduoduo.bean.ProductTypeBean;
import com.qunyu.taoduoduo.fragment.HomeFragment;
import com.qunyu.taoduoduo.fragment.HomeTypeFragment;
import com.qunyu.taoduoduo.fragment.HomesFragment;
import com.qunyu.taoduoduo.global.AnyEventType;
import com.qunyu.taoduoduo.global.Constant;
import com.qunyu.taoduoduo.global.Untool;
import com.qunyu.taoduoduo.pulltorefresh.PullableGridView;
import com.umeng.analytics.MobclickAgent;
import com.zhy.android.percent.support.PercentLinearLayout;

import org.greenrobot.eventbus.Subscribe;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/11/23.
 */


/**
 * Tab框架
 *
 * @author toby
 */
public class TypeTabActivity extends BaseActivity {
    private TabLayout tabLayout;
    public static AbTabPagerView tabPagerView = null;
    String[] tabTexts;
    CheckGroupFreeBean date = null;
    String typeid, tt;
    ImageView xiala;
    View v6666;
    View mb;

    public void onCreate(Bundle savedInstanceState) {
        //setTheme(getResources().getInteger(R.style.AppTheme));
        super.onCreate(savedInstanceState);
        typeid = getIntent().getStringExtra("typeId");
        tt = getIntent().getStringExtra("tt");
        baseSetContentView(R.layout.act_home_tab_top);
        baseSetText(tt);
//        ProductTypeGet();
        mInflater = LayoutInflater.from(this);
        setTabtop(HomeFragment.list);
        initPopupWindow();
    }

    @Subscribe
    public void onEvent(AnyEventType event) {
        if (event.getMsg().equals("home")) {
            try {
                tabPagerView.getViewPager().setCurrentItem(0);
            } catch (Exception e) {
            }
        }
    }


    public void onPause() {
        super.onPause();
        MobclickAgent.onPause(this);
    }

    private void setTabtop(ArrayList<ProductTypeBean> date) {
        xiala = (ImageView) findViewById(R.id.xiala);
        v6666 = (View) findViewById(R.id.v6666);
        mb = (View) findViewById(R.id.mb);
        xiala.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPopupWindow();
            }
        });
        tabLayout = (TabLayout) findViewById(R.id.tab_layout);
        tabPagerView = (AbTabPagerView) findViewById(R.id.tabPagerView);
        mb.setVisibility(View.GONE);
        //缓存数量
        tabPagerView.getViewPager().setOffscreenPageLimit(15);
        List<Fragment> fragmentList = new ArrayList<Fragment>();
        HomeTypeFragment page1 = new HomeTypeFragment().newInstance(typeid, tt);
        fragmentList.add(page1);
        List<String> list = new ArrayList<String>();
        list.add("全部");
//        for (int i = 0; i < date.size(); i++) {
//            HomeTypeFragment page = new HomeTypeFragment().newInstance(date.get(i).oneId, date.get(i).oneName);
//            fragmentList.add(page);
//            list.add(date.get(i).oneName);
//        }
        try {
            for (int a = 0; a < date.size(); a++) {
                if (date.get(a).oneId.equals(typeid)) {
                    for (int b = 0; b < date.get(a).twoLevelList.size(); b++) {
    //                    fragmentList.add(NavigationsFragment.getIntence(Integer.parseInt(date.get(a).twoLevelList.get(b).twoId), 2));
                        HomeTypeFragment page = new HomeTypeFragment().newInstance(date.get(a).twoLevelList.get(b).twoId, date.get(a).twoLevelList.get(b).twoName);
                        fragmentList.add(page);
                        list.add(date.get(a).twoLevelList.get(b).twoName);
                    }
                } else {
                    for (int b = 0; b < date.get(a).twoLevelList.size(); b++) {
                        if (date.get(a).twoLevelList.get(b).twoId.equals(typeid)) {
                            for (int c = 0; c < date.get(a).twoLevelList.get(b).threeLevelList.size(); c++) {
    //                            fragmentList.add(NavigationsFragment.getIntence(Integer.parseInt(list.get(a).twoLevelList.get(b).threeLevelList.get(c).threeId), 3));
                                HomeTypeFragment page = new HomeTypeFragment().newInstance(date.get(a).twoLevelList.get(b).threeLevelList.get(c).threeId, date.get(a).twoLevelList.get(b).threeLevelList.get(c).threeName);
                                fragmentList.add(page);
                                list.add(date.get(a).twoLevelList.get(b).threeLevelList.get(c).threeName);
                            }
                        }
                    }
                }

            }
            tabTexts = list.toArray(new String[list.size()]);
            if (list.size() > 5) {
                xiala.setVisibility(View.VISIBLE);
            } else {
                xiala.setVisibility(View.GONE);
            }
            tabPagerView.setTabTextSize(12);
            tabPagerView.getTabLayout().setVisibility(View.GONE);
            int[] tabTextColors = new int[]{TypeTabActivity.this.getResources().getColor(R.color.text_02), TypeTabActivity.this.getResources().getColor(R.color.text_01)};
            tabPagerView.setTabTextColors(tabTextColors);
            tabPagerView.setTabBackgroundResource(android.R.color.white);
            tabPagerView.getTabLayout().setSelectedTabIndicatorColor(TypeTabActivity.this.getResources().getColor(R.color.text_01));
            tabPagerView.getTabLayout().setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, ScreenUtils.getScreenWidth(TypeTabActivity.this) / 10));
            tabPagerView.setTabs(tabTexts, fragmentList);
            tabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);
            tabLayout.setupWithViewPager(tabPagerView.getViewPager());
        } catch (Resources.NotFoundException e) {
            e.printStackTrace();
        }

    }

    /**
     * 添加getTabView的方法，来进行自定义Tab的布局View
     *
     * @param position
     * @return
     */
    public View getTabView(int position) {
        LayoutInflater mInflater = LayoutInflater.from(TypeTabActivity.this);
        View view = null;

        view = mInflater.inflate(R.layout.item_bottom_tab, null);
        TextView tv = (TextView) view.findViewById(R.id.textView);
        tv.setText(tabTexts[position]);
        ImageView img = (ImageView) view.findViewById(R.id.tab_icon);
        if (position == 0) {
//            img.setImageResource(icons_press[position]);
            tv.setTextColor(TypeTabActivity.this.getResources().getColor(R.color.text_01));
        } else {
//            img.setImageResource(icons[position]);
            tv.setTextColor(TypeTabActivity.this.getResources().getColor(R.color.text_02));
        }
        return view;
    }

    //    public void selectPager(int index) {
//        viewPager.setCurrentItem(index);
//    }
    PopupWindow popupWindow;
    View mPopupWindowView;

    /**
     * 显示popupwindow
     */
    private void showPopupWindow() {

        if (!popupWindow.isShowing()) {
            popupWindow.showAsDropDown(v6666, Gravity.TOP, 0, 0);
            mb.setVisibility(View.VISIBLE);
//            backgroundAlpha(TypeTabActivity.this, 0.5f);// 0.0-1.0
        } else {
            popupWindow.dismiss();
        }
    }


    /**
     * 初始化popupwindow
     */
    private void initPopupWindow() {
        initPopupWindowView();
        //初始化popupwindow，绑定显示view，设置该view的宽度/高度
        popupWindow = new PopupWindow(mPopupWindowView, LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        popupWindow.setFocusable(true);
        popupWindow.setOutsideTouchable(true);
        ColorDrawable dw = new ColorDrawable(0x00000000);
        // 设置SelectPicPopupWindow弹出窗体的背景
        popupWindow.setBackgroundDrawable(dw);
        // 这个是为了点击“返回Back”也能使其消失，并且并不会影响你的背景；使用该方法点击窗体之外，才可关闭窗体
//        popupWindow.setBackgroundDrawable(getResources().getDrawable(R.drawable.bitmap_book_read_chapterlist_repeat));
        //Background不能设置为null，dismiss会失效
//		popupWindow.setBackgroundDrawable(null);
        //设置渐入、渐出动画效果
//		popupWindow.setAnimationStyle(R.style.popupwindow);
        popupWindow.update();
        //popupWindow调用dismiss时触发，设置了setOutsideTouchable(true)，点击view之外/按键back的地方也会触发
        popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {

            @Override
            public void onDismiss() {
                // TODO Auto-generated method stub
                mb.setVisibility(View.GONE);
            }
        });
    }

    /**
     * 设置添加屏幕的背景透明度
     *
     * @param bgAlpha
     */
    public void backgroundAlpha(Activity context, float bgAlpha) {
        WindowManager.LayoutParams lp = context.getWindow().getAttributes();
        lp.alpha = bgAlpha;
        context.getWindow()
                .addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
        context.getWindow().setAttributes(lp);
    }

    /**
     * 初始化popupwindowView,监听view中的textview点击事件
     */
    private void initPopupWindowView() {

        mPopupWindowView = View.inflate(this,
                R.layout.act_tab_type, null);
        PullableGridView gv_t = (PullableGridView) mPopupWindowView.findViewById(R.id.gv_t);
        ImageView ss = (ImageView) mPopupWindowView.findViewById(R.id.ss);
        ss.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupWindow.dismiss();
            }
        });
        ad = new LvsvAdapte();
        gv_t.setAdapter(ad);
//        textview_edit.setOnClickListener(this);
//        TextView textview_file = (TextView) mPopupWindowView.findViewById(R.id.textview_file);
//        textview_file.setOnClickListener(this);
//        TextView textview_about = (TextView) mPopupWindowView.findViewById(R.id.textview_about);
//        textview_about.setOnClickListener(this);
    }

    LayoutInflater mInflater;
    LvsvAdapte ad;

    // 评论适配器
    public class LvsvAdapte extends BaseAdapter {

        @Override
        public int getCount() {
            return tabTexts.length;
        }

        @Override
        public Object getItem(int position) {

            return tabTexts[position];
        }

        @Override
        public long getItemId(int position) {

            return position;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {

            ViewHolder holder;
            if (convertView == null) {
                convertView = mInflater.inflate(
                        R.layout.act_tab_type_item, null);
                holder = new ViewHolder();
                holder.tv_user_name = (TextView) convertView
                        .findViewById(R.id.fxm);
                holder.line = (ImageView) convertView
                        .findViewById(R.id.line);
                convertView.setTag(holder);// 绑定ViewHolder对象
            } else {
                holder = (ViewHolder) convertView.getTag();
            }

            try {
                if (tabPagerView.getViewPager().getCurrentItem() == position) {
//                    holder.tv_user_name.setTextColor(getApplicationContext().getResources().getColor(R.color.base_red));
                    holder.line.setVisibility(View.VISIBLE);
                } else {
                    holder.tv_user_name.setTextColor(getApplicationContext().getResources().getColor(R.color.text_04));
                    holder.line.setVisibility(View.GONE);
                }
                if (tabTexts[position].length() > 4) {
                    holder.tv_user_name.setText(tabTexts[position].substring(0,4)+"...");
                } else {
                    holder.tv_user_name.setText(tabTexts[position]);
                }
                holder.tv_user_name.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        tabPagerView.getViewPager().setCurrentItem(position);
                        showPopupWindow();
                    }
                });
            } catch (Exception e) {
                e.printStackTrace();
            }

            return convertView;
        }

        public class ViewHolder {
            TextView tv_user_name, tv_user_comment;
            ImageView line;
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        MobclickAgent.onResume(this);
    }

}
