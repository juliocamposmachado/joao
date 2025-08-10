package com.example.lavarapido;

public class Servico {
    private int id;
    private String nome;
    private double preco;

    public Servico() {
    }

    public Servico(String nome, double preco) {
        this.nome = nome;
        this.preco = preco;
    }

    public Servico(int id, String nome, double preco) {
        this.id = id;
        this.nome = nome;
        this.preco = preco;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public double getPreco() {
        return preco;
    }

    public void setPreco(double preco) {
        this.preco = preco;
    }

    @Override
    public String toString() {
        return nome + " - R$ " + String.format("%.2f", preco);
    }
}
