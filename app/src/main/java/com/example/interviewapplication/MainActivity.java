package com.example.interviewapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        findViewById(R.id.button1).setOnClickListener(
                new View.OnClickListener(){
                    @Override
                    public void onClick(View view){
                        Intent intent = new Intent(MainActivity.this,AnswerActivity.class);
                        Question question = selectQuestion("intro");
                        intent.putExtra("id",question);
                        startActivity(intent);
                    }
                }
        );

        findViewById(R.id.button2).setOnClickListener(
                new View.OnClickListener(){
                    @Override
                    public void onClick(View view){
                        Intent intent = new Intent(MainActivity.this,AnswerActivity.class);
                        Question question = selectQuestion("gakuchika");
                        intent.putExtra("id",question);
                        startActivity(intent);
                    }
                }
        );

        findViewById(R.id.button3).setOnClickListener(
                new View.OnClickListener(){
                    @Override
                    public void onClick(View view){
                        Intent intent = new Intent(MainActivity.this,AnswerActivity.class);
                        Question question = selectQuestion("strength");
                        intent.putExtra("id",question);
                        startActivity(intent);
                    }
                }
        );

        findViewById(R.id.button4).setOnClickListener(
                new View.OnClickListener(){
                    @Override
                    public void onClick(View view){
                        Intent intent = new Intent(MainActivity.this,AnswerActivity.class);
                        Question question = selectQuestion("weakness");
                        intent.putExtra("id",question);
                        startActivity(intent);
                    }
                }
        );
        System.out.println("onCreate()");
    }

    @Override
    protected void onStart(){
        super.onStart();
        System.out.println("onStart()");
    }

    @Override
    protected void onResume(){
        super.onResume();
        System.out.println("onResume()");
    }

    @Override
    protected void onPause(){
        super.onPause();
        System.out.println("onPause()");
    }

    @Override
    protected void onStop(){
        super.onStop();
        System.out.println("onStop()");
    }

    @Override
    protected void onRestart(){
        super.onRestart();
        System.out.println("onRestart()");
    }

    @Override
    protected void onDestroy(){
        super.onDestroy();
        System.out.println("onDestroy()");
    }

    private Question selectQuestion(String id){
        QuestionRepository questionRepository=new QuestionRepository();
        return questionRepository.getQuestion(id);

    }


}