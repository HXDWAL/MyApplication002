package com.example.myapplication002;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class Main2Activity extends AppCompatActivity {
    EditText rmb;
    TextView currency;
    private Float DollarRate=(1/6.5f);
    private Float EuroRate=(1/10f);
    private  Float PoundRate=(1/8f);

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
            float value=f*DollarRate;
            currency.setText(""+value+"$");

        }
        else if(btn.getId()==R.id.btnEuro){
            double value=f*EuroRate;
            currency.setText(""+value+"€");

        }else if(btn.getId()==R.id.btnPound){
            double value=f*PoundRate;
            currency.setText(""+value+"￡");

        }else{
            Toast.makeText(this,"请输入金额",Toast.LENGTH_SHORT).show();
        }


    }

    public void SetRate(View btn){
        Intent set=new Intent(this,RateSetActivity.class);
        set.putExtra("rate_of_Dollar",DollarRate);
        set.putExtra("rate_of_Euro",EuroRate);
        set.putExtra("rate_of_Pound",PoundRate);

       // startActivity(set);
        startActivityForResult(set,1);


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent date){
        if(requestCode==1&&resultCode==2){
            Bundle bundle=date.getExtras();
            DollarRate=bundle.getFloat("key_Dollar");
            EuroRate=bundle.getFloat("key_Euro");
            PoundRate=bundle.getFloat("key_Pound");


        }
        super.onActivityResult(requestCode,resultCode,date);
    }

}
