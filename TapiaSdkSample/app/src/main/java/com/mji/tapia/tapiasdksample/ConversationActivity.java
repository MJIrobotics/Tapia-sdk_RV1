package com.mji.tapia.tapiasdksample;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings;
import android.util.JsonReader;
import android.util.JsonToken;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;

import com.mji.tapia.sdk_animation.AnimationView;
import com.mji.tapia.sdk_animation.face.HappyAnimation;
import com.mji.tapia.sdk_core.LanguageManager;

import com.mji.tapia.sdk_speech.Configuration;
import com.mji.tapia.sdk_speech.SDKSpeech;
import com.mji.tapia.sdk_speech.STTManager;
import com.mji.tapia.sdk_speech.TTSManager;
import com.mji.tapia.sdk_speech.mji.STTConfig;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

import static com.mji.tapia.sdk_animation.AnimationController.TAG;



//タピアとお話する
//public class TalkActivity extends AppCompatActivity {
//public class TalkActivity extends TapiaActivity {
public class ConversationActivity extends Activity {

    private List<Word> keywordList = new ArrayList<Word>();
    STTManager sttManager = null;
    TTSManager ttsManager;
    TTSManager.Session tts_Session = null;
    String sAnswer = null;
    private String keyword;//キーワードから取得
    private String answer;//タピアの発話

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //アクティビティに作成したレイアウトを配置する
        setContentView(R .layout.talk_activity);

        //前の画面に戻る用のImageViewオブジェクト取得
        AnimationView view  = findViewById(R.id.animation_view);

        //画面をタップした時に終了する
        view.setOnClickListener(v -> {
            //STTManagerの終話処理を入れる
            sttManager.stopCurrent();
            finish();
        });

        //笑顔のアニメーションを設定する
        HappyAnimation happyAnimation = new HappyAnimation(this);
        view.startAnimation(happyAnimation);
        happyAnimation.setAnimationSpeed(30);

        //JSNONに設定した会話ファイルを読み込む
        try {
                //  assetsフォルダからJSON データを取得する
                InputStream oInStream = null;
                oInStream = getAssets().open("keyword.json");

                // JSON データをオブジェクトに変換する
                JsonReader jReader = new JsonReader( new InputStreamReader( oInStream ,"UTF-8") );

                //データをkeywordListへ追加する
                jReader.beginArray();
                while (jReader.hasNext()) {
                    keywordList.add(readMessage(jReader));
                }
                jReader.endArray();

            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }


        //TTSManagerの設定をする。日本語を設定します。
        ttsManager = SDKSpeech.getDefaultTTSManager(this.getApplicationContext(), LanguageManager.Language.JAPANESE);

        if (!ttsManager.isInit()) {
            ttsManager.init();
        }

        //タピアが「はい」と発話する
        sayRepeat("こんにちは。");

    }


//ユーザーが発話した言葉をキャッチして、それに合った言葉をタピアが発話する
        public STTManager.Session getSTTSession() {
        return sttManager.createSession().setOnResultListener(results -> {

            if (results.size() > 0) {

            //タピアが聞き取った言葉を振り分ける。
            for (String sentence : results){ //sentence:ユーザー発話
                for (int iList = 0; iList < keywordList.size() ; iList ++ ){//カテゴリをカウントする
                    for (int iKeylist = 0; iKeylist < keywordList.get(iKeylist).getKeyword().size() ; iKeylist++){  //キーワードをカウントする

                        keyword = keywordList.get(iList).getKeyword().get(iKeylist) ;//キーワードを変数にセットする

                        //ユーザー発話とキーワードが合致した場合、ArrayListからanswerをランダムで取得する
                        if (sentence.equals(keyword)){

                            Random r = new Random();
                            int n = r.nextInt(keywordList.get(iList).getAnswer().size());
                            answer = keywordList.get(iList).getAnswer().get(n);

                            break;
                        }else {
                            answer = "もしもし";//ユーザー発話がキーワードにない場合「もしもし」と答える
                        }
                    }
                    if (sentence.equals(keyword)){//ループを抜ける
                        break;
                    }
                }
                if (sentence.equals(keyword)){//ループを抜ける
                    break;
                }
            }

        }else{
            answer="もう一度お願いします。";
        }

        //キーワードリストから取得した言葉を発話する
        sayRepeat(answer);//
        });
    }

    private void sayRepeat(String sentence) {

        tts_Session = ttsManager.createSession(sentence);
        tts_Session.setOnFinishListener(new TTSManager.Session.OnFinishListener() {
            @Override
            public void onFinish() {

                sttManager = SDKSpeech.getDefaultSTTManager(LanguageManager.Language.JAPANESE);
                sttManager.setDefaultLanguage(LanguageManager.Language.JAPANESE);

                if (!sttManager.isInit()) {
                    Configuration configuration = new STTConfig();
                    HashMap<Configuration.Option, Object> optionObjectHashMap = new HashMap<>();
                    optionObjectHashMap.put(STTConfig.MJIOption.VAD_OFFTIME,100 );
                    configuration.setProviderOptions(optionObjectHashMap);
                    sttManager.init(configuration);
                }

                getSTTSession().listen();
            }
        });
        //発話する
        tts_Session.start();
    }

    //JSONデータをkeywordListへ追加する
    public Word readMessage(JsonReader reader) throws IOException {
        String category = null;
        List<String> keyword = null;
        List<String> answer = null;

        reader.beginObject();
        while (reader.hasNext()) {
            String name = reader.nextName();
            if (name.equals("Category")) {
                category = reader.nextString();
            } else if (name.equals("User") && reader.peek() != JsonToken.NULL) {
                keyword = readStringArray(reader);
            } else if (name.equals("Tapia") && reader.peek() != JsonToken.NULL) {
                answer = readStringArray(reader);
            } else {
                reader.skipValue();
            }
        }
        reader.endObject();
        return  new Word(category,keyword,answer);
    }

    //カテゴリ毎にデータを読む
    public List<String> readStringArray(JsonReader reader) throws IOException {
        List<String> wordList = new ArrayList<String>();

        reader.beginArray();
        while (reader.hasNext()) {
            wordList.add(reader.nextString());
        }
        reader.endArray();
        return wordList;
    }


    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onStop(){
        super.onStop();
        sttManager.stopCurrent();
    }

    @Override
    protected void onDestroy(){
        super.onDestroy();
        sttManager.stopCurrent();
    }

}
