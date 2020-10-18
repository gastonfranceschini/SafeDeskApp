package com.ort.SafeDesk.Interface;

import com.ort.SafeDesk.Model.TurnoBody;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface PostTurno {

    @POST("api/turnos")
    Call<TurnoBody> saveTurno(@Body TurnoBody turno);

}
