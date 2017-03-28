package com.example.medhasingh.unclejoy2;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.*;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.apache.http.HttpStatus;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by medha singh on 7/20/2016.
 */
public class OTP extends AppCompatActivity {
    EditText otp;
    Button verify;
    TextView resend;
    ArrayList<String> detail2=new ArrayList<String>();
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.otp);
        otp=(EditText)findViewById(R.id.otp);
        verify=(Button)findViewById(R.id.verify);
        Intent e=getIntent();
        final String id=e.getStringExtra("id");
        verify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                senddata(id);
            }
        });
       resend=(TextView) findViewById(R.id.resend);
        resend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if ((!otp.getText().toString().equals(""))) {
                    Toast.makeText(OTP.this, "Please enter the otp number", Toast.LENGTH_SHORT).show();
                }else {
                sendagain(id);}
            }
        });

    }
    public void senddata(final String a){
        final ProgressDialog pdialog=new ProgressDialog(this);
        pdialog.setTitle("Please wait");
        pdialog.setMessage("Loading..");
        pdialog.show();


        String otp_no= otp.getText().toString();
        JSONObject js =new JSONObject();
        try {
            JSONObject jsonobject_one = new JSONObject();
            jsonobject_one.put("otp", otp_no);
            jsonobject_one.put("id",a );
            js.put("user",jsonobject_one);
            System.out.println(js.toString());

        }catch (JSONException e) {
            e.printStackTrace();
        }
         final NetworkResponse networkResponse=null;
        JsonObjectRequest jsonObjReq2 = new JsonObjectRequest(Request.Method.POST,
                "https://ujapi.herokuapp.com/api/v1/u/users/verify", js,
                new com.android.volley.Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        pdialog.dismiss();

                          System.out.println(response.toString());
                        Intent intent = new Intent(OTP.this, activity_navigation.class);
                        startActivity(intent);}

                    }
                , new com.android.volley.Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                pdialog.dismiss();
                VolleyLog.d("Medha", "Error: " + error.getMessage());
                NetworkResponse networkResponse = error.networkResponse;
                if (networkResponse != null && networkResponse.statusCode == 422) {
                    otp.setError("Please enter a valid otp");
                    // HTTP Status Code: 401 Unauthorized
                }
                //System.out.println(error.networkResponse.statusCode);


            }}){

            /**
             * Passing some request headers
             */
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<String, String>();
                headers.put("Content-Type", "application/json; charset=utf-8");
                return headers;
            }};
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(jsonObjReq2);


    }
    public void sendagain(final String b){


        JSONObject jsonobject_one =new JSONObject();
        try {
            jsonobject_one.put("id", b);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        System.out.println(jsonobject_one.toString());



        JsonObjectRequest jsonObjReq2 = new JsonObjectRequest(Request.Method.POST,
                "https://ujapi.herokuapp.com/api/v1/u/users/resend", jsonobject_one,
                new com.android.volley.Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        System.out.println(response.toString());

                        Toast.makeText(getApplicationContext(),"OTP has been resent to your provided mobile number",Toast.LENGTH_SHORT).show();

                    }}
                , new com.android.volley.Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d("Medha", "Error: " + error.getMessage());
                //System.out.println(error.networkResponse.statusCode);



            }}){

            /**
             * Passing some request headers
             */
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<String, String>();
                headers.put("Content-Type", "application/json; charset=utf-8");
                return headers;
            }};
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(jsonObjReq2);
    }
}
