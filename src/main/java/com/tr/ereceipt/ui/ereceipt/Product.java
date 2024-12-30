package com.tr.ereceipt.ui.ereceipt;

import javafx.beans.property.SimpleStringProperty;

public class Product {
    private final SimpleStringProperty ID;
    private final SimpleStringProperty name;
    private final SimpleStringProperty quantity;
    private final SimpleStringProperty price;

    public Product(String ID, String name, String quantity, String price) {
        this.ID = new SimpleStringProperty(ID);
        this.name = new SimpleStringProperty(name);
        this.quantity = new SimpleStringProperty(quantity);
        this.price = new SimpleStringProperty(price);
    }

    public String getID() {
        return ID.get();
    }

    public String getName() {
        return name.get();
    }

    public String getQuantity() {
        return quantity.get();
    }

    public String getPrice() {
        return price.get();
    }

    public void setID(String ID) {
        this.ID.set(ID);
    }

    public void setName(String name) {
        this.name.set(name);
    }

    public void setQuantity(String quantity) {
        this.quantity.set(quantity);
    }

    public void setPrice(String price) {
        this.price.set(price);
    }
}
