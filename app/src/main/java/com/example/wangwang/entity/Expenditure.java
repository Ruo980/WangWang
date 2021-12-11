package com.example.wangwang.entity;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.sql.Date;

@Entity
public class Expenditure {
    @PrimaryKey(autoGenerate = true)
    private int id;
    private String account;
    //支付金额不能为空
    @NonNull
    private int payMoney;
    private String payDate;//sqlite不支持sql.Date,更不支持util.Date
    private int month;
    private int week;
    private String payType;
    private String payPlace;
    private String notes;

    public Expenditure(String account, int payMoney, String payDate, int month, int week, String payType, String payPlace, String notes) {
        this.id = id;
        this.account = account;
        this.payMoney = payMoney;
        this.payDate = payDate;
        this.month = month;
        this.week = week;
        this.payType = payType;
        this.payPlace = payPlace;
        this.notes = notes;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public int getPayMoney() {
        return payMoney;
    }

    public void setPayMoney(int payMoney) {
        this.payMoney = payMoney;
    }

    public String getPayDate() {
        return payDate;
    }

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public int getWeek() {
        return week;
    }

    public void setWeek(int week) {
        this.week = week;
    }

    public void setPayDate(String payDate) {
        this.payDate = payDate;
    }

    public String getPayType() {
        return payType;
    }

    public void setPayType(String payType) {
        this.payType = payType;
    }

    public String getPayPlace() {
        return payPlace;
    }

    public void setPayPlace(String payPlace) {
        this.payPlace = payPlace;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    @Override
    public String toString() {
        return "支出表{" +
                "id=" + id +
                ", 账号'" + account + '\'' +
                ", 支付数额=" + payMoney +
                ", 支付日期='" + payDate + '\'' +
                ", 支付类型='" + payType + '\'' +
                ", 支付地点='" + payPlace + '\'' +
                ", 备注='" + notes + '\'' +
                '}'+"\n";
    }
}