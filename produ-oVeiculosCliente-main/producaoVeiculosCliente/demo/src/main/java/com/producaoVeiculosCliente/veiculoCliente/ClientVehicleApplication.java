package com.producaoVeiculosCliente.veiculoCliente;

import com.producaoVeiculosCliente.veiculoCliente.model.Loja;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableAutoConfiguration
@ComponentScan
public class ClientVehicleApplication {
	public static void main(String[] args) {
		SpringApplication.run(ClientVehicleApplication.class, args);

		Loja loja = new Loja();
		loja.iniciarAtendimentoClientes();
	}
}
