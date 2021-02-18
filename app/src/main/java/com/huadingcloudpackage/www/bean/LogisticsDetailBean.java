package com.huadingcloudpackage.www.bean;

import java.util.List;

public  class  LogisticsDetailBean{


    private String msg;
    private int code;
    private InfoBean info;

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

    public InfoBean getInfo() {
        return info;
    }

    public void setInfo(InfoBean info) {
        this.info = info;
    }

    public static class InfoBean {
     
         

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
           

            private DriverBean driver;
            private OrderBean order;
            private List<TmsCarTrackListBean> tmsCarTrackList;
            private List<TmsOrderDetailsBean> tmsOrderDetails;
            private List<ListBean> list;

            public DriverBean getDriver() {
                return driver;
            }

            public void setDriver(DriverBean driver) {
                this.driver = driver;
            }

            public OrderBean getOrder() {
                return order;
            }

            public void setOrder(OrderBean order) {
                this.order = order;
            }

            public List<TmsCarTrackListBean> getTmsCarTrackList() {
                return tmsCarTrackList;
            }

            public void setTmsCarTrackList(List<TmsCarTrackListBean> tmsCarTrackList) {
                this.tmsCarTrackList = tmsCarTrackList;
            }

            public List<TmsOrderDetailsBean> getTmsOrderDetails() {
                return tmsOrderDetails;
            }

            public void setTmsOrderDetails(List<TmsOrderDetailsBean> tmsOrderDetails) {
                this.tmsOrderDetails = tmsOrderDetails;
            }

            public List<ListBean> getList() {
                return list;
            }

            public void setList(List<ListBean> list) {
                this.list = list;
            }

            public static class DriverBean {
                /**
                 * address : 河南省郑州市中原区金梭路64号郑州威科姆科技股份有限公司(莲花街)
                 * deliveryAddress : 河南省郑州市二七区庆丰街与陇海路
                 * name : JN司机-济南南lyz
                 * tel : 15800000006
                 */

                private String address;
                private String deliveryAddress;
                private String name;
                private String tel;

                public String getAddress() {
                    return address;
                }

                public void setAddress(String address) {
                    this.address = address;
                }

                public String getDeliveryAddress() {
                    return deliveryAddress;
                }

                public void setDeliveryAddress(String deliveryAddress) {
                    this.deliveryAddress = deliveryAddress;
                }

                public String getName() {
                    return name;
                }

                public void setName(String name) {
                    this.name = name;
                }

                public String getTel() {
                    return tel;
                }

                public void setTel(String tel) {
                    this.tel = tel;
                }
            }

            public static class OrderBean {
                /**
                 * orderType : 1
                 * assignCarNumber : 鲁A9Z0J1
                 * requiredArrivalTime : null
                 * truckerId : 337
                 * receiptNum : null
                 * keywords : null
                 * orderId : 310121
                 * counts : null
                 * assignDriverPhone : 15800000006
                 * goodsList : null
                 * warehousetel : null
                 * customerType : null
                 * sendcode : null
                 * deliveryMode : null
                 * assignStatus : 0
                 * arrivalTime : null
                 * consigneeLat : 34.735213
                 * orderIds : null
                 * invoiceNo : HDYS20200829144012
                 * ottype : null
                 * consigneeAddress : 河南省郑州市二七区庆丰街与陇海路
                 * destinationPoint : null
                 * consignee : 张芝灵
                 * consignorLon : 113.900183
                 * receiptDate : null
                 * params : {}
                 * warehouse : 普洛斯
                 * realCount : 56
                 * customerName : 锅圈食汇河南省郑州市二七区庆丰街与陇海路店410273
                 * isSh : 0
                 * transactionId : null
                 * consignorPhone : null
                 * departurePoint : null
                 * driverId : null
                 * consigneeLon : 113.652361
                 * sendType : null
                 * creater : null
                 * locationCode : null
                 * payCode : null
                 * orderNums : null
                 * freightCharges : null
                 * status : null
                 * receiptImg : null
                 * shipperId : null
                 * transportNature : null
                 * consignorCoordinate : null
                 * orderRemark : 报货APP订单,2单一起发货
                 * orderStatus : 3
                 * remark : null
                 * settlementMethod : null
                 * updater : null
                 * consignorAddress : 河南省华鼎供应链管理有限公司普洛斯仓
                 * assignDriver : JN司机-济南南lyz
                 * updateBy : null
                 * consigneePhone : 15037131939
                 * consigneeCoordinate : null
                 * requiredDeliveryTime : null
                 * startTime : null
                 * shippingTime : null
                 * isReceipt : null
                 * payName : null
                 * destinationNode : null
                 * consignor : null
                 * consigneeUnit : null
                 * consignorDetailedAddress : null
                 * amountReceivable : null
                 * updateTime : 2020-08-29 14:41:36
                 * headOfSales : null
                 * consignorLat : 34.699987
                 * createBy : null
                 * forwardingUnit : null
                 * isHc : 1
                 * createTime : 2020-08-21 15:04:31
                 * consigneeDetailedAddress : null
                 * days : null
                 * endTime : null
                 * openDate : 2020-08-21 15:04:31
                 * searchValue : null
                 * payStatus : 0
                 * customerNo : SHFHTZD243236
                 */

