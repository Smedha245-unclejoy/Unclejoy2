package com.example.medhasingh.unclejoy2;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.UiThread;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
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
 * Created by medha singh on 6/15/2016.
 */
public class Fragment2 extends Fragment implements LoaderManager.LoaderCallbacks<List<Response>>, SwipeRefreshLayout.OnRefreshListener {
    private List<Response> listSuperHeroes;
    String RESPOND_URL = "https://ujapi.herokuapp.com/api/v1/u/bookings/responded";
    public static String count = "0";

    //Creating Views
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private RecyclerView.Adapter adapter;
    private SwipeRefreshLayout swipeRefreshLayout;
    private ViewFlipper flipper;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        final View viewn = inflater.inflate(R.layout.responded, container, false);
        recyclerView = (RecyclerView) viewn.findViewById(R.id.recyclerView5);
        swipeRefreshLayout = (SwipeRefreshLayout) viewn.findViewById(R.id.swipe_refresh);
        swipeRefreshLayout.setOnRefreshListener(this);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        listSuperHeroes = new ArrayList<>();
        SharedPreferences sharedPreferences = this.getContext().getSharedPreferences(Config.SHARED_PREF_NAME, Context.MODE_PRIVATE);
        final String token2 = sharedPreferences.getString(Config.TOKEN_SHARED_PREF, "");
        flipper = (ViewFlipper) viewn.findViewById(R.id.flip_me);
        if (listSuperHeroes.size() == 0) {
            flipper.setDisplayedChild(1);
        } else {
            flipper.setDisplayedChild(0);
        }

        //Calling method to get data
        getdata(token2);
        return viewn;
    }

    private void getdata(final String a) {


        //Creating a json array request
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(RESPOND_URL,
                new com.android.volley.Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        parsedata(response);
                        if (response.toString() == null) {
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

        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        int socketTimeout = 200000;//30 seconds - change to what you want
        RetryPolicy policy = new DefaultRetryPolicy(socketTimeout, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
        jsonArrayRequest.setRetryPolicy(policy);

        //Adding request to the queue
        requestQueue.add(jsonArrayRequest);
        adapter = new CardAdapter2(listSuperHeroes, getContext());
        recyclerView.setLayoutManager(layoutManager);
        //Adding adapter to recyclerview
        recyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
        //Creating request queue
    }

    private void parsedata(JSONArray array) {
        JSONObject json = null;
        for (int i = 0; i < array.length(); i++) {
            final Response superHero = new Response();


            try {

                json = array.getJSONObject(i);
                final String id = String.valueOf(json.getString("id"));
                superHero.setBooking_id(id);

                // System.out.println("Color"+getcount(String.valueOf(json.getString("id"))));

                //superHero.setStorename(json.getJSONObject("user").getString("name"));
                //superHero.setStore_category(json.getJSONObject("store_category").getString("category"));
                superHero.setDate(json.getString("date"));
                superHero.setTime(json.getString("time"));
                superHero.setStorename(json.getJSONObject("user").getString("name"));
                superHero.setTimeelapsed(json.getString("created_at"));
                superHero.setStore_category(json.getJSONObject("store_category").getString("category"));
                superHero.setMap(json.getString("address"));
                listSuperHeroes.add(superHero);
                adapter.notifyDataSetChanged();

                if (listSuperHeroes.size() != 0) {
                    flipper.setDisplayedChild(0);
                } else {
                    flipper.setDisplayedChild(0);
                }


            } catch (JSONException e) {
                e.printStackTrace();
            }


        }

        //Finally initializing our adapter
        adapter = new CardAdapter2(listSuperHeroes, getActivity());
        recyclerView.setLayoutManager(layoutManager);
        //Adding adapter to recyclerview
        recyclerView.setAdapter(adapter);


        // recyclerView.invalidate();
    }

    @Override
    public Loader<List<Response>> onCreateLoader(int id, Bundle args) {
        return null;
    }

    @Override
    public void onLoadFinished(Loader<List<Response>> loader, List<Response> data) {

    }

    @Override
    public void onLoaderReset(Loader<List<Response>> loader) {

    }

    @Override
    public void onRefresh() {
        listSuperHeroes.clear();
        SharedPreferences sharedPreferences = getContext().getSharedPreferences(Config.SHARED_PREF_NAME, Context.MODE_PRIVATE);
        getdata(sharedPreferences.getString(Config.TOKEN_SHARED_PREF, ""));
        swipeRefreshLayout.setRefreshing(false);
    }



}