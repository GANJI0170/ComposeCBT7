package com.cookandroid.cbt7;

import android.annotation.SuppressLint;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.RemoteMessage;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AlarmActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private NotificationAdapter adapter;
    private List<NotificationItem> notificationList;
    private ImageButton setButton;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alarm);

        TextView test = findViewById(R.id.test);
        test.setText("알림 페이지입니다.");

        recyclerView = findViewById(R.id.notification_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        notificationList = new ArrayList<>();
        adapter = new NotificationAdapter(notificationList);
        recyclerView.setAdapter(adapter);
        setButton = (ImageButton)findViewById(R.id.setButton);
        setButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent in = new Intent(getApplicationContext(), AlarmsettingActivity.class);
                startActivity(in);
            }
        });
    }

    // 키워드가 등록된 사용자들에게 알림을 보내는 메서드
    public void sendNotificationToUsers(List<String> users, String keyword) {
        for (String userToken : users) {
            // 알림을 보내는 메서드 호출
            sendNotification(userToken, keyword);
        }
    }

    // 알림을 보내는 메서드

    public void sendNotification(String userToken, String keyword) {
        // FCM 서비스 초기화
        FirebaseMessaging fm = FirebaseMessaging.getInstance();

        // 알림 내용 설정
        String title = "새로운 습득물 등록";
        String message = "키워드 " + keyword + "를 포함한 습득물이 등록되었습니다.";

        // 알림 데이터 생성
        Map<String, String> data = new HashMap<>();
        data.put("title", title);
        data.put("message", message);

        // 알림 메시지 설정
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this)
                .setContentTitle(title)
                .setContentText(message)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT);

        // 알림 채널 설정 (안드로이드 8.0 이상에서 필요)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            String channelId = "channel_id";
            String channelName = "Channel Name";
            NotificationChannel channel = new NotificationChannel(channelId, channelName, NotificationManager.IMPORTANCE_DEFAULT);
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
            builder.setChannelId(channelId);
        }

        // 알림 전송
        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(this);
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        notificationManager.notify(0, builder.build());

        // 알림 목록에 추가
        notificationList.add(new NotificationItem(title, message));
        adapter.notifyDataSetChanged();
    }

    // 알림 아이템을 표시할 ViewHolder 클래스
    private static class NotificationViewHolder extends RecyclerView.ViewHolder {
        private TextView titleTextView;
        private TextView messageTextView;

        public NotificationViewHolder(@NonNull View itemView) {
            super(itemView);
            titleTextView = itemView.findViewById(R.id.notification_title_textview);
            messageTextView = itemView.findViewById(R.id.notification_message_textview);
        }

        public void bind(NotificationItem item) {
            titleTextView.setText(item.getTitle());
            messageTextView.setText(item.getMessage());
        }
    }

    // 알림 목록을 관리할 Adapter 클래스
    private class NotificationAdapter extends RecyclerView.Adapter<NotificationViewHolder> {
        private List<NotificationItem> notificationList;

        public NotificationAdapter(List<NotificationItem> notificationList) {
            this.notificationList = notificationList;
        }

        @NonNull
        @Override
        public NotificationViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_message, parent, false);
            return new NotificationViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull NotificationViewHolder holder, int position) {
            NotificationItem item = notificationList.get(position);
            holder.bind(item);
        }

        @Override
        public int getItemCount() {
            return notificationList.size();
        }
    }

    // 알림 아이템 클래스
    private static class NotificationItem {
        private String title;
        private String message;

        public NotificationItem(String title, String message) {
            this.title = title;
            this.message = message;
        }

        public String getTitle() {
            return title;
        }

        public String getMessage() {
            return message;
        }
    }
}