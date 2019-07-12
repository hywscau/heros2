package com.example.heros.image.imageshape;

import android.app.Activity;
import android.os.Bundle;

public class ReflectViewTest extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(new ReflectView(this));
    }
}
