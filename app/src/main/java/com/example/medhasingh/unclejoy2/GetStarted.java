package com.example.medhasingh.unclejoy2;

import android.app.Activity;
import android.app.FragmentManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.TabLayout;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;


/**
 * Created by medha singh on 7/8/2016.
 */
public class GetStarted extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.get_started);
        final ViewPager viewpager = (ViewPager)findViewById(R.id.myviewpager);
        CircleIndicator indicator = (CircleIndicator)findViewById(R.id.indicator);
        TabLayout tabLayout = (TabLayout) findViewById(R.id.tab_layout_get);
        tabLayout.addTab(tabLayout.newTab().setText(""));
        tabLayout.addTab(tabLayout.newTab().setText(""));
        tabLayout.addTab(tabLayout.newTab().setText(""));
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);
        final PagerAdapter mPageAdapter = new mypageadapt(getSupportFragmentManager(),tabLayout.getTabCount());
        viewpager.setAdapter(mPageAdapter);
        final Handler handler = new Handler();
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < mPageAdapter.getCount(); i++) {
                    final int value = i;
                    try {
                        Thread.sleep(4000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            viewpager.setCurrentItem(value);
                        }
                    });
                }
            }
        };
        new Thread(runnable).start();
        indicator.setViewPager(viewpager);
        indicator.configureIndicator(35, 35, 5);


       // viewpager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewpager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });


    }
    protected void onResume(){
        super.onResume();
        SharedPreferences get_started=getSharedPreferences("GET_STARTED",0);
        if (get_started.getBoolean("viewed",false)==true){
            Intent n=new Intent(getApplicationContext(),Login.class);
            startActivity(n);
        }

    }
}
