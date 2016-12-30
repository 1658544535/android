package com.qunyu.taoduoduo.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.andbase.library.http.AbHttpUtil;
import com.andbase.library.http.listener.AbStringHttpResponseListener;
import com.andbase.library.http.model.AbResult;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.qunyu.taoduoduo.R;
import com.qunyu.taoduoduo.activity.SearchTextActivity;
import com.qunyu.taoduoduo.adapter.ProductCategorylv1Adapter;
import com.qunyu.taoduoduo.adapter.ProductCategorylv2Adapter;
import com.qunyu.taoduoduo.base.BaseModel;
import com.qunyu.taoduoduo.base.BaseUtil;
import com.qunyu.taoduoduo.bean.ProductTypeBean;
import com.qunyu.taoduoduo.global.Constant;
import com.qunyu.taoduoduo.pulltorefresh.PullToRefreshLayout;
import com.qunyu.taoduoduo.pulltorefresh.PullableListView;
import com.qunyu.taoduoduo.utils.LogUtil;
import com.qunyu.taoduoduo.utils.ToastUtils;
import com.zhy.android.percent.support.PercentRelativeLayout;

import java.lang.reflect.Type;
import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Administrator on 2016/11/14.
 */

public class ProductCategoryFragment extends Fragment implements View.OnClickListener {

    private View view;
    //    private ApiClient apiClient;
    private ArrayList<ProductTypeBean> oneList;
//    private ArrayList<ProductCategoryModel.twoLevelLists> twoList;
//    private ArrayList<ProductCategoryModel.twoLevelLists.threeLevelLists> threeList;

    private ProductCategorylv1Adapter lv1_adapter;
    private ProductCategorylv2Adapter lv2_adapter;
    Integer lv1ClickPosition = 0;

    @BindView(R.id.lv1_productCategory)
    ListView lv1;
    @BindView(R.id.lv2_productCategory)
    PullableListView lv2;
    //    @BindView(R.id.tv_seach)
//    TextView tv_search;
    @BindView(R.id.refresh_view)
    PullToRefreshLayout refreshLayout;
    @BindView(R.id.tv_seach)
    TextView iv_search;
    @BindView(R.id.layout_search)
    PercentRelativeLayout layout_search;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.productcategory_activity, null);
        ButterKnife.bind(this, view);
        oneList = new ArrayList<>();
        onListener();
        getMsg();
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();

    }


    public void getMsg() {
//        oneList = HomeFragment.list;
////        String jsonStr= HomeFragment.list;
////        Gson gson=new Gson();
////        Type type=new TypeToken<BaseModel<ArrayList<ProductCategoryApi>>>(){}.getType();
////        BaseModel<ArrayList<ProductCategoryApi>> base =gson.fromJson(jsonStr,type);
////        if(base!=null && base.result!=null){
//
//        if (oneList != null) {
////                    oneList=base.result;
//            lv1_adapter = new ProductCategorylv1Adapter(getActivity(), oneList);
//            lv2_adapter = new ProductCategorylv2Adapter(getActivity(), oneList);
////                    lv2_adapter.mposition=lv1.getLastVisiblePosition();
//            lv1.setAdapter(lv1_adapter);
//            lv2.setAdapter(lv2_adapter);
//        }
//        refreshLayout.refreshFinish(PullToRefreshLayout.SUCCEED);

        LogUtil.Log(Constant.productType);
        AbHttpUtil.getInstance(getActivity()).get(Constant.productType, new AbStringHttpResponseListener() {

            @Override
            public void onStart() {
//                AbDialogUtil.showProgressDialog(LoginActivity.this, 0, "正在登录...");
            }

            @Override
            public void onFinish() {
//                AbDialogUtil.removeDialog(LoginActivity.this);
                try {
                    refreshLayout.refreshFinish(PullToRefreshLayout.SUCCEED);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int statusCode, String content, Throwable error) {
                ToastUtils.showShortToast(getActivity(), "网络异常，数据加载失败");
                LogUtil.Log(error.getMessage());
            }

            @Override
            public void onSuccess(int statusCode, String content) {
                AbResult result = new AbResult(content);
                if (result.getResultCode() == AbResult.RESULT_OK) {
                    Gson gson = new Gson();
                    Type type = new TypeToken<BaseModel<ArrayList<ProductTypeBean>>>() {
                    }.getType();
                    BaseModel<ArrayList<ProductTypeBean>> base = gson.fromJson(content, type);
                    if (base.result != null) {
                        oneList = base.result;
                        lv1_adapter = new ProductCategorylv1Adapter(getActivity(), oneList);
                        lv2_adapter = new ProductCategorylv2Adapter(getActivity(), oneList);
                        lv1.setAdapter(lv1_adapter);
                        lv2.setAdapter(lv2_adapter);
                    }
                } else {
                    ToastUtils.showShortToast(getActivity(), "网络异常，数据加载失败");
                    LogUtil.Log(result.getResultMessage());
                }

            }
        });
    }

    private void onListener() {
        // tv_search.setOnClickListener(this);

        lv2.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
                LogUtil.Log("xy====" + lv2.getScrollX() + "|" + lv2.getScrollY());
            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                //if(lv1ClickPosition==lv2.getLastVisiblePosition()){
                //  lv1_adapter.selectposition=lv1ClickPosition;
                //}else{
                lv1_adapter.selectposition = firstVisibleItem;
                //  }

                if (lv1_adapter != null) {
                    lv1_adapter.notifyDataSetChanged();
                }
            }
        });
        lv1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                lv2.smoothScrollToPositionFromTop(position, 0, 200);
                lv2.setSelection(position);
                LogUtil.Log(position + "");
                lv1_adapter.selectposition = position;
                lv1_adapter.notifyDataSetChanged();
                lv1ClickPosition = position;
            }
        });

        refreshLayout.setOnPullListener(new PullToRefreshLayout.OnPullListener() {
            @Override
            public void onRefresh(PullToRefreshLayout pullToRefreshLayout) {
                getMsg();
            }

            @Override
            public void onLoadMore(PullToRefreshLayout pullToRefreshLayout) {
                pullToRefreshLayout.loadmoreFinish(PullToRefreshLayout.SUCCEED);
            }
        });
    }


    @Override
    @OnClick({R.id.layout_search})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.layout_search:
                BaseUtil.ToAc(getActivity(), SearchTextActivity.class);
        }
    }
}
