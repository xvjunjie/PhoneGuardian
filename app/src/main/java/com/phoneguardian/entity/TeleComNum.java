package com.phoneguardian.entity;

/**
 * Created by Administrator on 2016/6/1.
 */
public class TeleComNum {
    public String number;
    public int mode;//拦截类型

    public TeleComNum() {
    }

    public TeleComNum(String number, int mode) {
        this.number = number;
        this.mode = mode;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public int getMode() {
        return mode;
    }

    public void setMode(int mode) {
        this.mode = mode;
    }
}
