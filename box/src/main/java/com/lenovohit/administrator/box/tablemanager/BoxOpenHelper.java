package com.lenovohit.administrator.box.tablemanager;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.lenovohit.administrator.box.application.BoxApplication;
import com.lenovohit.administrator.box.parser.BoxAttrs;

import java.lang.reflect.Field;
import java.util.List;

/**
 * Created by SharkChao on 2017-07-27.
 */

public class BoxOpenHelper extends SQLiteOpenHelper{

    public BoxOpenHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }
    public BoxOpenHelper(String dbname,int version){
        this(BoxApplication.getContext(),dbname,null,version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        parseTables(BoxAttrs.getInstance().getClassNames(),db);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
    private static void parseTables(List<String> classNames,SQLiteDatabase db){
        for (String className:classNames)
            try {
                Class<?> aClass = Class.forName(className);
                Field[] fields = aClass.getDeclaredFields();
                String sql = "";
                for (Field field : fields) {
                    sql = sql + "," + field.getName() + " varchar(20) ";
                }
                db.execSQL("create table " + aClass.getSimpleName() + "( id integer  primary key autoincrement " + sql + " )");
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
    }
}
