package com.huadingcloudpackage.www.util;

import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.huadingcloudpackage.www.R;



/**
 * @name 工程名：yaosuwang
 * @class 包名：com.yaosuce.yaosuwang.util
 * @describe 描述：Toast 工具类
 * @anthor 作者：whffi QQ:84569945
 * @time 时间：2017/6/2 10:18
 * @change 变更：
 * @chang 时间：
 * @class 描述：
 */

public class T {
    static Toast mToast;

    private T() {
        /* cannot be instantiated */
        throw new UnsupportedOperationException("cannot be instantiated");
    }

    public static boolean isShow = true;


    /**
     * 短时间显示Toast
     *
     * @param context
     * @param message
     */
    public static void show(Context context, CharSequence message) {
        if (isShow)

            mToast = new Toast(context);
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View BgView = inflater.inflate(R.layout.toast_center, null);

        //自定义toast文本
        TextView  mTextView = (TextView)BgView.findViewById(R.id.toast_msg);
        mTextView.setText(message);

        mToast.setView(BgView);
        mToast.setGravity(Gravity.CENTER, 0, 0);
        mToast.show();

        //  Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
    }


    /**
     * 短时间显示Toast
     *
     * @param context
     * @param message
     */
    public static void showShort(Context context, CharSequence message) {
        if (isShow)
            mToast = new Toast(context);
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View BgView = inflater.inflate(R.layout.toast_center, null);

        //自定义toast文本
        TextView  mTextView = (TextView)BgView.findViewById(R.id.toast_msg);
        mTextView.setText(message.toString());

        mToast.setView(BgView);
        mToast.setGravity(Gravity.CENTER, 0, 0);
        mToast.show();
    }


    public static void showToastyCenter(Context context, CharSequence message){
        Toast toast = MyToasty.normal(context, message, Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.show();
    }

    public static void showToastyBottom(Context context, CharSequence message){
        Toast toast = MyToasty.normal(context, message, Toast.LENGTH_SHORT);
        toast.show();
    }

}