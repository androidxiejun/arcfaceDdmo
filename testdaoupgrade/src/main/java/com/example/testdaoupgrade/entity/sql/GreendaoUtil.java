package com.example.testdaoupgrade.entity.sql;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.example.testdaoupgrade.entity.dao.DaoMaster;
import com.example.testdaoupgrade.entity.dao.DaoSession;


/**
 * Created by AndroidXJ on 2019/8/23.
 */
public class GreendaoUtil {
    private static GreendaoUtil sInstance;
    private Context mContext;
    private DaoSession mDaoSession;
    private DaoMaster.OpenHelper mOpenHelper;
    private DaoMaster mDaoMaster;
    private SQLiteDatabase mDatabase;

    public static GreendaoUtil getInstance() {
        synchronized (GreendaoUtil.class) {
            if (null == sInstance) {
                sInstance = new GreendaoUtil();
            }
            return sInstance;
        }
    }

    public void init(Context context) {
        mContext = context.getApplicationContext();
        mOpenHelper = new MyOpenHelper(mContext, "greeddao.db");
        mDatabase = mOpenHelper.getWritableDatabase();
        mDaoMaster = new DaoMaster(mDatabase);
        mDaoSession = mDaoMaster.newSession();
    }

    public DaoSession getSession() {
        return mDaoSession;
    }
}
