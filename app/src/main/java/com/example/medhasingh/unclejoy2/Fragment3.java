package com.example.medhasingh.unclejoy2;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
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

import com.android.volley.AuthFailureError;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
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
public class Fragment3 extends Fragment implements LoaderManager.LoaderCallbacks<List<Hired>>, SwipeRefreshLayout.OnRefreshListener{
    String HIRED_URL="https://ujapi.herokuapp.com/api/v1/u/bookings/hired";
    private List<Hired> listSuperHeroes;
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private RecyclerView.Adapter adapter;
    private SwipeRefreshLayout swipeRefreshLayout;
    private ViewFlipper flipper;
    @Override
    public View onCreateView(LayoutInflater inflater,ViewGroup container,Bundle savedInstanceState){
        super.onCreateView(inflater,container,savedInstanceState);
        View view3 = inflater.inflate(R.layout.hired, container, false);
        recyclerView = (RecyclerView) view3.findViewById(R.id.recyclerView7);
        recyclerView.setHasFixedSize(true);
        swipeRefreshLayout = (SwipeRefreshLayout) view3.findViewById(R.id.swipe_refresh2);
        swipeRefreshLayout.setOnRefreshListener(this);
        layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        //adapter = new CardAdapter4(listSuperHeroes, getContext());
        //recyclerView.setAdapter(adapter);
        listSuperHeroes = new ArrayList<>();
        flipper=(ViewFlipper)view3.findViewById(R.id.flip_me2);
        if(listSuperHeroes.size()==0){
            flipper.setDisplayedChild(1);
        }
        else {flipper.setDisplayedChild(0);}
        SharedPreferences sharedPreferences =this.getContext().getSharedPreferences(Config.SHARED_PREF_NAME, Context.MODE_PRIVATE);
        final String token2 = sharedPreferences.getString(Config.TOKEN_SHARED_PREF, "");


        //Calling method to get data
        getdata(token2);

        return view3;
    }
    private void getdata(final String a){


        //Creating a json array request
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(HIRED_URL,
                new com.android.volley.Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {

                        parsedata(response);
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

        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());

        //Adding request to the queue
        requestQueue.add(jsonArrayRequest);
        adapter = new CardAdapter4(listSuperHeroes, getContext());

        recyclerView.setLayoutManager(layoutManager);
        //Adding adapter to recyclerview
        recyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
        //Creating request queue
           }

    private void parsedata(JSONArray array){

        for (int i = 0; i < array.length(); i++) {
            Hired superHero = new Hired();
            JSONObject json = null;
            try {

                json = array.getJSONObject(i);
                superHero.setHire_name(json.getJSONObject("store").getString("store_name"));
                //superHero.setStorename(json.getJSONObject("user").getString("name"));
                //superHero.setStore_category(json.getJSONObject("store_category").getString("category"));
                superHero.setHire_time(json.getString("created_at"));
                superHero.setHire_type(json.getJSONObject("store_category").getString("category"));
                superHero.setHire_location(json.getJSONObject("store").getString("address"));
                superHero.setHire_date(json.getString("date"));
                superHero.setHire_dtime(json.getString("time"));
                superHero.setHire_no(json.getJSONObject("store").getString("mobile"));
               // System.out.println(json.getJSONObject("store").getString("mobile"));


                listSuperHeroes.add(superHero);
                if(listSuperHeroes.size()!=0){
                    flipper.setDisplayedChild(0);
                }else {flipper.setDisplayedChild(0);}


            } catch (JSONException e) {
                e.printStackTrace();
            }


        }
        //Finally initializing our adapter
        adapter = new CardAdapter4(listSuperHeroes, getActivity());
        recyclerView.setLayoutManager(layoutManager);
        //Adding adapter to recyclerview
        recyclerView.setAdapter(adapter);

    }

    @Override
    public Loader<List<Hired>> onCreateLoader(int id, Bundle args) {
        return null;
    }

    @Override
    public void onLoadFinished(Loader<List<Hired>> loader, List<Hired> data) {

    }

    @Override
    public void onLoaderReset(Loader<List<Hired>> loader) {

    }

    @Override
    public void onRefresh() {
         listSuperHeroes.clear();
       SharedPreferences sharedPreferences=getContext().getSharedPreferences(Config.SHARED_PREF_NAME,Context.MODE_PRIVATE);
        getdata(sharedPreferences.getString(Config.TOKEN_SHARED_PREF,""));
        swipeRefreshLayout.setRefreshing(false);
    }

}