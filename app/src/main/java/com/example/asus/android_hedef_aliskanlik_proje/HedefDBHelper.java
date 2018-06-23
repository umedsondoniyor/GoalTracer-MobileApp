package com.example.asus.android_hedef_aliskanlik_proje;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class HedefDBHelper extends SQLiteOpenHelper {
    public  static String ETIKET1="android_hedef_aliskanlik_proje";
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "hedef_aliskanlik.db";
    public   static  final String TABLE_NAME="ETKINLIK_TABLOSU";
    private  static final String ETKINLIKAD="ad";
    private  static final String ETKINLIKTARİH="tarih";
    private  static final String ETKINLIKID="id";
    private  SQLiteDatabase db;
    public  final static  Date simdikiZaman = new Date();
    public final static DateFormat df = new SimpleDateFormat("dd / MM /yyyy");

    public HedefDBHelper(Context context ){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table " + HedefDbSema.TABLO_ADI + "(" +
                HedefDbSema.SUTUN_ID +
                " integer primary key autoincrement, " +
                HedefDbSema.SUTUN_ADI + ", " +
                HedefDbSema.SUTUN_TARIH + ", " +
                HedefDbSema.SUTUN_SAAT + ", " +
                HedefDbSema.SUTUN_ACIKLAMA + ")"
        );
        String tablo_olusturma= " CREATE TABLE " + TABLE_NAME +
                " ( "+ ETKINLIKID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                ETKINLIKAD + " TEXT, " +
                ETKINLIKTARİH + " TEXT );";
        db.execSQL(tablo_olusturma);
    }
        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            db.execSQL("drop table if exists " + HedefDbSema.TABLO_ADI);
            //  onCreate(db);//veritabanını oluşturdu
            db.execSQL(" DROP TABLE IF EXISTS "+TABLE_NAME);
            onCreate(db);//veritabanını oluşturdu
        }

    public long EtkinlikEkle(Etkinlik etkinlik) {// sınıfından parametre aldık
        //İLK ÖNCE VERİTABANI AÇMAK GEREKİYOR
        SQLiteDatabase db = getWritableDatabase();//hem okunabilir hem yazılabilir veritabanı
        ContentValues  cv=new ContentValues(); //bu sınıf ile kaydet güncelle
        //intentlerde olduğu gibi ekleme işlemi yapılıyor
        cv.put(ETKINLIKAD,etkinlik.getAd());
        cv.put(ETKINLIKTARİH,etkinlik.getTarih());
        long id=db.insert(TABLE_NAME,null,cv);//kayıt eklenmiş oldu
        //geriye long deger döndürür bu değer eklenen satırın ıd sidir bi hata olursa değer -1 olur
        db.close();
        return  id;
    }

    public void kaydiSil(long id) {
        // TODO Auto-generated method stub
        SQLiteDatabase db=this.getWritableDatabase();
        db.delete(TABLE_NAME, ETKINLIKID  + "=" + id,null);
        //db.delete(TABLE_NAME, ETKINLIKTARİH + "=?",new String[]{ String.valueOf(TARİH)});
        db.close();
    }

    public List<Etkinlik> TumKayitleriGetir() {
        SQLiteDatabase db=this.getReadableDatabase();
        String [] sutunlar=new String[]{ETKINLIKAD,ETKINLIKTARİH};
        //Cursor c=db.query(TABLE_NAME,sütunlar,null,null,null,null,null);
        Cursor cursor=db.rawQuery("SELECT*FROM "+TABLE_NAME,null);
        int idno=cursor.getColumnIndex(ETKINLIKID);
        int adno=cursor.getColumnIndex(ETKINLIKAD);
        int tarihno=cursor.getColumnIndex(ETKINLIKTARİH);

        List<Etkinlik> etkinlikList=new ArrayList<Etkinlik>();
        for(cursor.moveToFirst();!cursor.isAfterLast();cursor.moveToNext()){
            Etkinlik etkinlik=new Etkinlik();
            etkinlik.setAd(cursor.getString(adno));
            etkinlik.setTarih(cursor.getString(tarihno));
            etkinlik.setId(cursor.getLong(idno));
            etkinlikList.add(etkinlik);
        }
        db.close();
        return  etkinlikList;
    }
}
