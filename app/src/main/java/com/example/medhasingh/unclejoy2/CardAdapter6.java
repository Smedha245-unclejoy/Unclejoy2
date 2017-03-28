package com.example.medhasingh.unclejoy2;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;

import java.util.List;

/**
 * Created by medha singh on 8/6/2016.
 */
public class CardAdapter6  extends RecyclerView.Adapter<CardAdapter6.ViewHolder> {

    private ImageLoader imageLoader;
    private Context context;
    List<Image_attr> imag;
    public CardAdapter6(List<Image_attr> imag,Context context){
        super();
        this.imag=imag;
        this.context=context;
    }

    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.picture_item, parent, false);
        ViewHolder viewHolder = new ViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        //Getting the particular item from the list
        Image_attr searching =  imag.get(position);

        //Loading image from url


        if ((imageLoader == null)&&(searching.getThumbnailUrl()!="null")){
            imageLoader = AppController.getInstance().getImageLoader();
            holder.img.setImageUrl(searching.getThumbnailUrl(),imageLoader);
        }


    }

    @Override
    public int getItemCount() {
        return imag.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        //Views
        NetworkImageView img;

        //Initializing Views
        public ViewHolder(View itemView) {
            super(itemView);
            img=(NetworkImageView)itemView.findViewById(R.id.thumbnail);
            img.setDefaultImageResId(R.drawable.profile);
            img.setErrorImageResId(R.drawable.profile);


        }
    }
}
