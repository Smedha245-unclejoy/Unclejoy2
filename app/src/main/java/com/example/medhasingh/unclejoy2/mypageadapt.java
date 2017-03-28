package com.example.medhasingh.unclejoy2;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

/**
 * Created by medha singh on 7/11/2016.
 */
public class mypageadapt extends FragmentStatePagerAdapter {
    int mNumOfTabs;
    public mypageadapt(FragmentManager fm, int mNumOfTabs) {
        super(fm);
        this.mNumOfTabs=mNumOfTabs;
    }




    @Override
    public Fragment getItem(int position) {

        switch (position){
            case 0:
                Fragment f1=new gs1();
                return f1;
            case 1:
                Fragment f2=new gs2();
                return f2;
            case 2:
                Fragment f3=new gs3();
                return f3;
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return mNumOfTabs;
    }
}
