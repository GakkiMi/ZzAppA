package com.huadingcloudpackage.www.dialog;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;

import com.huadingcloudpackage.www.R;

import com.huadingcloudpackage.www.widget.PasswordView;

/**
 * 文 件 名：PayEnterPwdDialog
 * 描   述：输入支付密码dialog
 */
public class PayEnterPwdDialog extends Dialog {


    private PasswordView pwdView;

    public PayEnterPwdDialog(@NonNull Context context, String drawmoney, PayEnterPwdDialog.IminputComplete iminputComplete) {
        this(context, R.style.dialog, drawmoney, iminputComplete);
    }

    public PayEnterPwdDialog(@NonNull Context context, int themeResId, String drawmoney, PayEnterPwdDialog.IminputComplete iminputComplete) {
        super(context, themeResId);

        setContentView(R.layout.pop_enter_password);
        setCanceledOnTouchOutside(false);

        mIminputComplete = iminputComplete;

        pwdView = findViewById(R.id.pwd_view);
        pwdView.getMoneyText().setText(drawmoney);
        //添加密码输入完成的响应
        pwdView.setOnPasswordInputFinish(password -> {
            //输入完成的回调
        });

        // 监听X关闭按钮
        pwdView.getImgCancel().setOnClickListener(v -> dismiss());

        // 监听键盘上方的返回
        pwdView.getVirtualKeyboardView().getLayoutBack().setOnClickListener(v -> dismiss());

        pwdView.getTvForgetPwd().setOnClickListener(v -> {
            if (mIminputComplete != null) {
                mIminputComplete.forgetPwd();
            }
        });

        pwdView.getTvSure().setOnClickListener(v -> {

            String pwd = pwdView.getPwd();
            if (pwd.length() == 6) {
                if (mIminputComplete != null) {
                    mIminputComplete.inputOverListener(pwd);
                }
            }
        });


        Window dialogWindow = this.getWindow();
        dialogWindow.setGravity(Gravity.BOTTOM | Gravity.LEFT);
        dialogWindow.setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        WindowManager.LayoutParams lp = dialogWindow.getAttributes();
        lp.x = 0;
        lp.y = 0;

        dialogWindow.setAttributes(lp);
        dialogWindow.setWindowAnimations(R.style.pay_dialog_anima);

    }


    public void againInput() {
        pwdView.againInput();
    }

    public void pwdErrorTip() {
        pwdView.pwdErrorTip();
    }



    PayEnterPwdDialog.IminputComplete mIminputComplete;


    public interface IminputComplete {
        void forgetPwd();

        void inputOverListener(String inputPwd);
    }

}
