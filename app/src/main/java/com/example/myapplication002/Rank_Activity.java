package com.example.myapplication002;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
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



        Intent intent=getIntent();
        SharedPreferences share=getSharedPreferences("MyRank", Activity.MODE_PRIVATE);


        Top1.setText(""+share.getInt("New_record01",0));
        Top2.setText(""+share.getInt("New_record02",0));
        Top3.setText(""+share.getInt("New_record03",0));

    }

    public void Return(View btn){
        finish();
    }

}
