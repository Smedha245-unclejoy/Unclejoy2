package com.example.medhasingh.unclejoy2;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.*;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.apache.http.HttpStatus;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by medha singh on 6/12/2016.
 */
public class ForgotPassword extends AppCompatActivity{
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.forgot_password);
        Button forgot=(Button)findViewById(R.id.rest_pass);
        final EditText em=(EditText) findViewById(R.id.email_forgot);

        forgot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if ((!em.getText().toString().equals(""))) {
                    Toast.makeText(ForgotPassword.this, "Please enter your Email Address ", Toast.LENGTH_SHORT).show();
                }else {
                    String email=em.getText().toString();
                    resetpassword(email);}

            }
        });
    }
    public void resetpassword(String a){
        final ProgressDialog pdialog=new ProgressDialog(this);
        pdialog.setTitle("Please wait");
        pdialog.setMessage("Loading..");
        pdialog.show();
        String url="https://ujapi.herokuapp.com/api/v1/u/password/reset";
            JSONObject jsd=new JSONObject();
        try {
            jsd.put("email",a);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.POST,
                    url,jsd,
                    new com.android.volley.Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            pdialog.dismiss();
                            System.out.println(response.toString());
                            Intent i=new Intent(getApplicationContext(),Login.class);
                            startActivity(i);
                        }}
                    , new com.android.volley.Response.ErrorListener() {

                @Override
                public void onErrorResponse(VolleyError error) {
                    pdialog.dismiss();
                    VolleyLog.d("Medha", "Error: " + error.getMessage());
                    NetworkResponse networkResponse = error.networkResponse;
                    if (networkResponse != null && networkResponse.statusCode == 422) {
                        Toast.makeText(getApplicationContext(),"Please enter a valid email address !!",Toast.LENGTH_SHORT).show();
                        // HTTP Status Code: 401 Unauthorized
                    }

                }}){

                /**
                 * Passing some request headers
                 */
                @Override
                public Map<String, String> getHeaders() throws AuthFailureError {
                    HashMap<String, String> headers = new HashMap<String, String>();
                    headers.put("Content-Type", "application/json");
                    return headers;
                }};



            RequestQueue requestQueue = Volley.newRequestQueue(ForgotPassword.this);
            requestQueue.add(jsonObjReq);

    }

}
