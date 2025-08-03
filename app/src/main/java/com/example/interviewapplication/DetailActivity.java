package com.example.interviewapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class DetailActivity extends AppCompatActivity {

    private String id;
    private TextView textView;

    private long nowId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        id = getIntent().getStringExtra("id");
        nowId = getIntent().getLongExtra("nowId",-1);

        textView = (TextView) findViewById(R.id.text);

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

        textView.setText(id);
    }
}
