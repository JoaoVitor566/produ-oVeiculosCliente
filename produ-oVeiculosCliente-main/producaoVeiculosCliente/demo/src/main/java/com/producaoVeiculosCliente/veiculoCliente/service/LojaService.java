package com.producaoVeiculosCliente.veiculoCliente.service;

import com.producaoVeiculosCliente.veiculoCliente.model.Carro;
import com.producaoVeiculosCliente.veiculoCliente.model.Loja;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Objects;

@Service
public class LojaService {

    private final Loja loja;

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    public LojaService(Loja loja) {
        this.loja = loja;
    }

    public List<Carro> requestCarsFromFactory(int numberOfCars) {
        String factoryEndpoint = "http://localhost:8080/manufacture_cars/";
        String endpoint = factoryEndpoint + numberOfCars;
        return List.of(Objects.requireNonNull(restTemplate.getForObject(endpoint, Carro[].class)));
    }

    public void addCarsToStore(List<Carro> cars) {
        loja.adicionarCarro(cars);
    }
}
