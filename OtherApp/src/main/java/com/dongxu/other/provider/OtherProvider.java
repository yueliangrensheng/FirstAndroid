package com.dongxu.other.provider;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;

import com.dongxu.other.db.DBHelper;

/**
 * 类描述：
 *
 * @author zhaishaoping
 * @data 2019-06-18 11:09
 */
public class OtherProvider extends ContentProvider {

    private DBHelper mDBHelper;
    SQLiteDatabase db;

    private static final UriMatcher sMatcher;
    private static final String AUTHORITY_PROVIDER = "com.dx.provider.authority";

    private static final int CODE_PROVIDER_USER = 1;


    static {
        sMatcher = new UriMatcher(UriMatcher.NO_MATCH);
        sMatcher.addURI(AUTHORITY_PROVIDER, DBHelper.TABLE_USER, CODE_PROVIDER_USER);
    }

    @Override
    public boolean onCreate() {
        //在 ContentProvider 创建时候，初始化Database，运行在UI线程
        mDBHelper = new DBHelper(getContext(), "", null, 1);
        db = mDBHelper.getWritableDatabase();

        //清空数据；再插入两条记录
        db.execSQL("delete from user");
        db.execSQL("insert into user values(1,'moon',18,80.5)");
        db.execSQL("insert into user values(2,'life',20,90.1)");

        return true;
    }

    public String getTableName(Uri uri) {
        String tableName = "";
        switch (sMatcher.match(uri)) {
            case CODE_PROVIDER_USER:
                tableName = DBHelper.TABLE_USER;
                break;
        }

        return tableName;
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        return null;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String selection, @Nullable String[] selectionArgs, @Nullable String sortOrder) {
        String tableName = getTableName(uri);
        if (TextUtils.isEmpty(tableName)) {
            return null;
        }

        Cursor cursor = db.query(tableName, projection, selection, selectionArgs, null, null, sortOrder, null);
        return cursor;
    }


    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues values) {
        String tableName = getTableName(uri);
        if (TextUtils.isEmpty(tableName)) {
            return null;
        }

        long insert = db.insert(tableName, null, values);
        if (insert > 0) {
            //该URI的 ContentProvider数据发生变化时，通知外界的访问者
            getContext().getContentResolver().notifyChange(uri, null);
        }
        return uri;
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String selection, @Nullable String[] selectionArgs) {
        String tableName = getTableName(uri);
        if (TextUtils.isEmpty(tableName)) {
            return 0;
        }
        int delete = db.delete(tableName, selection, selectionArgs);
        if (delete > 0){
            getContext().getContentResolver().notifyChange(uri, null);
        }

        return delete;
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues values, @Nullable String selection, @Nullable String[] selectionArgs) {
        String tableName = getTableName(uri);
        if (TextUtils.isEmpty(tableName)) {
            return 0;
        }

        int update = db.update(tableName, values, selection, selectionArgs);
        if (update > 0){
            getContext().getContentResolver().notifyChange(uri, null);
        }
        return update;
    }
}
