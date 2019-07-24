package com.example.testleakcanary;

import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import java.lang.ref.WeakReference;

public class MainActivity extends AppCompatActivity {

    private Button mBtn;
//    private MyHandler mHandler = new MyHandler(this);


    //使用static可以避免内存泄漏，也会让handler能第一时间被销毁，使用弱引用可以避免内存泄漏，但是需要Handler的任务执行完才将activity回收
    //内部类最好使用static修饰，不然会因为引用有外部类的实例而导致内存泄漏
    private static Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
//        final Handler mHandler = new Handler() {
//            @Override
//            public void handleMessage(Message msg) {
//                super.handleMessage(msg);
//            }
//        };

        mBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mHandler.sendMessageDelayed(Message.obtain(), 10 * 1000);
                finish();
            }
        });
//        new LeakThread().start();

    }

    private void initView() {
        mBtn = findViewById(R.id.btn_finish);
    }


    static class LeakThread extends Thread {
        @Override
        public void run() {
            super.run();
            try {
                Thread.sleep(6 * 60 * 1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        ///手动清除handler的任务
        if (mHandler != null) {
            mHandler.removeCallbacksAndMessages(null);
        }
//        //在Fragment中调用该方法可以监控Fragment的内存泄露现象，Activity中不必刻意去实现
//        RefWatcher refWatcher = LeakApplication.getLeak(getApplicationContext());
//        refWatcher.watch(this);

    }

    private static class MyHandler extends Handler {
        private final WeakReference<MainActivity> mActivity;

        private MyHandler(MainActivity activity) {
            mActivity = new WeakReference<MainActivity>(activity);
        }

        @Override
        public void handleMessage(Message msg) {
            if (mActivity.get() != null) {
                //doSomething
            }
        }
    }
}
