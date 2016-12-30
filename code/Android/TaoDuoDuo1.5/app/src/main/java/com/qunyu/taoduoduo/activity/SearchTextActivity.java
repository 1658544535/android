package com.qunyu.taoduoduo.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.andbase.library.http.AbHttpUtil;
import com.andbase.library.http.listener.AbStringHttpResponseListener;
import com.andbase.library.http.model.AbResult;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.qunyu.taoduoduo.R;
import com.qunyu.taoduoduo.adapter.SearchProductAdapter;
import com.qunyu.taoduoduo.api.SearchAllApi;
import com.qunyu.taoduoduo.base.BaseModel;
import com.qunyu.taoduoduo.bean.SearchAllProductBean;
import com.qunyu.taoduoduo.pulltorefresh.PullToRefreshLayout;
import com.qunyu.taoduoduo.pulltorefresh.PullableGridView;
import com.qunyu.taoduoduo.utils.LogUtil;
import com.qunyu.taoduoduo.utils.StringUtils;
import com.qunyu.taoduoduo.utils.ToastUtils;
import com.qunyu.taoduoduo.utils.UserInfoUtils;
import com.umeng.analytics.MobclickAgent;
import com.zhy.android.percent.support.PercentLinearLayout;
import com.zhy.android.percent.support.PercentRelativeLayout;
import com.zhy.view.flowlayout.FlowLayout;
import com.zhy.view.flowlayout.TagAdapter;
import com.zhy.view.flowlayout.TagFlowLayout;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2016/12/2.
 * 搜索商品页面(更换时把之前的fragment删除)
 */

public class SearchTextActivity extends Activity {

    private SearchAllApi searchAllApi;
    private SearchAllProductBean bean;
    private ArrayList<SearchAllProductBean.productss> list;
    private SearchProductAdapter adapter;

    @BindView(R.id.edt_searchContent)
    EditText search_content;
    @BindView(R.id.iv_clean)
    ImageView iv_clean;
    @BindView(R.id.refresh_view)
    PullToRefreshLayout refreshLayout;
    @BindView(R.id.gv_s)
    PullableGridView gridView;
    @BindView(R.id.tv_searchCount)
    TextView tv_searchCount;
    @BindView(R.id.layout_searchText1)
    PercentRelativeLayout layout_searchText1;
    @BindView(R.id.layout_searchText2)
    PercentLinearLayout layout_searchText2;
    @BindView(R.id.iv_history_clean)
    ImageView iv_history_clean;
    @BindView(R.id.tv_cancel)
    TextView tv_cancel;

    TagFlowLayout fl_searchHistory;

    private String sorting;//排序（1为销量降序，11为销量升序，2为价格升序，22为价格降序， 3为点击数降序，3为点击数升序）
    private Integer pageNo;
    private String searchProName;//搜索的商品名称
    private String count;//搜索结果条数

    private List<String> historystrings = null;
    LayoutInflater layoutInflater;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search_text_fragment);
        ButterKnife.bind(this);
        fl_searchHistory = (TagFlowLayout) findViewById(R.id.fl_searchHistory);
        bean = new SearchAllProductBean();
        list = new ArrayList<SearchAllProductBean.productss>();
        pageNo = 1;
        onListener();
//        search_content.performClick();
    }

    @Override
    public void onResume() {
        super.onResume();
        MobclickAgent.onResume(this);
        setSearchHistory();

    }
    public void onPause() {
        super.onPause();
        MobclickAgent.onPause(this);
    }
    private void setSearchHistory() {
        historystrings = UserInfoUtils.getSearchHistory();
        if (historystrings.size() > 0) {
            layoutInflater = LayoutInflater.from(SearchTextActivity.this);
            fl_searchHistory.setAdapter(new TagAdapter<String>(historystrings) {
                @Override
                public View getView(FlowLayout parent, int position, String s) {
                    TextView tv = (TextView) layoutInflater.inflate(R.layout.searchhistory_item,
                            fl_searchHistory, false);
                    tv.setText(s);
                    return tv;
                }
            });
        }
    }

    private void sethistory(String key) {
        if (historystrings != null) {
            for (int i = 0; i < historystrings.size(); i++) {
                if (key.equals(historystrings.get(i))) {
                    historystrings.remove(i);
                }
            }
        }
        historystrings.add(0, key);
        UserInfoUtils.setSearchHistory(historystrings);
    }

    private void onListener() {
        refreshLayout.setPullDownEnable(true);
        refreshLayout.setOnPullListener(new MyPullListener());
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(SearchTextActivity.this, GoodsDetailActivity.class);
                intent.putExtra("activityId", list.get(position).getActivityId());
                intent.putExtra("pid", list.get(position).getProductId());
                startActivity(intent);
            }
        });

        search_content.setImeOptions(EditorInfo.IME_ACTION_SEARCH);
        search_content.setInputType(EditorInfo.TYPE_CLASS_TEXT);
        search_content.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (KeyEvent.KEYCODE_ENTER == keyCode && event.getAction() == KeyEvent.ACTION_DOWN) {
                    searchProName = search_content.getText().toString().trim();
                    pageNo = 1;
                    LogUtil.Log(pageNo + "=================");
                    if (StringUtils.isEmpty(searchProName)) {
                        ToastUtils.showShortToast(SearchTextActivity.this, "请输入要搜索的关键词");
                    } else {
                        sethistory(searchProName);
                        getProductMsg();
                    }
                    return true;
                }
                return false;
            }
        });
        iv_clean.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                search_content.setText("");
            }
        });
        iv_history_clean.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                historystrings.clear();
                UserInfoUtils.clearSearchHistory();
                if (fl_searchHistory.getAdapter() != null) {
                    fl_searchHistory.getAdapter().notifyDataChanged();
                    //setSearchHistory();
                }

            }
        });
        fl_searchHistory.setOnTagClickListener(new TagFlowLayout.OnTagClickListener() {
            @Override
            public boolean onTagClick(View view, int position, FlowLayout parent) {
                if (historystrings.get(position) != null) {
                    searchProName = historystrings.get(position);
                    search_content.setText(searchProName);
                    getProductMsg();
                }
                return true;
            }
        });
        tv_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                layout_searchText1.setVisibility(View.VISIBLE);
