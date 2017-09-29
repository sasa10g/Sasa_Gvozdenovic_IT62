package com.example.sasa10.mljac.klasa;

import java.io.Serializable;


public class Obrok implements Serializable { //Serializable omogucava da se prebacuje iz jednog aktivitija u drugi

    private String naziv;
    private String recept;
    private int id;



    public Obrok(){

    }


    public Obrok(int id,String naziv,String recept){

        this.id=id;
        this.naziv=naziv;
        this.recept=recept;

    }
    public Obrok(String naziv,String recept){


        this.naziv=naziv;
        this.recept=recept;

    }


    public String getNaziv() {
        return naziv;
    }

    public void setNaziv(String naziv) {
        this.naziv = naziv;
    }

    public String getRecept() {
        return recept;
    }

    public void setRecept(String recept) {
        this.recept = recept;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
