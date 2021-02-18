package com.huadingcloudpackage.www.activity.order.orderdeclare;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.guoquan.yunpu.util.ComTools;
import com.huadingcloudpackage.www.R;
import com.huadingcloudpackage.www.activity.confirmgoods.ConfirmgoodsActivity;
import com.huadingcloudpackage.www.activity.gouwuche.GouWuCheActivity;
import com.huadingcloudpackage.www.activity.logisticsdetail.LogisticsDetailActivity;
import com.huadingcloudpackage.www.activity.order.BaseOrderDetailActivity;
import com.huadingcloudpackage.www.activity.order.CommonFunUtils;
import com.huadingcloudpackage.www.activity.order.ordercommit.PayOderActivity;
import com.huadingcloudpackage.www.adapter.itemdecoration.BravhListDivider;
import com.huadingcloudpackage.www.adapter.orderdeclare.DeclareOrderDetailGroupAdapter;
import com.huadingcloudpackage.www.bean.BaseBean;
import com.huadingcloudpackage.www.bean.DeclareOrderDetailOuterBean;
import com.huadingcloudpackage.www.bean.DeclareOrderDetailOuterBean.DeclareOrderDetailRespBean;
import com.huadingcloudpackage.www.bean.DeclareOrderDetailOuterBean.DeclareOrderDetailDataBean;
import com.huadingcloudpackage.www.bean.DeclareOrderOuterBean;
import com.huadingcloudpackage.www.callback.DialogCallback;
import com.huadingcloudpackage.www.dialog.CommomDialog;
import com.huadingcloudpackage.www.eventbus.DeclareOrderDetailEvent;
import com.huadingcloudpackage.www.http.Constants;
import com.huadingcloudpackage.www.http.UrlContent;
import com.huadingcloudpackage.www.util.CountDownTimeUtils;
import com.huadingcloudpackage.www.util.JsonUtils;
import com.huadingcloudpackage.www.util.SpannableUtils;
import com.huadingcloudpackage.www.util.StatusBarUtils;
import com.huadingcloudpackage.www.util.T;
import com.huadingcloudpackage.www.widget.NoScrollLinearLayoutManager;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.model.Response;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import okhttp3.RequestBody;

public class DeclareOrderDetailActivity extends BaseOrderDetailActivity {

    @BindView(R.id.order_detail_common_rl_root_view)
    RelativeLayout rlRootView;
    @BindView(R.id.order_detail_common_ll_footer_view)
    LinearLayout llFooterView;


    @BindView(R.id.order_detail_common_iv_order_status)
    public ImageView ivOrderStatus;//订单状态图片
    @BindView(R.id.order_detail_common_tv_order_status)
    public TextView tvOrderStatus;//订单状态
    @BindView(R.id.order_detail_common_tv_order_status_des)
    public TextView tvOrderStatusDes;//订单状态描述

    @BindView(R.id.order_detail_common_tv_receiver_name)
    public TextView tvReceiverName;//收货人姓名
    @BindView(R.id.order_detail_common_tv_receiver_tel)
    public TextView tvReceiverTel;//收货人手机号
    @BindView(R.id.order_detail_common_tv_receiver_address)
    public TextView tvReceiverAddress;//收货人地址
    @BindView(R.id.order_detail_common_iv_address_right_arrow)
    public ImageView ivRightArrow;//收货人地址向右箭头

    @BindView(R.id.order_detail_common_rv_list)
    public RecyclerView rvList;

    @BindView(R.id.order_detail_common_tv_amount_type)
    public TextView tvTotalAmountType;//总金额类型
    @BindView(R.id.order_detail_common_tv_amount)
    public TextView tvTotalAmount;//总金额

    @BindView(R.id.order_detail_common_tv_order_no_type)
    public TextView tvOrderNoType;//编号类型
    @BindView(R.id.order_detail_common_tv_order_no)
    public TextView tvOrderNo;//订单编号
    @BindView(R.id.order_detail_common_tv_order_time)
    public TextView tvOrderTime;//下单时间
    @BindView(R.id.order_detail_common_tv_payment_time)
    public TextView tvPaymentTime;//支付时间
    @BindView(R.id.order_detail_common_tv_delivery_time)
    public TextView tvDeliveryTime;//发货时间
    @BindView(R.id.order_detail_common_tv_transaction_time)
    public TextView tvTransTime;//成交时间
    @BindView(R.id.order_detail_common_tv_delivery_type)
    public TextView tvDeliveryType;//配送方式


