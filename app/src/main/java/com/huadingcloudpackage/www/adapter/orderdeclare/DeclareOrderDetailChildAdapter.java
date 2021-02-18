package com.huadingcloudpackage.www.adapter.orderdeclare;

import android.content.Context;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.huadingcloudpackage.www.R;
import com.huadingcloudpackage.www.adapter.CommentAdapter;
import com.huadingcloudpackage.www.adapter.viewholder.BaseOrderGoodsViewHolder;
import com.huadingcloudpackage.www.bean.DeclareOrderOuterBean.OrderGoodsBean;
import com.huadingcloudpackage.www.util.SpannableUtils;

import java.util.List;

/**
 * 文 件 名：DeclareOrderDetailChildAdapter
 * 描   述：报货订单详情子适配器
 */
public class DeclareOrderDetailChildAdapter extends CommentAdapter<OrderGoodsBean> {

    private static final String TAG = "DeclareOrderDetailChild";
    private Context mContext;
    private BaseOrderGoodsViewHolder baseOrderGoodsViewHolder;

    public DeclareOrderDetailChildAdapter(Context context, int layoutResId, @Nullable List<OrderGoodsBean> data) {
        super(layoutResId, data);
        this.mContext = context;
        baseOrderGoodsViewHolder = new BaseOrderGoodsViewHolder();
    }

    @Override
    public void setViewData(BaseViewHolder holder, OrderGoodsBean item, int position) {
        Log.i(TAG, "----------" + position);
        ImageView ivIcon = holder.getView(R.id.item_order_detail_rv_list_iv_show);
        TextView tvTitle = holder.getView(R.id.item_order_detail_rv_list_tv_goods_name);
        TextView tvPrice = holder.getView(R.id.item_order_detail_rv_list_tv_goods_price);
        TextView tvGuige = holder.getView(R.id.item_order_detail_rv_list_tv_goods_spec);
        TextView tvGoodsCount = holder.getView(R.id.item_order_detail_rv_list_tv_goods_count);

        Glide.with(mContext)
                .load(item.getGoodsImageUrl())
                .placeholder(R.mipmap.icon_loading_fail)
                .into(ivIcon);
        tvTitle.setText(item.getGoodsName());
        tvPrice.setText(SpannableUtils.changeSpannableTv("¥" + item.getGoodsPrice()));
        tvGuige.setText(item.getGoodsSpecName());
        tvGoodsCount.setText("x" + item.getGoodsNum());



        baseOrderGoodsViewHolder.showGoodsSpecView(holder, item);
        baseOrderGoodsViewHolder.setTvPriceAttr(15f, ContextCompat.getColor(mContext, R.color.colorPriceColor));
        baseOrderGoodsViewHolder.setTvCountAttr(15f, ContextCompat.getColor(mContext, R.color.colorGrayText));

    }

    @Override
    public void setEvent(BaseViewHolder holder, OrderGoodsBean item, int position) {

    }
}