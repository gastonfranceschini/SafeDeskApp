package com.ort.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.Toast;

import com.ort.myapplication.Interface.APIDiagnostico;
import com.ort.myapplication.Interface.Login;
import com.ort.myapplication.Model.Diagnostico;
import com.ort.myapplication.Model.Token;
import com.ort.myapplication.Model.UserDTO;
import com.ort.myapplication.utils.Global;

import org.json.JSONObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class DiagnosticoActivity extends AppCompatActivity {

    //private <> temperatura;
    private Switch perdidaGusto;
    private Switch contactoCercano;
    private Switch embarazada;
    private Switch cancer;
    private Switch diabetes;
    private Switch hepatitis;
    private Switch perdidaOlfato;
    private Switch dolorGarganta;
    private Switch dificultadRespiratoria;

    private Button confirmar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_diagnostico2);
        setSwitchsValue();
        confirmar = findViewById(R.id.buttonDiag);

        confirmar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //saveAutoDiagnostico();
            }
        });
    }

    private void saveAutoDiagnostico(){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://10.0.2.2:3000/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        APIDiagnostico apiDiagnostico = retrofit.create(APIDiagnostico.class);

        Call<Token> call = apiDiagnostico.saveDiagnostico(new Diagnostico());
        call.enqueue(new Callback<Token>()  {
            @Override
            public void onResponse(Call<Token> call, Response<Token> response) {
                if(response.isSuccessful()) {
                    //response.code() == 200
                }
                else
                {
                    //response.code() == 422
                }
            }

            @Override
            public void onFailure(Call<Token> call, Throwable t) {
                Toast. makeText(getApplicationContext(),t.getMessage(),Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void setSwitchsValue(){
        contactoCercano = (Switch) findViewById(R.id.switch0);
        perdidaGusto = (Switch) findViewById(R.id.switch1);
        embarazada = (Switch) findViewById(R.id.switch2);
        cancer = (Switch) findViewById(R.id.switch3);
        diabetes = (Switch) findViewById(R.id.switch4);
        hepatitis = (Switch) findViewById(R.id.switch5);
        perdidaOlfato = (Switch) findViewById(R.id.switch6);
        dolorGarganta = (Switch) findViewById(R.id.switch7);
        dificultadRespiratoria = (Switch) findViewById(R.id.switch8);
    }
}