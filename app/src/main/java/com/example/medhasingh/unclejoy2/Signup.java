package com.example.medhasingh.unclejoy2;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import com.android.volley.*;
import com.android.volley.Response;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.apache.http.HttpStatus;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import nl.changer.polypicker.ImagePickerActivity;

/**
 * Created by medha singh on 6/5/2016.
 */
public class Signup extends AppCompatActivity{

    public static final String SIGNUP_URL = "https://ujapi.herokuapp.com/api/v1/u/users";

    private EditText editTextUsername;
    private EditText editTextPassword;
    private EditText editTextName;
    private Button buttonsign;
    private EditText mobile_no;
    private String username;
    private String password;
    private String name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signup);
        TextView tv=(TextView)findViewById(R.id.log_sign);
        mobile_no=(EditText)findViewById(R.id.mobile);
        editTextUsername = (EditText) findViewById(R.id.email2);
        editTextPassword = (EditText) findViewById(R.id.password2);
        editTextName=(EditText)findViewById(R.id.name2);
           buttonsign=(Button)findViewById(R.id.sign);
        tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent ii=new Intent(getApplicationContext(),Login.class);
                  startActivity(ii);
            }
        });
        buttonsign.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ConnectionDetector cd = new ConnectionDetector(getApplicationContext());
                Boolean isInternetPresent = cd.isConnectingToInternet();
                if (isInternetPresent) {
                    if ((!editTextUsername.getText().toString().equals("")) && ((!editTextPassword.getText().toString().equals(""))) && (!editTextName.getText().toString().equals("")) && (!mobile_no.getText().toString().equals(""))) {
                        userSign();
                    } else if ((!editTextPassword.getText().toString().equals("")) || (editTextPassword.getText().toString().length()<8)) {
                        Toast.makeText(Signup.this, "Password must be greater than or equal to 8 characters", Toast.LENGTH_SHORT).show();
                    } else if (!editTextName.getText().toString().equals("")) {
                        Toast.makeText(Signup.this, "Name field empty", Toast.LENGTH_SHORT).show();
                    } else if (!editTextUsername.getText().toString().equals("")) {
                        Toast.makeText(Signup.this, "Email field empty", Toast.LENGTH_SHORT).show();
                    }else if (!mobile_no.getText().toString().equals("")) {
                        Toast.makeText(Signup.this, "Mobile number field empty", Toast.LENGTH_SHORT).show();
                    }  else {
                        Toast.makeText(Signup.this, "Email ,Name,Mobile and Password field empty", Toast.LENGTH_SHORT).show();
                    }

                    // Internet Connection is Present
                    // make HTTP requests
                    //showAlertDialog(activity_navigation.this, "Internet Connection",
                    // "You have internet connection", true);

                } else {
                    showAlertDialog(Signup.this,"Signup","Please turn on your Wi-fi or Mobile data first and then try to Signup.Right now,the application can't connect to server",false);
                }
            }
        });

    }



    private void userSign() {
        final ProgressDialog pdialog=new ProgressDialog(this);
        pdialog.setTitle("Please wait");
        pdialog.setMessage("Loading..");
        pdialog.show();
        String tag_json_obj="user";
        final String mobile=mobile_no.getText().toString();
        name=editTextName.getText().toString().trim();
        username = editTextUsername.getText().toString().trim();
        password = editTextPassword.getText().toString().trim();
        new Thread(new Runnable() {
            @Override
            public void run() {
        Map<String, String> postParam= new HashMap<String, String>();
        JSONObject js =new JSONObject();
        try {
            JSONObject jsonobject_one = new JSONObject();
            jsonobject_one.put("name", name);
            jsonobject_one.put("email", username);
            jsonobject_one.put("password", password);
            jsonobject_one.put("mobile","+91"+mobile);

            js.put("user",jsonobject_one);

        }catch (JSONException e) {
            e.printStackTrace();
        }

        JsonObjectRequest jsonObjReq2 = new JsonObjectRequest(Request.Method.POST,
                "https://ujapi.herokuapp.com/api/v1/u/users", js,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        pdialog.dismiss();
                        System.out.println(response.toString());

                        SharedPreferences sharedPreferences=Signup.this.getSharedPreferences(Config.SHARED_PREF_NAME,Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.putString(Config.EMAIL_SHARED_PREF, username);
                        editor.putString(Config.SHARED_USER_NAME,name);
                        try {
                            editor.putString(Config.SHARED_USER_ID,response.getString("id"));
                            editor.putString(Config.TOKEN_SHARED_PREF, response.getString("authentication_token"));
                            editor.apply();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        //   SharedPreferences.Editor editor = sharedPreferences.edit();
                          //  editor.putBoolean(Config.SIGNED_UP,true);
                       // editor.commit();


                        try {
                           String id= response.getString("id");
                            System.out.println(response.getString("id"));
                            Intent intent = new Intent(Signup.this, OTP.class);
                            intent.putExtra("id",id);
                            startActivity(intent);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }



                    }}
                , new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                pdialog.dismiss();
                VolleyLog.d("Medha", "Error: " + error.getMessage());

                NetworkResponse networkResponse = error.networkResponse;
                if (networkResponse != null && networkResponse.statusCode == 422) {
                    Toast.makeText(getApplicationContext(),"Your entered credentials is not correct. !!",Toast.LENGTH_SHORT).show();
                    // HTTP Status Code: 401 Unauthorized
                }else{
                    Toast.makeText(getApplicationContext(),"Either Internet connection is very slow or there is no internet connection",Toast.LENGTH_SHORT).show();
                }


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
        RequestQueue requestQueue = Volley.newRequestQueue(Signup.this);
        requestQueue.add(jsonObjReq2);
            }
        }).start();

    }




                // case R.id.log_sign:
                //   Intent ii=new Intent(getApplicationContext(),Login.class);
                //  startActivity(ii);


    protected void onResume(){
        super.onResume();

    }
    public void showAlertDialog(Context context, String title, String message, Boolean status) {
        android.app.AlertDialog alertDialog = new android.app.AlertDialog.Builder(context).create();

        // Setting Dialog Title
        alertDialog.setTitle(title);

        // Setting Dialog Message
        alertDialog.setMessage(message);

        // Setting alert dialog icon

        // Setting OK Button
        alertDialog.setButton("OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
            }
        });

        // Showing Alert Message
        alertDialog.show();
    }
}
