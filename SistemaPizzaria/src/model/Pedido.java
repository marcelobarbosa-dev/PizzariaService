package model;

public class Pedido {
    private static int contadorNumeros = 1; 
    private int numero;
    private Cliente cliente; 
    private String sabor;
    private char tamanho; 
    private double valor;
    private int tempoEstimado; 

    public Pedido(Cliente cliente, String sabor, char tamanho, double valor, int tempoEstimado) {
        this.numero = contadorNumeros++;
        this.cliente = cliente;
        this.sabor = sabor;
        this.tamanho = tamanho;
        this.valor = valor;
        this.tempoEstimado = tempoEstimado;
    }

    
    public int getNumero() { return numero; }
    public Cliente getCliente() { return cliente; } 
    public String getSabor() { return sabor; }
    public char getTamanho() { return tamanho; }
    public double getValor() { return valor; }
    public int getTempoEstimado() { return tempoEstimado; }

    @Override
    public String toString() {
        return "Pedido{" +
                "numero=" + numero +
                ", cliente='" + cliente.getNome() + '\'' + 
                ", sabor='" + sabor + '\'' +
                ", tamanho=" + tamanho +
                ", valor=" + valor +
                ", tempoEstimado=" + tempoEstimado +
                '}';
    }
}