package com.huadingcloudpackage.www.bean;

import android.text.TextUtils;

import java.io.Serializable;
import java.util.List;

/**
 * @version V1.0
 * @类描述：
 * @创建人：李歌
 * @修改人：
 * @修改备注：
 */
public class OrderDetailBean implements Serializable {

    /**
     * msg : 操作成功
     * code : 200
     * order : {"searchValue":null,"createBy":null,"createTime":null,"updateBy":null,"updateTime":null,"remark":null,"params":{},"startTime":null,"endTime":null,"orderId":2186,"orderSn":"20200829100905577056","orderSonSn":null,"goodsName":null,"userId":50,"masterOrderSn":"","orderStatus":"1","payStatus":"1","shippingStatus":"0","consignee":"ssss","country":0,"province":0,"city":0,"district":0,"twon":0,"address":"Asfsw","zipcode":"","mobile":"111","email":"","shippingCode":"","shippingName":"","shippingPrice":30,"payCode":"8","payName":"余额","invoiceTitle":"","taxpayer":"","goodsPrice":0,"userMoney":0,"couponPrice":0,"Stringegral":0,"StringegralMoney":0,"orderAmount":0,"totalAmount":0,"paidMoney":0,"transactionId":"","transactionIdZx":null,"promId":0,"promType":0,"orderPromId":0,"orderPromAmount":0,"discount":0,"userNote":"","adminNote":"","parentSn":"","storeId":1,"isComment":0,"shopId":0,"deleted":0,"orderStatisId":0,"kingStatus":0,"kingId":0,"kingNum":null,"kingDel":0,"kingSaleNo":null,"thirdStoreId":"0","kingSendNo":null,"tiancaiSendNo":null,"sync":null,"tiancaiId":0,"needPay":null,"isPay":0,"confirmTime":"2020-08-29 10:09:11","payTime":"2020-08-29 10:09:11","addTime":"2020-08-29 10:09:05","shippingTime":null,"finishTime":null,"cancelTime":null,"posKingFlag":1,"posKingStock":"ZJD001","exhibiName":null,"exhibiContacts":null,"exhibiPhone":null,"deliverMan":"","deliverPhone":"","userType":"02","isSplit":0,"auditStatus":0,"storeName":null,"suppliersId":null,"userName":null,"followUserName":null,"orderGoodsList":[{"searchValue":null,"createBy":null,"createTime":"2020-08-29 10:09:05","updateBy":null,"updateTime":null,"remark":null,"params":{},"startTime":null,"endTime":null,"recId":1082,"orderId":0,"goodsId":58,"goodsName":"m m m","goodsRemark":null,"goodsSn":"HDG00000206","goodsNum":1,"finalPrice":15,"goodsPrice":15,"itemPrice":15,"costPrice":0,"memberGoodsPrice":0,"giveStringegral":0,"specKey":"","specKeyName":"瓶","barCode":"","isComment":0,"promType":"0","promId":0,"isSend":0,"deliveryId":0,"sku":"01009","itemNo":null,"storeId":0,"commission":0,"isCheckout":0,"deleted":0,"distribut":0,"shopId":0,"thirdStoreId":0,"thumbnailImageUrl":"http://47.92.11.135:8001/group1/M00/00/6B/wKgAtV8qIlCAAm5VAAGd9ydqlUA536_200x150.jpg","suppliersId":69,"unit":null,"storeUnit":null,"priceUnit":null,"orderSn":"20200829100905577056","subTotal":15,"goodsContent":"","orderSonSn":"2020082910090559001401","receivedNum":null,"isDifference":null,"examineState":null,"rank":null},{"searchValue":null,"createBy":null,"createTime":"2020-08-29 10:09:05","updateBy":null,"updateTime":null,"remark":null,"params":{},"startTime":null,"endTime":null,"recId":1083,"orderId":0,"goodsId":74,"goodsName":"薯片","goodsRemark":null,"goodsSn":"HDG00000222","goodsNum":1,"finalPrice":10,"goodsPrice":10,"itemPrice":10,"costPrice":0,"memberGoodsPrice":0,"giveStringegral":0,"specKey":"","specKeyName":"15","barCode":"","isComment":0,"promType":"0","promId":0,"isSend":0,"deliveryId":0,"sku":"CH4954","itemNo":null,"storeId":0,"commission":0,"isCheckout":0,"deleted":0,"distribut":0,"shopId":0,"thirdStoreId":0,"thumbnailImageUrl":"http://47.92.11.135:8001/group1/M00/00/85/wKgAtV8-HlyAMfN9AABXyLnKTfs373_200x150.jpg","suppliersId":86,"unit":null,"storeUnit":null,"priceUnit":null,"orderSn":"20200829100905577056","subTotal":10,"goodsContent":"","orderSonSn":"2020082910090560260153","receivedNum":null,"isDifference":null,"examineState":null,"rank":null}],"goodsTypeNum":null,"addTimeStart":null,"addTimeEnd":null,"invoiceStatus":null,"fullAddress":"北京市北京市东城区Asfsw","pickThe":0,"sequence":0,"clerkId":0,"deliveryMethod":null,"isDifference":null,"sonOrderAmount":null,"selfTime":null,"sonTotalAmount":null,"overTime":null,"supplier":null,"orderSonSnList":[{"searchValue":null,"createBy":null,"createTime":null,"updateBy":null,"updateTime":null,"remark":null,"params":{},"startTime":null,"endTime":null,"id":75,"orderSn":"20200829100905577056","orderSonSn":"2020082910090559001401","isDifference":1,"deliveryMethod":2,"selfTime":"2020-08-30 am","sonTotalAmount":15,"sonOrderAmount":null,"orderStatus":1,"shippingTime":null,"suppliersId":69,"overTime":null,"suppliersName":"薯片","orderRemark":null,"cartsIds":null,"orderGoodsList":[{"searchValue":null,"createBy":null,"createTime":"2020-08-29 10:09:05","updateBy":null,"updateTime":null,"remark":null,"params":{},"startTime":null,"endTime":null,"recId":1082,"orderId":0,"goodsId":58,"goodsName":"m m m","goodsRemark":null,"goodsSn":"HDG00000206","goodsNum":1,"finalPrice":15,"goodsPrice":15,"itemPrice":15,"costPrice":0,"memberGoodsPrice":0,"giveStringegral":0,"specKey":"","specKeyName":"瓶","barCode":"","isComment":0,"promType":"0","promId":0,"isSend":0,"deliveryId":0,"sku":"01009","itemNo":null,"storeId":0,"commission":0,"isCheckout":0,"deleted":0,"distribut":0,"shopId":0,"thirdStoreId":0,"thumbnailImageUrl":"http://47.92.11.135:8001/group1/M00/00/6B/wKgAtV8qIlCAAm5VAAGd9ydqlUA536_200x150.jpg","suppliersId":69,"unit":null,"storeUnit":null,"priceUnit":null,"orderSn":"20200829100905577056","subTotal":15,"goodsContent":"","orderSonSn":"2020082910090559001401","receivedNum":null,"isDifference":null,"examineState":null,"rank":null}],"cancelTime":null,"orderId":null,"freight":"0","rank":null}],"supplierName":null,"rank":null,"deliverAddress":null}
     */

