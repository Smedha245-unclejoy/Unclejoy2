package com.example.medhasingh.unclejoy2;



import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;
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
public class Login extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        final EditText emaile = (EditText) findViewById(R.id.email);
        final EditText passwordl = (EditText) findViewById(R.id.password);
        final Button login = (Button) findViewById(R.id.login);
        TextView signup = (TextView) findViewById(R.id.signup_login);
        ImageButton google_sign = (ImageButton) findViewById(R.id.google);
        ImageButton fb_sign = (ImageButton) findViewById(R.id.fb);
        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getApplicationContext(),Signup.class);
                startActivity(intent);
            }
        });

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if ((!emaile.getText().toString().equals(""))&&((!passwordl.getText().toString().equals("")))) {
                    final String username = emaile.getText().toString();
                    final String password2 = passwordl.getText().toString();
                    Loginverify loginverify=new Loginverify(username,password2);
                    loginverify.execute(username, password2);

                    } else if ((!emaile.getText().toString().equals(""))) {
                    Toast.makeText(Login.this, "Password field empty", Toast.LENGTH_SHORT).show();
                } else if ((!passwordl.getText().toString().equals(""))) {
                    Toast.makeText(Login.this, "Email field empty", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(Login.this, "Email and Password field empty", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    public class Loginverify extends AsyncTask<String, Integer, String> {
        String a="success";
        ProgressDialog progressDialog;
        String b;
        String url="https://ujapi.herokuapp.com/api/v1/login";
        public Loginverify(String a,String b){
            this.a=a;
            this.b=b;

        }
          protected void onPreExecute(){
              progressDialog.setMessage("Authenticating ...");
              progressDialog.isIndeterminate();
          }

        @Override
        protected String doInBackground(String... params) {
            String s;
            int status = 0;
            HttpClient client = new DefaultHttpClient();
            //HttpConnectionParams.setConnectionTimeout(client.getParams(), 10000); //Timeout Limit
            HttpResponse response;
            String success="Logged in";

            try {
                HttpPost post = new HttpPost(url);
                JSONObject session1=new JSONObject();
                try {
                    session1.put(("email"), params[0]);
                    session1.put("password", params[1]);
                }catch (JSONException e){
                    e.printStackTrace();
                }

                JSONObject json = new JSONObject();
                json.put("session", session1);
                StringEntity se = new StringEntity(json.toString());
                se.setContentType(new BasicHeader(HTTP.CONTENT_TYPE, "application/json"));
                post.setEntity(se);
                response = client.execute(post);
                 status=response.getStatusLine().getStatusCode();
                if(response.getStatusLine().getStatusCode() == 200){
                    System.out.println(response.getStatusLine().getStatusCode());
                    return params[0];



                }else {
                    System.out.println(response.getStatusLine().getStatusCode());
                }

            } catch(Exception e) {
                e.printStackTrace();
            }
            return null;
        }
        protected void onPostExecute(String a){
            Intent ii=new Intent(getApplicationContext(),activity_navigation.class);
            ii.putExtra("key",a);
            startActivity(ii);
            finish();
        }


    }

}















