package com.huadingcloudpackage.www.activity.order.orderdiff;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.guoquan.yunpu.util.ComTools;
import com.huadingcloudpackage.www.R;
import com.huadingcloudpackage.www.activity.order.ordercommit.PayOderActivity;
import com.huadingcloudpackage.www.adapter.itemdecoration.RvSpaceItemDecoration;
import com.huadingcloudpackage.www.adapter.orderdiff.DiffOrderListGroupAdapter;
import com.huadingcloudpackage.www.base.BaseActivity;
import com.huadingcloudpackage.www.bean.BaseBean;
import com.huadingcloudpackage.www.bean.DiffOrderOuterBean;
import com.huadingcloudpackage.www.bean.DiffOrderOuterBean.DiffOrderDataList;
import com.huadingcloudpackage.www.dialog.CommomDialog;
import com.huadingcloudpackage.www.eventbus.DiffOrderDetailEvent;
import com.huadingcloudpackage.www.http.UrlContent;
import com.huadingcloudpackage.www.util.JsonUtils;
import com.huadingcloudpackage.www.util.StatusBarUtils;
import com.huadingcloudpackage.www.util.T;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;
import com.scwang.smart.refresh.layout.SmartRefreshLayout;
import com.scwang.smart.refresh.layout.api.RefreshLayout;
import com.scwang.smart.refresh.layout.listener.OnRefreshLoadMoreListener;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import okhttp3.RequestBody;

/**
 * 售后差异单
 */
public class DiffOrderListActivity extends BaseActivity implements DiffOrderListGroupAdapter.DiffOrderBottomTvClickImpl {

    @BindView(R.id.diff_order_list_rv_list)
    RecyclerView recycleView;
    @BindView(R.id.diff_order_list_srl)
    SmartRefreshLayout smartrefreshlayout;

    private DiffOrderListGroupAdapter diffOrderListGroupAdapter;
    public List<DiffOrderDataList> mDatas = new ArrayList<>();


    private int mPage = 1;
    private int totalPage;

    private static final String TAG = "DiffOrderListActivity";

    private View rvEmptyView;
    @Override
    public int getLayoutResId() {
        return R.layout.activity_diff_order_list;
    }


