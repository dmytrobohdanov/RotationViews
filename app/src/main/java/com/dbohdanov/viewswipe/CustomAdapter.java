package com.dbohdanov.viewswipe;

import android.support.annotation.NonNull;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.dbohdanov.viewswipe.rotation.RotationistsAdapter;

import java.util.ArrayList;

/**
 *
 */
public class CustomAdapter extends RotationistsAdapter<CustomAdapter.RightViewHolder, CustomAdapter.LeftViewHolder> {
    private final String TAG = getClass().getSimpleName() + "taag";

    private final ArrayList<DataModel> leftDataArray;
    private final ArrayList<DataModel> rigthDataArray;

    public CustomAdapter(ArrayList<DataModel> leftDataArray, ArrayList<DataModel> rigthDataArray,
                         RelativeLayout layoutLeft, RelativeLayout layoutRight) {
        super(layoutLeft, layoutRight);

        this.leftDataArray = leftDataArray;
        this.rigthDataArray = rigthDataArray;
    }


    @Override
    public LeftViewHolder onCreateLeftViewHolder(@NonNull ViewGroup parent) {
        View itemView = LayoutInflater.from(parent.getContext().getApplicationContext())
                .inflate(R.layout.left_holder, parent, false);
        return new LeftViewHolder(itemView);
    }

    @Override
    public RightViewHolder onCreateRightViewHolder(@NonNull ViewGroup parent) {
        View itemView = LayoutInflater.from(parent.getContext().getApplicationContext())
                .inflate(R.layout.right_holder, parent, false);
        return new RightViewHolder(itemView);
    }


    @Override
    public void onBindRightViewHolder(RightViewHolder rightViewHolder, int position) {
        rightViewHolder.textView.setText(rigthDataArray.get(position).getText());
    }

    @Override
    public void onBindLeftViewHolder(LeftViewHolder leftViewHolder, int position) {
        leftViewHolder.textView.setText(leftDataArray.get(position).getText());
    }


    @Override
    public int getRightItemsCount() {
        return rigthDataArray.size();
    }

    @Override
    public int getLeftItemsCount() {
        return leftDataArray.size();
    }


    static class RightViewHolder extends RotationistsAdapter.ViewHolder {
        View rootView;
        TextView textView;

        public RightViewHolder(View rootView) {
            this.rootView = rootView;
            textView = rootView.findViewById(R.id.right_tv);
        }

        @Override
        public void showView() {
            rootView.setVisibility(View.VISIBLE);
        }

        @Override
        public void hideView() {
            rootView.setVisibility(View.INVISIBLE);
        }

        @Override
        public View getRootView() {
            return rootView;
        }
    }

    static class LeftViewHolder extends RotationistsAdapter.ViewHolder {
        View rootView;
        TextView textView;

        public LeftViewHolder(View rootView) {
            this.rootView = rootView;
            textView = rootView.findViewById(R.id.left_tv);
        }

        @Override
        public void showView() {
            rootView.setVisibility(View.VISIBLE);
        }

        @Override
        public void hideView() {
            rootView.setVisibility(View.INVISIBLE);
        }

        @Override
        public View getRootView() {
            return rootView;
        }
    }
}
