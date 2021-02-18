package com.huadingcloudpackage.www.bean;

import java.io.Serializable;
import java.util.List;

/**
 * @version V1.0
 * @类描述： 提交差异单  传参
 * @创建人：李歌
 * @修改人：
 * @修改备注：
 */
public class SubmitChaYiDanBean implements Serializable {

    /**
     * differencesReason : 1
     * refund : 60
     * differencesExplain : 破损了
     * orderSonSn : 2020082413424622701251
     * goodsSnNum : [{"goodsSn":"HDG00000206","receivedNum":"10"},{"goodsSn":"HDG00000205","receivedNum":"15"}]
     * orderSn : 20200824110256874312
     */

    private String differencesReason;
    private int refund;
    private String differencesExplain;
    private String orderSonSn;
    private String orderSn;
    private List<GoodsSnNumBean> goodsSnNum;

    public String getDifferencesReason() {
        return differencesReason;
    }

    public void setDifferencesReason(String differencesReason) {
        this.differencesReason = differencesReason;
    }

    public int getRefund() {
        return refund;
    }

    public void setRefund(int refund) {
        this.refund = refund;
    }

    public String getDifferencesExplain() {
        return differencesExplain;
    }

    public void setDifferencesExplain(String differencesExplain) {
        this.differencesExplain = differencesExplain;
    }

    public String getOrderSonSn() {
        return orderSonSn;
    }

    public void setOrderSonSn(String orderSonSn) {
        this.orderSonSn = orderSonSn;
    }

    public String getOrderSn() {
        return orderSn;
    }

    public void setOrderSn(String orderSn) {
        this.orderSn = orderSn;
    }

    public List<GoodsSnNumBean> getGoodsSnNum() {
        return goodsSnNum;
    }

    public void setGoodsSnNum(List<GoodsSnNumBean> goodsSnNum) {
        this.goodsSnNum = goodsSnNum;
    }

    public static class GoodsSnNumBean implements Serializable{
        /**
         * goodsSn : HDG00000206
         * receivedNum : 10
         * receivedBigNum : 10
         */

        private String goodsSn;
        private String receivedNum;
        private String receivedBigNum;

        public String getGoodsSn() {
            return goodsSn;
        }

        public void setGoodsSn(String goodsSn) {
            this.goodsSn = goodsSn;
        }

        public String getReceivedNum() {
            return receivedNum;
        }

        public void setReceivedNum(String receivedNum) {
            this.receivedNum = receivedNum;
        }

        public String getReceivedBigNum() {
            return receivedBigNum;
        }

        public void setReceivedBigNum(String receivedBigNum) {
            this.receivedBigNum = receivedBigNum;
        }
    }
}
