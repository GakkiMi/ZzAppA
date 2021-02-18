package com.huadingcloudpackage.www.activity.order.ordercommit;


import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bigkoo.pickerview.builder.OptionsPickerBuilder;
import com.bigkoo.pickerview.view.OptionsPickerView;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.contrarywind.view.WheelView;
import com.huadingcloudpackage.www.R;
import com.huadingcloudpackage.www.adapter.CommentAdapter;
import com.huadingcloudpackage.www.adapter.ordersettlement.OrderSettleChildAdapter;
import com.huadingcloudpackage.www.base.BaseActivity;
import com.huadingcloudpackage.www.bean.AddressListBean;
import com.huadingcloudpackage.www.bean.BaseBean;
import com.huadingcloudpackage.www.bean.DefaultAddressBean;
import com.huadingcloudpackage.www.bean.OrderCreatBean;
import com.huadingcloudpackage.www.bean.OrderSettlementBean;
import com.huadingcloudpackage.www.bean.OrderSettlementBean.OrderSettlementData;
import com.huadingcloudpackage.www.bean.OrderSettlementBean.OrderSettleGoodsList;
import com.huadingcloudpackage.www.callback.DialogCallback;
import com.huadingcloudpackage.www.eventbus.AHomeEvent;
import com.huadingcloudpackage.www.eventbus.GoodsDetailEvent;
import com.huadingcloudpackage.www.eventbus.GouwucheEvent;
import com.huadingcloudpackage.www.http.UrlContent;
import com.huadingcloudpackage.www.util.BigDecimalUtils;
import com.huadingcloudpackage.www.util.GlideUtils;
import com.huadingcloudpackage.www.util.JsonUtils;
import com.huadingcloudpackage.www.util.Logger;
import com.huadingcloudpackage.www.util.SimpleDataFormatUtils;
import com.huadingcloudpackage.www.util.SpannableUtils;
import com.huadingcloudpackage.www.util.T;
import com.huadingcloudpackage.www.widget.ShapeTextView;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.model.Response;

import org.greenrobot.eventbus.EventBus;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import butterknife.BindView;
import butterknife.OnClick;
import okhttp3.RequestBody;


/**
 * 订单结算
 */
public class OrderCommitActivity extends BaseActivity {

    @BindView(R.id.order_commit_rl_shouhuo_info)
    RelativeLayout rlShouhuoInfo;
    @BindView(R.id.recycleView)
    RecyclerView recyclerView;
    @BindView(R.id.tv_jiesuan)
    ShapeTextView tv_jiesuan;
    @BindView(R.id.order_commit_tv_name)
    TextView tvReceiverName;
    @BindView(R.id.order_commit_tv_phone_num)
    TextView tvReceiverPhoneNum;
    @BindView(R.id.order_commit_tv_address)
    TextView tvReceiverAddress;

    @BindView(R.id.order_sta_tv_count)
    TextView tvGoodsTotalCount;
    @BindView(R.id.order_sta_tv_price)
    TextView tvGoodsTotalPrice;
    @BindView(R.id.order_sta_tv_yunfei)
    TextView tvGoodsTotalYunFei;
    @BindView(R.id.order_tv_should_pay_price)
    TextView tvShouldPayPrice;
    @BindView(R.id.order_commit_ll_content)
    LinearLayout llContent;
    @BindView(R.id.order_commit_rl_bottom)
    RelativeLayout rlBottom;
    @BindView(R.id.order_commit_tv_reload)
    ShapeTextView stvReload;

    private Context mContext = OrderCommitActivity.this;

    private OrderAdapter orderAdapter;//父适配器
    private CommentAdapter<OrderSettleGoodsList> commentAdapterInner;//子适配器

    private OptionsPickerView optionsPickerView = null;

    private OptionsPickerView optionsTimePickerView;//自提时间选择器
    private ArrayList<Object> timePickFirstCloumnDatas, timePickSecondCloumnDatas;//第一列数据,第二列数据
    private ArrayList<String> pickTimeRealYmdList;//自提时间选择器真正选中的时间

    private OptionsPickerView optionsDeliveryPickerView;//配送方式选择器
    private ArrayList<Object> deliverTypeList;//数据

