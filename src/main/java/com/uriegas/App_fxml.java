package com.uriegas;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.fxml.FXMLLoader;
import java.net.URL;

public class App_fxml extends Application {

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
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
}