package com.dongxu.other.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.annotation.Nullable;

/**
 * 类描述：
 *
 * @author zhaishaoping
 * @data 2019-06-18 11:12
 */
public class DBHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "dx_provider.db";
    private static final int VERSION = 1;
    public static final String TABLE_USER = "user";

    public DBHelper(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, DATABASE_NAME, factory, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //创建一个 User表
        db.execSQL(
                "CREATE TABLE IF NOT EXISTS " + TABLE_USER +

                        "(uid INTEGER PRIMARY KEY AUTOINCREMENT," +

                        " name VARCHAR(30)," +

                        "age INTEGER," +

                        "score DOUBLE)"
        );
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }


}