    //网络返回的数据
    private List<OrderSettlementData> mRealDatas = new ArrayList<>();
    private double mShippingPrice = 0.0;//运费
    private double mGoodsPrice = 0.0;//商品价格
    private int mGoodsCount = 0;//商品数量

    private String TAG = "OrderCommitActivity";

    private String addressId = null;//收货默认地址id

    private double totalAmount = 0.0;//总金额

    private Bundle mBundle;

    private String actionType;//从购物车过来为 actCart  从立即购买为 actGoods

    private String phoneNumber = "";//登录用户的手机号码（当用户忘记密码和未设置密码需要用到）

    private boolean isNetError = false;//网络异常的标志

    private String mSpecKeyName;//立即购买进入订单结算时返回的一个字段

    private String mUnitTypeFromBuyNow;//立即购买传过来的商品单位

    @Override
    public int getLayoutResId() {
        return R.layout.activity_order_commit;
    }


    @Override
    public void initView(Bundle savedInstanceState) {
        initToolbar();
        setBackBtn();
        setTitle("订单结算");
        setToolbarBgColor(getResources().getColor(R.color.colorPageBg), false);

        orderAdapter = new OrderAdapter(R.layout.item_commit_goods, mRealDatas);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(orderAdapter);

        initCustomTimePickView();
        initCustomDeliveryPickView();

        mBundle = getIntent().getExtras();

        String requestJson = mBundle.getString("data");
//        String requestJson = "{\"list\":[{\"specId\":\"GG2020120600001\",\"goodsId\":\"SP2020120700007\",\"goodsNum\":1}]}";
        requestDefaultAddress();
        requestOrderSettlement(requestJson);
    }


    @OnClick({R.id.tv_jiesuan, R.id.order_commit_rl_shouhuo_info, R.id.order_commit_tv_reload})
    public void onClick(View view) {
        if (isFastClick()) {
            return;
        }
        switch (view.getId()) {
            case R.id.order_commit_rl_shouhuo_info://跳转到我的地址
//                Bundle bundle = new Bundle();
//                bundle.putString("pageTag", "OrderCommitActivity");//传递一个bundle
//                startActivity(AddressListActivity.class, bundle);
                break;
            case R.id.tv_jiesuan:
//                if (addressId == null) {
//                    T.showToastyCenter(this, "请先设置收货地址");
//                    return;
//                }
                requestSubmitOrder(getParams());
                break;
            case R.id.order_commit_tv_reload:
                break;
            default:
                break;
        }
    }


    //购物车参数赋值
    private String getParams() {
        HashMap<String, Object> map = new HashMap<>();
        List<HashMap<String, Object>> orderSettleList = new ArrayList<>();
        for (OrderSettlementData orderSettlementData : mRealDatas) {
            HashMap<String, Object> settleDataMap = new HashMap<>();
            settleDataMap.put("freight", orderSettlementData.getFreight());
            settleDataMap.put("totalPrice", orderSettlementData.getTotalPrice());
            settleDataMap.put("goodsItemNums", orderSettlementData.getGoodsItemNums());
            settleDataMap.put("supplierType", orderSettlementData.getSupplierType());
            settleDataMap.put("name", orderSettlementData.getName());
            settleDataMap.put("deliveryMethod", "1");
            settleDataMap.put("remark", orderSettlementData.getOrderRemark());

            List<OrderSettleGoodsList> goodsList = new ArrayList<>();
            for (OrderSettleGoodsList orderSettleGoods : orderSettlementData.getList()) {
                HashMap<String, Object> goodsMap = new HashMap<>();
                goodsMap.put("goodsId", orderSettleGoods.getGoodsId());
                goodsMap.put("specId", orderSettleGoods.getSpecId());
                goodsMap.put("goodsNum", orderSettleGoods.getGoodsNum());
                goodsMap.put("cartId", orderSettleGoods.getCartId());
                goodsMap.put("specRatio", orderSettleGoods.getSpecRatio());
                goodsMap.put("goodsName", orderSettleGoods.getGoodsName());
                goodsList.add(orderSettleGoods);
            }
            settleDataMap.put("list", goodsList);
            orderSettleList.add(settleDataMap);
        }
        map.put("list", orderSettleList);
        String jsonStr = JsonUtils.toJsonString(map);

        Logger.i(TAG, "-------jsonStrFromCart:" + jsonStr);

        return jsonStr;
    }


