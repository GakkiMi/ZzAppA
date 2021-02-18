package com.huadingcloudpackage.www.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;

import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.huadingcloudpackage.www.R;

import com.huadingcloudpackage.www.dialog.GoodsNumModifyDialog;
import com.huadingcloudpackage.www.util.T;


/**
 * 文 件 名：CountModifyView
 * 描   述：购物车数量修改的view
 */
public class CountModifyView extends RelativeLayout {


    private int goodsStoreCount;//商品库存
    private int currentCount;//当前数量


    private ImageView ivNumJian;
    private ImageView ivNumJia;
    private TextView tvNumShow;

    GoodsNumModifyDialog goodsNumModifyDialog;

    public CountModifyView(Context context) {
        this(context, null);
    }

    public CountModifyView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CountModifyView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context);
    }

    private void initView(Context context) {
        View viewLayout = LayoutInflater.from(context).inflate(R.layout.view_goods_count_modify, this, true);
        ivNumJian = viewLayout.findViewById(R.id.goods_num_iv_jian);
        ivNumJia = viewLayout.findViewById(R.id.goods_num_iv_jia);
        tvNumShow = viewLayout.findViewById(R.id.goods_num_tv_show);

        ivNumJian.setOnClickListener(view -> {
            int showNum = currentCount;

            if (showNum <= 1) {
                T.showToastyCenter(getContext(), "数量不能小于1");
            } else {
                int modifyGoodsNum = showNum - 1;

                goodsCountModifyImp.modifyCount(modifyGoodsNum);
            }
        });
        ivNumJia.setOnClickListener(view -> {
            int showNum = currentCount;

            if (showNum < goodsStoreCount) {
                int modifyGoodsNum = showNum + 1;

                goodsCountModifyImp.modifyCount(modifyGoodsNum);
            } else {
                T.showToastyCenter(getContext(), "最多只能选" + goodsStoreCount + "件哦~");
            }
        });
        tvNumShow.setOnClickListener(view -> {
            goodsNumModifyDialog = new GoodsNumModifyDialog.Builder(context)
                    .setStroeCount(goodsStoreCount)
                    .setGoodsNum(currentCount)
                    .setPositiveButton("确定", modifyGoodsNum -> {
                        goodsCountModifyImp.modifyCount(modifyGoodsNum);
                        goodsNumModifyDialog.dismiss();
                    }).setNegativeButton("取消", () -> goodsNumModifyDialog.dismiss()).create();
            goodsNumModifyDialog.show();
        });

    }

    public void setTvCount() {
        tvNumShow.setTextColor(getResources().getColor(currentCount > goodsStoreCount ? R.color.black000 : R.color.black000));
        tvNumShow.setText(currentCount + "");
    }


    public void setGoodsStoreCount(int goodsStoreCount) {
        this.goodsStoreCount = goodsStoreCount;
    }

    public void setCurrentCount(int currentCount) {
        this.currentCount = currentCount;
    }

    public GoodsCountModifyImp goodsCountModifyImp;

    public void setGoodsCountModifyImp(GoodsCountModifyImp goodsCountModifyImp) {
        this.goodsCountModifyImp = goodsCountModifyImp;
    }

    public interface GoodsCountModifyImp {
        void modifyCount(int modifyCount);
    }

    public int getCurrentCount() {
        return currentCount;
    }

    public int getGoodsStoreCount() {
        return goodsStoreCount;
    }


}
