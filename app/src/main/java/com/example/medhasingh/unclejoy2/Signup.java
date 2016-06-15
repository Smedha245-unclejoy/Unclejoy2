package com.example.medhasingh.unclejoy2;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicHeader;
import org.apache.http.protocol.HTTP;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by medha singh on 6/5/2016.
 */
public class Signup extends AppCompatActivity {
    EditText name;
    EditText email;
    EditText password;
    EditText phnumber;
    Button signup;
    private static String KEY_SUCCESS = "success";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signup);
        name=(EditText)findViewById(R.id.name);
        email=(EditText)findViewById(R.id.email_sign);
        password=(EditText)findViewById(R.id.password);
        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               String user_name=name.getText().toString();
                String user_email=email.getText().toString();
                String user_pass=password.getText().toString();

                 if(user_name.isEmpty()|| user_name.length()<3){
                    name.setError("atleast 3 characters");

                }else if(user_email.isEmpty()|| !Patterns.EMAIL_ADDRESS.matcher(user_email).matches()){
                    email.setError(null);
                }else if(user_pass.isEmpty() || user_pass.length()<8){
                    password.setError("Atleast 8 characters");
                }else{
                     Signup_verify verify=new Signup_verify(user_email,user_name,user_pass);
                     verify.execute(user_email, user_name, user_pass);
                 }
             }
        });



    }
    public class Signup_verify extends AsyncTask<String,String,String>{
        String a,b,c;
        public Signup_verify(String a,String b,String c){
           this.a=a;
            this.b=b;
            this.c=c;
        }

        @Override
        protected String doInBackground(String... params) {
            int status=0;
            String url="https://ujapi.herokuapp.com/api/v1/users";
            HttpClient client = new DefaultHttpClient();
            HttpResponse response;
            try {
                HttpPost post = new HttpPost(url);
                JSONObject session1=new JSONObject();
                try {
                    session1.put("name",params[1]);
                    session1.put("email", params[0]);
                    session1.put("password", params[2]);

                }catch (JSONException e){
                    e.printStackTrace();
                }

                JSONObject json = new JSONObject();
                json.put("user", session1);
                StringEntity se = new StringEntity(json.toString());
                se.setContentType(new BasicHeader(HTTP.CONTENT_TYPE, "application/json"));
                post.setEntity(se);
                response = client.execute(post);
                status=response.getStatusLine().getStatusCode();
                if(response.getStatusLine().getStatusCode() == 200){
                    System.out.println(response.getStatusLine().getStatusCode());
                    Intent m=new Intent(getApplicationContext(),Login.class);
                    startActivity(m);
                }else {
                    System.out.println(response.getStatusLine().getStatusCode());
                }

            } catch(Exception e) {
                e.printStackTrace();
            }
            return null;
        }
    }
}
