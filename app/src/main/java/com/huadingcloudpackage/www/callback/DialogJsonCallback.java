package com.huadingcloudpackage.www.callback;

import android.content.Context;

import com.huadingcloudpackage.www.widget.NewLoadingDialog;
import com.lzy.okgo.request.base.Request;

/**
 * @name 工程名：yaosuwang
 * @class 包名：com.yaosuce.yaosuwang.http
 * @describe 描述：
 * @anthor 作者：whffi QQ:84569945
 * @time 时间：2017/6/13 17:00
 * @change 变更：
 * @chang 时间：
 * @class 描述：
 */

public abstract class DialogJsonCallback<T> extends JsonCallback<T> {

    // private LoadingDialog dialog;

    private NewLoadingDialog dialog;

    private void initDialog(Context context) {
        dialog = new NewLoadingDialog(context);
        dialog.show();
    }


    public DialogJsonCallback(Context context) {
        super();
        initDialog(context);
    }


    @Override
    public void onStart(Request request) {
        if (dialog != null && !dialog.isShowing()) {
            dialog.getWindow().setDimAmount(0);
            dialog.show();
        }

    }

    @Override
    public void onFinish() {
        //            //网络请求结束后关闭对话框
        if (dialog != null && dialog.isShowing()) {
            dialog.dismiss();
        }
    }
}