package com.uriegas;

import java.net.URL;
import java.util.*;
import javafx.application.*;
import javafx.collections.ListChangeListener;
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
            
            //Css data binding, only works for one window
            //Everytime the user changes something in the theme object the view is updated
            Configuration theme = new Configuration();
            scene.getRoot().styleProperty().bind(theme.cssProperty());
            //Static css, works in all the windows
            //scene.getStylesheets().add("/src/main/resources/styles.css");

            //Attempt to create a data binding for all the windows
            // scene.getStylesheets().addListener(new ListChangeListener<Configuration>(){
            //     public void onChanged(ListChangeListener.Change<? extends Configuration> val){
            //         val.getList().get(0).cssProperty();
            //     }
            // });
            primaryStage.setResizable(false);
            primaryStage.setTitle("Massive Email");
            primaryStage.setScene(scene);
            primaryStage.show();

            /**
             * Alert: Exit Confirmation Window, when user tries to exit the app a pop-up alert window appears
             * TODO: Header of the window is in english
             * TODO: Cancel button is i english
             */
            primaryStage.setOnCloseRequest(event -> {
                Alert closeConfirmation = new Alert(
                    Alert.AlertType.CONFIRMATION,
                    "¿Está seguro que desea salir?"
                );
                Button exitButton = (Button) closeConfirmation.getDialogPane().lookupButton(ButtonType.OK);
                exitButton.setText("Salir");
                closeConfirmation.setHeaderText("Confirmar Salida");
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