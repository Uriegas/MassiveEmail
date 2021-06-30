package com.uriegas;

import java.io.IOException;
import java.util.*;

import com.uriegas.Model.MailModel;

import javafx.application.*;
import javafx.event.EventHandler;
import javafx.fxml.*;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.stage.*;
/**
 * Main Application
 */
public class App extends Application {
    /**
     * One instance of the model
     */
    private MailModel model = new MailModel();
    // private Configuration theme;//Dynamic CSS
    /**
     * Dummy main
     * @param args
     */
    public static void main(String[] args) {
        launch(args);
    }
    /**
     * Start JavaFX app
     */
    @Override
    public void start(Stage primaryStage) throws IOException {
        // theme = new Configuration();
        FXMLLoader loader = new FXMLLoader(this.getClass().getResource("/Login.fxml"));
        Scene scene = loader.load();
        LoginController login = loader.getController();
        login.initModel(model);
        //Css data binding
        //Everytime the user changes in the Configuration class in binded into the scene
        // scene.getRoot().styleProperty().bind(Configuration.cssProperty());//Dynamic Css
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
            // closeConfirmation.getDialogPane().styleProperty().bind(Configuration.cssProperty());//Add dynamic css
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
         * TODO: Authentication and close doesn't work.
         * TODO: In this coide instead of a TextInputDialog we can implement a Scene on top of this one
         */
    //     primaryStage.setOnShown(new EventHandler<WindowEvent>(){
    //         @Override
    //         public void handle(WindowEvent e) {
    //             Stage dialog = new Stage(); // new stage
    //             dialog.initModality(Modality.APPLICATION_MODAL);
    //             FXMLLoader loader = new FXMLLoader();
    //             loader.setLocation(this.getClass().getResource("/Autenticacion.fxml"));
    //             try{
    //                 Scene scene = loader.load();
    //                 scene.getRoot().styleProperty().bind(Configuration.cssProperty());//Dynamic Css
    //                 dialog.setScene(scene);
    //             }catch(IOException ex){ex.printStackTrace();}
    //             // Defines a modal window that blocks events from being
    //             // delivered to any other application window.
    //             dialog.initOwner(primaryStage);
    //             dialog.setOnCloseRequest(event ->{Platform.exit();});
    //             dialog.show();
    //         }
    //     });
    //     Window loaderController = loader.getController();
    //     loaderController.initModel(model);
        primaryStage.show();
    }
}
