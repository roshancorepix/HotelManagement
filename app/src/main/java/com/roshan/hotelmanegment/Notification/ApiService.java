package com.roshan.hotelmanegment.Notification;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface ApiService {

    @Headers(
            {
                    "Content-Type:application/json",
                    "Authorization:key=AAAAGaugVPs:APA91bG56k7AX37N1dt0KzhrbnsOJYoVtjulWZ5Gq_ObP2rCYBoI4LNuCWNVdA64AAUSyyqgEohVkbQqEWXb64TWQtm3BRiOPLrGHlK-c4zbH3rKXpORAzcauGKp9GbYW0wnQuRLbDKb"
            }
    )
    @POST("fcm/send")
    Call<Response> sendNotification(@Body Sender body);
}
