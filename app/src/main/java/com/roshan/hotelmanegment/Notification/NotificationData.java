package com.roshan.hotelmanegment.Notification;

public class NotificationData {
    private String user;
    private String title;
    private String body;
    private int icon;
    private String sented;

    public NotificationData() {
    }

    public NotificationData(String user, String title, String body, int icon, String sented) {
        this.user = user;
        this.title = title;
        this.body = body;
        this.icon = icon;
        this.sented = sented;
    }

    public String getUser() {
        return user;
    }

    public String getTitle() {
        return title;
    }

    public String getBody() {
        return body;
    }

    public int getIcon() {
        return icon;
    }

    public String getSented() {
        return sented;
    }
}