                private int orderType;
                private String assignCarNumber;
                private String requiredArrivalTime;
                private int truckerId;
                private String receiptNum;
                private String keywords;
                private int orderId;
                private String counts;
                private String assignDriverPhone;
                private String goodsList;
                private String warehousetel;
                private String customerType;
                private String sendcode;
                private String deliveryMode;
                private int assignStatus;
                private String arrivalTime;
                private String consigneeLat;
                private String orderIds;
                private String invoiceNo;
                private String ottype;
                private String consigneeAddress;
                private String destinationPoint;
                private String consignee;
                private String consignorLon;
                private String receiptDate;
                private ParamsBean params;
                private String warehouse;
                private int realCount;
                private String customerName;
                private int isSh;
                private String transactionId;
                private String consignorPhone;
                private String departurePoint;
                private String driverId;
                private String consigneeLon;
                private String sendType;
                private String creater;
                private String locationCode;
                private String payCode;
                private String orderNums;
                private String freightCharges;
                private String status;
                private String receiptImg;
                private String shipperId;
                private String transportNature;
                private String consignorCoordinate;
                private String orderRemark;
                private String orderStatus;
                private String remark;
                private String settlementMethod;
                private String updater;
                private String consignorAddress;
                private String assignDriver;
                private String updateBy;
                private String consigneePhone;
                private String consigneeCoordinate;
                private String requiredDeliveryTime;
                private String startTime;
                private String shippingTime;
                private String isReceipt;
                private String payName;
                private String destinationNode;
                private String consignor;
                private String consigneeUnit;
                private String consignorDetailedAddress;
                private String amountReceivable;
                private String updateTime;
                private String headOfSales;
                private String consignorLat;
                private String createBy;
                private String forwardingUnit;
                private int isHc;
                private String createTime;
                private String consigneeDetailedAddress;
                private String days;
                private String endTime;
                private String openDate;
                private String searchValue;
                private String payStatus;
                private String customerNo;

                public int getOrderType() {
                    return orderType;
                }

                public void setOrderType(int orderType) {
                    this.orderType = orderType;
                }

                public String getAssignCarNumber() {
                    return assignCarNumber;
                }

                public void setAssignCarNumber(String assignCarNumber) {
                    this.assignCarNumber = assignCarNumber;
                }

                public String getRequiredArrivalTime() {
                    return requiredArrivalTime;
                }

                public void setRequiredArrivalTime(String requiredArrivalTime) {
                    this.requiredArrivalTime = requiredArrivalTime;
                }

                public int getTruckerId() {
                    return truckerId;
                }

