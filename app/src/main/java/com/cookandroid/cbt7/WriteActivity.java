package com.cookandroid.cbt7;

import android.os.Bundle;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class WriteActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_write);

        TextView test = (TextView) findViewById(R.id.test);
        test.setText("글 작성 페이지입니다.");
    }
}
