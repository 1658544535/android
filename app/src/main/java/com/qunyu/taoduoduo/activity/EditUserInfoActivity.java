package com.qunyu.taoduoduo.activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.andbase.library.http.AbHttpUtil;
import com.andbase.library.http.listener.AbStringHttpResponseListener;
import com.andbase.library.http.model.AbRequestParams;
import com.andbase.library.http.model.AbResult;
import com.bumptech.glide.Glide;
import com.qunyu.taoduoduo.R;
import com.qunyu.taoduoduo.global.Constant;
import com.qunyu.taoduoduo.utils.LogUtil;
import com.qunyu.taoduoduo.utils.StringUtils;
import com.qunyu.taoduoduo.utils.ToastUtils;
import com.qunyu.taoduoduo.utils.UserInfoUtils;
import com.qunyu.taoduoduo.view.CircleTransform;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Administrator on 2016/12/12.
 */

public class EditUserInfoActivity extends Activity implements View.OnClickListener {

    @BindView(R.id.tv_name)
    TextView tv_name;
    @BindView(R.id.iv_photo)
    ImageView iv_photo;
    @BindView(R.id.iv_head_left)
    ImageView iv_left;
    @BindView(R.id.tv_head_right)
    TextView tv_right;

    String name;
    String photo_url;
    String photo = null;

    ProgressDialog progressDialog;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edituserinfo_activity);
        ButterKnife.bind(this);
        name = getIntent().getStringExtra("name");
        photo_url = getIntent().getStringExtra("photo");
        init();
    }


    private void init() {
        tv_right.setText("保存");
        tv_right.setTextColor(getResources().getColor(R.color.red));
        loadMsg();
    }


    private void loadMsg() {
        if (StringUtils.isNotBlank(name)) {
            tv_name.setText(name);
        }
        Glide.with(this).load(photo_url).transform(new CircleTransform(this)).placeholder(R.mipmap.default_touxiang)
                .error(R.mipmap.default_touxiang).into(iv_photo);

    }

    //保存
    private void submit() {
        AbRequestParams params = new AbRequestParams();
        if (StringUtils.isNotBlank(photo)) {
            params.put("file", new File(photo_url));
        }
        params.put("name", name);
        params.put("uid", UserInfoUtils.GetUid());
        LogUtil.Log(Constant.editUserInfoApi + "?" + params.getParamString());
        AbHttpUtil.getInstance(this).get(Constant.editUserInfoApi, params, new AbStringHttpResponseListener() {

            @Override
            public void onStart() {
                showLoading("加载中...");
            }

            @Override
            public void onFinish() {
                hideLoading();
            }

            @Override
            public void onFailure(int i, String s, Throwable throwable) {
                ToastUtils.showShortToast(EditUserInfoActivity.this, "网络异常，加载数据失败");
                LogUtil.Log(throwable.getMessage());
            }

            @Override
            public void onSuccess(int i, String s) {
                AbResult result = new AbResult(s);
                if (result.getResultCode() == AbResult.RESULT_OK) {
                    try {
                        JSONObject jsonObject = new JSONObject(s);
                        ToastUtils.showShortToast(
                                EditUserInfoActivity.this,
                                jsonObject.getString("error_msg"));
                        if (jsonObject.getBoolean("success")) {
                            Intent intent = new Intent();
                            intent.putExtra("photo", photo_url);
                            intent.putExtra("name", name);
                            EditUserInfoActivity.this.setResult(Activity.RESULT_OK, intent);
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }

    private void showLoading(String msg) {
        if (progressDialog == null) {
            progressDialog = new ProgressDialog(this);
        }
        progressDialog.setMessage(msg);
        progressDialog.setCancelable(false);
        progressDialog.show();
    }

    private void hideLoading() {
        if (progressDialog != null && progressDialog.isShowing()) {
            progressDialog.dismiss();
        }
    }

    @Override
    @OnClick({R.id.layout_photo, R.id.layout_name, R.id.iv_head_left, R.id.tv_head_right})
    public void onClick(View v) {
        if (v.getId() == R.id.layout_photo) {
            Intent intent = new Intent(this, SelectPicActivity.class);
            intent.putExtra("imgId", R.id.iv_photo);
            startActivityForResult(intent, R.id.iv_photo);
        } else if (v.getId() == R.id.layout_name) {
            Intent intent = new Intent(this, ResetNameActivity.class);
            intent.putExtra("imgId", R.id.tv_name);
            startActivityForResult(intent, R.id.tv_name);
        } else if (v.getId() == R.id.iv_head_left) {
            onBackPressed();
        } else if (v.getId() == R.id.tv_head_right) {
            //保存按钮
            submit();
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == R.id.iv_photo) {
                ImageView img = (ImageView) findViewById(requestCode);
                img.setImageBitmap(null);
                String picPath = data
                        .getStringExtra(SelectPicActivity.KEY_PHOTO_PATH);
                LogUtil.Log(picPath);
                if (picPath != null && !StringUtils.isBlank(picPath)) {
                    LogUtil.Log(requestCode + "最终选择的图片=" + picPath);
//                    Bitmap bm = PictureUtil.getSmallBitmap(picPath);
//                    img.setImageBitmap(bm);
                    Glide.with(this).load(picPath).transform(new CircleTransform(this)).placeholder(R.mipmap.default_touxiang)
                            .error(R.mipmap.default_touxiang).into(img);
                    photo_url = picPath;
                    photo = picPath;
                }
            } else if (requestCode == R.id.tv_name) {
                if (StringUtils.isNotBlank(data.getStringExtra(ResetNameActivity.USER_NAME))) {
                    tv_name.setText(data.getStringExtra(ResetNameActivity.USER_NAME));
                    name = data.getStringExtra(ResetNameActivity.USER_NAME);
                }
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
}
