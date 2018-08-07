package com.dbohdanov.viewswipe.rotation;

import android.view.MotionEvent;
import android.view.View;

import static com.dbohdanov.viewswipe.rotation.Settings.ANGLE_MAX;
import static com.dbohdanov.viewswipe.rotation.Settings.ANGLE_TO_DELETE;
import static com.dbohdanov.viewswipe.rotation.Settings.ANGLE_ZERO;
import static com.dbohdanov.viewswipe.rotation.Settings.BACK_ANIMATION_DURATION;
import static com.dbohdanov.viewswipe.rotation.Settings.VILOCITY;

/**
 * handles rotation listening
 */
public class RotationListener implements View.OnTouchListener {
    public static final int POSITION_LEFT = 0;
    public static final int POSITION_RIGHT = 1;

    private final OnMaxAngleReachedListener onMaxAngleReachedListener;


    private float angle_start = 0;

    //the position of block. for now could be left of right
    private int position;

    public RotationListener(int position, OnMaxAngleReachedListener onMaxAngleReachedListener) {
        if (!(position == POSITION_LEFT || position == POSITION_RIGHT)) {
            throw new IllegalArgumentException("illegal argument: position could be only int value of RIGHT or LEFT");
        }
        this.position = position;
        this.onMaxAngleReachedListener = onMaxAngleReachedListener;
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        float ax = v.getWidth() + v.getX();
        float ay = event.getRawY();
        float bx = v.getX() + v.getWidth();
        float by = v.getY() + v.getHeight();
        float cx = event.getRawX();
        float cy = event.getRawY();

        double ab = Math.sqrt(sqr(ax - bx) + sqr(ay - by));
        double bc = Math.sqrt(sqr(bx - cx) + sqr(by - cy));

        float angle = (-1) * (float) (Math.acos(ab / bc) * 180 / Math.PI);

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                angle_start = angle;
                break;

            case MotionEvent.ACTION_MOVE:
                float resultedAngle = (angle - angle_start) * VILOCITY;

                if (position == POSITION_LEFT) {
                    //set borders of rotation [ANGLE_ZERO; ANGLE_MAX]
                    if (resultedAngle >= ANGLE_ZERO) {
                        resultedAngle = ANGLE_ZERO;
                    } else if (resultedAngle <= -ANGLE_MAX) {
                        resultedAngle = -ANGLE_MAX;
                        onMaxAngleReachedListener.onMaxAngleReached();
                    }
                } else if (position == POSITION_RIGHT) {
                    if (resultedAngle <= ANGLE_ZERO) {
                        resultedAngle = ANGLE_ZERO;
                    } else if (resultedAngle >= ANGLE_MAX) {
                        resultedAngle = ANGLE_MAX;
                        onMaxAngleReachedListener.onMaxAngleReached();
                    }
                }

                v.setRotation(resultedAngle);

                break;

            case MotionEvent.ACTION_UP:
                if (position == POSITION_LEFT) {
                    if (v.getRotation() <= -ANGLE_TO_DELETE) {
                        onMaxAngleReachedListener.onMaxAngleReached();
                    } else {
                        v.animate().rotation(ANGLE_ZERO).setDuration(BACK_ANIMATION_DURATION);
                    }
                } else if (position == POSITION_RIGHT) {
                    if (v.getRotation() >= ANGLE_TO_DELETE) {
                        onMaxAngleReachedListener.onMaxAngleReached();
                    } else {
                        v.animate().rotation(ANGLE_ZERO).setDuration(BACK_ANIMATION_DURATION);
                    }
                }
                break;

            default:
                break;
        }
        return true;
    }

    private float sqr(float a) {
        return (float) Math.pow(a, 2);
    }
}
