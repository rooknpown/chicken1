package com.example.chicken;

import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.speech.tts.TextToSpeech;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.GlideDrawableImageViewTarget;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Locale;

import static android.speech.tts.TextToSpeech.ERROR;

public class ThirdActivity extends AppCompatActivity {
    private ArrayList<Second_order_info> order_array;
    private TextToSpeech tts;
    MediaPlayer mediaPlayer;
    private ArrayList<String> match_text;
    private static final int REQUEST_CODE = 1234;
    String[] anslist;
    private String daesa;

//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_third);
//        ImageView gif = (ImageView)findViewById(R.id.gif);
//        GlideDrawableImageViewTarget imageViewTarget = new GlideDrawableImageViewTarget(gif);
//        Glide.with(this).load(R.raw.loading).into(gif);
//
//        Intent intent = getIntent();
//        order_array = (ArrayList<Second_order_info>)intent.getExtras().getSerializable("order_array");
//
//        mediaPlayer = MediaPlayer.create(ThirdActivity.this, R.raw.hi);
//        mediaPlayer.start();
//        new Waiter().execute();
//    }
//    class Waiter extends AsyncTask<Void,Void,Void> {
//        @Override
//        protected Void doInBackground(Void... voids) {
//            while (mediaPlayer.isPlaying()){
//                try{Thread.sleep(500);}catch (Exception e){}
//            }
//            return null;
//        }
//        @Override
//        protected void onPostExecute(Void aVoid) {
//            super.onPostExecute(aVoid);
//            if(isConnected()){
//                Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
//                intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,
//                        RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
//                intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, "ko-KR");
//                startActivityForResult(intent, REQUEST_CODE);
//            }
//        }
//    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_third);
        Intent intent = getIntent();
        anslist = new String[] {"asdf", "asdf", "asdf", "asdf"};

        tts = new TextToSpeech(this, new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if(status != ERROR){
                    tts.setLanguage(Locale.KOREAN);
                    tts.setSpeechRate((float)1.5);
                    tts.speak("안녕하세요, 몰입 치킨입니다. 무엇을 도와드릴까요?", TextToSpeech.QUEUE_FLUSH, null);
                    new Waiter().execute();
                }
            }
        });
    }
    class Waiter extends AsyncTask<Void,Void,Void> {
        @Override
        protected Void doInBackground(Void... voids) {
            while (tts.isSpeaking()){
                try{Thread.sleep(500);}catch (Exception e){}
            }
            return null;
        }
        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            if(isConnected()){
                Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
                intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                        RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
                intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, "ko-KR");
                startActivityForResult(intent, REQUEST_CODE);
                Log.d("string" , "----------------------");
            }

        }
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == REQUEST_CODE && resultCode == RESULT_OK){
            match_text = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
            try{
                String[] result = new DeepLearning1(ThirdActivity.this, MainActivity.komoran, MainActivity.retMap).getAnswer(match_text.get(0));
                for(int i=0;i<4;i++){
                    if(!(result[i].equals("asdf"))){
                        anslist[i] = result[i];
                    }
                }
                if(!(anslist[0].equals("asdf"))&&!(anslist[1].equals("asdf"))&&!(anslist[2].equals("asdf"))){
                    daesa = "네. 그럼 30분뒤에 "+ anslist[1] +" 로 " + anslist[0] + " 치킨 "  +  anslist[3] +"마리 가져다 드리겠습니다. 감사합니다.";
                }
                else if((anslist[0].equals("asdf"))&&!(anslist[1].equals("asdf"))){
                    daesa = "네. 어떤 메뉴 주문하시겠어요?";
                }
                else if(!(anslist[0].equals("asdf"))&&(anslist[1].equals("asdf"))){
                    daesa = "네. 어디로 배달해 드릴까요?";
                }
                else if(!(anslist[0].equals("asdf"))&&!(anslist[1].equals("asdf"))&&(anslist[2].equals("asdf"))) {
                    if(anslist[3].equals("asdf")){
                        daesa = "네. 몇 마리 배달해 드릴까요?";
                    }
                    else{
                        daesa = "네. 결제 현금으로 할까요 카드로 할까요?";
                    }
                }
                else{
                    daesa = "네?";
                }
                speakCheckInBackground(daesa);
            }catch(IOException e){

            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    public void speakCheckInBackground(String text) {
        Log.d("string" , "======================");
        tts.speak(text, TextToSpeech.QUEUE_FLUSH, null);
        if(!(!(anslist[0].equals("asdf"))&&!(anslist[1].equals("asdf"))&&!(anslist[2].equals("asdf")))) new Waiter().execute();

    }

    public  boolean isConnected()
    {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo net = cm.getActiveNetworkInfo();
        if (net!=null && net.isAvailable() && net.isConnected()) {
            return true;
        } else {
            return false;
        }
    }

}
