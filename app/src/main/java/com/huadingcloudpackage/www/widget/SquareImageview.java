package com.huadingcloudpackage.www.widget;

import android.content.Context;
import android.util.AttributeSet;

import androidx.appcompat.widget.AppCompatImageView;

/**
 * @name 工程名：yaosuwang
 * @class 包名：com.yaosuce.yaosuwang.widget
 * @describe 描述：
 * @anthor 作者：whffi QQ:84569945
 * @time 时间：2018/3/18 21:18
 * @change 变更：
 * @chang 时间：
 * @class 描述：
 */

public class SquareImageview extends AppCompatImageView {

    public SquareImageview(Context context) {
        super(context);
    }

    public SquareImageview(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public SquareImageview(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        setMeasuredDimension(getDefaultSize(0, widthMeasureSpec), getDefaultSize(0, heightMeasureSpec));
        int childWidthSize = getMeasuredWidth();
        // 高度和宽度一样
        heightMeasureSpec = widthMeasureSpec = MeasureSpec.makeMeasureSpec(childWidthSize, MeasureSpec.EXACTLY);
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }
}
