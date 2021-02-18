package com.huadingcloudpackage.www.bean;

import java.util.List;

/**
 * @version V1.0
 * @类描述：
 * @创建人：李歌
 * @修改人：
 * @修改备注：
 */
public class AddressListBean {

    /**
     * msg : 操作成功
     * code : 200
     * data : {"total":1,"list":[{"id":24,"memberId":52,"memberCellphone":"","receiveMember":"凤凰","receiveCellphone":"15578497654","province":"福建省","city":"南平市","county":"武夷山市","address":"发货的好吧","remark":"","addressStatus":"1","creater":"","createTime":1596785871868,"modify":"","modifyTime":""}],"pageNum":1,"pageSize":1,"size":1,"startRow":0,"endRow":0,"pages":1,"prePage":0,"nextPage":0,"isFirstPage":true,"isLastPage":true,"hasPreviousPage":false,"hasNextPage":false,"navigatePages":8,"navigatepageNums":[1],"navigateFirstPage":1,"navigateLastPage":1,"firstPage":1,"lastPage":1}
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
        private String cusPhone;
        private String linkName;
        private String cusAddressAll;

        public String getCusPhone() {
            return cusPhone;
        }

        public void setCusPhone(String cusPhone) {
            this.cusPhone = cusPhone;
        }

        public String getLinkName() {
            return linkName;
        }

        public void setLinkName(String linkName) {
            this.linkName = linkName;
        }

        public String getCusAddressAll() {
            return cusAddressAll;
        }

        public void setCusAddressAll(String cusAddressAll) {
            this.cusAddressAll = cusAddressAll;
        }
    }
}
