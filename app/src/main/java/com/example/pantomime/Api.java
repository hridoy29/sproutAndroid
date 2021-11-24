package com.example.pantomime;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface Api {
    @POST("User")
        Call<ClassRegestration> UserRegestration(
            @Body ClassRegestration ClassRegestration
    );

    @GET("User")
    Call<ArrayList<ClassRegestration>> UserLogin();
}
