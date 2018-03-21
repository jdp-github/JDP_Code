package com.jdp.arsenal.test;

import android.app.Activity;
import android.os.Bundle;

import com.jdp.arsenal.R;

public class DimActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dim);

        CrashHandler.getInstance().init(this);
    }
}
