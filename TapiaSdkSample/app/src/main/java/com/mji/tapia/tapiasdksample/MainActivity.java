package com.mji.tapia.tapiasdksample;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import com.mji.tapia.sdk_core.SDK;


public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.menu_activity);

        //アカウント登録した時に発行したキーを取得
        SDK.init(getApplication(),getString(R.string.sdk_key), R.raw.sdk_license);

        SDK.setOnInitFinishListener(b ->{

                //メニュー画面が表示
                Intent intent = new Intent(this, MenuActivity.class);
                startActivity(intent);

        });

    }

}
