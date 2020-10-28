package com.ort.SafeDesk;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Switch;
import android.widget.TextView;

import com.ort.SafeDesk.Model.Token;
import com.ort.SafeDesk.utils.Global;
import com.ort.SafeDesk.utils.SettingPreferences;


public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private static final int OPERADOR = 1;
    private static final int SUPERVISOR = 2;
    private static final int GERENTE = 3;
    private static final int ADMINISTRADOR = 4;
    private static final int SEGURIDAD = 5;
    private TextView myJsonTxtView;

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

        autoDiagnostico = findViewById(R.id.cardView1);
        reservaJornada = findViewById(R.id.cardView2);
        misReservas = findViewById(R.id.cardView3);
        codigoQR = findViewById(R.id.cardView4);
        reportes = findViewById(R.id.cardView5);
        administracion = findViewById(R.id.cardView6);
        cerrarSesion = findViewById(R.id.cardView7);
        nombre = findViewById(R.id.txtnombre);
        email = findViewById(R.id.txtemail);
        nombre.setText(Global.token.getNombre());
        email.setText(Global.token.getEmail());
        switchReserva = findViewById(R.id.switchReserva);

        setEnableCardViews(Global.token.getIdTipoDeUsuario());

        //onClickListeners
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
        if (autoDiagnostico.equals(view)) {
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

    private void DesactivarBoton(CardView view)
    {
        view.setEnabled(false);
        view.setCardBackgroundColor(getResources().getColor(R.color.colorDisabled));
    }
    private void setEnableCardViews(int idTipoUsuario){
        switch(idTipoUsuario){
            case OPERADOR:
                DesactivarBoton(codigoQR);
                DesactivarBoton(administracion);
                DesactivarBoton(reportes);
                break;
            case SUPERVISOR:
            case GERENTE:
                DesactivarBoton(codigoQR);
                DesactivarBoton(administracion);
                break;
            case ADMINISTRADOR:
                break;
            case SEGURIDAD:
                DesactivarBoton(autoDiagnostico);
                DesactivarBoton(reservaJornada);
                DesactivarBoton(misReservas);
                DesactivarBoton(reportes);
                break;
            default:
                DesactivarBoton(autoDiagnostico);
                DesactivarBoton(reservaJornada);
                DesactivarBoton(misReservas);
                DesactivarBoton(codigoQR);
                DesactivarBoton(reportes);
                DesactivarBoton(administracion);
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
