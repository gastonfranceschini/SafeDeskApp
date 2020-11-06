package com.ort.SafeDesk.Interface;

import com.ort.SafeDesk.Model.Diagnostico;

import retrofit2.Call;
import retrofit2.http.GET;

public interface GetDiagnosticoUser {

    @GET("api/diagnosticos/valido")
    Call<Boolean> getUserDiagnostico();
}
