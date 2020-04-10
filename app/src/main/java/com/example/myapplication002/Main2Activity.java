package com.example.myapplication002;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class Main2Activity extends AppCompatActivity {
    EditText rmb;
    TextView currency;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        rmb=(EditText)findViewById(R.id.RMB);
        currency=(TextView)findViewById(R.id.Currency);
    }

    public void onClick(View btn){
        String str=rmb.getText().toString();
        Float f=0f;
        if(str.length()>0){
           f=Float.parseFloat(str);
        }
        if(btn.getId()==R.id.btnDollar){
            float value=f*(1/6.5f);
            currency.setText(""+value+"$");

        }
        else if(btn.getId()==R.id.btnEuro){
            double value=f*(1/9);
            currency.setText(""+value+"€");

        }else if(btn.getId()==R.id.btnPound){
            double value=f*(1/8);
            currency.setText(""+value+"￡");

        }else{
            Toast.makeText(this,"请输入金额",Toast.LENGTH_SHORT).show();
        }


    }

}
