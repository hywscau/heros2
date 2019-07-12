package com.example.heros.listview;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ArrayAdapter;

import com.example.heros.R;

public class FlexibleListViewActivity extends Activity {

    private FlexibleListView mFlexibleListView;
    private String[] data = new String[30];
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_flexible_list_view);

        for (int i = 0; i < 30; i++) {
            data[i] = "" + i;
        }
        mFlexibleListView = (FlexibleListView) findViewById(R.id.flexible_listview);
        mFlexibleListView.setAdapter(new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1,
                data));
    }
}
