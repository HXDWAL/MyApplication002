package com.example.myapplication002;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class Main2Activity extends AppCompatActivity implements Runnable{
    EditText rmb;
    TextView currency;
    private Float DollarRate;
    private Float EuroRate;
    private  Float PoundRate;
    Handler handler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        rmb = (EditText) findViewById(R.id.RMB);
        currency = (TextView) findViewById(R.id.Currency);

        SharedPreferences share = getSharedPreferences("MyRate", Activity.MODE_PRIVATE);
        DollarRate = share.getFloat("Dollar_Rate", 0.0f);
        EuroRate = share.getFloat("Euro_Rate", 0.0f);
        PoundRate = share.getFloat("Pound_Rate", 0.0f);

        //开启子线程
        Thread t = new Thread(this);
        t.start();

        handler = new Handler() {
            public void handerMessage(Message msg) {

                if (msg.what == 5) {
                    String str = (String) msg.obj;
                }
                super.handleMessage(msg);
            }

        };
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

            SharedPreferences sp=getSharedPreferences("MyRate",Activity.MODE_PRIVATE);
            SharedPreferences.Editor editor=sp.edit();
            editor.putFloat("Dollar_Rate",DollarRate);
            editor.putFloat("Euro_Rate",EuroRate);
            editor.putFloat("Pound_Rate",PoundRate);
            editor.commit();




        }
        super.onActivityResult(requestCode,resultCode,date);
    }

    @Override
    public void run(){
        for(int i=1;i<6;i++){
          try {
              Thread.sleep(1000);
          }catch(InterruptedException e){
              e.printStackTrace();
          }
        }
        //获取Message对象，用于返回主线程
        Message msg=handler.obtainMessage();
        msg.what=5;
        msg.obj="Hello World!";
        handler.sendMessage(msg);



    }

}
