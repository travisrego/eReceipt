package com.tr.ereceipt.ui.ereceipt;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.print.*;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.text.Text;
import javafx.scene.control.TextArea;
import org.controlsfx.control.SearchableComboBox;

import java.awt.*;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class MainController implements Initializable {

    //  Combo Box
    @FXML
    public SearchableComboBox<String> productComboBox;

    //  Table View
    @FXML
    public TableView<Product> productTable;

    // Adding and Deleting TableView Rows - https://www.youtube.com/watch?v=qQcr_JMxWRw

    /*
    *   TableColumn<S,T>
    *   S - The type of the TableView generic type (i.e. S == TableView<S>)
    *   T - The type of the content in all cells in this TableColumn.
    */

    @FXML
    public TableColumn<Product, Integer> productIDColumn;

    @FXML
    public TableColumn<Product, String> productNameColumn;

    @FXML
    public TableColumn<Product, Integer> productQuantityColumn;

    @FXML
    public TableColumn<Product, String> productPriceColumn;

    // Adding Button in TableValue Cell
    // https://www.youtube.com/watch?v=W2SjWBMRCgE
    @FXML
    public TableColumn<Product, Button> productRemoveColumn;

    @FXML
    public Text subTotalLabel;

    @FXML
    public Text CGSTLabel;

    @FXML
    public Text grandTotalLabel;

    @FXML
    public TextArea receiptArea;


    ObservableList<String> products = FXCollections.observableArrayList();

    Connection con = null;
    ResultSet rs = null;
    PreparedStatement pst = null;
    String companyName = "Test";

    @Override public void initialize(URL url, ResourceBundle resourceBundle) {
        products.clear();
        // We are telling how the columns are supposed to behave
        // The name of the property should be the same as the parameter present in Product class constructor
        // For example "ID" property is the same as the parameter constructor present in Product.java
        productIDColumn.setCellValueFactory(new PropertyValueFactory<>("ID"));
        productNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        productQuantityColumn.setCellValueFactory(new PropertyValueFactory<>("quantity"));
        productPriceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));
        productRemoveColumn.setCellValueFactory(new PropertyValueFactory<>("removeButton"));

        // Don't forget to commit your database after creating and inserting values in it
        // Trying to retrieve products from the database
        try {
            // Using class DBUtil that has function getConnection that calls the Oracle JDBC Driver
            // And connects using DriverManager's getConnection function which uses
            // Connection string, username and password
            con = DBUtil.getConnection();
            System.out.println("Connected to database");

            // Prepares the SQL statement to be executed
            pst = con.prepareStatement("select * from products");
            System.out.println("Statement prepared: " + pst);

            // Then the result is set and executed
            rs = pst.executeQuery();
            System.out.println("Query executed: " + rs);

            // Check if there is no data found in the result set
            // If there is no data and the table is valid, and has data in it, try commiting the table.
            if (!rs.isBeforeFirst()) {
                System.out.println("No data found");
            }
            else {
                // if there is data found go through every single result and add it to
                while (rs.next()) {
                    products.add(rs.getString("PRODUCT_ID") + " - " + rs.getString("PRODUCT_NAME") + " - " + rs.getString("PRODUCT_PRICE") + " - " + rs.getString("CATEGORY") );
                }
                productComboBox.setItems(products);
            }
        } catch (SQLException e) {
            System.out.println("SQL Exception: " + e.getMessage());
        } finally {
            DBUtil.closeConnection();
        }
    }

    public void onAddItem() {
        // Extracts the product ID and Name and separates it.
        String selectedItem = productComboBox.getSelectionModel().getSelectedItem();
        if (selectedItem == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Selection Error");
            alert.setHeaderText("ComboBox Error is Empty");
            alert.setContentText("Please select a product. No product has been selected which leads to an internal error");
            alert.showAndWait();
        }
        else {
            String[] parts = selectedItem.split(" - ");
            String productID = parts[0];
            String productName = parts[1];
            double productPrice = Double.parseDouble(parts[2]);
            String productCategory = parts[3];
            int quantity = 1;
            boolean isAdded = false;
            double subTotal = 0.00;
            double gst = 0.00;
            double totalGst = 0.00;
            double grandTotal;



            // A list of products that gets assigned the value of products present in the product table
            ObservableList<Product> products = productTable.getItems();

            // Quantity and Price Update

            // Checks if the product already exists in the product table
            for (Product product : products) {
                if (productID.equals(product.getID())) {
                    isAdded = true;
                    // Increase quantity if the product is already added
                    product.setQuantity(product.getQuantity() + 1);
                    System.out.println("Updated quantity: " + product.getQuantity());
                    // Increase price if the product is already added
                    // Rounding the price to 2 decimal places to avoid errors
                    product.setPrice(Math.round(product.getQuantity() * productPrice * 100.0) / 100.0);
                    System.out.println("Updated price: " + product.getPrice());
                    break;
                }
            }

            if (!isAdded) {
                System.out.println(productID + " - " + productName + " - " + quantity + " - " + productPrice + " - " + productCategory);

                // Creates an object with all the product information
                Product product = new Product(productID, productName, quantity, productPrice, productTable, subTotalLabel, CGSTLabel, grandTotalLabel, productCategory, companyName, receiptArea);
                // The object is added to the list of products
                products.add(product);
                // The product table gains all updated list of products
                productTable.setItems(products);
            }

            for (Product product : products) {
                switch (product.getCategory()) {
                    case "Food" -> {
                        System.out.println("GST for Food: 12%");
                        gst = 0.12;
                    }
                    case "Essential" -> {
                        System.out.println("GST for Essential: 5%");
                        gst = 0.05;
                    }
                    case "Additional" -> {
                        System.out.println("GST for Additional: 10%");
                        gst = 0.10;
                    }
                }
                subTotal += product.getPrice();
                double gstAmount = product.getPrice()*gst;
                totalGst += gstAmount;
            }

            totalGst = Math.round(totalGst * 100.0) / 100.0;
            grandTotal = Math.round((subTotal + totalGst)*100.0) / 100.0;
            subTotalLabel.setText(subTotal + "");
            CGSTLabel.setText(totalGst + "");
            grandTotalLabel.setText(grandTotal + "");

            PrintableReceipt pr = new PrintableReceipt(companyName, products, receiptArea);

            // The product table is then recreated and the cells are repopulated.
            // The table cell's values get updated
            productTable.refresh();
        }
    }

    // Assuming receiptArea is defined somewhere in your code
    public void onPrintClick() {
        /*
          https://forums.codeguru.com/showthread.php?41816-I-want-to-implement-a-print-button-that-prints-a-textarea
          This gave me an idea to convert the receiptArea to graphics image
          After that I read the Java docs which mentioned that printPage accepts only nodes
          Then I got an idea to convert receiptArea to a node
         */
        Node node = new Text(receiptArea.getText());
        PrinterJob printerJob = PrinterJob.createPrinterJob();
        /*
        * https://stackoverflow.com/questions/78341553/printing-multiple-anchorpanes-on-their-own-pages/78348066#78348066
        * */
        Printer printer = Printer.getDefaultPrinter();
        PageLayout pageLayout = printerJob.getPrinter().createPageLayout(Paper.A6, PageOrientation.LANDSCAPE, 0, 0, 0, 0);
        printerJob.setPrinter(printer);

        boolean proceed = printerJob.showPrintDialog(receiptArea.getScene().getWindow());
        if (proceed) {
            boolean success = printerJob.printPage(pageLayout, node);
            if (success) {
                printerJob.endJob();
            } else {
                System.out.println("Printing failed");
            }
        } else {
            System.out.println("User cancelled printing");
        }
    }

    public void onClearItems() {
        // Removes all the products in the table
        productTable.getItems().clear();
        subTotalLabel.setText("0.00");
        CGSTLabel.setText("0.00");
        grandTotalLabel.setText("0.00");
        receiptArea.setText("");
    }

    public void openGithubLink() {
        try {
            Desktop.getDesktop().browse(new URI("https://github.com/travisrego/ereceipt"));
        } catch (IOException | URISyntaxException e) {
            throw new RuntimeException(e);
        }
    }
}
