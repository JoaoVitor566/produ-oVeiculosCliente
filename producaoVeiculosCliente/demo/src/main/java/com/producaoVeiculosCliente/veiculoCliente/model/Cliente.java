package com.producaoVeiculosCliente.veiculoCliente.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Cliente {
    private int id;
    private String nome;
    private List<Veiculo> garagem;
}
