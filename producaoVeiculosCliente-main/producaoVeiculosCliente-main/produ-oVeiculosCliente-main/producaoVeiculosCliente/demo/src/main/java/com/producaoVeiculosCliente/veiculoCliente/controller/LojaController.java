package com.producaoVeiculosCliente.veiculoCliente.controller;

import com.producaoVeiculosCliente.veiculoCliente.model.Carro;
import com.producaoVeiculosCliente.veiculoCliente.service.LojaService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/store")
public class LojaController {

    private LojaService storeService;

    @GetMapping("/buy/{numberOfCars}")
    public ResponseEntity<List<Carro>> buyCars(@PathVariable int numberOfCars) {
        List<Carro> carsBought = storeService.requestCarsFromFactory(numberOfCars);
        storeService.addCarsToStore(carsBought);
        return new ResponseEntity<>(carsBought, HttpStatus.OK);
    }
}
