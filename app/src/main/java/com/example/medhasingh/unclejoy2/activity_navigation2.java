package com.example.medhasingh.unclejoy2;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

/**
 * Created by medha singh on 6/14/2016.
 */
public class activity_navigation2 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_activity_navigation2);
        Intent ii=getIntent();
        TextView em=(TextView)findViewById(R.id.textView2);
        if(ii.hasExtra("key")) {
            em.setText(ii.getStringExtra("key"));
        }

    }
}
