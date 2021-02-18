package com.huadingcloudpackage.www.adapter.orderdiff;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.huadingcloudpackage.www.R;
import com.huadingcloudpackage.www.activity.order.orderdiff.DiffOrderDetailActivity;
import com.huadingcloudpackage.www.adapter.BaseOrderGroupAdapter;
import com.huadingcloudpackage.www.bean.DiffOrderOuterBean.DiffOrderDataList;
import com.huadingcloudpackage.www.bean.DiffOrderOuterBean.DiffOrderGoodsBean;
import com.huadingcloudpackage.www.util.SpannableUtils;
import com.huadingcloudpackage.www.widget.NoScrollLinearLayoutManager;

import java.util.ArrayList;
import java.util.List;

/**
 * 文 件 名：DiffOrderListGroupAdapter
 * 描   述：差异订单列表父适配器
 */
public class DiffOrderListGroupAdapter extends BaseOrderGroupAdapter<DiffOrderDataList> {

    private Context mContext;
    private String mPageTag;

    public DiffOrderListGroupAdapter(Context context, int layoutResId, @Nullable List<DiffOrderDataList> data, String pageTag) {
        super(context, layoutResId, data);
        this.mContext = context;
        this.mPageTag = pageTag;
    }

    @Override
    public int getDefItemCount() {
        return super.getDefItemCount();
    }

    @Override
    public void setViewData(BaseViewHolder holder, DiffOrderDataList item, int position) {

        int orderStatus = Integer.parseInt(item.getOrderState());

        holder.setText(R.id.item_diff_order_rv_group_tv_diff_order_no, "差异单编号：" + item.getDifferenceSn());

        //差异单状态
        TextView tvDiffOrderType = holder.getView(R.id.item_diff_order_rv_group_tv_order_type);
        TextView tvBtLeft = holder.getView(R.id.item_diff_order_rv_group_tv_left_click);
        TextView tvBtRight = holder.getView(R.id.item_diff_order_rv_group_tv_right_click);
        if (orderStatus == 1) {
            tvDiffOrderType.setText("待审核");
            setButtonStyle(tvBtLeft, tvBtRight, 0, 2,"取消申请","");
        } else if (orderStatus == 2) {
            tvDiffOrderType.setText("已完成");
            setButtonStyle(tvBtLeft, tvBtRight, 0, 2,"删除订单","");
        } else if (orderStatus == 3) {
            tvDiffOrderType.setText("已拒绝");
            setButtonStyle(tvBtLeft, tvBtRight, 0, 2,"删除订单","");
        } else if (orderStatus == 4) {
            tvDiffOrderType.setText("已取消");
            setButtonStyle(tvBtLeft, tvBtRight, 0, 2,"删除订单","");
        } else if (orderStatus == 5) {
            tvDiffOrderType.setText("待结算");
            setButtonStyle(tvBtLeft, tvBtRight, 2, 1,"","去付款");
        } else {
            tvDiffOrderType.setText("");
            setButtonStyle(tvBtLeft, tvBtRight, 2, 2,"","");
        }

        if (TextUtils.isEmpty(item.getRefund())) {
            holder.setText(R.id.item_diff_order_rv_group_tv_diff_price, SpannableUtils.changeSpannableTv("¥" + item.getPayTotalPrice()));
            holder.setText(R.id.item_diff_order_rv_group_tv_payment_status, "待付：");
        } else if (TextUtils.isEmpty(item.getPayTotalPrice())) {
            holder.setText(R.id.item_diff_order_rv_group_tv_diff_price, SpannableUtils.changeSpannableTv("¥" + item.getRefund()));
            holder.setText(R.id.item_diff_order_rv_group_tv_payment_status, "退款：");
        }


        ImageView ivDown = holder.getView(R.id.item_order_list_footer_expand_view_iv);
        int totalGoodsCount = item.getList().size();

        holder.setText(R.id.item_diff_order_rv_group_tv_goods_count, "共" + totalGoodsCount + "件商品");

        List<DiffOrderGoodsBean> goodsBeanList = new ArrayList<>();

        if (totalGoodsCount > showMinCount) {
            ivDown.setVisibility(View.VISIBLE);
            ivDown.setRotation(0f);//恢复旋转角度，防止复用造成角度显示错误
            for (DiffOrderGoodsBean orderGoods : item.getList()) {
                goodsBeanList.add(orderGoods);
                if (goodsBeanList.size() == showMinCount) {
                    break;
                }
            }
        } else {
            ivDown.setVisibility(View.GONE);
            goodsBeanList.addAll(item.getList());
        }


        RecyclerView rvListChild = holder.getView(R.id.item_diff_order_rv_group_rv_child);
        rvListChild.setLayoutManager(new NoScrollLinearLayoutManager(mContext));
        DiffOrderListChildAdapter childAdapter = new DiffOrderListChildAdapter(mContext, R.layout.item_diff_order_rv_child_common, goodsBeanList);
        rvListChild.setAdapter(childAdapter);
        childAdapter.setOnItemClickListener((adapter, view, position1) -> {
            if (isFastClick()) return;
            Bundle bundle = new Bundle();
            bundle.putString("diffSn", item.getDifferenceSn() + "");
            bundle.putString("pageTag", mPageTag);
            bundle.putInt("groupItemIndex", position);
            Intent intent = new Intent(getContext(), DiffOrderDetailActivity.class);
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
                    goodsBeanList.addAll(item.getList());
                } else {
                    for (DiffOrderGoodsBean orderGoods : item.getList()) {
                        goodsBeanList.add(orderGoods);
                        if (goodsBeanList.size() == showMinCount) {
                            break;
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
    public void setEvent(BaseViewHolder holder, DiffOrderDataList item, int position) {
        int orderStatus = Integer.parseInt(item.getOrderState());

        holder.getView(R.id.item_diff_order_rv_group_tv_left_click).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isFastClick()) return;
                if (orderBottomTvClick != null) {
                    Bundle bundle = new Bundle();
                    bundle.putInt("orderStatus", orderStatus);
                    bundle.putString("diffSn", item.getDifferenceSn());

                    bundle.putInt("groupItemIndex", position);
                    bundle.putString("pageTag", mPageTag);
                    orderBottomTvClick.leftTvClick(bundle);
                }
            }
        });
        holder.getView(R.id.item_diff_order_rv_group_tv_right_click).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isFastClick()) return;
                if (orderBottomTvClick != null) {
                    Bundle bundle = new Bundle();
                    bundle.putInt("orderStatus", orderStatus);
                    bundle.putString("totalAmount", item.getPayTotalPrice());
                    bundle.putString("orderSonSn", item.getOrderSonSn());
                    bundle.putString("differenceSn", item.getDifferenceSn());


                    bundle.putInt("groupItemIndex", position);
                    bundle.putString("pageTag", mPageTag);
                    orderBottomTvClick.rightTvClick(bundle);
                }
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
    private DiffOrderBottomTvClickImpl orderBottomTvClick;

    public void setOrderBottomTvClick(DiffOrderBottomTvClickImpl orderBottomTvClick) {
        this.orderBottomTvClick = orderBottomTvClick;
    }

    public interface DiffOrderBottomTvClickImpl {
        void leftTvClick(Bundle bundle);

        void rightTvClick(Bundle bundle);

        void expandMore(int position);
    }

}
