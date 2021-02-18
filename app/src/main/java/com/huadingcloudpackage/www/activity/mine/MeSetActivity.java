package com.huadingcloudpackage.www.activity.mine;

import android.Manifest;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;


import androidx.annotation.NonNull;

import com.huadingcloudpackage.www.R;
import com.huadingcloudpackage.www.activity.login.LoginActivity;
import com.huadingcloudpackage.www.activity.set.SetPasswordActivity;
import com.huadingcloudpackage.www.activity.set.UpdatePhoneNumActivity;
import com.huadingcloudpackage.www.activity.set.UpdatePhonePswActivity;
import com.huadingcloudpackage.www.activity.webview.WebViewActivity;
import com.huadingcloudpackage.www.base.BaseActivity;
import com.huadingcloudpackage.www.bean.AppDateBean;
import com.huadingcloudpackage.www.bean.AppUpdateBean;
import com.huadingcloudpackage.www.bean.BaseBean;
import com.huadingcloudpackage.www.callback.DialogCallback;
import com.huadingcloudpackage.www.dialog.CommomDialog;
import com.huadingcloudpackage.www.http.UrlContent;
import com.huadingcloudpackage.www.sp.PreferenceManager;
import com.huadingcloudpackage.www.util.AppUpdateUtils;
import com.huadingcloudpackage.www.util.JsonUtils;
import com.huadingcloudpackage.www.util.MyUtils;
import com.huadingcloudpackage.www.util.T;
import com.huadingcloudpackage.www.widget.ShapeTextView;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.model.Response;
import com.permissionx.guolindev.PermissionX;


import java.util.HashMap;

import butterknife.BindView;
import butterknife.OnClick;
import okhttp3.RequestBody;
import permissions.dispatcher.NeedsPermission;
import permissions.dispatcher.OnNeverAskAgain;
import permissions.dispatcher.OnPermissionDenied;
import permissions.dispatcher.OnShowRationale;
import permissions.dispatcher.PermissionRequest;
import permissions.dispatcher.RuntimePermissions;

/**
 * 设置
 */
public class MeSetActivity extends BaseActivity/* implements EasyPermissions.PermissionCallbacks */ {

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
    @BindView(R.id.ll_update_phonepass)
    LinearLayout llUpdatePhonepass;
    @BindView(R.id.ll_set_password)
    LinearLayout llSetPassword;
    @BindView(R.id.ll_updata_phonenum)
    LinearLayout llUpdataPhonenum;
    @BindView(R.id.ll_about)
    LinearLayout llAbout;
    @BindView(R.id.ll_check)
    LinearLayout llCheck;
    @BindView(R.id.tv_Quit)
    TextView tvQuit;
    @BindView(R.id.tv_paypsw)
    TextView tvPaypsw;
    private String phonenumber;
    @BindView(R.id.my_set_update_tv_version_name)
    TextView tvVersionName;
    @BindView(R.id.my_set_update_tips_stv)
    ShapeTextView stvUpdateTips;

    private static final String TAG = "MeSetActivity";

    @Override
    public int getLayoutResId() {
        return R.layout.activity_me_set;
    }

    @Override
    public void initView(Bundle savedInstanceState) {
        initToolbar();
        setBackBtn();
        setTitle("设置");
        setToolbarBgColor(getResources().getColor(R.color.colorPageBg), false);
        Intent intent = getIntent();
        phonenumber = intent.getStringExtra("phone");
        tvPaypsw.setText("设置支付密码");
        tvVersionName.setText("V " + MyUtils.getAppVersionName(this));
        tvVersionName.setTextColor((MyUtils.isApkInDebug(this) ? Color.parseColor("#000000") : Color.parseColor("#999999")));
        requesetIsSettingPwd();

        checkUpdate();
    }

    @OnClick({R.id.ll_update_phonepass, R.id.ll_set_password, R.id.ll_updata_phonenum, R.id.ll_about, R.id.ll_check, R.id.tv_Quit, R.id.ll_yszc})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ll_update_phonepass:
                if (isFastClick()) {
                    return;
                }
                Bundle bundle = new Bundle();
                bundle.putString("phone", phonenumber);
                startActivity(UpdatePhonePswActivity.class, bundle);
                break;
            case R.id.ll_set_password:
                Bundle bundle1 = new Bundle();
                bundle1.putString("phone", phonenumber);
                startActivity(SetPasswordActivity.class, bundle1);
                break;
            case R.id.ll_updata_phonenum:
                startActivity(UpdatePhoneNumActivity.class);
                break;
            case R.id.ll_about:
                startActivity(AboutUseActivity.class);
                break;
            case R.id.ll_check:
                AppUpdateUtils.appCheckUpdate(this, this, null, true);
                break;
            case R.id.ll_yszc:
                if (isFastClick()) {
                    return;
                }
                startActivity(new Intent(mContext, WebViewActivity.class).putExtra("url", UrlContent.YSZC).putExtra("title", "隐私政策"));
                break;
            case R.id.tv_Quit:
                if (isFastClick()) {
                    return;
                }
                new CommomDialog(this, "确认退出登录吗？", new CommomDialog.OnCloseListener() {
                    @Override
                    public void onClick(Dialog dialog, boolean confirm) {
                        if (confirm) {
                            PreferenceManager.instance().saveToken("");
                            Intent intent = new Intent(MeSetActivity.this, LoginActivity.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                            startActivity(intent);
                        }
                    }
                }).setNegativeButton("取消")
                        .setPositiveButton("确定")
                        .show();
                break;
        }
    }

    /**
     * 是否设置支付密码
     */
    private void requesetIsSettingPwd() {
        OkGo.<String>post(UrlContent.SELECT_SETTING_PASSWORD)
                .tag(this)
                .execute(new DialogCallback(this, true) {
                    @Override
                    public void onSuccess(Response<String> response) {
                        BaseBean baseBean = JsonUtils.parse(response.body(), BaseBean.class);
                        if (baseBean.getCode() != 200) {
                            tvPaypsw.setText("设置支付密码");
                        } else {
                            tvPaypsw.setText("修改支付密码");
                        }
                    }

                    @Override
                    public void onError(Response<String> response) {
                        T.showToastyCenter(mContext, "网络开小差，请稍后重试 ~");
                    }
                });
    }

    /**
     * 检查更新
     */
    private void checkUpdate() {
        HashMap<String, Object> params = new HashMap<>();
        params.put("channel", "0");
        String json = JsonUtils.toJsonString(params);
        RequestBody body = RequestBody.create(UrlContent.JSON, json);

        OkGo.<String>post(UrlContent.BBGX)
                .tag(TAG)
                .upRequestBody(body)
                .execute(new DialogCallback(this, true) {
                    @Override
                    public void onSuccess(Response<String> response) {
                        BaseBean baseBean = JsonUtils.parse(response.body(), BaseBean.class);

                        comTools.parsingReturnData(baseBean, () -> {
                            AppUpdateBean appUpdateBean = JsonUtils.parse(response.body(), AppUpdateBean.class);
                            AppDateBean appDateBean = appUpdateBean.getData();
                            /** 新版本 **/
                            int serverVersionCode = appDateBean.getVersionCode();//服务器返回的code
                            long currentVersionCode = MyUtils.getAppVersionCode(mContext);//当前安装包的code
                            stvUpdateTips.setVisibility(serverVersionCode > currentVersionCode ? View.VISIBLE : View.GONE);
                        });
                    }

                    @Override
                    public void onError(Response<String> response) {
                        T.showToastyCenter(mContext, "网络开小差，请稍后重试 ~");
                    }
                });
    }


}
