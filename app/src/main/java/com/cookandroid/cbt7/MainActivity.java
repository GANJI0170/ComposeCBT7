package com.cookandroid.cbt7;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
       Button rgBtn = (Button)findViewById(R.id.rgBtn);
       Button lgBtn = (Button)findViewById(R.id.lgBtn);
       Button lostidbtn = (Button)findViewById(R.id.lostidbtn);
       Button lostpsbtn = (Button)findViewById(R.id.lostpsbtn);

       rgBtn.setOnClickListener(new View.OnClickListener(){
           @Override
           public void onClick(View view){
               Toast.makeText(MainActivity.this, "로그인", Toast.LENGTH_SHORT).show();
               Intent in = new Intent(MainActivity.this, LoginActivity.class);
               startActivity(in);
           }
       });
        lgBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Toast.makeText(MainActivity.this, "회원가입", Toast.LENGTH_SHORT).show();
            }
        });
        lostidbtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Toast.makeText(MainActivity.this, "아이디찾기", Toast.LENGTH_SHORT).show();
            }
        });
        lostpsbtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Toast.makeText(MainActivity.this, "비밀번호찾기", Toast.LENGTH_SHORT).show();
            }
        });
    }
}