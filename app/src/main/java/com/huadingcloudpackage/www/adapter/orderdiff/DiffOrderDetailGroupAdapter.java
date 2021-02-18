package com.huadingcloudpackage.www.adapter.orderdiff;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.chaychan.library.ExpandableLinearLayout;
import com.huadingcloudpackage.www.R;
import com.huadingcloudpackage.www.adapter.CommentAdapter;
import com.huadingcloudpackage.www.bean.DeclareOrderOuterBean;
import com.huadingcloudpackage.www.bean.DiffOrderDetailOuterBean.DiffOrderDetailDataBean;
import com.huadingcloudpackage.www.bean.DiffOrderOuterBean;
import com.huadingcloudpackage.www.util.DisPlayUtils;
import com.huadingcloudpackage.www.util.SpannableUtils;
import com.huadingcloudpackage.www.widget.NoScrollLinearLayoutManager;

import java.util.List;

/**
 * 文 件 名：DiffOrderDetailGroupAdapter
 * 描   述：差异单详情父适配器
 */
public class DiffOrderDetailGroupAdapter extends CommentAdapter<DiffOrderDetailDataBean> {

    private Context mContext;

    public DiffOrderDetailGroupAdapter(Context context, int layoutResId, @Nullable List<DiffOrderDetailDataBean> data) {
        super(layoutResId, data);
        this.mContext = context;
    }

    @Override
    public void setViewData(BaseViewHolder holder, DiffOrderDetailDataBean item, int position) {
        boolean hasHeaderLayout = hasHeaderLayout();
        LinearLayout rootView = holder.getView(R.id.item_diff_order_detail_rv_list_group_root_view);
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
                rootView.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.shape_order_item_round_corner_only_bottom_bg));
            }
        } else {
            rootView.setPadding(hPadding, vPadding, hPadding, vPadding);
            rootView.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.shape_order_item_round_corner_bg));
        }



        holder.setText(R.id.item_diff_order_detail_rv_list_group_tv_name, item.getName());

        TextView tvDiffPayType = holder.getView(R.id.item_diff_order_detail_rv_list_tv_diff_pay_type);
        TextView tvDiffPrice = holder.getView(R.id.item_diff_order_detail_rv_list_tv_diff_price);

        if (TextUtils.isEmpty(item.getRefund())) {
            tvDiffPrice.setText(SpannableUtils.changeSpannableTv("¥" + item.getPayTotalPrice()));
            tvDiffPayType.setText("待付总金额");
        } else if (TextUtils.isEmpty(item.getPayTotalPrice())) {
            tvDiffPrice.setText(SpannableUtils.changeSpannableTv("¥" + item.getRefund()));
            tvDiffPayType.setText("退款总金额");
        }


        RecyclerView rvChild = holder.getView(R.id.item_diff_order_detail_rv_list_group_rv_child);
        rvChild.setLayoutManager(new NoScrollLinearLayoutManager(mContext));
        DiffOrderDetailChildAdapter childAdapter = new DiffOrderDetailChildAdapter(mContext, R.layout.item_diff_order_detail_rv_list_child, item.getOrderGoodsRespList());
        rvChild.setAdapter(childAdapter);


    }

    @Override
    public void setEvent(BaseViewHolder holder, DiffOrderDetailDataBean item, int position) {

    }
}