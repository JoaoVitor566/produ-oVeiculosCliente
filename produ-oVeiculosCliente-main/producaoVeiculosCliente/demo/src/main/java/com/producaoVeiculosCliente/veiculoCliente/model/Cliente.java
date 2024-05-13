package com.producaoVeiculosCliente.veiculoCliente.model;

import lombok.Getter;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Cliente implements Runnable {
    @Getter
    private final int id;
    @Getter
    private final String nome;
    private final Random random = new Random();

    public Cliente(int id, String nome) {
        this.id = id;
        this.nome = nome;
    }

    @Override
    public void run() {
        while (true) {
            try {
                Thread.sleep(random.nextInt(5000) + 1000);
                // Randomly choose a store
                Loja loja = Loja.getRandomLoja();
                if (loja != null) {
                    loja.comprarCarro(this);
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                break;
            }
        }
    }
}
