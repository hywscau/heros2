package com.example.heros.image;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;

import com.example.heros.R;

public class ImageActivity extends Activity {

    ImageView iv_image;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image);
        iv_image = findViewById(R.id.iv_image);
    }

    public void btnPixelsEffect(View view) {
//        startActivity(new Intent(this, PixelsEffect.class));
        iv_image.setVisibility(View.GONE);
    }

    public void btnColorMatrix(View view) {
//        startActivity(new Intent(this, ColorMatrix.class));
        Animation rotateAnimation  = new RotateAnimation(0f, 360f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        rotateAnimation.setFillAfter(true);
        rotateAnimation.setDuration(500);
        rotateAnimation.setRepeatCount(0);
        rotateAnimation.setInterpolator(new LinearInterpolator());
        iv_image.startAnimation(rotateAnimation);

    }

    public void btnPrimaryColor(View view) {
        startActivity(new Intent(this, PrimaryColor.class));
    }
}
