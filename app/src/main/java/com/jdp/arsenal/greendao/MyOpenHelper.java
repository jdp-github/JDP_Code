package com.jdp.arsenal.greendao;

import android.content.Context;

import org.greenrobot.greendao.database.Database;

/**
 * Describe:
 * Author: jidp
 * Date: 2017/2/15
 */
public class MyOpenHelper extends DaoMaster.OpenHelper {

    public MyOpenHelper(Context context, String name) {
        super(context, name);
    }

    @Override
    public void onUpgrade(Database db, int oldVersion, int newVersion) {
        super.onUpgrade(db, oldVersion, newVersion);
        MigrationHelper.getInstance().migrate(db, UserDao.class);
    }
}
