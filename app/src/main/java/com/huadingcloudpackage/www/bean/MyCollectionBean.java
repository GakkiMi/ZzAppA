package com.huadingcloudpackage.www.bean;

import java.util.List;

/**
 * @version V1.0
 * @类描述：
 * @创建人：李歌
 * @修改人：
 * @修改备注：
 */
public class MyCollectionBean {


    private String msg;
    private int code;
    private List<ListBean> data;

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

    public List<ListBean> getData() {
        return data;
    }

    public void setData(List<ListBean> data) {
        this.data = data;
    }

    public static class ListBean {

        private boolean checked;
        private String id;
        private String goodsName;
        private String isWhole;
        private String img;
        private String specId;
        private String specKey;
        private String shopPrice;
        private String specNum;
        private String goodsNum;
        private boolean specFlag;
        private String exhibitionId;
        private int carCount,specRatio;
        private String onTime;
        private String storeCount;

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

        public boolean isChecked() {
            return checked;
        }

        public void setChecked(boolean checked) {
            this.checked = checked;
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

        public String getShopPrice() {
            return shopPrice;
        }

        public void setShopPrice(String shopPrice) {
            this.shopPrice = shopPrice;
        }

        public String getSpecNum() {
            return specNum;
        }

        public void setSpecNum(String specNum) {
            this.specNum = specNum;
        }

        public int getGoodsNum() {
            if (goodsNum != null && !goodsNum.equals("")) {
                return Integer.parseInt(goodsNum);
            }
            return 0;
        }

        public void setGoodsNum(String goodsNum) {
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

        public int getStoreCount() {
            if (storeCount != null && !storeCount.equals("")) {
                return Double.valueOf(storeCount).intValue();
            }
            return 0;
        }

        public void setStoreCount(String storeCount) {
            this.storeCount = storeCount;
        }
    }
}
