package com.example.interviewapplication;

import static android.Manifest.permission.RECORD_AUDIO;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
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

        if (ContextCompat.checkSelfPermission(this, RECORD_AUDIO) != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this, RECORD_AUDIO)) {
                showPermissionExplanation();
            } else {
                int MY_PERMISSIONS_RECORD_AUDIO = 1;
                ActivityCompat.requestPermissions(this, new String[]{RECORD_AUDIO}, MY_PERMISSIONS_RECORD_AUDIO);
            }
        }

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

    private Question selectQuestion(String id){
        QuestionRepository questionRepository=new QuestionRepository();
        return questionRepository.getQuestion(id);
    }

    private void showPermissionExplanation(){
        new AlertDialog.Builder(this)
                .setTitle("マイクの権限が必要です")
                .setMessage("音声入力機能を使うためにマイクの権限が必要です")
                .setPositiveButton("許可する", (dialog, which) -> {
                    ActivityCompat.requestPermissions(this, new String[]{RECORD_AUDIO}, 1);
                })
                .setNegativeButton("キャンセル", null)
                .show();
    }
}