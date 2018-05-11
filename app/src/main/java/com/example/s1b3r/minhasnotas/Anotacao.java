package com.example.s1b3r.minhasnotas;

/**
 * Created by S1B3R on 13/12/2017.
 */

public class Anotacao {

    private String dataCriacao;
    private String titulo;
    private String texto;
    private int id;

    public Anotacao(String dataCriacao, String titulo, String texto, int id){
        this.dataCriacao = dataCriacao;
        this.titulo = titulo;
        this.texto = texto;
        this.id = id;
    }

    public void setDate(String dataCriacao){
        this.dataCriacao = dataCriacao;
    }

    public void setTitulo(String titulo){
        this.titulo = titulo;
    }

    public void setTexto(String texto) {
        this.texto = texto;
    }

    public void setId(int id){
        this.id = id;
    }

    public String getDataCriacao(){
        return dataCriacao;
    }

    public String getTitulo() {
        return titulo;
    }

    public String getTexto() {
        return texto;
    }

    public int getId(){
        return id;
    }
}
