package com.huadingcloudpackage.www.activity.login;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.guoquan.yunpu.util.ComTools;
import com.huadingcloudpackage.www.R;
import com.huadingcloudpackage.www.base.BaseActivity;
import com.huadingcloudpackage.www.bean.BaseBean;
import com.huadingcloudpackage.www.http.UrlContent;
import com.huadingcloudpackage.www.sp.PreferenceManager;
import com.huadingcloudpackage.www.util.JsonUtils;
import com.huadingcloudpackage.www.util.MyUtils;
import com.huadingcloudpackage.www.util.T;
import com.huadingcloudpackage.www.widget.ClearEditText;
import com.huadingcloudpackage.www.widget.CountDownTimerButton;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 忘记密码
 */
public class ForGetPwdActivity extends BaseActivity {

    @BindView(R.id.edit_phone)
    ClearEditText editPhone;
    @BindView(R.id.edit_smscode)
    EditText editSmscode;
    @BindView(R.id.tv_get_code)
    CountDownTimerButton tvGetCode;
    @BindView(R.id.tv_confirm)
    TextView tvConfirm;

    @Override
    public int getLayoutResId() {
        return R.layout.activity_for_get_pwd;
    }

    @Override
    public void initView(Bundle savedInstanceState) {
        initToolbar();
        setTitle("找回密码");
        setBackBtn();
        editPhone.setText(PreferenceManager.instance().getPhoneNum());
    }

    @OnClick({R.id.tv_get_code, R.id.tv_confirm})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_get_code:
                if (isFastClick()){
                    return;
                }
                if (MyUtils.isEtEmpty(editPhone)) {
                    T.show(this, "请输入手机号");
                    return;
                }
                if (MyUtils.getEtText(editPhone).length() != 11) {
                    T.show(this, "请输入正确的11位手机号");
                    return;
                }
//                getCode();
                break;
            case R.id.tv_confirm:
                if (isFastClick()){
                    return;
                }
                if (MyUtils.isEtEmpty(editPhone)) {
                    T.show(this, "请输入手机号");
                    return;
                }
                if (MyUtils.getEtText(editPhone).length() != 11) {
                    T.show(this, "请输入正确的11位手机号");
                    return;
                }
                if (MyUtils.isEtEmpty(editSmscode)) {
                    T.show(this, "请输入短信验证码");
                    return;
                }
//                getNext();
                break;
        }
    }

//    private void getNext() {
//        showLoading();
//        OkGo.<String>get(UrlContent.VERIFICATION_MSG)
//                .tag(this)
//                .params("captcha",editSmscode.getText().toString())
//                .params("phone",editPhone.getText().toString())
//                .execute(new StringCallback() {
//                    @Override
//                    public void onSuccess(Response<String> response) {
//                        comTools.parsingReturnData(JsonUtils.parse(response.body(), BaseBean.class), new ComTools.OnParsingReturnListener() {
//                            @Override
//                            public void onParsingSuccess() {
//                                startActivity(new Intent(mContext,SetNewPwdActivity.class).putExtra("phone",editPhone.getText().toString()));
//                            }
//                        });
//                    }
//
//                    @Override
//                    public void onError(Response<String> response) {
//                        super.onError(response);
//                    }
//
//                    @Override
//                    public void onFinish() {
//                        super.onFinish();
//                        dissMissLoading();
//                    }
//                });
//    }
//
//    private void getCode() {
//        dissMissLoading();
//        OkGo.<String>get(UrlContent.SEND_MSG)
//                .tag(this)
//                .params("phone",editPhone.getText().toString())
//                .execute(new StringCallback() {
//                    @Override
//                    public void onSuccess(Response<String> response) {
//                        comTools.parsingReturnData(JsonUtils.parse(response.body(), BaseBean.class), new ComTools.OnParsingReturnListener() {
//                            @Override
//                            public void onParsingSuccess() {
//                                T("验证码已发送");
//                                tvGetCode.startTimer();//发送验证码后开始计时
//                            }
//                        });
//                    }
//
//                    @Override
//                    public void onError(Response<String> response) {
//                        super.onError(response);
//                    }
//
//                    @Override
//                    public void onFinish() {
//                        super.onFinish();
//                        dissMissLoading();
//                    }
//                });
//    }


}
