package com.ort.SafeDesk.Interface;

import com.ort.SafeDesk.Model.UsuarioDep;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface UsuariosDependiente {

    @GET("api/usuarios/dependientes")
    Call<List<UsuarioDep>> getUsuarios();

}
