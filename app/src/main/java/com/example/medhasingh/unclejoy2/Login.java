package com.example.medhasingh.unclejoy2;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.apache.http.HttpStatus;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by medha singh on 6/5/2016.
 */
public class Login extends AppCompatActivity{
    private boolean loggedIn = false;
    public static final String LOGIN_URL = "https://ujapi.herokuapp.com/api/v1/u/login";

    public static final String KEY_USERNAME="username";
    public static final String KEY_PASSWORD="password";

    private EditText editTextUsername;
    private EditText editTextPassword;
    private TextView forgot;
    private Button buttonLogin;
    private TextView sign_up;

    private String username;
    private String password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        editTextUsername = (EditText) findViewById(R.id.email);
        editTextPassword = (EditText) findViewById(R.id.password);

        buttonLogin = (Button) findViewById(R.id.login);
        sign_up=(TextView)findViewById(R.id.signup_login);
        forgot=(TextView)findViewById(R.id.forgotpasword);
        buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ConnectionDetector cd = new ConnectionDetector(getApplicationContext());
                Boolean isInternetPresent = cd.isConnectingToInternet();
                if (isInternetPresent) {
                    if((!editTextUsername.getText().toString().equals("")) && (!editTextPassword.getText().toString().equals(""))) {
                       userLogin();
                    }else if((!editTextUsername.getText().toString().equals(""))){
                        Toast.makeText(Login.this, "Password field empty", Toast.LENGTH_SHORT).show();
                    }
                    else if ((!editTextPassword.getText().toString().equals(""))) {
                        Toast.makeText(Login.this, "Email field empty", Toast.LENGTH_SHORT).show();
                    }else {
                        Toast.makeText(Login.this,"Email and Password field empty",Toast.LENGTH_SHORT).show();
                    }
                    // Internet Connection is Present
                    // make HTTP requests
                    //showAlertDialog(activity_navigation.this, "Internet Connection",
                    // "You have internet connection", true);

                } else {
                   showAlertDialog(Login.this,"Login","Please turn on your Wi-fi or Mobile data first and then try to Login.Right now,the application can't connect to server",false);
                    // Internet connection is not present
                    // Ask user to connect to Internet
                    //showAlertDialog(activity_navigation.this, "No Internet Connection",
                    // "Please turn on your internet connection.", false);
                    // Intent inti=new Intent(getApplicationContext(),offline.class);
                    //startActivity(inti);
                }

            }
        });
        sign_up.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent ii=new Intent(getApplicationContext(),Signup.class);
                startActivity(ii);
            }
        });
        forgot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                    Intent in=new Intent(getApplicationContext(),ForgotPassword.class);
                startActivity(in);

            }
        });
    }



    private void userLogin() {

        final ProgressDialog pdialog=new ProgressDialog(this);
        pdialog.setTitle("Please wait");
        pdialog.setMessage("Loading..");
        pdialog.show();
        username = editTextUsername.getText().toString().trim();
        password = editTextPassword.getText().toString().trim();

        String tag_json_obj="session";
        JSONObject js=new JSONObject();
        JSONObject json=new JSONObject();
        try {
            json.put("email", username);
            json.put("password",password);
            js.put("session",json);
            System.out.println(js.toString());

        } catch (JSONException e) {
            e.printStackTrace();
        }
       /* username = editTextUsername.getText().toString().trim();
        password = editTextPassword.getText().toString().trim();
        Map<String, String> postParam= new HashMap<String, String>();
        postParam.put("email", username);
        postParam.put("password", password);*/
        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.POST,
                LOGIN_URL, js,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        System.out.println(response.toString());
                       pdialog.dismiss();

                        SharedPreferences sharedPreferences = Login.this.getSharedPreferences(Config.SHARED_PREF_NAME, Context.MODE_PRIVATE);
                        //Creating editor to store values to shared preferences
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        //Adding values to editor
                       editor.putBoolean(Config.LOGGEDIN_SHARED_PREF, true);
                        editor.putString(Config.EMAIL_SHARED_PREF, username);
                        //Saving values to editor
                        try {
                            editor.putString(Config.TOKEN_SHARED_PREF, response.getString("authentication_token"));
                            editor.putString(Config.SHARED_USER_NAME,response.getString("name"));
                            editor.putString(Config.SHARED_USER_ID,response.getString("id"));
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }     editor.clear();

                            editor.apply();



                        //Starting profile activity
                        Intent intent = new Intent(Login.this, activity_navigation.class);
                        startActivity(intent);

                    }}
                , new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                pdialog.dismiss();
                VolleyLog.d("Medha", "Error: " + error.getMessage());
                NetworkResponse networkResponse = error.networkResponse;
                if (networkResponse != null && networkResponse.statusCode == 422) {
                    Toast.makeText(getApplicationContext(),"The email address and password entered did't match !!",Toast.LENGTH_SHORT).show();
                    // HTTP Status Code: 401 Unauthorized
                }
                if (networkResponse != null && networkResponse.statusCode == 417) {
                    Toast.makeText(getApplicationContext(),"You are not a verified user.Please signup and verify using Otp first",Toast.LENGTH_SHORT).show();
                    // HTTP Status Code: 401 Unauthorized
                } if (networkResponse != null && networkResponse.statusCode == 500) {
                    Toast.makeText(getApplicationContext(),"The email address and password entered did't match !!",Toast.LENGTH_SHORT).show();
                    // HTTP Status Code: 401 Unauthorized
                }else{
                    Toast.makeText(getApplicationContext(),"Either internet speed is very slow or there is no internet connection",Toast.LENGTH_SHORT).show();
                }


               // if(error.networkResponse.statusCode!=0){
                   /* if(error.networkResponse.statusCode==422){
                        Toast.makeText(Login.this,"Please check the email id and password you have entered,they don't match each other",Toast.LENGTH_SHORT).show();
                    }
                if(error.networkResponse.statusCode==417){
                    Toast.makeText(Login.this,"Please verify your email id and password after signup",Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(Login.this,"Some other problem",Toast.LENGTH_SHORT).show();
                }*/
               // }

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



        RequestQueue requestQueue = Volley.newRequestQueue(Login.this);
        requestQueue.add(jsonObjReq);

    }

    private void openProfile(){
        Intent intent = new Intent(this, activity_navigation.class);
        intent.putExtra(KEY_USERNAME, username);
        startActivity(intent);
    }



    @Override
    protected void onResume(){
        super.onResume();
        SharedPreferences sharedPreferences = Login.this.getSharedPreferences(Config.SHARED_PREF_NAME, Context.MODE_PRIVATE);
        if(sharedPreferences.getBoolean(Config.LOGGEDIN_SHARED_PREF,false)==true){
            Intent l=new Intent(getApplicationContext(),activity_navigation.class);
            startActivity(l);
        }


    }
    public void showAlertDialog(Context context, String title, String message, Boolean status) {
        AlertDialog alertDialog = new AlertDialog.Builder(context).create();

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
    public void onBackPressed(){
        super.onBackPressed();
        moveTaskToBack(true);
    }

    }

















