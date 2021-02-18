package com.huadingcloudpackage.www.activity.set;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.TextUtils;
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
 * 描述：设置支付密码
 */
public class SetPasswordActivity extends BaseActivity {
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
    @BindView(R.id.tv_get_code_setpsw)
    TextView tvGetCodeSetpsw;
    @BindView(R.id.btn_commit)
    Button btnCommit;

    @Override
    public int getLayoutResId() {
        return R.layout.activity_setpsw;
    }

    @Override
    public void initView(Bundle savedInstanceState) {
        initToolbar();
        setBackBtn();
        if (!PreferenceManager.instance().getPayPsw().equals("")) {
            setTitle("修改支付密码");
        } else {
            setTitle("设置支付密码");
        }
        Intent intent = getIntent();
        String phonenumber = PreferenceManager.instance().getPhoneNum();
        String title = intent.getStringExtra("title");//支付界面传递过来
        if (!TextUtils.isEmpty(title)) {
            setTitle("忘记密码");
        }
        setToolbarBgColor(getResources().getColor(R.color.colorPageBg), false);
        tvPhonenum.setText(phonenumber);
    }

    @OnClick({R.id.tv_get_code_setpsw, R.id.btn_commit})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_get_code_setpsw:
                if (isCheckNewOk()) {
                    verifyCode(MyUtils.getText(tvPhonenum));
                }
                break;
            case R.id.btn_commit:
                if (isCheckOk()) {
                    UpdatePhonePsw(MyUtils.getEtText(etPayPsw), MyUtils.getText(tvPhonenum), MyUtils.getEtText(etInputCode));
                }
                break;
        }
    }

    /**
     * 修改密码
     *
     * @param passWord
     * @param exhibiCellphone
     * @param phoneCode
     */
    private void UpdatePhonePsw(String passWord, String exhibiCellphone, String phoneCode) {
        showLoading();

        RequestBody body = null;
        try {
            JSONObject json1 = new JSONObject();
            json1.put("passWord", passWord);
            json1.put("exhibiCellphone", exhibiCellphone);
            json1.put("phoneCode", phoneCode);
            body = RequestBody.create(UrlContent.JSON, String.valueOf(json1));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        OkGo.<String>post(UrlContent.SETPWD)
                .tag(this)
                .upRequestBody(body)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        BaseBean baseBean = JsonUtils.parse(response.body(), BaseBean.class);
                        comTools.parsingReturnData(baseBean, new ComTools.OnParsingReturnListener() {
                            @Override
                            public void onParsingSuccess() {
                                //存储支付密码
                                PreferenceManager.instance().savePayPsw(MyUtils.getEtText(etPayPsw));
                                if (!PreferenceManager.instance().getPayPsw().equals("")) {
                                    T("修改密码成功~");
                                } else {
                                    T("设置支付密码成功~");
                                }
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
     * 俩次密码文本项检测
     *
     * @return
     */
    private Boolean isCheckNewOk() {
        String newPassWord = etPayPsw.getText().toString().trim();
        String newPassWordTwo = etConfirmPayPsw.getText().toString().trim();

        if (!newPassWord.equals(newPassWordTwo)) {
            T("两次密码输入不一致，请重新输入");
            etPayPsw.setText("");
            etConfirmPayPsw.setText("");
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
        String newPassWord = etPayPsw.getText().toString().trim();
        String newPassWordTwo = etConfirmPayPsw.getText().toString().trim();
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
            T("请输入请输入5-16位确认密码");
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

            if (tvGetCodeSetpsw != null) {
                tvGetCodeSetpsw.setClickable(false);
                tvGetCodeSetpsw.setEnabled(false);
                tvGetCodeSetpsw.setText(millisUntilFinished / 1000 + "s后重新获取");
                tvGetCodeSetpsw.setTextColor(Color.parseColor("#084D8E"));
            }
        }

        @Override
        public void onFinish() {
            if (tvGetCodeSetpsw != null) {
                tvGetCodeSetpsw.setText("获取验证码");
                tvGetCodeSetpsw.setTextColor(Color.parseColor("#084D8E"));
                tvGetCodeSetpsw.setEnabled(true);
                tvGetCodeSetpsw.setClickable(true);
            }
        }
    }
}
