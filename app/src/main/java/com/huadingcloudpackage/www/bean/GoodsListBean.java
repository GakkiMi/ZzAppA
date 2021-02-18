package com.huadingcloudpackage.www.bean;

import java.util.List;

/**
 * {
 * "msg":"操作成功",
 * "code":200,
 * "data":{
 * "total":1,
 * "list":[
 * {
 * "id":"SP2020111600001",
 * "goodsName":"山药",
 * "isWhole":"1",
 * "img":"http://121.89.202.8:8001/group1/M00/00/1B/rBp6pV-x1naATnX1AACOslLQ_Gs016_200x150.jpg",
 * "specId":"GG2020111600002",
 * "specKey":"箱",
 * "shopPrice":450,
 * "specNum":null,
 * "goodsNum":12,
 * "specFlag":false,
 * "exhibitionId":null,
 * "onTime":null,
 * "storeCount":"99"
 * }
 * ],
 * "pageNum":1,
 * "pageSize":10,
 * "size":1,
 * "startRow":1,
 * "endRow":1,
 * "pages":1,
 * "prePage":0,
 * "nextPage":0,
 * "isFirstPage":true,
 * "isLastPage":true,
 * "hasPreviousPage":false,
 * "hasNextPage":false,
 * "navigatePages":8,
 * "navigatepageNums":[
 * 1
 * ],
 * "navigateFirstPage":1,
 * "navigateLastPage":1
 * }
 * }
 */

public class GoodsListBean {
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

        public int getTotal() {
            return total;
        }

        public void setTotal(int total) {
            this.total = total;
        }

        public List<Item> getList() {
            return list;
        }

        public void setList(List<Item> list) {
            this.list = list;
        }

        public static class Item {
            private String onTime, exhibitionId, id, goodsName, isWhole, img, specId, specKey, storeCount;
            private double specNum;
            private String shopPrice;
            private int goodsNum;
            private int specRatio, carCount;
            private boolean specFlag;

            public int getCarCount() {
                return carCount;
            }

            public void setCarCount(int carCount) {
                this.carCount = carCount;
            }

            public int getSpecRatio() {
                return specRatio;
            }

            public void setSpecRatio(int specRatio) {
                this.specRatio = specRatio;
            }

            public String getOnTime() {
                return onTime;
            }

            public void setOnTime(String onTime) {
                this.onTime = onTime;
            }

            public String getExhibitionId() {
                return exhibitionId;
            }

            public void setExhibitionId(String exhibitionId) {
                this.exhibitionId = exhibitionId;
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

            public String getStoreCount() {
                return storeCount;
            }

            public void setStoreCount(String storeCount) {
                this.storeCount = storeCount;
            }

            public double getSpecNum() {
                return specNum;
            }

            public void setSpecNum(double specNum) {
                this.specNum = specNum;
            }

            public String getShopPrice() {
                if (shopPrice == null || shopPrice.equals("")) {
                    return "0";
                }
                return shopPrice;
            }

            public void setShopPrice(String shopPrice) {
                this.shopPrice = shopPrice;
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
        }


    }
}
