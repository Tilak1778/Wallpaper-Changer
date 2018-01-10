package com.example.tilakrajchordiya.databasexa;


import android.content.ContentResolver;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;


public class imageAdapter extends BaseAdapter {
    private Context context;
    ArrayList uriString;

    int flag=image_Show.takeFlags;


    public imageAdapter(Context c,ArrayList uri) {
        context=c;
        uriString=uri;

    }

    @Override
    public int getCount() {
        return uriString.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View Grid;
        LayoutInflater inflater=(LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        if (convertView==null){

            Grid=inflater.inflate(R.layout.grid_single,null);
            Uri imageUri=Uri.parse((String) uriString.get(position));


           InputStream inputStream;

            try {

                ContentResolver resolver=context.getContentResolver();
                resolver.takePersistableUriPermission(imageUri,flag);
                inputStream= resolver.openInputStream(imageUri);
                Bitmap img=BitmapFactory.decodeStream(inputStream);
                Log.e("database","get Bitmap");
                ImageView imgView=Grid.findViewById(R.id.grid_image_single);
                imgView.setPadding(3,3,3,3);
                imgView.setImageBitmap(img);

            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }





        }
        else
            Grid=convertView;

        return Grid;
    }


}
