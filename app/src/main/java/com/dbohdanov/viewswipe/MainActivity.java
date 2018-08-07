package com.dbohdanov.viewswipe;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewTreeObserver;

public class MainActivity extends AppCompatActivity {
    public static final String TAG = "taag";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        View card = findViewById(R.id.some_card_view);

        card.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                card.getViewTreeObserver().removeOnGlobalLayoutListener(this);

                float pivot_x = card.getWidth() / 2 + card.getX();
                float pivot_y = (5f / 6) * card.getHeight() + card.getY();

                card.setPivotX(pivot_x);
                card.setPivotY(pivot_y);
            }
        });

        card.setOnTouchListener(new RotationListener(RotationListener.POSITION_RIGHT));
    }

}
