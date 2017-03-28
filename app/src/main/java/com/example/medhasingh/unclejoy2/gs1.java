package com.example.medhasingh.unclejoy2;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

/**
 * Created by medha singh on 7/8/2016.
 */
public class gs1 extends Fragment {
    public View onCreateView(LayoutInflater inflater,ViewGroup container,Bundle savedInstanceState){
        super.onCreateView(inflater, container, savedInstanceState);
        final View view_gs = inflater.inflate(R.layout.gs_1, container, false);
        CircleIndicator indicator = (CircleIndicator)view_gs.findViewById(R.id.indicator);
        TextView skip=(TextView)view_gs.findViewById(R.id.skip);
        skip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences get_started=getContext().getSharedPreferences("GET_STARTED",0);
                SharedPreferences.Editor edit=get_started.edit();
                edit.putBoolean("viewed",true);
                edit.apply();
                Intent l=new Intent(getContext(),Login.class);
                startActivity(l);
            }
        });
        return view_gs;
    }
}
