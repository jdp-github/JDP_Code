package com.jdp.common.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.widget.RadioButton;

import jdp.com.libcommon.R;

/**
 * Describe:带红点的RadioButton
 * Author: jidapeng
 * Date: 2015/12/9
 */
public class BadgeRadio extends RadioButton {

    /** 背景 */
    private Paint mBgPaint;
    /** 文字颜色 */
    private Paint mTextPaint;

    /** 半径 */
    private float mRaduis;

    private boolean mIsShowBadge = true;
    private boolean mIsShowText;
    private String mText;

    public void setIsShowBadge(boolean isShowBadge) {
        this.mIsShowBadge = isShowBadge;
        invalidate();
    }

    public void setIsShowText(boolean isShowText) {
        this.mIsShowText = isShowText;
        invalidate();
    }

    public void setShowText(String text) {
        this.mText = text;
    }

    public BadgeRadio(Context context, AttributeSet attrs) {
        super(context, attrs);

        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.BadgeRadio);
        mRaduis = typedArray.getDimension(R.styleable.BadgeRadio_badge_radius, context.getResources().getDimension(R.dimen.badge_radio_default_radius));


        initPaint(context, typedArray);
        typedArray.recycle();
    }

    private void initPaint(Context ctx, TypedArray typedArray) {
        int bgColor = typedArray.getColor(R.styleable.BadgeRadio_badge_background, Color.RED);
        int textColor = typedArray.getColor(R.styleable.BadgeRadio_badge_text_color, Color.WHITE);
        float textSize = typedArray.getDimension(R.styleable.BadgeRadio_badge_text_size, ctx.getResources().getDimension(R.dimen.badge_radio_default_text_size));

        // 背景
        mBgPaint = new Paint();
        mBgPaint.setColor(bgColor);
        mBgPaint.setAntiAlias(true);
        // 字
        mTextPaint = new Paint();
        mTextPaint.setColor(textColor);
        mTextPaint.setTextSize(textSize);
        mTextPaint.setAntiAlias(true);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        if (mIsShowBadge) {
            float xPos = getWidth() - mRaduis;
            canvas.drawCircle(xPos, mRaduis, mRaduis, mBgPaint);
            if (mIsShowText && !TextUtils.isEmpty(mText)) {
                canvas.drawText(mText, xPos - 5 * mText.length(), mRaduis + 7, mTextPaint);
            }
        }
    }
}
