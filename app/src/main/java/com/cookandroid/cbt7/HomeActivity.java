package com.cookandroid.cbt7;

import androidx.appcompat.app.AppCompatActivity;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.cookandroid.cbt7.database.articlefoundList;
import com.cookandroid.cbt7.database.articlelostList;
import com.cookandroid.cbt7.database.foundAdaptor;
import com.cookandroid.cbt7.database.lostAdaptor;
import com.cookandroid.cbt7.navigation.fragment_home;
import com.cookandroid.cbt7.navigation.fragment_myarticle;
import com.cookandroid.cbt7.navigation.fragment_write;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import java.util.ArrayList;

public class HomeActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    private ArrayList<articlefoundList> arrayListfound;
    private ArrayList<articlelostList> arrayListlost;
    private FirebaseDatabase database;
    private DatabaseReference databaseReference;
    private fragment_home fragmentHome = new fragment_home();
    private fragment_myarticle fragmentMyarticle = new fragment_myarticle();
    private fragment_write fragmentWrite = new fragment_write();
    private Button BTNlost, BTNfound;
    private LinearLayout l;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

//      bottomNavigationView 설정
//        FrameLayout frameLayout = (FrameLayout) findViewById(R.id.framelayout1);
        BottomNavigationView bottomNavigationView = (BottomNavigationView) findViewById(R.id.menu_bottom_navigation);
        bottomNavigationView.setSelectedItemId(R.id.menuhome);
        bottomNavigationView.setOnNavigationItemSelectedListener(listener);
        getSupportFragmentManager().beginTransaction().replace(R.id.framelayout1, fragmentHome).commit();

        l = (LinearLayout) findViewById(R.id.board);
        l.setVisibility(View.GONE);

        BTNfound = (Button) findViewById(R.id.BTNfound);
        BTNlost = (Button) findViewById(R.id.BTNlost);

        BTNfound.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boardDatabase(1);
            }
        });
        BTNlost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boardDatabase(2);
            }
        });
    }
    private BottomNavigationView.OnNavigationItemSelectedListener listener = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            l = (LinearLayout) findViewById(R.id.board);
            l.setVisibility(View.GONE);
            switch (item.getItemId()) {
                    case R.id.menuwriting:
                        getSupportFragmentManager().beginTransaction().replace(R.id.framelayout1, fragmentWrite).commit();
                        return true;
                    case R.id.menuhome:
                        getSupportFragmentManager().beginTransaction().replace(R.id.framelayout1, fragmentHome).commit();
                        return true;
                    case R.id.menulistofarticles:
                        getSupportFragmentManager().beginTransaction().replace(R.id.framelayout1, fragmentMyarticle).commit();
                        return true;
            }
            return false;
        }
    };

    private void boardDatabase(int n) {
        l = (LinearLayout) findViewById(R.id.board);
        l.setVisibility(View.VISIBLE);
        recyclerView = findViewById(R.id.boardrecyclerV);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(getApplicationContext(),new LinearLayoutManager(this).getOrientation());
        recyclerView.addItemDecoration(dividerItemDecoration);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        arrayListlost = new ArrayList<>(); //articleList 객체를 어댑터 쪽으로 담을 arrayList
        arrayListfound = new ArrayList<>();
        database = FirebaseDatabase.getInstance(); //파이어베이스 연동
        switch (n) {
            case 1:
                databaseReference = database.getReference("found_article");
                databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        arrayListfound.clear();
                        for (DataSnapshot snapshot1 : snapshot.getChildren()) { //반복문으로 데이터 리스트 추출
                            articlefoundList articlefoundList = snapshot1.getValue(articlefoundList.class); //arrayList 객체에 데이터 담기
                            arrayListfound.add(articlefoundList);
                        }
                        adapter.notifyDataSetChanged(); //리스트 저장 및 새로고침
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        Log.e("LoginActivity", String.valueOf(error.toException()));
                    }
                });
                adapter = new foundAdaptor(arrayListfound, this);
                recyclerView.setAdapter(adapter);
                break;
            case 2:
                databaseReference = database.getReference("lost_article"); // DB 테이블 연결
                databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        arrayListlost.clear();
                        for (DataSnapshot snapshot1 : snapshot.getChildren()) { //반복문으로 데이터 리스트 추출
                            articlelostList articlelostList = snapshot1.getValue(articlelostList.class); //arrayList 객체에 데이터 담기
                            arrayListlost.add(articlelostList);
                        }
                        adapter.notifyDataSetChanged(); //리스트 저장 및 새로고침
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        Log.e("LoginActivity", String.valueOf(error.toException()));
                    }
                });
                adapter = new lostAdaptor(arrayListlost, this);
                recyclerView.setAdapter(adapter);
                break;
        }
    }
}

