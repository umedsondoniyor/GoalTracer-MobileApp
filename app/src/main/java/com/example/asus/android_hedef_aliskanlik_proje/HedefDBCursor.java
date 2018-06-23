package com.example.asus.android_hedef_aliskanlik_proje;

import android.database.Cursor;
import android.database.CursorWrapper;
//VeriTabani olusturma
public class HedefDBCursor extends CursorWrapper {
//veritabanı sorgusu rastgele okuma-yazma erişimi sağlar.
    public HedefDBCursor(Cursor cursor) {
        super(cursor);
    }
    public  HedefModel getHedef()
    {
        String ID = getString(getColumnIndex(HedefDbSema.SUTUN_ID));
        String ismi = getString(getColumnIndex(HedefDbSema.SUTUN_ADI));
        String tarih = getString(getColumnIndex(HedefDbSema.SUTUN_TARIH));
        String saat = getString(getColumnIndex(HedefDbSema.SUTUN_SAAT));
        String aciklama = getString(getColumnIndex(HedefDbSema.SUTUN_ACIKLAMA));


        HedefModel hedef = new HedefModel();
        hedef.setID(ID);
        hedef.setIsmi(ismi);
        hedef.setTarih(tarih);
        hedef.setSaat(saat);
        hedef.setAciklama(aciklama);
        return hedef;
    }
}