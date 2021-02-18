package com.huadingcloudpackage.www.adapter.ordersettlement;

import android.content.Context;
import android.graphics.Color;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;

import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.huadingcloudpackage.www.R;
import com.huadingcloudpackage.www.adapter.CommentAdapter;
import com.huadingcloudpackage.www.adapter.viewholder.BaseOrderGoodsViewHolder;
import com.huadingcloudpackage.www.bean.OrderSettlementBean;
import com.huadingcloudpackage.www.util.GlideUtils;

import java.util.List;

/**
 * 文 件 名：OrderSettleChildAdapter
 * 描   述：订单结算子适配器
 */
public class OrderSettleChildAdapter extends CommentAdapter<OrderSettlementBean.OrderSettleGoodsList> {
    private Context mContext;
    private BaseOrderGoodsViewHolder baseOrderGoodsViewHolder;


    public OrderSettleChildAdapter(Context context, int layoutResId, @Nullable List<OrderSettlementBean.OrderSettleGoodsList> data) {
        super(layoutResId, data);
        this.mContext = context;
        baseOrderGoodsViewHolder = new BaseOrderGoodsViewHolder();
    }

    @Override
    public void setViewData(BaseViewHolder holder, OrderSettlementBean.OrderSettleGoodsList item, int position) {
        ImageView ivShow = holder.getView(R.id.item_commit_goods_child_iv_show);//商品展示图
        //加载图片
        GlideUtils.showRoundBorderGildeImg(getContext(), item.getGoodsImg(), ivShow, 0.2f, Color.parseColor("#80000000"), 20, 0);

        TextView tvGoodsName = holder.getView(R.id.item_commit_goods_child_tv_name);//商品名称
        TextView tvGoodsGuige = holder.getView(R.id.item_commit_goods_child_tv_guige);//商品规格


        tvGoodsName.setText(item.getGoodsName());
        tvGoodsGuige.setText(item.getSpecName());

        baseOrderGoodsViewHolder.showGoodsSpecView(holder, item);
        baseOrderGoodsViewHolder.setTvPriceAttr(15f, ContextCompat.getColor(mContext, R.color.colorPriceColor));
        baseOrderGoodsViewHolder.setTvCountAttr(15f, ContextCompat.getColor(mContext, R.color.colorGrayText));
    }

    @Override
    public void setEvent(BaseViewHolder holder, OrderSettlementBean.OrderSettleGoodsList item, int position) {

    }
}
