package com.example.asus.android_hedef_aliskanlik_proje;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class EtkinlikActivity extends AppCompatActivity  {
    TextView sonucText;
    EditText etkinliktext;
    Button kaydetbuton,butonlistele,butonsil;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_etkinlik);
        etkinliktext = (EditText) findViewById(R.id.etkinlik);
        sonucText = (TextView) findViewById(R.id.sonuc);
        kaydetbuton = (Button) findViewById(R.id.butonkaydet);
        butonlistele = (Button) findViewById(R.id.butonlistele);
        final Intent intent = getIntent();
        String sonuc = intent.getStringExtra(Takvim.ETIKET);
        sonucText.setText(sonuc);
    }
            public void butonaDokunuldu(View view) {
                switch (view.getId()) {
                    case R.id.butonkaydet:
                        String ad = etkinliktext.getText().toString();
                        String tarih = sonucText.getText().toString();

                        try {

                            Etkinlik etkinlik = new Etkinlik(ad, tarih);
                            HedefDBHelper db = new HedefDBHelper(getApplicationContext());
                            long ıd = db.EtkinlikEkle(etkinlik);//öğrenci sınıfındaki nesneyi vermiş olduk
                            if (ıd == -1) {
                                Toast.makeText(EtkinlikActivity.this, "Oglum kayit eklerken hata olustu", Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(EtkinlikActivity.this, "Kayit islemi basarili", Toast.LENGTH_SHORT).show();
                            }

                        } catch (Exception e) {
                            Toast.makeText(EtkinlikActivity.this, "Oglum!\n" + e.getMessage().toString(), Toast.LENGTH_SHORT).show();
                        }

                        sonucText.setText("");
                        etkinliktext.setText("");
                        break;
                    case R.id.butonlistele:
                        Intent i = new Intent(getApplicationContext(), Listele.class);
                        startActivity(i);
                        break;
                }
            }
}