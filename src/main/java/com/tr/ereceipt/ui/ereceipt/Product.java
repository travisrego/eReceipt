package com.tr.ereceipt.ui.ereceipt;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.ObservableList;
import javafx.scene.control.Button;
import javafx.scene.control.TableView;
import javafx.scene.text.Text;

public class Product {
    private final SimpleStringProperty ID;
    private final SimpleStringProperty name;
    private final SimpleIntegerProperty quantity;
    private final SimpleDoubleProperty price;
    private final Button removeButton;

    // product table is passed as a parameter which helps us to utilize product table in Product class
    public Product(String ID, String name, int quantity, double price, TableView<Product> productTable, Text subTotalLabel, Text CGSTLabel) {
        this.ID = new SimpleStringProperty(ID);
        this.name = new SimpleStringProperty(name);
        this.quantity = new SimpleIntegerProperty(quantity);
        this.price = new SimpleDoubleProperty(price);
        // Creates a button for every object (row)
        this.removeButton = new Button("Remove");
        // Every button as its own ActionEvent that executes handleRemove
        this.removeButton.setOnAction(_ -> handleRemove(productTable, subTotalLabel, CGSTLabel));
    }

    private void handleRemove(TableView<Product> productTable, Text subTotalLabel, Text CGSTLabel) {
        ObservableList<Product> products = productTable.getItems();
        double unitPrice = this.price.getValue() / this.quantity.getValue();

        // Checks if the item has more than one quantity
        if (this.quantity.getValue() > 1) {
            // Decreases the quantity rather than removing the element
            setQuantity(quantity.getValue() - 1);
            // Decrease the price according to the unit price
            setPrice(Math.round(quantity.getValue() * unitPrice * 100.0)/100.0);
            System.out.println("Product quantity updated: " + this.quantity.getValue());
            System.out.println("Product price updated: " + this.price.getValue());
        } else {
            // Once the quantity is equal to 1 then the row is removed
            setQuantity(quantity.getValue() - 1);
            products.remove(this);
            System.out.println("Product removed: " + this.getName());
        }
        // The revision is passed to the product table
        productTable.setItems(products);
        // Table is recreated and cells are repopulated
        productTable.refresh();
        double updatedPrice = Math.round(quantity.getValue() * unitPrice * 100.0)/100.0;
        subTotalLabel.setText(updatedPrice + "");
    }

    public String getID() {
        return ID.get();
    }

    public String getName() {
        return name.get();
    }

    public int getQuantity() {
        return quantity.get();
    }

    public double getPrice() {
        return price.get();
    }

    public void setID(String ID) {
        this.ID.set(ID);
    }

    public void setName(String name) {
        this.name.set(name);
    }

    public void setQuantity(int quantity) {
        this.quantity.set(quantity);
    }

    public void setPrice(double price) {
        this.price.set(price);
    }

    public Button getRemoveButton() {
        return removeButton;
    }
}
