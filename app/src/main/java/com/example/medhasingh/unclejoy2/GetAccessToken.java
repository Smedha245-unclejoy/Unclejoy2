package com.example.medhasingh.unclejoy2;


import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by medha singh on 6/11/2016.
 */
public class GetAccessToken  {
    static InputStream is = null;
    static JSONObject jObj = null;
    String url="https://unclejoy.herokuapp.com/api/v1/login";
    static String json = "";
    String username,password;
    public GetAccessToken(String username,String password){
        this.username=username;
        this.password=password;

    }
    List<NameValuePair> params = new ArrayList<NameValuePair>();
    Map<String, String> mapn;
    DefaultHttpClient httpClient;
    HttpPost httpPost;
    public String gettoken(String username, String password){
        try{
            httpClient=new DefaultHttpClient();
            HttpPost httpPost=new HttpPost(url);
            params.add(new BasicNameValuePair("email", username));
            params.add(new BasicNameValuePair("password", password));
            httpPost.setHeader("Content-Type", "application/json");
            httpPost.setEntity(new UrlEncodedFormEntity(params));
            HttpResponse httpResponse = httpClient.execute(httpPost);
            HttpEntity httpEntity = httpResponse.getEntity();
            is = httpResponse.getEntity().getContent();
            if (is!=null) {
                json = jObj.toString();
            }

        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return json;
        }


    }




