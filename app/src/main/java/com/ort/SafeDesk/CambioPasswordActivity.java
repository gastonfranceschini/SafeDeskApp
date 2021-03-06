package com.ort.SafeDesk;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import com.ort.SafeDesk.LoginActivity;

import com.ort.SafeDesk.Interface.APIUsuarios;
import com.ort.SafeDesk.Model.ChangePassDTO;
import com.ort.SafeDesk.Utils.ApiUtils;
import com.ort.SafeDesk.Utils.Global;
import com.ort.SafeDesk.Utils.SettingPreferences;

import org.json.JSONObject;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
public class CambioPasswordActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText antigua;
    private EditText nueva;
    private EditText confirma;
    private Button cambiar;
    private SettingPreferences settingPreferences;
    //LoginActivity ct = new LoginActivity();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cambio_contrasena);
        settingPreferences = new SettingPreferences(getApplicationContext());
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


            if  (nueva.getText().toString().equals(""))
            {
                //Toast.makeText(getApplicationContext(), "La contrase??a nueva no puede estar vacia.", Toast.LENGTH_LONG).show();
                mostrarToast(2, "La contrase??a nueva no puede estar vacia");
                return;
            }

            if  (!nueva.getText().toString().equals(confirma.getText().toString()))
            {
                //Toast.makeText(getApplicationContext(), "La contrase??a nueva y la confirmacion no coinciden.", Toast.LENGTH_LONG).show();
                mostrarToast(2, "La contrase??a nueva y la confirmacion no coinciden");
                return;
            }

            changePass(new ChangePassDTO(Global.token.getUserId(),antigua.getText().toString(),nueva.getText().toString()));
        }
    }
    private void accessMainApp() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
    private void changePass(ChangePassDTO changePass) {

        APIUsuarios userService = (APIUsuarios) ApiUtils.getAPI(APIUsuarios.class);
        Call<ResponseBody> call = userService.changePassword(changePass);

        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    Global.token.setCambioPassObligatorio(0);
                    //Global.save();
                    settingPreferences.saveToken(Global.token);
                    //Toast.makeText(getApplicationContext(), "Password cambiada correctamente.", Toast.LENGTH_LONG).show();
                    mostrarToast(1, "Password cambiada correctamente");
                    accessMainApp();
                } else {
                    try {
                        JSONObject jObjError = new JSONObject(response.errorBody().string());
                        //Toast.makeText(getApplicationContext(), jObjError.getString("error"), Toast.LENGTH_LONG).show();
                        mostrarToast(3, jObjError.getString("error"));
                    } catch (Exception e) {
                        //Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_LONG).show();
                        mostrarToast(2, e.getMessage());
                    }
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void accessMainApp(Class activity) {
        Intent intent = new Intent(this, activity);
        startActivity(intent);
    }

    private void mostrarToast(int estado, String textoT){
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