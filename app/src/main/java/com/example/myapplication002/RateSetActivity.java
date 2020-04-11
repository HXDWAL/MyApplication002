package com.example.myapplication002;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

public class RateSetActivity extends AppCompatActivity {
    EditText DollarRate;
    EditText EuroRate;
    EditText PoundRate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rate_set);

        DollarRate=(EditText)findViewById(R.id.dollarRate);
        EuroRate=(EditText)findViewById(R.id.euroRate);
        PoundRate=(EditText)findViewById(R.id.poundRate);

        Intent intent=getIntent();
        Float Dor=intent.getFloatExtra("rate_of_Dollar",0.0f);
        Float Eur=intent.getFloatExtra("rate_od_Euro",0.0f);
        Float Pnd=intent.getFloatExtra("rate_of_Pound",0.0f);
        //接收数据

        DollarRate.setText(String.valueOf(Dor));
        EuroRate.setText(String.valueOf(Eur));
        PoundRate.setText(String.valueOf(Pnd));
        //显示数据


    }
    public void UpdateRate(View btn){


    }

    public void Back(View btn){
        Intent back=new Intent(this,Main2Activity.class);
        startActivity(back);

    }
}
