package com.mji.tapia.tapiasdksample;

import android.app.Activity;
import android.os.Bundle;
import com.mji.tapia.sdk_animation.AnimationView;
import com.mji.tapia.sdk_animation.face.HappyAnimation;
import com.mji.tapia.sdk_core.LanguageManager;
import com.mji.tapia.sdk_speech.SDKSpeech;
import com.mji.tapia.sdk_speech.TTSManager;


//タピアが「初めまして」としゃべる
public class TalkActivity extends Activity {

    TTSManager.Session session;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //アクティビティに作成したレイアウトを配置する
        setContentView(R .layout.talk_activity);

        //前の画面に戻る用のImageViewオブジェクト取得
        AnimationView view  = findViewById(R.id.animation_view);

        //画面をタップした時に終了する
        view.setOnClickListener(v -> {
            finish();
        });

        //TTSManagerのインスタンスを取得します。日本語を設定します。
        TTSManager ttsManager = SDKSpeech.getDefaultTTSManager(this.getApplicationContext(), LanguageManager.Language.JAPANESE);

        //これか必ず必要ですか?
        if (!ttsManager.isInit()) {
            ttsManager.init();
        }

        //start関数で「初めまして」と話をさせる
         session = ttsManager.createSession("初めまして");
        session.start();

        //笑顔のアニメーションを設定をする
        HappyAnimation happyAnimation = new HappyAnimation(this);
        view.startAnimation(happyAnimation);
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
        session.cancel();
    }

    @Override
    protected void onDestroy(){
        super.onDestroy();
        session.cancel();
    }





}
