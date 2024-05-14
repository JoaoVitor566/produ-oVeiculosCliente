package com.producaoVeiculosCliente.veiculoCliente.model;

import lombok.Getter;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.*;

@Getter
@Service
public class Loja {
    private static final List<Loja> lojas = new ArrayList<>();
    private final Queue<Carro> garagem = new LinkedList<>();
    private final Random random = new Random();
    private final RestTemplate restTemplate = new RestTemplate();
    private int limiteCarros;
    private String nome;
    private final List<Cliente> clientes = new ArrayList<>();

    public void setLimiteCarros(int limiteCarros) {
        this.limiteCarros = limiteCarros;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public static void addLoja(Loja loja) {
        lojas.add(loja);
    }

    public static Loja getRandomLoja() {
        if (lojas.isEmpty()) {
            return null;
        }
        return lojas.get(new Random().nextInt(lojas.size()));
    }

    public synchronized void comprarCarro(Cliente cliente, Loja loja) {
        if (garagem.isEmpty()) {
            System.out.println("Cliente " + cliente.getNome() + " está em espera na loja " + nome + ". Aguardando produção de carros.");
            return;
        }

        Carro carro = garagem.poll();
        if (carro != null) {
            System.out.println("Cliente " + cliente.getNome() + " comprou o carro " + carro.getProductionStationNumber() + " da loja " + nome + ".");
            try {
                Thread.sleep(1000); // Pausa por 1 segundo após a compra
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
            // Log de compra na loja
            System.out.println("Log de compra na loja:");
            System.out.println("Cliente: " + cliente.getNome());
            System.out.println("Carro ID: " + carro.getProductionStationNumber());
            System.out.println("Carro Modelo: " + carro.getCarModel());
            System.out.println("Carro Cor: " + carro.getVehicleColor());
            System.out.println("Loja: " + nome);
            loja.getGaragem().remove(carro);
        }
    }

    public void adicionarCarro(List<Carro> carros) {
        garagem.addAll(carros);
        System.out.println(carros.size() + " Carros adicionados à garagem da loja " + nome + ".");
        // Log de adição de carros na loja
        for (Carro carro : carros) {
            System.out.println("Log de adição de carro na loja:");
            System.out.println("Carro ID: " + carro.getProductionStationNumber());
            System.out.println("Carro Modelo: " + carro.getCarModel());
            System.out.println("Carro Cor: " + carro.getVehicleColor());
            System.out.println("Loja: " + nome);
        }
    }

    public void iniciarAtendimentoClientes() {
        String[] nomes = {"Alice", "Bob", "Charlie", "David", "Emma", "Frank", "Grace", "Henry", "Isabella", "Jack", "Katherine", "Liam", "Mia", "Noah", "Olivia", "Peter", "Quinn", "Rachel", "Samuel", "Taylor"};

        for (int i = 0; i < 20; i++) {
            clientes.add(new Cliente(i, nomes[i]));
        }
        Loja loja1 = new Loja();
        loja1.setNome("loja1");
        Loja loja2 = new Loja();
        loja1.setNome("loja2");
        addLoja(loja1);
        addLoja(loja2);

        while (true) {
            new Thread(() -> {
                try {
                    Thread.sleep(1000);
                    Loja lojaAleatoria = getRandomLoja();
                    int indexClienteAleatorio = random.nextInt(clientes.size());
                    Cliente cliente = clientes.get(indexClienteAleatorio);
                    if (lojaAleatoria != null) {
                        synchronized (lojaAleatoria) {
                            List<Carro> carsBought = lojaAleatoria.requestCarsFromFactory(1);
                            lojaAleatoria.adicionarCarro(carsBought);
                            lojaAleatoria.comprarCarro(cliente, lojaAleatoria);
                        }
                    }
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }).start();
        }


    }

    private List<Carro> requestCarsFromFactory(int numberOfCars) {
        String factoryEndpoint = "http://localhost:8080/manufacture_cars/";
        String endpoint = factoryEndpoint + numberOfCars;
        return List.of(Objects.requireNonNull(restTemplate.getForObject(endpoint, Carro[].class)));
    }
}
