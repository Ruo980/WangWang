package com.example.wangwang.entity;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Notes {

    @PrimaryKey(autoGenerate = true)
    @NonNull
    private int id;
    private String account;
    private String name;
    private String notesDate;
    private int month;
    private int week;
    private String content;


    public Notes(String account, String name, String notesDate, int month, int week, String content) {
        this.account = account;
        this.name = name;
        this.notesDate = notesDate;
        this.month = month;
        this.week = week;
        this.content = content;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNotesDate() {
        return notesDate;
    }

    public void setNotesDate(String incomeDate) {
        this.notesDate = incomeDate;
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

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Override
    public String toString() {
        return "标签表{" +
                "标签名='" + name + '\'' +
                ", 标签日期='" + notesDate + '\'' +
                ", 月份=" + month +
                ", 周=" + week +
                ", 内容='" + content + '\'' +
                '}'+"\n";
    }
}
