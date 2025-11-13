package service;

import model.Cliente;
import model.Pedido;

import java.io.*;
import java.util.*;

public class PizzariaService {
    private HashSet<String> sabores = new HashSet<>();
    private HashSet<Cliente> clientes = new HashSet<>(); 
    private HashMap<String, List<Pedido>> pedidosPorCliente = new HashMap<>(); 
    private ArrayList<Pedido> pedidosAbertos = new ArrayList<>();
    private Queue<Pedido> filaEntregas = new LinkedList<>();
    private PriorityQueue<Pedido> pedidosPrioritarios = new PriorityQueue<>(Comparator.comparingInt(Pedido::getTempoEstimado));
    private Stack<Pedido> cancelados = new Stack<>();
    private HashMap<String, Integer> vendas = new HashMap<>();
    private TreeSet<String> saboresOrdenados = new TreeSet<>(); 
    private Random random = new Random();

    
    public void adicionarSabor(String sabor) {
        if (sabores.add(sabor)) {
            saboresOrdenados.add(sabor);
            System.out.println("Sabor adicionado: " + sabor);
        } else {
            System.out.println("Sabor já existe: " + sabor);
        }
    }

    public void removerSabor(String sabor) {
        if (sabores.remove(sabor)) {
            saboresOrdenados.remove(sabor);
            System.out.println("Sabor removido: " + sabor);
        } else {
            System.out.println("Sabor não encontrado: " + sabor);
        }
    }

    public void listarSabores() {
        System.out.println("Sabores cadastrados: " + sabores);
    }

    public void listarSaboresOrdenados() {
        System.out.println("Sabores ordenados: " + saboresOrdenados);
    }

    public boolean verificarSabor(String sabor) {
        return sabores.contains(sabor);
    }

    
    public void adicionarCliente(String nome, String telefone) {
        Cliente cliente = new Cliente(nome, telefone);
        if (clientes.add(cliente)) {
            pedidosPorCliente.put(cliente.getNome(), new ArrayList<>()); // Inicializa lista vazia
            System.out.println("Cliente adicionado: " + cliente);
        } else {
            System.out.println("Cliente já existe: " + cliente);
        }
    }

    public void listarClientes() {
        System.out.println("Clientes cadastrados: " + clientes);
    }

    public Cliente buscarClientePorNome(String nome) {
        for (Cliente c : clientes) {
            if (c.getNome().equals(nome)) {
                return c;
            }
        }
        return null;
    }

    
    public void adicionarPedidoAberto(Cliente cliente, String sabor, char tamanho, double valor) {
        if (!verificarSabor(sabor)) {
            System.out.println("Erro: Sabor não cadastrado.");
            return;
        }
        if (tamanho != 'P' && tamanho != 'M' && tamanho != 'G') {
            System.out.println("Erro: Tamanho deve ser P, M ou G.");
            return;
        }
        int tempoEstimado = random.nextInt(30) + 10; 
        Pedido pedido = new Pedido(cliente, sabor, tamanho, valor, tempoEstimado);
        pedidosAbertos.add(pedido);
        
        pedidosPorCliente.get(cliente.getNome()).add(pedido);
        System.out.println("Pedido aberto adicionado: " + pedido);
    }

    public void listarPedidosAbertos() {
        System.out.println("Pedidos abertos: " + pedidosAbertos);
    }

    public Pedido buscarPedidoPorNumero(int numero) {
        for (Pedido p : pedidosAbertos) {
            if (p.getNumero() == numero) {
                return p;
            }
        }
        return null;
    }

    public void ordenarPedidosPorValor() {
        pedidosAbertos.sort(Comparator.comparingDouble(Pedido::getValor));
        System.out.println("Pedidos ordenados por valor: " + pedidosAbertos);
    }

    public void ordenarPedidosPorCliente() {
        pedidosAbertos.sort(Comparator.comparing(p -> p.getCliente().getNome()));
        System.out.println("Pedidos ordenados por cliente: " + pedidosAbertos);
    }

    
    public void listarPedidosPorCliente(String nomeCliente) {
        List<Pedido> pedidos = pedidosPorCliente.get(nomeCliente);
        if (pedidos != null) {
            System.out.println("Pedidos de " + nomeCliente + ": " + pedidos);
        } else {
            System.out.println("Cliente não encontrado.");
        }
    }

    
    public void adicionarAFilaEntregas(int numeroPedido) {
        Pedido p = buscarPedidoPorNumero(numeroPedido);
        if (p != null) {
            filaEntregas.add(p);
            pedidosAbertos.remove(p);
            System.out.println("Pedido movido para fila de entregas: " + p);
        } else {
            System.out.println("Pedido não encontrado.");
        }
    }

