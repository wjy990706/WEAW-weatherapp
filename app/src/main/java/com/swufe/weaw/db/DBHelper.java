package com.swufe.weaw.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelper extends SQLiteOpenHelper{

    public DBHelper(Context context){
        super(context,"forecast.db",null,1);
    }//游标，版本号
    @Override
    public void onCreate(SQLiteDatabase db) {
//    创建表
        String sql = "create table info(_id integer primary key autoincrement,city varchar(20) unique not null,content text not null)";//表名info，id整型主键自增长，城市唯一非空，城市信息非空
        db.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
