package com.huadingcloudpackage.www.adapter.viewholder;

import android.util.TypedValue;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.huadingcloudpackage.www.R;
import com.huadingcloudpackage.www.bean.DeclareOrderOuterBean;
import com.huadingcloudpackage.www.bean.DiffOrderOuterBean;
import com.huadingcloudpackage.www.bean.OrderSettlementBean;
import com.huadingcloudpackage.www.util.BigDecimalUtils;
import com.huadingcloudpackage.www.util.SpannableUtils;

/**
 * 文 件 名：BaseOrderGoodsViewHolder
 * 描   述：订单商品多规基础设置显示
 */
public class BaseOrderGoodsViewHolder<T> {

    TextView tvBigUnitPrice;
    TextView tvBigUnitCount;
    TextView tvSmallUnitPrice;
    TextView tvSmallUnitCount;

    public void showGoodsSpecView(BaseViewHolder holder, T item) {

        tvBigUnitPrice = holder.getView(R.id.goods_multiple_spec_tv_unit_big_price);//大单位商品价格
        tvBigUnitCount = holder.getView(R.id.goods_multiple_spec_tv_unit_big_count);//大单位商品数量
        tvSmallUnitPrice = holder.getView(R.id.goods_multiple_spec_tv_unit_small_price);//小单位商品价格
        tvSmallUnitCount = holder.getView(R.id.goods_multiple_spec_tv_unit_small_count);//小单位商品数量

        RelativeLayout rlBigUnit = holder.getView(R.id.goods_multiple_spec_unit_big_rl);//大单位布局
        RelativeLayout rlSmallUnit = holder.getView(R.id.goods_multiple_spec_unit_small_rl);//小单位布局

        rlBigUnit.setVisibility(View.GONE);
        rlSmallUnit.setVisibility(View.GONE);

        int isWhole = 0;//是否拆零标志
        int conversion;//大小单位转换系数
        double goodsNum;//商品原始数量

        double bigUnitCount = 0;//转换后大单位商品数量
        double smallUnitCount = 0;//转换后小单位商品数量

        String goodsBaseSpecPrice = "", goodsBaseSpecUnit = "";//基础规格商品价格和单位
        String goodsPrice = "", goodsUnit = "";//商品的价格和单位


        if (item instanceof DeclareOrderOuterBean.OrderGoodsBean) {//报货订单
            DeclareOrderOuterBean.OrderGoodsBean orderGoodsBean = (DeclareOrderOuterBean.OrderGoodsBean) item;
            isWhole = Integer.parseInt(orderGoodsBean.isWhole());
            conversion = Integer.parseInt(orderGoodsBean.getItemConversion());
            goodsNum = Integer.parseInt(orderGoodsBean.getGoodsNum());

            goodsBaseSpecPrice = orderGoodsBean.getGoodsBasicsSpecPrice();
            goodsBaseSpecUnit = orderGoodsBean.getGoodsBasicsSpecKey();
            goodsPrice = orderGoodsBean.getGoodsPrice();
            goodsUnit = orderGoodsBean.getGoodsItem();

            bigUnitCount = (int) (goodsNum / conversion);//取模
            smallUnitCount = (int) (goodsNum % conversion);//取余
        } else if (item instanceof DiffOrderOuterBean.DiffOrderGoodsBean) {//差异订单
            DiffOrderOuterBean.DiffOrderGoodsBean diffOrderGoodsBean = (DiffOrderOuterBean.DiffOrderGoodsBean) item;
            isWhole = Integer.parseInt(diffOrderGoodsBean.isWhole());
            conversion = Integer.parseInt(diffOrderGoodsBean.getItemConversion());
            goodsNum = Math.abs(Double.valueOf(diffOrderGoodsBean.getDifferenceGoodsNum()));

            goodsBaseSpecPrice = diffOrderGoodsBean.getGoodsBasicsSpecPrice();
            goodsBaseSpecUnit = diffOrderGoodsBean.getGoodsBasicsSpecKey();
            goodsPrice = diffOrderGoodsBean.getGoodsPrice();
            goodsUnit = diffOrderGoodsBean.getGoodsItem();

            if (isWhole == 0) {//去拆零
                bigUnitCount = (int) (goodsNum / conversion);//取模
                smallUnitCount = BigDecimalUtils.sub(goodsNum, (bigUnitCount * conversion));//取余
            } else {//不拆零
                try {
                    bigUnitCount = BigDecimalUtils.div(goodsNum, conversion, 2);
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        } else if (item instanceof OrderSettlementBean.OrderSettleGoodsList) {//订单结算
            OrderSettlementBean.OrderSettleGoodsList OrderSettleGoods = (OrderSettlementBean.OrderSettleGoodsList) item;
            isWhole = Integer.parseInt(OrderSettleGoods.isWhole());
            conversion = Integer.parseInt(OrderSettleGoods.getSpecRatio());
            goodsNum = Integer.parseInt(OrderSettleGoods.getGoodsNum());

            goodsBaseSpecPrice = OrderSettleGoods.getGoodsBasicsSpecPrice();
            goodsBaseSpecUnit = OrderSettleGoods.getGoodsBasicsSpecKey();
            goodsPrice = OrderSettleGoods.getGoodsPrice();
            goodsUnit = OrderSettleGoods.getSpecKey();

            bigUnitCount = (int) (goodsNum / conversion);//取模
            smallUnitCount = (int) (goodsNum % conversion);//取余
        }
        if (isWhole == 0) {//去拆零
            if (bigUnitCount > 0) {//大小单位都有
                rlBigUnit.setVisibility(View.VISIBLE);
                tvBigUnitPrice.setText(SpannableUtils.changeSpannableTv("¥" + goodsPrice + "/" + goodsUnit));
                setTvCount(tvBigUnitCount, bigUnitCount);
                if (smallUnitCount > 0) {
                    rlSmallUnit.setVisibility(View.VISIBLE);
                    tvSmallUnitPrice.setText(SpannableUtils.changeSpannableTv("¥" + goodsBaseSpecPrice + "/" + goodsBaseSpecUnit));
                    setTvCount(tvSmallUnitCount, smallUnitCount);
                }
            } else {//只有小单位
                rlSmallUnit.setVisibility(View.VISIBLE);
                tvSmallUnitPrice.setText(SpannableUtils.changeSpannableTv("¥" + goodsBaseSpecPrice + "/" + goodsBaseSpecUnit));
                setTvCount(tvSmallUnitCount, smallUnitCount);
            }
        } else {//不拆零
            rlSmallUnit.setVisibility(View.VISIBLE);
            tvSmallUnitPrice.setText(SpannableUtils.changeSpannableTv("¥" + goodsPrice + "/" + goodsUnit));
            setTvCount(tvSmallUnitCount, bigUnitCount);
        }

    }

    //设置价格textview的属性
    public void setTvPriceAttr(float textSize, int textColor) {
        tvBigUnitPrice.setTextSize(TypedValue.COMPLEX_UNIT_SP, textSize);
        tvBigUnitPrice.setTextColor(textColor);
        tvSmallUnitPrice.setTextSize(TypedValue.COMPLEX_UNIT_SP, textSize);
        tvSmallUnitPrice.setTextColor(textColor);
    }

    //设置数量textview的属性
    public void setTvCountAttr(float textSize, int textColor) {
        tvBigUnitCount.setTextSize(TypedValue.COMPLEX_UNIT_SP, textSize);
        tvBigUnitCount.setTextColor(textColor);
        tvSmallUnitCount.setTextSize(TypedValue.COMPLEX_UNIT_SP, textSize);
        tvSmallUnitCount.setTextColor(textColor);
    }

    private void setTvCount(TextView tv, double count) {
        tv.setText("x" + BigDecimalUtils.removeInvalidZero(Math.abs(count) + ""));
    }

}
