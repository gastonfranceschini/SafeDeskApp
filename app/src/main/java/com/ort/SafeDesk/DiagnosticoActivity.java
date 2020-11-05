package com.ort.SafeDesk;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.ort.SafeDesk.Interface.APIDiagnostico;
import com.ort.SafeDesk.Model.Diagnostico;
import com.ort.SafeDesk.utils.ApiUtils;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DiagnosticoActivity extends AppCompatActivity {

//    private TextView tempTxt;
//    private SeekBar seekBar;
//
//    private float temperatura;
    private Switch temperatura;
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

//        tempTxt = (TextView) findViewById(R.id.seekBarTxt);
//        seekBar = (SeekBar) findViewById(R.id.seekBar2);
//        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
//            @Override
//            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
//                tempTxt.setText("" + progress + "\u2103");
//                temperatura = progress;
//            }
//
//            @Override
//            public void onStartTrackingTouch(SeekBar seekBar) {
//
//            }
//
//            @Override
//            public void onStopTrackingTouch(SeekBar seekBar) {
//
//            }
//        });

        setSwitchsValue();
        confirmar = findViewById(R.id.buttonDiag);

        confirmar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveAutoDiagnostico();
            }
        });
    }

    private void saveAutoDiagnostico(){
        APIDiagnostico apiDiagnostico = (APIDiagnostico) ApiUtils.getAPI(APIDiagnostico.class);

        Diagnostico diagnostico = new Diagnostico(temperatura.isChecked(), perdidaGusto.isChecked(), contactoCercano.isChecked(), embarazada.isChecked(),
                cancer.isChecked(), diabetes.isChecked(), hepatitis.isChecked(), perdidaOlfato.isChecked(), dolorGarganta.isChecked(),
                dificultadRespiratoria.isChecked());

        Call<Diagnostico> call = apiDiagnostico.saveDiagnostico(diagnostico);
        call.enqueue(new Callback<Diagnostico>()  {
            @Override
            public void onResponse(Call<Diagnostico> call, Response<Diagnostico> response) {
                if(response.isSuccessful()) {
                    Toast.makeText(getApplicationContext(),"Guardado con exito",Toast. LENGTH_SHORT).show();
                    accessMainApp();
                }
                else
                {
                    Toast.makeText(getApplicationContext(),"Error al guardar",Toast. LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Diagnostico> call, Throwable t) {
                Toast. makeText(getApplicationContext(),t.getMessage(),Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void setSwitchsValue(){
        temperatura = (Switch) findViewById(R.id.switch00);
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

    private void accessMainApp() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}