package com.cookandroid.cbt7;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.content.Context;
import android.content.res.AssetManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import kr.co.shineware.nlp.komoran.constant.DEFAULT_MODEL;
import kr.co.shineware.nlp.komoran.core.Komoran;
import kr.co.shineware.nlp.komoran.model.KomoranResult;
import kr.co.shineware.nlp.komoran.model.Token;

public class SearchActivity extends AppCompatActivity {

    private LostAndFoundSearch mSearch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        mSearch = new LostAndFoundSearch(this);
        mSearch.loadKeywords();

//        FileOutputStream fos;
//        String strFileContents = "부캐\tNNP\n" + "제트플립\tNNP\n" + "갤럭시노트10\tNNP\n"
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


        EditText keyword = findViewById(R.id.keyword);
        Button btn1 = findViewById(R.id.btn1);
        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String result = mSearch.searchKeyword(keyword.getText().toString());
                Toast.makeText(getApplicationContext(), result, Toast.LENGTH_SHORT).show();
                // 검색 결과를 어떻게 처리할지 여기에 작성
                // 결과값과 게시판 키워드 비교로 결과 출력 해줘야한다.
            }
        });
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
                    }
                }
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
            return "\"" + keyword + "\"";
            //검색어 없을시 그대로 검색되는 처리해줘야함.
        }
    }

    private InputStream getAssetInputStream(String fileName) throws IOException {
        AssetManager assetManager = mContext.getAssets();
        return assetManager.open(fileName);
    }
}
