package com.jdp.arsenal.test;

import android.app.Activity;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.view.ViewGroup;

import com.jdp.arsenal.R;

/**
 * 上传图片遮罩层
 */
public class UploadImgActivity extends Activity {

    View myView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload_img);

        myView = findViewById(R.id.myview);
        myView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startAnim();
            }
        });
    }

    int myHeight;

    private void startAnim() {
        myHeight = myView.getHeight();
        CountDownTimer countDownTimer = new CountDownTimer(5000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                ViewGroup.LayoutParams params = myView.getLayoutParams();
                myHeight -= 100;
                params.height = myHeight;
                myView.setLayoutParams(params);
            }

            @Override
            public void onFinish() {

            }
        };
        countDownTimer.start();
    }
}
