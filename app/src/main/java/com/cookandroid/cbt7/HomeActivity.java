package com.cookandroid.cbt7;

import static java.security.AccessController.getContext;

import androidx.appcompat.app.AppCompatActivity;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.cookandroid.cbt7.database.CustomAdapter;
import com.cookandroid.cbt7.database.articlefoundList;
import com.cookandroid.cbt7.database.articlelostList;
import com.cookandroid.cbt7.database.chatList;
import com.cookandroid.cbt7.database.foundAdaptor;
import com.cookandroid.cbt7.database.lostAdaptor;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
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
    private Button btnSearch, btnwrite, btnmyarticle, btnchat, btnalarm, btnprofile;

//    private fragment_home fragmentHome = new fragment_home();
//    private fragment_myarticle fragmentMyarticle = new fragment_myarticle();
//    private fragment_write fragmentWrite = new fragment_write();
//    private Button BTNlost, BTNfound;
//    private LinearLayout l;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

//      탭호스트 설정
        TabHost tabHost1 = (TabHost) findViewById(R.id.tabHost1);
        tabHost1.setup() ;
        TabHost.TabSpec ts1 = tabHost1.newTabSpec("Tab Spec 1");
        TabHost.TabSpec ts2 = tabHost1.newTabSpec("Tab Spec 2");
        TabHost.TabSpec ts3 = tabHost1.newTabSpec("Tab Spec 3");
        ts1.setContent(R.id.mainboard);
        ts2.setContent(R.id.lostboard);
        ts3.setContent(R.id.foundboard);
        ts1.setIndicator("홈");
        ts2.setIndicator("분실물게시판");
        ts3.setIndicator("습득물게시판");
        tabHost1.addTab(ts1);
        tabHost1.addTab(ts2);
        tabHost1.addTab(ts3);

        boardDatabase();

        EditText editkw = (EditText) findViewById(R.id.editkw);


//      검색 버튼 클릭시 검색화면으로 전환
        btnSearch = (Button)findViewById(R.id.btnSearch) ;
        btnSearch.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Toast.makeText(HomeActivity.this, "검색창이동", Toast.LENGTH_SHORT).show();
                Intent in = new Intent(HomeActivity.this, SearchActivity.class);
                in.putExtra("searchText", editkw.getText().toString());
                startActivity(in);
            }
        });

        btnwrite = (Button) findViewById(R.id.btnwrite);
        btnwrite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent in = new Intent(getApplicationContext(), WriteActivity.class);
                startActivity(in);
            }
        });
        btnmyarticle = (Button) findViewById(R.id.btnmyarticle);
        btnmyarticle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent in = new Intent(getApplicationContext(), MyarticleActivity.class);
                startActivity(in);
            }
        });
        btnchat = (Button) findViewById(R.id.btnchat);
        btnchat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in = new Intent(getApplicationContext(), ChatListActivity.class);
                startActivity(in);
            }
        });
        btnalarm = (Button) findViewById(R.id.btnalarm);
        btnalarm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent in = new Intent(getApplicationContext(), AlarmActivity.class);
                startActivity(in);
            }
        });
        btnprofile = (Button) findViewById(R.id.btnprofile);
        btnprofile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent in = new Intent(getApplicationContext(), ProfileActivity.class);
                startActivity(in);
            }
        });




