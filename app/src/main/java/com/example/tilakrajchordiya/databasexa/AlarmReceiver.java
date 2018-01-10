

package com.example.tilakrajchordiya.databasexa;

import android.app.WallpaperManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.util.Log;
import android.widget.Toast;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;


public class AlarmReceiver extends BroadcastReceiver {

    image_Show imageShow;
    public static int j=0;
    ArrayList<String> uriList;
    Cursor cursor;
    DataBaseHelper dataBaseHelper;
    SQLiteDatabase sqLiteDatabase;


    @Override
    public void onReceive(Context context, Intent intent) {

        uriList=intent.getStringArrayListExtra("name");

     /*   String albumName=intent.getStringExtra("name");
dataBaseHelper=new DataBaseHelper(context);
sqLiteDatabase=dataBaseHelper.getWritableDatabase();

        cursor=dataBaseHelper.getImagesFromDtabase(albumName,sqLiteDatabase);
        Log.e("database","images retrieved");


        uriList=new ArrayList<String>();

        if (cursor.moveToFirst())
        {
            do {
                uriList.add(cursor.getString(1));

            }while (cursor.moveToNext());
        }
        */

        WallpaperManager wallpaperManager= WallpaperManager.getInstance(context);

        String uri=uriList.get(j);
        Uri imageUri=Uri.parse(uri);
        try {
            InputStream inputStream= context.getContentResolver().openInputStream(imageUri);
            wallpaperManager.setStream(inputStream);

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        j++;
        if (j>(uriList.size()-1)) {
            j = 0;
        }

    }
}


