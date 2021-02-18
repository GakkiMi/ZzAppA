package com.huadingcloudpackage.www.activity.login;

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
import com.huadingcloudpackage.www.util.JsonUtils;
import com.huadingcloudpackage.www.util.MyUtils;
import com.luck.picture.lib.tools.ToastUtils;
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
 * 描述：登录页忘记密码
 */
public class ForGetPassActivity extends BaseActivity {
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
    @BindView(R.id.tv_phonenum)
    TextView tvPhonenum;
    @BindView(R.id.et_pay_psw)
    EditText etPayPsw;
    @BindView(R.id.et_confirm_pay_psw)
    EditText etConfirmPayPsw;
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
        return R.layout.activity_for_get_pass;
    }

    @Override
    public void initView(Bundle savedInstanceState) {
        initToolbar();
        setBackBtn();
        setTitle("忘记密码");
    }

    @OnClick({R.id.tv_get_code, R.id.btn_commit})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_get_code:
                verifyCode(MyUtils.getText(tvPhonenum));
                break;
            case R.id.btn_commit:
                if (isCheckOk()) {
                    forgerPsw(MyUtils.getText(tvPhonenum), MyUtils.getEtText(etPayPsw));
                }
                finish();
                break;
        }
    }

    /**
     * 修改密码
     *
     * @param phonenumber
     * @param password
     */
    private void forgerPsw(String phonenumber, String password) {
        showLoading();

        RequestBody body = null;
        try {
            JSONObject json1 = new JSONObject();
            json1.put("phonenumber", phonenumber);
            json1.put("password", password);
            json1.put("usertype", "02");
            body = RequestBody.create(UrlContent.JSON, String.valueOf(json1));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        OkGo.<String>post(UrlContent.FORGERPASSCODE)
                .tag(this)
                .upRequestBody(body)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        BaseBean baseBean = JsonUtils.parse(response.body(), BaseBean.class);
                        comTools.parsingReturnData(baseBean, new ComTools.OnParsingReturnListener() {
                            @Override
                            public void onParsingSuccess() {

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
                                tvGetCode.setEnabled(false);
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

                        tvGetCode.setClickable(true);
                        tvGetCode.setEnabled(true);
                    }
                });
    }

    /**
     * 俩次密码文本项检测
     *
     * @return
     */
    private Boolean isCheckOk() {
        String newPassWord = etPayPsw.getText().toString().trim();
        String newPassWordTwo = etConfirmPayPsw.getText().toString().trim();
        if ("".equals(newPassWord) && newPassWord != null) {
            ToastUtils.s(this, "请输入密码");
            return false;
        }
        if (!(newPassWord.length() >= 6) || !(newPassWord.length() <= 15)) {
            ToastUtils.s(this, "请输入5-16位密码");
            return false;
        }
        if ("".equals(newPassWordTwo) && newPassWordTwo != null) {
            ToastUtils.s(this, "请输入确认密码");
            return false;
        }
        if (!(newPassWordTwo.length() >= 6) || !(newPassWordTwo.length() <= 15)) {
            ToastUtils.s(this, "请输入请输入5-16位确认密码");
            return false;
        }
        if (!newPassWord.equals(newPassWordTwo)) {
            ToastUtils.s(this, "两次密码输入不一致，请重新输入");
            etPayPsw.setText("");
            etConfirmPayPsw.setText("");
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
            tvGetCode.setText(millisUntilFinished / 1000 + "s后重新获取");
            tvGetCode.setTextColor(Color.parseColor("#084D8E"));
        }

        @Override
        public void onFinish() {
            tvGetCode.setText("获取验证码");
            tvGetCode.setTextColor(Color.parseColor("#084D8E"));
            tvGetCode.setEnabled(true);
            tvGetCode.setClickable(true);
        }
    }
}
