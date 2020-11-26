package com.ort.SafeDesk;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.ort.SafeDesk.Interface.APIDiagnosticos;
import com.ort.SafeDesk.Model.Diagnostico;
import com.ort.SafeDesk.Utils.ApiUtils;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DiagnosticoActivity extends AppCompatActivity {

    private TextView tempTxt;
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
        APIDiagnosticos apiDiagnostico = (APIDiagnosticos) ApiUtils.getAPI(APIDiagnosticos.class);

        Diagnostico diagnostico = new Diagnostico(temperatura.isChecked(), perdidaGusto.isChecked(), contactoCercano.isChecked(), embarazada.isChecked(),
                cancer.isChecked(), diabetes.isChecked(), hepatitis.isChecked(), perdidaOlfato.isChecked(), dolorGarganta.isChecked(),
                dificultadRespiratoria.isChecked());

        Call<Boolean> call = apiDiagnostico.saveDiagnostico(diagnostico);
        call.enqueue(new Callback<Boolean>()  {
            @Override
            public void onResponse(Call<Boolean> call, Response<Boolean> response) {
                if(response.isSuccessful()) {
                    //Toast.makeText(getApplicationContext(),"Diagnóstico guardado con éxito!",Toast. LENGTH_SHORT).show();
                    mostrarToast(1,"Diagnóstico guardado con éxito!");
                    accessMainApp();
                }
                else
                {
                    //Toast.makeText(getApplicationContext(),"Error al guardar el diagnóstico. \n Inténtalo nuevamente por favor.",Toast. LENGTH_SHORT).show();
                    mostrarToast(3,"Error al guardar el diagnóstico. \n Inténtalo nuevamente por favor.");
                }
            }

            @Override
            public void onFailure(Call<Boolean> call, Throwable t) {
                //Toast. makeText(getApplicationContext(),t.getMessage(),Toast.LENGTH_SHORT).show();
                mostrarToast(1, t.getMessage());
            }
        });
    }

    private void setSwitchsValue(){
        temperatura = (Switch) findViewById(R.id.switch15);
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

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    public void mostrarToast(int estado, String textoT){
        LayoutInflater inflater = getLayoutInflater();
        View layout;

        switch (estado){
            case 1:
                layout = inflater.inflate(R.layout.custom_toast, (ViewGroup)findViewById(R.id.container_toast));
                break;
            case 2:
                layout = inflater.inflate(R.layout.custom_toast2, (ViewGroup)findViewById(R.id.container_toast2));
                break;
            case 3:
                layout = inflater.inflate(R.layout.custom_toast3, (ViewGroup)findViewById(R.id.container_toast3));
                break;
            default:
                throw new IllegalStateException("Error de tipo: " + textoT);
        }
        TextView textView = layout.findViewById(R.id.toast_text);
        textView.setText(textoT);

        final Toast toast = new Toast(getApplicationContext());
        toast.setGravity(Gravity.CENTER_VERTICAL | Gravity.BOTTOM,0,200);
        toast.setDuration(Toast.LENGTH_LONG);
        toast.setView(layout);
        toast.show();
    }
}