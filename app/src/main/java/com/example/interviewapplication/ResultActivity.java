package com.example.interviewapplication;

import android.app.appsearch.ReportSystemUsageRequest;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import java.util.List;

public class ResultActivity extends AppCompatActivity {

    private TextView textView;

    private Button button;
    private AppDatabase db;
    private long nowId;

    private LocalDataStore localDataStore;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        nowId = getIntent().getLongExtra("nowId",-1);

        db = Room.databaseBuilder(getApplicationContext(),
                AppDatabase.class, "data").build();

        LocalDataDao localDataDao = db.localDataDao();

        textView=findViewById(R.id.text);

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
//                            Data data = new Data();
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

