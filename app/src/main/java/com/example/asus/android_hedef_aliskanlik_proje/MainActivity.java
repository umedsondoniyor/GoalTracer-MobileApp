package com.example.asus.android_hedef_aliskanlik_proje;

import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.os.Vibrator;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class MainActivity extends  AppCompatActivity implements SensorEventListener{
    //SensorEventListener=>Yeni sensör verisi olduğunda SensorManager'dan bildirim almak için kullanılır
    private SensorManager sensorManager;
    private Vibrator vibrator;
    private float  last_x, last_y, last_z = 0;

    List<Etkinlik> etkinlikList=new ArrayList<Etkinlik>();
    String etkinlik_adlari[];
    String etkinlik_tarih[];
    int etkinlik_idler[];
    public static String[] separated;
    int i;
    int id;

    Button gec,hedef,liste;
    Sensor sensor;
    TextView metin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //xml den class'a button tanimlamalari
         gec=(Button)findViewById(R.id.butongec);
        hedef=(Button)findViewById(R.id.butonhedef);
        liste=(Button)findViewById(R.id.butonliste);

        //gec buttona basinca gec
        gec.setOnClickListener(new View.OnClickListener() {

           @Override
           public void onClick(View v) {
               Intent i=new Intent(MainActivity.this,Takvim.class);
               startActivity(i);

           }
         });

       //hedef buttona basinca hedef
        hedef.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {

         Intent i=new Intent(MainActivity.this,Hedef.class);
            startActivity(i);

        }
        });
        //liste buttona basinca liste
        liste.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(MainActivity.this,Listele.class);
                startActivity(i);

            }
        });

        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        sensorManager.registerListener(this,
                sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER),
                SensorManager.SENSOR_DELAY_UI);
        vibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
        }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        sensorManager.unregisterListener(MainActivity.this);
    }
    @Override
    public void onSensorChanged(SensorEvent event) {
        if (event.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
            float[] values = event.values;

            float x = values[0];
            float y = values[1];
            float z = values[2];

            if (z * last_z<0  && x*last_x<0 && y*last_y <0 ) {
                vibrator.vibrate(400);
                HedefDBHelper db = new HedefDBHelper(getApplicationContext());
                etkinlikList=db.TumKayitleriGetir();
                etkinlik_adlari = new String[etkinlikList.size()];
                etkinlik_tarih=new String [etkinlikList.size()];
                etkinlik_idler = new int[etkinlikList.size()];
                LayoutInflater inflater = getLayoutInflater();
                View layout = inflater.inflate(R.layout.layout,
                        (ViewGroup) findViewById(R.id.custom_toast_layout));

                TextView text = (TextView) layout.findViewById(R.id.textToShow);
                for(i=0;i<etkinlikList.size();i++){
                    etkinlik_adlari[i] = etkinlikList.get(i).getAd();
                    etkinlik_tarih[i]=etkinlikList.get(i).getTarih();

                    Date simdikiZaman = new Date();
                    java.text.DateFormat df = new SimpleDateFormat("d/M/yyyy");
                    String bgnTarih1=df.format(simdikiZaman).toString();
                    if(etkinlik_tarih[i].equalsIgnoreCase(bgnTarih1)){
                        text.setText("Bugunku AHA:\n "+etkinlik_adlari[i]);
                        Toast toast = new Toast(getApplicationContext());
                        toast.setGravity(Gravity.CENTER_VERTICAL, 0, 0);
                        toast.setDuration(Toast.LENGTH_LONG);
                        toast.setView(layout);
                        toast.show();
                    }
                }
            }
            Log.d("MainActivity", String.format("x : %f y : %f z : %f", x, y, z));
            last_x = x;
            last_y = y;
            last_z = z;
        }
    }
    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }
}