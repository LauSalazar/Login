package com.example.salazarlaura.login.services;

import com.example.salazarlaura.login.models.User;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface IServices {

    @GET("user")
    Call<User> getUser(@Path("email") String user, @Path("password") String password);
}
