package com.ort.SafeDesk.Interface;

import com.ort.SafeDesk.Model.Edificio;
import com.ort.SafeDesk.Model.Hora;
import com.ort.SafeDesk.Model.Piso;
import com.ort.SafeDesk.Model.TurnoDTO;
import com.ort.SafeDesk.Model.Turno;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface APITurnos {

    @GET("api/turnos/misturnos/")
    Call<List<Turno>> getTurnos();

    @GET("api/turnos/{turno}")
    Call<Turno> getTurno(@Path("turno") int idTurno);

    @POST("api/turnos/{turno}")
    Call<Turno> saveTurno(@Path("turno") Turno turno);

    @GET("/api/turnos/edificios/fecha/{fecha1}")
    Call<List<Edificio>> getEdificios(@Path("fecha1") String fecha1);

    @GET("/api/turnos/edificio/{idEdificio}/fecha/{fecha}/HorariosDeEntrada")
    Call<List<Hora>> getHoras(@Path("idEdificio") long idEdificio, @Path("fecha") String fecha);

    @GET("/api/turnos/pisos/fecha/{fecha}/edificio/{idEdificio}")
    Call<List<Piso>> getPisos(@Path("fecha") String fecha, @Path("idEdificio") long idEdificio);

    @GET("api/turnos/misturnoshistorico/")
    Call<List<Turno>> getTurnosHistoricos();

    @POST("api/turnos")
    Call<TurnoDTO> saveTurno(@Body TurnoDTO turno);

}

