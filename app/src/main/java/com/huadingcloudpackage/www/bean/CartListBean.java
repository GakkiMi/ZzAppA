package com.huadingcloudpackage.www.bean;


import java.util.List;

/**
 * {
 * "msg":"操作成功",
 * "code":200,
 * "data":[
 * {
 * "suppliersName":"哈哈",
 * "cartGoods":[
 * {
 * "cartId":"CT2020111100008",
 * "suppliersId":"GY2020110400020",
 * "suppliersName":"哈哈",
 * "goodsNum":10,
 * "goodsName":"眼镜蛇",
 * "goodsImg":"http://121.89.202.8:8001/group1/M00/00/1A/rBp6pV-uTuWANqs5AAn49fEIKto772_200x150.png",
 * "specId":"GG2020111300043",
 * "specKey":"个",
 * "storeCount":586,
 * "specName":"1个",
 * "goodsPrice":2,
 * "isSelect":1,
 * "goodsId":"SP2020111300001"
 * }
 * ]
 * }
 * <p>
 * ]
 * }
 */


public class CartListBean {
    private List<Item> data;

    public static class Item {
        private String suppliersName;
        private List<CartGoods> cartGoods;

        public String getSuppliersName() {
            return suppliersName;
        }

        public void setSuppliersName(String suppliersName) {
            this.suppliersName = suppliersName;
        }

        public List<CartGoods> getCartGoods() {
            return cartGoods;
        }

        public void setCartGoods(List<CartGoods> cartGoods) {
            this.cartGoods = cartGoods;
        }

        public static class CartGoods {

            private String cartId;
            private String suppliersId;
            private String suppliersName;
            private int goodsNum;
            private String goodsName;
            private String goodsImg;
            private String specId;
            private String specKey;
            private double storeCount;
            private String isWhole;
            private String specName;
            private double goodsPrice;
            private int isSelect;
            private String goodsId;
            private int specRatio;
            private String goodsBasicsSpecKey;
            private String goodsBasicsSpecPrice;

            public int getSpecRatio() {
                return specRatio;
            }

            public void setSpecRatio(int specRatio) {
                this.specRatio = specRatio;
            }

            public String getGoodsBasicsSpecKey() {
                return goodsBasicsSpecKey;
            }

            public void setGoodsBasicsSpecKey(String goodsBasicsSpecKey) {
                this.goodsBasicsSpecKey = goodsBasicsSpecKey;
            }

            public String getGoodsBasicsSpecPrice() {
                return goodsBasicsSpecPrice;
            }

            public void setGoodsBasicsSpecPrice(String goodsBasicsSpecPrice) {
                this.goodsBasicsSpecPrice = goodsBasicsSpecPrice;
            }

            public String getIsWhole() {
                return isWhole;
            }

            public void setIsWhole(String isWhole) {
                this.isWhole = isWhole;
            }

            public String getCartId() {
                return cartId;
            }

            public void setCartId(String cartId) {
                this.cartId = cartId;
            }

            public String getSuppliersId() {
                return suppliersId;
            }

            public void setSuppliersId(String suppliersId) {
                this.suppliersId = suppliersId;
            }

            public String getSuppliersName() {
                return suppliersName;
            }

            public void setSuppliersName(String suppliersName) {
                this.suppliersName = suppliersName;
            }

            public int getGoodsNum() {
                return goodsNum;
            }

            public void setGoodsNum(int goodsNum) {
                this.goodsNum = goodsNum;
            }

            public String getGoodsName() {
                return goodsName;
            }

            public void setGoodsName(String goodsName) {
                this.goodsName = goodsName;
            }

            public String getGoodsImg() {
                return goodsImg;
            }

            public void setGoodsImg(String goodsImg) {
                this.goodsImg = goodsImg;
            }

            public String getSpecId() {
                return specId;
            }

            public void setSpecId(String specId) {
                this.specId = specId;
            }

            public String getSpecKey() {
                return specKey;
            }

            public void setSpecKey(String specKey) {
                this.specKey = specKey;
            }

            public double getStoreCount() {
                return storeCount;
            }

            public void setStoreCount(double storeCount) {
                this.storeCount = storeCount;
            }

            public String getSpecName() {
                return specName;
            }

            public void setSpecName(String specName) {
                this.specName = specName;
            }

            public double getGoodsPrice() {
                return goodsPrice;
            }

            public void setGoodsPrice(double goodsPrice) {
                this.goodsPrice = goodsPrice;
            }

            public int getIsSelect() {
                return isSelect;
            }

            public void setIsSelect(int isSelect) {
                this.isSelect = isSelect;
            }

            public String getGoodsId() {
                return goodsId;
            }

            public void setGoodsId(String goodsId) {
                this.goodsId = goodsId;
            }
        }

    }

    public List<Item> getData() {
        return data;
    }

    public void setData(List<Item> data) {
        this.data = data;
    }
}
