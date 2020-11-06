package com.ort.SafeDesk.utils;

import android.content.Context;
import android.content.res.Configuration;

import java.util.Locale;

public class Commons {

    public static void cambiarContextoEspanol(Context context) {
        Configuration configuration = context.getResources().getConfiguration();
        Locale spanish = new Locale("es", "AR");
        configuration.setLocale(spanish);
        configuration.setLayoutDirection(spanish);
        context.createConfigurationContext(configuration);
        context.getApplicationContext().getResources().updateConfiguration(configuration, null);
    }

}