package com.huadingcloudpackage.www.activity;


import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import androidx.annotation.Nullable;

import com.huadingcloudpackage.www.R;
import com.huadingcloudpackage.www.base.BaseActivity;
import com.huadingcloudpackage.www.util.GlideUtils;

import java.util.List;

import cn.bingoogolapple.bgabanner.BGABanner;

/**
 * 查看大图
 */
public class OtherActivity extends BaseActivity {

    private BGABanner mBannerGuideBackground;
    private RelativeLayout mRelativeLayout;

    @Override
    public int getLayoutResId() {
        return R.layout.activity_other;
    }

    @Override
    public void initView(Bundle savedInstanceState) {

        mBannerGuideBackground = findViewById(R.id.banner_guide_background);
        mRelativeLayout = findViewById(R.id.relative);
        mRelativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
                overridePendingTransition(R.anim.screen_zoom_in,R.anim.screen_zoom_out);
            }
        });
        mBannerGuideBackground.setAdapter(new BGABanner.Adapter<ImageView, String>() {
            @Override
            public void fillBannerItem(BGABanner banner, ImageView itemView, @Nullable String model, int position) {
                GlideUtils.showGildeImg(mContext,model,itemView);
            }
        });
        mBannerGuideBackground.setData((List<String>) getIntent().getSerializableExtra("list"),null);
        mBannerGuideBackground.setCurrentItem(getIntent().getIntExtra("position",0));
        mBannerGuideBackground.setDelegate(new BGABanner.Delegate() {
            @Override
            public void onBannerItemClick(BGABanner banner, View itemView, @Nullable Object model, int position) {
                finish();
                overridePendingTransition(R.anim.screen_zoom_in,R.anim.screen_zoom_out);
            }
        });
    }


}
