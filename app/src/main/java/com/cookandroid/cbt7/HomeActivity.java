package com.cookandroid.cbt7;

import androidx.appcompat.app.AppCompatActivity;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TabHost;
import android.widget.Toast;

import java.util.ArrayList;

public class HomeActivity extends AppCompatActivity {

    private RecyclerView recyclerView1, recyclerView2;
    private RecyclerView.Adapter adapter1, adapter2;
    private RecyclerView.LayoutManager layoutManager1, layoutManager2;
    private ArrayList<articlefoundList> arrayListfound;
    private ArrayList<articlelostList> arrayListlost;
    private FirebaseDatabase database;
    private DatabaseReference databaseReference1, databaseReference2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

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
                Toast.makeText(HomeActivity.this, "검색창이동", Toast.LENGTH_SHORT).show();
                Intent in = new Intent(HomeActivity.this, SearchActivity.class);
                startActivity(in);
            }
        });
        EditText editkw2 = (EditText)findViewById(R.id.editkw2) ;
        editkw2.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Toast.makeText(HomeActivity.this, "검색창이동", Toast.LENGTH_SHORT).show();
                Intent in = new Intent(HomeActivity.this, SearchActivity.class);
                startActivity(in);
            }
        });
        EditText editkw3 = (EditText)findViewById(R.id.editkw3) ;
        editkw3.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Toast.makeText(HomeActivity.this, "검색창이동", Toast.LENGTH_SHORT).show();
                Intent in = new Intent(HomeActivity.this, SearchActivity.class);
                startActivity(in);
            }
        });

        recyclerView1 = findViewById(R.id.recyclerView1);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(getApplicationContext(),new LinearLayoutManager(this).getOrientation());
        recyclerView1.addItemDecoration(dividerItemDecoration);
        recyclerView1.setHasFixedSize(true);
        layoutManager1 = new LinearLayoutManager(this);
        recyclerView1.setLayoutManager(layoutManager1);
        arrayListlost = new ArrayList<>(); //articleList 객체를 어댑터 쪽으로 담을 arrayList

        database = FirebaseDatabase.getInstance(); //파이어베이스 연동

        databaseReference1 = database.getReference("lost_article"); // DB 테이블 연결
        databaseReference1.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                //파이어베이스 데이터를 받아오는 곳
                arrayListlost.clear(); //기존 배열 초기화
                for (DataSnapshot snapshot1 : snapshot.getChildren()) { //반복문으로 데이터 리스트 추출
                    articlelostList articlelostList = snapshot1.getValue(articlelostList.class); //arrayList 객체에 데이터 담기
                    arrayListlost.add(articlelostList);
                }
                adapter1.notifyDataSetChanged(); //리스트 저장 및 새로고침
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.e("LoginActivity", String.valueOf(error.toException()));
            }
        });

        recyclerView2 = findViewById(R.id.recyclerView2);
        recyclerView2.addItemDecoration(dividerItemDecoration);
        recyclerView2.setHasFixedSize(true);
        layoutManager2 = new LinearLayoutManager(this);
        recyclerView2.setLayoutManager(layoutManager2);
        arrayListfound = new ArrayList<>();

        databaseReference2 = database.getReference("found_article");
        databaseReference2.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                arrayListfound.clear();
                for (DataSnapshot snapshot1 : snapshot.getChildren()) { //반복문으로 데이터 리스트 추출
                    articlefoundList articlefoundList = snapshot1.getValue(articlefoundList.class); //arrayList 객체에 데이터 담기
                    arrayListfound.add(articlefoundList);
                }
                adapter2.notifyDataSetChanged(); //리스트 저장 및 새로고침
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.e("LoginActivity", String.valueOf(error.toException()));
            }
        });

        adapter1 = new lostAdaptor(arrayListlost, this);
        adapter2 = new foundAdaptor(arrayListfound, this);
        recyclerView1.setAdapter(adapter1);
        recyclerView2.setAdapter(adapter2);

    }
}

