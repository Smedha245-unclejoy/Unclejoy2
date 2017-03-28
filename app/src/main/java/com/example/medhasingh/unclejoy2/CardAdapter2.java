package com.example.medhasingh.unclejoy2;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.toolbox.ImageLoader;

import java.util.List;


/**
 * Created by medha singh on 7/5/2016.
 */
public class CardAdapter2 extends RecyclerView.Adapter<CardAdapter2.ViewHolder> {
    private ImageLoader imageLoader;
    private Context context;
    List<Response> response;
    public CardAdapter2(List<Response> response,Context context){
        super();
        this.response=response;
        this.context=context;
    }

    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v2 = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.responded_part, parent, false);
        ViewHolder viewHolder = new ViewHolder(v2);
        return viewHolder;
    }


    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {

        //Getting the particular item from the list
        Response responsed =  response.get(position);

        //Loading image from url

        holder.storename.setText(responsed.getStorename());
        holder.time_elapsed.setText(responsed.getTimelapsed());
        holder.store_category.setText(responsed.getStore_category());
        holder.map.setText(responsed.getMap());
        holder.date.setText(responsed.getDate());
        holder.time.setText(responsed.getTime());
        holder.id.setText(String.valueOf(responsed.getBooking_id()));
// compile 'com.afollestad.material-dialogs:core:0.8.6.1'
       holder.link.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               Intent ii=new Intent(v.getContext(),Pictures.class);
                ii.putExtra("Position",position);
                System.out.println("Position:"+position);
                v.getContext().startActivity(ii);
            }
        });
        holder.respon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Response movie=response.get(position);
                SharedPreferences sharedPreferences=view.getContext().getSharedPreferences(Config.SHARED_PREF_NAME, Context.MODE_PRIVATE);
                SharedPreferences.Editor editor=sharedPreferences.edit();
                final String token2=sharedPreferences.getString(Config.TOKEN_SHARED_PREF, "");

                editor.putString(Config.SHARED_BOOKING_ID,movie.getBooking_id());
                System.out.println(movie.getBooking_id());
                editor.commit();
                String booking_id=sharedPreferences.getString(Config.SHARED_BOOKING_ID,"");
                Toast.makeText(view.getContext(),booking_id+" is selected!",Toast.LENGTH_SHORT).show();
                Intent n=new Intent(view.getContext(),Store_list.class);
                view.getContext().startActivity(n);
            }
        });
    }


    @Override
    public int getItemCount() {
        return response.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        //Views

        public TextView link;
        public TextView time_elapsed;
        public TextView store_category;
        public TextView map;
        public TextView date;
        public TextView time;
        public TextView storename;
        private Button respon;
        public TextView id;
        public TextView count;

        //Initializing Views
        public ViewHolder(View itemView) {
            super(itemView);
            storename = (TextView) itemView.findViewById(R.id.storename);
            time_elapsed=(TextView) itemView.findViewById(R.id.time_elapsed);
            store_category=(TextView)itemView.findViewById(R.id.store_category);
            map=(TextView)itemView.findViewById(R.id.map);
            date=(TextView)itemView.findViewById(R.id.date);
            time=(TextView)itemView.findViewById(R.id.time);
            link=(TextView)itemView.findViewById(R.id.link);
            id=(TextView)itemView.findViewById(R.id.booking_id);
            respon=(Button)itemView.findViewById(R.id.responded_stores);

        }
    }


}
