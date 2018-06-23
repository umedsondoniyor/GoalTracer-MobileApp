package com.example.asus.android_hedef_aliskanlik_proje;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class HedefDBErisim {
    private static HedefDBErisim mhedefDBErisim;
    private Context mcontext;
    private SQLiteDatabase mdb;

    public  HedefDBErisim(Context context)
    {
        mcontext=context.getApplicationContext();
        mdb = new HedefDBHelper(mcontext).getWritableDatabase();
    }

    public static HedefDBErisim get(Context context)
    {
        if(mhedefDBErisim == null)
        {
            mhedefDBErisim = new HedefDBErisim(context);
        }
        return mhedefDBErisim;
    }
    public  HedefDBCursor HedefSorgula(String whereIfadesi,String[] whereArgs)
    {
        Cursor cursor = mdb.query(HedefDbSema.TABLO_ADI,null,
                whereIfadesi,
                whereArgs,null,null,null);
        return  new HedefDBCursor(cursor);
    }


    private ContentValues getDegerler(HedefModel hedef)
    {
        ContentValues degerler = new ContentValues();
        degerler.put(HedefDbSema.SUTUN_ID,hedef.getID());
        degerler.put(HedefDbSema.SUTUN_ADI,hedef.getIsmi());
        degerler.put(HedefDbSema.SUTUN_TARIH,hedef.getTarih());
        degerler.put(HedefDbSema.SUTUN_SAAT,hedef.getSaat());
        degerler.put(HedefDbSema.SUTUN_ACIKLAMA,hedef.getAciklama());

        return  degerler;

    }

    //////////////////////////////////////////////////////

    public  HedefModel Hedefbul(String id)
    {
       HedefDBCursor hedefDBCursor=HedefSorgula(
               HedefDbSema.SUTUN_ID + "=?",
               new String[]{id});
        try{
            if (hedefDBCursor.getCount()==0){
                return null;
            }
            hedefDBCursor.moveToFirst();
            return  hedefDBCursor.getHedef();
        }
        finally {
            hedefDBCursor.close();
        }
    }

    //////////////////////////////////////////////////////////

    public  String Hedefekle(HedefModel m)
    {
        ContentValues deger=getDegerler(m);
        long yeniID = mdb.insert(HedefDbSema.TABLO_ADI,null,deger);
        return String.valueOf(yeniID);
    }

    /////////////////////////////////////////////////////////////

    public  void HedefGuncelle(HedefModel m)
    {
        String id = m.getID();
        ContentValues degerler = getDegerler(m);

        mdb.update(HedefDbSema.TABLO_ADI,degerler,
                HedefDbSema.SUTUN_ID + " = ?",
                new String[]{id});
    }

    //////////////////////////////////////////////////////////////

    public void KisiSil(HedefModel m)
    {
        String id = m.getID();
        mdb.delete(HedefDbSema.TABLO_ADI,HedefDbSema.SUTUN_ID + " = ?",
                new String[]{id});
       }

    /////////////////////////////////////////////////////////////

    public List<HedefModel> tumListe()
    {
        List<HedefModel> liste = new ArrayList<>();

        HedefDBCursor cursor = HedefSorgula(null,null);
        cursor.moveToFirst();

        while (!cursor.isAfterLast())
        {
            liste.add(cursor.getHedef());
            cursor.moveToNext();
        }

        cursor.close();
        return liste;
    }
}