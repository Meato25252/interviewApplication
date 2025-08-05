package com.example.interviewapplication;

import android.content.Intent;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
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
import java.util.Locale;

public class AnswerActivity extends AppCompatActivity{

    private TextView textView;
    private Question question;
    private EditText editText;
    private AppDatabase db;
    private long nowId;
    private TextToSpeech textToSpeech;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_answer);



        textToSpeech= new TextToSpeech(AnswerActivity.this, new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if (TextToSpeech.SUCCESS == status) {
                    System.out.println("debug"+"initialized");
                    textToSpeech.setLanguage(Locale.JAPANESE);
                    textToSpeech.speak(question.getText(),TextToSpeech.QUEUE_FLUSH,null);
                } else {
                    System.out.println("debug"+"failed to initialize");
                }
            }
        });

        question = getIntent().getParcelableExtra("id");
        textView = (TextView) findViewById(R.id.text);

        System.out.println("■："+question);

        db = Room.databaseBuilder(getApplicationContext(),
                AppDatabase.class, "data").build();

        LocalDataDao localDataDao = db.localDataDao();
        Data data = new Data();
//        LocalDataStore localDataStore = new LocalDataStore();


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
                        editText = findViewById(R.id.edittext);
                        String text = editText.getText().toString();
//                        localDataStore.add(text);

                        new Thread(()-> {
                            //room
                            data.text1 = text;
                            data.tag = question.getText();
                            nowId=localDataDao.insertAll(data);
                            System.out.println(localDataDao.getAll());
                            System.out.println(nowId);

                            runOnUiThread(()->{
                                System.out.println(nowId);
                                Intent intent2 = new Intent(AnswerActivity.this,ResultActivity.class);
                                intent2.putExtra("nowId",nowId);
                                startActivity(intent2);
                            });
                        }).start();
                    }
                }
        );
    }

    @Override
    protected void onStart(){
        super.onStart();

        textView.setText(question.getText());

    }

    @Override
    protected void onDestroy(){
        super.onDestroy();
        textToSpeech.stop();
        textToSpeech.shutdown();
    }

}
