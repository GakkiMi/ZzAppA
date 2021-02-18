package com.huadingcloudpackage.www.dialog;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
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

public class CommomDialog extends Dialog implements View.OnClickListener {
    private TextView contentTxt;//内容
    private TextView titleTxt;//标题
    private View view;
    private TextView submitTxt;//确定按钮
    private TextView cancelTxt;//取消按钮

    private Context mContext;
    private CharSequence content;
    private OnCloseListener listener;
    private String positiveName;
    private String negativeName;
    private String title;
    private String negativeColor;
    private String positiveColor;
    private int NegativeButtonVisible = View.VISIBLE;


    public CommomDialog(Context context) {
        super(context, R.style.dialog);
        this.mContext = context;
        Log.e("","------------构造方法");
    }

    public CommomDialog(Context context, int themeResId, String content) {
        super(context, themeResId);
        this.mContext = context;
        this.content = content;
    }


    public CommomDialog(Context context, CharSequence content, OnCloseListener listener) {
        super(context, R.style.dialog);
        this.mContext = context;
        this.content = content;
        this.listener = listener;
    }


    public CommomDialog(Context context, int themeResId, String content, OnCloseListener listener) {
        super(context, themeResId);
        this.mContext = context;
        this.content = content;
        this.listener = listener;
    }

    protected CommomDialog(Context context, boolean cancelable, OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
        this.mContext = context;
    }

    public CommomDialog setTitle(String title) {
        this.title = title;
        return this;
    }

    public CommomDialog setPositiveButton(String name) {
        this.positiveName = name;
        return this;
    }


    public CommomDialog setNegativeButton(String name) {
        this.negativeName = name;
        return this;
    }

    public CommomDialog setContent(CharSequence content) {
        this.content = content;
        return this;
    }

    public CommomDialog setOnCloseListener(OnCloseListener listener) {
        this.listener = listener;
        return this;
    }

    public CommomDialog setNegativeColor(String negativeColor) {
        this.negativeColor = negativeColor;
        return this;
    }

    public CommomDialog setPositiveColor(String positiveColor) {
        this.positiveColor = positiveColor;
        return this;
    }

    public CommomDialog setNegativeButtonVisible(int visible) {
        this.NegativeButtonVisible = visible;
        return this;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_commom);
        setCanceledOnTouchOutside(false);
        Log.e("","------------onCreate");

        initView();
    }

    private void initView() {
        contentTxt = (TextView) findViewById(R.id.content);
        titleTxt = (TextView) findViewById(R.id.title);
        view = (View) findViewById(R.id.view);
        submitTxt = (TextView) findViewById(R.id.submit);
        submitTxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null) {
                    listener.onClick(CommomDialog.this, true);
                }
                CommomDialog.this.dismiss();
            }
        });
        cancelTxt = (TextView) findViewById(R.id.cancel);
        cancelTxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null) {
                    listener.onClick(CommomDialog.this, false);
                }
                CommomDialog.this.dismiss();
            }
        });


        if (!TextUtils.isEmpty(positiveName)) {
            submitTxt.setText(positiveName);
        }

        if (!TextUtils.isEmpty(negativeName)) {
            cancelTxt.setText(negativeName);
        }

        if (!TextUtils.isEmpty(positiveColor)) {
            submitTxt.setTextColor(Color.parseColor(positiveColor));
        }

        if (!TextUtils.isEmpty(negativeColor)) {
            cancelTxt.setTextColor(Color.parseColor(negativeColor));
        }

        if (!TextUtils.isEmpty(title)) {
            titleTxt.setVisibility(View.VISIBLE);
            titleTxt.setText(title);
        } else {
            titleTxt.setVisibility(View.GONE);
        }

        if (!TextUtils.isEmpty(content)) {
            contentTxt.setVisibility(View.VISIBLE);
            contentTxt.setText(content);
        } else {
            contentTxt.setVisibility(View.GONE);
        }
        cancelTxt.setVisibility(NegativeButtonVisible);
        view.setVisibility(NegativeButtonVisible);

    }

    public TextView getContentTxt() {
        return contentTxt;
    }


    public TextView getTitleTxt() {
        return titleTxt;
    }

    public TextView getSubmitTxt() {
        return submitTxt;
    }

    public TextView getCancelTxt() {
        return cancelTxt;
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