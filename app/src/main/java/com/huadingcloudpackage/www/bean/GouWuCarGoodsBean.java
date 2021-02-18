package com.huadingcloudpackage.www.bean;

import java.util.List;

/**
 * 文 件 名：GouWuCarGoodsBean
 * 描   述：购物车实体
 */
public class GouWuCarGoodsBean extends BaseBean {

    /**
     * "msg": "操作成功",
     * "code": 200,
     * "totalPrice": 30.00,
     * "cartList": [{}]
     */


    private double totalPrice;
    private List<CartListBean> cartList;
    //自己加的字段
    private String wareHouseName;//仓库名称
    private boolean isChecked;//是否选中

    private String deliveryType;//配送方式
    private double shipping;//运费
    private String whAddress;//仓库地址
    private String selfMentionTime;//自提时间
    private String orderRemark;//订单备注



    public double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public List<CartListBean> getCartList() {
        return cartList;
    }

    public void setCartList(List<CartListBean> cartList) {
        this.cartList = cartList;
    }

    public String getWareHouseName() {
        return wareHouseName;
    }

    public void setWareHouseName(String wareHouseName) {
        this.wareHouseName = wareHouseName;
    }

    @Override
    public boolean isChecked() {
        return isChecked;
    }

    @Override
    public void setChecked(boolean checked) {
        isChecked = checked;
    }


    public String getDeliveryType() {
        return deliveryType;
    }

    public void setDeliveryType(String deliveryType) {
        this.deliveryType = deliveryType;
    }

    public double getShipping() {
        return shipping;
    }

    public void setShipping(double shipping) {
        this.shipping = shipping;
    }

    public String getWhAddress() {
        return whAddress;
    }

    public void setWhAddress(String whAddress) {
        this.whAddress = whAddress;
    }

    public String getSelfMentionTime() {
        return selfMentionTime;
    }

    public void setSelfMentionTime(String selfMentionTime) {
        this.selfMentionTime = selfMentionTime;
    }

    public String getOrderRemark() {
        return orderRemark;
    }

    public void setOrderRemark(String orderRemark) {
        this.orderRemark = orderRemark;
    }

    //购物车商品实体
    public static class CartListBean {
        /**
         * "searchValue": null,
         * "createBy": null,
         * "createTime": null,
         * "updateBy": null,
         * "updateTime": null,
         * "remark": null,
         * "params": {},
         * "startTime": null,
         * "endTime": null,
         * "cartId": 120,
         * "userId": 52,
         * "sessionId": null,
         * "goodsId": 63,
         * "goodsSn": "HDG00000211",
         * "goodsName": "扣扣",
         * "goodsPrice": 15.00,
         * "shopPrice": null,
         * "memberGoodsPrice": null,
         * "goodsNum": 1,
         * "specKey": null,
         * "specKeyName": "箱",
         * "barCode": "",
         * "selected": 1,
         * "addTime": "2020-08-17 00:00:00",
         * "promType": null,
         * "promId": null,
         * "sku": "02002",
         * "storeId": 223,
         * "shopId": null,
         * "sgsId": null,
         * "itemId": 60,
         * "thumbnailImageUrl": "http://47.92.11.135:8001/group1/M00/00/6E/wKgAtV8tBCmAPXC1AAGd9ydqlUA511_200x150.jpg",
         * "item": "箱",
         * "storeCount": 8.0,
         * "itemPrice": 15.00,
         * "promStatus": "0",
         * "district": null,
         * "suppliersId": 81,
         * "itemNo": "HD1596785725811QV3",
         * "goodsContent": "",
         * "exhibiId": 52,
         * "exhibiNo": null,
         * "rank": null
         */

        private String searchValue;//       null,
        private String createBy;//    null,
        private String createTime;//      null,
        private String updateBy;//    null,
        private String updateTime;//      null,
        private String remark;//  null,
        private Object params;//  {},
        private String startTime;//     null,
        private String endTime;//   null,
        private String cartId;//  120,
        private String userId;//  52,
        private String sessionId;//     null,
        private int goodsId;//   63,
        private String goodsSn;//   "HDG00000211",
        private String goodsName;//     "扣扣",
        private double goodsPrice;//      15.00,
        private String shopPrice;//     null,
        private String memberGoodsPrice;//            null,
        private int goodsNum;//    1,
        private String specKey;//   null,
        private String specKeyName;//       "箱",
        private String barCode;//   "",
        private String selected;//    1,
        private String addTime;//   "2020-08-17 00:00:00",
        private String promType;//    null,
        private String promId;//  null,
        private String sku;  //";//02002",
        private String storeId;//   223,
        private String shopId;//  null,
        private String sgsId;// null,
        private String itemId;//  60,
        private String thumbnailImageUrl;//             "http://47.92.11.135:8001/group1/M00/00/6E/wKgAtV8tBCmAPXC1AAGd9ydqlUA511_200x150.jpg",
        private String item;//"箱",
        private int storeCount;//      8.0,
        private double itemPrice;//     15.00,
        private String promStatus;//      "0",
        private String district;//    null,
        private String suppliersId;//       81,
        private String itemNo;//  "HD1596785725811QV3",
        private String goodsContent;//        "",
        private String exhibiId;//    52,
        private String exhibiNo;//    null,
        private String rank;//null
        private boolean isChecked;


        public String getSearchValue() {
            return searchValue;
        }

        public void setSearchValue(String searchValue) {
            this.searchValue = searchValue;
        }

        public String getCreateBy() {
            return createBy;
        }

        public void setCreateBy(String createBy) {
            this.createBy = createBy;
        }

        public String getCreateTime() {
            return createTime;
        }

        public void setCreateTime(String createTime) {
            this.createTime = createTime;
        }

        public String getUpdateBy() {
            return updateBy;
        }

