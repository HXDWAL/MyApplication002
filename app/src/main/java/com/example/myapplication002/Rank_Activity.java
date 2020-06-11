package com.example.myapplication002;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class Rank_Activity extends AppCompatActivity {
    TextView Top1;
    TextView Top2;
    TextView Top3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rank_);

        Top1=(TextView)findViewById(R.id.top1);
        Top2=(TextView)findViewById(R.id.top2);
        Top3=(TextView)findViewById(R.id.top3);

        int a=Integer.parseInt(Top1.getText().toString());
        int b=Integer.parseInt(Top2.getText().toString());
        int c=Integer.parseInt(Top3.getText().toString());


        Intent intent=getIntent();
        int record=intent.getIntExtra("New_Score",0);

        if (record>=a){
            Top1.setText(""+record);
            Top2.setText(""+a);
            Top3.setText(""+b);
        }else if(record>=b){
            Top2.setText(""+record);
            Top3.setText(""+b);
        }else if(record>=c){
            Top3.setText(""+record);
        }
    }

    public void Return(View btn){
        finish();
    }

}
