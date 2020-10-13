package com.ort.myapplication;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.google.gson.JsonObject;
import com.ort.myapplication.Interface.GetEdificios;
import com.ort.myapplication.Interface.Login;
import com.ort.myapplication.Model.Edificio;
import com.ort.myapplication.Model.Token;
import com.ort.myapplication.utils.ApiUtils;
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

    private TextView myJsonTxtView;

    private CardView autoDiagnostico;
    private CardView reservaJornada;
    private CardView misReservas;
    private CardView codigoQR;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        CardView rep = (CardView) findViewById(R.id.cardView5);
//        rep.setEnabled(false);
//        rep.setCardBackgroundColor(979797);


        autoDiagnostico = findViewById(R.id.cardView1);
        reservaJornada = findViewById(R.id.cardView2);
        misReservas = findViewById(R.id.cardView3);
        codigoQR = findViewById(R.id.cardView4);

        //onClickListeners
        autoDiagnostico.setOnClickListener(this);
        reservaJornada.setOnClickListener(this);
        misReservas.setOnClickListener(this);
        codigoQR.setOnClickListener(this);
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
