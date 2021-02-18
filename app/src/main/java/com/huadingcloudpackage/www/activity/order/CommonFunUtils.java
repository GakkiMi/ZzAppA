package com.huadingcloudpackage.www.activity.order;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.text.Html;
import android.text.TextUtils;
import android.widget.TextView;

import com.guoquan.yunpu.util.ComTools;
import com.huadingcloudpackage.www.AppApplication;
import com.huadingcloudpackage.www.bean.BaseBean;
import com.huadingcloudpackage.www.bean.KefuBean;
import com.huadingcloudpackage.www.dialog.CommomDialog;
import com.huadingcloudpackage.www.http.UrlContent;
import com.huadingcloudpackage.www.util.JsonUtils;
import com.huadingcloudpackage.www.util.T;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;

import static android.content.Context.CLIPBOARD_SERVICE;

/**
 * 文 件 名：CommonFunUtils
 * 描   述：一些界面的公共方法
 */
public class CommonFunUtils {

    private CommomDialog callDialog;
    private ComTools comTools;

    private Context mContext;

    private volatile static CommonFunUtils commonFunUtils;

    /*public static CommonFunUtils getSingleton(Context mContext) {
        if (commonFunUtils == null) {
            synchronized (CommonFunUtils.class) {
                if (commonFunUtils == null) {
                    commonFunUtils = new CommonFunUtils(mContext);
                }
            }
        }
        return commonFunUtils;
    }*/

    public CommonFunUtils(Context context) {
        comTools = new ComTools();
        this.mContext = context;
    }


    /**
     * 获取客服电话
     */
    private void getPhone() {
        OkGo.<String>post(UrlContent.GETPHONE)
                .tag(this)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        BaseBean baseBean = JsonUtils.parse(response.body(), BaseBean.class);
                        comTools.parsingReturnData(baseBean, new ComTools.OnParsingReturnListener() {
                            @Override
                            public void onParsingSuccess() {
                                KefuBean kefuBean = JsonUtils.parse(response.body(), KefuBean.class);
                                if (kefuBean.getData() == null || kefuBean.getData().equals("")) {
                                    return;
                                }
                                initCallDialog(kefuBean.getData());
                            }
                        });
                    }

                    @Override
                    public void onFinish() {
                        super.onFinish();
                    }

                    @Override
                    public void onError(Response<String> response) {
                        super.onError(response);
                        T.show(mContext, "网络开小差，请稍后重试 ~");
                    }
                });
    }


    /**
     * 初始化拨打电话确认dialog
     *
     * @param phoneNum 电话号码
     */
    private void initCallDialog(String phoneNum) {
        String html = "<font color=\"#999999\">客服电话：</font>" + "<font color=\"#000000\">" + phoneNum + "</font>";
        callDialog = new CommomDialog(mContext);
        callDialog.setTitle("联系客服");
        callDialog.setContent(Html.fromHtml(html));
        callDialog.setPositiveButton("确定");
        callDialog.setNegativeButton("取消");
        callDialog.setNegativeColor("#5c5c5c");
        callDialog.setOnCloseListener((dialog1, confirm) -> {
            if (confirm) {
                Intent intent = new Intent(Intent.ACTION_DIAL);
                Uri data = Uri.parse("tel:" + phoneNum);
                intent.setData(data);
                mContext.startActivity(intent);
            }
        });
        callDialog.show();
    }

    /**
     * 拨打客服电话
     */
    public void callKefu() {
        if (callDialog == null) {
            getPhone();
//            initCallDialog("400-158-6787");
        } else {
            callDialog.show();
        }
    }


    public void copyText(TextView copyTv) {
        if (TextUtils.isEmpty(copyTv.getText())) {
            return;
        }
        ClipboardManager mClipboardManager = (ClipboardManager) mContext.getSystemService(CLIPBOARD_SERVICE);
        //第一个参数，是描述复制的内容，也可以和内容一样。
        ClipData clipData = ClipData.newPlainText("copynum", copyTv.getText());
        mClipboardManager.setPrimaryClip(clipData);
        T.showToastyCenter(AppApplication.instance, "复制成功");
    }


}
