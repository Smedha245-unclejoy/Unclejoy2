package com.example.medhasingh.unclejoy2;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by medha singh on 7/14/2016.
 */
public class CardAdapter3 extends RecyclerView.Adapter<CardAdapter3.ViewHolder> {
    private Context context;
    List<Response2> response2;
    public CardAdapter3(List<Response2> response,Context context){
        super();
        this.response2=response;
        this.context=context;
    }

    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v2 = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.respond_child, parent, false);
        ViewHolder viewHolder = new ViewHolder(v2);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {

        //Getting the particular item from the list
        Response2 responsed =  response2.get(position);

        //Loading image from url

        holder.store.setText(responsed.getStore());
        holder.location.setText(responsed.getLocation());
        holder.type.setText(responsed.getType());
        holder.discount.setText(responsed.getDiscount());
        holder.ied.setText(responsed.getIed());
        holder.created.setText(responsed.getCreated());
        holder.hire.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
                Response2 movie = response2.get(position);
                final SharedPreferences sharedPreferences = view.getContext().getSharedPreferences(Config.SHARED_PREF_NAME, Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                final String token2 = sharedPreferences.getString(Config.TOKEN_SHARED_PREF, "");
                editor.putString(Config.SHARED_STORE_ID,movie.getIed());
                editor.commit();
                    String url="https://ujapi.herokuapp.com/api/v1/u/bookings/"+ sharedPreferences.getString(Config.SHARED_BOOKING_ID, "")+"/stores/"+sharedPreferences.getString(Config.SHARED_STORE_ID,"");
                    StringRequest jsonObjReq = new StringRequest(Request.Method.PUT,
                            url,
                            new com.android.volley.Response.Listener<String>() {
                                @Override
                                public void onResponse(String response){
                                    Toast.makeText(view.getContext(),"Store has been hired",Toast.LENGTH_SHORT).show();
                                }}
                            , new com.android.volley.Response.ErrorListener() {

                        @Override
                        public void onErrorResponse(VolleyError error) {
                            VolleyLog.d("Medha", "Error: " + error.getMessage());


                        }}){

                        /**
                         * Passing some request headers
                         */
                        @Override
                        public Map<String, String> getHeaders() throws AuthFailureError {
                            HashMap<String, String> headers = new HashMap<String, String>();
                            headers.put("Content-Type", "application/json");
                            headers.put("Authorization",sharedPreferences.getString(Config.TOKEN_SHARED_PREF,""));
                            return headers;
                        }};



                    AppController.getInstance().addToRequestQueue(jsonObjReq);


            }
        });


    }

    @Override
    public int getItemCount() {
        return response2.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        //Views

        public TextView location;
        public TextView discount;
        public TextView type;
        public TextView profile;
        public TextView created;
        public TextView store;
        public TextView ied;
        public Button hire;

        //Initializing Views
        public ViewHolder(View itemView) {
            super(itemView);
            store = (TextView) itemView.findViewById(R.id.store);
            location=(TextView) itemView.findViewById(R.id.location);
            type=(TextView)itemView.findViewById(R.id.type);
            discount=(TextView)itemView.findViewById(R.id.discount);
            created=(TextView)itemView.findViewById(R.id.created);
            ied=(TextView)itemView.findViewById(R.id.ied);
            hire=(Button)itemView.findViewById(R.id.hire);

        }
    }

}
