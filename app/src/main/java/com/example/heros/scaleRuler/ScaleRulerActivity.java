package com.example.heros.scaleRuler;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.example.heros.R;

public class ScaleRulerActivity extends Activity {

    DecimalScaleRulerView mWeightRulerView;
    TextView mWeightValueTwo;
    private float mWeight = 60.0f;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scale_ruler);

        mWeightRulerView = findViewById(R.id.ruler_weight);
        mWeightValueTwo = findViewById(R.id.tv_user_weight_value_two);

        mWeightRulerView.setParam(DrawUtil.dip2px(20), DrawUtil.dip2px(52), DrawUtil.dip2px(44),
                DrawUtil.dip2px(34), DrawUtil.dip2px(9), DrawUtil.dip2px(20));
        mWeightRulerView.initViewParam(mWeight, 20.0f, 200.0f, 1);
        mWeightRulerView.setValueChangeListener(new DecimalScaleRulerView.OnValueChangeListener() {
            @Override
            public void onValueChange(float value) {
                mWeightValueTwo.setText(value + "kg");
                mWeight = value;
                Log.e("hyw","mWeight:"+mWeight);
            }
        });
    }
}
