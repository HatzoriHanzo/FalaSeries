package com.example.falaserie.activities.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.ListView;

import com.example.falaserie.R;
import com.example.falaserie.activities.adapter.MainAdapterActivity;
import com.example.falaserie.activities.bean.Series;
import com.example.falaserie.activities.bo.SeriesBo;
import com.example.falaserie.activities.bo.UsuarioBo;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import mobi.stos.httplib.HttpAsync;
import mobi.stos.httplib.inter.FutureCallback;

public class MainActivity extends AppCompatActivity {
    private List<Series> series;
       private ListView mListView;
       UsuarioBo usuarioBo = new UsuarioBo(this);


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        UsuarioBo usuarioBo = new UsuarioBo(this);
        Log.e("token", "onCreate: "+ usuarioBo.list().get(0).getToken());
        mListView = findViewById(R.id.list_series);
        Button btn_Cadastrar = findViewById(R.id.btn_Cadastrar);
        btn_Cadastrar.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this,CadastroActivity.class);
            startActivity(intent);
        });

    }

    @Override
    protected void onResume() {
        super.onResume();
        listarSeries();
    }

    public void listarSeries(){ try {

        HttpAsync httpAsync = new HttpAsync(new URL(getString(R.string.base_url)+"series"));
        httpAsync.addHeader("Authorization","Bearer " + usuarioBo.list().get(0).getToken());
        httpAsync.setDebug(true);
        httpAsync.get(new FutureCallback() {
            @Override
            public void onBeforeExecute() {


            }

            @Override
            public void onAfterExecute() {
                MainAdapterActivity mainAdapterActivity = new MainAdapterActivity(MainActivity.this,new SeriesBo(MainActivity.this).list());
                mListView.setDivider(null);
                mListView.setAdapter(mainAdapterActivity);
                mListView.setOnItemClickListener((parent, view, position, id) -> {
                    Series serie = mainAdapterActivity.getItem(position);
                    Intent intent = new Intent(MainActivity.this,EditarDadosActivity.class);
                    intent.putExtra("SERIE",serie);
                    startActivity(intent);
                });
                }

            @Override
            public void onSuccess(int responseCode, Object object) {
                System.out.println("Response code "+ responseCode);

                if (responseCode == 200){
                    JSONObject jsonObject = (JSONObject) object;
                    series = new ArrayList<>();
                    series.clear();
                    try {
                        JSONArray jsonArray = jsonObject.getJSONArray("data");
                        for (int i = 0; i <jsonArray.length(); i++) {
                            Series serie = new Series();
                            serie.setId(jsonArray.getJSONObject(i).getInt("id"));
                            serie.setLabel(jsonArray.getJSONObject(i).getString("nome"));
                            serie.setDescricao(jsonArray.getJSONObject(i).getString("descricao"));
                            serie.setImagem(jsonArray.getJSONObject(i).getString("capa"));

                            series.add(serie);
                        }
                        SeriesBo seriesBo = new SeriesBo(MainActivity.this);
                        seriesBo.clean();
                        seriesBo.insert(series);
                        Log.e("TAG", "onSuccess: "+seriesBo.list().size() );
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
        }





    }

