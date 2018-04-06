package com.example.assignment.helpers;

import android.support.v4.view.ViewPager;
import android.content.Context;
import android.os.Parcelable;
import android.util.AttributeSet;
import android.view.MotionEvent;

/**
 * Hacky fix for Issue #4 and
 * http://code.google.com/p/android/issues/detail?id=18990
 * <p>
 * ScaleGestureDetector seems to mess up the touch events, which means that
 * ViewGroups which make use of onInterceptTouchEvent throw a lot of
 * IllegalArgumentException: pointerIndex out of range.
 * <p>
 * There's not much I can do in my code for now, but we can mask the result bye
 * just catching the problem and ignoring it.
 *
 * @author Chris Banes
 */
public class ViewPagerModified extends ViewPager {

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        try {
            super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        } catch (Exception e) {
            reloadAdapter();
        }
    }

    @Override
    public void setCurrentItem(int item, boolean animation) {
        int currentItem = getCurrentItem();
        if (currentItem == item) {
            return;
        }
        if (currentItem <= item) {
            setMovingForward();
        } else {
            setMovingBackward();
        }
        super.setCurrentItem(item, animation);
    }

    private boolean mMovingForward = true;
    private boolean mMovingBackward = false;


    public ViewPagerModified setMovingBackward() {
        this.mMovingForward = false;
        this.mMovingBackward = true;
        return this;
    }

    public ViewPagerModified setMovingForward() {
        this.mMovingForward = true;
        this.mMovingBackward = false;
        return this;
    }

    public boolean isMovingForward() {
        return mMovingForward;
    }

    public boolean isMovingBack() {
        return mMovingBackward;
    }

    public ViewPagerModified(Context context) {
        super(context);
    }

    public ViewPagerModified(Context context, AttributeSet attrs) {
        super(context, attrs);
    }


    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        try {
            return super.onTouchEvent(ev);
        } catch (Exception ignored) {
        }
        return false;
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        try {
            return super.onInterceptTouchEvent(ev);
        } catch (Exception ignored) {
        }
        return false;
    }

    public void reloadAdapter() {
        try {
            Parcelable state = onSaveInstanceState();
            setAdapter(getAdapter());
            onRestoreInstanceState(state);
        } catch (Exception ignored) {
        }
    }

}

