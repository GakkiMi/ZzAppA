package com.huadingcloudpackage.www.activity.set;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.guoquan.yunpu.util.ComTools;
import com.huadingcloudpackage.www.R;
import com.huadingcloudpackage.www.base.BaseActivity;
import com.huadingcloudpackage.www.bean.BaseBean;
import com.huadingcloudpackage.www.http.UrlContent;
import com.huadingcloudpackage.www.sp.PreferenceManager;
import com.huadingcloudpackage.www.util.JsonUtils;
import com.huadingcloudpackage.www.util.LogUtilsApp;
import com.huadingcloudpackage.www.util.MyUtils;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;

import org.json.JSONException;
import org.json.JSONObject;

import butterknife.BindView;
import butterknife.OnClick;
import okhttp3.RequestBody;

/**
 * @author lige
 * 描述：修改手机密码
 */
public class UpdatePhonePswActivity extends BaseActivity {


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
    @BindView(R.id.tv_phone)
    TextView tvPhone;
    @BindView(R.id.et_old_psw)
    EditText etOldPsw;
    @BindView(R.id.et_new_psw)
    EditText etNewPsw;
    @BindView(R.id.et_confirm_psw)
    EditText etConfirmPsw;
    @BindView(R.id.tv_text)
    TextView tvText;
    @BindView(R.id.et_input_code)
    EditText etInputCode;
    @BindView(R.id.tv_get_code_phonepsw)
    TextView tvGetCodePhonepsw;
    @BindView(R.id.btn_commit)
    Button btnCommit;

    @Override
    public int getLayoutResId() {
        return R.layout.activity_update_phonepsw;
    }

    @Override
    public void initView(Bundle savedInstanceState) {
        initToolbar();
        setBackBtn();
        setTitle("修改手机密码");
        setToolbarBgColor(getResources().getColor(R.color.colorPageBg), false);
        String phonenumber = PreferenceManager.instance().getPhoneNum();
        tvPhone.setText(phonenumber);
        LogUtilsApp.d("psw++++++" + PreferenceManager.instance().getPwd());
    }

