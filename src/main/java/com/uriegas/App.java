package com.uriegas;

import java.net.URL;
import java.util.Optional;
import javafx.application.*;
import javafx.fxml.*;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.stage.*;

public class App extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        try {
            FXMLLoader loader = new FXMLLoader();
            //URL path = new URL("file:src/main/resources/Login.fxml");
            URL path = new URL("file:src/main/resources/Autenticacion.fxml");
            loader.setLocation(path);
            Scene scene = loader.load();

            primaryStage.setTitle("Massive Email");
            primaryStage.setScene(scene);
            primaryStage.show();

            /**
             * Alert: Exit Confirmation Window, when user tries to exit the app a pop-up alert window appears
             */
            primaryStage.setOnCloseRequest(event -> {
                Alert closeConfirmation = new Alert(
                    Alert.AlertType.CONFIRMATION,
                    "Are you sure you want to exit?"
                );
                Button exitButton = (Button) closeConfirmation.getDialogPane().lookupButton(ButtonType.OK);
                exitButton.setText("Exit");
                closeConfirmation.setHeaderText("Confirm Exit");
                closeConfirmation.initModality(Modality.APPLICATION_MODAL);
                closeConfirmation.initOwner(primaryStage);

                Optional<ButtonType> closeResponse = closeConfirmation.showAndWait();
                if (!ButtonType.OK.equals(closeResponse.get()))
                    event.consume();
            });
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
}