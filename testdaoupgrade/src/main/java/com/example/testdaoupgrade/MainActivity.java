package com.example.testdaoupgrade;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.example.testdaoupgrade.entity.bean.TestUpdate2;
import com.example.testdaoupgrade.entity.bean.TestUpgrade;
import com.example.testdaoupgrade.entity.sql.GreendaoUtil;
import com.example.testdaoupgrade.entity.sql.UpGradeHelper;

import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    public static final String TAG = MainActivity.class.getSimpleName();
    private Button mBtnAdd, mBtnLoad;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        GreendaoUtil.getInstance().init(this);
    }

    private void initView() {
        mBtnAdd = findViewById(R.id.btn_add);
        mBtnLoad = findViewById(R.id.btn_load);
        mBtnAdd.setOnClickListener(this);
        mBtnLoad.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_add:
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        TestUpdate2 testUpdate2 = new TestUpdate2();
                        testUpdate2.setData("张三" + System.currentTimeMillis());
                        testUpdate2.setData2("李四" + System.currentTimeMillis());
                        GreendaoUtil.getInstance().getSession().getTestUpdate2Dao().save(testUpdate2);

                        TestUpgrade testUpgrade = new TestUpgrade();
                        testUpgrade.setName(System.currentTimeMillis() + "");
                        testUpgrade.setAddress("浙江-------");
                        testUpgrade.setScore("55");
                        GreendaoUtil.getInstance().getSession().getTestUpgradeDao().save(testUpgrade);
                    }
                }).start();
                break;
            case R.id.btn_load:
                List<TestUpgrade> testUpgrades = GreendaoUtil.getInstance().getSession().getTestUpgradeDao().loadAll();
                Log.d(TAG, "数据大小---------" + testUpgrades.size());
                for (TestUpgrade testUpgrade1 : testUpgrades) {
                    Log.d(TAG, "id---------------------" + testUpgrade1.getId());
                    Log.d(TAG, "name-------------------" + testUpgrade1.getName());
                    Log.d(TAG, "score-------------------" + testUpgrade1.getScore());
                }

                List<TestUpdate2> testUpdate2s = GreendaoUtil.getInstance().getSession().getTestUpdate2Dao().loadAll();
                if (testUpdate2s != null && testUpdate2s.size() > 0) {
                    for (TestUpdate2 testUpdate2 : testUpdate2s) {
                        Log.d(TAG, "id---------------------" + testUpdate2.getId());
                        Log.d(TAG, "name-------------------" + testUpdate2.getData());
                    }
                }
                break;
        }
    }
}
