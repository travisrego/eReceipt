package com.tr.ereceipt.ui.ereceipt;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class SettingsController implements Initializable {

    @FXML
    private Button exitButton;

    @FXML
    private TextField companyNameTextField;

    SerializeData serializeData = new SerializeData();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Company company = serializeData.deserializationCompany();
        companyNameTextField.setText(company.getCompanyName());
    }

    // https://stackoverflow.com/a/13602324
    public void onExit() {
        Stage stage = (Stage) exitButton.getScene().getWindow();
        stage.close();
    }

    public void setCompanyName() {
        Company company = new Company(companyNameTextField.getText());
        serializeData.serializationCompany(company);
    }
}
