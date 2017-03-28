package com.example.medhasingh.unclejoy2;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.NavUtils;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.GestureDetector;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;
import android.widget.ViewFlipper;

import com.android.volley.*;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by medha singh on 7/15/2016.
 */
public class Store_list extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener{
    private List<Response2> listSuperHeroes2;
    RecyclerView recyclerView2;
    RecyclerView.LayoutManager layoutManager2;
    RecyclerView.Adapter adapter2;
    private SwipeRefreshLayout swipeRefreshLayout;
    private ViewFlipper flipper;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.store_list);
        recyclerView2=(RecyclerView)findViewById(R.id.recyclerView6);
        recyclerView2.setHasFixedSize(true);
        layoutManager2 = new LinearLayoutManager(this);
        recyclerView2.setLayoutManager(layoutManager2);
       /* Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(getResources().getDrawable(R.drawable.ic_action_navigation_arrow_back));
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });*/
        swipeRefreshLayout=(SwipeRefreshLayout)findViewById(R.id.swipe_refresh_store);
        adapter2 = new CardAdapter3(listSuperHeroes2,getApplicationContext());
        recyclerView2.setAdapter(adapter2);
        listSuperHeroes2 = new ArrayList<>();
        flipper=(ViewFlipper)findViewById(R.id.flip_me_store);
        if(listSuperHeroes2.size()==0){
            flipper.setDisplayedChild(1);
        }
        else {flipper.setDisplayedChild(0);}
        SharedPreferences sharedPreferences =this.getSharedPreferences(Config.SHARED_PREF_NAME, Context.MODE_PRIVATE);
        final String token2 = sharedPreferences.getString(Config.TOKEN_SHARED_PREF, "");

        getstores(token2);
    }
    public void getstores(final String a){
        new Thread(new Runnable() {
            @Override
            public void run() {
        SharedPreferences sharedPreferences =Store_list.this.getSharedPreferences(Config.SHARED_PREF_NAME, Context.MODE_PRIVATE);
        String STORES_URL="https://ujapi.herokuapp.com/api/v1/u/bookings/"+sharedPreferences.getString(Config.SHARED_BOOKING_ID,"")+"/responded_stores";

        //Creating a json array request
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(STORES_URL,
                new com.android.volley.Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        parsedata2(response);
                        if(response.toString()==null){
                            flipper.setDisplayedChild(1);
                        }

                    }
                },
                new com.android.volley.Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                super.getHeaders();
                HashMap<String, String> headers = new HashMap<String, String>();

                headers.put("Content-Type", "application/json; charset=utf-8");
                headers.put("Authorization", a);
                return headers;
            }

        };
        RequestQueue requestQueue = Volley.newRequestQueue(Store_list.this);

        //Adding request to the queue
        requestQueue.add(jsonArrayRequest);
        adapter2 = new CardAdapter3(listSuperHeroes2, Store_list.this);

        recyclerView2.setLayoutManager(layoutManager2);
        //Adding adapter to recyclerview
        recyclerView2.setAdapter(adapter2);
        adapter2.notifyDataSetChanged();
        //Creating request queue
            }
        }).start();

    }
    public void parsedata2(JSONArray array){
        for (int i = 0; i < array.length(); i++) {
            Response2 superHero = new Response2();
            JSONObject json = null;
            try {

                json = array.getJSONObject(i);

                //superHero.setStorename(json.getJSONObject("user").getString("name"));
                //superHero.setStore_category(json.getJSONObject("store_category").getString("category"));
                superHero.setDiscount(json.getString("discount"));
                superHero.setCreated(json.getString("created_at"));
                superHero.setType(json.getJSONObject("store_category").getString("category"));
                superHero.setStore(json.getJSONObject("store").getString("store_name"));
                superHero.setLocation(json.getJSONObject("store").getString("address"));
                superHero.setIed(json.getString("store_id"));

                listSuperHeroes2.add(superHero);


            } catch (JSONException e) {
                e.printStackTrace();
            }


        }
        //Finally initializing our adapter
        if(listSuperHeroes2.size()!=0){
            flipper.setDisplayedChild(0);
        }else {flipper.setDisplayedChild(0);}
        adapter2 = new CardAdapter3(listSuperHeroes2, this);
        recyclerView2.setLayoutManager(layoutManager2);
        //Adding adapter to recyclerview
        recyclerView2.setAdapter(adapter2);
    }

    @Override
    public void onRefresh() {
        listSuperHeroes2.clear();
        SharedPreferences sharedPreferences=getSharedPreferences(Config.SHARED_PREF_NAME,Context.MODE_PRIVATE);
        getstores(sharedPreferences.getString(Config.TOKEN_SHARED_PREF,""));
        swipeRefreshLayout.setRefreshing(false);
    }






}
