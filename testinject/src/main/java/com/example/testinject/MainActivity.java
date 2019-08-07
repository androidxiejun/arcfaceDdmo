package com.example.testinject;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    public static final String TAG = MainActivity.class.getSimpleName();

    @FindViewById(R.id.btn)
    Button mBtn;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        InjectUtil.inject(this);
//        mTxt.setText("1111111111111");
    }

    @OnClick(R.id.btn)
    public void onClick() {
        Log.d(TAG, "按钮被点击了-----------");
    }
}
