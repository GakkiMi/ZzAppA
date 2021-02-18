package com.huadingcloudpackage.www.adapter.orderdiff;

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
import com.huadingcloudpackage.www.bean.DiffOrderOuterBean.DiffOrderGoodsBean;
import com.huadingcloudpackage.www.util.BigDecimalUtils;
import com.huadingcloudpackage.www.util.SpannableUtils;

import java.util.List;

/**
 * 文 件 名：DiffOrderListChildAdapter
 * 描   述：差异订单列表子适配器
 */
public class DiffOrderListChildAdapter extends BaseOrderGroupAdapter<DiffOrderGoodsBean> {

    private Context mContext;
    public List mListBeans;
    private BaseOrderGoodsViewHolder baseOrderGoodsViewHolder;

    public DiffOrderListChildAdapter(Context context, int layoutResId, List data) {
        super(context, layoutResId, data);
        this.mContext = context;
        this.mListBeans = data;
        baseOrderGoodsViewHolder = new BaseOrderGoodsViewHolder();
    }


    @Override
    public void setViewData(BaseViewHolder holder, DiffOrderGoodsBean item, int position) {
        ImageView ivIcon = holder.getView(R.id.item_diff_order_rv_child_iv_icon);
        ImageView ivDiffPayType = holder.getView(R.id.item_diff_order_rv_child_iv_diff_pay_type);
        TextView tvTitle = holder.getView(R.id.item_diff_order_rv_child_tv_goods_name);
        TextView tvPrice = holder.getView(R.id.item_diff_order_rv_child_tv_goods_price);
        TextView tvGoodsNum = holder.getView(R.id.item_diff_order_rv_child_tv_goods_num);
        TextView tvSpec = holder.getView(R.id.item_diff_order_rv_child_tv_goods_spec);


        Glide.with(mContext)
                .load(item.getGoodsImageUrl())
                .placeholder(R.mipmap.icon_loading_fail)
                .into(ivIcon);
        tvTitle.setText(item.getGoodsName());
        tvPrice.setText(SpannableUtils.changeSpannableTv("¥" + item.getGoodsPrice() + "/" + item.getGoodsItem()));
        tvSpec.setText(item.getGoodsBasicsSpecName());

        double diffGoodsNum = Double.valueOf(item.getDifferenceGoodsNum());
        setTvCount(tvGoodsNum,diffGoodsNum);

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
        baseOrderGoodsViewHolder.setTvPriceAttr(12f, ContextCompat.getColor(mContext, R.color.colorPriceColor));
        baseOrderGoodsViewHolder.setTvCountAttr(12f, ContextCompat.getColor(mContext, R.color.colorGrayText));


    }


    @Override
    public void setEvent(BaseViewHolder holder, DiffOrderGoodsBean item, int position) {

    }


    private void setTvCount(TextView tv,double count ){
        tv.setText("x" +BigDecimalUtils.removeInvalidZero(Math.abs(count)+""));
    }


}
