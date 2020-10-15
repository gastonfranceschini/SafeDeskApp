package com.ort.myapplication.Interface;

import com.ort.myapplication.Model.Edificio;
import com.ort.myapplication.Model.Turnos;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface GetTurnos {

    @GET("api/turnos/misturnos/")
    Call<List<Turnos>> getTurnos();

    @POST("api/turnos/{turno}")
    Call<Turnos> saveTurno(@Path("turno") Turnos turno);
}
