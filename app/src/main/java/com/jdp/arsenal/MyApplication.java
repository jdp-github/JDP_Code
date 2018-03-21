package com.jdp.arsenal;

import android.app.Application;

import com.jdp.arsenal.greendao.DaoSession;

/**
 * Describe:
 * Author: jidp
 * Date: 2017/2/10
 */
public class MyApplication extends Application {

    public static final boolean ENCRYPTED = false;

    protected static MyApplication instance;

    public static MyApplication getInstance() {
        return instance;
    }

    private DaoSession daoSession;

    public DaoSession getDaoSession() {
        return daoSession;
    }

    @Override
    public void onCreate() {
        super.onCreate();

        instance = this;

//        MyOpenHelper helper = new MyOpenHelper(this, ENCRYPTED ? "notes-db-encrypted.db" : "test-db.db");
//        Database db = ENCRYPTED ? helper.getEncryptedWritableDb("super-secret") : helper.getWritableDb();
//        daoSession = new DaoMaster(db).newSession();
    }
}
