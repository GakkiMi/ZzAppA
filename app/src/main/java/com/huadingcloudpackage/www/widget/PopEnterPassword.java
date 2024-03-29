package com.huadingcloudpackage.www.widget;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.PopupWindow;

import com.huadingcloudpackage.www.R;
import com.huadingcloudpackage.www.adapter.OnPasswordInputFinish;


/**
 * 输入支付密码
 *
 * @author lining
 */
public class PopEnterPassword extends PopupWindow {

    private PasswordView pwdView;

    private View mMenuView;

    private Activity mContext;


    public PopEnterPassword(final Activity context, String drawmoney, IminputComplete iminputComplete) {

        super(context);

        this.mContext = context;

        mIminputComplete = iminputComplete;
        LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        mMenuView = inflater.inflate(R.layout.pop_enter_password, null);

        pwdView = (PasswordView) mMenuView.findViewById(R.id.pwd_view);
        pwdView.getMoneyText().setText(drawmoney);
        //添加密码输入完成的响应
        /*pwdView.setOnFinishInput(new OnPasswordInputFinish() {
            @Override
            public void inputFinish(final String password) {

                new Thread(new Runnable() {

                    @Override
                    public void run() {
                        // 模拟耗时的操作。
                        try {
                            Thread.sleep(500);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }

                        mContext.runOnUiThread(new Runnable() {

                            @Override
                            public void run() {

                                pwd = password;
                                if (mIminputComplete != null) {
                                    mIminputComplete.inputOverListener(password);
                                }

//                                Toast.makeText(mContext, "正在验证...", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }

                }).start();
            }
        });*/

        // 监听X关闭按钮
        pwdView.getImgCancel().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

        // 监听键盘上方的返回
        pwdView.getVirtualKeyboardView().getLayoutBack().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

        pwdView.getTvForgetPwd().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mIminputComplete != null) {
                    mIminputComplete.forgetPwd();
                }
            }
        });


        // 设置SelectPicPopupWindow的View
        this.setContentView(mMenuView);
        // 设置SelectPicPopupWindow弹出窗体的宽
        this.setWidth(LayoutParams.MATCH_PARENT);
        // 设置SelectPicPopupWindow弹出窗体的高
        this.setHeight(LayoutParams.WRAP_CONTENT);
        // 设置SelectPicPopupWindow弹出窗体可点击
        this.setFocusable(true);
        // 设置SelectPicPopupWindow弹出窗体动画效果
        this.setAnimationStyle(R.style.mypopwindow_anim_style);
        // 实例化一个ColorDrawable颜色为半透明
        ColorDrawable dw = new ColorDrawable(0x66000000);
        // 设置SelectPicPopupWindow弹出窗体的背景
        this.setBackgroundDrawable(dw);

    }

    public void againInput() {
        pwdView.againInput();
    }


    IminputComplete mIminputComplete;
    String pwd;

    public String inputComplete() {
        return pwd;
    }

    public interface IminputComplete {
        void forgetPwd();

        void inputOverListener(String inputPwd);
    }
}
