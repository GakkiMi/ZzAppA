package com.huadingcloudpackage.www.activity.login;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.huadingcloudpackage.www.MainActivity;
import com.huadingcloudpackage.www.R;
import com.huadingcloudpackage.www.base.BaseActivity;
import com.huadingcloudpackage.www.sp.PreferenceManager;
import com.huadingcloudpackage.www.util.StatusBarUtils;

/**
 * 欢迎页
 */
public class WelcomeActivity extends AppCompatActivity {
    
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // 避免从桌面启动程序后，会重新实例化入口类的activity
        if (!this.isTaskRoot()) { // 当前类不是该Task的根部，那么之前启动
            Intent intent = getIntent();
            if (intent != null) {
                String action = intent.getAction();
                if (intent.hasCategory(Intent.CATEGORY_LAUNCHER) && Intent.ACTION_MAIN.equals(action)) { // 当前类是从桌面启动的
                    finish(); // finish掉该类，直接打开该Task中现存的Activity
                    return;
                }
            }
        }
        setContentView(R.layout.activity_welcome);
        StatusBarUtils.setTranslucentStatus(this);
        next();
    }



    private void next() {
        if (PreferenceManager.instance().getIsFirstIn()) {
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    //引导页
                    startActivity(LoginActivity.class, null);
                    finish();
                }
            }, 1500);
            PreferenceManager.instance().saveIsFirstIn(false);
        } else {
            if (PreferenceManager.instance().getToken() == null || PreferenceManager.instance().getToken().equals("")) {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        //登陆页
                        startActivity(LoginActivity.class, null);
                        finish();
                    }
                }, 1500);
                PreferenceManager.instance().saveIsFirstIn(false);
            } else {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        startActivity(MainActivity.class, null);
                        finish();
                    }
                }, 1000);

            }
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    public void startActivity(Class<?> clz, Bundle bundle) {
        Intent intent = new Intent();
        intent.setClass(this, clz);
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        startActivity(intent);
    }

}