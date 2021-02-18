package com.huadingcloudpackage.www.adapter;



import androidx.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.module.LoadMoreModule;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;

import org.jetbrains.annotations.NotNull;

import java.util.List;


/**
 * Created by Administrator on 2018/1/31 0031.
 * 常规的adapter
 */
public abstract class CommentAdapter<T> extends BaseQuickAdapter<T, BaseViewHolder> implements LoadMoreModule {
    public CommentAdapter(int layoutResId, @Nullable List<T> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, T item) {
        setViewData(helper, item, helper.getLayoutPosition());
        setEvent(helper, item, helper.getLayoutPosition());
    }


    public abstract void setViewData(BaseViewHolder holder, T item, int position);

    public abstract void setEvent(BaseViewHolder holder, T item, int position);

    // 两次点击间隔不能少于1000ms
    private static final int FAST_CLICK_DELAY_TIME = 1000;
    private static long lastClickTime;

    /**
     * @return : 判断是否快速点击
     */
    public static boolean isFastClick() {
        boolean flag = true;
        long currentClickTime = System.currentTimeMillis();
        if ((currentClickTime - lastClickTime) >= FAST_CLICK_DELAY_TIME) {
            flag = false;
        }
        lastClickTime = currentClickTime;
        return flag;
    }

}
