package com.jdp.common.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.os.CountDownTimer;
import android.util.AttributeSet;
import android.view.View;

import jdp.com.libcommon.R;

/**
 * Describe: 自动画圆弧
 * Author: jidapeng
 * Date: 2015/12/10
 */
public class DrawArcView extends View {

    private Paint mPaint;
    private RectF mOval;

    /** 弧线宽度 */
    private float mStrokeWidth;
    /** 弧线颜色 */
    private int mStrokeColor;
    /** 弧度 */
    private long mRadian;
    private CountDownTimer mDownTimer;

    /** 总时长 */
    private long mTotalTime;
    /** 计时间隔 */
    private long mCountTimeInterval;
    /** 累加弧度 */
    private long mRadinAccumulation;

    private boolean mIsRunning;
    private OnDrawArc mOnDrawArc;

    public void setOnDrawArc(OnDrawArc onDrawArc) {
        this.mOnDrawArc = onDrawArc;
    }

    public void setTotalTime(long totalTime) {
        this.mTotalTime = totalTime;
    }

    public void setCountTimeInterval(long countTimeInterval) {
        this.mCountTimeInterval = countTimeInterval;
    }

    public void setRadinAccumulation(long radinAccumulation) {
        this.mRadinAccumulation = radinAccumulation;
    }

    public DrawArcView(final Context context, AttributeSet attrs) {
        super(context, attrs);

        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.DrawArcView);
        mStrokeColor = typedArray.getColor(R.styleable.DrawArcView_draw_arc_stroke_color, Color.RED);
        mStrokeWidth = typedArray.getDimension(R.styleable.DrawArcView_draw_arc_stroke_width, context.getResources().getDimension(R.dimen.draw_arc_default_stroke_width));

        mPaint = new Paint();
        mOval = new RectF();

        typedArray.recycle();
    }

    public DrawArcView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    /** 初始化RectF */
    private void initRectF() {
        mOval.left = mStrokeWidth;
        mOval.top = mStrokeWidth;
        mOval.right = getWidth() - mStrokeWidth;
        mOval.bottom = getHeight() - mStrokeWidth;
    }

    /** 初始化Paint */
    private void initPaint() {
        mPaint.setAntiAlias(true);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeWidth(mStrokeWidth);
        mPaint.setColor(mStrokeColor);
    }

    /** 开始计时 */
    public void start() {
        if (mTotalTime == 0) {
            throw new IllegalArgumentException("总时长不能为0");
        }
        if (mCountTimeInterval == 0) {
            throw new IllegalArgumentException("计时间隔不能为0");
        }
        if (mRadinAccumulation == 0) {
            throw new IllegalArgumentException("累加弧度不能为0");
        }
        if (mRadinAccumulation > 360) {
            throw new IllegalArgumentException("累加弧度不超过360");
        }

        if (!mIsRunning) {

            initPaint();
            initRectF();

            mDownTimer = new CountDownTimer(mTotalTime, mCountTimeInterval) {
                @Override
                public void onTick(long millisUntilFinished) {
                    mRadian += mRadinAccumulation;
                    invalidate();
                    if (mOnDrawArc != null) {
                        mOnDrawArc.drawing(mRadian);
                    }
                }

                @Override
                public void onFinish() {
                    mIsRunning = false;
                    if (mOnDrawArc != null) {
                        mOnDrawArc.onFinished();
                    }
                }
            };
            mDownTimer.start();
            mIsRunning = true;
        }
    }

    public void clear() {
        if (!mIsRunning) {
            mRadian = 0;
            mPaint.setColor(Color.TRANSPARENT);
            invalidate();
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawArc(mOval, 0, mRadian, false, mPaint);
    }

    public interface OnDrawArc {
        void drawing(long currArc);

        void onFinished();
    }
}
