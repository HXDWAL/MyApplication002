package com.example.myapplication002;

import android.app.Activity;
import android.app.ListActivity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class PageActivity extends ListActivity implements Runnable, AdapterView.OnItemClickListener{
    EditText search;
    ListView Result;
    private ArrayList<HashMap<String,String>> listItems;
    private SimpleAdapter listItemAdapter; //适配器
    private int msgwhat=7;
    Handler handler;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initListView();
        this.setListAdapter(listItemAdapter);

        search=(EditText)findViewById(R.id.SEARCH);
        Result=(ListView)findViewById(R.id.result);

        Thread t=new Thread(this);
        t.start();

        handler = new Handler() {
            public void handerMessage(Message msg) {
                if(msg.what==msgwhat){
                    List<HashMap<String,String>> retlist=(List<HashMap<String, String>>)msg.obj;
                    SimpleAdapter adapter=new SimpleAdapter(PageActivity.this,retlist,
                            R.layout.list_item,
                            new String[]{"ItemTitle","ItemHerf"},
                            new int[]{R.id.itemTitle,R.id.itemHerf});
                    setListAdapter(adapter);
                }
                super.handleMessage(msg);
            }
        };
        getListView().setOnItemClickListener(this);
    }


    private void initListView(){
        listItems=new ArrayList<HashMap<String, String>>();
        for(int i=0;i<5;i++){
            HashMap<String,String> map=new HashMap<String,String>();
            map.put("ItemTitle","Title:"+i);
            map.put("ItemHerf","Herf:"+i);
            listItems.add(map);
        }
        listItemAdapter=new SimpleAdapter(this,listItems,
                R.layout.list_item,
                new String[]{"ItemTitle","ItemHerf"},
                new int[]{R.id.itemTitle,R.id.itemHerf}
                );
    }


    //获取网页数据
    public void run(){
        boolean marker=false;
        List<HashMap<String,String>> resultlist=new ArrayList<HashMap<String, String>>();

        try{
            Document doc= Jsoup.connect("https://it.swufe.edu.cn/").get();
            Elements h3s=doc.getElementsByTag("h3");
            Element h3=h3s.get(3);
            Elements yinhaos=h3.getElementsByAttribute("\"");

            for( int i=27;i<yinhaos.size();i+=10){

                Element Text=yinhaos.get(i+2);
                Element Herf=yinhaos.get(i);

                String herf=Herf.text();
                String title=Text.text();

                HashMap<String,String> map=new HashMap<String, String>();
                map.put("ItemTitle",title);
                map.put("ItemHerf",herf);
                resultlist.add(map);

                SharedPreferences sp=getSharedPreferences("PageActivity", Activity.MODE_PRIVATE);
                SharedPreferences.Editor editor=sp.edit();
                String key=String.valueOf(i);
                editor.putString("Title"+key,title);
                editor.putString("Herf"+key,herf);
                editor.apply();

            }
            marker=true;

        }catch(IOException e){
            e.printStackTrace();
        }

        //获取Message对象，用于返回主线程
        Message msg=handler.obtainMessage();
        msg.what=msgwhat;
        if(marker){
            msg.arg1=1;
        }else{
            msg.arg1=0;
        }

        msg.obj=resultlist;
        handler.sendMessage(msg);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        //获取列表中的herf项数据，调用默认浏览器访问网页
        HashMap<String,String> map=(HashMap<String, String>)getListView().getItemAtPosition(position);
        String herf=map.get("ItemHerf");
        Intent intent=new Intent();
        intent.setAction("android.intent.action.VIEW");
        Uri url=Uri.parse(herf);
        intent.setData(url);
        startActivity(intent);

    }

}
