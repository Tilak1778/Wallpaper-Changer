package com.example.tilakrajchordiya.databasexa;


import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class setting extends AppCompatActivity {
    Button ok;
    EditText interval;
    image_Show imageShow;
    TextView preTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        ok=findViewById(R.id.setTime);

        interval=findViewById(R.id.timeInterval);
        imageShow=new image_Show();
        preTime=findViewById(R.id.preTime);
        int preT=imageShow.interval;
        preTime.setText(""+preT/60000);

    }

    public void setTime(View view){
        String time=interval.getText().toString();

        if (time!=null) {
            int t=Integer.parseInt(time);
            if (t >= 1) {

                preTime.setText("" + t);
                imageShow.interval = t * 1000 * 60;

            } else
                Toast.makeText(getApplicationContext(), "minimun time should be 1 min.", Toast.LENGTH_LONG).show();


        }
    }
}
