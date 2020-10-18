package com.ort.SafeDesk.Interface;

import com.ort.SafeDesk.Model.Hora;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface GetHora {
    @GET("/api/turnos/edificio/{idEdificio}/fecha/{fecha}/HorariosDeEntrada")
    Call<List<Hora>> getHoras(@Path("idEdificio") long idEdificio, @Path("fecha") String fecha);

}
