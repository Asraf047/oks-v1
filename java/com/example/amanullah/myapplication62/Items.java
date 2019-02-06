package com.example.amanullah.myapplication63;

import android.view.View;

import java.util.ArrayList;

public class Items {
    private String name;
    private String totalScore;
    private String price;
    private String hall;
    private String role;
    private String match;
    private String roll;

    private View.OnClickListener requestBtnClickListener;

    public Items() {
    }

    public Items(String name, String totalScore, String price, String hall, String role, String match, String roll) {
        this.name = name;
        this.totalScore = totalScore;
        this.price = price;
        this.hall = hall;
        this.role = role;
        this.match = match;
        this.roll = roll;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTotalScore() {
        return totalScore;
    }

    public void setTotalScore(String totalScore) {
        this.totalScore = totalScore;
    }

    public String getHall() {
        return hall;
    }

    public void setHall(String hall) {
        this.hall = hall;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getMatch() {
        return match;
    }

    public void setMatch(String match) {
        this.match = match;
    }

    public String getRoll() {
        return roll;
    }

    public void setRoll(String roll) {
        this.roll = roll;
    }

    public View.OnClickListener getRequestBtnClickListener() {
        return requestBtnClickListener;
    }

    public void setRequestBtnClickListener(View.OnClickListener requestBtnClickListener) {
        this.requestBtnClickListener = requestBtnClickListener;
    }

    public static ArrayList<Items> getTestingList() {
        ArrayList<Items> items = new ArrayList<>();
        items.add(new Items("Amanullah Asraf", "78", "$270", "Amar Ekushy Hall", "batsman", "4", "1507047"));
        items.add(new Items("Abu Bokor", "71", "$255", "Bangabandhu Shiekh Mujibur Rahman", "bowler", "2", "1502065"));
        items.add(new Items("Awanur Rahman", "83", "$270", "Amar Ekushy Hall", "bowler", "3", "1507055"));
        items.add(new Items("Sowrov Khan", "39", "$135", "Bangabandhu Shiekh Mujibur Rahman", "batsman", "9", "1508061"));
        items.add(new Items("Amanullah Asraf", "78", "$270", "Amar Ekushy Hall", "batsman", "4", "1507047"));
        items.add(new Items("Abu Bokor", "71", "$255", "Bangabandhu Shiekh Mujibur Rahman", "bowler", "2", "1502065"));
        items.add(new Items("Awanur Rahman", "83", "$270", "Amar Ekushy Hall", "bowler", "3", "1507055"));
        items.add(new Items("Sowrov Khan", "39", "$135", "Bangabandhu Shiekh Mujibur Rahman", "batsman", "9", "1508061"));
        items.add(new Items("Amanullah Asraf", "78", "$270", "Amar Ekushy Hall", "batsman", "4", "1507047"));
        items.add(new Items("Abu Bokor", "71", "$255", "Bangabandhu Shiekh Mujibur Rahman", "bowler", "2", "1502065"));
        items.add(new Items("Awanur Rahman", "83", "$270", "Amar Ekushy Hall", "bowler", "3", "1507055"));
        items.add(new Items("Sowrov Khan", "39", "$135", "Bangabandhu Shiekh Mujibur Rahman", "batsman", "9", "1508061"));
        return items;

    }

}
