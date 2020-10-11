package com.ort.myapplication.Interface;

import com.ort.myapplication.Model.Edificio;
import com.ort.myapplication.Model.Turnos;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface GetTurnos {

    @GET("api/turnos/misturnos/")
    Call<List<Turnos>> getTurnos();

}
