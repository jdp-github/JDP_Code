package com.jdp.common.view;

import android.app.Activity;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.os.AsyncTask;
import android.os.Environment;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.View;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

import jdp.com.libcommon.R;

/**
 * Describe:手写板
 * Author: jidapeng
 * Date: 2015/12/29
 */
public class HandWriteBoard extends View {

    private Context mCtx;

    private Paint mPaint;
    private Path mPath;
    private int mBgColor;

    private int mScreenWidth;
    private int mScreenHeight;

    private float mCurrX;
    private float mCurrY;

    /** 保存图片的路径 */
    private String mFileDir;

    private OnSavePicCallBack mOnSavePicCallBack;

    public HandWriteBoard(Context context, AttributeSet attrs) {
        super(context, attrs);

        mCtx = context;

        initScreenPixel();

        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.HandWriteBoard);
        initPaint(typedArray);
        mBgColor = typedArray.getColor(R.styleable.HandWriteBoard_hand_write_background_color, Color.WHITE);
        mPath = new Path();

        typedArray.recycle();
    }

    public void setFileDir(String fileDir) {
        this.mFileDir = fileDir;
    }

    public void setOnSavePicCallBack(OnSavePicCallBack onSavePicCallBack) {
        this.mOnSavePicCallBack = onSavePicCallBack;
    }

    /** 初始化屏幕宽高 */
    private void initScreenPixel() {
        DisplayMetrics dm = new DisplayMetrics();
        ((Activity) mCtx).getWindowManager().getDefaultDisplay().getMetrics(dm);
        mScreenWidth = dm.widthPixels;// 获取屏幕分辨率宽度
        mScreenHeight = dm.heightPixels;
    }

    private void initPaint(TypedArray typedArray) {
        float strokeWidth = typedArray.getDimension(R.styleable.HandWriteBoard_hand_write_stroke_width, 5);
        int strokeColor = typedArray.getColor(R.styleable.HandWriteBoard_hand_write_storke_color, Color.GREEN);

        mPaint = new Paint();
        mPaint.setAntiAlias(true); // 去除锯齿
        mPaint.setStrokeWidth(strokeWidth);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setColor(strokeColor);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        float x = event.getX();
        event.getHistorySize();
        float y = event.getY();

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                mCurrX = x;
                mCurrY = y;
                mPath.moveTo(mCurrX, mCurrY);
                break;
            case MotionEvent.ACTION_MOVE:
                mCurrX = x;
                mCurrY = y;
                mPath.quadTo(mCurrX, mCurrY, x, y);
                break;
            case MotionEvent.ACTION_UP:
                break;
        }

        invalidate();

        return true;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.drawPath(mPath, mPaint);
    }

    /**
     * 清空
     */
    public void clear() {
        mPath.reset();
        invalidate();
    }

    /**
     * 保存图片
     */
    public void savePic() {
        Bitmap blankBitmap = Bitmap.createBitmap(getWidth(), getHeight(), Bitmap.Config.RGB_565);
        Canvas canvas = new Canvas(blankBitmap);
        canvas.drawColor(mBgColor);
        draw(canvas);

        AsyncTask<Bitmap, Void, File> task = new AsyncTask<Bitmap, Void, File>() {
            @Override
            protected void onPreExecute() {
                if (mOnSavePicCallBack != null) {
                    mOnSavePicCallBack.preSavePic();
                }
            }

            @Override
            protected File doInBackground(Bitmap... params) {
                Bitmap bitmap = params[0];
                File dirFile;
                if (TextUtils.isEmpty(mFileDir)) {
                    dirFile = Environment.getExternalStorageDirectory();
                } else {
                    dirFile = new File(mFileDir);
                    if (!dirFile.exists()) {
                        dirFile.mkdirs();
                    }
                }

                File saveFile = new File(dirFile, System.currentTimeMillis() + ".png");
                FileOutputStream fos = null;
                try {
                    fos = new FileOutputStream(saveFile);
                    bitmap.compress(Bitmap.CompressFormat.PNG, 80, fos);
                    bitmap.recycle();
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (Exception e) {
                    e.printStackTrace();
                }

                return saveFile;
            }

            @Override
            protected void onPostExecute(File file) {
                if (mOnSavePicCallBack != null) {
                    if (file.exists()) {
                        mOnSavePicCallBack.onSaveSuccess(file);
                    } else {
                        mOnSavePicCallBack.onSaveFail("file not found");
                    }
                }
            }
        };
        task.execute(blankBitmap);
    }

    /**
     * 保存图片回调
     */
    public interface OnSavePicCallBack {
        /**
         * 保存前
         */
        void preSavePic();

        /**
         * 保存失败
         *
         * @param msg 错误信息
         */
        void onSaveFail(String msg);

        /**
         * 保存成功
         *
         * @param file 生成的文件
         */
        void onSaveSuccess(File file);
    }
}
