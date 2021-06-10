package com.example.falaserie.activities.bo;

import android.content.Context;

import com.example.falaserie.activities.bean.Series;
import com.example.falaserie.activities.dao.SeriesDao;

import mobi.stos.podataka_lib.service.AbstractService;
import mobi.stos.podataka_lib.interfaces.IOperations;


public class SeriesBo extends AbstractService<Series> {
    private SeriesDao seriesDao;

    public SeriesBo(Context context){
        super();
        this.seriesDao = new SeriesDao(context);
    }

    @Override
    protected IOperations<Series> getDao() { return seriesDao; }
}
