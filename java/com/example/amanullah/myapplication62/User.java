package com.example.amanullah.myapplication63;

import java.util.ArrayList;

public class User extends  Person{
    private String uid;
    private String roll;
    private boolean hasSquad;
    private boolean isAdmin;
    public String balance;

    ArrayList<Player> userTeam;

    public User() {
    }

    public boolean isAdmin() {
        return isAdmin;
    }

    public void setAdmin(boolean admin) {
        isAdmin = admin;
    }

    public boolean isHasSquad() {
        return hasSquad;
    }

    public void setHasSquad(boolean hasSquad) {
        this.hasSquad = hasSquad;
    }

    public ArrayList<Player> getUserTeam() {
        return userTeam;
    }

    public void setUserTeam(ArrayList<Player> userTeam) {
        this.userTeam = userTeam;
    }

    public String getBalance() {
        return balance;
    }

    public void setBalance(String balance) {
        this.balance = balance;
    }

    public String getRoll() {
        return roll;
    }

    public void setRoll(String roll) {
        this.roll = roll;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }
}
