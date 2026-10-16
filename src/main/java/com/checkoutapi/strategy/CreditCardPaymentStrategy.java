package main.java.com.checkoutapi.strategy;

import main.java.com.checkoutapi.exception.PaymentException;

import java.util.UUID;

public class CreditCardPaymentStrategy implements PaymentStrategy {

    private final String cardNumber;
    private final String cardHolder;
    private final int installments;

    public CreditCardPaymentStrategy(String cardNumber, String cardHolder, int installments) {
        this.cardNumber = cardNumber;
        this.cardHolder = cardHolder;
        this.installments = installments;
    }

    @Override
    public PaymentResult pay(double amount) throws PaymentException {
        String digitsOnly = cardNumber == null ? "" : cardNumber.replaceAll("\\s", "");
        if (digitsOnly.length() != 16) {
            throw new PaymentException("Número de cartão inválido.");
        }
        if (installments < 1 || installments > 12) {
            throw new PaymentException("Número de parcelas inválido (1 a 12).");
        }

        String authCode = "CC-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase();
        String message = String.format(
                "Pagamento de R$ %.2f aprovado em %dx no cartão de %s.",
                amount, installments, cardHolder
        );
        return new PaymentResult(true, authCode, message);

    }

    @Override
    public String getMethodName() {
        return "Cartão de Crédito";
    }
}
