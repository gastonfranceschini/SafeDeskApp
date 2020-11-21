package com.ort.SafeDesk;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.ort.SafeDesk.Utils.Global;

public class EditarPerfilActivity extends AppCompatActivity implements View.OnClickListener {

    private Button confirmar;
    private Button cambiar;
    private TextView nombre;
    //private TextView apellido;
    private TextView dni;
    private TextView email;
    private TextView gerencia;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editar_perfil);

        confirmar = findViewById(R.id.btn_finperfil);
        cambiar = findViewById(R.id.btn_cambiopass);
        nombre = findViewById(R.id.nomperfil);
        //apellido = findViewById(R.id.apellidoperfil);
        dni = findViewById(R.id.dniperfil);
        email = findViewById(R.id.emailperfil);
        gerencia = findViewById(R.id.gerenperfil);

        if (Global.token.getNombre() != null)
            nombre.setText(Global.token.getNombre());

        if (Global.token.getEmail() != null)
            email.setText(Global.token.getEmail());

        dni.setText(Global.token.getUserId());
        gerencia.setText(Global.token.getGerencia());
        confirmar.setOnClickListener(this);
        cambiar.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if (confirmar.equals(view)){
            accessMainApp(MainActivity.class);
        }else if (cambiar.equals(view)){
            accessMainApp(CambioPasswordActivity.class);
        }
    }

    private void accessMainApp(Class activity) {
        Intent intent = new Intent(this, activity);
        startActivity(intent);
    }

}