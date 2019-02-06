package com.example.amanullah.myapplication63;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;


public class ShowTeamFragment extends Fragment {
    private View inflatedView;
    public ShowTeamFragment() {
    }

    private void setupList(){
        RecyclerView recyclerViewBatsman = inflatedView.findViewById(R.id.fragment_show_team_batsmen_rv),
                recyclerViewBowler = inflatedView.findViewById(R.id.fragment_show_team_bowler_rv),
                recyclerViewAllRounder = inflatedView.findViewById(R.id.fragment_show_team_all_rounder_rv),
                recyclerViewWK = inflatedView.findViewById(R.id.fragment_show_team_wk_rv);

        PlayerAdapter3 batsmanAdapter,bowlerAdapter,allRounderAdapter,wkAdapter;
        ArrayList<Player> batsmen = new ArrayList<>(),
                bowlers = new ArrayList<>(),
                allRounders = new ArrayList<>(),
                wks = new ArrayList<>();

        ArrayList <Player> userTeam = Globals.user.getUserTeam();
        for(Player x: userTeam){
            if(x.getRole().equals(Constants.Player.batsman)){
                batsmen.add(x);
            }
            if(x.getRole().equals(Constants.Player.bowler)){
                bowlers.add(x);
            }

            if(x.getRole().equals(Constants.Player.all_rounder)){
                allRounders.add(x);
            }

            if(x.getRole().equals(Constants.Player.batsman_wk)){
                wks.add(x);
            }
        }
        batsmanAdapter = new PlayerAdapter3(batsmen,getActivity());
        bowlerAdapter = new PlayerAdapter3(bowlers,getActivity());
        allRounderAdapter = new PlayerAdapter3(allRounders,getActivity());
        wkAdapter = new PlayerAdapter3(wks,getActivity());

        if(recyclerViewBatsman == null){
            Log.d("NullPointerDei","eta null");
        }
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL,false);
        LinearLayoutManager linearLayoutManager2 = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL,false);
        LinearLayoutManager linearLayoutManager3 = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL,false);
        LinearLayoutManager linearLayoutManager4 = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL,false);

        recyclerViewBatsman.setLayoutManager(linearLayoutManager);
        recyclerViewBowler.setLayoutManager(linearLayoutManager2);
        recyclerViewAllRounder.setLayoutManager(linearLayoutManager3);
        recyclerViewWK.setLayoutManager(linearLayoutManager4);

        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(recyclerViewBatsman.getContext(),linearLayoutManager.getOrientation());
        recyclerViewBatsman.addItemDecoration(dividerItemDecoration);

        DividerItemDecoration dividerItemDecoration2 = new DividerItemDecoration(recyclerViewBowler.getContext(),linearLayoutManager2.getOrientation());
        recyclerViewBowler.addItemDecoration(dividerItemDecoration2);

        DividerItemDecoration dividerItemDecoration3 = new DividerItemDecoration(recyclerViewAllRounder.getContext(),linearLayoutManager3.getOrientation());
        recyclerViewAllRounder.addItemDecoration(dividerItemDecoration3);

        DividerItemDecoration dividerItemDecoration4 = new DividerItemDecoration(recyclerViewWK.getContext(),linearLayoutManager4.getOrientation());
        recyclerViewWK.addItemDecoration(dividerItemDecoration4);


        recyclerViewBatsman.setAdapter(batsmanAdapter);
        recyclerViewBowler.setAdapter(bowlerAdapter);
        recyclerViewAllRounder.setAdapter(allRounderAdapter);
        recyclerViewWK.setAdapter(wkAdapter);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        inflatedView = inflater.inflate(R.layout.fragment_show_team, container, false);
        setupList();
        return inflatedView;
    }

}
