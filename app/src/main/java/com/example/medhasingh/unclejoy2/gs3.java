package com.example.medhasingh.unclejoy2;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

/**
 * Created by medha singh on 7/8/2016.
 */
public class gs3 extends Fragment{
    public View onCreateView(LayoutInflater inflater,ViewGroup container,Bundle savedInstanceState){
        super.onCreateView(inflater, container, savedInstanceState);
        final View view_gs = inflater.inflate(R.layout.gs_3, container, false);
        TextView b=(TextView)view_gs.findViewById(R.id.starting);
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences get_started=getContext().getSharedPreferences("GET_STARTED",0);
                SharedPreferences.Editor edit=get_started.edit();
                edit.putBoolean("viewed",true);
                edit.apply();
                Intent s=new Intent(getContext(),Login.class);
                startActivity(s);
            }
        });
        //TextView bl=(TextView)view_gs.findViewById(R.id.skip3);
        /*bl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent s=new Intent(getContext(),Login.class);
                startActivityForResult(s,0);
            }
        });*/

        return view_gs;
    }
}
