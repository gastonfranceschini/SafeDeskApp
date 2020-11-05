package com.ort.SafeDesk.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.preference.PreferenceManager;

import com.google.gson.Gson;
import com.ort.SafeDesk.Model.Token;

import java.util.Locale;

import javax.sql.CommonDataSource;


public class SettingPreferences {

    private static final String TOKEN_KEY = "TOKEN_KEY";
    private final Context context;

    public SettingPreferences(Context context) {

        this.context = context;
        // AR Pongo las fechas en español
        Commons.cambiarContextoEspanol(this.context);
    }

    public void saveToken(Token token) {
        SharedPreferences.Editor edit = getSharedPreferences().edit();
        String json = new Gson().toJson(token);
        edit.putString(TOKEN_KEY,json);
        edit.commit();
        Global.token = token; //seteo el global
    }

    public Token getTokenSaved() {
        String json = getSharedPreferences().getString(TOKEN_KEY,null);
        if(json == null){
            return new Token();
        }
        Token token = new Gson().fromJson(json, Token.class);
        Global.token = token; //seteo el global
        return token;
    }

    private SharedPreferences getSharedPreferences(){
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        return preferences;
    }

}