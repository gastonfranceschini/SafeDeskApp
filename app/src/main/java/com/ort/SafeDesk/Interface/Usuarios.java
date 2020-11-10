package com.ort.SafeDesk.Interface;

import com.ort.SafeDesk.Model.ChangePassDTO;
import com.ort.SafeDesk.Model.Gerencias;
import com.ort.SafeDesk.Model.TurnoBody;
import com.ort.SafeDesk.Model.UsuarioDep;

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.PUT;

public interface Usuarios {

    @GET("api/usuarios/dependientes")
    Call<List<UsuarioDep>> getUsuariosDependientes();

    @GET("api/usuarios/gerencias")
    Call<List<Gerencias>> getGerencias();

    @PUT("api/usuarios/password")
    Call<ResponseBody> changePassword(@Body ChangePassDTO changePass);


}
