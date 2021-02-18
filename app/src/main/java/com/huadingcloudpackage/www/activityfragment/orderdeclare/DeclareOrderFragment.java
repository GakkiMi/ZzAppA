package com.huadingcloudpackage.www.activityfragment.orderdeclare;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.guoquan.yunpu.util.ComTools;
import com.huadingcloudpackage.www.R;
import com.huadingcloudpackage.www.activity.confirmgoods.ConfirmgoodsActivity;
import com.huadingcloudpackage.www.activity.gouwuche.GouWuCheActivity;
import com.huadingcloudpackage.www.activity.logisticsdetail.LogisticsDetailActivity;
import com.huadingcloudpackage.www.activity.order.ordercommit.PayOderActivity;
import com.huadingcloudpackage.www.activity.order.orderdeclare.DecOneMoreOrderActivity;
import com.huadingcloudpackage.www.activity.order.orderdeclare.DeclareOrderPuclicRequest;
import com.huadingcloudpackage.www.adapter.itemdecoration.RvSpaceItemDecoration;
import com.huadingcloudpackage.www.adapter.orderdeclare.DeclareOrderListGroupAdapter;
import com.huadingcloudpackage.www.base.BaseLazyFragment;
import com.huadingcloudpackage.www.bean.BaseBean;
import com.huadingcloudpackage.www.bean.DeclareOrderOuterBean;
import com.huadingcloudpackage.www.bean.DeclareOrderOuterBean.OrderDataList;
import com.huadingcloudpackage.www.dialog.CommomDialog;
import com.huadingcloudpackage.www.http.UrlContent;
import com.huadingcloudpackage.www.util.JsonUtils;
import com.huadingcloudpackage.www.util.T;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;
import com.scwang.smart.refresh.layout.SmartRefreshLayout;
import com.scwang.smart.refresh.layout.api.RefreshLayout;
import com.scwang.smart.refresh.layout.listener.OnRefreshLoadMoreListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import okhttp3.RequestBody;

/**
 * 文 件 名：DeclareOrderFragment
 * 描   述：TODO
 */
public class DeclareOrderFragment extends BaseLazyFragment implements DeclareOrderListGroupAdapter.DeclareOrderBottomTvClickImpl {

    @BindView(R.id.fragment_declare_order_rv_list)
    RecyclerView rlvOrderList;
    @BindView(R.id.fragment_declare_order_srl)
    SmartRefreshLayout smartrefreshlayout;


    private DeclareOrderListGroupAdapter declareOrderListGroupAdapter;
    public List<OrderDataList> mDatas = new ArrayList<>();

    private int mPage = 1;
    private int totalPage;

    public int mOrderType;//只用于请求相应的订单列表

    private String mPageTag = "";//为"全部"时表示再全部的tab下  ""为其他某一tab状态下

    private Context mContext;
    private boolean flag = false;//用来防止initview中的请求和refreshData中请求的重复

    private static final String TAG = "DeclareOrderFragment";

    private String mSearchOrderSn = "";//输入框中的订单号

    private View rvEmptyView;

    public static DeclareOrderFragment getInstance(int orderType) {
        DeclareOrderFragment declareOrderFragment = new DeclareOrderFragment();

        Bundle bundle = new Bundle();
        //将需要传递的字符串以键值对的形式传入bundle
        bundle.putInt("orderType", orderType);
        declareOrderFragment.setArguments(bundle);
        return declareOrderFragment;
    }


