package com.example.falaserie.activities.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.app.AlertDialog;
import android.content.DialogInterface;
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
import com.example.falaserie.activities.bean.Series;
import com.example.falaserie.activities.bo.UsuarioBo;
import com.squareup.picasso.Picasso;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.net.URL;

import mobi.stos.httplib.HttpAsync;
import mobi.stos.httplib.inter.FutureCallback;

public class EditarDadosActivity extends AppCompatActivity {
    private Series serie = new Series();
    private EditText editText_titulo,editText_sinopse;
    private ImageView imageView;
    private UsuarioBo usuarioBo;
    private final int SELECT_PICTURE = 200;
    private String img;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editar_dados);
        imageView = findViewById(R.id.imagem_adaptereditar);
        editText_titulo = findViewById(R.id.editar_dados_activity_edittext_titulo);
        editText_sinopse = findViewById(R.id.editar_dados_activity_edittext_sinopse);
        Button btn_editar = findViewById(R.id.editar_cadastro_activity_btn_editar);
        Button btn_excluir = findViewById(R.id.editar_dados_btn_deletar);
        Button btn_alterarImagem = findViewById(R.id.editar_dados_activity_btn_alterarimagem);
        usuarioBo = new UsuarioBo(this);
        serie = (Series) getIntent().getSerializableExtra("SERIE");
        editText_titulo.setText(serie.getLabel());
        editText_sinopse.setText(serie.getDescricao());
        Picasso.get().load(serie.getImagem()).into(imageView);

        btn_alterarImagem.setOnClickListener(v -> imageChooser());

        btn_excluir.setOnClickListener(v -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            new AlertDialog.Builder(this)
                    .setTitle("Quer mesmo deletar a série ?")
                    .setMessage("")
                    .setPositiveButton("Confirmar - a série é horrivel",new DialogInterface.OnClickListener(){
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    try {
                        HttpAsync httpAsync = new HttpAsync(new URL(getString(R.string.base_url)+"series/"+serie.getId()));
                        httpAsync.addHeader("Authorization","Bearer " + usuarioBo.list().get(0).getToken());
                        httpAsync.setDebug(true);
                        httpAsync.delete(new FutureCallback() {
                            @Override
                            public void onBeforeExecute() {


                            }

                            @Override
                            public void onAfterExecute() {
                                Intent intent = new Intent(EditarDadosActivity.this,MainActivity.class);
                                startActivity(intent);
                            }

                            @Override
                            public void onSuccess(int responseCode, Object object) {
                                System.out.println("Response code "+ responseCode);

                                if (responseCode == 204){
                                    Toast.makeText(EditarDadosActivity.this, "Série Deletada com Sucesso.", Toast.LENGTH_SHORT).show();
                                    Intent intent = new Intent(EditarDadosActivity.this,DadosDeletadosActivity.class);
                                    startActivity(intent);


                                }
                                else{
                                    Toast.makeText(EditarDadosActivity.this, "Não foi possível deletar a série...", Toast.LENGTH_SHORT).show();
                                }

                            }

                            @Override
                            public void onFailure(Exception exception) {

                            }
                        });


                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }).setNegativeButton("Cancelar,confundi com Friends", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                }
            })
            .setIcon(android.R.drawable.ic_dialog_alert)
                    .show();
            ;



        });

        btn_editar.setOnClickListener(v -> {
            if (editText_titulo.getText().toString().equals("")||editText_sinopse.getText().toString().equals("")||img == null){
                Toast.makeText(this, "Há campos vazios, preencha os campos ou insira uma imagem compatível.", Toast.LENGTH_SHORT).show();
            }
            else {
            try {
                HttpAsync httpAsync = new HttpAsync(new URL(getString(R.string.base_url)+"series/"+serie.getId()));
                httpAsync.addHeader("Authorization","Bearer " + usuarioBo.list().get(0).getToken());
                httpAsync.addParam("nome",editText_titulo.getText().toString());
                httpAsync.addParam("descricao",editText_sinopse.getText().toString());
                httpAsync.addParam("capa", img);

                httpAsync.setDebug(true);
                httpAsync.put(new FutureCallback() {
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
                            Toast.makeText(EditarDadosActivity.this, "Cadastro atualizado com sucesso!!", Toast.LENGTH_SHORT).show();
                            Intent intent2 = new Intent(EditarDadosActivity.this,MainActivity.class);
                            startActivity(intent2);


                        }
                        else{
                            Toast.makeText(EditarDadosActivity.this, "Não foi possível atualizar a série, tente novamente mais tarde.", Toast.LENGTH_SHORT).show();
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
                Uri selectedImageUri = data.getData();
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
                    imageView.setImageURI(selectedImageUri);

                }
            }
        }
    }
    }






