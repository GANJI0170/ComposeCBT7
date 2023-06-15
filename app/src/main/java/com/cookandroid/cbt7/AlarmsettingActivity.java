package com.cookandroid.cbt7;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Switch;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class AlarmsettingActivity extends AppCompatActivity {
    private TextView test;
    private Switch switch1;
    private Switch switch2;
    private Switch switch3;
    private TextView textView2;
    private ImageButton imageButton2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alarmsetting);

        // Find views by their IDs
        test = findViewById(R.id.test);
        switch1 = findViewById(R.id.switch1);
        switch2 = findViewById(R.id.switch2);
        switch3 = findViewById(R.id.switch3);
        textView2 = findViewById(R.id.textView2);
        imageButton2 = findViewById(R.id.imageButton2);


        imageButton2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in = new Intent(getApplicationContext(), KeywordActivity.class);
                startActivity(in);
            }
        });

    }
}