    @OnClick({R.id.tv_get_code_phonepsw, R.id.btn_commit})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_get_code_phonepsw:
                if (isCheckNewOk()) {
                    verifyCode(MyUtils.getText(tvPhone));
                }
                break;
            case R.id.btn_commit:
                if (isCheckOk()) {
                    UpdatePhonePsw(MyUtils.getEtText(etOldPsw), MyUtils.getEtText(etNewPsw), MyUtils.getEtText(etInputCode));
                }
                break;
        }
    }

    /**
     * 修改密码
     *
     * @param oldPassword
     * @param newPassword
     */
    private void UpdatePhonePsw(String oldPassword, String newPassword, String phoneCode) {
        showLoading();

        RequestBody body = null;
        try {
            JSONObject json1 = new JSONObject();
            json1.put("oldPassword", oldPassword);
            json1.put("newPassword", newPassword);
            json1.put("phoneCode", phoneCode);
            body = RequestBody.create(UrlContent.JSON, String.valueOf(json1));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        OkGo.<String>post(UrlContent.RESETPWD)
                .tag(this)
                .upRequestBody(body)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        BaseBean baseBean = JsonUtils.parse(response.body(), BaseBean.class);
                        comTools.parsingReturnData(baseBean, new ComTools.OnParsingReturnListener() {
                            @Override
                            public void onParsingSuccess() {
                                T("修改手机密码成功~");
                                PreferenceManager.instance().savePwd(MyUtils.getEtText(etNewPsw));
                                finish();
                            }
                        });


                    }

                    @Override
                    public void onFinish() {
                        super.onFinish();
                        dissMissLoading();
                    }

                    @Override
                    public void onError(Response<String> response) {
                        super.onError(response);
                        T("网络开小差，请稍后重试 ~");

                    }
                });
    }

    /**
     * 获取验证码
     *
     * @param exhibiCellphone
     */
    private void verifyCode(String exhibiCellphone) {
        showLoading();

        RequestBody body = null;
        try {
            JSONObject json1 = new JSONObject();
            json1.put("exhibiCellphone", exhibiCellphone);
            body = RequestBody.create(UrlContent.JSON, String.valueOf(json1));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        OkGo.<String>post(UrlContent.VERIFYPHONECODE)
                .tag(this)
                .upRequestBody(body)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        BaseBean baseBean = JsonUtils.parse(response.body(), BaseBean.class);
                        comTools.parsingReturnData(baseBean, new ComTools.OnParsingReturnListener() {
                            @Override
                            public void onParsingSuccess() {
                                MyCount myCount = new MyCount(60000, 1000);
                                myCount.start();
                            }
                        });


                    }

                    @Override
                    public void onFinish() {
                        super.onFinish();
                        dissMissLoading();
                    }

                    @Override
                    public void onError(Response<String> response) {
                        super.onError(response);
                        T("网络开小差，请稍后重试 ~");
                    }
                });
    }

    /**
     * 获取验证码之前
     * 判断输入的两次新密码是否一致
     *
     * @return
     */
    private Boolean isCheckNewOk() {
        String oldPassWord = etOldPsw.getText().toString().trim();
        String newPassWord = etNewPsw.getText().toString().trim();
        String newPassWordTwo = etConfirmPsw.getText().toString().trim();
        if (!oldPassWord.equals(PreferenceManager.instance().getPwd())) {
            T("原密码输入错误");
            return false;
        }
        if (!newPassWord.equals(newPassWordTwo)) {
            T("两次密码输入不一致，请重新输入");
            etNewPsw.setText("");
            etConfirmPsw.setText("");
            return false;
        }
        return true;
    }

    /**
     * 俩次密码文本项检测
     *
     * @return
     */
    private Boolean isCheckOk() {
        String oldPassWord = etOldPsw.getText().toString().trim();
        String newPassWord = etNewPsw.getText().toString().trim();
        String newPassWordTwo = etConfirmPsw.getText().toString().trim();

        if ("".equals(oldPassWord) && oldPassWord != null) {
            T("请输入密码");
            return false;
        }
        if (!(oldPassWord.length() >= 6) || !(oldPassWord.length() <= 15)) {
            T("请输入5-16位密码");
            return false;
        }
        if ("".equals(newPassWord) && newPassWord != null) {
            T("请输入密码");
            return false;
        }
        if (!(newPassWord.length() >= 6) || !(newPassWord.length() <= 15)) {
            T("请输入5-16位密码");
            return false;
        }
        if ("".equals(newPassWordTwo) && newPassWordTwo != null) {
            T("请输入确认密码");
            return false;
        }
        if (!(newPassWordTwo.length() >= 6) || !(newPassWordTwo.length() <= 15)) {
            T("请输入正确的确认密码");
            return false;
        }
        return true;
    }

    /**
     * 验证码记时器
     */
    public class MyCount extends CountDownTimer {

        public MyCount(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);
        }

        @Override
        public void onTick(long millisUntilFinished) {
            if (tvGetCodePhonepsw != null) {
                tvGetCodePhonepsw.setEnabled(false);
                tvGetCodePhonepsw.setClickable(false);
                tvGetCodePhonepsw.setText(millisUntilFinished / 1000 + "s后重新获取");
                tvGetCodePhonepsw.setTextColor(Color.parseColor("#084D8E"));
            }
        }

        @Override
        public void onFinish() {
            if (tvGetCodePhonepsw != null) {
                tvGetCodePhonepsw.setText("获取验证码");
                tvGetCodePhonepsw.setTextColor(Color.parseColor("#084D8E"));
                tvGetCodePhonepsw.setEnabled(true);
                tvGetCodePhonepsw.setClickable(true);
            }
        }
    }

}
