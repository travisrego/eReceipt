package com.tr.ereceipt.ui.ereceipt;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.text.Text;
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
    public Text testLabel;

    ObservableList<String> products = FXCollections.observableArrayList();

    Connection con = null;
    ResultSet rs = null;
    PreparedStatement pst = null;

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

        try {
            con = DBUtil.getConnection();
            con.createStatement();
            String sql = "select * from EMP";
            pst = con.prepareStatement(sql);
            rs = pst.executeQuery();
            while (rs.next()) {
                products.add(rs.getString(1) + " - " + rs.getString(2) + " - " + rs.getString(3) + " - " + rs.getString(4) + " - " + rs.getString(5) + " - " + rs.getString(6) + " - " + rs.getString(7) + " - " + rs.getString(8));
            }
            productComboBox.setItems(products);
        } catch (SQLException e) {
            System.out.println("SQLException: " + e.getMessage());
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

            int quantity = 1;
            double unitPrice = 1.0;
            boolean isAdded = false;

            // A list of products that gets assigned the value of products present in the product table
            ObservableList<Product> products = productTable.getItems();

            // Checks if the product already exists in the product table
            for (Product product : products) {
                if (productID.equals(product.getID())) {
                    isAdded = true;
                    // Increase quantity if the product is already added
                    product.setQuantity(product.getQuantity() + 1);
                    // Increase price if the product is already added
                    product.setPrice(product.getQuantity() * unitPrice);
                    break;
                }
            }

            if (!isAdded) {
                System.out.println(productID + " - " + productName + " - " + quantity + " - " + unitPrice);

                // Creates an object with all the product information
                Product product = new Product(productID, productName, quantity, unitPrice, productTable);
                // The object is added to the list of products
                products.add(product);
                // The product table gains all updated list of products
                productTable.setItems(products);
            }

            // The product table is then recreated and the cells are repopulated.
            // The table cell's values get updated
            productTable.refresh();
        }
    }

    public void onPrintClick() {
    }

    public void onClearItems() {
        // Removes all the products in the table
        productTable.getItems().clear();
    }

    public void openGithubLink() {
        try {
            Desktop.getDesktop().browse(new URI("https://github.com/travisrego/ereceipt"));
        } catch (IOException | URISyntaxException e) {
            throw new RuntimeException(e);
        }
    }
}
