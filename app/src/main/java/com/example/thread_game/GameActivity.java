package com.example.thread_game;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import java.util.Random;

public class GameActivity extends AppCompatActivity {
    Thread thread = null;
    TextView time;
    TextView score;
    ImageView[] imgViewArr = new ImageView[16];
    int[] imageId = {R.id.card01, R.id.card02, R.id.card03, R.id.card04, R.id.card05, R.id.card06, R.id.card07, R.id.card08, R.id.card09, R.id.card10, R.id.card11, R.id.card12, R.id.card13, R.id.card14, R.id.card15, R.id.card16};
    public static final int ran[] = {R.drawable.up_mole, R.drawable.up_mole1, R.drawable.up_rabbit};
    int sc = 0;

    final String TAG_Mole1 = "mole"; //태그용
    final String TAG_Mole2 = "mole1";
    final String TAG_Rabbit = "rabbit";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.game);

        time = findViewById(R.id.time_tv);
        score = findViewById(R.id.score_tv);

        for (int i = 0; i < imgViewArr.length; i++) {
            imgViewArr[i] = (ImageView) findViewById(imageId[i]);
            imgViewArr[i].setImageResource(R.drawable.off);

            imgViewArr[i].setOnClickListener(new View.OnClickListener() { //두더지이미지에 온클릭리스너
                @Override
                public void onClick(View v) {
                    if (((ImageView) v).getTag().toString().equals(TAG_Mole1)) {
                        score.setText("Point : "+String.valueOf(sc += 200));
                        ((ImageView) v).setImageResource(R.drawable.off);
                    } else if (((ImageView) v).getTag().toString().equals(TAG_Mole2)) {
                        score.setText("Point : "+String.valueOf(sc -= 100));
                        ((ImageView) v).setImageResource(R.drawable.off);
                    } else {
                        score.setText("Point : "+String.valueOf(sc -= 200));
                        ((ImageView) v).setImageResource(R.drawable.off);
                    }
                }

            });
        }

        time.setText("Time : 20");
        score.setText("Point : 0");

        new Thread(new timer()).start();
        for (int i = 0; i < imgViewArr.length; i++) {
            new Thread(new objectThread(i)).start();
        }

    }




    @SuppressLint("HandlerLeak")
    Handler handler = new Handler(Looper.getMainLooper()) {
        @Override
        public void handleMessage(Message msg) {

            time.setText("Time : "+ msg.arg1);
        }
    };

    Handler onHandler = new Handler(Looper.getMainLooper()){
        @Override
        public void handleMessage(Message msg) {
            for (int i=0;i<5;i++){
                int index = (int) (Math.random() * 3);
                imgViewArr[msg.arg1].setImageResource(ran[index]);
                if(index==0){
                    imgViewArr[msg.arg1].setTag(TAG_Mole1);
                }
                else if(index==1){
                    imgViewArr[msg.arg1].setTag(TAG_Mole2);
                }
                else{
                    imgViewArr[msg.arg1].setTag(TAG_Rabbit);
                }
            }
        }
    };

    Handler offHandler = new Handler(Looper.getMainLooper()){
        @Override
        public void handleMessage(Message msg) {
            imgViewArr[msg.arg1].setImageResource(R.drawable.off);
        }
    };


    public class timer implements Runnable {
        final int TIME = 20;

        @Override
        public void run() {
            for (int i = TIME; i >= 0; i--) {
                Message msg = new Message();
                msg.arg1 = i;
                handler.sendMessage(msg);

                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            }
            Intent intent = new Intent(GameActivity.this, ResultActivity.class);
            intent.putExtra("score", sc);
            startActivity(intent);
            finish();
        }
    }

    public class objectThread implements Runnable{

        int index;

        objectThread(int index){
            this.index=index;
        }

        @Override
        public void run() {
            while(true){
                try {
                    Message msg1 = new Message();
                    int offtime = new Random().nextInt(6000) + 1000;
                    Thread.sleep(offtime); //두더지가 내려가있는 시간

                    msg1.arg1 = index;
                    onHandler.sendMessage(msg1);

                    int ontime = new Random().nextInt(3000)+ 500;
                    Thread.sleep(ontime); //두더지가 올라가있는 시간
                    Message msg2 = new Message();
                    msg2.arg1= index;
                    offHandler.sendMessage(msg2);

                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }


}
