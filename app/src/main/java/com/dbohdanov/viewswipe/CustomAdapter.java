package com.dbohdanov.viewswipe;

import android.content.Context;
import android.support.annotation.NonNull;
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
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.left_holder, parent, false);
        return new LeftViewHolder(itemView);
    }

    @Override
    public RightViewHolder onCreateRightViewHolder(@NonNull ViewGroup parent) {
        View itemView = LayoutInflater.from(parent.getContext())
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
        TextView textView;

        public RightViewHolder(View rootView) {
            super(rootView, rootView.getContext());
            textView = rootView.findViewById(R.id.right_tv);
        }
    }

    static class LeftViewHolder extends RotationistsAdapter.ViewHolder {
        TextView textView;

        public LeftViewHolder(View rootView) {
            super(rootView, rootView.getContext());
            textView = rootView.findViewById(R.id.left_tv);
        }
    }
}