    @BindView(R.id.order_detail_common_tv_click_left)
    public TextView tvBottomClickLeft;//底部靠左点击按钮
    @BindView(R.id.order_detail_common_tv_click_right)
    public TextView tvBottomClickRight;//底部靠右点击按钮
    @BindView(R.id.order_detail_common_rl_bottom)
    public RelativeLayout rlBottom;//底部根布局


    private static final String TAG = "DodetailActivity";

    private DeclareOrderDetailGroupAdapter declareOrderDetailAdapter;
    private List<DeclareOrderDetailRespBean> mDatas;

    private int orderStatus;

    private String mOrderSn;
    private String mOrderSonSn;
    private int groupItemIndex;//记录从哪个条目进来的索引
    private String mPageTag;//记录从哪个页面进来

    private CommonFunUtils commonFunUtils;

    private DeclareOrderDetailDataBean mDataBean;
    private DeclareOrderDetailRespBean mRespBean;

    private CountDownTimeUtils countDownTimeUtils;
    private int totalSecond = 0;//秒


    @Override
    public int getLayoutResId() {
        return R.layout.activity_declare_order_detail;
    }

    @Override
    public void initView(Bundle savedInstanceState) {
        StatusBarUtils.changStatusIconCollor(this, false);
        Bundle bundle = getIntent().getExtras();
        mOrderSn = bundle.getString("orderSn");
        mOrderSonSn = bundle.getString("orderSonSn", null);// 待付款/已取消不传
        groupItemIndex = bundle.getInt("groupItemIndex", -1);
        mPageTag = bundle.getString("pageTag");


        commonFunUtils = new CommonFunUtils(this);

        mDatas = new ArrayList<>();
        initAdatper();

        rlBottom.setVisibility(View.GONE);
        changePageState(Constants.PageState.INIT);

        getDeclareOrderDetailData();
    }

    private void initAdatper() {
        rvList.setLayoutManager(new LinearLayoutManager(this));
        declareOrderDetailAdapter = new DeclareOrderDetailGroupAdapter(this, R.layout.item_declare_order_detail_rv_list_group, mDatas);
        rvList.setAdapter(declareOrderDetailAdapter);
        rvList.addItemDecoration(new BravhListDivider(this, BravhListDivider.VERTICAL_LIST, BravhListDivider.DIVIDER_NO_LAST));
        setOverScroll(rvList);
        rvListAddFooterView();
    }


    private void rvListAddFooterView() {
        rlRootView.removeView(llFooterView);
        llFooterView.setVisibility(View.VISIBLE);
        declareOrderDetailAdapter.addFooterView(llFooterView);
    }


