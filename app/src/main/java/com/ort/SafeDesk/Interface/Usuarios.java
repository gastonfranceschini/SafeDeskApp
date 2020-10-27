package com.ort.SafeDesk.Interface;

import com.ort.SafeDesk.Model.Gerencias;
import com.ort.SafeDesk.Model.UsuarioDep;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface Usuarios {

    @GET("api/usuarios/dependientes")
    Call<List<UsuarioDep>> getUsuariosDependientes();

    @GET("api/usuarios/gerencias")
    Call<List<Gerencias>> getGerencias();

}
