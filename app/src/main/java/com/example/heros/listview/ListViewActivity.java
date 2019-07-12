package com.example.heros.listview;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;

import com.example.heros.R;

import java.util.ArrayList;
import java.util.List;

public class ListViewActivity extends Activity {
    private List<String> mData;
    private ListView mListView;
    private NotifyAdapter mAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_view);

        mData = new ArrayList<String>();
        for (int i = 0; i < 20; i++) {
            mData.add("" + i);
        }
        mListView = (ListView) findViewById(R.id.listView);
        mAdapter = new NotifyAdapter(this, mData);
        mListView.setAdapter(mAdapter);
        for (int i = 0; i < mListView.getChildCount(); i++) {
            View view = mListView.getChildAt(i);
        }
        mListView.setEmptyView(findViewById(R.id.empty));
        initButton();
    }

    private void initButton() {
        findViewById(R.id.btn_fixlist).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ListViewActivity.this,FlexibleListViewActivity.class));
            }
        });
        findViewById(R.id.btn_headhide).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ListViewActivity.this,ScrollHideListViewActivity.class));
            }
        });
    }


    public void btnAdd(View view) {
//        mData.add("new");
//        mAdapter.notifyDataSetChanged();
//        mListView.setSelection(mData.size() - 1);
//        mListView.setSelection(2);
//        mListView.smoothScrollToPosition(2);
        mData.clear();
        mAdapter.notifyDataSetChanged();
    }
}
