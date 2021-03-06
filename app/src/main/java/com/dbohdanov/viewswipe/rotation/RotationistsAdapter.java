package com.dbohdanov.viewswipe.rotation;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.RelativeLayout;

/**
 * adapter for creating 2-blocks view set with ability to delete items
 */
public abstract class RotationistsAdapter<VHR extends RotationistsAdapter.ViewHolder, VHL extends RotationistsAdapter.ViewHolder> {
    private final String TAG = getClass().getSimpleName() + "taag";

    //left and right layouts
    private RelativeLayout layoutLeft;
    private RelativeLayout layoutRight;

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


    public RotationistsAdapter(RelativeLayout layoutLeft, RelativeLayout layoutRight) {
        this.layoutLeft = layoutLeft;
        this.layoutRight = layoutRight;
    }

    /**
     * must be called after constructor
     */
    @SuppressLint("ClickableViewAccessibility")
    public void initAdapter() {
        //getting size of data arrays
        leftItemsCount = getLeftItemsCount();
        rightItemsCount = getRightItemsCount();

        //creating views
        leftViewFront = onCreateLeftViewHolder(layoutLeft);
        leftViewBack = onCreateLeftViewHolder(layoutLeft);

        rightViewBack = onCreateRightViewHolder(layoutRight);
        rightViewFront = onCreateRightViewHolder(layoutRight);

        //adding views to layouts
        layoutLeft.addView(leftViewBack.getRootView());
        layoutLeft.addView(leftViewFront.getRootView());

        layoutRight.addView(rightViewBack.getRootView());
        layoutRight.addView(rightViewFront.getRootView());

        //setting rotate listeners
        leftViewFront.getRootView().setOnTouchListener(new RotationListener(RotationListener.POSITION_LEFT, this::popLeft));
        rightViewFront.getRootView().setOnTouchListener(new RotationListener(RotationListener.POSITION_RIGHT, this::popRight));

        setPivotToView(rightViewFront.getRootView());
        setPivotToView(leftViewFront.getRootView());

        currentLeftPosition = 0;
        currentRightPosition = 0;

        setLeftAccordingToCurrentPosition();
        setRightAccordingToCurrentPosition();

    }

    private void setRightAccordingToCurrentPosition() {
        if (currentRightPosition == rightItemsCount) {
            rightViewFront.hideView();
            rightViewBack.hideView();
        } else if (currentRightPosition == (rightItemsCount - 1)) {
            rightViewFront.showView();
            rightViewBack.hideView();
            onBindRightViewHolder(rightViewFront, currentRightPosition);
        } else {
            rightViewFront.showView();
            rightViewBack.showView();
            onBindRightViewHolder(rightViewFront, currentRightPosition);
            onBindRightViewHolder(rightViewBack, currentRightPosition + 1);
        }
    }

    private void setLeftAccordingToCurrentPosition() {
        if (currentLeftPosition == (leftItemsCount)) {
            leftViewFront.hideView();
            leftViewBack.hideView();
        } else if (currentLeftPosition == (leftItemsCount - 1)) {
            leftViewFront.showView();
            leftViewBack.hideView();
            onBindLeftViewHolder(leftViewFront, currentLeftPosition);
        } else {
            leftViewFront.showView();
            leftViewBack.showView();
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
        currentLeftPosition++;
        leftViewFront.hideView();
        setDefaultPosition(leftViewFront.getRootView());
        setLeftAccordingToCurrentPosition();
    }

    /**
     * removes right item
     */
    private void popRight() {
        currentRightPosition++;
        rightViewFront.hideView();
        setDefaultPosition(rightViewFront.getRootView());
        setRightAccordingToCurrentPosition();
    }

    private void setDefaultPosition(View view) {
        view.setRotation(0);
    }

    private void setPivotToView(View view) {
        view.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                view.getViewTreeObserver().removeOnGlobalLayoutListener(this);

                float pivot_x = view.getWidth() / 2 + view.getX();
                float pivot_y = (5f / 6) * view.getHeight() + view.getY();

                view.setPivotX(pivot_x);
                view.setPivotY(pivot_y);
            }
        });
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


    public static abstract class ViewHolder {
        public abstract void showView();

        public abstract void hideView();

        public abstract View getRootView();
    }
}
