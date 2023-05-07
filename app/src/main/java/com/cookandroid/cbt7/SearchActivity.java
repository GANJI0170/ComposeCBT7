package com.cookandroid.cbt7;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.content.Context;
import android.content.res.AssetManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
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

        EditText keyword = findViewById(R.id.keyword);
        Button btn1 = findViewById(R.id.btn1);
        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String result = mSearch.searchKeyword(keyword.getText().toString());
                Toast.makeText(getApplicationContext(), result, Toast.LENGTH_SHORT).show();
                // 검색 결과를 어떻게 처리할지 여기에 작성
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

            // 키워드가 포함된 텍스트 파일 읽어오기
            InputStream inputStream = getAssetInputStream("lostfoundkeywords.txt");
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                // 텍스트 파일에서 키워드 추출하여 HashMap에 저장
                KomoranResult result = komoran.analyze(line);
                List<Token> tokens = result.getTokenList();
                for (Token token : tokens) {
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
            return "해당 키워드를 찾을 수 없습니다.";
        }
    }

    private InputStream getAssetInputStream(String fileName) throws IOException {
        AssetManager assetManager = mContext.getAssets();
        return assetManager.open(fileName);
    }
}
