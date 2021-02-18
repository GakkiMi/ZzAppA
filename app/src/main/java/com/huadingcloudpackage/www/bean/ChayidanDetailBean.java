package com.huadingcloudpackage.www.bean;

import java.util.List;

/**
 * @version V1.0
 * @类描述：
 * @创建人：李歌
 * @修改人：
 * @修改备注：
 */
public class ChayidanDetailBean {

    /**
     * msg : 操作成功
     * code : 200
     * data : {"id":1027,"differenceSn":"CZ2020091115255835928093","examineState":4,"orderSonSn":"Z2020091115255835928093","applyTime":"2020-09-11 15:34:20","differencesReason":"商品过期","differencesExplain":null,"refund":"30","orderGoodsAll":[{"searchValue":null,"createBy":null,"createTime":null,"updateBy":null,"updateTime":"2020-09-11 17:19:19","remark":null,"params":{},"startTime":null,"endTime":null,"recId":1488,"orderId":2394,"goodsId":156,"goodsName":"臭豆腐","goodsRemark":null,"goodsSn":"HDG00000499","goodsNum":1,"finalPrice":99.99,"goodsPrice":99.99,"itemPrice":null,"costPrice":null,"memberGoodsPrice":null,"giveIntegral":0,"specKey":null,"specKeyName":"1*箱/袋","barCode":null,"isComment":null,"promType":null,"promId":null,"isSend":null,"deliveryId":null,"sku":"","itemNo":null,"storeId":null,"commission":null,"isCheckout":null,"deleted":null,"distribut":null,"shopId":null,"thirdStoreId":null,"thumbnailImageUrl":"http://47.92.11.135:8001/group1/M00/00/9A/wKgAtV9TK2uAQwUgAABEdp0QRBE908_200x150.jpg","suppliersId":103,"unit":null,"storeUnit":null,"priceUnit":null,"orderSn":"20200911162004261650","subTotal":100,"goodsContent":"甜脆大水蜜桃甜脆大水蜜桃甜脆大水蜜桃","orderSonSn":"Z2020091116200426449859","receivedNum":1,"isDifference":1,"examineState":null,"rank":null}],"supplierName":"华鼎","updateTime":null,"differenceImageList":["http://47.92.11.135:8001/group1/M00/00/AC/wKgAtV9bKHuAZ1r8AAKBQAgbTqQ59.jpeg"]}
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
         * id : 1027
         * differenceSn : CZ2020091115255835928093
         * examineState : 4
         * orderSonSn : Z2020091115255835928093
         * applyTime : 2020-09-11 15:34:20
         * differencesReason : 商品过期
         * differencesExplain : null
         * refund : 30
         * orderGoodsAll : [{"searchValue":null,"createBy":null,"createTime":null,"updateBy":null,"updateTime":"2020-09-11 17:19:19","remark":null,"params":{},"startTime":null,"endTime":null,"recId":1488,"orderId":2394,"goodsId":156,"goodsName":"臭豆腐","goodsRemark":null,"goodsSn":"HDG00000499","goodsNum":1,"finalPrice":99.99,"goodsPrice":99.99,"itemPrice":null,"costPrice":null,"memberGoodsPrice":null,"giveIntegral":0,"specKey":null,"specKeyName":"1*箱/袋","barCode":null,"isComment":null,"promType":null,"promId":null,"isSend":null,"deliveryId":null,"sku":"","itemNo":null,"storeId":null,"commission":null,"isCheckout":null,"deleted":null,"distribut":null,"shopId":null,"thirdStoreId":null,"thumbnailImageUrl":"http://47.92.11.135:8001/group1/M00/00/9A/wKgAtV9TK2uAQwUgAABEdp0QRBE908_200x150.jpg","suppliersId":103,"unit":null,"storeUnit":null,"priceUnit":null,"orderSn":"20200911162004261650","subTotal":100,"goodsContent":"甜脆大水蜜桃甜脆大水蜜桃甜脆大水蜜桃","orderSonSn":"Z2020091116200426449859","receivedNum":1,"isDifference":1,"examineState":null,"rank":null}]
         * supplierName : 华鼎
         * updateTime : null
         * differenceImageList : ["http://47.92.11.135:8001/group1/M00/00/AC/wKgAtV9bKHuAZ1r8AAKBQAgbTqQ59.jpeg"]
         */

        private int id;
        private String differenceSn;//差异单号
        private int examineState;//差异状态
        private String orderSonSn;//订单号
        private String applyTime;//申请时间
        private String differencesReason;//差异原因
        private String differencesExplain;//差异说明
        private String refund;//应退金额
        private String payTotalPrice;//应付金额
        private String supplierName;
        private String exhibiName;
        private String isSplitOrder;
        private String updateTime;
        private String payTime;
        private List<OrderGoodsAllBean> orderGoodsAll;
        private List<String> differenceImageList;
        private String accomplishTime;//完成
        private String refundMode;// 支付/退款方式
        private String refuseReason;//拒绝原因

