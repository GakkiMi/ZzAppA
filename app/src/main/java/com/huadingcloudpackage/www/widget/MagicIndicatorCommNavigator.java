package com.huadingcloudpackage.www.widget;

import android.content.Context;
import android.graphics.Color;
import android.view.View;

import androidx.viewpager.widget.ViewPager;

import net.lucode.hackware.magicindicator.buildins.UIUtil;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.CommonNavigator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.CommonNavigatorAdapter;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerIndicator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerTitleView;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.indicators.LinePagerIndicator;

/**
 * 文 件 名：MagicIndicatorCommNavigator
 * 描   述：magic公共的指示器
 */
public class MagicIndicatorCommNavigator extends CommonNavigator {

    private ViewPager mViewPager;
    private String[] mTitiles;


    /**
     *
     * @param isAdjustMode  true为充满均分模式   false为scroll模式
     */
    public MagicIndicatorCommNavigator(Context context,String[] titles,ViewPager viewPager,boolean isAdjustMode) {
        super(context);
        this.mTitiles = titles;
        this.mViewPager=viewPager;
        setAdjustMode(isAdjustMode);
        initCommonNavigatorAdapter(isAdjustMode?0:13);
    }


    private void initCommonNavigatorAdapter(int textHorPadding) {
        CommonNavigatorAdapter commonNavigatorAdapter = new CommonNavigatorAdapter() {
            @Override
            public int getCount() {
                return mTitiles == null ? 0 : mTitiles.length;
            }

            @Override
            public IPagerTitleView getTitleView(Context context, final int index) {
                MySimplePagerTitleView simplePagerTitleView = new MySimplePagerTitleView(context,textHorPadding);
                simplePagerTitleView.setText(mTitiles[index]);
                simplePagerTitleView.setNormalColor(Color.parseColor("#CCFFFFFF"));
                simplePagerTitleView.setSelectedColor(Color.parseColor("#FFFFFF"));
                simplePagerTitleView.setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mViewPager.setCurrentItem(index);
                    }
                });
                return simplePagerTitleView;
            }

            @Override
            public IPagerIndicator getIndicator(Context context) {
                LinePagerIndicator indicator = new LinePagerIndicator(context);
                indicator.setColors(Color.parseColor("#FFFFFF"));
                indicator.setLineWidth(UIUtil.dip2px(context, 25));
                indicator.setRoundRadius(UIUtil.dip2px(context, 2));
                indicator.setMode(2);
                return indicator;
            }
        };
        setAdapter(commonNavigatorAdapter);
    }


}
