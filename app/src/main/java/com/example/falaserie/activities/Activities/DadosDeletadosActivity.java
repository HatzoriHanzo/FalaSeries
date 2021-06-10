package com.example.falaserie.activities.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import com.example.falaserie.R;

public class DadosDeletadosActivity extends AppCompatActivity {
    private Button mBtn_MenuPrincipal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dados_deletados);
        mBtn_MenuPrincipal = findViewById(R.id.dados_deletados_btn_telainicial);
        mBtn_MenuPrincipal.setOnClickListener(v -> {
            Intent intent = new Intent(DadosDeletadosActivity.this,MainActivity.class);
            startActivity(intent);
        });
    }
}