    @OnClick({R.id.order_detail_common_title_bar, R.id.order_detail_common_tv_copy, R.id.order_detail_common_stv_call_fuke,
            R.id.state_layout_net_error, R.id.root_state_layout,
            R.id.order_detail_common_rl_address, R.id.order_detail_common_tv_click_left, R.id.order_detail_common_tv_click_right})
    public void onClick(View view) {
        if (isFastClick()) return;
        switch (view.getId()) {
            case R.id.order_detail_common_title_bar:
                finish();
                break;
            case R.id.order_detail_common_tv_copy:
                commonFunUtils.copyText(tvOrderNo);
                break;
            case R.id.order_detail_common_stv_call_fuke:
                commonFunUtils.callKefu();
                break;
            case R.id.order_detail_common_rl_address://修改收货地址
                break;
            case R.id.state_layout_net_error:
                getDeclareOrderDetailData();
                break;
            case R.id.root_state_layout:
                break;
            case R.id.order_detail_common_tv_click_left:
                if (orderStatus == 0) {//待付款（取消订单）
                    new CommomDialog(mContext, "确定取消此订单？", (dialog, confirm) -> {
                        if (confirm) {
                            DeclareOrderPuclicRequest.cancelDeclareOrder(this, mRespBean.getOrderSn(), () -> {
                                T.showToastyCenter(mContext, "操作成功");
                                registEventBus(0, "DecOrderCancel");
                                cancelTimer();
                                getDeclareOrderDetailData();
                            });
                        }
                    }).setTitle("取消订单").setNegativeButton("取消").setPositiveButton("确定").show();
                } else if (orderStatus == 2) {//待收货（查看物流）
                    Bundle bundle = new Bundle();
                    bundle.putString("omsOutSn", mRespBean.getOmsOutNo());
                    startActivity(LogisticsDetailActivity.class, bundle);
                } else if (orderStatus == 3) {//已完成（删除订单）
                    new CommomDialog(mContext, "确定删除此订单？", (dialog, confirm) -> {
                        //删除订单接口
                        if (confirm) {
                            DeclareOrderPuclicRequest.deleteDeclareOrder(this, mRespBean.getOrderSn(), () -> {
                                T.showToastyCenter(mContext, "操作成功");
                                registEventBus(3, "DecOrderDelete");
                                finish();
                            });
                        }
                    }).setTitle("删除订单").setNegativeButton("取消").setPositiveButton("确定").show();
                } else if (orderStatus == 4) {//已取消（删除订单）
                    new CommomDialog(mContext, "确定删除此订单？", (dialog, confirm) -> {
                        //删除订单接口
                        if (confirm) {
                            DeclareOrderPuclicRequest.deleteDeclareOrder(this, mRespBean.getOrderSn(), () -> {
                                T.showToastyCenter(mContext, "操作成功");
                                registEventBus(4, "DecOrderDelete");
                                finish();
                            });
                        }
                    }).setTitle("删除订单").setNegativeButton("取消").setPositiveButton("确定").show();
                }
                break;
            case R.id.order_detail_common_tv_click_right:
                if (orderStatus == 0) {//待付款（去付款）
                    Bundle intentBundle = new Bundle();
                    intentBundle.putString("orderSn", mDataBean.getOrderSn());
                    intentBundle.putString("totalAmount", mDataBean.getTotalAmount());
                    intentBundle.putInt("groupItemIndex", groupItemIndex);
                    intentBundle.putString("pageTag", mPageTag);
                    Intent intent = new Intent(mContext, PayOderActivity.class);//跳转到支付页面
                    intent.putExtras(intentBundle);
                    mContext.startActivity(intent);
                } else if (orderStatus == 1) {//待发货(提醒发货)
                    DeclareOrderPuclicRequest.remindDeclareOrder(this, mRespBean.getOrderSonSn(), () -> {
                        T.showToastyCenter(mContext, "操作成功");
                        registEventBus(1, "DecOrderRemind");
                        mRespBean.setRemind("1");
                        int remindStatus = Integer.parseInt(mRespBean.isRemind());
                        if (remindStatus == 0) {
                            setButtonStyle(2, 1, "", "提醒发货");
                        } else {
                            setButtonStyle(0, 2, "已提醒", "");
                        }
                    });
                } else if (orderStatus == 2) {//待收货（确认收货）
                    Bundle intentBundle = new Bundle();
                    intentBundle.putString("orderSonSn", mRespBean.getOrderSonSn());
                    intentBundle.putInt("groupItemIndex", groupItemIndex);
                    intentBundle.putString("pageTag", mPageTag);

                    //将item转成json 并传递到确认收货界面
                    List<DeclareOrderOuterBean.OrderGoodsBean> goodsBeanList = new ArrayList<>();
                    for (DeclareOrderDetailRespBean orderRespBean : mDataBean.getOrderSonDetailsRespList()) {
                        goodsBeanList.addAll(orderRespBean.getOrderGoodsDetailsRespList());
                    }
                    String dataJson = JsonUtils.toJsonString(goodsBeanList);
                    intentBundle.putString("dataJson", dataJson);
                    Intent intent = new Intent(mContext, ConfirmgoodsActivity.class);//跳转到确认收货页面
                    intent.putExtras(intentBundle);
                    mContext.startActivity(intent);
                } else if (orderStatus == 4) {//已取消（再来一单）
                    Bundle intentBundle = new Bundle();
                    intentBundle.putBoolean("isOneMoreOrder", true);
                    intentBundle.putString("orderSn", mRespBean.getOrderSn());
                    Intent intent = new Intent(mContext,DecOneMoreOrderActivity.class);//跳转到再来一单界面
                    intent.putExtras(intentBundle);
                    mContext.startActivity(intent);
                }
                break;
            default:
                break;
        }
    }


