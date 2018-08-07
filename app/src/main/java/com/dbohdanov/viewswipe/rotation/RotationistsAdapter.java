package com.dbohdanov.viewswipe.rotation;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.RelativeLayout;

import java.util.ArrayList;

/**
 * adapter for creating 2-blocks view set with ability to delete items
 */
public abstract class RotationistsAdapter<VHR extends RotationistsAdapter.ViewHolder, VHL extends RotationistsAdapter.ViewHolder> {
    //left and right layouts
    private RelativeLayout layoutLeft;
    private RelativeLayout rightLayout;

    //views: 2 for each block
    //one is front (see always)
    //second one is bac (see when rotating front one)
    private VHL leftViewFront;
    private VHL leftViewBack;
    private VHR rightViewFront;
    private VHR rightViewBack;

    private int rightItemsCount;
    private int leftItemsCount;

    private int currentLeftPosition;
    private int currentRightPosition;


    @SuppressLint("ClickableViewAccessibility")
    public RotationistsAdapter(RelativeLayout layoutLeft, RelativeLayout layoutRight) {
        this.layoutLeft = layoutLeft;
        this.rightLayout = layoutRight;

        //getting size of data arrays
        leftItemsCount = getLeftItemsCount();
        rightItemsCount = getRightItemsCount();

        //creating views
        leftViewFront = onCreateLeftViewHolder(layoutLeft);
        leftViewBack = onCreateLeftViewHolder(layoutLeft);

        rightViewBack = onCreateRightViewHolder(layoutRight);
        rightViewFront = onCreateRightViewHolder(layoutRight);

        //adding views to layouts
        layoutLeft.addView(leftViewBack);
        layoutLeft.addView(leftViewFront);

        layoutRight.addView(rightViewBack);
        layoutRight.addView(rightViewFront);

        //setting rotate listeners
        leftViewFront.setOnTouchListener(new RotationListener(RotationListener.POSITION_LEFT, this::popLeft));
        leftViewFront.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                leftViewFront.getViewTreeObserver().removeOnGlobalLayoutListener(this);

                float pivot_x = leftViewFront.getWidth() / 2 + leftViewFront.getX();
                float pivot_y = (5f / 6) * leftViewFront.getHeight() + leftViewFront.getY();

                leftViewFront.setPivotX(pivot_x);
                leftViewFront.setPivotY(pivot_y);
            }
        });

        rightViewFront.setOnTouchListener(new RotationListener(RotationListener.POSITION_RIGHT, this::popRight));
        rightViewFront.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                rightViewFront.getViewTreeObserver().removeOnGlobalLayoutListener(this);

                float pivot_x = rightViewFront.getWidth() / 2 + rightViewFront.getX();
                float pivot_y = (5f / 6) * rightViewFront.getHeight() + rightViewFront.getY();

                rightViewFront.setPivotX(pivot_x);
                rightViewFront.setPivotY(pivot_y);
            }
        });

        currentLeftPosition = 0;
        currentRightPosition = 0;

        setLeftAccordingToCurrentPosition();
        setRightAccordingToCurrentPosition();


    }

    private void setRightAccordingToCurrentPosition() {
        if (currentRightPosition == (rightItemsCount - 1)) {
            rightViewFront.setVisibility(View.INVISIBLE);
            rightViewBack.setVisibility(View.INVISIBLE);
        } else if (currentRightPosition == (rightItemsCount - 2)) {
            rightViewFront.setVisibility(View.VISIBLE);
            rightViewBack.setVisibility(View.INVISIBLE);
            onBindRightViewHolder(rightViewFront, currentLeftPosition);
        } else {
            rightViewFront.setVisibility(View.VISIBLE);
            rightViewBack.setVisibility(View.VISIBLE);
            onBindRightViewHolder(rightViewFront, currentLeftPosition);
            onBindRightViewHolder(rightViewBack, currentLeftPosition + 1);
        }
    }

    private void setLeftAccordingToCurrentPosition() {
        if (currentLeftPosition == (leftItemsCount - 1)) {
            leftViewFront.setVisibility(View.INVISIBLE);
            leftViewBack.setVisibility(View.INVISIBLE);
        } else if (currentLeftPosition == (leftItemsCount - 2)) {
            leftViewFront.setVisibility(View.VISIBLE);
            leftViewBack.setVisibility(View.INVISIBLE);
            onBindLeftViewHolder(leftViewFront, currentLeftPosition);
        } else {
            leftViewFront.setVisibility(View.VISIBLE);
            leftViewBack.setVisibility(View.VISIBLE);
            onBindLeftViewHolder(leftViewFront, currentLeftPosition);
            onBindLeftViewHolder(leftViewBack, currentLeftPosition + 1);
        }
    }

    /**
     * todo
     */
    public abstract VHL onCreateLeftViewHolder(@NonNull ViewGroup parent);

    /**
     * todo
     */
    public abstract VHR onCreateRightViewHolder(@NonNull ViewGroup parent);


    /**
     * removes left item
     */
    private void popLeft() {
        leftItemsCount--;
        currentLeftPosition++;
        setLeftAccordingToCurrentPosition();
    }

    /**
     * removes right item
     */
    private void popRight() {
        rightItemsCount--;
        currentRightPosition++;
        setRightAccordingToCurrentPosition();
    }


    /**
     * @return number of items in data array for right column
     */
    public abstract int getRightItemsCount();

    /**
     * @return number of items in data array for left column
     */
    public abstract int getLeftItemsCount();

    /**
     * action on creating view holder with specific data
     */
    public abstract void onBindRightViewHolder(VHR rightViewHolder, int position);

    /**
     * action on creating view holder with specific data
     */
    public abstract void onBindLeftViewHolder(VHL leftViewHolder, int position);


    public static abstract class ViewHolder extends View {
        public ViewHolder(View rootView, Context context) {
            super(context);
        }
    }
}
