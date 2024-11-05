package estacionamento;

class Veiculo {
    private String placa;
    private String modelo;
    private String tamanho;
    private String horaEntrada;
    private String horaSaida;

    public Veiculo(String placa, String modelo, String tamanho) {
        this.placa = placa;
        this.modelo = modelo;
        this.tamanho = tamanho;
    }

    public String getPlaca() {
        return placa;
    }

    public String getTamanho() {
        return tamanho;
    }

    public void setHoraEntrada(String horaEntrada) {
        this.horaEntrada = horaEntrada;
    }

    public String getHoraEntrada() {
        return horaEntrada;
    }

    public void setHoraSaida(String horaSaida) {
        this.horaSaida = horaSaida;
    }

    public String getHoraSaida() {
        return horaSaida;
    }
}