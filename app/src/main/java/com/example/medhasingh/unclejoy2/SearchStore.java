package com.example.medhasingh.unclejoy2;


import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;

import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.ViewFlipper;


import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import com.miguelcatalan.materialsearchview.MaterialSearchView;

/**
 * Created by medha singh on 6/16/2016.
 */
public class SearchStore extends Fragment {
   // private static final String TAG = SearchStore.class.getSimpleName();
    private ProgressDialog pDialog;
    ViewFlipper viewflip;
    RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private List<Search> movieList = new ArrayList<Search>();
    String SEARCH_URL="https://ujapi.herokuapp.com/api/v1/u/stores/storeswithlocation?search=";
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        final View view9=inflater.inflate(R.layout.search_store,container,false);
        final LinearLayout card=(LinearLayout)view9.findViewById(R.id.card);
        final Button sh=(Button)view9.findViewById(R.id.bt);
         viewflip=(ViewFlipper)view9.findViewById(R.id.viewflip);
        if(movieList.size()==0){
            viewflip.setDisplayedChild(0);
        }
        else {viewflip.setDisplayedChild(1);}
        final Button back=(Button)view9.findViewById(R.id.next);

        recyclerView=(RecyclerView)view9.findViewById(R.id.recyclerView2);
        recyclerView.setHasFixedSize(true);
        sh.setVisibility(View.VISIBLE);
        final MaterialSearchView search = (MaterialSearchView) view9.findViewById(R.id.stores_search);
        search.hideKeyboard(view9);
        search.setVisibility(View.INVISIBLE);
       sh.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v1) {
               sh.setVisibility(v1.INVISIBLE);
               search.setVisibility(v1.VISIBLE);
               search.showSearch();
           }
       });
        ImageButton mback=(ImageButton)view9.findViewById(R.id.action_up_btn);
        mback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                search.closeSearch();
                search.hideKeyboard(v);
                sh.setVisibility(View.VISIBLE);
            }
        });
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 1, GridLayoutManager.HORIZONTAL, false));
        adapter=new CardAdapter5(movieList,getContext());
                recyclerView.setAdapter(new CardAdapter5(movieList,getContext()));
        Button btn=(Button)view9.findViewById(R.id.btnsearch);
        SharedPreferences sharedPreferences = getContext().getSharedPreferences(Config.SHARED_PREF_NAME, Context.MODE_PRIVATE);
        final String token2 = sharedPreferences.getString(Config.TOKEN_SHARED_PREF, "");
        final CustomViewPager pager=(CustomViewPager)getActivity().findViewById(R.id.pager2);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pager.setCurrentItem(1);
            }
        });
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pager.setCurrentItem(3);
            }
        });
        //search.setIMEoptions(EditorInfo.IME_ACTION_SEARCH);
        //search.setSubmitButtonEnabled(true);
        //search.setCloseIcon();
        //search.showSearch();
        search.setOnSearchViewListener(new MaterialSearchView.SearchViewListener() {
            @Override
            public void onSearchViewShown() {
                search.setBackgroundColor(Color.WHITE);
                search.setHint("Search your nearby stores");
                search.showKeyboard(view9);
            }

            @Override
            public void onSearchViewClosed() {
                //Do some magic
            }
        });
        search.setOnQueryTextListener(new MaterialSearchView.OnQueryTextListener() {

            @Override
            public boolean onQueryTextSubmit(String query) {
                movieList.clear();
                query.toString();
                query.trim();
                movieList.clear();
                // String search_result = search.getQuery().toString();

                try {
                    String encodedURL=java.net.URLEncoder.encode(query,"UTF-8");
                    final String final_url = SEARCH_URL + encodedURL;
                    System.out.println(encodedURL);
                    gettourl(token2, final_url);
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
                //Calling method to get data

                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                movieList.clear();
                //String search_result = search.getQuery().toString();
                // movieList.clear();
                // final String final_url = SEARCH_URL + newText;
                //Calling method to get data
                // gettourl(token2, final_url);
                return true;
            }
        });


        return view9;
    }
    public void gettourl(String p,String q){
        JsonArrayRequest movieReq = new JsonArrayRequest(q,
                new com.android.volley.Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        Log.d("Medha", response.toString());
                        if(response.toString()==null){
                            viewflip.setDisplayedChild(0);
                        }


                        parsedata(response);


                    }
                }, new com.android.volley.Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d("Medha", "Error: " + error.getMessage());

            }
        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                super.getHeaders();
                HashMap<String, String> headers = new HashMap<String, String>();
                SharedPreferences sharedPreferences=getContext().getSharedPreferences(Config.SHARED_PREF_NAME,0);

                headers.put("Content-Type", "application/json; charset=utf-8");
                headers.put("Authorization", sharedPreferences.getString(Config.TOKEN_SHARED_PREF,""));
                return headers;
            }

        };

        // Adding request to request queue
        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        requestQueue.add(movieReq);
        adapter = new CardAdapter5(movieList, getActivity());
        adapter.notifyDataSetChanged();
        //Adding adapter to recyclerview
        recyclerView.setAdapter(adapter);
       /* listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent=new Intent(WelcomeFreshers.this,ViewFullImage.class);
                intent.putExtra("position",position);
                startActivity(intent);
            }
        });*/
    }
    public void parsedata(JSONArray array){



            for (int i = 0; i < array.length(); i++) {
                Search movie = new Search();
                JSONObject obj = null;

                try {
                    obj = array.getJSONObject(i);
                    movie.setThumbnailUrl(obj.getJSONObject("picture").getJSONObject("picture").getString("url"));
                    movie.setTitle(obj.getString("store_name"));
                    movie.setAddress(obj.getString("address"));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                movieList.add(movie);
                if(movieList.size()!=0){
                    viewflip.setDisplayedChild(1);
                }else {viewflip.setDisplayedChild(0);}

                adapter.notifyDataSetChanged();
            }


    }
}