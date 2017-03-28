package com.example.medhasingh.unclejoy2;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.util.List;

/**
 * Created by medha singh on 7/16/2016.
 */
public class CardAdapter4 extends RecyclerView.Adapter<CardAdapter4.ViewHolder> {
    private Context context;
    List<Hired> response;
    public CardAdapter4(List<Hired> response,Context context){
        super();
        this.response=response;
        this.context=context;
    }

    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v3 = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.hired_part, parent, false);
        ViewHolder viewHolder = new ViewHolder(v3);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {

        //Getting the particular item from the list
        Hired responsed =  response.get(position);

        //Loading image from url

        holder.hired_name.setText(responsed.getHire_name());
        holder.hired_location.setText(responsed.getHire_location());
        holder.hired_type.setText(responsed.getHire_type());
        holder.hired_time.setText(responsed.getHire_time());
        holder.hired_dtime.setText(responsed.getHire_dtime());
        holder.hired_date.setText(responsed.getHire_date());
        holder.mobile.setText(responsed.getHire_no());
        holder.call.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Hired movie = response.get(position);
                System.out.println(movie.getHire_no());
                Intent intent = new Intent(Intent.ACTION_DIAL);
                intent.setData(Uri.parse("tel:"+movie.getHire_no()));
                System.out.println(Uri.parse("tel:"+movie.getHire_no()));
                view.getContext().startActivity(intent);
            }
        });



    }

    @Override
    public int getItemCount() {
        return response.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        //Views

        public TextView hired_name;
        public TextView hired_time;
        public TextView hired_type;
        public TextView hired_location;
        public TextView hired_date;
        public TextView hired_dtime;
        public TextView mobile;
        public Button call;


        //Initializing Views
        public ViewHolder(View itemView) {
            super(itemView);
            hired_name=(TextView)itemView.findViewById(R.id.hire_name);
            hired_time=(TextView)itemView.findViewById(R.id.hire_time);
            hired_type=(TextView)itemView.findViewById(R.id.hire_type);
            hired_location=(TextView)itemView.findViewById(R.id.hire_location);
            hired_date=(TextView)itemView.findViewById(R.id.hire_date);
            hired_dtime=(TextView)itemView.findViewById(R.id.hire_dtime);
            mobile=(TextView)itemView.findViewById(R.id.mobile_no);
            call=(Button)itemView.findViewById(R.id.hire_call);


        }
    }
}