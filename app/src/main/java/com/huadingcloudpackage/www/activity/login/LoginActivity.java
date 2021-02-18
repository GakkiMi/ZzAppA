package com.huadingcloudpackage.www.activity.login;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.TextUtils;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.text.style.ForegroundColorSpan;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.core.content.ContextCompat;

import com.guoquan.yunpu.util.ComTools;
import com.huadingcloudpackage.www.MainActivity;
import com.huadingcloudpackage.www.R;
import com.huadingcloudpackage.www.activity.webview.WebViewActivity;
import com.huadingcloudpackage.www.base.BaseActivity;
import com.huadingcloudpackage.www.bean.BaseBean;
import com.huadingcloudpackage.www.bean.LoginBean;
import com.huadingcloudpackage.www.dialog.CommomDialog;
import com.huadingcloudpackage.www.http.UrlContent;
import com.huadingcloudpackage.www.sp.PreferenceManager;
import com.huadingcloudpackage.www.util.JsonUtils;
import com.huadingcloudpackage.www.util.MyUtils;
import com.huadingcloudpackage.www.util.T;
import com.huadingcloudpackage.www.widget.ClearEditText;
import com.huadingcloudpackage.www.widget.NoLineClickSpan;
import com.huadingcloudpackage.www.widget.ShapeTextView;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.HttpHeaders;
import com.lzy.okgo.model.HttpParams;
import com.lzy.okgo.model.Response;

import org.json.JSONException;
import org.json.JSONObject;

import butterknife.BindView;
import butterknife.OnClick;
import butterknife.OnLongClick;
import okhttp3.MediaType;
import okhttp3.RequestBody;

/**
 * 登录页
 */
public class LoginActivity extends BaseActivity {
    @BindView(R.id.edit_username)
    ClearEditText editUsername;
    @BindView(R.id.edit_pwd)
    EditText editPwd;
    @BindView(R.id.tv_login)
    Button tvLogin;
    @BindView(R.id.login_iv_logo)
    ImageView ivLoginLogo;

    @BindView(R.id.tv_forget_pwd_yzh)
    TextView tvForgetPwdYzh;

    @BindView(R.id.yszc)
    TextView yszc;
    @BindView(R.id.fwxy)
    TextView fwxy;

    @BindView(R.id.login_cb_agree)
    CheckBox cbAgree;


    private AlertDialog dialog;

