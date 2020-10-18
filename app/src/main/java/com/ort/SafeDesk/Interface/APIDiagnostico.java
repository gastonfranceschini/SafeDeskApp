package com.ort.SafeDesk.Interface;

import com.ort.SafeDesk.Model.Diagnostico;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface APIDiagnostico {


    @POST("api/diagnosticos")
    Call<Diagnostico> saveDiagnostico(@Body Diagnostico diagnostico);

}