                public void setTruckerId(int truckerId) {
                    this.truckerId = truckerId;
                }

                public String getReceiptNum() {
                    return receiptNum;
                }

                public void setReceiptNum(String receiptNum) {
                    this.receiptNum = receiptNum;
                }

                public String getKeywords() {
                    return keywords;
                }

                public void setKeywords(String keywords) {
                    this.keywords = keywords;
                }

                public int getOrderId() {
                    return orderId;
                }

                public void setOrderId(int orderId) {
                    this.orderId = orderId;
                }

                public String getCounts() {
                    return counts;
                }

                public void setCounts(String counts) {
                    this.counts = counts;
                }

                public String getAssignDriverPhone() {
                    return assignDriverPhone;
                }

                public void setAssignDriverPhone(String assignDriverPhone) {
                    this.assignDriverPhone = assignDriverPhone;
                }

                public String getGoodsList() {
                    return goodsList;
                }

                public void setGoodsList(String goodsList) {
                    this.goodsList = goodsList;
                }

                public String getWarehousetel() {
                    return warehousetel;
                }

                public void setWarehousetel(String warehousetel) {
                    this.warehousetel = warehousetel;
                }

                public String getCustomerType() {
                    return customerType;
                }

                public void setCustomerType(String customerType) {
                    this.customerType = customerType;
                }

                public String getSendcode() {
                    return sendcode;
                }

                public void setSendcode(String sendcode) {
                    this.sendcode = sendcode;
                }

                public String getDeliveryMode() {
                    return deliveryMode;
                }

                public void setDeliveryMode(String deliveryMode) {
                    this.deliveryMode = deliveryMode;
                }

                public int getAssignStatus() {
                    return assignStatus;
                }

                public void setAssignStatus(int assignStatus) {
                    this.assignStatus = assignStatus;
                }

                public String getArrivalTime() {
                    return arrivalTime;
                }

                public void setArrivalTime(String arrivalTime) {
                    this.arrivalTime = arrivalTime;
                }

                public String getConsigneeLat() {
                    return consigneeLat;
                }

                public void setConsigneeLat(String consigneeLat) {
                    this.consigneeLat = consigneeLat;
                }

                public String getOrderIds() {
                    return orderIds;
                }

                public void setOrderIds(String orderIds) {
                    this.orderIds = orderIds;
                }

                public String getInvoiceNo() {
                    return invoiceNo;
                }

                public void setInvoiceNo(String invoiceNo) {
                    this.invoiceNo = invoiceNo;
                }

                public String getOttype() {
                    return ottype;
                }

                public void setOttype(String ottype) {
                    this.ottype = ottype;
                }

                public String getConsigneeAddress() {
                    return consigneeAddress;
                }

                public void setConsigneeAddress(String consigneeAddress) {
                    this.consigneeAddress = consigneeAddress;
                }

                public String getDestinationPoint() {
                    return destinationPoint;
                }

                public void setDestinationPoint(String destinationPoint) {
                    this.destinationPoint = destinationPoint;
                }

                public String getConsignee() {
                    return consignee;
                }

                public void setConsignee(String consignee) {
                    this.consignee = consignee;
                }

                public String getConsignorLon() {
                    return consignorLon;
                }

                public void setConsignorLon(String consignorLon) {
                    this.consignorLon = consignorLon;
                }

                public String getReceiptDate() {
                    return receiptDate;
                }

                public void setReceiptDate(String receiptDate) {
                    this.receiptDate = receiptDate;
                }

                public ParamsBean getParams() {
                    return params;
                }

                public void setParams(ParamsBean params) {
                    this.params = params;
                }

                public String getWarehouse() {
                    return warehouse;
                }

                public void setWarehouse(String warehouse) {
                    this.warehouse = warehouse;
                }

                public int getRealCount() {
                    return realCount;
                }

                public void setRealCount(int realCount) {
                    this.realCount = realCount;
                }

                public String getCustomerName() {
                    return customerName;
                }