    /**
     * 根据传过来的订单状态进行界面相应的改动
     *
     * @param orderStatus //0待付款，1待发货，2待收货，3已完成，4已取消
     */
    public void setDeclareDetailInfo(int orderStatus) {
        if (orderStatus == 0) {//待支付
            tvOrderStatus.setText("订单待付款");
            //倒计时字段显示
            startCountDownTime();
            ivOrderStatus.setImageResource(R.drawable.img_order_status_daifukuan);
            setOrderInfoShowStatus(true, false, false, false);
            setButtonStyle(0, 1, "取消订单", "立即支付");
//            ivRightArrow.setVisibility(View.VISIBLE);//显示地址栏右边箭头
        } else if (orderStatus == 1) {//待发货
            tvOrderStatus.setText("订单待发货");
            ivOrderStatus.setImageResource(R.drawable.img_order_status_daifahuo);
            setOrderInfoShowStatus(true, true, false, false);
            int remindStatus = Integer.parseInt(mRespBean.isRemind());
            if (remindStatus == 0) {
                setButtonStyle(2, 1, "", "提醒发货");
            } else {
                setButtonStyle(0, 2, "已提醒", "");
            }
        } else if (orderStatus == 2) {//待收货
            tvOrderStatus.setText("订单待收货");
            ivOrderStatus.setImageResource(R.drawable.img_order_status_daishouhuo);
            setOrderInfoShowStatus(true, true, true, false);
            setButtonStyle(0, 1, "查看物流", "确认收货");
        } else if (orderStatus == 3) {//已完成
            tvOrderStatus.setText("订单已完成");
            ivOrderStatus.setImageResource(R.drawable.img_order_status_completed);
            setOrderInfoShowStatus(true, true, true, true);
            setButtonStyle(0, 2, "删除订单", "");
        } else if (orderStatus == 4) {//已取消
            tvOrderStatus.setText("订单已取消");
            ivOrderStatus.setImageResource(R.drawable.img_order_status_cancelled);
            setOrderInfoShowStatus(true, false, false, true);
            setButtonStyle(0, 1, "删除订单", "再次发起订单");
        }
    }


    /**
     * @param leftStyle  左边按钮样式  0标识灰色  1标识蓝色  2标识隐藏
     * @param rightStyle 右边按钮样式  0标识灰色  1标识蓝色  2标识隐藏
     */
    public void setButtonStyle(int leftStyle, int rightStyle, String leftText, String rightText) {
        if (leftStyle == 2 && rightStyle == 2) {
            rlBottom.setVisibility(View.GONE);
            return;
        }
        rlBottom.setVisibility(View.VISIBLE);
        setBaseButtonStyle(tvBottomClickLeft, leftStyle, leftText);
        setBaseButtonStyle(tvBottomClickRight, rightStyle, rightText);

    }


    /**
     * 设置订单的一些基本信息
     *
     * @param isShowOrderTime    下单时间是否显示
     * @param isShowPaymentTime  支付时间是否显示
     * @param isShowDeliveryTime 发货时间是否显示
     * @param isShowTransTime    成交时间是否显示
     */
    public void setOrderInfoShowStatus(boolean isShowOrderTime, boolean isShowPaymentTime,
                                       boolean isShowDeliveryTime, boolean isShowTransTime) {
        tvOrderTime.setVisibility(isShowOrderTime ? View.VISIBLE : View.GONE);
        tvPaymentTime.setVisibility(isShowPaymentTime ? View.VISIBLE : View.GONE);
        tvDeliveryTime.setVisibility(isShowDeliveryTime ? View.VISIBLE : View.GONE);
        tvTransTime.setVisibility(isShowTransTime ? View.VISIBLE : View.GONE);
    }


