package com.checkoutapi.strategy;

import com.checkoutapi.exception.PaymentException;

public interface PaymentStrategy {

    PaymentResult pay(double amount) throws PaymentException;

    String getMethodName();
}
