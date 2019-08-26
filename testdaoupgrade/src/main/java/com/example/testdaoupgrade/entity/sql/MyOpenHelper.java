package com.example.testdaoupgrade.entity.sql;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;


import com.example.testdaoupgrade.entity.dao.DaoMaster;

import org.greenrobot.greendao.database.Database;

/**
 * Created by AndroidXJ on 2019/8/23.
 */
public class MyOpenHelper extends DaoMaster.OpenHelper {
    public MyOpenHelper(Context context, String name) {
        super(context, name);
    }

    @Override
    public void onCreate(Database db) {
        super.onCreate(db);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        super.onUpgrade(db, oldVersion, newVersion);
        if (newVersion > oldVersion) {
            UpGradeHelper.getInstance().upgrade(db, this);
        }
    }
}