    @Override
    public void initView(Bundle savedInstanceState) {
        StatusBarUtils.changStatusIconCollor(this, false);

        Intent intent = getIntent();

        recycleView.setLayoutManager(new LinearLayoutManager(DiffOrderListActivity.this));
        RvSpaceItemDecoration rvSpaceItemDecoration = new RvSpaceItemDecoration(this, 15);
        recycleView.addItemDecoration(rvSpaceItemDecoration);
        diffOrderListGroupAdapter = new DiffOrderListGroupAdapter(this, R.layout.item_diff_order_rv_group, mDatas, "售后");
        diffOrderListGroupAdapter.setOrderBottomTvClick(this);
        recycleView.setAdapter(diffOrderListGroupAdapter);

        rvEmptyView=diffOrderListGroupAdapter.getRvEmptyView();

        smartrefreshlayout.setOnRefreshLoadMoreListener(new OnRefreshLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                if (mPage <= totalPage) {
                    getDiffOrderList();
                } else {
                    smartrefreshlayout.finishLoadMoreWithNoMoreData();
                }
            }

            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                mPage = 1;
                smartrefreshlayout.finishRefresh(1000);
                getDiffOrderList();
            }
        });
        getDiffOrderList();
    }

    @OnClick({R.id.diff_order_list_iv_return})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.diff_order_list_iv_return:
                finish();
                break;
        }
    }

    private void setRvEmptyView() {
        if (diffOrderListGroupAdapter.hasEmptyView()) return;
        diffOrderListGroupAdapter.setEmptyView(rvEmptyView);
    }


    /**
     * 差异单列表
     */
    private void getDiffOrderList() {
        showLoading();
        HashMap<String, Object> params = new HashMap<>();
        params.put("pageNum", mPage);
        params.put("pageSize", 10);
        RequestBody requestBody = RequestBody.create(UrlContent.JSON, JsonUtils.toJsonString(params));
        OkGo.<String>post(UrlContent.DIFF_ORDER_LIST)
                .tag(this)
                .upRequestBody(requestBody)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        BaseBean baseBean = JsonUtils.parse(response.body(), BaseBean.class);
                        comTools.parsingReturnData(baseBean, new ComTools.OnParsingReturnListener() {
                            @Override
                            public void onParsingSuccess() {
                                setRvEmptyView();
                                DiffOrderOuterBean diffOrderOuterBean = JsonUtils.parse(response.body(), DiffOrderOuterBean.class);

                                if (mPage == 1) {
                                    mDatas.clear();
                                }
                                if (diffOrderOuterBean.getData() == null) {
                                    diffOrderListGroupAdapter.notifyDataSetChanged();
                                    return;
                                }
                                totalPage = Integer.parseInt(diffOrderOuterBean.getData().getPages());

//                                for (DiffOrderDataList diffOrderData : diffOrderOuterBean.getData().getList()) {
//                                    List<DiffOrderOuterBean.DiffOrderGoodsBean> list = diffOrderData.getList();
//                                    for (int i = 0; i < 50; i++) {
//                                        list.add(list.get(0));
//                                    }
//                                }


                                mPage++;
                                mDatas.addAll(diffOrderOuterBean.getData().getList());
                                diffOrderListGroupAdapter.notifyDataSetChanged();
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
        diffOrderListGroupAdapter.notifyDataSetChanged();
    }

    /**
     * 删除条目(有动画)
     *
     * @param itemIndex 要更新的条目索引
     */
    public void notifyRemoveRvListWithAnima(int itemIndex) {
        mDatas.remove(itemIndex);
        diffOrderListGroupAdapter.notifyItemRemoved(itemIndex);
        diffOrderListGroupAdapter.notifyItemRangeChanged(itemIndex, mDatas.size() - itemIndex);
    }

    /**
     * 刷新条目变成取消状态
     */
    public void notifyItemToCancel(int itemIndex) {
        mDatas.get(itemIndex).setOrderState("4");
        diffOrderListGroupAdapter.notifyDataSetChanged();
    }

    /**
     * 刷新条目变成完成状态
     */
    public void notifyItemToCompleted(int itemIndex) {
        mDatas.get(itemIndex).setOrderState("2");
        diffOrderListGroupAdapter.notifyDataSetChanged();
    }


    @Override
    public void leftTvClick(Bundle bundle) {
        int orderStatus = bundle.getInt("orderStatus");
        int itemIndex = bundle.getInt("groupItemIndex");

        String diffSn = bundle.getString("diffSn");

        if (orderStatus == 1 || orderStatus == 5) {//1待审核（取消申请） //5待结算（取消订单）
            String content = orderStatus == 1 ? "确定取消申请？" : "确定取消订单？";
            String title = orderStatus == 1 ? "取消申请" : "取消订单";
            new CommomDialog(mContext, content, new CommomDialog.OnCloseListener() {
                @Override
                public void onClick(Dialog dialog, boolean confirm) {
                    if (confirm) {
                        DiffOrderPublicRequest.cancelDiffOrder(mContext, diffSn, () -> {
                            T.showToastyCenter(mContext, "操作成功");
                            notifyItemToCancel(itemIndex);
                        });
                    }
                }
            }).setTitle(title).setNegativeButton("取消").setPositiveButton("确定").show();
        } else if (orderStatus == 2 || orderStatus == 3 || orderStatus == 4) { //2已完成 3已拒绝 4已取消
            new CommomDialog(mContext, "确定删除此订单？", new CommomDialog.OnCloseListener() {
                @Override
                public void onClick(Dialog dialog, boolean confirm) {
                    if (confirm) {
                        DiffOrderPublicRequest.deleteDiffOrder(mContext, diffSn, () -> {
                            T.showToastyCenter(mContext, "操作成功");
                            notifyRemoveRvListWithAnima(itemIndex);
                        });
                    }
                }
            }).setTitle("删除订单").setNegativeButton("取消").setPositiveButton("确定").show();
        }
    }

    @Override
    public void rightTvClick(Bundle bundle) {
        int orderStatus = bundle.getInt("orderStatus");
        if (orderStatus == 5) {//5待结算(去付款)
            Bundle intentBundle = new Bundle();
            intentBundle.putString("orderSonSn", bundle.getString("orderSonSn"));
            intentBundle.putString("totalAmount", bundle.getString("totalAmount"));
            intentBundle.putString("differenceSn", bundle.getString("differenceSn"));

            intentBundle.putInt("groupItemIndex", bundle.getInt("groupItemIndex"));
            intentBundle.putString("pageTag", bundle.getString("pageTag"));

            Intent intent = new Intent(this, PayOderActivity.class);
            intent.putExtras(intentBundle);
            mContext.startActivity(intent);//跳转到订单结算页面
        }
    }

    @Override
    public void expandMore(int position) {
        recycleView.scrollToPosition(position);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void itemUpdate(DiffOrderDetailEvent event) {
        int tag = event.getTag();
        Log.i(TAG, "----------event:" + JsonUtils.toJsonString(event));
        if (tag == 1) {//1 待审核(取消申请)
            HashMap<String, Object> map = (HashMap<String, Object>) event.getObject();
            String doTag = (String) map.get("doTag");
            int itemIndex = (int) map.get("groupItemIndex");
            if (itemIndex == -1) {
                return;
            }
            if (doTag.equals("DiffOrderCancel")) {
                notifyItemToCancel(itemIndex);
            }
        } else if (tag == 2 || tag == 3 || tag == 4) {//2.已完成、3.已拒绝 4.已取消  (删除订单)
            HashMap<String, Object> map = (HashMap<String, Object>) event.getObject();
            String doTag = (String) map.get("doTag");
            int itemIndex = (int) map.get("groupItemIndex");
            if (itemIndex == -1) {
                return;
            }
            if (doTag.equals("DiffOrderDelete")) {
                notifyRemoveRvListWithAnima(itemIndex);
            }
        } else if (tag == 5) {// 5 待付款(取消订单|去支付)
            HashMap<String, Object> map = (HashMap<String, Object>) event.getObject();
            String doTag = (String) map.get("doTag");
            int itemIndex = (int) map.get("groupItemIndex");
            if (itemIndex == -1) {
                return;
            }
            if (doTag.equals("DiffOrderCancel")) {
                notifyItemToCancel(itemIndex);
            } else if (doTag.equals("DiffOrderPaySuccess")) {
                notifyItemToCompleted(itemIndex);
            }
        }
    }


}
