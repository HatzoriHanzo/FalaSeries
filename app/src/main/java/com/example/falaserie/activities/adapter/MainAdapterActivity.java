package com.example.falaserie.activities.adapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.falaserie.R;
import com.example.falaserie.activities.bean.Series;

import java.util.List;

import com.squareup.picasso.Picasso;

public class MainAdapterActivity extends ArrayAdapter<Series> {



            private Context context;

            public MainAdapterActivity(Context context, List<Series> series) {
                super(context,0 ,series);
                this.context = context;
            }



            @NonNull
            @Override
            public View getView(int i, @Nullable View convertView, @NonNull ViewGroup parent) {
                View root = LayoutInflater.from(getContext()).inflate(R.layout.activity_main_adapter, null);
                Series series = getItem(i);

                TextView titulo = root.findViewById(R.id.titulo_adapter);
                TextView sinopse = root.findViewById(R.id.sinopse_adapter);
                ImageView imagem = root.findViewById(R.id.imagem_adaptereditar);
                titulo.setText(series.getLabel());
                sinopse.setText(series.getDescricao());
                Picasso.get().load(series.getImagem()).into(imagem);





                return root;
            }



        }

