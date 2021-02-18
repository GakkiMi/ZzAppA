package com.huadingcloudpackage.www.eventbus;

public class AHomeEvent {

    private int tag;//用来区分不同的订阅事件
    private Object object;

    public AHomeEvent() {
    }

    public AHomeEvent(int tag) {
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
