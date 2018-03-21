package com.example.binder3u.popituserapp;

import java.util.Date;

/**
 * Created by Cindy on 21/03/2018.
 */

public class Question {
    private int numero;
    private String texte;
    private int note;
    private Date temps;

    public Question() {
    }

    public Question(int numero, String texte) {
        this.numero = numero;
        this.texte = texte;
    }

    public int getNumero() {
        return numero;
    }

    public void setNumero(int numero) {
        this.numero = numero;
    }

    public String getTexte() {
        return texte;
    }

    public void setTexte(String texte) {
        this.texte = texte;
    }

    public int getNote() {
        return note;
    }

    public void setNote(int note) {
        this.note = note;
    }

    public Date getTemps() {
        return temps;
    }

    public void setTemps(Date temps) {
        this.temps = temps;
    }
}
