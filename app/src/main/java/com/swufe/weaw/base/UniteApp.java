package com.swufe.weaw.base;

import android.app.Application;

import com.swufe.weaw.db.DBManager;

import org.xutils.x;

public class UniteApp extends Application {

    @Override
    public void onCreate() //项目一旦创建就应初始化以下两项，因为uniteapp在manifest中已写明
    {
        super.onCreate();
        x.Ext.init(this);//初始化xutils
        DBManager.initDB(this);//初始化数据库
    }
}
