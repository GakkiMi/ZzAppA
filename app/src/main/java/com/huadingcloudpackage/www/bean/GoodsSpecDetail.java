package com.huadingcloudpackage.www.bean;

import java.math.BigDecimal;
import java.util.List;

public class GoodsSpecDetail {


    private String msg;
    private int code;
    private List<Item> data;

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public List<Item> getData() {
        return data;
    }

    public void setData(List<Item> data) {
        this.data = data;
    }

    public static class Item {
        private String goodsNum, storeCount, specKey, goodsCount, specName, goodsId, id, isWhole, goodsSpecKey;
        private double shopPrice, goodsSpecPrice;
        private int specRatio;

        public String getIsWhole() {
            return isWhole;
        }

        public void setIsWhole(String isWhole) {
            this.isWhole = isWhole;
        }

        public int getSpecRatio() {
            return specRatio;
        }

        public void setSpecRatio(int specRatio) {
            this.specRatio = specRatio;
        }

        public String getGoodsSpecKey() {
            return goodsSpecKey;
        }

        public void setGoodsSpecKey(String goodsSpecKey) {
            this.goodsSpecKey = goodsSpecKey;
        }

        public double getGoodsSpecPrice() {
            return goodsSpecPrice;
        }

        public void setGoodsSpecPrice(double goodsSpecPrice) {
            this.goodsSpecPrice = goodsSpecPrice;
        }

        public int getGoodsNum() {
            if (goodsNum == null || goodsNum.equals("")) {
                return 0;
            }
            return Integer.parseInt(goodsNum);
        }

        public void setGoodsNum(String goodsNum) {

            this.goodsNum = String.valueOf(Double.valueOf(goodsNum).intValue());
        }

        public double getStoreCount() {
            if (storeCount != null || !storeCount.equals("")) {
                BigDecimal b = new BigDecimal(storeCount);
                double b1 = b.setScale(4, BigDecimal.ROUND_HALF_UP).doubleValue();
                return b1;
            }
            return 0;
        }

        public void setStoreCount(String storeCount) {
            this.storeCount = storeCount;
        }

        public String getSpecKey() {
            return specKey;
        }

        public void setSpecKey(String specKey) {
            this.specKey = specKey;
        }

        public String getGoodsCount() {
            return goodsCount;
        }

        public void setGoodsCount(String goodsCount) {
            this.goodsCount = goodsCount;
        }

        public String getSpecName() {
            return specName;
        }

        public void setSpecName(String specName) {
            this.specName = specName;
        }

        public String getGoodsId() {
            return goodsId;
        }

        public void setGoodsId(String goodsId) {
            this.goodsId = goodsId;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public double getShopPrice() {
            return shopPrice;
        }

        public void setShopPrice(double shopPrice) {
            this.shopPrice = shopPrice;
        }
    }


}