    /**
     * 获取报货订单详情
     */
    private void getDeclareOrderDetailData() {
        HashMap<String, Object> params = new HashMap<>();
        params.put("orderSn", mOrderSn);
        params.put("orderSonSn", mOrderSonSn);
//        params.put("status", orderStatus);
        RequestBody body = RequestBody.create(UrlContent.JSON, JsonUtils.toJsonString(params));
        OkGo.<String>post(UrlContent.BUSAPP_ORDER_DETAILS)
                .tag(this)
                .upRequestBody(body)
                .execute(new DialogCallback(this, true) {
                    @Override
                    public void onSuccess(Response<String> response) {
                        changePageState(Constants.PageState.NORMAL);
                        BaseBean baseBean = JsonUtils.parse(response.body(), BaseBean.class);
                        comTools.parsingReturnData(baseBean, new ComTools.OnParsingReturnListener() {
                            @Override
                            public void onParsingSuccess() {

                                DeclareOrderDetailOuterBean declareOrderDetailOuterBean = JsonUtils.parse(response.body(), DeclareOrderDetailOuterBean.class);
                                List<DeclareOrderDetailDataBean> dataList = declareOrderDetailOuterBean.getData();
                                if (dataList == null) {
                                    return;
                                }
                                mDataBean = dataList.get(0);

                                mRespBean = mDataBean.getOrderSonDetailsRespList().get(0);
                                orderStatus = Integer.parseInt(mRespBean.getOrderSonStatus());

                                String receiver = mDataBean.getConsigneeName();//收货人
                                String receiverTel = mDataBean.getConsigneePhone();//收货手机号
                                String receiverAddress = mDataBean.getConsigneeAddress();//收货地址

                                String orderTime = mDataBean.getAddTime();//下单时间
                                String paymentTime = mDataBean.getPayTime();//支付时间
                                String deliverTime = mRespBean.getShipmentsTime();//发货时间
                                String overTime = mRespBean.getOverTime();//结束时间
                                String cancelTime = mRespBean.getCancelTime();//取消时间

                                String orderNo = "";//订单编号
                                String totalAmount = "";//总金额

                                //订单状态 0待付款，1待发货，2待收货，3已完成，4已取消
                                if (orderStatus == 0 || orderStatus == 4) {
                                    tvOrderNoType.setText("主单单号：");
                                    tvTotalAmountType.setText("应付总金额");
                                    orderNo = mRespBean.getOrderSn();
                                    totalAmount = mDataBean.getTotalAmount();
                                } else {
                                    tvOrderNoType.setText("订单编号：");
                                    tvTotalAmountType.setText("实付总金额");
                                    orderNo = mRespBean.getOrderSonSn();
                                    totalAmount = mRespBean.getTotalSonAmount();
                                }
                                tvOrderNo.setText(orderNo);
                                tvTotalAmount.setText(SpannableUtils.changeSpannableTv("¥" + totalAmount));//总金额

                                tvDeliveryType.setText("配送方式：快递物流");
                                tvReceiverName.setText(receiver);
                                tvReceiverTel.setText(receiverTel);
                                tvReceiverAddress.setText(receiverAddress);

                                tvOrderTime.setText("下单时间：" + orderTime);
                                tvPaymentTime.setText("付款时间：" + paymentTime);
                                tvDeliveryTime.setText("发货时间：" + deliverTime);

                                if (orderStatus == 0) {//待支付
                                    tvOrderStatusDes.setText("应付金额：¥" + totalAmount);
                                } else if (orderStatus == 1) {//待发货
                                    tvOrderStatusDes.setText("仓库备货中，准备出库");
                                } else if (orderStatus == 2) {//待收货
                                    tvOrderStatusDes.setVisibility(View.GONE);
                                } else if (orderStatus == 3) {//已完成
                                    tvTransTime.setText("成交时间：" + overTime);
                                    tvOrderStatusDes.setVisibility(View.GONE);
                                } else if (orderStatus == 4) {//已取消
                                    tvTransTime.setText("取消时间：" + cancelTime);
                                    tvOrderStatusDes.setText("应付金额：¥" + totalAmount);
                                }
                                totalSecond = Integer.parseInt(mDataBean.getCountdownTime());
//                                totalSecond =5;

                                setDeclareDetailInfo(orderStatus);

//                                List<DeclareOrderOuterBean.OrderGoodsBean> sd = new ArrayList<>();
//                                for (int i = 0; i < 1; i++) {
//                                    sd.addAll(declareOrderDetailOuterBean.getData().get(0).getOrderSonDetailsRespList().get(0).getOrderGoodsDetailsRespList());
//                                }
//                                for (DeclareOrderDetailRespBean orderRespBean : declareOrderDetailOuterBean.getData().get(0).getOrderSonDetailsRespList()) {
//                                    if (orderRespBean != null) {
//                                        orderRespBean.setOrderGoodsDetailsRespList(sd);
//                                    }
//                                }


                                mDatas.clear();
                                for (int i = 0; i < 1; i++) {//此处增加for循环只为了制造数据量大的情况
                                    mDatas.addAll(mDataBean.getOrderSonDetailsRespList());
                                }
                                declareOrderDetailAdapter.notifyDataSetChanged();
                            }
                        });
                    }

                    @Override
                    public void onError(Response<String> response) {
                        super.onError(response);
                        changePageState(Constants.PageState.ERROR);
                    }
                });
    }


