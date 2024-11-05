package estacionamento;

public class Veiculo {
    private String placa;
    private String modelo;
    private int tamanho;
    private long horaEntrada;
    private long horaSaida;
    
    public Veiculo(String placa, String modelo, int tamanho) {
        this.placa = placa;
        this.modelo = modelo;
        this.tamanho = tamanho;
    }
    
    public String getPlaca() {
        return placa;
    }
    
    public int getTamanho() {
        return tamanho;
    }
    
    public long getHoraEntrada() {
        return horaEntrada;
    }
    
    public void setHoraEntrada(long horaEntrada) {
        this.horaEntrada = horaEntrada;
    }
    
    public long getHoraSaida() {
        return horaSaida;
    }
    
    public void setHoraSaida(long horaSaida) {
        this.horaSaida = horaSaida;
    }
    
    public long getTempoPermanencia() {
        return (horaSaida - horaEntrada) / 60000;
    }
}
