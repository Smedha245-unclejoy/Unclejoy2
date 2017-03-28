package com.example.medhasingh.unclejoy2;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.widget.TextView;

/**
 * Created by medha singh on 6/15/2016.
 */
public class MyPagerAdapter extends FragmentStatePagerAdapter {
    int mNumOfTabs;

    public MyPagerAdapter(FragmentManager fm, int NumOfTabs) {
        super(fm);
        this.mNumOfTabs = NumOfTabs;
    }


    @Override
    public int getCount() {
        return mNumOfTabs;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position){

        case 0:
        Fragment1 fragment1=new Fragment1();
        return fragment1;

        case 1:
        Fragment2 responded=new Fragment2();
        return responded;

        case 2:
        Fragment3 hired=new Fragment3();
        return hired;

        default:
        return null;}
    }
}
