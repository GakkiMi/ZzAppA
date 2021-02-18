package com.huadingcloudpackage.www.activity.login;


import android.os.Bundle;
import android.widget.ImageView;

import com.huadingcloudpackage.www.R;
import com.huadingcloudpackage.www.base.BaseActivity;
import com.huadingcloudpackage.www.sp.PreferenceManager;

import cn.bingoogolapple.bgabanner.BGABanner;
import cn.bingoogolapple.bgabanner.BGALocalImageSize;


/**
 * 引导页
 */
public class GuideActivity extends BaseActivity {

    BGABanner mBackgroundBanner;

    @Override
    public int getLayoutResId() {
        return R.layout.activity_guide;
    }

    private final int[] mPageImages = {
            R.mipmap.guide_1,
            R.mipmap.guide_2,
            R.mipmap.guide_3,
            R.mipmap.guide_4
    };

    @Override
    public void initView(Bundle savedInstanceState) {
        mBackgroundBanner = findViewById(R.id.banner_guide_background);

        //设置跳过和立即进入的点击事件（默认已经设置了这两个按钮的显示，隐藏）
        mBackgroundBanner.setEnterSkipViewIdAndDelegate(R.id.enter, R.id.skip, new BGABanner.GuideDelegate() {
            @Override
            public void onClickEnterOrSkip() {
                startActivity(LoginActivity.class);
                finish();
            }
        });
        //设置数据
        BGALocalImageSize localImageSize = new BGALocalImageSize(720, 1280, 320, 640);
        mBackgroundBanner.setData(localImageSize,ImageView.ScaleType.CENTER_CROP, mPageImages);
        PreferenceManager.instance().saveIsFirstIn(false);
    }


    @Override
    protected void onResume() {
        super.onResume();
        mBackgroundBanner.setBackgroundResource(android.R.color.white);

    }



}
