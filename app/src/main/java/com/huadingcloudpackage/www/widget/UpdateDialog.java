package com.huadingcloudpackage.www.widget;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.huadingcloudpackage.www.R;


/**
 * @name 工程名：yaosuwang
 * @class 包名：com.yaosuce.yaosuwang.widget
 * @describe 描述：
 * @anthor 作者：whffi QQ:84569945
 * @time 时间：2017/7/5 19:23
 * @change 变更：
 * @chang 时间：
 * @class 描述：
 */

public class UpdateDialog extends Dialog implements View.OnClickListener {
    private TextView contentTxt;
    private TextView versionTxt;

    private TextView submitTxt;
    private ImageView cancel;

    private Context mContext;
    private String content;
    private String version;
    private OnCloseListener listener;
    private String gxlx;


    private Boolean CanceledOnTouchOutside = true;

    public UpdateDialog(Context context) {
        super(context, R.style.dialog);
        this.mContext = context;
    }

    public UpdateDialog(Context context, int themeResId, String content) {
        super(context, themeResId);
        this.mContext = context;
        this.content = content;
    }


    public UpdateDialog(Context context, String content, OnCloseListener listener) {
        super(context, R.style.dialog);
        this.mContext = context;
        this.content = content;
        this.listener = listener;
    }

    public UpdateDialog(Context context, String content, String version, String gxlx, OnCloseListener listener) {
        super(context, R.style.dialog);
        this.mContext = context;
        this.content = content;
        this.version = version;
        this.listener = listener;
        this.gxlx = gxlx;
    }


    public UpdateDialog(Context context, int themeResId, String content, OnCloseListener listener) {
        super(context, themeResId);
        this.mContext = context;
        this.content = content;
        this.listener = listener;
    }

    protected UpdateDialog(Context context, boolean cancelable, OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
        this.mContext = context;
    }


    public UpdateDialog setCancelOutside(Boolean canceledOnTouchOutside) {
        this.CanceledOnTouchOutside = canceledOnTouchOutside;

        return this;
    }


    public UpdateDialog setContent(String content) {
        this.content = content;
        return this;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_update);
        setCanceledOnTouchOutside(false);


        initView();
    }

    private void initView() {
        contentTxt = (TextView) findViewById(R.id.content);
        versionTxt = (TextView) findViewById(R.id.version);

        submitTxt = (TextView) findViewById(R.id.submit);
        submitTxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null) {
                    listener.onClick(UpdateDialog.this, true);
                }
                UpdateDialog.this.dismiss();
            }
        });
        cancel = (ImageView) findViewById(R.id.cancel);


        if (gxlx.equals("N")) {
            cancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null) {
                        listener.onClick(UpdateDialog.this, false);
                    }
                    UpdateDialog.this.dismiss();
                }
            });
        } else {
            cancel.setVisibility(View.GONE);
        }

        this.setCancelable(false);

        contentTxt.setText(content);
        versionTxt.setText("澄明CRM V"+version);
        if (CanceledOnTouchOutside) {
            this.setCanceledOnTouchOutside(true);
        } else {
            this.setCanceledOnTouchOutside(false);
        }


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
//            case R.id.cancel:
//
//                break;
//            case R.id.submit:
//
//                break;
        }
    }

    public interface OnCloseListener {
        void onClick(Dialog dialog, boolean confirm);
    }
}