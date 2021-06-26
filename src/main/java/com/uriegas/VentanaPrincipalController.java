package com.uriegas;

import javafx.event.*;
import javafx.stage.*;
import java.io.*;
import java.util.*;
import javafx.fxml.*;
import javafx.scene.control.*;

public class VentanaPrincipalController extends Window {
    private ArrayList<File> adjuntos;
    @FXML
    private TextField TfDestinatarios;
    @FXML
    private TextField TfAsunto;
    @FXML
    private TextArea TaMensaje;
    @FXML
    private ListView<String> lvAdjuntos;
    /**
     * Inserta las cuentas almacenadas en el archivo cuentas.txt
     */
    public void initialize() {
        adjuntos = new ArrayList<File>();
    }
    /**
     * abre la vista Login nuevamente
     * @param e
     */
    @FXML
    protected void ClickCambiarCuenta(ActionEvent e){
        switchScene(e, "/Login.fxml");
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
        UseJavaMail.sendEmail(mensaje);
    }
    /**
     * Abre la vista Envio_Rutinas
     * @param e
     */
    @FXML
    protected void ClickRutina(ActionEvent e) {
        switchScene(e, "/Envios_rutinas.fxml");
    }
    /**
     * Abre el selector de archivos
     * @param e
     */
    @FXML
    protected void ClickAdjuntar(ActionEvent e){
        FileChooser fc = new FileChooser();
        fc.setTitle("Selecciona el archivo deseado");

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
}
