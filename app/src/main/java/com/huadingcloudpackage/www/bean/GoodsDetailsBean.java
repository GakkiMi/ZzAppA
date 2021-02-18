package com.huadingcloudpackage.www.bean;

import java.util.List;

public class GoodsDetailsBean {

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
        /**
         * goodsId : 63
         * goodsName : 扣扣
         * suppliersId : 81
         * isHot : 0
         * isNew : 0
         * isRecommend : 0
         * exhibiNo : null
         * storageMode : 保鲜
         * goodsContent :
         * imgList : [{"imageUrl":"http://47.92.11.135:8001/group1/M00/00/6E/wKgAtV8tBCmAPXC1AAGd9ydqlUA511.jpg","thumbnailImageUrl":"http://47.92.11.135:8001/group1/M00/00/6E/wKgAtV8tBCmAPXC1AAGd9ydqlUA511_200x150.jpg"}]
         * itemList : [{"itemId":60,"item":"箱","storeCount":8,"sku":"02002","promId":0,"promType":"0","promStatus":"0","promPrice":null,"unit":"dwz02302","baseUnit":"dwz02301","smallUnit":"dwz02301","unitName":"件","baseUnitName":"袋","smallName":"袋","mole":220,"small":1,"price":"15.00","unitGroupId":null,"isPrice":null,"shopPrice":15}]
         * goodsRemark :
         * brandname : null
         * goodsSn : HDG00000211
         * cartGoodsNum : null
         * houseCode : null
         * goodsWeight:null
         * isCollect:0
         * unitList:[{"type":"big","name":"盒","price":20.00,"storeCount":10,"weight":null}]
         */

        private int goodsId;
        private String goodsName;
        private int suppliersId;
        private int isHot;
        private int isNew;
        private int isRecommend;
        private int isCollect;
        private String exhibiNo;
        private String storageMode;
        private String goodsContent;
        private String goodsRemark;
        private String brandname;
        private String goodsSn;
        private String cartGoodsNum;
        private String houseCode;
        private List<ImgListBean> imgList;
        private List<ItemListBean> itemList;
        private String goodWeight;
        private List<UnitListBean> unitList;
        private String qualityTerm;

        public int getIsCollect() {
            return isCollect;
        }

        public void setIsCollect(int isCollect) {
            this.isCollect = isCollect;
        }

        public int getGoodsId() {
            return goodsId;
        }

        public void setGoodsId(int goodsId) {
            this.goodsId = goodsId;
        }

        public String getGoodsName() {
            return goodsName;
        }

        public void setGoodsName(String goodsName) {
            this.goodsName = goodsName;
        }

        public int getSuppliersId() {
            return suppliersId;
        }

        public void setSuppliersId(int suppliersId) {
            this.suppliersId = suppliersId;
        }

        public int getIsHot() {
            return isHot;
        }

        public void setIsHot(int isHot) {
            this.isHot = isHot;
        }

        public int getIsNew() {
            return isNew;
        }

        public void setIsNew(int isNew) {
            this.isNew = isNew;
        }

        public int getIsRecommend() {
            return isRecommend;
        }

        public void setIsRecommend(int isRecommend) {
            this.isRecommend = isRecommend;
        }

        public String getExhibiNo() {
            return exhibiNo;
        }

        public void setExhibiNo(String exhibiNo) {
            this.exhibiNo = exhibiNo;
        }

        public String getStorageMode() {
            return storageMode;
        }

        public void setStorageMode(String storageMode) {
            this.storageMode = storageMode;
        }

        public String getGoodsContent() {
            return goodsContent;
        }

        public void setGoodsContent(String goodsContent) {
            this.goodsContent = goodsContent;
        }

        public String getGoodsRemark() {
            return goodsRemark;
        }

        public void setGoodsRemark(String goodsRemark) {
            this.goodsRemark = goodsRemark;
        }

        public String getBrandname() {
            return brandname;
        }

        public void setBrandname(String brandname) {
            this.brandname = brandname;
        }

        public String getGoodsSn() {
            return goodsSn;
        }

        public void setGoodsSn(String goodsSn) {
            this.goodsSn = goodsSn;
        }

        public String getCartGoodsNum() {
            return cartGoodsNum;
        }

        public void setCartGoodsNum(String cartGoodsNum) {
            this.cartGoodsNum = cartGoodsNum;
        }

        public String getHouseCode() {
            return houseCode;
        }

        public void setHouseCode(String houseCode) {
            this.houseCode = houseCode;
        }

        public List<ImgListBean> getImgList() {
            return imgList;
        }

        public void setImgList(List<ImgListBean> imgList) {
            this.imgList = imgList;
        }

        public List<ItemListBean> getItemList() {
            return itemList;
        }

        public void setItemList(List<ItemListBean> itemList) {
            this.itemList = itemList;
        }

        public String getGoodWeight() {
            return goodWeight;
        }

        public void setGoodWeight(String goodWeight) {
            this.goodWeight = goodWeight;
        }

        public List<UnitListBean> getUnitList() {
            return unitList;
        }

        public void setUnitList(List<UnitListBean> unitList) {
            this.unitList = unitList;
        }

        public String getQualityTerm() {
            return qualityTerm;
        }

        public void setQualityTerm(String qualityTerm) {
            this.qualityTerm = qualityTerm;
        }

