package com.huadingcloudpackage.www.adapter.itemdecoration;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.huadingcloudpackage.www.adapter.CommentAdapter;
import com.huadingcloudpackage.www.util.DisPlayUtils;


/**
 * BravhListDivider
 * 适用于bravh框架的分割线(并对是否有headerview和footerview进行了判断)
 */
public class BravhListDivider extends RecyclerView.ItemDecoration {

    public static final int HORIZONTAL_LIST = LinearLayoutManager.HORIZONTAL;

    public static final int VERTICAL_LIST = LinearLayoutManager.VERTICAL;

    public static final int DIVIDER_ALL = 0;//全部分割线
    public static final int DIVIDER_NO_FIRST = 1;//不绘制第一条分割线
    public static final int DIVIDER_NO_LAST = 2;//不绘制最后一条分割线
    public static final int DIVIDER_NO_FIRST_LAST = 3;//不绘制第一天和最后一条分割线

    private Paint mDividerPaint;//分割线画笔

    private int mDividerHeight;//分割线高度
    private int mDividerOffsetLeft=0;//分割线左侧偏移量
    private int mDividerOffsetRight=0;//分割线右侧偏移量
    private int mDividerMode;//分割线Mode


    private int mOrientation;

    private Context mContext;


    public BravhListDivider(Context context, int orientation) {
        this(context, orientation, DIVIDER_ALL);
    }

    public BravhListDivider(Context context, int orientation, int dividerMode) {
        this.mContext = context;
        this.mDividerMode = dividerMode;
        mDividerHeight = DisPlayUtils.px2dip(2);//默认为2

        setOrientation(orientation);

        mDividerPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mDividerPaint.setColor(Color.parseColor("#EEEEEE"));
        mDividerPaint.setStyle(Paint.Style.FILL);
    }


    public void setOrientation(int orientation) {
        if (orientation != HORIZONTAL_LIST && orientation != VERTICAL_LIST) {
            throw new IllegalArgumentException("invalid orientation");
        }
        mOrientation = orientation;
    }

    public void setmDividerColor(int mDividerColor) {
        mDividerPaint.setColor(ContextCompat.getColor(mContext, mDividerColor));
    }

    public void setmDividerHeight(int mDividerHeight) {
        this.mDividerHeight = DisPlayUtils.px2dip(mDividerHeight);
    }

    public void setmDividerOffsetLeft(int mDividerOffsetLeft) {
        this.mDividerOffsetLeft = mDividerOffsetLeft;
    }

    public void setmDividerOffsetRight(int mDividerOffsetRight) {
        this.mDividerOffsetRight = mDividerOffsetRight;
    }


    @Override
    public void onDraw(@NonNull Canvas c, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
        if (mOrientation == VERTICAL_LIST) {
            drawVertical(c, parent);
        } else {
            drawHorizontal(c, parent);
        }
    }

    public void drawVertical(Canvas c, RecyclerView parent) {
        int left = parent.getPaddingLeft() + mDividerOffsetLeft;
        int right = parent.getWidth() - parent.getPaddingRight() - mDividerOffsetRight;

        //当前界面展示的数量
        int childShowCount = parent.getChildCount();

        //获取条目数量(包括header和footer,分别算一条)
        int itemTotalCount = parent.getAdapter().getItemCount();
        CommentAdapter commentAdapter = (CommentAdapter) parent.getAdapter();
        boolean hasHeader = commentAdapter.hasHeaderLayout();
        boolean hasFooter = commentAdapter.hasFooterLayout();

        for (int i = 0; i < childShowCount - 1; i++) {
            View child = parent.getChildAt(i);
            int top = child.getBottom();
            int bottom = top + mDividerHeight;

            //获取绘制view在适配器中的位置
            int childAdapterPosition = parent.getChildAdapterPosition(child);


            if (childAdapterPosition == 0) {
                if (mDividerMode == DIVIDER_NO_FIRST || mDividerMode == DIVIDER_NO_FIRST_LAST) {

                } else {
                    c.drawRect(left, top, right, bottom, mDividerPaint);
                }
            } else if (childAdapterPosition == itemTotalCount - (hasFooter ? 2 : 1)) {
                if (mDividerMode == DIVIDER_NO_LAST || mDividerMode == DIVIDER_NO_FIRST_LAST) {

                } else {
                    c.drawRect(left, top, right, bottom, mDividerPaint);
                }
            } else {
                c.drawRect(left, top, right, bottom, mDividerPaint);
            }
        }
    }

    public void drawHorizontal(Canvas c, RecyclerView parent) {
        final int top = parent.getPaddingTop();
        final int bottom = parent.getHeight() - parent.getPaddingBottom();

        final int childCount = parent.getChildCount();
        for (int i = 0; i < childCount; i++) {
            final View child = parent.getChildAt(i);
            final RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) child
                    .getLayoutParams();
            final int left = child.getRight() + params.rightMargin;
            final int right = left;
            c.drawRect(left, top, right, bottom, mDividerPaint);
        }
    }

    @Override
    public void getItemOffsets(@NonNull Rect outRect, @NonNull View view, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
        if (mOrientation == VERTICAL_LIST) {
            outRect.set(0, 0, 0, mDividerHeight);
        } else {
            outRect.set(0, 0, mDividerHeight, 0);
        }
    }
}
