package com.example.testbaserecyclerview.activity;

import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.example.testbaserecyclerview.R;
import com.example.testbaserecyclerview.adapter.ReadIndexAdapter;
import com.example.testbaserecyclerview.bean.WorkInfo;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    public static final String TAG = MainActivity.class.getSimpleName();
    private RecyclerView mRecyclerView;
    private ReadIndexAdapter mAdapter;
    private List<WorkInfo> mDatas = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initData();
        initAdapter();
        initView();
    }

    private void initData() {
        for (int i = 0; i < 10; i++) {
            WorkInfo workInfo = new WorkInfo();
            workInfo.setTitle("Titlte--------" + i);
            workInfo.setDescription("Description--------" + i);
            mDatas.add(workInfo);
        }
    }

    private void initAdapter() {
        mAdapter = new ReadIndexAdapter(mDatas);
        //添加头部信息
        mAdapter.addHeaderView(getHeaderView());
        //添加尾部信息
        mAdapter.setFooterView(getHeaderView());
        //设置动画
        mAdapter.openLoadAnimation(BaseQuickAdapter.ALPHAIN);

        mAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                Log.d(TAG, "cLick-------position-------==" + position);
            }
        });

        mAdapter.setOnItemLongClickListener(new BaseQuickAdapter.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(BaseQuickAdapter adapter, View view, int position) {
                Log.d(TAG, "longCLick-------position-------==" + position);

                return false;
            }
        });
    }

    private void initView() {
        mRecyclerView = findViewById(R.id.recycler_view);
        //设置布局管理者
//        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setLayoutManager(new GridLayoutManager(this, 3));
        mRecyclerView.setAdapter(mAdapter);
    }

    private View getHeaderView() {
        View headView = LayoutInflater.from(this).inflate(R.layout.layout_header, null);
        headView.setLayoutParams(new DrawerLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 50));
        return headView;
    }
}
