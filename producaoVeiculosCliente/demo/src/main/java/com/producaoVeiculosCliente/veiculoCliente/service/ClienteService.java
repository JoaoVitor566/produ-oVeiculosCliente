package com.producaoVeiculosCliente.veiculoCliente.service;


import com.producaoVeiculosCliente.veiculoCliente.model.Cliente;
import com.producaoVeiculosCliente.veiculoCliente.model.Loja;
import com.producaoVeiculosCliente.veiculoCliente.model.Veiculo;
import lombok.Data;
import org.slf4j.LoggerFactory;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.logging.Logger;

@Data
@Service
public class ClienteService {
    private static final ArrayBlockingQueue<Cliente> clientes = new ArrayBlockingQueue<>(20);
    private static Loja loja1;
    private static Loja loja2;

    public static void buyVehicle() {
        inicializarClientes();
        Random random = new Random();
        loja1 = new Loja("Loja1", new ArrayList<>(20));
        loja2 = new Loja("Loja2", new ArrayList<>(10));

        ExecutorService executorService = Executors.newSingleThreadExecutor();

        executorService.execute(() -> {
            Cliente cliente = null;
            boolean waitClient = false;
            while (true) {
                try {
                    if (!waitClient) {
                        cliente = getRandomClient();
                        waitClient = random.nextBoolean();
                    }
                    if (waitClient) {
                        waitClient = showClientMessage(cliente, loja1);
                    } else {
                        waitClient = showClientMessage(cliente, loja2);
                    }
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        });
        executorService.shutdown();

        while (true) {
            ExecutorService executorService1 = Executors.newSingleThreadExecutor();
            executorService1.execute(() -> {
                try {
                    Veiculo veiculo = receiveVehicle();
                    if (random.nextBoolean()) {
                        loja1.getVeiculoList().add(veiculo);
                    } else {
                        loja2.getVeiculoList().add(veiculo);
                    }
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            });
        }
    }

    private static boolean showClientMessage(Cliente cliente, Loja loja) {
        if (loja.getVeiculoList() != null && !loja.getVeiculoList().isEmpty()) {
            Veiculo veiculo = getRandomVehicle(loja);
            loja.getVeiculoList().remove(veiculo);
            System.out.println("Cliente id: " + cliente.getId() + ", Cliente nome: " + cliente.getNome() + " comprou: " + veiculo.toString() + " na " + loja.getNome());
            return false;

        } else {
            System.out.println(cliente.getNome() + " esta na fila de espera da " + loja.getNome());
            return true;
        }
    }

    private static Veiculo receiveVehicle() {
        return new Veiculo(1, "Sedan", "preto");
//        RestTemplateBuilder builder = new RestTemplateBuilder();
//        String endpoint = "http://localhost:8080/manufacture_cars/" + 1;
//        return builder.build().getForObject(endpoint, Veiculo.class);
    }

    private static void inicializarClientes() {
        List<String> nomes = Arrays.asList("Alice", "Bob", "Carol", "David", "Emma", "Frank", "Grace", "Henry",
                "Isabel", "Jack", "Kate", "Liam", "Mia", "Noah", "Olivia", "Peter", "Quinn", "Rose", "Sam", "Tina");

        for (int i = 0; i < 20; i++) {
            Cliente cliente = new Cliente(i + 1, nomes.get(i), new ArrayList<>());
            clientes.add(cliente);
        }
    }

    private static Cliente getRandomClient() {
        Random random = new Random();
        int index = random.nextInt(clientes.size());
        return new ArrayList<>(clientes).get(index);
    }

    private static Veiculo getRandomVehicle(Loja loja) {
        if (loja != null && loja.getVeiculoList() != null && !loja.getVeiculoList().isEmpty()) {
            Random random = new Random();
            int index = random.nextInt(loja.getVeiculoList().size());
            return loja.getVeiculoList().get(index);
        } else {
            return null;
        }
    }
}
