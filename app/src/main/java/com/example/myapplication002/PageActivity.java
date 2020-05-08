package com.example.myapplication002;

import android.app.ListActivity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.HashMap;

public class PageActivity extends ListActivity {
    EditText search;
    ListView Result;
    private String target[];
    private ArrayList<HashMap<String,String>> listItems;
    private SimpleAdapter listItemAdapter; //适配器
    private int msgwhat=7;

    Handler handler;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_page);

        search=(EditText)findViewById(R.id.SEARCH);
        Result=(ListView)findViewById(R.id.result);
    }

    public void onClick(View btn){

    }


    //获取网页数据
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
            doc= Jsoup.connect("https://it.swufe.edu.cn/").get();
            Elements h3s=doc.getElementsByTag("h3");
            Element h3=h3s.get(3);

            Elements yinhao=h3.getElementsByTag("\"");

            int j=0;
            for( int i=0;i<h3s.size();i+=10){
                Element Herf=yinhao.get(i);
                Element Text=h3s.get(i+2);

                String herf=Herf.text();
                String title=Text.text();
                //使用字符串数组存储获取的数据，偶序号结点存储连接，奇序号存储标题
                target[j]=herf;
                target[j+1]=title;
                j=j+2;




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
    private String inputStreamString(InputStream inputStream)throws IOException {
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
