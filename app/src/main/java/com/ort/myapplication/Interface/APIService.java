package com.ort.myapplication.Interface;

import com.ort.myapplication.Model.Token;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface APIService {

    @POST("/posts")
    @FormUrlEncoded
    Call<Token> savePost(@Field("title") String title,
                         @Field("body") String body,
                         @Field("userId") long userId);
}
