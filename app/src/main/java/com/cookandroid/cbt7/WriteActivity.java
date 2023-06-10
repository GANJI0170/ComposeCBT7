package com.cookandroid.cbt7;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RadioGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.cookandroid.cbt7.database.addfoundarticle;
import com.cookandroid.cbt7.database.addlostarticle;
import com.cookandroid.cbt7.database.articlelostList;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.Date;

public class WriteActivity extends AppCompatActivity {

    private DatabaseReference databaseReference;
    private Button btn;
    private int board=0;
    private String foundnum, lostnum;
    private String id = "user001";
    private EditText titleedit, keywordedit, dateedit, placeedit, thingedit, featureedit, detailedit;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_write);

        databaseReference = FirebaseDatabase.getInstance().getReference("lost_article");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    lostnum = dataSnapshot.child("lost_number").getValue(String.class);
                    System.out.println(lostnum);
                    int n1 = Integer.parseInt(lostnum);
                    n1+=1;
                    lostnum = Integer.toString(n1);
                    lostnum = "0" + lostnum;
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        databaseReference = FirebaseDatabase.getInstance().getReference("found_article");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    foundnum = dataSnapshot.child("found_number").getValue(String.class);
                    System.out.println(foundnum);
                    int n2 = Integer.parseInt(foundnum);
                    n2+=1;
                    foundnum = Integer.toString(n2);
                    foundnum = "0" + foundnum;
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        titleedit = (EditText) findViewById(R.id.titleedit);
        keywordedit = (EditText) findViewById(R.id.keywordedit);
        dateedit = (EditText) findViewById(R.id.dateedit);
        placeedit = (EditText) findViewById(R.id.placeedit);
        thingedit = (EditText) findViewById(R.id.thingedit);
        featureedit = (EditText) findViewById(R.id.featureedit);
        detailedit = (EditText) findViewById(R.id.detailedit);

        ImageButton backbtn = (ImageButton) findViewById(R.id.backbtn);
        backbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();

            }
        });

        RadioGroup boardRG = (RadioGroup) findViewById(R.id.boardRG);
        boardRG.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.radioButton1:
                        board=0;
                        break;
                    case R.id.radioButton2:
                        board=1;
                        break;
                }
            }
        });

        long now = System.currentTimeMillis();
        Date mDate = new Date(now);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String getTime = simpleDateFormat.format(mDate);

        btn = (Button) findViewById(R.id.button);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (board) {
                    case 0:
                        addlostarticle(lostnum, titleedit.getText().toString(), keywordedit.getText().toString(),
                                id, dateedit.getText().toString(), placeedit.getText().toString(),
                                thingedit.getText().toString(), featureedit.getText().toString(), detailedit.getText().toString(), getTime, "0", "");
                        Intent intent1 = new Intent(getApplicationContext(), LookupActivity.class);
                        intent1.putExtra("boardType", "분실물 게시판");
                        intent1.putExtra("articleNum", lostnum);
                        startActivity(intent1);
                        finish();
                        break;
                    case 1:
                        addfoundarticle(foundnum, titleedit.getText().toString(), keywordedit.getText().toString(),
                                id, dateedit.getText().toString(), placeedit.getText().toString(),
                                thingedit.getText().toString(), featureedit.getText().toString(), detailedit.getText().toString(), getTime, "0", "");
                        Intent intent2 = new Intent(getApplicationContext(), LookupActivity.class);
                        intent2.putExtra("boardType", "습득물 게시판");
                        intent2.putExtra("articleNum", foundnum);
                        startActivity(intent2);
                        finish();
                        break;
                }
            }
        });

    }

    public void addfoundarticle(String number, String title, String keyword, String id, String date, String place, String thing, String feature, String detail, String post_date, String hits, String image) {
        addfoundarticle addfoundarticle = new addfoundarticle(number, title, keyword, id, date, place, thing, feature, detail, post_date, hits, image);
        databaseReference = FirebaseDatabase.getInstance().getReference();
        databaseReference.child("found_article").push().setValue(addfoundarticle);
    }

    public void addlostarticle(String number, String title, String keyword, String id, String date, String place, String thing, String feature, String detail, String post_date, String hits, String image) {
        addlostarticle addlostarticle = new addlostarticle(number, title, keyword, id, date, place, thing, feature, detail, post_date, hits, image);
        databaseReference = FirebaseDatabase.getInstance().getReference();
        databaseReference.child("lost_article").push().setValue(addlostarticle);
    }
}
