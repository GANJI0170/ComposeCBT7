package com.cookandroid.cbt7;

import android.os.Bundle;
import android.util.Log;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.cookandroid.cbt7.database.articlefoundList;
import com.cookandroid.cbt7.database.articlelostList;
import com.cookandroid.cbt7.database.foundAdaptor;
import com.cookandroid.cbt7.database.lostAdaptor;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;

public class MyarticleActivity extends AppCompatActivity {
    private DatabaseReference databaseReference;
    private ArrayList<articlefoundList> foundarrayList;
    private ArrayList<articlelostList> lostarrayList;
    private RecyclerView myarticleRecyclerView;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    private RadioGroup myarticleradiogroup;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_myarticle);

        myarticleRecyclerView = findViewById(R.id.myarticleRecyclerView);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(getApplicationContext(),new LinearLayoutManager(this).getOrientation());
        myarticleRecyclerView.addItemDecoration(dividerItemDecoration);
        myarticleRecyclerView.setHasFixedSize(true);
        foundarrayList = new ArrayList<>();
        lostarrayList = new ArrayList<>();

        layoutManager = new LinearLayoutManager(this);
        myarticleRecyclerView.setLayoutManager(layoutManager);

        lostdatabase();

        myarticleradiogroup = (RadioGroup) findViewById(R.id.myarticleradiogroup);
        myarticleradiogroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.lostbtn:
                        lostdatabase();
                        break;
                    case R.id.foundbtn:
                        founddatabase();
                        break;
                }
            }
        });

    }

    public void lostdatabase() {
        databaseReference = FirebaseDatabase.getInstance().getReference("lost_article");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                lostarrayList.clear();
                for (DataSnapshot snapshot1 : snapshot.getChildren()) {
                    String id = snapshot1.child("lost_id").getValue(String.class);
                    System.out.println(id);
                    if(id.equals("user001")) {
                        articlelostList articlelostList = snapshot1.getValue(articlelostList.class);
                        lostarrayList.add(articlelostList);
                    }
                }
                adapter.notifyDataSetChanged();
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.e("myarticleActivity", String.valueOf(error.toException()));
            }
        });
        adapter = new lostAdaptor(lostarrayList, getApplicationContext(), 0);
        myarticleRecyclerView.setAdapter(adapter);
    }
    public void founddatabase() {
        databaseReference = FirebaseDatabase.getInstance().getReference("found_article");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                foundarrayList.clear();
                for (DataSnapshot snapshot1 : snapshot.getChildren()) {
                    String id = snapshot1.child("found_id").getValue(String.class);
                    System.out.println(id);
                    if(id.equals("user001")) {
                        articlefoundList articlefoundList = snapshot1.getValue(articlefoundList.class);
                        foundarrayList.add(articlefoundList);
                    }
                }
                adapter.notifyDataSetChanged();
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.e("SearchActivity", String.valueOf(error.toException()));
            }
        });
        adapter = new foundAdaptor(foundarrayList, getApplicationContext(), 0);
        myarticleRecyclerView.setAdapter(adapter);
    }
}
