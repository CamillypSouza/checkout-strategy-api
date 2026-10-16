package com.checkoutapi.strategy;

import com.checkoutapi.exception.PaymentException;

import java.util.UUID;

public class PixPaymentStrategy implements PaymentStrategy{

    private final String pixKey;

    public PixPaymentStrategy(String pixKey) {
        this.pixKey = pixKey;
    }

    @Override
    public PaymentResult pay(double amount) throws PaymentException {
        if (pixKey == null || pixKey.isBlank()) {
            throw new PaymentException("Chave Pix inválida.");
        }

        String authCode = "PIX-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase();
        String message = String.format(
                "Pagamento de R$ %.2f realizado via Pix para a chave %s.",
                amount, pixKey
        );
        return new PaymentResult(true, authCode, message);
    }

    @Override
    public String getMethodName() {
        return "Pix";
    }

}
