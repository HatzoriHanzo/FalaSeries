package com.example.falaserie.activities.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.falaserie.R;
import com.example.falaserie.activities.Util.Util;
import com.example.falaserie.activities.bo.UsuarioBo;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.net.URL;

import mobi.stos.httplib.HttpAsync;
import mobi.stos.httplib.inter.FutureCallback;

public class CadastroActivity extends AppCompatActivity {
    private  String token;
    private Button btn_cadastrar, btn_cadastrarimagem, btn_cancelar;
    private EditText edittext_titulo,edittext_sinopse;
    private int SELECT_PICTURE = 200;
    private ImageView IVPreviewImage;
    private Uri selectedImageUri;
    private String img;
    UsuarioBo usuarioBo = new UsuarioBo(this);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro);

        btn_cadastrar = findViewById(R.id.btn_cadastrarseries);
        btn_cadastrarimagem = findViewById(R.id.btn_cadastrarimagem);
        btn_cancelar = findViewById(R.id.btn_cancelar);

        edittext_sinopse = findViewById(R.id.editTextTextsinopseserie);
        edittext_titulo = findViewById(R.id.editTextTexttitulodaserie);

        IVPreviewImage = findViewById(R.id.IVPreviewImage);
        btn_cancelar.setOnClickListener(v -> {
            finish();
        });
        btn_cadastrarimagem.setOnClickListener(v -> {
            imageChooser();
        });

        btn_cadastrar.setOnClickListener(v -> {
            if (edittext_titulo.getText().toString().equals("")||edittext_sinopse.getText().toString().equals("")||img.isEmpty()){
                Toast.makeText(this, "Há campos vazios, preencha os campos ou insira uma imagem compatível.", Toast.LENGTH_SHORT).show();
            }
                else{

                try {
                HttpAsync httpAsync = new HttpAsync(new URL(getString(R.string.base_url)+"series"));
                httpAsync.addHeader("Authorization","Bearer " + usuarioBo.list().get(0).getToken());
                httpAsync.addParam("nome",edittext_titulo.getText().toString());
                httpAsync.addParam("descricao",edittext_sinopse.getText().toString());
                httpAsync.addParam("capa", img);
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

                        if (responseCode == 201){
                            Toast.makeText(CadastroActivity.this, "Cadastro realizado com sucesso!!", Toast.LENGTH_SHORT).show();
                            Intent intent2 = new Intent(CadastroActivity.this,CadastroRealizadoActivity.class);
                            startActivity(intent2);


                        }
                        else{
                            Toast.makeText(CadastroActivity.this, "Não foi possível realizar o cadastro, tente novamente mais tarde.", Toast.LENGTH_SHORT).show();
                        }

                    }

                    @Override
                    public void onFailure(Exception exception) {

                    }
                });


            } catch (Exception e) {
                e.printStackTrace();
            }
        }});

    }

    private void imageChooser() {

        Intent i = new Intent();
        i.setType("image/*");
        i.setAction(Intent.ACTION_GET_CONTENT);


        startActivityForResult(Intent.createChooser(i, "Select Picture"), SELECT_PICTURE);
    }


    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK) {

            if (requestCode == SELECT_PICTURE) {
                selectedImageUri = data.getData();
                final InputStream imageStream;
                try {
                    imageStream = getContentResolver().openInputStream(selectedImageUri);

                final Bitmap selectedImage = BitmapFactory.decodeStream(imageStream);
                 img = Util.bitmapToBase64(selectedImage);
                    Log.e("image", "onActivityResult: "+img );
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }

                if (null != selectedImageUri) {
                    IVPreviewImage.setImageURI(selectedImageUri);
                }
            }
        }
    }
}