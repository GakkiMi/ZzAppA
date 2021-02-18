package com.huadingcloudpackage.www.adapter.orderdiff;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.huadingcloudpackage.www.R;
import com.huadingcloudpackage.www.adapter.CommentAdapter;
import com.huadingcloudpackage.www.adapter.viewholder.BaseOrderGoodsViewHolder;
import com.huadingcloudpackage.www.bean.DiffOrderOuterBean.DiffOrderGoodsBean;
import com.huadingcloudpackage.www.util.BigDecimalUtils;
import com.huadingcloudpackage.www.util.SpannableUtils;

import java.util.List;

/**
 * 文 件 名：DiffOrderDetailChildAdapter
 * 描   述：差异单详情子适配器
 */
public class DiffOrderDetailChildAdapter extends CommentAdapter<DiffOrderGoodsBean> {

    private Context mContext;
    private BaseOrderGoodsViewHolder baseOrderGoodsViewHolder;

    public DiffOrderDetailChildAdapter(Context context, int layoutResId, @Nullable List<DiffOrderGoodsBean> data) {
        super(layoutResId, data);
        this.mContext = context;
        baseOrderGoodsViewHolder = new BaseOrderGoodsViewHolder();
    }

    @Override
    public void setViewData(BaseViewHolder holder, DiffOrderGoodsBean item, int position) {
        ImageView ivIcon = holder.getView(R.id.item_diff_order_detail_rv_list_iv_show);
        ImageView ivDiffPayType = holder.getView(R.id.item_diff_order_detail_rv_list_iv_diff_pay_type);
        TextView tvTitle = holder.getView(R.id.item_diff_order_detail_rv_list_tv_goods_name);
        TextView tvPrice = holder.getView(R.id.item_diff_order_detail_rv_list_tv_goods_price);
        TextView tvSpec = holder.getView(R.id.item_diff_order_detail_rv_list_tv_goods_spec);
        TextView tvGoodsCount = holder.getView(R.id.item_diff_order_detail_rv_list_tv_goods_count);

        Glide.with(mContext)
                .load(item.getGoodsImageUrl())
                .placeholder(R.mipmap.icon_loading_fail)
                .into(ivIcon);
        tvTitle.setText(item.getGoodsName());
        tvPrice.setText(SpannableUtils.changeSpannableTv("¥" + item.getGoodsPrice() + "/" + item.getGoodsItem()));
        tvSpec.setText(item.getGoodsBasicsSpecName());


        double diffGoodsNum = Double.valueOf(item.getDifferenceGoodsNum());
        setTvCount(tvGoodsCount, diffGoodsNum);

        if (diffGoodsNum > 0) {//差异数量大于0 为退款状态
            ivDiffPayType.setVisibility(View.VISIBLE);
            ivDiffPayType.setImageResource(R.drawable.img_shouhou_refund);
        } else if (diffGoodsNum < 0) {//差异数量小于0 为付款状态
            ivDiffPayType.setVisibility(View.VISIBLE);
            ivDiffPayType.setImageResource(R.drawable.img_shouhou_payment);
        } else {
            ivDiffPayType.setVisibility(View.GONE);
        }


        baseOrderGoodsViewHolder.showGoodsSpecView(holder, item);
        baseOrderGoodsViewHolder.setTvPriceAttr(15f, ContextCompat.getColor(mContext, R.color.colorPriceColor));
        baseOrderGoodsViewHolder.setTvCountAttr(15f, ContextCompat.getColor(mContext, R.color.colorGrayText));

    }

    private void setTvCount(TextView tv, double count) {
        tv.setText("x" + BigDecimalUtils.removeInvalidZero(Math.abs(count) + ""));
    }

    @Override
    public void setEvent(BaseViewHolder holder, DiffOrderGoodsBean item, int position) {

    }
}