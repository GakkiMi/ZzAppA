package com.huadingcloudpackage.www.bean;

import java.util.List;

public class SearchResultBean {

    /**
     * msg : 操作成功
     * code : 200
     * totalSize : 1
     * totalPages : 1
     * list : [{"goodsId":63,"goodsSn":null,"itemId":60,"goodsName":"扣扣","price":"15.00","unitName":"件","isCount":1,"goodsNum":null,"thumbnailImageUrl":"http://47.92.11.135:8001/group1/M00/00/6E/wKgAtV8tBCmAPXC1AAGd9ydqlUA511_200x150.jpg","isHot":0,"isNew":0,"isRecommend":0,"promStatus":"0","promPrice":null,"addTime":null,"sku":null,"exgoodsItem":null,"isPrice":null,"shopPrice":15}]
     * pageNum : 1
     */

    private String msg;
    private int code;
    private Data data;

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }

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

    public static class Data {
        private List<Item> list;
        private int total;

        public List<Item> getList() {
            return list;
        }

        public void setList(List<Item> list) {
            this.list = list;
        }

        public int getTotal() {
            return total;
        }

        public void setTotal(int total) {
            this.total = total;
        }
    }

    public static class Item {
        private String id;
        private String goodsName;
        private String isWhole;
        private String img;
        private String specId;
        private int specRatio;
        private String specKey;
        private double shopPrice;
        private String specNum;
        private int carCount;
        private int goodsNum;
        private boolean specFlag;
        private String exhibitionId;
        private String onTime;
        private double storeCount;

        public int getSpecRatio() {
            return specRatio;
        }

        public void setSpecRatio(int specRatio) {
            this.specRatio = specRatio;
        }

        public int getCarCount() {
            return carCount;
        }

        public void setCarCount(int carCount) {
            this.carCount = carCount;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getGoodsName() {
            return goodsName;
        }

        public void setGoodsName(String goodsName) {
            this.goodsName = goodsName;
        }

        public String getIsWhole() {
            return isWhole;
        }

        public void setIsWhole(String isWhole) {
            this.isWhole = isWhole;
        }

        public String getImg() {
            return img;
        }

        public void setImg(String img) {
            this.img = img;
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

        public double getShopPrice() {
            return shopPrice;
        }

        public void setShopPrice(double shopPrice) {
            this.shopPrice = shopPrice;
        }

        public String getSpecNum() {
            return specNum;
        }

        public void setSpecNum(String specNum) {
            this.specNum = specNum;
        }

        public int getGoodsNum() {
            return goodsNum;
        }

        public void setGoodsNum(int goodsNum) {
            this.goodsNum = goodsNum;
        }

        public boolean isSpecFlag() {
            return specFlag;
        }

        public void setSpecFlag(boolean specFlag) {
            this.specFlag = specFlag;
        }

        public String getExhibitionId() {
            return exhibitionId;
        }

        public void setExhibitionId(String exhibitionId) {
            this.exhibitionId = exhibitionId;
        }

        public String getOnTime() {
            return onTime;
        }

        public void setOnTime(String onTime) {
            this.onTime = onTime;
        }

        public double getStoreCount() {
            return storeCount;
        }

        public void setStoreCount(double storeCount) {
            this.storeCount = storeCount;
        }
    }
}
