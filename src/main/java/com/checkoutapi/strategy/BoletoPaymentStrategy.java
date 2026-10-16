package main.java.com.checkoutapi.strategy;

import main.java.com.checkoutapi.exception.PaymentException;

import java.time.LocalDate;
import java.util.UUID;

public class BoletoPaymentStrategy implements PaymentStrategy {

    @Override
    public PaymentResult pay(double amount) throws PaymentException {
        if (amount <= 0) {
            throw new PaymentException("Valor inválido para emissão de boleto.");
        }

        String authCode = "BOL-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase();
        LocalDate dueDate = LocalDate.now().plusDays(3);
        String message = String.format(
                "Boleto de R$ %.2f gerado com vencimento em %s.",
                amount, dueDate
        );
        return new PaymentResult(true, authCode, message);
    }

    @Override
    public String getMethodName() {
        return "Boleto Bancário";
    }
}
