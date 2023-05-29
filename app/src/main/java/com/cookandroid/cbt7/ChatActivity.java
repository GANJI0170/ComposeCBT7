package com.cookandroid.cbt7;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.telecom.Call;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.textclassifier.ConversationActions;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.Request;
import com.bumptech.glide.request.RequestOptions;
//import com.cookandroid.cbt7.database.MessageAdapter;
import com.google.android.gms.common.api.Response;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ServerValue;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.database.annotations.NotNull;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

import javax.security.auth.callback.Callback;

public class ChatActivity extends AppCompatActivity {
    private String chatRoomId;
    private RecyclerView messageRecyclerView;
    private EditText messageEditText;
    private Button sendButton;
    private DatabaseReference messageRef;
    private List<Message> messages;
//    private MessageAdapter messageAdapter;


    // 메시지 목록 초기화
    @Override
    public void onBackPressed() {
        Intent intent = new Intent(ChatActivity.this, ChatListActivity.class);

        startActivity(intent);
    }
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_chat);
//
//        // 파이어베이스 데이터베이스 인스턴스 가져오기
//        FirebaseDatabase database = FirebaseDatabase.getInstance();
//        messageRef = database.getReference("chat_rooms").child(chatRoomId).child("messages");
//
//        // 메시지 목록 초기화
//        messages = new ArrayList<>();
//
//        chatRoomId = getIntent().getStringExtra("chatRoomId");
//        // RecyclerView 설정
//        messageRecyclerView = findViewById(R.id.message_recycler_view);
//        messageRecyclerView.setLayoutManager(new LinearLayoutManager(this));
//        messageAdapter = new MessageAdapter(messages);
//        messageRecyclerView.setAdapter(messageAdapter);
//
//        // 메시지 입력 EditText와 전송 버튼 찾기
//        messageEditText = findViewById(R.id.message_edit_text);
//        sendButton = findViewById(R.id.send_button);
//
//        // 전송 버튼에 클릭 리스너 설정
//        sendButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                sendMessage();
//            }
//        });
//        // 파이어베이스에서 메시지를 불러오기 위한 리스너 등록
//        messageRef.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                messages.clear();
//
//                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
//                    Message message = dataSnapshot.getValue(Message.class);
//                    messages.add(message);
//                }
//
//                messageAdapter.notifyDataSetChanged();
//                messageRecyclerView.scrollToPosition(messages.size() - 1);
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//                // 불러오기 실패
//                Toast.makeText(ChatActivity.this, "Failed to load messages", Toast.LENGTH_SHORT).show();
//                Log.e("Firebase", "Failed to load messages", error.toException());
//            }
//        });
//    }
//
//    private void sendMessage() {
//        // 메시지 내용 가져오기
//        String messageContent = messageEditText.getText().toString().trim();
//
//        if (TextUtils.isEmpty(messageContent)) {
//            Toast.makeText(this, "Please enter a message", Toast.LENGTH_SHORT).show();
//            return;
//        }
//
//        // 메시지 데이터 생성
//        Message message = new Message();
//        message.setContent(messageContent);
//        message.setTimestamp(System.currentTimeMillis());
//
//        // 메시지 데이터를 파이어베이스에 저장
//        String messageId = messageRef.push().getKey();
//        messageRef.child(messageId).setValue(message)
//                .addOnSuccessListener(new OnSuccessListener<Void>() {
//                    @Override
//                    public void onSuccess(Void aVoid) {
//                        // 메시지 저장 성공
//                        messageEditText.setText("");
//                    }
//                })
//                .addOnFailureListener(new OnFailureListener() {
//                    @Override
//                    public void onFailure(@NonNull Exception e) {
//                        // 메시지 저장 실패
//                        Toast.makeText(ChatActivity.this, "Failed to send message", Toast.LENGTH_SHORT).show();
//                        Log.e("Firebase", "Failed to send message", e);
//                    }
//                });
//    }
}
