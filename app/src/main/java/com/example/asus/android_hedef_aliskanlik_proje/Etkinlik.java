package com.example.asus.android_hedef_aliskanlik_proje;

public class Etkinlik {

    private String id;
    private  String ad;
    private  String tarih;
    private long Id;
    public Etkinlik() {
        //boş olan yapıcı metot
    }

    public Etkinlik(String ad, String tarih) {


        this.ad = ad;
        this.tarih = tarih;
    }

    public void setId(long id)
    {
        Id=id;
    }
    public  long getId()
    {return  Id;}

    public String getAd() {
        return ad;
    }

    public void setAd(String ad) {
        this.ad = ad;
    }

    public String getTarih() {
        return tarih;
    }

    public void setTarih(String tarih) {
        this.tarih = tarih;
    }

}