package com.example.medhasingh.unclejoy2;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;



/**
 * Created by medha singh on 6/17/2016.
 */
public class MyPagerAdapter2 extends FragmentStatePagerAdapter {
    private final CustomViewPager viewPager;
    int mNumOfTabs;
    public MyPagerAdapter2(FragmentManager fm, int mNumOfTabs, CustomViewPager viewPager) {
        super(fm);
        this.mNumOfTabs=mNumOfTabs;
        this.viewPager=viewPager;
    }



    @Override
    public Fragment getItem(int position) {
        switch(position){
            case 0:
               Fragment f1=new Fragment4();
                return f1;
            case 1:
                Fragment f7=new Fragment7();
                return f7;
            case 2:
                Fragment f2=new SearchStore();
                return f2;
            case 3:
                Fragment f3=new Fragment5();
                return f3;
            case 4:
                 Fragment f6=new Fragment6();
                return f6;


            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return mNumOfTabs ;
    }
}
