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

    private LocalDataStore localDataStore;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        textView=findViewById(R.id.text);

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

//    @Override
//    protected void onStart(){
//        super.onStart();
//
//        textView.setText(change(localDataStore.get()));
//    }
//
//    public String change(List<String> list){
//        //仮置き
//        return list.get(0);
//    }

}

