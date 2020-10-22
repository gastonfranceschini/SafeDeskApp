package com.ort.SafeDesk;

import android.os.Bundle;
import android.view.View;
import android.widget.Switch;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.preference.PreferenceFragmentCompat;

public class AdministracionActivity extends AppCompatActivity {

    private Switch enabledReserva;
    private CardView reservaTurno;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings_activity);

        enabledReserva = findViewById(R.id.switchReserva);
        reservaTurno = findViewById(R.id.cardView2);

    }

}