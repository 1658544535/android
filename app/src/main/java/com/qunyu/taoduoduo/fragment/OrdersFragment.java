package com.qunyu.taoduoduo.fragment;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import com.andbase.library.http.AbHttpUtil;
import com.andbase.library.http.listener.AbStringHttpResponseListener;
import com.andbase.library.http.model.AbRequestParams;
import com.andbase.library.http.model.AbResult;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.qunyu.taoduoduo.R;
import com.qunyu.taoduoduo.activity.OrderDetailActivity;
import com.qunyu.taoduoduo.activity.RefundDetailsActivity;
import com.qunyu.taoduoduo.adapter.MyOrdersAdapter;
import com.qunyu.taoduoduo.base.BaseModel;
import com.qunyu.taoduoduo.base.BaseUtil;
import com.qunyu.taoduoduo.bean.MyOrderBean;
import com.qunyu.taoduoduo.global.AnyEventType;
import com.qunyu.taoduoduo.global.Constant;
import com.qunyu.taoduoduo.pulltorefresh.PullToRefreshLayout;
import com.qunyu.taoduoduo.pulltorefresh.PullableListView;
import com.qunyu.taoduoduo.utils.LogUtil;
import com.qunyu.taoduoduo.utils.ToastUtils;
import com.qunyu.taoduoduo.utils.UserInfoUtils;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.lang.reflect.Type;
import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link OrdersFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class OrdersFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    String TAG = "OrdersFragment";
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    int pageNo = 1;
    @BindView(R.id.refresh_view)
    PullToRefreshLayout refreshView;
    ArrayList<MyOrderBean> date;
    MyOrdersAdapter adapter;
    @BindView(R.id.lv_t)
    PullableListView lvT;
    // TODO: Rename and change types of parameters
    public String mParam1;
    public String mParam2;

    private OnFragmentInteractionListener mListener;

    public OrdersFragment() {
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
    public static OrdersFragment newInstance(String param1, String param2) {
        OrdersFragment fragment = new OrdersFragment();
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
        View view = inflater.inflate(R.layout.fg_order, container, false);
        ButterKnife.bind(this, view);
        init();
        EventBus.getDefault().register(this);
        pageNo = 1;
        MyorderGet();
        return view;
    }

    @Subscribe
    @Override
    public void onDestroy() {
        super.onDestroy();
//        EventBus.getDefault().unregister(mr);
        EventBus.getDefault().unregister(this);
//        EventBus.getDefault().unregister(page2);
//        EventBus.getDefault().unregister(page3);EventBus.getDefault().unregister(page4);
//        EventBus.getDefault().unregister(page5);
//        EventBus.getDefault().unregister(page6);
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Subscribe
    public void onEvent(AnyEventType event) {
        try {
            Log.d(TAG, "onEvent: 1111111111111");
            String[] ary = event.getMsg().split(";");
            if (mParam1.equals("7")) {
                for (int i = 0; i < date.size(); i++) {
                    if (ary[0].equals(date.get(i).getId())) {
                        if (ary[1].equals("cancel")) {
                            date.get(i).setIsCancel("1");
                            Log.d(TAG, "onEvent: 1111111111111");
                        } else if (ary[1].equals("finish")) {
                            date.get(i).setOrderStatus("6");
                            Log.d(TAG, "onEvent: 1111111111111");
                        }
                    }
                }
            } else if (mParam1.equals("4")) {
                pageNo = 1;
                MyorderGet();
            } else if (ary[1].equals("xin")) {
                pageNo = 1;
                MyorderGet();
                Log.d(TAG, "onEvent: 1111111111111");
            } else {

                for (int i = 0; i < date.size(); i++) {
                    if (ary[0].equals(date.get(i).getId())) {
                        if (ary[1].equals("cancel")) {
                            date.remove(i);
                        } else if (ary[1].equals("finish")) {
                            date.get(i).setOrderStatus("6");
//                        OrdersActivity.setbean(date.get(i));
                            date.remove(i);
                            Log.d(TAG, "onEvent: 1111111111111");
                        }
                    }
                }
            }
        } catch (Exception e) {
        }
        adapter.notifyDataSetChanged();
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
                        MyorderGet();

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
                        MyorderGet();
                    }
                }.sendEmptyMessageDelayed(0, 800);
            }
        });
        refreshView.setPullDownEnable(false);
        lvT.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
                                    long arg3) {
                // TODO Auto-generated method stub
//                if (!date.get(arg2).getRefundStatus().equals("1")) {
                Bundle bundle = new Bundle();
                bundle.putString("oid", date.get(arg2).getId() + "");
                if (date.get(arg2).getSource().equals("5") || date.get(arg2).getSource().equals("7")) {
                    bundle.putString("type", "555");
                } else {
                    Log.d(TAG, "onItemClick: " + date.get(arg2).getId());
                    int i = 0;
                    if (date.get(arg2).getOrderStatus().equals("1")) {
                        //待付款
                        if (date.get(arg2).getIsCancel().equals("1")) {
                            //取消
                            i = 1;
                        } else {
                            //正常
                            i = 2;
                        }
                    } else if (date.get(arg2).getOrderStatus().equals("2")) {
                        //已付款
                        if (date.get(arg2).getIsSuccess().equals("0")) {
                            //拼团中
                            i = 3;
                        } else if (date.get(arg2).getIsSuccess().equals("1")) {
                            //拼团成功
                            i = 4;
                        } else if (date.get(arg2).getIsSuccess().equals("2")) {
                            //拼团失败
                            i = 41;
                        }
                    } else if (date.get(arg2).getOrderStatus().equals("3")) {
                        //已发货
                        i = 5;
                    } else {
                        //已完成
                        i = 6;
                    }
                    bundle.putString("type", i + "");
                }
                BaseUtil.ToAcb(getActivity(), OrderDetailActivity.class, bundle);
//                } else {
//                    Bundle bundle = new Bundle();
//                    bundle.putString("oid", date.get(arg2).getId() + "");
//                    BaseUtil.ToAcb(getActivity(), RefundDetailsActivity.class, bundle);
//                }
            }


        });
    }

    private void MyorderGet() {
        AbRequestParams params = new AbRequestParams();
        params.put("orderStatus", mParam1);
        params.put("pageNo", pageNo + "");
        params.put("uid", UserInfoUtils.GetUid());
        LogUtil.Log(Constant.myorder + "?" + params.getParamString());
        AbHttpUtil.getInstance(getActivity()).get(Constant.myorder, params, new AbStringHttpResponseListener() {
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
                    Type type = new TypeToken<BaseModel<ArrayList<MyOrderBean>>>() {
                    }.getType();
                    BaseModel<ArrayList<MyOrderBean>> base = gson.fromJson(content, type);
//                    Log.d(TAG, "onSuccess: " + content);
                    if (base.result != null && base.result.size() != 0) {
                        lvT.setBackgroundDrawable(null);
                        if (pageNo == 1) {
                            date = base.result;
                            adapter = new MyOrdersAdapter(getActivity(), date, mParam1);
                            lvT.setAdapter(adapter);
                            Log.d(TAG, "onSuccess: " + mParam1 + content);
                        } else {
                            date.addAll(base.result);
                            adapter.notifyDataSetChanged();
                        }
                    } else {
                        if (pageNo == 1) {
                        } else {
                            ToastUtils.showShortToast(getActivity(), base.error_msg);
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
