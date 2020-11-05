package com.swufe.weaw.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;
//源码来自b站up主写代码的狐狸所发布的教学视频，有修改
public class DBManager {
    public static SQLiteDatabase database;

    public static void initDB(Context context)//初始化数据库信息
    {
        DBHelper dbHelper = new DBHelper(context);
        database = dbHelper.getWritableDatabase();
    }

    public static List<String>queryAllCityName()//查找数据库当中城市列表
    {
        Cursor cursor = database.query("info", null, null, null, null, null,null);
        List<String>cityList = new ArrayList<>();
        while (cursor.moveToNext()) {//把游标查看到的city，加入列表中。主界面调用这个方法可以查看城市列表
            String city = cursor.getString(cursor.getColumnIndex("city"));
            cityList.add(city);
        }
        return cityList;
    }

    public static int updateInfoByCity(String city,String content)//根据城市名称，替换信息内容
    {
        ContentValues values = new ContentValues();
        values.put("content",content);
        return database.update("info",values,"city=?",new String[]{city});
    }

    public static long addCityInfo(String city,String content) //新增一条城市记录
    {
        ContentValues values = new ContentValues();
        values.put("city",city);
        values.put("content",content);
        return database.insert("info",null,values);
    }

    public static String queryInfoByCity(String city)// 根据城市名，查询数据库当中的内容
    {
        Cursor cursor = database.query("info", null, "city=?", new String[]{city}, null, null, null);
        if (cursor.getCount()>0) {
            cursor.moveToFirst();
            String content = cursor.getString(cursor.getColumnIndex("content"));
            return content;
        }
        return null;
    }


    public static List<DatabaseBean>queryAllInfo()//查询数据库当中的全部信息
    {
        Cursor cursor = database.query("info", null, null, null, null, null, null);
        List<DatabaseBean>list = new ArrayList<>();
        while (cursor.moveToNext()) //遍历游标
        {
            int id = cursor.getInt(cursor.getColumnIndex("_id"));
            String city = cursor.getString(cursor.getColumnIndex("city"));
            String content = cursor.getString(cursor.getColumnIndex("content"));
            DatabaseBean bean = new DatabaseBean(id, city, content);
            list.add(bean);
        }
        return list;
    }


    public static int deleteInfoByCity(String city)//根据城市名称，删除这个城市在数据库当中的数据
    {
        return database.delete("info","city=?",new String[]{city});
    }


/*    public static void deleteAllInfo() //删除表当中所有的数据信息
    {
        String sql = "delete from info";
        database.execSQL(sql);
    }*/
}
