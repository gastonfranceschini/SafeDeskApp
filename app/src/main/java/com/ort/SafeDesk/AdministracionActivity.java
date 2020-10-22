package com.ort.SafeDesk;

import android.os.Bundle;
import android.widget.Switch;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

public class AdministracionActivity extends AppCompatActivity {

    private Switch enabledReserva;
    private CardView reservaTurno;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        enabledReserva = findViewById(R.id.switchReserva);
        reservaTurno = findViewById(R.id.cardView2);

    }

}