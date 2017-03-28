package com.example.medhasingh.unclejoy2;


import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
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
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
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
 * Created by medha singh on 6/17/2016.
 */
public class Fragment4 extends Fragment implements SwipeRefreshLayout.OnRefreshListener {
    private List<Uncle> listSuperHeroes;
    String CATEGORY_URL = "https://ujapi.herokuapp.com/api/v1/u/categories";

    //Creating Views
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private RecyclerView.Adapter adapter;
    private SwipeRefreshLayout swipeRefreshLayout;
    private ViewFlipper flipper;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        final View view1 = inflater.inflate(R.layout.new_lead2, container, false);
        recyclerView = (RecyclerView) view1.findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        swipeRefreshLayout=(SwipeRefreshLayout)view1.findViewById(R.id.swipe);
        layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        final CustomViewPager vpager=(CustomViewPager)getActivity().findViewById(R.id.pager2);
        //Initializing our superheroes list
        listSuperHeroes = new ArrayList<>();
        flipper=(ViewFlipper)view1.findViewById(R.id.flip_me3);
        if(listSuperHeroes.size()==0){
            flipper.setDisplayedChild(1);
        }
        else {flipper.setDisplayedChild(0);}
        adapter = new CastAdapter(listSuperHeroes, getActivity());
        adapter.notifyDataSetChanged();
        swipeRefreshLayout.setOnRefreshListener(this);
        //Adding adapter to recyclerview
        recyclerView.setAdapter(adapter);
        final SharedPreferences sharedPreferences = this.getContext().getSharedPreferences(Config.SHARED_PREF_NAME, Context.MODE_PRIVATE);
        final String token2 = sharedPreferences.getString(Config.TOKEN_SHARED_PREF, "");
        recyclerView.addOnItemTouchListener(new RecyclerTouchListener(this.getContext(), recyclerView, new ClickListener() {
            @Override
            public void onClick(View view, int position) {
                Uncle movie = listSuperHeroes.get(position);
               // SharedPreferences sharedPreferences = getContext().getSharedPreferences(Config.SHARED_PREF_NAME, Context.MODE_PRIVATE);
               // SharedPreferences.Editor editor = sharedPreferences.edit();

               // editor.putString(Config.SHARED_STORE_CATEGORY, movie.getid());
                //editor.commit();
                //String category_id=sharedPreferences.getString(Config.SHARED_STORE_CATEGORY, "");
                //movie.getid()
                DataHolderClass.getInstance().setDistributor_id(movie.getid());
                String category_id=movie.getid();
                System.out.println(DataHolderClass.getInstance().getDistributor_id());
                Toast.makeText(getContext(), category_id + " is selected!", Toast.LENGTH_SHORT).show();
                vpager.setCurrentItem(1);
            }

            @Override
            public void onLongClick(View view, int position) {

            }


        }));
        //Calling method to get data

      getdata(token2);
        return view1;
    }
    private void getdata(final String a){

        //Creating a json array request
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(CATEGORY_URL,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                       parsedata(response);
                        if(response.toString()==null){
                            flipper.setDisplayedChild(1);
                        }

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        NetworkResponse networkResponse = error.networkResponse;
                        if (networkResponse != null && networkResponse.statusCode == 401) {
                            Toast.makeText(getContext(),"Your session has expired ...please logout first and login again",Toast.LENGTH_SHORT).show();
                            // HTTP Status Code: 401 Unauthorized
                        }

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
        adapter = new CastAdapter(listSuperHeroes, getActivity());
        adapter.notifyDataSetChanged();
        //Adding adapter to recyclerview
        recyclerView.setAdapter(adapter);
        //Creating request queue



    }
    private void parsedata(JSONArray array){
        for (int i = 0; i < array.length(); i++) {
            Uncle superHero = new Uncle();
            JSONObject json = null;
            try {
                json = array.getJSONObject(i);

                superHero.setTitle(json.getString("category"));
                superHero.setid(json.getString("id"));

            } catch (JSONException e) {
                e.printStackTrace();
            }
            listSuperHeroes.add(superHero);
            if(listSuperHeroes.size()!=0){
                flipper.setDisplayedChild(0);
            }else {flipper.setDisplayedChild(0);}

        }
        //Finally initializing our adapter
        adapter = new CastAdapter(listSuperHeroes, getActivity());

        //Adding adapter to recyclerview
        recyclerView.setAdapter(adapter);
    }


    public void onRefresh() {
        listSuperHeroes.clear();
        SharedPreferences sharedPreferences=getContext().getSharedPreferences(Config.SHARED_PREF_NAME,Context.MODE_PRIVATE);
        getdata(sharedPreferences.getString(Config.TOKEN_SHARED_PREF,""));
        swipeRefreshLayout.setRefreshing(false);
    }

    public interface ClickListener {
        void onClick(View view, int position);

        void onLongClick(View view, int position);
    }
    public static class RecyclerTouchListener implements RecyclerView.OnItemTouchListener {

        private GestureDetector gestureDetector;
        private Fragment4.ClickListener clickListener;

        public RecyclerTouchListener(Context context, final RecyclerView recyclerView, final Fragment4.ClickListener clickListener) {
            this.clickListener = clickListener;
            gestureDetector = new GestureDetector(context, new GestureDetector.SimpleOnGestureListener() {
                @Override
                public boolean onSingleTapUp(MotionEvent e) {
                    return true;
                }

                @Override
                public void onLongPress(MotionEvent e) {
                    View child = recyclerView.findChildViewUnder(e.getX(), e.getY());
                    if (child != null && clickListener != null) {
                        clickListener.onLongClick(child, recyclerView.getChildPosition(child));
                    }
                }
            });
        }

        @Override
        public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {

            View child = rv.findChildViewUnder(e.getX(), e.getY());
            if (child != null && clickListener != null && gestureDetector.onTouchEvent(e)) {
                clickListener.onClick(child, rv.getChildPosition(child));
            }
            return false;
        }

        @Override
        public void onTouchEvent(RecyclerView rv, MotionEvent e) {
        }

        @Override
        public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {

        }
    }

}



