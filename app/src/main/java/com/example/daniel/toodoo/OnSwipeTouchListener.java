package com.example.daniel.toodoo;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * Created by Daniel on 10/4/2015.
 */
/**
 * Detects left and right swipes across a view.
 */
public class OnSwipeTouchListener implements View.OnTouchListener {
    private TextView toodoo;
    private final GestureDetector gestureDetector;
    private LinearLayout layout;

    public OnSwipeTouchListener(Context context, TextView toodoo, LinearLayout layout) {
        gestureDetector = new GestureDetector(context, new GestureListener());
        this.toodoo = toodoo;
        this.layout = layout;
    }

    public LinearLayout getLayout() {
        return layout;
    }

    public TextView getTextView() {
        return toodoo;
    }

    public void onSwipeLeft() {
    }

    public void onSwipeRight() {
    }

    public void onClick() {

    }

    public boolean onTouch(View v, MotionEvent event) {
        return gestureDetector.onTouchEvent(event);
    }

    private final class GestureListener extends GestureDetector.SimpleOnGestureListener {

        private static final int SWIPE_DISTANCE_THRESHOLD = 100;
        private static final int SWIPE_VELOCITY_THRESHOLD = 100;

        @Override
        public boolean onDown(MotionEvent e) {
            return true;
        }
        boolean isHidden;
        @TargetApi(Build.VERSION_CODES.ICE_CREAM_SANDWICH)
        @Override
        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
            float distanceX = e2.getX() - e1.getX();
            float distanceY = e2.getY() - e1.getY();
            if (Math.abs(distanceX) > Math.abs(distanceY) && Math.abs(distanceX) > SWIPE_DISTANCE_THRESHOLD && Math.abs(velocityX) > SWIPE_VELOCITY_THRESHOLD) {
                if (distanceX > 0) {
                    float velocity = Math.abs(velocityX / 8000f);
                    long duration = (long) (100 / velocity); // calculate duration based on velocity
                    //getTextView().animate().translationX(isHidden ? 0 : 500).alpha(isHidden ? 1 : 0).setDuration(duration).start();
//                    getTextView().animate().translationX(isHidden ? 0 : 500).alpha(isHidden ? 1 : 0).setDuration(duration).setListener(new AnimatorListenerAdapter() {
//                        @Override
//                        public void onAnimationEnd(Animator animation) {
//                            layout.removeView(toodoo);
//                        }
//                    })
//                    .start();
                    //getTextView().animate().translationX(500).alpha(0).setDuration(150).start();
                    //know exactly what this does
                    //getLayout().removeView(getTextView());
                    onSwipeRight();
                }
//                else
//                    onSwipeLeft();
                return true;
            }

            //isHidden != isHidden;
            return false;
        }
    }
}
