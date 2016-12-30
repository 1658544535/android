package com.qunyu.taoduoduo.fragment;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;

import com.andbase.library.http.AbHttpUtil;
import com.andbase.library.http.listener.AbStringHttpResponseListener;
import com.andbase.library.http.model.AbRequestParams;
import com.andbase.library.http.model.AbResult;
import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.qunyu.taoduoduo.R;
import com.qunyu.taoduoduo.activity.GoodsDetailActivity;
import com.qunyu.taoduoduo.activity.TypeTabActivity;
import com.qunyu.taoduoduo.adapter.FindGroupByTypeIdAdapter;
import com.qunyu.taoduoduo.base.BaseModel;
import com.qunyu.taoduoduo.base.BaseUtil;
import com.qunyu.taoduoduo.bean.FindGroupByTypeIdBean;
import com.qunyu.taoduoduo.global.Constant;
import com.qunyu.taoduoduo.pulltorefresh.PullToRefreshLayout;
import com.qunyu.taoduoduo.pulltorefresh.PullableGridView;
import com.qunyu.taoduoduo.utils.LogUtil;
import com.qunyu.taoduoduo.utils.ToastUtils;
import com.zhy.android.percent.support.PercentLinearLayout;

import java.lang.reflect.Type;
import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link HomeTypeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HomeTypeFragment extends Fragment implements View.OnClickListener {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    String TAG = "HomeTypeFragment";
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    int pageNo = 1;
    @BindView(R.id.gv_t)
    PullableGridView gvT;
    @BindView(R.id.refresh_view)
    PullToRefreshLayout refreshView;
    ArrayList<FindGroupByTypeIdBean> date;
    FindGroupByTypeIdAdapter adapter;
    @BindView(R.id.iv_top)
    ImageView ivTop;
    @BindView(R.id.activity_start)
    PercentLinearLayout activityStart;
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private String level = "1";
    private boolean issb = true;
    private OnFragmentInteractionListener mListener;

    public HomeTypeFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment HomeTypeFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static HomeTypeFragment newInstance(String param1, String param2) {
        HomeTypeFragment fragment = new HomeTypeFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fg_goods, container, false);
        ButterKnife.bind(this, view);
        init();
        FindGroupByTypeIdGet();
        return view;
    }

    private void init() {
        //
        refreshView.setOnPullListener(new PullToRefreshLayout.OnPullListener() {
            @Override
            public void onRefresh(PullToRefreshLayout pullToRefreshLayout) {
                new Handler() {
                    @Override
                    public void handleMessage(Message msg) {
                        // 千万别忘了告诉控件刷新完毕了哦！
                        pageNo = 1;
                        FindGroupByTypeIdGet();

                    }
                }.sendEmptyMessageDelayed(0, 800);
            }

            @Override
            public void onLoadMore(PullToRefreshLayout pullToRefreshLayout) {
                new Handler() {
                    @Override
                    public void handleMessage(Message msg) {
                        // 千万别忘了告诉控件加载完毕了哦！
                        pageNo++;
                        FindGroupByTypeIdGet();
                    }
                }.sendEmptyMessageDelayed(0, 800);
            }
        });
//        refreshView.setPullDownEnable(false);
        gvT.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                try {
                    FindGroupByTypeIdBean findGroupByTypeIdBean = (FindGroupByTypeIdBean) parent.getAdapter().getItem(position);
                    Intent intent = new Intent(getActivity(), GoodsDetailActivity.class);
                    intent.putExtra("activityId", findGroupByTypeIdBean.getActivityId());
                    intent.putExtra("pid", findGroupByTypeIdBean.getProductId());
                    startActivity(intent);
                } catch (Exception c) {
                }
            }
        });
        ivTop.setVisibility(View.GONE);
        gvT.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView absListView, int i) {
                if (getScrollY() > 1000) {
                    ivTop.setVisibility(View.VISIBLE);
                } else {
                    ivTop.setVisibility(View.GONE);
                }
            }

            @Override
            public void onScroll(AbsListView absListView, int i, int i1, int i2) {

            }
        });
        if (HomeFragment.list != null) {
            for (int a = 0; a < HomeFragment.list.size(); a++) {
                if (HomeFragment.list.get(a).oneId.equals(mParam1)) {
                    level = "1";
                    if (mParam2.equals("T")) {
                        head();
                    }
                    return;
                }
                for (int b = 0; b < HomeFragment.list.get(a).twoLevelList.size(); b++) {
                    if (HomeFragment.list.get(a).twoLevelList.get(b).twoId.equals(mParam1)) {
                        level = "2";
                        return;
                    }
                    for (int c = 0; c < HomeFragment.list.get(a).twoLevelList.get(b).threeLevelList.size(); c++) {
                        if (HomeFragment.list.get(a).twoLevelList.get(b).threeLevelList.get(c).threeId.equals(mParam1)) {
                            level = "3";
                            return;
                        }
                    }
                }
            }
        }


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ll_011:
            case R.id.ll_022:
            case R.id.ll_033:
            case R.id.ll_044:
            case R.id.ll_055:
            case R.id.ll_066:
            case R.id.ll_077:
            case R.id.ll_088:
                Bundle bundle = new Bundle();
                bundle.putString("tt", v.getTag(R.id.ll_011).toString());
                bundle.putString("typeId", v.getTag().toString());
                BaseUtil.ToAcb(getActivity(), TypeTabActivity.class, bundle);
                break;
            default:
                break;
        }
    }

    private void head() {
        View vHead = View.inflate(getActivity(), R.layout.type_fg, null);
        ImageView imageView1 = (ImageView) vHead.findViewById(R.id.iv_11);
        TextView TextView1 = (TextView) vHead.findViewById(R.id.tv_11);
        ImageView imageView2 = (ImageView) vHead.findViewById(R.id.iv_12);
        TextView TextView2 = (TextView) vHead.findViewById(R.id.tv_12);
        ImageView imageView3 = (ImageView) vHead.findViewById(R.id.iv_13);
        TextView TextView3 = (TextView) vHead.findViewById(R.id.tv_13);
        ImageView imageView4 = (ImageView) vHead.findViewById(R.id.iv_14);
        TextView TextView4 = (TextView) vHead.findViewById(R.id.tv_14);
        ImageView imageView5 = (ImageView) vHead.findViewById(R.id.iv_15);
        TextView TextView5 = (TextView) vHead.findViewById(R.id.tv_15);
        ImageView imageView6 = (ImageView) vHead.findViewById(R.id.iv_16);
        TextView TextView6 = (TextView) vHead.findViewById(R.id.tv_16);
        ImageView imageView7 = (ImageView) vHead.findViewById(R.id.iv_17);
        TextView TextView7 = (TextView) vHead.findViewById(R.id.tv_17);
        ImageView imageView8 = (ImageView) vHead.findViewById(R.id.iv_18);
        TextView TextView8 = (TextView) vHead.findViewById(R.id.tv_18);
        PercentLinearLayout ll1 = (PercentLinearLayout) vHead.findViewById(R.id.ll_011);
        PercentLinearLayout ll2 = (PercentLinearLayout) vHead.findViewById(R.id.ll_022);
        PercentLinearLayout ll3 = (PercentLinearLayout) vHead.findViewById(R.id.ll_033);
        PercentLinearLayout ll4 = (PercentLinearLayout) vHead.findViewById(R.id.ll_044);
        PercentLinearLayout ll5 = (PercentLinearLayout) vHead.findViewById(R.id.ll_055);
        PercentLinearLayout ll6 = (PercentLinearLayout) vHead.findViewById(R.id.ll_066);
        PercentLinearLayout ll7 = (PercentLinearLayout) vHead.findViewById(R.id.ll_077);
        PercentLinearLayout ll8 = (PercentLinearLayout) vHead.findViewById(R.id.ll_088);
        ll1.setOnClickListener(this);
        ll2.setOnClickListener(this);
        ll3.setOnClickListener(this);
        ll4.setOnClickListener(this);
        ll5.setOnClickListener(this);
        ll6.setOnClickListener(this);
        ll7.setOnClickListener(this);
        ll8.setOnClickListener(this);
        PercentLinearLayout ll11 = (PercentLinearLayout) vHead.findViewById(R.id.ll_11);
        PercentLinearLayout ll12 = (PercentLinearLayout) vHead.findViewById(R.id.ll_22);
        LogUtil.Log("666" + HomeFragment.list.size());
        for (int i = 0; i < HomeFragment.list.size(); i++) {
            if (HomeFragment.list.get(i).oneId.equals(mParam1 + "")) {
                try {
                    Glide.with(getActivity()).load(HomeFragment.list.get(i).twoLevelList.get(0).twoIcon)
                            .placeholder(R.mipmap.default_load)
                            .into(imageView1);
                    TextView1.setText(HomeFragment.list.get(i).twoLevelList.get(0).twoName);
                    ll1.setVisibility(View.VISIBLE);
                    ll1.setTag(HomeFragment.list.get(i).twoLevelList.get(0).twoId);
                    ll1.setTag(R.id.ll_011, HomeFragment.list.get(i).twoLevelList.get(0).twoName);
                    ll11.setVisibility(View.VISIBLE);
                    Glide.with(getActivity()).load(HomeFragment.list.get(i).twoLevelList.get(1).twoIcon)
                            .placeholder(R.mipmap.default_load)
                            .into(imageView2);
                    TextView2.setText(HomeFragment.list.get(i).twoLevelList.get(1).twoName);
                    ll2.setVisibility(View.VISIBLE);
                    ll2.setTag(HomeFragment.list.get(i).twoLevelList.get(1).twoId);
                    ll2.setTag(R.id.ll_011, HomeFragment.list.get(i).twoLevelList.get(1).twoName);
                    Glide.with(getActivity()).load(HomeFragment.list.get(i).twoLevelList.get(2).twoIcon)
                            .placeholder(R.mipmap.default_load)
                            .into(imageView3);
                    TextView3.setText(HomeFragment.list.get(i).twoLevelList.get(2).twoName);
                    ll3.setVisibility(View.VISIBLE);
                    ll3.setTag(HomeFragment.list.get(i).twoLevelList.get(2).twoId);
                    ll3.setTag(R.id.ll_011, HomeFragment.list.get(i).twoLevelList.get(2).twoName);
                    Glide.with(getActivity()).load(HomeFragment.list.get(i).twoLevelList.get(3).twoIcon)
                            .placeholder(R.mipmap.default_load)
                            .into(imageView4);
                    TextView4.setText(HomeFragment.list.get(i).twoLevelList.get(3).twoName);
                    ll4.setVisibility(View.VISIBLE);
                    ll4.setTag(HomeFragment.list.get(i).twoLevelList.get(3).twoId);
                    ll4.setTag(R.id.ll_011, HomeFragment.list.get(i).twoLevelList.get(3).twoName);
                    Glide.with(getActivity()).load(HomeFragment.list.get(i).twoLevelList.get(4).twoIcon)
                            .placeholder(R.mipmap.default_load)
                            .into(imageView5);
                    TextView5.setText(HomeFragment.list.get(i).twoLevelList.get(4).twoName);
                    ll5.setVisibility(View.VISIBLE);
                    ll5.setTag(HomeFragment.list.get(i).twoLevelList.get(4).twoId);
                    ll5.setTag(R.id.ll_011, HomeFragment.list.get(i).twoLevelList.get(4).twoName);
                    ll12.setVisibility(View.VISIBLE);
                    Glide.with(getActivity()).load(HomeFragment.list.get(i).twoLevelList.get(5).twoIcon)
                            .placeholder(R.mipmap.default_load)
                            .into(imageView6);
                    TextView6.setText(HomeFragment.list.get(i).twoLevelList.get(5).twoName);
                    ll6.setVisibility(View.VISIBLE);
                    ll6.setTag(HomeFragment.list.get(i).twoLevelList.get(5).twoId);
                    ll6.setTag(R.id.ll_011, HomeFragment.list.get(i).twoLevelList.get(5).twoName);
                    Glide.with(getActivity()).load(HomeFragment.list.get(i).twoLevelList.get(6).twoIcon)
                            .placeholder(R.mipmap.default_load)
                            .into(imageView7);
                    TextView7.setText(HomeFragment.list.get(i).twoLevelList.get(6).twoName);
                    ll7.setVisibility(View.VISIBLE);
                    ll7.setTag(HomeFragment.list.get(i).twoLevelList.get(6).twoId);
                    ll7.setTag(R.id.ll_011, HomeFragment.list.get(i).twoLevelList.get(6).twoName);
                    ll8.setTag(HomeFragment.list.get(i).twoLevelList.get(7).twoId);
                    ll8.setVisibility(View.VISIBLE);
                    ll8.setTag(mParam1);
                    ll8.setTag(R.id.ll_011, HomeFragment.list.get(i).oneName);
                } catch (Exception e) {
                }
            }
        }
        gvT.addHeaderView(vHead);
    }

    public int getScrollY() {
        View c = gvT.getChildAt(0);
        if (c == null) {
            return 0;
        }
        int firstVisiblePosition = gvT.getFirstVisiblePosition();
        int top = c.getTop();
        return -top + firstVisiblePosition * c.getHeight();
    }

    private void FindGroupByTypeIdGet() {
        AbRequestParams params = new AbRequestParams();
        params.put("pageNo", pageNo);
        params.put("level", level);
        params.put("id", mParam1);
        params.put("sorting", "4");
        LogUtil.Log(Constant.findProductListApi + "?" + params.getParamString());
        AbHttpUtil.getInstance(getActivity()).get(Constant.findProductListApi, params, new AbStringHttpResponseListener() {
            @Override
            public void onStart() {
//                AbDialogUtil.showProgressDialog(LoginActivity.this, 0, "正在登录...");

            }

            @Override
            public void onFinish() {
//                AbDialogUtil.removeDialog(LoginActivity.this);
                try {
                    refreshView.refreshFinish(PullToRefreshLayout.SUCCEED);
                    refreshView.loadmoreFinish(PullToRefreshLayout.SUCCEED);
                } catch (Exception e) {
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
                    Type type = new TypeToken<BaseModel<ArrayList<FindGroupByTypeIdBean>>>() {
                    }.getType();
                    BaseModel<ArrayList<FindGroupByTypeIdBean>> base = gson.fromJson(content, type);
                    Log.d(TAG, "onSuccess: " + content);
                    if (base.result != null) {
                        if (pageNo == 1) {
                            date = base.result;
                            adapter = new FindGroupByTypeIdAdapter(getActivity(), date);
                            gvT.setAdapter(adapter);
                        } else {
                            try {
                                date.addAll(base.result);
                                adapter.notifyDataSetChanged();
                            } catch (Exception e) {
                                LogUtil.Log(e + "");
                            }
                        }
                    }
                } else {
                    ToastUtils.showShortToast(getActivity(), "网络异常，数据加载失败");
                    LogUtil.Log(result.getResultMessage());
                }

            }
        });

    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
//        if (context instanceof OnFragmentInteractionListener) {
//            mListener = (OnFragmentInteractionListener) context;
//        } else {
//            throw new RuntimeException(context.toString()
//                    + " must implement OnFragmentInteractionListener");
//        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @OnClick(R.id.iv_top)
    public void onClick() {
        gvT.smoothScrollToPosition(0);
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
