package com.example.myapplication002;

import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.TextView;
import android.widget.EditText;
import android.view.View;
import java.math.*;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TextView t=(TextView)findViewById(R.id.textView);
        t.setText("");

        EditText e=(EditText)findViewById(R.id.editText);
        String str1=e.getText().toString();

        EditText e2=(EditText)findViewById(R.id.editText02);
        String str2=e2.getText().toString();

        Button btn=(Button)findViewById(R.id.button);
        btn.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        TextView t=(TextView)findViewById(R.id.textView);

        EditText e=(EditText)findViewById(R.id.editText);
        String str1=e.getText().toString();

        EditText e2=(EditText)findViewById(R.id.editText02);
        String str2=e2.getText().toString();

        if(str2.equals("华氏")){
           long a=Long.parseLong(str1);
            double b=1.8*a+32;
            t.setText(a+"°C"+"转化为华氏温度是"+b+"°F");
        }else{
            long a=Long.parseLong(str1);
            double b=(a-32)/1.8;
            long c=Math.round(b);
            t.setText(a+"°F"+"转化为摄氏温度是"+c+"°C");

        }

    }
}