                public void setCustomerName(String customerName) {
                    this.customerName = customerName;
                }

                public int getIsSh() {
                    return isSh;
                }

                public void setIsSh(int isSh) {
                    this.isSh = isSh;
                }

                public String getTransactionId() {
                    return transactionId;
                }

                public void setTransactionId(String transactionId) {
                    this.transactionId = transactionId;
                }

                public String getConsignorPhone() {
                    return consignorPhone;
                }

                public void setConsignorPhone(String consignorPhone) {
                    this.consignorPhone = consignorPhone;
                }

                public String getDeparturePoint() {
                    return departurePoint;
                }

                public void setDeparturePoint(String departurePoint) {
                    this.departurePoint = departurePoint;
                }

                public String getDriverId() {
                    return driverId;
                }

                public void setDriverId(String driverId) {
                    this.driverId = driverId;
                }

                public String getConsigneeLon() {
                    return consigneeLon;
                }

                public void setConsigneeLon(String consigneeLon) {
                    this.consigneeLon = consigneeLon;
                }

                public String getSendType() {
                    return sendType;
                }

                public void setSendType(String sendType) {
                    this.sendType = sendType;
                }

                public String getCreater() {
                    return creater;
                }

                public void setCreater(String creater) {
                    this.creater = creater;
                }

                public String getLocationCode() {
                    return locationCode;
                }

                public void setLocationCode(String locationCode) {
                    this.locationCode = locationCode;
                }

                public String getPayCode() {
                    return payCode;
                }

                public void setPayCode(String payCode) {
                    this.payCode = payCode;
                }

                public String getOrderNums() {
                    return orderNums;
                }

                public void setOrderNums(String orderNums) {
                    this.orderNums = orderNums;
                }

                public String getFreightCharges() {
                    return freightCharges;
                }

                public void setFreightCharges(String freightCharges) {
                    this.freightCharges = freightCharges;
                }

                public String getStatus() {
                    return status;
                }

                public void setStatus(String status) {
                    this.status = status;
                }

                public String getReceiptImg() {
                    return receiptImg;
                }

                public void setReceiptImg(String receiptImg) {
                    this.receiptImg = receiptImg;
                }

                public String getShipperId() {
                    return shipperId;
                }

                public void setShipperId(String shipperId) {
                    this.shipperId = shipperId;
                }

                public String getTransportNature() {
                    return transportNature;
                }

                public void setTransportNature(String transportNature) {
                    this.transportNature = transportNature;
                }

                public String getConsignorCoordinate() {
                    return consignorCoordinate;
                }

                public void setConsignorCoordinate(String consignorCoordinate) {
                    this.consignorCoordinate = consignorCoordinate;
                }

                public String getOrderRemark() {
                    return orderRemark;
                }

                public void setOrderRemark(String orderRemark) {
                    this.orderRemark = orderRemark;
                }

                public String getOrderStatus() {
                    return orderStatus;
                }

                public void setOrderStatus(String orderStatus) {
                    this.orderStatus = orderStatus;
                }

                public String getRemark() {
                    return remark;
                }

                public void setRemark(String remark) {
                    this.remark = remark;
                }

                public String getSettlementMethod() {
                    return settlementMethod;
                }

                public void setSettlementMethod(String settlementMethod) {
                    this.settlementMethod = settlementMethod;
                }

                public String getUpdater() {
                    return updater;
                }

                public void setUpdater(String updater) {
                    this.updater = updater;
                }

                public String getConsignorAddress() {
                    return consignorAddress;
                }

                public void setConsignorAddress(String consignorAddress) {
                    this.consignorAddress = consignorAddress;
                }

                public String getAssignDriver() {
                    return assignDriver;
                }

                public void setAssignDriver(String assignDriver) {
                    this.assignDriver = assignDriver;
                }

                public String getUpdateBy() {
                    return updateBy;
                }

                public void setUpdateBy(String updateBy) {
                    this.updateBy = updateBy;
                }

