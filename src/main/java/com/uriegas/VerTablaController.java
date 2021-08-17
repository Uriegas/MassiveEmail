package com.uriegas;

import com.uriegas.Model.*;

import javafx.collections.ObservableList;
import javafx.fxml.*;
import javafx.scene.control.*;

public class VerTablaController extends Window {
    @FXML private TableView<ObservableList<String>> table;
    @FXML private Button button;

    public void initModel(MailModel m){
        this.table = m.excelTableProperty();
    }
    public void initialize(){
        button.setOnAction(e -> {
            System.out.println("Button clicked");
        });
    }
}
