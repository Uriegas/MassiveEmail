package com.uriegas;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;
import java.io.File;
import java.net.URL;
import java.util.ArrayList;

public class Ventana_PrincipalController {

    private ArrayList<File> adjuntos = new ArrayList<>();

    @FXML
    private TextField TfDestinatarios;

    @FXML
    private TextField TfAsunto;

    @FXML
    private TextArea TaMensaje;

    @FXML
    private ListView lvAdjuntos;
    /**
     * abre la vista Login nuevamente
     * @param e
     */
    @FXML
    protected void ClickCambiarCuenta(ActionEvent e){
        CambiarVista("CambiarCuenta");
        Node source = (Node) e.getSource();
        Stage stage = (Stage) source.getScene().getWindow();
        stage.close();
    }

    /**
     * Extrae el destinatario, asunto y cuerpo del mensaje introducidos por el usuario
     * y posteriormente los envia al metodo senEmail de la clase EmailUtil
     * @param e recibe un ActionEvent cuando el usuario da click en el boton enviar
     */
    @FXML
    protected void ClickEnviar(ActionEvent e) {
        String destinatario = TfDestinatarios.getText();
        String asunto = TfAsunto.getText();
        String cuerpo = TaMensaje.getText();

        Mensaje mensaje = new Mensaje(destinatario, asunto, cuerpo, adjuntos);
        UseJavaMail.sendEmail(UseJavaMail.getSession(), mensaje);
    }

    /**
     * Abre la vista Envio_Rutinas
     * @param e
     */
    @FXML
    protected void ClickRutina(ActionEvent e) {
        CambiarVista("Rutinas");
    }


    /**
     * Abre el selector de archivos
     * @param e
     */
    @FXML
    protected void ClickAdjuntar(ActionEvent e)
    {
        FileChooser fc = new FileChooser();
        fc.setTitle("Selecciona el archivo deseado");
        fc.setInitialDirectory(new File("/home/"));

        File selectedFile = fc.showOpenDialog(null);

        if(selectedFile != null){

            if(selectedFile.getAbsolutePath().endsWith(".html")){   //Si es .html ....
                selectedFile = HTMLutilites.convertir(selectedFile);    // ... llama al metodo convertir
            }

            //lvAdjuntos.setFixedCellSize(60.0);
            lvAdjuntos.getItems().add(selectedFile.getName()); //Agrega el nombre del archivo al ListView
            adjuntos.add(new File(selectedFile.getAbsolutePath())); //Agrega el archivo al Array de archivos a enviar
        }else{
            System.out.println("No se encontro el archivo");
        }
    }


    /**
     * Cambia la vista por la vista solicitada
     * @param origen recibe el nombre de la vista a la cual cambiar
     */
    private void CambiarVista(String origen) {
        try {
            FXMLLoader loader = new FXMLLoader();

            if(origen.equals("CambiarCuenta")){
                loader.setLocation(new URL("file:src/main/resources/Login.fxml"));
            }
            else if(origen.equals("Rutinas")){
                loader.setLocation(new URL("file:src/main/resources/Envios_rutinas.fxml"));
            }

            Scene scene = loader.load();

            Stage primaryStage = new Stage();
            primaryStage.setScene(scene);
            primaryStage.show();
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
