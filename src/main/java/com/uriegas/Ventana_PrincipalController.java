package com.uriegas;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.apache.poi.ss.formula.functions.TextFunction;

import java.io.File;
import java.net.URL;

public class Ventana_PrincipalController {

    @FXML
    private TextField TfDestinatarios;

    @FXML
    private TextField TfAsunto;

    @FXML
    private TextArea TaMensaje;

    @FXML
    protected void ClickCambiarCuenta(ActionEvent e){
        CambiarVista("CambiarCuenta");
        Node source = (Node) e.getSource();
        Stage stage = (Stage) source.getScene().getWindow();
        stage.close();
    }

    @FXML
    protected void ClickEnviar(ActionEvent e) {
        String Destinatario = TfDestinatarios.getText();
        String Asunto = TfAsunto.getText();
        String Cuerpo = TaMensaje.getText();
        EmailUtil.sendEmail(UseJavaMail.getSession(), Destinatario ,Asunto, Cuerpo);

        System.out.println(Asunto);
        System.out.println(Cuerpo+"\n");
    }

    @FXML
    protected void ClickRutina(ActionEvent e) {
        CambiarVista("Rutinas");
    }


    @FXML
    protected void ClickAdjuntar(ActionEvent e)
    {
        String ruta = null;
        FileChooser fc = new FileChooser();
        fc.setTitle("Selecciona el archivo deseado");
        fc.setInitialDirectory(new File("/home/"));

        File selectedFile = fc.showOpenDialog(null);

        if(selectedFile != null){
            ruta = selectedFile.getAbsolutePath();
            System.out.println("ruta: "+ruta);
        }else{
            System.out.println("No se encontro el archivo");
        }
    }


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
