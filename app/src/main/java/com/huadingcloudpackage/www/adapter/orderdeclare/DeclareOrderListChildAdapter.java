package com.huadingcloudpackage.www.adapter.orderdeclare;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.core.content.ContextCompat;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.huadingcloudpackage.www.R;
import com.huadingcloudpackage.www.adapter.BaseOrderGroupAdapter;
import com.huadingcloudpackage.www.adapter.viewholder.BaseOrderGoodsViewHolder;
import com.huadingcloudpackage.www.bean.DeclareOrderOuterBean.OrderGoodsBean;
import com.huadingcloudpackage.www.util.SpannableUtils;

import java.util.List;

/**
 * 文 件 名：DeclareOrderListChildAdapter
 * 描   述：报货订单列表子适配器
 */
public class DeclareOrderListChildAdapter extends BaseOrderGroupAdapter<OrderGoodsBean> {

    private Context mContext;
    public List mListBeans;
    private BaseOrderGoodsViewHolder baseOrderGoodsViewHolder;

    public DeclareOrderListChildAdapter(Context context, int layoutResId, List data) {
        super(context, layoutResId, data);
        this.mContext = context;
        this.mListBeans = data;
        baseOrderGoodsViewHolder = new BaseOrderGoodsViewHolder();
    }


    @Override
    public void setViewData(BaseViewHolder holder, OrderGoodsBean item, int position) {
        ImageView ivIcon = holder.getView(R.id.item_order_rv_child_common_iv_icon);
        TextView tvTitle = holder.getView(R.id.item_order_rv_child_common_tv_title);
        TextView tvPrice = holder.getView(R.id.item_order_rv_child_common_tv_price);
        TextView tvUnit = holder.getView(R.id.item_order_rv_child_common_tv_unit);
        TextView tvCount = holder.getView(R.id.item_order_rv_child_common_tv_count);
        TextView tvSpec = holder.getView(R.id.item_order_rv_child_common_tv_spec);


        Glide.with(mContext)
                .load(item.getGoodsImageUrl())
                .placeholder(R.mipmap.icon_loading_fail)
                .into(ivIcon);
        tvTitle.setText(item.getGoodsName());
        tvPrice.setText(SpannableUtils.changeSpannableTv("¥" + item.getGoodsPrice() + "/" + item.getGoodsItem()));
        tvSpec.setText(item.getGoodsSpecName());
        tvCount.setText("x" + item.getGoodsNum());



        baseOrderGoodsViewHolder.showGoodsSpecView(holder, item);
        baseOrderGoodsViewHolder.setTvPriceAttr(12f, ContextCompat.getColor(mContext, R.color.black000));
        baseOrderGoodsViewHolder.setTvCountAttr(12f, ContextCompat.getColor(mContext, R.color.colorGrayText));
    }


    @Override
    public void setEvent(BaseViewHolder holder, OrderGoodsBean item, int position) {

    }


}
