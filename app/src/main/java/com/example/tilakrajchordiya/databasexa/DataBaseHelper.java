package com.example.tilakrajchordiya.databasexa;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by Tilak Raj Chordiya on 19/12/2017.
 */

public class DataBaseHelper extends SQLiteOpenHelper {

    private static final String database_name="ALBUM.DB";
    private static final int database_verion=1;
    private static final String create_quary=
            "CREATE TABLE "+album.album_info.TABLE_NAME+"("+album.album_info.ID+
                    " integer primary key autoincrement not null,"+album.album_info.NAME+" TEXT);";


    public DataBaseHelper(Context context) {

        super(context, database_name,null,1);
        Log.e("database operations","database created/opened.........");
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(create_quary);
        Log.e("database operations","table created.........");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
    }

    public void insertData(String MyName,SQLiteDatabase db){

        ContentValues contentValues=new ContentValues();
        contentValues.put(album.album_info.NAME,MyName);
        db.insert(album.album_info.TABLE_NAME,null,contentValues);
        Log.e("database operations","one row inserted........");

    }

    public Cursor getAlbum(SQLiteDatabase db){

        Cursor cursor = null;
        String [] projections={album.album_info.ID,album.album_info.NAME};
        db.query(album.album_info.TABLE_NAME,projections,null,null,null,null,null);
        return cursor;
    }

    public Cursor getAllData(SQLiteDatabase db)
    {
        Cursor res=db.rawQuery("select * from "+album.album_info.TABLE_NAME,null);
        return res;

    }

    public Cursor getImagesFromDtabase(String tableName,SQLiteDatabase db){
        Cursor res=db.rawQuery("select * from "+tableName,null);
        return res;

    }
    public void DeleteAlbum(String name,SQLiteDatabase db){

        db.delete(album.album_info.TABLE_NAME,"name=\'"+name+"\';",null);
        Log.e("database","album deteted");

    }

    public void create_table(String table_name,SQLiteDatabase db){
        db.execSQL("create table "+table_name+"(id integer primary key autoincrement not null,imagePath text);");

        Log.e("database","table created......");

    }
    public void delete_table(String tableName,SQLiteDatabase db){
        db.execSQL("drop table if exists "+tableName+";");
        Log.e("database","table deleted.....");

    }
    public void insertImageUri(String tableName,String imageUri,SQLiteDatabase db){

        db.execSQL("INSERT INTO "+tableName+"(imagePath) values('"+imageUri+"');");
        Log.e("database","image inserted");
    }

}
