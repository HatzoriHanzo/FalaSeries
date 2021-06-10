package com.example.falaserie.activities.bean;

import java.io.Serializable;

import mobi.stos.podataka_lib.annotations.Column;
import mobi.stos.podataka_lib.annotations.Entity;

@Entity
public class Usuario implements Serializable {
    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    @Column
        private String token;

}
