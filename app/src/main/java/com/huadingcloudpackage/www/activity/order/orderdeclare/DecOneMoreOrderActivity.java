package com.huadingcloudpackage.www.activity.order.orderdeclare;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;

import com.huadingcloudpackage.www.R;
import com.huadingcloudpackage.www.activityfragment.orderdeclare.DecOneMoreOrderFragment;
import com.huadingcloudpackage.www.base.BaseActivity;
import com.huadingcloudpackage.www.util.StatusBarUtils;

public class DecOneMoreOrderActivity extends BaseActivity {

    private FragmentManager mFragmentManager;

    private DecOneMoreOrderFragment oneMoreOrderFragment;


    @Override
    public int getLayoutResId() {
        return R.layout.activity_dec_one_more_order;
    }

    @Override
    public void initView(Bundle savedInstanceState) {
        StatusBarUtils.changStatusIconCollor(this, false);
        mFragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = mFragmentManager.beginTransaction();

        Bundle bundle = getIntent().getExtras();
        //第一次只加载首页
        oneMoreOrderFragment = DecOneMoreOrderFragment.getInstance(bundle);
        transaction.add(R.id.dec_one_more_order_frame_layout, oneMoreOrderFragment);
        transaction.commit();
    }
}
