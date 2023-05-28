package com.cookandroid.cbt7;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Text;

public class LookupActivity extends AppCompatActivity {
    private DatabaseReference databaseReference;
    private TextView boardType, titleText, keywordText, dateText, placeText, thingText, featureText, detailText;
    private ImageView articleImage;
    private String board, articleNum, num;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lookup);

        Intent intent = getIntent();
        board = intent.getStringExtra("boardType");
        articleNum = intent.getStringExtra("articleNum");

        boardType = (TextView) findViewById(R.id.board_type);
        boardType.setText(board);

        titleText = (TextView) findViewById(R.id.titleText);
        keywordText = (TextView) findViewById(R.id.keywordText);
        dateText = (TextView) findViewById(R.id.dateText);
        placeText = (TextView) findViewById(R.id.placeText);
        thingText = (TextView) findViewById(R.id.thingText);
        featureText = (TextView) findViewById(R.id.featureText);
        detailText = (TextView) findViewById(R.id.detailText);
        articleImage = (ImageView) findViewById(R.id.articleImage);

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
}
