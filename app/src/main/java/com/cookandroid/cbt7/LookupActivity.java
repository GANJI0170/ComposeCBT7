package com.cookandroid.cbt7;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.cookandroid.cbt7.database.ChatRoom;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Text;

public class LookupActivity extends AppCompatActivity {
    private DatabaseReference databaseReference;
    private TextView textdate, textplace, textthing, textfeature, textdetail;
    private TextView boardType, titleText, keywordText, dateText, placeText, thingText, featureText, detailText;
    private ImageView articleImage;
    private String board, articleNum, num;
    private Button report, chat;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lookup);

        Intent intent = getIntent();
        board = intent.getStringExtra("boardType");
        articleNum = intent.getStringExtra("articleNum");

        boardType = (TextView) findViewById(R.id.board_type);
        boardType.setText(board);

        textdate = (TextView) findViewById(R.id.textdate);
        textplace = (TextView) findViewById(R.id.textplace);
        textthing = (TextView) findViewById(R.id.textthing);
        textfeature = (TextView) findViewById(R.id.textfeature);
        textdetail = (TextView) findViewById(R.id.textdetail);

        titleText = (TextView) findViewById(R.id.titleText);
        keywordText = (TextView) findViewById(R.id.keywordText);
        dateText = (TextView) findViewById(R.id.dateText);
        placeText = (TextView) findViewById(R.id.placeText);
        thingText = (TextView) findViewById(R.id.thingText);
        featureText = (TextView) findViewById(R.id.featureText);
        detailText = (TextView) findViewById(R.id.detailText);
        articleImage = (ImageView) findViewById(R.id.articleImage);
        report = (Button) findViewById(R.id.report);
        chat = (Button) findViewById(R.id.chat);

        ImageButton backbtn = (ImageButton) findViewById(R.id.backbtn);
        backbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();

            }
        });

        chat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createChatRoom();

            }
        });

        switch (board) {
            case "분실물 게시판":
                databaseReference = FirebaseDatabase.getInstance().getReference("lost_article");
                databaseReference.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                            num = dataSnapshot.child("lost_number").getValue(String.class);
                            if(num.contains(articleNum)) {
                                titleText.setText(dataSnapshot.child("lost_title").getValue(String.class));
                                keywordText.setText(dataSnapshot.child("lost_keyword").getValue(String.class));
                                dateText.setText(dataSnapshot.child("lost_date").getValue(String.class));
                                placeText.setText(dataSnapshot.child("lost_place").getValue(String.class));
                                thingText.setText(dataSnapshot.child("lost_thing").getValue(String.class));
                                featureText.setText(dataSnapshot.child("lost_content").getValue(String.class));
                                detailText.setText(dataSnapshot.child("lost_data").getValue(String.class));
                                Glide.with(getApplicationContext()).load(dataSnapshot.child("lost_image").getValue(String.class)).into(articleImage);

                                break;
                            }
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        Log.e("LookupActivity", String.valueOf(error.toException()));
                    }
                });
                break;
            case "습득물 게시판":
                textdate.setText("습득 날짜 : ");
                textplace.setText("습득 장소 : ");
                textthing.setText("습득 물건 : ");
                textfeature.setText("습득물 특징 : ");
                textdetail.setText("습득물 상세내용 : ");
                databaseReference = FirebaseDatabase.getInstance().getReference("found_article");
                databaseReference.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                            num = dataSnapshot.child("found_number").getValue(String.class);
                            if(num.contains(articleNum)) {
                                titleText.setText(dataSnapshot.child("found_title").getValue(String.class));
                                keywordText.setText(dataSnapshot.child("found_keyword").getValue(String.class));
                                dateText.setText(dataSnapshot.child("found_date").getValue(String.class));
                                placeText.setText(dataSnapshot.child("found_place").getValue(String.class));
                                thingText.setText(dataSnapshot.child("found_thing").getValue(String.class));
                                featureText.setText(dataSnapshot.child("found_content").getValue(String.class));
                                detailText.setText(dataSnapshot.child("found_data").getValue(String.class));
                                Glide.with(getApplicationContext()).load(dataSnapshot.child("found_image")
                                        .getValue(String.class)).into(articleImage);
                                break;
                            }
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        Log.e("LookupActivity", String.valueOf(error.toException()));
                    }
                });
                break;
        }



    }
    private void createChatRoom() {
        // 채팅방 생성 및 파이어베이스에 저장하는 로직 작성
        // 파이어베이스 Realtime Database 또는 Firestore를 사용할 수 있습니다.
        // 아래 예시 코드에서는 Realtime Database를 사용합니다.

        // 채팅방 정보 생성
        ChatRoom chatRoom = new ChatRoom();
        chatRoom.setName("Chat Room 1");
        chatRoom.setMember("seok1234");
        chatRoom.setMessage("hello");
        chatRoom.setDate("2018.05.28");

        // 파이어베이스 데이터베이스 인스턴스 가져오기
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference reference = database.getReference("chat_rooms"); // 채팅방 데이터가 저장될 노드 경로

        // 채팅방 데이터를 파이어베이스에 저장
        String chatRoomId = reference.push().getKey(); // 채팅방에 대한 고유한 키 생성
        reference.child(chatRoomId).setValue(chatRoom)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        // 채팅방 생성 및 저장 성공
                        Toast.makeText(LookupActivity.this, "Chat room created successfully", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(LookupActivity.this, ChatActivity.class);
                        intent.putExtra("chatRoomId", chatRoomId);
                        startActivity(intent);
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        // 채팅방 생성 및 저장 실패
                        Toast.makeText(LookupActivity.this, "Failed to create chat room", Toast.LENGTH_SHORT).show();
                        Log.e("Firebase", "Failed to create chat room", e);
                    }
                });
    }
}