        public static class ImgListBean {
            /**
             * imageUrl : http://47.92.11.135:8001/group1/M00/00/6E/wKgAtV8tBCmAPXC1AAGd9ydqlUA511.jpg
             * thumbnailImageUrl : http://47.92.11.135:8001/group1/M00/00/6E/wKgAtV8tBCmAPXC1AAGd9ydqlUA511_200x150.jpg
             */

            private String imageUrl;
            private String thumbnailImageUrl;

            public String getImageUrl() {
                return imageUrl;
            }

            public void setImageUrl(String imageUrl) {
                this.imageUrl = imageUrl;
            }

            public String getThumbnailImageUrl() {
                return thumbnailImageUrl;
            }

            public void setThumbnailImageUrl(String thumbnailImageUrl) {
                this.thumbnailImageUrl = thumbnailImageUrl;
            }
        }

        public static class ItemListBean {
            /**
             * itemId : 60
             * item : 箱
             * storeCount : 8.0
             * sku : 02002
             * promId : 0
             * promType : 0
             * promStatus : 0
             * promPrice : null
             * unit : dwz02302
             * baseUnit : dwz02301
             * smallUnit : dwz02301
             * unitName : 件
             * baseUnitName : 袋
             * smallName : 袋
             * mole : 220
             * small : 1
             * price : 15.00
             * unitGroupId : null
             * isPrice : null
             * shopPrice : 15.0
             */

            private String itemId;
            private String item;
            private String storeCount;
            private String sku;
            private int promId;
            private String promType;
            private String promStatus;
            private String promPrice;
            private String unit;
            private String baseUnit;
            private String smallUnit;
            private String unitName;
            private String baseUnitName;
            private String smallName;
            private int mole;
            private int small;
            private String price;
            private String unitGroupId;
            private String isPrice;
            private String shopPrice;
            private String weight;
            private boolean isSelect;

            public String getWeight() {
                return weight;
            }

            public void setWeight(String weight) {
                this.weight = weight;
            }

            public boolean isSelect() {
                return isSelect;
            }

            public void setSelect(boolean select) {
                isSelect = select;
            }

            public String getItemId() {
                return itemId;
            }

            public void setItemId(String itemId) {
                this.itemId = itemId;
            }

            public String getItem() {
                return item;
            }

            public void setItem(String item) {
                this.item = item;
            }

            public String getStoreCount() {
                return storeCount;
            }

            public void setStoreCount(String storeCount) {
                this.storeCount = storeCount;
            }

            public String getSku() {
                return sku;
            }

            public void setSku(String sku) {
                this.sku = sku;
            }

            public int getPromId() {
                return promId;
            }

            public void setPromId(int promId) {
                this.promId = promId;
            }

            public String getPromType() {
                return promType;
            }

            public void setPromType(String promType) {
                this.promType = promType;
            }

            public String getPromStatus() {
                return promStatus;
            }

            public void setPromStatus(String promStatus) {
                this.promStatus = promStatus;
            }

            public String getPromPrice() {
                return promPrice;
            }

            public void setPromPrice(String promPrice) {
                this.promPrice = promPrice;
            }

            public String getUnit() {
                return unit;
            }

            public void setUnit(String unit) {
                this.unit = unit;
            }

            public String getBaseUnit() {
                return baseUnit;
            }

            public void setBaseUnit(String baseUnit) {
                this.baseUnit = baseUnit;
            }

            public String getSmallUnit() {
                return smallUnit;
            }

            public void setSmallUnit(String smallUnit) {
                this.smallUnit = smallUnit;
            }

            public String getUnitName() {
                return unitName;
            }

            public void setUnitName(String unitName) {
                this.unitName = unitName;
            }

            public String getBaseUnitName() {
                return baseUnitName;
            }

            public void setBaseUnitName(String baseUnitName) {
                this.baseUnitName = baseUnitName;
            }

            public String getSmallName() {
                return smallName;
            }

            public void setSmallName(String smallName) {
                this.smallName = smallName;
            }

            public int getMole() {
                return mole;
            }

            public void setMole(int mole) {
                this.mole = mole;
            }

            public int getSmall() {
                return small;
            }

            public void setSmall(int small) {
                this.small = small;
            }

            public String getPrice() {
                return price;
            }

            public void setPrice(String price) {
                this.price = price;
            }

            public String getUnitGroupId() {
                return unitGroupId;
            }

            public void setUnitGroupId(String unitGroupId) {
                this.unitGroupId = unitGroupId;
            }

            public String getIsPrice() {
                return isPrice;
            }

            public void setIsPrice(String isPrice) {
                this.isPrice = isPrice;
            }

            public String getShopPrice() {
                return shopPrice;
            }

            public void setShopPrice(String shopPrice) {
                this.shopPrice = shopPrice;
            }
        }


        public static class UnitListBean {

            /**
             * "type":"small",
             * "name":"个",
             * "price":100,
             * "storeCount":10,
             * "weight":50
             */

            private String type;//":"small",
            private String name;//":"个",
            private String price;//":100,
            private String storeCount;//":10,
            private String weight;//":50
            private boolean isSelect;

            public String getType() {
                return type;
            }

            public void setType(String type) {
                this.type = type;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getPrice() {
                return price;
            }

            public void setPrice(String price) {
                this.price = price;
            }

            public String getStoreCount() {
                return storeCount;
            }

            public void setStoreCount(String storeCount) {
                this.storeCount = storeCount;
            }

            public String getWeight() {
                return weight;
            }

            public void setWeight(String weight) {
                this.weight = weight;
            }

            public boolean isSelect() {
                return isSelect;
            }

            public void setSelect(boolean select) {
                isSelect = select;
            }
        }
    }
}
