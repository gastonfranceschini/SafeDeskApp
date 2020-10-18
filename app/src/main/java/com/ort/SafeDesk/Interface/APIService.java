package com.ort.SafeDesk.Interface;

import com.ort.SafeDesk.Model.Token;

import retrofit2.Call;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

import retrofit2.http.Field;

public interface APIService {

    @POST("/posts")
    @FormUrlEncoded
    Call<Token> savePost(@Field("title") String title,
                         @Field("body") String body,
                         @Field("userId") long userId);
}
