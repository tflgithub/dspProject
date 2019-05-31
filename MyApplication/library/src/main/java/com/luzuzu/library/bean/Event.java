package com.luzuzu.library.bean;

/**
 * Created by a10105 on 2019/3/7.
 */
public class Event<T>{

    private String action;
    private T data;

    public Event(String action) {
        this.action = action;
    }

    public Event(String action, T data) {
        this.action = action;
        this.data = data;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
