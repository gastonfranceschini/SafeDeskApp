package com.ort.myapplication.Interface;

import com.ort.myapplication.Model.Edificio;

import java.sql.Date;
import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface GetEdificios {

    @GET("/api/turnos/edificios/fecha/{fecha1}")
    Call<List<Edificio>> getEdificios(@Path("fecha1") String fecha1);

}
