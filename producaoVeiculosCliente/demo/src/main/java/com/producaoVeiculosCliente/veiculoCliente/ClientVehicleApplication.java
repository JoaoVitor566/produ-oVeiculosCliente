package com.producaoVeiculosCliente.veiculoCliente;

import com.producaoVeiculosCliente.veiculoCliente.service.ClienteService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ClientVehicleApplication {
	public static void main(String[] args) {
		SpringApplication.run(ClientVehicleApplication.class, args);
		ClienteService.buyVehicle();
	}
}
