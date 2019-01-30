package com.example.amanullah.myapplication62;

/**
 * Created by AMANULLAH on 05-Feb-18.
 */

public class Player {
    String name;
//    int image;
    String price;
    String point;
    String details;

    public Player() {
    }

    public Player(String name, String price, String point, String details) {
        this.name = name;
        this.price = price;
        this.point = point;
        this.details = details;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getPoint() {
        return point;
    }

    public void setPoint(String point) {
        this.point = point;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }
}
