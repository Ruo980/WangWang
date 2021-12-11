package com.example.wangwang.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.wangwang.entity.Notes;
import com.example.wangwang.entity.User;

import java.util.List;

@Dao
public interface NotesDao {

    @Insert
    public Long insertNotes(Notes Notes);

    @Query("SELECT * FROM Notes WHERE account=:account")
    public List<Notes> loadNotesByAccount(String account);

    @Query("SELECT * FROM Notes WHERE id=:id")
    public List<Notes> loadNotesById(int id);

    @Query("SELECT * FROM Notes WHERE account=:account AND month=:month")
    public List<Notes> selectNotesByMonth(String account,int month);

    @Query("SELECT * FROM Notes WHERE account=:account AND month=:month AND week=:week")
    public List<Notes> selectNotesByMonthAndWeek(String account,int month,int week);

    @Update
    public void updateNotes(Notes notes);

    @Delete
    public void deleteNotes(Notes notes);
}
