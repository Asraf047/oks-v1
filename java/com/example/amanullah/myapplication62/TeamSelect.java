package com.example.amanullah.myapplication63;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.Gson;
import com.ramotion.foldingcell.FoldingCell;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public class TeamSelect extends AppCompatActivity {
    final List<Player> bowlers=new ArrayList<>(),batsmen=new ArrayList<>(),all_rounders=new ArrayList<>(),wks=new ArrayList<>(),all_players=new ArrayList<>();
    ListView batsmanListView,bowlerListView,wkListView,allRounderListView,allPlayerListView;
    FoldingAdapter batsmanAdapter, bowlerAdapter, all_rounderAdapter,wksAdapter,all_playerAdapter;
    private int batsmenSelected = 0,bowlerSelected = 0, allRounderSelected = 0, wicketKeeperSelected = 0, allPlayerSelected = 0;
    private int batsmanWKSelected = 0,totalPlayerSelected = 0;
    private long i1 = 0;
    private int usedBalance;
    private TextView balanceTextView1,balanceTextView2,balanceTextView3,balanceTextView4,balanceTextView5,c1,c2,c3,c4,c5;
    private MyProcessingDialog myProcessingDialog;
    FloatingActionButton f1,f2,f3,f4,f5,f6,f7,f8;
    String type="Batsman";
    boolean submit=false;

    private ActionBar toolbar;
    ViewGroup header1;
    ViewGroup header2;
    ViewGroup header3;
    ViewGroup header4;
    ViewGroup header5;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_team_selector2);

