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

    private TextView textView;

    private Button button;
    private AppDatabase db;
    private long nowId;
    private String[] str;

    private LocalDataStore localDataStore;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        nowId = getIntent().getLongExtra("nowId",-1);
        str=new String[(int)nowId+1];

        db = Room.databaseBuilder(getApplicationContext(),
                AppDatabase.class, "data").build();

        LocalDataDao localDataDao = db.localDataDao();

        RecyclerView recyclerView = findViewById(R.id.main_recycler_view);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);


//        new Thread(()-> {
//            for(long i=1;i<=nowId;i++){
//                str[(int)i-1]=localDataDao.getById(i);
//            }
//
//            runOnUiThread(()->{
//                for(int j=0;j<nowId;j++){
//                    System.out.println(str[j]);
//                }
//                CustomAdapter mainAdapter = new CustomAdapter(str);//親クラスで子クラスを呼び出している
//                recyclerView.setAdapter(mainAdapter);
//                mainAdapter.setOnItemClickListener(
//                        new CustomAdapter.onItemClickListener() {
//                    @Override
//                    public void onClick(View view,String str) {
//                        Intent intent = new Intent(ResultActivity.this,DetailActivity.class);
//                        startActivity(intent);
//                    }
//                });
//            });
//        }).start();

        for(int i=1;i<=nowId;i++){
            str[i-1]=String.valueOf(i);
        }

        CustomAdapter mainAdapter = new CustomAdapter(str);//親クラスで子クラスを呼び出している
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



        findViewById(R.id.button).setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(ResultActivity.this,MainActivity.class);
                        startActivity(intent);

                        Data data = new Data();
                        System.out.println(nowId);

//                        new Thread(()-> {
//                            //room
//                            data.uid=nowId;
//                            data.lastName = "test";
//                            System.out.println(localDataDao.getById(nowId));
//                            localDataDao.updateAll(data);
//                            System.out.println(localDataDao.getAll());
//
//                        }).start();

                    }
                }
        );

    }
}

