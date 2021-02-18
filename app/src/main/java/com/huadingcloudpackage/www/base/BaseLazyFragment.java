package com.huadingcloudpackage.www.base;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Color;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.text.style.RelativeSizeSpan;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.guoquan.yunpu.util.ComTools;
import com.huadingcloudpackage.www.R;
import com.huadingcloudpackage.www.sp.PreferenceManager;
import com.huadingcloudpackage.www.util.T;
import com.huadingcloudpackage.www.widget.NewLoadingDialog;
import com.lzy.okgo.OkGo;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.Unbinder;

import static android.view.View.GONE;


public abstract class BaseLazyFragment extends Fragment {

    protected InputMethodManager inputMethodManager;
    public NewLoadingDialog loadingDialog;

    public ComTools comTools;
    public int page = 1;
    private Unbinder mUnbinder;
    public int mPage = 1;

    private boolean isFirstLoad = false;

    private View mView;

    private ImageView loadingView;

    private boolean isShowloadingWithView = false;//判断加载框是以dialog形式出现还是以view形式出现  默认false 以dialog形式出现
    private long duration = 300;//动画时长


    @Subscribe
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this);
        }
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initFontScale();
        comTools = new ComTools();
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        inputMethodManager = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        view.setClickable(true);
        mUnbinder = ButterKnife.bind(this, view);
        loadingDialog = new NewLoadingDialog(getContext());

        mView = view;

        initLoadingView();

        isFirstLoad = true;
        if (getUserVisibleHint()) {
//            Log.i("BaseLazyFragment", "-------onCreateView--懒加载:" + getClass().getName());
            initView(view);
            isFirstLoad = false;
        }


        initData();
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser && isFirstLoad) {
//            Log.i("BaseLazyFragment", "-------setUserVisibleHint--懒加载" + getClass().getName());
            initView(mView);
            isFirstLoad = false;

        }
    }

    /**
     * 设置加载框以view的形式进行展示
     *
     * @param showloadingWithView
     */
    public void setShowloadingWithView(boolean showloadingWithView) {
        isShowloadingWithView = showloadingWithView;
    }


    protected void initView(View view) {
//        mAppTitleBar = (AppTitleBar) view.findViewById(R.id.app_title_bar);
    }

    protected void initData() {

    }

    private void initFontScale() {
        Configuration configuration = getResources().getConfiguration();
        configuration.fontScale = (float) 1;
        //0.85 小, 1 标准大小, 1.15 大，1.3 超大 ，1.45 特大
        DisplayMetrics metrics = new DisplayMetrics();
        getActivity().getWindowManager().getDefaultDisplay().getMetrics(metrics);
        metrics.scaledDensity = configuration.fontScale * metrics.density;
        getActivity().getBaseContext().getResources().updateConfiguration(configuration, metrics);
    }


    @Subscribe
    public void onDestroyView() {
        super.onDestroyView();
        mUnbinder.unbind();
        OkGo.getInstance().cancelTag(this);
        if (EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().unregister(this);
        }
    }

    @Override
    public void onPause() {
        super.onPause();

    }

    @Override
    public void onResume() {
        super.onResume();

    }

    public void startActivity(Class<?> clz) {
        startActivity(clz, null);
    }

    /**
     * 携带数据的页面跳转
     *
     * @param clz
     * @param bundle
     */
    public void startActivity(Class<?> clz, Bundle bundle) {
        Intent intent = new Intent();
        intent.setClass(getContext(), clz);
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        startActivity(intent);
    }

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


    /**
     * 隐藏键盘
     */
    protected void hideSoftKeyboard() {
        if (getActivity().getWindow().getAttributes().softInputMode != WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN) {
            if (getActivity().getCurrentFocus() != null)
                if (inputMethodManager != null) {
                    inputMethodManager.hideSoftInputFromWindow(getActivity().getCurrentFocus().getWindowToken(),
                            InputMethodManager.HIDE_NOT_ALWAYS);
                }
        }
    }