                public String getConsigneePhone() {
                    return consigneePhone;
                }

                public void setConsigneePhone(String consigneePhone) {
                    this.consigneePhone = consigneePhone;
                }

                public String getConsigneeCoordinate() {
                    return consigneeCoordinate;
                }

                public void setConsigneeCoordinate(String consigneeCoordinate) {
                    this.consigneeCoordinate = consigneeCoordinate;
                }

                public String getRequiredDeliveryTime() {
                    return requiredDeliveryTime;
                }

                public void setRequiredDeliveryTime(String requiredDeliveryTime) {
                    this.requiredDeliveryTime = requiredDeliveryTime;
                }

                public String getStartTime() {
                    return startTime;
                }

                public void setStartTime(String startTime) {
                    this.startTime = startTime;
                }

                public String getShippingTime() {
                    return shippingTime;
                }

                public void setShippingTime(String shippingTime) {
                    this.shippingTime = shippingTime;
                }

                public String getIsReceipt() {
                    return isReceipt;
                }

                public void setIsReceipt(String isReceipt) {
                    this.isReceipt = isReceipt;
                }

                public String getPayName() {
                    return payName;
                }

                public void setPayName(String payName) {
                    this.payName = payName;
                }

                public String getDestinationNode() {
                    return destinationNode;
                }

                public void setDestinationNode(String destinationNode) {
                    this.destinationNode = destinationNode;
                }

                public String getConsignor() {
                    return consignor;
                }

                public void setConsignor(String consignor) {
                    this.consignor = consignor;
                }

                public String getConsigneeUnit() {
                    return consigneeUnit;
                }

                public void setConsigneeUnit(String consigneeUnit) {
                    this.consigneeUnit = consigneeUnit;
                }

                public String getConsignorDetailedAddress() {
                    return consignorDetailedAddress;
                }

                public void setConsignorDetailedAddress(String consignorDetailedAddress) {
                    this.consignorDetailedAddress = consignorDetailedAddress;
                }

                public String getAmountReceivable() {
                    return amountReceivable;
                }

                public void setAmountReceivable(String amountReceivable) {
                    this.amountReceivable = amountReceivable;
                }

                public String getUpdateTime() {
                    return updateTime;
                }

                public void setUpdateTime(String updateTime) {
                    this.updateTime = updateTime;
                }

                public String getHeadOfSales() {
                    return headOfSales;
                }

                public void setHeadOfSales(String headOfSales) {
                    this.headOfSales = headOfSales;
                }

                public String getConsignorLat() {
                    return consignorLat;
                }

                public void setConsignorLat(String consignorLat) {
                    this.consignorLat = consignorLat;
                }

                public String getCreateBy() {
                    return createBy;
                }

                public void setCreateBy(String createBy) {
                    this.createBy = createBy;
                }

                public String getForwardingUnit() {
                    return forwardingUnit;
                }

                public void setForwardingUnit(String forwardingUnit) {
                    this.forwardingUnit = forwardingUnit;
                }

                public int getIsHc() {
                    return isHc;
                }

                public void setIsHc(int isHc) {
                    this.isHc = isHc;
                }

                public String getCreateTime() {
                    return createTime;
                }

                public void setCreateTime(String createTime) {
                    this.createTime = createTime;
                }

                public String getConsigneeDetailedAddress() {
                    return consigneeDetailedAddress;
                }

                public void setConsigneeDetailedAddress(String consigneeDetailedAddress) {
                    this.consigneeDetailedAddress = consigneeDetailedAddress;
                }

                public String getDays() {
                    return days;
                }

                public void setDays(String days) {
                    this.days = days;
                }

                public String getEndTime() {
                    return endTime;
                }

                public void setEndTime(String endTime) {
                    this.endTime = endTime;
                }

                public String getOpenDate() {
                    return openDate;
                }

                public void setOpenDate(String openDate) {
                    this.openDate = openDate;
                }

                public String getSearchValue() {
                    return searchValue;
                }

