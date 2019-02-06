package com.example.amanullah.myapplication63;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.Gson;

public class LoginActivity extends AppCompatActivity {

    private EditText email,password;
    private MyProcessingDialog myProcessingDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        init();
        setOnClickListeners();
    }

    private String getEmail(){
        String emailS = email.getText().toString();
        return emailS;
    }
    private String getPassword(){
        String passwordS = password.getText().toString();
        return passwordS;
    }
    private boolean checkEmailValidity(){
        String emailString = email.getText().toString();

        if(emailString == null){
            return false;
        }
        else if(emailString.equals("")){
            return false;
        }
        else{
            Integer occurence = 0;
            StringBuilder sb = new StringBuilder();
            char lastAppend = 0;
            for(Integer i = 0; i < emailString.length(); i++){
                if(emailString.charAt(i) == '@'){
                    occurence++;
                }
            }

            if(occurence != 1){
                return false;
            }
            return true;
            /*
            for(Integer i = 0; i < emailString.length(); i++){
                char ch = emailString.charAt(i);

                if(ch >= 'A' && ch <= 'Z' || ch >= 'a' && ch <= 'z' || ch >= '0' && ch <= '9')
                    if(lastAppend != 't') {
                        sb.append('t');
                        lastAppend = 't';
                    }
                else if(ch == '@'){
                    lastAppend = 'a';
                    sb.append('a');
                }
                else if(ch == '.'){
                    lastAppend = 'd'
                }
            }
            */
        }
    }
    private boolean checkPassswordValidity(){
        String passwordText = password.getText().toString();

        if(password == null)
            return false;
        else if(password.equals(""))
            return false;
        return true;
    }
    private void init() {
        email = findViewById(R.id.editText);
        password = findViewById(R.id.editText3);
        myProcessingDialog = new MyProcessingDialog(LoginActivity.this,"Please wait","Logging in");
    }

    private void setOnClickListeners(){
        (findViewById(R.id.button3)).setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent intent = new Intent(LoginActivity.this,RegisterActivity.class);
                startActivity(intent);
                finish();
            }
        });

        (findViewById(R.id.button)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkEmailValidity() && checkPassswordValidity()) {
                    myProcessingDialog.show();
                    FirebaseAuth.getInstance().signInWithEmailAndPassword(getEmail(), getPassword()).
                            addOnCompleteListener(
                                    new OnCompleteListener<AuthResult>() {
                                        @Override
                                        public void onComplete(@NonNull Task<AuthResult> task) {
                                            if (task.isSuccessful()) {
                                                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                                                Globals.user = new User();
                                                Globals.user.setUid(user.getUid());
                                                FirebaseDatabase.getInstance().getReference().child("users").child(user.getUid()).addListenerForSingleValueEvent(
                                                        new ValueEventListener() {
                                                            @Override
                                                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                                                Globals.user = new Gson().fromJson(dataSnapshot.getValue().toString(),User.class);
                                                                Log.i("Login", "valuees "+dataSnapshot.getValue().toString());
                                                                if(Globals.user.isAdmin()){
                                                                    Intent intent = new Intent(LoginActivity.this,AdminHome.class);
                                                                    startActivity(intent);
                                                                    finish();
                                                                }
                                                                else{
                                                                    if(Globals.user.isHasSquad()){
                                                                        Intent intent = new Intent(LoginActivity.this,OwnerActivity.class);
                                                                        startActivity(intent);
                                                                        finish();
                                                                    }
                                                                    else{
                                                                        Intent intent = new Intent(LoginActivity.this,TeamSelectActivity.class);
                                                                        startActivity(intent);
                                                                        finish();
                                                                    }
                                                                }
                                                                myProcessingDialog.dismiss();
                                                            }
                                                            @Override
                                                            public void onCancelled(@NonNull DatabaseError databaseError) {
                                                                myProcessingDialog.dismiss();
                                                            }
                                                        }
                                                );
                                            } else {
                                                myProcessingDialog.dismiss();
                                                new ShowMessage().showMessage(LoginActivity.this, "Error", "Email and password don't match", null);
                                            }
                                        }
                                    }
                            );
                } else {
                    if (checkEmailValidity() == false) {
                        new ShowMessage().showMessage(LoginActivity.this, "Error", "Invalid email", null);
                    } else {

                    }
                }
            }
        });
    }
}
