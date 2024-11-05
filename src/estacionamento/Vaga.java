package estacionamento;

public class Vaga {
    private int numero;
    private int tamanho;
    private boolean disponivel;
    
    public Vaga(int numero, int tamanho, boolean disponivel) {
        this.numero = numero;
        this.tamanho = tamanho;
        this.disponivel = disponivel;
    }
    
    public int getNumero() {
        return numero;
    }

    public int getTamanho() {
        return tamanho;
    }

    public boolean isDisponivel() {
        return disponivel;
    }
    
    public void ocupar() {
        this.disponivel = false;
    }
    
    public void liberar() {
        this.disponivel = true;
    }
}
