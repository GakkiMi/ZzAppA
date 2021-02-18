package com.huadingcloudpackage.www.bean;

import java.util.List;

public class GoodsDetailBean {

    /**
     * msg : 操作成功
     * code : 200
     * data : {"goodsId":63,"goodsName":"扣扣","suppliersId":81,"isHot":0,"isNew":0,"isRecommend":0,"exhibiNo":null,"storageMode":"保鲜","goodsContent":"","imgList":[{"imageUrl":"http://47.92.11.135:8001/group1/M00/00/6E/wKgAtV8tBCmAPXC1AAGd9ydqlUA511.jpg","thumbnailImageUrl":"http://47.92.11.135:8001/group1/M00/00/6E/wKgAtV8tBCmAPXC1AAGd9ydqlUA511_200x150.jpg"}],"itemList":[{"itemId":60,"item":"箱","storeCount":8,"sku":"02002","promId":0,"promType":"0","promStatus":"0","promPrice":null,"unit":"dwz02302","baseUnit":"dwz02301","smallUnit":"dwz02301","unitName":"件","baseUnitName":"袋","smallName":"袋","mole":220,"small":1,"price":"15.00","unitGroupId":null,"isPrice":null,"shopPrice":15}],"goodsRemark":"","brandname":null,"goodsSn":"HDG00000211","cartGoodsNum":null,"houseCode":null}
     */

    private String msg;
    private int code;
    private DataBean data;

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

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {

       private String brandName,  baseSpecName, storageMode,  qualityTerm, storeId, goodsContent, exhibitionId, storeCount, specId, specKey, goodsName, id;
       private String[] imgs;
       private double shopPrice;
       private int flag;

        public String getBrandName() {
            return brandName;
        }

        public void setBrandName(String brandName) {
            this.brandName = brandName;
        }

        public String getBaseSpecName() {
            return baseSpecName;
        }

        public void setBaseSpecName(String baseSpecName) {
            this.baseSpecName = baseSpecName;
        }

        public String getStorageMode() {
            return storageMode;
        }

        public void setStorageMode(String storageMode) {
            this.storageMode = storageMode;
        }

        public String getQualityTerm() {
            return qualityTerm;
        }

        public void setQualityTerm(String qualityTerm) {
            this.qualityTerm = qualityTerm;
        }

        public String getStoreId() {
            return storeId;
        }

        public void setStoreId(String storeId) {
            this.storeId = storeId;
        }

        public String getGoodsContent() {
            return goodsContent;
        }

        public void setGoodsContent(String goodsContent) {
            this.goodsContent = goodsContent;
        }

        public String getExhibitionId() {
            return exhibitionId;
        }

        public void setExhibitionId(String exhibitionId) {
            this.exhibitionId = exhibitionId;
        }

        public String getStoreCount() {
            return storeCount;
        }

        public void setStoreCount(String storeCount) {
            this.storeCount = storeCount;
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

        public String getGoodsName() {
            return goodsName;
        }

        public void setGoodsName(String goodsName) {
            this.goodsName = goodsName;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String[] getImgs() {
            return imgs;
        }

        public void setImgs(String[] imgs) {
            this.imgs = imgs;
        }

        public double getShopPrice() {
            return shopPrice;
        }

        public void setShopPrice(double shopPrice) {
            this.shopPrice = shopPrice;
        }

        public int getFlag() {
            return flag;
        }

        public void setFlag(int flag) {
            this.flag = flag;
        }
    }
}
