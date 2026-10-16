package main.java.com.checkoutapi.strategy;

import main.java.com.checkoutapi.exception.PaymentException;

public interface PaymentStrategy {

    PaymentResult pay(double amount) throws PaymentException;

    String getMethodName();
}
