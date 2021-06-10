package com.example.falaserie.activities.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;

import com.example.falaserie.R;
import com.example.falaserie.activities.bo.UsuarioBo;

public class CadastroRealizadoActivity extends AppCompatActivity {
    private String token;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        UsuarioBo usuarioBo = new UsuarioBo(this);
        Log.e("token", "onCreate: "+ usuarioBo.list().get(0).getToken());
        setContentView(R.layout.activity_cadastro_realizado);
        Button btn_telainicial = findViewById(R.id.dados_deletados_btn_telainicial);
        btn_telainicial.setOnClickListener(v -> {
            Intent intent = new Intent(CadastroRealizadoActivity.this,MainActivity.class);
            intent.putExtra("TOKEN",token);
            startActivity(intent);
        });
    }


}