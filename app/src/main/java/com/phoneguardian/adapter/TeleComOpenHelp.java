package com.phoneguardian.adapter;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Administrator on 2016/6/2.
 * SQLite 帮助类
 *
 */
public class TeleComOpenHelp  extends SQLiteOpenHelper{

    public TeleComOpenHelp(Context context) {
        super(context, "blacknumber.db",null, 1);
    }

    //第一次建立的时候调用
    @Override
    public void onCreate(SQLiteDatabase db) {
        // 两个字段number:电话号码, mode: 拦截模式 , 1 拦截电话, 2 拦截短信, 3 拦截全部
        String sql = "create table blacknumber (_id integer primary key autoincrement, number varchar(20), mode integer)";
        db.execSQL(sql);
    }
//    更新2时调用
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
