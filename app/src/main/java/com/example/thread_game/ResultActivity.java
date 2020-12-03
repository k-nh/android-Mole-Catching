package com.example.thread_game;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class ResultActivity extends AppCompatActivity {

    Button bt_replay;
    TextView tv_finalScore;
    TextView tv_rankId;
    TextView tv_rankScore;
    EditText et_id;
    Button bt_save;

    int score;

    SharedPreferences spf;
    SharedPreferences.Editor editor2;

    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.result);
        bt_replay=findViewById(R.id.btn_replay);
        tv_finalScore=findViewById(R.id.tv_finalScore);
        et_id = findViewById(R.id.id);
        bt_save = findViewById(R.id.save);
        tv_rankId = findViewById(R.id.tv_rankid);
        tv_rankScore = findViewById(R.id.tv_rankscore);


         score = getIntent().getIntExtra("score",-1);

        tv_finalScore.setText(String.valueOf(score));

         sharedPreferences = getSharedPreferences("id", MODE_PRIVATE);
         editor = sharedPreferences.edit();

        spf = getSharedPreferences("spfScore",MODE_PRIVATE);
        editor2 = spf.edit();


        tv_rankId.setText(sharedPreferences.getString("id",""));
        tv_rankScore.setText(spf.getInt("spfscore",0)+"");


        bt_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {




                //tv_rankScore.setText(spf.getString("spfscore",""));

                if(spf.getInt("spfscore",0) < score){ //내점수가 저번 점수보다 크면
                    editor.putString("id",et_id.getText().toString()).commit();
                    editor.apply();

                    editor2.putInt("spfscore",score).commit(); //반영의 commit(). 현재상태 저장
                    editor2.apply();

                }


                tv_rankId.setText(sharedPreferences.getString("id",""));
                tv_rankScore.setText(spf.getInt("spfscore",0)+"");




            }
        });






        bt_replay.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ResultActivity.this,GameActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }
}
