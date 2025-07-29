package com.example.interviewapplication;

import android.widget.EditText;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.ArrayList;
import java.util.List;

public class LocalDataStore {
    private List<String> dataList = new ArrayList<>();

    //ローカルDBにデータを追加
    public void add(String text){
        dataList.add(text);
    }
    //ローカルDBのデータを取得
    public List<String> get(){
        return dataList;
    }
}

//表示は画面(Activity)で処理を書く。