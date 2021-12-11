package com.example.wangwang.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.wangwang.entity.User;

import java.util.List;

@Dao
public interface UserDao {

    @Insert
    public Long insertUser(User user);

    @Update
    public void updateUser(User user);

    @Delete
    public void deleteUser(User user);

    @Query("SELECT * FROM user")
    public List<User> loadAllUsers();

    @Query("SELECT * FROM user WHERE  account= :account")
    public List<User> findAllUserByAccount(String account);

    @Query("SELECT * FROM user WHERE  account= :account and password=:password")
    public List<User> loginUser(String account, String password);
}
