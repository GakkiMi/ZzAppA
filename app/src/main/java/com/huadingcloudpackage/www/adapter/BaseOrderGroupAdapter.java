package com.huadingcloudpackage.www.adapter;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;


import com.huadingcloudpackage.www.R;
import com.huadingcloudpackage.www.adapter.CommentAdapter;

import java.util.List;

/**
 * 文 件 名：BaseOrderGroupAdapter
 * 描   述：订单列表组适配器（基类）
 */
public abstract class BaseOrderGroupAdapter<T>  extends CommentAdapter<T> {

    public int showMinCount = 3;//条目最小展示数量

    private Context mContext;

    public BaseOrderGroupAdapter(Context context, int layoutResId, @Nullable List<T> data) {
        super(layoutResId, data);
        this.mContext=context;
    }

    /**
     * @param tv    传入的TextView
     * @param style 0标识灰色  1标识蓝色  2标识隐藏
     * @param text  显示文本
     */
    public void setBaseButtonStyle(TextView tv, int style, String text) {
        if (tv != null) {
            tv.setText(text);
            if (style == 0) {
                setClickTvShapeGrayBg(tv);
            } else if (style == 1) {
                setClickTvShapeBlueBg(tv);
            } else if (style == 2) {
                tv.setVisibility(View.GONE);
            }
        }
    }

    /**
     * 设置底部点击tv的shape（灰）背景
     * @param tvClick
     */
    public void setClickTvShapeGrayBg(TextView tvClick){
        tvClick.setVisibility(View.VISIBLE);
        tvClick.setBackground(mContext.getResources().getDrawable(R.drawable.shape_dingdan_gray));
        tvClick.setTextColor(ContextCompat.getColor(mContext, R.color.colorGrayText));
    }
    /**
     * 设置底部点击tv的shape（蓝）背景
     * @param tvClick
     */
    public void setClickTvShapeBlueBg(TextView tvClick){
        tvClick.setVisibility(View.VISIBLE);
        tvClick.setBackground(mContext.getResources().getDrawable(R.drawable.shape_dingdan_blue));
        tvClick.setTextColor(ContextCompat.getColor(mContext, R.color.colorAccent));
    }

    // 箭头的动画
    public void doArrowAnim(boolean isExpand, ImageView ivDown) {
        if (isExpand) {
            // 当前是展开，箭头由下变为上
            ObjectAnimator.ofFloat(ivDown, "rotation", 0, 180).start();
        } else {
            // 当前是收起，箭头由上变为下
            ObjectAnimator.ofFloat(ivDown, "rotation", 180, 0).start();
        }
    }

    /**
     * 初始化rv无数据的view
     */
    public View getRvEmptyView() {
        View rvEmptyView = LayoutInflater.from(mContext).inflate(R.layout.layout_empty_view, null);
        ImageView ivEmpty = rvEmptyView.findViewById(R.id.iv_empty);
        TextView tvEmpty = rvEmptyView.findViewById(R.id.tv_empty);
        ivEmpty.setImageResource(R.drawable.img_order_no_data);
        tvEmpty.setText("暂无订单");
        return rvEmptyView;
    }

}
