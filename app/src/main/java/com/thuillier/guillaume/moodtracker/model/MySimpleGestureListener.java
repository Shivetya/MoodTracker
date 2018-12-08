package com.thuillier.guillaume.moodtracker.model;

import android.view.GestureDetector;
import android.view.MotionEvent;

public class MySimpleGestureListener extends GestureDetector.SimpleOnGestureListener {

    private MotionEvent mLastOnDownEvent = null;

    public static float sDeltaY;

    @Override
    public boolean onDown(MotionEvent e) {
        mLastOnDownEvent = e;
        return super.onDown(e);
    }

    @Override
    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {

        if (e1 == null) {
            e1 = mLastOnDownEvent;
        }

        if (e1 == null && e2 == null){
            return false;
        }

        if (e1 != null) {
            sDeltaY = e1.getY() - e2.getY();
            return true;

        } else {
            return false;
        }
    }

}
