package com.huadingcloudpackage.www.eventbus;

/**
 * 文 件 名：OrderListEvent
 * 描   述：OrderListEvent  订单列表的事件
 */
public class OrderListEvent {

    private int tag;//用来区分不同的订阅事件   1是待付款里面的事件  2是待发货  3是待收货  4已完成  5已取消  6售后
    private Object object;

    public OrderListEvent() {
    }

    public OrderListEvent(int tag) {
        this.tag = tag;
    }

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

    @Override
    public String toString() {
        return "OrderListEvent{" +
                "tag=" + tag +
                ", object=" + object +
                '}';
    }
}
