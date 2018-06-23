package com.example.asus.android_hedef_aliskanlik_proje;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class Hedef extends AppCompatActivity
      implements HedefModelFragment.OnListFragmentInteractionListener{
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hedef);
        FragmentManager fragmentManager=getSupportFragmentManager();
        Fragment fragment =fragmentManager.findFragmentById(R.id.container);

        if(fragment==null)
        {
            fragment=new HedefModelFragment();
            fragmentManager.beginTransaction().add(R.id.container,fragment).commit();
        }
    }
    @Override
    public void onListFragmentInteraction(HedefModel item) {
        FragmentManager fragmentManager=getSupportFragmentManager();
        Fragment yenifragment=HedefFragment.newInstance(item.getID());
        fragmentManager.beginTransaction().replace(R.id.container,yenifragment)
                .addToBackStack(null)
                .commit();
    }
}