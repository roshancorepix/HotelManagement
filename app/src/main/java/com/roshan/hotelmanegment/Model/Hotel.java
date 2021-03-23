package com.roshan.hotelmanegment.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Hotel {
    @SerializedName("hotelID")
    @Expose
    private String hotelID;
    @SerializedName("hotelLocation")
    @Expose
    private String hotelLocation;
    @SerializedName("hotelName")
    @Expose
    private String hotelName;
    @SerializedName("hotelPrice")
    @Expose
    private String hotelPrice;
    @SerializedName("hotelRating")
    @Expose
    private String hotelRating;
    @SerializedName("imageUrl")
    @Expose
    private String imageUrl;

    public Hotel() {
    }

    public Hotel(String hotelID, String hotelLocation, String hotelName, String hotelPrice, String hotelRating, String imageUrl) {
        this.hotelID = hotelID;
        this.hotelLocation = hotelLocation;
        this.hotelName = hotelName;
        this.hotelPrice = hotelPrice;
        this.hotelRating = hotelRating;
        this.imageUrl = imageUrl;
    }

    public String getHotelID() {
        return hotelID;
    }

    public String getHotelLocation() {
        return hotelLocation;
    }

    public String getHotelName() {
        return hotelName;
    }

    public String getHotelPrice() {
        return hotelPrice;
    }

    public String getHotelRating() {
        return hotelRating;
    }

    public String getImageUrl() {
        return imageUrl;
    }
}
