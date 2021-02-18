package com.huadingcloudpackage.www.eventbus;

/**
 * 文 件 名：MainEvent
 * 描   述：MainActivity  EventBus事件
 */
public class MainEvent {

    private int tag;//用来区分不同的订阅事件
    private Object object;

    public MainEvent() {
    }

    public MainEvent(int tag) {
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
}
