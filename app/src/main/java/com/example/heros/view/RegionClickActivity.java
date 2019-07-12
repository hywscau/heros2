package com.example.heros.view;

import android.app.Activity;
import android.os.Bundle;

public class RegionClickActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(new RemoteControlMenu(this));
    }
}
