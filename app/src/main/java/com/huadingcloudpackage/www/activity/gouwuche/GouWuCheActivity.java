package com.huadingcloudpackage.www.activity.gouwuche;

import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;

import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.huadingcloudpackage.www.R;
import com.huadingcloudpackage.www.activityfragment.BGouWuCarFragment;
import com.huadingcloudpackage.www.activityfragment.orderdeclare.DeclareOrderListFragment;
import com.huadingcloudpackage.www.base.BaseActivity;
import com.huadingcloudpackage.www.util.StatusBarUtils;

import butterknife.BindView;

/**
 * 购物车界面
 */

public class GouWuCheActivity extends BaseActivity {


    private FragmentManager mFragmentManager;

    private BGouWuCarFragment gouWuCarFragment;


    @Override
    public int getLayoutResId() {
        return R.layout.activity_gou_wu_che;
    }

    @Override
    public void initView(Bundle savedInstanceState) {
        StatusBarUtils.changStatusIconCollor(this, false);
        mFragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = mFragmentManager.beginTransaction();

        Bundle bundle = getIntent().getExtras();
        if (bundle == null) {
            bundle = new Bundle();
        }
        bundle.putString("tag", "GouWuCheActivity");//增加这条数据只是为了让bundle不为空 在BGouWuCarFragment如果bundle不为空则显示返回箭头
        //第一次只加载首页
        gouWuCarFragment = BGouWuCarFragment.getInstance(bundle);
        transaction.add(R.id.gou_wu_che_frame_layout, gouWuCarFragment);
        transaction.commit();
    }
}
