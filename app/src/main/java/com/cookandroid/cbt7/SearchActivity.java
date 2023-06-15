package com.cookandroid.cbt7;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.AssetManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
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

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.Normalizer;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import kr.co.shineware.nlp.komoran.constant.DEFAULT_MODEL;
import kr.co.shineware.nlp.komoran.core.Komoran;
import kr.co.shineware.nlp.komoran.model.KomoranResult;
import kr.co.shineware.nlp.komoran.model.Token;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;


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

    private Button btnSelectImage;
    private static final int REQUEST_IMAGE_PICK = 1;
    private static final int PERMISSION_REQUEST_READ_EXTERNAL_STORAGE = 2;
    private static final int PERMISSION_REQUEST_READ_MEDIA_IMAGES = 1;
    private String responseServer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        /*        //사전 정의
        FileOutputStream fos;
        String strFileContents = "제트플립\tNNP\n" + "제트 플립\tNNP\n" + "갤럭시노트10\tNNP\n"
                +"아이폰13프로\tNNP\n"+"갤럭시S20울트라\tNNP\n"+"갤럭시S23울트라\tNNP\n"
                +"갤럭시Z플립4\tNNP\n"+"접이식우산\tNNP\n"+"장우산\tNNP\n"+"골프우산\tNNP\n"
                +"갤럭시Z플립4\tNNP\n"+"접이식우산\tNNP\n"+"장우산\tNNP\n"+"골프우산\tNNP\n"
                +"거꾸로우산\tNNP\n"+"자동우산\tNNP\n"+"크로스백\tNNP\n"+"여행가방\tNNP\n"
                +"백팩\tNNP\n"+"토트백\tNNP\n"+"보스턴백\tNNP\n"+"베이스볼 캡\tNNP\n"
                +"비니\tNNP\n"+"페도라\tNNP\n"+"버킷햇\tNNP\n"+"트루퍼햇\tNNP\n"
                +"장지갑\tNNP\n"+"반지갑\tNNP\n"+"동전지갑\tNNP\n";
        try {
            fos = openFileOutput("userDic.txt", MODE_PRIVATE);
            fos.write(strFileContents.getBytes(StandardCharsets.UTF_8));
            fos.close();
            System.out.println("성공");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }*/

        Intent intent = getIntent();
        String searchText = intent.getStringExtra("searchText");

        komoranThread thread = new komoranThread();
        thread.start();

        editText = (EditText) findViewById(R.id.keyword);
        editText.setText(searchText);

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

        ImageButton backbtn = (ImageButton) findViewById(R.id.backbtn);
        backbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();

            }
        });

        btnSelectImage = findViewById(R.id.btnimage); // 연결된 파일의 btnimage id를 가진 버튼을 찾음

        btnSelectImage.setOnClickListener(new View.OnClickListener() { // OnClickListener 클래스의 onClick 매서드 재정의
            @Override
            public void onClick(View v) {
                Log.d("TAG", "TEST");
                System.out.println("이미지 버튼 누름");
                checkReadStoragePermission();
            } // 버튼을 누르면 checkReadStoragePerission 메서드를 실행
        });
    }

    public class komoranThread extends Thread {
        @Override
        public void run() {
            mSearch = new LostAndFoundSearch(getApplicationContext());
            mSearch.loadKeywords();
        }
    }

    public void lostdatabase() {
        System.out.println("lostdatabase 실행");
        resultoriginal = editText.getText().toString();
        databaseReference = FirebaseDatabase.getInstance().getReference("lost_article");
        lostarrayList.clear();
        lostarrayList2.clear();
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
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
                }
                if (radioStr == "최신순") {
                    Collections.reverse(lostarrayList);
                    Collections.reverse(lostarrayList2);
                }
                adapter.notifyDataSetChanged();
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
        foundarrayList.clear();
        foundarrayList2.clear();
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
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
                }
                if (radioStr == "최신순") {
                    Collections.reverse(foundarrayList);
                    Collections.reverse(foundarrayList2);
                }
                adapter.notifyDataSetChanged();
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

    public void checkReadStoragePermission() { // 필요한 권한을 요청함. 이미 있는 경우 무시
        // 사용자가 이미 앱에 권한을 부여했는지 확인하는 메서드, 권한이 없는 경우 권한을 요청함
        // 권한이 있는경우 PERMISSION_GRANTED를 반환하고 없는 경우 PERMISSION_DENIED를 반환함
        if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.READ_MEDIA_IMAGES)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_MEDIA_IMAGES}, PERMISSION_REQUEST_READ_MEDIA_IMAGES);
        } else { // 권한이 있는 경우
            pickImage();
        }
    }

    private void pickImage() {
        System.out.println("pickImage 메서드 작동");
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI); //이미지 선택을 위한 창으로 전환함
        startActivityForResult(intent, REQUEST_IMAGE_PICK);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        System.out.println("onActivityResult 메서드 작동");
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_IMAGE_PICK && resultCode == Activity.RESULT_OK && data != null) {
            Uri imageUri = data.getData();
            if (imageUri != null) {
                uploadImage(imageUri);
            }

        }
    }

    private void UnicodeInKorean() {
        System.out.println("UnicodeInKorean 작동");
        System.out.println(responseServer);

        System.out.println("response.length : " + responseServer.length());
        String Data = responseServer.replaceAll("[\"]" , "");

        String hangulCharacters = "";
        System.out.println("Data.length : " + Data.length());
        for (int i = 2; i < Data.length(); i += 6) {
            String hexCode = Data.substring(i, i + 4);
            try {
                hangulCharacters += Character.toString((char) Integer.parseInt(hexCode, 16)); // Code 형태(16진수)의 문자열을 해석해 10진수로 변경하고 그 값을 char
                //타입으로 변경한 후에 다시 String으로 변경하는 라인.
            } catch (NumberFormatException e){
                e.printStackTrace();
                String str = Data.substring(i - 2, i - 1);
                System.out.println("str : " + str);
                i -= 5;
                hangulCharacters += str;
            }
            if(i+6 == Data.length()){
                String str = Data.substring(i + 4, i + 5);
                hangulCharacters += str;
            }
        }
        System.out.println("서버 응답 해석 : " + hangulCharacters);
        responseServer = hangulCharacters;

        // UI 요소에 접근하는 부분
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                String normalizedKeyword = Normalizer.normalize(responseServer, Normalizer.Form.NFC);
                editText.setText(normalizedKeyword);
                result = mSearch.searchKeyword(normalizedKeyword);

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

    private void uploadImage(Uri imageUri) {
        System.out.println("uploadImage 메서드 작동");
        String imagePath = getImagePath(imageUri);
        File imageFile = new File(imagePath);
        // 서버 주소
        String ngrokServerUrl = "http://45c9-34-82-138-130.ngrok.io";

        new Thread(() -> {
            System.out.println("thread 작동");
            OkHttpClient client = new OkHttpClient();
            MediaType mediaType = MediaType.parse("image/*");

            RequestBody requestBody = new MultipartBody.Builder()
                    .setType(MultipartBody.FORM)
                    .addFormDataPart("file", imageFile.getName(),
                            RequestBody.create(mediaType, imageFile))
                    .build();

            Request request = new Request.Builder()
                    .url(ngrokServerUrl)
                    .post(requestBody)
                    .build();

            try {
                Response response = client.newCall(request).execute();
                if (response.isSuccessful()) {
                    String responseData = response.body().string();
                    handleServerResponse(responseData);
                } else {
                    System.out.println("----요청실패----"); // 이 스레드에서 ui 스레드(toast)를 동작시키지 못해서 임시로 이렇게 처리함
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            System.out.println("thread 완료");
        }).start();
    }

    private String getImagePath(Uri uri) {
        String[] projection = {MediaStore.Images.Media.DATA};
        Cursor cursor = getContentResolver().query(uri, projection, null, null, null);
        if (cursor != null) {
            int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            cursor.moveToFirst();
            String imagePath = cursor.getString(column_index);
            cursor.close();
            return imagePath;
        }
        return uri.getPath();
    }

    private void handleServerResponse(String responseData) {
        System.out.println("서버 응답 : " + responseData);
        responseServer = responseData;
        UnicodeInKorean(); // 서버로부터 받은 유니코드를 한글로 변환
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
        }
        else {
            return keyword;
            //검색어 없을시 그대로 검색되는 처리해줘야함.
        }
    }

    private InputStream getAssetInputStream(String fileName) throws IOException {
        AssetManager assetManager = mContext.getAssets();
        return assetManager.open(fileName);
    }
}