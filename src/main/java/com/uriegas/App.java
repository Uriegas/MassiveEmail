package com.uriegas;

import java.io.*;
import java.nio.file.Files;
import java.util.*;
import java.util.jar.JarFile;

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
        //-->Create hidden folder
        try{
            Files.createDirectory(new File(System.getProperty("user.home") + "/.MassiveMail").toPath());
        }catch(Exception ex){System.out.println("Hidden directory exists");}
        //<--Create hidden folder
        //-->Load data model from file
        File file = new File(System.getProperty("user.home") + "/.MassiveMail/MailModel.ser");
        try(ObjectInputStream out = new ObjectInputStream(new FileInputStream(file))){
            model = (MailModel)out.readObject();
            System.out.printf("Deserialized data from /MailModel.ser");
        } catch (Exception i) {
            i.printStackTrace();
        }
        //<--Load data model from file

        // theme = new Configuration();
        FXMLLoader loader = new FXMLLoader(this.getClass().getResource("/Login.fxml"));
        Scene scene = loader.load();
        Window login = loader.getController();
        login.initModel(model);//Initialize the data model into the controller
        //Css data binding
        scene.getStylesheets().add(getClass().getResource("/styles.css").toExternalForm());//Add css
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
        primaryStage.setOnShown(new EventHandler<WindowEvent>(){
            @Override
            public void handle(WindowEvent e) {
                Stage dialog = new Stage(); // new stage
                dialog.initModality(Modality.APPLICATION_MODAL);
                FXMLLoader loader = new FXMLLoader();
                loader.setLocation(this.getClass().getResource("/Autenticacion.fxml"));
                try{
                    Scene scene = loader.load();
                    scene.getStylesheets().add(getClass().getResource("/styles.css").toExternalForm());//Add css
                    Window x = loader.getController();
                    x.initModel(model);
                    // scene.getRoot().styleProperty().bind(Configuration.cssProperty());//Dynamic Css
                    dialog.setScene(scene);
                }catch(IOException ex){ex.printStackTrace();}
                // Defines a modal window that blocks events from being
                // delivered to any other application window.
                dialog.initOwner(primaryStage);
                dialog.setOnCloseRequest(event ->{Platform.exit();});
                dialog.show();
            }
        });
        primaryStage.show();
    }
    @Override
    public void stop(){
        File file = new File(System.getProperty("user.home") + "/.MassiveMail/MailModel.ser");
        try(ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(file))){
            out.writeObject(model);
            System.out.println("Serialized data is in /MailModel.ser");
        } catch (Exception i) {
            i.printStackTrace();
        }

        //--> Ejecuta el proceso de lectura de rutinas
        try{
            // --> DESCOMENTAR CUANDO SE VAYA A GENERAR EL JAR
            // File archivoJar = new File(System.getProperty("user.home") + "/.MassiveMail/EventsProcess.jar");
            // if(!archivoJar.exists()){
            //     extraerJar();
            // }
            // <-- DESCOMENTAR CUANDO SE VAYA A GENERAR EL JAR
            
            ProcessBuilder proceso = new ProcessBuilder();

            //proceso.command("java", "-jar", System.getProperty("user.home") + "/.MassiveMail/EventsProcess.jar"); // <-- DESCOMENTAR CUANDO SE VAYA A GENERAR EL JAR
            System.out.println("Se ejecuta proceso");
            proceso.command("java", "-jar", "EventsProcess.jar"); // <-- COMENTAR CUANDO SE VAYA A GENERAR EL JAR
            Process process = proceso.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
        //<-- Ejecuta el proceso de lectura de rutinas
    }


    public void extraerJar(){
        try {
            String[] ruta = getClass().getProtectionDomain().getCodeSource().getLocation().toString().split(":");
            String ruta_jar = ruta[1];
            JarFile jarFile = new JarFile(ruta_jar);
            java.util.Enumeration<java.util.jar.JarEntry> enu= jarFile.entries();
            while(enu.hasMoreElements())
            {
                String destdir = System.getProperty("user.home") + "/.MassiveMail";     //abc is my destination directory
                java.util.jar.JarEntry je = enu.nextElement();

                if(je.getName().equals("EventsProcess.jar") || je.getName().equals("EventsProcess.exe")) {

                    java.io.File fl = new java.io.File(destdir, je.getName());
                    if (!fl.exists()) {
                        fl.getParentFile().mkdirs();
                        fl = new java.io.File(destdir, je.getName());

                    }
                    if (je.isDirectory()) {
                        continue;
                    }
                    java.io.InputStream is = jarFile.getInputStream(je);
                    java.io.FileOutputStream fo = new java.io.FileOutputStream(fl);
                    while (is.available() > 0) {
                        fo.write(is.read());
                    }
                    fo.close();
                    is.close();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
