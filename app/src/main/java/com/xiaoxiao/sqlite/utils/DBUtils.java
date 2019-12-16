package com.xiaoxiao.sqlite.utils;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import com.xiaoxiao.sqlite.App;

import java.util.Date;

/**
 * @author: 潇潇
 * @create on:  2019/12/10
 * @describe:DOTO
 */

public class DBUtils {

    public static SQLiteDatabase database = null;
    private static Context context;


    public DBUtils() {
        openDb(App.getContext());
    }

    public static void openDb(Context con) {
        if (context == null && con != null)
            context = con;
        if (database == null || !database.isOpen())
            database = context.openOrCreateDatabase("Book.db", 0, null);
    }

    public static void close() {
        if (database != null) {
            database.close();
        }
    }


    public boolean hasTable(Context context, String table) {
        String sql = "SELECT COUNT(*) FROM sqlite_master where type='table' and name='" + table + "'";
        if (database == null) {
            openDb(context);
        }
        Cursor cursor = null;
        try {
            cursor = database.rawQuery(sql, null);
            if (cursor.moveToNext()) {
                int count = cursor.getInt(0);
                if (count > 0) {
                    return true;
                }
            }
        } catch (Exception e) {
        } finally {
            if (cursor != null)
                cursor.close();
        }
        return false;
    }

    //创建book表
    public void createBookTable() {
        if (hasTable(context, "book")) {
            return;
        }
        StringBuilder createBook = new StringBuilder("CREATE TABLE book(id integer PRIMARY KEY,name char,")
                .append("createDate date default CURRENT_TIMESTAMP ,editDate date default CURRENT_TIMESTAMP)");
        try {
            database.execSQL(createBook.toString());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void add(String name) {
        ContentValues contentValues = new ContentValues();
        contentValues.put("name", name);
        database.insert("book", null, contentValues);
        database.beginTransaction();
    }

    //查询book表中的数据
    public Cursor queryAllBookData() {
        String sql = "select * from book  order by createDate asc";
        Cursor cursor = database.rawQuery(sql.toString(), null);
        return cursor;
    }


    //修改
    public int edit(int id, String value) {
        ContentValues contentValues = new ContentValues();
        contentValues.put("name", value);
        contentValues.put("editDate", DateUtils.getDate()+"");
        return database.update("book", contentValues, "id=?", new String[]{id + ""});
    }

    //删除
    public int delete(int id) {
        return database.delete("book", "id = ?", new String[]{id + ""});
    }


}
