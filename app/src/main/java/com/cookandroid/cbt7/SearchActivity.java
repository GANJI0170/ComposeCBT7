package com.cookandroid.cbt7;
import android.content.Context;
import android.content.res.AssetManager;
import android.util.Log;

import android.os.Bundle;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
/*import org.apache.commons.io.IOUtils;
import opennlp.tools.tokenize.SimpleTokenizer;
import opennlp.tools.tokenize.Tokenizer;
import opennlp.tools.tokenize.TokenizerModel;
*/
public class SearchActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        EditText keyword = (EditText)findViewById(R.id.keyword);

        /*public class LostAndFoundSearch {
            private static final String TAG = "LostAndFoundSearch";

            private Context mContext;
            private Map<String, String> mDictionary;

            public LostAndFoundSearch(Context context) {
                mContext = context;
                mDictionary = new HashMap<>();
            }

            public void loadKeywords() {
                try {
                    // OpenNLP tokenizer 초기화
                    //TokenizerModel model = new TokenizerModel(getAssetInputStream("en-token.bin"));
                    //Tokenizer tokenizer = SimpleTokenizer.INSTANCE;

                    // 분실물과 습득물에 관한 키워드를 저장할 HashMap 초기화

                    // 키워드가 포함된 텍스트 파일 읽어오기
                    String text = IOUtils.toString(getAssetInputStream("lostfoundkeywords.txt"), "UTF-8");

                    // 텍스트 파일에서 키워드 추출하여 HashMap에 저장
                    String[] tokens = tokenizer.tokenize(text);
                    for (int i = 0; i < tokens.length; i++) {
                        if (tokens[i].equals("lost") || tokens[i].equals("found")) {
                            String keyword = tokens[i] + " " + tokens[i+1];
                            String meaning = tokens[i+2];
                            mDictionary.put(keyword, meaning);
                            i += 2;
                        }
                    }
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

         */
    }
}