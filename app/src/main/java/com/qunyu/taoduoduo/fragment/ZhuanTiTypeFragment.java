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
import com.qunyu.taoduoduo.adapter.SpecialListApiAdapter;
import com.qunyu.taoduoduo.base.BaseModel;
import com.qunyu.taoduoduo.bean.SpecialListBean;
import com.qunyu.taoduoduo.global.Constant;
import com.qunyu.taoduoduo.pulltorefresh.PullToRefreshLayout;
import com.qunyu.taoduoduo.pulltorefresh.PullToRefreshLayout2;
import com.qunyu.taoduoduo.pulltorefresh.PullableListView;
import com.qunyu.taoduoduo.utils.LogUtil;
import com.qunyu.taoduoduo.utils.ToastUtils;

import java.lang.reflect.Type;
import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link ZhuanTiTypeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ZhuanTiTypeFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    String TAG = "HomeTypeFragment";
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    int pageNo = 1;
    @BindView(R.id.refresh_view)
    PullToRefreshLayout2 refreshView;
    ArrayList<SpecialListBean> date;
    SpecialListApiAdapter adapter;
    @BindView(R.id.lv_t)
    PullableListView lvT;
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public ZhuanTiTypeFragment() {
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
    public static ZhuanTiTypeFragment newInstance(String param1, String param2) {
        ZhuanTiTypeFragment fragment = new ZhuanTiTypeFragment();
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
        View view = inflater.inflate(R.layout.fg_home, container, false);
        ButterKnife.bind(this, view);
        init();
        SpecialListGet();
        return view;
    }

    private void init() {
        //
        refreshView.setOnPullListener(new PullToRefreshLayout2.OnPullListener() {
                                          @Override
                                          public void onRefresh(PullToRefreshLayout2 pullToRefreshLayout) {
                                              new Handler() {
                                                  @Override
                                                  public void handleMessage(Message msg) {
                                                      // 千万别忘了告诉控件刷新完毕了哦！
                                                      pageNo = 1;
                                                      SpecialListGet();

                                                  }
                                              }.sendEmptyMessageDelayed(0, 800);
                                          }

                                          @Override
                                          public void onLoadMore(PullToRefreshLayout2 pullToRefreshLayout) {
                                              new Handler() {
                                                  @Override
                                                  public void handleMessage(Message msg) {
                                                      // 千万别忘了告诉控件加载完毕了哦！
                                                      pageNo++;
                                                      SpecialListGet();
                                                  }
                                              }.sendEmptyMessageDelayed(0, 800);
                                          }
                                      }
        );
        refreshView.setPullDownEnable(false);
        lvT.setOnItemClickListener(new AdapterView.OnItemClickListener()

                                   {
                                       @Override
                                       public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                FindGroupByTypeIdBean findGroupByTypeIdBean = (FindGroupByTypeIdBean) parent.getAdapter().getItem(position);
//                Intent intent = new Intent(getActivity(), GoodsDetailActivity.class);
//                intent.putExtra("activityId", findGroupByTypeIdBean.getActivityId());
//                intent.putExtra("pid", findGroupByTypeIdBean.getProductId());
//                startActivity(intent);

                                       }
                                   }

        );

    }

    private void SpecialListGet() {
        AbRequestParams params = new AbRequestParams();
        params.put("pageNo", pageNo);
        params.put("typeId", mParam1);
//        Log.d(TAG, "onSuccess: " + FindGroupByTypeIdApi.getUrl() + FindGroupByTypeIdApi.getParamMap().getParamString());
        AbHttpUtil.getInstance(getActivity()).get(Constant.specialListApi, params, new AbStringHttpResponseListener() {
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
                    Type type = new TypeToken<BaseModel<ArrayList<SpecialListBean>>>() {
                    }.getType();
                    BaseModel<ArrayList<SpecialListBean>> base = gson.fromJson(content, type);
                    Log.d(TAG, "onSuccess: " + content);
                    if (base.result != null) {
                        if (pageNo == 1) {
                            date = base.result;
                            adapter = new SpecialListApiAdapter(getActivity(), date);
                            lvT.setAdapter(adapter);
                        } else {
                            date.addAll(base.result);
                            adapter.notifyDataSetChanged();
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
