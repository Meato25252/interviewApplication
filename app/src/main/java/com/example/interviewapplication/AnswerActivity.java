package com.example.interviewapplication;

import static android.Manifest.permission.RECORD_AUDIO;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.speech.RecognitionListener;
import android.speech.RecognizerIntent;
import android.speech.SpeechRecognizer;
import android.speech.tts.TextToSpeech;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.room.Room;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import android.os.CountDownTimer;

import org.w3c.dom.Text;

public class AnswerActivity extends AppCompatActivity{

    private TextView textView;
    private Question question;
    private EditText editText;
    private AppDatabase db;
    private long nowId;
    private TextToSpeech textToSpeech;
    private SpeechRecognizer speechRecognizer;
    private Intent intentR;
    private ArrayList<String> matches;
    private CountDownTimer countDownTimer;
    private TextView textView2;
    private boolean isTimer = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_answer);



        if (ContextCompat.checkSelfPermission(this, RECORD_AUDIO) != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this, RECORD_AUDIO)) {
                // 拒否した場合
            } else {
                // 許可した場合
                int MY_PERMISSIONS_RECORD_AUDIO = 1;
                ActivityCompat.requestPermissions(this, new String[]{RECORD_AUDIO}, MY_PERMISSIONS_RECORD_AUDIO);
            }
        }


        speechRecognizer = SpeechRecognizer.createSpeechRecognizer(AnswerActivity.this);
        speechRecognizer.setRecognitionListener(new RecognitionListener() {
            @Override
            public void onBeginningOfSpeech() {}
            @Override
            public void onBufferReceived(byte[] buffer) {}
            @Override
            public void onEndOfSpeech() {}
            @Override
            public void onError(int error) {
                System.out.println("SpeechRecognizer エラー: " + error);
                countDownTimer.cancel();
                textView2.setText("エラー");
                switch (error) {
                    case SpeechRecognizer.ERROR_AUDIO:
                        System.out.println("ERROR_AUDIO");
                        break;
                    case SpeechRecognizer.ERROR_CLIENT:
                        System.out.println("ERROR_CLIENT");
                        isTimer=true;
                        System.out.println(isTimer);
                        break;
                    case SpeechRecognizer.ERROR_INSUFFICIENT_PERMISSIONS:
                        System.out.println("ERROR_INSUFFICIENT_PERMISSIONS");
                        break;
                    case SpeechRecognizer.ERROR_NETWORK:
                        System.out.println("ERROR_NETWORK");
                        break;
                    case SpeechRecognizer.ERROR_NO_MATCH:
                        System.out.println("ERROR_NO_MATCH");
                        isTimer=true;
                        System.out.println(isTimer);
                        break;
                    case SpeechRecognizer.ERROR_RECOGNIZER_BUSY:
                        System.out.println("ERROR_RECOGNIZER_BUSY");
                        break;
                    default:
                        System.out.println("その他のエラー: " + error);
                }
            }
            @Override
            public void onEvent(int eventType, Bundle params) {}
            @Override
            public void onPartialResults(Bundle partialResults) {}
            @Override
            public void onReadyForSpeech(Bundle params) {}
            @Override
            public void onResults(Bundle results) {
                matches = results.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION);
                if (matches != null && !matches.isEmpty()) {
                    System.out.println("Speech" + "認識結果: " + matches.get(0));
                    countDownTimer.cancel();
                    isTimer=true;
                    System.out.println(isTimer);
                    textView2.setText("終了");
                }else{
                    System.out.println("★★null");
                }
            }
            @Override
            public void onRmsChanged(float rmsdB) {}
        });


        question = getIntent().getParcelableExtra("id");

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

        textView = (TextView) findViewById(R.id.text);
        textView2 = (TextView) findViewById(R.id.timer);

        System.out.println("■："+question);

        db = Room.databaseBuilder(getApplicationContext(),
                AppDatabase.class, "data").build();

        LocalDataDao localDataDao = db.localDataDao();
        Data data = new Data();

        intentR = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intentR.putExtra(RecognizerIntent.EXTRA_CALLING_PACKAGE, getPackageName());
        intentR.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intentR.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());


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
                        new Thread(()-> {
                            if(matches == null || matches.isEmpty()){
                                data.text1="入力がされませんでした。";
                            }else{
                                data.text1 = matches.get(0);
                            }
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


        findViewById(R.id.button3).setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        System.out.println("aaaa");
                        speechRecognizer.startListening(intentR);
                        if(isTimer) {
                            System.out.println("test1");
                            startTimer();
                            isTimer=false;
                        }else{
                            System.out.println("test2");
                            countDownTimer.cancel();
                            speechRecognizer.cancel();
                            isTimer=true;
                        }
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
        speechRecognizer.cancel();
        speechRecognizer.destroy();
    }

    private void startTimer(){
        countDownTimer = new CountDownTimer(30000,1000) {
            @Override
            public void onFinish() {
                textView2.setText("時間切れ");
                speechRecognizer.stopListening();
            }
            @Override
            public void onTick(long millisUntilFinished) {
                textView2.setText(String.valueOf("00:"+millisUntilFinished/1000));
            }

        }.start();
        countDownTimer.start();
    }
}