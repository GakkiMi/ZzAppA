package com.huadingcloudpackage.www.adapter.orderdeclare;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.huadingcloudpackage.www.R;
import com.huadingcloudpackage.www.activity.order.orderdeclare.DeclareOrderDetailActivity;
import com.huadingcloudpackage.www.adapter.BaseOrderGroupAdapter;
import com.huadingcloudpackage.www.bean.DeclareOrderOuterBean.OrderDataList;
import com.huadingcloudpackage.www.bean.DeclareOrderOuterBean.OrderRespBean;
import com.huadingcloudpackage.www.bean.DeclareOrderOuterBean.OrderGoodsBean;
import com.huadingcloudpackage.www.util.JsonUtils;
import com.huadingcloudpackage.www.util.SpannableUtils;
import com.huadingcloudpackage.www.widget.NoScrollLinearLayoutManager;

import java.util.ArrayList;
import java.util.List;

/**
 * 文 件 名：DeclareOrderListGroupAdapter
 * 描   述：报货订单列表父适配器
 */
public class DeclareOrderListGroupAdapter extends BaseOrderGroupAdapter<OrderDataList> {

    private Context mContext;
    public List<OrderDataList> mListBeans;

    private String mPageTag;

    public DeclareOrderListGroupAdapter(Context context, int layoutResId, List data, String pageTag) {
        super(context, layoutResId, data);
        this.mContext = context;
        this.mListBeans = data;
        this.mPageTag = pageTag;
    }


