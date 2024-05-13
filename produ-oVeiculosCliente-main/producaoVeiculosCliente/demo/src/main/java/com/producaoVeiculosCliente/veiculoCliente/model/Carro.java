package com.producaoVeiculosCliente.veiculoCliente.model;

import lombok.Getter;

@Getter
public class Carro {
    private final int id;
    private final String modelo;
    private final String cor;

    public Carro(int id, String modelo, String cor) {
        this.id = id;
        this.modelo = modelo;
        this.cor = cor;
    }

    @Override
    public String toString() {
        return "Carro(" +
                "id=" + id +
                ", modelo='" + modelo + '\'' +
                ", cor='" + cor + '\'' +
                ')';
    }
}
