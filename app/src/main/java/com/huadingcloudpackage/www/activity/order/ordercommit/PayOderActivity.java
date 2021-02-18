package com.huadingcloudpackage.www.activity.order.ordercommit;


import android.content.Context;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.core.content.ContextCompat;

import com.guoquan.yunpu.util.ComTools;
import com.huadingcloudpackage.www.R;
import com.huadingcloudpackage.www.activity.order.orderdeclare.DeclareOrderDetailActivity;
import com.huadingcloudpackage.www.activity.set.SetPasswordActivity;
import com.huadingcloudpackage.www.base.BaseActivity;
import com.huadingcloudpackage.www.bean.BaseBean;
import com.huadingcloudpackage.www.bean.ExhibiWalletBean;
import com.huadingcloudpackage.www.bean.ExhibiWalletBean.ExhibiWalletDataBean;
import com.huadingcloudpackage.www.callback.DialogCallback;
import com.huadingcloudpackage.www.dialog.CommomDialog;
import com.huadingcloudpackage.www.dialog.PayEnterPwdDialog;
import com.huadingcloudpackage.www.eventbus.DeclareOrderDetailEvent;
import com.huadingcloudpackage.www.eventbus.DiffOrderDetailEvent;
import com.huadingcloudpackage.www.http.UrlContent;
import com.huadingcloudpackage.www.util.BigDecimalUtils;
import com.huadingcloudpackage.www.util.JsonUtils;
import com.huadingcloudpackage.www.util.StatusBarUtils;
import com.huadingcloudpackage.www.util.T;
import com.huadingcloudpackage.www.widget.ShapeTextView;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.model.Response;

import org.greenrobot.eventbus.EventBus;

import java.math.BigDecimal;
import java.util.HashMap;

import butterknife.BindView;
import butterknife.OnClick;
import okhttp3.RequestBody;

public class PayOderActivity extends BaseActivity implements PayEnterPwdDialog.IminputComplete {


    @BindView(R.id.iv_back)
    ImageView iv_back;
    @BindView(R.id.stv_jiesuan)
    ShapeTextView stv_jiesuan;
    @BindView(R.id.ll_all_1)
    LinearLayout ll_all_1;
    @BindView(R.id.ll_yue_pay)
    LinearLayout ll_yue_pay;

    @BindView(R.id.ll_ali_pay)
    LinearLayout ll_ali_pay;
    @BindView(R.id.ll_wx_pay)
    LinearLayout ll_wx_pay;

    @BindView(R.id.pay_order_tv_yuE)
    TextView tvYuE;
    @BindView(R.id.pay_order_tv_tip_yuE_buzu)
    TextView tvTipYuEBuzu;

    @BindView(R.id.pay_order_tv_money)
    TextView tvOrderMoney;//订单金额
    @BindView(R.id.pay_order_tv_number)
    TextView tvOrderNum;//订单号
    @BindView(R.id.pay_order_tv_exhibi_wallet)
    TextView tvExhibiWallet;//可用余额
    @BindView(R.id.pay_order_bottom_tv_total_money)
    TextView tvTotalMoney;//总支付金额


    private CommomDialog tipDialog;//未设置支付密码弹出来的dialog
    private CommomDialog pwdInputErrorDialog;//密码输入错误弹出的dialog
    private CommomDialog giveUpPayDialog;//放弃支付弹出的dialog

    private String orderSn;//
    private String orderSonSn;//
    private String differenceSn;//

    private PayEnterPwdDialog payEnterPwdDialog;//要输入密码的dialog

    private int payTag = 0;//支付方式的标记  0 为余额支付  1为微信支付  2为支付宝支付

    private String totalAmount = "0";

    private Context mContext = PayOderActivity.this;

    //可能从OrderCommitActivity ShouhouActivity AllOrderFragment DaifukuanDetailActivity DaifukuanFragment ChayidanDetailActivity进来
    private String pageTag;//
    private int groupItemIndex;

    @Override
    public int getLayoutResId() {
        return R.layout.activity_pay_oder;
    }

    @Override
    public void initView(Bundle savedInstanceState) {

        getBundleData();
        requestExhibiWallet();

        StatusBarUtils.changStatusIconCollor(this, false);
    }