                public void setSearchValue(String searchValue) {
                    this.searchValue = searchValue;
                }

                public String getPayStatus() {
                    return payStatus;
                }

                public void setPayStatus(String payStatus) {
                    this.payStatus = payStatus;
                }

                public String getCustomerNo() {
                    return customerNo;
                }

                public void setCustomerNo(String customerNo) {
                    this.customerNo = customerNo;
                }

                public static class ParamsBean {
                }
            }

            public static class TmsCarTrackListBean {
                /**
                 * updateMan : null
                 * remark : null
                 * lon : 113.5627094184028
                 * equipmentId : null
                 * temp1 : null
                 * organizationId : null
                 * oil : null
                 * createMan : 485
                 * updateBy : null
                 * equipmentName : null
                 * power : null
                 * palaceName : 河南省郑州市中原区金梭路64号郑州威科姆科技股份有限公司(莲花街)
                 * lat : 34.82304931640625
                 * odometer : null
                 * trackId : 117293
                 * cartId : 265
                 * updateTime : null
                 * params : {}
                 * equipmentCarRalaitonid : null
                 * carNumber : 鲁A9Z0J1
                 * createBy : null
                 * phoneNumber : 15800000006
                 * driverId : 337
                 * createTime : 2020-08-29 14:43:09
                 * driverName : JN司机-济南南lyz
                 * gpstime : 2020-08-29 14:43:09
                 * searchValue : null
                 */

                private String updateMan;
                private String remark;
                private String lon;
                private String equipmentId;
                private String temp1;
                private String organizationId;
                private String oil;
                private String createMan;
                private String updateBy;
                private String equipmentName;
                private String power;
                private String palaceName;
                private String lat;
                private String odometer;
                private int trackId;
                private int cartId;
                private String updateTime;
                private ParamsBeanX params;
                private String equipmentCarRalaitonid;
                private String carNumber;
                private String createBy;
                private String phoneNumber;
                private int driverId;
                private String createTime;
                private String driverName;
                private String gpstime;
                private String searchValue;

                public String getUpdateMan() {
                    return updateMan;
                }

                public void setUpdateMan(String updateMan) {
                    this.updateMan = updateMan;
                }

                public String getRemark() {
                    return remark;
                }

                public void setRemark(String remark) {
                    this.remark = remark;
                }

                public String getLon() {
                    return lon;
                }

                public void setLon(String lon) {
                    this.lon = lon;
                }

                public String getEquipmentId() {
                    return equipmentId;
                }

                public void setEquipmentId(String equipmentId) {
                    this.equipmentId = equipmentId;
                }

                public String getTemp1() {
                    return temp1;
                }

                public void setTemp1(String temp1) {
                    this.temp1 = temp1;
                }

                public String getOrganizationId() {
                    return organizationId;
                }

                public void setOrganizationId(String organizationId) {
                    this.organizationId = organizationId;
                }

                public String getOil() {
                    return oil;
                }

                public void setOil(String oil) {
                    this.oil = oil;
                }

                public String getCreateMan() {
                    return createMan;
                }

                public void setCreateMan(String createMan) {
                    this.createMan = createMan;
                }

                public String getUpdateBy() {
                    return updateBy;
                }

                public void setUpdateBy(String updateBy) {
                    this.updateBy = updateBy;
                }

                public String getEquipmentName() {
                    return equipmentName;
                }

                public void setEquipmentName(String equipmentName) {
                    this.equipmentName = equipmentName;
                }

                public String getPower() {
                    return power;
                }

                public void setPower(String power) {
                    this.power = power;
                }

                public String getPalaceName() {
                    return palaceName;
                }

                public void setPalaceName(String palaceName) {
                    this.palaceName = palaceName;
                }

                public String getLat() {
                    return lat;
                }

                public void setLat(String lat) {
                    this.lat = lat;
                }

                public String getOdometer() {
                    return odometer;
                }

