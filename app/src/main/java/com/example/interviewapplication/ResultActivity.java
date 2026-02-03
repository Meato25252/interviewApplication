package com.example.interviewapplication;

import android.app.appsearch.ReportSystemUsageRequest;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

public class ResultActivity extends AppCompatActivity {
    private long nowId;
    private String[] str;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        nowId = getIntent().getLongExtra("nowId",-1);
        str=new String[(int)nowId];

        RecyclerView recyclerView = findViewById(R.id.main_recycler_view);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);


        new Thread(()-> {
            for(int i=1;i<=nowId;i++){
                String text=String.valueOf(i);
                str[i-1]=text;
            }

            runOnUiThread(()->{
                CustomAdapter mainAdapter = new CustomAdapter(str);
                recyclerView.setAdapter(mainAdapter);
                mainAdapter.setOnItemClickListener(
                        new CustomAdapter.onItemClickListener() {
                            @Override
                            public void onClick(View view,String str) {
                                Intent intent = new Intent(ResultActivity.this,DetailActivity.class);
                                intent.putExtra("id",str);
                                intent.putExtra("nowId",nowId);
                                startActivity(intent);
                            }
                        });
            });
        }).start();

        findViewById(R.id.button).setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(ResultActivity.this,MainActivity.class);
                        startActivity(intent);
                    }
                }
        );

    }
}

