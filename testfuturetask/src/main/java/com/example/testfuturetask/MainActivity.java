package com.example.testfuturetask;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

public class MainActivity extends AppCompatActivity {
    public static final String TAG = MainActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        excuteTask();
    }

    private void excuteTask() {
        WorkTask workTask = new WorkTask();
        final FutureTask<Integer> futureTask = new FutureTask<Integer>(workTask) {
            @Override
            protected void done() {
                try {
                    Log.d(TAG, "当前线程----done----" + Thread.currentThread().getName());
                    Log.d(TAG, "done------------------------------");
                    if (isCancelled()) {
                        return;
                    }
                    int result = get();
                    Log.i(TAG, "result..." + result);
                    Thread.currentThread().getName();

                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                }
            }
        };
        Thread thread = new Thread(futureTask);
        thread.start();
        boolean cancel = futureTask.cancel(true);
        Log.d(TAG, "取消结果-------" + cancel);
    }

    class WorkTask implements Callable<Integer> {

        @Override
        public Integer call() throws Exception {
            Log.d(TAG, "当前线程---call-----" + Thread.currentThread().getName());
            Log.d(TAG, "call------------------------------");
            Thread.sleep(5 * 1000);
            return 100;
        }
    }
}
