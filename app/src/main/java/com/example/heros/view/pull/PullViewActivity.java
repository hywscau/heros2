package com.example.heros.view.pull;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.view.KeyEvent;
import android.widget.SimpleAdapter;

import com.example.heros.R;
import com.example.heros.view.pull.xlistview.XListView;

import java.util.ArrayList;
import java.util.HashMap;

public class PullViewActivity extends Activity implements XListView.IXListViewListener {

    private XListView mListView;
    private SimpleAdapter mAdapter1;
    private Handler mHandler;
    private ArrayList<HashMap<String, Object>> dlist;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pull_view);


        /** 下拉刷新，上拉加载 */
        dlist = new ArrayList<HashMap<String, Object>>();
        mListView = (XListView) findViewById(R.id.techan_xListView);// 你这个listview是在这个layout里面
//        mListView.setPullRefreshEnable(false);
        mListView.setPullLoadEnable(true);// 设置让它上拉，FALSE为不让上拉，便不加载更多数据
        mAdapter1 = new SimpleAdapter(PullViewActivity.this, getData(),
                R.layout.list_items, new String[]{"name", "img", "content"},
                new int[]{R.id.title, R.id.mImage, R.id.content});
        mListView.setAdapter(mAdapter1);
        mListView.setXListViewListener(this);
        mHandler = new Handler();

    }

    /**
     * 初始化本地数据
     */
    String data[] = new String[]{"三块石国家森林公园", "关山湖国家水利风景区", "小鹿沟青龙寺景区",
            "天女山风景区", "后安腰堡采摘园"};
    String data1[] = new String[]{"抚顺县救兵乡王木村", "抚顺县救兵乡王木村", "抚顺县救兵乡王木村",
            "抚顺县救兵乡王木村", "抚顺县救兵乡王木村"};

    private ArrayList<HashMap<String, Object>> getData() {
        for (int i = 0; i < data.length; i++) {
            HashMap<String, Object> map = new HashMap<String, Object>();
            map.put("name", data[i]);
            map.put("content", data1[i]);
            map.put("img", R.drawable.ic_launcher);
            dlist.add(map);
        }
        return dlist;
    }

    /**
     * 停止刷新，
     */
    private void onLoad() {
        mListView.stopRefresh();
        mListView.stopLoadMore();
        mListView.setRefreshTime("刚刚");
    }

    // 刷新
    @Override
    public void onRefresh() {
        mHandler.postDelayed(new Runnable() {

            @Override
            public void run() {
                getData();
                mListView.setAdapter(mAdapter1);
                onLoad();
            }
        }, 2000);
    }

    // 加载更多
    @Override
    public void onLoadMore() {
        mHandler.postDelayed(new Runnable() {

            @Override
            public void run() {
                getData();
                mAdapter1.notifyDataSetChanged();
                onLoad();
            }
        }, 2000);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
            this.finish();
        }

        return false;
    }

}
