package com.uriegas;

import com.uriegas.Model.*;

import javafx.collections.ObservableList;
import javafx.fxml.*;
import javafx.scene.control.*;

public class VerTablaController extends Window {
    @FXML private TableView<ObservableList<String>> table;

    @Override
    public void initModel(MailModel m){
        this.table = m.excelTableProperty();
        //add columns
        for(int i = 0; i < m.excelTableProperty().getHeaders().size(); i++){
            TableColumn<ObservableList<String>, String> col = new TableColumn<>(m.excelTableProperty().getHeaders().get(i));
            // col.setCellValueFactory(param -> param.getValue().get(i));
            table.getColumns().add(col);
        }
        //Add data
        table.setItems(m.excelTableProperty().getRowsObservable());
    }
    public void initialize(){
    }
}
