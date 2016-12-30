package com.qunyu.taoduoduo.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.andbase.library.http.AbHttpUtil;
import com.andbase.library.http.listener.AbStringHttpResponseListener;
import com.andbase.library.http.model.AbRequestParams;
import com.andbase.library.http.model.AbResult;
import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.qunyu.taoduoduo.R;
import com.qunyu.taoduoduo.base.BaseActivity;
import com.qunyu.taoduoduo.base.BaseModel;
import com.qunyu.taoduoduo.global.Constant;
import com.qunyu.taoduoduo.global.Untool;
import com.qunyu.taoduoduo.utils.LogUtil;
import com.qunyu.taoduoduo.utils.ToastUtils;
import com.umeng.analytics.MobclickAgent;
import com.zhy.android.percent.support.PercentLinearLayout;
import com.zhy.android.percent.support.PercentRelativeLayout;

import java.io.File;
import java.lang.reflect.Type;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import me.nereo.multi_image_selector.MultiImageSelectorActivity;

public class SaiDanActivity extends BaseActivity {

    @BindView(R.id.iv_logo)
    ImageView ivLogo;
    @BindView(R.id.tv_name)
    TextView tvName;
    @BindView(R.id.editText)
    EditText editText;
    @BindView(R.id.tv_sz)
    TextView tvSz;
    @BindView(R.id.iv_01)
    ImageView iv01;
    @BindView(R.id.pl_01)
    PercentLinearLayout pl01;
    @BindView(R.id.iv_02)
    ImageView iv02;
    @BindView(R.id.pl_02)
    PercentLinearLayout pl02;
    @BindView(R.id.iv_03)
    ImageView iv03;
    @BindView(R.id.pl_03)
    PercentLinearLayout pl03;
    @BindView(R.id.iv_04)
    ImageView iv04;
    @BindView(R.id.pl_04)
    PercentLinearLayout pl04;
    @BindView(R.id.iv_05)
    ImageView iv05;
    @BindView(R.id.pl_05)
    PercentLinearLayout pl05;
    @BindView(R.id.iv_06)
    ImageView iv06;
    @BindView(R.id.pl_06)
    PercentLinearLayout pl06;
    @BindView(R.id.iv_07)
    ImageView iv07;
    @BindView(R.id.pl_07)
    PercentLinearLayout pl07;
    @BindView(R.id.iv_08)
    ImageView iv08;
    @BindView(R.id.pl_08)
    PercentLinearLayout pl08;
    @BindView(R.id.iv_09)
    ImageView iv09;
    @BindView(R.id.pl_09)
    PercentLinearLayout pl09;
    @BindView(R.id.hs_1)
    HorizontalScrollView hs1;
    @BindView(R.id.tv_tj)
    TextView tvTj;
    @BindView(R.id.activity_sai_dan)
    PercentRelativeLayout activitySaiDan;
    String activityId, pid, image, name, AttendId;
    int p1 = -1, p2 = -1, p3 = -1, p4 = -1, p5 = -1, p6 = -1, p7 = -1, p8 = -1, p9 = -1, pp = 10;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AttendId = getIntent().getStringExtra("AttendId");
        activityId = getIntent().getStringExtra("activityId");
        pid = getIntent().getStringExtra("pid");
        image = getIntent().getStringExtra("image");
        name = getIntent().getStringExtra("name");
        baseSetContentView(R.layout.activity_sai_dan);
        baseSetText("我要晒图");
        ButterKnife.bind(this);
        Glide.with(SaiDanActivity.this).load(image).placeholder(R.mipmap.default_load_long).error(R.mipmap.default_load_long).into(ivLogo);
        tvName.setText(name);
        editText.addTextChangedListener(mTextWatcher);
    }
    public void onResume() {
        super.onResume();
        MobclickAgent.onResume(this);
    }
    public void onPause() {
        super.onPause();
        MobclickAgent.onPause(this);
    }
    TextWatcher mTextWatcher = new TextWatcher() {
        private CharSequence temp;
        private int editStart;
        private int editEnd;

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            // TODO Auto-generated method stub
            temp = s;
        }

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count,
                                      int after) {
            // TODO Auto-generated method stub
//          mTextView.setText(s);//将输入的内容实时显示
        }

        @Override
        public void afterTextChanged(Editable s) {
            // TODO Auto-generated method stub
            editStart = editText.getSelectionStart();
            editEnd = editText.getSelectionEnd();
            tvSz.setText(500 - temp.length() + "");
            if (temp.length() > 500) {
                Toast.makeText(SaiDanActivity.this,
                        "你输入的字数已经超过了限制！", Toast.LENGTH_SHORT)
                        .show();
                s.delete(editStart - 1, editEnd);
                int tempSelection = editStart;
                editText.setText(s);
                editText.setSelection(tempSelection);
            }
        }
    };

    private void GetDrawCommentDetailsApi() {
        AbRequestParams params = new AbRequestParams();
        params.put("attendId", AttendId);
        params.put("content", editText.getText() + "");
        try {
            params.put("img1", new File(pl01.getTag().toString()));
            params.put("img2", new File(pl02.getTag().toString()));
            params.put("img3", new File(pl03.getTag().toString()));
            params.put("img4", new File(pl04.getTag().toString()));
            params.put("img5", new File(pl05.getTag().toString()));
            params.put("img6", new File(pl06.getTag().toString()));
            params.put("img7", new File(pl07.getTag().toString()));
            params.put("img8", new File(pl08.getTag().toString()));
            params.put("img9", new File(pl09.getTag().toString()));
        } catch (Exception e) {
        }
        params.put("userId", Untool.getUid());
        AbHttpUtil.getInstance(SaiDanActivity.this).post(Constant.actProductComment, params, new AbStringHttpResponseListener() {
            @Override
            public void onStart() {
//                AbDialogUtil.showProgressDialog(LoginActivity.this, 0, "正在登录...");

            }

            @Override
            public void onFinish() {
//                AbDialogUtil.removeDialog(LoginActivity.this);
            }

            @Override
            public void onFailure(int statusCode, String content, Throwable error) {
                ToastUtils.showShortToast(SaiDanActivity.this, "网络异常，数据加载失败");
                LogUtil.Log(error.getMessage());
            }

            @Override
            public void onSuccess(int statusCode, String content) {
                AbResult result = new AbResult(content);
                if (result.getResultCode() == AbResult.RESULT_OK) {
                    Gson gson = new Gson();
                    Type type = new TypeToken<BaseModel<String>>() {
                    }.getType();
                    BaseModel<String> base = gson.fromJson(content, type);
                    LogUtil.Log(content);
                    if (base.success) {
                        Intent intent = new Intent(SaiDanActivity.this, PinLunSuActivity.class);
                        intent.putExtra("activityId", activityId);
                        startActivity(intent);
                        finish();
                        ToastUtils.showShortToast(SaiDanActivity.this, base.error_msg);
                    } else {
                        ToastUtils.showShortToast(SaiDanActivity.this, base.error_msg);
                    }
                } else {
                    ToastUtils.showShortToast(SaiDanActivity.this, "网络异常，数据加载失败");
                    LogUtil.Log(result.getResultMessage());
                }

            }
        });

    }

    int REQUEST_IMAGE = 6666;

    private void SetMu(int st) {
        if (st == -1) {
            Intent intent = new Intent(SaiDanActivity.this, MultiImageSelectorActivity.class);
            // 是否显示调用相机拍照
            intent.putExtra(MultiImageSelectorActivity.EXTRA_SHOW_CAMERA, true);
            // 最大图片选择数量
            intent.putExtra(MultiImageSelectorActivity.EXTRA_SELECT_COUNT, 9);
            // 设置模式 (支持 单选/MultiImageSelectorActivity.MODE_SINGLE 或者 多选/MultiImageSelectorActivity.MODE_MULTI)
            intent.putExtra(MultiImageSelectorActivity.EXTRA_SELECT_MODE, MultiImageSelectorActivity.MODE_MULTI);
            startActivityForResult(intent, REQUEST_IMAGE);
            pp = 10;
        } else {
            Intent intent = new Intent(SaiDanActivity.this, MultiImageSelectorActivity.class);
            // 是否显示调用相机拍照
            intent.putExtra(MultiImageSelectorActivity.EXTRA_SHOW_CAMERA, true);
            // 最大图片选择数量
//            intent.putExtra(MultiImageSelectorActivity.EXTRA_SELECT_COUNT, 9);
            // 设置模式 (支持 单选/MultiImageSelectorActivity.MODE_SINGLE 或者 多选/MultiImageSelectorActivity.MODE_MULTI)
            intent.putExtra(MultiImageSelectorActivity.EXTRA_SELECT_MODE, MultiImageSelectorActivity.MODE_SINGLE);
            startActivityForResult(intent, REQUEST_IMAGE);
            pp = st;
        }
    }

    List<String> path = null;
    List<String> path1;
    int i = 1;

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_IMAGE) {
            if (resultCode == RESULT_OK) {
                // 获取返回的图片列表
                if (pp == 10) {
                    if (i == 1) {
                        path = data.getStringArrayListExtra(MultiImageSelectorActivity.EXTRA_RESULT);
                        i = 0;
                    } else {
                        path.addAll(data.getStringArrayListExtra(MultiImageSelectorActivity.EXTRA_RESULT));
                    }
                } else {
                    path1 = data.getStringArrayListExtra(MultiImageSelectorActivity.EXTRA_RESULT);
                }
                // 处理你自己的逻辑 ....
                if (pp == 10) {
                    try {
                        Glide.with(SaiDanActivity.this).load(path.get(0)).placeholder(R.mipmap.default_load_long).error(R.mipmap.default_load_long).into(iv01);
                        pl01.setTag(path.get(0));
                        p1 = 1;
                        pl02.setVisibility(View.VISIBLE);
                        Glide.with(SaiDanActivity.this).load(path.get(1)).placeholder(R.mipmap.default_load_long).error(R.mipmap.default_load_long).into(iv02);
                        pl02.setTag(path.get(1));
                        p2 = 2;
                        pl03.setVisibility(View.VISIBLE);
                        Glide.with(SaiDanActivity.this).load(path.get(2)).placeholder(R.mipmap.default_load_long).error(R.mipmap.default_load_long).into(iv03);
                        pl03.setTag(path.get(2));
                        p3 = 3;
                        pl04.setVisibility(View.VISIBLE);
                        Glide.with(SaiDanActivity.this).load(path.get(3)).placeholder(R.mipmap.default_load_long).error(R.mipmap.default_load_long).into(iv04);
                        pl04.setTag(path.get(3));
                        p4 = 4;
                        pl05.setVisibility(View.VISIBLE);
                        Glide.with(SaiDanActivity.this).load(path.get(4)).placeholder(R.mipmap.default_load_long).error(R.mipmap.default_load_long).into(iv05);
                        pl05.setTag(path.get(4));
                        p5 = 5;
                        pl06.setVisibility(View.VISIBLE);
                        Glide.with(SaiDanActivity.this).load(path.get(5)).placeholder(R.mipmap.default_load_long).error(R.mipmap.default_load_long).into(iv06);
                        pl06.setTag(path.get(5));
                        p6 = 6;
                        pl07.setVisibility(View.VISIBLE);
                        Glide.with(SaiDanActivity.this).load(path.get(6)).placeholder(R.mipmap.default_load_long).error(R.mipmap.default_load_long).into(iv07);
                        pl07.setTag(path.get(6));
                        p7 = 7;
                        pl08.setVisibility(View.VISIBLE);
                        Glide.with(SaiDanActivity.this).load(path.get(7)).placeholder(R.mipmap.default_load_long).error(R.mipmap.default_load_long).into(iv08);
                        pl08.setTag(path.get(7));
                        p8 = 8;
                        pl09.setVisibility(View.VISIBLE);
                        Glide.with(SaiDanActivity.this).load(path.get(8)).placeholder(R.mipmap.default_load_long).error(R.mipmap.default_load_long).into(iv09);
                        pl09.setTag(path.get(8));
                        p9 = 9;
                    } catch (Exception e) {
                    }
                } else {
                    switch (pp) {
                        case 1:
                            Glide.with(SaiDanActivity.this).load(path1.get(0)).placeholder(R.mipmap.default_load_long).error(R.mipmap.default_load_long).into(iv01);
                            pl01.setTag(path1.get(0));
                            break;
                        case 2:
                            Glide.with(SaiDanActivity.this).load(path1.get(0)).placeholder(R.mipmap.default_load_long).error(R.mipmap.default_load_long).into(iv02);
                            pl02.setTag(path1.get(0));
                            break;
                        case 3:
                            Glide.with(SaiDanActivity.this).load(path1.get(0)).placeholder(R.mipmap.default_load_long).error(R.mipmap.default_load_long).into(iv03);
                            pl03.setTag(path1.get(0));
                            break;
                        case 4:
                            Glide.with(SaiDanActivity.this).load(path1.get(0)).placeholder(R.mipmap.default_load_long).error(R.mipmap.default_load_long).into(iv04);
                            pl04.setTag(path1.get(0));
                            break;
                        case 5:
                            Glide.with(SaiDanActivity.this).load(path1.get(0)).placeholder(R.mipmap.default_load_long).error(R.mipmap.default_load_long).into(iv05);
                            pl05.setTag(path1.get(0));
                            break;
                        case 6:
                            Glide.with(SaiDanActivity.this).load(path1.get(0)).placeholder(R.mipmap.default_load_long).error(R.mipmap.default_load_long).into(iv06);
                            pl06.setTag(path1.get(0));
                            break;
                        case 7:
                            Glide.with(SaiDanActivity.this).load(path1.get(0)).placeholder(R.mipmap.default_load_long).error(R.mipmap.default_load_long).into(iv07);
                            pl07.setTag(path1.get(0));
                            break;
                        case 8:
                            Glide.with(SaiDanActivity.this).load(path1.get(0)).placeholder(R.mipmap.default_load_long).error(R.mipmap.default_load_long).into(iv08);
                            pl08.setTag(path1.get(0));
                            break;
                        case 9:
                            Glide.with(SaiDanActivity.this).load(path1.get(0)).placeholder(R.mipmap.default_load_long).error(R.mipmap.default_load_long).into(iv09);
                            pl09.setTag(path1.get(0));
                            break;
                    }
                }
            }
        }
    }

    @OnClick({R.id.pl_01, R.id.pl_02, R.id.pl_03, R.id.pl_04, R.id.pl_05, R.id.pl_06, R.id.pl_07, R.id.pl_08, R.id.pl_09, R.id.tv_tj})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.pl_01:
                SetMu(p1);
                break;
            case R.id.pl_02:
                SetMu(p2);
                break;
            case R.id.pl_03:
                SetMu(p3);
                break;
            case R.id.pl_04:
                SetMu(p4);
                break;
            case R.id.pl_05:
                SetMu(p5);
                break;
            case R.id.pl_06:
                SetMu(p6);
                break;
            case R.id.pl_07:
                SetMu(p7);
                break;
            case R.id.pl_08:
                SetMu(p8);
                break;
            case R.id.pl_09:
                SetMu(p9);
                break;
            case R.id.tv_tj:
                if (editText.getText().toString().length() != 0) {
                    GetDrawCommentDetailsApi();
                } else {
                    ToastUtils.showShortToast(SaiDanActivity.this, "评论内容不能为空！");
                }
                break;
        }
    }

}
