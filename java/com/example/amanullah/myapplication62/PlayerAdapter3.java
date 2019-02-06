package com.example.amanullah.myapplication63;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.util.List;

public class PlayerAdapter3 extends RecyclerView.Adapter<PlayerAdapter3.ViewHolder>{
    private List<Player> mPlayers;
    Context thisContext;
    class ViewHolder extends RecyclerView.ViewHolder{
        public ImageView profilePic;
        public TextView nameTextView,hallTextView;
        ViewHolder(View itemView){
            super(itemView);
            //profilePic = itemView.findViewById(R.id.team_selector_rv_single_elem_imageView);
            nameTextView = itemView.findViewById(R.id.team_selector_rv_single_elem_textView_name);
            hallTextView = itemView.findViewById(R.id.team_selector_rv_single_elem_textView_hall);
        }
    }

    public PlayerAdapter3(List <Player> s, Context c){
        this.mPlayers = s;
        thisContext = c;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        View view = inflater.inflate(R.layout.team_selector_rv_single_elem,parent,false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int position){
        Player p = mPlayers.get(position);
        ImageView imView = viewHolder.profilePic;
        TextView nameTv = viewHolder.nameTextView,hallTv = viewHolder.hallTextView;
        nameTv.setText(p.getName());
        hallTv.setText(p.getHall());
    }

    @Override
    public int getItemCount(){
        return mPlayers.size();
    }
}
