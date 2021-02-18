package com.huadingcloudpackage.www.activity.order;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.huadingcloudpackage.www.R;
import com.huadingcloudpackage.www.base.BaseActivity;
import com.huadingcloudpackage.www.http.Constants;
import com.huadingcloudpackage.www.util.GouwucheAnimator;

import butterknife.BindView;
import me.everything.android.ui.overscroll.OverScrollDecoratorHelper;
import me.everything.android.ui.overscroll.VerticalOverScrollBounceEffectDecorator;
import me.everything.android.ui.overscroll.adapters.RecyclerViewOverScrollDecorAdapter;
import me.everything.android.ui.overscroll.adapters.ScrollViewOverScrollDecorAdapter;

import static me.everything.android.ui.overscroll.OverScrollDecoratorHelper.ORIENTATION_VERTICAL;

/**
 * 文 件 名：BaseOrderDetailActivity
 * 描   述：TODO
 */
public abstract class BaseOrderDetailActivity extends BaseActivity {


    @BindView(R.id.root_state_layout)
    View mStateLayout;//stateLayout根布局
    @BindView(R.id.state_layout_net_error)
    LinearLayout ll_page_state_error;//stateLayout网络错误的布局
    @BindView(R.id.state_layout_empty)
    LinearLayout ll_page_state_empty;//stateLayout无数据的布局
    @BindView(R.id.state_layout_loading)
    LinearLayout ll_page_state_loading;//stateLayout正在加载的布局


    private ObjectAnimator animatorAlpha;

    /**
     * 设置带有阻尼的滑动
     *
     * @param scrollView
     */
    public void setOverScroll(ScrollView scrollView) {
        OverScrollDecoratorHelper.setUpOverScroll(scrollView);
        new VerticalOverScrollBounceEffectDecorator(new ScrollViewOverScrollDecorAdapter(scrollView),
                9f, // Default is 3
                VerticalOverScrollBounceEffectDecorator.DEFAULT_TOUCH_DRAG_MOVE_RATIO_BCK,
                VerticalOverScrollBounceEffectDecorator.DEFAULT_DECELERATE_FACTOR);
    }

    /**
     * 设置带有阻尼的滑动
     *
     * @param recyclerView
     */
    public void setOverScroll(RecyclerView recyclerView) {
        OverScrollDecoratorHelper.setUpOverScroll(recyclerView, ORIENTATION_VERTICAL);
     }

    /**
     * 改变页面布局
     *
     * @param pageState 传入的页面状态
     */
    public void changePageState(String pageState) {
        switch (pageState) {
            case Constants.PageState.NORMAL:
//                alphaAnima(mStateLayout, 1000, 1.0f, 0f);
                mStateLayout.setVisibility(View.GONE);
                break;
            case Constants.PageState.ERROR:
                mStateLayout.setVisibility(View.VISIBLE);
                ll_page_state_error.setVisibility(View.VISIBLE);
                ll_page_state_empty.setVisibility(View.GONE);
                ll_page_state_loading.setVisibility(View.GONE);
                break;
            case Constants.PageState.EMPTY:
                mStateLayout.setVisibility(View.VISIBLE);
                ll_page_state_error.setVisibility(View.GONE);
                ll_page_state_empty.setVisibility(View.VISIBLE);
                ll_page_state_loading.setVisibility(View.GONE);
                break;
            case Constants.PageState.LOADING:
                mStateLayout.setVisibility(View.VISIBLE);
                ll_page_state_loading.setVisibility(View.VISIBLE);
                ll_page_state_error.setVisibility(View.GONE);
                ll_page_state_empty.setVisibility(View.GONE);
                break;
            case Constants.PageState.INIT:
                mStateLayout.setVisibility(View.VISIBLE);
                ll_page_state_loading.setVisibility(View.GONE);
                ll_page_state_error.setVisibility(View.GONE);
                ll_page_state_empty.setVisibility(View.GONE);
                break;
            default:
                break;
        }
    }


    public void alphaAnima(View alphaView, int animaDuration, float... values) {
        if (animatorAlpha == null) {
            animatorAlpha = ObjectAnimator.ofFloat(alphaView, "alpha", values);
            animatorAlpha.addListener(new Animator.AnimatorListener() {
                @Override
                public void onAnimationStart(Animator animation) {

                }

                @Override
                public void onAnimationEnd(Animator animation) {
                    alphaView.setVisibility(View.GONE);
                }

                @Override
                public void onAnimationCancel(Animator animation) {

                }

                @Override
                public void onAnimationRepeat(Animator animation) {

                }
            });
        }

        animatorAlpha.setDuration(animaDuration);
        animatorAlpha.setFloatValues(values);
        animatorAlpha.start();
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
     *
     * @param tvClick
     */
    public void setClickTvShapeGrayBg(TextView tvClick) {
        tvClick.setVisibility(View.VISIBLE);
        tvClick.setBackground(mContext.getResources().getDrawable(R.drawable.shape_dingdan_gray));
        tvClick.setTextColor(ContextCompat.getColor(mContext, R.color.colorGrayText));
    }

    /**
     * 设置底部点击tv的shape（蓝）背景
     *
     * @param tvClick
     */
    public void setClickTvShapeBlueBg(TextView tvClick) {
        tvClick.setVisibility(View.VISIBLE);
        tvClick.setBackground(mContext.getResources().getDrawable(R.drawable.shape_dingdan_blue));
        tvClick.setTextColor(ContextCompat.getColor(mContext, R.color.colorAccent));
    }


    private ReloadInterface reloadInterface;

    public void setReloadInterface(ReloadInterface reloadInterface) {
        this.reloadInterface = reloadInterface;
    }

    //网络错误重新加载的接口
    public interface ReloadInterface {
        void reloadClickListener();
    }
}



