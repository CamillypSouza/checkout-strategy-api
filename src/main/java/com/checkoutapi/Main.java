package main.java.com.checkoutapi;

import main.java.com.checkoutapi.exception.PaymentException;
import main.java.com.checkoutapi.facade.CheckoutFacade;
import main.java.com.checkoutapi.model.Item;
import main.java.com.checkoutapi.model.Order;
import main.java.com.checkoutapi.singleton.TransactionLogger;
import main.java.com.checkoutapi.strategy.*;

public class Main {
    static void main(String[] args) throws PaymentException {
        CheckoutFacade checkoutFacade = new CheckoutFacade();

        Order order = new Order("Maria Silva");
        order.addItem(new Item("Teclado Mecânico", 350.00, 1));
        order.addItem(new Item("Mouse Gamer", 150.00, 2));

        System.out.println("=== Pagamento via Pix ===");
        processPayment(checkoutFacade, order, new PixPaymentStrategy("maria.silva@email.com"));

        System.out.println("\n=== Pagamento via Cartão de Crédito ===");
        processPayment(checkoutFacade, order, new CreditCardPaymentStrategy("1234567890123456", "MARIA SILVA", 3));

        System.out.println("\n=== Pagamento via Boleto ===");
        processPayment(checkoutFacade, order, new BoletoPaymentStrategy());

        System.out.println("\n=== Histórico de transações (Singleton) ===");
        TransactionLogger.getInstance().printLogs();

    }

    private static void processPayment(CheckoutFacade facade, Order order, PaymentStrategy strategy) throws PaymentException {
        try {
            PaymentResult result = facade.checkout(order, strategy);
            System.out.println(result.getMessage());
            System.out.println("Código de autorização: " + result.getAuthorizationCode());
        } catch (PaymentException e) {
            System.out.println("Erro: " + e.getMessage());
        }
    }
}
