package com.example.amanullah.myapplication63;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class AdminChoice extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_choice);
        init();
    }

    private void init(){
        Button choiceAdminMode = findViewById(R.id.admin_choice_admin_panel);
        Button choiceAdminGameMode = findViewById(R.id.admin_choice_game_mode);

        choiceAdminMode.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(AdminChoice.this,AdminHome.class);
                        startActivity(intent);
                    }
                }
        );
        
    }
}
