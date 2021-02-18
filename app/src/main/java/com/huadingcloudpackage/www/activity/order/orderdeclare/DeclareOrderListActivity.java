package com.huadingcloudpackage.www.activity.order.orderdeclare;

import android.content.Intent;
import android.os.Bundle;
import android.widget.FrameLayout;

import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.huadingcloudpackage.www.R;
import com.huadingcloudpackage.www.activityfragment.orderdeclare.DeclareOrderListFragment;
import com.huadingcloudpackage.www.base.BaseActivity;
import com.huadingcloudpackage.www.util.StatusBarUtils;

import butterknife.BindView;

/**
 *
 */
public class DeclareOrderListActivity extends BaseActivity {

    @BindView(R.id.declare_order_list_frame_layout)
    FrameLayout frameLayout;


    private FragmentManager mFragmentManager;
    private DeclareOrderListFragment declareOrderListFragment;

    @Override
    public int getLayoutResId() {
        return R.layout.activity_declare_order_list;
    }

    @Override
    public void initView(Bundle savedInstanceState) {
        StatusBarUtils.changStatusIconCollor(this, false);
        mFragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = mFragmentManager.beginTransaction();

        //第一次只加载首页
        declareOrderListFragment = new DeclareOrderListFragment();
        transaction.add(R.id.declare_order_list_frame_layout, declareOrderListFragment);
        transaction.commit();
    }


    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        if (declareOrderListFragment != null) {
            int pageIndex = intent.getIntExtra("targetDeclarePageIndex", 0);
            declareOrderListFragment.setViewPagerCurrentItem(pageIndex);
        }
    }
}
