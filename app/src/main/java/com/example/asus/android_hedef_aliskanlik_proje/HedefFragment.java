package com.example.asus.android_hedef_aliskanlik_proje;

import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.Calendar;

public class HedefFragment extends Fragment {
    private TimePickerDialog timePickerDialog;
    final static int REQUEST_CODE = 1;
    private static final String HEDEFID = "HedefID";

    HedefModel hedef;
    EditText hedeftext, aciklama;
    TextView tarih, saat,alarmtext;
    TextView baslangic;
    Button saatbutton, tarihbutton,alarmbuton;

    public HedefFragment() {
        // Required empty public constructor
    }

    public static HedefFragment newInstance(String  HedefID) {
        Bundle args = new Bundle();
        args.putString(HEDEFID, HedefID);
        HedefFragment hedeffragment = new HedefFragment();
        hedeffragment.setArguments(args);
        return hedeffragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        String HedefID = getArguments().getString(HEDEFID);
        hedef=HedefDBErisim.get(getActivity()).Hedefbul(HedefID);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment,
        View v = inflater.inflate(R.layout.fragment_hedef, container, false);
        hedeftext= (EditText) v.findViewById(R.id.hedeftext);
        aciklama = (EditText) v.findViewById(R.id.aciklamatext);
        tarih =     (TextView) v.findViewById(R.id.tarihtext);
        saat =      (TextView) v.findViewById(R.id.saattext);
        alarmtext = (TextView) v.findViewById(R.id.alarmtext);
        baslangic = (TextView) v.findViewById(R.id.baslangictext);
        saatbutton = (Button) v.findViewById(R.id.saatbuton);
        tarihbutton = (Button) v.findViewById(R.id.tarihbuton);
        alarmbuton = (Button) v.findViewById(R.id.butonalarm);

        tarihbutton.setOnClickListener(new View.OnClickListener() {//tarihButona Click Listener ekliyoruz

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                Calendar mcurrentTime = Calendar.getInstance();
                int year = mcurrentTime.get(Calendar.YEAR);//Güncel Yılı alıyoruz
                int month = mcurrentTime.get(Calendar.MONTH);//Güncel Ayı alıyoruz
                int day = mcurrentTime.get(Calendar.DAY_OF_MONTH);//Güncel Günü alıyoruz
                DatePickerDialog datePicker;//Datepicker objemiz
                datePicker = new DatePickerDialog(getContext(), new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear,
                                          int dayOfMonth) {
                        // TODO Auto-generated method stub
                        tarih.setText( dayOfMonth + "/" + monthOfYear+ "/"+year);//Ayarla butonu tıklandığında textview'a yazdırıyoruz
                    }
                },year,month,day);//başlarken set edilcek değerlerimizi atıyoruz
                datePicker.setTitle("Tarih Seçiniz");
                datePicker.setButton(DatePickerDialog.BUTTON_POSITIVE, "Ayarla", datePicker);
                datePicker.setButton(DatePickerDialog.BUTTON_NEGATIVE, "İptal", datePicker);
                datePicker.show();
            }
        });

        saatbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar mcurrentTime = Calendar.getInstance();//
                int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);//Güncel saati aldık
                int minute = mcurrentTime.get(Calendar.MINUTE);//Güncel dakikayı aldık
                TimePickerDialog timePicker; //Time Picker referansımızı oluşturduk

                //TimePicker objemizi oluşturuyor ve click listener ekliyoruz
                timePicker = new TimePickerDialog(getContext(), new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                        saat.setText( selectedHour + ":" + selectedMinute);//Ayarla butonu tıklandığında textview'a yazdırıyoruz
                    }
                }, hour, minute, true);//true 24 saatli sistem için
                timePicker.setTitle("Saat Seçiniz");
                timePicker.setButton(DatePickerDialog.BUTTON_POSITIVE, "Ayarla", timePicker);
                timePicker.setButton(DatePickerDialog.BUTTON_NEGATIVE, "İptal", timePicker);
                timePicker.show();
            }
        });


        alarmbuton.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        openPickerDialog(false);
    }
        });
        hedeftext.setText(hedef.getIsmi());
        aciklama.setText(hedef.getAciklama());
        tarih.setText(hedef.getTarih());
        saat.setText(hedef.getSaat());

        hedeftext.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                hedef.setIsmi(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        tarih.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                hedef.setTarih(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        saat.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                hedef.setSaat(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        aciklama.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                hedef.setAciklama(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        return v;
    }
    private void openPickerDialog(boolean is24hour) {

        Calendar calendar = Calendar.getInstance();

        timePickerDialog = new TimePickerDialog(
               getContext(),
                onTimeSetListener,
                calendar.get(Calendar.HOUR_OF_DAY),
                calendar.get(Calendar.MINUTE),
                is24hour);
        timePickerDialog.setTitle("Alarm Ayarla");

        timePickerDialog.show();
    }

    TimePickerDialog.OnTimeSetListener onTimeSetListener
            = new TimePickerDialog.OnTimeSetListener(){

        @Override
        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {

            Calendar calNow = Calendar.getInstance();
            Calendar calSet = (Calendar) calNow.clone();

            calSet.set(Calendar.HOUR_OF_DAY, hourOfDay);
            calSet.set(Calendar.MINUTE, minute);
            calSet.set(Calendar.SECOND, 0);
            calSet.set(Calendar.MILLISECOND, 0);

            if(calSet.compareTo(calNow) <= 0){
                calSet.add(Calendar.DATE, 1);
            }
            setAlarm(calSet);
        }};
    private void setAlarm(Calendar alarmCalender) {
        Toast.makeText(getActivity().getApplicationContext(), "Alarm Ayarlandı!", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(getActivity().getBaseContext(), alarm.class);
        final int _id=(int) System.currentTimeMillis();
        PendingIntent pendingIntent = PendingIntent.getBroadcast(getActivity().getBaseContext(),_id,intent,PendingIntent.FLAG_ONE_SHOT);
        AlarmManager alarmManager = (AlarmManager) getActivity().getSystemService(Context.ALARM_SERVICE);
        alarmManager.set(AlarmManager.RTC_WAKEUP, alarmCalender.getTimeInMillis(), pendingIntent);

      //  final int id=(int)System.currentTimeMillis();
       // PendingIntent appIntent = PendingIntent.getBroadcast(getActivity().getBaseContext(), PendingIntent.FLAG_NO_CREATE, intent, 0);
    }

    @Override
    public void onPause() {
        super.onPause();
        HedefDBErisim.get(getActivity()).HedefGuncelle(hedef);
    }
}