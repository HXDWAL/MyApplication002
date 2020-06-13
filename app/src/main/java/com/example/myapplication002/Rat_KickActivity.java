package com.example.myapplication002;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

public class Rat_KickActivity extends AppCompatActivity implements View.OnClickListener {
    //开始游戏，点击按纽后地鼠作为一个可点击对象出现在屏幕中央，并且会随着倒计时的结束而消失
    private Button btn;
    private Button rank;
    private TextView scoreshow; //击中地鼠后加分
    private TextView timeshow; //用handler写一个计时器方法，判断剩余时间
    private ImageView rat; //用于触发事件，击中老鼠后老鼠消失，随机生成新的位置，并且分数增加
    int height;//定义屏幕高度
    int width;//屏幕宽度
    int Score=0;
    int Record=0;
    int Time=10; //初始化游戏的分数和时间变量

    @SuppressLint("HandlerLeak")
    Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg){
            super.handleMessage(msg);
            if(msg.what==100){
                int time=msg.arg1;
                if(time>0){
                    timeshow.setText(time+"");
                }else{
                    btn.setVisibility(View.VISIBLE);
                    timeshow.setText(""+10);
                    rat.setClickable(false);
                    Toast.makeText(Rat_KickActivity.this, "游戏结束", Toast.LENGTH_SHORT).show();
                    //若时间已到，则还原按钮初始状态，并且设置地鼠为不可点击，游戏结束
                    Record=Score;
                    Score=0;
                    //将分数值传给Record，之后分数归零

                    SharedPreferences share=getSharedPreferences("MyRank", Activity.MODE_PRIVATE);
                    int a=share.getInt("New_record01",0);
                    int b=share.getInt("New_record02",0);
                    int c=share.getInt("New_record03",0);
                    if(Record>a||Record>b||Record>c){
                        if (Record>a){
                            c=b;
                            b=a;
                            a=Record;
                        }else if (Record>b){
                            c=b;
                            b=Record;
                        }else{
                            c=Record;
                        }
                    }
                    //更新SP文件数据
                    SharedPreferences.Editor editor=share.edit();
                    editor.putInt("New_record01",a);
                    editor.putInt("New_record02",b);
                    editor.putInt("New_record03",c);
                    editor.commit();

                }
            }else if(msg.what==200){
                int x=msg.arg1;
                int y=msg.arg2;
                Log.i("tag111","地鼠的位置x："+x+"地鼠的位置y:"+y);
                scoreshow.setText(Score+"分");
                rat.setVisibility(View.VISIBLE);
                rat.setImageResource(R.drawable.rat);
                rat.setX(x);
                rat.setY(y);
                //成功击中地鼠后重置地鼠的位置，并进行加分
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rat__kick);
        btn=(Button)findViewById(R.id.Start_btn);
        scoreshow=(TextView)findViewById(R.id.Points);
        timeshow=(TextView)findViewById(R.id.Time_Show);
        rat=(ImageView)findViewById(R.id.Rat);
        //初始化控件


        DisplayMetrics metrics = getResources().getDisplayMetrics();
        this.width=metrics.widthPixels;
        this.height=metrics.heightPixels;
        Log.i("tag111","onCreate：屏幕的height=="+this.height+"width=="+this.width);
        //获取屏幕的宽度和高度

        btn.setOnClickListener(this);
        rat.setOnClickListener(this);
        rat.setClickable(false);
        //设置触发事件

        //创建Sp文件MyRank用以保存分数记录
        SharedPreferences share=getSharedPreferences("MyRank", Activity.MODE_PRIVATE);
        SharedPreferences.Editor editor=share.edit();
        editor.putInt("New_record01",0);
        editor.putInt("New_record02",0);
        editor.putInt("New_record03",0);
        editor.commit();


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main,menu);
       return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId()==R.id.Rank){
            Rank_Show();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View view){
        game_run(view);
    }

    private void game_run(View view) {
        switch (view.getId()){
            case R.id.Start_btn:  //开始游戏点击
                rat.setClickable(true);
                rat.setX(width/2);
                rat.setY(height/2); //设置地鼠的初始坐标
                btn.setVisibility(View.INVISIBLE);
                startCountDown(); //开始倒计时
                break;

            case R.id.Rat:
                rat.setImageResource(R.drawable.rat_hit);
//                rat.setVisibility(View.INVISIBLE);
                Score++;
                Message msg=new Message();
                msg.what=200;
                msg.arg1=(int)(Math.random()*(width-400)+200);
                msg.arg2=(int)(Math.random()*(height-400)+200);
                //随机生成地鼠下一次出现的位置

                    handler.sendMessageDelayed(msg,200); //发送消息通知地鼠的出现
                    break;

        }
    }


    private void Rank_Show() {
        Intent show=new Intent(this, Rank_Activity.class);
        startActivity(show);
    }

    public void startCountDown(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                for(int i=Time-1;i>=0;i--){
                    try{
                        Thread.sleep(1000);
                        //向子线程发送一条消息
                        Message msg=new Message();
                        msg.what=100;
                        msg.arg1=i;
                        handler.sendMessage(msg);
                    }catch (InterruptedException e){
                        e.printStackTrace();
                    }
                }
            }
        }).start();
    }
}