                public void setOdometer(String odometer) {
                    this.odometer = odometer;
                }

                public int getTrackId() {
                    return trackId;
                }

                public void setTrackId(int trackId) {
                    this.trackId = trackId;
                }

                public int getCartId() {
                    return cartId;
                }

                public void setCartId(int cartId) {
                    this.cartId = cartId;
                }

                public String getUpdateTime() {
                    return updateTime;
                }

                public void setUpdateTime(String updateTime) {
                    this.updateTime = updateTime;
                }

                public ParamsBeanX getParams() {
                    return params;
                }

                public void setParams(ParamsBeanX params) {
                    this.params = params;
                }

                public String getEquipmentCarRalaitonid() {
                    return equipmentCarRalaitonid;
                }

                public void setEquipmentCarRalaitonid(String equipmentCarRalaitonid) {
                    this.equipmentCarRalaitonid = equipmentCarRalaitonid;
                }

                public String getCarNumber() {
                    return carNumber;
                }

                public void setCarNumber(String carNumber) {
                    this.carNumber = carNumber;
                }

                public String getCreateBy() {
                    return createBy;
                }

                public void setCreateBy(String createBy) {
                    this.createBy = createBy;
                }

                public String getPhoneNumber() {
                    return phoneNumber;
                }

                public void setPhoneNumber(String phoneNumber) {
                    this.phoneNumber = phoneNumber;
                }

                public int getDriverId() {
                    return driverId;
                }

                public void setDriverId(int driverId) {
                    this.driverId = driverId;
                }

                public String getCreateTime() {
                    return createTime;
                }

                public void setCreateTime(String createTime) {
                    this.createTime = createTime;
                }

                public String getDriverName() {
                    return driverName;
                }

                public void setDriverName(String driverName) {
                    this.driverName = driverName;
                }

                public String getGpstime() {
                    return gpstime;
                }

                public void setGpstime(String gpstime) {
                    this.gpstime = gpstime;
                }

                public String getSearchValue() {
                    return searchValue;
                }

                public void setSearchValue(String searchValue) {
                    this.searchValue = searchValue;
                }

                public static class ParamsBeanX {
                }
            }

            public static class TmsOrderDetailsBean {
                /**
                 * receiptImg : null
                 * orderId : null
                 * pname : 锅圈食汇混合芝麻酱（360g）
                 * goodsId : null
                 * num : 2.0
                 * weight : null
                 * updateTime : null
                 * remark : null
                 * params : {}
                 * volume : null
                 * createBy : null
                 * createTime : null
                 * updateBy : null
                 * id : 1373643
                 * sunit : 件
                 * searchValue : null
                 * value : null
                 * remarks : null
                 * customerNo : SHFHTZD243236
                 */

                private String receiptImg;
                private String orderId;
                private String pname;
                private String goodsId;
                private double num;
                private String weight;
                private String updateTime;
                private String remark;
                private ParamsBeanXX params;
                private String volume;
                private String createBy;
                private String createTime;
                private String updateBy;
                private int id;
                private String sunit;
                private String searchValue;
                private String value;
                private String remarks;
                private String customerNo;

                public String getReceiptImg() {
                    return receiptImg;
                }

                public void setReceiptImg(String receiptImg) {
                    this.receiptImg = receiptImg;
                }

                public String getOrderId() {
                    return orderId;
                }

                public void setOrderId(String orderId) {
                    this.orderId = orderId;
                }

                public String getPname() {
                    return pname;
                }

                public void setPname(String pname) {
                    this.pname = pname;
                }

                public String getGoodsId() {
                    return goodsId;
                }

                public void setGoodsId(String goodsId) {
                    this.goodsId = goodsId;
                }

                public double getNum() {
                    return num;
                }

                public void setNum(double num) {
                    this.num = num;
                }

                public String getWeight() {
                    return weight;
                }

