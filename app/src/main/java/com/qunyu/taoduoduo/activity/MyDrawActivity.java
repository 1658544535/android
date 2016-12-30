//package com.qunyu.taoduoduo.activity;
//
//import android.os.Bundle;
//import android.support.v4.app.Fragment;
//import android.support.v4.app.FragmentManager;
//import android.support.v4.app.FragmentTransaction;
//import android.view.View;
//import android.widget.TextView;
//
//import com.qunyu.taoduoduo.R;
//import com.qunyu.taoduoduo.base.BaseActivity;
//import com.qunyu.taoduoduo.fragment.MyDrawFragment;
//import com.umeng.analytics.MobclickAgent;
//
//import butterknife.BindView;
//import butterknife.ButterKnife;
//import butterknife.OnClick;
//
///**
// * Created by Administrator on 2016/10/27.
// * 我的抽奖
// */
//
//public class MyDrawActivity extends BaseActivity implements View.OnClickListener {
//
//
//    @BindView(R.id.tv_lottery)
//    TextView tv_lottery;
//    @BindView(R.id.tv_freelottery)
//    TextView tv_freelottery;
//    MyDrawFragment f;
//    MyDrawFragment f2;
////    FragmentTransaction transaction;
//
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        baseSetContentView(R.layout.my_draw_activity);
//        ButterKnife.bind(this);
//
//        onsetFragment1();
//        onsetFragment2();
//
//        tv_lottery.performClick();
//    }
//
//    @Override
//    protected void onResume() {
//        super.onResume();
//        MobclickAgent.onResume(this);
//    }
//
//    public void onPause() {
//        super.onPause();
//        MobclickAgent.onPause(this);
//    }
//
//    @Override
//    protected void init() {
//        super.init();
//        baseSetText("我的抽奖");
//    }
//
//    private void onsetFragment1() {
//        f = new MyDrawFragment();
//        Bundle b = new Bundle();
//        b.putString("type", "1");
//        f.setArguments(b);
//    }
//
//    private void onsetFragment2() {
//        f2 = new MyDrawFragment();
//        Bundle b = new Bundle();
//        b.putString("type", "2");
//        f2.setArguments(b);
//    }
//
//    private void onCutFragment(Fragment f) {
//        FragmentManager manager = getSupportFragmentManager();
//        FragmentTransaction transaction = manager.beginTransaction();
//        transaction.replace(R.id.layout_content, f);
//        transaction.commit();
//    }
//
//
//    private void setBaseText() {
//        tv_lottery.setTextColor(getResources().getColor(R.color.text_05));
//        tv_lottery.setBackgroundResource(R.color.white);
//        tv_freelottery.setTextColor(getResources().getColor(R.color.text_05));
//        tv_freelottery.setBackgroundResource(R.color.white);
//    }
//
//    @Override
//    @OnClick({R.id.tv_lottery, R.id.tv_freelottery})
//    public void onClick(View v) {
//        setBaseText();
//        if (v.getId() == R.id.tv_lottery) {
//            tv_lottery.setTextColor(getResources().getColor(R.color.white));
//            tv_lottery.setBackgroundResource(R.color.red);
//            onCutFragment(f);
//            tv_lottery.setClickable(false);
//            tv_freelottery.setClickable(true);
//        } else if (v.getId() == R.id.tv_freelottery) {
//            tv_freelottery.setTextColor(getResources().getColor(R.color.white));
//            tv_freelottery.setBackgroundResource(R.color.red);
//            onCutFragment(f2);
//            tv_lottery.setClickable(true);
//            tv_freelottery.setClickable(false);
//        }
//    }
//}
