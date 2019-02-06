package com.example.amanullah.myapplication63;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

public class ViewPlayerList extends AppCompatActivity {
    public static ViewPlayerList fa;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_player_list);
        fa = this;
        init();
    }
    private void init(){
        RecyclerView recyclerView = findViewById(R.id.rvPlayers);
        final List <Player> players = new ArrayList<>();
        String playerRole = getIntent().getStringExtra("role");
        for(Player x: Globals.allPlayers){
            if(x.getRole().equals(playerRole) && !(Globals.playerTaken).contains(x.getId())){
                    players.add(x);
            }
        }
        PlayerAdapter2 adapter2 = new PlayerAdapter2(players,ViewPlayerList.this);
        recyclerView.setAdapter(adapter2);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.addOnItemTouchListener(
                new RecyclerItemClickListener(ViewPlayerList.this, recyclerView, new RecyclerItemClickListener.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {
                        //Globals.temp = players.get(position);
                        //Globals.hasPlayer = true;
                        Intent intent = new Intent(ViewPlayerList.this,ViewPlayerDetails.class);
                        intent.putExtra("player",new Gson().toJson(players.get(position)));
                        intent.putExtra("mode","buy");
                        //intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                        //startActivityIfNeeded(intent,0);
                        startActivity(intent);
                    }
                    @Override
                    public void onLongItemClick(View view, int position) {
                    }
                })
        );
    }
}

class PlayerAdapter2 extends RecyclerView.Adapter<PlayerAdapter2.MyViewHolder> {

    private List<Player> players;
    Context thisContext;
    class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView nameTextView;
        public ImageView imView;
        public TextView hallTextView;

        public MyViewHolder(View itemView) {
            super(itemView);
            nameTextView = (TextView) itemView.findViewById(R.id.textView7);
            hallTextView = (TextView) itemView.findViewById(R.id.textView8);
            imView = (ImageView) itemView.findViewById(R.id.imageView3);
        }
    }

    public PlayerAdapter2(List<Player> s, Context c) {
        this.players = s; thisContext = c;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        View contactView = inflater.inflate(R.layout.custom_row_rv2, parent, false);

        MyViewHolder holder = new MyViewHolder(contactView);
        return holder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder viewHolder, int position) {
        Player p = players.get(position);

        TextView nameView = viewHolder.nameTextView;
        nameView.setText(p.getName());

        TextView hallView = viewHolder.hallTextView;
        hallView.setText(p.getHall());

        ImageView imageView = viewHolder.imView;
        //Glide.with(thisContext).load(p.getPicture()).apply(RequestOptions.circleCropTransform()).into(imageView);
    }
    @Override
    public int getItemCount() {
        return players.size();
    }
}
