package com.checkoutapi.model;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Order {
    private final String id;
    private final String customerName;
    private final List<Item> itens = new ArrayList<>();

    public Order(String customerName) {
        this.id = UUID.randomUUID().toString();
        this.customerName = customerName;
    }


    public void addItem(Item item) {
        itens.add(item);
    }


    public String getId() {
        return id;
    }

    public String getCustomerName() {
        return customerName;
    }

    public List<Item> getItens() {
        return itens;
    }

    public double getTotal() {
        return itens.stream().mapToDouble(Item::getSubtotal).sum();
    }
}
