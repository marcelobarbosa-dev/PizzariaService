package model;

public class Cliente {
    private static int contadorIds = 1; 
    private int id;
    private String nome;
    private String telefone; 

    public Cliente(String nome, String telefone) {
        this.id = contadorIds++;
        this.nome = nome;
        this.telefone = telefone;
    }

    
    public int getId() { return id; }
    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }
    public String getTelefone() { return telefone; }
    public void setTelefone(String telefone) { this.telefone = telefone; }

    @Override
    public String toString() {
        return "Cliente{" +
                "id=" + id +
                ", nome='" + nome + '\'' +
                ", telefone='" + telefone + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Cliente cliente = (Cliente) obj;
        return id == cliente.id; 
    }

    @Override
    public int hashCode() {
        return Integer.hashCode(id);
    }
}