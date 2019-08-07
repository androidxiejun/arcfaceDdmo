package com.example.testbaserecyclerview.activity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.example.testbaserecyclerview.R;
import com.example.testbaserecyclerview.adapter.MutipleAdapter;
import com.example.testbaserecyclerview.bean.MutipleData;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by AndroidXJ on 2019/8/7.
 */

public class MultiActivity extends AppCompatActivity {
    private RecyclerView mRecyclerView;
    private MutipleAdapter mAdapter;
    private List<MutipleData> datas = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initData();
        initAdapter();
        initView();
    }

    private void initData() {
        for (int i = 0; i < 15; i++) {
            MutipleData data = new MutipleData();
            data.setItemType(i % 3 == 0 ? 0 : 1);
            data.setBitmap(getResources().getDrawable(R.drawable.ic_launcher_background));
            data.setContent("这是文字-------" + i);
            datas.add(data);
        }
    }

    private void initAdapter() {
        mAdapter = new MutipleAdapter(datas);

    }

    private void initView() {
        mRecyclerView = findViewById(R.id.recycler_view);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setAdapter(mAdapter);
    }
}
