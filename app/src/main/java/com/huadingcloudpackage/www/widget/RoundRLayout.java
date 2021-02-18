package com.huadingcloudpackage.www.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.RelativeLayout;

import com.huadingcloudpackage.www.R;



/**
 * 圆角布局
 */
public class RoundRLayout extends RelativeLayout {
    private float roundLayoutRadius = 14f;
    private Path roundPath;
    private Path strokePath;
    private RectF rectF;
    private Paint strokePaint;
    private float strokeWidth;
    private int strokeColor;

    private boolean flag = true;

    public RoundRLayout(Context context) {
        this(context, null);
    }

    public RoundRLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.RoundRLayout);
        roundLayoutRadius = typedArray.getDimensionPixelSize(R.styleable.RoundRLayout_roundLayoutRadius, (int) roundLayoutRadius);
        strokeWidth = typedArray.getDimensionPixelSize(R.styleable.RoundRLayout_roundLayoutStrokeWidth, 0);
        strokeColor = typedArray.getColor(R.styleable.RoundRLayout_roundLayoutStrokeColor, Color.parseColor("#33ABABB3"));
        typedArray.recycle();
        init();
    }


    private void init() {
        setWillNotDraw(false);//如果你继承的是ViewGroup,注意此行,否则draw方法是不会回调的;
        roundPath = new Path();
        strokePath = new Path();
        rectF = new RectF();

        strokePaint = new Paint();
        strokePaint.setColor(strokeColor);
        strokePaint.setAntiAlias(true);
        strokePaint.setStyle(Paint.Style.STROKE);
        strokePaint.setStrokeWidth(strokeWidth);
        strokePaint.setDither(true);

    }

    private void setRoundPath() {
        //添加一个圆角矩形到path中, 如果要实现任意形状的View, 只需要手动添加path就行
        roundPath.addRoundRect(rectF, roundLayoutRadius, roundLayoutRadius, Path.Direction.CW);
        strokePath.addRoundRect(new RectF(strokeWidth / 2, strokeWidth / 2, getMeasuredWidth() - strokeWidth / 2, getMeasuredHeight() - strokeWidth / 2),
                roundLayoutRadius, roundLayoutRadius, Path.Direction.CW);
    }


    public void setRoundLayoutRadius(float roundLayoutRadius) {
        this.roundLayoutRadius = roundLayoutRadius;
        setRoundPath();
        postInvalidate();
    }

    /**
     * 此方法在界面刷新的时候会执行多次
     */
    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);
        if (!flag) return;
        rectF.set(0f, 0f, getMeasuredWidth(), getMeasuredHeight());
        setRoundPath();
        flag = false;
    }

    @Override
    public void draw(Canvas canvas) {
        if (roundLayoutRadius > 0f) {
            canvas.clipPath(roundPath);
        }
        super.draw(canvas);
        if (strokeWidth > 0) {
            canvas.drawPath(strokePath, strokePaint);
        }
    }
}
