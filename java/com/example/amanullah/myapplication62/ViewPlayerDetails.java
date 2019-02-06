package com.example.amanullah.myapplication63;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.gson.Gson;

public class ViewPlayerDetails extends AppCompatActivity {
    private Player player;
    private String mode;
    private View.OnClickListener buyOnClickListener;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_player_details);
        init();
    }

    private void init(){

        player = new Gson().fromJson(getIntent().getStringExtra("player"),Player.class);
        Log.i("Login",Globals.userBalance+ " valuees "+player.getPrice());
        mode = getIntent().getStringExtra("mode");
        ((TextView)(findViewById(R.id.player_details_player_name))).setText(player.getName());
        ((TextView)(findViewById(R.id.player_details_player_role))).setText(player.getRole());
        ((TextView)(findViewById(R.id.player_details_player_score))).setText((player.getScore()+""));
        //((ImageView)(findViewById(R.id.player_details_player_image))).setImageBitmap(player.getPicture());
        //Glide.with(ViewPlayerDetails.this).load(player.getPicture()).apply(RequestOptions.circleCropTransform()).into( ((ImageView)(findViewById(R.id.player_details_player_image))));
        Button submitButton = (Button) findViewById(R.id.view_player_button);
        if(mode.equals("buy")){
            submitButton.setText("Buy (" + player.getPrice() + "TK)");
            if(Globals.userBalance < player.getPrice()){
                submitButton.setEnabled(false);
            }
        }
        buyOnClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ViewPlayerList.fa.finish();
                Globals.role = player.getRole();
                Globals.hasPlayer = true;
                Globals.temp = player;
                Intent intent = new Intent(ViewPlayerDetails.this,TeamSelector.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                startActivityIfNeeded(intent,0);
                finish();

            }
        };
        if(mode.equals("buy")){
            submitButton.setOnClickListener(buyOnClickListener);
        }

    }
}
