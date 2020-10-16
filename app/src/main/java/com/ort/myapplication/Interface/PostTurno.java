package com.ort.myapplication.Interface;

import com.ort.myapplication.Model.Diagnostico;
import com.ort.myapplication.Model.TurnoBody;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface PostTurno {

    @POST("api/turnos")
    Call<TurnoBody> saveTurno(@Body TurnoBody turno);

}
