package com.ort.SafeDesk.Interface;

import com.ort.SafeDesk.Model.Reporte;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface Reportes {

    @GET("api/reportes")
    Call<List<Reporte>> getReportes();

}
