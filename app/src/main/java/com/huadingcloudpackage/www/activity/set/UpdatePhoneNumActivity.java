package com.huadingcloudpackage.www.activity.set;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.huadingcloudpackage.www.R;
import com.huadingcloudpackage.www.base.BaseActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @author lige
 * 描述：更换手机号
 */
public class UpdatePhoneNumActivity extends BaseActivity {
    @BindView(R.id.img_back)
    ImageView imgBack;
    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.img_right)
    ImageView imgRight;
    @BindView(R.id.view_red_)
    View viewRed;
    @BindView(R.id.tv_right)
    TextView tvRight;
    @BindView(R.id.toolbar)
    LinearLayout toolbar;
    @BindView(R.id.tv_old_phonenum)
    TextView tvOldPhonenum;
    @BindView(R.id.tv_text)
    TextView tvText;
    @BindView(R.id.et_input_code)
    EditText etInputCode;
    @BindView(R.id.tv_get_code)
    TextView tvGetCode;
    @BindView(R.id.btn_commit)
    Button btnCommit;

    @Override
    public int getLayoutResId() {
        return R.layout.activity_update_phonenum;
    }

    @Override
    public void initView(Bundle savedInstanceState) {
        initToolbar();
        setBackBtn();
        setTitle("更换手机号");
    }

    @OnClick({R.id.tv_get_code, R.id.btn_commit})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_get_code:
                break;
            case R.id.btn_commit:
                break;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }
}
