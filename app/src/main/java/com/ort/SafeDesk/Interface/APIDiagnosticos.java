package com.ort.SafeDesk.Interface;

import com.ort.SafeDesk.Model.Diagnostico;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface APIDiagnosticos {


    @POST("api/diagnosticos")
    Call<Boolean> saveDiagnostico(@Body Diagnostico diagnostico);

    @GET("api/diagnosticos/valido")
    Call<Boolean> getUserDiagnostico();

}
