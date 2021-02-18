package com.huadingcloudpackage.www.widget;

import android.content.Context;

import androidx.recyclerview.widget.LinearLayoutManager;

/**
 * 文 件 名：NoScrollLinearLayoutManager
 * 描   述：禁用rv滑动
 */
public class NoScrollLinearLayoutManager extends LinearLayoutManager {

    public NoScrollLinearLayoutManager(Context context) {
        super(context);
    }

    @Override
    public boolean canScrollVertically() {
        return false;
    }
}
