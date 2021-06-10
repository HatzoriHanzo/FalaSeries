package com.example.falaserie.activities.bo;

import android.content.Context;

import com.example.falaserie.activities.bean.Usuario;
import com.example.falaserie.activities.dao.UsuarioDao;

import mobi.stos.podataka_lib.interfaces.IOperations;
import mobi.stos.podataka_lib.service.AbstractService;

public class UsuarioBo extends AbstractService<Usuario> {
    private UsuarioDao usuarioDao;

    public UsuarioBo(Context context) {
        super();
        this.usuarioDao = new UsuarioDao(context);

    }

    @Override
    protected IOperations<Usuario> getDao() {
        return usuarioDao;
    }
}
