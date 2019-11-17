package com.example.shopay.Retrofit;

import com.example.shopay.user;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Header;
import retrofit2.http.POST;

public interface NodeJs {


    @FormUrlEncoded
    @POST("adduserjs/adduser")
    Call<user> adduser(@Header("Authorization") String token,
                         @Field("stripetoken") String secret,
                       @Field("email") String email);

    @FormUrlEncoded
    @POST("removecardjs/removecard")
    Call<user> removecard(@Header("Authorization") String token,
                       @Field("custid") String secret);

    @FormUrlEncoded
    @POST("gettokenjs/gettoken")
    Call<user> gettoken(@Field("id") String id);

}