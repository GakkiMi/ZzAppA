package com.huadingcloudpackage.www.activity.order.ordercommit;


import com.huadingcloudpackage.www.MainActivity;
import com.huadingcloudpackage.www.R;
import com.huadingcloudpackage.www.activity.order.orderdeclare.DeclareOrderListActivity;
import com.huadingcloudpackage.www.base.BaseActivity;
import com.huadingcloudpackage.www.eventbus.MainEvent;
import com.huadingcloudpackage.www.widget.ShapeTextView;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import org.greenrobot.eventbus.EventBus;

import butterknife.BindView;
import butterknife.OnClick;

public class PayResultActivity extends BaseActivity {

    @BindView(R.id.pay_result_iv_show)
    ImageView ivShow;
    @BindView(R.id.pay_result_tv_money)
    TextView tvMoney;
    @BindView(R.id.pay_result_tv_des)
    TextView tvDes;


    @BindView(R.id.pay_result_stv_left)
    ShapeTextView stvLeft;
    @BindView(R.id.pay_result_stv_right)
    ShapeTextView stvRight;

    private String tag = "";
    private String amount = "";
    private boolean isDiffOrder;

    @Override
    public int getLayoutResId() {
        return R.layout.activity_pay_result;
    }

    @Override
    public void initView(Bundle savedInstanceState) {
        initToolbar();
        setBackBtn();
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            tag = bundle.getString("tag");
            amount = bundle.getString("amount");
            isDiffOrder = bundle.getBoolean("isDiffOrder", false);
        }

        setTitle("success".equals(tag) ? "支付成功" : "支付失败");
        ivShow.setImageResource("success".equals(tag) ? R.drawable.img_pay_result_success : R.drawable.img_pay_result_fail);
        stvLeft.setText("success".equals(tag) ? "查看订单" : "查看订单");

        tvMoney.setText("¥ " + amount);
        tvDes.setText("success".equals(tag) ? "您的订单支付成功" : "您的订单支付失败");
    }


    @OnClick({R.id.pay_result_stv_left, R.id.pay_result_stv_right})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.pay_result_stv_left:
                if (isDiffOrder) {//是差异单
                    if ("success".equals(tag)) {
                    } else {
                    }
                    //成功或失败直接finish当前页面
                    finish();
                } else {//报货顶单
                    Bundle bundle2 = new Bundle();
                    if ("success".equals(tag)) {
                        bundle2.putInt("targetDeclarePageIndex", 2);//普通订单支付成功跳转到待发货订单tab
                    } else {
                        bundle2.putInt("targetDeclarePageIndex", 0);//普通订单支付失败跳转到全部订单tab
                    }
                    startActivity(DeclareOrderListActivity.class, bundle2);
                    finish();
                }
                break;
            case R.id.pay_result_stv_right:
                startActivity(MainActivity.class);
                EventBus.getDefault().post(new MainEvent(2));//返回首页
                break;
            default:
                break;
        }
    }


}
