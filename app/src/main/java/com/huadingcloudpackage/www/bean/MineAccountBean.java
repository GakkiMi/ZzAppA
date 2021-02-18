package com.huadingcloudpackage.www.bean;

import java.util.List;

/**
 * @version V1.0
 * @类描述：
 * @创建人：李歌
 * @修改人：
 * @修改备注：
 */
public class MineAccountBean {

    /**
     * msg : 操作成功
     * code : 200
     * totalSize : 1
     * totalPages : 1
     * list : [{"wallet":{"walletId":33,"tpExhibiId":49,"rechargeMoney":99960,"giveMoney":0,"freezMoney":0,"money":99960,"Stringegral":0,"walletStatus":"0","createBy":"","createTime":"2020-08-07 13:51:06","updateBy":"","updateTime":"2020-08-07 14:00:18","remark":"","tpExhibiNo":"HD20200807wWA"},"monthTotal":[{"searchValue":null,"createBy":null,"createTime":null,"updateBy":null,"updateTime":null,"remark":null,"params":{},"startTime":null,"endTime":null,"id":null,"orderId":null,"orderSn":null,"exhibiId":null,"rechargeMoney":null,"giveMoney":null,"orderAmount":null,"Stringegral":null,"status":null,"type":null,"walletTime":"2020-08","dayIncomeTotal":null,"dayExpendTotal":null,"monthIncomeTotal":100000,"monthExpendTotal":40,"timeAll":null,"yearTime":null,"monthTime":null,"dayTime":null,"exhibiWalletLogList":[{"searchValue":null,"createBy":null,"createTime":null,"updateBy":null,"updateTime":null,"remark":null,"params":{},"startTime":null,"endTime":null,"id":null,"orderId":null,"orderSn":null,"exhibiId":null,"rechargeMoney":null,"giveMoney":null,"orderAmount":null,"Stringegral":null,"status":null,"type":null,"walletTime":"2020-08-07","dayIncomeTotal":100000,"dayExpendTotal":40,"monthIncomeTotal":null,"monthExpendTotal":null,"timeAll":null,"yearTime":null,"monthTime":null,"dayTime":null,"exhibiWalletLogList":[{"searchValue":null,"createBy":null,"createTime":"2020-08-07 14:00:52","updateBy":null,"updateTime":null,"remark":null,"params":{},"startTime":null,"endTime":null,"id":173,"orderId":2080,"orderSn":"20200807135953334321","exhibiId":49,"rechargeMoney":40,"giveMoney":null,"orderAmount":null,"Stringegral":null,"status":"1","type":1,"walletTime":null,"dayIncomeTotal":null,"dayExpendTotal":null,"monthIncomeTotal":null,"monthExpendTotal":null,"timeAll":null,"yearTime":null,"monthTime":null,"dayTime":null,"exhibiWalletLogList":null,"rank":null},{"searchValue":null,"createBy":null,"createTime":"2020-08-07 14:00:18","updateBy":null,"updateTime":null,"remark":null,"params":{},"startTime":null,"endTime":null,"id":172,"orderId":null,"orderSn":null,"exhibiId":49,"rechargeMoney":100000,"giveMoney":null,"orderAmount":null,"Stringegral":null,"status":null,"type":0,"walletTime":null,"dayIncomeTotal":null,"dayExpendTotal":null,"monthIncomeTotal":null,"monthExpendTotal":null,"timeAll":null,"yearTime":null,"monthTime":null,"dayTime":null,"exhibiWalletLogList":null,"rank":null}],"rank":null}],"rank":null}]}]
     * pageNum : 1
     */

    private String msg;
    private int code;
    private List<MonthTotalBean> monthTotal;
    private Data data;
    public String getMsg() {
        return msg;
    }

    public List<MonthTotalBean> getMonthTotal() {
        return monthTotal;
    }

    public void setMonthTotal(List<MonthTotalBean> monthTotal) {
        this.monthTotal = monthTotal;
    }

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
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

        private String walletId;
        private String rechargeMoney;
        private String giveMoney;
        private String freezMoney;
        private String money;
        private String integral;
        private String walletStatus;
        private String createBy;
        private String createTime;
        private String updateBy;
        private String updateTime;
        private String remark;
        private String customerNo;

        public String getIntegral() {
            return integral;
        }

        public void setIntegral(String integral) {
            this.integral = integral;
        }

        public String getCustomerNo() {
            return customerNo;
        }

        public void setCustomerNo(String customerNo) {
            this.customerNo = customerNo;
        }

        public String getWalletId() {
            return walletId;
        }

        public void setWalletId(String walletId) {
            this.walletId = walletId;
        }


        public String getRechargeMoney() {
            return rechargeMoney;
        }

        public void setRechargeMoney(String rechargeMoney) {
            this.rechargeMoney = rechargeMoney;
        }

        public String getGiveMoney() {
            return giveMoney;
        }

        public void setGiveMoney(String giveMoney) {
            this.giveMoney = giveMoney;
        }

        public String getFreezMoney() {
            return freezMoney;
        }

        public void setFreezMoney(String freezMoney) {
            this.freezMoney = freezMoney;
        }

        public String getMoney() {
            return money;
        }

        public void setMoney(String money) {
            this.money = money;
        }


        public String getWalletStatus() {
            return walletStatus;
        }

        public void setWalletStatus(String walletStatus) {
            this.walletStatus = walletStatus;
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


    }


