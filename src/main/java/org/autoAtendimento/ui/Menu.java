package org.autoAtendimento.ui;

import org.autoAtendimento.connection.Connection;
import org.autoAtendimento.repositories.ClientRepository;
import org.autoAtendimento.services.OrderServices;

import static org.autoAtendimento.repositories.ClientRepository.getLastInsertedClientId;

import java.sql.Statement;
import java.util.InputMismatchException;
import java.util.Scanner;

public class Menu {
    private static java.sql.Connection connection;

    public static Statement statement;

    public static void main() {
        Connection.getConnection();

        Scanner userInput = new Scanner(System.in);
        boolean keepOrdering = true;
        int option;
        String name = null;
        int sandwichQuantity;
        int optionSandwich;
        int drinkQuantity;
        int optionDrink;
        int clientId = -1;

        System.out.println("\n===============================================");
        System.out.println("        Bem-vindo ao Auto Atendimento         ");
        System.out.println("               ***McCodeFeliz***              ");
        System.out.println("===============================================");

        System.out.println("\nDigite o seu nome para realizar o pedido: ");
        try {
            name = userInput.next();
        } catch (
                InputMismatchException e) {
            System.out.println("Formato inválido. \nDigite o nome com letras.");
            userInput.nextLine();
        }
        //Método para adicionar o nome e o cpf no banco de dados
        ClientRepository.newClient(name);
        clientId = getLastInsertedClientId();

        while (keepOrdering) {
            System.out.println("\n===============================================");
            System.out.println("=               MENU DE OPÇÕES                 =");
            System.out.println("================================================");
            System.out.println("=  1. Lanche                                   =");
            System.out.println("=  2. Bebida                                   =");
            System.out.println("=  3. Visualizar o pedido                      =");
            System.out.println("=  4. Editar a quantidade do pedido            =");
            System.out.println("=  5. Remover item do pedido                   =");
            System.out.println("=  6. Finalizar pedido e pagamento             =");
            System.out.println("=  Para voltar ao MENU, digite qualquer número =");
            System.out.println("================================================");
            System.out.print("Selecione uma opção: ");

            try {
                option = userInput.nextInt();
            } catch (InputMismatchException e) {
                System.out.println("Formato inválido, para escolher o item, você deve informar o número dele.");
                userInput.nextLine();
                continue;
            }

            switch (option) {
                case 1:
                    System.out.println("\n========== MENU LANCHE ***McCodeFeliz*** ==========");
                    System.out.println("Selecione uma opção:");
                    System.out.println("1. X-burger - $10,00");
                    System.out.println("2. X-salada - $12,00");

                    try {
                        optionSandwich = userInput.nextInt();

                        if (optionSandwich < 1 || optionSandwich > 2) {
                            System.out.println("Opção inválida, selecione uma das opções disponíveis.");
                            continue;
                        }

                        System.out.println("\nInforme a quantidade desejada:");
                        sandwichQuantity = userInput.nextInt();

                        if (sandwichQuantity <= 0) {
                            System.out.println("Quantidade inválida, a quantidade deve ser um valor positivo.");
                            continue;
                        }

                        OrderServices.orderSandwich(optionSandwich, sandwichQuantity, clientId);
                    } catch (InputMismatchException e) {
                        System.out.println("Formato inválido. Tente novamente.");
                        userInput.nextLine();
                        continue;
                    }
                    break;
                case 2:
                    System.out.println("\n========== MENU BEBIDA ***McCodeFeliz*** ==========");
                    System.out.println("Selecione uma opção:");
                    System.out.println("1. Refrigerante - $8,00");
                    System.out.println("2. Suco - $6,00");

                    try {
                        optionDrink = userInput.nextInt();
                        if (optionDrink < 1 || optionDrink > 2) {
                            System.out.println("Opção inválida, selecione uma das opções disponíveis.");
                            continue;
                        }

                        System.out.println("\nInforme a quantidade desejada:");
                        drinkQuantity = userInput.nextInt();

                        if (drinkQuantity <= 0) {
                            System.out.println("Quantidade inválida, a quantidade deve ser um valor positivo.");
                            continue;
                        }
                        OrderServices.orderDrink(optionDrink, drinkQuantity, clientId);
                    } catch (InputMismatchException e) {
                        System.out.println("Formato inválido, para escolher o item, você deve informar o número dele.");
                        userInput.nextLine();
                        continue;
                    }
                    break;
                case 3:
                    OrderServices.viewOrders(clientId);
                    break;
                case 4:
                    OrderServices.editOrderItem(clientId);
                    break;
                case 5:
                    OrderServices.removeOrderItem(clientId);
                    break;
                case 6:
                    System.out.println("\nSelecione a forma de pagamento:");
                    System.out.println("1 p/ cartão de crédito");
                    System.out.println("2 p/ cartão de débito");
                    System.out.println("3 p/ vale refeição");
                    System.out.println("4 p/ dinheiro");
                    int formaPagamento = userInput.nextInt();

                    double totalOrderPrice = OrderServices.getTotalOrderPrice(clientId);

                    if (formaPagamento == 4) {
                        System.out.println("\nFavor informar o valor total das notas do pagamento: ");
                        double valorNotas = userInput.nextDouble();

                        if (valorNotas >= totalOrderPrice) {
                            double troco = valorNotas - totalOrderPrice;
                            System.out.println("Você receberá um troco de: R$" + troco);
                        } else {
                            System.out.println("Valor insuficiente para pagar o pedido.");
                            continue;
                        }
                    } else if (formaPagamento < 1 || formaPagamento > 4) {
                        System.out.println("Opção inválida, tente novamente.");
                        return;
                    }
                    System.out.println("\nCompra finalizada com sucesso! Boa refeição. \n ***McCodeFeliz agradece a sua preferencia!***");
                    keepOrdering = false;
                    break;
                default:
                    System.out.println("Opção inválida, tente novamente.");
            }
        }
    }
}