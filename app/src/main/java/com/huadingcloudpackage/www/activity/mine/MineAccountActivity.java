package com.huadingcloudpackage.www.activity.mine;

import android.content.Intent;
import android.os.Bundle;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.guoquan.yunpu.util.ComTools;
import com.huadingcloudpackage.www.R;
import com.huadingcloudpackage.www.adapter.CommentAdapter;
import com.huadingcloudpackage.www.adapter.itemdecoration.RvSpaceItemDecoration;
import com.huadingcloudpackage.www.base.BaseActivity;
import com.huadingcloudpackage.www.bean.BaseBean;
import com.huadingcloudpackage.www.bean.MineAccountBean;
import com.huadingcloudpackage.www.dialog.MineDataDialog;
import com.huadingcloudpackage.www.dialog.PaymentTypeDialog;
import com.huadingcloudpackage.www.http.UrlContent;
import com.huadingcloudpackage.www.util.JsonUtils;
import com.huadingcloudpackage.www.util.MyUtils;
import com.huadingcloudpackage.www.util.StatusBarUtils;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import okhttp3.RequestBody;

/**
 * 个人账户
 */
public class MineAccountActivity extends BaseActivity {

    @BindView(R.id.iv_return)
    ImageView ivReturn;
    @BindView(R.id.tv_balance)
    TextView tvBalance;
    @BindView(R.id.ll_type)
    LinearLayout llType;
    @BindView(R.id.tv_type)
    TextView tvType;
    @BindView(R.id.ll_data)
    LinearLayout llData;
    @BindView(R.id.tv_shouru)
    TextView tvShouru;
    @BindView(R.id.tv_zhichu)
    TextView tvZhichu;
    @BindView(R.id.rv_account)
    RecyclerView rvAccount;

    @BindView(R.id.tv_data)
    TextView tvData;
    private Calendar c;
    public int mIndex = 2;
    CommentAdapter<MineAccountBean.MonthTotalBean> commentAdapter;
    public List<MineAccountBean.MonthTotalBean> parentBeans = new ArrayList<>();
    public List<MineAccountBean.MonthTotalBean> childBeans = new ArrayList<>();
    private CommentAdapter<MineAccountBean.MonthTotalBean> commentAdapterInner;
    private int year, month, date;
    private long yearMonthDay;
    private long yearMonth;
    private String money;
    public Boolean isDay = false;


    private PaymentTypeDialog paymentTypeDialog;
    private MineDataDialog mineDataDialog;

    @Override
    public int getLayoutResId() {
        return R.layout.activity_mineaccount;
    }

