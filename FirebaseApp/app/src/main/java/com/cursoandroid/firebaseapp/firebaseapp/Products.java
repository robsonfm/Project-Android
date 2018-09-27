package com.cursoandroid.firebaseapp.firebaseapp;

class Products {

    private String descricao;
    private String cor;
    private String fabricante;


    public Products(String descricao, String cor, String fabricante) {
        this.descricao = descricao;
        this.cor = cor;
        this.fabricante = fabricante;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getCor() {
        return cor;
    }

    public void setCor(String cor) {
        this.cor = cor;
    }

    public String getFabricante() {
        return fabricante;
    }

    public void setFabricante(String fabricante) {
        this.fabricante = fabricante;
    }
}