package com.dbohdanov.viewswipe;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.RelativeLayout;

import com.dbohdanov.viewswipe.rotation.RotationListener;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    CustomAdapter customAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ArrayList<DataModel> leftData = getLeftData();
        ArrayList<DataModel> rightData = getRightData();

        RelativeLayout rlLeft = findViewById(R.id.main_left);
        RelativeLayout rlRight = findViewById(R.id.main_right);

        customAdapter = new CustomAdapter(leftData, rightData, rlLeft, rlRight);
        customAdapter.initAdapter();
    }

    private ArrayList<DataModel> getLeftData() {
        ArrayList<DataModel> dataModelArray = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            dataModelArray.add(new DataModel("left " + i));
        }
        return dataModelArray;
    }

    private ArrayList<DataModel> getRightData() {
        ArrayList<DataModel> dataModelArray = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            dataModelArray.add(new DataModel("right " + i));
        }
        return dataModelArray;
    }
}
