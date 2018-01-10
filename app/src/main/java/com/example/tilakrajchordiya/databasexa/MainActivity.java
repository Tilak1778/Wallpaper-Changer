package com.example.tilakrajchordiya.databasexa;


import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;
import java.util.ArrayList;



public class MainActivity extends AppCompatActivity {

    DataBaseHelper myDb;
    SQLiteDatabase sqLiteDatabase;
    ListView listView;
    Context context=this;
    Cursor cursor;
    ArrayAdapter<String> adapter;
    PendingIntent pendingIntent;
    Button stop;




    public void add(String name){
        myDb=new DataBaseHelper(context);
        sqLiteDatabase=myDb.getWritableDatabase();
        myDb.insertData(name,sqLiteDatabase);
        Toast.makeText(getBaseContext(),"Album created",Toast.LENGTH_LONG).show();
        myDb.close();

    }


    public void ViewAlbum(){

        cursor=myDb.getAllData(sqLiteDatabase);

        ArrayList<String> list=new ArrayList<String>();

        if (cursor.moveToFirst())
        {
            do {
                list.add(cursor.getString(1));

            }while (cursor.moveToNext());
        }
        adapter=new ArrayAdapter<String>(getApplicationContext(),android.R.layout.simple_list_item_1,list);
        listView.setAdapter(adapter);
        Log.e("database","adapter seted");
        adapter.notifyDataSetChanged();


    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        stop=findViewById(R.id.stop);
        Intent alarmIntent = new Intent(this, AlarmReceiver.class);
        pendingIntent = PendingIntent.getBroadcast(this, 0, alarmIntent, 0);


        listView=findViewById(R.id.album_listview);

        myDb=new DataBaseHelper(context);
        sqLiteDatabase=myDb.getReadableDatabase();
        ViewAlbum();




        FloatingActionButton fab=findViewById(R.id.fab);
        fab.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        LayoutInflater li= LayoutInflater.from(context);
                        View fab_view=li.inflate(R.layout.fab_input,null);
                        AlertDialog.Builder fab_dialog=new AlertDialog.Builder(context);
                        fab_dialog.setView(fab_view);
                        final EditText fab_input=fab_view.findViewById(R.id.fab_message);

                        fab_dialog.setCancelable(false)
                                .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialogInterface, int i) {
                                                String input=fab_input.getText().toString();
                                               add(input);
                                                myDb=new DataBaseHelper(context);
                                                sqLiteDatabase=myDb.getWritableDatabase();

                                                myDb.create_table(input.split("\\s++")[0],sqLiteDatabase);

                                                ViewAlbum();

                                            }
                                        }
                                )
                                .setNegativeButton("cancel", new DialogInterface.OnClickListener(){

                                    @Override
                                    public void onClick(DialogInterface dialog, int i) {
                                        dialog.cancel();

                                    }
                                });
                        AlertDialog fabDialog=fab_dialog.create();

                        fabDialog.show();


                    }
                }

        );



        listView.setOnItemClickListener(

                new ListView.OnItemClickListener(){

                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                        final int pos=i;
                        AlertDialog.Builder listDialog=new AlertDialog.Builder(context);


                        listDialog.setCancelable(false)
                                .setNegativeButton("delete",new DialogInterface.OnClickListener(){
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int possition) {
                                        myDb=new DataBaseHelper(context);
                                        sqLiteDatabase=myDb.getWritableDatabase();
                                        Log.e("database","initialiseh");
                                        String name=((String) listView.getItemAtPosition(pos));
                                        Log.e("database","item get");
                                        myDb.DeleteAlbum(name,sqLiteDatabase);
                                        name=name.split("\\s++")[0];
                                        myDb.delete_table(name,sqLiteDatabase);

                                        Toast.makeText(getApplicationContext(),name,Toast.LENGTH_LONG).show();
                                        ViewAlbum();
                                        myDb.close();


                                    }
                                })
                                .setPositiveButton("extend", new DialogInterface.OnClickListener(){
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        String name=((String) listView.getItemAtPosition(pos)).split("\\s++")[0];
                                        Intent intent=new Intent(context,image_Show.class);
                                        intent.putExtra("album_name",name);
                                        startActivity(intent);

                                    }
                                });
                                    AlertDialog ListItemDialog=listDialog.create();

                        ListItemDialog.show();

                    }
                }
        );

        stop.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        cancelAlarm();
                    }
                }
        );

    }
    public void cancelAlarm() {

        Intent intent = new Intent(this, AlarmReceiver.class);

        PendingIntent pIntent = PendingIntent.getBroadcast(this, 0, intent, PendingIntent.FLAG_NO_CREATE);

        AlarmManager manager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);

        if (pIntent!=null) {
            manager.cancel(pIntent);
            pIntent.cancel();
            Toast.makeText(this, "Stoped", Toast.LENGTH_SHORT).show();
        }
    }





}
