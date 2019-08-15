package com.example.testfactory;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.testfactory.factory.AHumanFactory;
import com.example.testfactory.factory.HumanFactory;
import com.example.testfactory.human.IHuman;
import com.example.testfactory.human.WriteHuman;

public class MainActivity extends AppCompatActivity {

    public static final String TAG = MainActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        AHumanFactory humanFactory = new HumanFactory();
        IHuman human = HumanFactory.createHuman(WriteHuman.class);
        human.getColor();
        human.talk();

        WriteHuman human1 = new WriteHuman();
        human1.getColor();
        human1.talk();


    }
}
