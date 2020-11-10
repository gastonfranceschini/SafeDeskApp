package com.ort.SafeDesk;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Switch;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.ort.SafeDesk.Interface.Reportes;
import com.ort.SafeDesk.Interface.Usuarios;
import com.ort.SafeDesk.Model.Configuracion;
import com.ort.SafeDesk.Model.UsuarioDep;
import com.ort.SafeDesk.utils.ApiUtils;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AdministracionActivity extends AppCompatActivity implements View.OnClickListener {

    private Switch enabledReserva;
    private Switch enabledAutodiag;
    private Button guardar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_administracion);

        enabledReserva = findViewById(R.id.switchReserva);
        enabledAutodiag = findViewById(R.id.switchAutodiag);
        guardar = findViewById(R.id.btn_config);
        guardar.setOnClickListener(this);
        getConfiguraciones();

    }
    private void getConfiguraciones()
    {
        getConfiguracion(ApiUtils.CONFIG_TURNOS);
        getConfiguracion(ApiUtils.CONFIG_DIAGNOSTICOS);
    }
    private void guardarConfiguraciones()
    {
        setConfiguracion(ApiUtils.CONFIG_TURNOS, enabledReserva.isChecked());
        setConfiguracion(ApiUtils.CONFIG_DIAGNOSTICOS,enabledAutodiag.isChecked());

    }

    private void responseConfig(String nombre, Configuracion config)
    {
        boolean checked = false;
        if (config.getValor() == 1)
            checked = true;
        switch (nombre)
        {
            case ApiUtils.CONFIG_TURNOS:
                enabledReserva.setChecked(checked);
                break;
            case ApiUtils.CONFIG_DIAGNOSTICOS:
                enabledAutodiag.setChecked(checked);
                break;
        }
    }
    private void getConfiguracion(final String Nombre) {
        Reportes getConfig = (Reportes) ApiUtils.getAPI(Reportes.class);
        Call<Configuracion> call = getConfig.getConfig(Nombre);

        call.enqueue(new Callback<Configuracion>() {
            @Override
            public void onResponse(Call<Configuracion> call, Response<Configuracion> response) {
                if (response.isSuccessful()) {
                    Configuracion config = response.body();
                    responseConfig(Nombre, config);
                } else {
                    try {
                        JSONObject jObjError = new JSONObject(response.errorBody().string());
                        Toast.makeText(getApplicationContext(), jObjError.getString("error"), Toast.LENGTH_LONG).show();
                    } catch (Exception e) {
                        Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_LONG).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<Configuracion> call, Throwable t) {
                Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
    private void accessMainApp() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
    private void setConfiguracion(String Nombre,boolean Valor) {
        String Activado = "0";

        if (Valor)
            Activado = "1";

        Reportes setConfig = (Reportes) ApiUtils.getAPI(Reportes.class);
        Call<ResponseBody> call = setConfig.setConfig(Nombre,Activado);

        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    //Configuracion config = response.body();
                    //responseConfig(Nombre, config);
                    Toast.makeText(getApplicationContext(), "Configuracion Guardada", Toast.LENGTH_LONG).show();
                    accessMainApp();
                } else {
                    try {
                        JSONObject jObjError = new JSONObject(response.errorBody().string());
                        Toast.makeText(getApplicationContext(), jObjError.getString("error"), Toast.LENGTH_LONG).show();
                    } catch (Exception e) {
                        Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_LONG).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void onClick(View view) {
        if(guardar.equals(view)){
            guardarConfiguraciones();
        }
    }

}