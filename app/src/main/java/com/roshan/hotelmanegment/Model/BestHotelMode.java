package com.roshan.hotelmanegment.Model;

public class BestHotelMode {
    private int hotelImage;
    private String hotelName;
    private String hotelLocation;
    private String hotelPrice;
    private float hotelRating;

    public BestHotelMode(int hotelImage, String hotelName, String hotelLocation, String hotelPrice, float hotelRating) {
        this.hotelImage = hotelImage;
        this.hotelName = hotelName;
        this.hotelLocation = hotelLocation;
        this.hotelPrice = hotelPrice;
        this.hotelRating = hotelRating;
    }

    public int getHotelImage() {
        return hotelImage;
    }

    public String getHotelName() {
        return hotelName;
    }

    public String getHotelLocation() {
        return hotelLocation;
    }

    public String getHotelPrice() {
        return hotelPrice;
    }

    public float getHotelRating() {
        return hotelRating;
    }
}
