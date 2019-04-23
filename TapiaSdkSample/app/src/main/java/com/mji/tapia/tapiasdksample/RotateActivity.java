package com.mji.tapia.tapiasdksample;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.Toast;

import com.mji.tapia.sdk_animation.AnimationView;
import com.mji.tapia.sdk_animation.face.CryAnimation;
import com.mji.tapia.sdk_core.SDK;
import com.mji.tapia.sdk_robot.SDKRobot;
import com.mji.tapia.sdk_robot.TapiaRobotManager;

import static com.mji.tapia.sdk_robot.TapiaRobotManager.Direction;
import static com.mji.tapia.sdk_robot.TapiaRobotManager.OnRotateListener;

//タピアが泣きながら上下左右に動く
    public class RotateActivity extends Activity {

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //アクティビティに作成したレイアウトを配置する
        setContentView(R.layout.talk_activity);

        AnimationView view  = findViewById(R.id.animation_view);

        //画面をタップした時に終了する
        view.setOnClickListener(v -> {
            finish();
        });


        //RobotManagerのインスタンスを設定する
        TapiaRobotManager tapiaRobotManager = SDKRobot.getRobotManager(getApplicationContext());

        //タピアが上向きの最大まで回転する。その後下向き最大までに回転する。その後右左に回転する。
        tapiaRobotManager.rotateToVerticalMax(new OnRotateListener() {
            @Override
            public void onRotate() {

                tapiaRobotManager.rotateToVerticalMin(new OnRotateListener() {
                    @Override
                    public void onRotate() {

                        //右に15度回転します。
                        tapiaRobotManager.rotate(Direction.RIGHT,15, new TapiaRobotManager.OnRotateListener(){
                            @Override
                            public void onRotate() {

                                //左に20度回転します。
                                tapiaRobotManager.rotate(Direction.LEFT,20,null);

                            }
                        });

                    }
                });

            }
        });

        //タピアが泣いているアニメーションを設定する
        CryAnimation cryAnimation = new CryAnimation(this);
        view.startAnimation(cryAnimation);
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
