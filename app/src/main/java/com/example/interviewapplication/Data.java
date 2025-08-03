package com.example.interviewapplication;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Data {

    @PrimaryKey(autoGenerate = true)
    public long uid;

    @ColumnInfo(name = "text1")
    public String text1;

    @ColumnInfo(name = "tag")
    public String tag;

    @Override
    public String toString() {
        return "Data{" +
                "uid=" + uid +
                ", text1='" + text1 + '\'' +
                ", tag='" + tag + '\'' +
                '}';
    }
}
