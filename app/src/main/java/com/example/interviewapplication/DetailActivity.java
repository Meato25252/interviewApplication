package com.example.interviewapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

public class DetailActivity extends AppCompatActivity {

    private String id;
    private TextView textView1;
    private TextView textView2;

    private long nowId;
    private AppDatabase db;
    private LocalDataDao localDataDao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        id = getIntent().getStringExtra("id");
        nowId = getIntent().getLongExtra("nowId",-1);

        db = Room.databaseBuilder(getApplicationContext(),
                AppDatabase.class, "data").build();

        localDataDao = db.localDataDao();

        textView1 = (TextView) findViewById(R.id.text1);
        textView2 = (TextView) findViewById(R.id.text2);

        findViewById(R.id.button).setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(DetailActivity.this,ResultActivity.class);
                        intent.putExtra("nowId",nowId);
                        startActivity(intent);

                    }
                }
        );


    }

    @Override
    protected void onStart(){
        super.onStart();
        new Thread(()-> {
            textView1.setText("お題:"+localDataDao.getBytag(Long.parseLong(id)));
            textView2.setText(localDataDao.getBytext1(Long.parseLong(id)));
        }).start();
    }
}