    public static class MonthTotalBean {
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
         * id : null
         * orderId : null
         * orderSn : null
         * exhibiId : null
         * rechargeMoney : null
         * giveMoney : null
         * orderAmount : null
         * Stringegral : null
         * status : null
         * type : null
         * walletTime : 2020-08
         * dayIncomeTotal : null
         * dayExpendTotal : null
         * monthIncomeTotal : 100000
         * monthExpendTotal : 40
         * timeAll : null
         * yearTime : null
         * monthTime : null
         * dayTime : null
         * exhibiWalletLogList : [{"searchValue":null,"createBy":null,"createTime":null,"updateBy":null,"updateTime":null,"remark":null,"params":{},"startTime":null,"endTime":null,"id":null,"orderId":null,"orderSn":null,"exhibiId":null,"rechargeMoney":null,"giveMoney":null,"orderAmount":null,"Stringegral":null,"status":null,"type":null,"walletTime":"2020-08-07","dayIncomeTotal":100000,"dayExpendTotal":40,"monthIncomeTotal":null,"monthExpendTotal":null,"timeAll":null,"yearTime":null,"monthTime":null,"dayTime":null,"exhibiWalletLogList":[{"searchValue":null,"createBy":null,"createTime":"2020-08-07 14:00:52","updateBy":null,"updateTime":null,"remark":null,"params":{},"startTime":null,"endTime":null,"id":173,"orderId":2080,"orderSn":"20200807135953334321","exhibiId":49,"rechargeMoney":40,"giveMoney":null,"orderAmount":null,"Stringegral":null,"status":"1","type":1,"walletTime":null,"dayIncomeTotal":null,"dayExpendTotal":null,"monthIncomeTotal":null,"monthExpendTotal":null,"timeAll":null,"yearTime":null,"monthTime":null,"dayTime":null,"exhibiWalletLogList":null,"rank":null},{"searchValue":null,"createBy":null,"createTime":"2020-08-07 14:00:18","updateBy":null,"updateTime":null,"remark":null,"params":{},"startTime":null,"endTime":null,"id":172,"orderId":null,"orderSn":null,"exhibiId":49,"rechargeMoney":100000,"giveMoney":null,"orderAmount":null,"Stringegral":null,"status":null,"type":0,"walletTime":null,"dayIncomeTotal":null,"dayExpendTotal":null,"monthIncomeTotal":null,"monthExpendTotal":null,"timeAll":null,"yearTime":null,"monthTime":null,"dayTime":null,"exhibiWalletLogList":null,"rank":null}],"rank":null}]
         * rank : null
         */

        private String id;
        private String orderId;
        private String orderSn;
        private String customerNo;
        private String rechargeMoney;
        private String giveMoney;
        private String orderAmount;
        private String integral;
        private String status;
        private String createBy;
        private String createTime;
        private String updateBy;
        private String updateTime;
        private String remark;
        private String type;
        private String walletTime;
        private String dayIncomeTotal;
        private String dayExpendTotal;
        private String monthIncomeTotal;
        private String monthExpendTotal;
        private String timeAll;
        private String yearTime;
        private String monthTime;
        private String dayTime;
        private List<MonthTotalBean> exhibiWalletLogList;

        public String getCustomerNo() {
            return customerNo;
        }

        public void setCustomerNo(String customerNo) {
            this.customerNo = customerNo;
        }

        public String getIntegral() {
            return integral;
        }

        public void setIntegral(String integral) {
            this.integral = integral;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
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


        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
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


        public String getRechargeMoney() {
            return rechargeMoney;
        }

        public void setRechargeMoney(String rechargeMoney) {
            this.rechargeMoney = rechargeMoney;
        }

        public String getGiveMoney() {
            return giveMoney;
        }

        public void setGiveMoney(String giveMoney) {
            this.giveMoney = giveMoney;
        }

        public String getOrderAmount() {
            return orderAmount;
        }

        public void setOrderAmount(String orderAmount) {
            this.orderAmount = orderAmount;
        }


        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }


        public String getWalletTime() {
            return walletTime;
        }

        public void setWalletTime(String walletTime) {
            this.walletTime = walletTime;
        }

        public String getDayIncomeTotal() {
            return dayIncomeTotal;
        }

        public void setDayIncomeTotal(String dayIncomeTotal) {
            this.dayIncomeTotal = dayIncomeTotal;
        }

        public String getDayExpendTotal() {
            return dayExpendTotal;
        }

        public void setDayExpendTotal(String dayExpendTotal) {
            this.dayExpendTotal = dayExpendTotal;
        }

        public String getMonthIncomeTotal() {
            return monthIncomeTotal;
        }

        public void setMonthIncomeTotal(String monthIncomeTotal) {
            this.monthIncomeTotal = monthIncomeTotal;
        }

        public String getMonthExpendTotal() {
            return monthExpendTotal;
        }

        public void setMonthExpendTotal(String monthExpendTotal) {
            this.monthExpendTotal = monthExpendTotal;
        }

        public String getTimeAll() {
            return timeAll;
        }

        public void setTimeAll(String timeAll) {
            this.timeAll = timeAll;
        }

        public String getYearTime() {
            return yearTime;
        }

        public void setYearTime(String yearTime) {
            this.yearTime = yearTime;
        }

        public String getMonthTime() {
            return monthTime;
        }

        public void setMonthTime(String monthTime) {
            this.monthTime = monthTime;
        }

        public String getDayTime() {
            return dayTime;
        }

        public void setDayTime(String dayTime) {
            this.dayTime = dayTime;
        }


        public List<MonthTotalBean> getExhibiWalletLogList() {
            return exhibiWalletLogList;
        }

        public void setExhibiWalletLogList(List<MonthTotalBean> exhibiWalletLogList) {
            this.exhibiWalletLogList = exhibiWalletLogList;
        }

    }


}
