package com.example.interviewapplication;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
interface LocalDataDao {
    @Insert
    long insertAll(Data data);

    @Delete
    void delete(Data data);

    @Update
    void updateAll(Data data);

    @Query("SELECT * FROM Data")
    List<Data> getAll();

    @Query("SELECT text1 FROM Data WHERE uid = :id")
    String getById(long id);
}
