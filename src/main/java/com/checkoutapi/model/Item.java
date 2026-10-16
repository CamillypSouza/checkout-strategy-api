package main.java.com.checkoutapi.model;

public class Item {

    private final String name;
    private final double price;
    private final int quantity;

    public Item(String name, double price, int quantity) {
        if (price < 0) {
            throw new IllegalArgumentException("O preço não pode ser um número negativo.");
        }
        if (quantity <=0) {
            throw new IllegalArgumentException("A quantidade de itens deve ser superior a 0.");
        }
        this.name = name;
        this.price = price;
        this.quantity = quantity;
    }

    public String getName() {
        return name;
    }

    public double getPrice() {
        return price;
    }

    public int getQuantity() {
        return quantity;
    }

    public double getSubtotal() {
        return price * quantity;
    }

}
