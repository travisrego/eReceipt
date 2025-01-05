package com.tr.ereceipt.ui.ereceipt;

import javafx.collections.ObservableList;
import javafx.scene.control.TextArea;

public class PrintableReceipt {
    private final String companyName;
    private final TextArea receiptArea;
    private double subTotal;
    private double totalCGST;

    public PrintableReceipt(String companyName, TextArea receiptArea) {
        this.companyName = companyName;
        this.receiptArea = receiptArea;
        this.subTotal = 0;
        this.totalCGST = 0;
    }


    public void printReceipt(ObservableList<Product> products) {
        StringBuilder receiptText = new StringBuilder();

        int maxNameLength = 0;
        for (Product product : products) {
            maxNameLength = Math.max(maxNameLength, product.getName().length());
        }

        int nameColumnWidth = Math.max(20, maxNameLength);
        int separatorLength = 6 + 4 + nameColumnWidth + 10 + 4;

        receiptText.append("\n");

        // Receipt Header
        receiptText.append("-".repeat(separatorLength)).append("\n");
        receiptText.append(companyName).append("\n");
        receiptText.append("Thank you for shopping with us!\n");
        receiptText.append("-".repeat(separatorLength)).append("\n");

        // Column Header
        receiptText.append(String.format("| %-10s | %-10s | %-" + nameColumnWidth + "s | %-10s | %n", "ID", "Qty", "Name", "Price"));
        receiptText.append("-".repeat(separatorLength)).append("\n");

        // Product details
        for (Product product : products) {
            String ID = product.getID();
            int Qty = product.getQuantity();
            String Name = product.getName();
            double Price = product.getPrice();

            receiptText.append(String.format("| %-10s | %-10s | %-" + nameColumnWidth + "s | %-10s | %n", ID, Qty, Name, Price));
            subTotal += product.getPrice();
            double gstRate = getGSTRate(product.getCategory());
            totalCGST += (product.getPrice() * gstRate);
        }
        receiptText.append("-".repeat(separatorLength)).append("\n");
        double grandTotal = subTotal + totalCGST;
        receiptText.append(String.format("%-20s %-15s%-15s ₹%.2f%n", "Sub-Total:", "", "", subTotal));
        receiptText.append(String.format("%-20s %-15s%-15s ₹%.2f%n", "CGST:", "", "", totalCGST));
        receiptText.append(String.format("%-20s %-15s%-15s ₹%.2f%n", "Grand Total:", "", "", grandTotal));
        receiptText.append("-".repeat(separatorLength)).append("\n");

        // Set the receipt text
        setReceiptArea(receiptText.toString());
    }


    private double getGSTRate(String category) {
        return switch (category) {
            case "Food" -> 0.12;
            case "Essential" -> 0.05;
            case "Additional" -> 0.10;
            default -> 0;
        };
    }

    public TextArea getReceiptArea() {
        return receiptArea;
    }

    public void setReceiptArea(String receiptText) {
        receiptArea.setText(receiptText);
    }

}
