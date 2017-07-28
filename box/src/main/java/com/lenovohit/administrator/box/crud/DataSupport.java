package com.lenovohit.administrator.box.crud;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.lenovohit.administrator.box.connection.BoxConnection;

import java.lang.reflect.Field;
import java.util.Collection;

/**
 * Created by SharkChao on 2017-07-27.
 */

public class DataSupport {
    public long id;
    /**
     * 保存数据
     * @return
     */
    private long  save(){
        SQLiteDatabase db = BoxConnection.getDatabase();
        Class<? extends DataSupport> aClass = this.getClass();
        Field[] fields = aClass.getDeclaredFields();
        ContentValues contentValues = new ContentValues();
        for (int i = 0;i<fields.length;i++){
            Field field =fields[i];
            field.setAccessible(true);
            try {
                contentValues.put(field.getName(),field.get(this)+"");
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        long insert = db.insert(aClass.getSimpleName(), null, contentValues);
        id = insert;
        return insert;
    }



    /**
     * 保存数据集合
     */
   public static  <T extends DataSupport> void saveAll(Collection<T>collection){
       SQLiteDatabase db = BoxConnection.getDatabase();
       if (collection!=null && collection.size()>0){
           DataSupport[] array = collection.toArray(new DataSupport[0]);
           DataSupport object = array[0];
           Class<?> aClass = object.getClass();
           String tableName = aClass.getSimpleName();
           Field[] fields = aClass.getDeclaredFields();
           for (int j=0;j<array.length;j++){
               ContentValues contentValues = new ContentValues();
               DataSupport obj = array[j];
               for (int i = 0;i<fields.length;i++){
                   Field field = fields[i];
                   field.setAccessible(true);
                   try {
                       String columnName = field.getName();
                       String value = (String) field.get(obj);
                       contentValues.put(columnName,value);
                   } catch (IllegalAccessException e) {
                       e.printStackTrace();
                   }
               }
               long insert = db.insert(tableName, null, contentValues);
               obj.id = insert;
           }
       }
    }
    /**
     * 删除一条数据
     */
    public void delete(){
        SQLiteDatabase db = BoxConnection.getDatabase();
        Class<? extends DataSupport> aClass = this.getClass();
        String tableName = aClass.getSimpleName();
        int delete = db.delete(tableName, " id = ?", new String[]{this.id + ""});
        Log.e("tag",delete+"..........");
        id=0;
    }
    /**
     * 删除所有数据
     */
    public static void deleteAll(Class<?>aClass){
        SQLiteDatabase db = BoxConnection.getDatabase();
        String tableName = aClass.getSimpleName();
        db.delete(tableName,null,null);
    }
    /**
     * 根据条件删除相应的数据
     */
    public static void deleteAll(Class<?>aClass,String where,String[]string){
        SQLiteDatabase db = BoxConnection.getDatabase();
        String tableName = aClass.getSimpleName();
        db.delete(tableName,where,string);
    }
    /**
     * 修改一条数据
     */
    public void update(){
        SQLiteDatabase db = BoxConnection.getDatabase();
        Class<? extends DataSupport> aClass = this.getClass();
        Class<?> superclass = aClass.getSuperclass();
        String tableName = aClass.getSimpleName();
        Field[] fields = aClass.getDeclaredFields();
        ContentValues contentValues = new ContentValues();
        for (Field field:fields){
            field.setAccessible(true);
            try {
                if (field.getName().equals("id")){
                    contentValues.put(field.getName(), (Long) superclass.getDeclaredField("id").get(this));
                }else {
                    contentValues.put(field.getName(), (String) field.get(this));
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        db.update(tableName,contentValues," id = ?",new String[]{this.id+""});
    }
    /**
     * 修改数据集合
     */
    public static <T extends DataSupport> void updateAll(Collection<T>collection){
        SQLiteDatabase db = BoxConnection.getDatabase();
        DataSupport[] array = collection.toArray(new DataSupport[0]);
        DataSupport dataSupport = array[0];
        Class<? extends DataSupport> aClass = dataSupport.getClass();
        String tableName = aClass.getSimpleName();
        for (int i = 0;i<array.length;i++){
            DataSupport support = array[i];
            Class<?> superclass = support.getClass().getSuperclass();
            Class<? extends DataSupport> cls = support.getClass();
            Field[] fields = cls.getDeclaredFields();
            ContentValues contentValues = new ContentValues();
            long mId = 0;
            for (Field field:fields){
                field.setAccessible(true);
                try{
                    if (field.getName().equals("id")){
                        mId = (Long) superclass.getDeclaredField("id").get(support);
                        contentValues.put(field.getName(), mId);
                    }else {
                        contentValues.put(field.getName(),(String) field.get(support));
                    }
                }catch(Exception e){
                    e.printStackTrace();
                }
            }
            try {
                db.update(tableName,contentValues,"id = ?",new String[]{mId+""});
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
    /**
     * 根据条件更新 数据集合
     */
    public static void updateAll(){

    }
    /**
     * 判断当前对象是否保存到数中去了
     */
    public boolean isSaved(){
        return id>0;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }
}
