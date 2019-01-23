package com.example.salazarlaura.login.services;

import com.example.salazarlaura.login.models.User;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface IServices {

    @GET("user/auth")
    Call<User> getUser(@Query("email") String user, @Query("password") String password);

    @GET("user")
    Call<User> getUser(@Header("Authorization") String token);
}
