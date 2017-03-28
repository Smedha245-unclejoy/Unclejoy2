package com.example.medhasingh.unclejoy2;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ViewFlipper;

import com.android.volley.*;
import com.android.volley.toolbox.JsonArrayRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Handler;
import java.util.logging.LogRecord;

/**
 * Created by medha singh on 7/25/2016.
 */
public class Pictures extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener {
    private static final String TAG = Pictures.class.getSimpleName();

    // Movies json url
    private List<Image_attr> listSuperHeroes;
    private ProgressDialog pDialog;
    private RecyclerView.LayoutManager layoutManager;
    RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private SwipeRefreshLayout swipeRefreshLayout;
    private ViewFlipper flipper;
    String url="https://ujapi.herokuapp.com/api/v1/u/bookings/responded";
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pictures);
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView_pic);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        listSuperHeroes = new ArrayList<>();
        recyclerView.setLayoutManager(layoutManager);
        swipeRefreshLayout=(SwipeRefreshLayout)findViewById(R.id.swipe_refresh_pic);
        swipeRefreshLayout.setOnRefreshListener(this);
        flipper=(ViewFlipper)findViewById(R.id.flip_me_pic);
        if(listSuperHeroes.size()==0){
            flipper.setDisplayedChild(1);
        }
        else {flipper.setDisplayedChild(0);}

        pDialog = new ProgressDialog(this);
        // Showing progress dialog before making http request
        pDialog.setMessage("Loading...");
        pDialog.show();
        adapter = new CardAdapter6(listSuperHeroes, this);
        adapter.notifyDataSetChanged();
        recyclerView.setAdapter(adapter);
        Intent e=getIntent();
        int p=e.getIntExtra("Position",0);
        System.out.println("POSITION_PICTURE:"+p);
        gettourl(p);

    }
    public void gettourl(final int p){
        new Thread(new Runnable() {
            @Override
            public void run() {


        JsonArrayRequest movieReq = new JsonArrayRequest(url,
                new com.android.volley.Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        Log.d(TAG, response.toString());
                        hidePDialog();
                       parsedata(response,p);

                        // Parsing json
                       /* for (int i = 0; i < response.length(); i++) {
                            try {

                                JSONObject obj = response.getJSONObject(i);
                                Image_attr movie = new Image_attr();

                                movie.setThumbnailUrl(obj.getString("image"));
                                // Genre is json array

                                // adding movie to movies array
                                movieList.add(movie);

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                        }*/

                        // notifying list adapter about data changes
                        // so that it renders the list view with updated data
                        adapter.notifyDataSetChanged();
                    }
                }, new com.android.volley.Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d(TAG, "Error: " + error.getMessage());
                hidePDialog();

            }
        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                super.getHeaders();
                HashMap<String, String> headers = new HashMap<String, String>();
                SharedPreferences sharedPreferences=Pictures.this.getSharedPreferences(Config.SHARED_PREF_NAME,0);

                headers.put("Content-Type", "application/json; charset=utf-8");
                headers.put("Authorization", sharedPreferences.getString(Config.TOKEN_SHARED_PREF,""));
                return headers;
            }

        };

        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(movieReq);
       /* listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent=new Intent(WelcomeFreshers.this,ViewFullImage.class);
                intent.putExtra("position",position);
                startActivity(intent);
            }
        });*/
            }
        }).start();
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        hidePDialog();
    }

    private void hidePDialog() {
        if (pDialog != null) {
            pDialog.dismiss();
            pDialog = null;
        }
    }

    @Override
    public void onRefresh() {
        listSuperHeroes.clear();
        Intent e=getIntent();
        int p=e.getIntExtra("Position",0);
        gettourl(p);
        swipeRefreshLayout.setRefreshing(false);
    }
    public void parsedata(JSONArray picture_array,int p){
        try {

            JSONObject obj=picture_array.getJSONObject(p);
            Image_attr movie = new Image_attr();
            JSONArray arr=obj.getJSONArray("attachments");
            for(int i=0;i<arr.length();i++){
                movie.setThumbnailUrl(arr.getJSONObject(i).getJSONObject("attachment").getJSONObject("attachment").getString("url"));
                System.out.println(arr.getJSONObject(i).getJSONObject("attachment").getJSONObject("attachment").getString("url"));
                listSuperHeroes.add(movie);
            }

            adapter.notifyDataSetChanged();
            adapter = new CardAdapter6(listSuperHeroes, this);

            //Adding adapter to recyclerview
            recyclerView.setAdapter(adapter);
            if(listSuperHeroes.size()!=0){
                flipper.setDisplayedChild(0);
            }else {flipper.setDisplayedChild(0);}
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
    public void onBackPressed(){
       super.onBackPressed();
        listSuperHeroes.clear();
    }
}

