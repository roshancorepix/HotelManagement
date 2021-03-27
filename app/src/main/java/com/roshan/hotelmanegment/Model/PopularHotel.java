package com.roshan.hotelmanegment.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PopularHotel {
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
    @SerializedName("hotelStar")
    @Expose
    private String hotelStar;
    @SerializedName("imageUrl")
    @Expose
    private String imageUrl;
    @SerializedName("hotelRating")
    @Expose
    private String hotelRating;

    public PopularHotel() {
    }

    public PopularHotel(String hotelID, String hotelLocation, String hotelName, String hotelPrice, String hotelStar, String imageUrl, String hotelRating) {
        this.hotelID = hotelID;
        this.hotelLocation = hotelLocation;
        this.hotelName = hotelName;
        this.hotelPrice = hotelPrice;
        this.hotelStar = hotelStar;
        this.imageUrl = imageUrl;
        this.hotelRating = hotelRating;
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

    public String getHotelStar() {
        return hotelStar;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public String getHotelRating() {
        return hotelRating;
    }
}
