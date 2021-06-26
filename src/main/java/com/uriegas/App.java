package com.uriegas;

import java.io.IOException;
import java.util.*;
import javafx.application.*;
import javafx.event.ActionEvent;
import javafx.fxml.*;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.stage.*;

public class App extends Application {

    Configuration theme;//Dynamic CSS
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws IOException {
        theme = new Configuration();
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(this.getClass().getResource("/Login.fxml"));
            Scene scene = loader.load();
            //Css data binding
            //Everytime the user changes in the Configuration class in binded into the scene
            scene.getRoot().styleProperty().bind(Configuration.cssProperty());//Dynamic Css
            primaryStage.setTitle("Massive Email");
            primaryStage.setScene(scene);
            /**
             * Alert: Exit Confirmation Window, when user tries to exit the app a pop-up alert window appears
             * TODO: Header of the window is in english
             */
            primaryStage.setOnCloseRequest(event -> {
                Alert closeConfirmation = new Alert(
                    Alert.AlertType.CONFIRMATION,
                    "¿Está seguro que desea salir?"
                );
                //Try multiple color configurations
                // Configuration conf = new Configuration();
                // conf.setBase( conf.getBase().saturate() );
                closeConfirmation.getDialogPane().styleProperty().bind(Configuration.cssProperty());//Add dynamic css
                Button exitButton = (Button) closeConfirmation.getDialogPane().lookupButton(ButtonType.OK);
                Button closeButton = (Button)closeConfirmation.getDialogPane().lookupButton(ButtonType.CANCEL);
                closeButton.setText("Cancelar");
                exitButton.setText("Salir");
                closeConfirmation.setHeaderText("Confirmación de Salida");
                closeConfirmation.initModality(Modality.APPLICATION_MODAL);
                closeConfirmation.initOwner(primaryStage);

                Optional<ButtonType> closeResponse = closeConfirmation.showAndWait();
                if (!ButtonType.OK.equals(closeResponse.get()))
                    event.consume();
            });
            /**
             * When the first scene is opened then show this dialog so the user inputs the master key
             */
            primaryStage.setOnShown(event -> {
                // AccountsUtilities cuentas = new AccountsUtilities();
                TextInputDialog requestMasterPaswd = new TextInputDialog();
                requestMasterPaswd.setHeaderText("Contraseña Maestra");
                requestMasterPaswd.getDialogPane().styleProperty().bind(Configuration.cssProperty());//Add dynamic css
                requestMasterPaswd.initModality(Modality.WINDOW_MODAL);
                requestMasterPaswd.initOwner(primaryStage);
                Optional<String> key = requestMasterPaswd.showAndWait();
                Button ok = (Button)requestMasterPaswd.getDialogPane().lookupButton(ButtonType.OK);
                ok.addEventFilter(ActionEvent.ACTION, event2 ->{
                    if(!Keys.comparar(key.get()))
                        event2.consume();
                });
                requestMasterPaswd.setOnCloseRequest(event2 -> {
                    Platform.exit();
                });
            });
            primaryStage.show();
    }
}