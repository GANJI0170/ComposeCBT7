package com.cookandroid.cbt7;


import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;

public class KeywordActivity extends AppCompatActivity {
    private TextView test;
    private EditText keywordText;
    private Button button2;
    private ChipGroup chipGroup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_keyword);

        // Find views by their IDs
        test = findViewById(R.id.test);
        keywordText = findViewById(R.id.keywordText);
        button2 = findViewById(R.id.button2);
        chipGroup = findViewById(R.id.chipGroup);

        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String enteredText = keywordText.getText().toString();

                // Create a new Chip
                Chip chip = new Chip(KeywordActivity.this);
                chip.setText(enteredText);
                chip.setCloseIconVisible(true);
                chip.setClickable(true);

                // Add the Chip to the ChipGroup
                chipGroup.addView(chip);

                // Clear the EditText
                keywordText.setText("");
            }
        });

    }
}

