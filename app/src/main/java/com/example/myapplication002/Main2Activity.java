package com.example.myapplication002;

import android.annotation.SuppressLint;
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

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;

public class Main2Activity extends AppCompatActivity implements Runnable{
    EditText rmb;
    TextView currency;
    private Float DollarRate;
    private Float EuroRate;
    private  Float PoundRate;
    Handler handler;


    @SuppressLint("HandlerLeak")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        rmb = (EditText) findViewById(R.id.SEARCH);
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
                   Bundle b=(Bundle)msg.obj;
                   DollarRate=b.getFloat("Dollar_Rate");
                   EuroRate=b.getFloat("Euro_Rate");
                   PoundRate=b.getFloat("Pound_Rate");

                   Toast.makeText(Main2Activity.this,"汇率已更新！",Toast.LENGTH_SHORT).show();
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
            editor.apply();




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

        

        Bundle bundle=new Bundle();

        Document doc=null;
        try{
            doc= Jsoup.connect("http://www.usd-cny.com/bankofchina.htm").get();
            Elements tables=doc.getElementsByTag("table");

            Element table6=tables.get(5);
            Elements tds=table6.getElementsByTag("td");
            for(int i=0;i<tds.size();i+=8){
                Element td1=tds.get(i);
                Element td2=tds.get(i+5);

                String str=td1.text();
                String value=td2.text();

                if("美元".equals(str)){
                    bundle.putFloat("Dollar_Rate",100f/Float.parseFloat(value));
                }else if("欧元".equals(str)){
                    bundle.putFloat("Euro_Rate",100f/Float.parseFloat(value));
                }else if("英镑".equals(str)){
                    bundle.putFloat("Pound_Rate",100f/Float.parseFloat(value));
                }

            }

        }catch(IOException e){
            e.printStackTrace();
        }

     //获取Message对象，用于返回主线程
     Message msg=handler.obtainMessage(5);
      msg.obj=bundle;
     handler.sendMessage(msg);


    }
    //获取网页输入
    private String inputStreamString(InputStream inputStream)throws IOException{
        final int bufferSize=1024;
        final char[] buffer=new char[bufferSize];
        final StringBuilder out=new StringBuilder();
        Reader in=new InputStreamReader(inputStream,"gb2312");
        for(;;){
            int rsc=in.read(buffer,0,buffer.length);
            if(rsc<0)
                break;
            out.append(buffer,0,rsc);
        }
        return out.toString();
    }

}
