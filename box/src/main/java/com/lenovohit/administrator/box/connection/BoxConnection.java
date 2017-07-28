package com.lenovohit.administrator.box.connection;

import android.database.sqlite.SQLiteDatabase;

import com.lenovohit.administrator.box.parser.BoxAttrs;
import com.lenovohit.administrator.box.tablemanager.BoxOpenHelper;

/**
 * Created by SharkChao on 2017-07-26.
 * 用来连接数据库，获取可读或者可写数据库
 */

public class BoxConnection {

    private static BoxOpenHelper sBoxOpenHelper;
    /**
     * 获取可写数据库
     * @return
     */
    private synchronized static SQLiteDatabase getWriteDatabase(){
        BoxOpenHelper boxOpenHelper = buildHelper();
        return boxOpenHelper.getWritableDatabase();
    }

    /**
     * 获取可读数据库，放弃这个方法，一般使用可写数据库
     * @return
     */
    private synchronized  static SQLiteDatabase getReadDatabase(){
        BoxOpenHelper boxOpenHelper = buildHelper();
        return boxOpenHelper.getReadableDatabase();
    }

    /**
     * 获取sqlite数据库，供外部调用
     * @return
     */
    public static SQLiteDatabase getDatabase(){
        return BoxConnection.getWriteDatabase();
    }
    private static BoxOpenHelper buildHelper(){
        BoxAttrs attrs = BoxAttrs.getInstance();
        attrs.checkSelfValid();
        if (sBoxOpenHelper == null){
            String dbname = attrs.getDbName();
        /*  /*  if (Constact.Config.CONFIG_DB_EXTERNAL.equals(attrs.getStorage())){
                dbname = BoxApplication.getContext().getExternalFilesDir("")+"/databases/"+dbname;
            }else if (Constact.Config.CONFIG_DB_INTERNAL.equals(attrs.getStorage())){
                String dbpath = Environment.getExternalStorageDirectory().getPath()+"/"+attrs.getStorage();
                dbpath = dbpath.replace("//","/");
                if (BaseUtil.isExistClassAndMethod("android.support.v4.content.ContextCompat", "checkSelfPermission") &&
                        ContextCompat.checkSelfPermission(BoxApplication.getContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                    throw new DatabaseGenerateException(DatabaseGenerateException.NOT_DATA_PERMISSION);
                }
                File file = new File(dbpath);
                if (!file.exists()){
                    file.mkdir();
                }
                dbname = dbpath+"/"+dbname;
            }*/
            sBoxOpenHelper = new BoxOpenHelper(dbname,attrs.getVersion());
        }
        return sBoxOpenHelper;
    }
}
