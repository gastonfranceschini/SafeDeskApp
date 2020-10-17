package com.ort.myapplication.Interface;

import com.ort.myapplication.Model.Diagnostico;
import com.ort.myapplication.Model.Token;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface APIDiagnostico {


    @POST("api/diagnosticos")
    Call<Diagnostico> saveDiagnostico(@Body Diagnostico diagnostico);

    @GET("api/diagnosticos/userDiag/{id}")
    Call<Boolean> getDiagnosticoUsuario(@Path("id") String id);
}
