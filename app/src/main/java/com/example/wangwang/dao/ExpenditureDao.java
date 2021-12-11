package com.example.wangwang.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.wangwang.entity.Expenditure;

import java.util.List;

@Dao
public
interface ExpenditureDao {
    @Insert
    public Long insertExpenditure(Expenditure Expenditure);

    @Query("SELECT * FROM Expenditure")
    public List<Expenditure> loadAllExpenditure();

    @Query("SELECT * FROM Expenditure WHERE account=:account")
    public List<Expenditure> loadExpendituresByAccount(String account);

    @Query("SELECT * FROM Expenditure WHERE id=:id")
    public List<Expenditure> selectExpenditureById(int id);

    @Query("SELECT * FROM Expenditure WHERE account=:account AND month=:month")
    public List<Expenditure> selectExpenditureByMonth(String account, int month);

    @Query("SELECT * FROM Expenditure WHERE account=:account AND month=:month AND week=:week")
    public List<Expenditure> selectExpenditureByMonthAndWeek(String account, int month, int week);

    @Delete
    public void deleteExpenditure(Expenditure expenditure);
}
