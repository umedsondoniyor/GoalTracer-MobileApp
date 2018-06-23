package com.example.asus.android_hedef_aliskanlik_proje;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static android.R.attr.id;

public class Listele extends AppCompatActivity {
    TextView tarih;
    long id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listele);
        tarih = (TextView) findViewById(R.id.tarih);
        TextView tv = (TextView) findViewById(R.id.tv);
        Date simdikiZaman = new Date();
        DateFormat df = new SimpleDateFormat("d/M/yyyy");
        tarih.setText(df.format(simdikiZaman).toString());

        HedefDBHelper db = new HedefDBHelper(getApplicationContext());
        List<Etkinlik> etkinlikList = new ArrayList<Etkinlik>();
        etkinlikList = db.TumKayitleriGetir();
        final StringBuilder stringBuilder = new StringBuilder();
        for (final Etkinlik etkinlik : etkinlikList) {
            stringBuilder.append(etkinlik.getTarih() + "\n" + etkinlik.getAd() + "\n\n");

            tv.setText(stringBuilder);
            tv.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    id=etkinlik.getId();
                    final AlertDialog.Builder alertDialog = new AlertDialog.Builder(Listele.this);

                    alertDialog.setTitle("Uyari");
                    alertDialog.setMessage("Hedefin Silinsin mi ?");
                    alertDialog.setPositiveButton("Evet", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            HedefDBHelper db = new HedefDBHelper(getApplicationContext());
                            db.kaydiSil(id);
                            new Listele();
                            //uzerinde tiklayip durdugunda uyari mesaji olusturur
                            Toast.makeText(getApplicationContext(), "Hedefin Basariyla Silindi", Toast.LENGTH_LONG).show();
                            Intent i = new Intent(getApplicationContext(), Listele.class);

                            startActivity(i);
                            finish();
                        }
                    });
                    alertDialog.setNegativeButton("Hayir", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                        }
                    });
                    alertDialog.show();
                    return false;
                }


            });
        }
    }
}