package com.example.reproductormusica.modelos;

public class Cancion {
    private int refecancion;
    private String nomcancion;

    public Cancion(int refecancion, String nomcancion) {
        this.refecancion = refecancion;
        this.nomcancion = nomcancion;
    }

    public int getRefecancion() {
        return refecancion;
    }

    public void setRefecancion(int refecancion) {
        this.refecancion = refecancion;
    }

    public String getNomcancion() {
        return nomcancion;
    }

    public void setNomcancion(String nomcancion) {
        this.nomcancion = nomcancion;
    }

}
