package com.huadingcloudpackage.www.adapter.orderdeclare;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.chaychan.library.ExpandableLinearLayout;
import com.huadingcloudpackage.www.R;
import com.huadingcloudpackage.www.adapter.CommentAdapter;
import com.huadingcloudpackage.www.bean.DeclareOrderDetailOuterBean.DeclareOrderDetailRespBean;
import com.huadingcloudpackage.www.bean.DeclareOrderOuterBean;
import com.huadingcloudpackage.www.util.DisPlayUtils;
import com.huadingcloudpackage.www.util.SpannableUtils;
import com.huadingcloudpackage.www.widget.NoScrollLinearLayoutManager;

import java.util.List;

/**
 * 文 件 名：DeclareOrderDetailGroupAdapter
 * 描   述：报货订单详情父适配器
 */
public class DeclareOrderDetailGroupAdapter extends CommentAdapter<DeclareOrderDetailRespBean> {

    private Context mContext;

    private static final String TAG = "DeclareOrderDetailGroup";
    public DeclareOrderDetailGroupAdapter(Context context, int layoutResId, @Nullable List<DeclareOrderDetailRespBean> data) {
        super(layoutResId, data);
        this.mContext = context;
    }

    @Override
    public void setViewData(BaseViewHolder holder, DeclareOrderDetailRespBean item, int position) {
        Log.i(TAG,"----------"+position);

        boolean hasHeaderLayout = hasHeaderLayout();
        LinearLayout rootView = holder.getView(R.id.item_order_detail_rv_list_group_root_view);
        int hPadding = DisPlayUtils.dip2px(getContext(), 15);
        int vPadding = DisPlayUtils.dip2px(getContext(), 10);
        if (getData().size() > 1) {
            if (hasHeaderLayout ? position == 1 : position == 0) {
                rootView.setPadding(hPadding, vPadding, hPadding, vPadding);
                rootView.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.shape_order_item_round_corner_only_top_bg));
            } else if (hasHeaderLayout ? position == getData().size() : position == getData().size()- 1 ) {
                rootView.setPadding(hPadding, vPadding, hPadding, vPadding);
                rootView.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.shape_order_item_round_corner_only_middle_bg));
            } else {
                rootView.setPadding(hPadding, vPadding, hPadding, vPadding);
                rootView.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.shape_order_item_round_corner_only_middle_bg));
            }
        } else {
            rootView.setPadding(hPadding, vPadding, hPadding, vPadding);
            rootView.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.shape_order_item_round_corner_only_top_bg));
        }



        holder.setText(R.id.item_order_detail_rv_list_group_tv_name, item.getSplitName());

        int orderStatus = Integer.parseInt(item.getOrderSonStatus());

        RelativeLayout rlAmount = holder.getView(R.id.item_order_detail_rv_list_group_rl_amount);
        RelativeLayout rlFreight = holder.getView(R.id.item_order_detail_rv_list_group_rl_freight);
        RelativeLayout rlRemark = holder.getView(R.id.item_order_detail_rv_list_group_rl_remark);

        if (orderStatus == 0 || orderStatus == 4) {
            holder.setText(R.id.item_order_detail_rv_list_group_tv_amount_type, "应付款");
        } else {
            holder.setText(R.id.item_order_detail_rv_list_group_tv_amount_type, "实付款");
        }
        holder.setText(R.id.item_order_detail_rv_list_group_tv_amount, SpannableUtils.changeSpannableTv("¥"+item.getTotalSonAmount()));
        holder.setText(R.id.item_order_detail_rv_list_group_tv_order_remark, item.getRemark());
        holder.setText(R.id.item_order_detail_rv_list_group_tv_freight, SpannableUtils.changeSpannableTv("¥"+item.getFreightPrice()));

        RecyclerView rvChild = holder.getView(R.id.item_order_detail_rv_list_group_rv_child);
        rvChild.setLayoutManager(new NoScrollLinearLayoutManager(mContext));
        DeclareOrderDetailChildAdapter childAdapter = new DeclareOrderDetailChildAdapter(mContext, R.layout.item_declare_order_detail_rv_list_child, item.getOrderGoodsDetailsRespList());
        rvChild.setAdapter(childAdapter);

    }

    @Override
    public void setEvent(BaseViewHolder holder, DeclareOrderDetailRespBean item, int position) {

    }
}