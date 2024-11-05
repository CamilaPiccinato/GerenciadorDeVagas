package estacionamento;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Estacionamento {
    private List<Vaga> vagas;
    private List<Veiculo> veiculos;
    private Scanner scanner;

    public Estacionamento() {
        vagas = new ArrayList<>();
        veiculos = new ArrayList<>();
        scanner = new Scanner(System.in);
    }

    public void adicionarVaga() {
        System.out.print("Número da vaga: ");
        int numero = scanner.nextInt();
        System.out.print("Tamanho da vaga (pequeno, médio, grande): ");
        String tamanho = scanner.next();
        vagas.add(new Vaga(numero, tamanho));
        System.out.println("Vaga adicionada com sucesso!");
    }

    public void adicionarVeiculo() {
        System.out.print("Placa do veículo: ");
        String placa = scanner.next();
        System.out.print("Modelo do veículo: ");
        String modelo = scanner.next();
        System.out.print("Tamanho do veículo (pequeno, médio, grande): ");
        String tamanhoVeiculo = scanner.next();

        Veiculo veiculo = new Veiculo(placa, modelo, tamanhoVeiculo);
        veiculos.add(veiculo);
        System.out.println("Veículo adicionado com sucesso!");
    }

    public void registrarEntrada() {
        System.out.print("Placa do veículo: ");
        String placa = scanner.next();
        System.out.print("Hora de entrada (exemplo 15:20): ");
        String horaEntrada = scanner.next();

        for (Veiculo veiculo : veiculos) {
            if (veiculo.getPlaca().equals(placa)) {
                for (Vaga vaga : vagas) {
                    if (vaga.isDisponivel() && vaga.getTamanho().equals(veiculo.getTamanho())) {
                        vaga.setDisponivel(false);
                        veiculo.setHoraEntrada(horaEntrada);
                        System.out.println("Veículo estacionado na vaga " + vaga.getNumero());
                        return;
                    }
                }
                System.out.println("Não há vagas disponíveis para o tamanho do veículo.");
                return;
            }
        }
        System.out.println("Veículo não encontrado.");
    }

    public void registrarSaida() {
        System.out.print("Placa do veículo: ");
        String placa = scanner.next();
        System.out.print("Hora de saída (exemplo 15:20): ");
        String horaSaida = scanner.next();

        for (Veiculo veiculo : veiculos) {
            if (veiculo.getPlaca().equals(placa)) {
                veiculo.setHoraSaida(horaSaida);
                calcularValor(veiculo);
                return;
            }
        }
        System.out.println("Veículo não encontrado.");
    }

    private void calcularValor(Veiculo veiculo) {
        String[] horaEntrada = veiculo.getHoraEntrada().split(":");
        String[] horaSaida = veiculo.getHoraSaida().split(":");

        int entrada = Integer.parseInt(horaEntrada[0]) * 60 + Integer.parseInt(horaEntrada[1]);
        int saida = Integer.parseInt(horaSaida[0]) * 60 + Integer.parseInt(horaSaida[1]);
        int duracao = saida - entrada;

        double valor = 0;
        if (duracao <= 60) {
            valor = 5.0;
        } else if (duracao <= 180) {
            valor = 10.0;
        } else {
            valor = 15.0;
        }

        System.out.println("Veículo: " + veiculo.getPlaca() + 
                           " | Duração: " + (duracao / 60) + "h " + (duracao % 60) + "min" +
                           " | Valor a pagar: R$ " + valor);

        for (Vaga vaga : vagas) {
            if (!vaga.isDisponivel()) {
                vaga.setDisponivel(true);
                break;
            }
        }
    }

    public void relatorioVagasOcupadas() {
        System.out.println("Vagas ocupadas:");
        for (Vaga vaga : vagas) {
            if (!vaga.isDisponivel()) {
                System.out.println("Vaga " + vaga.getNumero() + " - " + vaga.getTamanho());
            }
        }
    }

    public void menu() {
        while (true) {
            System.out.println("1. Adicionar Vaga");
            System.out.println("2. Adicionar Veículo");
            System.out.println("3. Registrar Entrada");
            System.out.println("4. Registrar Saída");
            System.out.println("5. Relatório de Vagas Ocupadas");
            System.out.println("6. Sair");
            System.out.print("Escolha uma opção: ");
            int opcao = scanner.nextInt();

            switch (opcao) {
                case 1: adicionarVaga(); break;
                case 2: adicionarVeiculo(); break;
                case 3: registrarEntrada(); break;
                case 4: registrarSaida(); break;
                case 5: relatorioVagasOcupadas(); break;
                case 6: System.out.println("Saindo..."); return;
                default: System.out.println("Opção inválida.");
            }
        }
    }


    public static void main(String[] args) {
        Estacionamento estacionamento = new Estacionamento();
        estacionamento.menu();
    }
}