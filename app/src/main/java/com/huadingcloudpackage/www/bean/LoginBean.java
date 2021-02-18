package com.huadingcloudpackage.www.bean;

public class LoginBean {


    /**
     * {
     * "msg":"登录成功",
     * "code":200,
     * "expire":43199999,
     * "storeId":"1",
     * "userId":968,
     * "token":"eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJleHAiOjE2MDY4OTEyODQsInVzZXJuYW1lIjoiMTM2NzE1NjcxMDUifQ.OMCyJPl0CBeXe7uuPJlRmSZNgayQy9Q4-KQqPoQrFEA"
     * }
     */

    private String msg;
    private int code;
    private String expire;
    private String storeId;
    private String exhibiTime;
    private String userId;
    private String token;
    private String exhibiId;
    private String exhibiTime1;
    private String exhibiTime2;

    public String getExhibiTime2() {
        return exhibiTime2;
    }

    public void setExhibiTime2(String exhibiTime2) {
        this.exhibiTime2 = exhibiTime2;
    }

    public String getExhibiTime1() {
        return exhibiTime1;
    }

    public void setExhibiTime1(String exhibiTime1) {
        this.exhibiTime1 = exhibiTime1;
    }

    public String getExhibiTime() {
        return exhibiTime;
    }

    public void setExhibiTime(String exhibiTime) {
        this.exhibiTime = exhibiTime;
    }

    public String getExhibiId() {
        return exhibiId;
    }

    public void setExhibiId(String exhibiId) {
        this.exhibiId = exhibiId;
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

    public String getExpire() {
        return expire;
    }

    public void setExpire(String expire) {
        this.expire = expire;
    }

    public String getStoreId() {
        return storeId;
    }

    public void setStoreId(String storeId) {
        this.storeId = storeId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
