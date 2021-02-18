package com.huadingcloudpackage.www.activity.login;

import android.os.Bundle;
import android.text.InputType;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.guoquan.yunpu.util.ComTools;
import com.huadingcloudpackage.www.R;
import com.huadingcloudpackage.www.base.BaseActivity;
import com.huadingcloudpackage.www.bean.BaseBean;
import com.huadingcloudpackage.www.http.UrlContent;
import com.huadingcloudpackage.www.sp.PreferenceManager;
import com.huadingcloudpackage.www.util.JsonUtils;
import com.huadingcloudpackage.www.util.MD5Class;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;
import com.lzy.okgo.request.PostRequest;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 设置新密码
 */
public class SetNewPwdActivity extends BaseActivity {
    @BindView(R.id.edit_pwd_one)
    EditText editPwdOne;
    @BindView(R.id.iv_pwd_one_eye)
    ImageView ivPwdOneEye;
    @BindView(R.id.edit_pwd_two)
    EditText editPwdTwo;
    @BindView(R.id.iv_pwd_two_eye)
    ImageView ivPwdTwoEye;
    @BindView(R.id.tv_confirm)
    TextView tvConfirm;
    @BindView(R.id.tv_previous_step)
    TextView tvPreviousStep;
    private String phone;

//    private String url = UrlContent.CHANGE_PWD;

    @Override
    public int getLayoutResId() {
        return R.layout.activity_set_new_pwd;
    }

    @Override
    public void initView(Bundle savedInstanceState) {
        initToolbar();
        setTitle("找回密码");
        setBackBtn();
        if (getIntent().getExtras() != null) {
            phone = getIntent().getStringExtra("phone");
            tvPreviousStep.setText("1. 验证手机号");
//            url = UrlContent.CHANGE_PWD;
        }else {
            tvPreviousStep.setText("1. 验证原密码");
//            url = UrlContent.UPDATE_PWD;
        }
    }

    @OnClick({R.id.iv_pwd_one_eye, R.id.iv_pwd_two_eye, R.id.tv_confirm})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_pwd_one_eye:
                if (isFastClick()){
                    return;
                }
                showOrHide(editPwdOne, ivPwdOneEye);
                break;
            case R.id.iv_pwd_two_eye:
                if (isFastClick()){
                    return;
                }
                showOrHide(editPwdTwo, ivPwdTwoEye);
                break;
            case R.id.tv_confirm:
                if (isFastClick()){
                    return;
                }
                if (TextUtils.isEmpty(editPwdOne.getText().toString()) || editPwdOne.getText().length() < 6) {
                    T("请输入最少六位的密码");
                    return;
                }
                if (!editPwdOne.getText().toString().equals(editPwdTwo.getText().toString())) {
                    T("两次输入密码请保持一致");
                    return;
                }
//                OkGo.<String>get(UrlContent.FIND_SALT)
//                        .tag(this)
//                        .params("phone", TextUtils.isEmpty(phone)?PreferenceManager.instance().getPhoneNum():phone)
//                        .execute(new StringCallback() {
//                            @Override
//                            public void onSuccess(Response<String> response) {
//                                BaseBean baseBean = JsonUtils.parse(response.body(),BaseBean.class);
//                                comTools.parsingReturnData(baseBean, new ComTools.OnParsingReturnListener() {
//                                    @Override
//                                    public void onParsingSuccess() {
//                                        ResultBean bean = JsonUtils.parse(response.body(), ResultBean.class);
//                                        if (!TextUtils.isEmpty(bean.getData())){
//                                            setNewPwd(bean.getData());
//                                        }
//                                    }
//                                });
//                            }
//                        });


                break;
        }
    }
//
//    private void setNewPwd(String code) {
//        showLoading();
//        PostRequest<String> params = OkGo.<String>post(url)
//                .tag(this);
//                params.params("password", editPwdOne.getText().toString());
//        if (TextUtils.isEmpty(phone)){
//            params.params("password", MD5Class.MD5(PreferenceManager.instance().getPhoneNum()+editPwdOne.getText().toString()+code))
//                    .params("userId",PreferenceManager.instance().getUserId());
//        }else {
//            params.params("password", MD5Class.MD5(phone+editPwdOne.getText().toString()+code))
//                    .params("phone", phone)
//                    .params("isAdmin", "N");
//        }
//        params.execute(new StringCallback() {
//                    @Override
//                    public void onSuccess(Response<String> response) {
//                        comTools.parsingReturnData(JsonUtils.parse(response.body(), BaseBean.class), new ComTools.OnParsingReturnListener() {
//                            @Override
//                            public void onParsingSuccess() {
//                                if (TextUtils.isEmpty(phone)){
//                                }else {
//                                    PreferenceManager.instance().savePhoneNum(phone);
//                                }
//                                T("密码修改成功");
//                                PreferenceManager.instance().savePwd(editPwdOne.getText().toString());
//                                finish();
////                                EventBus.getDefault().postSticky(new SetNewPwdEvent());//密码修改成功，发消息关闭验证手机号页面
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

    /**
     * 显示隐藏密码
     *
     * @param etPassword
     */

    private void showOrHide(EditText etPassword, ImageView iv) {
        //记住光标开始的位置
        int pos = etPassword.getSelectionStart();
        if (etPassword.getInputType() != (InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD)) {//隐藏密码
            iv.setImageResource(R.mipmap.icon_close_eye);
            etPassword.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
        } else {//显示密码
            iv.setImageResource(R.mipmap.icon_open_eye);
            etPassword.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
        }
        etPassword.setSelection(pos);

    }

}
