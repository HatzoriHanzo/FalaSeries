package com.example.falaserie.activities.dao;

import android.content.Context;

import com.example.falaserie.activities.bean.Usuario;

import mobi.stos.podataka_lib.repository.AbstractRepository;

public class UsuarioDao extends AbstractRepository<Usuario> {
    public UsuarioDao(Context context) {

        super(context, Usuario.class);
    }
}
