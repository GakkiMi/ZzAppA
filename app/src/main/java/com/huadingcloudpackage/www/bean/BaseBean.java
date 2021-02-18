package com.huadingcloudpackage.www.bean;

import com.contrarywind.interfaces.IPickerViewData;

import java.io.Serializable;

public class BaseBean implements Serializable, IPickerViewData {

    private int code;
    private String msg;

    private boolean isShowToast=true;//是否要弹出吐司提示  默认为true
    private boolean isSelect;
    private boolean checked;
    public String text ;

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public boolean isShowToast() {
        return isShowToast;
    }

    public void setShowToast(boolean showToast) {
        isShowToast = showToast;
    }

    @Override
    public String getPickerViewText() {
        return text;
    }
    public BaseBean() {

    }

    public boolean isChecked() {
        return checked;
    }

    public void setChecked(boolean checked) {
        this.checked = checked;
    }

    public boolean isSelect() {
        return isSelect;
    }

    public void setSelect(boolean select) {
        isSelect = select;
    }

    public BaseBean(int status, String msg) {
        this.code = status;
        this.msg = msg;
    }

    public BaseBean(int status, String msg, boolean isSelect, boolean checked) {
        this.code = status;
        this.msg = msg;
        this.isSelect = isSelect;
        this.checked = checked;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int status) {
        this.code = status;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