    @Override
    public int getLayoutResId() {
        return R.layout.activity_login;
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void initView(Bundle savedInstanceState) {
//        initToolbar();
//        setTitle("");
//        setBackBtn();
        if (!PreferenceManager.instance().getPhoneNum().equals("")) {
            editUsername.setText(PreferenceManager.instance().getPhoneNum());//记住手机号
        }


        if (!PreferenceManager.instance().getPolicyIsAgree()) {
            CommomDialog policyDialog = new CommomDialog(mContext);
            policyDialog.setTitle("隐私政策")
                    .setContent(getPolicySpanContent())
                    .setNegativeButton("暂不使用")
                    .setNegativeColor("#000000")
                    .setPositiveButton("同意")
                    .setOnCloseListener(new CommomDialog.OnCloseListener() {
                        @Override
                        public void onClick(Dialog dialog, boolean confirm) {
                            if (confirm) {
                                PreferenceManager.instance().savePolicyIsAgree(true);
                            } else {
                                finish();
                            }
                        }
                    }).show();
            policyDialog.getContentTxt().setMovementMethod(LinkMovementMethod.getInstance());
            policyDialog.getContentTxt().setGravity(Gravity.LEFT);
            policyDialog.getContentTxt().setHighlightColor(ContextCompat.getColor(this, R.color.colorTransparent));
        }
    }

    @OnClick({R.id.tv_login, R.id.tv_forget_pwd_yzh, R.id.yszc, R.id.fwxy})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_login://登录
                if (isFastClick()) {
                    return;
                }
                if (TextUtils.isEmpty(MyUtils.getEtText(editUsername))) {
                    T.showToastyCenter(this, "请输入登录账号");
                    return;
                }
                if (TextUtils.isEmpty(MyUtils.getEtText(editPwd))) {
                    T.showToastyCenter(this, "请输入密码");
                    return;
                }

                login(MyUtils.getEtText(editUsername), MyUtils.getEtText(editPwd));
                break;
            case R.id.tv_forget_pwd_yzh://找回密码
                if (isFastClick()) {
                    return;
                }
                startActivity(new Intent(mContext, ForGetPassActivity.class));
                break;

            case R.id.yszc://隐私政策
                if (isFastClick()) {
                    return;
                }
                startActivity(new Intent(mContext, WebViewActivity.class).putExtra("url", UrlContent.YSZC).putExtra("title", "隐私政策"));
                break;
            case R.id.fwxy://服务协议
                if (isFastClick()) {
                    return;
                }
                startActivity(new Intent(mContext, WebViewActivity.class).putExtra("url", UrlContent.FWXY).putExtra("title", "服务协议"));
                break;

            default:
                break;
        }
    }

    @OnLongClick({R.id.login_iv_logo})
    public void onLongClick(View view) {
        switch (view.getId()) {
            case R.id.login_iv_logo://登录
                String baseUrl = UrlContent.BASE_URL;
                String type = "";
                if (baseUrl.equals("http://121.89.202.8:15009/")) {
                    type = "Test";
                    T.showToastyBottom(mContext, "华鼎云报货端" + type);
                } else if (baseUrl.equals("http://172.16.20.81:9527/")) {
                    type = "Local";
                    T.showToastyBottom(mContext, "华鼎云报货端" + type);
                }
                break;
            default:
                break;
        }
    }


    /**
     * 不允许报货提示
     */
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void showWrong(String title, String content) {
        if (dialog == null) {
            View view = LayoutInflater.from(this).inflate(R.layout.dialog_login, null);
            ImageView close = view.findViewById(R.id.iv_close);
            close.setOnClickListener(v -> {
                dialog.dismiss();
            });
            dialog = new AlertDialog.Builder(this).setView(view).create();
            dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
            dialog.setCanceledOnTouchOutside(false);
        }
        dialog.show();
        TextView tv_content = dialog.findViewById(R.id.tv_content);
        TextView tv_title = dialog.findViewById(R.id.tv_title);
        tv_title.setText(title);
        tv_content.setText(content);
    }


    private void login(String username, String password) {
        showLoading();
        //http://121.89.202.8:15009/auth/login         post
        //password: "登录密码"
        //userType: "02  用户类型  传  02"
        //username: "登录账号"

        RequestBody body = null;
        try {
            JSONObject json1 = new JSONObject();
            json1.put("loginName", username);
            json1.put("password", password);
            json1.put("userType", "02");
            body = RequestBody.create(UrlContent.JSON, String.valueOf(json1));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        OkGo.<String>post(UrlContent.LOGIN)
                .tag(this)
                .upRequestBody(body)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        BaseBean baseBean = JsonUtils.parse(response.body(), BaseBean.class);
                        comTools.parsingReturnData(baseBean, new ComTools.OnParsingReturnListener() {
                            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
                            @Override
                            public void onParsingSuccess() {
                                LoginBean loginBean = JsonUtils.parse(response.body(), LoginBean.class);
                                if (loginBean.getExhibiTime1() == null || loginBean.getExhibiTime1().equals("")) {
                                    PreferenceManager.instance().saveToken(loginBean.getToken());
                                    PreferenceManager.instance().saveUserId(loginBean.getUserId());
                                    PreferenceManager.instance().savestoreId(loginBean.getStoreId());
                                    PreferenceManager.instance().saveexhibiId(loginBean.getExhibiId());
                                    PreferenceManager.instance().savePwd(MyUtils.getEtText(editPwd));
                                    PreferenceManager.instance().savePhoneNum(MyUtils.getEtText(editUsername));
                                    OkGo.getInstance().addCommonHeaders(new HttpHeaders("token", PreferenceManager.instance().getToken()));//添加公共参数
                                    OkGo.getInstance().addCommonHeaders(new HttpHeaders("currentUserId", PreferenceManager.instance().getUserId()));//添加公共参数
                                    OkGo.getInstance().addCommonHeaders(new HttpHeaders("storeId", PreferenceManager.instance().getstoreId()));//添加公共参数
                                    OkGo.getInstance().addCommonHeaders(new HttpHeaders("exhibitionId", PreferenceManager.instance().getexhibiId()));//添加公共参数
                                    OkGo.getInstance().addCommonHeaders(new HttpHeaders("userType", "02"));//添加公共参数
                                    T("登陆成功");
                                    startActivity(new Intent(mContext, MainActivity.class));
                                    finish();
                                } else {
                                    showWrong(loginBean.getExhibiTime1(), loginBean.getExhibiTime2());
                                }

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


    //目前只有隐私政策
    private CharSequence getPolicySpanContent() {
        String text = "请你务必审慎阅读、充分理解“隐私政策”各条款，包括但不限于:为了向你提供内容等服务，我们要收集你的设备信息、操作日志等个人信息。" +
                "你可以在“设置”中查看、変更、删除个人信息并管理你的授权。你可阅读《隐私政策》了解详细信息。如您同意，请点击“同意”开始接受我们的服务。";
        String yhxyLink = "《用户协议》";
        String yszcLink = "《隐私政策》";
        int yhxyLinkIndex = text.indexOf(yhxyLink);
        int yszcLinkIndex = text.indexOf(yszcLink);

        final SpannableStringBuilder policySpanStringBuilder = new SpannableStringBuilder(text);

        //用户协议点击事件
        NoLineClickSpan clickableSpanYHXY = new NoLineClickSpan() {
            @Override
            public void onClick(View widget) {
                startActivity(new Intent(mContext, WebViewActivity.class).putExtra("url", UrlContent.YSZC).putExtra("title", "用户协议"));
            }
        };
        //隐私政策点击事件
        NoLineClickSpan clickableSpanYSZC = new NoLineClickSpan() {
            @Override
            public void onClick(View widget) {
                startActivity(new Intent(mContext, WebViewActivity.class).putExtra("url", UrlContent.YSZC).putExtra("title", "隐私政策"));
            }
        };
//        policySpanStringBuilder.setSpan(clickableSpanYHXY, yhxyLinkIndex, yhxyLinkIndex + 6, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        policySpanStringBuilder.setSpan(clickableSpanYSZC, yszcLinkIndex, yszcLinkIndex + 6, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);


        //设置部分文字颜色
        ForegroundColorSpan foregroundColorSpan = new ForegroundColorSpan(Color.parseColor("#084D8E"));
        ForegroundColorSpan foregroundColorSpan1 = new ForegroundColorSpan(Color.parseColor("#084D8E"));
//        policySpanStringBuilder.setSpan(foregroundColorSpan, yhxyLinkIndex, yhxyLinkIndex + 6, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        policySpanStringBuilder.setSpan(foregroundColorSpan1, yszcLinkIndex, yszcLinkIndex + 6, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        return policySpanStringBuilder;
    }

}
