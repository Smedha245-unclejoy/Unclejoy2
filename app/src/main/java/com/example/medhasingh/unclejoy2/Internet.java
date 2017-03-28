package com.example.medhasingh.unclejoy2;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

/**
 * Created by medha singh on 8/6/2016.
 */
public class Internet extends AppCompatActivity {
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.no_internet);
        Button no=(Button)findViewById(R.id.no_internet);
        no.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent ee=new Intent(getApplicationContext(),activity_navigation.class);
                startActivity(ee);
            }
        });
    }
    public void onBackPressed(){
        super.onBackPressed();
        moveTaskToBack(true);
    }
}
