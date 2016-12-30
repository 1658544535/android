package com.qunyu.taoduoduo.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.qunyu.taoduoduo.R;
import com.qunyu.taoduoduo.utils.LogUtil;
import com.qunyu.taoduoduo.utils.StringUtils;
import com.qunyu.taoduoduo.utils.ToastUtils;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2016/12/12.
 */

public class ResetNameActivity extends Activity {
    String TAG = "ResetNameActivity";
    public static final String USER_NAME = "userName";//

    @BindView(R.id.iv_head_left)
    ImageView iv_head_left;
    @BindView(R.id.tv_head_right)
    TextView tv_head_right;

    private TextView txt_head_title;
    private Button btn_head_left, btn_head_right;
    private EditText et_name;
    private String userName = "";
    String digits = "/\\:*?<>|\"\n\t";
    private Intent resultIntent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.new_t_reset_name_activity);
        ButterKnife.bind(this);
        LogUtil.Log(TAG, "onCreate");
        resultIntent = getIntent();
        if (resultIntent.getStringExtra(USER_NAME) != null) {
            userName = resultIntent.getStringExtra(USER_NAME);
        }
        initView();
    }

    private void initView() {
        iv_head_left.setOnClickListener(clickListener);
        tv_head_right.setOnClickListener(clickListener);
        tv_head_right.setText("保存");
        tv_head_right.setVisibility(View.VISIBLE);
        et_name = (EditText) findViewById(R.id.et_name);
        if (!userName.equals("")) {
            et_name.setText(userName);
        }
    }

    View.OnClickListener clickListener = new View.OnClickListener() {

        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.iv_head_left:
                    onBackPressed();
                    break;
                case R.id.tv_head_right:

                    if (et_name.getText().toString().length() < 1
                            || et_name.getText().toString().length() > 14) {
                        ToastUtils.showShortToast(ResetNameActivity.this,
                                "昵称长度不符合要求！");
                        return;
                    }
//                    if (!StringUtils.isnc(et_name.getText().toString())) {
//                        ToastUtils.showShortToast(ResetNameActivity.this,
//                                "昵称不符合要求！");
//                        return;
//                    }
                    if (StringUtils.isNotEmpty(et_name.getText().toString())) {
                        userName = et_name.getText().toString();
                        resultIntent.putExtra(USER_NAME, userName);
                        System.out.println("user_name  " + userName);
                        setResult(Activity.RESULT_OK, resultIntent);
                        finish();
                    } else {
                        ToastUtils.showShortToast(ResetNameActivity.this, "请输入昵称！");
                    }
                    break;
            }
        }
    };

}