    @Override
    public void setViewData(BaseViewHolder holder, OrderDataList item, int position) {
        LinearLayout llNormalView = holder.findView(R.id.item_declare_order_rv_group_ll_normal_view);
        RelativeLayout rlErrorView = holder.findView(R.id.item_declare_order_rv_group_rl_error_view);

        llNormalView.setVisibility(View.GONE);
        rlErrorView.setVisibility(View.VISIBLE);

        if (item.getOrderRespList() == null) {
            return;
        }
        if (item.getOrderRespList().size() == 0) {
            return;
        }
        OrderRespBean firstOrderResp = item.getOrderRespList().get(0);
        if (firstOrderResp == null) {
            return;
        }
        llNormalView.setVisibility(View.VISIBLE);
        rlErrorView.setVisibility(View.GONE);
        //0待付款，1待发货，2待收货，3已完成，4已取消
        int orderStatus = Integer.parseInt(firstOrderResp.getOrderSonStatus());
        if (0 == orderStatus || orderStatus == 4) {
            holder.setText(R.id.item_declare_order_rv_group_tv_order_no, "主单单号：" + firstOrderResp.getOrderSn());
            holder.setText(R.id.item_declare_order_rv_group_tv_pay_status, "应付款：");
            holder.setText(R.id.item_declare_order_rv_group_tv_amount, SpannableUtils.changeSpannableTv("¥" + item.getTotalAmount()));
        } else if (1 == orderStatus || orderStatus == 2 || orderStatus == 3) {
            holder.setText(R.id.item_declare_order_rv_group_tv_order_no, "订单编号：" + firstOrderResp.getOrderSonSn());
            holder.setText(R.id.item_declare_order_rv_group_tv_pay_status, "实付款：");
            holder.setText(R.id.item_declare_order_rv_group_tv_amount, SpannableUtils.changeSpannableTv("¥" + firstOrderResp.getTotalSonAmount()));
        } else if (orderStatus == 5) {
            holder.setText(R.id.item_declare_order_rv_group_tv_order_no, "差异单编号：" + firstOrderResp.getOrderSonSn());
            holder.setText(R.id.item_declare_order_rv_group_tv_pay_status, "实付款：");
            holder.setText(R.id.item_declare_order_rv_group_tv_amount, SpannableUtils.changeSpannableTv("¥" + firstOrderResp.getTotalSonAmount()));
        }

        TextView tvShippingPrice = holder.getView(R.id.item_declare_order_rv_group_tv_shipping_price);

        if (1 == orderStatus || orderStatus == 2) {
            tvShippingPrice.setVisibility(View.VISIBLE);
            tvShippingPrice.setText(SpannableUtils.changeSpannableTv("运费¥" + firstOrderResp.getFreightPrice()));
        } else {
            tvShippingPrice.setVisibility(View.GONE);
        }

        TextView tvOrderType = holder.getView(R.id.item_declare_order_rv_group_tv_order_type);
        TextView tvLeft = holder.getView(R.id.item_declare_order_rv_group_tv_click_left);
        TextView tvRight = holder.getView(R.id.item_declare_order_rv_group_tv_click_right);


        if (orderStatus == 0) {
            tvOrderType.setText("待支付");
            setButtonStyle(tvLeft, tvRight, 0, 1, "取消订单", "去付款");
        } else if (orderStatus == 1) {
            tvOrderType.setText("待发货");
            int remindStatus = Integer.parseInt(firstOrderResp.isRemind());
            if (remindStatus == 0) {
                setButtonStyle(tvLeft, tvRight, 2, 1, "", "提醒发货");
            } else {
                setButtonStyle(tvLeft, tvRight, 0, 2, "已提醒", "");
            }
        } else if (orderStatus == 2) {
            tvOrderType.setText("待收货");
            setButtonStyle(tvLeft, tvRight, 0, 1, "查看物流", "确认收货");
        } else if (orderStatus == 3) {
            tvOrderType.setText("已完成");
            setButtonStyle(tvLeft, tvRight, 0, 2, "删除订单", "");
        } else if (orderStatus == 4) {
            tvOrderType.setText("已取消");
            setButtonStyle(tvLeft, tvRight, 0, 2, "删除订单", "");
        } else if (orderStatus == 5) {
            tvOrderType.setText("差异单");
            setButtonStyle(tvLeft, tvRight, 2, 2, "", "");
        } else {
            tvOrderType.setText("");
            setButtonStyle(tvLeft, tvRight, 2, 2, "", "");
        }

        ImageView ivDown = holder.getView(R.id.item_order_list_footer_expand_view_iv);
        int totalGoodsCount = 0;
        for (OrderRespBean orderResp : item.getOrderRespList()) {
            totalGoodsCount += orderResp.getOrderGoodsList().size();
        }

        holder.setText(R.id.item_declare_order_rv_group_tv_goods_count, "共" + totalGoodsCount + "件商品");

        List<OrderGoodsBean> goodsBeanList = new ArrayList<>();

        if (totalGoodsCount > showMinCount) {
            ivDown.setVisibility(View.VISIBLE);
            ivDown.setRotation(0f);//恢复旋转角度，防止复用造成角度显示错误
            jump:for (OrderRespBean orderRespList : item.getOrderRespList()) {
                for (OrderGoodsBean orderGoods : orderRespList.getOrderGoodsList()) {
                    goodsBeanList.add(orderGoods);
                    if(goodsBeanList.size()==showMinCount){
                        break jump;
                    }
                }
            }
        } else {
            ivDown.setVisibility(View.GONE);
            for (OrderRespBean orderRespList : item.getOrderRespList()) {
                for (OrderGoodsBean orderGoods : orderRespList.getOrderGoodsList()) {
                    goodsBeanList.add(orderGoods);
                }
            }
        }


        RecyclerView rvListChild = holder.getView(R.id.item_declare_order_rv_group_rv_list);
        rvListChild.setLayoutManager(new NoScrollLinearLayoutManager(mContext));
        DeclareOrderListChildAdapter childAdapter = new DeclareOrderListChildAdapter(mContext, R.layout.item_declare_order_rv_child_common, goodsBeanList);
        rvListChild.setAdapter(childAdapter);
        childAdapter.setOnItemClickListener((adapter, view, position1) -> {
            if (isFastClick()) return;
            Bundle bundle = new Bundle();
            bundle.putString("orderSn", item.getOrderSn());
            if (orderStatus == 0 || orderStatus == 4) {
            } else if (orderStatus == 1 || orderStatus == 2 || orderStatus == 3) {
                bundle.putString("orderSonSn", firstOrderResp.getOrderSonSn());
            } else {
                return;
            }

            bundle.putInt("groupItemIndex", position);
            bundle.putString("pageTag", mPageTag);
            Intent intent = new Intent(getContext(), DeclareOrderDetailActivity.class);
            intent.putExtras(bundle);
            mContext.startActivity(intent);
        });

        //为自定义的底部设置点击事件
        holder.getView(R.id.item_order_list_footer_expand_view_rl).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean isExpand = !item.isExpand();
                doArrowAnim(isExpand, ivDown);//根据状态箭头旋转
                item.setExpand(isExpand);

                goodsBeanList.clear();
                if (isExpand) {
                    for (OrderRespBean orderRespList : item.getOrderRespList()) {
                        for (OrderGoodsBean orderGoods : orderRespList.getOrderGoodsList()) {
                            goodsBeanList.add(orderGoods);
                        }
                    }
                } else {
                    jump:for (OrderRespBean orderRespList : item.getOrderRespList()) {
                        for (OrderGoodsBean orderGoods : orderRespList.getOrderGoodsList()) {
                            goodsBeanList.add(orderGoods);
                            if(goodsBeanList.size()==showMinCount){
                                break jump;
                            }
                        }
                    }
                }
                childAdapter.notifyDataSetChanged();
                if (orderBottomTvClick != null) {
                    if (!isExpand) {
                        orderBottomTvClick.expandMore(position);
                    }
                }
            }
        });

    }


    @Override
    public void setEvent(BaseViewHolder holder, OrderDataList item, int position) {
        if (item.getOrderRespList() == null) {
            return;
        }
        if (item.getOrderRespList().size() == 0) {
            return;
        }
        OrderRespBean firstOrderResp = item.getOrderRespList().get(0);
        if (firstOrderResp == null) {
            return;
        }

        int orderStatus = Integer.parseInt(firstOrderResp.getOrderSonStatus());

        holder.getView(R.id.item_declare_order_rv_group_tv_click_left).setOnClickListener(v -> {
            if (isFastClick()) return;
            if (orderBottomTvClick != null) {
                Bundle bundle = new Bundle();
                bundle.putInt("orderStatus", orderStatus);
                bundle.putString("orderSn", item.getOrderSn());
                bundle.putString("omsOutSn", firstOrderResp.getOmsOutNo());

                bundle.putInt("groupItemIndex", position);
                bundle.putString("pageTag", mPageTag);
                orderBottomTvClick.leftTvClick(bundle);
            }
        });

        holder.getView(R.id.item_declare_order_rv_group_tv_click_right).setOnClickListener(v -> {
            if (isFastClick()) return;
            if (orderBottomTvClick != null) {
                Bundle bundle = new Bundle();
                bundle.putInt("orderStatus", orderStatus);
                bundle.putString("orderSn", item.getOrderSn());
                bundle.putString("orderSonSn", firstOrderResp.getOrderSonSn());
                bundle.putString("totalAmount", item.getTotalAmount());
                //将item转成json 并传递到确认收货界面

                List<OrderGoodsBean> goodsBeanList = new ArrayList<>();
                for (OrderRespBean orderRespBean : item.getOrderRespList()) {
                    goodsBeanList.addAll(orderRespBean.getOrderGoodsList());
                }
                String dataJson = JsonUtils.toJsonString(goodsBeanList);
                bundle.putString("dataJson", dataJson);

                bundle.putInt("groupItemIndex", position);
                bundle.putString("pageTag", mPageTag);

                orderBottomTvClick.rightTvClick(bundle);
            }
        });

    }


    /**
     * @param tvLeft
     * @param tvRight
     * @param leftStyle  左边按钮样式  0标识灰色  1标识蓝色  2标识隐藏
     * @param rightStyle 右边按钮样式  0标识灰色  1标识蓝色  2标识隐藏
     */
    public void setButtonStyle(TextView tvLeft, TextView tvRight, int leftStyle, int rightStyle, String leftText, String rightText) {
        setBaseButtonStyle(tvLeft,leftStyle,leftText);
        setBaseButtonStyle(tvRight,rightStyle,rightText);
    }


    //订单底部按钮点击接口
    private DeclareOrderBottomTvClickImpl orderBottomTvClick;

    public void setOrderBottomTvClick(DeclareOrderBottomTvClickImpl orderBottomTvClick) {
        this.orderBottomTvClick = orderBottomTvClick;
    }

    public interface DeclareOrderBottomTvClickImpl {
        void leftTvClick(Bundle bundle);

        void rightTvClick(Bundle bundle);

        void expandMore(int position);
    }


}
