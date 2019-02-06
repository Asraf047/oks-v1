package com.example.amanullah.myapplication63;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by AMANULLAH on 05-Feb-18.
 */

public class PlayerAdapter extends RecyclerView.Adapter<PlayerAdapter.MyViewHolder> implements Filterable {
    private Context context;
    private List<Player> playerList;
    private List<Player> playerListFiltered;
    private PlayerAdapterListener listener;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView name,point,price,details;
        public ImageView thumbnail;

        public MyViewHolder(View view) {
            super(view);
            name = view.findViewById(R.id.name);
            point = view.findViewById(R.id.point);
            price = view.findViewById(R.id.price);
            details = view.findViewById(R.id.details);
            thumbnail = view.findViewById(R.id.thumbnail);

            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    // send selected player in callback
                    listener.onPlayerSelected(playerListFiltered.get(getAdapterPosition()));
                }
            });
        }
    }


    public PlayerAdapter(Context context, List<Player> playerList, PlayerAdapterListener listener) {
        this.context = context;
        this.listener = listener;
        this.playerList = playerList;
        this.playerListFiltered = playerList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list_content, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        final Player player = playerListFiltered.get(position);
        holder.name.setText(player.getName());
        //holder.point.setText(player.getPoint());
        holder.price.setText(player.getPrice());
        //holder.details.setText(player.getDetails());

        Glide.with(context)
                .load(R.drawable.i5)
                //.thumbnail(/*sizeMultiplier=*/ 0.9f)
                .apply(RequestOptions.circleCropTransform())
                .into(holder.thumbnail);


    }

    @Override
    public int getItemCount() {
        return playerListFiltered.size();
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                String charString = charSequence.toString();
                if (charString.isEmpty()) {
                    playerListFiltered = playerList;
                } else {
                    List<Player> filteredList = new ArrayList<>();
                    for (Player row : playerList) {

                        // name match condition. this might differ depending on your requirement
                        // here we are looking for name or phone number match
                        /*
                        if (row.getName().toLowerCase().contains(charString.toLowerCase()) || row.getDetails().contains(charSequence)) {
                            filteredList.add(row);
                        }
                        */
                    }

                    playerListFiltered = filteredList;
                }

                FilterResults filterResults = new FilterResults();
                filterResults.values = playerListFiltered;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                playerListFiltered = (ArrayList<Player>) filterResults.values;
                notifyDataSetChanged();
            }
        };
    }

    public interface PlayerAdapterListener {
        void onPlayerSelected(Player player);
    }
}
