package com.ort.SafeDesk;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;

public class CambioContrasena extends AppCompatActivity implements View.OnClickListener {

    private EditText antigua;
    private EditText nueva;
    private EditText confirma;
    private Button cambiar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cambio_contrasena);

        antigua = findViewById(R.id.editpassant);
        nueva = findViewById(R.id.editpassnueva);
        confirma = findViewById(R.id.editconfpassnueva);
        cambiar = findViewById(R.id.btn_fincambio);

        cambiar.setOnClickListener(this);
    }

    public void hideKeyboard(View view) {
        InputMethodManager inputMethodManager=(InputMethodManager) getSystemService(Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(),0);
    }

    @Override
    public void onClick(View view) {
        if (cambiar.equals(view)){

        }
    }

    private void accessMainApp(Class activity) {
        Intent intent = new Intent(this, activity);
        startActivity(intent);
    }
}