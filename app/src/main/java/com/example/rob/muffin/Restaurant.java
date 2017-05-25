package com.example.rob.muffin;

/**
 * Created by roberthurst on 25/05/2017.
 */

public class Restaurant {

    private String id;
    private String Name;
    private String Address;
    private int Rating;

    public Restaurant(){

    };
    public Restaurant(String id, String Name, String Address, int Rating){

        this.id = id;
        this.Name = Name;
        this.Address = Address;
        this.Rating = Rating;
    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getAddress() {
        return Address;
    }

    public void setAddress(String address) {
        Address = address;
    }

    public int getRating() {
        return Rating;
    }

    public void setRating(int rating) {
        Rating = rating;
    }
}
