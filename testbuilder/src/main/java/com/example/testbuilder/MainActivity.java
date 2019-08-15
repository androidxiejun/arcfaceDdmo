package com.example.testbuilder;

import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.example.testbuilder.builder.AdultBuilder;
import com.example.testbuilder.builder.ChildBuilder;
import com.example.testbuilder.director.SuperManDirector;
import com.example.testbuilder.superman.SuperMan;

public class MainActivity extends AppCompatActivity {
    public static final String TAG = MainActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SuperMan adultMan = SuperManDirector.getAdultMan();
        Log.d(TAG, "body---------------" + adultMan.getBody());
        Log.d(TAG, "symbol---------------" + adultMan.getSymbol());
        Log.d(TAG, "talent---------------" + adultMan.getTalent());

        SuperMan childMan = SuperManDirector.getChildMan();
        Log.d(TAG, "body---------------" + childMan.getBody());
        Log.d(TAG, "symbol---------------" + childMan.getSymbol());
        Log.d(TAG, "talent---------------" + childMan.getTalent());

    }
}
