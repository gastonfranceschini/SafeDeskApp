package com.ort.myapplication.Interface;

import com.ort.myapplication.Model.Edificio;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface GetEdificios {

    @GET("api/edificios/")
    Call<List<Edificio>> getEdificios();

}