    private void getBundleData() {
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            orderSn = bundle.getString("orderSn");//订单号
            totalAmount = bundle.getString("totalAmount");//总金额

            //保留两位小数
            totalAmount = BigDecimalUtils.keepTwoDecimalPlaces(Double.valueOf(totalAmount));

            pageTag = bundle.getString("pageTag", "");
            groupItemIndex = bundle.getInt("groupItemIndex", -1);

            if ("售后".equals(pageTag)) {
                orderSonSn = bundle.getString("orderSonSn", null);//子订单号
                differenceSn = bundle.getString("differenceSn");//差异单号
                tvOrderNum.setText("订单号：" + differenceSn);
            } else {
                tvOrderNum.setText("订单号：" + orderSn);
            }
            tvOrderMoney.setText("¥ " + totalAmount);
            tvTotalMoney.setText("¥ " + totalAmount);
        }
    }


    @OnClick({R.id.iv_back, R.id.stv_jiesuan, R.id.ll_yue_pay, R.id.ll_ali_pay, R.id.ll_wx_pay})
    public void onClick(View view) {
        if (isFastClick()) {
            return;
        }
        switch (view.getId()) {
            case R.id.iv_back:
                showGiveUpPayDialog();
                break;
            case R.id.stv_jiesuan:
                requesetIsSettingPwd();
                break;
            case R.id.ll_yue_pay:
                payTag = 0;
                selectPay(true, false, false);
                break;
            case R.id.ll_wx_pay:
                payTag = 1;
                selectPay(false, true, false);
                break;
            case R.id.ll_ali_pay:
                payTag = 2;
                selectPay(false, false, true);
                break;
            default:
                break;
        }
    }

    /**
     * 选择支付方式
     *
     * @param yuEpay 余额支付
     * @param wxPay  微信支付
     * @param aliPay 支付宝支付
     */
    private void selectPay(boolean yuEpay, boolean wxPay, boolean aliPay) {
        ll_yue_pay.setSelected(yuEpay);
        ll_ali_pay.setSelected(aliPay);
        ll_wx_pay.setSelected(wxPay);
    }


    /**
     * 获取可用余额
     */
    private void requestExhibiWallet() {
        OkGo.<String>post(UrlContent.GET_EXHIBI_WALLET)
                .tag(this)
                .execute(new DialogCallback(this, true) {
                    @Override
                    public void onSuccess(Response<String> response) {
                        ExhibiWalletBean exhibiWallet = JsonUtils.parse(response.body(), ExhibiWalletBean.class);
                        ExhibiWalletDataBean exhibiWalletBean = exhibiWallet.getData();
                        if (exhibiWalletBean == null) {
                            return;
                        }

                        BigDecimal data1 = new BigDecimal(totalAmount);
                        BigDecimal data2 = new BigDecimal(exhibiWalletBean.getMoney());
                        int x = BigDecimalUtils.compare(data1, data2);

                        if (x > 0) {//余额不足
                            String str = "余额：¥ " + BigDecimal.valueOf(exhibiWalletBean.getMoney());
                            tvExhibiWallet.setText(str);
                            tvExhibiWallet.setTextColor(ContextCompat.getColor(mContext, R.color.cp_gray));
                        } else {//余额充足 x<0|x=0
                            String str = "¥ " + BigDecimal.valueOf(exhibiWalletBean.getMoney());
                            String html = "<font color=\"#000000\"> 余额：</font>" + "<font color=\"#FD6D14\">" + str + "</font>";
                            tvExhibiWallet.setText(Html.fromHtml(html));
                        }
                        tvYuE.setTextColor(ContextCompat.getColor(mContext, x > 0 ? R.color.cp_gray : R.color.black000));
                        tvYuE.setCompoundDrawablesWithIntrinsicBounds(getResources().getDrawable(x > 0 ? R.drawable.img_yu_e_normal : R.drawable.img_yu_e_select), null, null, null);

                        tvTipYuEBuzu.setVisibility(x > 0 ? View.VISIBLE : View.GONE);

                        stv_jiesuan.setEnabled(x > 0 ? false : true);
                        stv_jiesuan.setBgNormalColor(ContextCompat.getColor(mContext, x > 0 ? R.color.cp_gray : R.color.theme_color));

                        ll_yue_pay.setEnabled(x > 0 ? false : true);
                        selectPay(x > 0 ? false : true, false, false);

                    }

                    @Override
                    public void onError(Response<String> response) {
                        T.showToastyCenter(mContext, "网络开小差，请稍后重试 ~");
                    }
                });
    }

    /**
     * 是否设置支付密码
     */
    private void requesetIsSettingPwd() {
        OkGo.<String>post(UrlContent.SELECT_SETTING_PASSWORD)
                .tag(this)
                .execute(new DialogCallback(this, true) {
                    @Override
                    public void onSuccess(Response<String> response) {
                        BaseBean baseBean = JsonUtils.parse(response.body(), BaseBean.class);
                        if (baseBean.getCode() == 222) {
                            showTipDialog();
                            baseBean.setShowToast(false);
                        }
                        comTools.parsingReturnData(baseBean, () -> {
                            payEnterPwdDialog = new PayEnterPwdDialog(PayOderActivity.this, "", PayOderActivity.this);
                            payEnterPwdDialog.show();
                        });
                    }

                    @Override
                    public void onError(Response<String> response) {
                        T.showToastyCenter(mContext, "网络开小差，请稍后重试 ~");
                    }
                });
    }


    /**
     * 普通报货订单支付
     */
    private void requestOrderConfirm(String pwd) {
        HashMap<String, Object> params = new HashMap<>();
        params.put("orderSn", orderSn);
        params.put("price", totalAmount);
        params.put("password", pwd);
        RequestBody body = RequestBody.create(UrlContent.JSON, JsonUtils.toJsonString(params));
        OkGo.<String>post(UrlContent.APP_PAY_MONEY)
                .tag(this)
                .upRequestBody(body)
                .execute(new DialogCallback(this, true) {
                    @Override
                    public void onSuccess(Response<String> response) {
                        BaseBean baseBean = JsonUtils.parse(response.body(), BaseBean.class);
                        if (baseBean.getCode() == 50001) {
                            baseBean.setShowToast(false);
                        }
                        Bundle bundle = new Bundle();
                        bundle.putString("amount", totalAmount);
                        comTools.parsingReturnData(baseBean, new ComTools.RequestCallBack() {
                            @Override
                            public void onSuccess() {
                                bundle.putString("tag", "success");

                                DeclareOrderDetailEvent event = new DeclareOrderDetailEvent();
                                event.setTag(0);
                                HashMap<String, Object> map = new HashMap<>();
                                map.put("groupItemIndex", groupItemIndex);
                                map.put("doTag", "DecOrderPaySuccess");
                                map.put("pageTag", pageTag);
                                event.setObject(map);
                                EventBus.getDefault().post(event);
                                payEnterPwdDialog.dismiss();
                                startActivity(PayResultActivity.class, bundle);
                                finish();
                            }

                            @Override
                            public void onError() {
                                if (baseBean.getCode() == 50001) {
                                    showPwdInputError();
                                } else {
                                    payEnterPwdDialog.dismiss();
                                    bundle.putString("tag", "fail");
                                    startActivity(PayResultActivity.class, bundle);
                                    finish();
                                }
                            }
                        });
                    }

                    @Override
                    public void onError(Response<String> response) {
                        T.showToastyCenter(mContext, "网络开小差，请稍后重试 ~");
                    }
                });
    }

    /**
     * 差异订单支付
     */
    private void requestDiffOrderConfirm(String pwd) {
        HashMap<String, Object> params = new HashMap<>();
        params.put("orderSonSn", orderSonSn);
        params.put("password", pwd);
        params.put("price", totalAmount);
        params.put("differenceSn", differenceSn);
        RequestBody body = RequestBody.create(UrlContent.JSON, JsonUtils.toJsonString(params));
        OkGo.<String>post(UrlContent.DIFF_ORDER_PAY)
                .tag(this)
                .upRequestBody(body)
                .execute(new DialogCallback(this, true) {
                    @Override
                    public void onSuccess(Response<String> response) {
                        BaseBean baseBean = JsonUtils.parse(response.body(), BaseBean.class);
                        if (baseBean.getCode() == 50001) {
                            baseBean.setShowToast(false);
                        }
                        Bundle bundle = new Bundle();
                        bundle.putString("amount", totalAmount);
                        comTools.parsingReturnData(baseBean, new ComTools.RequestCallBack() {
                            @Override
                            public void onSuccess() {
                                bundle.putString("tag", "success");
                                bundle.putBoolean("isDiffOrder", true);

                                DiffOrderDetailEvent event = new DiffOrderDetailEvent();
                                event.setTag(5);//通知售后界面
                                HashMap<String, Object> map = new HashMap<>();
                                map.put("groupItemIndex", groupItemIndex);
                                map.put("doTag", "DiffOrderPaySuccess");
                                map.put("pageTag", pageTag);
                                event.setObject(map);
                                EventBus.getDefault().post(event);

                                payEnterPwdDialog.dismiss();
                                startActivity(PayResultActivity.class, bundle);
                                finish();
                            }

                            @Override
                            public void onError() {
                                if (baseBean.getCode() == 50001) {
                                    showPwdInputError();
                                } else {
                                    payEnterPwdDialog.dismiss();
                                    bundle.putString("tag", "fail");
                                    startActivity(PayResultActivity.class, bundle);
                                    finish();
                                }
                            }
                        });

                    }

                    @Override
                    public void onError(Response<String> response) {
                        T.showToastyCenter(mContext, "网络开小差，请稍后重试 ~");
                    }
                });
    }


    private void showTipDialog() {
        if (tipDialog != null) {
            tipDialog.show();
            return;
        }
        tipDialog = new CommomDialog(this);
        tipDialog.setTitle("温馨提示");
        tipDialog.setContent("您还没有支付密码,请先去设置");
        tipDialog.setPositiveButton("前往设置");
        tipDialog.setNegativeButton("取消");
        tipDialog.setNegativeColor("#5c5c5c");
        tipDialog.setOnCloseListener((dialog, confirm) -> {
            if (confirm) {
                startActivity(SetPasswordActivity.class);
            }
        });
        tipDialog.show();
    }


    private void showPwdInputError() {
//        if (pwdInputErrorDialog != null) {
//            pwdInputErrorDialog.show();
//            return;
//        }
//        pwdInputErrorDialog = new CommomDialog(this);
//        pwdInputErrorDialog.setTitle("支付密码不正确");
//        pwdInputErrorDialog.setContent("");
//        pwdInputErrorDialog.setPositiveButton("重新输入");
//        pwdInputErrorDialog.setNegativeButtonVisible(View.GONE);
//        pwdInputErrorDialog.setOnCloseListener((dialog, confirm) -> {
//            if (confirm) {
//                //重新输入
//                requesetIsSettingPwd();
//            }
//        });
//        pwdInputErrorDialog.show();


        payEnterPwdDialog.againInput();
        payEnterPwdDialog.pwdErrorTip();

    }


    private void showGiveUpPayDialog() {
        if (giveUpPayDialog != null) {
            giveUpPayDialog.show();
            return;
        }
        giveUpPayDialog = new CommomDialog(this);
        giveUpPayDialog.setTitle("是否放弃本次付款？");
        giveUpPayDialog.setContent("");
        giveUpPayDialog.setPositiveButton("继续付款");
        giveUpPayDialog.setNegativeButton("放弃");
        giveUpPayDialog.setNegativeColor("#5c5c5c");
        giveUpPayDialog.setCancelable(false);
        giveUpPayDialog.setCanceledOnTouchOutside(false);
        giveUpPayDialog.setOnCloseListener((dialog, confirm) -> {
            if (!confirm) {
                if ("OrderCommitActivity".equals(pageTag)) {
                    Bundle bundle = new Bundle();
                    bundle.putString("orderSn", orderSn);
                    startActivity(DeclareOrderDetailActivity.class, bundle);
                    finish();
                } else {
                    finish();
                }
            }
        });
        giveUpPayDialog.show();
    }

    @Override
    public void onBackPressed() {
        showGiveUpPayDialog();
    }

    @Override
    public void forgetPwd() {
        payEnterPwdDialog.dismiss();
        Bundle bundle = new Bundle();
        bundle.putString("title", "忘记密码");
        startActivity(SetPasswordActivity.class, bundle);
    }

    @Override
    public void inputOverListener(String inputPwd) {
//        payEnterPwdDialog.dismiss();
        if ("售后".equals(pageTag)) {//从售后界面进来差异单支付
            requestDiffOrderConfirm(inputPwd);
        } else {
            requestOrderConfirm(inputPwd);
        }
    }


}
