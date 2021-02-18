package com.huadingcloudpackage.www.dialog;

import android.app.Dialog;
import android.content.Context;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.huadingcloudpackage.www.R;
import com.huadingcloudpackage.www.util.T;
import com.huadingcloudpackage.www.widget.ShapeTextView;

/**
 * 文 件 名：WarmTipsDialog
 * 描   述：温馨提示dialog
 */
public class WarmTipsDialog extends Dialog {

    public WarmTipsDialog(@NonNull Context context) {
        super(context,R.style.dialog);
    }

    public WarmTipsDialog(@NonNull Context context, int themeResId) {
        super(context, themeResId);
    }



    public static class Builder {

        Context context;
        WarmTipsDialog warmTipsDialog;
        String content;

        public Builder(Context context) {
            this.context = context;
        }


        //设置内容
        public Builder setContent(String content) {
            this.content = content;
            return this;
        }


        public WarmTipsDialog create() {

            View v = LayoutInflater.from(context).inflate(R.layout.view_dialog_tips, null);
            ImageView ivClose = v.findViewById(R.id.view_dialog_tips_iv_close);
            //negativeButton
            TextView tvContent = v.findViewById(R.id.view_dialog_tips_tv_content);
            tvContent.setText(content);

            ivClose.setOnClickListener(v1 -> {
                warmTipsDialog.dismiss();
            });
            warmTipsDialog = new WarmTipsDialog(context, R.style.softInputModeShow);
            //设置点击空白部分不消失  点击返回键可消失
            warmTipsDialog.setCanceledOnTouchOutside(false);
            warmTipsDialog.setCancelable(true);
            warmTipsDialog.setContentView(v);

            return warmTipsDialog;
        }
    }
}
