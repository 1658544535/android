//package com.qunyu.taoduoduo.fragment;
//
//import android.content.Context;
//import android.os.Bundle;
//import android.os.Handler;
//import android.support.v4.app.DialogFragment;
//import android.util.DisplayMetrics;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.view.Window;
//import android.widget.AdapterView;
//import android.widget.ArrayAdapter;
//import android.widget.EditText;
//import android.widget.ListView;
//import android.widget.PopupWindow;
//import android.widget.Spinner;
//import android.widget.TextView;
//
//import com.andbase.library.http.AbHttpUtil;
//import com.andbase.library.http.listener.AbStringHttpResponseListener;
//import com.andbase.library.http.model.AbResult;
//import com.andbase.library.util.AbToastUtil;
//import com.blankj.utilcode.utils.StringUtils;
//import com.google.gson.Gson;
//import com.google.gson.reflect.TypeToken;
//import com.qunyu.taoduoduo.R;
//import com.qunyu.taoduoduo.activity.MyAddressActivity;
//import com.qunyu.taoduoduo.api.AddAddressApi;
//import com.qunyu.taoduoduo.api.EditAddressApi;
//import com.qunyu.taoduoduo.base.BaseModel;
//import com.qunyu.taoduoduo.bean.MyAddressBean;
//import com.qunyu.taoduoduo.bean.TddAreaModel;
//import com.qunyu.taoduoduo.bean.TddCityModel;
//import com.qunyu.taoduoduo.bean.TddProvinceModel;
//import com.qunyu.taoduoduo.global.Untool;
//
//import org.json.JSONException;
//import org.json.JSONObject;
//
//import java.io.BufferedReader;
//import java.io.IOException;
//import java.io.InputStreamReader;
//import java.lang.reflect.Type;
//import java.util.ArrayList;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//
//import butterknife.BindView;
//import butterknife.ButterKnife;
//
///**
// * Created by Administrator on 2016/10/5.
// * 添加地址弹窗
// */
//
//
//public class CaiAddressDialogFragment extends DialogFragment implements View.OnClickListener {
//    @BindView(R.id.sp_address1)
//    Spinner spinnerText1;
//    @BindView(R.id.sp_address2)
//    Spinner spinnerText2;
//    @BindView(R.id.sp_address3)
//    Spinner spinnerText3;
//    @BindView(R.id.btn_commit)
//    TextView btn_commit;
//    @BindView(R.id.edt_shouhuoren)
//    EditText edt_shouhuoren;
//    @BindView(R.id.edt_lianxifangshi)
//    EditText edt_lianxifangshi;
//    @BindView(R.id.edt_addressDetial)
//    EditText edt_addressDetial;
//    @BindView(R.id.tv_address1)
//    TextView tv_address1;
//    @BindView(R.id.tv_address2)
//    TextView tv_address2;
//    @BindView(R.id.tv_address3)
//    TextView tv_address3;
//
//    private ArrayAdapter<String> adapter, adapter1, adapter2;
//
//    public static Handler mhandler;
//    View view, sp_layout;
//    LayoutInflater inflater;
//    PopupWindow sp_window;
//    ListView lv_popuWindow;
//
//    protected String[] mProvinceDatas;//所有省
//    String[] cityNames;
//    String[] areaNames;
//
//    protected Map<String, String[]> mCitisDatasMap = new HashMap<>();//省
//    protected Map<String, String[]> mAreaDatasMap = new HashMap<>();//市
//    protected Map<String, Integer> map = new HashMap<>();//区
//    protected Map<Integer, String> stringMap = new HashMap<>();
//    protected Map<Integer, Integer> positionMap = new HashMap<>();//地区id---position
//
//    protected String mCurrentProviceName;//当前省名称
//    protected String mCurrentCityName;//当前市名称
//    protected String mCurrentAreaName;//当前区名称
//
//    protected Integer provinceId;
//    protected Integer cityId;
//    protected Integer areaId;
//
//    private Integer addId = null;//地址id
//    private Integer mCurrentPosition = null;//当前地址item
//    private ArrayList<MyAddressBean> mlist;
//    private MyAddressBean bean;
//
//
//    private Integer isDefault;
//    private int index;
//    private final static String fileName = "province.json";
//
//    //地址信息
//    private AddAddressApi addAddressApi;
//    private EditAddressApi editAddressApi;
//
//    String text_shouhuoren, text_lianxifangshi, text_province, text_city, text_area;
//    String text_detailAddress;
//
//
//    @Override
//    public void onStart() {
//        super.onStart();
////        if (dialog != null) {
//        DisplayMetrics dm = new DisplayMetrics();
//        getActivity().getWindowManager().getDefaultDisplay().getMetrics(dm);
//        getDialog().getWindow().setBackgroundDrawableResource(R.drawable.shap_corner_address);
//        getDialog().getWindow().setLayout((int) (dm.widthPixels * 0.87), (int) (dm.heightPixels * 0.37));
//
////        }
//    }
//
//    @Override
//    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
//        getDialog().requestWindowFeature(Window.FEATURE_NO_TITLE);
//        view = inflater.inflate(R.layout.addaddress_dialog, null);
//        ButterKnife.bind(this, view);
//        mlist = new ArrayList<>();
//        initList();
//        init();
//        return view;
//    }
//
//
//    private void init() {
//        mCurrentPosition = MyAddressActivity.mCurrentPosition;
//        adapter = new ArrayAdapter<String>(getActivity(), R.layout.address_textitem, mProvinceDatas);
//        spinnerText1.setAdapter(adapter);
//
//
//        spinnerText1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//            @Override
//            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//                adapter1 = new ArrayAdapter<String>(getActivity(), R.layout.address_textitem
//                        , mCitisDatasMap.get(mProvinceDatas[position]));
//                spinnerText2.setAdapter(adapter1);
//                provinceId = map.get(mProvinceDatas[position]);
//                cityNames = mCitisDatasMap.get(mProvinceDatas[position]);
//
//                if (mCurrentPosition != null) {
//                    tv_address1.setText(stringMap.get(bean.getProvince()));
//                } else {
//                    tv_address1.setText(mProvinceDatas[position]);
//                }
//            }
//
//            @Override
//            public void onNothingSelected(AdapterView<?> parent) {
//
//            }
//        });
//        spinnerText2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//            @Override
//            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//                adapter2 = new ArrayAdapter<String>(getActivity(), R.layout.address_textitem
//                        , mAreaDatasMap.get(cityNames[position]));
//                spinnerText3.setAdapter(adapter2);
//                cityId = map.get(cityNames[position]);
//                areaNames = mAreaDatasMap.get(cityNames[position]);
//
//                if (mCurrentPosition != null) {
//                    tv_address2.setText(stringMap.get(bean.getCity()));
//                } else {
//                    tv_address2.setText(cityNames[position]);
//                }
//
//            }
//
//            @Override
//            public void onNothingSelected(AdapterView<?> parent) {
//
//            }
//        });
//        spinnerText3.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//            @Override
//            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//                areaId = map.get(areaNames[position]);
//                positionMap.put(areaId, position);
//                if (mCurrentPosition != null) {
//                    tv_address3.setText(stringMap.get(bean.getArea()));
//                    mCurrentPosition = null;
//                    MyAddressActivity.mCurrentPosition = null;
//                } else {
//                    tv_address3.setText(areaNames[position]);
//                }
//            }
//
//            @Override
//            public void onNothingSelected(AdapterView<?> parent) {
//
//            }
//        });
//
//        if (mCurrentPosition != null) {
//            mlist.clear();
//            bean = null;
//            mlist = MyAddressActivity.list;
//            bean = mlist.get(mCurrentPosition);
//
//            isDefault = bean.getIsDefault();
//            addId = Integer.parseInt(bean.getAddId());
//
//            edt_shouhuoren.setText(bean.getName());
//            edt_lianxifangshi.setText(bean.getTel());
//
//            String province, city, area, addressDetail;
//
//            //详情地址设置
//            province = stringMap.get(bean.getProvince());
//            city = stringMap.get(bean.getCity());
//            area = stringMap.get(bean.getArea());
//            addressDetail = bean.getAddress();
//            addressDetail = addressDetail.replaceAll("" + province + city + area, "");
//            edt_addressDetial.setText(addressDetail);
//        }
//
//        //提交按钮
//        btn_commit.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                text_shouhuoren = edt_shouhuoren.getText().toString().trim();
//                text_lianxifangshi = edt_lianxifangshi.getText().toString().trim();
//                text_province = provinceId + "";
//                text_city = cityId + "";
//                text_area = areaId + "";
//                text_detailAddress = edt_addressDetial.getText().toString().trim();
//                if (text_shouhuoren != "" && text_lianxifangshi != "" && text_province != ""
//                        && text_city != "" && text_area != "" && text_detailAddress != "") {
//                    if (addId == null) {
//                        commitAddress();
//                    } else {
//                        edtAddress();
//                    }
////                    Intent intent = new Intent(getActivity(), ConfirmOrderActivity.class);
////                    intent.putExtra("activityId", CaiGoodsDetailActivity.activityId);
////                    intent.putExtra("num", "1");
////                    intent.putExtra("pid", CaiGoodsDetailActivity.productId);
////                    intent.putExtra("skuLinkId", "");
////                    intent.putExtra("source", "3");
////                    startActivity(intent);
//                } else {
//                    ToastUtils.showShortToast(getActivity(), "填写的地址信息不完整！");
//                }
//            }
//        });
//
//    }
//
//    //提交地址
//    private void commitAddress() {
//        addAddressApi = new AddAddressApi();
//        addAddressApi.setAddress(text_detailAddress);
//        addAddressApi.setArea(text_area);
//        addAddressApi.setCity(text_city);
//        addAddressApi.setIsDefault("0");//默认地址（取业务字典：0否1是）
//        addAddressApi.setName(text_shouhuoren);
//        addAddressApi.setPostCode("");
//        addAddressApi.setProvince(text_province);
//        addAddressApi.setTel(text_lianxifangshi);
//        addAddressApi.setUid(Untool.getUid());
//        AbHttpUtil.getInstance(getActivity()).get(addAddressApi.getUrl(), addAddressApi.getParamMap(), new AbStringHttpResponseListener() {
//            @Override
//            public void onSuccess(int statusCode, String content) {
//                AbResult result = new AbResult();
//                if (result.getResultCode() == AbResult.RESULT_OK) {
//                    try {
//                        JSONObject json = new JSONObject(content);
//                        ToastUtils.showShortToast(getActivity(), json.optString("error_msg"));
//                        if (json.optBoolean("success")) {
//                            Dismiss();
//                        }
//                    } catch (JSONException e) {
//                        e.printStackTrace();
//                    }
//                }
//            }
//
//            @Override
//            public void onStart() {
//
//            }
//
//            @Override
//            public void onFinish() {
//
//            }
//
//            @Override
//            public void onFailure(int i, String s, Throwable error) {
//                ToastUtils.showShortToast(getActivity(), "网络异常，数据加载失败");
//                Dismiss();
//            }
//        });
//    }
//
//    //修改地址
//    private void edtAddress() {
//        editAddressApi = new EditAddressApi();
//        editAddressApi.setAddId(addId + "");
//        editAddressApi.setAddress(text_detailAddress);
//        editAddressApi.setArea(text_area);
//        editAddressApi.setCity(text_city);
//        editAddressApi.setIsDefault(isDefault + "");
//        editAddressApi.setName(text_shouhuoren);
//        editAddressApi.setPostCode("");
//        editAddressApi.setProvince(text_province);
//        editAddressApi.setTel(text_lianxifangshi);
//        editAddressApi.setUid(Untool.getUid());
//        AbHttpUtil.getInstance(getActivity()).get(editAddressApi.getUrl(), editAddressApi.getParamMap(), new AbStringHttpResponseListener() {
//            @Override
//            public void onSuccess(int i, String content) {
//                AbResult result = new AbResult();
//                if (result.getResultCode() == AbResult.RESULT_OK) {
//                    try {
//                        JSONObject json = new JSONObject(content);
//                        ToastUtils.showShortToast(getActivity(), json.optString("error_msg"));
//                        if (json.optBoolean("success")) {
//
//                            Dismiss();
//                        }
//                    } catch (JSONException e) {
//                        e.printStackTrace();
//                    }
//                }
//            }
//
//            @Override
//            public void onStart() {
//
//            }
//
//            @Override
//            public void onFinish() {
//
//            }
//
//            @Override
//            public void onFailure(int i, String s, Throwable error) {
//                ToastUtils.showShortToast(getActivity(), "网络异常，数据加载失败");
//            }
//        });
//    }
//
//
//    //三级省市区数据获取
//    private void initList() {
//        String jsonStr = geFileFromAssets(getActivity(), fileName);
//        Gson gson = new Gson();
//        Type type = new TypeToken<BaseModel<List<TddProvinceModel>>>() {
//        }.getType();
//        BaseModel<List<TddProvinceModel>> base = gson.fromJson(jsonStr, type);
//        if (base.success && base.result != null) {
//            List<TddProvinceModel> provinceModels = base.result;
//            // */ 初始化默认选中的省、市、区
//            if (provinceModels != null && !provinceModels.isEmpty()) {
//                mCurrentProviceName = provinceModels.get(0).provincState;
//                provinceId = provinceModels.get(0).provinceId;
//                List<TddCityModel> cityList = provinceModels.get(0).cities;
//                if (cityList != null && !cityList.isEmpty()) {
//                    mCurrentCityName = cityList.get(0).cityState;
//                    cityId = cityList.get(0).cityId;
//                    List<TddAreaModel> districtList = cityList.get(0).areas;
//                    mCurrentAreaName = districtList.get(0).areaState;
//                    areaId = districtList.get(0).areaId;
//                    // mCurrentZipCode = districtList.get(0).getZipcode();
//                }
//            }
//            // */
//            mProvinceDatas = new String[provinceModels.size()];
//            for (int i = 0; i < provinceModels.size(); i++) {
//                // 遍历所有省的数据
//                mProvinceDatas[i] = provinceModels.get(i).provincState;
//                map.put(provinceModels.get(i).provincState,
//                        provinceModels.get(i).provinceId);
//                stringMap.put(provinceModels.get(i).provinceId, provinceModels.get(i).provincState);
//                List<TddCityModel> cityList = provinceModels.get(i).cities;
//                cityNames = new String[cityList.size()];
//                for (int j = 0; j < cityList.size(); j++) {
//                    // 遍历省下面的所有市的数据
//                    cityNames[j] = cityList.get(j).cityState;
//                    map.put(cityNames[j], cityList.get(j).cityId);
//                    stringMap.put(cityList.get(j).cityId, cityNames[j]);
//                    List<TddAreaModel> areaModels = cityList.get(j).areas;
//                    areaNames = new String[areaModels.size()];
//                    for (int k = 0; k < areaModels.size(); k++) {
//                        // 遍历市下面所有区/县的数据
//                        areaNames[k] = areaModels.get(k).areaState;
//                        map.put(areaModels.get(k).areaState,
//                                areaModels.get(k).areaId);
//                        stringMap.put(areaModels.get(k).areaId,
//                                areaModels.get(k).areaState);
//                        // TzmAreaModel districtModel = new TzmAreaModel();
//                        // 区/县对于的邮编，保存到mZipcodeDatasMap
//                        // mZipcodeDatasMap.put(districtList.get(k).getName(),
//                        // districtList.get(k).getZipcode());
//                        // distrinctArray[k] = districtModel;
//                        // distrinctNameArray[k] = districtModel.areaState;
//                    }
//                    // 市-区/县的数据，保存到mDistrictDatasMap
//                    mAreaDatasMap.put(cityNames[j], areaNames);
//                }
//                // 省-市的数据，保存到mCitisDatasMap
//                mCitisDatasMap.put(provinceModels.get(i).provincState,
//                        cityNames);
//
//            }
//        }
//
//    }
//
//    //读取assets文件夹内文件
//    public static String geFileFromAssets(Context context, String fileName) {
//        if (context == null || StringUtils.isEmpty(fileName)) {
//            return null;
//        }
//        StringBuilder s = new StringBuilder("");
//        try {
//            InputStreamReader in = new InputStreamReader(context.getResources()
//                    .getAssets().open(fileName));
//            BufferedReader br = new BufferedReader(in);
//            String line;
//            while ((line = br.readLine()) != null) {
//                s.append(line);
//            }
//            return s.toString();
//        } catch (IOException e) {
//            e.printStackTrace();
//            return null;
//        }
//    }
//
//    private void Dismiss() {
////        Message msg = Message.obtain();
////        msg.what = 2;
////        MyAddressActivity.handler.sendMessage(msg);
//        this.dismiss();
//    }
//
//    @Override
//    public void onClick(View v) {
//        switch (v.getId()) {
//            case R.id.tv_address1:
//                spinnerText1.performClick();
//                break;
//            case R.id.tv_address2:
//                spinnerText2.performClick();
//                break;
//            case R.id.tv_address3:
//                spinnerText3.performClick();
//                break;
//        }
//    }
//}
