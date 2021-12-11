package com.example.wangwang.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.wangwang.entity.Income;

import java.util.List;


@Dao
public
interface IncomeDao {
    @Insert
    public Long insertIncome(Income income);

    @Query("SELECT * FROM income WHERE account=:account")
    public List<Income> loadIncomeByAccount(String account);

    @Query("SELECT * FROM income WHERE id=:id")
    public List<Income> selectIncomeById(int id);

    @Query("SELECT * FROM Income WHERE account=:account AND month=:month")
    public List<Income> selectIncomeByMonth(String account, int month);

    @Query("SELECT * FROM Income WHERE account=:account AND month=:month AND week =:week")
    public List<Income> selectIncomeByMonthAndWeek(String account, int month, int week);

    @Delete
    public void deleteIncome(Income income);

}
