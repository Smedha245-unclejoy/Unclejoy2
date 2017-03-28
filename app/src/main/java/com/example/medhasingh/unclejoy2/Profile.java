package com.example.medhasingh.unclejoy2;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v7.app.AppCompatActivity;

import android.support.v7.widget.Toolbar;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.*;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.github.siyamed.shapeimageview.CircularImageView;
import com.github.siyamed.shapeimageview.mask.PorterShapeImageView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by medha singh on 7/23/2016.
 */
public class Profile extends AppCompatActivity{
    EditText ed1;
    EditText ed2;
    EditText ed3;
    ImageView circle;
    private String name;
    private String pass;
    private String phone;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profile);
        ed1=(EditText)findViewById(R.id.edit_name);
        ed2=(EditText)findViewById(R.id.edit_phone);
        ed3=(EditText)findViewById(R.id.edit_pass);
        name=ed1.getText().toString();
        /*Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(getResources().getDrawable(R.drawable.ic_action_navigation_arrow_back));
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v2) {
                onBackPressed();
            }
        });*/

        ImageView circle=(ImageView)findViewById(R.id.pf);
        circle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent,
                                "Choose Photo"),
                        1);
            }
        });
        Button btn=(Button)findViewById(R.id.update_pf);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
             updateprofile();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && data != null
                && data.getData() != null) {
            Uri _uri = data.getData();
            try {
                Toast.makeText(getApplicationContext(), "yo i am here!!", Toast.LENGTH_SHORT).show();
                Bitmap profileBmp = MediaStore.Images.Media.getBitmap(getContentResolver(), _uri);
                ImageView v=(ImageView)findViewById(R.id.pf);
                //if (profileBmp != null) {
                    v.setImageBitmap(profileBmp);
                //File file = new File(_uri.getPath());
                //Bitmap bitmap = decodeFile(file);
                String base = converttobase64(profileBmp);
                System.out.println(base);
                SharedPreferences sharing = this.getSharedPreferences("UPDATE", 0);
                SharedPreferences.Editor edit = sharing.edit();
                edit.putString("pic", base);
                edit.apply();
               // }

            } catch (OutOfMemoryError e) {
                e.printStackTrace();
            } catch (Exception e) {

            }
        }


                        /*Uri imageUri = data.getData();
                        InputStream imageStream = getContentResolver().openInputStream(imageUri);
                        Bitmap selectedImage = BitmapFactory.decodeStream(imageStream);
                        circle.setImageBitmap(selectedImage);
                        File file = new File(imageUri.getPath());
                        Bitmap bitmap = decodeFile(file);
                        String base = converttobase64(bitmap);
                        SharedPreferences sharing = this.getSharedPreferences("UPDATE", 0);
                        SharedPreferences.Editor edit = sharing.edit();
                        edit.putString("pic", base);
                        edit.apply();
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();

                    }*/


    }
    private Bitmap decodeFile(File f) {
        try {
            // Decode image size
            BitmapFactory.Options o = new BitmapFactory.Options();
            o.inJustDecodeBounds = true;
            BitmapFactory.decodeStream(new FileInputStream(f), null, o);

            // The new size we want to scale to
            final int REQUIRED_SIZE=70;

            // Find the correct scale value. It should be the power of 2.
            int scale = 1;
            while(o.outWidth / scale / 2 >= REQUIRED_SIZE &&
                    o.outHeight / scale / 2 >= REQUIRED_SIZE) {
                scale *= 2;
            }

            // Decode with inSampleSize
            BitmapFactory.Options o2 = new BitmapFactory.Options();
            o2.inSampleSize = scale;
            return BitmapFactory.decodeStream(new FileInputStream(f), null, o2);
        } catch (FileNotFoundException e) {}
        return null;
    }
    public String converttobase64(Bitmap bmp){
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.JPEG, 70, baos);
        byte[] imageBytes = baos.toByteArray();
        String encodedImage = Base64.encodeToString(imageBytes, Base64.DEFAULT);
        return encodedImage;
    }
    public void updateprofile(){
         name=ed1.getText().toString();
         phone=ed2.getText().toString();
         pass=ed3.getText().toString();
        final SharedPreferences sharedPreferences=this.getSharedPreferences(Config.SHARED_PREF_NAME, 0);
        SharedPreferences sharing=this.getSharedPreferences("UPDATE",0);
        String URL="https://ujapi.herokuapp.com/api/v1/u/users/"+sharedPreferences.getString(Config.SHARED_USER_ID,"");
        String tag_json_obj="user";
        JSONObject js=new JSONObject();
        JSONObject json=new JSONObject();
        try {
            json.put("name", name);
            json.put("password",pass);
            json.put("mobile","91"+phone);
            json.put("picture","data:image/jpeg;base64,"+sharing.getString("pic",""));
            js.put("user",json);
            System.out.println(js.toString());

        } catch (JSONException e) {
            e.printStackTrace();
        }
       /* username = editTextUsername.getText().toString().trim();
        password = editTextPassword.getText().toString().trim();
        Map<String, String> postParam= new HashMap<String, String>();
        postParam.put("email", username);
        postParam.put("password", password);*/
        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.PUT,
                URL, js,
                new com.android.volley.Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        System.out.println(response.toString());
                        Toast.makeText(getApplicationContext(),"Profile Updated",Toast.LENGTH_SHORT);



                    }}
                , new com.android.volley.Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {

                VolleyLog.d("Medha", "Error: " + error.getMessage());
                // if(error.networkResponse.statusCode==500){
                //   Toast.makeText(Login.this,"Please check the email id and password entered",Toast.LENGTH_SHORT).show();
                // }

            }}){

            /**
             * Passing some request headers
             */
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<String, String>();
                headers.put("Content-Type", "application/json");
                headers.put("Authorization", sharedPreferences.getString(Config.TOKEN_SHARED_PREF, ""));
                return headers;
            }};



        RequestQueue requestQueue = Volley.newRequestQueue(Profile.this);
        requestQueue.add(jsonObjReq);
    }
    }