    private String msg;
    private int code;
    private OrderBean order;

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

    public OrderBean getOrder() {
        return order;
    }

    public void setOrder(OrderBean order) {
        this.order = order;
    }

    public static class OrderBean implements Serializable {
        /**
         * searchValue : null
         * createBy : null
         * createTime : null
         * updateBy : null
         * updateTime : null
         * remark : null
         * params : {}
         * startTime : null
         * endTime : null
         * orderId : 2186
         * orderSn : 20200829100905577056
         * orderSonSn : null
         * goodsName : null
         * userId : 50
         * masterOrderSn :
         * orderStatus : 1
         * payStatus : 1
         * shippingStatus : 0
         * consignee : ssss
         * country : 0
         * province : 0
         * city : 0
         * district : 0
         * twon : 0
         * address : Asfsw
         * zipcode :
         * mobile : 111
         * email :
         * shippingCode :
         * shippingName :
         * shippingPrice : 30.0
         * payCode : 8
         * payName : 余额
         * invoiceTitle :
         * taxpayer :
         * goodsPrice : 0.0
         * userMoney : 0.0
         * couponPrice : 0.0
         * Stringegral : 0
         * StringegralMoney : 0.0
         * orderAmount : 0.0
         * totalAmount : 0.0
         * paidMoney : 0.0
         * transactionId :
         * transactionIdZx : null
         * promId : 0
         * promType : 0
         * orderPromId : 0
         * orderPromAmount : 0.0
         * discount : 0.0
         * userNote :
         * adminNote :
         * parentSn :
         * storeId : 1
         * isComment : 0
         * shopId : 0
         * deleted : 0
         * orderStatisId : 0
         * kingStatus : 0
         * kingId : 0
         * kingNum : null
         * kingDel : 0
         * kingSaleNo : null
         * thirdStoreId : 0
         * kingSendNo : null
         * tiancaiSendNo : null
         * sync : null
         * tiancaiId : 0
         * needPay : null
         * isPay : 0
         * confirmTime : 2020-08-29 10:09:11
         * payTime : 2020-08-29 10:09:11
         * addTime : 2020-08-29 10:09:05
         * shippingTime : null
         * finishTime : null
         * cancelTime : null
         * posKingFlag : 1
         * posKingStock : ZJD001
         * exhibiName : null
         * exhibiContacts : null
         * exhibiPhone : null
         * deliverMan :
         * deliverPhone :
         * userType : 02
         * isSplit : 0
         * auditStatus : 0
         * storeName : null
         * suppliersId : null
         * userName : null
         * followUserName : null
         * orderGoodsList : [{"searchValue":null,"createBy":null,"createTime":"2020-08-29 10:09:05","updateBy":null,"updateTime":null,"remark":null,"params":{},"startTime":null,"endTime":null,"recId":1082,"orderId":0,"goodsId":58,"goodsName":"m m m","goodsRemark":null,"goodsSn":"HDG00000206","goodsNum":1,"finalPrice":15,"goodsPrice":15,"itemPrice":15,"costPrice":0,"memberGoodsPrice":0,"giveStringegral":0,"specKey":"","specKeyName":"瓶","barCode":"","isComment":0,"promType":"0","promId":0,"isSend":0,"deliveryId":0,"sku":"01009","itemNo":null,"storeId":0,"commission":0,"isCheckout":0,"deleted":0,"distribut":0,"shopId":0,"thirdStoreId":0,"thumbnailImageUrl":"http://47.92.11.135:8001/group1/M00/00/6B/wKgAtV8qIlCAAm5VAAGd9ydqlUA536_200x150.jpg","suppliersId":69,"unit":null,"storeUnit":null,"priceUnit":null,"orderSn":"20200829100905577056","subTotal":15,"goodsContent":"","orderSonSn":"2020082910090559001401","receivedNum":null,"isDifference":null,"examineState":null,"rank":null},{"searchValue":null,"createBy":null,"createTime":"2020-08-29 10:09:05","updateBy":null,"updateTime":null,"remark":null,"params":{},"startTime":null,"endTime":null,"recId":1083,"orderId":0,"goodsId":74,"goodsName":"薯片","goodsRemark":null,"goodsSn":"HDG00000222","goodsNum":1,"finalPrice":10,"goodsPrice":10,"itemPrice":10,"costPrice":0,"memberGoodsPrice":0,"giveStringegral":0,"specKey":"","specKeyName":"15","barCode":"","isComment":0,"promType":"0","promId":0,"isSend":0,"deliveryId":0,"sku":"CH4954","itemNo":null,"storeId":0,"commission":0,"isCheckout":0,"deleted":0,"distribut":0,"shopId":0,"thirdStoreId":0,"thumbnailImageUrl":"http://47.92.11.135:8001/group1/M00/00/85/wKgAtV8-HlyAMfN9AABXyLnKTfs373_200x150.jpg","suppliersId":86,"unit":null,"storeUnit":null,"priceUnit":null,"orderSn":"20200829100905577056","subTotal":10,"goodsContent":"","orderSonSn":"2020082910090560260153","receivedNum":null,"isDifference":null,"examineState":null,"rank":null}]
         * goodsTypeNum : null
         * addTimeStart : null
         * addTimeEnd : null
         * invoiceStatus : null
         * fullAddress : 北京市北京市东城区Asfsw
         * pickThe : 0
         * sequence : 0
         * clerkId : 0
         * deliveryMethod : null
         * isDifference : null
         * sonOrderAmount : null
         * selfTime : null
         * sonTotalAmount : null
         * overTime : null
         * supplier : null
         * orderSonSnList : [{"searchValue":null,"createBy":null,"createTime":null,"updateBy":null,"updateTime":null,"remark":null,"params":{},"startTime":null,"endTime":null,"id":75,"orderSn":"20200829100905577056","orderSonSn":"2020082910090559001401","isDifference":1,"deliveryMethod":2,"selfTime":"2020-08-30 am","sonTotalAmount":15,"sonOrderAmount":null,"orderStatus":1,"shippingTime":null,"suppliersId":69,"overTime":null,"suppliersName":"薯片","orderRemark":null,"cartsIds":null,"orderGoodsList":[{"searchValue":null,"createBy":null,"createTime":"2020-08-29 10:09:05","updateBy":null,"updateTime":null,"remark":null,"params":{},"startTime":null,"endTime":null,"recId":1082,"orderId":0,"goodsId":58,"goodsName":"m m m","goodsRemark":null,"goodsSn":"HDG00000206","goodsNum":1,"finalPrice":15,"goodsPrice":15,"itemPrice":15,"costPrice":0,"memberGoodsPrice":0,"giveStringegral":0,"specKey":"","specKeyName":"瓶","barCode":"","isComment":0,"promType":"0","promId":0,"isSend":0,"deliveryId":0,"sku":"01009","itemNo":null,"storeId":0,"commission":0,"isCheckout":0,"deleted":0,"distribut":0,"shopId":0,"thirdStoreId":0,"thumbnailImageUrl":"http://47.92.11.135:8001/group1/M00/00/6B/wKgAtV8qIlCAAm5VAAGd9ydqlUA536_200x150.jpg","suppliersId":69,"unit":null,"storeUnit":null,"priceUnit":null,"orderSn":"20200829100905577056","subTotal":15,"goodsContent":"","orderSonSn":"2020082910090559001401","receivedNum":null,"isDifference":null,"examineState":null,"rank":null}],"cancelTime":null,"orderId":null,"freight":"0","rank":null}]
         * supplierName : null
         * rank : null
         * deliverAddress : null
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
        private String orderId;
        private String orderSn;
        private String orderSonSn;
        private String goodsName;
        private String userId;
        private String masterOrderSn;
        private String orderStatus;
        private String payStatus;
        private String shippingStatus;
        private String consignee;
        private String country;
        private String province;
        private String city;
        private String district;
        private String twon;
        private String address;
        private String zipcode;
        private String mobile;
        private String email;
        private String shippingCode;
        private String shippingName;
        private String shippingPrice;
        private String payCode;
        private String payName;
        private String invoiceTitle;
        private String taxpayer;
        private String goodsPrice;
        private String userMoney;
        private String couponPrice;
        private String Stringegral;
        private String StringegralMoney;
        private String orderAmount;
        private String totalAmount;
        private String paidMoney;
        private String transactionId;
        private String transactionIdZx;
        private String promId;
        private String promType;
        private String orderPromId;
        private String orderPromAmount;
        private String discount;
        private String userNote;
        private String adminNote;
        private String parentSn;
        private String storeId;
        private String isComment;
        private String shopId;
        private String deleted;
        private String orderStatisId;
        private String kingStatus;
        private String kingId;
        private String kingNum;
        private String kingDel;
        private String kingSaleNo;
        private String thirdStoreId;
        private String kingSendNo;
        private String tiancaiSendNo;
        private String sync;
        private String tiancaiId;
        private String needPay;
        private String isPay;
        private String confirmTime;
        private String payTime;
        private String addTime;
        private String shippingTime;
        private String finishTime;
        private String cancelTime;
        private String posKingFlag;
        private String posKingStock;
        private String exhibiName;
        private String exhibiContacts;
        private String exhibiPhone;
        private String deliverMan;
        private String deliverPhone;
        private String userType;
        private String isSplit;
        private String auditStatus;
        private String storeName;
        private String suppliersId;
        private String userName;
        private String followUserName;
        private String goodsTypeNum;
        private String addTimeStart;
        private String addTimeEnd;
        private String invoiceStatus;
        private String fullAddress;
        private String pickThe;
        private String sequence;
        private String clerkId;
        private String deliveryMethod;
        private String isDifference;
        private String sonOrderAmount;
        private String selfTime;
        private String sonTotalAmount;
        private String overTime;
        private String supplier;
        private String supplierName;
        private String rank;
        private String deliverAddress;
        private List<OrderGoodsListBean> orderGoodsList;
        private List<OrderSonSnListBean> orderSonSnList;
        private String dlyoPickupCode;
        private String isSplitOrder;
        private String countdownTime;//倒计时

        public String getIsSplitOrder() {
            return isSplitOrder;
        }

        public void setIsSplitOrder(String isSplitOrder) {
            this.isSplitOrder = isSplitOrder;
        }

        public String getDlyoPickupCode() {
            return dlyoPickupCode;
        }

        public void setDlyoPickupCode(String dlyoPickupCode) {
            this.dlyoPickupCode = dlyoPickupCode;
        }

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

        public String getOrderId() {
            return orderId;
        }

        public void setOrderId(String orderId) {
            this.orderId = orderId;
        }

        public String getOrderSn() {
            return orderSn;
        }

        public void setOrderSn(String orderSn) {
            this.orderSn = orderSn;
        }

        public String getOrderSonSn() {
            return orderSonSn;
        }

        public void setOrderSonSn(String orderSonSn) {
            this.orderSonSn = orderSonSn;
        }

        public String getGoodsName() {
            return goodsName;
        }

        public void setGoodsName(String goodsName) {
            this.goodsName = goodsName;
        }

        public String getUserId() {
            return userId;
        }

        public void setUserId(String userId) {
            this.userId = userId;
        }

        public String getMasterOrderSn() {
            return masterOrderSn;
        }

        public void setMasterOrderSn(String masterOrderSn) {
            this.masterOrderSn = masterOrderSn;
        }

        public String getOrderStatus() {
            return orderStatus;
        }

        public void setOrderStatus(String orderStatus) {
            this.orderStatus = orderStatus;
        }

        public String getPayStatus() {
            return payStatus;
        }

        public void setPayStatus(String payStatus) {
            this.payStatus = payStatus;
        }

        public String getShippingStatus() {
            return shippingStatus;
        }

        public void setShippingStatus(String shippingStatus) {
            this.shippingStatus = shippingStatus;
        }

        public String getConsignee() {
            return consignee;
        }

        public void setConsignee(String consignee) {
            this.consignee = consignee;
        }

        public String getCountry() {
            return country;
        }

        public void setCountry(String country) {
            this.country = country;
        }

        public String getProvince() {
            return province;
        }

        public void setProvince(String province) {
            this.province = province;
        }

        public String getCity() {
            return city;
        }

        public void setCity(String city) {
            this.city = city;
        }

        public String getDistrict() {
            return district;
        }

        public void setDistrict(String district) {
            this.district = district;
        }

        public String getTwon() {
            return twon;
        }

        public void setTwon(String twon) {
            this.twon = twon;
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public String getZipcode() {
            return zipcode;
        }

        public void setZipcode(String zipcode) {
            this.zipcode = zipcode;
        }

        public String getMobile() {
            return mobile;
        }

        public void setMobile(String mobile) {
            this.mobile = mobile;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public String getShippingCode() {
            return shippingCode;
        }

        public void setShippingCode(String shippingCode) {
            this.shippingCode = shippingCode;
        }

        public String getShippingName() {
            return shippingName;
        }

        public void setShippingName(String shippingName) {
            this.shippingName = shippingName;
        }

        public String getShippingPrice() {
            return shippingPrice;
        }

        public void setShippingPrice(String shippingPrice) {
            this.shippingPrice = shippingPrice;
        }

        public String getPayCode() {
            return payCode;
        }

        public void setPayCode(String payCode) {
            this.payCode = payCode;
        }

        public String getPayName() {
            return payName;
        }

        public void setPayName(String payName) {
            this.payName = payName;
        }

        public String getInvoiceTitle() {
            return invoiceTitle;
        }

        public void setInvoiceTitle(String invoiceTitle) {
            this.invoiceTitle = invoiceTitle;
        }

        public String getTaxpayer() {
            return taxpayer;
        }

        public void setTaxpayer(String taxpayer) {
            this.taxpayer = taxpayer;
        }

        public String getGoodsPrice() {
            return goodsPrice;
        }

        public void setGoodsPrice(String goodsPrice) {
            this.goodsPrice = goodsPrice;
        }

        public String getUserMoney() {
            return userMoney;
        }

        public void setUserMoney(String userMoney) {
            this.userMoney = userMoney;
        }

        public String getCouponPrice() {
            return couponPrice;
        }

        public void setCouponPrice(String couponPrice) {
            this.couponPrice = couponPrice;
        }

        public String getStringegral() {
            return Stringegral;
        }

        public void setStringegral(String Stringegral) {
            this.Stringegral = Stringegral;
        }

        public String getStringegralMoney() {
            return StringegralMoney;
        }

        public void setStringegralMoney(String StringegralMoney) {
            this.StringegralMoney = StringegralMoney;
        }

        public String getOrderAmount() {
            return orderAmount;
        }

        public void setOrderAmount(String orderAmount) {
            this.orderAmount = orderAmount;
        }

        public String getTotalAmount() {
            return totalAmount;
        }

        public void setTotalAmount(String totalAmount) {
            this.totalAmount = totalAmount;
        }

        public String getPaidMoney() {
            return paidMoney;
        }

        public void setPaidMoney(String paidMoney) {
            this.paidMoney = paidMoney;
        }

        public String getTransactionId() {
            return transactionId;
        }

        public void setTransactionId(String transactionId) {
            this.transactionId = transactionId;
        }

        public String getTransactionIdZx() {
            return transactionIdZx;
        }

        public void setTransactionIdZx(String transactionIdZx) {
            this.transactionIdZx = transactionIdZx;
        }

        public String getPromId() {
            return promId;
        }

        public void setPromId(String promId) {
            this.promId = promId;
        }

        public String getPromType() {
            return promType;
        }

        public void setPromType(String promType) {
            this.promType = promType;
        }

        public String getOrderPromId() {
            return orderPromId;
        }

        public void setOrderPromId(String orderPromId) {
            this.orderPromId = orderPromId;
        }

        public String getOrderPromAmount() {
            return orderPromAmount;
        }

        public void setOrderPromAmount(String orderPromAmount) {
            this.orderPromAmount = orderPromAmount;
        }

        public String getDiscount() {
            return discount;
        }

        public void setDiscount(String discount) {
            this.discount = discount;
        }

        public String getUserNote() {
            return userNote;
        }

        public void setUserNote(String userNote) {
            this.userNote = userNote;
        }

        public String getAdminNote() {
            return adminNote;
        }

        public void setAdminNote(String adminNote) {
            this.adminNote = adminNote;
        }

        public String getParentSn() {
            return parentSn;
        }

        public void setParentSn(String parentSn) {
            this.parentSn = parentSn;
        }

        public String getStoreId() {
            return storeId;
        }

        public void setStoreId(String storeId) {
            this.storeId = storeId;
        }

        public String getIsComment() {
            return isComment;
        }

        public void setIsComment(String isComment) {
            this.isComment = isComment;
        }

        public String getShopId() {
            return shopId;
        }

        public void setShopId(String shopId) {
            this.shopId = shopId;
        }

        public String getDeleted() {
            return deleted;
        }

        public void setDeleted(String deleted) {
            this.deleted = deleted;
        }

        public String getOrderStatisId() {
            return orderStatisId;
        }

        public void setOrderStatisId(String orderStatisId) {
            this.orderStatisId = orderStatisId;
        }

        public String getKingStatus() {
            return kingStatus;
        }

        public void setKingStatus(String kingStatus) {
            this.kingStatus = kingStatus;
        }

        public String getKingId() {
            return kingId;
        }

        public void setKingId(String kingId) {
            this.kingId = kingId;
        }

        public String getKingNum() {
            return kingNum;
        }

        public void setKingNum(String kingNum) {
            this.kingNum = kingNum;
        }

        public String getKingDel() {
            return kingDel;
        }

        public void setKingDel(String kingDel) {
            this.kingDel = kingDel;
        }

        public String getKingSaleNo() {
            return kingSaleNo;
        }

        public void setKingSaleNo(String kingSaleNo) {
            this.kingSaleNo = kingSaleNo;
        }

        public String getThirdStoreId() {
            return thirdStoreId;
        }

        public void setThirdStoreId(String thirdStoreId) {
            this.thirdStoreId = thirdStoreId;
        }

        public String getKingSendNo() {
            return kingSendNo;
        }

        public void setKingSendNo(String kingSendNo) {
            this.kingSendNo = kingSendNo;
        }

        public String getTiancaiSendNo() {
            return tiancaiSendNo;
        }

        public void setTiancaiSendNo(String tiancaiSendNo) {
            this.tiancaiSendNo = tiancaiSendNo;
        }

        public String getSync() {
            return sync;
        }

        public void setSync(String sync) {
            this.sync = sync;
        }

        public String getTiancaiId() {
            return tiancaiId;
        }

        public void setTiancaiId(String tiancaiId) {
            this.tiancaiId = tiancaiId;
        }

        public String getNeedPay() {
            return needPay;
        }

        public void setNeedPay(String needPay) {
            this.needPay = needPay;
        }

        public String getIsPay() {
            return isPay;
        }

        public void setIsPay(String isPay) {
            this.isPay = isPay;
        }

        public String getConfirmTime() {
            return confirmTime;
        }

        public void setConfirmTime(String confirmTime) {
            this.confirmTime = confirmTime;
        }

        public String getPayTime() {
            return payTime;
        }

        public void setPayTime(String payTime) {
            this.payTime = payTime;
        }

        public String getAddTime() {
            return addTime;
        }

        public void setAddTime(String addTime) {
            this.addTime = addTime;
        }

        public String getShippingTime() {
            return shippingTime;
        }

        public void setShippingTime(String shippingTime) {
            this.shippingTime = shippingTime;
        }

        public String getFinishTime() {
            return finishTime;
        }

        public void setFinishTime(String finishTime) {
            this.finishTime = finishTime;
        }

        public String getCancelTime() {
            return cancelTime;
        }

        public void setCancelTime(String cancelTime) {
            this.cancelTime = cancelTime;
        }

        public String getPosKingFlag() {
            return posKingFlag;
        }

        public void setPosKingFlag(String posKingFlag) {
            this.posKingFlag = posKingFlag;
        }

        public String getPosKingStock() {
            return posKingStock;
        }

        public void setPosKingStock(String posKingStock) {
            this.posKingStock = posKingStock;
        }

        public String getExhibiName() {
            return exhibiName;
        }

        public void setExhibiName(String exhibiName) {
            this.exhibiName = exhibiName;
        }

        public String getExhibiContacts() {
            return exhibiContacts;
        }

        public void setExhibiContacts(String exhibiContacts) {
            this.exhibiContacts = exhibiContacts;
        }

        public String getExhibiPhone() {
            return exhibiPhone;
        }

        public void setExhibiPhone(String exhibiPhone) {
            this.exhibiPhone = exhibiPhone;
        }

        public String getDeliverMan() {
            return deliverMan;
        }

        public void setDeliverMan(String deliverMan) {
            this.deliverMan = deliverMan;
        }

        public String getDeliverPhone() {
            return deliverPhone;
        }

        public void setDeliverPhone(String deliverPhone) {
            this.deliverPhone = deliverPhone;
        }

        public String getUserType() {
            return userType;
        }

        public void setUserType(String userType) {
            this.userType = userType;
        }

        public String getIsSplit() {
            return isSplit;
        }

        public void setIsSplit(String isSplit) {
            this.isSplit = isSplit;
        }

        public String getAuditStatus() {
            return auditStatus;
        }

        public void setAuditStatus(String auditStatus) {
            this.auditStatus = auditStatus;
        }

        public String getStoreName() {
            return storeName;
        }

        public void setStoreName(String storeName) {
            this.storeName = storeName;
        }

        public String getSuppliersId() {
            return suppliersId;
        }

        public void setSuppliersId(String suppliersId) {
            this.suppliersId = suppliersId;
        }

        public String getUserName() {
            return userName;
        }

        public void setUserName(String userName) {
            this.userName = userName;
        }

        public String getFollowUserName() {
            return followUserName;
        }

        public void setFollowUserName(String followUserName) {
            this.followUserName = followUserName;
        }

        public String getGoodsTypeNum() {
            return goodsTypeNum;
        }

        public void setGoodsTypeNum(String goodsTypeNum) {
            this.goodsTypeNum = goodsTypeNum;
        }

        public String getAddTimeStart() {
            return addTimeStart;
        }

        public void setAddTimeStart(String addTimeStart) {
            this.addTimeStart = addTimeStart;
        }

        public String getAddTimeEnd() {
            return addTimeEnd;
        }

        public void setAddTimeEnd(String addTimeEnd) {
            this.addTimeEnd = addTimeEnd;
        }

        public String getInvoiceStatus() {
            return invoiceStatus;
        }

        public void setInvoiceStatus(String invoiceStatus) {
            this.invoiceStatus = invoiceStatus;
        }

        public String getFullAddress() {
            return fullAddress;
        }

        public void setFullAddress(String fullAddress) {
            this.fullAddress = fullAddress;
        }

        public String getPickThe() {
            return pickThe;
        }

        public void setPickThe(String pickThe) {
            this.pickThe = pickThe;
        }

        public String getSequence() {
            return sequence;
        }

        public void setSequence(String sequence) {
            this.sequence = sequence;
        }

        public String getClerkId() {
            return clerkId;
        }

        public void setClerkId(String clerkId) {
            this.clerkId = clerkId;
        }

        public String getDeliveryMethod() {
            return deliveryMethod;
        }

        public void setDeliveryMethod(String deliveryMethod) {
            this.deliveryMethod = deliveryMethod;
        }

        public String getIsDifference() {
            return isDifference;
        }

        public void setIsDifference(String isDifference) {
            this.isDifference = isDifference;
        }

        public String getSonOrderAmount() {
            return sonOrderAmount;
        }

        public void setSonOrderAmount(String sonOrderAmount) {
            this.sonOrderAmount = sonOrderAmount;
        }

        public String getSelfTime() {
            return selfTime;
        }

        public void setSelfTime(String selfTime) {
            this.selfTime = selfTime;
        }

        public String getSonTotalAmount() {
            return sonTotalAmount;
        }

        public void setSonTotalAmount(String sonTotalAmount) {
            this.sonTotalAmount = sonTotalAmount;
        }

        public String getOverTime() {
            return overTime;
        }

        public void setOverTime(String overTime) {
            this.overTime = overTime;
        }

        public String getSupplier() {
            return supplier;
        }

        public void setSupplier(String supplier) {
            this.supplier = supplier;
        }

        public String getSupplierName() {
            return supplierName;
        }

        public void setSupplierName(String supplierName) {
            this.supplierName = supplierName;
        }

        public String getRank() {
            return rank;
        }

        public void setRank(String rank) {
            this.rank = rank;
        }

        public String getDeliverAddress() {
            return deliverAddress;
        }

        public void setDeliverAddress(String deliverAddress) {
            this.deliverAddress = deliverAddress;
        }

        public List<OrderGoodsListBean> getOrderGoodsList() {
            return orderGoodsList;
        }

        public void setOrderGoodsList(List<OrderGoodsListBean> orderGoodsList) {
            this.orderGoodsList = orderGoodsList;
        }

        public List<OrderSonSnListBean> getOrderSonSnList() {
            return orderSonSnList;
        }

        public void setOrderSonSnList(List<OrderSonSnListBean> orderSonSnList) {
            this.orderSonSnList = orderSonSnList;
        }

        public String getCountdownTime() {
            return TextUtils.isEmpty(countdownTime)?"0":countdownTime;
        }

        public void setCountdownTime(String countdownTime) {
            this.countdownTime = countdownTime;
        }

        public static class ParamsBean implements Serializable {
        }

        public static class OrderGoodsListBean implements Serializable {
            /**
             * searchValue : null
             * createBy : null
             * createTime : 2020-08-29 10:09:05
             * updateBy : null
             * updateTime : null
             * remark : null
             * params : {}
             * startTime : null
             * endTime : null
             * recId : 1082
             * orderId : 0
             * goodsId : 58
             * goodsName : m m m
             * goodsRemark : null
             * goodsSn : HDG00000206
             * goodsNum : 1
             * finalPrice : 15.0
             * goodsPrice : 15.0
             * itemPrice : 15.0
             * costPrice : 0.0
             * memberGoodsPrice : 0.0
             * giveStringegral : 0
             * specKey :
             * specKeyName : 瓶
             * barCode :
             * isComment : 0
             * promType : 0
             * promId : 0
             * isSend : 0
             * deliveryId : 0
             * sku : 01009
             * itemNo : null
             * storeId : 0
             * commission : 0
             * isCheckout : 0
             * deleted : 0
             * distribut : 0.0
             * shopId : 0
             * thirdStoreId : 0
             * thumbnailImageUrl : http://47.92.11.135:8001/group1/M00/00/6B/wKgAtV8qIlCAAm5VAAGd9ydqlUA536_200x150.jpg
             * suppliersId : 69
             * unit : null
             * storeUnit : null
             * priceUnit : null
             * orderSn : 20200829100905577056
             * subTotal : 15
             * goodsContent :
             * orderSonSn : 2020082910090559001401
             * receivedNum : null
             * isDifference : null
             * examineState : null
             * rank : null
             */

