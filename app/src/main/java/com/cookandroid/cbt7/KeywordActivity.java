package com.cookandroid.cbt7;


import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

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

                if (!enteredText.isEmpty()) {
                    // Create a new Chip
                    Chip chip = new Chip(KeywordActivity.this);
                    chip.setText(enteredText);
                    chip.setCloseIconVisible(true);
                    chip.setClickable(true);
                    chip.setOnCloseIconClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Chip chip = (Chip) v;
                            chipGroup.removeView(chip);
                            // Release any resources held by the chip, if applicable
                        }
                    });

                    // Add the Chip to the ChipGroup
                    chipGroup.addView(chip);

                    // Clear the EditText
                    keywordText.setText("");
                }
            }
        });

        // chip instances click listener
        int chipCount = chipGroup.getChildCount();
        for (int i = 0; i < chipCount; i++) {
            View chipView = chipGroup.getChildAt(i);
            if (chipView instanceof Chip) {
                final Chip chip = (Chip) chipView;
                chip.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                    }
                });
            }
        }
    }
}