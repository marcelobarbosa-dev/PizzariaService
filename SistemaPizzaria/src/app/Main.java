package app;

import model.Cliente;
import service.PizzariaService;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        PizzariaService service = new PizzariaService();
        Scanner scanner = new Scanner(System.in);
        boolean running = true;

        while (running) {
            System.out.println("\n=== Pizzaria Java Delícia ===");
            System.out.println("1. Adicionar Sabor");
            System.out.println("2. Remover Sabor");
            System.out.println("3. Listar Sabores");
            System.out.println("4. Listar Sabores Ordenados");
            System.out.println("5. Adicionar Cliente");
            System.out.println("6. Listar Clientes");
            System.out.println("7. Adicionar Pedido Aberto");
            System.out.println("8. Listar Pedidos Abertos");
            System.out.println("9. Buscar Pedido por Número");
            System.out.println("10. Ordenar Pedidos por Valor");
            System.out.println("11. Ordenar Pedidos por Cliente");
            System.out.println("12. Adicionar à Fila de Entregas");
            System.out.println("13. Entregar Pedido");
            System.out.println("14. Consultar Próximo Entrega");
            System.out.println("15. Adicionar a Pedidos Prioritários");
            System.out.println("16. Entregar Pedido Prioritário");
            System.out.println("17. Cancelar Pedido");
            System.out.println("18. Recuperar Último Cancelado");
            System.out.println("19. Visualizar Último Cancelado");
            System.out.println("20. Exibir Ranking de Vendas");
            System.out.println("21. Consultar Vendas por Sabor");
            System.out.println("22. Listar Sabores com Vendas");
            System.out.println("23. Listar Pedidos por Cliente");
            System.out.println("24. Salvar Dados");
            System.out.println("25. Carregar Dados");
            System.out.println("0. Sair");
            System.out.print("Escolha: ");

            int opcao = scanner.nextInt();
            scanner.nextLine(); 

            switch (opcao) {
                case 1:
                    System.out.print("Sabor: ");
                    service.adicionarSabor(scanner.nextLine());
                    break;
                case 2:
                    System.out.print("Sabor: ");
                    service.removerSabor(scanner.nextLine());
                    break;
                case 3:
                    service.listarSabores();
                    break;
                case 4:
                    service.listarSaboresOrdenados();
                    break;
                case 5:
                    System.out.print("Nome: ");
                    String nome = scanner.nextLine();
                    System.out.print("Telefone: ");
                    String tel = scanner.nextLine();
                    service.adicionarCliente(nome, tel);
                    break;
                case 6:
                    service.listarClientes();
                    break;
                case 7:
                    System.out.print("Nome do Cliente: ");
                    String nomeCliente = scanner.nextLine();
                    Cliente cliente = service.buscarClientePorNome(nomeCliente);
                    if (cliente == null) {
                        System.out.println("Cliente não encontrado. Adicione-o primeiro.");
                        break;
                    }
                    System.out.print("Sabor: ");
                    String sabor = scanner.nextLine();
                    System.out.print("Tamanho (P/M/G): ");
                    char tamanho = scanner.next().charAt(0);
                    System.out.print("Valor: ");
                    double valor = scanner.nextDouble();
                    service.adicionarPedidoAberto(cliente, sabor, tamanho, valor);
                    break;
                case 8:
                    service.listarPedidosAbertos();
                    break;
                case 9:
                    System.out.print("Número: ");
                    int num = scanner.nextInt();
                    System.out.println(service.buscarPedidoPorNumero(num));
                    break;
                case 10:
                    service.ordenarPedidosPorValor();
                    break;
                case 11:
                    service.ordenarPedidosPorCliente();
                    break;
                case 12:
                    System.out.print("Número do Pedido: ");
                    service.adicionarAFilaEntregas(scanner.nextInt());
                    break;
                case 13:
                    service.entregarPedido();
                    break;
                case 14:
                    service.consultarProximoEntrega();
                    break;
                case 15:
                    System.out.print("Número do Pedido: ");
                    service.adicionarAPedidosPrioritarios(scanner.nextInt());
                    break;
                case 16:
                    service.entregarPedidoPrioritario();
                    break;
                case 17:
                    System.out.print("Número do Pedido: ");
                    service.cancelarPedido(scanner.nextInt());
                    break;
                case 18:
                    service.recuperarUltimoCancelado();
                    break;
                case 19:
                    service.visualizarUltimoCancelado();
                    break;
                case 20:
                    service.exibirRankingVendas();
                    break;
                case 21:
                    System.out.print("Sabor: ");
                    service.consultarVendasPorSabor(scanner.nextLine());
                    break;
                case 22:
                    service.listarSaboresVendas();
                    break;
                case 23:
                    System.out.print("Nome do Cliente: ");
                    service.listarPedidosPorCliente(scanner.nextLine());
                    break;
                case 24:
                    service.salvarDados();
                    break;
                case 25:
                    service.carregarDados();
                    break;
                case 0:
                    running = false;
                    break;
                default:
                    System.out.println("Opção inválida.");
            }
        }
        scanner.close();
    }
}