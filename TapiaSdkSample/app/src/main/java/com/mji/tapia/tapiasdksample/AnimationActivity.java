package com.mji.tapia.tapiasdksample;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;

import com.mji.tapia.sdk_animation.Animation;
import com.mji.tapia.sdk_animation.AnimationView;

import com.mji.tapia.sdk_animation.face.HeartAnimation;
import com.mji.tapia.sdk_animation.face.SmileAnimation;
import com.mji.tapia.sdk_core.LanguageManager;
import com.mji.tapia.sdk_speech.SDKSpeech;
import com.mji.tapia.sdk_speech.TTSManager;


//タピアの顔をタップするとアニメーションが切り替わる
    public class AnimationActivity extends Activity {

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //アクティビティに作成したレイアウトを配置する
        setContentView(R.layout.talk_activity);

        //戻るボタン用のImageViewオブジェクト取得
        AnimationView view  = findViewById(R.id.animation_view);

        //画面をタップした時に終了する
        view.setOnClickListener(v -> {
            finish();
        });

        //日本語の設定をする
        TTSManager ttsManager = SDKSpeech.getDefaultTTSManager(this.getApplicationContext(), LanguageManager.Language.JAPANESE);

        //これか必ず必要ですか?
        if (!ttsManager.isInit()) {
            ttsManager.init();
        }

        //アニメーションのインスタンスを設定します。
        HeartAnimation heartAnimation = new HeartAnimation(this);

        //ハートのアニメーションが開始した時に「はい、こんにちは」としゃべる。
        heartAnimation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onEvent(Animation animation, Animation.Event event) {
                switch ((HeartAnimation.HeartEvent) event) {
                    case HEART_DISPLAY_END:

                        //こんにちはと話しをします。
                        TTSManager.Session session = ttsManager.createSession("はい、こんにちは。");
                        session.start();
                        break;
                }
            }
        });
        //アニメーション開始
       view.startAnimation(heartAnimation);

        //アニメーションの変更
        //ハートのアニメーションの後に、スマイルアニメーションに変更。
        new Handler().postDelayed(() -> {
            SmileAnimation smileAnimation = new SmileAnimation(this);
            view.startAnimation(smileAnimation);
            smileAnimation.setAnimationSpeed(30);
        }, 5000);
   }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

}
