package com.example.heros.view;

import android.app.Activity;
import android.os.Bundle;

public class CanvasActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(new MyCanvas(this));

    }
}
