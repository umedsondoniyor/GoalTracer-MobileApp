package com.example.asus.android_hedef_aliskanlik_proje;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.preference.PreferenceManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Takvim extends AppCompatActivity  {

    public  static String ETIKET="android_hedef_aliskanlik_proje";
    TextView takvimgecis;
    CalendarView calendar;
    String a;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_takvim);

        calendar = (CalendarView) findViewById(R.id.calendar);
        takvimgecis=(TextView)findViewById(R.id.tarihgecis);
        calendar.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {

            @Override
            public void onSelectedDayChange(final CalendarView view,
                                            int year, int month , int dayOfMonth) {
                int montha = 1+ month;
                Toast.makeText(getApplicationContext(),
                        dayOfMonth +  "/" + montha  + "/" + year, Toast.LENGTH_LONG).show();

               a= dayOfMonth + "/" + montha + "/" + year;
                takvimgecis.setText(a);

                final Context context = getApplicationContext();
                AlertDialog.Builder builder = new AlertDialog.Builder(Takvim.this);
                builder.setTitle("Etkinlik");
                builder.setMessage("Etkinlik eklemek istedigine emin misin?");
                builder.setNegativeButton("Hayir", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {

                    }
                });

                builder.setPositiveButton("Evet", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                      //  Toast.makeText(Takvim.this, "a" + view, Toast.LENGTH_LONG).show();
                        Intent i = new Intent(Takvim.this, EtkinlikActivity.class);
                        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(Takvim.this);
                        SharedPreferences.Editor editor = prefs.edit();
                        editor.putString("a", String.valueOf(view));

                        i.putExtra(ETIKET,a);
                        editor.commit();
                        startActivity(i);
                    }
                });

                builder.show();
            }

        });
    }
}