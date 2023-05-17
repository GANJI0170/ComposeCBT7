package com.cookandroid.cbt7;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TabHost;
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        TabHost tabHost1 = (TabHost) findViewById(R.id.tabHost1);
        tabHost1.setup() ;
        TabHost.TabSpec ts1 = tabHost1.newTabSpec("Tab Spec 1");
        TabHost.TabSpec ts2 = tabHost1.newTabSpec("Tab Spec 2");
        TabHost.TabSpec ts3 = tabHost1.newTabSpec("Tab Spec 3");
        ts1.setContent(R.id.mainboard);
        ts2.setContent(R.id.lostboard);
        ts3.setContent(R.id.foundboard);
        ts1.setIndicator("메인게시판");
        ts2.setIndicator("분실물게시판");
        ts3.setIndicator("습득물게시판");
        tabHost1.addTab(ts1);
        tabHost1.addTab(ts2);
        tabHost1.addTab(ts3);

        EditText editkw1 = (EditText)findViewById(R.id.editkw1) ;
        editkw1.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Toast.makeText(LoginActivity.this, "검색창이동", Toast.LENGTH_SHORT).show();
                Intent in = new Intent(LoginActivity.this, SearchActivity.class);
                startActivity(in);
            }
        });
        EditText editkw2 = (EditText)findViewById(R.id.editkw2) ;
        editkw2.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Toast.makeText(LoginActivity.this, "검색창이동", Toast.LENGTH_SHORT).show();
                Intent in = new Intent(LoginActivity.this, SearchActivity.class);
                startActivity(in);
            }
        });
        EditText editkw3 = (EditText)findViewById(R.id.editkw3) ;
        editkw3.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Toast.makeText(LoginActivity.this, "검색창이동", Toast.LENGTH_SHORT).show();
                Intent in = new Intent(LoginActivity.this, SearchActivity.class);
                startActivity(in);
            }
        });


        //상단 탭호스트 2개 버전
//        EditText editkw1 = (EditText) findViewById(R.id.editkw1);
//        editkw1.setOnClickListener(new View.OnClickListener(){
//            @Override
//            public void onClick(View view){
//                Toast.makeText(LoginActivity.this, "검색창이동", Toast.LENGTH_SHORT).show();
//                Intent in = new Intent(LoginActivity.this, SearchActivity.class);
//                startActivity(in);
//            }
//        });
//
//        EditText editkw2 = (EditText) findViewById(R.id.editkw2);
//        editkw2.setOnClickListener(new View.OnClickListener(){
//            @Override
//            public void onClick(View view){
//                Toast.makeText(LoginActivity.this, "검색창이동", Toast.LENGTH_SHORT).show();
//                Intent in = new Intent(LoginActivity.this, SearchActivity.class);
//                startActivity(in);
//            }
//        });
    }
}