            private String searchValue;
            private String createBy;
            private String createTime;
            private String updateBy;
            private String updateTime;
            private String remark;
            private ParamsBeanX params;
            private String startTime;
            private String endTime;
            private String recId;
            private String orderId;
            private String goodsId;
            private String goodsName;
            private String goodsRemark;
            private String goodsSn;
            private String goodsNum;
            private String finalPrice;
            private String goodsPrice;
            private String itemPrice;
            private String costPrice;
            private String memberGoodsPrice;
            private String giveStringegral;
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
            private String suppliersId;
            private String unit;
            private String storeUnit;
            private String priceUnit;
            private String orderSn;
            private String subTotal;
            private String goodsContent;
            private String orderSonSn;
            private String receivedNum;
            private String isDifference;
            private String examineState;
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

            public ParamsBeanX getParams() {
                return params;
            }

            public void setParams(ParamsBeanX params) {
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

            public String getRecId() {
                return recId;
            }

            public void setRecId(String recId) {
                this.recId = recId;
            }

            public String getOrderId() {
                return orderId;
            }

            public void setOrderId(String orderId) {
                this.orderId = orderId;
            }

            public String getGoodsId() {
                return goodsId;
            }

            public void setGoodsId(String goodsId) {
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

            public String getGoodsNum() {
                return goodsNum;
            }

            public void setGoodsNum(String goodsNum) {
                this.goodsNum = goodsNum;
            }

            public String getFinalPrice() {
                return finalPrice;
            }

            public void setFinalPrice(String finalPrice) {
                this.finalPrice = finalPrice;
            }

            public String getGoodsPrice() {
                return goodsPrice;
            }

            public void setGoodsPrice(String goodsPrice) {
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

            public String getGiveStringegral() {
                return giveStringegral;
            }

            public void setGiveStringegral(String giveStringegral) {
                this.giveStringegral = giveStringegral;
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

            public String getSuppliersId() {
                return suppliersId;
            }

            public void setSuppliersId(String suppliersId) {
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

            public String getReceivedNum() {
                return receivedNum;
            }

            public void setReceivedNum(String receivedNum) {
                this.receivedNum = receivedNum;
            }

            public String getIsDifference() {
                return isDifference;
            }

            public void setIsDifference(String isDifference) {
                this.isDifference = isDifference;
            }

            public String getExamineState() {
                return examineState;
            }

            public void setExamineState(String examineState) {
                this.examineState = examineState;
            }

            public String getRank() {
                return rank;
            }

            public void setRank(String rank) {
                this.rank = rank;
            }

            public static class ParamsBeanX implements Serializable {
            }
        }

        public static class OrderSonSnListBean implements Serializable {
            /**
             * searchValue : null
             * createBy : null
             * createTime : null
             * updateBy : null
             * updateTime : null
             * remark : null
             * params : {}
             * startTime : null
             * endTime : null
             * id : 75
             * orderSn : 20200829100905577056
             * orderSonSn : 2020082910090559001401
             * isDifference : 1
             * deliveryMethod : 2
             * selfTime : 2020-08-30 am
             * sonTotalAmount : 15
             * sonOrderAmount : null
             * orderStatus : 1
             * shippingTime : null
             * suppliersId : 69
             * overTime : null
             * suppliersName : 薯片
             * orderRemark : null
             * cartsIds : null
             * orderGoodsList : [{"searchValue":null,"createBy":null,"createTime":"2020-08-29 10:09:05","updateBy":null,"updateTime":null,"remark":null,"params":{},"startTime":null,"endTime":null,"recId":1082,"orderId":0,"goodsId":58,"goodsName":"m m m","goodsRemark":null,"goodsSn":"HDG00000206","goodsNum":1,"finalPrice":15,"goodsPrice":15,"itemPrice":15,"costPrice":0,"memberGoodsPrice":0,"giveStringegral":0,"specKey":"","specKeyName":"瓶","barCode":"","isComment":0,"promType":"0","promId":0,"isSend":0,"deliveryId":0,"sku":"01009","itemNo":null,"storeId":0,"commission":0,"isCheckout":0,"deleted":0,"distribut":0,"shopId":0,"thirdStoreId":0,"thumbnailImageUrl":"http://47.92.11.135:8001/group1/M00/00/6B/wKgAtV8qIlCAAm5VAAGd9ydqlUA536_200x150.jpg","suppliersId":69,"unit":null,"storeUnit":null,"priceUnit":null,"orderSn":"20200829100905577056","subTotal":15,"goodsContent":"","orderSonSn":"2020082910090559001401","receivedNum":null,"isDifference":null,"examineState":null,"rank":null}]
             * cancelTime : null
             * orderId : null
             * freight : 0
             * rank : null
             */

            private String searchValue;
            private String createBy;
            private String createTime;
            private String updateBy;
            private String updateTime;
            private String remark;
            private ParamsBeanXX params;
            private String startTime;
            private String endTime;
            private String id;
            private String orderSn;
            private String orderSonSn;
            private String isDifference;
            private String deliveryMethod;
            private String selfTime;
            private String sonTotalAmount;
            private String sonOrderAmount;
            private String orderStatus;
            private String shippingTime;
            private String suppliersId;
            private String overTime;
            private String suppliersName;
            private String orderRemark;
            private String cartsIds;
            private String cancelTime;
            private String orderId;
            private String freight;
            private String rank;
            private String exhibiName;
            private List<OrderGoodsListBeanX> orderGoodsList;

            public String getExhibiName() {
                return exhibiName;
            }

            public void setExhibiName(String exhibiName) {
                this.exhibiName = exhibiName;
            }

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

            public ParamsBeanXX getParams() {
                return params;
            }

            public void setParams(ParamsBeanXX params) {
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

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public String getOrderSn() {
                return orderSn;
            }

            public void setOrderSn(String orderSn) {
                this.orderSn = orderSn;
            }

            public String getOrderSonSn() {
                return orderSonSn;
            }

            public void setOrderSonSn(String orderSonSn) {
                this.orderSonSn = orderSonSn;
            }

            public String getIsDifference() {
                return isDifference;
            }

            public void setIsDifference(String isDifference) {
                this.isDifference = isDifference;
            }

            public String getDeliveryMethod() {
                return deliveryMethod;
            }

            public void setDeliveryMethod(String deliveryMethod) {
                this.deliveryMethod = deliveryMethod;
            }

            public String getSelfTime() {
                return selfTime;
            }

            public void setSelfTime(String selfTime) {
                this.selfTime = selfTime;
            }

            public String getSonTotalAmount() {
                return sonTotalAmount;
            }

            public void setSonTotalAmount(String sonTotalAmount) {
                this.sonTotalAmount = sonTotalAmount;
            }

            public String getSonOrderAmount() {
                return sonOrderAmount;
            }

            public void setSonOrderAmount(String sonOrderAmount) {
                this.sonOrderAmount = sonOrderAmount;
            }

            public String getOrderStatus() {
                return orderStatus;
            }

            public void setOrderStatus(String orderStatus) {
                this.orderStatus = orderStatus;
            }

            public String getShippingTime() {
                return shippingTime;
            }

            public void setShippingTime(String shippingTime) {
                this.shippingTime = shippingTime;
            }

            public String getSuppliersId() {
                return suppliersId;
            }

            public void setSuppliersId(String suppliersId) {
                this.suppliersId = suppliersId;
            }

            public String getOverTime() {
                return overTime;
            }

            public void setOverTime(String overTime) {
                this.overTime = overTime;
            }

            public String getSuppliersName() {
                return suppliersName;
            }

            public void setSuppliersName(String suppliersName) {
                this.suppliersName = suppliersName;
            }

            public String getOrderRemark() {
                return orderRemark;
            }

            public void setOrderRemark(String orderRemark) {
                this.orderRemark = orderRemark;
            }

            public String getCartsIds() {
                return cartsIds;
            }

            public void setCartsIds(String cartsIds) {
                this.cartsIds = cartsIds;
            }

            public String getCancelTime() {
                return cancelTime;
            }

            public void setCancelTime(String cancelTime) {
                this.cancelTime = cancelTime;
            }

            public String getOrderId() {
                return orderId;
            }

            public void setOrderId(String orderId) {
                this.orderId = orderId;
            }

            public String getFreight() {
                return freight;
            }

            public void setFreight(String freight) {
                this.freight = freight;
            }

            public String getRank() {
                return rank;
            }

            public void setRank(String rank) {
                this.rank = rank;
            }

            public List<OrderGoodsListBeanX> getOrderGoodsList() {
                return orderGoodsList;
            }

            public void setOrderGoodsList(List<OrderGoodsListBeanX> orderGoodsList) {
                this.orderGoodsList = orderGoodsList;
            }

            public static class ParamsBeanXX implements Serializable {
            }

            public static class OrderGoodsListBeanX implements Serializable {
                /**
                 * searchValue : null
                 * createBy : null
                 * createTime : 2020-08-29 10:09:05
                 * updateBy : null
                 * updateTime : null
                 * remark : null
                 * params : {}
                 * startTime : null
                 * endTime : null
                 * recId : 1082
                 * orderId : 0
                 * goodsId : 58
                 * goodsName : m m m
                 * goodsRemark : null
                 * goodsSn : HDG00000206
                 * goodsNum : 1
                 * finalPrice : 15.0
                 * goodsPrice : 15.0
                 * itemPrice : 15.0
                 * costPrice : 0.0
                 * memberGoodsPrice : 0.0
                 * giveStringegral : 0
                 * specKey :
                 * specKeyName : 瓶
                 * barCode :
                 * isComment : 0
                 * promType : 0
                 * promId : 0
                 * isSend : 0
                 * deliveryId : 0
                 * sku : 01009
                 * itemNo : null
                 * storeId : 0
                 * commission : 0
                 * isCheckout : 0
                 * deleted : 0
                 * distribut : 0.0
                 * shopId : 0
                 * thirdStoreId : 0
                 * thumbnailImageUrl : http://47.92.11.135:8001/group1/M00/00/6B/wKgAtV8qIlCAAm5VAAGd9ydqlUA536_200x150.jpg
                 * suppliersId : 69
                 * unit : null
                 * storeUnit : null
                 * priceUnit : null
                 * orderSn : 20200829100905577056
                 * subTotal : 15
                 * goodsContent :
                 * orderSonSn : 2020082910090559001401
                 * receivedNum : null
                 * isDifference : null
                 * examineState : null
                 * rank : null
                 */

                private String searchValue;
                private String createBy;
                private String createTime;
                private String updateBy;
                private String updateTime;
                private String remark;
                private ParamsBeanXXX params;
                private String startTime;
                private String endTime;
                private String recId;
                private String orderId;
                private String goodsId;
                private String goodsName;
                private String goodsRemark;
                private String goodsSn;
                private String goodsNum;
                private String finalPrice;
                private String goodsPrice;
                private String itemPrice;
                private String costPrice;
                private String memberGoodsPrice;
                private String giveStringegral;
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
                private String suppliersId;
                private String unit;
                private String storeUnit;
                private String priceUnit;
                private String orderSn;
                private String subTotal;
                private String goodsContent;
                private String orderSonSn;
                private String receivedNum;
                private String isDifference;
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

                public ParamsBeanXXX getParams() {
                    return params;
                }

                public void setParams(ParamsBeanXXX params) {
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

                public String getRecId() {
                    return recId;
                }

                public void setRecId(String recId) {
                    this.recId = recId;
                }

                public String getOrderId() {
                    return orderId;
                }

                public void setOrderId(String orderId) {
                    this.orderId = orderId;
                }

                public String getGoodsId() {
                    return goodsId;
                }

                public void setGoodsId(String goodsId) {
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

                public String getGoodsNum() {
                    return goodsNum;
                }

                public void setGoodsNum(String goodsNum) {
                    this.goodsNum = goodsNum;
                }

                public String getFinalPrice() {
                    return finalPrice;
                }

                public void setFinalPrice(String finalPrice) {
                    this.finalPrice = finalPrice;
                }

                public String getGoodsPrice() {
                    return goodsPrice;
                }

                public void setGoodsPrice(String goodsPrice) {
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

                public String getGiveStringegral() {
                    return giveStringegral;
                }

                public void setGiveStringegral(String giveStringegral) {
                    this.giveStringegral = giveStringegral;
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

                public String getSuppliersId() {
                    return suppliersId;
                }

                public void setSuppliersId(String suppliersId) {
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

                public String getReceivedNum() {
                    return receivedNum;
                }

                public void setReceivedNum(String receivedNum) {
                    this.receivedNum = receivedNum;
                }

                public String getIsDifference() {
                    return isDifference;
                }

                public void setIsDifference(String isDifference) {
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
                    return bigUnitNum == null ? "0" : bigUnitNum;
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
                    return smallUnitNum == null ? "0" : smallUnitNum;
                }

                public void setSmallUnitNum(String smallUnitNum) {
                    this.smallUnitNum = smallUnitNum;
                }

                public String getRank() {
                    return rank;
                }

                public void setRank(String rank) {
                    this.rank = rank;
                }

                public static class ParamsBeanXXX implements Serializable {
                }
            }
        }
    }
}