    public class OrderAdapter extends BaseQuickAdapter<OrderSettlementData, BaseViewHolder> {

        public OrderAdapter(int layoutResId, @Nullable List<OrderSettlementData> data) {
            super(layoutResId, data);
        }

        @Override
        protected void convert(@NotNull BaseViewHolder holder, OrderSettlementData item) {
            int position = holder.getLayoutPosition();

            TextView tvWhName = holder.getView(R.id.item_commit_goods_ware_house_name);//仓库名称
            TextView tvDeliveryType = holder.getView(R.id.item_commit_goods_tv_delivery_type);//配送方式
            TextView tvRemark = holder.getView(R.id.item_commit_goods_tv_remark);//订单备注
            TextView tvYunFei = holder.getView(R.id.item_commit_goods_tv_shipping);//运费
            TextView tvWhHouseAddress = holder.getView(R.id.item_commit_goods_tv_wh_address);//仓库地址
            TextView tvZiTiTime = holder.getView(R.id.item_commit_goods_tv_time);//自提时间


//            if (TextUtils.isEmpty(deliverAddress)) {//没有仓库地址
            tvDeliveryType.setEnabled(false);
            tvDeliveryType.setCompoundDrawablesWithIntrinsicBounds(null, null, null, null);
//            } else {
//            tvDeliveryType.setEnabled(true);
//            tvDeliveryType.setCompoundDrawablesWithIntrinsicBounds(null, null, getResources().getDrawable(R.mipmap.return_right), null);
//            }
            tvWhName.setText(item.getName());
            tvDeliveryType.setText(item.getDeliveryType());
            tvRemark.setText(item.getOrderRemark());

            LinearLayout llDeliveryType = holder.getView(R.id.ll_choose_dlivery_type);
            LinearLayout llYunFei = holder.getView(R.id.ll_yunfei);
            LinearLayout llWhAddress = holder.getView(R.id.ll_cangku_address);
            LinearLayout llZiTiTime = holder.getView(R.id.ll_ziti_time);

            llDeliveryType.setVisibility(View.GONE);
            String deliveryType = item.getDeliveryType();
            if ("快递物流".equals(deliveryType)) {
                llYunFei.setVisibility(View.GONE);
                llWhAddress.setVisibility(View.GONE);
                llZiTiTime.setVisibility(View.GONE);
                tvYunFei.setText("¥" + item.getFreight());
            } else if ("仓库自提".equals(deliveryType)) {
                llYunFei.setVisibility(View.GONE);
                llWhAddress.setVisibility(View.VISIBLE);
                llZiTiTime.setVisibility(View.VISIBLE);
                tvWhHouseAddress.setText("");
                tvZiTiTime.setText(item.getSelfMentionTimeShow());
            }

            View dividerView = holder.getView(R.id.item_commit_goods_divider_view);
            if (mRealDatas.size() == 1 || (mRealDatas.size() > 1 && position == mRealDatas.size() - 1)) {
                dividerView.setVisibility(View.GONE);
            } else {
                dividerView.setVisibility(View.VISIBLE);
            }

            RecyclerView recycleViewGoods = holder.getView(R.id.recycleViewGoods);
            recycleViewGoods.setLayoutManager(new LinearLayoutManager(OrderCommitActivity.this));
            List<OrderSettleGoodsList> goodsList = mRealDatas.get(position).getList();
            OrderSettleChildAdapter orderSettleChildAdapter = new OrderSettleChildAdapter(mContext, R.layout.item_commit_goods_child, goodsList);
            recycleViewGoods.setAdapter(orderSettleChildAdapter);


            //订单备注的点击  弹出输入框进行输入
            tvRemark.setOnClickListener(v -> {
                View dialogInputView = LayoutInflater.from(mContext).inflate(R.layout.dialog_order_remark_input, null);
                EditText etInput = dialogInputView.findViewById(R.id.dialog_input_et);
                String orderRemark = item.getOrderRemark();
                etInput.setText(orderRemark);
                etInput.setFocusable(true);
                etInput.setFocusableInTouchMode(true);
                etInput.requestFocus();
                AlertDialog dialog = new AlertDialog.Builder(mContext)
                        .setView(dialogInputView)
                        .setPositiveButton("完成", (dialog1, which) -> {
                            String inputStr = etInput.getText().toString().trim();
                            item.setOrderRemark(inputStr);
                            tvRemark.setText(inputStr);
                            //设置软键盘为挤压模式
                            updateSoftInputMethod(OrderCommitActivity.this, WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
                        }).setNegativeButton("取消", (dialog12, which) -> {
                            //设置软键盘为挤压模式
                            updateSoftInputMethod(OrderCommitActivity.this, WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
                        }).create();
                dialog.show();
                Timer timer = new Timer();
                timer.schedule(new TimerTask() {
                    public void run() {
//                        InputMethodManager inputManager = (InputMethodManager) etInput.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
//                        inputManager.showSoftInput(etInput, 0);
                        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                        imm.showSoftInput(etInput, InputMethodManager.SHOW_IMPLICIT);
                    }
                }, 300);

                //设置软键盘为覆盖模式
                updateSoftInputMethod(OrderCommitActivity.this, WindowManager.LayoutParams.SOFT_INPUT_ADJUST_NOTHING);
            });
            //配送方式的点击选择
            tvDeliveryType.setOnClickListener(view -> {
                optionsDeliveryPickerView.show();
                setPickViewSelectImp((firstColumnIndex, secondColumnIndex, thirdColumnIndex) -> {
                    String type = (String) deliverTypeList.get(firstColumnIndex);
                    mRealDatas.get(position).setDeliveryType(type);
                    notifyItemChanged(position, "deliverType");
                    updateBaseInfo();
                });
            });
            //自提时间的点击选择
            tvZiTiTime.setOnClickListener(view -> {
                optionsTimePickerView.show();
                setPickViewSelectImp((firstColumnIndex, secondColumnIndex, thirdColumnIndex) -> {
                    setSelfMentionTime(mRealDatas.get(position), firstColumnIndex, secondColumnIndex);
                    notifyItemChanged(position, "selfTime");
                });
            });


        }

        @Override
        protected void convert(@NotNull BaseViewHolder holder, OrderSettlementData item, @NotNull List<?> payloads) {
            super.convert(holder, item, payloads);
            TextView tvYunFei = holder.getView(R.id.item_commit_goods_tv_shipping);//运费
            TextView tvWhHouseAddress = holder.getView(R.id.item_commit_goods_tv_wh_address);//仓库地址
            TextView tvZiTiTime = holder.getView(R.id.item_commit_goods_tv_time);//自提时间
            TextView tvDeliveryType = holder.getView(R.id.item_commit_goods_tv_delivery_type);//配送方式
            LinearLayout llYunFei = holder.getView(R.id.ll_yunfei);
            LinearLayout llWhAddress = holder.getView(R.id.ll_cangku_address);
            LinearLayout llZiTiTime = holder.getView(R.id.ll_ziti_time);

            String deliveryType = item.getDeliveryType();
            tvDeliveryType.setText(deliveryType);

            for (Object payload : payloads) {
                switch (String.valueOf(payload)) {
                    case "deliverType":
                        if ("快递物流".equals(deliveryType)) {
                            llYunFei.setVisibility(View.VISIBLE);
                            llWhAddress.setVisibility(View.GONE);
                            llZiTiTime.setVisibility(View.GONE);
                            tvYunFei.setText("¥" + item.getFreight());
                        } else if ("仓库自提".equals(deliveryType)) {
                            llYunFei.setVisibility(View.GONE);
                            llWhAddress.setVisibility(View.VISIBLE);
                            llZiTiTime.setVisibility(View.VISIBLE);
                            tvWhHouseAddress.setText("");
                            tvZiTiTime.setText(item.getSelfMentionTimeShow());
                        }
                        break;
                    case "selfTime":
                        tvZiTiTime.setText(item.getSelfMentionTimeShow());
                        break;
                    default:
                        break;
                }
            }
        }
    }


    /**
     * 获取默认收货地址
     */
    private void requestDefaultAddress() {
        OkGo.<String>post(UrlContent.GET_DEFALULT_ADDRESS)
                .tag(this)
                .execute(new DialogCallback(this, true) {
                    @Override
                    public void onSuccess(Response<String> response) {
                        BaseBean baseBean = JsonUtils.parse(response.body(), BaseBean.class);
                        comTools.parsingReturnData(baseBean, () -> {
                            DefaultAddressBean bean = JsonUtils.parse(response.body(), DefaultAddressBean.class);
                            AddressListBean.DataBean addressDataBean = bean.getData();
                            if (addressDataBean != null) {
                                setAddressInfo(addressDataBean);
                            }
                        });
                    }

                    @Override
                    public void onError(Response<String> response) {
                        String responseStr = response.getException().toString();
                        if (responseStr.contains("ConnectException")) {
                            T.showToastyCenter(mContext, "网络开小差，请稍后重试 ~");
                        }
                    }
                });
    }

    /**
     * 更新地址信息
     *
     * @param dataBean 地址实体类
     */
    private void setAddressInfo(AddressListBean.DataBean dataBean) {
        tvReceiverName.setText(dataBean.getLinkName());
        tvReceiverPhoneNum.setText(dataBean.getCusPhone());
        tvReceiverAddress.setText(dataBean.getCusAddressAll());
//        addressId = dataBean.getId() + "";
    }


    /**
     * 获取订单结算列表
     */
    private void requestOrderSettlement(String params) {
        RequestBody requestBody = RequestBody.create(UrlContent.JSON, params);
        OkGo.<String>post(UrlContent.SETTLE_ORDER)
                .tag(this)
                .upRequestBody(requestBody)
                .execute(new DialogCallback(this, true) {
                    @Override
                    public void onSuccess(Response<String> response) {
                        mRealDatas.clear();

                        BaseBean baseBean = JsonUtils.parse(response.body(), BaseBean.class);
                        comTools.parsingReturnData(baseBean, () -> {
                            OrderSettlementBean orderSettlementBean = JsonUtils.parse(response.body(), OrderSettlementBean.class);


                            List<OrderSettlementData> settlementDataList = orderSettlementBean.getData();


                            for (OrderSettlementData settlementData : settlementDataList) {
                                //网络请求初始化自加字段
                                settlementData.setOrderRemark("");
                                settlementData.setDeliveryType("快递物流");
                                setSelfMentionTime(settlementData, 0, 0);
                            }

                            mRealDatas.addAll(settlementDataList);
                            if (orderAdapter != null) {
                                orderAdapter.notifyDataSetChanged();
                            }
                            updateBaseInfo();
                        });
                    }

                    @Override
                    public void onError(Response<String> response) {
                        String responseStr = response.getException().toString();
                        if (responseStr.contains("ConnectException")) {
                            isNetError = true;
                        }
                        mRealDatas.clear();
                        if (orderAdapter != null) {
                            orderAdapter.notifyDataSetChanged();
                        }
                        updateBaseInfo();
                        isNetError = false;
                    }
                });
    }


    /**
     * 更新基本展示信息
     */
    private void updateBaseInfo() {


        llContent.setVisibility(mRealDatas.size() > 0 ? View.VISIBLE : View.GONE);
        rlBottom.setVisibility(mRealDatas.size() > 0 ? View.VISIBLE : View.GONE);

        if (isNetError) {
            stvReload.setVisibility(View.VISIBLE);
            return;
        } else {
            stvReload.setVisibility(View.GONE);
        }

        tvGoodsTotalCount.setText(mGoodsCount + "件");
        tvGoodsTotalPrice.setText("¥" + mGoodsPrice);

        double totalYunFei = 0.0;
        mGoodsPrice = 0.0;
        mGoodsCount = 0;
        for (OrderSettlementData settlementData : mRealDatas) {
            if ("快递物流".equals(settlementData.getDeliveryType())) {
                totalYunFei = BigDecimalUtils.add(totalYunFei, Double.valueOf(settlementData.getFreight()));
            }
            mGoodsPrice = BigDecimalUtils.add(mGoodsPrice, Double.valueOf(settlementData.getTotalPrice()));
            mGoodsCount += Integer.parseInt(settlementData.getGoodsItemNums());
        }
        try {
            tvGoodsTotalCount.setText(mGoodsCount + "件");
            tvGoodsTotalPrice.setText(SpannableUtils.changeSpannableTv("¥" + mGoodsPrice));
            tvGoodsTotalYunFei.setText(SpannableUtils.changeSpannableTv("¥" + BigDecimalUtils.round(totalYunFei, 2)));
            totalAmount = BigDecimalUtils.add(mGoodsPrice, totalYunFei);
            tvShouldPayPrice.setText(SpannableUtils.changeSpannableTv("¥ " + BigDecimalUtils.round(totalAmount, 2)));
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }


    /**
     * 结算提交订单
     *
     * @param paramsJson 传递的参数json串
     */
    private void requestSubmitOrder(String paramsJson) {
        RequestBody body = RequestBody.create(UrlContent.JSON, paramsJson);

        OkGo.<String>post(UrlContent.CREATE_ORDER)
                .tag(this)
                .upRequestBody(body)
                .execute(new DialogCallback(this, true) {
                    @Override
                    public void onSuccess(Response<String> response) {
                        BaseBean baseBean = JsonUtils.parse(response.body(), BaseBean.class);
                        Logger.LogOutput(response.body());

                        comTools.parsingReturnData(baseBean, () -> {
//                            //刷新购物车列表
                            EventBus.getDefault().post(new GouwucheEvent(0));
//                            //更新商品详细角标
                            EventBus.getDefault().post(new GoodsDetailEvent(0));
                            OrderCreatBean orderCreatBean = JsonUtils.parse(response.body(), OrderCreatBean.class);
                            Bundle bundle = new Bundle();
                            bundle.putString("orderSn", orderCreatBean.getData().getOrderSn());
                            bundle.putString("totalAmount", orderCreatBean.getData().getTotal());
                            bundle.putString("pageTag", "OrderCommitActivity");
                            startActivity(PayOderActivity.class, bundle);
                            finish();
                        });
                    }

                    @Override
                    public void onError(Response<String> response) {
                        T("网络开小差，请稍后重试 ~");
                    }
                });
    }


    /**
     * 设置自提时间（显示的和上传的）
     *
     * @param orderSettlementData 当前要设置的仓库实体
     * @param firstColumnIndex    pickView第一列索引
     * @param secondColumnIndex   pickView第二列索引
     */
    private void setSelfMentionTime(OrderSettlementData orderSettlementData, int firstColumnIndex, int secondColumnIndex) {
        String showPickTime = timePickFirstCloumnDatas.get(firstColumnIndex).toString() + " " + timePickSecondCloumnDatas.get(secondColumnIndex).toString();

        orderSettlementData.setSelfMentionTimeShow(showPickTime);
        String amOrPm = ("上午").equals(timePickSecondCloumnDatas.get(secondColumnIndex)) ? "am" : "pm";
        orderSettlementData.setSelfMentionTimeUpLoad(pickTimeRealYmdList.get(firstColumnIndex) + " " + amOrPm);
    }


    /**
     * 初始化自提时间选择器
     */
    private void initCustomTimePickView() {
        pickTimeRealYmdList = new ArrayList<>();
        //时间选择器 第一列数据
        timePickFirstCloumnDatas = new ArrayList<>();
        //时间选择器 第二列数据
        timePickSecondCloumnDatas = new ArrayList<>();

        //根据本地时间算出后五天的数据
        Date todayDate = new Date();
        for (int i = 1; i <= 5; i++) {
            long time = todayDate.getTime() + i * 24 * 60 * 60 * 1000;
            Date date = new Date(time);
            String titileWeek = SimpleDataFormatUtils.DateToWeek(date);
            String titleMd = SimpleDataFormatUtils.formatDateToNeed(date, "MM月dd日");
            String requestDate = SimpleDataFormatUtils.formatDateToNeed(date, "yyyy-MM-dd");

            timePickFirstCloumnDatas.add(titleMd);
            pickTimeRealYmdList.add(requestDate);
        }

        timePickSecondCloumnDatas.add("上午");
        timePickSecondCloumnDatas.add("下午");

        customPickView("自提时间", 1, timePickFirstCloumnDatas, timePickSecondCloumnDatas, timePickFirstCloumnDatas);
    }

    /**
     * 初始化配送方式选择器
     */
    private void initCustomDeliveryPickView() {
        deliverTypeList = new ArrayList<>();
        deliverTypeList.add("快递物流");
        deliverTypeList.add("仓库自提");

        customPickView("配送方式", 1, deliverTypeList, deliverTypeList, deliverTypeList);
    }

    /**
     * 初始化选择器
     */
    private void customPickView(String title, final int showColumns, List firstColumnDatas, List secondColumnDatas, List thirdColumnDatas) {
        OptionsPickerBuilder optionsPickerBuilder = new OptionsPickerBuilder(this, (options1, options2, options3, v) -> {
            if (pickViewSelectImp != null) {
                pickViewSelectImp.pickViewSelect(options1, options2, options3);
            }
        }).setLayoutRes(R.layout.pickerview_custom_options, v -> {
            WheelView wheelView2 = v.findViewById(R.id.options2);
            WheelView wheelView3 = v.findViewById(R.id.options3);
            if ("配送方式".equals(title)) {
                //将第二级第三级wheelview隐藏掉
                wheelView2.setLayoutParams(new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT, 0f));
                wheelView3.setLayoutParams(new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT, 0f));
            } else if ("自提时间".equals(title)) {//展示两列
                wheelView3.setLayoutParams(new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT, 0f));
            }
            /*下面的不起作用
            wheelView2.setVisibility(View.GONE);*/

            //自定义布局中的控件初始化及事件处理
            TextView tvTitle = v.findViewById(R.id.pickview_custom_option_title);
            tvTitle.setText(title);
            TextView tvSure = v.findViewById(R.id.tv_sure);
            ImageView ivClose = v.findViewById(R.id.iv_close);
            tvSure.setOnClickListener(v1 -> {
                if ("配送方式".equals(title)) {
                    optionsDeliveryPickerView.returnData();
                    optionsDeliveryPickerView.dismiss();
                } else {
                    optionsTimePickerView.returnData();
                    optionsTimePickerView.dismiss();
                }

            });
            ivClose.setOnClickListener(iew -> {
                if ("配送方式".equals(title)) {
                    optionsDeliveryPickerView.dismiss();
                } else {
                    optionsTimePickerView.dismiss();
                }
            });
        }).setLineSpacingMultiplier(1.8f).setDecorView(this.getWindow().getDecorView().findViewById(android.R.id.content));
        if ("配送方式".equals(title)) {
            optionsDeliveryPickerView = optionsPickerBuilder.build();
            optionsDeliveryPickerView.setNPicker(firstColumnDatas, secondColumnDatas, thirdColumnDatas);
        } else {
            optionsTimePickerView = optionsPickerBuilder.build();
            optionsTimePickerView.setNPicker(firstColumnDatas, secondColumnDatas, thirdColumnDatas);
        }
    }


    public PickViewSelectImp pickViewSelectImp;

    public void setPickViewSelectImp(PickViewSelectImp pickViewSelectImp) {
        this.pickViewSelectImp = pickViewSelectImp;
    }

    public interface PickViewSelectImp {
        /**
         * @param firstColumnIndex  第一列索引
         * @param secondColumnIndex 第二列索引
         * @param thirdColumnIndex  第三列索引
         */
        void pickViewSelect(int firstColumnIndex, int secondColumnIndex, int thirdColumnIndex);
    }

    private static void updateSoftInputMethod(Activity activity, int softInputMode) {
        if (!activity.isFinishing()) {
            WindowManager.LayoutParams params = activity.getWindow().getAttributes();
            if (params.softInputMode != softInputMode) {
                params.softInputMode = softInputMode;
                activity.getWindow().setAttributes(params);
            }
        }
    }
}
