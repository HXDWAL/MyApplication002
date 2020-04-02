package com.example.myapplication002;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class Main3Activity extends AppCompatActivity {
    TextView score;
    TextView score1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);
        score=(TextView)findViewById(R.id.score);
        score1=(TextView)findViewById(R.id.score1);

    }
    public void btnAdd1(View btn){
        showscore(1);
    }
    public void btnAdd1b(View btn){
        showscore1(1);
    }


    public void btnAdd2(View btn){
        showscore(2);
    }
    public void btnAdd2b(View btn){
        showscore1(2);
    }

    public void btnAdd3(View btn){
        showscore(3);
    }
    public void btnAdd3b(View btn){
        showscore1(3);
    }


    public void btnReset(View btn){
        score.setText("0");
        score1.setText("0");
    }
    public void showscore(int value){
        String previous=(String)score.getText();
        int newscore=Integer.parseInt(previous)+value;
        score.setText(""+newscore);
    }
    public void showscore1(int value){
        String previous=(String)score1.getText();
        int newscore=Integer.parseInt(previous)+value;
        score1.setText(""+newscore);
    }

}