//
//    @AfterPermissionGranted(1)
//    private void requirePermission() {
//        String[] permissions = {
//                Manifest.permission.ACCESS_COARSE_LOCATION,
//                Manifest.permission.ACCESS_FINE_LOCATION,
//                Manifest.permission.READ_PHONE_STATE,
//                Manifest.permission.WRITE_EXTERNAL_STORAGE
//        };
//        String[] permissionsForQ = {
//                Manifest.permission.ACCESS_COARSE_LOCATION,
//                Manifest.permission.ACCESS_FINE_LOCATION,
//                Manifest.permission.ACCESS_BACKGROUND_LOCATION, //target为Q时，动态请求后台定位权限
//                Manifest.permission.READ_PHONE_STATE,
//                Manifest.permission.WRITE_EXTERNAL_STORAGE
//        };
//        if (Build.VERSION.SDK_INT >= 29 ? EasyPermissions.hasPermissions(getContext(), permissionsForQ) :
//                EasyPermissions.hasPermissions(getContext(), permissions)) {
////            Toast.makeText(getContext(), "权限OK", Toast.LENGTH_LONG).show();
//        } else {
//            EasyPermissions.requestPermissions(this, "需要权限",
//                    1, Build.VERSION.SDK_INT >= 29 ? permissionsForQ : permissions);
//        }
//    }

    /**
     * @return :判断是否登录
     */
    public boolean isLogin() {
        if (PreferenceManager.instance().getToken().equals("")) {
            return false;
        } else {
            return true;
        }
    }

    public void showLoading() {
        if (isShowloadingWithView) {
            showLoadingView();
            return;
        }
        if (loadingDialog == null) {
            loadingDialog = new NewLoadingDialog(getContext());
        }
        loadingDialog.show();
    }

    public void dissMissLoading() {
        if (isShowloadingWithView) {
            hideLoadingView();
            return;
        }
        if (loadingDialog != null) {
            loadingDialog.cancel();
        }
    }


    /**
     * 初始化加载框 View
     */
    protected void initLoadingView() {
        if (isShowloadingWithView) {
            loadingView = mView.findViewById(R.id.img_loading);
            RequestOptions options = new RequestOptions()
                    .diskCacheStrategy(DiskCacheStrategy.RESOURCE);
            Glide.with(getContext()).load(R.mipmap.loading).apply(options).into(loadingView);
            initAlphaAnimator();
        }
    }


    /**
     * 加载框以view的形式进行展示
     */
    public void showLoadingView() {
        if (loadingView != null) {
            if (animatorShow == null) {
                initAlphaAnimator();
            }
            animatorShow.start();
        }
    }

    /**
     * 加载框以view的形式进行隐藏
     */
    public void hideLoadingView() {
        if (loadingView != null) {
            if (animatorHide == null) {
                initAlphaAnimator();
            }
            animatorHide.start();
        }
    }

    /**
     * 初始化透明动画对象
     */

    private boolean isAlphaAniming = false;
    private ObjectAnimator animatorShow;
    private ObjectAnimator animatorHide;

    public void initAlphaAnimator() {
        if (animatorHide == null) {
            animatorHide = new ObjectAnimator();
            animatorHide.setTarget(loadingView);
            animatorHide.setPropertyName("alpha");
            animatorHide.setFloatValues(1.0f, 0f);
            animatorHide.setDuration(duration);
            animatorHide.addListener(new Animator.AnimatorListener() {
                @Override
                public void onAnimationStart(Animator animation) {
                    isAlphaAniming = true;
                }

                @Override
                public void onAnimationEnd(Animator animation) {
                    loadingView.setVisibility(GONE);
                }

                @Override
                public void onAnimationCancel(Animator animation) {

                }

                @Override
                public void onAnimationRepeat(Animator animation) {

                }
            });
        }
        if (animatorShow == null) {
            animatorShow = new ObjectAnimator();
            animatorShow.setTarget(loadingView);
            animatorShow.setPropertyName("alpha");
            animatorShow.setFloatValues(0f, 1.0f);
            animatorShow.setDuration(duration);
            animatorShow.addListener(new Animator.AnimatorListener() {
                @Override
                public void onAnimationStart(Animator animation) {
                    loadingView.setVisibility(View.VISIBLE);
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


    }




    /**
     * 吐司
     *
     * @param msg :
     */
    public void T(String msg) {
        T.showShort(getContext(), msg);
    }
}