//        BTNfound = (Button) findViewById(R.id.BTNfound);
//        BTNlost = (Button) findViewById(R.id.BTNlost);
//
//        BTNfound.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                callFragment(1);
//
//            }
//        });
//        BTNlost.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                callFragment(2);
//
//            }
//        });

    }

    private void boardDatabase() {

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

//      습득물 게시판 데이터베이스 연동
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
    //채팅방 연동


//    private BottomNavigationView.OnNavigationItemSelectedListener listener = new BottomNavigationView.OnNavigationItemSelectedListener() {
//        @Override
//        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
//            l = (LinearLayout) findViewById(R.id.board);
//            switch (item.getItemId()) {
//                    case R.id.menuwriting:
//                        l.setVisibility(View.GONE);
//                        getSupportFragmentManager().beginTransaction().replace(R.id.framelayout1, fragmentWrite).commit();
//                        return true;
//                    case R.id.menuhome:
//                        l.setVisibility(View.GONE);
//                        getSupportFragmentManager().beginTransaction().replace(R.id.framelayout1, fragmentHome).commit();
//                        return true;
//                    case R.id.menulistofarticles:
//                        l.setVisibility(View.GONE);
//                        getSupportFragmentManager().beginTransaction().replace(R.id.framelayout1, fragmentMyarticle).commit();
//                        return true;
//            }
//            return false;
//        }
//    };
//
//    private void callFragment(int n) {
//        l = (LinearLayout) findViewById(R.id.board);
//        switch (n) {
//            case 1:
////                getSupportFragmentManager().beginTransaction().replace(R.id.framelayout1, fragmentboard).commit();
//                l.setVisibility(View.VISIBLE);
//                recyclerView1 = findViewById(R.id.boardrecyclerView);
//                DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(getApplicationContext(),new LinearLayoutManager(this).getOrientation());
//                recyclerView1.addItemDecoration(dividerItemDecoration);
//                recyclerView1.setHasFixedSize(true);
//                layoutManager1 = new LinearLayoutManager(this);
//                recyclerView1.setLayoutManager(layoutManager1);
//                arrayListlost = new ArrayList<>(); //articleList 객체를 어댑터 쪽으로 담을 arrayList
//                database = FirebaseDatabase.getInstance(); //파이어베이스 연동
//
//
//                databaseReference1 = database.getReference("lost_article"); // DB 테이블 연결
//                databaseReference1.addListenerForSingleValueEvent(new ValueEventListener() {
//                    @Override
//                    public void onDataChange(@NonNull DataSnapshot snapshot) {
//                        //파이어베이스 데이터를 받아오는 곳
//                        arrayListlost.clear(); //기존 배열 초기화
//                        for (DataSnapshot snapshot1 : snapshot.getChildren()) { //반복문으로 데이터 리스트 추출
//                            articlelostList articlelostList = snapshot1.getValue(articlelostList.class); //arrayList 객체에 데이터 담기
//                            arrayListlost.add(articlelostList);
//                        }
//                        adapter1.notifyDataSetChanged(); //리스트 저장 및 새로고침
//                    }
//
//                    @Override
//                    public void onCancelled(@NonNull DatabaseError error) {
//                        Log.e("LoginActivity", String.valueOf(error.toException()));
//                    }
//                });
//                adapter1 = new lostAdaptor(arrayListlost, this);
//                recyclerView1.setAdapter(adapter1);
//                break;
//            case 2:
////                getSupportFragmentManager().beginTransaction().replace(R.id.framelayout1, fragmentboard).commit();
//                l.setVisibility(View.VISIBLE);
//                recyclerView2 = findViewById(R.id.boardrecyclerView);
//                DividerItemDecoration dividerItemDecoration1 = new DividerItemDecoration(getApplicationContext(),new LinearLayoutManager(this).getOrientation());
//                recyclerView2.addItemDecoration(dividerItemDecoration1);
//                recyclerView2.setHasFixedSize(true);
//                layoutManager2 = new LinearLayoutManager(this);
//                recyclerView2.setLayoutManager(layoutManager2);
//                arrayListfound = new ArrayList<>();
//
//                databaseReference2 = database.getReference("found_article");
//                databaseReference2.addListenerForSingleValueEvent(new ValueEventListener() {
//                    @Override
//                    public void onDataChange(@NonNull DataSnapshot snapshot) {
//                        arrayListfound.clear();
//                        for (DataSnapshot snapshot1 : snapshot.getChildren()) { //반복문으로 데이터 리스트 추출
//                            articlefoundList articlefoundList = snapshot1.getValue(articlefoundList.class); //arrayList 객체에 데이터 담기
//                            arrayListfound.add(articlefoundList);
//                        }
//                        adapter2.notifyDataSetChanged(); //리스트 저장 및 새로고침
//                    }
//
//                    @Override
//                    public void onCancelled(@NonNull DatabaseError error) {
//                        Log.e("LoginActivity", String.valueOf(error.toException()));
//                    }
//                });
//                adapter2 = new foundAdaptor(arrayListfound, this);
//                recyclerView2.setAdapter(adapter2);
//                break;
//        }
//    }
}

