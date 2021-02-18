package com.huadingcloudpackage.www;


import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.google.gson.JsonObject;
import com.huadingcloudpackage.www.activityfragment.AHomeFragment;
import com.huadingcloudpackage.www.activityfragment.BGouWuCarFragment;
import com.huadingcloudpackage.www.activityfragment.CMineFragment;
import com.huadingcloudpackage.www.base.BaseActivity;
import com.huadingcloudpackage.www.bean.BaseBean;
import com.huadingcloudpackage.www.eventbus.MainEvent;
import com.huadingcloudpackage.www.http.UrlContent;
import com.huadingcloudpackage.www.util.AppUpdateUtils;
import com.huadingcloudpackage.www.util.JsonUtils;
import com.huadingcloudpackage.www.util.StatusBarUtils;
import com.huadingcloudpackage.www.util.T;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import butterknife.BindView;
import butterknife.OnClick;
import okhttp3.RequestBody;

public class MainActivity extends BaseActivity {


    @BindView(R.id.iv_one)
    ImageView ivOne;
    @BindView(R.id.tv_one)
    TextView tvOne;
    @BindView(R.id.ll_one_small)
    LinearLayout llOneSmall;
    @BindView(R.id.ll_one)
    LinearLayout llOne;
    @BindView(R.id.iv_two)
    ImageView ivTwo;

    @BindView(R.id.tv_two)
    TextView tvTwo;
    @BindView(R.id.ll_two_small)
    LinearLayout llTwoSmall;
    @BindView(R.id.ll_two)
    RelativeLayout llTwo;
    @BindView(R.id.iv_four)
    ImageView ivFour;
    @BindView(R.id.tv_four)
    TextView tvFour;
    @BindView(R.id.ll_four_small)
    LinearLayout llFourSmall;
    @BindView(R.id.ll_four)
    LinearLayout llFour;
    @BindView(R.id.fragment_container)
    FrameLayout fragment_container;

    @BindView(R.id.shop_car_bage_tv_number)
    TextView tvShopCarBageNum;

    private String TAG = "MainActivity";

    private FragmentManager mFragmentManager;
    private AHomeFragment homeFragment;
    private BGouWuCarFragment gouWuCarFragment;
    private CMineFragment cMineFragment;


    @Override
    public int getLayoutResId() {
        return R.layout.activity_main;
    }

    @Override
    public void initView(Bundle savedInstanceState) {
        int fragmentFlag = getIntent().getIntExtra("fragment_flag", 1);

        mFragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = mFragmentManager.beginTransaction();

        //第一次只加载首页
        homeFragment = new AHomeFragment();
        transaction.add(R.id.fragment_container, homeFragment);
        transaction.commit();

        requestCartGoodsCount();
        StatusBarUtils.changStatusIconCollor(this, false);

        switch (fragmentFlag) {
            case 1:
                transaction.show(homeFragment);
                break;
            case 2:
                transaction.show(gouWuCarFragment);
                break;
            case 3:
                transaction.show(cMineFragment);
                break;
        }
        AppUpdateUtils.appCheckUpdate(this, this, null, false);

    }


    @OnClick({R.id.ll_one, R.id.ll_two, R.id.ll_four})
    public void onViewClicked(View view) {
        FragmentTransaction transaction = mFragmentManager.beginTransaction();
        hideFragment(transaction);
        resetTextViewColor();
        resetImageViewSrc();
        switch (view.getId()) {
            case R.id.ll_one:
                tvOne.setTextColor(getResources().getColor(R.color.colorAccent));
                ivOne.setImageResource(R.mipmap.home_select);
                if (homeFragment == null) {
                    homeFragment = new AHomeFragment();
                    transaction.add(R.id.fragment_container, homeFragment);
                    transaction.show(homeFragment);
                } else {
                    transaction.show(homeFragment);
                }
                homeFragment.onSelected();
                break;
            case R.id.ll_two:
                tvTwo.setTextColor(getResources().getColor(R.color.colorAccent));
                ivTwo.setImageResource(R.mipmap.gouwuche_checked);

                if (gouWuCarFragment == null) {
                    gouWuCarFragment = new BGouWuCarFragment();
                    transaction.add(R.id.fragment_container, gouWuCarFragment);
                    transaction.show(gouWuCarFragment);
                } else {
                    transaction.show(gouWuCarFragment);
                    gouWuCarFragment.requestCarList(true);
                }

                break;
            case R.id.ll_four:
                tvFour.setTextColor(getResources().getColor(R.color.colorAccent));
                ivFour.setImageResource(R.mipmap.mine_checked);
                if (cMineFragment == null) {
                    cMineFragment = new CMineFragment();
                    transaction.add(R.id.fragment_container, cMineFragment);
                    transaction.show(cMineFragment);
                } else {
                    transaction.show(cMineFragment);
                }
                break;
        }
        transaction.commitAllowingStateLoss();
    }