        public String getExhibiName() {
            return exhibiName;
        }

        public void setExhibiName(String exhibiName) {
            this.exhibiName = exhibiName;
        }

        public String getIsSplitOrder() {
            return isSplitOrder;
        }

        public void setIsSplitOrder(String isSplitOrder) {
            this.isSplitOrder = isSplitOrder;
        }

        public String getAccomplishTime() {
            return accomplishTime;
        }

        public void setAccomplishTime(String accomplishTime) {
            this.accomplishTime = accomplishTime;
        }

        public String getRefundMode() {
            return refundMode;
        }

        public void setRefundMode(String refundMode) {
            this.refundMode = refundMode;
        }

        public String getRefuseReason() {
            return refuseReason;
        }

        public void setRefuseReason(String refuseReason) {
            this.refuseReason = refuseReason;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getDifferenceSn() {
            return differenceSn;
        }

        public void setDifferenceSn(String differenceSn) {
            this.differenceSn = differenceSn;
        }

        public int getExamineState() {
            return examineState;
        }

        public void setExamineState(int examineState) {
            this.examineState = examineState;
        }

        public String getOrderSonSn() {
            return orderSonSn;
        }

        public void setOrderSonSn(String orderSonSn) {
            this.orderSonSn = orderSonSn;
        }

        public String getApplyTime() {
            return applyTime;
        }

        public void setApplyTime(String applyTime) {
            this.applyTime = applyTime;
        }

        public String getDifferencesReason() {
            return differencesReason;
        }

        public void setDifferencesReason(String differencesReason) {
            this.differencesReason = differencesReason;
        }

        public String getDifferencesExplain() {
            return differencesExplain;
        }

        public void setDifferencesExplain(String differencesExplain) {
            this.differencesExplain = differencesExplain;
        }

        public String getRefund() {
            return refund;
        }

        public void setRefund(String refund) {
            this.refund = refund;
        }


        public String getPayTotalPrice() {
            return payTotalPrice;
        }

        public void setPayTotalPrice(String payTotalPrice) {
            this.payTotalPrice = payTotalPrice;
        }

        public String getSupplierName() {
            return supplierName;
        }

        public void setSupplierName(String supplierName) {
            this.supplierName = supplierName;
        }

        public String getUpdateTime() {
            return updateTime;
        }

        public void setUpdateTime(String updateTime) {
            this.updateTime = updateTime;
        }


        public String getPayTime() {
            return payTime;
        }

        public void setPayTime(String payTime) {
            this.payTime = payTime;
        }

        public List<OrderGoodsAllBean> getOrderGoodsAll() {
            return orderGoodsAll;
        }

        public void setOrderGoodsAll(List<OrderGoodsAllBean> orderGoodsAll) {
            this.orderGoodsAll = orderGoodsAll;
        }

        public List<String> getDifferenceImageList() {
            return differenceImageList;
        }

        public void setDifferenceImageList(List<String> differenceImageList) {
            this.differenceImageList = differenceImageList;
        }

        public static class OrderGoodsAllBean {
            /**
             * searchValue : null
             * createBy : null
             * createTime : null
             * updateBy : null
             * updateTime : 2020-09-11 17:19:19
             * remark : null
             * params : {}
             * startTime : null
             * endTime : null
             * recId : 1488
             * orderId : 2394
             * goodsId : 156
             * goodsName : 臭豆腐
             * goodsRemark : null
             * goodsSn : HDG00000499
             * goodsNum : 1
             * finalPrice : 99.99
             * goodsPrice : 99.99
             * itemPrice : null
             * costPrice : null
             * memberGoodsPrice : null
             * giveIntegral : 0
             * specKey : null
             * specKeyName : 1*箱/袋
             * barCode : null
             * isComment : null
             * promType : null
             * promId : null
             * isSend : null
             * deliveryId : null
             * sku : 
             * itemNo : null
             * storeId : null
             * commission : null
             * isCheckout : null
             * deleted : null
             * distribut : null
             * shopId : null
             * thirdStoreId : null
             * thumbnailImageUrl : http://47.92.11.135:8001/group1/M00/00/9A/wKgAtV9TK2uAQwUgAABEdp0QRBE908_200x150.jpg
             * suppliersId : 103
             * unit : null
             * storeUnit : null
             * priceUnit : null
             * orderSn : 20200911162004261650
             * subTotal : 100
             * goodsContent : 甜脆大水蜜桃甜脆大水蜜桃甜脆大水蜜桃
             * orderSonSn : Z2020091116200426449859
             * receivedNum : 1
             * isDifference : 1
             * examineState : null
             * rank : null
             */

            private String searchValue;
            private String createBy;
            private String createTime;
            private String updateBy;
            private String updateTime;
            private String remark;
            private ParamsBean params;
            private String startTime;
            private String endTime;
            private int recId;
            private int orderId;
            private int goodsId;
            private String goodsName;
            private String goodsRemark;
            private String goodsSn;
            private double goodsNum;
            private double finalPrice;
            private double goodsPrice;
            private String itemPrice;
            private String costPrice;
            private String memberGoodsPrice;
            private int giveIntegral;
            private String specKey;
            private String specKeyName;
            private String barCode;
            private String isComment;
            private String promType;
            private String promId;
            private String isSend;
            private String deliveryId;
            private String sku;
            private String itemNo;
            private String storeId;
            private String commission;
            private String isCheckout;
            private String deleted;
            private String distribut;
            private String shopId;
            private String thirdStoreId;
            private String thumbnailImageUrl;
            private int suppliersId;
            private String unit;
            private String storeUnit;
            private String priceUnit;
            private String orderSn;
            private String subTotal;
            private String goodsContent;
            private String orderSonSn;
            private double receivedNum;
            private int isDifference;
            private String examineState;

            private String unitType;
            private String bigKeyName;
            private String bigUnitName;
            private String bigUnitNum;
            private String bigUnitPrice;
            private String bigPrice;
            private String smallPrice;
            private String smallUnitPrice;
            private String smallUnitName;
            private String smallUnitNum;
            private String differenceGoodsType;//支付状态

            private String rank;

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

            public ParamsBean getParams() {
                return params;
            }

            public void setParams(ParamsBean params) {
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

            public int getRecId() {
                return recId;
            }

            public void setRecId(int recId) {
                this.recId = recId;
            }

            public int getOrderId() {
                return orderId;
            }

            public void setOrderId(int orderId) {
                this.orderId = orderId;
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

            public String getGoodsRemark() {
                return goodsRemark;
            }

            public void setGoodsRemark(String goodsRemark) {
                this.goodsRemark = goodsRemark;
            }

            public String getGoodsSn() {
                return goodsSn;
            }

            public void setGoodsSn(String goodsSn) {
                this.goodsSn = goodsSn;
            }

            public double getGoodsNum() {
                return goodsNum;
            }

            public void setGoodsNum(double goodsNum) {
                this.goodsNum = goodsNum;
            }

            public double getFinalPrice() {
                return finalPrice;
            }

            public void setFinalPrice(double finalPrice) {
                this.finalPrice = finalPrice;
            }

            public double getGoodsPrice() {
                return goodsPrice;
            }

            public void setGoodsPrice(double goodsPrice) {
                this.goodsPrice = goodsPrice;
            }

            public String getItemPrice() {
                return itemPrice;
            }

            public void setItemPrice(String itemPrice) {
                this.itemPrice = itemPrice;
            }

            public String getCostPrice() {
                return costPrice;
            }

            public void setCostPrice(String costPrice) {
                this.costPrice = costPrice;
            }

            public String getMemberGoodsPrice() {
                return memberGoodsPrice;
            }

            public void setMemberGoodsPrice(String memberGoodsPrice) {
                this.memberGoodsPrice = memberGoodsPrice;
            }

            public int getGiveIntegral() {
                return giveIntegral;
            }

            public void setGiveIntegral(int giveIntegral) {
                this.giveIntegral = giveIntegral;
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

            public String getIsComment() {
                return isComment;
            }

            public void setIsComment(String isComment) {
                this.isComment = isComment;
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

            public String getIsSend() {
                return isSend;
            }

            public void setIsSend(String isSend) {
                this.isSend = isSend;
            }

            public String getDeliveryId() {
                return deliveryId;
            }

            public void setDeliveryId(String deliveryId) {
                this.deliveryId = deliveryId;
            }

            public String getSku() {
                return sku;
            }

            public void setSku(String sku) {
                this.sku = sku;
            }

            public String getItemNo() {
                return itemNo;
            }

            public void setItemNo(String itemNo) {
                this.itemNo = itemNo;
            }

            public String getStoreId() {
                return storeId;
            }

            public void setStoreId(String storeId) {
                this.storeId = storeId;
            }

            public String getCommission() {
                return commission;
            }

            public void setCommission(String commission) {
                this.commission = commission;
            }

            public String getIsCheckout() {
                return isCheckout;
            }

            public void setIsCheckout(String isCheckout) {
                this.isCheckout = isCheckout;
            }

            public String getDeleted() {
                return deleted;
            }

            public void setDeleted(String deleted) {
                this.deleted = deleted;
            }

            public String getDistribut() {
                return distribut;
            }

            public void setDistribut(String distribut) {
                this.distribut = distribut;
            }

            public String getShopId() {
                return shopId;
            }

            public void setShopId(String shopId) {
                this.shopId = shopId;
            }

            public String getThirdStoreId() {
                return thirdStoreId;
            }

            public void setThirdStoreId(String thirdStoreId) {
                this.thirdStoreId = thirdStoreId;
            }

            public String getThumbnailImageUrl() {
                return thumbnailImageUrl;
            }

            public void setThumbnailImageUrl(String thumbnailImageUrl) {
                this.thumbnailImageUrl = thumbnailImageUrl;
            }

            public int getSuppliersId() {
                return suppliersId;
            }

            public void setSuppliersId(int suppliersId) {
                this.suppliersId = suppliersId;
            }

            public String getUnit() {
                return unit;
            }

            public void setUnit(String unit) {
                this.unit = unit;
            }

            public String getStoreUnit() {
                return storeUnit;
            }

            public void setStoreUnit(String storeUnit) {
                this.storeUnit = storeUnit;
            }

            public String getPriceUnit() {
                return priceUnit;
            }

            public void setPriceUnit(String priceUnit) {
                this.priceUnit = priceUnit;
            }

            public String getOrderSn() {
                return orderSn;
            }

            public void setOrderSn(String orderSn) {
                this.orderSn = orderSn;
            }

            public String getSubTotal() {
                return subTotal;
            }

            public void setSubTotal(String subTotal) {
                this.subTotal = subTotal;
            }

            public String getGoodsContent() {
                return goodsContent;
            }

            public void setGoodsContent(String goodsContent) {
                this.goodsContent = goodsContent;
            }

            public String getOrderSonSn() {
                return orderSonSn;
            }

            public void setOrderSonSn(String orderSonSn) {
                this.orderSonSn = orderSonSn;
            }

            public double getReceivedNum() {
                return receivedNum;
            }

            public void setReceivedNum(int receivedNum) {
                this.receivedNum = receivedNum;
            }

            public int getIsDifference() {
                return isDifference;
            }

            public void setIsDifference(int isDifference) {
                this.isDifference = isDifference;
            }

            public String getExamineState() {
                return examineState;
            }

            public void setExamineState(String examineState) {
                this.examineState = examineState;
            }

            public String getUnitType() {
                return unitType;
            }

            public void setUnitType(String unitType) {
                this.unitType = unitType;
            }

            public String getBigKeyName() {
                return bigKeyName;
            }

            public void setBigKeyName(String bigKeyName) {
                this.bigKeyName = bigKeyName;
            }

            public String getBigUnitName() {
                return bigUnitName;
            }

            public void setBigUnitName(String bigUnitName) {
                this.bigUnitName = bigUnitName;
            }

            public String getBigUnitNum() {
                return bigUnitNum==null?"0":bigUnitNum;
            }

            public void setBigUnitNum(String bigUnitNum) {
                this.bigUnitNum = bigUnitNum;
            }

            public String getBigUnitPrice() {
                return bigUnitPrice;
            }

            public void setBigUnitPrice(String bigUnitPrice) {
                this.bigUnitPrice = bigUnitPrice;
            }

            public String getBigPrice() {
                return bigPrice;
            }

            public void setBigPrice(String bigPrice) {
                this.bigPrice = bigPrice;
            }

            public String getSmallPrice() {
                return smallPrice;
            }

            public void setSmallPrice(String smallPrice) {
                this.smallPrice = smallPrice;
            }

            public String getSmallUnitPrice() {
                return smallUnitPrice;
            }

            public void setSmallUnitPrice(String smallUnitPrice) {
                this.smallUnitPrice = smallUnitPrice;
            }

            public String getSmallUnitName() {
                return smallUnitName;
            }

            public void setSmallUnitName(String smallUnitName) {
                this.smallUnitName = smallUnitName;
            }

            public String getSmallUnitNum() {
                return smallUnitNum==null?"0":smallUnitNum;
            }

            public void setSmallUnitNum(String smallUnitNum) {
                this.smallUnitNum = smallUnitNum;
            }

            public String getDifferenceGoodsType() {
                return differenceGoodsType==null?"":differenceGoodsType;
            }

            public void setDifferenceGoodsType(String differenceGoodsType) {
                this.differenceGoodsType = differenceGoodsType;
            }

            public String getRank() {
                return rank;
            }

            public void setRank(String rank) {
                this.rank = rank;
            }

            public static class ParamsBean {
            }
        }
    }
}
