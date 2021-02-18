package com.huadingcloudpackage.www.eventbus;

/**
 * 文 件 名：DeclareOrderDetailEvent
 * 描   述：报货订单详细界面的事件  EventBus事件
 */
public class DeclareOrderDetailEvent {

    private int tag;//用来区分不同的订阅事件   0待付款 1待发货 2待收货 3已完成 4已取消
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
