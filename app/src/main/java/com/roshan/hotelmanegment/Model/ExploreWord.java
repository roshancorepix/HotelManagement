package com.roshan.hotelmanegment.Model;

public class ExploreWord {
    private int countryImage;
    private String countryName;
    private String sayDays;
    private String packageAmount;

    public ExploreWord(int countryImage, String countryName, String sayDays, String packageAmount) {
        this.countryImage = countryImage;
        this.countryName = countryName;
        this.sayDays = sayDays;
        this.packageAmount = packageAmount;
    }

    public int getCountryImage() {
        return countryImage;
    }

    public String getCountryName() {
        return countryName;
    }

    public String getSayDays() {
        return sayDays;
    }

    public String getPackageAmount() {
        return packageAmount;
    }
}
