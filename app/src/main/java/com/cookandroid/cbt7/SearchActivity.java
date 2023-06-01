package com.cookandroid.cbt7;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.content.Context;
import android.content.res.AssetManager;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.cookandroid.cbt7.database.articlefoundList;
import com.cookandroid.cbt7.database.articlelostList;
import com.cookandroid.cbt7.database.foundAdaptor;
import com.cookandroid.cbt7.database.lostAdaptor;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import kr.co.shineware.nlp.komoran.constant.DEFAULT_MODEL;
import kr.co.shineware.nlp.komoran.core.Komoran;
import kr.co.shineware.nlp.komoran.model.KomoranResult;
import kr.co.shineware.nlp.komoran.model.Token;

public class SearchActivity extends AppCompatActivity {

    private LostAndFoundSearch mSearch;
    private DatabaseReference databaseReference;
    private ArrayList<articlefoundList> foundarrayList, foundarrayList2;
    private ArrayList<articlelostList> lostarrayList, lostarrayList2;
    private RecyclerView resultrecyclerView;
    private RecyclerView.Adapter adapter;
    private String radioStr = "최신순";
    private String spinnerStr, resultoriginal, result;
    private RadioGroup resultradiogroup;
    private EditText editText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        //        //사전 정의
//        FileOutputStream fos;
//        String strFileContents = "제트플립\tNNP\n" + "제트 플립\tNNP\n" + "갤럭시노트10\tNNP\n"
//                +"아이폰13프로\tNNP\n"+"갤럭시S20울트라\tNNP\n"+"갤럭시S23울트라\tNNP\n"
//                +"갤럭시Z플립4\tNNP\n"+"접이식우산\tNNP\n"+"장우산\tNNP\n"+"골프우산\tNNP\n"
//                +"갤럭시Z플립4\tNNP\n"+"접이식우산\tNNP\n"+"장우산\tNNP\n"+"골프우산\tNNP\n"
//                +"거꾸로우산\tNNP\n"+"자동우산\tNNP\n"+"크로스백\tNNP\n"+"여행가방\tNNP\n"
//                +"백팩\tNNP\n"+"토트백\tNNP\n"+"보스턴백\tNNP\n"+"베이스볼 캡\tNNP\n"
//                +"비니\tNNP\n"+"페도라\tNNP\n"+"버킷햇\tNNP\n"+"트루퍼햇\tNNP\n"
//                +"장지갑\tNNP\n"+"반지갑\tNNP\n"+"동전지갑\tNNP\n";
//        try {
//            fos = openFileOutput("userDic.txt", MODE_PRIVATE);
//            fos.write(strFileContents.getBytes(StandardCharsets.UTF_8));
//            fos.close();
//            System.out.println("성공");
//        } catch (FileNotFoundException e) {
//            e.printStackTrace();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }

        Intent intent = getIntent();
        String searchText = intent.getStringExtra("searchText");

        editText = (EditText) findViewById(R.id.keyword);
        editText.setText(searchText);

        mSearch = new LostAndFoundSearch(this);
        mSearch.loadKeywords();


