package com.huadingcloudpackage.www.callback;

import android.content.Context;

import com.huadingcloudpackage.www.AppApplication;
import com.huadingcloudpackage.www.util.NetUtils;
import com.huadingcloudpackage.www.widget.NewLoadingDialog;
import com.lzy.okgo.callback.AbsCallback;
import com.lzy.okgo.convert.StringConvert;
import com.lzy.okgo.request.base.Request;

import okhttp3.Response;

/**
 * 文 件 名：DialogCallback
 * 描   述：将dialog的show和dismiss通过这个类管理
 */
public abstract class DialogCallback extends AbsCallback<String> {

    private NewLoadingDialog dialog;
    private boolean isShowDialog;
    private StringConvert convert;
    private Context mContext;

    public DialogCallback(Context context, boolean isShowDialog) {
        convert = new StringConvert();
        this.isShowDialog = isShowDialog;
        initDialog(context);
        this.mContext = context;

    }

    private void initDialog(Context context) {
        dialog = new NewLoadingDialog(context);
    }

    @Override
    public void onStart(Request request) {
        boolean flag = NetUtils.isNetworkAvailable(mContext);

        if (!isShowDialog) {
            return;
        }
        if (dialog != null && !dialog.isShowing()) {
            dialog.getWindow().setDimAmount(0);
            dialog.show();
        }

    }

    @Override
    public void onFinish() {
        //网络请求结束后关闭对话框
        if (!isShowDialog) {
            return;
        }
        if (dialog != null && dialog.isShowing()) {
            dialog.dismiss();
        }
    }

    @Override
    public String convertResponse(Response response) throws Throwable {
        String s = convert.convertResponse(response);
        response.close();
        return s;
    }

//    @Override
//    public void onError(com.lzy.okgo.model.Response<String> response) {
//        String responseStr = response.toString();
//        if (responseStr.contains("ConnectException")) {
//            onNetError();
//            return;
//        }
//        super.onError(response);
//    }
//
//    abstract void onNetError();


}
