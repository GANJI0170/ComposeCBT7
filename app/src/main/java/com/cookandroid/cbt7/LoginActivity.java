package com.cookandroid.cbt7;

import androidx.appcompat.app.AppCompatActivity;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TabHost;
import android.widget.Toast;

import java.util.ArrayList;

public class LoginActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    private ArrayList<articleList> arrayList;
    private FirebaseDatabase database;
    private DatabaseReference databaseReference;

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

        recyclerView = findViewById(R.id.recyclerView1);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(getApplicationContext(),new LinearLayoutManager(this).getOrientation());
        recyclerView.addItemDecoration(dividerItemDecoration);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        arrayList = new ArrayList<>(); //articleList 객체를 어댑터 쪽으로 담을 arrayList

        database = FirebaseDatabase.getInstance(); //파이어베이스 연동

        databaseReference = database.getReference("found_article"); // DB 테이블 연결
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                //파이어베이스 데이터를 받아오는 곳
                arrayList.clear(); //기존 배열 초기화
                for (DataSnapshot snapshot1 : snapshot.getChildren()) { //반복문으로 데이터 리스트 추출
                    articleList articleList = snapshot1.getValue(com.cookandroid.cbt7.articleList.class); //arrayList 객체에 데이터 담기
                    arrayList.add(articleList);
                }
                adapter.notifyDataSetChanged(); //리스트 저장 및 새로고침
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.e("LoginActivity", String.valueOf(error.toException()));
            }
        });

        adapter = new CustomAdaptor(arrayList, this);
        recyclerView.setAdapter(adapter);

    }
}

