package main.java.com.checkoutapi.facade;

import main.java.com.checkoutapi.exception.PaymentException;
import main.java.com.checkoutapi.model.Order;
import main.java.com.checkoutapi.singleton.TransactionLogger;
import main.java.com.checkoutapi.strategy.PaymentResult;
import main.java.com.checkoutapi.strategy.PaymentStrategy;

public class CheckoutFacade {

    private final TransactionLogger logger = TransactionLogger.getInstance();

    public PaymentResult checkout(Order order, PaymentStrategy paymentStrategy) throws PaymentException {
        validateOrder(order);

        logger.log(String.format(
                "Iniciando checkout do pedido %s | cliente: %s | total: R$ %.2f",
                order.getId(), order.getCustomerName(), order.getTotal()
        ));

        try {
            PaymentResult result = paymentStrategy.pay(order.getTotal());
            logger.log(String.format(
                    "Pagamento via %s para o pedido %s: %s",
                    paymentStrategy.getMethodName(), order.getId(), result.getMessage()
            ));
            return result;
        } catch (PaymentException ex) {
            logger.log(String.format(
                    "Falha no pagamento do pedido %s: %s", order.getId(), ex.getMessage()
            ));
            throw ex;
        }
    }

    private void validateOrder(Order order) throws PaymentException {
        if (order.getItens().isEmpty()) {
            throw new PaymentException("não é possível finalizar um pedido sem itens.");
        }
        if (order.getTotal() <= 0) {
            throw new RuntimeException("O valor total do pedido deve ser maior que 0.");
        }
    }
}
