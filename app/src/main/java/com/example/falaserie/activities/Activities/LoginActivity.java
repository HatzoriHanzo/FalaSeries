package com.example.falaserie.activities.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Base64;
import android.widget.Button;
import android.widget.EditText;

import com.example.falaserie.R;
import com.example.falaserie.activities.bean.Usuario;
import com.example.falaserie.activities.bo.UsuarioBo;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import org.json.JSONException;
import org.json.JSONObject;

import java.net.URL;

import mobi.stos.httplib.HttpAsync;
import mobi.stos.httplib.inter.FutureCallback;

public class LoginActivity extends AppCompatActivity {
    private  Button button_entrar;
   private Usuario usuario;
   private UsuarioBo usuarioBo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        TextInputLayout edittext_login = findViewById(R.id.editText_Login);
        TextInputLayout edittext_password = findViewById(R.id.editTxt_Password);
        button_entrar = findViewById(R.id.btn_Login);

        btnLogin();
    }

    private void btnLogin(){
        button_entrar.setOnClickListener(v -> {
            try {
        String basic = Base64.encodeToString("DDpfFDZo!@7oQIhxDb%y7JQd3LUB%@IN:DbVHAbP7of2@2ZZL3GYsnWUnDJ5Feq1w".getBytes(),Base64.NO_WRAP);
                HttpAsync httpAsync = new HttpAsync(new URL(getString(R.string.base_url)+"login"));
                httpAsync.addHeader("Authorization","Basic " + basic);
                httpAsync.addParam("email","android@audax.mobi");
                httpAsync.addParam("senha","cDcGnvUAbpT@");
                httpAsync.setDebug(true);
                httpAsync.post(new FutureCallback() {
                    @Override
                    public void onBeforeExecute() {

                    }

                    @Override
                    public void onAfterExecute() {

                    }

                    @Override
                    public void onSuccess(int responseCode, Object object) {
                        System.out.println("Response code "+ responseCode);

                        if (responseCode == 200){


                            try {
                                JSONObject jsonObject = (JSONObject) object;
                                usuario = new Usuario();
                                usuario.setToken(jsonObject.getString("access_token"));
                                usuarioBo = new UsuarioBo(LoginActivity.this);
                                usuarioBo.clean();
                                usuarioBo.insert(usuario);
                                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                                startActivity(intent);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }

                    }

                    @Override
                    public void onFailure(Exception exception) {

                    }
                });


            } catch (Exception e) {
                e.printStackTrace();
            }

        });
    }



}