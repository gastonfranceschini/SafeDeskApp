package com.ort.myapplication.Interface;

import com.ort.myapplication.Model.Turnos;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface GetTurnosHistoricos {

    @GET("api/turnos/misturnoshistorico/")
    Call<List<Turnos>> getTurnos();

}
