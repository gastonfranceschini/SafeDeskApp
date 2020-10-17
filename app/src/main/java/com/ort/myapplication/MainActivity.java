package com.ort.myapplication;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.google.gson.JsonObject;
import com.ort.myapplication.Interface.APIDiagnostico;
import com.ort.myapplication.Interface.GetEdificios;
import com.ort.myapplication.Interface.Login;
import com.ort.myapplication.Model.Edificio;
import com.ort.myapplication.Model.Token;
import com.ort.myapplication.utils.ApiUtils;
import com.ort.myapplication.utils.Global;
import com.ort.myapplication.utils.RetrofitClient;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import android.app.AlertDialog;
import android.widget.Toast;

import org.json.JSONObject;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView nombre;
    private TextView email;

    private CardView autoDiagnostico;
    private CardView reservaJornada;
    private CardView misReservas;
    private CardView codigoQR;
    private CardView reportes;
    private CardView administracion;
    private CardView cerrarSesion;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //setEnableCardViews(Global.token.getIdTipoDeUsuario());
        setEnableCardViews(1);
        isAutoDiagSubmited(Global.token.getUserId());

        //Textos / nombre-email
        nombre = findViewById(R.id.txtnombre);
        nombre.setText(Global.token.getNombre());
        email = findViewById(R.id.txtemail);
        email.setText(Global.token.getEmail());
        //CardViews
        autoDiagnostico = findViewById(R.id.cardView1);
        reservaJornada = findViewById(R.id.cardView2);
        misReservas = findViewById(R.id.cardView3);
        codigoQR = findViewById(R.id.cardView4);
        reportes = findViewById(R.id.cardView5);
        administracion = findViewById(R.id.cardView6);
        cerrarSesion = findViewById(R.id.cardView7);
        //onClickListeners
        autoDiagnostico.setOnClickListener(this);
        reservaJornada.setOnClickListener(this);
        misReservas.setOnClickListener(this);
        codigoQR.setOnClickListener(this);
        cerrarSesion.setOnClickListener(this);
    }


    private void isAutoDiagSubmited(String id){
        APIDiagnostico apiDiagnostico = (APIDiagnostico)ApiUtils.getAPI(APIDiagnostico.class);

        Call<Boolean> call = apiDiagnostico.getDiagnosticoUsuario(id);
        call.enqueue(new Callback<Boolean>() {
            @Override
            public void onResponse(Call<Boolean> call, Response<Boolean> response) {
                if(response.body().booleanValue()){
                    CardView resJor = (CardView) findViewById(R.id.cardView2);
                    resJor.setEnabled(false);
                    resJor.setCardBackgroundColor(getResources().getColor(R.color.colorDisabled));
                }else{
                    CardView resJor = (CardView) findViewById(R.id.cardView2);
                    resJor.setEnabled(true);
                    resJor.setCardBackgroundColor(null);
                }
            }

            @Override
            public void onFailure(Call<Boolean> call, Throwable t) {

            }
        });
    }

    @Override
    public void onClick(View view) {
        if (autoDiagnostico.equals(view)) {
            accessMainApp(DiagnosticoActivity.class);
        }else if(reservaJornada.equals(view)){
            accessMainApp(ReservaTurno.class);
        }else if(misReservas.equals(view)){
            accessMainApp(MisReservasActivity.class);
        }else if(codigoQR.equals(view)){
            accessMainApp(GeneracionQRActivity.class);
        }else if(cerrarSesion.equals(view)){
            accessMainApp(LoginActivity.class);
            finish();
        }
    }

    private void setEnableCardViews(int idTipoUsuario){
        CardView resJor = (CardView) findViewById(R.id.cardView2);
        CardView reser = (CardView) findViewById(R.id.cardView3);
        CardView codigoQR = (CardView) findViewById(R.id.cardView4);
        CardView rep = (CardView) findViewById(R.id.cardView5);
        CardView admin = (CardView) findViewById(R.id.cardView6);
        switch(idTipoUsuario){
            case 1:
                rep.setEnabled(false);
                rep.setCardBackgroundColor(getResources().getColor(R.color.colorDisabled));
                admin.setEnabled(false);
                admin.setCardBackgroundColor(getResources().getColor(R.color.colorDisabled));
                break;

            case 2:

            case 3:

            case 4:
                codigoQR.setEnabled(false);
                codigoQR.setCardBackgroundColor(getResources().getColor(R.color.colorDisabled));
                break;

            case 5:
                rep.setEnabled(false);
                rep.setCardBackgroundColor(getResources().getColor(R.color.colorDisabled));
                admin.setEnabled(false);
                admin.setCardBackgroundColor(getResources().getColor(R.color.colorDisabled));
                resJor.setEnabled(false);
                resJor.setCardBackgroundColor(getResources().getColor(R.color.colorDisabled));
                reser.setEnabled(false);
                reser.setCardBackgroundColor(getResources().getColor(R.color.colorDisabled));
                break;
            default:
                break;
        }
    }

    private boolean isAutoDiagSubmited(){
        //aca se llama al back y se consulta por el autodiagnostico del usuario
        return true;
    }

    private void accessMainApp(Class activity) {
        Intent intent = new Intent(this, activity);
        startActivity(intent);
    }

    @Override
    public void onBackPressed() {
    }

}
