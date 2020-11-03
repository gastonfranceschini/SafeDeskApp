package com.ort.SafeDesk.Interface;

import com.ort.SafeDesk.Model.Reporte;
import com.ort.SafeDesk.Model.ReporteDTO;
import com.ort.SafeDesk.Model.TurnoBody;

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Path;

public interface Reportes {

    @GET("api/reportes")
    Call<List<Reporte>> getReportes();

    @POST("api/reportes/dinamic/{idReporte}")
    Call<ResponseBody> getReporteDinamico(@Body ReporteDTO reporteDTO, @Path("idReporte") int idReporte);
    //Call<ResponseBody> getReporteDinamico(@Part List<String> campos, @Part List<String> valores, @Path("idReporte") int idReporte);
    //Call<ResponseBody> getReporteDinamico(@Body List<String> campos, List<String> valores, @Path("idReporte") int idReporte);
}