    public DeclareOrderFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mOrderType = getArguments().getInt("orderType");
        return inflater.inflate(R.layout.fragment_declare_order_common, container, false);
    }

    @Override
    protected void initView(View view) {
        super.initView(view);
        mContext = getActivity();
        rlvOrderList.setLayoutManager(new LinearLayoutManager(getActivity()));
        RvSpaceItemDecoration rvSpaceItemDecoration = new RvSpaceItemDecoration(getActivity(), 15);
        rlvOrderList.addItemDecoration(rvSpaceItemDecoration);
        if (mOrderType == -1) {
            mPageTag = "全部";
        } else {
            mPageTag = "";
        }
        declareOrderListGroupAdapter = new DeclareOrderListGroupAdapter(getActivity(), R.layout.item_declare_order_rv_group, mDatas, mPageTag);
        declareOrderListGroupAdapter.setOrderBottomTvClick(this);
        rlvOrderList.setAdapter(declareOrderListGroupAdapter);

        rvEmptyView = declareOrderListGroupAdapter.getRvEmptyView();

        smartrefreshlayout.setOnRefreshLoadMoreListener(new OnRefreshLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                if (mPage <= totalPage) {
                    getDeclareOrderData();
                } else {
                    smartrefreshlayout.finishLoadMoreWithNoMoreData();
                }
            }

            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                smartrefreshlayout.finishRefresh(1000);
                mPage = 1;
                getDeclareOrderData();
            }
        });
        getDeclareOrderData();
    }


    private void setRvEmptyView() {
        if (declareOrderListGroupAdapter.hasEmptyView()) return;
        declareOrderListGroupAdapter.setEmptyView(rvEmptyView);
    }


    /**
     * 每次点击tab重新请求第一页的数据  防止由于在当前tab下进行订单操作引起的其他tab下面订单最新显示问题
     * 首次被选中时，不去触发请求（一些对象还没初始化，会造成空指针）先让initView中的请求去执行
     */
    public void tabSelectRefreshData(String searchOrderSn) {
        if (!flag) {//如果flag=false 说明还没进行第一次请求  此方法不可执行
            flag = true;
            return;
        }
        mPage = 1;
        rlvOrderList.scrollToPosition(0);
        mSearchOrderSn = searchOrderSn;
        getDeclareOrderData();
    }


    /**
     * 所有订单
     */
    private void getDeclareOrderData() {
        showLoading();
        HashMap<String, Object> params = new HashMap<>();
        params.put("pageNum", mPage);
        params.put("pageSize", 10);
        params.put("status", mOrderType == -1 ? "" : mOrderType);
        params.put("orderSn", mSearchOrderSn);
        RequestBody requestBody = RequestBody.create(UrlContent.JSON, JsonUtils.toJsonString(params));
        OkGo.<String>post(UrlContent.BUSAPP_ORDER_LIST)
                .tag(this)
                .upRequestBody(requestBody)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        setRvEmptyView();
                        BaseBean baseBean = JsonUtils.parse(response.body(), BaseBean.class);
                        comTools.parsingReturnData(baseBean, new ComTools.OnParsingReturnListener() {
                            @Override
                            public void onParsingSuccess() {
                                DeclareOrderOuterBean listBean = JsonUtils.parse(response.body(), DeclareOrderOuterBean.class);
                                if (mPage == 1) {
                                    mDatas.clear();
                                }
                                if (listBean.getData() == null) {
                                    declareOrderListGroupAdapter.notifyDataSetChanged();
                                    return;
                                }
                                totalPage = Integer.parseInt(listBean.getData().getPages());
                                mPage++;


//                                List<DeclareOrderOuterBean.OrderGoodsBean> sd = new ArrayList<>();
//                                for (int i = 0; i < 50; i++) {
//                                    sd.addAll(listBean.getData().getList().get(0).getOrderRespList().get(0).getOrderGoodsList());
//                                }
//                                for (OrderDataList orderDataList : listBean.getData().getList()) {
//                                    for (DeclareOrderOuterBean.OrderRespBean orderRespBean : orderDataList.getOrderRespList()) {
//                                        if (orderRespBean != null) {
//                                            orderRespBean.setOrderGoodsList(sd);
//                                        }
//                                    }
//                                }
                                //过滤异常的单子（没有商品的）
                                for (OrderDataList orderDataList : listBean.getData().getList()) {
                                    if (orderDataList.getOrderRespList() != null) {
                                        if (orderDataList.getOrderRespList().size() > 0) {
                                            DeclareOrderOuterBean.OrderRespBean firstOrderResp = orderDataList.getOrderRespList().get(0);
                                            if (firstOrderResp != null) {
                                                mDatas.add(orderDataList);
                                            }
                                        }
                                    }
                                }

//                                mDatas.addAll(listBean.getData().getList());
                                declareOrderListGroupAdapter.notifyDataSetChanged();
                            }
                        });
                    }

                    @Override
                    public void onFinish() {
                        super.onFinish();
                        dissMissLoading();
                        if (smartrefreshlayout == null) return;
                        if (smartrefreshlayout.isLoading()) {
                            smartrefreshlayout.finishLoadMore();
                        }
                    }

                    @Override
                    public void onError(Response<String> response) {
                        super.onError(response);
                        T("网络开小差，请稍后重试 ~");
                    }
                });
    }


    /**
     * 删除条目(无动画)
     *
     * @param itemIndex 要更新的条目索引
     */
    public void notifyRemoveRvList(int itemIndex) {
        mDatas.remove(itemIndex);
        declareOrderListGroupAdapter.notifyDataSetChanged();
    }

    /**
     * 删除条目(有动画)
     *
     * @param itemIndex 要更新的条目索引
     */
    public void notifyRemoveRvListWithAnima(int itemIndex) {
        mDatas.remove(itemIndex);
        declareOrderListGroupAdapter.notifyItemRemoved(itemIndex);
        declareOrderListGroupAdapter.notifyItemRangeChanged(itemIndex, mDatas.size() - itemIndex);
    }


    /**
     * 刷新条目变成待发货
     */
    public void notifyItemToDeliver(int itemIndex) {
        mDatas.get(itemIndex).getOrderRespList().get(0).setOrderSonStatus("1");
        declareOrderListGroupAdapter.notifyDataSetChanged();
    }

    /**
     * 刷新条目变成已完成状态
     */
    public void notifyItemToCompleted(int itemIndex) {
        mDatas.get(itemIndex).getOrderRespList().get(0).setOrderSonStatus("3");
        declareOrderListGroupAdapter.notifyDataSetChanged();
    }

    /**
     * 刷新条目变成取消状态
     */
    public void notifyItemToCancel(int itemIndex) {
        mDatas.get(itemIndex).getOrderRespList().get(0).setOrderSonStatus("4");
        declareOrderListGroupAdapter.notifyDataSetChanged();
    }

    /**
     * 待发货条目变成已提醒
     */
    public void notifyItemToReminded(int itemIndex) {
        mDatas.get(itemIndex).getOrderRespList().get(0).setRemind("1");
        declareOrderListGroupAdapter.notifyDataSetChanged();
    }


    @Override
    public void leftTvClick(Bundle bundle) {
        int orderStatus = bundle.getInt("orderStatus");

        String orderSn = bundle.getString("orderSn");
        String omsOutSn = bundle.getString("omsOutSn");

        String pageTag = bundle.getString("pageTag");
        int itemIndex = bundle.getInt("groupItemIndex");
        if (orderStatus == 0) {//待付款(取消订单)
            new CommomDialog(mContext, "确定取消此订单？", (dialog, confirm) -> {
                if (confirm) {
                    DeclareOrderPuclicRequest.cancelDeclareOrder(mContext, orderSn, () -> {
                        T.showToastyCenter(mContext, "操作成功");
                        if ("全部".equals(pageTag)) {//在全部tab更新该条目为已取消状态
                            notifyItemToCancel(itemIndex);
                        } else {//在待付款tab下则删除该条目
                            notifyRemoveRvListWithAnima(itemIndex);
                        }
                    });
                }
            }).setTitle("取消订单").setNegativeButton("取消").setPositiveButton("确定").show();
        } else if (orderStatus == 2) {//待收货(查看物流)
            Bundle intentBundle = new Bundle();
            intentBundle.putString("omsOutSn", omsOutSn);
            startActivity(LogisticsDetailActivity.class, intentBundle);
        } else if (orderStatus == 3) {//已完成(删除订单)
            new CommomDialog(mContext, "确定删除此订单？", (dialog, confirm) -> {
                //删除订单接口
                if (confirm) {
                    DeclareOrderPuclicRequest.deleteDeclareOrder(mContext, orderSn, () -> {
                        T.showToastyCenter(mContext, "操作成功");
                        notifyRemoveRvListWithAnima(itemIndex);
                    });
                }
            }).setTitle("删除订单").setNegativeButton("取消").setPositiveButton("确定").show();
        } else if (orderStatus == 4) {//已取消(删除订单)
            new CommomDialog(mContext, "确定删除此订单？", (dialog, confirm) -> {
                //删除订单接口
                if (confirm) {
                    DeclareOrderPuclicRequest.deleteDeclareOrder(mContext, orderSn, () -> {
                        T.showToastyCenter(mContext, "操作成功");
                        notifyRemoveRvListWithAnima(itemIndex);
                    });
                }
            }).setTitle("删除订单").setNegativeButton("取消").setPositiveButton("确定").show();
        }
    }

    @Override
    public void rightTvClick(Bundle bundle) {
        int orderStatus = bundle.getInt("orderStatus");
        String orderSonSn = bundle.getString("orderSonSn");
        String orderSn = bundle.getString("orderSn");

        String pageTag = bundle.getString("pageTag");
        int itemIndex = bundle.getInt("groupItemIndex");
        if (orderStatus == 0) {//待付款(去付款)
            // PayOderActivity
            Bundle intentBundle = new Bundle();
            intentBundle.putString("orderSn", orderSn);
            intentBundle.putString("totalAmount", bundle.getString("totalAmount"));
            intentBundle.putInt("groupItemIndex", itemIndex);
            intentBundle.putString("pageTag", pageTag);
            Intent intent = new Intent(mContext, PayOderActivity.class);//跳转到支付页面
            intent.putExtras(intentBundle);
            mContext.startActivity(intent);
        } else if (orderStatus == 1) {//待发货(提醒发货)
            DeclareOrderPuclicRequest.remindDeclareOrder(mContext, orderSonSn, () -> {
                T.showToastyCenter(mContext, "操作成功");
                notifyItemToReminded(bundle.getInt("groupItemIndex"));
            });
        } else if (orderStatus == 2) {//待收货(确认收货)
            Bundle intentBundle = new Bundle();
            intentBundle.putString("orderSonSn", orderSonSn);
            intentBundle.putInt("groupItemIndex", itemIndex);
            intentBundle.putString("pageTag", pageTag);
            //将item转成json 并传递到确认收货界面
            String dataJson = bundle.getString("dataJson");
            intentBundle.putString("dataJson", dataJson);
            Intent intent = new Intent(mContext, ConfirmgoodsActivity.class);//跳转到确认收货页面
            intent.putExtras(intentBundle);
            mContext.startActivity(intent);
        }

    }

    @Override
    public void expandMore(int position) {
        rlvOrderList.scrollToPosition(position);
    }
}
