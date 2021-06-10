package com.example.falaserie.activities.dao;

import android.content.Context;

import com.example.falaserie.activities.bean.Series;

import mobi.stos.podataka_lib.repository.AbstractRepository;

public class SeriesDao extends AbstractRepository<Series> {

    public SeriesDao (Context context){
        super(context, Series.class);
    }
}
