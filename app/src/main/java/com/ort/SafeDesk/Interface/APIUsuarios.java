package com.ort.SafeDesk.Interface;

import com.ort.SafeDesk.Model.ChangePassDTO;
import com.ort.SafeDesk.Model.Gerencia;
import com.ort.SafeDesk.Model.Usuario;

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.PUT;

public interface APIUsuarios {

    @GET("api/usuarios/dependientes")
    Call<List<Usuario>> getUsuariosDependientes();

    @GET("api/usuarios/gerencias")
    Call<List<Gerencia>> getGerencias();

    @PUT("api/usuarios/password")
    Call<ResponseBody> changePassword(@Body ChangePassDTO changePass);

}
