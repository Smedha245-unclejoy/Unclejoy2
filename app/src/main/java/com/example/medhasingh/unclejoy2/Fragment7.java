package com.example.medhasingh.unclejoy2;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.Loader;
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
import android.widget.Button;

import com.android.volley.AuthFailureError;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
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
 * Created by medha singh on 7/5/2016.
 */
public class Fragment7 extends Fragment implements SwipeRefreshLayout.OnRefreshListener {
    private List<Uncle> listSuperHeroes;
    String CATEGORY_URL = "https://ujapi.herokuapp.com/api/v1/u/categories";
    Button back,next;
    //Creating Views
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private RecyclerView.Adapter adapter;
    SwipeRefreshLayout swipeRefreshLayout;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        final View view7 = inflater.inflate(R.layout.sub_category, container, false);
        recyclerView = (RecyclerView) view7.findViewById(R.id.recyclerView3);
        recyclerView.setHasFixedSize(true);
        swipeRefreshLayout = (SwipeRefreshLayout) view7.findViewById(R.id.swipe_refresh7);
        swipeRefreshLayout.setOnRefreshListener(this);
        layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        final CustomViewPager vpager=(CustomViewPager)getActivity().findViewById(R.id.pager2);
        back=(Button)view7.findViewById(R.id.back_sub);
        next=(Button)view7.findViewById(R.id.next_sub);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                vpager.setCurrentItem(0);
            }
        });
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                vpager.setCurrentItem(2);
            }
        });


        //Initializing our superheroes list
        listSuperHeroes = new ArrayList<>();
        final SharedPreferences sharedPreferences = this.getContext().getSharedPreferences(Config.SHARED_PREF_NAME, Context.MODE_PRIVATE);
        final String token2 = sharedPreferences.getString(Config.TOKEN_SHARED_PREF, "");
        final String category_id=DataHolderClass.getInstance().getDistributor_id();
//        System.out.println(category_id);
        String final_url="https://ujapi.herokuapp.com/api/v1/u/categories/"+category_id;
        recyclerView.addOnItemTouchListener(new RecyclerTouchListener(getContext(), recyclerView, new ClickListener() {
            @Override
            public void onClick(View view, int position) {
                Uncle movie = listSuperHeroes.get(position);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString(Config.SHARED_STORE_SUB_CATEGORY, movie.getid());
                editor.commit();
                //Toast.makeText(getContext(), movie.getTitle() + " is selected!", Toast.LENGTH_SHORT).show();
                vpager.setCurrentItem(2);
            }

            @Override
            public void onLongClick(View view, int position) {

            }


        }));
        //Calling method to get data

        getdata(token2,final_url);
        return view7;
    }
    private void getdata(final String a,final String b){


        //Creating a json array request
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(b,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        parsedata(response);

                    }
                },
                new Response.ErrorListener() {
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
        adapter = new CastAdapter2(listSuperHeroes, getActivity());
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

                superHero.setTitle(json.getString("subcategory"));
                superHero.setid(json.getString("id"));

            } catch (JSONException e) {
                e.printStackTrace();
            }
            listSuperHeroes.add(superHero);

        }

        //Finally initializing our adapter
        adapter = new CastAdapter2(listSuperHeroes, getActivity());

        //Adding adapter to recyclerview
        recyclerView.setAdapter(adapter);
    }



    @Override
    public void onRefresh() {
        listSuperHeroes.clear();
        SharedPreferences sharedPreferences=getContext().getSharedPreferences(Config.SHARED_PREF_NAME,Context.MODE_PRIVATE);
        final String category_id=DataHolderClass.getInstance().getDistributor_id();
        //System.out.println(category_id);
        String final_url="https://ujapi.herokuapp.com/api/v1/u/categories/"+category_id;
        getdata(sharedPreferences.getString(Config.TOKEN_SHARED_PREF,""),final_url);
        swipeRefreshLayout.setRefreshing(false);
    }

    public interface ClickListener {
        void onClick(View view, int position);

        void onLongClick(View view, int position);
    }
    public static class RecyclerTouchListener implements RecyclerView.OnItemTouchListener {

        private GestureDetector gestureDetector;
        private ClickListener clickListener;

        public RecyclerTouchListener(Context context, final RecyclerView recyclerView, final Fragment7.ClickListener clickListener) {
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
