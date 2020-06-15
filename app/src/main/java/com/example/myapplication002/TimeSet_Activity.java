package com.example.myapplication002;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class TimeSet_Activity extends AppCompatActivity {
    TextView time;
    EditText timeinput;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_time_set_);

        timeinput=(EditText)findViewById(R.id.TIME);
        time=(TextView)findViewById(R.id.textViewtime);

    }

    public void Return(View btn){
        int a=Integer.parseInt(timeinput.getText().toString());

        SharedPreferences share=getSharedPreferences("MyRank", Activity.MODE_PRIVATE);
        SharedPreferences.Editor editor=share.edit();
        editor.putInt("Time",a);
        editor.commit();

        finish();
    }


}
