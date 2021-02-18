package com.huadingcloudpackage.www.eventbus;

/**
 * 文 件 名：GouwucheEvent
 * 描   述：BGouWuCarFragment  EventBus事件
 */
public class GouwucheEvent {

    public GouwucheEvent() {
    }

    public GouwucheEvent(int tag) {
        this.tag = tag;
    }

    private int tag;//用来区分不同的订阅事件
    private Object object;


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
