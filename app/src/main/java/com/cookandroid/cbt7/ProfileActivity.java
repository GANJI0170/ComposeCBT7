package com.cookandroid.cbt7;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ProfileActivity extends AppCompatActivity {
    private Button testbtn, testbtn1;
    private DatabaseReference databaseReference;
    private String value;
    private String key;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);



//        데이터베이스 데이터 저장 테스트용
        testbtn = (Button) findViewById(R.id.testbtn);
        testbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                databaseReference = FirebaseDatabase.getInstance().getReference("test");
                databaseReference.push().child("test1").setValue("데이터베이스 저장 테스트");
            }
        });
        testbtn1 = (Button) findViewById(R.id.testbtn1);
        testbtn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                databaseReference = FirebaseDatabase.getInstance().getReference("test");
                databaseReference.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        key = "";
                        for (DataSnapshot snapshot1 : snapshot.getChildren()) {
                            value = snapshot1.child("test1").getValue(String.class);
                            System.out.println(value);
                            if(value.equals("데이터베이스 저장 테스트")) {
                                System.out.println("동일");
                                key = snapshot1.getKey();
                                System.out.println(key);
                            }
                        }
                        System.out.println("한번");
                        if(key != "") {
                            databaseReference.child(key).removeValue();
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            }
        });
    }
}
