package com.cookandroid.cbt7;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TabHost;
import android.widget.Toast;

import androidx.annotation.NonNull;
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
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class HomeActivity extends AppCompatActivity {

    private RecyclerView recyclerView1, recyclerView2;
    private RecyclerView.Adapter adapter1, adapter2;
    private RecyclerView.LayoutManager layoutManager1, layoutManager2;
    private ArrayList<articlefoundList> arrayListfound;
    private ArrayList<articlelostList> arrayListlost;
    private FirebaseDatabase database;
    private DatabaseReference databaseReference1, databaseReference2;
    private Button btnSearch, btnwrite, btnmyarticle, btnchat, btnalarm, btnprofile;

    FirebaseDatabase database_re = FirebaseDatabase.getInstance();
    DatabaseReference lostRef = database_re.getReference("lost_article");
    DatabaseReference foundRef = database_re.getReference("found_article");
    Query query = foundRef.limitToFirst(80);

    private String lostContent, foundContent, foundNumber;

    RecyclerView rcommend_re;
    recommendAdaptor recommend_ad;
    List<found_list> foundList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        rcommend_re = findViewById(R.id.recyclerView5);

        // LinearLayoutManager를 사용하여 리사이클러뷰에 레이아웃 매니저 설정
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        rcommend_re.setLayoutManager(layoutManager);

        recommend_ad = new recommendAdaptor(foundList, this, 1);
        rcommend_re.setAdapter(recommend_ad);


//      탭호스트 설정
        TabHost tabHost1 = (TabHost) findViewById(R.id.tabHost1);
        tabHost1.setup() ;
        TabHost.TabSpec ts1 = tabHost1.newTabSpec("Tab Spec 1");
        TabHost.TabSpec ts2 = tabHost1.newTabSpec("Tab Spec 2");
        TabHost.TabSpec ts3 = tabHost1.newTabSpec("Tab Spec 3");
        ts1.setContent(R.id.mainboard);
        ts2.setContent(R.id.lostboard);
        ts3.setContent(R.id.foundboard);
        ts1.setIndicator("홈");
        ts2.setIndicator("분실물게시판");
        ts3.setIndicator("습득물게시판");
        tabHost1.addTab(ts1);
        tabHost1.addTab(ts2);
        tabHost1.addTab(ts3);

        boardDatabase();

        EditText editkw = (EditText) findViewById(R.id.editkw);


//      검색 버튼 클릭시 검색화면으로 전환
        btnSearch = (Button)findViewById(R.id.btnSearch) ;
        btnSearch.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Toast.makeText(HomeActivity.this, "검색창이동", Toast.LENGTH_SHORT).show();
                Intent in = new Intent(HomeActivity.this, SearchActivity.class);
                in.putExtra("searchText", editkw.getText().toString());
                startActivity(in);
            }
        });

        btnwrite = (Button) findViewById(R.id.btnwrite);
        btnwrite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent in = new Intent(getApplicationContext(), WriteActivity.class);
                startActivity(in);
            }
        });
        btnmyarticle = (Button) findViewById(R.id.btnmyarticle);
        btnmyarticle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent in = new Intent(getApplicationContext(), MyarticleActivity.class);
                startActivity(in);
            }
        });
        btnchat = (Button) findViewById(R.id.btnchat);
        btnchat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in = new Intent(getApplicationContext(), ChatListActivity.class);
                startActivity(in);
            }
        });
        btnalarm = (Button) findViewById(R.id.btnalarm);
        btnalarm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent in = new Intent(getApplicationContext(), AlarmActivity.class);
                startActivity(in);
            }
        });
        btnprofile = (Button) findViewById(R.id.btnprofile);
        btnprofile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent in = new Intent(getApplicationContext(), ProfileActivity.class);
                startActivity(in);
            }
        });

        //추천
        lostRef.orderByChild("lost_id").equalTo("user001").limitToFirst(1).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    DataSnapshot latestSnapshot = dataSnapshot.getChildren().iterator().next();
                    lostContent = latestSnapshot.child("lost_content").getValue(String.class);
                } else {
                    Log.d("MainActivity", "No matching data found.");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.e("MainActivity", "Error retrieving data: " + databaseError.getMessage());
            }
        });

        foundList = new ArrayList<>();

        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    List<found_list> resultList = new ArrayList<>();
                    foundList.clear();

                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        foundNumber = snapshot.child("found_number").getValue(String.class);
                        foundContent = snapshot.child("found_content").getValue(String.class);

                        //유사도 추출(레벤슈타인 클래스)
                        double similarity = Levenshtin.SimilarityCalculator.calculateSimilarity(lostContent, foundContent);

                        // found_list 객체 생성 및 값을 설정
                        found_list foundItem = new found_list();
                        foundItem.setFound_number(foundNumber);
                        foundItem.setFound_content(foundContent);
                        foundItem.setFound_similarity(similarity);

                        resultList.add(foundItem);

