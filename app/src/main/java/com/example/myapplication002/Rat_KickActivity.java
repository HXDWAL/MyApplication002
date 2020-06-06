package com.example.myapplication002;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class Rat_KickActivity extends AppCompatActivity implements View.OnClickListener {
    private Button btn; //开始游戏，点击按纽后地鼠作为一个可点击对象出现在屏幕中央，并且会随着倒计时的结束而消失
    private TextView scoreshow; //击中地鼠后加分
    private TextView timeshow; //用handler写一个计时器方法，判断剩余时间；
    private ImageView rat; //用于触发事件，击中老鼠后老鼠消失，随机生成新的位置，并且分数增加；
    int height;//定义屏幕高度
    int Score=0;
    int Time=10; //初始化游戏的分数和时间变量
    Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg){
            super.handleMessage(msg);
            if(msg.what==100){
                int time=msg.arg1;
                if(Time>0){
                    timeshow.setText(time+"");
                }else{
                    btn.setVisibility(View.VISIBLE);
                    timeshow.setText(""+10);
                    rat.setClickable(false);
                    Toast.makeText(Rat_KickActivity.this, "游戏结束", Toast.LENGTH_SHORT).show();
                    //若时间已到，则还原按钮初始状态，并且设置地鼠为不可点击，游戏结束
                }
            }else if(msg.what==200){
                int x=msg.arg1;
                int y=msg.arg2;
                Log.i("tag111","地鼠的位置x："+x+"地鼠的位置y:"+y);
                scoreshow.setText(Score+"分");
                rat.setVisibility(View.VISIBLE);
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

        DisplayMetrics metrics=getResources().
    }
}
