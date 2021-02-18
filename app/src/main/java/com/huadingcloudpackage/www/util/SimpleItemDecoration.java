package com.huadingcloudpackage.www.util;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.view.View;

import androidx.annotation.ColorRes;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

/**
 * @version V1.0
 * @类描述：
 * @创建人：李歌
 * @修改人：
 * @修改备注：
 */
public class SimpleItemDecoration  extends RecyclerView.ItemDecoration {
    private Context context;
    private int dividerHeight = 2;//分割线的高度为2px
    private Paint dividerPaint;//分割线画笔
    private int paddingLeft = -1;//分割线左边空白长度
    private int paddingRight = -1;//分割线右边空白长度

    private SimpleItemDecoration(Context context){
        this.context = context;
        dividerPaint = new Paint();
        dividerPaint.setColor(Color.parseColor("#e6e6e6"));//默认的分割线颜色
    }

    /**
     * 创建对象
     * @param context 上下文
     * @return
     */
    public static SimpleItemDecoration getInstance(Context context){
        return new SimpleItemDecoration(context);
    }

    //实现padding的效果
    @Override
    public void getItemOffsets(@NonNull Rect outRect, @NonNull View view, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
        super.getItemOffsets(outRect, view, parent, state);
        if (!isLinearAndVertical(parent)){
            return;
        }

        outRect.bottom = this.dividerHeight;//类似加了一个bottom padding
    }

    //实现绘制背景的效果,填充padding等部分
    @Override
    public void onDraw(@NonNull Canvas c, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
        super.onDraw(c, parent, state);
        if (!isLinearAndVertical(parent)){
            return;
        }

        int count = parent.getChildCount();
        if (paddingLeft == -1){
            paddingLeft = parent.getPaddingLeft();
        }
        if (paddingRight == -1){
            paddingRight = parent.getPaddingRight();
        }

        for (int i = 0; i < count - 1;i++){
            View child = parent.getChildAt(i);
            float top = child.getBottom();
            float bottom = child.getBottom() + dividerHeight;
            c.drawRect(paddingLeft,top,parent.getWidth() - paddingRight,bottom,dividerPaint);
        }
    }

    /**
     * 设置左边空白边距
     * @param paddingLeft 单位dp
     */
    public SimpleItemDecoration setPaddingLeft(int paddingLeft){
        this.paddingLeft = CommonUtils.dp2px(context,paddingLeft);
        return this;
    }

    /**
     * 设置右边空白边距
     * @param paddingRight  单位dp
     */
    public SimpleItemDecoration setPaddingRight(int paddingRight){
        this.paddingRight = CommonUtils.dp2px(context,paddingRight);
        return this;
    }

    /**
     * 设置分割线的高度
     * @param dividerHeight  单位dp
     */
    public SimpleItemDecoration setDividerHeight(int dividerHeight){
        this.dividerHeight = CommonUtils.dp2px(context,dividerHeight);
        return this;
    }

    /**
     * 设置分割线的颜色
     * @param colorId 颜色的
     * @return
     */
    public SimpleItemDecoration setDividerColor(@ColorRes int colorId){
        dividerPaint.setColor(context.getResources().getColor(colorId));
        return this;
    }

    /**
     * 判断LayoutManager类型，目前GroupItemDecoration仅支持LinearLayoutManager.VERTICAL
     */
    public boolean isLinearAndVertical(RecyclerView parent){
        RecyclerView.LayoutManager manager = parent.getLayoutManager();
        if (!(manager instanceof LinearLayoutManager)){
            return false;
        }else {
            if (((LinearLayoutManager)manager).getOrientation() != LinearLayoutManager.VERTICAL){
                return false;
            }
        }
        return true;
    }
}