        public void setUpdateBy(String updateBy) {
            this.updateBy = updateBy;
        }

        public String getUpdateTime() {
            return updateTime;
        }

        public void setUpdateTime(String updateTime) {
            this.updateTime = updateTime;
        }

        public String getRemark() {
            return remark;
        }

        public void setRemark(String remark) {
            this.remark = remark;
        }

        public Object getParams() {
            return params;
        }

        public void setParams(Object params) {
            this.params = params;
        }

        public String getStartTime() {
            return startTime;
        }

        public void setStartTime(String startTime) {
            this.startTime = startTime;
        }

        public String getEndTime() {
            return endTime;
        }

        public void setEndTime(String endTime) {
            this.endTime = endTime;
        }

        public String getCartId() {
            return cartId;
        }

        public void setCartId(String cartId) {
            this.cartId = cartId;
        }

        public String getUserId() {
            return userId;
        }

        public void setUserId(String userId) {
            this.userId = userId;
        }

        public String getSessionId() {
            return sessionId;
        }

        public void setSessionId(String sessionId) {
            this.sessionId = sessionId;
        }

        public int getGoodsId() {
            return goodsId;
        }

        public void setGoodsId(int goodsId) {
            this.goodsId = goodsId;
        }

        public String getGoodsSn() {
            return goodsSn;
        }

        public void setGoodsSn(String goodsSn) {
            this.goodsSn = goodsSn;
        }

        public String getGoodsName() {
            return goodsName;
        }

        public void setGoodsName(String goodsName) {
            this.goodsName = goodsName;
        }

        public double getGoodsPrice() {
            return goodsPrice;
        }

        public void setGoodsPrice(double goodsPrice) {
            this.goodsPrice = goodsPrice;
        }

        public String getShopPrice() {
            return shopPrice;
        }

        public void setShopPrice(String shopPrice) {
            this.shopPrice = shopPrice;
        }

        public String getMemberGoodsPrice() {
            return memberGoodsPrice;
        }

        public void setMemberGoodsPrice(String memberGoodsPrice) {
            this.memberGoodsPrice = memberGoodsPrice;
        }

        public int getGoodsNum() {
            return goodsNum;
        }

        public void setGoodsNum(int goodsNum) {
            this.goodsNum = goodsNum;
        }

        public String getSpecKey() {
            return specKey;
        }

        public void setSpecKey(String specKey) {
            this.specKey = specKey;
        }

        public String getSpecKeyName() {
            return specKeyName;
        }

        public void setSpecKeyName(String specKeyName) {
            this.specKeyName = specKeyName;
        }

        public String getBarCode() {
            return barCode;
        }

        public void setBarCode(String barCode) {
            this.barCode = barCode;
        }

        public String getSelected() {
            return selected;
        }

        public void setSelected(String selected) {
            this.selected = selected;
        }

        public String getAddTime() {
            return addTime;
        }

        public void setAddTime(String addTime) {
            this.addTime = addTime;
        }

        public String getPromType() {
            return promType;
        }

        public void setPromType(String promType) {
            this.promType = promType;
        }

        public String getPromId() {
            return promId;
        }

        public void setPromId(String promId) {
            this.promId = promId;
        }

        public String getSku() {
            return sku;
        }

        public void setSku(String sku) {
            this.sku = sku;
        }

        public String getStoreId() {
            return storeId;
        }

        public void setStoreId(String storeId) {
            this.storeId = storeId;
        }

        public String getShopId() {
            return shopId;
        }

        public void setShopId(String shopId) {
            this.shopId = shopId;
        }

        public String getSgsId() {
            return sgsId;
        }

        public void setSgsId(String sgsId) {
            this.sgsId = sgsId;
        }

        public String getItemId() {
            return itemId;
        }

        public void setItemId(String itemId) {
            this.itemId = itemId;
        }

        public String getThumbnailImageUrl() {
            return thumbnailImageUrl;
        }

        public void setThumbnailImageUrl(String thumbnailImageUrl) {
            this.thumbnailImageUrl = thumbnailImageUrl;
        }

        public String getItem() {
            return item;
        }

        public void setItem(String item) {
            this.item = item;
        }

        public int getStoreCount() {
            return storeCount;
        }

        public void setStoreCount(int storeCount) {
            this.storeCount = storeCount;
        }

        public double getItemPrice() {
            return itemPrice;
        }

        public void setItemPrice(double itemPrice) {
            this.itemPrice = itemPrice;
        }

        public String getPromStatus() {
            return promStatus;
        }

        public void setPromStatus(String promStatus) {
            this.promStatus = promStatus;
        }

        public String getDistrict() {
            return district;
        }

        public void setDistrict(String district) {
            this.district = district;
        }

        public String getSuppliersId() {
            return suppliersId;
        }

        public void setSuppliersId(String suppliersId) {
            this.suppliersId = suppliersId;
        }

        public String getItemNo() {
            return itemNo;
        }

        public void setItemNo(String itemNo) {
            this.itemNo = itemNo;
        }

        public String getGoodsContent() {
            return goodsContent;
        }

        public void setGoodsContent(String goodsContent) {
            this.goodsContent = goodsContent;
        }

        public String getExhibiId() {
            return exhibiId;
        }

        public void setExhibiId(String exhibiId) {
            this.exhibiId = exhibiId;
        }

        public String getExhibiNo() {
            return exhibiNo;
        }

        public void setExhibiNo(String exhibiNo) {
            this.exhibiNo = exhibiNo;
        }

        public String getRank() {
            return rank;
        }

        public void setRank(String rank) {
            this.rank = rank;
        }

        public boolean isChecked() {
            return isChecked;
        }

        public void setChecked(boolean checked) {
            isChecked = checked;
        }
    }

}
