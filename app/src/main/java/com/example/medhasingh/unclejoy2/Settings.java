package com.example.medhasingh.unclejoy2;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.github.siyamed.shapeimageview.CircularImageView;

/**
 * Created by medha singh on 7/17/2016.
 */
public class Settings extends AppCompatActivity{
    ListView list;
    String[] web = {
            "Profile",
            "FAQ",
            "About us",
            "Contact Us"

    } ;
    Integer[] imageId = {
            R.drawable.pf,
            R.drawable.help,
            R.drawable.abtus,
            R.drawable.phone,
    };
    private int CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE=1;
    private int PICKFILE_REQUEST_CODE=2;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings);
        list=(ListView)findViewById(R.id.listview);
        CustomList adapter=new CustomList(Settings.this,web,imageId);
        list.setAdapter(adapter);

        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
               if(position==0){
                   Intent in =new Intent(getApplicationContext(),Profile.class);
                   startActivity(in);
               }
            }
        });

    }
}
