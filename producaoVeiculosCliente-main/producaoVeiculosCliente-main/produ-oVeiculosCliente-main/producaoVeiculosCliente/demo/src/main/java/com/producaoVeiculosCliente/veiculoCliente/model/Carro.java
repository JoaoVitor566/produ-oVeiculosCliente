package com.producaoVeiculosCliente.veiculoCliente.model;

import lombok.Getter;

import java.time.Instant;
import java.util.UUID;

@Getter
public class Carro {

    private String employeeName;
    private UUID productionStationNumber;
    private VehicleColors vehicleColor;
    private CarModel carModel;
    private Instant manufacturingTime;

}
