package com.example.amanullah.myapplication63;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.ramotion.foldingcell.FoldingCell;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private Player player;
    private String mode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);

        // get our list view
        final ListView theListView = (ListView) findViewById(R.id.mainListView);

        mode = getIntent().getStringExtra("mode");

        final List<Player> players = new ArrayList<>();
        String playerRole = getIntent().getStringExtra("role");
        for(Player x: Globals.allPlayers){
            if(x.getRole().equals(playerRole) && !(Globals.playerTaken).contains(x.getId())){
                players.add(x);
            } else if(playerRole.equals("All Player") && !(Globals.playerTaken).contains(x.getId())){
                players.add(x);
            }
        }

        final FoldingAdapter adapter = new FoldingAdapter(this, players);

        adapter.setDefaultRequestBtnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final int position = theListView.getPositionForView((View) v.getParent());
                player = players.get(position);
                Log.i("Login",Globals.userBalance+ " valuees= "+player.getPrice());
                //Toast.makeText(getApplicationContext(),position+ " DEFAULT HANDLER FOR ALL BUTTONS", Toast.LENGTH_SHORT).show();

                switch (v.getId()) {

                    case R.id.content_request_btn: {
                        if(mode.equals("buy")){
                            if(Globals.userBalance >= player.getPrice()){
                                Toast.makeText(getApplicationContext(),position+ " Buy Successfull. ("+ player.getPrice() + "TK)", Toast.LENGTH_SHORT).show();
                                Globals.role = player.getRole();
                                Globals.hasPlayer = true;
                                Globals.temp = player;
                                Intent intent = new Intent(MainActivity.this,TeamSelectActivity.class);
                                intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                                startActivityIfNeeded(intent,0);
                                finish();
                            }else {
                                Toast.makeText(getApplicationContext(),position+ " Sorry, Not enough balance.", Toast.LENGTH_SHORT).show();
                            }
                        }else if(mode.equals("buyOwner")){
                            if(Globals.userBalance >= player.getPrice()){
                                Toast.makeText(getApplicationContext(),position+ " Buy Successfull. ("+ player.getPrice() + "TK)", Toast.LENGTH_SHORT).show();
                                Globals.role = player.getRole();
                                Globals.hasPlayer = true;
                                Globals.temp = player;
                                Intent intent = new Intent(MainActivity.this,OwnerActivity.class);
                                intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                                startActivityIfNeeded(intent,0);
                                finish();
                            }else {
                                Toast.makeText(getApplicationContext(),position+ " Sorry, Not enough balance.", Toast.LENGTH_SHORT).show();
                            }
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

        // set elements to adapter
        theListView.setAdapter(adapter);

        // set on click event listener to list view
        theListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int pos, long l) {
                ((FoldingCell) view).toggle(false);
                adapter.registerToggle(pos);
            }
        });

    }
}
