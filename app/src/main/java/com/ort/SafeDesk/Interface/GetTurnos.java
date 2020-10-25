package com.ort.SafeDesk.Interface;

import com.ort.SafeDesk.Model.Turnos;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface GetTurnos {

    @GET("api/turnos/misturnos/")
    Call<List<Turnos>> getTurnos();

    @GET("api/turnos/{turno}")
    Call<Turnos> getTurno(@Path("turno") int idTurno);

    @POST("api/turnos/{turno}")
    Call<Turnos> saveTurno(@Path("turno") Turnos turno);
}
