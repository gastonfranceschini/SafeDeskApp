package com.ort.myapplication.Interface;

import com.ort.myapplication.Model.Diagnostico;
import com.ort.myapplication.Model.Token;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface APIDiagnostico {

    @POST("api/diagnostico/save")
    Call<Token> saveDiagnostico(@Body Diagnostico diagnostico);

}
