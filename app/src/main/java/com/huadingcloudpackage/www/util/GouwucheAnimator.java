package com.huadingcloudpackage.www.util;

import android.animation.Animator;
import android.animation.Keyframe;
import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.util.Log;
import android.view.View;

import static android.view.View.GONE;

/**
 * 文 件 名：GouwucheAnimator
 * 描   述：TODO
 */
public class GouwucheAnimator {


    private ObjectAnimator animatorHide;
    private ObjectAnimator animatorShow;
    private View mHideView;
    private View mShowView;
    private boolean isAlphaAniming = false;
    private ObjectAnimator animatorAlpha;

    private static final String TAG = "GouwucheAnimator";


    /**
     * 初始化透明动画对象
     */
    public void initAlphaAnimator() {
        animatorHide = new ObjectAnimator();
        animatorShow = new ObjectAnimator();

        animatorHide.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {
                isAlphaAniming = true;
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                mHideView.setVisibility(GONE);
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });

        animatorShow.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {
                mShowView.setVisibility(View.VISIBLE);
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                isAlphaAniming = false;
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });
    }

    /**
     * @param hideView      要隐藏的view
     * @param showView      要显示的view
     * @param animaDuration 动画时长
     */
    public void switchAnima(View hideView, View showView, int animaDuration) {
        mHideView = hideView;
        mShowView = showView;

        if (animatorHide == null) {
            animatorHide = new ObjectAnimator();
        }
        animatorHide.setTarget(hideView);
        animatorHide.setPropertyName("alpha");
        animatorHide.setFloatValues(1.0f, 0f);
        animatorHide.setDuration(animaDuration);
        animatorHide.start();

        if (animatorShow == null) {
            animatorShow = new ObjectAnimator();
        }
        animatorShow.setTarget(showView);
        animatorShow.setPropertyName("alpha");
        animatorShow.setFloatValues(0f, 1.0f);
        animatorShow.setDuration(animaDuration);
        animatorShow.start();
    }

    public boolean isAlphaAniming() {
        return isAlphaAniming;
    }

    public void alphaAnima(View alphaView, int animaDuration, float... values) {
        if (animatorAlpha == null) {
            animatorAlpha = ObjectAnimator.ofFloat(alphaView, "alpha", values);
        }
        animatorAlpha.setDuration(animaDuration);
        animatorAlpha.setFloatValues(values);
        animatorAlpha.start();
    }


    //
    public void shakeAnima(View view, float shakeDegrees, long duration) {
        if (view == null) {
            return;
        }
        //TODO 验证参数的有效性

        //先变小后变大
        PropertyValuesHolder scaleXValuesHolder = PropertyValuesHolder.ofKeyframe(View.SCALE_X,
                Keyframe.ofFloat(0f, 1.0f),
                Keyframe.ofFloat(0.5f, 1.1f),
                Keyframe.ofFloat(1.0f, 1.0f)
        );
        PropertyValuesHolder scaleYValuesHolder = PropertyValuesHolder.ofKeyframe(View.SCALE_Y,
                Keyframe.ofFloat(0f, 1.0f),
                Keyframe.ofFloat(0.5f, 1.1f),
                Keyframe.ofFloat(1.0f, 1.0f)
        );

        //先往左再往右
        PropertyValuesHolder rotateValuesHolder = PropertyValuesHolder.ofKeyframe(View.ROTATION,
                Keyframe.ofFloat(0f, 0f),
                Keyframe.ofFloat(0.1f, -shakeDegrees),
                Keyframe.ofFloat(0.2f, shakeDegrees),
                Keyframe.ofFloat(0.3f, -shakeDegrees),
                Keyframe.ofFloat(0.4f, shakeDegrees),
                Keyframe.ofFloat(0.5f, -shakeDegrees),
                Keyframe.ofFloat(0.6f, shakeDegrees),
                Keyframe.ofFloat(0.7f, -shakeDegrees),
                Keyframe.ofFloat(0.8f, shakeDegrees),
                Keyframe.ofFloat(0.9f, -shakeDegrees),
                Keyframe.ofFloat(1.0f, 0f)
        );

        ObjectAnimator objectAnimator = ObjectAnimator.ofPropertyValuesHolder(view, scaleXValuesHolder, scaleYValuesHolder, rotateValuesHolder);
        objectAnimator.setDuration(duration);
        objectAnimator.start();
    }


}
