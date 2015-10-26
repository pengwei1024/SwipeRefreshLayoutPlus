package com.apkfuns.swiperefreshlayoutplusdemo;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.apkfuns.swiperefreshlayoutplus.SwipeRefreshLayoutPlus;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements SwipeRefreshLayoutPlus.OnPullRefreshListener,
        SwipeRefreshLayoutPlus.OnPushLoadMoreListener {
    private SwipeRefreshLayoutPlus refreshLayout;
    private ListView listView;
    private List<String> list;
    private ArrayAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        refreshLayout = (SwipeRefreshLayoutPlus) findViewById(R.id.refreshView);
        listView = (ListView) findViewById(R.id.listView);
        list = new ArrayList<>();
        adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, list);
        listView.setAdapter(adapter);
        refreshLayout.setOnPullRefreshListener(this);
        refreshLayout.setOnPushLoadMoreListener(this);
//        refreshLayout.setHeaderViewBackgroundColor(Color.parseColor("#dddddd"));
    }

    private void addData() {
        for (int i = 'A'; i <= 'Z'; i++) {
            list.add(String.valueOf((char) i));
        }
    }

    @Override
    public void onRefresh() {
        list.clear();
        addData();
        adapter.notifyDataSetChanged();
        refreshLayout.setRefreshing(false);
    }

    @Override
    public void onPullDistance(int distance) {

    }

    @Override
    public void onPullEnable(boolean enable) {

    }

    @Override
    public void onLoadMore() {
        addData();
        adapter.notifyDataSetChanged();
        refreshLayout.setLoadMore(false);
    }

    @Override
    public void onPushDistance(int distance) {

    }

    @Override
    public void onPushEnable(boolean enable) {

    }
}

