package com.huadingcloudpackage.www.bean;

/**
 * @version V1.0
 * @类描述：
 * @创建人：李歌
 * @修改人：
 * @修改备注：
 */
public class MineBean {

    /**
     * msg : 操作成功
     * notYetShipped : 3
     * code : 200
     * address : 北京市市辖区东城区北京市东城区
     * money : 1.234273401E7
     * phonenumber : 17639760002
     * notConsignee : 0
     * exhibiName : 河南郑州
     * storeId : 219
     * unpaid : 7
     */

    private String msg;
    private int notYetShipped;
    private int code;
    private String address;
    private String money;
    private String phonenumber;
    private int notConsignee;
    private String exhibiName;
    private String storeId;
    private int unpaid;
    private int afterSale;
    private int completed;
    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public int getNotYetShipped() {
        return notYetShipped;
    }

    public void setNotYetShipped(int notYetShipped) {
        this.notYetShipped = notYetShipped;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getMoney() {
        return money;
    }

    public void setMoney(String money) {
        this.money = money;
    }

    public String getPhonenumber() {
        return phonenumber;
    }

    public void setPhonenumber(String phonenumber) {
        this.phonenumber = phonenumber;
    }

    public int getNotConsignee() {
        return notConsignee;
    }

    public void setNotConsignee(int notConsignee) {
        this.notConsignee = notConsignee;
    }

    public String getExhibiName() {
        return exhibiName;
    }

    public void setExhibiName(String exhibiName) {
        this.exhibiName = exhibiName;
    }

    public String getStoreId() {
        return storeId;
    }

    public void setStoreId(String storeId) {
        this.storeId = storeId;
    }

    public int getUnpaid() {
        return unpaid;
    }

    public void setUnpaid(int unpaid) {
        this.unpaid = unpaid;
    }

    public int getAfterSale() {
        return afterSale;
    }

    public void setAfterSale(int afterSale) {
        this.afterSale = afterSale;
    }

    public int getCompleted() {
        return completed;
    }

    public void setCompleted(int completed) {
        this.completed = completed;
    }
}
