package com.cookandroid.cbt7;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.graphics.Color;
import android.os.Build;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;

import com.cookandroid.cbt7.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

public class MyFirebaseMessagingService extends FirebaseMessagingService {

    private static final String TAG = "MyFirebaseMessaging";
    private static final String CHANNEL_ID = "MyNotificationChannel";

    private DatabaseReference keywordReference;

    @Override
    public void onCreate() {
        super.onCreate();

        // Firebase Realtime Database의 "keywords" 경로를 가져옵니다.
        keywordReference = FirebaseDatabase.getInstance().getReference("keywords");
    }

    @Override
    public void onNewToken(@NonNull String token) {

        super.onNewToken(token);
        Log.d(TAG, "onNewToken: $token");
        // 토큰을 서버로 전송하는 로직을 구현합니다.
        // 토큰을 서버에 등록하여 알림을 전송할 수 있도록 합니다.
        sendTokenToServer(token);
    }

    private void sendTokenToServer(String token) {
        // 서버에 토큰을 전송하는 로직을 작성합니다.
        // 이 부분을 서버와의 통신 방식에 따라 구현해야 합니다.
        // 예를 들어, Retrofit 또는 Volley를 사용하여 토큰을 서버로 전송할 수 있습니다.
        // 토큰을 서버에 전송하여 등록하고, 알림을 전송할 수 있도록 합니다.

        // 예시: Firebase Realtime Database를 사용하여 토큰을 저장하는 방법
        DatabaseReference tokenReference = FirebaseDatabase.getInstance().getReference("token");

        // 토큰을 "tokens" 경로에 저장합니다.
        tokenReference.child(token).setValue(true)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Log.d(TAG, "Token sent to server successfully");
                        } else {
                            Log.e(TAG, "Failed to send token to server", task.getException());
                        }
                    }
                });
    }
    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        // 푸시 알림을 수신했을 때 실행되는 메서드
        // 이곳에서 푸시 알림을 처리하고 사용자에게 알림을 표시할 수 있습니다.

        // 알림 표시를 위한 코드
        String message = remoteMessage.getNotification().getBody();
        // 푸시 알림 내용 가져오기

        // 알림을 표시할 방식을 구현합니다.
        // 이 예시에서는 단순히 알림을 표시하는 로직을 작성합니다.
        showNotification(message);
    }

    private void showNotification(String message) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            // Android 8.0 (Oreo) 이상 버전에서는 알림 채널을 생성해야 합니다.
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID,
                    "My Channel", NotificationManager.IMPORTANCE_DEFAULT);
            channel.setDescription("My Notification Channel");
            channel.enableLights(true);
            channel.setLightColor(Color.RED);
            channel.enableVibration(true);

            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }

        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, CHANNEL_ID)
                .setContentTitle("새로운 게시물")
                .setContentText(message)
                .setAutoCancel(true);

        NotificationManager notificationManager = getSystemService(NotificationManager.class);
        notificationManager.notify(0, builder.build());
    }


}