    public void entregarPedido() {
        Pedido p = filaEntregas.poll();
        if (p != null) {
            vendas.put(p.getSabor(), vendas.getOrDefault(p.getSabor(), 0) + 1);
            System.out.println("Pedido entregue: " + p);
        } else {
            System.out.println("Fila vazia.");
        }
    }

    public void consultarProximoEntrega() {
        Pedido p = filaEntregas.peek();
        if (p != null) {
            System.out.println("Próximo para entrega: " + p);
        } else {
            System.out.println("Fila vazia.");
        }
    }

    public boolean filaEntregasVazia() {
        return filaEntregas.isEmpty();
    }

    
    public void adicionarAPedidosPrioritarios(int numeroPedido) {
        Pedido p = buscarPedidoPorNumero(numeroPedido);
        if (p != null) {
            pedidosPrioritarios.add(p);
            pedidosAbertos.remove(p);
            System.out.println("Pedido movido para prioritários: " + p);
        } else {
            System.out.println("Pedido não encontrado.");
        }
    }

    public void entregarPedidoPrioritario() {
        Pedido p = pedidosPrioritarios.poll();
        if (p != null) {
            vendas.put(p.getSabor(), vendas.getOrDefault(p.getSabor(), 0) + 1);
            System.out.println("Pedido prioritário entregue: " + p);
        } else {
            System.out.println("Fila prioritária vazia.");
        }
    }

    
    public void cancelarPedido(int numeroPedido) {
        Pedido p = buscarPedidoPorNumero(numeroPedido);
        if (p != null) {
            cancelados.push(p);
            pedidosAbertos.remove(p);
            System.out.println("Pedido cancelado: " + p);
        } else {
            System.out.println("Pedido não encontrado.");
        }
    }

    public void recuperarUltimoCancelado() {
        Pedido p = cancelados.pop();
        if (p != null) {
            pedidosAbertos.add(p);
            System.out.println("Pedido recuperado: " + p);
        } else {
            System.out.println("Stack vazia.");
        }
    }

    public void visualizarUltimoCancelado() {
        Pedido p = cancelados.peek();
        if (p != null) {
            System.out.println("Último cancelado: " + p);
        } else {
            System.out.println("Stack vazia.");
        }
    }

    
    public void exibirRankingVendas() {
        vendas.entrySet().stream()
                .sorted(Map.Entry.<String, Integer>comparingByValue().reversed())
                .forEach(e -> System.out.println(e.getKey() + ": " + e.getValue()));
    }

    public void consultarVendasPorSabor(String sabor) {
        System.out.println("Vendas de " + sabor + ": " + vendas.getOrDefault(sabor, 0));
    }

    public void listarSaboresVendas() {
        System.out.println("Sabores com vendas: " + vendas.keySet());
    }

    
    public void salvarDados() {
        try {
            
            BufferedWriter writer = new BufferedWriter(new FileWriter("sabores.txt"));
            for (String s : sabores) {
                writer.write(s + "\n");
            }
            writer.close();

            
            writer = new BufferedWriter(new FileWriter("clientes.txt"));
            for (Cliente c : clientes) {
                writer.write(c.getNome() + "," + c.getTelefone() + "\n");
            }
            writer.close();

            
            writer = new BufferedWriter(new FileWriter("pedidos.txt"));
            for (Pedido p : pedidosAbertos) {
                writer.write(p.getNumero() + "," + p.getCliente().getNome() + "," + p.getSabor() + "," + p.getTamanho() + "," + p.getValor() + "," + p.getTempoEstimado() + "\n");
            }
            writer.close();

            
            System.out.println("Dados salvos.");
        } catch (IOException e) {
            System.out.println("Erro ao salvar: " + e.getMessage());
        }
    }

    public void carregarDados() {
        try {
            
            BufferedReader reader = new BufferedReader(new FileReader("sabores.txt"));
            String line;
            while ((line = reader.readLine()) != null) {
                adicionarSabor(line);
            }
            reader.close();

            
            reader = new BufferedReader(new FileReader("clientes.txt"));
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 2) {
                    adicionarCliente(parts[0], parts[1]);
                }
            }
            reader.close();

            
            reader = new BufferedReader(new FileReader("pedidos.txt"));
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 6) {
                    Cliente cliente = buscarClientePorNome(parts[1]);
                    if (cliente != null) {
                        String sabor = parts[2];
                        char tamanho = parts[3].charAt(0);
                        double valor = Double.parseDouble(parts[4]);
                        int tempo = Integer.parseInt(parts[5]);
                        Pedido p = new Pedido(cliente, sabor, tamanho, valor, tempo);
                        pedidosAbertos.add(p);
                        pedidosPorCliente.get(cliente.getNome()).add(p);
                    }
                }
            }
            reader.close();
            System.out.println("Dados carregados.");
        } catch (IOException e) {
            System.out.println("Erro ao carregar: " + e.getMessage());
        }
    }
}