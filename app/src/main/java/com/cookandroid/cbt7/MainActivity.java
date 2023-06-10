package com.cookandroid.cbt7;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
       Button rgBtn = (Button)findViewById(R.id.rgBtn);
       Button lgBtn = (Button)findViewById(R.id.lgBtn);
       Button lostidbtn = (Button)findViewById(R.id.lostidbtn);
       Button lostpsbtn = (Button)findViewById(R.id.lostpsbtn);

       rgBtn.setOnClickListener(new View.OnClickListener(){
           @Override
           public void onClick(View view){
               Toast.makeText(MainActivity.this, "로그인", Toast.LENGTH_SHORT).show();
               Intent in = new Intent(MainActivity.this, HomeActivity.class);
               startActivity(in);
           }
       });
        lgBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Toast.makeText(MainActivity.this, "회원가입", Toast.LENGTH_SHORT).show();
                Toast.makeText(MainActivity.this, "ㅊㅊ", Toast.LENGTH_SHORT).show();
                Intent in = new Intent(MainActivity.this, HomeActivity.class);
                startActivity(in);
                finish();
            }
        });
        lostidbtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Toast.makeText(MainActivity.this, "아이디찾기", Toast.LENGTH_SHORT).show();
            }
        });
        lostpsbtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Toast.makeText(MainActivity.this, "비밀번호찾기", Toast.LENGTH_SHORT).show();
            }
        });
    }
    // Declare the launcher at the top of your Activity/Fragment:
    private final ActivityResultLauncher<String> requestPermissionLauncher =
            registerForActivityResult(new ActivityResultContracts.RequestPermission(), isGranted -> {
                if (isGranted) {
                    // FCM SDK (and your app) can post notifications.
                } else {
                    // TODO: Inform user that that your app will not show notifications.
                }
            });


}