    private void hideFragment(FragmentTransaction fragmentTransaction) {

        if (homeFragment != null) {
            fragmentTransaction.hide(homeFragment);
        }
        if (gouWuCarFragment != null) {
            fragmentTransaction.hide(gouWuCarFragment);
        }

        if (cMineFragment != null) {
            fragmentTransaction.hide(cMineFragment);
        }

    }

    private void resetImageViewSrc() {
        ivOne.setImageResource(R.mipmap.home);
        ivTwo.setImageResource(R.mipmap.gouwuche);
        ivFour.setImageResource(R.mipmap.mine);

    }

    private void resetTextViewColor() {
        tvOne.setTextColor(Color.parseColor("#7F8389"));
        tvTwo.setTextColor(Color.parseColor("#7F8389"));
        tvFour.setTextColor(Color.parseColor("#7F8389"));

    }

    /**
     * 返回键的方法
     *
     * @param keyCode
     * @param event
     * @return
     */
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {

        if (keyCode == KeyEvent.KEYCODE_BACK) {
            exitByDoubleClick();
        }
        return false;
    }

    boolean isExit;

    private void exitByDoubleClick() {
        Timer tExit = null;
        if (!isExit) {
            isExit = true;
            T.showShort(mContext, "再按一次退出程序");
            tExit = new Timer();
            tExit.schedule(new TimerTask() {
                @Override
                public void run() {
                    isExit = false;//取消退出
                }
            }, 2000);// 如果2秒钟内没有按下返回键，则启动定时器取消掉刚才执行的任务
        } else {
            finish();
            //            System.exit(0);
        }
    }


    private void setShopCarBageNum(int count) {
        tvShopCarBageNum.setVisibility(count > 0 ? View.VISIBLE : View.GONE);
        tvShopCarBageNum.setText(count + "");
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void shopCarBageNumUpdate(MainEvent event) {
        Log.i(TAG, "-------------EventBUS:" + event.getTag());
        int tag = event.getTag();
        switch (tag) {
            case 0:
                requestCartGoodsCount();
                break;
            case 1:
                int goodsCount = (int) event.getObject();
                setShopCarBageNum(goodsCount);
                break;
            case 2:
                FragmentTransaction transaction = mFragmentManager.beginTransaction();
                hideFragment(transaction);
                resetTextViewColor();
                resetImageViewSrc();
                tvOne.setTextColor(getResources().getColor(R.color.colorAccent));
                ivOne.setImageResource(R.mipmap.home_select);
                if (homeFragment == null) {
                    homeFragment = new AHomeFragment();
                    transaction.add(R.id.fragment_container, homeFragment);
                    transaction.show(homeFragment);
                } else {
                    transaction.show(homeFragment);
                }
                transaction.commitAllowingStateLoss();
                break;
            default:
                break;
        }
    }


    /**
     * 请求购物车商品数量  更新底部购物车角标
     */
    public void requestCartGoodsCount() {
        HashMap<String, Object> params = new HashMap<>();
        OkGo.<String>post(UrlContent.CART_TOTAL)
                .tag(this)
                .upRequestBody(RequestBody.create(UrlContent.JSON, JsonUtils.toJsonString(params)))
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        int goodsTotalCount = 0;//购物车商品总数量
                        BaseBean baseBean = JsonUtils.parse(response.body(), BaseBean.class);
                        if (baseBean.getCode() == 200 || baseBean.getCode() == 201) {
                            try {
                                JSONObject jsonObject = new JSONObject(response.body());
                                goodsTotalCount = jsonObject.getInt("data");
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        } else {
                            goodsTotalCount = 0;
                        }
                        setShopCarBageNum(goodsTotalCount);
                    }

                    @Override
                    public void onFinish() {
                        super.onFinish();
                    }

                    @Override
                    public void onError(Response<String> response) {
                        super.onError(response);
                        T("网络开小差，请稍后重试 ~");
                    }
                });
    }


}
