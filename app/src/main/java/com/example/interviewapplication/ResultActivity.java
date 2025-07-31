package com.example.interviewapplication;

import android.app.appsearch.ReportSystemUsageRequest;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

public class ResultActivity extends AppCompatActivity {

    private TextView textView;

    private Button button;
    private AppDatabase db;
    private long nowId;
    private String[] str;

    private LocalDataStore localDataStore;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        str=new String[3];
        str[0] = "10";
        str[1] = "20";
        str[2] = "30";

        nowId = getIntent().getLongExtra("nowId",-1);

        db = Room.databaseBuilder(getApplicationContext(),
                AppDatabase.class, "data").build();

        LocalDataDao localDataDao = db.localDataDao();

        RecyclerView recyclerView = findViewById(R.id.recycler_view);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        RecyclerView.Adapter mainAdapter = new CustomAdapter(str);
        recyclerView.setAdapter(mainAdapter);

        findViewById(R.id.button).setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(ResultActivity.this,MainActivity.class);
                        startActivity(intent);

                        Data data = new Data();
                        System.out.println(nowId);

                        new Thread(()-> {
                            //room
                            data.uid=nowId;
                            data.firstName = "test";
                            data.lastName = "test";
                            localDataDao.updateAll(data);
                            System.out.println(localDataDao.getAll());

                        }).start();

                    }
                }
        );

    }
}

