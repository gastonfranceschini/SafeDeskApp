package com.ort.SafeDesk;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.text.Layout;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;

import com.ort.SafeDesk.Interface.GetDiagnosticoUser;
import com.ort.SafeDesk.Interface.PostTurno;
import com.ort.SafeDesk.Model.Diagnostico;
import com.ort.SafeDesk.Model.Token;
import com.ort.SafeDesk.Model.TurnoBody;
import com.ort.SafeDesk.utils.ApiUtils;
import com.ort.SafeDesk.utils.Global;
import com.ort.SafeDesk.utils.SettingPreferences;

import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private static final int OPERADOR = 1;
    private static final int SUPERVISOR = 2;
    private static final int GERENTE = 3;
    private static final int ADMINISTRADOR = 4;
    private static final int SEGURIDAD = 5;
    private TextView myJsonTxtView;
    private boolean resp;

    private LinearLayout perfil;
    private CardView autoDiagnostico;
    private CardView reservaJornada;
    private CardView misReservas;
    private CardView codigoQR;
    private CardView reportes;
    private CardView administracion;
    private CardView cerrarSesion;
    private TextView nombre;
    private TextView email;
    private Switch switchReserva;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        perfil = findViewById(R.id.layoutperfil);
        autoDiagnostico = findViewById(R.id.cardView1);
        reservaJornada = findViewById(R.id.cardView2);
        misReservas = findViewById(R.id.cardView3);
        codigoQR = findViewById(R.id.cardView4);
        reportes = findViewById(R.id.cardView5);
        administracion = findViewById(R.id.cardView6);
        cerrarSesion = findViewById(R.id.cardView7);
        nombre = findViewById(R.id.txtnombre);
        email = findViewById(R.id.txtemail);

        if (Global.token.getNombre() != null)
            nombre.setText(Global.token.getNombre());

        if (Global.token.getEmail() != null)
            email.setText(Global.token.getEmail());

        //if(!isAutoDiagActive()){ OcultarBoton(autoDiagnostico); }

        switchReserva = findViewById(R.id.switchReserva);

        setEnableCardViews(Global.token.getIdTipoDeUsuario());

        //onClickListeners
        perfil.setOnClickListener(this);
        autoDiagnostico.setOnClickListener(this);
        reservaJornada.setOnClickListener(this);
        misReservas.setOnClickListener(this);
        codigoQR.setOnClickListener(this);
        administracion.setOnClickListener(this);
        reportes.setOnClickListener(this);
        cerrarSesion.setOnClickListener(this);
    }


    @Override
    public void onClick(View view) {
        if (perfil.equals(view)){
            accessMainApp(EditarPerfil.class);
        }else if (autoDiagnostico.equals(view)) {
            accessMainApp(DiagnosticoActivity.class);
        }else if(reservaJornada.equals(view)){
            accessMainApp(ReservaTurno.class);
        }else if(misReservas.equals(view)){
            accessMainApp(MisReservasActivity.class);
        }else if(codigoQR.equals(view)) {
            accessMainApp(GeneracionQRActivity.class);
        }else if(administracion.equals(view)){
            accessMainApp(AdministracionActivity.class);
        }else if(reportes.equals(view)){
            accessMainApp(ReportesActivity.class);
        }else if(cerrarSesion.equals(view)){
            accessMainApp(LoginActivity.class);
            SettingPreferences settingPreferences = new SettingPreferences(getApplicationContext());
            settingPreferences.saveToken(new Token());
            finish();
        }
    }

    private boolean isAutoDiagActive(){
        GetDiagnosticoUser userDiag = (GetDiagnosticoUser) ApiUtils.getAPI(GetDiagnosticoUser.class);

        Call<Boolean> call = userDiag.getUserDiagnostico();
        call.enqueue(new Callback<Boolean>() {
            @Override
            public void onResponse(Call<Boolean> call, Response<Boolean> response) {
                if(response.isSuccessful()){
                    if(response.body()){
                        resp = true;
                    }else{
                        resp = false;
                    }
                }
            }

            @Override
            public void onFailure(Call<Boolean> call, Throwable t) {

            }
        });
        return resp;
    }

    private void OcultarBoton(CardView view)
    {
        view.setVisibility(View.GONE);
    }

    private void setEnableCardViews(int idTipoUsuario){
        switch(idTipoUsuario){
            case OPERADOR:
                OcultarBoton(codigoQR);
                OcultarBoton(administracion);
                OcultarBoton(reportes);
                break;
            case SUPERVISOR:
            case GERENTE:
                OcultarBoton(codigoQR);
                OcultarBoton(administracion);
                break;
            case ADMINISTRADOR:
                break;
            case SEGURIDAD:
                OcultarBoton(autoDiagnostico);
                OcultarBoton(reservaJornada);
                OcultarBoton(misReservas);
                OcultarBoton(reportes);
                break;
            default:
                OcultarBoton(autoDiagnostico);
                OcultarBoton(reservaJornada);
                OcultarBoton(misReservas);
                OcultarBoton(codigoQR);
                OcultarBoton(reportes);
                OcultarBoton(administracion);
                break;
        }
    }

    private void accessMainApp(Class activity) {
        Intent intent = new Intent(this, activity);
        startActivity(intent);
    }

    @Override
    public void onBackPressed() {
    }

}
