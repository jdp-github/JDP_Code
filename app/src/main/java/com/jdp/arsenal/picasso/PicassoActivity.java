package com.jdp.arsenal.picasso;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ImageView;

import com.jdp.arsenal.R;
import com.squareup.picasso.Picasso;

public class PicassoActivity extends Activity {

    String url1 = "http://www.jcodecraeer.com/uploads/20140731/67391406772378.png";

    private ImageView mIv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_picasso);

        mIv = (ImageView) findViewById(R.id.picasso_img);
        Picasso.with(this).load(url1).placeholder(R.drawable.about).error(R.mipmap.ic_launcher).into(mIv);
    }
}