    /**
     * 启动订单倒计时
     */
    private void startCountDownTime() {
        Log.i(TAG, "------------totalSecond:" + totalSecond);
        if (totalSecond > 0) {
            countDownTimeUtils = new CountDownTimeUtils(totalSecond * 1000, 1000);
            countDownTimeUtils.start();
            countDownTimeUtils.setCountDownTime(new CountDownTimeUtils.CountDonwTimeImpl() {
                @Override
                public void onTick(String time) {
                    String str = time + "后订单将自动取消";
                    if (tvOrderStatus != null) {
                        tvOrderStatus.setText(str);
                    }
                }

                @Override
                public void onFinish() {
                    DeclareOrderPuclicRequest.cancelDeclareOrder(mContext, mRespBean.getOrderSn(), () -> {
                        T.showToastyCenter(mContext, "当前订单已取消");
                        registEventBus(0, "DecOrderCancel");
                        getDeclareOrderDetailData();
                    });
                }
            });
        }
    }


    /**
     * 注册EventBus事件
     */
    private void registEventBus(int tag, String doTag) {
        DeclareOrderDetailEvent event = new DeclareOrderDetailEvent();
        event.setTag(tag);
        HashMap<String, Object> map = new HashMap<>();
        map.put("groupItemIndex", groupItemIndex);
        map.put("pageTag", mPageTag);
        map.put("doTag", doTag);
        event.setObject(map);
        EventBus.getDefault().post(event);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(DeclareOrderDetailEvent event) {
        int tag = event.getTag();
        Log.i(TAG, "----------event:" + JsonUtils.toJsonString(event));
        if (tag == 0) {//待付款（修改收货地址）
            HashMap<String, Object> map = (HashMap<String, Object>) event.getObject();
            String doTag = (String) map.get("doTag");
            if (doTag.equals("DecOrderPaySuccess")) {//订阅了从PayOrderActivity界面发布的事件（支付成功）
                cancelTimer();
                getDeclareOrderDetailData();
            }
        } else if (tag == 2) {//待收货（确认收货）
            HashMap<String, Object> map = (HashMap<String, Object>) event.getObject();
            String doTag = (String) map.get("doTag");
            if (doTag.equals("DecOrderReceivedSuccess")) {//订阅了从ConfirmgoodsActivity界面发布的事件（收货成功）
                getDeclareOrderDetailData();
            } else if (doTag.equals("DiffOrderCreated")) {//订阅了从ConfirmgoodsActivity界面发布的事件（差异单创建成功）
                finish();
            }
        }
    }

    private void cancelTimer() {
        if (countDownTimeUtils != null) {
            countDownTimeUtils.cancel();
        }
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        cancelTimer();
    }


}