//                        // 유사도 계산 및 결과 출력(메인 함수내에서)
//                        calculateAndDisplaySimilarity(foundNumber, foundContent);
                    }

                    //getTopNItems 함수 호출하여 내림차순 되어 있는 리스트에서 10개의 값 추출
                    List<found_list> top10List = getTopNItems(resultList, 10);

                    // found_list를 사용하여 원하는 작업 수행
                    // 예를 들어, 리스트를 어댑터에 설정하여 화면에 표시
                    //유사도 순으로 리사이클 뷰에 표시하기
                    foundRef.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            List<found_list> matchingSnapshots = new ArrayList<>();

                            for (found_list item : top10List) {
                                String foundNumberFromTop10 = item.getFound_number();
                                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                                    String foundNumberFromSnapshot = snapshot.child("found_number").getValue(String.class);
                                    if (foundNumberFromTop10.equals(foundNumberFromSnapshot)) {
                                        found_list foundItem = new found_list();
                                        foundItem.setFound_number(foundNumberFromTop10);
                                        foundItem.setFound_date(snapshot.child("found_date").getValue(String.class));
                                        foundItem.setFound_hits(snapshot.child("found_hits").getValue(String.class));
                                        foundItem.setFound_id(snapshot.child("found_id").getValue(String.class));
                                        foundItem.setFound_keyword(snapshot.child("found_keyword").getValue(String.class));
                                        foundItem.setFound_post_date(snapshot.child("found_post_date").getValue(String.class));
                                        foundItem.setFound_title(snapshot.child("found_title").getValue(String.class));
                                        foundItem.setFound_image(snapshot.child("found_image").getValue(String.class));

                                        matchingSnapshots.add(foundItem);
                                        break;
                                    }
                                }
                            }

                            // matchingSnapshots를 리사이클러뷰에 설정하고 표시하는 작업 수행
                            // ...
                            // matchingSnapshots를 어댑터에 설정
                            recommend_ad.setData(matchingSnapshots);

                            // 변경사항 반영
                            recommend_ad.notifyDataSetChanged();

                            // 로그로 출력해보기
                            for (found_list item : matchingSnapshots) {
                                Log.d("MainActivity", item.toString());
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {
                            Log.e("MainActivity", "Error retrieving data: " + databaseError.getMessage());
                        }
                    });

//                    //앱에 re_list 텍스트 뷰로 나타내기
//                    StringBuilder result = new StringBuilder();
//                    for (found_list data : top10List) {
//                        result.append("Number: ").append(data.getFound_number())
//                                .append(", Content: ").append(data.getFound_content())
//                                .append(", Similarity: ").append(data.getFound_similarity())
//                                .append("\n");
//                    }
//                    re_list.setText(result.toString());

                } else {
                    Log.d("MainActivity", "No matching data found.");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.e("MainActivity", "Error retrieving data: " + databaseError.getMessage());
            }
        });

    }

    private void boardDatabase() {

        recyclerView1 = findViewById(R.id.recyclerView1);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(getApplicationContext(),new LinearLayoutManager(this).getOrientation());
        recyclerView1.addItemDecoration(dividerItemDecoration);
        recyclerView1.setHasFixedSize(true);
        layoutManager1 = new LinearLayoutManager(this);
        recyclerView1.setLayoutManager(layoutManager1);
        arrayListlost = new ArrayList<>(); //articleList 객체를 어댑터 쪽으로 담을 arrayList
        database = FirebaseDatabase.getInstance(); //파이어베이스 연동


        databaseReference1 = database.getReference("lost_article"); // DB 테이블 연결
        databaseReference1.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                //파이어베이스 데이터를 받아오는 곳
                arrayListlost.clear(); //기존 배열 초기화
                for (DataSnapshot snapshot1 : snapshot.getChildren()) { //반복문으로 데이터 리스트 추출
                    articlelostList articlelostList = snapshot1.getValue(articlelostList.class); //arrayList 객체에 데이터 담기
                    arrayListlost.add(articlelostList);
                }
                adapter1.notifyDataSetChanged(); //리스트 저장 및 새로고침
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.e("LoginActivity", String.valueOf(error.toException()));
            }
        });

//      습득물 게시판 데이터베이스 연동
        recyclerView2 = findViewById(R.id.recyclerView2);
        recyclerView2.addItemDecoration(dividerItemDecoration);
        recyclerView2.setHasFixedSize(true);
        layoutManager2 = new LinearLayoutManager(this);
        recyclerView2.setLayoutManager(layoutManager2);
        arrayListfound = new ArrayList<>();

        databaseReference2 = database.getReference("found_article");
        databaseReference2.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                arrayListfound.clear();
                for (DataSnapshot snapshot1 : snapshot.getChildren()) { //반복문으로 데이터 리스트 추출
                    articlefoundList articlefoundList = snapshot1.getValue(articlefoundList.class); //arrayList 객체에 데이터 담기
                    arrayListfound.add(articlefoundList);
                }
                adapter2.notifyDataSetChanged(); //리스트 저장 및 새로고침
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.e("LoginActivity", String.valueOf(error.toException()));
            }
        });
        adapter1 = new lostAdaptor(arrayListlost, this, 1);
        adapter2 = new foundAdaptor(arrayListfound, this, 1);
        recyclerView1.setAdapter(adapter1);
        recyclerView2.setAdapter(adapter2);
    }
    //채팅방 연동



    //추천글
    //유사도를 추출한 리스트에 대해 내림차순정렬
    private List<found_list> getTopNItems(List<found_list> list, int n) {
        Collections.sort(list, new SimilarityComparator());

        return list.subList(0, Math.min(list.size(), n));
    }
}

