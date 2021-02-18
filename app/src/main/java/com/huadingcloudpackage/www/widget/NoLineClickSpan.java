package com.huadingcloudpackage.www.widget;

import android.text.TextPaint;
import android.text.style.ClickableSpan;
import android.view.View;

/**
 * 文 件 名：NoLineClickSpan
 * 描   述：TODO
 */
public class NoLineClickSpan extends ClickableSpan {


    public NoLineClickSpan() {
        super();

    }

    @Override
    public void updateDrawState(TextPaint ds) {
        ds.setColor(ds.linkColor);
        ds.setUnderlineText(false); //去掉下划线
    }

    @Override
    public void onClick(View widget) {
    }

}
