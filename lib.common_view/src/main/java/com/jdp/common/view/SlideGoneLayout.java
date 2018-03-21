package com.jdp.common.view;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.widget.LinearLayout;

/**
 * Describe:侧滑消失
 * Author: jidapeng
 * Date: 2015/12/9
 */
@TargetApi(Build.VERSION_CODES.HONEYCOMB)
public class SlideGoneLayout extends LinearLayout {

    // 屏幕分辨率宽度
    private int mScreenWidth;
    private int mLastX;
    /** 原始横坐标 */
    private float mOrigX;
    private float mTouchX;

    private OnViewGone mOnViewGone;

    public SlideGoneLayout(Context context, AttributeSet attrs) {
        super(context, attrs);

        DisplayMetrics dm = new DisplayMetrics();
        ((Activity) context).getWindowManager().getDefaultDisplay().getMetrics(dm);
        mScreenWidth = dm.widthPixels;
        mOrigX = getTranslationX();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int x = (int) event.getRawX();
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                mTouchX = event.getRawX();
                break;
            case MotionEvent.ACTION_MOVE:
                int moveX = x - mLastX;
                setTranslationX(getTranslationX() + moveX);
                setAlpha(Math.abs(mTouchX - event.getRawX()) / mScreenWidth / 2 + 0.3f);
                break;
            case MotionEvent.ACTION_UP:
                if (x <= mScreenWidth / 4) {
                    smoothScroll(getTranslationX(), 0);
                } else if (x > mScreenWidth / 4 && x <= mScreenWidth / 2 || x > mScreenWidth / 2 && x <= mScreenWidth * 3 / 4) {
                    smoothScroll(getTranslationX(), mOrigX);
                    changeAlpha(getAlpha(), 1);
                } else if (x > mScreenWidth * 3 / 4) {
                    smoothScroll(getTranslationX(), mScreenWidth);
                }
                break;
        }
        mLastX = x;

        return true;
    }

    private void smoothScroll(float from, float to) {
        ObjectAnimator animator = ObjectAnimator.ofFloat(this, "translationX", from, to);
        animator.start();
        animator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {
                if (mOnViewGone != null) {
                    mOnViewGone.onViewGone(SlideGoneLayout.this);
                }
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });
    }

    private void changeAlpha(float from, float to) {
        ObjectAnimator animator = ObjectAnimator.ofFloat(this, "alpha", from, to);
        animator.start();
    }

    public interface OnViewGone {
        void onViewGone(SlideGoneLayout slideGoneLayout);
    }
}
