package com.example.wangwang.entity;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.Date;

@Entity
public class Income {
    @PrimaryKey(autoGenerate = true)
    private int id;
    private String account;
    @NonNull
    private int incomeMoney;
    private String incomeDate;
    private int month;
    private int week;
    private String incomeType;
    private String payPeople;
    private String notes;

    public Income(String account, int incomeMoney, String incomeDate, int month, int week, String incomeType, String payPeople, String notes) {
        this.id = id;
        this.account = account;
        this.incomeMoney = incomeMoney;
        this.incomeDate = incomeDate;
        this.month = month;
        this.week = week;
        this.incomeType = incomeType;
        this.payPeople = payPeople;
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

    public int getIncomeMoney() {
        return incomeMoney;
    }

    public void setIncomeMoney(int incomeMoney) {
        this.incomeMoney = incomeMoney;
    }

    public String getIncomeDate() {
        return incomeDate;
    }

    public void setIncomeDate(String incomeDate) {
        this.incomeDate = incomeDate;
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

    public String getIncomeType() {
        return incomeType;
    }

    public void setIncomeType(String incomeType) {
        this.incomeType = incomeType;
    }

    public String getPayPeople() {
        return payPeople;
    }

    public void setPayPeople(String payPeople) {
        this.payPeople = payPeople;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    @Override
    public String toString() {
        return "收入表{" +
                "id=" + id +
                ", 账号='" + account + '\'' +
                ", 收入数额=" + incomeMoney +
                ", 收入日期='" + incomeDate + '\'' +
                ", 收入类型='" + incomeType + '\'' +
                ", 支付人='" + payPeople + '\'' +
                ", 备注='" + notes + '\'' +
                '}'+"\n";
    }
}
