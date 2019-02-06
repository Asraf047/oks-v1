package com.example.amanullah.myapplication63;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public class TeamSelector extends AppCompatActivity {
    final List<Player> bowlers=new ArrayList<>(),batsmen=new ArrayList<>(),all_rounders=new ArrayList<>(),wks=new ArrayList<>();

    RecyclerView batsmanRecyclerView,bowlerRecyclerView,wkRecyclerView,allRounderRecyclerView;
    PlayerAdapter3 batsmanAdapter, bowlerAdapter, all_rounderAdapter,wksAdapter;
    private int batsmenSelected = 0,bowlerSelected = 0, allRounderSelected = 0, wicketKeeperSelected = 0;
    private int batsmanWKSelected = 0,totalPlayerSelected = 0;
    private long i1 = 0;
    private int usedBalance;
    private TextView balanceTextView;
    private MyProcessingDialog myProcessingDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_team_selector);
        init();
        new ShowMessage().showMessage(TeamSelector.this, "Make your squad", "You have to make an initial squad " +
                        "for the first time.",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        myProcessingDialog.show();
                        Globals.allPlayers = new ArrayList<>();

                        FirebaseDatabase.getInstance().getReference().child("players").addListenerForSingleValueEvent(
                                new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                        for(DataSnapshot ds: dataSnapshot.getChildren()){
                                            Player p = new Gson().fromJson(ds.getValue().toString(),Player.class);
                                            Globals.allPlayers.add(p);
                                        }
                                        setupViews();
                                        myProcessingDialog.dismiss();
                                    }

                                    @Override
                                    public void onCancelled(@NonNull DatabaseError databaseError) {

                                    }
                                }
                        );
                    }
                });


    }
    @Override
    protected void onResume(){
        super.onResume();
        if(Globals.hasPlayer){
            if(Globals.role.equals(Constants.Player.batsman)){
                addBatsman(Globals.temp);
            }
            else if(Globals.role.equals(Constants.Player.bowler)){
                addBowler(Globals.temp);
            }
            else if(Globals.role.equals(Constants.Player.all_rounder)){
                addAllRounder(Globals.temp);
            }
            else if(Globals.role.equals(Constants.Player.batsman_wk)){
                addWK(Globals.temp);
            }
            Globals.playerTaken.add(Globals.temp.getId());
            Globals.hasPlayer = false;
        }
    }
    private void addBatsman(Player p){
        batsmen.add(p);

        batsmanAdapter.notifyItemInserted(batsmen.size()-1);
        batsmenSelected++;
        if(p.getRole().equals(Constants.Player.batsman_wk)){
            wicketKeeperSelected++;
            batsmanWKSelected++;
        }
        totalPlayerSelected++;
        int temp = p.getPrice();
        Globals.userBalance -= temp;
        usedBalance += temp;
        updateBalance();
    }
    private void addBowler(Player p){
        bowlers.add(p);
        bowlerAdapter.notifyItemInserted(bowlers.size()-1);
        bowlerSelected++;
        totalPlayerSelected++;
        int temp = p.getPrice();
        Globals.userBalance -= temp;
        updateBalance();
        usedBalance += temp;
    }
    private void addAllRounder(Player p){
        all_rounders.add(p);
        all_rounderAdapter.notifyItemInserted(all_rounders.size()-1);
        allRounderSelected++;
        totalPlayerSelected++;
        int temp = p.getPrice();
        Globals.userBalance -= temp;
        updateBalance();
        usedBalance += temp;
    }
    private void addWK(Player p){
        wks.add(p);
        wksAdapter.notifyItemInserted(wks.size()-1);
        wicketKeeperSelected++;
        if(p.getRole().equals(Constants.Player.batsman_wk)){
            batsmenSelected++;
            batsmanWKSelected++;
        }
        totalPlayerSelected++;
        int temp = p.getPrice();
        Globals.userBalance -= temp;
        updateBalance();
        usedBalance += temp;
    }
    private void updateBalance(){
        balanceTextView.setText(Globals.userBalance.toString());
    }

    private void init() {
        usedBalance = 0;
        Globals.playerTaken = new HashSet<>();
        Globals.userBalance = Integer.parseInt(Globals.user.getBalance());
        Log.i("Login",Globals.userBalance+ " valuees "+Globals.user.getBalance());
        balanceTextView = findViewById(R.id.team_selector_balance_value);
        batsmanRecyclerView = findViewById(R.id.recycler_view);
        bowlerRecyclerView = findViewById(R.id.recycler_view2);
        allRounderRecyclerView = findViewById(R.id.recycler_view3);
        wkRecyclerView = findViewById(R.id.recycler_view4);
        myProcessingDialog = new MyProcessingDialog(TeamSelector.this, "Please wait...", "Downloading data");
    }

    private void setupViews(){
            batsmanAdapter = new PlayerAdapter3(batsmen,TeamSelector.this);
            bowlerAdapter = new PlayerAdapter3(bowlers,TeamSelector.this);
            all_rounderAdapter = new PlayerAdapter3(all_rounders,TeamSelector.this);
            wksAdapter = new PlayerAdapter3(wks,TeamSelector.this);
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(TeamSelector.this, LinearLayoutManager.HORIZONTAL,false);
            LinearLayoutManager linearLayoutManager2 = new LinearLayoutManager(TeamSelector.this, LinearLayoutManager.HORIZONTAL,false);
            LinearLayoutManager linearLayoutManager3 = new LinearLayoutManager(TeamSelector.this, LinearLayoutManager.HORIZONTAL,false);
            LinearLayoutManager linearLayoutManager4 = new LinearLayoutManager(TeamSelector.this, LinearLayoutManager.HORIZONTAL,false);

            batsmanRecyclerView.setLayoutManager(linearLayoutManager);
            bowlerRecyclerView.setLayoutManager(linearLayoutManager2);
            allRounderRecyclerView.setLayoutManager(linearLayoutManager3);
            wkRecyclerView.setLayoutManager(linearLayoutManager4);

            DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(batsmanRecyclerView.getContext(),linearLayoutManager.getOrientation());
            batsmanRecyclerView.addItemDecoration(dividerItemDecoration);

            DividerItemDecoration dividerItemDecoration2 = new DividerItemDecoration(bowlerRecyclerView.getContext(),linearLayoutManager2.getOrientation());
            bowlerRecyclerView.addItemDecoration(dividerItemDecoration2);

            DividerItemDecoration dividerItemDecoration3 = new DividerItemDecoration(allRounderRecyclerView.getContext(),linearLayoutManager3.getOrientation());
            allRounderRecyclerView.addItemDecoration(dividerItemDecoration3);

            DividerItemDecoration dividerItemDecoration4 = new DividerItemDecoration(wkRecyclerView.getContext(),linearLayoutManager4.getOrientation());
            wkRecyclerView.addItemDecoration(dividerItemDecoration4);


            batsmanRecyclerView.setAdapter(batsmanAdapter);
            bowlerRecyclerView.setAdapter(bowlerAdapter);
            allRounderRecyclerView.setAdapter(all_rounderAdapter);
            wkRecyclerView.setAdapter(wksAdapter);


            Glide.with(TeamSelector.this).load(R.drawable.add2).apply(RequestOptions.circleCropTransform()).into((ImageView)findViewById(R.id.addBowler));
            Glide.with(TeamSelector.this).load(R.drawable.add2).apply(RequestOptions.circleCropTransform()).into((ImageView)findViewById(R.id.addBatsmen));
            Glide.with(TeamSelector.this).load(R.drawable.add2).apply(RequestOptions.circleCropTransform()).into((ImageView)findViewById(R.id.addAllRounder));
            Glide.with(TeamSelector.this).load(R.drawable.add2).apply(RequestOptions.circleCropTransform()).into((ImageView)findViewById(R.id.addWK));

            (findViewById(R.id.addBatsmen)).setOnClickListener(
                    new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Globals.role = Constants.Player.batsman;
//                            Intent intent = new Intent(TeamSelector.this,ViewPlayerList.class);
                            Intent intent = new Intent(TeamSelector.this,MainActivity.class);
                            intent.putExtra("role",Constants.Player.batsman);
                            intent.putExtra("mode","buy");
                            startActivity(intent);
                        }
                    }
            );

            (findViewById(R.id.addBowler)).setOnClickListener(
                    new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Globals.role = Constants.Player.bowler;
                            Intent intent = new Intent(TeamSelector.this,MainActivity.class);
                            intent.putExtra("role",Constants.Player.bowler);
                            intent.putExtra("mode","buy");
                            startActivity(intent);
                        }
                    }
            );

            (findViewById(R.id.addAllRounder)).setOnClickListener(
                    new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Globals.role = Constants.Player.all_rounder;
                            Intent intent = new Intent(TeamSelector.this,MainActivity.class);
                            intent.putExtra("role",Constants.Player.all_rounder);
                            intent.putExtra("mode","buy");
                            startActivity(intent);
                        }
                    }
            );

            (findViewById(R.id.addWK)).setOnClickListener(
                    new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Globals.role = Constants.Player.batsman_wk;
                            Intent intent = new Intent(TeamSelector.this,MainActivity.class);
                            intent.putExtra("role",Constants.Player.batsman_wk);
                            intent.putExtra("mode","buy");
                            startActivity(intent);
                        }
                    }
            );

            findViewById(R.id.team_selector_reset).setOnClickListener(
                    new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            batsmen.clear();
                            bowlers.clear();
                            all_rounders.clear();
                            wks.clear();

                            batsmenSelected = bowlerSelected = wicketKeeperSelected = batsmanWKSelected = allRounderSelected = totalPlayerSelected = 0;
                            batsmanAdapter.notifyDataSetChanged();
                            bowlerAdapter.notifyDataSetChanged();
                            all_rounderAdapter.notifyDataSetChanged();
                            wksAdapter.notifyDataSetChanged();
                            Globals.userBalance += usedBalance;
                            usedBalance = 0;
                            updateBalance();
                            Globals.playerTaken.clear();
                        }
                    }
            );

            findViewById(R.id.team_selector_submit).setOnClickListener(
                    new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if(batsmenSelected < 4){
                                new ShowMessage().showMessage(TeamSelector.this, "Too few batsmen", "You must choose at least 4 batsmen",
                                        new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {

                                            }
                                        });
                            }

                            else if(bowlerSelected < 2){
                                new ShowMessage().showMessage(TeamSelector.this, "Too few bowlers", "You must choose at least 2 bowlers",
                                        new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {}
                                        });
                            }

                            else if(bowlerSelected + allRounderSelected < 5){
                                new ShowMessage().showMessage(TeamSelector.this, "Error", "Number of all rounders + bowlers must be at least 5",
                                        new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                            }
                                        });
                            }

                            else if(wicketKeeperSelected < 1){
                                new ShowMessage().showMessage(TeamSelector.this, "Too few wicket keeper", "Number of wicket keeper should be at least 1",
                                        new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                            }
                                        });
                            }

                            else if(batsmanWKSelected > 1){
                                new ShowMessage().showMessage(TeamSelector.this, "Too many batsman + WK", "You can have at most 1 batsman + WK",
                                        new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                            }
                                        });
                            }
                            else if(totalPlayerSelected < 11){
                                new ShowMessage().showMessage(TeamSelector.this, "Too few players", "You must choose at least 11 players",
                                        new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                            }
                                        });
                            }
                            else{
                                new ShowMessage().showMessage(TeamSelector.this, "Success", "Success",
                                        new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                                DatabaseReference ref = FirebaseDatabase.getInstance().getReference();
                                                AlertDialog.Builder alertDialog2 = new AlertDialog.Builder(TeamSelector.this);
                                                alertDialog2.setTitle("Are you sure?");
                                                alertDialog2.setMessage("Do you want to commit this changes?");

                                                alertDialog2.setPositiveButton("Yes",
                                                        new DialogInterface.OnClickListener() {
                                                            @Override
                                                            public void onClick(DialogInterface dialog, int which) {
                                                                MyProcessingDialog myProcessingDialog2 = new MyProcessingDialog(TeamSelector.this,"Please wait","Uploading user data...");
                                                                myProcessingDialog2.show();
                                                                ArrayList<Player> selectedPlayers = new ArrayList<>();
                                                                for(Player x: batsmen){
                                                                    selectedPlayers.add(x);
                                                                }

                                                                for(Player x: bowlers){
                                                                    selectedPlayers.add(x);
                                                                }

                                                                for(Player x: all_rounders){
                                                                    selectedPlayers.add(x);
                                                                }

                                                                for(Player x: wks){
                                                                    selectedPlayers.add(x);
                                                                }

                                                                Globals.user.setUserTeam(selectedPlayers);
                                                                Globals.user.setHasSquad(true);
                                                                FirebaseDatabase.getInstance().getReference().child("users").
                                                                        child(Globals.user.getUid()).setValue(
                                                                                new Gson().toJson(Globals.user)
                                                                        );
                                                                Intent intent = new Intent(TeamSelector.this,HomeActivity.class);
                                                                startActivity(intent);
                                                                finish();
                                                                myProcessingDialog2.dismiss();

                                                            }
                                                        }
                                                        );
                                                alertDialog2.setNegativeButton("No", null);

                                                alertDialog2.show();
                                            }
                                        });
                            }
                        }
                    }
            );
        }
    }


