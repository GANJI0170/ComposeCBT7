package com.cookandroid.cbt7;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.cookandroid.cbt7.database.CustomAdapter;
import com.cookandroid.cbt7.database.chatList;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ChatListActivity extends AppCompatActivity {
    private TextView textView1;
    private RecyclerView recyclerView3;
    private RecyclerView.LayoutManager layoutManager3;
    private RecyclerView.Adapter adapter3;
    private ArrayList<chatList> arrayListchat;
    private FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    private DatabaseReference databaseReference3 = firebaseDatabase.getReference();
    private FirebaseDatabase database;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chatlist);
        recyclerView3 = (RecyclerView) findViewById(R.id.recyclerView3);
        chatDatabase();
    }
    private void chatDatabase(){
        recyclerView3 = findViewById(R.id.recyclerView3);
        layoutManager3 = new LinearLayoutManager(this);
        recyclerView3.setLayoutManager(layoutManager3);
        arrayListchat = new ArrayList<>(); //chatList 객체를 어댑터 쪽으로 담을 arrayList
        database = FirebaseDatabase.getInstance(); //파이어 베이스 연동


        databaseReference3 = database.getReference("chat_rooms"); // DB 테이블 연결
        databaseReference3.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                //파이어베이스 데이터를 받아오는 곳
                arrayListchat.clear(); //기존 배열 초기화
                for (DataSnapshot snapshot1 : snapshot.getChildren()) { //반복문으로 데이터 리스트 추출
                    chatList chatList = snapshot1.getValue(chatList.class); //arrayList 객체에 데이터 담기
                    arrayListchat.add(chatList);
                }
                adapter3.notifyDataSetChanged(); //리스트 저장 및 새로고침
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.e("ChatListActivity", String.valueOf(error.toException()));
            }
        });
        adapter3 = new CustomAdapter(arrayListchat, this);
        recyclerView3.setAdapter(adapter3); // 리사이클러뷰에 어댑터 연결

    }

}