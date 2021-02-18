package com.huadingcloudpackage.www.eventbus;

/**
 * 文 件 名：SaleOrderDetailEvent
 * 描   述：差异订单详细界面的事件  EventBus事件
 */
public class DiffOrderDetailEvent {

    private int tag;//用来区分不同的订阅事件   //1待审核、2.已完成、3.已拒绝 4.已取消、5.待付款

    private Object object;//可用来传递参数


    public int getTag() {
        return tag;
    }

    public void setTag(int tag) {
        this.tag = tag;
    }

    public Object getObject() {
        return object;
    }

    public void setObject(Object object) {
        this.object = object;
    }


}
