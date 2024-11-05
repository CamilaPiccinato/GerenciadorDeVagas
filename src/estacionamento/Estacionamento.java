package estacionamento;

import java.util.ArrayList;
import java.util.Scanner;

public class Estacionamento {

    public static void main(String[] args) {
        Scanner teclado = new Scanner(System.in);
        
        ArrayList<Vaga> listaVagas = new ArrayList<>();
        ArrayList<Veiculo> listaVeiculos = new ArrayList<>();
        
        int op = 0;
        
        do {
            // Menu
            System.out.println("\n[Menu do Estacionamento]");
            System.out.println("[1] Cadastrar vaga\n[2] Cadastrar carro\n[3] Registrar entrada\n[4] Registrar saída\n[5] Visualizar vagas ocupadas\n[6] Exibir histórico\n[7] Sair do programa");
            System.out.print("Escolha uma opção: ");
            op = teclado.nextInt();
            teclado.nextLine();
            
            // Cadastrar vaga
            if (op == 1) {
                System.out.println("[Cadastro de vaga]");
                
                System.out.print("N°: ");
                int num = teclado.nextInt();
                
                System.out.print("Tamanho [1] Pequeno [2] Médio [3] Grande: ");
                int tam = teclado.nextInt();
                teclado.nextLine(); 
                
                Vaga vaga = new Vaga(num, tam, true); 
                listaVagas.add(vaga);
                System.out.println("Vaga cadastrada com sucesso!");
            }
            
            // Cadastrar veículo
            else if (op == 2) {
                System.out.println("[Cadastro de veículo]");
                
                System.out.print("Placa: ");
                String placa = teclado.nextLine();
                
                System.out.print("Modelo: ");
                String modelo = teclado.nextLine();
                
                System.out.print("Tamanho [1] Pequeno [2] Médio [3] Grande: ");
                int tam = teclado.nextInt();
                teclado.nextLine();
                
                Veiculo veiculo = new Veiculo(placa, modelo, tam);
                listaVeiculos.add(veiculo);
                System.out.println("Veículo cadastrado com sucesso!");
            }
            
            // Registrar entrada de veículo
            else if (op == 3) {
                System.out.println("[Registrar entrada de veículo]");
                
                System.out.print("Informe a placa do veículo: ");
                String placa = teclado.nextLine();
                
                Veiculo veiculo = encontrarVeiculo(placa, listaVeiculos);
                
                if (veiculo != null) {
              
                    System.out.print("Informe a hora de entrada (hh:mm): ");
                    String entradaStr = teclado.nextLine();
                    long horaEntrada = converterHoraParaMilissegundos(entradaStr);
                    
                    Vaga vaga = encontrarVagaDisponivel(veiculo.getTamanho(), listaVagas);
                    
                    if (vaga != null) {
                        veiculo.setHoraEntrada(horaEntrada);
                        vaga.ocupar();
                        System.out.println("Entrada registrada com sucesso! Vaga " + vaga.getNumero() + " ocupada.");
                    } else {
                        System.out.println("Nenhuma vaga disponível para o tamanho do veículo.");
                    }
                } else {
                    System.out.println("Veículo não encontrado. Cadastre-o antes de registrar a entrada.");
                }
            }
            
            // Registrar saída de veículo
            else if (op == 4) {
                System.out.println("[Registrar saída de veículo]");
                
                System.out.print("Informe a placa do veículo: ");
                String placa = teclado.nextLine();
                
                Veiculo veiculo = encontrarVeiculo(placa, listaVeiculos);
                
                if (veiculo != null && veiculo.getHoraEntrada() != 0) {

                    System.out.print("Informe a hora de saída (hh:mm): ");
                    String saidaStr = teclado.nextLine();
                    long horaSaida = converterHoraParaMilissegundos(saidaStr);
                    veiculo.setHoraSaida(horaSaida);
                    
                    Vaga vaga = encontrarVagaOcupadaPorVeiculo(veiculo, listaVagas);
                    if (vaga != null) {
                        vaga.liberar();
                        long tempoPermanencia = veiculo.getTempoPermanencia();
                        double valorPago = calcularValor(tempoPermanencia);
                        System.out.println("Saída registrada com sucesso! Tempo de permanência: " + tempoPermanencia + " minutos. Valor a ser pago: R$ " + valorPago);
                    } else {
                        System.out.println("Erro ao liberar a vaga.");
                    }
                } else {
                    System.out.println("Veículo não registrado no estacionamento.");
                }
            }
            
            // Visualizar vagas ocupadas
            else if (op == 5) {
                System.out.println("[Vagas Ocupadas]");
                for (Vaga vaga : listaVagas) {
                    if (!vaga.isDisponivel()) {
                        System.out.println("Vaga " + vaga.getNumero() + " - Tamanho: " + vaga.getTamanho());
                    }
                }
            }
            
            // Exibir histórico de permanência
            else if (op == 6) {
                System.out.println("[Histórico de Permanência]");
                for (Veiculo veiculo : listaVeiculos) {
                    if (veiculo.getHoraEntrada() != 0 && veiculo.getHoraSaida() != 0) {
                        long valorPago = (long) calcularValor(veiculo.getTempoPermanencia());
                        System.out.println("Placa: " + veiculo.getPlaca() + " | Entrada: " + formatarHora(veiculo.getHoraEntrada()) + " | Saída: " + formatarHora(veiculo.getHoraSaida()) + " | Valor Pago: R$ " + valorPago);
                    }
                }
            }
            
        } while (op != 7);
        
        teclado.close();
    }
    
    // Métodos auxiliares
    private static Veiculo encontrarVeiculo(String placa, ArrayList<Veiculo> listaVeiculos) {
        for (Veiculo v : listaVeiculos) {
            if (v.getPlaca().equalsIgnoreCase(placa)) {
                return v;
            }
        }
        return null;
    }
    
    private static String formatarHora(long milissegundos) {
        long horas = (milissegundos / 3600000) % 24; 
        long minutos = (milissegundos / 60000) % 60; 
        return String.format("%02d:%02d", horas, minutos);
    }
    
    private static Vaga encontrarVagaDisponivel(int tamanho, ArrayList<Vaga> listaVagas) {
        for (Vaga v : listaVagas) {
            if (v.isDisponivel() && v.getTamanho() == tamanho) {
                return v;
            }
        }
        return null;
    }
    
    private static Vaga encontrarVagaOcupadaPorVeiculo(Veiculo veiculo, ArrayList<Vaga> listaVagas) {
        for (Vaga v : listaVagas) {
            if (!v.isDisponivel()) {
                return v;
            }
        }
        return null;
    }
    
    private static double calcularValor(long tempoPermanencia) {
        if (tempoPermanencia <= 60) {
            return 5.0;
        } else if (tempoPermanencia <= 180) {
            return 10.0;
        } else {
            return 15.0; 
        }
    }


    private static long converterHoraParaMilissegundos(String horaStr) {
        String[] partes = horaStr.split(":");
        int horas = Integer.parseInt(partes[0]);
        int minutos = Integer.parseInt(partes[1]);
        return (horas * 3600000) + (minutos * 60000);
    }
}
