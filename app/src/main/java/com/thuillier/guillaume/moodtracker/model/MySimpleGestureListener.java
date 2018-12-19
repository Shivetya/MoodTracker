package com.thuillier.guillaume.moodtracker.model;

import android.view.GestureDetector;
import android.view.MotionEvent;

/**Here, the method "onFling" is override, that allow us to know, if the user swipe vertically, in which orientation
 * the swipe is done.
 * sDeltaY will be used in MainActivity.
 */

public class MySimpleGestureListener extends GestureDetector.SimpleOnGestureListener {

    public static float sDeltaY;

    @Override
    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {

        if (e1 == null && e2 == null) {
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
