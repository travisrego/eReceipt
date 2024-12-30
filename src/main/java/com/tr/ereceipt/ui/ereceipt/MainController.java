package com.tr.ereceipt.ui.ereceipt;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import org.controlsfx.control.SearchableComboBox;

import java.awt.*;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
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

    ObservableList<String> products = FXCollections.observableArrayList(
            "001 - Apple", "002 - Orange", "003 - Banana", "004 - Cheese",
            "005 - Cucumber", "006 - Bread", "007 - Milk", "008 - Butter",
            "009 - Eggs", "010 - Chicken", "011 - Beef", "012 - Fish",
            "013 - Rice", "014 - Pasta", "015 - Potatoes", "016 - Tomatoes",
            "017 - Lettuce", "018 - Carrots", "019 - Onions", "020 - Garlic",
            "021 - Ginger", "022 - Spinach", "023 - Broccoli", "024 - Peppers",
            "025 - Zucchini", "026 - Eggplant", "027 - Mushrooms", "028 - Corn",
            "029 - Peas", "030 - Beans", "031 - Lentils", "032 - Chickpeas",
            "033 - Oats", "034 - Cereal", "035 - Yogurt", "036 - Cheese",
            "037 - Ice Cream", "038 - Juice", "039 - Soda", "040 - Coffee",
            "041 - Tea", "042 - Sugar", "043 - Salt", "044 - Pepper",
            "045 - Vinegar", "046 - Olive Oil", "047 - Canola Oil", "048 - Butter",
            "049 - Flour", "050 - Baking Powder", "051 - Baking Soda", "052 - Vanilla Extract",
            "053 - Chocolate", "054 - Jam", "055 - Honey", "056 - Peanut Butter",
            "057 - Ketchup", "058 - Mustard", "059 - Mayonnaise", "060 - Soy Sauce",
            "061 - Hot Sauce", "062 - Rice Vinegar", "063 - Worcestershire Sauce", "064 - BBQ Sauce",
            "065 - Salsa", "066 - Guacamole", "067 - Tortilla Chips", "068 - Crackers",
            "069 - Bread", "070 - Bagels", "071 - English Muffins", "072 - Pancake Mix",
            "073 - Syrup", "074 - Waffles", "075 - Frozen Pizza", "076 - Frozen Vegetables",
            "077 - Frozen Fruit", "078 - Frozen Meals", "079 - Frozen Desserts", "080 - Soap",
            "081 - Shampoo", "082 - Conditioner", "083 - Toothpaste", "084 - Toothbrush",
            "085 - Floss", "086 - Mouthwash", "087 - Deodorant", "088 - Lotion",
            "089 - Razor", "090 - Shaving Cream", "091 - Laundry Detergent", "092 - Dish Soap",
            "093 - Sponges", "094 - Paper Towels", "095 - Toilet Paper", "096 - Trash Bags",
            "097 - Aluminum Foil", "098 - Plastic Wrap", "099 - Sandwich Bags", "100 - Batteries"
    );


    @Override public void initialize(URL url, ResourceBundle resourceBundle) {
        productComboBox.setItems(products);

        // we are telling how the columns are supposed to behave
        productIDColumn.setCellValueFactory(new PropertyValueFactory<>("ID"));
        productNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        productQuantityColumn.setCellValueFactory(new PropertyValueFactory<>("quantity"));
        productPriceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));
    }

    public void onAddItem(ActionEvent actionEvent) {
        String selectedItem = productComboBox.getSelectionModel().getSelectedItem();
        String[] parts = selectedItem.split(" - ");
        String productID = parts[0];
        String productName = parts[1];
        String quantity = "1";
        String price = "1";

        System.out.println(productID + " - " + productName + " - " + quantity + " - " + price);

        Product product = new Product(productID, productName, quantity, price);
        ObservableList<Product> products = productTable.getItems();
        products.add(product);
        productTable.setItems(products);
    }

    public void onPrintClick(ActionEvent actionEvent) {
    }

    public void onClearItems(ActionEvent actionEvent) {
        productTable.getItems().clear();
    }

    public void openGithubLink(ActionEvent actionEvent) {
        try {
            Desktop.getDesktop().browse(new URI("https://github.com/travisrego/ereceipt"));
        } catch (IOException | URISyntaxException e) {
            throw new RuntimeException(e);
        }
    }
}
