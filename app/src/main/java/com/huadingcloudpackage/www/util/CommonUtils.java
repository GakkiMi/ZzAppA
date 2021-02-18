package com.huadingcloudpackage.www.util;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.graphics.Rect;
import android.os.Build;
import android.util.DisplayMetrics;
import android.widget.Toast;

public class CommonUtils {

    /**
     * 验证用户密码是否合格
     *
     * @param context
     * @param passWord
     * @return
     */
    public static boolean identify(Context context, String passWord) {
        //注册密码包含数字
        boolean containsDigit = false;
        //注册密码包含字母
        boolean containsLetter = false;
        char[] chars = passWord.toCharArray();
        for (int i = 0; i < chars.length; i++) {
            if (48 <= chars[i] && chars[i] <= 57) {//包含数字
                containsDigit = true;
            } else if (65 <= chars[i] && chars[i] <= 90 || 97 <= chars[i] && chars[i] <= 122) {//包含字母
                containsLetter = true;
            } else {
                containsDigit = false;
                containsLetter = false;
            }

        }
        return containsLetter && containsDigit;
    }

    public static Toast mToast;

    /**
     * 单例吐司
     *
     * @param mContext
     * @param msg
     */
    public static void showInstanceToast(Context mContext, String msg) {
        if (mToast == null) {
            mToast = Toast.makeText(mContext, "", Toast.LENGTH_SHORT);
        }
        mToast.setText(msg);
        mToast.show();
    }

    /**
     * 软键盘是否弹出
     *
     * @param context
     * @return
     */
    public static boolean isSoftShowing(Context context) {
        //获取当前屏幕内容的高度
        int screenHeight = ((Activity) context).getWindow().getDecorView().getHeight();
        //获取View可见区域的bottom
        Rect rect = new Rect();
        ((Activity) context).getWindow().getDecorView().getWindowVisibleDisplayFrame(rect);

        return screenHeight - rect.bottom - getSoftButtonsBarHeight(context) != 0;
    }

    /**
     * 底部虚拟按键栏的高度
     *
     * @return
     */
    @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
    private static int getSoftButtonsBarHeight(Context context) {
        DisplayMetrics metrics = new DisplayMetrics();
        //这个方法获取可能不是真实屏幕的高度
        ((Activity) context).getWindowManager().getDefaultDisplay().getMetrics(metrics);
        int usableHeight = metrics.heightPixels;
        //获取当前屏幕的真实高度
        ((Activity) context).getWindowManager().getDefaultDisplay().getRealMetrics(metrics);
        int realHeight = metrics.heightPixels;
        if (realHeight > usableHeight) {
            return realHeight - usableHeight;
        } else {
            return 0;
        }
    }

    public static int dp2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }


    public static int px2dip(Context context, float pxValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }
}
