package com.example.interviewapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.room.Room;

import java.util.List;

public class AnswerActivity extends AppCompatActivity {

    private TextView textView;
    private Question question;

    private EditText editText;
    private AppDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_answer);

        question = getIntent().getParcelableExtra("id");
        textView = (TextView) findViewById(R.id.text);

        db = Room.databaseBuilder(getApplicationContext(),
                AppDatabase.class, "data").build();

        LocalDataDao localDataDao = db.localDataDao();
//        List<Data> listData = localDataDao.getAll();値表示maybe



        findViewById(R.id.button1).setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(AnswerActivity.this,MainActivity.class);
                        startActivity(intent);
                    }
                }
        );

        findViewById(R.id.button2).setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        LocalDataStore localDataStore = new LocalDataStore();
                        editText = findViewById(R.id.edittext);
                        String text = editText.getText().toString();
                        localDataStore.add(text);

                        System.out.println(localDataStore.get());

                        new Thread(()-> {
                            //room
                            Data data = new Data();
                            data.uid = 5;
                            data.firstName = "Taro";
                            data.lastName = "Yamada";
                            localDataDao.insertAll(data);
                            System.out.println(localDataDao.getAll());

                        }).start();

                        Intent intent2 = new Intent(AnswerActivity.this,ResultActivity.class);
                        startActivity(intent2);
                    }
                }
        );
    }

    @Override
    protected void onStart(){
        super.onStart();

        textView.setText(question.getText());
    }
}
