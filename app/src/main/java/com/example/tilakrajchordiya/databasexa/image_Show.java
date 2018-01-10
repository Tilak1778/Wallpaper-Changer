package com.example.tilakrajchordiya.databasexa;


import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.SystemClock;;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class image_Show extends AppCompatActivity {

   Context context;
    String albumName;
    SQLiteDatabase sqLiteDatabase;
    DataBaseHelper dataBaseHelper;
    Cursor cursor;
    GridView gridView;
    imageAdapter adapter;
    Button selectAlbum;
    Button setting;
    EditText timeInterval;
    TextView preTime;
    public  ArrayList<String> list;
    public static int takeFlags;

   AlarmManager manager;
   PendingIntent pendingIntent;
    public static int interval=1;



    public void showImages(){
        cursor=dataBaseHelper.getImagesFromDtabase(albumName,sqLiteDatabase);
        Log.e("database","images retrieved");

        list=new ArrayList<String>();

        if (cursor.moveToFirst())
        {
            do {
                list.add(cursor.getString(1));

            }while (cursor.moveToNext());
        }
        adapter=new imageAdapter(getApplicationContext(),list);

        gridView.setAdapter(adapter);

    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.image__show_activity);

        context=this;
        timeInterval=findViewById(R.id.timeInterval);
        setting=findViewById(R.id.setting);
        selectAlbum=findViewById(R.id.selectAlbum);

         gridView=findViewById(R.id.imgGridView);
        Intent intent=getIntent();
        albumName=intent.getStringExtra("album_name");

        Toast.makeText(getApplicationContext(),albumName,Toast.LENGTH_LONG).show();


        dataBaseHelper=new DataBaseHelper(getApplicationContext());
        sqLiteDatabase=dataBaseHelper.getWritableDatabase();
        showImages();


        FloatingActionButton imageFab=findViewById(R.id.addImageFab);

        imageFab.setOnClickListener(

                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        Intent intent=new Intent(Intent.ACTION_OPEN_DOCUMENT);
                        intent.addCategory(Intent.CATEGORY_OPENABLE);
                        intent.addFlags(Intent.FLAG_GRANT_PERSISTABLE_URI_PERMISSION);
                        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                        intent.setType("image/*");
                        intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE,true);
                        startActivityForResult(Intent.createChooser(intent,"select image"),0);

                    }
                }
        );


       selectAlbum.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                       startAlarm();

                    }
                }
        );


       setting.setOnClickListener(
               new View.OnClickListener() {
                   @Override
                   public void onClick(View view) {
                       Intent i=new Intent(context, com.example.tilakrajchordiya.databasexa.setting.class);
                       startActivity(i);

                   }
               }
       );


    }

    public void startAlarm() {


        Intent alarmIntent = new Intent(this, AlarmReceiver.class).putExtra("name",list);

        pendingIntent = PendingIntent.getBroadcast(this, 0, alarmIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        manager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        preTime=findViewById(R.id.preTime);

        manager.setRepeating(AlarmManager.ELAPSED_REALTIME,
                SystemClock.elapsedRealtime()+5000,interval,pendingIntent);

        Toast.makeText(this, "Album Selected", Toast.LENGTH_SHORT).show();

        Log.e("database","alarm started");

    }




    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode==0){
            if (resultCode== Activity.RESULT_OK){
                takeFlags=data.getFlags()
                        & (Intent.FLAG_GRANT_READ_URI_PERMISSION
                        | Intent.FLAG_GRANT_WRITE_URI_PERMISSION);

                if (data.getClipData() !=null){
                    int count=data.getClipData().getItemCount();
                    int currentItem=0;
                    while (currentItem<count) {

                        Uri imageuri = data.getClipData().getItemAt(currentItem).getUri();

                        int no=currentItem+1;
                        Log.e("database", "get image uri of " + no + " image");
                        String uriToString=imageuri.toString();
                            dataBaseHelper=new DataBaseHelper(getApplicationContext());
                            sqLiteDatabase=dataBaseHelper.getWritableDatabase();
                            dataBaseHelper.insertImageUri(albumName,uriToString,sqLiteDatabase);

                        currentItem++;

                    }

                }
                else if (data.getData() !=null){
                    Uri uri= data.getData();
                    Log.e("database","get image uri");


                    String imageUri=uri.toString();
                    dataBaseHelper=new DataBaseHelper(getApplicationContext());
                    sqLiteDatabase=dataBaseHelper.getWritableDatabase();
                    dataBaseHelper.insertImageUri(albumName,imageUri,sqLiteDatabase);


                }

                dataBaseHelper=new DataBaseHelper(getApplicationContext());
                sqLiteDatabase=dataBaseHelper.getWritableDatabase();
                showImages();
            }
        }


    }


}
