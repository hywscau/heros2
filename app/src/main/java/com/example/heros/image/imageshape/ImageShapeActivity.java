package com.example.heros.image.imageshape;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.heros.R;

public class ImageShapeActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_shape);
    }

    public void btnReflect(View view) {
        startActivity(new Intent(this, ReflectViewTest.class));
    }

    public void btnImageMatrix(View view) {
        startActivity(new Intent(this, ImageMatrixTest.class));
    }

    public void btnPorterDuffXfermode(View view) {
        startActivity(new Intent(this, XfermodeViewTest.class));
    }

    public void btnFlag(View view) {
        startActivity(new Intent(this, FlagBitmapMeshTest.class));
    }

    public void btnRoundRect(View view) {
        startActivity(new Intent(this, RoundRectTest.class));
    }
}
