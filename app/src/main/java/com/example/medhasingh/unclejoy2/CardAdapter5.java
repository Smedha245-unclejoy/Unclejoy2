package com.example.medhasingh.unclejoy2;

/**
 * Created by medha singh on 7/26/2016.
 */

import android.net.Uri;
import android.widget.ImageView;
import android.widget.TextView;

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
 * Created by medha singh on 6/23/2016.
 */
public class CardAdapter5 extends RecyclerView.Adapter<CardAdapter5.ViewHolder> {
    private ImageLoader imageLoader;
    private Context context;
    List<Search> search;
    public CardAdapter5(List<Search> search,Context context){
        super();
        this.search=search;
        this.context=context;
    }

    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.search_item, parent, false);
        ViewHolder viewHolder = new ViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        //Getting the particular item from the list
        Search searching =  search.get(position);

        //Loading image from url

        holder.textViewName.setText(searching.getTitle());
        if ((imageLoader == null)&&(searching.getThumbnailUrl()!="null")){
            imageLoader = AppController.getInstance().getImageLoader();
            holder.img.setImageUrl(searching.getThumbnailUrl(),imageLoader);
       }
        holder.textViewaddress.setText(searching.getAddress());

    }

    @Override
    public int getItemCount() {
        return search.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        //Views

        public TextView textViewName;
        public TextView textViewaddress;
        NetworkImageView img;

        //Initializing Views
        public ViewHolder(View itemView) {
            super(itemView);
            textViewName = (TextView) itemView.findViewById(R.id.store_name);
            textViewaddress=(TextView)itemView.findViewById(R.id.address_name);
            img=(NetworkImageView)itemView.findViewById(R.id.img);
            img.setDefaultImageResId(R.drawable.profile);
            img.setErrorImageResId(R.drawable.profile);


        }
    }
}
