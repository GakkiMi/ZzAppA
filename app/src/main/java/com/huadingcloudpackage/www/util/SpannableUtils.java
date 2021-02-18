package com.huadingcloudpackage.www.util;

import android.graphics.Color;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.text.style.RelativeSizeSpan;

/**
 * 文 件 名：SpannableUtils
 * 描   述：TODO
 */
public class SpannableUtils {

    /**
     * 实现  ¥ 及 /件 小化
     * @param value
     * @return
     */
    public static SpannableString changeSpannableTv(String value) {
        SpannableString spannableString = new SpannableString(value);
        if (value.contains("/")) {
            int x = value.indexOf("/");
            spannableString.setSpan(new RelativeSizeSpan(0.7f), x, value.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            spannableString.setSpan(new ForegroundColorSpan(Color.parseColor("#999999")), x, value.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        }
        if (value.contains("¥")) {
            int x = value.indexOf("¥");
            spannableString.setSpan(new RelativeSizeSpan(0.8f), x, x + 1, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        }
        return spannableString;
    }


    /**
     * 实现 金额后面的 .00小化
     * @param value
     * @return
     */
    public static SpannableString changTvSize(String value) {
        SpannableString spannableString = new SpannableString(value);
        if (value.contains(".")) {
            spannableString.setSpan(new RelativeSizeSpan(0.8f), value.indexOf("."), value.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        }
        return spannableString;
    }



}