                public void setWeight(String weight) {
                    this.weight = weight;
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

                public String getVolume() {
                    return volume;
                }

                public void setVolume(String volume) {
                    this.volume = volume;
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

                public int getId() {
                    return id;
                }

                public void setId(int id) {
                    this.id = id;
                }

                public String getSunit() {
                    return sunit;
                }

                public void setSunit(String sunit) {
                    this.sunit = sunit;
                }

                public String getSearchValue() {
                    return searchValue;
                }

                public void setSearchValue(String searchValue) {
                    this.searchValue = searchValue;
                }

                public String getValue() {
                    return value;
                }

                public void setValue(String value) {
                    this.value = value;
                }

                public String getRemarks() {
                    return remarks;
                }

                public void setRemarks(String remarks) {
                    this.remarks = remarks;
                }

                public String getCustomerNo() {
                    return customerNo;
                }

                public void setCustomerNo(String customerNo) {
                    this.customerNo = customerNo;
                }

                public static class ParamsBeanXX {
                }
            }

            public static class ListBean {
                /**
                 * orderId : 310121
                 * orderRemark : 运输中
                 * orderStatus : 3
                 * updateTime : null
                 * remark : null
                 * params : {}
                 * userName : null
                 * userId : 485
                 * updater : null
                 * createBy : null
                 * phoneNumber : null
                 * createTime : 2020-08-29 14:42:19
                 * updateBy : null
                 * orderTrackId : 226659
                 * creater : JN司机-济南南lyz
                 * invoiceNo : HDYS20200829144012
                 * searchValue : null
                 * customerNo : SHFHTZD243236
                 */

                private int orderId;
                private String orderRemark;
                private String orderStatus;
                private String updateTime;
                private String remark;
                private ParamsBeanXXX params;
                private String userName;
                private int userId;
                private String updater;
                private String createBy;
                private String phoneNumber;
                private String createTime;
                private String updateBy;
                private int orderTrackId;
                private String creater;
                private String invoiceNo;
                private String searchValue;
                private String customerNo;

                public int getOrderId() {
                    return orderId;
                }

                public void setOrderId(int orderId) {
                    this.orderId = orderId;
                }

                public String getOrderRemark() {
                    return orderRemark;
                }

                public void setOrderRemark(String orderRemark) {
                    this.orderRemark = orderRemark;
                }

                public String getOrderStatus() {
                    return orderStatus;
                }

                public void setOrderStatus(String orderStatus) {
                    this.orderStatus = orderStatus;
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

                public String getUserName() {
                    return userName;
                }

                public void setUserName(String userName) {
                    this.userName = userName;
                }

                public int getUserId() {
                    return userId;
                }

                public void setUserId(int userId) {
                    this.userId = userId;
                }

                public String getUpdater() {
                    return updater;
                }

                public void setUpdater(String updater) {
                    this.updater = updater;
                }

                public String getCreateBy() {
                    return createBy;
                }

                public void setCreateBy(String createBy) {
                    this.createBy = createBy;
                }

                public String getPhoneNumber() {
                    return phoneNumber;
                }

                public void setPhoneNumber(String phoneNumber) {
                    this.phoneNumber = phoneNumber;
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

                public int getOrderTrackId() {
                    return orderTrackId;
                }

                public void setOrderTrackId(int orderTrackId) {
                    this.orderTrackId = orderTrackId;
                }

                public String getCreater() {
                    return creater;
                }

                public void setCreater(String creater) {
                    this.creater = creater;
                }

                public String getInvoiceNo() {
                    return invoiceNo;
                }

                public void setInvoiceNo(String invoiceNo) {
                    this.invoiceNo = invoiceNo;
                }

                public String getSearchValue() {
                    return searchValue;
                }

                public void setSearchValue(String searchValue) {
                    this.searchValue = searchValue;
                }

                public String getCustomerNo() {
                    return customerNo;
                }

                public void setCustomerNo(String customerNo) {
                    this.customerNo = customerNo;
                }

                public static class ParamsBeanXXX {
                }
            }
        }
    }
}