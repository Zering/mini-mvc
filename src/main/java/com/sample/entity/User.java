package com.sample.entity;

/**
 * Created by zhanghaojie on 2017/10/19.
 *
 * 用户信息
 */
public class User {

    private String name;
    private int age;
    // 反馈信息
    private String callback;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getCallback() {
        return callback;
    }

    public void setCallback(String callback) {
        this.callback = callback;
    }
}
