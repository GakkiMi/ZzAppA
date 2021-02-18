package com.huadingcloudpackage.www.eventbus;

/**
 * 文 件 名：OrderCommitEvent
 * 描   述： EventBus事件
 */
public class OrderCommitEvent {

    private int tag;//用来区分不同的订阅事件
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
