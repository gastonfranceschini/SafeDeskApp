package com.ort.SafeDesk.Interface;

import com.ort.SafeDesk.Model.Token;
import com.ort.SafeDesk.Model.UserDTO;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface APILogin {

    //@POST("api/auth/signin")
    //Call<List<Post>> getPost();

    @POST("api/auth/signin")
    Call<Token> login(@Body UserDTO userDTO);

    //@POST("api/auth/signin/test")
    //Call<Token> loginTest();
    //Call<Token> savePost(@Field("email") String email,
                         //@Field("password") String password);

}