//                layout_searchText2.setVisibility(View.GONE);
                search_content.setText("");
//                if (fl_searchHistory.getAdapter() != null) {
//                    fl_searchHistory.getAdapter().notifyDataChanged();
//                }
                onBackPressed();
            }
        });


    }


    private void getProductMsg() {
        searchAllApi = new SearchAllApi();
        LogUtil.Log("getProductMsg========" + pageNo);
        searchAllApi.setPageNo(pageNo + "");
        searchAllApi.setName(searchProName);
        searchAllApi.setSorting(sorting);
        LogUtil.Log(searchAllApi.getUrl() + "?" + searchAllApi.getParamMap().getParamString());
        AbHttpUtil.getInstance(SearchTextActivity.this).get(searchAllApi.getUrl(), searchAllApi.getParamMap(), new AbStringHttpResponseListener() {
            @Override
            public void onSuccess(int i, String content) {
                AbResult result = new AbResult(content);
                if (result.getResultCode() == AbResult.RESULT_OK) {
                    Gson gson = new Gson();
                    Type type = new TypeToken<BaseModel<SearchAllProductBean>>() {
                    }.getType();
                    BaseModel<SearchAllProductBean> base = gson.fromJson(content, type);
                    if (base != null) {
                        bean = base.result;
                        count = bean.getCount();
                        tv_searchCount.setText("共找到" + count + "条相关结果");
                        if (count.equals("0")) {
                            list.clear();
                            if (adapter != null) {
                                adapter.notifyDataSetChanged();
                            }
                        }
                        if (bean.getProducts() != null && bean.getProducts().size() > 0) {
                            if (pageNo == 1) {
                                list = bean.getProducts();
                                adapter = new SearchProductAdapter(SearchTextActivity.this, list);
                                gridView.setAdapter(adapter);
                            } else {
                                list.addAll(bean.getProducts());
                                if (adapter != null) {
                                    adapter.notifyDataSetChanged();
                                }
                            }
                        }
                    }
                } else {
                    ToastUtils.showShortToast(SearchTextActivity.this, "网络异常，数据加载失败");
                    LogUtil.Log(result.getResultMessage());
                }
                layout_searchText1.setVisibility(View.GONE);
                layout_searchText2.setVisibility(View.VISIBLE);
                setSearchHistory();
            }

            @Override
            public void onStart() {

            }

            @Override
            public void onFinish() {
                try {
                    refreshLayout.loadmoreFinish(PullToRefreshLayout.SUCCEED);
                    refreshLayout.refreshFinish(PullToRefreshLayout.SUCCEED);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int i, String s, Throwable error) {
                ToastUtils.showShortToast(SearchTextActivity.this, "网络异常，数据加载失败");
                LogUtil.Log(error.getMessage());
            }
        });
    }

    @Override
    public void onBackPressed() {
        InputMethodManager inputMethodManager =
                (InputMethodManager) SearchTextActivity.this.getSystemService(Context.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(search_content.getWindowToken(), 0);
        super.onBackPressed();
    }

    class MyPullListener implements PullToRefreshLayout.OnPullListener {

        @Override
        public void onRefresh(PullToRefreshLayout pullToRefreshLayout) {
            new Handler() {
                @Override
                public void handleMessage(Message msg) {
                    super.handleMessage(msg);
                    pageNo = 1;
                    getProductMsg();
                }
            }.sendEmptyMessageDelayed(0, 1600);
        }

        @Override
        public void onLoadMore(PullToRefreshLayout pullToRefreshLayout) {
            new Handler() {
                @Override
                public void handleMessage(Message msg) {
                    super.handleMessage(msg);
                    pageNo++;
                    getProductMsg();
                }
            }.sendEmptyMessageDelayed(0, 1600);
        }
    }
}