        Spinner spinner = (Spinner) findViewById(R.id.spinner);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                spinnerStr = parent.getItemAtPosition(position).toString();
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        resultradiogroup =(RadioGroup) findViewById(R.id.resultradiogroup);
        resultradiogroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.newestradiobtn:
                        radioStr = "최신순";
                        break;
                    case R.id.oldradiobtn:
                        radioStr = "오래된순";
                        break;
                }
            }
        });

        resultrecyclerView = findViewById(R.id.resultrecyclerView);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(getApplicationContext(),new LinearLayoutManager(this).getOrientation());
        resultrecyclerView.addItemDecoration(dividerItemDecoration);
        resultrecyclerView.setHasFixedSize(true);
        foundarrayList = new ArrayList<>();
        foundarrayList2 = new ArrayList<>();
        lostarrayList = new ArrayList<>();
        lostarrayList2 = new ArrayList<>();

        Button btn1 = findViewById(R.id.btn1);
        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                result = mSearch.searchKeyword(editText.getText().toString());
                Toast.makeText(getApplicationContext(), result, Toast.LENGTH_SHORT).show();

                switch (spinnerStr) {
                    case "분실물":
                        lostdatabase();
                        break;
                    case "습득물":
                        founddatabase();
                        break;
                }
            }
        });

    }

    public void lostdatabase() {
        resultoriginal = editText.getText().toString();
        databaseReference = FirebaseDatabase.getInstance().getReference("lost_article");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                lostarrayList.clear();
                lostarrayList2.clear();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    String str = dataSnapshot.child("lost_keyword").getValue(String.class);
                    if(str.contains(resultoriginal)) {
                        articlelostList articlelostList = dataSnapshot.getValue(articlelostList.class);
                        if(str.contains(result)) {
                            lostarrayList2.add(articlelostList);
                        }else {
                            lostarrayList.add(articlelostList);
                        }
                    }
                    adapter.notifyDataSetChanged();
                }
                if (radioStr == "최신순") {
                    Collections.reverse(lostarrayList);
                    Collections.reverse(lostarrayList2);
                }
                lostarrayList.addAll(lostarrayList2);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.e("SearchActivity", String.valueOf(error.toException()));
            }
        });
        adapter = new lostAdaptor(lostarrayList, getApplicationContext(), 1);
        resultrecyclerView.setAdapter(adapter);
    }
    public void founddatabase() {
        resultoriginal = editText.getText().toString();
        databaseReference = FirebaseDatabase.getInstance().getReference("found_article");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                foundarrayList.clear();
                foundarrayList2.clear();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    String str = dataSnapshot.child("found_keyword").getValue(String.class);
                    if(str.contains(resultoriginal)) {
                        articlefoundList articlefoundList = dataSnapshot.getValue(articlefoundList.class);
                        if(str.contains(result)) {
                            foundarrayList2.add(articlefoundList);
                        }else {
                            foundarrayList.add(articlefoundList);
                        }
                    }
                    adapter.notifyDataSetChanged();
                }
                if (radioStr == "최신순") {
                    Collections.reverse(foundarrayList);
                    Collections.reverse(foundarrayList2);
                }
                foundarrayList.addAll(foundarrayList2);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.e("SearchActivity", String.valueOf(error.toException()));
            }
        });
        adapter = new foundAdaptor(foundarrayList, getApplicationContext(), 1);
        resultrecyclerView.setAdapter(adapter);
    }
}



class LostAndFoundSearch {
    private static final String TAG = "LostAndFoundSearch";
    private Context mContext;
    private Map<String, String> mDictionary;

    public LostAndFoundSearch(Context context) {
        mContext = context;
        mDictionary = new HashMap<>();
    }

    public void loadKeywords() {
        try {
            // 코모란 초기화
            Komoran komoran = new Komoran(DEFAULT_MODEL.FULL);
            komoran.setUserDic("/data/data/com.cookandroid.cbt7/files/userDic.txt");
            // 키워드가 포함된 텍스트 파일 읽어오기
            InputStream inputStream = getAssetInputStream("lostfoundkeywords.txt");
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                // 텍스트 파일에서 키워드 추출하여 HashMap에 저장
                KomoranResult result = komoran.analyze(line);
                List<Token> tokens = result.getTokenList();
                for (Token token : tokens) {
                    System.out.println("token : " + token);
                    String pos = token.getPos();
                    if (pos.equals("NNP") || pos.equals("NNG")) { // 고유명사 또는 일반명사만 추출
                        String keyword = token.getMorph();
                        String meaning = line.replaceAll(keyword, "").trim();
                        mDictionary.put(keyword, meaning);
                        break;
                    }
                }
                System.out.println("한줄 끝");
                System.out.println(mDictionary);
            }

            bufferedReader.close();
            inputStream.close();
        } catch (IOException e) {
            Log.e(TAG, "Failed to load keywords", e);
        }
    }

    public String searchKeyword(String keyword) {
        if (mDictionary.containsKey(keyword)) {
            return mDictionary.get(keyword);
        } else {
            return keyword;
            //검색어 없을시 그대로 검색되는 처리해줘야함.
        }
    }

    private InputStream getAssetInputStream(String fileName) throws IOException {
        AssetManager assetManager = mContext.getAssets();
        return assetManager.open(fileName);
    }
}
