package com.example.medhasingh.unclejoy2;

import android.animation.Animator;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.TextView;
import java.util.List;



/**
 * Created by medha singh on 6/22/2016.
 */
public class CastAdapter extends RecyclerView.Adapter<CastAdapter.ViewHolder> {

    private Context context;

    //List of superHeroes
    List<Uncle> superHeroes;

    public CastAdapter(List<Uncle> superHeroes, Context context){
        super();
        //Getting all the superheroes
        this.superHeroes = superHeroes;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.new_lead_part, parent, false);

        ViewHolder viewHolder = new ViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        Uncle superHero =  superHeroes.get(position);



        holder.textViewName.setText(superHero.getTitle());
        holder.textViewRank.setText(String.valueOf(superHero.getid()));



    }

    @Override
    public int getItemCount() {
        return superHeroes.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{

        public TextView textViewName;
        public TextView textViewRank;


        public ViewHolder(View itemView) {
            super(itemView);

            textViewName = (TextView) itemView.findViewById(R.id.title);
            textViewRank= (TextView) itemView.findViewById(R.id.id);

        }
    }

}
