package com.ort.myapplication.Interface;

import com.ort.myapplication.Model.Post;
import com.ort.myapplication.Model.Token;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface Login {

    //@POST("api/auth/signin")
    //Call<List<Post>> getPost();

    @POST("api/auth/signin")
    Call<Token> login(@Body String body);

    //Call<Token> savePost(@Field("email") String email,
                         //@Field("password") String password);



}
