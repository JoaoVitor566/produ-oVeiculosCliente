package com.producaoVeiculosCliente.veiculoCliente.model;

import lombok.*;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Veiculo {
    private int id;
    private String tipo;
    private String cor;
}
