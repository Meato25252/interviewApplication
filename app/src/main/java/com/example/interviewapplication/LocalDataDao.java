package com.example.interviewapplication;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
interface LocalDataDao {
    @Insert
    void insertAll(Data data);

    @Delete
    void delete(Data data);

    @Query("SELECT * FROM Data")
    List<Data> getAll();


}
