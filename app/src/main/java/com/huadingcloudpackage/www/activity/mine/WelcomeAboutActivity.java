package com.huadingcloudpackage.www.activity.mine;

import android.os.Bundle;
import android.widget.ImageView;

import com.huadingcloudpackage.www.R;
import com.huadingcloudpackage.www.base.BaseActivity;
import com.huadingcloudpackage.www.sp.PreferenceManager;

import butterknife.BindView;
import cn.bingoogolapple.bgabanner.BGABanner;
import cn.bingoogolapple.bgabanner.BGALocalImageSize;

/**
 * 欢迎页面
 */
public class WelcomeAboutActivity extends BaseActivity {


    @BindView(R.id.banner_guide_background)
    BGABanner bannerGuideBackground;

    @Override
    public int getLayoutResId() {
        return R.layout.activity_welcome_about;
    }

    private final int[] mPageImages = {
            R.mipmap.guide_1,
            R.mipmap.guide_2,
            R.mipmap.guide_3,
            R.mipmap.guide_4
    };

    @Override
    public void initView(Bundle savedInstanceState) {
        initToolbar();
        setBackBtn();
        setTitle("欢迎页面");
        //设置数据
        BGALocalImageSize localImageSize = new BGALocalImageSize(720, 1280, 320, 640);
        bannerGuideBackground.setData(localImageSize, ImageView.ScaleType.CENTER_CROP, mPageImages);
        PreferenceManager.instance().saveIsFirstIn(false);
    }
    @Override
    protected void onResume() {
        super.onResume();
        bannerGuideBackground.setBackgroundResource(android.R.color.white);

    }
}
