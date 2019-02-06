package com.example.amanullah.myapplication63;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class RegisterActivity extends AppCompatActivity {

    private EditText firstName, lastName, email, password, confirmedPassword, roll;
    private FirebaseAuth mAuth;
    private Context thisContext;
    private DialogInterface.OnClickListener dialogOnClick;
    private MyProcessingDialog myProcessingDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        getSupportActionBar().setTitle("Registration");
        init();
        setOnRegisterClick();
    }

    private boolean checkFirstName() {
        boolean result = true;
        String first_name = getFirstName();
        if (!StringHandle.isNotNullAndEmpty(first_name)) return false;
        for (int i = 0; i < first_name.length(); i++) {
            char ch = first_name.charAt(i);
            if (!((ch >= 'A' && ch <= 'Z') || (ch >= 'a' && ch <= 'z'))) {
                result = false;
            }
        }
        return result;
    }
    private boolean checkLastName() {
        boolean result = true;
        String last_name = getLastName();

        if (!StringHandle.isNotNullAndEmpty(last_name)) return false;

        for (int i = 0; i < last_name.length(); i++) {
            char ch = last_name.charAt(i);
            if (!((ch >= 'A' && ch <= 'Z') || (ch >= 'a' && ch <= 'z'))) {
                result = false;
            }
        }
        return result;
    }
    private boolean checkEmail() {
        String emailString = getEmail();
        if (!StringHandle.isNotNullAndEmpty(emailString)) return false;
        else {
            int occurence = 0;
            for (int i = 0; i < emailString.length(); i++) {
                if (emailString.charAt(i) == '@') {
                    occurence++;
                }
            }

            return occurence == 1;
        }
    }
    private boolean checkPassword() {
        String password = getPassword();
        String confirmedPassword = getConfirmedPassword();
        if (!StringHandle.isNotNullAndEmpty(password)) return false;
        if (!StringHandle.isNotNullAndEmpty(confirmedPassword)) return false;
        return password.equals(confirmedPassword);
    }
    private boolean checkRoll() {
        String rollString = getRoll();
        return StringHandle.isNotNullAndEmpty(rollString);
    }
    private String getFirstName() {
        return firstName.getText().toString();
    }
    private String getLastName() {
        return lastName.getText().toString();
    }
    private String getEmail() {
        return email.getText().toString();
    }
    private String getPassword() {
        return password.getText().toString();
    }
    private String getConfirmedPassword() {
        return confirmedPassword.getText().toString();
    }
    private String getRoll() {
        return roll.getText().toString();
    }
    private void init() {
        thisContext = RegisterActivity.this;
        firstName = findViewById(R.id.register_first_name);
        lastName = findViewById(R.id.register_last_name);
        email = findViewById(R.id.register_email);
        password = findViewById(R.id.register_password);
        confirmedPassword = findViewById(R.id.register_confirm_password);
        roll = findViewById(R.id.register_roll);
        mAuth = FirebaseAuth.getInstance();
    }
    private void writeNewUser(User u) {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference();
        myRef.child("users").child(u.getUid()).setValue(new Gson().toJson(u));
    }
    private void setOnRegisterClick(){
        ((Button)findViewById(R.id.register_submit_button)).setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (checkFirstName()) {
                            if (checkLastName()) {
                                if (checkEmail()) {
                                    if (checkPassword()) {
                                        if (checkRoll()) {
                                            myProcessingDialog = new MyProcessingDialog(RegisterActivity.this,"Please wait","Creating new user...");
                                            myProcessingDialog.show();
                                            mAuth.createUserWithEmailAndPassword(getEmail(), getPassword()).addOnCompleteListener(
                                                    new OnCompleteListener<AuthResult>() {
                                                        @Override
                                                        public void onComplete(@NonNull Task<AuthResult> task) {
                                                            if (task.isSuccessful()) {
                                                                User user = new User();
                                                                user.setFirstName(getFirstName());
                                                                user.setLastName(getLastName());
                                                                user.setEmail(getEmail());
                                                                user.setRoll(getRoll());
                                                                user.setBalance("100000");
                                                                user.setUserTeam(new ArrayList<Player>());
                                                                user.setUid(FirebaseAuth.getInstance().getCurrentUser().getUid());
                                                                user.setHasSquad(false);
                                                                user.setAdmin(false);

                                                                writeNewUser(user);
                                                                myProcessingDialog.dismiss();
                                                                new ShowMessage().showMessage(thisContext, "Registration Successful", "Please log in again",
                                                                        new DialogInterface.OnClickListener() {
                                                                            @Override
                                                                            public void onClick(DialogInterface dialog, int which) {
                                                                                Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                                                                                startActivity(intent);
                                                                                finish();
                                                                            }
                                                                        });
                                                            } else {
                                                                myProcessingDialog.dismiss();
                                                                new ShowMessage().showMessage(thisContext, "Error", "Registration error", null);
                                                            }
                                                        }
                                                    }
                                            );
                                        } else {
                                            new ShowMessage().showMessage(thisContext, "Error", "Enter valid roll", null);
                                        }
                                    } else {
                                        new ShowMessage().showMessage(thisContext, "Invalid Password", "Either passwords don't match, or invalid password.", dialogOnClick);
                                    }
                                } else {
                                    new ShowMessage().showMessage(thisContext, "Invalid Email", "Invalid email address", dialogOnClick);
                                }
                            } else {
                                new ShowMessage().showMessage(thisContext, "Last Name", "Enter valid last name", dialogOnClick);
                            }
                        } else {
                            new ShowMessage().showMessage(thisContext, "First Name", "Enter valid first name", dialogOnClick);
                        }
                    }
                }
        );
    }
}