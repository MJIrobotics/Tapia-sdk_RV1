package com.mji.tapia.tapiasdksample;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

public class MenuActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.menu_activity);

            //話す
            findViewById(R.id.button1).setOnClickListener(v -> {
                Intent intent = new Intent(this, TalkActivity.class);
                startActivity(intent);
            });

            //会話
            findViewById(R.id.button2).setOnClickListener(v -> {
                Intent intent = new Intent(this, ConversationActivity.class);
                startActivity(intent);
            });

            //回転
            findViewById(R.id.button3).setOnClickListener(v -> {
                Intent intent = new Intent(this, RotateActivity.class);
                startActivity(intent);
            });

            //アニメーション
            findViewById(R.id.button4).setOnClickListener(v -> {
                Intent intent = new Intent(this, AnimationActivity.class);
                startActivity(intent);
            });

    }


}