//        header = View.inflate(this, R.layout.header, null);


        init();
        new ShowMessage().showMessage(TeamSelect.this, "Make your squad", "You have to make an initial squad " +
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

        toolbar = getSupportActionBar();
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        toolbar.setTitle("Batsman");
    }

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_shop:
                    toolbar.setTitle("Batsman");
                    batsmanListView.setVisibility(View.VISIBLE);
                    bowlerListView.setVisibility(View.GONE);
                    wkListView.setVisibility(View.GONE);
                    allRounderListView.setVisibility(View.GONE);
                    allPlayerListView.setVisibility(View.GONE);
                    f1.setVisibility(View.VISIBLE);
                    if(submit){
                        f2.setVisibility(View.VISIBLE);
                    } else {
                        f2.setVisibility(View.GONE);
                    }
                    Globals.role = Constants.Player.batsman;
                    type="Batsman";
                    return true;
                case R.id.navigation_gifts:
                    toolbar.setTitle("Bowler");
                    batsmanListView.setVisibility(View.GONE);
                    bowlerListView.setVisibility(View.VISIBLE);
                    wkListView.setVisibility(View.GONE);
                    allRounderListView.setVisibility(View.GONE);
                    allPlayerListView.setVisibility(View.GONE);
                    f1.setVisibility(View.VISIBLE);
                    if(submit){
                        f2.setVisibility(View.VISIBLE);
                    } else {
                        f2.setVisibility(View.GONE);
                    }
                    Globals.role = Constants.Player.bowler;
                    type="Bowler";
                    return true;
                case R.id.navigation_cart:
                    toolbar.setTitle("Wicket Keeper");
                    batsmanListView.setVisibility(View.GONE);
                    bowlerListView.setVisibility(View.GONE);
                    wkListView.setVisibility(View.VISIBLE);
                    allRounderListView.setVisibility(View.GONE);
                    allPlayerListView.setVisibility(View.GONE);
                    f1.setVisibility(View.VISIBLE);
                    if(submit){
                        f2.setVisibility(View.VISIBLE);
                    } else {
                        f2.setVisibility(View.GONE);
                    }
                    Globals.role = Constants.Player.batsman_wk;
                    type="Batsman + WK";
                    return true;
                case R.id.navigation_profile:
                    toolbar.setTitle("All Rounder");
                    batsmanListView.setVisibility(View.GONE);
                    bowlerListView.setVisibility(View.GONE);
                    wkListView.setVisibility(View.GONE);
                    allRounderListView.setVisibility(View.VISIBLE);
                    allPlayerListView.setVisibility(View.GONE);
                    f1.setVisibility(View.VISIBLE);
                    if(submit){
                        f2.setVisibility(View.VISIBLE);
                    } else {
                        f2.setVisibility(View.GONE);
                    }
                    Globals.role = Constants.Player.all_rounder;
                    type="All rounder";
                    return true;
                case R.id.navigation_all:
                    toolbar.setTitle("All Player");
                    batsmanListView.setVisibility(View.GONE);
                    bowlerListView.setVisibility(View.GONE);
                    wkListView.setVisibility(View.GONE);
                    allRounderListView.setVisibility(View.GONE);
                    allPlayerListView.setVisibility(View.VISIBLE);
                    f1.setVisibility(View.GONE);
                    f2.setVisibility(View.GONE);
                    Globals.role = Constants.Player.all_rounder;
                    type="All Player";
                    return true;
            }
            return false;
        }
    };

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
        batsmanAdapter.notifyDataSetChanged();
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
//        all_players.clear();
//        all_players.addAll(batsmen);
//        all_playerAdapter.notifyDataSetChanged();
    }
    private void addBowler(Player p){
        bowlers.add(p);
        bowlerAdapter.notifyDataSetChanged();
        bowlerSelected++;
        totalPlayerSelected++;
        int temp = p.getPrice();
        Globals.userBalance -= temp;
        updateBalance();
        usedBalance += temp;
    }
    private void addAllRounder(Player p){
        all_rounders.add(p);
        all_rounderAdapter.notifyDataSetChanged();
        allRounderSelected++;
        totalPlayerSelected++;
        int temp = p.getPrice();
        Globals.userBalance -= temp;
        updateBalance();
        usedBalance += temp;
    }
    private void addWK(Player p){
        wks.add(p);
        wksAdapter.notifyDataSetChanged();
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
        balanceTextView1.setText(Globals.userBalance.toString());
        balanceTextView2.setText(Globals.userBalance.toString());
        balanceTextView3.setText(Globals.userBalance.toString());
        balanceTextView4.setText(Globals.userBalance.toString());
        balanceTextView5.setText(Globals.userBalance.toString());
        c1.setText(totalPlayerSelected+"");
        c2.setText(totalPlayerSelected+"");
        c3.setText(totalPlayerSelected+"");
        c4.setText(totalPlayerSelected+"");
        c5.setText(totalPlayerSelected+"");
        submit=true;
        f2.setVisibility(View.VISIBLE);
    }

    private void init() {
        usedBalance = 0;
        Globals.playerTaken = new HashSet<>();
        Globals.userBalance = Integer.parseInt(Globals.user.getBalance());
        Log.i("Login",Globals.userBalance+ " valuees "+Globals.user.getBalance());
        batsmanListView = findViewById(R.id.mainListView1);
        bowlerListView = findViewById(R.id.mainListView2);
        allRounderListView = findViewById(R.id.mainListView3);
        wkListView = findViewById(R.id.mainListView4);
        allPlayerListView = findViewById(R.id.mainListView5);
        myProcessingDialog = new MyProcessingDialog(TeamSelect.this, "Please wait...", "Downloading data");
        f1 = (FloatingActionButton) findViewById(R.id.f1);
        f2 = (FloatingActionButton) findViewById(R.id.f2);
        f2.setVisibility(View.GONE);

        LayoutInflater inflater = getLayoutInflater();
        header1 = (ViewGroup)inflater.inflate(R.layout.header, batsmanListView, false);
        batsmanListView.addHeaderView(header1, null, false);
        header2 = (ViewGroup)inflater.inflate(R.layout.header, bowlerListView, false);
        bowlerListView.addHeaderView(header2, null, false);
        header3 = (ViewGroup)inflater.inflate(R.layout.header, wkListView, false);
        wkListView.addHeaderView(header3, null, false);
        header4 = (ViewGroup)inflater.inflate(R.layout.header, allRounderListView, false);
        allRounderListView.addHeaderView(header4, null, false);
        header5 = (ViewGroup)inflater.inflate(R.layout.header, allPlayerListView, false);
        allPlayerListView.addHeaderView(header5, null, false);
        balanceTextView1 = header1.findViewById(R.id.title_weight2);
        balanceTextView2 = header2.findViewById(R.id.title_weight2);
        balanceTextView3 = header3.findViewById(R.id.title_weight2);
        balanceTextView4 = header4.findViewById(R.id.title_weight2);
        balanceTextView5 = header5.findViewById(R.id.title_weight2);
        c1 = header1.findViewById(R.id.counts);
        c2 = header2.findViewById(R.id.counts);
        c3 = header3.findViewById(R.id.counts);
        c4 = header4.findViewById(R.id.counts);
        c5 = header5.findViewById(R.id.counts);
        c1.setText("0");
        c2.setText("0");
        c3.setText("0");
        c4.setText("0");
        c5.setText("0");
    }

    private void setupViews(){
        batsmanAdapter = new FoldingAdapter(this, batsmen);
        bowlerAdapter = new FoldingAdapter(this, bowlers);
        all_rounderAdapter = new FoldingAdapter(this, all_rounders);
        wksAdapter = new FoldingAdapter(this, wks);
        all_playerAdapter = new FoldingAdapter(this, all_players);

        batsmanListView.setAdapter(batsmanAdapter);
        bowlerListView.setAdapter(bowlerAdapter);
        allRounderListView.setAdapter(all_rounderAdapter);
        wkListView.setAdapter(wksAdapter);
        allPlayerListView.setAdapter(all_playerAdapter);

        batsmanAdapter.setDefaultRequestBtnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final int position = batsmanListView.getPositionForView((View) v.getParent());
                Player player = batsmen.get(position-1);
                Log.i("Login",Globals.userBalance+ " valuees= "+player.getPrice());

                switch (v.getId()) {
                    case R.id.content_request_btn: {
                            if(Globals.userBalance >= player.getPrice()){
                                Toast.makeText(getApplicationContext(),position+ " Price= "+ player.getPrice() + "TK", Toast.LENGTH_SHORT).show();
                            }else {
                                Toast.makeText(getApplicationContext(),position+ " Sorry, Not enough balance.", Toast.LENGTH_SHORT).show();
                            }
                        break;
                    }
                    case R.id.content_request_btn2:
                        Toast.makeText(getApplicationContext(),position+ " BUTTONS 2", Toast.LENGTH_SHORT).show();
                        break;

                    default:
                        break;
                }
            }
        });
        batsmanListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int pos, long l) {
                ((FoldingCell) view).toggle(false);
                batsmanAdapter.registerToggle(pos);
            }
        });

        bowlerAdapter.setDefaultRequestBtnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final int position = bowlerListView.getPositionForView((View) v.getParent());
                Player player = bowlers.get(position-1);
                Log.i("Login",Globals.userBalance+ " valuees= "+player.getPrice());

                switch (v.getId()) {
                    case R.id.content_request_btn: {
                        if(Globals.userBalance >= player.getPrice()){
                            Toast.makeText(getApplicationContext(),position+ " Price= "+ player.getPrice() + "TK", Toast.LENGTH_SHORT).show();
                        }else {
                            Toast.makeText(getApplicationContext(),position+ " Sorry, Not enough balance.", Toast.LENGTH_SHORT).show();
                        }
                        break;
                    }
                    case R.id.content_request_btn2:
                        Toast.makeText(getApplicationContext(),position+ " BUTTONS 2", Toast.LENGTH_SHORT).show();
                        break;

                    default:
                        break;
                }
            }
        });
        bowlerListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int pos, long l) {
                ((FoldingCell) view).toggle(false);
                bowlerAdapter.registerToggle(pos);
            }
        });

        wksAdapter.setDefaultRequestBtnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final int position = wkListView.getPositionForView((View) v.getParent());
                Player player = wks.get(position-1);
                Log.i("Login",Globals.userBalance+ " valuees= "+player.getPrice());

                switch (v.getId()) {
                    case R.id.content_request_btn: {
                        if(Globals.userBalance >= player.getPrice()){
                            Toast.makeText(getApplicationContext(),position+ " Price= "+ player.getPrice() + "TK", Toast.LENGTH_SHORT).show();
                        }else {
                            Toast.makeText(getApplicationContext(),position+ " Sorry, Not enough balance.", Toast.LENGTH_SHORT).show();
                        }
                        break;
                    }
                    case R.id.content_request_btn2:
                        Toast.makeText(getApplicationContext(),position+ " BUTTONS 2", Toast.LENGTH_SHORT).show();
                        break;

                    default:
                        break;
                }
            }
        });
        wkListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int pos, long l) {
                ((FoldingCell) view).toggle(false);
                wksAdapter.registerToggle(pos);
            }
        });

        all_rounderAdapter.setDefaultRequestBtnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final int position = allRounderListView.getPositionForView((View) v.getParent());
                Player player = all_rounders.get(position-1);
                Log.i("Login",Globals.userBalance+ " valuees= "+player.getPrice());

                switch (v.getId()) {
                    case R.id.content_request_btn: {
                        if(Globals.userBalance >= player.getPrice()){
                            Toast.makeText(getApplicationContext(),position+ " Price= "+ player.getPrice() + "TK", Toast.LENGTH_SHORT).show();
                        }else {
                            Toast.makeText(getApplicationContext(),position+ " Sorry, Not enough balance.", Toast.LENGTH_SHORT).show();
                        }
                        break;
                    }
                    case R.id.content_request_btn2:
                        Toast.makeText(getApplicationContext(),position+ " BUTTONS 2", Toast.LENGTH_SHORT).show();
                        break;

                    default:
                        break;
                }
            }
        });
        allRounderListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int pos, long l) {
                ((FoldingCell) view).toggle(false);
                all_rounderAdapter.registerToggle(pos);
            }
        });

        all_playerAdapter.setDefaultRequestBtnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final int position = allPlayerListView.getPositionForView((View) v.getParent());
                Player player = all_players.get(position-1);
                Log.i("Login",Globals.userBalance+ " valuees= "+player.getPrice());

                switch (v.getId()) {
                    case R.id.content_request_btn: {
                        if(Globals.userBalance >= player.getPrice()){
                            Toast.makeText(getApplicationContext(),position+ " Price= "+ player.getPrice() + "TK", Toast.LENGTH_SHORT).show();
                        }else {
                            Toast.makeText(getApplicationContext(),position+ " Sorry, Not enough balance.", Toast.LENGTH_SHORT).show();
                        }
                        break;
                    }
                    case R.id.content_request_btn2:
                        Toast.makeText(getApplicationContext(),position+ " BUTTONS 2", Toast.LENGTH_SHORT).show();
                        break;

                    default:
                        break;
                }
            }
        });
        allPlayerListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int pos, long l) {
                ((FoldingCell) view).toggle(false);
                all_playerAdapter.registerToggle(pos);
            }
        });


            (findViewById(R.id.f1)).setOnClickListener(
                    new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
//                            Globals.role = Constants.Player.batsman;
//                            Intent intent = new Intent(TeamSelector.this,ViewPlayerList.class);
                            Intent intent = new Intent(TeamSelect.this,MainActivity.class);
                            intent.putExtra("role",type);
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

            findViewById(R.id.f2).setOnClickListener(
                    new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if(batsmenSelected < 4){
                                new ShowMessage().showMessage(TeamSelect.this, "Too few batsmen", "You must choose at least 4 batsmen",
                                        new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {

                                            }
                                        });
                            }

                            else if(bowlerSelected < 2){
                                new ShowMessage().showMessage(TeamSelect.this, "Too few bowlers", "You must choose at least 2 bowlers",
                                        new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {}
                                        });
                            }

                            else if(bowlerSelected + allRounderSelected < 5){
                                new ShowMessage().showMessage(TeamSelect.this, "Error", "Number of all rounders + bowlers must be at least 5",
                                        new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                            }
                                        });
                            }

                            else if(wicketKeeperSelected < 1){
                                new ShowMessage().showMessage(TeamSelect.this, "Too few wicket keeper", "Number of wicket keeper should be at least 1",
                                        new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                            }
                                        });
                            }

                            else if(batsmanWKSelected > 1){
                                new ShowMessage().showMessage(TeamSelect.this, "Too many batsman + WK", "You can have at most 1 batsman + WK",
                                        new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                            }
                                        });
                            }
                            else if(totalPlayerSelected < 11){
                                new ShowMessage().showMessage(TeamSelect.this, "Too few players", "You must choose at least 11 players",
                                        new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                            }
                                        });
                            }
                            else{
                                new ShowMessage().showMessage(TeamSelect.this, "Success", "Success",
                                        new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                                DatabaseReference ref = FirebaseDatabase.getInstance().getReference();
                                                AlertDialog.Builder alertDialog2 = new AlertDialog.Builder(TeamSelect.this);
                                                alertDialog2.setTitle("Are you sure?");
                                                alertDialog2.setMessage("Do you want to commit this changes?");

                                                alertDialog2.setPositiveButton("Yes",
                                                        new DialogInterface.OnClickListener() {
                                                            @Override
                                                            public void onClick(DialogInterface dialog, int which) {
                                                                MyProcessingDialog myProcessingDialog2 = new MyProcessingDialog(TeamSelect.this,"Please wait","Uploading user data...");
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
                                                                Intent intent = new Intent(TeamSelect.this,HomeActivity.class);
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


