package com.dbohdanov.viewswipe.rotation;

import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

/**
 * handles rotation listening
 */
public class RotationListener implements View.OnTouchListener {
    public static final String TAG = "taag";


    public static final int POSITION_LEFT = 0;
    public static final int POSITION_RIGHT = 1;

    private final OnMaxAngleReachedListener onMaxAngleReachedListener;
    private Float yTouchConstant = null;

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

    //    @Override
//    public boolean onTouch(View v, MotionEvent event) {
//        float ax = v.getWidth() + v.getX();
//        float ay = yTouchConstant == null ? event.getRawY() : yTouchConstant;
//        float bx = v.getX() + v.getWidth();
//        float by = v.getY() + v.getHeight();
//        float cx = event.getRawX();
//        float cy = yTouchConstant == null ? event.getRawY() : yTouchConstant;
//
//
//        double ab = Math.sqrt(sqr(ax - bx) + sqr(ay - by));
//        double bc = Math.sqrt(sqr(bx - cx) + sqr(by - cy));
//
//        float angle = (position == POSITION_LEFT ? (-1) : 1) * (float) (Math.acos(ab / bc) * 180 / Math.PI);
//
//        switch (event.getAction()) {
//            case MotionEvent.ACTION_DOWN:
//                angle_start = angle;
//                yTouchConstant = event.getRawY();
//                break;
//
//            case MotionEvent.ACTION_MOVE:
//                float resultedAngle = (angle - angle_start) * VILOCITY;
//
//                if (position == POSITION_LEFT) {
//                    //set borders of rotation [ANGLE_ZERO; ANGLE_MAX]
//                    if (resultedAngle >= ANGLE_ZERO) {
//                        resultedAngle = ANGLE_ZERO;
//                    } else if (resultedAngle <= -ANGLE_MAX) {
//                        resultedAngle = -ANGLE_MAX;
//                        onMaxAngleReachedListener.onMaxAngleReached();
//                    }
//                } else if (position == POSITION_RIGHT) {
//                    if (resultedAngle <= ANGLE_ZERO) {
//                        resultedAngle = ANGLE_ZERO;
//                    } else if (resultedAngle >= ANGLE_MAX) {
//                        resultedAngle = ANGLE_MAX;
//                        onMaxAngleReachedListener.onMaxAngleReached();
//                    }
//                }
//
//                v.setRotation(resultedAngle);
//
//                break;
//
//            case MotionEvent.ACTION_UP:
//                if (position == POSITION_LEFT) {
//                    if (v.getRotation() <= -ANGLE_TO_DELETE) {
//                        onMaxAngleReachedListener.onMaxAngleReached();
//                    } else {
//                        v.animate().rotation(ANGLE_ZERO).setDuration(BACK_ANIMATION_DURATION);
//                    }
//                } else if (position == POSITION_RIGHT) {
//                    if (v.getRotation() >= ANGLE_TO_DELETE) {
//                        onMaxAngleReachedListener.onMaxAngleReached();
//                    } else {
//                        v.animate().rotation(ANGLE_ZERO).setDuration(BACK_ANIMATION_DURATION);
//                    }
//                }
//                yTouchConstant = null;
//                break;
//
//            default:
//                break;
//        }
//        return true;
//    }
    private int x_cord, y_cord, x, y;
    private int flag = 0;
    private float x0;

    private float sqr(float a) {
        return (float) Math.pow(a, 2);
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        x_cord = (int) event.getRawX();
        y_cord = (int) event.getRawY();

        int viewCenter = v.getWidth() / 2 + (int) x0;
        int viewWidth = v.getWidth();

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                x0 = event.getRawX() - event.getX();
                x = (int) event.getRawX();
                y = (int) event.getRawY();
                break;

            case MotionEvent.ACTION_MOVE:
                x_cord = (int) event.getRawX();
                y_cord = (int) event.getRawY();
//                Log.d(TAG, "x " + x
//                        + " y " + y
//                        + " xcord " + x_cord
//                        + " ycord " + y_cord);
//
//                Log.d(TAG, "new x" + (x_cord - x));
                v.setX(x_cord - x);
                v.setY(y_cord - y);


                if (x_cord >= viewCenter) {
//                    v.setRotation((float) ((x_cord - viewCenter - (position == POSITION_LEFT ? 0 : (event.getRawX() - event.getX()))) * (Math.PI / 32)));
                    v.setRotation((float) ((x_cord - viewCenter) * (Math.PI / 32)));

                    if (Math.abs(v.getRotation()) >= Settings.ANGLE_TO_DELETE) {
                        flag = 2;
                    } else {
                        flag = 0;
                    }
//                    if (x_cord > (viewCenter + (viewCenter / 2))) {
//                        if (x_cord > (viewWidth - (viewCenter / 4))) {
//                            flag = 2;
//                        } else {
//                            flag = 0;
//                        }
//                    } else {
//                        flag = 0;
//                    }
                } else {
                    // rotate image while moving
                    v.setRotation((float) ((x_cord - viewCenter) * (Math.PI / 32)));

                    if (Math.abs(v.getRotation()) >= Settings.ANGLE_TO_DELETE) {
                        flag = 2;
                    } else {
                        flag = 0;
                    }
//                    if (x_cord < (viewCenter / 2)) {
//                        if (x_cord < viewCenter / 4) {
//                            flag = 1;
//                        } else {
//                            flag = 0;
//                        }
//                    } else {
//                        flag = 0;
//                    }
                }

                break;
            case MotionEvent.ACTION_UP:
                v.setX(0);
                v.setY(0);

                x_cord = (int) event.getRawX();
                y_cord = (int) event.getRawY();

//                Log.e("X Point", "" + x_cord + " , Y " + y_cord);
//                tvUnLike.setAlpha(0);
//                tvLike.setAlpha(0);

                if (flag == 0) {
                    v.setX(0);
                    v.setY(0);
                    v.setRotation(0);
                } else if (flag == 1) {
                    onMaxAngleReachedListener.onMaxAngleReached();
//                    parentView.removeView(containerView);
                } else if (flag == 2) {
                    onMaxAngleReachedListener.onMaxAngleReached();
                    //                    parentView.removeView(containerView);
                }
                break;
            default:
                break;
        }
        return true;

    }
}
