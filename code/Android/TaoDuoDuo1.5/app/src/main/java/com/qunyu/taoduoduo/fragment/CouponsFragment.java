package com.qunyu.taoduoduo.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import com.andbase.library.http.AbHttpUtil;
import com.andbase.library.http.listener.AbStringHttpResponseListener;
import com.andbase.library.http.model.AbResult;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.qunyu.taoduoduo.R;
import com.qunyu.taoduoduo.activity.CouponsActivity;
import com.qunyu.taoduoduo.activity.GoodsDetailActivity;
import com.qunyu.taoduoduo.activity.TabActivity;
import com.qunyu.taoduoduo.adapter.CouponsAdapter;
import com.qunyu.taoduoduo.api.MyCouponsApi;
import com.qunyu.taoduoduo.base.BaseModel;
import com.qunyu.taoduoduo.base.BaseUtil;
import com.qunyu.taoduoduo.bean.MyCouponsListBean;
import com.qunyu.taoduoduo.pulltorefresh.PullToRefreshLayout;
import com.qunyu.taoduoduo.pulltorefresh.PullableListView;
import com.qunyu.taoduoduo.utils.LogUtil;
import com.qunyu.taoduoduo.utils.ToastUtils;
import com.qunyu.taoduoduo.utils.UserInfoUtils;
import com.zhy.android.percent.support.PercentRelativeLayout;

import java.lang.reflect.Type;
import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2016/9/27.
 */

public class CouponsFragment extends Fragment {
    View view;
    private String type;//类型(1未使用,2已过期,3已使用)
    private int pageNo;
    private MyCouponsListBean bean;
    private ArrayList<MyCouponsListBean.CouponList> arrayList;
    private ArrayList<MyCouponsListBean> arrayL;
    public static String notUsedNum,overdueNum,usedNum;
    private CouponsAdapter adapter;



    @BindView(R.id.refresh_view)
    PullToRefreshLayout refreshLayout;
    @BindView(R.id.lv_mycoupon)
    PullableListView listView;
    @BindView(R.id.layout_couponNull)
    PercentRelativeLayout layout_couponNull;
//    @BindView(R.id.tv_couponName)
//    TextView couponName;
//    @BindView(R.id.tv_jiandiaojine)
//    TextView tv_jdje;
//    @BindView(R.id.tv_effective_Dates)
//    TextView tv_effectiveTime;
//    @BindView(R.id.tv_m)
//    TextView tv_m;

    public static final CouponsFragment newInstance(String type){
        CouponsFragment f=new CouponsFragment();
        Bundle b=new Bundle();
        b.putString("type",type);
        f.setArguments(b);
        return f;
    }

    @Override
    public View onCreateView(LayoutInflater inflater,  ViewGroup container,  Bundle savedInstanceState) {
        view=inflater.inflate(R.layout.coupon_fragment,null,false);
        type=getArguments().getString("type");
        ButterKnife.bind(this,view);
        bean=new MyCouponsListBean();
        arrayList=new ArrayList<MyCouponsListBean.CouponList>();
        arrayL=new ArrayList<>();
        pageNo=1;
        init();
        onListener();
        return view;
    }
    private void init(){
        refreshLayout.setPullDownEnable(true);
        refreshLayout.setOnPullListener(new MyPullListener());
    }

    @Override
    public void onResume() {
        super.onResume();
        getCouponMsg();
    }

    private void onListener() {
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (arrayList.get(position).getIsProduct().equals("1")) {
                    if (type != null && type.equals("1")) {
                        Bundle b = new Bundle();
                        b.putString("activityId", arrayList.get(position).getActivityId());
                        b.putString("pid", arrayList.get(position).getProductId());
                        BaseUtil.ToAcb(getActivity(), GoodsDetailActivity.class, b);
                    }
                } else {
                    if (type != null && type.equals("1")) {
                        BaseUtil.ToAc(getActivity(), TabActivity.class);
                    }
                }

            }
        });
    }

    public void getCouponMsg() {
        MyCouponsApi couponApi=new MyCouponsApi();
        couponApi.setPageNo(String.valueOf(pageNo));
        couponApi.setType(type);
        couponApi.setUid(UserInfoUtils.GetUid());
        LogUtil.Log(couponApi.getUrl() + "?" + couponApi.getParamMap().getParamString());
        AbHttpUtil.getInstance(getActivity()).get(couponApi.getUrl(), couponApi.getParamMap(), new AbStringHttpResponseListener() {
            @Override
            public void onSuccess(int statusCode, String content) {
                AbResult result=new AbResult(content);
                if(result.getResultCode()==AbResult.RESULT_OK){
                    Gson gson=new Gson();
                    Type type=new TypeToken<BaseModel<MyCouponsListBean>>(){}.getType();
                    BaseModel<MyCouponsListBean> base=gson.fromJson(content,type);
                    if(base!=null){
                        bean=base.result;
                        notUsedNum=bean.getNotUsedNum();
                        overdueNum=bean.getOverdueNum();
                        usedNum=bean.getUsedNum();
                        Message msg=new Message();
                        CouponsActivity.handler.sendMessage(msg);
                        if (bean.getCouponList() != null) {
                            if (pageNo == 1) {
                                if (bean.getCouponList().size() > 0) {
                                    arrayList = bean.getCouponList();
                                    adapter = new CouponsAdapter(getActivity(), arrayList);
                                    listView.setAdapter(adapter);
                                } else {
                                    refreshLayout.setVisibility(View.GONE);
                                    layout_couponNull.setVisibility(View.VISIBLE);
                                }
                            } else {
                                arrayList.addAll(bean.getCouponList());
                                adapter.notifyDataSetChanged();
                            }
                        }else{
                            refreshLayout.setVisibility(View.GONE);
                            layout_couponNull.setVisibility(View.VISIBLE);

                        }

                    }
                }

            }

            @Override
            public void onStart() {

            }

            @Override
            public void onFinish() {
                try {
                    refreshLayout.refreshFinish(PullToRefreshLayout.SUCCEED);
                    refreshLayout.loadmoreFinish(PullToRefreshLayout.SUCCEED);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            @Override
            public void onFailure(int i, String s, Throwable error) {
                ToastUtils.showShortToast(getActivity(), "网络异常，数据加载失败");
                LogUtil.Log(error.getMessage());
            }
        });
    }

    public class MyPullListener implements PullToRefreshLayout.OnPullListener{

        @Override
        public void onRefresh(PullToRefreshLayout pullToRefreshLayout) {
            new Handler(){
                @Override
                public void handleMessage(Message msg) {
                    super.handleMessage(msg);
                    pageNo=1;
                    getCouponMsg();
                }
            }.sendEmptyMessageDelayed(0,1600);
        }

        @Override
        public void onLoadMore(PullToRefreshLayout pullToRefreshLayout) {
            new Handler(){
                @Override
                public void handleMessage(Message msg) {
                    super.handleMessage(msg);
                    pageNo++;
                    getCouponMsg();
                }
            }.sendEmptyMessageDelayed(0,1600);
        }
    }
}
