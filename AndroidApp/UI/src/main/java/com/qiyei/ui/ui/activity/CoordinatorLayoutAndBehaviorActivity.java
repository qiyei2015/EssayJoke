package com.qiyei.ui.ui.activity;

import android.os.Bundle;
import android.view.MotionEvent;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.qiyei.ui.R;

public class CoordinatorLayoutAndBehaviorActivity extends AppCompatActivity {


    ViewGroup.MarginLayoutParams mParams;
    TextView tv2;
    int top = 0;
    int y1 = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_coordinator_layout_and_behavior);

        tv2 = findViewById(R.id.tv_2);
        mParams = (ViewGroup.MarginLayoutParams) tv2.getLayoutParams();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                top = tv2.getTop();
                y1 = (int) event.getY();
                break;
            case MotionEvent.ACTION_MOVE:
                int y2 = (int) event.getY();
                mParams.topMargin = top + (y2 - y1);
                tv2.setLayoutParams(mParams);
                break;
            default:
                break;
        }
        return super.onTouchEvent(event);
    }
}
