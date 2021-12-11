package com.example.wangwang.database;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.example.wangwang.dao.ExpenditureDao;
import com.example.wangwang.dao.IncomeDao;
import com.example.wangwang.dao.NotesDao;
import com.example.wangwang.dao.UserDao;
import com.example.wangwang.entity.Expenditure;
import com.example.wangwang.entity.Income;
import com.example.wangwang.entity.Notes;
import com.example.wangwang.entity.User;

@Database(entities = {User.class, Expenditure.class, Income.class, Notes.class}, version = 1,exportSchema=false)
public abstract class FinancialAssistant extends RoomDatabase {
    public abstract UserDao userDao();
    public abstract ExpenditureDao ExpenditureDao();
    public abstract IncomeDao incomeDao();
    public abstract NotesDao NotesDao();
}