    @Override
    public void initView(Bundle savedInstanceState) {
        StatusBarUtils.changStatusIconCollor(this, false);

        Intent intent = getIntent();
        money = intent.getStringExtra("money");
        tvBalance.setText(money);
        c = Calendar.getInstance();
        /*14 - 个人数据 15 - 城市仓数据 16 - 冻品批数据 25 - 报货订单*/
        year = c.get(Calendar.YEAR);
        month = c.get(Calendar.MONTH) + 1;
        date = c.get(Calendar.DATE);
        tvData.setText(year + "年" + month + "月");
        tvType.setText("全部类型");
        long monthOfYear = Long.parseLong(String.valueOf(getTimeStamp(MyUtils.getText(tvData), "yyyy年MM月")));
        getMyAccount(mIndex + "", monthOfYear);
        yearMonth = monthOfYear;
        initAdapter();
        rvAccount.setLayoutManager(new LinearLayoutManager(MineAccountActivity.this));
        int spanCount = 1;
        int spacing = 50;
        boolean includeEdge = false;
        RvSpaceItemDecoration rvSpaceItemDecoration = new RvSpaceItemDecoration(this, 15, false);
        rvAccount.addItemDecoration(rvSpaceItemDecoration);
//        rvAccount.addItemDecoration(new GridSpacingItemDecoration(spanCount, spacing, includeEdge));
        rvAccount.setAdapter(commentAdapter);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @OnClick({R.id.iv_return, R.id.ll_type, R.id.ll_data})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_return:
                finish();
                break;
            case R.id.ll_type://支付类型弹窗
                if (paymentTypeDialog != null) {
                    paymentTypeDialog.show();
                    return;
                }
                paymentTypeDialog = new PaymentTypeDialog(this, new PaymentTypeDialog.OnItemListener() {
                    @Override
                    public void onItemSignin(int index) {
                        if (index == 2) {
                            mIndex = 2;
                            tvType.setText("全部类型");
                        } else if (index == 0) {
                            mIndex = 0;
                            tvType.setText("充值");
                        } else if (index == 1) {
                            mIndex = 1;
                            tvType.setText("订单支出");
                        } else if (index == 3) {
                            mIndex = 3;
                            tvType.setText("售后退款");
                        } else if (index == 4) {
                            mIndex = 4;
                            tvType.setText("售后支付");
                        }else if (index == 5) {
                            mIndex = 5;
                            tvType.setText("退款");
                        }


                        if (isDay) {
                            getMyDateAccount(mIndex + "", yearMonthDay + "");
                        } else {
                            getMyAccount(mIndex + "", yearMonth);
                        }
                    }
                });
                //设置弹窗位于屏幕底部
                paymentTypeDialog.getWindow().setGravity(Gravity.BOTTOM);
                paymentTypeDialog.show();
                //设置窗口横向填充
                WindowManager windowManager = getWindowManager();
                Display display = windowManager.getDefaultDisplay();
                WindowManager.LayoutParams lp = paymentTypeDialog.getWindow().getAttributes();
                lp.width = (int) (display.getWidth()); //设置宽度
                paymentTypeDialog.getWindow().setAttributes(lp);
                break;
            case R.id.ll_data:
                if (mineDataDialog != null) {
                    mineDataDialog.show();
                    return;
                }
                mineDataDialog = new MineDataDialog(this, R.style.dialog, new MineDataDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(int year, int monthOfYear, int dayOfMonth) {//某一天
                        tvData.setText(year + "年" + monthOfYear + "月" + dayOfMonth + "日");
                        //转换时间戳
                        yearMonthDay = getTimeStamp(MyUtils.getText(tvData), "yyyy年MM月dd日");
                        getMyDateAccount(mIndex + "", yearMonthDay + "");
                        isDay = true;
                    }
                },
                        new MineDataDialog.OnDateZeroSetListener() {//某一个月
                            @Override
                            public void onDateZeroSet(int year, int monthOfYear) {
                                tvData.setText(year + "年" + monthOfYear + "月");
                                //转换时间戳
                                yearMonth = getTimeStamp(MyUtils.getText(tvData), "yyyy年MM月");
                                getMyAccount(mIndex + "", yearMonth);
                                isDay = false;
                            }
                        }, year, month, date, true);
                //设置弹窗位于屏幕底部
                mineDataDialog.getWindow().setGravity(Gravity.BOTTOM);
                mineDataDialog.show();
                //设置窗口横向填充
                WindowManager windowManagerData = getWindowManager();
                Display displayData = windowManagerData.getDefaultDisplay();
                WindowManager.LayoutParams lpData = mineDataDialog.getWindow().getAttributes();
                lpData.width = (int) (displayData.getWidth()); //设置宽度
                mineDataDialog.getWindow().setAttributes(lpData);
                break;
        }
    }

    private void initAdapter() {
        commentAdapter = new CommentAdapter<MineAccountBean.MonthTotalBean>(R.layout.item_mine_account, parentBeans) {
            @Override
            public void setViewData(BaseViewHolder holder, MineAccountBean.MonthTotalBean item, int position) {
                holder.setText(R.id.tv_detial_data, item.getWalletTime());//查询日期
                holder.setText(R.id.tv_detail_zhichu, item.getDayExpendTotal());//支出金额
                holder.setText(R.id.tv_detail_shouru, item.getDayIncomeTotal());//收入金额
                RecyclerView recycleViewInner = holder.getView(R.id.recycleview_item);
                recycleViewInner.setLayoutManager(new LinearLayoutManager(MineAccountActivity.this));
                childBeans.clear();
                childBeans.addAll(item.getExhibiWalletLogList());
                initAdapterInner();
                recycleViewInner.setAdapter(commentAdapterInner);
            }

            @Override
            public void setEvent(BaseViewHolder holder, MineAccountBean.MonthTotalBean item, int position) {

            }
        };

        View view = LayoutInflater.from(this).inflate(R.layout.layout_empty_view, null);
        ImageView ivEmpty = view.findViewById(R.id.iv_empty);
        TextView tvEmpty = view.findViewById(R.id.tv_empty);
        ivEmpty.setImageResource(R.mipmap.unorder);
        tvEmpty.setText("暂无记录~");
        commentAdapter.setEmptyView(view);
    }

    private void initAdapterInner() {
        commentAdapterInner = new CommentAdapter<MineAccountBean.MonthTotalBean>(R.layout.item_mine_account_item, childBeans) {
            @Override
            public void setViewData(BaseViewHolder holder, MineAccountBean.MonthTotalBean item, int position) {
                String type = item.getType();
                ImageView ivType = holder.getView(R.id.iv_type);
                TextView tvDingdannum = holder.getView(R.id.tv_dingdannum);
                TextView tvMoney = holder.getView(R.id.tv_money);
                View viewHLine = holder.getView(R.id.view_h_line);
                View viewVLine = holder.getView(R.id.view_v_line);
                String createTime = item.getCreateTime();
                String[] strarray = createTime.split("[ ]");
                String orderSn = "";
                holder.setText(R.id.tv_time, strarray[1]);
                if ("0".equals(type)) {
                    ivType.setImageResource(R.mipmap.dingdanchongzhi);
                    holder.setText(R.id.tv_type_name, "充值");
                    tvMoney.setText("+" + item.getRechargeMoney());
                    tvMoney.setTextColor(0xfff8b040);
                    viewHLine.setVisibility(View.GONE);
                } else if ("1".equals(type)) {
                    ivType.setImageResource(R.mipmap.dingdanzhichu);
                    holder.setText(R.id.tv_type_name, "订单支出");
                    tvMoney.setText("-" + item.getRechargeMoney());
                    tvMoney.setTextColor(0xff333333);
                    orderSn = "主单编号：" + item.getOrderSn();
                    viewHLine.setVisibility(View.VISIBLE);
                } else if ("3".equals(type)) {
                    ivType.setImageResource(R.drawable.account_sell_back);
                    holder.setText(R.id.tv_type_name, "售后退款");
                    tvMoney.setText("+" + item.getRechargeMoney());
                    tvMoney.setTextColor(0xfffd6d14);
                    orderSn = "差异单编号：" + item.getOrderSn();
                    viewHLine.setVisibility(View.VISIBLE);
                } else if ("4".equals(type)) {
                    ivType.setImageResource(R.drawable.img_after_sale_pay);
                    holder.setText(R.id.tv_type_name, "售后支付");
                    tvMoney.setText("-" + item.getRechargeMoney());
                    tvMoney.setTextColor(0xff333333);
                    orderSn = "差异单编号：" + item.getOrderSn();
                    viewHLine.setVisibility(View.VISIBLE);
                } else if ("5".equals(type)) {
                    ivType.setImageResource(R.drawable.account_back);
                    holder.setText(R.id.tv_type_name, "退款");
                    tvMoney.setText("+" + item.getRechargeMoney());
                    tvMoney.setTextColor(0xff333333);
                    orderSn = "主单编号：" + item.getOrderSn();
                    viewHLine.setVisibility(View.VISIBLE);
                }


                tvDingdannum.setText(orderSn);

                if (position == childBeans.size() - 1) {
                    viewVLine.setVisibility(View.GONE);
                } else {
                    viewVLine.setVisibility(View.VISIBLE);
                }


            }

            @Override
            public void setEvent(BaseViewHolder holder, MineAccountBean.MonthTotalBean item, int position) {

            }
        };
    }


    private void updateInfo(MineAccountBean bean) {
        tvShouru.setText("收入 ¥0.00");
        tvZhichu.setText("支出 ¥0.00");
        if (bean.getMonthTotal() != null && bean.getMonthTotal().size() > 0) {
            tvShouru.setText("收入 ¥" + bean.getMonthTotal().get(0).getMonthIncomeTotal());
            tvZhichu.setText("支出 ¥" + bean.getMonthTotal().get(0).getMonthExpendTotal());
        }
        parentBeans.clear();
        if (bean.getMonthTotal() != null && bean.getMonthTotal().size() > 0) {
            parentBeans.addAll(bean.getMonthTotal().get(0).getExhibiWalletLogList());
        }
        commentAdapter.notifyDataSetChanged();
        if (parentBeans.size() > 0) {
            rvAccount.scrollToPosition(0);
        }
    }


    /**
     * 我的账户
     */
    private void getMyAccount(String type, long monthTime) {
        //Type 0：收入  1：支出  2：全部
        showLoading();
        HashMap<String, Object> params = new HashMap<>();
        params.put("type", type);
        params.put("monthTime", monthTime);
        OkGo.<String>post(UrlContent.WALLETWATE)
                .tag(this)
                .upRequestBody(RequestBody.create(UrlContent.JSON, JsonUtils.toJsonString(params)))
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        BaseBean baseBean = JsonUtils.parse(response.body(), BaseBean.class);
                        comTools.parsingReturnData(baseBean, new ComTools.OnParsingReturnListener() {
                            @Override
                            public void onParsingSuccess() {
                                MineAccountBean bean = JsonUtils.parse(response.body(), MineAccountBean.class);
                                updateInfo(bean);
                            }
                        });


                    }

                    @Override
                    public void onFinish() {
                        super.onFinish();
                        dissMissLoading();
                    }

                    @Override
                    public void onError(Response<String> response) {
                        super.onError(response);
                        T("网络开小差，请稍后重试 ~");

                    }
                });
    }

    /**
     * 我的账户
     */
    private void getMyDateAccount(String type, String dayTime) {
        //Type 0：收入  1：支出  2：全部
        showLoading();
        OkGo.<String>post(UrlContent.WALLETWATE)
                .tag(this)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        BaseBean baseBean = JsonUtils.parse(response.body(), BaseBean.class);
                        comTools.parsingReturnData(baseBean, new ComTools.OnParsingReturnListener() {
                            @Override
                            public void onParsingSuccess() {
                                MineAccountBean bean = JsonUtils.parse(response.body(), MineAccountBean.class);
                                updateInfo(bean);

                            }
                        });
                    }

                    @Override
                    public void onFinish() {
                        super.onFinish();
                        dissMissLoading();
                    }

                    @Override
                    public void onError(Response<String> response) {
                        super.onError(response);
                        T("网络开小差，请稍后重试 ~");

                    }
                });
    }

    /**
     * 时间转换为时间戳
     *
     * @param timeStr 时间 例如: 2016-03-09
     * @param format  时间对应格式  例如: yyyy-MM-dd
     * @return
     */
    public static long getTimeStamp(String timeStr, String format) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(format);
        Date date = null;
        try {
            date = simpleDateFormat.parse(timeStr);
            long timeStamp = date.getTime() / 1000;
            return timeStamp;
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return 0;
    }
}