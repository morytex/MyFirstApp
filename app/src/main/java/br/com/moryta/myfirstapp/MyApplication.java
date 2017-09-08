package br.com.moryta.myfirstapp;

import android.app.Application;

import com.crashlytics.android.Crashlytics;

import org.greenrobot.greendao.database.Database;

import br.com.moryta.myfirstapp.model.DaoMaster;
import br.com.moryta.myfirstapp.model.DaoMaster.DevOpenHelper;
import br.com.moryta.myfirstapp.model.DaoSession;
import io.fabric.sdk.android.Fabric;

/**
 * Created by moryta on 08/07/2017.
 */
public class MyApplication extends Application {
    private DaoSession daoSession;

    @Override
    public void onCreate() {
        super.onCreate();

        Fabric.with(this, new Crashlytics());

        DevOpenHelper helper = new DevOpenHelper(this, "myapp-db");
        Database db = helper.getWritableDb();
        daoSession = new DaoMaster(db).newSession();
    }

    /**
     * Get DAO session
     * @return DaoSession
     */
    public DaoSession getDaoSession() {
        return daoSession;
    }
}
